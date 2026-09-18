package com.inteavuthkuch.jankystuff.platform;

import com.inteavuthkuch.jankystuff.platform.services.IRegistryHelper;
import com.inteavuthkuch.jankystuff.platform.util.RegistryHolder;
import com.inteavuthkuch.jankystuff.util.function.TriFunction;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public <T extends Item> RegistryHolder<T> registerItem(String name, Function<Item.Properties, T> func) {
        ResourceKey<Item> key = IRegistryHelper.createItemKey(name);
        T item = Registry.register(BuiltInRegistries.ITEM, key.identifier(), func.apply(new Item.Properties().setId(key)));

        return new RegistryHolder<>() {
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
    public RegistryHolder<CreativeModeTab> registerCreativeModeTab(String name, Component title, Supplier<ItemStack> icon, Consumer<Consumer<ItemLike>> entries) {
        ResourceKey<CreativeModeTab> key = IRegistryHelper.createTabKey(name);
        CreativeModeTab.Builder builder = FabricCreativeModeTab.builder()
                .title(title)
                .icon(icon)
                .displayItems((_, output) -> entries.accept(output::accept));

        CreativeModeTab tab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key.identifier(), builder.build());

        return new RegistryHolder<>() {
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

    @Override
    public <T extends Block> RegistryHolder<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> func) {
        ResourceKey<Block> key = IRegistryHelper.createBlockKey(name);
        T block = Registry.register(BuiltInRegistries.BLOCK, key.identifier(), func.apply(BlockBehaviour.Properties.of().setId(key)));

        return new RegistryHolder<>() {
            @Override
            public T get() {
                return block;
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

    @Override
    public <T extends BlockEntity> RegistryHolder<BlockEntityType<T>> registerBlockEntityType(String name, Supplier<BiFunction<BlockPos, BlockState, T>> factory, Supplier<List<Block>> validBlocks) {
        ResourceKey<BlockEntityType<?>> key = IRegistryHelper.createBlockEntityTypeKey(name);
        BlockEntityType<T> blockEntityType = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, key.identifier(),
                FabricBlockEntityTypeBuilder.create(factory.get()::apply, validBlocks.get().toArray(Block[]::new)).build());

        return new RegistryHolder<>() {
            @Override
            public BlockEntityType<T> get() {
                return blockEntityType;
            }

            @Override
            public Identifier id() {
                return key.identifier();
            }
        };
    }

    @Override
    public <T extends AbstractContainerMenu> RegistryHolder<MenuType<T>> registerMenuType(String name, BiFunction<Integer, Inventory, T> factory) {
        ResourceKey<MenuType<?>> key = IRegistryHelper.createMenuTypeKey(name);
        MenuType<T> menuType = Registry.register(BuiltInRegistries.MENU, key.identifier(), new MenuType<>(factory::apply, FeatureFlags.REGISTRY.allFlags()));

        return new RegistryHolder<>() {
            @Override
            public MenuType<T> get() {
                return menuType;
            }

            @Override
            public Identifier id() {
                return key.identifier();
            }
        };
    }

    @Override
    public <T extends AbstractContainerMenu, D> RegistryHolder<MenuType<T>> registerMenuType(String name, TriFunction<Integer, Inventory, D, T> factory, StreamCodec<? super RegistryFriendlyByteBuf, D> streamCodec) {
        ResourceKey<MenuType<?>> key = IRegistryHelper.createMenuTypeKey(name);
        MenuType<T> menuType = Registry.register(BuiltInRegistries.MENU,
                key.identifier(),
                new ExtendedMenuType<>(factory::apply, streamCodec));

        return new RegistryHolder<>() {
            @Override
            public MenuType<T> get() {
                return menuType;
            }

            @Override
            public Identifier id() {
                return key.identifier();
            }
        };
    }


}
