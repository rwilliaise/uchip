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
	// (Expansion)
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

	private byte updateNZ(byte value) {
		if (value == 0)
			flags |= ZERO;
		else
			flags &= ~ZERO;

		if (value < 0)
			flags |= NEGATIVE;
		else
			flags &= ~NEGATIVE;
		return value;
	}

	private void immediate() {
		this.value = read();
		this.address = programCounter;
	}

	private void absolute() {
		this.address = readAbsolute(0);
		this.value = read(this.address);
	}

	private static Consumer<MOS6502Processor> absolute(Function<MOS6502Processor, Integer> index) {
		return (p) -> {
			p.address = p.readAbsolute(index.apply(p)); // TODO: extra cycle when going over pages
			p.value = p.read(p.address);
		};
	}

	private void zeroPage() {
		this.address = readZeroPage(0);
		this.value = read(this.address);
	}

	private static Consumer<MOS6502Processor> zeroPage(Function<MOS6502Processor, Integer> index) {
		return (p) -> {
			p.address = p.readZeroPage(index.apply(p));
			p.value = p.read(p.address);
		};
	}

	private int getX() { return x; }
	private int getY() { return y; }

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
		if (value < 0) // testing bit 7 (neat hack)
			this.flags |= CARRY;
		else 
			this.flags &= ~CARRY;
	}

	private void updateRightC(byte value) {
		if ((value & 1) != 0) // bit 0 goes into carry
			this.flags |= CARRY;
		else
			this.flags &= ~CARRY;
	}

	private void asla() {
		updateLeftC(this.accumulator);
		this.accumulator <<= 1;
	}
	private void asl() {
		updateLeftC(this.value);
		this.write(this.address, this.value << 1);
	}

	private void lsra() {
		updateRightC(this.accumulator);
		this.accumulator >>= 1;
	}

	private void lsr() {
		updateRightC(this.accumulator);
		this.write(this.address, this.value >> 1);
	}

	private void nop() { cycles++; } // 2 cycles

	// stack operations
	private void push(byte value) {
		owner.write(Byte.toUnsignedInt(stackPointer--), value);
	}

	private byte pull() {
		return read((short) Byte.toUnsignedInt(stackPointer++));
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
        return (short) (address + index);
    }

	// reading generic data from memory
	private byte read(short address) {
        cycles++;
		return (byte) owner.read(Short.toUnsignedInt(address));
	}

	private void write(short address, byte value) {
		owner.write(Short.toUnsignedInt(address), Byte.toUnsignedInt(value));
	}

    private byte read() {
        return (byte) read(programCounter++); // go to next byte in memory
    }

	private static void instruction(int op, Consumer<MOS6502Processor> cons) {
		INSTRUCTIONS.put((byte) op, cons);
	}

    private static void instruction(int op, Consumer<MOS6502Processor> cons, Consumer<MOS6502Processor> address) {
        INSTRUCTIONS.put((byte) op, (p) -> {
			address.accept(p);
			cons.accept(p);
		});
    }

    static {
		// load
        instruction(0xa9, MOS6502Processor::lda, MOS6502Processor::immediate);
		instruction(0xad, MOS6502Processor::lda, MOS6502Processor::absolute);
		instruction(0xbd, MOS6502Processor::lda, MOS6502Processor.absolute(MOS6502Processor::getX)); // x-indexed absolute
		instruction(0xb9, MOS6502Processor::lda, MOS6502Processor.absolute(MOS6502Processor::getY)); // y-indexed absolute
		instruction(0xa5, MOS6502Processor::lda, MOS6502Processor::zeroPage);
		instruction(0xb5, MOS6502Processor::lda, MOS6502Processor.zeroPage(MOS6502Processor::getX)); // x-indexed zero page
		// TODO: x-indexed zero page indirect
		// TODO: zero page indirect y-indexed
        instruction(0xa2, MOS6502Processor::ldx, MOS6502Processor::immediate);
		instruction(0xae, MOS6502Processor::ldx, MOS6502Processor::absolute);
		instruction(0xa6, MOS6502Processor::ldx, MOS6502Processor.absolute(MOS6502Processor::getY)); // y-indexed absolute
		instruction(0xb6, MOS6502Processor::ldx, MOS6502Processor::zeroPage);
		instruction(0xb6, MOS6502Processor::ldx, MOS6502Processor.zeroPage(MOS6502Processor::getY)); // y-indexed zero page
        instruction(0xa0, MOS6502Processor::ldy, MOS6502Processor::immediate);
		instruction(0xac, MOS6502Processor::ldy, MOS6502Processor::absolute);
		instruction(0xbc, MOS6502Processor::ldy, MOS6502Processor.absolute(MOS6502Processor::getX)); // x-indexed absolute
		instruction(0xa4, MOS6502Processor::ldy, MOS6502Processor::zeroPage);
		instruction(0xb4, MOS6502Processor::ldy, MOS6502Processor.zeroPage(MOS6502Processor::getX)); // x-indexed zero page
		
		// store
		instruction(0x8d, MOS6502Processor::sta, MOS6502Processor::absolute);
		instruction(0x9d, MOS6502Processor::sta, MOS6502Processor.absolute(MOS6502Processor::getX)); // x-indexed absolute
		instruction(0x99, MOS6502Processor::sta, MOS6502Processor.absolute(MOS6502Processor::getY)); // y-indexed absolute
		instruction(0x85, MOS6502Processor::sta, MOS6502Processor::zeroPage);
		instruction(0x95, MOS6502Processor::sta, MOS6502Processor.zeroPage(MOS6502Processor::getX)); // x-indexed zero-page
		// TODO: x-indexed zero page indirect
		// TODO: zero page indirect y-indexed
		instruction(0x8e, MOS6502Processor::stx, MOS6502Processor::absolute);
		instruction(0x86, MOS6502Processor::stx, MOS6502Processor::zeroPage);
		instruction(0x96, MOS6502Processor::stx, MOS6502Processor.zeroPage(MOS6502Processor::getY)); // y-indexed zero page
		instruction(0x8c, MOS6502Processor::sty, MOS6502Processor::absolute);
		instruction(0x84, MOS6502Processor::sty, MOS6502Processor::zeroPage);
		instruction(0x94, MOS6502Processor::sty, MOS6502Processor.zeroPage(MOS6502Processor::getX)); // x-indexed zero page

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

		// nop
		instruction(0xea, MOS6502Processor::nop);
    }
}
