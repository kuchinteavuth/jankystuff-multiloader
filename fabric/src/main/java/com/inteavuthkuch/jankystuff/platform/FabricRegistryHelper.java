package com.inteavuthkuch.jankystuff.platform;

import com.inteavuthkuch.jankystuff.platform.services.IRegistryHelper;
import com.inteavuthkuch.jankystuff.platform.util.IRegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public <T extends Item> IRegistryHolder<T> registerItem(String name, Function<Item.Properties, T> func) {
        ResourceKey<Item> key = IRegistryHelper.createItemKey(name);
        T item = Registry.register(BuiltInRegistries.ITEM, key.identifier(), func.apply(new Item.Properties().setId(key)));

        return new IRegistryHolder<>() {
            @Override
            public T get() {
                return item;
            }

            @Override
            public Identifier id() {
                return key.identifier();
            }
        };
    }
}
