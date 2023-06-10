package com.alotofletters.uchip.content.processor;

import com.alotofletters.uchip.content.processor.emulator.MOS6502Emulator;
import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.ComponentItem;
import net.minecraft.world.item.ItemStack;

public class MOS6502Item extends ComponentItem<MOS6502Emulator> {
    public MOS6502Item(Properties props) {
        super(props);
    }

    @Override
    public MOS6502Emulator createObject(Board board, ItemStack stack) {
        return null;
    }
}
