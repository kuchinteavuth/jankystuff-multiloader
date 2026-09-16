package com.inteavuthkuch.jankystuff.data;

import com.inteavuthkuch.jankystuff.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModBlockLootTable extends BlockLootSubProvider {
    public ModBlockLootTable(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.SIMPLE_LAMP.block().get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>();

        blocks.add(ModBlocks.SIMPLE_LAMP.block().get());

        return () -> blocks.stream().iterator();
    }
}
