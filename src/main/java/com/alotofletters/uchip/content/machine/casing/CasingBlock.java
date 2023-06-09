package com.alotofletters.uchip.content.machine.casing;

import com.alotofletters.uchip.MicrochipBlockEntities;
import com.alotofletters.uchip.MicrochipBlockStateProperties;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CasingBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final BooleanProperty HAS_BOARD = MicrochipBlockStateProperties.HAS_BOARD;
    public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;

    private static final VoxelShape X_AXIS_AABB = Block.box(0, 0, 4, 16, 16, 12);

    private static final VoxelShape Z_AXIS_AABB = Block.box(4, 0, 0, 12, 16, 16);

    public CasingBlock(Properties prop) {
        super(prop);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HAS_BOARD, false)
                .setValue(ENABLED, false));
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        if (p_60555_.getValue(FACING).getAxis() == Direction.Axis.X) {
            return X_AXIS_AABB;
        } else {
            return Z_AXIS_AABB;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
        return this.defaultBlockState().setValue(FACING, p_49820_.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING);
        p_49915_.add(HAS_BOARD);
        p_49915_.add(ENABLED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return MicrochipBlockEntities.CASING.create(pos, state);
    }
}
