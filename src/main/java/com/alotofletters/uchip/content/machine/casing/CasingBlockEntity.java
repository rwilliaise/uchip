package com.alotofletters.uchip.content.machine.casing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CasingBlockEntity extends BlockEntity {
    protected LazyOptional<IItemHandler> itemCapability;
    protected LazyOptional<IEnergyStorage> energyCapability;

    protected ItemStackHandler inventory;

    public CasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        inventory = new ItemStackHandler(1) {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };

        itemCapability = LazyOptional.of(() -> inventory);
    }

//    @Override
//    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
//        if (cap == ForgeCapabilities.ENERGY && side == getBlockState().getValue(CasingBlock.FACING).getOpposite()) {
//            return itemCapability.cast();
//        }
//        return super.getCapability(cap, side);
//    }
}
