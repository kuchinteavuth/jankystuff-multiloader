package com.inteavuthkuch.jankystuff.platform.services;

import com.inteavuthkuch.jankystuff.Constants;
import com.inteavuthkuch.jankystuff.platform.util.RegistryBlockItemHolder;
import com.inteavuthkuch.jankystuff.platform.util.RegistryHolder;
import com.inteavuthkuch.jankystuff.util.function.TriFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
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

public interface IRegistryHelper {
    <T extends Item> RegistryHolder<T> registerItem(String name, Function<Item.Properties, T> func);

    RegistryHolder<CreativeModeTab> registerCreativeModeTab(String name, Component title, Supplier<ItemStack> icon, Consumer<Consumer<ItemLike>> entries);

    <T extends Block> RegistryHolder<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> func);

    <T extends BlockItem> RegistryHolder<T> registerBlockItem(String name, RegistryHolder<? extends Block> block, BiFunction<Block, Item.Properties, T> func);

    default <T extends Block> RegistryBlockItemHolder<T> registerBlockWithItem(
            String name, Function<BlockBehaviour.Properties, T> block,
            BiFunction<Block, Item.Properties, BlockItem> item
    ) {
        RegistryHolder<T> blockHolder = registerBlock(name, block);
        RegistryHolder<BlockItem> itemHolder = registerBlockItem(name, blockHolder, item);
        return new RegistryBlockItemHolder<>(blockHolder, itemHolder);
    }

    default <T extends Block> RegistryBlockItemHolder<T> registerBlockWithItem(String name, Function<BlockBehaviour.Properties, T> block) {
        return registerBlockWithItem(name, block, BlockItem::new);
    }

    <T extends BlockEntity> RegistryHolder<BlockEntityType<T>> registerBlockEntityType(String name, Supplier<BiFunction<BlockPos, BlockState, T>> factory, Supplier<List<Block>> validBlocks);

//    default <T extends BlockEntity> RegistryHolder<BlockEntityType<T>> registerBlockEntityTypeForHolder(String name, Supplier<BiFunction<BlockPos, BlockState, T>> factory, List<RegistryBlockItemHolder<Block>> validBlocks){
//        List<Block> blocks = validBlocks.stream().map(b -> b.block().get()).toList();
//        return registerBlockEntityType(name, factory, blocks);
//    }

    <T extends AbstractContainerMenu> RegistryHolder<MenuType<T>> registerMenuType(String name, BiFunction<Integer, Inventory, T> factory);
    <T extends AbstractContainerMenu, D> RegistryHolder<MenuType<T>> registerMenuType(String name, TriFunction<Integer, Inventory, D, T> factory, StreamCodec<? super RegistryFriendlyByteBuf, D> streamCodec);

    static ResourceKey<Item> createItemKey(String name) {
        return ResourceKey.create(Registries.ITEM, Constants.modId(name));
    }
    static ResourceKey<Block> createBlockKey(String name) {
        return ResourceKey.create(Registries.BLOCK, Constants.modId(name));
    }
    static ResourceKey<CreativeModeTab> createTabKey(String name) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, Constants.modId(name));
    }
    static ResourceKey<BlockEntityType<?>> createBlockEntityTypeKey(String name) {
        return ResourceKey.create(Registries.BLOCK_ENTITY_TYPE, Constants.modId(name));
    }
    static ResourceKey<MenuType<?>> createMenuTypeKey(String name) {
        return ResourceKey.create(Registries.MENU, Constants.modId(name));
    }
}
