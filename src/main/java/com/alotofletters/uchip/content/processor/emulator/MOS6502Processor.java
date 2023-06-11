package com.alotofletters.uchip.content.processor.emulator;

import com.alotofletters.uchip.foundation.board.Board;
import net.minecraft.world.item.ItemStack;

public class MOS6502Processor extends Processor {
    public MOS6502Processor(Board owner, ItemStack stack) {
        super(owner, stack);
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
