package com.inteavuthkuch.jankystuff.platform;

import com.inteavuthkuch.jankystuff.Constants;
import com.inteavuthkuch.jankystuff.platform.services.IRegistryHelper;
import com.inteavuthkuch.jankystuff.platform.util.RegistryHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NeoForgeRegistryHelper implements IRegistryHelper {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MOD_ID);
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Constants.MOD_ID);
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        BLOCKS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
    }

    @Override
    public <T extends Item> RegistryHolder<T> registerItem(String name, Function<Item.Properties, T> func) {
        ResourceKey<Item> key = IRegistryHelper.createItemKey(name);
        DeferredItem<T> item = ITEMS.registerItem(name, func);

        return new RegistryHolder<>() {
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

    @Override
    public RegistryHolder<CreativeModeTab> registerCreativeModeTab(String name, Component title, Supplier<ItemStack> icon, Consumer<Consumer<ItemLike>> entries) {
        ResourceKey<CreativeModeTab> key = IRegistryHelper.createTabKey(name);
        CreativeModeTab.Builder builder = CreativeModeTab.builder()
                .title(title)
                .icon(icon)
                .displayItems((_, output) -> entries.accept(output::accept));

        DeferredHolder<CreativeModeTab, CreativeModeTab> tab = CREATIVE_MODE_TABS.register(name, builder::build);
        return new RegistryHolder<>() {
            @Override
            public CreativeModeTab get() {
                return tab.get();
            }

            @Override
            public Identifier id() {
                return key.identifier();
            }
        };
    }

    @Override
    public <T extends Block> RegistryHolder<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> func) {
        ResourceKey<Block> key = IRegistryHelper.createBlockKey(name);
        DeferredBlock<T> block = BLOCKS.registerBlock(name, func);

        return new RegistryHolder<T>() {
            @Override
            public T get() {
                return block.get();
            }

            @Override
            public Identifier id() {
                return key.identifier();
            }
        };
    }

    @Override
    public <T extends BlockItem> RegistryHolder<T> registerBlockItem(String name, RegistryHolder<? extends Block> block, BiFunction<Block, Item.Properties, T> func) {
        return registerItem(name, properties -> func.apply(block.get(), properties.useBlockDescriptionPrefix()));
    }


}
