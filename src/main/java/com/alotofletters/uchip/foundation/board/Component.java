package com.alotofletters.uchip.foundation.board;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

/**
 * Represents an item that interfaces with buses on the board.
 *
 * @see Component
 */
public abstract class Component {
    protected final Board owner;
    protected final ItemStack stack;

    public Component(Board owner, ItemStack stack) {
        this.owner = owner;
        this.stack = stack;
    }

    public Board getOwner() {
        return owner;
    }

    public abstract void save(CompoundTag tag);
}
