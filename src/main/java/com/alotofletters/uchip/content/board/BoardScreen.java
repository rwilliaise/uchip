package com.alotofletters.uchip.content.board;

import com.alotofletters.uchip.Microchip;
import com.alotofletters.uchip.core.board.Board;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

import java.util.regex.Pattern;

public class BoardScreen extends AbstractContainerScreen<BoardMenu> {
    private static final Style CHIP_FONT = Style.EMPTY.withFont(Microchip.location("chip"));
    private static final ResourceLocation TEXTURE = Microchip.location("textures/gui/container/circuit_board.png");

    private final Board board;

    public BoardScreen(BoardMenu menu, Inventory p_97742_, Component title) {
        super(menu, p_97742_, title);
        board = menu.createBoard();
        imageWidth = 177;
        imageHeight = 235;
        inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        titleLabelY = 4;
        FormattedCharSequence sequence = title.getVisualOrderText();
        titleLabelX = imageWidth / 2 - font.width(sequence) / 2;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        pPartialTick = minecraft.getFrameTime();
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        RenderSystem.disableBlend();
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pX, int pY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(pPoseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        RenderSystem.disableBlend();
        super.renderLabels(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    public void removed() {
        super.removed();
        // TODO: send BoardEditPacket
    }

    private class PageEditBox extends EditBox {
        public PageEditBox(Font pFont, int pX, int pY, int pWidth, int pHeight, Component pMessage) {
            super(pFont, pX, pY, pWidth, pHeight, pMessage);
            setMaxLength(4);
            setFilter(this::filterNumbers);
            setFormatter((str, i) -> FormattedCharSequence.forward(str, BoardScreen.CHIP_FONT));
        }

        private boolean filterNumbers(String str) {
            return Pattern.matches("[0-9]{4}", str);
        }
    }
}
