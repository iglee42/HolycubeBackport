package fr.iglee42.holycubebackport.blocks;

import com.mojang.serialization.MapCodec;
import fr.iglee42.holycubebackport.BonemealUtils;
import fr.iglee42.holycubebackport.Boxes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BushBlock extends net.minecraft.world.level.block.BushBlock implements BonemealableBlock {
    public static final MapCodec<BushBlock> CODEC = simpleCodec(BushBlock::new);
    private static final VoxelShape SHAPE = Boxes.column(16.0, 0.0, 13.0);

    @Override
    public MapCodec<BushBlock> codec() {
        return CODEC;
    }

    public BushBlock(BlockBehaviour.Properties p_51021_) {
        super(p_51021_);
    }

    @Override
    protected VoxelShape getShape(BlockState p_401432_, BlockGetter p_401175_, BlockPos p_401162_, CollisionContext p_401402_) {
        return SHAPE;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader p_401250_, BlockPos p_401361_, BlockState p_401191_) {
        return BonemealUtils.hasSpreadableNeighbourPos(p_401250_, p_401361_, p_401191_);
    }

    @Override
    public boolean isBonemealSuccess(Level p_401200_, RandomSource p_401387_, BlockPos p_401374_, BlockState p_401380_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel p_401091_, RandomSource p_401012_, BlockPos p_401218_, BlockState p_401130_) {
        BonemealUtils.findSpreadableNeighbourPos(p_401091_, p_401218_, p_401130_)
            .ifPresent(p_401015_ -> p_401091_.setBlockAndUpdate(p_401015_, this.defaultBlockState()));
    }
}
