package com.alotofletters.uchip;

import com.alotofletters.uchip.content.board.BoardItem;
import com.alotofletters.uchip.content.board.memory.ram.RamItem;
import com.alotofletters.uchip.content.board.memory.ram.RamType;
import com.alotofletters.uchip.content.board.processor.MOS6502Item;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class MicrochipItems {


    private static final Registrate REGISTRATE = Microchip.REGISTRATE.get();

    public static ItemEntry<BoardItem> BOARD = REGISTRATE.item("board", BoardItem::new)
            .model(empty())
            .lang("Proto Board")
            .register();

    public static ItemEntry<MOS6502Item> PROCESSOR_6502 = REGISTRATE.item("processor_6502", MOS6502Item::new)
            .model(empty())
            .lang("MOS 6502")
			.transform(MicrochipItems::chip)
            .register();

	public static ItemEntry<RamItem> RAM_32K = ram("ram_32k", RamType.RAM_32K)
			.model(empty())
			.transform(MicrochipItems::chip)
			.register();

    public static ItemEntry<Item> SILICON_WAFER = REGISTRATE.item("silicon_wafer", Item::new)
            .recipe((ctx, prov) -> prov.blasting(DataIngredient.items(Items.QUARTZ), ctx, 0.1f))
			.tag(MicrochipTags.GEM_SILICON)
            .register();

    private static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> empty() {
        return (ctx, prov) -> {}; // no model generation
    }

	private static ItemBuilder<RamItem, Registrate> ram(String name, RamType type) { 
		return REGISTRATE.item(name, (props) -> new RamItem(props, type));
	}

	private static <T extends Item> ItemBuilder<T, Registrate> chip(ItemBuilder<T, Registrate> builder) {
		return builder
			.properties((props) -> props.rarity(Rarity.UNCOMMON))
			.tag(MicrochipTags.CHIP);
	}

    public static void register() { }
}
