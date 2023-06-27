package com.alotofletters.uchip.content.board.shell;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class ShellMenu extends AbstractContainerMenu {
	public ShellMenu(MenuType<?> pMenuType, int pContainerId, Inventory inventory) {
		super(pMenuType, pContainerId);
	}

	@Override
	public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
		return null;
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return false;
	}


}
