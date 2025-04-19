package fr.iglee42.holycubebackport.mixins;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static fr.iglee42.holycubebackport.HolyBackVegetationPlacements.*;

@Mixin(TreePlacements.class)
public class TreePlacementsMixin {

    @Inject(method = "bootstrap",at = @At("TAIL"))
    private static void holyback$addLeafLitters(BootstrapContext<PlacedFeature> p_321763_, CallbackInfo ci){
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = p_321763_.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> holder21 = holdergetter.getOrThrow(CONFIGURED_OAK_BEES_0002_LEAF_LITTER);
        Holder<ConfiguredFeature<?, ?>> holder24 = holdergetter.getOrThrow(CONFIGURED_BIRCH_BEES_0002_LEAF_LITTER);
        Holder<ConfiguredFeature<?, ?>> holder26 = holdergetter.getOrThrow(CONFIGURED_FANCY_OAK_BEES_0002_LEAF_LITTER);
        Holder<ConfiguredFeature<?, ?>> holder30 = holdergetter.getOrThrow(CONFIGURED_OAK_LEAF_LITTER);
        Holder<ConfiguredFeature<?, ?>> holder31 = holdergetter.getOrThrow(CONFIGURED_DARK_OAK_LEAF_LITTER);
        Holder<ConfiguredFeature<?, ?>> holder32 = holdergetter.getOrThrow(CONFIGURED_BIRCH_LEAF_LITTER);
        Holder<ConfiguredFeature<?, ?>> holder33 = holdergetter.getOrThrow(CONFIGURED_FANCY_OAK_LEAF_LITTER);

        PlacementUtils.register(p_321763_, OAK_BEES_0002_LEAF_LITTER, holder21, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
        PlacementUtils.register(p_321763_, BIRCH_BEES_0002_LEAF_LITTER, holder24, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
        PlacementUtils.register(p_321763_, FANCY_OAK_BEES_0002_LEAF_LITTER, holder26, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
        PlacementUtils.register(p_321763_, OAK_LEAF_LITTER, holder30, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));
        PlacementUtils.register(p_321763_, BIRCH_LEAF_LITTER, holder32, PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING));
        PlacementUtils.register(p_321763_, DARK_OAK_LEAF_LITTER, holder31, PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING));
        PlacementUtils.register(p_321763_, FANCY_OAK_LEAF_LITTER, holder33, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING));

    }
}
