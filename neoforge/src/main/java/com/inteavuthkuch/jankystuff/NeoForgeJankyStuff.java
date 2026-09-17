package com.inteavuthkuch.jankystuff;


import com.inteavuthkuch.jankystuff.platform.NeoForgeRegistryHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class NeoForgeJankyStuff {

    public NeoForgeJankyStuff(IEventBus eventBus) {
        CommonClass.init();
        NeoForgeRegistryHelper.register(eventBus);
        eventBus.addListener(JankyStuffDataGenerator::gatherClientData);
        eventBus.addListener(NeoForgeJankyStuffClient::registerMenuScreens);
    }
}