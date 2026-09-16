package com.inteavuthkuch.jankystuff;

import com.inteavuthkuch.jankystuff.data.ModBlockTagsProvider;
import com.inteavuthkuch.jankystuff.data.ModEnglishLanguageProvider;
import com.inteavuthkuch.jankystuff.data.ModItemTagsProvider;
import com.inteavuthkuch.jankystuff.data.ModModelProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class JankyStuffDataGenerator {
    private JankyStuffDataGenerator() {}

    public static void gatherClientData(GatherDataEvent.Client event) {
        event.createProvider(ModEnglishLanguageProvider::new);
        event.createProvider(ModModelProvider::new);
        event.createProvider(ModItemTagsProvider::new);
        event.createProvider(ModBlockTagsProvider::new);
    }
}
