package com.inteavuthkuch.jankystuff.platform.services;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public interface IPlatformNetwork {
    void sendToServer(CustomPacketPayload payload);
    void sendToClient(ServerPlayer player, CustomPacketPayload payload);
}
