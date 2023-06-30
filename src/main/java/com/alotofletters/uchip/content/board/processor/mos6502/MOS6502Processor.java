package com.alotofletters.uchip.content.board.processor.mos6502;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.Processor;
import com.google.common.collect.Maps;

import net.minecraft.world.item.ItemStack;

public class MOS6502Processor extends Processor {
    private static final HashMap<Byte, Consumer<MOS6502Processor>> INSTRUCTIONS = Maps.newHashMap();

	// flag masks
	private static final int NEGATIVE 			= 0b10000000;
	private static final int OVERFLOW 			= 0b01000000;
	//                       (Expansion)
	private static final int BREAK 				= 0b00010000;
	private static final int DECIMAL			= 0b00001000;
	private static final int INTERRUPT_DISABLE	= 0b00000100;
	private static final int ZERO				= 0b00000010;
	private static final int CARRY				= 0b00000001;

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
        cycles = -1;
        byte op = read();
        Consumer<MOS6502Processor> cons = INSTRUCTIONS.get(op);
        if (cons != null) {
            cons.accept(this);
            return true;
        }
        return false;
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
        if ((Short.toUnsignedInt(address) & 0xff) == 0xff) { highAddress = (short) (address ^ 0xff); }
        this.address = (short) (read(address) | (read(address + 1) << 8));
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

    private void indexedZeroPageIndirect() {

    }

	// load
    private void lda() { this.accumulator = updateNZ(this.value); }
	private void ldx() { this.x = updateNZ(this.value); }
	private void ldy() { this.y = updateNZ(this.value); }

	// store
	private void sta() { this.write(this.address, this.accumulator); }
	private void stx() { this.write(this.address, this.x); }
	private void sty() { this.write(this.address, this.y); }

	// transfer
	private void tax() { this.x = updateNZ(this.accumulator); }
	private void tay() { this.y = updateNZ(this.accumulator); }
	private void tsx() { this.x = updateNZ(this.stackPointer); }
	private void txa() { this.accumulator = updateNZ(this.x); }
	private void txs() { this.stackPointer = updateNZ(this.x); }
	private void tya() { this.accumulator = updateNZ(this.y); }

	// stack operations (push/pull)
	private void pha() { push(this.accumulator); }
	private void php() { push(this.flags); }
	private void pla() { this.accumulator = pull(); }
	private void plp() { this.flags = updateNZ(pull()); }

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
		this.write(this.address, this.value >>> 1);
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

	private void and() { updateNZ(this.accumulator &= this.value); }
	private void bit() {
		byte value = (byte) (this.accumulator & this.value);
		setFlag(NEGATIVE, this.value < 0);
		setFlag(OVERFLOW, (this.value & OVERFLOW) != 0);
		setFlag(ZERO, value == 0);
	}
	private void eor() { updateNZ(this.accumulator ^= this.value); }
	private void ora() { updateNZ(this.accumulator |= this.value); }

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
        int medium = (byte) this.accumulator - (byte) this.value;
        setFlag(OVERFLOW, medium < -128 || medium > 127);
        setFlag(CARRY, medium < -128);
        this.accumulator = updateNZ((byte) medium);
    }

    private void dec() { this.write(this.address, updateNZ(--this.value)); }
    private void dex() { updateNZ(--this.x); cycles++; }
    private void dey() { updateNZ(--this.y); cycles++; }
    private void inc() { this.write(this.address, updateNZ(++this.value)); cycles += 2; } // :i_:
    private void inx() { updateNZ(++this.x); cycles++; }
    private void iny() { updateNZ(++this.y); cycles++; }

    private void brk() {
        push((byte) (programCounter >>> 8));
        push((byte) programCounter);
        programCounter = (short) (read(0xfffe) | (read(0xffff) << 8));
        setFlag(INTERRUPT_DISABLE, true);
    }
    private void jmp() { this.programCounter = this.address; }

	private void nop() { cycles++; } // 2 cycles

	// stack operations
	private void push(byte value) {
		cycles++;
		owner.write(0x0100 & Byte.toUnsignedInt(stackPointer--), value);
	}

	private byte pull() {
		return read((short) (0x0100 & Byte.toUnsignedInt(stackPointer++)));
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
        return (byte) read(programCounter++); // go to next byte in memory
    }

	private static void instruction(int op, Consumer<MOS6502Processor> cons) {
        if (INSTRUCTIONS.containsKey((byte) op)) {
            throw new IllegalStateException("Multiple opcodes registered for 0x" + Integer.toHexString(op));
        }
		INSTRUCTIONS.put((byte) op, cons);
	}

    private static void instruction(int op, Consumer<MOS6502Processor> cons, Consumer<MOS6502Processor> address) {
        instruction(op, (p) -> {
			address.accept(p);
			cons.accept(p);
		});
    }

    private static void whole(int low, Consumer<MOS6502Processor> cons) {
        int high = low + 0x10;
        instruction(low | 0x09, MOS6502Processor::lda, MOS6502Processor::immediate);
		instruction(low | 0x0d, MOS6502Processor::lda, MOS6502Processor::absolute);
		instruction(high | 0x09, MOS6502Processor::lda, MOS6502Processor.absolute(p -> p.y));
		instruction(high | 0x0d, MOS6502Processor::lda, MOS6502Processor.absolute(p -> p.x));
		instruction(low | 0x05, MOS6502Processor::lda, MOS6502Processor::zeroPage);
		instruction(high | 0x05, MOS6502Processor::lda, MOS6502Processor.zeroPage(p -> p.x));
		// TODO: x-indexed zero page indirect
		// TODO: zero page indirect y-indexed
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
		instruction(0x96, MOS6502Processor::stx, MOS6502Processor.zeroPage(p -> p.y)); // -3648, 3696 - 6040 3536
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
        whole(0xc0, MOS6502Processor.cmp(p -> p.accumulator));
        mini(0xe0, MOS6502Processor.cmp(p -> p.x));
        mini(0xc0, MOS6502Processor.cmp(p -> p.x));
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
		
		// nop
		instruction(0xea, MOS6502Processor::nop);
    }
}
