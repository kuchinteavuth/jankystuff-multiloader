package com.inteavuthkuch.jankystuff.data;

import com.inteavuthkuch.jankystuff.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.Set;

public class ModBlockLootTable extends BlockLootSubProvider {
    public ModBlockLootTable(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.SIMPLE_LAMP.block().get());
        dropSelf(ModBlocks.WOODEN_CRATE.block().get());
        add(ModBlocks.METAL_CRATE.block().get(), b -> copyComponents(b, List.of(DataComponents.CONTAINER, DataComponents.CUSTOM_NAME)));
    }

    private LootTable.Builder copyComponents(Block block, List<DataComponentType<?>> components) {

        CopyComponentsFunction.Builder builder = CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY);
        components.forEach(builder::include);

        return LootTable.lootTable()
                .withPool(this.applyExplosionCondition(block,
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(block)
                                        .apply(builder))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
       return () -> ModBlocks.BLOCKS.getEntries().stream()
               .<Block>map(holder -> holder.block().get())
               .iterator();
    }
}
