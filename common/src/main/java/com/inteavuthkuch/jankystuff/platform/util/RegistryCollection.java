package com.inteavuthkuch.jankystuff.platform.util;

import com.inteavuthkuch.jankystuff.platform.Services;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class RegistryCollection {

    public static class Items  {
        private final List<RegistryHolder<? extends Item>> list = new ArrayList<>();
        public boolean isEmpty() {
            return list.isEmpty();
        }

        public List<RegistryHolder<? extends Item>> getEntries() {
            return this.list;
        }

        public void forEach(Consumer<Item> consumer) {
            this.getEntries().forEach(holder -> consumer.accept(holder.get()));
        }

        public <T extends Item> RegistryHolder<T> registerItem(String name, Function<Item.Properties, T> func) {
            RegistryHolder<T> holder = Services.REGISTRY.registerItem(name, func);
            this.list.add(holder);
            return holder;
        }

        public void registerHolder(RegistryHolder<? extends Item> holder) {
            this.list.add(holder);
        }
    }

    public static class Blocks {
        private final RegistryCollection.Items items;
        private final List<RegistryBlockItemHolder<? extends Block>> list = new ArrayList<>();

        public Blocks(RegistryCollection.Items items) {
            this.items = items;
        }
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        public List<RegistryBlockItemHolder<? extends Block>> getEntries() {
            return this.list;
        }

        public void forEach(Consumer<Block> consumer) {
            this.getEntries().forEach(holder -> consumer.accept(holder.block().get()));
        }

        public <T extends Block> RegistryBlockItemHolder<T> registerBlockWithItem(String name,
                                                       Function<BlockBehaviour.Properties, T> block,
                                                       BiFunction<Block, Item.Properties, BlockItem> blockItem) {
            RegistryHolder<T> blockHolder = Services.REGISTRY.registerBlock(name, block);
            RegistryHolder<BlockItem> itemHolder = Services.REGISTRY.registerBlockItem(name, blockHolder, blockItem);
            RegistryBlockItemHolder<T> blockWithItemHolder = new RegistryBlockItemHolder<>(blockHolder, itemHolder);

            this.list.add(blockWithItemHolder);
            this.items.registerHolder(itemHolder);

            return blockWithItemHolder;
        }

        public <T extends Block> RegistryBlockItemHolder<T> registerBlockWithItem(String name,
                                                                                  Function<BlockBehaviour.Properties, T> block) {
            return this.registerBlockWithItem(name, block, BlockItem::new);
        }
    }
}

