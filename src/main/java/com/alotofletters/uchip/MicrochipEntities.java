package com.alotofletters.uchip;

import com.alotofletters.uchip.content.drone.DroneEntity;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.EntityEntry;

import net.minecraft.world.entity.MobCategory;

public class MicrochipEntities {

	private static final Registrate REGISTRATE = Microchip.REGISTRATE.get();

	public static final EntityEntry<DroneEntity> DRONE = REGISTRATE.entity("drone", DroneEntity::new, MobCategory.MISC)
		.register();

	public static void register() {}
}

