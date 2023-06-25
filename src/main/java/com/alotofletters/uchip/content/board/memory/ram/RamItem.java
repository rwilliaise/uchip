package com.alotofletters.uchip.content.board.memory.ram;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.ComponentItem;
import net.minecraft.world.item.ItemStack;

public class RamItem extends ComponentItem<RamComponent> {
    private final RamType type;

    // TODO: sram + dram, difference between them?
    public RamItem(Properties pProperties, RamType type) {
        super(pProperties);
        this.type = type;
    }

    @Override
    public RamComponent createComponent(Board board, ItemStack stack) {
        return new RamComponent(board, stack, type);
    }
}
