package com.alotofletters.uchip.content.machine.board;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.gui.ItemMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class BoardMenu extends ItemMenu {

    public BoardMenu(MenuType<?> type, int p_38852_, Inventory inventory, FriendlyByteBuf buf) {
        super(type, p_38852_, inventory, buf);
    }

    public BoardMenu(MenuType<?> type, int p_38852_, Inventory inventory, ItemStack stack) {
        super(type, p_38852_, inventory, stack);
    }

    @Override
    protected ItemStackHandler createItemInventory() {
        return new ItemStackHandler(4);
    }

    @Override
    protected void initialize(Inventory inventory, ItemStack owner) {
        super.initialize(inventory, owner);
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 153 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 211));
        }
    }

    @Override
    public void clicked(int pSlotId, int pButton, ClickType pClickType, Player pPlayer) {
        if (pSlotId != playerInventory.selected || pClickType == ClickType.THROW)
            super.clicked(pSlotId, pButton, pClickType, pPlayer);
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        ItemStack out = ItemStack.EMPTY;


        return out;
    }

    public Board createBoard() {
        if (stack.getItem() instanceof BoardItem boardItem) {
            return boardItem.createBoard(stack);
        }
        return null;
    }
}
