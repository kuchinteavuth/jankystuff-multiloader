package com.inteavuthkuch.jankystuff.block.entity;

import com.inteavuthkuch.jankystuff.common.CrateMaterials;
import com.inteavuthkuch.jankystuff.init.ModBlockEntities;
import com.inteavuthkuch.jankystuff.menu.MetalCrateMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class MetalCrateBlockEntity extends BaseCrateBlockEntity implements MenuProvider {

    public MetalCrateBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.METAL_CRATE_BE.get(), CrateMaterials.METAL, worldPosition, blockState);
    }

    @Override
    public Component getDisplayName() {
        return getCustomName() != null
                ? getCustomName()
                : Component.translatable("block.jankystuff.crate_metal");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new MetalCrateMenu(containerId, inventory, this);
    }
}
