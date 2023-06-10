package com.alotofletters.uchip.content.machine.casing;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CasingBlockEntity extends BlockEntity {
    private LazyOptional<IEnergyStorage> energyCapability;
    private ItemStack board = ItemStack.EMPTY;

    private EnergyStorage buffer;

    public CasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        buffer = new EnergyStorage(40000);
        energyCapability = LazyOptional.of(() -> buffer);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Board")) {
            board = ItemStack.of(tag.getCompound("BoardItem"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        if (!this.board.isEmpty()) {
            tag.put("BoardItem", board.save(new CompoundTag()));
        }
    }

    public void setBoard(ItemStack board) {
        this.board = board;
        this.setChanged();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY && side == getBlockState().getValue(CasingBlock.FACING).getOpposite()) {
            return energyCapability.cast();
        }
        return super.getCapability(cap, side);
    }
}
