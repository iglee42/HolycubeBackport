package fr.iglee42.holycubepaleoak;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
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


}
