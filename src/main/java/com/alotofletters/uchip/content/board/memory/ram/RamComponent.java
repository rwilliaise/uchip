package com.alotofletters.uchip.content.memory.ram;

import java.util.HashMap;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.BoardComponent;
import com.google.common.collect.Maps;

import net.minecraft.world.item.ItemStack;

public class RamComponent extends BoardComponent {
	private final int dataSize;
	private final int size;
    private HashMap<Integer, Byte> memory;

	public RamComponent(Board owner, ItemStack stack, int dataSize, int size) {
		super(owner, stack);
		this.dataSize = dataSize;
		this.size = size;
        this.memory = Maps.newHashMap();
	}

    @Override
    public int read(int address) {
        return memory.get(address);       
    }

    @Override
    public void write(int address, int value) {
        memory.put(address, (byte) value);
    }

	@Override
	public int getDataWidth() {
		return dataSize;
	}

	@Override
	public int getAddressSpace() {
		return size;
	}
}

