package com.alotofletters.uchip.content.processor;

import com.alotofletters.uchip.content.processor.emulator.ProcessorEmulator;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class ProcessorItem<E extends ProcessorEmulator> extends Item {
    public ProcessorItem(Properties p_41383_) {
        super(p_41383_);
    }

    public abstract E createEmulator(ItemStack stack);

}
