package fr.iglee42.holycubepaleoak.mixins;

import fr.iglee42.holycubepaleoak.HolyPOBlocks;
import fr.iglee42.holycubepaleoak.HolyPOItems;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AirItem.class)
public class AirItemMixin {

    @Inject(method = "<init>",at=@At("TAIL"))
    private void holycubepaleoak$registryPaleOak(Block p_40368_, Item.Properties p_40369_, CallbackInfo ci){
        Boat.Type ignored = Boat.Type.OAK;
        new HolyPOItems();
    }
}
