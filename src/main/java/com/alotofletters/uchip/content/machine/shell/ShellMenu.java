package com.alotofletters.uchip.content.machine.shell;

import org.jetbrains.annotations.Nullable;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class ShellMenu extends AbstractContainerMenu {
    private Inventory playerInventory;
    private Shell shell;

	public ShellMenu(MenuType<?> pMenuType, int pContainerId, Inventory inventory, @Nullable FriendlyByteBuf buf) {
		super(pMenuType, pContainerId); 
        if (buf == null) { return; }

        ShellType type = buf.readEnum(ShellType.class);
        init(inventory, type.getShell.apply(inventory.player.level, buf));
	}

    public ShellMenu(MenuType<?> menuType, int id, Inventory inventory, Shell shell) {
        super(menuType, id);
        init(inventory, shell);
    }

    private void init(Inventory playerInventory, Shell shell) {
        this.playerInventory = playerInventory;
        this.shell = shell;
    }

	@Override
	public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
		return null;
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return true; // TODO?
	}


}
