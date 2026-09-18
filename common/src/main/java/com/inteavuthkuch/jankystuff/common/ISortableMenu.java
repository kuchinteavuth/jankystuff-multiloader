package com.inteavuthkuch.jankystuff.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface ISortableMenu {
    BlockEntity getBlockEntity();
    default BlockPos getBlockPos() {
        return getBlockEntity().getBlockPos();
    }
}
