package com.alotofletters.uchip.content.machine.casing;

import com.alotofletters.uchip.content.board.BoardItem;
import com.alotofletters.uchip.foundation.board.Board;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Clearable;
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

public class CasingBlockEntity extends BlockEntity implements Clearable {
    private LazyOptional<IEnergyStorage> energyCapability;
    private Board runningBoard;

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
			runningBoard = Board.of(tag.getCompound("Board"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        if (runningBoard != null) {
			CompoundTag out = new CompoundTag();
			runningBoard.save(out);
            tag.put("Board", out);
        }
    }

    @Override
    public void clearContent() {
		runningBoard = null;
        setChanged();
    }

    public void setBoard(ItemStack board) {
        if (board.isEmpty() || board.getItem() instanceof BoardItem) {
            level.setBlock(getBlockPos(), getBlockState().setValue(CasingBlock.HAS_BOARD, !board.isEmpty()), 4);
            if (board.getItem() instanceof BoardItem item) {
                runningBoard = item.createBoard(board);
            } else {
				runningBoard = null;
			}
            setChanged();
        }
    }

    public ItemStack getBoard() {
		if (runningBoard == null) return ItemStack.EMPTY;
        return runningBoard.stack;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY && side == getBlockState().getValue(CasingBlock.FACING).getOpposite()) {
            return energyCapability.cast();
        }
        return super.getCapability(cap, side);
    }
}
