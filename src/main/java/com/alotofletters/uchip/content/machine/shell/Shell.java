package com.alotofletters.uchip.content.machine.shell;

import com.alotofletters.uchip.MicrochipMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public interface Shell extends MenuProvider {
    void setBoard(ItemStack stack);

    void write(FriendlyByteBuf buf);

    ShellType getShellType();

    default void openScreen(ServerPlayer player) {
        NetworkHooks.openScreen(player, this, buf -> {
            buf.writeEnum(getShellType());
            this.write(buf);
        });
    }

    @Nullable
    default AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ShellMenu(MicrochipMenuTypes.SHELL.get(), pContainerId, pPlayerInventory, this);
    }
}
