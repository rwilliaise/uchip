package com.alotofletters.uchip;

import com.alotofletters.uchip.content.board.BoardItem;
import com.alotofletters.uchip.content.board.memory.MemoryItem;
import com.alotofletters.uchip.content.board.memory.MemoryType;
import com.alotofletters.uchip.content.board.processor.mos6502.MOS6502Item;
import com.alotofletters.uchip.content.drone.DroneItem;
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
            .transform(MicrochipItems::unstackable)
            .register();

    public static ItemEntry<DroneItem> DRONE = REGISTRATE.item("drone", DroneItem::new)
            .model(empty())
            .transform(MicrochipItems::unstackable)
            .register();

    public static ItemEntry<MOS6502Item> PROCESSOR_6502 = REGISTRATE.item("processor_6502", MOS6502Item::new)
            .model(empty())
            .lang("MOS 6502")
            .transform(MicrochipItems::chip)
            .register();

    public static ItemEntry<MemoryItem> RAM_32K = ram("ram_32k", MemoryType.RAM_32K)
            .model(empty())
            .lang("32k SRAM") // TODO: cheaper but more power consuming dram?
            .transform(MicrochipItems::chip)
            .register();

    public static ItemEntry<Item> SILICON_WAFER = REGISTRATE.item("silicon_wafer", Item::new)
            .recipe((ctx, prov) -> prov.blasting(DataIngredient.items(Items.QUARTZ), ctx, 0.1f))
            .tag(MicrochipTags.GEM_SILICON)
            .register();

    private static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateItemModelProvider> empty() {
        return (ctx, prov) -> {
        }; // no model generation
    }

    private static ItemBuilder<MemoryItem, Registrate> ram(String name, MemoryType type) {
        return REGISTRATE.item(name, (props) -> new MemoryItem(props, type));
    }

    private static <T extends Item> ItemBuilder<T, Registrate> chip(ItemBuilder<T, Registrate> builder) {
        return builder
                .properties(props -> props.rarity(Rarity.UNCOMMON).stacksTo(1))
                .tag(MicrochipTags.CHIP);
    }

    private static <T extends Item> ItemBuilder<T, Registrate> unstackable(ItemBuilder<T, Registrate> builder) {
        return builder
                .properties(props -> props.stacksTo(1));
    }

    public static void register() {
    }
}
