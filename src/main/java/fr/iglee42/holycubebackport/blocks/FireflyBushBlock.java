package fr.iglee42.holycubebackport.blocks;

import com.mojang.serialization.MapCodec;
import fr.iglee42.holycubebackport.BonemealUtils;
import fr.iglee42.holycubebackport.HolyBackParticleTypes;
import fr.iglee42.holycubebackport.HolyBackSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public class FireflyBushBlock extends BushBlock implements BonemealableBlock {
    private static final double FIREFLY_CHANCE_PER_TICK = 0.7;
    private static final double FIREFLY_HORIZONTAL_RANGE = (double)10.0F;
    private static final double FIREFLY_VERTICAL_RANGE = (double)5.0F;
    private static final int FIREFLY_SPAWN_MAX_BRIGHTNESS_LEVEL = 13;
    private static final int FIREFLY_AMBIENT_SOUND_CHANCE_ONE_IN = 30;
    public static final MapCodec<FireflyBushBlock> CODEC = simpleCodec(FireflyBushBlock::new);

    public FireflyBushBlock(BlockBehaviour.Properties p_401237_) {
        super(p_401237_);
    }

    protected MapCodec<? extends FireflyBushBlock> codec() {
        return CODEC;
    }

    public void animateTick(BlockState p_401358_, Level p_401092_, BlockPos p_401110_, RandomSource p_401309_) {
        if (p_401309_.nextInt(30) == 0 && isMoonVisible(p_401092_) && p_401092_.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, p_401110_.getX(),p_401110_.getZ()) <= p_401110_.getY()) {
            p_401092_.playLocalSound(p_401110_, HolyBackSounds.FIREFLY_BUSH_IDLE, SoundSource.AMBIENT, 1.0F, 1.0F, false);
        }

        if (p_401092_.getLightEngine().getLayerListener(LightLayer.BLOCK).getLightValue(p_401110_) <= 13 && p_401309_.nextDouble() <= 0.7) {
            double d0 = (double)p_401110_.getX() + p_401309_.nextDouble() * (double)10.0F - (double)5.0F;
            double d1 = (double)p_401110_.getY() + p_401309_.nextDouble() * (double)5.0F;
            double d2 = (double)p_401110_.getZ() + p_401309_.nextDouble() * (double)10.0F - (double)5.0F;
            p_401092_.addParticle(HolyBackParticleTypes.FIREFLY, d0, d1, d2, (double)0.0F, (double)0.0F, (double)0.0F);
        }

    }

    public boolean isValidBonemealTarget(LevelReader p_401352_, BlockPos p_401332_, BlockState p_401436_) {
        return BonemealUtils.hasSpreadableNeighbourPos(p_401352_, p_401332_, p_401436_);
    }

    public boolean isBonemealSuccess(Level p_401120_, RandomSource p_401417_, BlockPos p_401298_, BlockState p_401423_) {
        return true;
    }

    public void performBonemeal(ServerLevel p_401415_, RandomSource p_401124_, BlockPos p_401112_, BlockState p_401260_) {
        BonemealUtils.findSpreadableNeighbourPos(p_401415_, p_401112_, p_401260_).ifPresent((p_405685_) -> p_401415_.setBlockAndUpdate(p_405685_, this.defaultBlockState()));
    }

    private boolean isMoonVisible(Level level) {
        if (!level.dimensionType().natural()) {
            return false;
        } else {
            int i = (int)(level.getDayTime() % 24000L);
            return i >= 12600 && i <= 23400;
        }
    }
}
