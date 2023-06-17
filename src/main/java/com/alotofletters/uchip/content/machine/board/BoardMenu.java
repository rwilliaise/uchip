package com.alotofletters.uchip.content.machine.board;

import com.alotofletters.uchip.foundation.gui.ItemMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class BoardMenu extends ItemMenu {

    public BoardMenu(@Nullable MenuType<?> type, int p_38852_, Inventory inventory, @Nullable FriendlyByteBuf buf) {
        super(type, p_38852_, inventory, buf);
    }

    public BoardMenu(@Nullable MenuType<?> type, int p_38852_, Inventory inventory, ItemStack stack) {
        super(type, p_38852_, inventory, stack);
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

}
