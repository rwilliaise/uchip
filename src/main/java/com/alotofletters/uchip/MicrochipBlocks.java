package com.alotofletters.uchip;

import com.alotofletters.uchip.content.machine.casing.CasingBlock;
import com.alotofletters.uchip.content.machine.chip_builder.ChipBuilderBlock;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ModelFile;

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
            .transform(b -> b.tag(BlockTags.MINEABLE_WITH_PICKAXE))
            .simpleItem()
            .register();

    public static final BlockEntry<CasingBlock> CASING = REGISTRATE
            .block("casing", CasingBlock::new)
            .blockstate((ctx, prov) -> prov.horizontalBlock(ctx.get(), state -> {
                if (state.getValue(BlockStateProperties.ENABLED))
                    return existingModel(prov, "block/casing/enabled");
                else if (state.getValue(CasingBlock.HAS_BOARD))
                    return existingModel(prov, "block/casing/board");
                return existingModel(prov, "block/casing/empty");
            }))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .item()
            .model((ctx, prov) -> prov.withExistingParent("casing", prov.modLoc("block/casing/board")))
            .build()
            .register();


    private static ModelFile.ExistingModelFile existingModel(RegistrateBlockstateProvider prov, String location) {
        return prov.models().getExistingFile(prov.modLoc(location));
    }

    public static void register() { }
}
