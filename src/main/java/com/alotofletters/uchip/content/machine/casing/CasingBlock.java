package com.alotofletters.uchip.content.machine.casing;

import com.alotofletters.uchip.MicrochipBlockEntities;
import com.alotofletters.uchip.MicrochipBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class CasingBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final BooleanProperty HAS_BOARD = MicrochipBlockStateProperties.HAS_BOARD;
    public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;

    public CasingBlock(Properties prop) {
        super(prop);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HAS_BOARD, false)
                .setValue(ENABLED, false));
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
