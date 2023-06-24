package com.alotofletters.uchip.content.machine.board;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.net.MicrochipPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class BoardEditPacket extends MicrochipPacket {
    private String name;
    private Board board;


    public BoardEditPacket(FriendlyByteBuf buf) {
        name = buf.readUtf();
        board = .readNbt();
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
            ServerPlayer player = context.getSender(); 
            ItemStack stack = player.getMainHandItem();
            if (!(stack.getItem() instanceof BoardItem)) return;
            stack.setTag(this.board);
        });
        return true;
    }
}
