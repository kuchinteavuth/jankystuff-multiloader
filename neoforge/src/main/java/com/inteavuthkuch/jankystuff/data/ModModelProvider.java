package com.inteavuthkuch.jankystuff.data;

import com.inteavuthkuch.jankystuff.Constants;
import com.inteavuthkuch.jankystuff.block.ModBlocks;
import com.inteavuthkuch.jankystuff.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.IRON_PAXEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.DIAMOND_PAXEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ModItems.NETHERITE_PAXEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);

        // Blocks
        blockModels.createTrivialCube(ModBlocks.SIMPLE_LAMP.block().get());
    }
}
