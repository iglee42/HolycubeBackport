package fr.iglee42.holycubepaleoak.mixins;

import fr.iglee42.holycubepaleoak.HolyPOItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static net.minecraft.world.level.block.ComposterBlock.COMPOSTABLES;

@Mixin(ComposterBlock.class)
public abstract class ComposterBlockMixin {

    @Shadow
    private static void add(float p_51921_, ItemLike p_51922_) {
    }

    @Inject(method = "bootStrap",at= @At("TAIL"),locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void holycubepaleoak$flammableBlocks(CallbackInfo ci, float f, float f1, float f2, float f3, float f4){
        add(0.3F, HolyPOItems.PALE_OAK_LEAVES);
        add(0.3F, HolyPOItems.PALE_OAK_SAPLING);
        add(0.3F, HolyPOItems.PALE_MOSS_CARPET);
        add(0.3F, HolyPOItems.PALE_HANGING_MOSS);
        add(0.65F, HolyPOItems.PALE_MOSS_BLOCK);
        add(0.65F, HolyPOItems.OPEN_EYEBLOSSOM);
        add(0.65F, HolyPOItems.CLOSED_EYEBLOSSOM);
    }

}
