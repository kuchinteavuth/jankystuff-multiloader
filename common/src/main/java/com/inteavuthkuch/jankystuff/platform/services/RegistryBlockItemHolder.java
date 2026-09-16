package com.inteavuthkuch.jankystuff.platform.services;

import com.inteavuthkuch.jankystuff.platform.util.IRegistryHolder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public record RegistryBlockItemHolder<T extends Block>(
        IRegistryHolder<T> block,
        IRegistryHolder<? extends BlockItem> blockItem
) {}
