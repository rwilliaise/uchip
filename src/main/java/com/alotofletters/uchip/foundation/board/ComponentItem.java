package com.alotofletters.uchip.foundation.board;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class ComponentItem<T extends Component<?>> extends Item {
    public ComponentItem(Properties p_41383_) {
        super(p_41383_);
    }

    public abstract T createObject(ItemStack stack);
}
