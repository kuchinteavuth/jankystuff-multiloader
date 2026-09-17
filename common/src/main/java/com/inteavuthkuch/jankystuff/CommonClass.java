package com.inteavuthkuch.jankystuff;

import com.inteavuthkuch.jankystuff.init.ModBlocks;
import com.inteavuthkuch.jankystuff.init.ModCreativeModeTabs;
import com.inteavuthkuch.jankystuff.init.ModItems;

public class CommonClass {

    public static void init() {
        ModItems.initialize();
        ModBlocks.initialize();
        ModCreativeModeTabs.initialize();
    }
}