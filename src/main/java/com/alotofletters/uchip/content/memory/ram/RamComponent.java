package com.alotofletters.uchip.content.memory.ram;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.BoardComponent;

import net.minecraft.world.item.ItemStack;

public class RamComponent extends BoardComponent {
	private final int dataSize;
	private final int size;

	public RamComponent(Board owner, ItemStack stack, int dataSize, int size) {
		super(owner, stack);
		this.dataSize = dataSize;
		this.size = size;
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

