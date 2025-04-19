package fr.iglee42.holycubebackport.mixins;

import fr.iglee42.holycubebackport.HolyBackBlocks;
import fr.iglee42.holycubebackport.HolyBackVegetationPlacements;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static fr.iglee42.holycubebackport.HolyBackVegetationPlacements.*;

@Mixin(VegetationPlacements.class)
public class VegetationPlacementsMixin {

    @Inject(method = "bootstrap",at = @At("TAIL"),locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void holyback$firefly(BootstrapContext<PlacedFeature> p_321769_, CallbackInfo ci, HolderGetter holdergetter, Holder holder, Holder holder1, Holder holder2, Holder holder3, Holder holder4, Holder holder5, Holder holder6, Holder holder7, Holder holder8, Holder holder9, Holder holder10, Holder holder11, Holder holder12, Holder holder13, Holder holder14, Holder holder15, Holder holder16, Holder holder17, Holder holder18, Holder holder19, Holder holder20, Holder holder21, Holder holder22, Holder holder23, Holder holder24, Holder holder25, Holder holder26, Holder holder27, Holder holder28, Holder holder29, Holder holder30, Holder holder31, Holder holder32, Holder holder33, Holder holder34, Holder holder35, Holder holder36, Holder holder37, Holder holder38, Holder holder39, Holder holder40, Holder holder41, Holder holder42, Holder holder43, Holder holder44, Holder holder45, Holder holder46, Holder holder47, Holder holder48, PlacementModifier placementmodifier){
        Holder<ConfiguredFeature<?, ?>> holder49 = holdergetter.getOrThrow(HolyBackVegetationPlacements.PATCH_FIREFLY_BUSH);
        Holder<ConfiguredFeature<?, ?>> holder50 = holdergetter.getOrThrow(PATCH_DRY_GRASS);

        PlacementUtils.register(
                p_321769_,
                PATCH_FIREFLY_BUSH_NEAR_WATER,
                holder49,
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                HEIGHTMAP_NO_LEAVES,
                BiomeFilter.biome(),
                nearWaterPredicate(HolyBackBlocks.FIREFLY_BUSH)
        );
        PlacementUtils.register(
                p_321769_,
                PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP,
                holder49,
                CountPlacement.of(3),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                nearWaterPredicate(HolyBackBlocks.FIREFLY_BUSH)
        );
        PlacementUtils.register(
                p_321769_,
                PATCH_FIREFLY_BUSH_SWAMP,
                holder49,
                RarityFilter.onAverageOnceEvery(8),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        PlacementUtils.register(
                p_321769_,
                PATCH_DRY_GRASS_BADLANDS,
                holder50,
                RarityFilter.onAverageOnceEvery(6),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        PlacementUtils.register(
                p_321769_,
                PATCH_DRY_GRASS_DESERT,
                holder50,
                RarityFilter.onAverageOnceEvery(3),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
    }
}
