package com.alotofletters.uchip.foundation.board;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;

/**
 * Represents a board BoardComponents can interface with. Stores items.
 *
 * @see BoardComponent
 */
public class Board {
    protected int data;
    protected int address;

    protected ArrayList<RangedComponent> components = Lists.newArrayList();
    protected Processor processor;

	public Board(ItemStack stack) {
		load(stack.getOrCreateTag());
	}

	public int read(int address) {
		return components.stream()
			.filter(comp -> comp.from <= address && comp.to > address)
			.map(comp -> comp.component.read(address))
			.reduce(0, (x, y) -> x | y);
	}

	public void write(int address, int value) {
		components.stream()
			.filter(comp -> comp.from <= address && comp.to > address)
			.forEach(comp -> comp.component.write(address, value));
	}

	public boolean clock() {
		if (this.processor == null) return false;
		return this.processor.clock();
	}

    public void save(CompoundTag tag) {
        ListTag components = new ListTag();
        this.components.forEach(component -> components.add(component.save(new CompoundTag())));
        tag.put("Components", components);
    }

    private void load(CompoundTag tag) {
        if (tag.contains("Components")) {
            ListTag components = tag.getList("Components", Tag.TAG_COMPOUND);
            components.forEach((component) -> this.components.add(RangedComponent.load(this, (CompoundTag) component)));
        }
    }

    public boolean equipComponent(BoardComponent component) {
        if (component instanceof Processor processor) {
            if (this.processor != null) { return false; }

            this.processor = processor;
            return true;
        }
        components.add(new RangedComponent(component, 0, (int) Math.pow(2, component.getAddressWidth())));
        return true;
    }

    public boolean isLittleEndian() {
		return true; // TODO
    }

    public record RangedComponent(BoardComponent component, int from, int to) {

        public CompoundTag save(CompoundTag tag) {
            CompoundTag component = this.component.getStack()
                    .getOrCreateTag();
            this.component.save(component);

            tag.put("Item", this.component.getStack().save(new CompoundTag()));

            tag.putInt("From", from);
            tag.putInt("To", to);
            return tag;
        }

        public static RangedComponent load(Board board, CompoundTag tag) {
            ItemStack stack = ItemStack.of(tag.getCompound("Item"));

            if (stack.getItem() instanceof ComponentItem<?> item) {
                BoardComponent component = item.createComponent(board, stack);

                return new RangedComponent(
                        component,
                        tag.getInt("From"),
                        tag.getInt("To")
                );
            }
            return null;
        }
    }
}
