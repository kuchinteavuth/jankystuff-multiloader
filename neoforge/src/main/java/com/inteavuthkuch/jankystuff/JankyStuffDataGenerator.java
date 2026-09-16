package com.inteavuthkuch.jankystuff;

import com.inteavuthkuch.jankystuff.data.*;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;

public final class JankyStuffDataGenerator {
    private JankyStuffDataGenerator() {}

    public static void gatherClientData(GatherDataEvent.Client event) {
        event.createProvider(ModEnglishLanguageProvider::new);
        event.createProvider(ModModelProvider::new);
        event.createProvider(ModItemTagsProvider::new);
        event.createProvider(ModBlockTagsProvider::new);
        event.createProvider(ModRecipeProvider.Runner::new);
        event.createProvider(((output, lookupProvider) -> new LootTableProvider(output, Collections.emptySet(),
                List.of(
                        new LootTableProvider.SubProviderEntry(ModBlockLootTable::new, LootContextParamSets.BLOCK)
                ), lookupProvider)));
    }
}
