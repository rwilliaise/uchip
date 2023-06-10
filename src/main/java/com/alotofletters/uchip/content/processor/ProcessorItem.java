package com.alotofletters.uchip.content.processor;

import com.alotofletters.uchip.content.processor.emulator.ProcessorEmulator;
import com.alotofletters.uchip.foundation.board.ComponentItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class ProcessorItem<T extends ProcessorEmulator<?>> extends ComponentItem<T> {
    public ProcessorItem(Properties p_41383_) {
        super(p_41383_);
    }

    public abstract T createEmulator(ItemStack stack);

}
