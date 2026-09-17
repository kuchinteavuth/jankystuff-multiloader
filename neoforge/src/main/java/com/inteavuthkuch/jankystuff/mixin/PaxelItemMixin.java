package com.inteavuthkuch.jankystuff.mixin;

import com.inteavuthkuch.jankystuff.item.PaxelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public abstract class PaxelItemMixin implements IItemExtension {
    @Override
    public boolean canPerformAction(ItemInstance stack, ItemAbility itemAbility) {
        if ((Object)this instanceof PaxelItem) {
            if (itemAbility == ItemAbilities.AXE_STRIP
                    || itemAbility == ItemAbilities.SHOVEL_FLATTEN
                    || itemAbility == ItemAbilities.AXE_WAX_OFF
                    || itemAbility == ItemAbilities.AXE_SCRAPE
                    || itemAbility == ItemAbilities.SHOVEL_DOUSE) {
                return true;
            }
        }
        return IItemExtension.super.canPerformAction(stack, itemAbility);
    }
}
