package com.inteavuthkuch.jankystuff.data;

import com.inteavuthkuch.jankystuff.Constants;
import com.inteavuthkuch.jankystuff.init.ModItems;
import com.inteavuthkuch.jankystuff.tag.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Constants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.Items.RODS_WOODEN).add(Items.STICK);

        tag(ModItemTags.TOOLS_PAXEL).add(ModItems.IRON_PAXEL.get(), ModItems.DIAMOND_PAXEL.get(), ModItems.NETHERITE_PAXEL.get());
        tag(Tags.Items.TOOLS).addTag(ModItemTags.TOOLS_PAXEL);
        tag(ItemTags.MINING_ENCHANTABLE).addTag(ModItemTags.TOOLS_PAXEL);
        tag(ItemTags.MINING_LOOT_ENCHANTABLE).addTag(ModItemTags.TOOLS_PAXEL);
        tag(ItemTags.DURABILITY_ENCHANTABLE).addTag(ModItemTags.TOOLS_PAXEL);
    }
}
