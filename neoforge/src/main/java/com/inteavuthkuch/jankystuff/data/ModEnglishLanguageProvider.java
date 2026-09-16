package com.inteavuthkuch.jankystuff.data;

import com.inteavuthkuch.jankystuff.Constants;
import com.inteavuthkuch.jankystuff.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModEnglishLanguageProvider extends LanguageProvider {
    public ModEnglishLanguageProvider(PackOutput output) {
        super(output, Constants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.jankystuff.main_tab", "Janky Stuff");

        add(ModItems.IRON_PAXEL.get(), "Iron Paxel");
        add(ModItems.DIAMOND_PAXEL.get(), "Diamond Paxel");
        add(ModItems.NETHERITE_PAXEL.get(), "Netherite Paxel");
    }
}
