package com.alotofletters.uchip.foundation.board;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

/**
 * Represents an item that interfaces with buses on the board.
 *
 * @see BoardComponent
 */
public abstract class BoardComponent {
    protected final Board owner;
    protected final ItemStack stack;

    public BoardComponent(Board owner, ItemStack stack) {
        this.owner = owner;
        this.stack = stack;

        load(stack.getOrCreateTag());
    }

    public Board getOwner() {
        return owner;
    }

    public ItemStack getStack() {
        return stack;
    }

    public abstract int getDataWidth();

    public abstract int getAddressWidth();

    public void save(CompoundTag tag) {}

    public void load(CompoundTag tag) {}
}
