package com.inteavuthkuch.jankystuff.block;

import com.inteavuthkuch.jankystuff.platform.Services;
import com.inteavuthkuch.jankystuff.platform.services.RegistryBlockItemHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {
    private ModBlocks() {}
    public static void initialize() {}

    public static final RegistryBlockItemHolder<Block> SIMPLE_LAMP = Services.REGISTRY.registerBlockWithItem("simple_lamp", p ->
            new Block(p
                    .lightLevel(_ -> 15)
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.PLING)
                    .strength(0.3F)
                    .sound(SoundType.GLASS)
            ));
}
