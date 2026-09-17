package com.inteavuthkuch.jankystuff.init;

import com.inteavuthkuch.jankystuff.block.entity.MetalCrateBlockEntity;
import com.inteavuthkuch.jankystuff.block.entity.WoodenCrateBlockEntity;
import com.inteavuthkuch.jankystuff.platform.Services;
import com.inteavuthkuch.jankystuff.platform.util.RegistryHolder;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.List;

public class ModBlockEntities {
    private ModBlockEntities() {}
    public static void initialize(){}

    public static final RegistryHolder<BlockEntityType<WoodenCrateBlockEntity>> WOODEN_CRATE_BE =
            Services.REGISTRY.registerBlockEntityType("crate_wooden_be",
                    () -> WoodenCrateBlockEntity::new,
                    () -> List.of(ModBlocks.WOODEN_CRATE.block().get()));

    public static final RegistryHolder<BlockEntityType<MetalCrateBlockEntity>> METAL_CRATE_BE =
            Services.REGISTRY.registerBlockEntityType("crate_metal_be",
                    () -> MetalCrateBlockEntity::new,
                    () -> List.of(ModBlocks.METAL_CRATE.block().get()));
}
