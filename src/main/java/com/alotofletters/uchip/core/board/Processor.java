package com.alotofletters.uchip.core.board;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public abstract class Processor extends BoardComponent {
    public Processor(Board owner, ItemStack stack) {
        super(owner, stack);
    }

    public abstract boolean clock();

    public abstract void reset();

    /**
     * Pages are sections of memory that can be allocated to components on the board.
     *
     * @return Size of one page in bytes.
     */
    public abstract int getPageSize();

    @Override
    public void save(CompoundTag tag) {
        // nothing to save
    }
}
