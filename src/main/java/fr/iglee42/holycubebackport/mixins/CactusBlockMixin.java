package fr.iglee42.holycubebackport.mixins;

import fr.iglee42.holycubebackport.HolyBackBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CactusBlock.class)
public abstract class CactusBlockMixin {

    @Shadow protected abstract boolean canSurvive(BlockState p_51153_, LevelReader p_51154_, BlockPos p_51155_);

    @Shadow @Final public static IntegerProperty AGE;

    @Inject(method = "randomTick", at = @At("TAIL"))
    private void holyback$growFlower(BlockState p_220913_, ServerLevel p_220914_, BlockPos p_220915_, RandomSource p_220916_, CallbackInfo ci){
        BlockPos blockpos = p_220915_.above();
        if (p_220914_.isEmptyBlock(blockpos)) {
            int i = 1;

            while (p_220914_.getBlockState(blockpos.below(i)).is(p_220913_.getBlock())) {
                i++;
            }
            if (i> 3)
                if(net.neoforged.neoforge.common.CommonHooks.canCropGrow(p_220914_, blockpos, p_220913_, true)) {
                    if (this.canSurvive(p_220913_.getBlock().defaultBlockState(), p_220914_, p_220915_.above())) {
                        double d0 = 0.25;
                        if (p_220916_.nextDouble() <= d0) {
                            p_220914_.setBlockAndUpdate(blockpos, HolyBackBlocks.CACTUS_FLOWER.defaultBlockState());
                        }
                    }
                    net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(p_220914_, p_220915_, p_220913_);
                }

        }
    }

}
