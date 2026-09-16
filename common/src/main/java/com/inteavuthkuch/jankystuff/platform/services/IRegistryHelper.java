package com.inteavuthkuch.jankystuff.platform.services;

import com.inteavuthkuch.jankystuff.Constants;
import com.inteavuthkuch.jankystuff.platform.util.IRegistryHolder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public interface IRegistryHelper {
    <T extends Item> IRegistryHolder<T> registerItem(String name, Function<Item.Properties, T> func);

    static ResourceKey<Item> createItemKey(String name) {
        return ResourceKey.create(Registries.ITEM, Constants.modId(name));
    }
}
