package com.alotofletters.uchip.content.board.memory;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;

/**
 * A more complex {@link Widget} described README.md which is used across
 * multiple {@link net.minecraft.client.gui.screens.Screen}s to directly modify
 * ROM memory. Shows a grid of different memory cells similar to a hex editor.
 * 
 * This should be accessible in the Board and Shell screens.
 */
public class RomWidget extends GuiComponent implements Widget, GuiEventListener {
    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {

    }
}
