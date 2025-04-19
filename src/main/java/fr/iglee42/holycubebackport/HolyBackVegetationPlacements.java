package fr.iglee42.holycubebackport;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class HolyBackVegetationPlacements {
    public static final ResourceKey<PlacedFeature> PATCH_FIREFLY_BUSH_SWAMP = PlacementUtils.createKey("patch_firefly_bush_swamp");
    public static final ResourceKey<PlacedFeature> PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP = PlacementUtils.createKey("patch_firefly_bush_near_water_swamp");
    public static final ResourceKey<PlacedFeature> PATCH_FIREFLY_BUSH_NEAR_WATER = PlacementUtils.createKey("patch_firefly_bush_near_water");
    public static final ResourceKey<PlacedFeature> PATCH_DRY_GRASS_BADLANDS = PlacementUtils.createKey("patch_dry_grass_badlands");
    public static final ResourceKey<PlacedFeature> PATCH_DRY_GRASS_DESERT = PlacementUtils.createKey("patch_dry_grass_desert");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FIREFLY_BUSH = FeatureUtils.createKey("patch_firefly_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DRY_GRASS = FeatureUtils.createKey("patch_dry_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_LEAF_LITTER = FeatureUtils.createKey("patch_leaf_litter");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIRCH_AND_OAK_LEAF_LITTER = FeatureUtils.createKey("trees_birch_and_oak_leaf_litter");

    public static final ResourceKey<PlacedFeature> OAK_BEES_0002_LEAF_LITTER = PlacementUtils.createKey("oak_bees_0002_leaf_litter");
    public static final ResourceKey<PlacedFeature> BIRCH_BEES_0002_LEAF_LITTER = PlacementUtils.createKey("birch_bees_0002_leaf_litter");
    public static final ResourceKey<PlacedFeature> FANCY_OAK_BEES_0002_LEAF_LITTER = PlacementUtils.createKey("fancy_oak_bees_0002_leaf_litter");
    public static final ResourceKey<PlacedFeature> OAK_LEAF_LITTER = PlacementUtils.createKey("oak_leaf_litter");
    public static final ResourceKey<PlacedFeature> DARK_OAK_LEAF_LITTER = PlacementUtils.createKey("dark_oak_leaf_litter");
    public static final ResourceKey<PlacedFeature> BIRCH_LEAF_LITTER = PlacementUtils.createKey("birch_leaf_litter");
    public static final ResourceKey<PlacedFeature> FANCY_OAK_LEAF_LITTER = PlacementUtils.createKey("fancy_oak_leaf_litter");


    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_OAK_BEES_0002_LEAF_LITTER = FeatureUtils.createKey("oak_bees_0002_leaf_litter");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_BIRCH_BEES_0002_LEAF_LITTER = FeatureUtils.createKey("birch_bees_0002_leaf_litter");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_FANCY_OAK_BEES_0002_LEAF_LITTER = FeatureUtils.createKey("fancy_oak_bees_0002_leaf_litter");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_OAK_LEAF_LITTER = FeatureUtils.createKey("oak_leaf_litter");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_DARK_OAK_LEAF_LITTER = FeatureUtils.createKey("dark_oak_leaf_litter");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_BIRCH_LEAF_LITTER = FeatureUtils.createKey("birch_leaf_litter");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_FANCY_OAK_LEAF_LITTER = FeatureUtils.createKey("fancy_oak_leaf_litter");


    public static BlockPredicateFilter nearWaterPredicate(Block p_401037_) {
        return BlockPredicateFilter.forPredicate(BlockPredicate.allOf(new BlockPredicate[]{BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(p_401037_.defaultBlockState(), BlockPos.ZERO), BlockPredicate.anyOf(new BlockPredicate[]{BlockPredicate.matchesFluids(new BlockPos(1, -1, 0), new Fluid[]{Fluids.WATER, Fluids.FLOWING_WATER}), BlockPredicate.matchesFluids(new BlockPos(-1, -1, 0), new Fluid[]{Fluids.WATER, Fluids.FLOWING_WATER}), BlockPredicate.matchesFluids(new BlockPos(0, -1, 1), new Fluid[]{Fluids.WATER, Fluids.FLOWING_WATER}), BlockPredicate.matchesFluids(new BlockPos(0, -1, -1), new Fluid[]{Fluids.WATER, Fluids.FLOWING_WATER})})}));
    }

    public static final PlacementModifier HEIGHTMAP_NO_LEAVES = HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES);

    public static List<BlockPos> getLowestTrunkOrRootOfTree(TreeDecorator.Context p_393567_) {
        List<BlockPos> list = Lists.newArrayList();
        List<BlockPos> list1 = p_393567_.roots();
        List<BlockPos> list2 = p_393567_.logs();
        if (list1.isEmpty()) {
            list.addAll(list2);
        } else if (!list2.isEmpty() && list1.get(0).getY() == list2.get(0).getY()) {
            list.addAll(list2);
            list.addAll(list1);
        } else {
            list.addAll(list1);
        }

        return list;
    }

}
