package com.alotofletters.uchip.content.machine.board;

import com.alotofletters.uchip.MicrochipLang;
import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.Board8;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

public class Board8Item extends BoardItem {
    public Board8Item(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public Board createBoard(ItemStack stack) {
        return new Board8(stack);
    }

    @Override
    public MutableComponent getTierComponent() {
        return Component.translatable(MicrochipLang.BOARD_8BIT);
    }
}
