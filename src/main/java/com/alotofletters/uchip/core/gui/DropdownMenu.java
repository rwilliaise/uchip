package com.alotofletters.uchip.core.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class DropdownMenu extends AbstractWidget {

    public DropdownMenu(int pX, int pY, int pWidth, int pHeight, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);
        
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
    }
}
