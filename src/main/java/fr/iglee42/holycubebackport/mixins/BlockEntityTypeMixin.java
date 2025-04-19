package fr.iglee42.holycubebackport.mixins;

import fr.iglee42.holycubebackport.HolyBackBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {

    @Inject(method = "isValid",at=@At("TAIL"), cancellable = true)
    private void holycubepaleoak$registryPaleOak(BlockState state, CallbackInfoReturnable<Boolean> cir){
        if (this.equals(BlockEntityType.SIGN) && (state.is(HolyBackBlocks.PALE_OAK_SIGN) || state.is(HolyBackBlocks.PALE_OAK_WALL_SIGN))) cir.setReturnValue(true);
        if (this.equals(BlockEntityType.HANGING_SIGN) && (state.is(HolyBackBlocks.PALE_OAK_HANGING_SIGN) || state.is(HolyBackBlocks.PALE_OAK_WALL_HANGING_SIGN))) cir.setReturnValue(true);
    }
}
