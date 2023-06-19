package com.alotofletters.uchip;

import com.alotofletters.uchip.content.machine.board.Board8Item;
import com.alotofletters.uchip.content.processor.MOS6502Item;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.item.Item;
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
            .initialProperties(() -> new Item.Properties().rarity(Rarity.UNCOMMON))
            .lang("8-bit Board")
            .tag(MicrochipTags.BOARD)
            .register();

    public static ItemEntry<Item> SILICON_WAFER = REGISTRATE.item("silicon_wafer", Item::new)
            .register();

    private static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> empty() {
        return (ctx, prov) -> {}; // no model generation
    }

    public static void register() { }
}
