package com.alotofletters.uchip.content.board.processor.mos6502;

import com.alotofletters.uchip.core.board.Board;
import com.alotofletters.uchip.core.board.Processor;
import com.google.common.collect.Maps;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A semi-finished emulator for the MOS 6502 processor. Includes a working ROR
 * instruction, however does not have the illegal opcodes.
 */
public class MOS6502Processor extends Processor {
    private static final HashMap<Byte, Consumer<MOS6502Processor>> INSTRUCTIONS = Maps.newHashMap();

    // flag masks
    private static final int NEGATIVE = 0b10000000;
    private static final int OVERFLOW = 0b01000000;
    //                       (Expansion)
    private static final int BREAK = 0b00010000;
    private static final int DECIMAL = 0b00001000;
    private static final int INTERRUPT_DISABLE = 0b00000100;
    private static final int ZERO = 0b00000010;
    private static final int CARRY = 0b00000001;

    private byte accumulator, y, x, stackPointer, flags;
    private short programCounter;

    // for addressing modes
    private short address; // address found using addressing mode
    private byte value; // value that is at this.address

    // used for delays after instructions
    private int cycles;

    public MOS6502Processor(Board owner, ItemStack stack) {
        super(owner, stack);
        if (owner != null) {
            this.reset();
        }
    }

    @Override
    public boolean clock() {
        if (cycles > 0) {
            cycles--;
            return true;
        }
        // +1 cycle, at least 2 cycles per instruction (since this cycle we just read next op from memory)
        byte op = read();
        Consumer<MOS6502Processor> cons = INSTRUCTIONS.get(op);
        if (cons != null) {
            cons.accept(this);
            return true;
        }
        return false; // for illegal ops just hard crash, for now
    }

    @Override
    public void reset() {
        cycles = 7;
        stackPointer = (byte) 0xff;
        programCounter = (short) mask(owner.read(0xfffd) << 8 + owner.read(0xfffc));
    }


    @Override
    public int getPageSize() {
        return 256; // sqrt(65536)
    }

    @Override
    public int getDataWidth() {
        return 8;
    }

    @Override
    public int getAddressWidth() {
        return 16;
    }

    private void setFlag(int mask, boolean cond) {
        if (cond)
            flags |= mask;
        else
            flags &= ~mask;
    }

    private boolean testFlag(int mask) {
        return (flags & mask) != 0;
    }

    private byte updateNZ(byte value) {
        setFlag(ZERO, value == 0);
        setFlag(NEGATIVE, value < 0);
        return value;
    }

    private void immediate() {
        this.address = programCounter;
        this.value = read();
    }

    private void absolute() {
        this.address = readAbsolute(0);
        this.value = read(this.address);
    }

    private void absoluteIndirect() {
        short address = readAbsolute(0);
        short highAddress = (short) (address + 1);
        if ((Short.toUnsignedInt(address) & 0xff) == 0xff) {
            highAddress = (short) (address ^ 0xff);
        }
        this.address = (short) (read(address) | (read(highAddress) << 8));
        this.value = read(this.address);
    }

    private static Consumer<MOS6502Processor> absolute(Function<MOS6502Processor, Byte> index) {
        return (p) -> {
            p.address = p.readAbsolute(index.apply(p)); // TODO: extra cycle when going over pages
            p.value = p.read(p.address);
        };
    }

    private void zeroPage() {
        this.address = readZeroPage(0);
        this.value = read(this.address);
    }

    private static Consumer<MOS6502Processor> zeroPage(Function<MOS6502Processor, Byte> index) {
        return (p) -> {
            p.address = p.readZeroPage(index.apply(p));
            p.value = p.read(p.address);
        };
    }

    private void xIndexedZeroPageIndirect() {
        byte indirect = (byte) (x + read());
        this.address = read(indirect);
        this.address |= read((byte) (indirect + 1)) << 8;
        this.value = read(this.address);
    }

    private void zeroPageIndirectYIndexed() {
        short indirect = (short) (y + read());
        this.address = read(indirect);
        this.address |= read((short) (indirect + 1)) << 8;
        this.value = read(this.address);
    }

    private void relative() {
        this.address = (short) (this.programCounter + read());
        this.value = read(this.address);
    }

    // load
    private void lda() {
        this.accumulator = updateNZ(this.value);
    }

    private void ldx() {
        this.x = updateNZ(this.value);
    }

    private void ldy() {
        this.y = updateNZ(this.value);
    }

    // store
    private void sta() {
        this.write(this.address, this.accumulator);
    }

    private void stx() {
        this.write(this.address, this.x);
    }

    private void sty() {
        this.write(this.address, this.y);
    }

