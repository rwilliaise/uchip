package com.alotofletters.uchip.content.board.memory.ram;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.ComponentItem;

import net.minecraft.world.item.ItemStack;

public class RamItem extends ComponentItem<RamComponent> {
	private final int dataSize;
	private final int size;

	// TODO: sram + dram, difference between them?
	public RamItem(Properties pProperties, int dataSize, int size) {
		super(pProperties);
		this.dataSize = dataSize;
		this.size = size;
	}

	@Override
	public RamComponent createComponent(Board board, ItemStack stack) {
		return new RamComponent(board, stack, dataSize, size);
	} 
}
