package com.inteavuthkuch.jankystuff.item;

import com.inteavuthkuch.jankystuff.platform.Services;
import com.inteavuthkuch.jankystuff.tag.ModBlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.context.UseOnContext;

public class PaxelItem extends Item {
    public PaxelItem(ToolMaterial material, float attackDamageBaseline, float attackSpeedBaseline, Properties properties) {
        super(
                properties.tool(material, ModBlockTags.MINEABLE_WITH_PAXEL, attackDamageBaseline, attackSpeedBaseline, 0.0F)
        );
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        InteractionResult result = Services.PAXEL_HELPER.useOn(context);

        return result != InteractionResult.PASS
                ? result
                : super.useOn(context);
    }
}
