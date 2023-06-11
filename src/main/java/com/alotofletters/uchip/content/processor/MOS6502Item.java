package com.alotofletters.uchip.content.processor;

import com.alotofletters.uchip.MicrochipLang;
import com.alotofletters.uchip.content.processor.emulator.MOS6502Emulator;
import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.ComponentItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MOS6502Item extends ComponentItem<MOS6502Emulator> {
    public MOS6502Item(Properties props) {
        super(props);
    }

    @Override
    public MOS6502Emulator createObject(Board board, ItemStack stack) {
        return null;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, list, flag);
        list.add(MicrochipLang.tab().append(Component.translatable(MicrochipLang.PROCESSOR_ARCH_6502)).withStyle(ChatFormatting.BLUE));
        list.add(MicrochipLang.tab().append(Component.translatable(MicrochipLang.PROCESSOR_BOARD_DATA_WIDTH, 8).withStyle(ChatFormatting.BLUE)));
        list.add(MicrochipLang.tab().append(Component.translatable(MicrochipLang.PROCESSOR_BOARD_ADDRESS_WIDTH, 16).withStyle(ChatFormatting.BLUE)));
    }
}
