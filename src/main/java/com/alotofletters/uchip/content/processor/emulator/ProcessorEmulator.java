package com.alotofletters.uchip.content.processor.emulator;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ProcessorEmulator extends Component {
    public ProcessorEmulator(Board owner, ItemStack stack) {
        super(owner, stack);
    }

    @Override
    public void save(CompoundTag tag) {

    }
}
