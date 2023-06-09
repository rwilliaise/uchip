package com.alotofletters.uchip;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import com.tterrag.registrate.Registrate;

@Mod(Microchip.MOD_ID)
public class Microchip
{
    public static final String MOD_ID = "uchip";

    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() ->
            Registrate.create(MOD_ID)
                    .creativeModeTab(() -> new CreativeModeTab("uchip") {
                        @Override
                        public ItemStack makeIcon() {
                            return MicrochipItems.PROCESSOR_6502.asStack();
                        }
                    })
    );

    public Microchip()
    {
        MicrochipItems.register();
        MicrochipLangPartials.register();
        MicrochipBlocks.register();
        MicrochipBlockEntities.register();
        MicrochipScreens.register();
    }

}
