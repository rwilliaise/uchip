package com.alotofletters.uchip;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.common.Mod;
import com.tterrag.registrate.Registrate;

import java.util.function.Supplier;

@Mod(Microchip.MOD_ID)
public class Microchip
{
    public static final String MOD_ID = "uchip";

    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MOD_ID));

    public Microchip()
    {
        MicrochipItems.register();
    }

}
