package com.alotofletters.uchip.content.board.memory;

import com.alotofletters.uchip.core.board.Board;
import com.alotofletters.uchip.core.board.BoardComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.nio.ByteBuffer;

public class MemoryComponent extends BoardComponent {
    private final MemoryType type;
    private ByteBuffer buffer;

    public MemoryComponent(Board owner, ItemStack stack, MemoryType type) {
        super(owner, stack);
        this.type = type;
    }

    @Override
    public int read(int address) {
        return buffer.get(mask(address));
    }

    @Override
    public void write(int address, int value) {
        if (!type.writable)
            return; // avoid exceptions
        buffer.put(mask(address), (byte) value);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Data")) {

        }
    }

    @Override
    public int getDataWidth() {
        return 8; // reading byte only (for now)
    }

    @Override
    public int getAddressWidth() {
        return type.addressWidth;
    }
}
