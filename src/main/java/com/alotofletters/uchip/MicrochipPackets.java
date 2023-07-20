package com.alotofletters.uchip;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Function;

import com.alotofletters.uchip.core.net.MicrochipPacket;

/**
 * Registers {@link MicrochipPacket} objects and handles a {@link SimpleChannel}
 * to send aforementioned packets.
 */
public class MicrochipPackets {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel channel = NetworkRegistry.newSimpleChannel(
            Microchip.location("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int id = 0;

    private static <T extends MicrochipPacket> void register(Class<T> type, Function<FriendlyByteBuf, T> factory, NetworkDirection dir) {
        channel.messageBuilder(type, id++, dir)
                .encoder(T::encode)
                .decoder(factory)
                .consumerNetworkThread((packet, contextSupplier) -> {
                    NetworkEvent.Context ctx = contextSupplier.get();
                    if (packet.handle(ctx)) {
                        ctx.setPacketHandled(true);
                    }
                })
                .add();
    }

    public static void register() {

    }
}
