package com.alotofletters.uchip.content.board.via;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.BoardComponent;

import net.minecraft.world.item.ItemStack;

public class MOS6522VIA extends BoardComponent {

	public MOS6522VIA(Board owner, ItemStack stack) {
		super(owner, stack);
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
