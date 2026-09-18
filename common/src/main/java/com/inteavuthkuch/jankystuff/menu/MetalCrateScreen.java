package com.inteavuthkuch.jankystuff.menu;

import com.inteavuthkuch.jankystuff.common.Texture;
import com.inteavuthkuch.jankystuff.network.packet.ServerBoundBlockEntitySortPacket;
import com.inteavuthkuch.jankystuff.platform.Services;
import com.inteavuthkuch.jankystuff.util.Size;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;

public class MetalCrateScreen extends AbstractSortableContainerScreen<MetalCrateMenu> {
    private final Texture texture;

    public MetalCrateScreen(MetalCrateMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, menu.getMaterial().texture().size());
        this.texture = menu.getMaterial().texture();
    }

    @Override
    protected void sortButtonClick() {
        Services.NETWORK.sendToServer(new ServerBoundBlockEntitySortPacket(getMenu().getBlockPos(), this.isSortByAmount, this.isAscending));
    }

    @Override
    protected Size<Integer> getButtonSize() {
        return Size.sizeInt(11, 11);
    }

    @Override
    protected int getButtonSpacing() {
        return 12;
    }

    @Override
    protected Point getButtonPosition() {
        return new Point(this.leftPos + 134, this.topPos + 4);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(RenderPipelines.GUI_TEXTURED, texture.location(), x, y, 0,0, imageWidth, imageHeight, 256, 256);
    }
}
