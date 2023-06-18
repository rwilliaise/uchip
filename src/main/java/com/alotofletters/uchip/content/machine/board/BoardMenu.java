package com.alotofletters.uchip.content.machine.board;

import com.alotofletters.uchip.foundation.gui.ItemMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class BoardMenu extends ItemMenu {

    public BoardMenu(MenuType<?> type, int p_38852_, Inventory inventory, FriendlyByteBuf buf) {
        super(type, p_38852_, inventory, buf);
    }

    public BoardMenu(MenuType<?> type, int p_38852_, Inventory inventory, ItemStack stack) {
        super(type, p_38852_, inventory, stack);
    }

    @Override
    public void clicked(int pSlotId, int pButton, ClickType pClickType, Player pPlayer) {
        if (pSlotId != playerInventory.selected || pClickType == ClickType.THROW)
            super.clicked(pSlotId, pButton, pClickType, pPlayer);
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return ItemStack.EMPTY;
    }


}
