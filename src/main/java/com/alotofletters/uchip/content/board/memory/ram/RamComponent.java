package com.alotofletters.uchip.content.board.memory.ram;

import com.alotofletters.uchip.core.board.Board;
import com.alotofletters.uchip.core.board.BoardComponent;
import com.google.common.collect.Maps;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class RamComponent extends BoardComponent {
    private final RamType type;
    private final HashMap<Integer, Byte> memory;

    public RamComponent(Board owner, ItemStack stack, RamType type) {
        super(owner, stack);
        this.memory = Maps.newHashMap();
        this.type = type;
    }

    @Override
    public int read(int address) {
        return memory.get(mask(address));
    }

    @Override
    public void write(int address, int value) {
        memory.put(mask(address), (byte) value);
    }

    @Override
    public int getDataWidth() {
        return type.dataWidth;
    }

    @Override
    public int getAddressWidth() {
        return type.addressWidth;
    }
}

