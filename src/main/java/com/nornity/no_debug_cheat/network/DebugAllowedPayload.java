package com.nornity.no_debug_cheat.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record DebugAllowedPayload(boolean allowed) implements CustomPacketPayload {

    public static final Type<DebugAllowedPayload> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath("nodebugcheat", "debug_allowed")
    );

    public static final StreamCodec<ByteBuf, DebugAllowedPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL,
                    DebugAllowedPayload::allowed,
                    DebugAllowedPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
