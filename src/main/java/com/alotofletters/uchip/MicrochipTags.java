package com.alotofletters.uchip;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MicrochipTags {

    public static final TagKey<Item> BOARD = item("board");

    private static TagKey<Item> item(String name) {
        return ItemTags.create(Microchip.location(name));
    }
}
