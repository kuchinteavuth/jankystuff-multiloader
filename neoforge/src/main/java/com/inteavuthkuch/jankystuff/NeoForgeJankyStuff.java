package com.inteavuthkuch.jankystuff;


import com.inteavuthkuch.jankystuff.network.packet.ServerBoundBlockEntitySortPacket;
import com.inteavuthkuch.jankystuff.network.packet.handler.ServerBoundBlockEntitySortPacketHandler;
import com.inteavuthkuch.jankystuff.platform.NeoForgeRegistryHelper;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(Constants.MOD_ID)
public class NeoForgeJankyStuff {

    public NeoForgeJankyStuff(IEventBus eventBus) {
        CommonClass.init();
        NeoForgeRegistryHelper.register(eventBus);
        eventBus.addListener(JankyStuffDataGenerator::gatherClientData);
        eventBus.addListener(NeoForgeJankyStuffClient::registerMenuScreens);
        eventBus.addListener(this::registerPackets);
    }

    private void registerPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1.0.1");
        registrar.playToServer(ServerBoundBlockEntitySortPacket.TYPE, ServerBoundBlockEntitySortPacket.STREAM_CODEC, (packet, context) -> {
            if(context.player() instanceof ServerPlayer player) {
                ServerBoundBlockEntitySortPacketHandler.handle(packet, player);
            }
        });
    }
}