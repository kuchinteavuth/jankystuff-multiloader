package com.inteavuthkuch.jankystuff.network.packet.handler;

import com.inteavuthkuch.jankystuff.common.ISortableBlockEntity;
import com.inteavuthkuch.jankystuff.network.packet.ServerBoundBlockEntitySortPacket;
import net.minecraft.server.level.ServerPlayer;

public final class ServerBoundBlockEntitySortPacketHandler {
    private ServerBoundBlockEntitySortPacketHandler() {}

    public static void handle(ServerBoundBlockEntitySortPacket packet, ServerPlayer player) {

        if (player.level().isLoaded(packet.position())) {
            if (player.level().getBlockEntity(packet.position()) instanceof ISortableBlockEntity sortable) {
                sortable.sort(packet.sortByAmount(), packet.isAscending());
            }
        }
    }
}
