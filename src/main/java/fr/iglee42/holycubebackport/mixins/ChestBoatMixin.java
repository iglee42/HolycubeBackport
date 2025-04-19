package fr.iglee42.holycubebackport.mixins;

import fr.iglee42.holycubebackport.HolyBackItems;
import fr.iglee42.holycubebackport.HolycubeBackport;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBoat.class)
public class ChestBoatMixin {

    @Inject(method = "getDropItem",at=@At("HEAD"), cancellable = true)
    private void holycubepaleoak$addPaleOak(CallbackInfoReturnable<Item> cir){
        Object object = this;
        ChestBoat boat = (ChestBoat) object;
        if (boat.getVariant().equals(HolycubeBackport.PALE_OAK_BOAT)) cir.setReturnValue(HolyBackItems.PALE_OAK_CHEST_BOAT);
    }

}
