package fr.iglee42.holycubebackport.mixins;

import fr.iglee42.holycubebackport.HolyBackBlocks;
import fr.iglee42.holycubebackport.HolycubeBackport;
import net.minecraft.util.ByIdMap;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(Boat.Type.class)
public class BoatTypeMixin {

    @Shadow
    @Final
    @Mutable
    private static Boat.Type[] $VALUES;

    @Invoker("<init>")
    public static Boat.Type holycubepaleoak$initInvoke(String internalName, int internalId, Block p_38427_, String p_38428_){
        throw new AssertionError();
    }

    @Inject(method = "<clinit>",at = @At("TAIL"))
    private static void holycubepaleoak$clinit(CallbackInfo ci) {
        HolycubeBackport.PALE_OAK_BOAT = holycubepaleoak$addVariant("PALE_OAK", HolyBackBlocks.PALE_OAK_PLANKS, "pale_oak");
    }

    @Unique
    private static Boat.Type holycubepaleoak$addVariant(String internalName,Block block,String name) {
        ArrayList<Boat.Type> variants = new ArrayList<>(Arrays.asList($VALUES));
        Boat.Type type = holycubepaleoak$initInvoke(internalName, variants.get(variants.size() - 1).ordinal() + 1,block,name);
        variants.add(type);
        BoatTypeMixin.$VALUES = variants.toArray(new Boat.Type[0]);
        Boat.Type.BY_ID = ByIdMap.continuous(Enum::ordinal, $VALUES, ByIdMap.OutOfBoundsStrategy.ZERO);
        return type;
    }


}
