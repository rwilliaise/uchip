package com.alotofletters.uchip.content.drone;

import com.alotofletters.uchip.content.machine.shell.Shell;
import com.alotofletters.uchip.content.machine.shell.ShellType;
import com.alotofletters.uchip.core.board.Board;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class Drone extends PathfinderMob implements Shell {
    private final ItemStackHandler handler = new ItemStackHandler(1);
    private Board board;

    public Drone(EntityType<Drone> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this, 10, true); // TODO
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.put("Board", handler.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("Board", Tag.TAG_COMPOUND)) {
            this.handler.deserializeNBT(pCompound.getCompound("Board"));
        }
    }

    protected @NotNull PathNavigation createNavigation(@NotNull Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanPassDoors(true);
        flyingpathnavigation.setCanFloat(true);
        return flyingpathnavigation;
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (!level.isClientSide && pPlayer instanceof ServerPlayer player) {
            this.openScreen(player);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        spawnAtLocation(handler.extractItem(0, 1, false));
    }

    public void setBoard(ItemStack stack) {
        handler.setStackInSlot(0, stack);
        this.board = new Board(stack);
    }

    @Override
    protected int calculateFallDamage(float pFallDistance, float pDamageMultiplier) {
        return 0;
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    public ShellType getShellType() {
        return ShellType.DRONE;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0F)
                .add(Attributes.FLYING_SPEED, 1.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.6F);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.getId());
    }
}

