package com.inteavuthkuch.jankystuff;
import com.inteavuthkuch.jankystuff.network.packet.ServerBoundBlockEntitySortPacket;
import com.inteavuthkuch.jankystuff.network.packet.handler.ServerBoundBlockEntitySortPacketHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;

public class FabricJankyStuff implements ModInitializer {
    
    @Override
    public void onInitialize() {
        CommonClass.init();

        registerClientBoundPackets(PayloadTypeRegistry.clientboundPlay());
        registerServerBoundPackets(PayloadTypeRegistry.serverboundPlay());
    }

    private static void registerClientBoundPackets(PayloadTypeRegistry<RegistryFriendlyByteBuf> registry) {
        // This is on CLIENT (Server send to Client)
        // ClientPlayNetworking.registerGlobalReceiver()
    }

    private static void registerServerBoundPackets(PayloadTypeRegistry<RegistryFriendlyByteBuf> registry) {
        // This is on SERVER (Client send to Server)
        registry.register(ServerBoundBlockEntitySortPacket.TYPE, ServerBoundBlockEntitySortPacket.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ServerBoundBlockEntitySortPacket.TYPE, (packet, context) -> {
            context.server().execute(() -> ServerBoundBlockEntitySortPacketHandler.handle(packet, context.player()));
        });
    }
}
