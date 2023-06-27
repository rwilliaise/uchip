package com.alotofletters.uchip.content.machine.casing;

import com.alotofletters.uchip.MicrochipBlocks;
import com.alotofletters.uchip.content.board.BoardItem;
import com.alotofletters.uchip.content.board.shell.Shell;
import com.alotofletters.uchip.content.board.shell.ShellType;
import com.alotofletters.uchip.foundation.board.Board;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.ItemStackHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CasingBlockEntity extends BlockEntity implements Clearable, Shell {
    private final LazyOptional<IEnergyStorage> energyCapability;
    private ItemStack stack = ItemStack.EMPTY;
    private Board runningBoard;

    private final EnergyStorage buffer;

    public CasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        buffer = new EnergyStorage(40000);
        energyCapability = LazyOptional.of(() -> buffer);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Board")) {
            setBoard(ItemStack.of(tag.getCompound("Board")));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        if (runningBoard != null) {
            CompoundTag out = new CompoundTag();
            runningBoard.save(out);
            stack.setTag(out);
            tag.put("Board", stack.save(new CompoundTag()));
        }
    }

    @Override
    public void clearContent() {
        runningBoard = null;
        setChanged();
    }

    public void setBoard(ItemStack board) {
        if (level.isClientSide) return;
        if (board.isEmpty() || board.getItem() instanceof BoardItem) {
            level.setBlock(getBlockPos(), getBlockState().setValue(CasingBlock.HAS_BOARD, !board.isEmpty()), 4);
            if (!stack.isEmpty()) {
                BlockPos pos = getBlockPos();
                level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack));
            }
            stack = board;
            if (!board.isEmpty()) {
                stack.setCount(1);
                runningBoard = new Board(board);
            }
            setChanged();
        }
    }

    public ItemStack getBoard() {
        return stack;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY && side == getBlockState().getValue(CasingBlock.FACING).getOpposite()) {
            return energyCapability.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public ShellType getShellType() {
        return ShellType.CASING;
    }

    @Override
    public Component getDisplayName() {
        return MicrochipBlocks.CASING.get()
                .getName();
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(getBlockPos());
    }
}
