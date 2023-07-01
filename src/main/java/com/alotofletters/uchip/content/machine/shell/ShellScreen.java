package com.alotofletters.uchip.content.machine.shell;

import com.alotofletters.uchip.Microchip;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ShellScreen extends AbstractContainerScreen<ShellMenu> {
    private static final ResourceLocation TEXTURE = Microchip.location("textures/gui/container/shell.png");

    public ShellScreen(ShellMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {

    }
}
