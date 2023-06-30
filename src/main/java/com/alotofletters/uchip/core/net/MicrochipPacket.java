package com.alotofletters.uchip.core.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public abstract class MicrochipPacket {

    public abstract void encode(FriendlyByteBuf buf);

    public abstract boolean handle(NetworkEvent.Context context);
}
