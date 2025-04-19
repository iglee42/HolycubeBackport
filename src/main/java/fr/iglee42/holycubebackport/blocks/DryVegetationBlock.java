package fr.iglee42.holycubebackport.blocks;

import com.mojang.serialization.MapCodec;
import fr.iglee42.holycubebackport.Boxes;
import fr.iglee42.holycubebackport.HolyBackSounds;
import fr.iglee42.holycubebackport.HolycubeBackport;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DryVegetationBlock extends net.minecraft.world.level.block.BushBlock {
    public static final MapCodec<DryVegetationBlock> CODEC = simpleCodec(DryVegetationBlock::new);
    private static final VoxelShape SHAPE = Boxes.column(12.0, 0.0, 13.0);
    private static final int IDLE_SOUND_CHANCE = 150;
    private static final int IDLE_SOUND_BADLANDS_DECREASED_CHANCE = 5;

    @Override
    public MapCodec<? extends DryVegetationBlock> codec() {
        return CODEC;
    }

    public DryVegetationBlock(BlockBehaviour.Properties p_401864_) {
        super(p_401864_);
    }

    @Override
    protected VoxelShape getShape(BlockState p_401767_, BlockGetter p_401764_, BlockPos p_401758_, CollisionContext p_401896_) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState p_401820_, BlockGetter p_401945_, BlockPos p_401852_) {
        return p_401820_.is(HolycubeBackport.DRY_VEGETATION_MAY_PLACE_ON);
    }

    @Override
    public void animateTick(BlockState p_401875_, Level p_401809_, BlockPos p_401789_, RandomSource p_401918_) {
        if (p_401918_.nextInt(150) == 0) {
            BlockState blockstate = p_401809_.getBlockState(p_401789_.below());
            if ((blockstate.is(Blocks.RED_SAND) || blockstate.is(BlockTags.TERRACOTTA)) && p_401918_.nextInt(5) != 0) {
                return;
            }

            BlockState blockstate1 = p_401809_.getBlockState(p_401789_.below(2));
            if (blockstate.is(HolycubeBackport.PLAYS_AMBIENT_DESERT_BLOCK_SOUNDS) && blockstate1.is(HolycubeBackport.PLAYS_AMBIENT_DESERT_BLOCK_SOUNDS)) {
                p_401809_.playLocalSound(
                    p_401789_.getX(), p_401789_.getY(), p_401789_.getZ(), HolyBackSounds.DEAD_BUSH_IDLE, SoundSource.AMBIENT, 1.0F, 1.0F, false
                );
            }
        }
    }
}
