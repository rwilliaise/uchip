package com.alotofletters.uchip.content.drone;

import com.alotofletters.uchip.MicrochipEntityTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class DroneItem extends Item {

    public DroneItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        // TODO: open drone screen
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Vec3 loc = pContext.getClickLocation();
        ItemStack stack = pContext.getItemInHand();
        Drone entity = Objects.requireNonNull(MicrochipEntityTypes.DRONE.create(level));
        entity.setPos(loc);
        level.addFreshEntity(entity);
        stack.shrink(1);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
