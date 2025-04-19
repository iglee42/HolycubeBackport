package fr.iglee42.holycubebackport.mixins;

import fr.iglee42.holycubebackport.FireflyParticle;
import fr.iglee42.holycubebackport.HolyBackParticleTypes;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public abstract class ParticleEngineMixin {

    @Shadow @Deprecated public abstract <T extends ParticleOptions> void register(ParticleType<T> p_107379_, ParticleEngine.SpriteParticleRegistration<T> p_107380_);

    @Inject(method = "registerProviders",at = @At("TAIL"))
    private void holyback$registerFirefly(CallbackInfo ci){
        register(HolyBackParticleTypes.FIREFLY, FireflyParticle.FireflyProvider::new);
    }
}
