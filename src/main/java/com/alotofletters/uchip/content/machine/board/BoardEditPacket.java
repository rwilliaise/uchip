package com.alotofletters.uchip.content.machine.board;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.net.MicrochipPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class BoardEditPacket extends MicrochipPacket {
    private String name;
    private CompoundTag board;


    public BoardEditPacket(FriendlyByteBuf buf) {
        name = buf.readUtf();
        board = buf.readNbt();
    }

    public BoardEditPacket(String name, CompoundTag board) {
        this.name = name;
        this.board = board;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(name);
        buf.writeNbt(board);
    }

    @Override
    public boolean handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {

        });
        return true;
    }
}
