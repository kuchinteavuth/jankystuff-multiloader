package com.inteavuthkuch.jankystuff.block.entity;

import com.inteavuthkuch.jankystuff.common.CrateMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public abstract class BaseCrateBlockEntity extends BlockEntity implements Container, Nameable {

    public final CrateMaterial material;
    protected NonNullList<ItemStack> inventory;
    protected @Nullable Component name;

    public BaseCrateBlockEntity(BlockEntityType<?> type, CrateMaterial material, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
        this.material = material;
        this.inventory = NonNullList.withSize(material.rows() * material.cols(), ItemStack.EMPTY);
    }

    protected NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    /* CONTAINER */

    @Override
    public int getContainerSize() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int count) {
        ItemStack result = ContainerHelper.removeItem(this.getItems(), slot, count);
        if (!result.isEmpty()) {
            this.setChanged();
        }

        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.getItems(), slot);
    }

    @Override
    public void setItem(int slot, ItemStack itemStack) {
        this.inventory.set(slot, itemStack);
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.inventory.clear();
    }

    @Override
    public void startOpen(ContainerUser containerUser) {
        if (this.getLevel() != null) {
            this.getLevel().playSound(null, getBlockPos(), SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 0.5F, this.getLevel().getRandom().nextFloat() * 0.1F + 0.9F);
        }
    }

    @Override
    public void stopOpen(ContainerUser containerUser) {
        if (this.getLevel() != null) {
            this.getLevel().playSound(null, getBlockPos(), SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS, 0.5F, this.getLevel().getRandom().nextFloat() * 0.1F + 0.9F);
        }
    }

    /*--------*/
    /* NAMEABLE */
    @Override
    public Component getName() {
        return this.name != null ? this.name : this.getDisplayName();
    }

    @Override
    public @Nullable Component getCustomName() {
        return this.name;
    }
    /*--------*/

    /*----SYNC SECTION----*/

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NonNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter components) {
        super.applyImplicitComponents(components);
        this.name = components.get(DataComponents.CUSTOM_NAME);
        components.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(this.inventory);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(DataComponents.CUSTOM_NAME, this.name);
        components.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(this.inventory));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void removeComponentsFromTag(ValueOutput output) {
        output.discard("CustomName");
        output.discard("Items");
    }

    /*----END SYNC----*/

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.name = parseCustomNameSafe(input, "CustomName");
        ContainerHelper.loadAllItems(input, inventory);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.storeNullable("CustomName", ComponentSerialization.CODEC, this.name);
        ContainerHelper.saveAllItems(output, inventory, false);
    }
}
