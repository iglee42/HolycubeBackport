package fr.iglee42.holycubepaleoak;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HolyPOSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT,"minecraft");

    public static final DeferredHolder<SoundEvent,SoundEvent> EYEBLOSSOM_IDLE = SOUNDS.register("block.eyeblossom.idle",SoundEvent::createVariableRangeEvent);
    public static final DeferredHolder<SoundEvent,SoundEvent> EYEBLOSSOM_OPEN_LONG = SOUNDS.register("block.eyeblossom.open_long",SoundEvent::createVariableRangeEvent);
    public static final DeferredHolder<SoundEvent,SoundEvent> EYEBLOSSOM_OPEN = SOUNDS.register("block.eyeblossom.open",SoundEvent::createVariableRangeEvent);
    public static final DeferredHolder<SoundEvent,SoundEvent> EYEBLOSSOM_CLOSE_LONG = SOUNDS.register("block.eyeblossom.close_long",SoundEvent::createVariableRangeEvent);
    public static final DeferredHolder<SoundEvent,SoundEvent> EYEBLOSSOM_CLOSE = SOUNDS.register("block.eyeblossom.close",SoundEvent::createVariableRangeEvent);
    public static final DeferredHolder<SoundEvent,SoundEvent> PALE_HANGING_MOSS_IDLE = SOUNDS.register("block.pale_hanging_moss.idle",SoundEvent::createVariableRangeEvent);

    public static final SoundEvent RESIN_BREAK = register("block.resin.break");
    public static final SoundEvent RESIN_FALL = register("block.resin.fall");
    public static final SoundEvent RESIN_PLACE = register("block.resin.place");
    public static final SoundEvent RESIN_STEP = register("block.resin.step");
    public static final SoundEvent RESIN_BRICKS_BREAK = register("block.resin_bricks.break");
    public static final SoundEvent RESIN_BRICKS_FALL = register("block.resin_bricks.fall");
    public static final SoundEvent RESIN_BRICKS_HIT = register("block.resin_bricks.hit");
    public static final SoundEvent RESIN_BRICKS_PLACE = register("block.resin_bricks.place");
    public static final SoundEvent RESIN_BRICKS_STEP = register("block.resin_bricks.step");
    
    public static final SoundType RESIN = new SoundType(
            1.0F, 1.0F, RESIN_BREAK, RESIN_STEP, RESIN_PLACE, SoundEvents.EMPTY, RESIN_FALL
    );

    public static final SoundType RESIN_BRICKS = new SoundType(
            1.0F,
            1.0F,
            RESIN_BRICKS_BREAK,
            RESIN_BRICKS_STEP,
            RESIN_BRICKS_PLACE,
            RESIN_BRICKS_HIT,
            RESIN_BRICKS_FALL
    );
    private static SoundEvent register(String p_12657_) {
        return register(ResourceLocation.withDefaultNamespace(p_12657_));
    }

    private static SoundEvent register(ResourceLocation p_263332_) {
        return register(p_263332_, p_263332_);
    }

    private static Holder.Reference<SoundEvent> registerForHolder(String p_263391_) {
        return registerForHolder(ResourceLocation.withDefaultNamespace(p_263391_));
    }

    private static Holder.Reference<SoundEvent> registerForHolder(ResourceLocation p_263361_) {
        return registerForHolder(p_263361_, p_263361_);
    }

    private static SoundEvent register(ResourceLocation p_263388_, ResourceLocation p_263340_) {
        return (SoundEvent) Registry.register(BuiltInRegistries.SOUND_EVENT, p_263388_, SoundEvent.createVariableRangeEvent(p_263340_));
    }

    private static Holder.Reference<SoundEvent> registerForHolder(ResourceLocation p_263362_, ResourceLocation p_263424_) {
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, p_263362_, SoundEvent.createVariableRangeEvent(p_263424_));
    }
    

}
