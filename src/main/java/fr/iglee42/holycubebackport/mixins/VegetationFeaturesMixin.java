package fr.iglee42.holycubebackport.mixins;

import fr.iglee42.holycubebackport.HolyBackBlocks;
import fr.iglee42.holycubebackport.blocks.LeafLitterBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static fr.iglee42.holycubebackport.HolyBackVegetationPlacements.*;

@Mixin(VegetationFeatures.class)
public abstract class VegetationFeaturesMixin {

    @Shadow
    protected static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
        return null;
    }

    @Inject(method = "bootstrap",at = @At("TAIL"),locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void holyback$firefly(BootstrapContext<ConfiguredFeature<?, ?>> p_321761_, CallbackInfo ci, HolderGetter holdergetter, Holder holder, Holder holder1, Holder holder2, Holder holder3, Holder holder4, HolderGetter holdergetter1, Holder holder5, Holder holder6, Holder holder7, Holder holder8, Holder holder9, Holder holder10, Holder holder11, Holder holder12, Holder holder13, Holder holder14, Holder holder15, Holder holder16, Holder holder17, Holder holder18, Holder holder19, Holder holder20, Holder holder21, Holder holder22, Holder holder23, Holder holder24, Holder holder25, Holder holder26, Holder holder27, Holder holder28, Holder holder29, SimpleWeightedRandomList.Builder builder){
        FeatureUtils.register(
                p_321761_,
                PATCH_FIREFLY_BUSH,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        20, 4, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(HolyBackBlocks.FIREFLY_BUSH)))
                )
        );
        FeatureUtils.register(
                p_321761_,
                PATCH_DRY_GRASS,
                Feature.RANDOM_PATCH,
                grassPatch(
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder().add(HolyBackBlocks.SHORT_DRY_GRASS.defaultBlockState(), 1).add(HolyBackBlocks.TALL_DRY_GRASS.defaultBlockState(), 1)
                        ),
                        64
                )
        );

        FeatureUtils.register(
                p_321761_,
                PATCH_LEAF_LITTER,
                Feature.RANDOM_PATCH,
                FeatureUtils.simpleRandomPatchConfiguration(
                        32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(holycubePaleOak$leafLitterPatchBuilder(1, 3))))
                )
        );

        FeatureUtils.register(
                p_321761_,
                CONFIGURED_WILDFLOWERS_BIRCH_FOREST,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        64,
                        6,
                        2,
                        PlacementUtils.onlyWhenEmpty(
                                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(holycubeBackport$flowerBedPatchBuilder(HolyBackBlocks.WILDFLOWERS)))
                        )
                )
        );
        FeatureUtils.register(
                p_321761_,
                CONFIGURED_WILDFLOWERS_MEADOW,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        8,
                        6,
                        2,
                        PlacementUtils.onlyWhenEmpty(
                                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(holycubeBackport$flowerBedPatchBuilder(HolyBackBlocks.WILDFLOWERS)))
                        )
                )
        );
    }

    @Unique
    private static SimpleWeightedRandomList.Builder<BlockState> holycubePaleOak$leafLitterPatchBuilder(int p_394377_, int p_393842_) {
        return holycubePaleOak$segmentedBlockPatchBuilder(HolyBackBlocks.LEAF_LITTER, p_394377_, p_393842_, LeafLitterBlock.AMOUNT, LeafLitterBlock.FACING);
    }

    @Unique
    private static SimpleWeightedRandomList.Builder<BlockState> holycubePaleOak$segmentedBlockPatchBuilder(
            Block p_394669_, int p_394218_, int p_393622_, IntegerProperty p_393893_, EnumProperty<Direction> p_394193_
    ) {
        SimpleWeightedRandomList.Builder<BlockState> builder = SimpleWeightedRandomList.builder();

        for (int i = p_394218_; i <= p_393622_; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                builder.add(p_394669_.defaultBlockState().setValue(p_393893_, i).setValue(p_394193_, direction), 1);
            }
        }

        return builder;
    }

    @Unique
    private static SimpleWeightedRandomList.Builder<BlockState> holycubeBackport$flowerBedPatchBuilder(Block p_393966_) {
        return holycubePaleOak$segmentedBlockPatchBuilder(p_393966_, 1, 4, PinkPetalsBlock.AMOUNT, PinkPetalsBlock.FACING);
    }
}
