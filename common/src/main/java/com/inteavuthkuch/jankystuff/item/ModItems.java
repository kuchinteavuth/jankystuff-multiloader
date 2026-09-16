package com.inteavuthkuch.jankystuff.item;

import com.inteavuthkuch.jankystuff.platform.Services;
import com.inteavuthkuch.jankystuff.platform.util.IRegistryHolder;
import net.minecraft.world.item.Item;

public class ModItems {

    public static void initialize() {}

    public static final IRegistryHolder<Item> IRON_PAXEL = Services.REGISTRY.registerItem("paxel_iron", Item::new);
    public static final IRegistryHolder<Item> DIAMOND_PAXEL = Services.REGISTRY.registerItem("paxel_diamond", Item::new);
    public static final IRegistryHolder<Item> NETHERITE_PAXEL = Services.REGISTRY.registerItem("paxel_netherite", Item::new);

}
