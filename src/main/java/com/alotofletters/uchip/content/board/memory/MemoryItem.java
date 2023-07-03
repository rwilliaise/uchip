package com.alotofletters.uchip.content.board.memory;

import com.alotofletters.uchip.core.board.Board;
import com.alotofletters.uchip.core.board.ComponentItem;
import net.minecraft.world.item.ItemStack;

public class MemoryItem extends ComponentItem<MemoryComponent> {
    protected final MemoryType type;

    public MemoryItem(Properties props, MemoryType type) {
        super(props);
        this.type = type;
    }

    @Override
    public MemoryComponent createComponent(Board board, ItemStack stack) {
        return new MemoryComponent(board, stack, type);
    }
}
