package com.inteavuthkuch.jankystuff.menu;

import com.inteavuthkuch.jankystuff.common.Texture;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class WoodenCrateScreen extends AbstractContainerScreen<WoodenCrateMenu> {
    private final Texture texture;

    public WoodenCrateScreen(WoodenCrateMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, menu.getMaterial().texture().size().x, menu.getMaterial().texture().size().y);
        this.texture = menu.getMaterial().texture();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(RenderPipelines.GUI_TEXTURED, texture.location(), x, y, 0,0, imageWidth, imageHeight, 256, 256);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        super.extractLabels(graphics, xm, ym);
    }
}
