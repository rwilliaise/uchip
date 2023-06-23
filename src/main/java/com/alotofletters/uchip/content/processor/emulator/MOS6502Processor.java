package com.alotofletters.uchip.content.processor.emulator;

import com.alotofletters.uchip.foundation.board.Board;
import net.minecraft.world.item.ItemStack;

public class MOS6502Processor extends Processor {
    private byte accumulator, y, x, programCounter, stackPointer;
    private boolean negative, overflow, brk, decimal, irq, zero, carry;

    public MOS6502Processor(Board owner, ItemStack stack) {
        super(owner, stack);
    }

    @Override
    public boolean clock() {
        return false;
    }

    @Override
    public int getPageSize() {
        return 256;
    }

    @Override
    public int getPages() {
        return 256;
    }

    @Override
    public int getDataWidth() {
        return 8;
    }

    @Override
    public int getAddressSpace() {
		return 65536;
    }
}
