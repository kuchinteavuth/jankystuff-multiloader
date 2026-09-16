package com.inteavuthkuch.jankystuff.data;

import com.inteavuthkuch.jankystuff.Constants;
import com.inteavuthkuch.jankystuff.block.ModBlocks;
import com.inteavuthkuch.jankystuff.item.ModItems;
import com.inteavuthkuch.jankystuff.platform.util.RegistryBlockItemHolder;
import com.inteavuthkuch.jankystuff.platform.util.RegistryHolder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModEnglishLanguageProvider extends LanguageProvider {
    public ModEnglishLanguageProvider(PackOutput output) {
        super(output, Constants.MOD_ID, "en_us");
    }

    private void add(RegistryBlockItemHolder<? extends Block> holder, String text) {
        add(holder.block().get(), text);
        add(holder.blockItem().get(), text);
    }

    private void add(RegistryHolder<? extends Item> holder, String text) {
        add(holder.get(), text);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.jankystuff.main_tab", "Janky Stuff");

        add(ModItems.IRON_PAXEL, "Iron Paxel");
        add(ModItems.DIAMOND_PAXEL, "Diamond Paxel");
        add(ModItems.NETHERITE_PAXEL, "Netherite Paxel");

        add(ModBlocks.SIMPLE_LAMP, "Simple Lamp");
    }
}
