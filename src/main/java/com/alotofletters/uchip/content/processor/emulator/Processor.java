package com.alotofletters.uchip.content.processor.emulator;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.BoardComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public abstract class Processor extends BoardComponent {
    public Processor(Board owner, ItemStack stack) {
        super(owner, stack);
        assert Math.pow(2, getAddressSpace()) == getPageSize() * getPages();
    }

    public abstract boolean clock();

    /**
     * @return Size of one page in bytes.
     */
    public abstract int getPageSize();

    /**
     * Pages are a unit of memory that can be allocated to different components on the board.
     * @return Number of pages this processor can access.
     */
    public abstract int getPages();

    @Override
    public void save(CompoundTag tag) {
        // nothing to save
    }
}
