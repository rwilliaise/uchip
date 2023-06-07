package com.alotofletters.uchip;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class MicrochipItems {

    public static ItemEntry<Item> PROCESSOR_6502;

    private static final Registrate REGISTRATE = Microchip.REGISTRATE.get();

    public static void register() {
        PROCESSOR_6502 = REGISTRATE.item("processor_6502", Item::new)
                .initialProperties(() -> new Item.Properties().rarity(Rarity.UNCOMMON))
                .lang("6502")
                .register();
    }
}
