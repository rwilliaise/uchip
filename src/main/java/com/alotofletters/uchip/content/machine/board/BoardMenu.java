package com.alotofletters.uchip.content.machine.board;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class BoardMenu extends AbstractContainerMenu {
    public BoardMenu(@Nullable MenuType<?> type, int p_38852_, FriendlyByteBuf buf) {
        super(type, p_38852_);
    }

    public BoardMenu(@Nullable MenuType<?> p_38851_, int p_38852_, Inventory inventory) {
        super(p_38851_, p_38852_);
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
