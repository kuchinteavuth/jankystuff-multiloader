package com.inteavuthkuch.jankystuff.platform;

import com.inteavuthkuch.jankystuff.Constants;
import com.inteavuthkuch.jankystuff.platform.services.IRegistryHelper;
import com.inteavuthkuch.jankystuff.platform.util.RegistryHolder;
import com.inteavuthkuch.jankystuff.util.function.TriFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
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
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NeoForgeRegistryHelper implements IRegistryHelper {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MOD_ID);
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Constants.MOD_ID);
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);
    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, Constants.MOD_ID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        BLOCKS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
        BLOCK_ENTITY_TYPES.register(bus);
        MENU_TYPES.register(bus);
    }

    @Override
    public <T extends Item> RegistryHolder<T> registerItem(String name, Function<Item.Properties, T> func) {
        DeferredItem<T> item = ITEMS.registerItem(name, func);

        return new RegistryHolder<>() {
            @Override
            public T get() {
                return item.get();
            }

            @Override
            public Identifier id() {
                return item.getId();
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
                return tab.getId();
            }
        };
    }

    @Override
    public <T extends Block> RegistryHolder<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> func) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, func);

        return new RegistryHolder<T>() {
            @Override
            public T get() {
                return block.get();
            }

            @Override
            public Identifier id() {
                return block.getId();
            }
        };
    }

    @Override
    public <T extends BlockItem> RegistryHolder<T> registerBlockItem(String name, RegistryHolder<? extends Block> block, BiFunction<Block, Item.Properties, T> func) {
        return registerItem(name, properties -> func.apply(block.get(), properties.useBlockDescriptionPrefix()));
    }

    @Override
    public <T extends BlockEntity> RegistryHolder<BlockEntityType<T>> registerBlockEntityType(String name, Supplier<BiFunction<BlockPos, BlockState, T>> factory, Supplier<List<Block>> validBlocks) {

        DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> holder = BLOCK_ENTITY_TYPES.register(name, () ->
                new BlockEntityType<>(factory.get()::apply, validBlocks.get().toArray(Block[]::new)));

        return new RegistryHolder<>() {
            @Override
            public BlockEntityType<T> get() {
                return holder.get();
            }

            @Override
            public Identifier id() {
                return holder.getId();
            }
        };
    }

    @Override
    public <T extends AbstractContainerMenu> RegistryHolder<MenuType<T>> registerMenuType(String name, BiFunction<Integer, Inventory, T> factory) {

        DeferredHolder<MenuType<?>, MenuType<T>> holder = MENU_TYPES.register(name, () -> new MenuType<>(factory::apply, FeatureFlags.REGISTRY.allFlags()));

        return new RegistryHolder<>() {
            @Override
            public MenuType<T> get() {
                return holder.get();
            }

            @Override
            public Identifier id() {
                return holder.getId();
            }
        };
    }

    @Override
    public <T extends AbstractContainerMenu, D> RegistryHolder<MenuType<T>> registerMenuType(String name, TriFunction<Integer, Inventory, D, T> factory, StreamCodec<? super RegistryFriendlyByteBuf, D> streamCodec) {
        DeferredHolder<MenuType<?>, MenuType<T>> holder = MENU_TYPES.register(name, () ->
                IMenuTypeExtension.create((windowId, inv, buf) -> {
                    D data = streamCodec.decode(buf);
                    return factory.apply(windowId, inv, data);
                })
        );

        return new RegistryHolder<>() {
            @Override
            public MenuType<T> get() {
                return holder.get();
            }

            @Override
            public Identifier id() {
                return holder.getId();
            }
        };
    }
}
