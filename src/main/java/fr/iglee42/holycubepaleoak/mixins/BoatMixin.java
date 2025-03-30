package fr.iglee42.holycubepaleoak.mixins;

import fr.iglee42.holycubepaleoak.HolyPOBlocks;
import fr.iglee42.holycubepaleoak.HolyPOItems;
import fr.iglee42.holycubepaleoak.HolycubePaleOak;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(Boat.class)
public abstract class BoatMixin {

    @Shadow public abstract Boat.Type getVariant();

    @Inject(method = "getDropItem",at=@At("HEAD"), cancellable = true)
    private void holycubepaleoak$addPaleOak(CallbackInfoReturnable<Item> cir){
        if (getVariant().equals(HolycubePaleOak.PALE_OAK_BOAT)) cir.setReturnValue(HolyPOItems.PALE_OAK_BOAT);
    }

}
