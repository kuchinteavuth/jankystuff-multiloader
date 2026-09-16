package com.inteavuthkuch.jankystuff.platform.services;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

public interface IPaxelHelper {
    InteractionResult useOn(UseOnContext context);
}
