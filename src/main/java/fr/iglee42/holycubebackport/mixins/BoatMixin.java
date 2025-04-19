package fr.iglee42.holycubebackport.mixins;

import fr.iglee42.holycubebackport.HolyBackItems;
import fr.iglee42.holycubebackport.HolycubeBackport;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Boat.class)
public abstract class BoatMixin {

    @Shadow public abstract Boat.Type getVariant();

    @Inject(method = "getDropItem",at=@At("HEAD"), cancellable = true)
    private void holycubepaleoak$addPaleOak(CallbackInfoReturnable<Item> cir){
        if (getVariant().equals(HolycubeBackport.PALE_OAK_BOAT)) cir.setReturnValue(HolyBackItems.PALE_OAK_BOAT);
    }

}
