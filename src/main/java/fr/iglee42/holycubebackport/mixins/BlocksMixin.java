package fr.iglee42.holycubebackport.mixins;

import fr.iglee42.holycubebackport.HolyBackBlocks;
import fr.iglee42.holycubebackport.HolyBackItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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


    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void holyback$slabsStairs(CallbackInfo ci){
        HolyBackBlocks.GRASS_STAIRS = register("grass_stairs",legacyStair(GRASS_BLOCK));
        HolyBackBlocks.GRASS_SLAB = register("grass_slab",new SlabBlock(BlockBehaviour.Properties.ofFullCopy(GRASS_BLOCK)));
        HolyBackItems.GRASS_STAIRS = Items.registerBlock(HolyBackBlocks.GRASS_STAIRS);
        HolyBackItems.GRASS_SLAB = Items.registerBlock(HolyBackBlocks.GRASS_SLAB);
    }

}
