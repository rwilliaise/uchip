package com.alotofletters.uchip.content.board.processor.mos6502;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.Processor;
import com.google.common.collect.Maps;

import net.minecraft.world.item.ItemStack;

public class MOS6502Processor extends Processor {
    private static final HashMap<Byte, BiConsumer<MOS6502Processor, Byte>> INSTRUCTIONS = Maps.newHashMap();
    private byte accumulator, y, x, stackPointer, flags;
    private short programCounter;

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
        BiConsumer<MOS6502Processor, Byte> cons = INSTRUCTIONS.get(op);
        if (cons != null) {
            cons.accept(this, op);
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

    public void lda(Byte op) {
        switch (Byte.toUnsignedInt(op)) {
        case 0xa9: // immediat
            this.accumulator = read();
            return;
        case 0xad: // absolute
            this.accumulator = readAbsolute(0);
            return;
        case 0xbd: // x-indexed absolute
            this.accumulator = readAbsolute(x);
            return;
        case 0xb9: // y-indexed absolute
            this.accumulator = readAbsolute(y);
            return;
        case 0xa5: // zero page
            this.accumulator = readZeroPage(0);
            return;
        case 0xb5: // x-indexed zero page
            this.accumulator = readZeroPage(x);
            return;
        case 0xa1: // x-indexed zero page indirect
            this.accumulator = (byte) owner.read(readZeroPage(x));
            return;
        case 0xb1: // zero page indirect y-indexed
            this.accumulator = (byte) owner.read(readZeroPage(y));
            return;
        }
    }

    private void updateNZ(int value) {
        
    }

    private byte readZeroPage(int index) {
        return (byte) owner.read((read() + index) & 0xff);
    }

    private byte readAbsolute(int index) {
        int address = read();
        address |= read() << 8;
        address += index;
        cycles++;
        return (byte) owner.read(address + index);
    }

    private byte read() { 
        cycles++;
        return (byte) owner.read(programCounter++);
    }

    private static void addInstruction(BiConsumer<MOS6502Processor, Byte> cons, int... ops) {
        for (int op : ops) {
            INSTRUCTIONS.put((byte) op, cons);
        }
    }

    static {
        addInstruction(MOS6502Processor::lda, 0xa9);
    }
}
