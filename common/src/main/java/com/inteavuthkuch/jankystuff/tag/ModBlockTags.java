package com.inteavuthkuch.jankystuff.tag;

import com.inteavuthkuch.jankystuff.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    private static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, Constants.modId(name));
    }

    public static final TagKey<Block> MINEABLE_WITH_PAXEL = create("mineable/paxel");
}
