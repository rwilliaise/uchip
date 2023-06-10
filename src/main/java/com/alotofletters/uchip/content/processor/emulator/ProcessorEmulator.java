package com.alotofletters.uchip.content.processor.emulator;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.BoardComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ProcessorEmulator extends BoardComponent {
    public ProcessorEmulator(Board owner, ItemStack stack) {
        super(owner, stack);
    }

    @Override
    public void save(CompoundTag tag) {

    }
}
