package com.inteavuthkuch.jankystuff.platform.util;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public record RegistryBlockItemHolder<T extends Block>(
        RegistryHolder<T> block,
        RegistryHolder<? extends BlockItem> blockItem
) {}
