package com.inteavuthkuch.jankystuff.platform.services;

import com.inteavuthkuch.jankystuff.Constants;
import com.inteavuthkuch.jankystuff.platform.util.IRegistryHolder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface IRegistryHelper {
    <T extends Item> IRegistryHolder<T> registerItem(String name, Function<Item.Properties, T> func);
    IRegistryHolder<CreativeModeTab> registerCreativeModeTab(String name, Component title, Supplier<ItemStack> icon, Consumer<Consumer<ItemLike>> entries);

    static ResourceKey<Item> createItemKey(String name) {
        return ResourceKey.create(Registries.ITEM, Constants.modId(name));
    }
    static ResourceKey<CreativeModeTab> createTabKey(String name) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, Constants.modId(name));
    }
}
