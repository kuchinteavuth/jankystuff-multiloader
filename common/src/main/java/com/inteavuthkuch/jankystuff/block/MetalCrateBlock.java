package com.inteavuthkuch.jankystuff.block;

import com.inteavuthkuch.jankystuff.block.entity.MetalCrateBlockEntity;
import com.inteavuthkuch.jankystuff.menu.MetalCrateMenu;
import com.inteavuthkuch.jankystuff.platform.Services;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class MetalCrateBlock extends BaseEntityBlock {
    public static final MapCodec<MetalCrateBlock> CODEC = simpleCodec(MetalCrateBlock::new);

    public MetalCrateBlock(Properties properties) {
        super(properties.mapColor(MapColor.METAL).instrument(NoteBlockInstrument.PLING).strength(2.5F).sound(SoundType.METAL));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new MetalCrateBlockEntity(worldPosition, blockState);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof MetalCrateBlockEntity be) {
            Services.PLATFORM.openExtendedMenu(player, be, pos, BlockPos.STREAM_CODEC);
            return InteractionResult.SUCCESS;
        }

        return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide()) {
            level.removeBlockEntity(pos);
        }

        this.spawnDestroyParticles(level, player, pos, state);
        return state;
    }
}
