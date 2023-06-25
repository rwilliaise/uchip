package com.alotofletters.uchip;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MicrochipTags {

	// mod
    public static final TagKey<Item> CHIP = item("chip");

	// forge
	public static final TagKey<Item> GEM_SILICON = item(forge("gems/silicon"));

    private static TagKey<Item> item(String name) {
        return ItemTags.create(Microchip.location(name));
    }

    private static TagKey<Item> item(ResourceLocation location) {
        return ItemTags.create(location);
    }

	private static ResourceLocation forge(String name) {
		return new ResourceLocation("forge", name);
	}
}
