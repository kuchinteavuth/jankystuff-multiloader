package com.inteavuthkuch.jankystuff.tag;

import com.inteavuthkuch.jankystuff.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    private ModItemTags() {}
    private static TagKey<Item> tag(String name) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
    }
    private static TagKey<Item> c(String name) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", name));
    }

    public static final TagKey<Item> TOOLS_PAXEL = c("tools/paxel");
}
