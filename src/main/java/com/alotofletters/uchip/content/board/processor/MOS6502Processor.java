package com.alotofletters.uchip.content.board.processor;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.Processor;

import net.minecraft.world.item.ItemStack;

public class MOS6502Processor extends Processor {
    private int accumulator, y, x, stackPointer, programCounter, flags;

    public MOS6502Processor(Board owner, ItemStack stack) {
        super(owner, stack);
		this.reset();
    }

    @Override
    public boolean clock() {
        return false;
    }

	@Override
	public void reset() {
		// TODO: 7 cycle RESET interrupt replication?
		stackPointer = 0xff;
		programCounter = mask(owner.read(0xfffd) << 8 + owner.read(0xfffc));
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
}
