package com.alotofletters.uchip;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(Microchip.MOD_ID)
public class Microchip {
    public static final String MOD_ID = "uchip";

    public static final CreativeModeTab TAB = new CreativeModeTab("uchip") {
        @Override
        public ItemStack makeIcon() {
            return MicrochipItems.PROCESSOR_6502.asStack();
        }
    };

    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() ->
            Registrate.create(MOD_ID)
                    .creativeModeTab(() -> TAB)
                    .addDataGenerator(ProviderType.LANG, (prov) -> prov.add(TAB, "Microchips"))
    );

    public Microchip() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.register(new MicrochipClient()));

        MicrochipItems.register();
        MicrochipLang.register();
        MicrochipBlocks.register();
        MicrochipBlockEntities.register();
        MicrochipMenuTypes.register();
        MicrochipEntityTypes.register();
    }

    public static ResourceLocation location(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

}
