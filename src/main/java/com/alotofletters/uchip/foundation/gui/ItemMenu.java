package com.alotofletters.uchip.foundation.gui;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public abstract class ItemMenu extends AbstractContainerMenu {
    public ItemStack stack;
    protected Inventory playerInventory;

    public ItemMenu(MenuType<?> type, int p_38852_, Inventory inventory, FriendlyByteBuf buf) {
        super(type, p_38852_);
        initialize(inventory, buf.readItem());
    }

    public ItemMenu(MenuType<?> p_38851_, int p_38852_, Inventory inventory, ItemStack item) {
        super(p_38851_, p_38852_);
        initialize(inventory, item);
    }

    protected void initialize(Inventory inventory, ItemStack owner) {
        this.stack = owner;
        this.playerInventory = inventory;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return playerInventory.getSelected() == stack;
    }
}
