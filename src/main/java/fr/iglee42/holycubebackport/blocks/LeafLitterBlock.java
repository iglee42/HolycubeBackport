package fr.iglee42.holycubebackport.blocks;

import com.mojang.serialization.MapCodec;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LeafLitterBlock extends BushBlock implements SegmentableBlock {
    public static final MapCodec<LeafLitterBlock> CODEC = simpleCodec(LeafLitterBlock::new);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public LeafLitterBlock(BlockBehaviour.Properties p_393543_) {
        super(p_393543_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(this.getSegmentAmountProperty(), 1));
    }

    @Override
    protected MapCodec<LeafLitterBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState rotate(BlockState p_394056_, Rotation p_394157_) {
        return p_394056_.setValue(FACING, p_394157_.rotate(p_394056_.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState p_393478_, Mirror p_393616_) {
        return p_393478_.rotate(p_393616_.getRotation(p_393478_.getValue(FACING)));
    }

    @Override
    public boolean canBeReplaced(BlockState p_394494_, BlockPlaceContext p_393674_) {
        return this.canBeReplaced(p_394494_, p_393674_, this.getSegmentAmountProperty()) ? true : super.canBeReplaced(p_394494_, p_393674_);
    }

    @Override
    protected boolean canSurvive(BlockState p_401850_, LevelReader p_401768_, BlockPos p_401902_) {
        BlockPos blockpos = p_401902_.below();
        return p_401768_.getBlockState(blockpos).isFaceSturdy(p_401768_, blockpos, Direction.UP);
    }

    @Override
    public VoxelShape getShape(BlockState p_393565_, BlockGetter p_393799_, BlockPos p_394123_, CollisionContext p_393894_) {
        return SHAPE_BY_PROPERTIES.apply(p_393565_.getValue(FACING),p_393565_.getValue(AMOUNT));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_394214_) {
        return this.getStateForPlacement(p_394214_, this, this.getSegmentAmountProperty(), FACING);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_393852_) {
        p_393852_.add(FACING, this.getSegmentAmountProperty());
    }
}
