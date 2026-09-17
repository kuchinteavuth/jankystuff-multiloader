package com.inteavuthkuch.jankystuff;

import com.inteavuthkuch.jankystuff.init.ModMenuTypes;
import com.inteavuthkuch.jankystuff.menu.MetalCrateScreen;
import com.inteavuthkuch.jankystuff.menu.WoodenCrateScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class FabricJankyStuffClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModMenuTypes.WOODEN_CRATE_MENU.get(), WoodenCrateScreen::new);
        MenuScreens.register(ModMenuTypes.METAL_CRATE_MENU.get(), MetalCrateScreen::new);
    }
}
