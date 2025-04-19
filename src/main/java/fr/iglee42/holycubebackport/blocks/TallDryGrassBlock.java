package fr.iglee42.holycubebackport.blocks;

import com.mojang.serialization.MapCodec;
import fr.iglee42.holycubebackport.BonemealUtils;
import fr.iglee42.holycubebackport.Boxes;
import fr.iglee42.holycubebackport.HolyBackBlocks;
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

public class TallDryGrassBlock extends DryVegetationBlock implements BonemealableBlock {
    public static final MapCodec<TallDryGrassBlock> CODEC = simpleCodec(TallDryGrassBlock::new);
    private static final VoxelShape SHAPE = Boxes.column(14.0, 0.0, 16.0);

    @Override
    public MapCodec<TallDryGrassBlock> codec() {
        return CODEC;
    }

    public TallDryGrassBlock(BlockBehaviour.Properties p_401761_) {
        super(p_401761_);
    }

    @Override
    protected VoxelShape getShape(BlockState p_401857_, BlockGetter p_401866_, BlockPos p_401940_, CollisionContext p_401832_) {
        return SHAPE;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader p_401899_, BlockPos p_401858_, BlockState p_401909_) {
        return BonemealUtils.hasSpreadableNeighbourPos(p_401899_, p_401858_, HolyBackBlocks.SHORT_DRY_GRASS.defaultBlockState());
    }

    @Override
    public boolean isBonemealSuccess(Level p_401931_, RandomSource p_401815_, BlockPos p_401808_, BlockState p_401935_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel p_401804_, RandomSource p_401769_, BlockPos p_401777_, BlockState p_401790_) {
        BonemealUtils.findSpreadableNeighbourPos(p_401804_, p_401777_, HolyBackBlocks.SHORT_DRY_GRASS.defaultBlockState())
            .ifPresent(p_401829_ -> p_401804_.setBlockAndUpdate(p_401829_, HolyBackBlocks.SHORT_DRY_GRASS.defaultBlockState()));
    }
}
