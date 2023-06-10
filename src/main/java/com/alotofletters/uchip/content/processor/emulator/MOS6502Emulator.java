package com.alotofletters.uchip.content.processor.emulator;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.Board8;
import net.minecraft.world.item.ItemStack;

public class MOS6502Emulator extends ProcessorEmulator {
    public MOS6502Emulator(Board owner, ItemStack stack) {
        super(owner, stack);
    }
}
