package com.alotofletters.uchip;

import com.alotofletters.uchip.content.board.Board8Item;
import com.alotofletters.uchip.content.board.memory.ram.RamItem;
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

    public static ItemEntry<MOS6502Item> PROCESSOR_6502 = REGISTRATE.item("processor_6502", MOS6502Item::new)
            .model(empty())
            .initialProperties(() -> new Item.Properties().rarity(Rarity.UNCOMMON))
            .lang("MOS 6502")
            .register();

    public static ItemEntry<Board8Item> BOARD8 = REGISTRATE.item("board8", Board8Item::new)
            .model(empty())
            .initialProperties(() -> new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1))
            .lang("8-bit Board")
            .tag(MicrochipTags.BOARD)
            .register();

	public static ItemEntry<RamItem> RAM_32K = ram("ram_32k", 8, 32_768)
		.register();

    public static ItemEntry<Item> SILICON_WAFER = REGISTRATE.item("silicon_wafer", Item::new)
            .recipe((ctx, prov) -> prov.blasting(DataIngredient.items(Items.QUARTZ), ctx, 0.1f))
            .register();

    private static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> empty() {
        return (ctx, prov) -> {}; // no model generation
    }

	private static ItemBuilder<RamItem, Registrate> ram(String name, int dataSize, int size) { 
		return REGISTRATE.item(name, (props) -> new RamItem(props, dataSize, size));
	}

    public static void register() { }
}
