package com.alotofletters.uchip.foundation.board;

import com.alotofletters.uchip.MicrochipLangPartials;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ComponentItem<T extends BoardComponent> extends Item {
    public ComponentItem(Properties props) {
        super(props);
    }

    public abstract T createObject(Board board, ItemStack stack);

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        list.add(CommonComponents.EMPTY);
        list.add(Component.translatable(MicrochipLangPartials.PROCESSOR_BOARD_EFFECT).withStyle(ChatFormatting.GRAY));
    }
}
