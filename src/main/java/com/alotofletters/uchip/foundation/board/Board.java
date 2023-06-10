package com.alotofletters.uchip.foundation.board;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;

/**
 * Represents a board BoardObjects can interface with.
 *
 * @see BoardComponent
 */
public abstract class Board {
    protected int data;
    protected int address;

    protected ItemStack stack;
    protected ArrayList<RangedComponent> components = Lists.newArrayList();

    public Board(ItemStack stack) {
        this.stack = stack;
    }

    public void save(CompoundTag tag) {
        ListTag components = new ListTag();

        this.components.forEach(component -> {
            components.add(component.save(new CompoundTag()));
        });

        tag.put("Components", components);
    }

    public void load(CompoundTag tag) {
        ListTag components = tag.getList("Components", Tag.TAG_COMPOUND);
    }

    public abstract int getDataWidth();

    public abstract int getAddressWidth();

    public record RangedComponent(BoardComponent component, int from, int to) {

        public CompoundTag save(CompoundTag tag) {
            CompoundTag component = new CompoundTag();
            this.component.save(component);
            tag.put("Component", component);

            tag.putInt("From", from);
            tag.putInt("To", to);
            return tag;
        }
    }
}
