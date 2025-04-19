package fr.iglee42.holycubebackport.blocks;

import com.mojang.serialization.MapCodec;
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

public class ShortDryGrassBlock extends DryVegetationBlock implements BonemealableBlock {
    public static final MapCodec<ShortDryGrassBlock> CODEC = simpleCodec(ShortDryGrassBlock::new);
    private static final VoxelShape SHAPE = Boxes.column(12.0, 0.0, 10.0);

    @Override
    public MapCodec<ShortDryGrassBlock> codec() {
        return CODEC;
    }

    public ShortDryGrassBlock(BlockBehaviour.Properties p_401946_) {
        super(p_401946_);
    }

    @Override
    protected VoxelShape getShape(BlockState p_401780_, BlockGetter p_401807_, BlockPos p_401895_, CollisionContext p_401802_) {
        return SHAPE;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader p_401814_, BlockPos p_401760_, BlockState p_401924_) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level p_401806_, RandomSource p_401772_, BlockPos p_401791_, BlockState p_401942_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel p_401950_, RandomSource p_401831_, BlockPos p_401948_, BlockState p_401868_) {
        p_401950_.setBlockAndUpdate(p_401948_, HolyBackBlocks.TALL_DRY_GRASS.defaultBlockState());
    }
}
