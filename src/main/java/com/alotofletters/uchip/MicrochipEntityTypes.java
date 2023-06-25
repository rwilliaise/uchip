package com.alotofletters.uchip;

import com.alotofletters.uchip.content.drone.DroneEntity;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.EntityEntry;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public class MicrochipEntityTypes {

	private static final Registrate REGISTRATE = Microchip.REGISTRATE.get();

	public static final EntityEntry<DroneEntity> DRONE = REGISTRATE.entity("drone", DroneEntity::new, MobCategory.MISC)
		.attributes(() -> AttributeSupplier.builder()
				.add(MAX_HEALTH, 1))
		.register();

	public static void register() {}
}

