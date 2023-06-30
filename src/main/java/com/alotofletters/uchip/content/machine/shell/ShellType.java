package com.alotofletters.uchip.content.machine.shell;

import java.util.function.BiFunction;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import com.alotofletters.uchip.content.drone.Drone;
import com.alotofletters.uchip.content.machine.casing.CasingBlockEntity;

public enum ShellType {
	CASING(ShellType::readCasing),
	DRONE(ShellType::readDrone);

    public final BiFunction<Level, FriendlyByteBuf, Shell> getShell;

	private ShellType(BiFunction<Level, FriendlyByteBuf, Shell> getShell) {
        this.getShell = getShell;
    }

    private static Shell readDrone(Level level, FriendlyByteBuf buf) {
        return (Drone) level.getEntity(buf.readInt());
    }

    private static Shell readCasing(Level level, FriendlyByteBuf buf) {
        return (CasingBlockEntity) level.getBlockEntity(buf.readBlockPos());
    }
}
