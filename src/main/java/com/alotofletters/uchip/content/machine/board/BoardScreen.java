package com.alotofletters.uchip.content.machine.board;

import com.alotofletters.uchip.Microchip;
import com.alotofletters.uchip.MicrochipLang;
import com.alotofletters.uchip.foundation.board.Board;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

public class BoardScreen extends AbstractContainerScreen<BoardMenu> {
    private static final Style CHIP_FONT = Style.EMPTY.withFont(Microchip.location("chip"));
    private static final ResourceLocation BOARD_LOCATION = Microchip.location("textures/gui/container/circuit_board.png");

    private final Board board;


    public BoardScreen(BoardMenu menu, Inventory p_97742_, Component title) {
        super(menu, p_97742_, title);
        imageWidth = 177;
        imageHeight = 235;
        titleLabelY = 4;
        FormattedCharSequence sequence = title.getVisualOrderText();
        titleLabelX = imageWidth / 2 - font.width(sequence) / 2;
        board = menu.createBoard();
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        pPartialTick = minecraft.getFrameTime();
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        font.draw(pPoseStack, Component.literal("Hello").withStyle(CHIP_FONT), 10, 10, 16777215);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pX, int pY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BOARD_LOCATION);
        this.blit(pPoseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void removed() {
        super.removed();
    }
}
