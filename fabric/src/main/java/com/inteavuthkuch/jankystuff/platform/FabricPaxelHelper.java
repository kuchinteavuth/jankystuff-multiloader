package com.inteavuthkuch.jankystuff.platform;

import com.google.common.collect.BiMap;
import com.inteavuthkuch.jankystuff.mixin.ShovelItemAccessor;
import com.inteavuthkuch.jankystuff.platform.services.IPaxelHelper;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Optional;
import java.util.function.Function;

public class FabricPaxelHelper implements IPaxelHelper {
    private static InteractionResult useOnAction(UseOnContext context, Function<BlockState, BlockState> func, SoundEvent sound, Integer particleLevelEvent) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();

        BlockState blockState = level.getBlockState(pos);
        BlockState updatedState = func.apply(blockState);
        if (updatedState == null)
            return InteractionResult.PASS;

        level.setBlock(pos, updatedState, Block.UPDATE_ALL_IMMEDIATE);
        level.playSound(player, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (particleLevelEvent != null) {
            level.levelEvent(player, particleLevelEvent, pos, 0);
        }
        level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);

        if (player instanceof ServerPlayer serverPlayer) {
            context.getItemInHand().hurtAndBreak(1, serverPlayer, context.getHand().asEquipmentSlot());
        }

        return InteractionResult.SUCCESS;
    }

    private static InteractionResult flatten(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();

        BlockState blockState = level.getBlockState(pos);
        BlockState newState = ShovelItemAccessor.getFlattenables().get(blockState.getBlock());
        BlockState updatedState = null;
        if (newState != null && level.getBlockState(pos.above()).isAir()) {
            level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            updatedState = newState;
        } else if (blockState.getBlock() instanceof CampfireBlock && (Boolean) blockState.getValue(CampfireBlock.LIT)) {
            if (!level.isClientSide()) {
                level.levelEvent(null, LevelEvent.SOUND_EXTINGUISH_FIRE, pos, 0);
            }

            CampfireBlock.dowse(context.getPlayer(), level, pos, blockState);
            updatedState = blockState.setValue(CampfireBlock.LIT, false);
        }

        if (updatedState != null) {
            if (!level.isClientSide()) {
                level.setBlock(pos, updatedState, 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, updatedState));
                if (player != null) {
                    context.getItemInHand().hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
                }
            }

            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult axeStrip = useOnAction(context, StrippableBlockRegistry::getStrippedBlockState, SoundEvents.AXE_STRIP, null);
        if (axeStrip != InteractionResult.PASS)
            return axeStrip;

        InteractionResult axeScrap = useOnAction(context, state -> WeatheringCopper.getPrevious(state).orElse(null), SoundEvents.AXE_SCRAPE, LevelEvent.PARTICLES_SCRAPE);
        if (axeScrap != InteractionResult.PASS)
            return axeScrap;

        InteractionResult axeWaxOff = useOnAction(context, oldState -> {
            Optional<BlockState> waxOffBlock = Optional.ofNullable((Block)((BiMap) HoneycombItem.WAX_OFF_BY_BLOCK.get())
                    .get(oldState.getBlock())).map((b) -> b.withPropertiesOf(oldState));
            return waxOffBlock.orElse(null);
        }, SoundEvents.AXE_WAX_OFF, LevelEvent.PARTICLES_WAX_OFF);
        if (axeWaxOff != InteractionResult.PASS)
            return axeWaxOff;

        return flatten(context);
    }
}
