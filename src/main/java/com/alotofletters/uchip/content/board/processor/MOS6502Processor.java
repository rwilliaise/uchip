package com.alotofletters.uchip.content.board.processor;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.Processor;

import net.minecraft.world.item.ItemStack;

public class MOS6502Processor extends Processor {
    private byte accumulator, y, x, stackPointer;
    private boolean negative, overflow, brk, decimal, irq, zero, carry;
    private short programCounter;

    private byte cycle;

    public MOS6502Processor(Board owner, ItemStack stack) {
        super(owner, stack);
    }

    @Override
    public boolean clock() {
        // RESET initialization sequence
        switch (cycle++) {
        case 0:
            
            return true;
        default:
            break;
        }
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
