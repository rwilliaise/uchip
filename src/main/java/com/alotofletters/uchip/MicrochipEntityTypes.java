package com.alotofletters.uchip;

import com.alotofletters.uchip.content.drone.Drone;
import com.alotofletters.uchip.content.drone.DroneRenderer;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.EntityEntry;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class MicrochipEntityTypes {

    private static final Registrate REGISTRATE = Microchip.REGISTRATE.get();

    public static final EntityEntry<Drone> DRONE = REGISTRATE.entity("drone", Drone::new, MobCategory.CREATURE)
            .attributes(Drone::createAttributes)
            .properties(b -> b.sized(0.8f, 0.2f))
            .renderer(() -> DroneRenderer::new)
            .loot((tables, type) -> tables.add(type, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(MicrochipItems.DRONE.get())))))
            .register();

    public static void register() {
    }
}

