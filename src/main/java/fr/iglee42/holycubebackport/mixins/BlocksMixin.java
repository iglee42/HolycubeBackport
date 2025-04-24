package fr.iglee42.holycubebackport.mixins;

import fr.iglee42.holycubebackport.HolyBackBlocks;
import fr.iglee42.holycubebackport.HolyBackItems;
import fr.iglee42.holycubebackport.blocks.PathSlabBlock;
import fr.iglee42.holycubebackport.blocks.PathStairBlock;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Blocks.class)
public abstract class BlocksMixin {

    @Shadow
    @Deprecated
    protected static Block legacyStair(Block p_312243_) {
        return null;
    }

    @Shadow @Final public static Block GRASS_BLOCK;

    @Shadow
    public static Block register(String p_50796_, Block p_50797_) {
        return null;
    }


    @Shadow @Final public static Block DIRT_PATH;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void holyback$slabsStairs(CallbackInfo ci){
        HolyBackBlocks.GRASS_STAIRS = register("grass_stairs",legacyStair(GRASS_BLOCK));
        HolyBackBlocks.GRASS_SLAB = register("grass_slab",new SlabBlock(BlockBehaviour.Properties.ofFullCopy(GRASS_BLOCK)));
        HolyBackBlocks.PATH_STAIRS = register("path_stairs",holycubeBackport$pathLegacyStair(DIRT_PATH));
        HolyBackBlocks.PATH_SLAB = register("path_slab",new PathSlabBlock(BlockBehaviour.Properties.ofFullCopy(DIRT_PATH)));
        ShovelItem.FLATTENABLES.put(HolyBackBlocks.GRASS_STAIRS,HolyBackBlocks.PATH_STAIRS.defaultBlockState());
        ShovelItem.FLATTENABLES.put(HolyBackBlocks.GRASS_SLAB,HolyBackBlocks.PATH_SLAB.defaultBlockState());
        HolyBackItems.GRASS_STAIRS = Items.registerBlock(HolyBackBlocks.GRASS_STAIRS);
        HolyBackItems.GRASS_SLAB = Items.registerBlock(HolyBackBlocks.GRASS_SLAB);
        HolyBackItems.PATH_STAIRS = Items.registerBlock(HolyBackBlocks.PATH_STAIRS);
        HolyBackItems.PATH_SLAB = Items.registerBlock(HolyBackBlocks.PATH_SLAB);
    }

    @Unique
    private static Block holycubeBackport$pathLegacyStair(Block p_312243_) {
        return new PathStairBlock(p_312243_.defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(p_312243_));
    }

}
