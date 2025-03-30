package fr.iglee42.holycubepaleoak;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BonemealableBlock.Type;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class BonemealableFeaturePlacerBlock extends Block implements BonemealableBlock {
    public static final MapCodec<BonemealableFeaturePlacerBlock> CODEC = RecordCodecBuilder.mapCodec((p_380194_) -> p_380194_.group(ResourceKey.codec(Registries.CONFIGURED_FEATURE).fieldOf("feature").forGetter((p_380141_) -> p_380141_.feature), propertiesCodec()).apply(p_380194_, BonemealableFeaturePlacerBlock::new));
    private final ResourceKey<ConfiguredFeature<?, ?>> feature;

    public MapCodec<BonemealableFeaturePlacerBlock> codec() {
        return CODEC;
    }

    public BonemealableFeaturePlacerBlock(ResourceKey<ConfiguredFeature<?, ?>> p_379459_, BlockBehaviour.Properties p_380054_) {
        super(p_380054_);
        this.feature = p_379459_;
    }

    public boolean isValidBonemealTarget(LevelReader p_379414_, BlockPos p_379932_, BlockState p_379449_) {
        return p_379414_.getBlockState(p_379932_.above()).isAir();
    }

    public boolean isBonemealSuccess(Level p_379855_, RandomSource p_380339_, BlockPos p_379438_, BlockState p_379693_) {
        return true;
    }

    public void performBonemeal(ServerLevel p_380244_, RandomSource p_379395_, BlockPos p_380245_, BlockState p_379519_) {
        p_380244_.registryAccess().lookup(Registries.CONFIGURED_FEATURE).flatMap((p_379951_) -> p_379951_.get(this.feature)).ifPresent((p_380225_) -> ((ConfiguredFeature)p_380225_.value()).place(p_380244_, p_380244_.getChunkSource().getGenerator(), p_379395_, p_380245_.above()));
    }

    public BonemealableBlock.Type getType() {
        return Type.NEIGHBOR_SPREADER;
    }
}
