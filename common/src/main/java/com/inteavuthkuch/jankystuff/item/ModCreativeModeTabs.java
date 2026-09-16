package com.inteavuthkuch.jankystuff.item;

import com.inteavuthkuch.jankystuff.platform.Services;
import com.inteavuthkuch.jankystuff.platform.util.RegistryHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class ModCreativeModeTabs {
    public static void initialize() {}
    public static RegistryHolder<CreativeModeTab> MAIN_TAB = Services.REGISTRY.registerCreativeModeTab("main_tab",
            Component.translatable("itemGroup.jankystuff.main_tab"), () -> ModItems.NETHERITE_PAXEL.get().getDefaultInstance(),
            output -> {
                ModItems.ITEMS.forEach(output::accept);
            });
}
