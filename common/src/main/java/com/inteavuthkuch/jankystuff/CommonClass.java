package com.inteavuthkuch.jankystuff;

import com.inteavuthkuch.jankystuff.init.*;

public class CommonClass {

    public static void init() {
        ModItems.initialize();
        ModBlocks.initialize();
        ModCreativeModeTabs.initialize();
        ModBlockEntities.initialize();
        ModMenuTypes.initialize();
    }
}