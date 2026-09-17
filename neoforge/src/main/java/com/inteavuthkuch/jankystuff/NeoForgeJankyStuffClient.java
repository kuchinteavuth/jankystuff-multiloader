package com.inteavuthkuch.jankystuff;

import com.inteavuthkuch.jankystuff.init.ModMenuTypes;
import com.inteavuthkuch.jankystuff.menu.MetalCrateScreen;
import com.inteavuthkuch.jankystuff.menu.WoodenCrateScreen;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class NeoForgeJankyStuffClient {

    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.WOODEN_CRATE_MENU.get(), WoodenCrateScreen::new);
        event.register(ModMenuTypes.METAL_CRATE_MENU.get(), MetalCrateScreen::new);
    }
}
