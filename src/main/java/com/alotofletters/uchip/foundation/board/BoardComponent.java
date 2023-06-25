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

    public int read(int address) {
        return 0;
    }

    public void write(int address, int value) {
    }

    public int mask() {
        return ((int) Math.pow(2, this.getAddressWidth())) - 1;
    }

    public int mask(int value) {
        return mask() & value;
    }

    /**
     * @return Pins present for data bus.
     */
    public abstract int getDataWidth();

    /**
     * @return Pins present for address bus.
     */
    public abstract int getAddressWidth();

    public void save(CompoundTag tag) {
    }

    public void load(CompoundTag tag) {
    }
}
