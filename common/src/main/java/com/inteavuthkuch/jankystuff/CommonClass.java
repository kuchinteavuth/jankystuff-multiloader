package com.inteavuthkuch.jankystuff;

import com.inteavuthkuch.jankystuff.item.ModCreativeModeTabs;
import com.inteavuthkuch.jankystuff.item.ModItems;

public class CommonClass {

    public static void init() {
        ModItems.initialize();
        ModCreativeModeTabs.initialize();
    }
}