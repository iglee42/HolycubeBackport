package fr.iglee42.holycubepaleoak;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;

public class PaleMossDecorator extends TreeDecorator {
    public static final MapCodec<PaleMossDecorator> CODEC = RecordCodecBuilder.mapCodec(
        p_379602_ -> p_379602_.group(
                    Codec.floatRange(0.0F, 1.0F).fieldOf("leaves_probability").forGetter(p_379568_ -> p_379568_.leavesProbability),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("trunk_probability").forGetter(p_379338_ -> p_379338_.trunkProbability),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("ground_probability").forGetter(p_379346_ -> p_379346_.groundProbability)
                )
                .apply(p_379602_, PaleMossDecorator::new)
    );
    private final float leavesProbability;
    private final float trunkProbability;
    private final float groundProbability;

    @Override
    protected TreeDecoratorType<?> type() {
        return HolycubePaleOak.PALE_MOSS_DECORATOR;
    }

    public PaleMossDecorator(float p_379816_, float p_379583_, float p_380313_) {
        this.leavesProbability = p_379816_;
        this.trunkProbability = p_379583_;
        this.groundProbability = p_380313_;
    }

    @Override
    public void place(TreeDecorator.Context p_379317_) {
        RandomSource randomsource = p_379317_.random();
        WorldGenLevel worldgenlevel = (WorldGenLevel)p_379317_.level();
        List<BlockPos> list = Util.shuffledCopy(p_379317_.logs(), randomsource);
        if (!list.isEmpty()) {
            Mutable<BlockPos> mutable = new MutableObject<>(list.getFirst());
            list.forEach(p_379657_ -> {
                if (p_379657_.getY() < mutable.getValue().getY()) {
                    mutable.setValue(p_379657_);
                }
            });
            BlockPos blockpos = mutable.getValue();
            if (randomsource.nextFloat() < this.groundProbability) {
                worldgenlevel.registryAccess()
                    .lookup(Registries.CONFIGURED_FEATURE)
                    .flatMap(p_382782_ -> p_382782_.get(HolycubePaleOak.PALE_MOSS_PATCH))
                    .ifPresent(
                        p_380064_ -> p_380064_.value()
                                .place(worldgenlevel, worldgenlevel.getLevel().getChunkSource().getGenerator(), randomsource, blockpos.above())
                    );
            }

            p_379317_.logs().forEach(p_382781_ -> {
                if (randomsource.nextFloat() < this.trunkProbability) {
                    BlockPos blockpos1 = p_382781_.below();
                    if (p_379317_.isAir(blockpos1)) {
                        addMossHanger(blockpos1, p_379317_);
                    }
                }
            });
            p_379317_.leaves().forEach(p_380012_ -> {
                if (randomsource.nextFloat() < this.leavesProbability) {
                    BlockPos blockpos1 = p_380012_.below();
                    if (p_379317_.isAir(blockpos1)) {
                        addMossHanger(blockpos1, p_379317_);
                    }
                }
            });
        }
    }

    private static void addMossHanger(BlockPos p_379694_, TreeDecorator.Context p_379973_) {
        while (p_379973_.isAir(p_379694_.below()) && !((double)p_379973_.random().nextFloat() < 0.5)) {
            p_379973_.setBlock(p_379694_, HolyPOBlocks.PALE_HANGING_MOSS.defaultBlockState().setValue(HangingMossBlock.TIP, Boolean.valueOf(false)));
            p_379694_ = p_379694_.below();
        }

        p_379973_.setBlock(p_379694_, HolyPOBlocks.PALE_HANGING_MOSS.defaultBlockState().setValue(HangingMossBlock.TIP, Boolean.valueOf(true)));
    }
}
