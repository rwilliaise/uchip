package com.alotofletters.uchip;

import com.alotofletters.uchip.content.machine.casing.CasingBlockEntity;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class MicrochipBlockEntities {

    private static final Registrate REGISTRATE = Microchip.REGISTRATE.get();

    public static final BlockEntityEntry<CasingBlockEntity> CASING = REGISTRATE.blockEntity("casing", CasingBlockEntity::new)
            .validBlock(MicrochipBlocks.CASING)
            .register();

    public static final BlockEntityEntry<CasingBlockEntity> CHIP_BUILDER = REGISTRATE.blockEntity("chip_builder", CasingBlockEntity::new)
            .validBlock(MicrochipBlocks.CHIP_BUILDER)
            .register();

    public static void register() { }
}
