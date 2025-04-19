package fr.iglee42.holycubebackport.mixins;

import com.google.common.collect.ImmutableList;
import fr.iglee42.holycubebackport.HolyBackBlocks;
import fr.iglee42.holycubebackport.PlaceOnGroundDecorator;
import fr.iglee42.holycubebackport.blocks.LeafLitterBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.OptionalInt;

import static fr.iglee42.holycubebackport.HolyBackVegetationPlacements.*;

@Mixin(TreeFeatures.class)
public abstract class TreeFeaturesMixin {

    @Shadow
    protected static TreeConfiguration.TreeConfigurationBuilder createOak() {
        return null;
    }

    @Shadow
    protected static TreeConfiguration.TreeConfigurationBuilder createBirch() {
        return null;
    }

    @Shadow
    protected static TreeConfiguration.TreeConfigurationBuilder createFancyOak() {
        return null;
    }

    @Inject(method = "bootstrap",at = @At("TAIL"))
    private static void holyback$addLeafLitters(BootstrapContext<ConfiguredFeature<?,?>> p_321763_, CallbackInfo ci){
        PlaceOnGroundDecorator placeongrounddecorator = new PlaceOnGroundDecorator(
                96, 4, 2, new WeightedStateProvider(holycubePaleOak$leafLitterPatchBuilder(1, 3))
        );
        PlaceOnGroundDecorator placeongrounddecorator1 = new PlaceOnGroundDecorator(
                150, 2, 2, new WeightedStateProvider(holycubePaleOak$leafLitterPatchBuilder(1, 4))
        );
        BeehiveDecorator beehivedecorator = new BeehiveDecorator(0.002F);
        FeatureUtils.register(
                p_321763_,
                CONFIGURED_OAK_BEES_0002_LEAF_LITTER,
                Feature.TREE,
                createOak().decorators(List.of(beehivedecorator, placeongrounddecorator, placeongrounddecorator1)).build()
        );
        FeatureUtils.register(
                p_321763_,
                CONFIGURED_BIRCH_BEES_0002_LEAF_LITTER,
                Feature.TREE,
                createBirch().decorators(List.of(beehivedecorator, placeongrounddecorator, placeongrounddecorator1)).build()
        );
        FeatureUtils.register(
                p_321763_,
                CONFIGURED_FANCY_OAK_BEES_0002_LEAF_LITTER,
                Feature.TREE,
                createFancyOak().decorators(List.of(beehivedecorator, placeongrounddecorator, placeongrounddecorator1)).build()
        );
        FeatureUtils.register(
                p_321763_, CONFIGURED_OAK_LEAF_LITTER, Feature.TREE, createOak().decorators(ImmutableList.of(placeongrounddecorator, placeongrounddecorator1)).build()
        );
        FeatureUtils.register(
                p_321763_,
                CONFIGURED_DARK_OAK_LEAF_LITTER,
                Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Blocks.DARK_OAK_LOG),
                        new DarkOakTrunkPlacer(6, 2, 1),
                        BlockStateProvider.simple(Blocks.DARK_OAK_LEAVES),
                        new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                        new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
                ).ignoreVines().decorators(ImmutableList.of(placeongrounddecorator, placeongrounddecorator1)).build()
        );
        FeatureUtils.register(
                p_321763_, CONFIGURED_BIRCH_LEAF_LITTER, Feature.TREE, createBirch().decorators(ImmutableList.of(placeongrounddecorator, placeongrounddecorator1)).build()
        );
        FeatureUtils.register(
                p_321763_, CONFIGURED_FANCY_OAK_LEAF_LITTER, Feature.TREE, createFancyOak().decorators(List.of(placeongrounddecorator, placeongrounddecorator1)).build()
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
}
