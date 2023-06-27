package com.alotofletters.uchip.content.board.shell;

import javax.annotation.Nullable;

import com.alotofletters.uchip.MicrochipMenuTypes;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkHooks;

public interface Shell extends MenuProvider {
    void setBoard(ItemStack stack);
	ShellType getShellType();

    default void openScreen(ServerPlayer player) {
		NetworkHooks.openScreen(player, this, buf -> {
			buf.writeEnum(getShellType());
		});
	}
    
    @Nullable
    default AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ShellMenu(MicrochipMenuTypes.SHELL.get(), pContainerId, pPlayerInventory, this);
    }
}