    // transfer
    private void tax() {
        this.x = updateNZ(this.accumulator);
    }

    private void tay() {
        this.y = updateNZ(this.accumulator);
    }

    private void tsx() {
        this.x = updateNZ(this.stackPointer);
    }

    private void txa() {
        this.accumulator = updateNZ(this.x);
    }

    private void txs() {
        this.stackPointer = updateNZ(this.x);
    }

    private void tya() {
        this.accumulator = updateNZ(this.y);
    }

    // stack operations (push/pull)
    private void pha() {
        push(this.accumulator);
    }

    private void php() {
        push(this.flags);
    }

    private void pla() {
        this.accumulator = pull();
    }

    private void plp() {
        this.flags = updateNZ(pull());
    }

    // arithmetic shift and logical shift
    private void updateLeftC(byte value) {
        setFlag(CARRY, value < 0);
    }

    private void updateRightC(byte value) {
        setFlag(CARRY, (value & 1) != 0);
    }

    private void asla() {
        updateLeftC(this.accumulator);
        this.accumulator <<= 1;
        updateNZ(this.accumulator);
    }

    private void asl() {
        updateLeftC(this.value);
        this.write(this.address, this.value <<= 1);
        updateNZ(this.value);
    }

    private void lsra() {
        updateRightC(this.accumulator);
        this.accumulator >>>= 1;
        updateNZ(this.accumulator);
    }

    private void lsr() {
        updateRightC(this.value);
        this.write(this.address, (byte) (this.value >>> 1));
        updateNZ(this.value);
    }

    private void rola() {
        updateLeftC(this.accumulator);
        this.accumulator = (byte) ((this.accumulator << 1) | (this.accumulator >>> 7));
        updateNZ(this.accumulator);
    }

    private void rol() {
        updateLeftC(this.value);
        this.value = (byte) ((this.value << 1) | (this.value >>> 7));
        updateNZ(this.value);
    }

    private void rora() {
        updateLeftC(this.accumulator);
        this.accumulator = (byte) ((this.accumulator >>> 1) | (this.accumulator << 7));
        updateNZ(this.accumulator);
    }

    private void ror() {
        updateLeftC(this.value);
        this.value = (byte) ((this.value >>> 1) | (this.value << 7));
        updateNZ(this.value);
    }

    private void and() {
        updateNZ(this.accumulator &= this.value);
    }

    private void bit() {
        byte value = (byte) (this.accumulator & this.value);
        setFlag(NEGATIVE, this.value < 0);
        setFlag(OVERFLOW, (this.value & OVERFLOW) != 0);
        setFlag(ZERO, value == 0);
    }

    private void eor() {
        updateNZ(this.accumulator ^= this.value);
    }

    private void ora() {
        updateNZ(this.accumulator |= this.value);
    }

    private void adc() {
        int medium = (int) this.value + (int) this.accumulator;
        setFlag(OVERFLOW, medium < -128 || medium > 127);
        setFlag(CARRY, medium > 127);
        this.accumulator = updateNZ((byte) medium);
    }

    private static Consumer<MOS6502Processor> cmp(Function<MOS6502Processor, Byte> comparable) {
        return (p) -> {
            byte result = (byte) (Byte.toUnsignedInt(comparable.apply(p)) - Byte.toUnsignedInt(p.value));
            p.updateNZ(result);
            p.setFlag(CARRY, p.value <= comparable.apply(p));
        };
    }

    private void sbc() {
        int medium = this.accumulator - this.value;
        setFlag(OVERFLOW, medium < -128 || medium > 127);
        setFlag(CARRY, medium < -128);
        this.accumulator = updateNZ((byte) medium);
    }

    private void dec() {
        this.write(this.address, updateNZ(--this.value));
    }

    private void dex() {
        updateNZ(--this.x);
        cycles++;
    }

    private void dey() {
        updateNZ(--this.y);
        cycles++;
    }

    private void inc() {
        this.write(this.address, updateNZ(++this.value));
        cycles += 2; // :i_:
    }

    private void inx() {
        updateNZ(++this.x);
        cycles++;
    }

    private void iny() {
        updateNZ(++this.y);
        cycles++;
    }

    private void brk() {
        this.push(programCounter);
        programCounter = (short) (read((short) 0xfffe) | (read((short) 0xffff) << 8)); // 0x notation is int by default
        setFlag(INTERRUPT_DISABLE, true);
    }

    private void jmp() {
        this.programCounter = this.address;
    }

    private void jsr() {
        this.push(programCounter);
        this.programCounter = this.address;
    }

    private void rti() {
        flags = pull();
        programCounter = pullShort();
    }

    private void rts() {
        programCounter = pullShort();
        programCounter++;
    }


    private void nop() {
    }

