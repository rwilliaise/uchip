package com.alotofletters.uchip.content.board.shell;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;

public interface Shell {
    ItemStackHandler getContainer();
	ShellType getType();

    default void openScreen(ServerPlayer player, MenuProvider prov) {
		NetworkHooks.openScreen(player, prov, buf -> {
			buf.writeEnum(getType());
		});
	}
}
