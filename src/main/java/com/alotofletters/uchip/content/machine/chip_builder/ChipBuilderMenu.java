package com.alotofletters.uchip.content.machine.chip_builder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class ChipBuilderMenu extends AbstractContainerMenu {
    public Inventory playerInventory;

    public ChipBuilderMenu(MenuType<?> p_38851_, int containerId, Inventory inventory, @Nullable FriendlyByteBuf buf) {
        super(p_38851_, containerId);
        playerInventory = inventory;
    }


    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return false;
    }
}
