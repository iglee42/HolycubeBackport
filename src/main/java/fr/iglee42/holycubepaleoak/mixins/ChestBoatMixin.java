package fr.iglee42.holycubepaleoak.mixins;

import fr.iglee42.holycubepaleoak.HolyPOItems;
import fr.iglee42.holycubepaleoak.HolycubePaleOak;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBoat.class)
public class ChestBoatMixin {

    @Inject(method = "getDropItem",at=@At("HEAD"), cancellable = true)
    private void holycubepaleoak$addPaleOak(CallbackInfoReturnable<Item> cir){
        Object object = this;
        ChestBoat boat = (ChestBoat) object;
        if (boat.getVariant().equals(HolycubePaleOak.PALE_OAK_BOAT)) cir.setReturnValue(HolyPOItems.PALE_OAK_CHEST_BOAT);
    }

}
