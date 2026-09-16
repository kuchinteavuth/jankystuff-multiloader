package com.inteavuthkuch.jankystuff.platform;

import com.inteavuthkuch.jankystuff.Constants;
import com.inteavuthkuch.jankystuff.platform.services.IRegistryHelper;
import com.inteavuthkuch.jankystuff.platform.util.IRegistryHolder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class NeoForgeRegistryHelper implements IRegistryHelper {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MOD_ID);
    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    @Override
    public <T extends Item> IRegistryHolder<T> registerItem(String name, Function<Item.Properties, T> func) {
        ResourceKey<Item> key = IRegistryHelper.createItemKey(name);
        DeferredItem<T> item = ITEMS.registerItem(name, func);

        return new IRegistryHolder<>() {
            @Override
            public T get() {
                return item.get();
            }

            @Override
            public Identifier id() {
                return key.identifier();
            }
        };
    }
}
