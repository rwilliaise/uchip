package com.alotofletters.uchip.foundation.board;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class ComponentItem<T extends Component> extends Item {
    public ComponentItem(Properties props) {
        super(props);
    }

    public abstract T createObject(Board board, ItemStack stack);
}
