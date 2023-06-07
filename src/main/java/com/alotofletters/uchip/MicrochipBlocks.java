package com.alotofletters.uchip;

import com.alotofletters.uchip.content.machine.chip_builder.ChipBuilderBlock;
import com.alotofletters.uchip.content.machine.chip_builder.ChipBuilderBlockEntity;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;

public class MicrochipBlocks {

    private static final Registrate REGISTRATE = Microchip.REGISTRATE.get();

    public static final BlockEntry<ChipBuilderBlock> CHIP_BUILDER = REGISTRATE.block("chip_builder", ChipBuilderBlock::new)
            .blockstate((ctx, prov) -> prov.horizontalBlock(ctx.get(),
                    prov.models().orientable(
                            ctx.getName(),
                            prov.mcLoc("block/furnace_side"),
                            prov.mcLoc("block/furnace_front"),
                            prov.mcLoc("block/furnace_top"))
            ))
            .simpleBlockEntity(ChipBuilderBlockEntity::new)
            .simpleItem()
            .register();

    public static void register() { }
}
