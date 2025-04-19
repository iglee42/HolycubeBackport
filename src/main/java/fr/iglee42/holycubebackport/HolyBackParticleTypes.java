package fr.iglee42.holycubebackport;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class HolyBackParticleTypes {

    public static final SimpleParticleType FIREFLY = register("firefly", false);
    public static final Codec<ParticleOptions> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, ParticleOptions> STREAM_CODEC;

    private static SimpleParticleType register(String p_123825_, boolean p_123826_) {
        return (SimpleParticleType)Registry.register(BuiltInRegistries.PARTICLE_TYPE, p_123825_, new SimpleParticleType(p_123826_));
    }

    private static <T extends ParticleOptions> ParticleType<T> register(String p_235906_, boolean p_235907_, final Function<ParticleType<T>, MapCodec<T>> p_235909_, final Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> p_320791_) {
        return (ParticleType)Registry.register(BuiltInRegistries.PARTICLE_TYPE, p_235906_, new ParticleType<T>(p_235907_) {
            public MapCodec<T> codec() {
                return (MapCodec)p_235909_.apply(this);
            }

            public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
                return (StreamCodec)p_320791_.apply(this);
            }
        });
    }

    static {
        CODEC = BuiltInRegistries.PARTICLE_TYPE.byNameCodec().dispatch("type", ParticleOptions::getType, ParticleType::codec);
        STREAM_CODEC = ByteBufCodecs.registry(Registries.PARTICLE_TYPE).dispatch(ParticleOptions::getType, ParticleType::streamCodec);
    }
}
