package com.inteavuthkuch.jankystuff.menu;

import com.inteavuthkuch.jankystuff.util.Size;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.awt.*;

public abstract class AbstractSortableContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    protected Button sortButton;
    protected Button sortOptionButon;
    protected Button sortDirectionButton;

    protected boolean isSortByAmount = false;
    protected boolean isAscending = true;

    public AbstractSortableContainerScreen(T menu, Inventory inventory, Component title, Point textureSize) {
        super(menu, inventory, title, textureSize.x, textureSize.y);
    }

    protected void updateTypeTooltip() {
        Component text = this.isSortByAmount
                ? Component.translatable("gui.jankystuff.button.sort.option.amount")
                : Component.translatable("gui.jankystuff.button.sort.option.name");
        this.sortOptionButon.setTooltip(Tooltip.create(text));
    }

    protected void updateDirectionTooltip() {
        Component text = this.isAscending
                ? Component.translatable("gui.jankystuff.button.sort.direction.asc")
                : Component.translatable("gui.jankystuff.button.sort.direction.desc");
        this.sortDirectionButton.setTooltip(Tooltip.create(text));
    }

    protected void sortOptionButtonClick() {
        this.isSortByAmount = !this.isSortByAmount;
        updateTypeTooltip();
    }

    protected void sortDirectionButtonClick() {
        this.isAscending = !this.isAscending;
        updateDirectionTooltip();
    }

    abstract protected void sortButtonClick();
    abstract protected Size<Integer> getButtonSize();
    abstract protected int getButtonSpacing();
    abstract protected Point getButtonPosition();
    protected boolean getSortButtonBorderHighlighted() { return false; }
    protected boolean getSortDirectionButtonBorderHighlighted() { return false; }
    protected boolean getSortOptionButtonBorderHighlighted() { return false; }

    /* AbstractContainerScreen */
    protected void renderScaledItem(GuiGraphicsExtractor graphics, ItemStack stack, int x, int y, float scale) {
        graphics.pose().pushMatrix();

        // Translate to target button position
        graphics.pose().translate(x, y);
        // Scale item model down
        graphics.pose().scale(scale, scale);

        // Draw item at local origin (0, 0)
        graphics.item(stack, 0, 0);

        graphics.pose().popMatrix();
    }

    @Override
    protected void init() {
        super.init();

//        int btnX = this.leftPos + 134;
//        int btnY = this.topPos + 4;
        int btnX = getButtonPosition().x;
        int btnY = getButtonPosition().y;
        int btnWidth = getButtonSize().width();
        int btnHeight = getButtonSize().height();
        int spacing = getButtonSpacing();

        this.sortButton = new Button.Builder(Component.empty(), _ -> sortButtonClick())
                .bounds(btnX + spacing * 2, btnY, btnWidth, btnHeight)
                .tooltip(Tooltip.create(Component.translatable("gui.jankystuff.button.sort")))
                .build();

        this.sortOptionButon = new Button.Builder(Component.empty(), _ -> sortOptionButtonClick())
                .bounds(btnX + spacing, btnY, btnWidth, btnHeight)
                .build();
        this.updateTypeTooltip();

        this.sortDirectionButton = new Button.Builder(Component.empty(), _ -> sortDirectionButtonClick())
                .bounds(btnX, btnY, btnWidth, btnHeight)
                .build();
        this.updateDirectionTooltip();

        this.sortButton.setOverrideRenderHighlightedSprite(this::getSortButtonBorderHighlighted);
        this.sortOptionButon.setOverrideRenderHighlightedSprite(this::getSortOptionButtonBorderHighlighted);
        this.sortDirectionButton.setOverrideRenderHighlightedSprite(this::getSortDirectionButtonBorderHighlighted);

        this.addRenderableWidget(this.sortButton);
        this.addRenderableWidget(this.sortOptionButon);
        this.addRenderableWidget(this.sortDirectionButton);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        super.extractLabels(graphics, xm, ym);

        // Desired icon size: ~9x9 to fit neatly inside 11x11 buttons
        float scale = 0.55f;

        // Compute relative, relative offsets
        int sortX = this.sortButton.getX() - this.leftPos;
        int sortY = this.sortButton.getY() - this.topPos;

        int optionX = this.sortOptionButon.getX() - this.leftPos;
        int optionY = this.sortOptionButon.getY() - this.topPos;

        int dirX = this.sortDirectionButton.getX() - this.leftPos;
        int dirY = this.sortDirectionButton.getY() - this.topPos;

        // 1. Sort Icon (Hopper)
        renderScaledItem(graphics, new ItemStack(Items.HOPPER), sortX + 1, sortY + 1, scale);

        // 2. Option Icon
        ItemStack optionIcon = this.isSortByAmount ? new ItemStack(Items.CHEST) : new ItemStack(Items.NAME_TAG);
        renderScaledItem(graphics, optionIcon, optionX + 1, optionY + 1, scale);

        // 3. Direction Icon
        ItemStack dirIcon = this.isAscending ? new ItemStack(Items.ARROW) : new ItemStack(Items.FEATHER);
        renderScaledItem(graphics, dirIcon, dirX + 1, dirY + 1, scale);
    }
}
