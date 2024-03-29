package com.alotofletters.uchip.content.board;

import com.alotofletters.uchip.core.board.Board;
import com.alotofletters.uchip.core.net.MicrochipPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

/**
 * Packet to update the board item stack with new settings given by the client.
 */
public class BoardEditPacket extends MicrochipPacket {
    private final Board board;

    public BoardEditPacket(FriendlyByteBuf buf) {
        board = new Board(buf.readItem());
    }

    public BoardEditPacket(Board board) {
        this.board = board;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        CompoundTag out = new CompoundTag();
        board.save(out);
        buf.writeNbt(out);
    }

    @Override
    public boolean handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ItemStack stack = player.getMainHandItem();
            if (!(stack.getItem() instanceof BoardItem)) return;
            CompoundTag out = new CompoundTag();
            board.save(out);
            stack.setTag(out);
        });
        return true;
    }
}
