package com.inteavuthkuch.jankystuff.platform;

import com.inteavuthkuch.jankystuff.platform.services.IPaxelHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class NeoForgePaxelHelper implements IPaxelHelper {

    private static InteractionResult useOnAction(UseOnContext context, ItemAbility ability, SoundEvent sound, Integer particleLevelEvent) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        ItemStack itemStack = context.getItemInHand();

        BlockState blockState = level.getBlockState(pos);
        BlockState updatedState = blockState.getToolModifiedState(context, ability, false);
        if (updatedState == null)
            return InteractionResult.PASS;

        level.setBlock(pos, updatedState, Block.UPDATE_ALL_IMMEDIATE);
        level.playSound(player, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (particleLevelEvent != null) {
            level.levelEvent(player, particleLevelEvent, pos, 0);
        }
        level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);

        if (player instanceof ServerPlayer serverPlayer) {
            itemStack.hurtAndBreak(1, serverPlayer, context.getHand().asEquipmentSlot());
        }

        return InteractionResult.SUCCESS;
    }

    private static InteractionResult flatten(UseOnContext context) {
        InteractionResult flattenResult = useOnAction(context, ItemAbilities.SHOVEL_FLATTEN, SoundEvents.SHOVEL_FLATTEN, null);
        if (flattenResult != InteractionResult.PASS)
            return flattenResult;

        return useOnAction(context, ItemAbilities.SHOVEL_DOUSE, SoundEvents.SHOVEL_FLATTEN, LevelEvent.SOUND_EXTINGUISH_FIRE);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult axeStrip = useOnAction(context, ItemAbilities.AXE_STRIP, SoundEvents.AXE_STRIP, null);
        if (axeStrip != InteractionResult.PASS) return axeStrip;

        InteractionResult axeScrap = useOnAction(context, ItemAbilities.AXE_SCRAPE, SoundEvents.AXE_SCRAPE, LevelEvent.PARTICLES_SCRAPE);
        if (axeScrap != InteractionResult.PASS) return axeScrap;

        InteractionResult axeWaxOff = useOnAction(context, ItemAbilities.AXE_WAX_OFF, SoundEvents.AXE_WAX_OFF, LevelEvent.PARTICLES_WAX_OFF);
        if (axeWaxOff != InteractionResult.PASS) return axeWaxOff;

        return flatten(context);
    }
}
