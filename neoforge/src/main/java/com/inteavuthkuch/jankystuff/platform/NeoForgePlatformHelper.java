package com.inteavuthkuch.jankystuff.platform;

import com.inteavuthkuch.jankystuff.platform.services.IPlatformHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.util.function.Consumer;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.getCurrent().isProduction();
    }

    @Override
    public <D> void openExtendedMenu(Player player, MenuProvider provider, D extraData, StreamCodec<? super RegistryFriendlyByteBuf, D> streamCodec) {
        player.openMenu(provider, buf -> streamCodec.encode(buf, extraData));
    }
}