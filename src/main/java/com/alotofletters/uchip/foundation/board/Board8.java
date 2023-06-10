package com.alotofletters.uchip.foundation.board;

import net.minecraft.world.item.ItemStack;

/**
 * Board representing the common 8-bit wide data bus and 16-bit address space of many 8-bit systems.
 */
public class Board8 extends Board {
    public Board8(ItemStack stack) {
        super(stack);
    }

    @Override
    public int getDataWidth() {
        return 8;
    }

    @Override
    public int getAddressWidth() {
        return 16;
    }
}
