package com.alotofletters.uchip.core.board;

import com.alotofletters.uchip.MicrochipLang;
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
    private final T empty = createComponent(null, ItemStack.EMPTY);

    public ComponentItem(Properties props) {
        super(props);
    }

    public abstract T createComponent(Board board, ItemStack stack);

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        list.add(CommonComponents.EMPTY);
        list.add(Component.translatable(MicrochipLang.BOARD_COMPONENT_EFFECT).withStyle(ChatFormatting.GRAY));
        list.add(MicrochipLang.tab().append(Component.translatable(MicrochipLang.BOARD_COMPONENT_DATA_WIDTH, empty.getDataWidth()).withStyle(ChatFormatting.BLUE)));
        list.add(MicrochipLang.tab().append(Component.translatable(MicrochipLang.BOARD_COMPONENT_ADDRESS_WIDTH, empty.getAddressWidth()).withStyle(ChatFormatting.BLUE)));
    }

    public T getEmpty() {
        return empty;
    }
}
