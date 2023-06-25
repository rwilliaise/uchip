package com.alotofletters.uchip;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.alotofletters.uchip.content.drone.DroneEntity;
import com.tterrag.registrate.Registrate;

@Mod(Microchip.MOD_ID)
public class Microchip
{
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
		MinecraftForge.EVENT_BUS.register(this);

        MicrochipItems.register();
        MicrochipLang.register();
        MicrochipBlocks.register();
        MicrochipBlockEntities.register();
        MicrochipMenuTypes.register();
		MicrochipEntities.register();
    }

	@SubscribeEvent
	public void entityInteract(EntityInteract event) {
	}

    public static ResourceLocation location(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

}
