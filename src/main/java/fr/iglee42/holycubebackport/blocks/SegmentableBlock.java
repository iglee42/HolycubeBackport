package fr.iglee42.holycubebackport.blocks;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface SegmentableBlock {
    int MIN_SEGMENT = 1;
    int MAX_SEGMENT = 4;
    IntegerProperty AMOUNT = IntegerProperty.create("segment_amount", 1, 4);

    static final BiFunction<Direction, Integer, VoxelShape> SHAPE_BY_PROPERTIES = Util.memoize(
            (p_296142_, p_294775_) -> {
                VoxelShape[] avoxelshape = new VoxelShape[]{
                        Block.box(8.0, 0.0, 8.0, 16.0, 3.0, 16.0),
                        Block.box(8.0, 0.0, 0.0, 16.0, 3.0, 8.0),
                        Block.box(0.0, 0.0, 0.0, 8.0, 3.0, 8.0),
                        Block.box(0.0, 0.0, 8.0, 8.0, 3.0, 16.0)
                };
                VoxelShape voxelshape = Shapes.empty();

                for (int i = 0; i < p_294775_; i++) {
                    int j = Math.floorMod(i - p_296142_.get2DDataValue(), 4);
                    voxelshape = Shapes.or(voxelshape, avoxelshape[j]);
                }

                return voxelshape.singleEncompassing();
            }
    );


    default IntegerProperty getSegmentAmountProperty() {
        return AMOUNT;
    }

    default double getShapeHeight() {
        return 1.0;
    }

    default boolean canBeReplaced(BlockState p_394221_, BlockPlaceContext p_394124_, IntegerProperty p_394071_) {
        return !p_394124_.isSecondaryUseActive() && p_394124_.getItemInHand().is(p_394221_.getBlock().asItem()) && p_394221_.getValue(p_394071_) < 4;
    }

    default BlockState getStateForPlacement(BlockPlaceContext p_394035_, Block p_393593_, IntegerProperty p_394488_, EnumProperty<Direction> p_393563_) {
        BlockState blockstate = p_394035_.getLevel().getBlockState(p_394035_.getClickedPos());
        return blockstate.is(p_393593_)
            ? blockstate.setValue(p_394488_, Math.min(4, blockstate.getValue(p_394488_) + 1))
            : p_393593_.defaultBlockState().setValue(p_393563_, p_394035_.getHorizontalDirection().getOpposite());
    }
}
