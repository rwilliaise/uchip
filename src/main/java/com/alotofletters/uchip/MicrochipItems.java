package com.alotofletters.uchip;

import com.alotofletters.uchip.content.processor.MOS6502Item;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class MicrochipItems {


    private static final Registrate REGISTRATE = Microchip.REGISTRATE.get();

    public static ItemEntry<MOS6502Item> PROCESSOR_6502 = REGISTRATE.item("processor_6502", MOS6502Item::new)
            .model((ctx, prov) -> {}) // don't generate anything
            .initialProperties(() -> new Item.Properties().rarity(Rarity.UNCOMMON))
            .lang("MOS 6502")
            .register();

    public static ItemEntry<Item> SILICON_WAFER = REGISTRATE.item("silicon_wafer", Item::new)
            .register();

    public static void register() { }
}
