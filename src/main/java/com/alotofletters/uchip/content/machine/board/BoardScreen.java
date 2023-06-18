package com.alotofletters.uchip.content.machine.board;

import com.alotofletters.uchip.Microchip;
import com.alotofletters.uchip.MicrochipLang;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

public class BoardScreen extends AbstractContainerScreen<BoardMenu> {
    private static final ResourceLocation BOARD_LOCATION = Microchip.location("textures/gui/container/circuit_board.png");

    private EditBox name;

    public BoardScreen(BoardMenu p_97741_, Inventory p_97742_, Component title) {
        super(p_97741_, p_97742_, title);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.name.tick();
    }

    @Override
    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        String s = this.name.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        this.name.setValue(s);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        pPartialTick = minecraft.getFrameTime();
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        RenderSystem.disableBlend();
        name.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
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
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        RenderSystem.disableBlend();
        super.renderLabels(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        imageWidth = 177;
        imageHeight = 166;
        titleLabelY = 4;
        FormattedCharSequence sequence = title.getVisualOrderText();
        titleLabelX = imageWidth / 2 - font.width(sequence) / 2;

        name = new EditBox(font, leftPos + 9, topPos + 147, 131, 10, Component.translatable(MicrochipLang.BOARD_NAME));
        name.setCanLoseFocus(false);
        name.setTextColor(-1);
        name.setTextColorUneditable(-1);
        name.setBordered(false);
        name.setMaxLength(50);
        name.setValue(menu.stack.getHoverName().getString());

        addWidget(name);
        name.setEditable(true);
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == InputConstants.KEY_ESCAPE) {
            this.minecraft.player.closeContainer();
        }

        return this.name.keyPressed(pKeyCode, pScanCode, pModifiers) || this.name.canConsumeInput() || super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public void removed() {
        super.removed();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }
}