    // stack operations
    private void push(byte value) {
        cycles++;
        owner.write(0x0100 | Byte.toUnsignedInt(stackPointer--), value); // page 1
    }

    private void push(short value) {
        push((byte) (value >>> 8));
        push((byte) (value));
    }

    private byte pull() {
        return read((short) (0x0100 | Byte.toUnsignedInt(stackPointer++))); // page 1
    }

    private short pullShort() {
        short value = pull();
        value |= ((short) pull()) << 8;
        return value;
    }

    // reading addresses from memory
    private short readZeroPage(int index) {
        return (short) ((read() + index) & 0xff);
    }

    private short readAbsolute(int index) {
        int address = read();
        address |= read() << 8;
        address += index;
        cycles++;
        return (short) address;
    }

    // reading generic data from memory
    private byte read(short address) {
        cycles++;
        return (byte) owner.read(Short.toUnsignedInt(address));
    }

    private void write(short address, byte value) {
        cycles++;
        owner.write(Short.toUnsignedInt(address), value);
    }

    private byte read() {
        return read(programCounter++); // go to next byte in memory
    }

    private static void instruction(int op, Consumer<MOS6502Processor> cons) {
        if (INSTRUCTIONS.containsKey((byte) op)) {
            throw new IllegalStateException("Multiple opcodes registered for 0x" + Integer.toHexString(op));
        }
        INSTRUCTIONS.put((byte) op, cons);
    }

    private static void instruction(int op, Consumer<MOS6502Processor> cons, Consumer<MOS6502Processor> address) {
        instruction(op, p -> {
            address.accept(p);
            cons.accept(p);
        });
    }

    private static void branch(int op, Function<MOS6502Processor, Boolean> func) {
        instruction(op, p -> {
            if (func.apply(p)) {
                p.cycles++;
                p.programCounter = p.address;
            }
        }, MOS6502Processor::relative);
    }

    private static void whole(int low, Consumer<MOS6502Processor> cons) {
        int high = low + 0x10;
        instruction(low | 0x09, cons, MOS6502Processor::immediate);
        instruction(low | 0x0d, cons, MOS6502Processor::absolute);
        instruction(high | 0x09, cons, MOS6502Processor.absolute(p -> p.y));
        instruction(high | 0x0d, cons, MOS6502Processor.absolute(p -> p.x));
        instruction(low | 0x05, cons, MOS6502Processor::zeroPage);
        instruction(high | 0x05, cons, MOS6502Processor.zeroPage(p -> p.x));
        instruction(low | 0x01, cons, MOS6502Processor::xIndexedZeroPageIndirect);
        instruction(high | 0x01, cons, MOS6502Processor::zeroPageIndirectYIndexed);
    }

    private static void mini(int low, Consumer<MOS6502Processor> cons) {
        instruction(low, cons, MOS6502Processor::immediate);
        instruction(low | 0x0c, cons, MOS6502Processor::absolute);
        instruction(low | 0x04, cons, MOS6502Processor::zeroPage);
    }

    private static void index(int low, Consumer<MOS6502Processor> cons) {
        int high = low + 0x10;
        instruction(low | 0x0e, cons, MOS6502Processor::absolute);
        instruction(low | 0x06, cons, MOS6502Processor::zeroPage);
        instruction(high | 0x0e, cons, MOS6502Processor.absolute(p -> p.x));
        instruction(high | 0x06, cons, MOS6502Processor.zeroPage(p -> p.x));
    }

    private static void shift(int low, Consumer<MOS6502Processor> consa, Consumer<MOS6502Processor> cons) {
        instruction(low | 0x0a, consa);
        index(low, cons);
    }

