package com.inteavuthkuch.jankystuff.init;

import com.inteavuthkuch.jankystuff.menu.MetalCrateMenu;
import com.inteavuthkuch.jankystuff.menu.WoodenCrateMenu;
import com.inteavuthkuch.jankystuff.platform.Services;
import com.inteavuthkuch.jankystuff.platform.util.RegistryHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {
    public static void initialize() {}

    public static RegistryHolder<MenuType<WoodenCrateMenu>> WOODEN_CRATE_MENU = Services.REGISTRY.registerMenuType("crate_wooden_menu", WoodenCrateMenu::new, BlockPos.STREAM_CODEC);
    public static RegistryHolder<MenuType<MetalCrateMenu>> METAL_CRATE_MENU = Services.REGISTRY.registerMenuType("crate_metal_menu", MetalCrateMenu::new, BlockPos.STREAM_CODEC);
}
