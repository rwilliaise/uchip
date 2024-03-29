package com.alotofletters.uchip.content.machine.chip_builder;

import com.alotofletters.uchip.MicrochipBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChipBuilderBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public ChipBuilderBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
        return this.defaultBlockState().setValue(FACING, p_49820_.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return MicrochipBlockEntities.CHIP_BUILDER.create(pos, state);
    }
}
