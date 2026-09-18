package com.inteavuthkuch.jankystuff.menu;

import com.inteavuthkuch.jankystuff.Constants;
import com.inteavuthkuch.jankystuff.common.ISortableBlockEntity;
import com.inteavuthkuch.jankystuff.common.Texture;
import com.inteavuthkuch.jankystuff.network.packet.ServerBoundBlockEntitySortPacket;
import com.inteavuthkuch.jankystuff.platform.Services;
import com.inteavuthkuch.jankystuff.util.Size;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;

public class WoodenCrateScreen extends AbstractSortableContainerScreen<WoodenCrateMenu> {
    private final Texture texture;
    private final Identifier sortIconTexture = Constants.modId("textures/gui/icon/sort.png");
    private final Identifier sortAscIconTexture = Constants.modId("textures/gui/icon/sort-ascending.png");
    private final Identifier sortDescIconTexture = Constants.modId("textures/gui/icon/sort-descending.png");

    public WoodenCrateScreen(WoodenCrateMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, menu.getMaterial().texture().size());
        this.texture = menu.getMaterial().texture();

        if (menu.getBlockEntity() instanceof ISortableBlockEntity sortable) {
            this.isSortByAmount = sortable.getIsSortByAmount();
            this.isAscending = sortable.getIsSortAscending();
        }
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

    /* AbstractContainerScreen */
    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(RenderPipelines.GUI_TEXTURED, texture.location(), x, y, 0,0, imageWidth, imageHeight, 256, 256);
    }
}
