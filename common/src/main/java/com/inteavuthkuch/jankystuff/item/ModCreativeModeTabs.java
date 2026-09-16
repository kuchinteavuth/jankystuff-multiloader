package com.inteavuthkuch.jankystuff.item;

import com.inteavuthkuch.jankystuff.block.ModBlocks;
import com.inteavuthkuch.jankystuff.platform.Services;
import com.inteavuthkuch.jankystuff.platform.util.IRegistryHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class ModCreativeModeTabs {
    public static void initialize() {}
    public static IRegistryHolder<CreativeModeTab> MAIN_TAB = Services.REGISTRY.registerCreativeModeTab("main_tab",
            Component.translatable("itemGroup.jankystuff.main_tab"), () -> ModItems.NETHERITE_PAXEL.get().getDefaultInstance(),
            output -> {
                output.accept(ModItems.IRON_PAXEL.get());
                output.accept(ModItems.DIAMOND_PAXEL.get());
                output.accept(ModItems.NETHERITE_PAXEL.get());
                output.accept(ModBlocks.SIMPLE_LAMP.blockItem().get());
            });
}
