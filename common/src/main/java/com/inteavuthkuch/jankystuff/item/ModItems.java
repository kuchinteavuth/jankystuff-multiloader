package com.inteavuthkuch.jankystuff.item;

import com.inteavuthkuch.jankystuff.common.ModToolMaterials;
import com.inteavuthkuch.jankystuff.item.custom.PaxelItem;
import com.inteavuthkuch.jankystuff.platform.util.RegistryCollection;
import com.inteavuthkuch.jankystuff.platform.util.RegistryHolder;
import net.minecraft.world.item.Item;

public class ModItems {

    public static void initialize() {}

    public static final RegistryCollection.Items ITEMS = new RegistryCollection.Items();

    public static final RegistryHolder<Item> IRON_PAXEL = ITEMS.registerItem("paxel_iron", p ->
            new PaxelItem(ModToolMaterials.PAXEL_IRON, 4.0F, -3.0F, p)); // why this worked?
    public static final RegistryHolder<Item> DIAMOND_PAXEL = ITEMS.registerItem("paxel_diamond", p ->
            new PaxelItem(ModToolMaterials.PAXEL_DIAMOND, 4.0F, -2.0F, p));
    public static final RegistryHolder<Item> NETHERITE_PAXEL = ITEMS.registerItem("paxel_netherite", p ->
            new PaxelItem(ModToolMaterials.PAXEL_NETHERITE, 4.0F, -2.0F, p));

}
