package fr.iglee42.holycubepaleoak.mixins;

import fr.iglee42.holycubepaleoak.HolyPOBlocks;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FireBlock.class)
public class FireBlockMixin {

    @Inject(method = "bootStrap",at= @At("TAIL"),locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void holycubepaleoak$flammableBlocks(CallbackInfo ci, FireBlock fireblock){
        fireblock.setFlammable(HolyPOBlocks.PALE_OAK_PLANKS, 5, 20);
        fireblock.setFlammable(HolyPOBlocks.PALE_OAK_SLAB, 5, 20);
        fireblock.setFlammable(HolyPOBlocks.PALE_OAK_FENCE_GATE, 5, 20);
        fireblock.setFlammable(HolyPOBlocks.PALE_OAK_FENCE, 5, 20);
        fireblock.setFlammable(HolyPOBlocks.PALE_OAK_STAIRS, 5, 20);
        fireblock.setFlammable(HolyPOBlocks.PALE_OAK_LOG, 5, 5);
        fireblock.setFlammable(HolyPOBlocks.STRIPPED_PALE_OAK_LOG, 5, 5);
        fireblock.setFlammable(HolyPOBlocks.STRIPPED_PALE_OAK_WOOD, 5, 5);
        fireblock.setFlammable(HolyPOBlocks.PALE_OAK_WOOD, 5, 5);
        fireblock.setFlammable(HolyPOBlocks.PALE_OAK_LEAVES, 30, 60);
        fireblock.setFlammable(HolyPOBlocks.PALE_MOSS_BLOCK, 5, 100);
        fireblock.setFlammable(HolyPOBlocks.PALE_MOSS_CARPET, 5, 100);
        fireblock.setFlammable(HolyPOBlocks.PALE_HANGING_MOSS, 5, 100);
    }

}
