package com.inteavuthkuch.jankystuff.platform;

import com.inteavuthkuch.jankystuff.platform.services.IRegistryHelper;
import com.inteavuthkuch.jankystuff.platform.util.IRegistryHolder;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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

    @Override
    public IRegistryHolder<CreativeModeTab> registerCreativeModeTab(String name, Component title, Supplier<ItemStack> icon, Consumer<Consumer<ItemLike>> entries) {
        ResourceKey<CreativeModeTab> key = IRegistryHelper.createTabKey(name);
        CreativeModeTab.Builder builder = FabricCreativeModeTab.builder()
                .title(title)
                .icon(icon)
                .displayItems((_, output) -> entries.accept(output::accept));

        CreativeModeTab tab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key.identifier(), builder.build());

        return new IRegistryHolder<>() {
            @Override
            public CreativeModeTab get() {
                return tab;
            }

            @Override
            public Identifier id() {
                return key.identifier();
            }
        };
    }


}
