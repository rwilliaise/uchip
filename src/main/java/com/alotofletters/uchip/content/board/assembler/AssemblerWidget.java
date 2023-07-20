package com.alotofletters.uchip.content.board.assembler;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;

/**
 * A more complex {@link Widget} described in README.md which is used across
 * multiple {@link net.minecraft.client.gui.screens.Screen}s to write and
 * compile asm for different ISAs. Should have a dropdown menu to select
 * different architectures to compile to.
 *
 * This should be accessible in the Board, Shell, and ROM screens.
 */
public class AssemblerWidget extends GuiComponent implements Widget, GuiEventListener {

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        
    }
}