    static {
        // TODO: undocuented opcodes
        // TODO: ensure cycle counts are correct

        // load
        whole(0xa0, MOS6502Processor::lda);
        instruction(0xa2, MOS6502Processor::ldx, MOS6502Processor::immediate);
        instruction(0xae, MOS6502Processor::ldx, MOS6502Processor::absolute);
        instruction(0xbe, MOS6502Processor::ldx, MOS6502Processor.absolute(p -> p.y));
        instruction(0xa6, MOS6502Processor::ldx, MOS6502Processor::zeroPage);
        instruction(0xb6, MOS6502Processor::ldx, MOS6502Processor.zeroPage(p -> p.y));

        instruction(0xa0, MOS6502Processor::ldy, MOS6502Processor::immediate);
        instruction(0xac, MOS6502Processor::ldy, MOS6502Processor::absolute);
        instruction(0xbc, MOS6502Processor::ldy, MOS6502Processor.absolute(p -> p.x));
        instruction(0xa4, MOS6502Processor::ldy, MOS6502Processor::zeroPage);
        instruction(0xb4, MOS6502Processor::ldy, MOS6502Processor.zeroPage(p -> p.x));

        // store
        whole(0x80, MOS6502Processor::sta);
        instruction(0x8e, MOS6502Processor::stx, MOS6502Processor::absolute);
        instruction(0x86, MOS6502Processor::stx, MOS6502Processor::zeroPage);
        instruction(0x96, MOS6502Processor::stx, MOS6502Processor.zeroPage(p -> p.y));
        instruction(0x8c, MOS6502Processor::sty, MOS6502Processor::absolute);
        instruction(0x84, MOS6502Processor::sty, MOS6502Processor::zeroPage);
        instruction(0x94, MOS6502Processor::sty, MOS6502Processor.zeroPage(p -> p.x));

        // transfer
        instruction(0xaa, MOS6502Processor::tax);
        instruction(0xa8, MOS6502Processor::tay);
        instruction(0xba, MOS6502Processor::tsx);
        instruction(0x8a, MOS6502Processor::txa);
        instruction(0x9a, MOS6502Processor::txs);
        instruction(0x98, MOS6502Processor::tya);

        // stack operations
        instruction(0x48, MOS6502Processor::pha);
        instruction(0x08, MOS6502Processor::php);
        instruction(0x68, MOS6502Processor::pla);
        instruction(0x28, MOS6502Processor::plp);

        // shifts
        shift(0x00, MOS6502Processor::asla, MOS6502Processor::asl);
        shift(0x40, MOS6502Processor::lsra, MOS6502Processor::lsr);
        shift(0x20, MOS6502Processor::rola, MOS6502Processor::rol);
        shift(0x60, MOS6502Processor::rora, MOS6502Processor::ror);

        // bit ops
        whole(0x20, MOS6502Processor::and);
        instruction(0x2c, MOS6502Processor::bit, MOS6502Processor::absolute);
        instruction(0x24, MOS6502Processor::bit, MOS6502Processor::zeroPage);
        whole(0x40, MOS6502Processor::eor);
        whole(0x00, MOS6502Processor::ora);

        // arithmetic operations
        whole(0x60, MOS6502Processor::adc);
        whole(0xc0, MOS6502Processor.cmp(p -> p.accumulator));  // cmp
        mini(0xe0, MOS6502Processor.cmp(p -> p.x));             // cpx
        mini(0xc0, MOS6502Processor.cmp(p -> p.x));             // cpy
        whole(0xe0, MOS6502Processor::sbc);

        // inc/dec
        index(0xc0, MOS6502Processor::dec);
        instruction(0xca, MOS6502Processor::dex);
        instruction(0x88, MOS6502Processor::dey);
        index(0xe0, MOS6502Processor::inc);
        instruction(0xe8, MOS6502Processor::inx);
        instruction(0xc8, MOS6502Processor::iny);

        // jump/interrupt
        instruction(0x00, MOS6502Processor::brk);
        instruction(0x4c, MOS6502Processor::jmp, MOS6502Processor::absolute);
        instruction(0x6c, MOS6502Processor::jmp, MOS6502Processor::absoluteIndirect);
        instruction(0x20, MOS6502Processor::jsr, MOS6502Processor::absolute);
        instruction(0x40, MOS6502Processor::rti);
        instruction(0x60, MOS6502Processor::rts);

        // branch
        branch(0x90, p -> !p.testFlag(CARRY));      // bcc
        branch(0xb0, p -> p.testFlag(CARRY));       // bcs
        branch(0xf0, p -> p.testFlag(ZERO));        // beq
        branch(0x30, p -> p.testFlag(NEGATIVE));    // bmi
        branch(0xd0, p -> !p.testFlag(ZERO));       // bne
        branch(0x10, p -> !p.testFlag(NEGATIVE));   // bpl
        branch(0x50, p -> !p.testFlag(OVERFLOW));   // bvc
        branch(0x70, p -> p.testFlag(OVERFLOW));    // bvs

        // flag ops
        instruction(0x18, p -> p.setFlag(CARRY, false));                // clc
        instruction(0xd8, p -> p.setFlag(DECIMAL, false));              // cld
        instruction(0x58, p -> p.setFlag(INTERRUPT_DISABLE, false));    // cli
        instruction(0xb8, p -> p.setFlag(OVERFLOW, false));             // clv
        instruction(0x38, p -> p.setFlag(CARRY, true));                 // sec
        instruction(0xf8, p -> p.setFlag(DECIMAL, true));               // sed
        instruction(0x78, p -> p.setFlag(INTERRUPT_DISABLE, true));     // sei

        // nop
        instruction(0xea, MOS6502Processor::nop);
    }
}
