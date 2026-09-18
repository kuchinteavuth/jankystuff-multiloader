package com.inteavuthkuch.jankystuff.network.packet;

import com.inteavuthkuch.jankystuff.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ServerBoundBlockEntitySortPacket(BlockPos position, boolean sortByAmount, boolean isAscending) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ServerBoundBlockEntitySortPacket> TYPE =
            new Type<>(Constants.modId("blockentity_sort_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerBoundBlockEntitySortPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, ServerBoundBlockEntitySortPacket::position,
            ByteBufCodecs.BOOL, ServerBoundBlockEntitySortPacket::sortByAmount,
            ByteBufCodecs.BOOL, ServerBoundBlockEntitySortPacket::isAscending,
            ServerBoundBlockEntitySortPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
