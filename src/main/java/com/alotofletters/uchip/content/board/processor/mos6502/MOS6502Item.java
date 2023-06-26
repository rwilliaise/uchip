package com.alotofletters.uchip.content.board.processor.6502;

import com.alotofletters.uchip.MicrochipLang;
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

public class MOS6502Item extends ComponentItem<MOS6502Processor> {
    public MOS6502Item(Properties props) {
        super(props);
    }

    @Override
    public MOS6502Processor createComponent(Board board, ItemStack stack) {
        return new MOS6502Processor(board, stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, list, flag);
        list.add(MicrochipLang.tab().append(Component.translatable(MicrochipLang.PROCESSOR_ARCH_6502)).withStyle(ChatFormatting.BLUE));
    }
}
