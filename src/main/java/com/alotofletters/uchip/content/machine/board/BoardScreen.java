package com.alotofletters.uchip.content.machine.board;

import com.alotofletters.uchip.Microchip;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BoardScreen extends AbstractContainerScreen<BoardMenu> {
    private static final ResourceLocation BOARD_LOCATION = Microchip.location("textures/gui/container/circuit_board.png");

    private EditBox name;

    public BoardScreen(BoardMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
        imageWidth = 177;
        imageHeight = 166;
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pX, int pY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BOARD_LOCATION);
        int i = (width - imageWidth) / 2;
        int j = (height - imageHeight) / 2;
        this.blit(pPoseStack, i, j, 0, 0, imageWidth, imageHeight);
    }

    @Override
    protected void init() {
        super.init();
        name = new EditBox(font, 8, 146, 133, 10, Component.empty());
        name.setTextColor(-1);
        name.setBordered(false);

        addWidget(name);
        setInitialFocus(name);
    }

    @Override
    public void removed() {
        super.removed();
    }
}
