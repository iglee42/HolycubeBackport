package fr.iglee42.holycubebackport.mixins;

import fr.iglee42.holycubebackport.HolyBackBlocks;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AirBlock.class)
public class AirBlockMixin {

    @Inject(method = "<init>",at=@At("TAIL"))
    private void holycubepaleoak$registryPaleOak(BlockBehaviour.Properties p_48756_, CallbackInfo ci){
        new Blocks();
        new HolyBackBlocks();
    }
}
