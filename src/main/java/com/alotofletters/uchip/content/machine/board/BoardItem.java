package com.alotofletters.uchip.content.machine.board;

import com.alotofletters.uchip.MicrochipScreens;
import com.alotofletters.uchip.foundation.board.Board;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BoardItem extends Item implements MenuProvider {
    public BoardItem(Properties p_41383_) {
        super(p_41383_);
    }

    public abstract Board createBoard(ItemStack stack);

    public abstract MutableComponent getTierComponent();

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!player.isShiftKeyDown() && hand == InteractionHand.MAIN_HAND) {
            if (!level.isClientSide && player instanceof ServerPlayer serverPlayer)
                NetworkHooks.openScreen(serverPlayer, this, buf -> {
                    buf.writeItem(player.getItemInHand(hand));
                });
        }
        return super.use(level, player, hand);
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.translatable(getDescriptionId()).append(" ").append(this.getTierComponent());
    }

    @Override
    public Component getName(ItemStack p_41458_) {
        return getDescription();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return getDescription();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return MicrochipScreens.BOARD.create(p_39954_, p_39955_);
    }
}
