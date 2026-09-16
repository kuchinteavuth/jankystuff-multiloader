package com.inteavuthkuch.jankystuff.common;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ToolMaterial;

public final class ModToolMaterials {
    private ModToolMaterials() {}

    public static final ToolMaterial PAXEL_IRON = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 250 * 2, 6.0F, 4.0F, 14, ItemTags.IRON_TOOL_MATERIALS);
    public static final ToolMaterial PAXEL_DIAMOND = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561 * 2, 8.0F, 6.0F, 10, ItemTags.DIAMOND_TOOL_MATERIALS);
    public static final ToolMaterial PAXEL_NETHERITE = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031 * 2, 9.0F, 8.0F, 15, ItemTags.NETHERITE_TOOL_MATERIALS);
}
