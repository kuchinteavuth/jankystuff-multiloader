package com.inteavuthkuch.jankystuff.menu;

import com.inteavuthkuch.jankystuff.common.CrateMaterial;
import com.inteavuthkuch.jankystuff.common.CrateMaterials;
import com.inteavuthkuch.jankystuff.common.Texture;
import com.inteavuthkuch.jankystuff.init.ModMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class WoodenCrateMenu extends AbstractContainerMenu {

    private final Container container;
    public static final CrateMaterial material = CrateMaterials.WOODEN;

    public WoodenCrateMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(material.rows() * material.cols()));
    }

    public WoodenCrateMenu(int containerId, Inventory inventory, Container container) {
        super(ModMenuTypes.WOODEN_CRATE_MENU.get(), containerId);

        this.container = container;
        Texture texture = material.texture();

        checkContainerSize(this.container, material.rows() * material.cols());
        this.container.startOpen(inventory.player);

        createSlotContainer(this.container, texture.inventory());
        createPlayerInventory(inventory, texture.playerInventory());
        createPlayerHotbar(inventory, texture.hotbar());
    }

    public CrateMaterial getMaterial() {
        return material;
    }


    private void createSlotContainer(Container container, Point position) {
        for(int i = 0; i < material.rows(); i++) {
            for(int j = 0; j < material.cols(); j++){
                this.addSlot(new Slot(container, j + i * 9, 8 + j * 18, 18 + i * 18));
            }
        }
    }

    private void createPlayerInventory(Container inventory, Point startPosition) {
        for(int i=0; i<3; ++i){
            for(int j=0; j<9; ++j){
                this.addSlot(new Slot(inventory, j + i * 9 + 9, startPosition.x + j * 18, startPosition.y + i * 18));
            }
        }
    }

    protected void createPlayerHotbar(Container inventory, Point startPosition) {
        for(int i=0; i<9; ++i){
            this.addSlot(new Slot(inventory, i, startPosition.x + i * 18, startPosition.y));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int pIndex) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if(slot.hasItem()){
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            if(pIndex < this.container.getContainerSize()){
                if(!this.moveItemStackTo(originalStack, this.container.getContainerSize(), this.slots.size(), true)){
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.moveItemStackTo(originalStack, 0, this.container.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if(originalStack.isEmpty()){
                slot.set(ItemStack.EMPTY);
            }
            else{
                slot.setChanged();
            }
        }
        return newStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }
}
