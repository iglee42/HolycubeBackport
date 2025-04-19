package fr.iglee42.holycubebackport;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FireflyParticle extends TextureSheetParticle {
    private static final float PARTICLE_FADE_OUT_LIGHT_TIME = 0.3F;
    private static final float PARTICLE_FADE_IN_LIGHT_TIME = 0.1F;
    private static final float PARTICLE_FADE_OUT_ALPHA_TIME = 0.5F;
    private static final float PARTICLE_FADE_IN_ALPHA_TIME = 0.3F;
    private static final int PARTICLE_MIN_LIFETIME = 36;
    private static final int PARTICLE_MAX_LIFETIME = 180;

    public FireflyParticle(ClientLevel p_401114_, double p_401068_, double p_401435_, double p_401238_, double p_401102_, double p_401330_, double p_401419_) {
        super(p_401114_, p_401068_, p_401435_, p_401238_, p_401102_, p_401330_, p_401419_);
        this.speedUpWhenYMotionIsBlocked = true;
        this.friction = 0.96F;
        this.quadSize *= 0.75F;
        this.yd *= (double)0.8F;
        this.xd *= (double)0.8F;
        this.zd *= (double)0.8F;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public int getLightColor(float p_401057_) {
        return (int)(255.0F * getFadeAmount(this.getLifetimeProgress((float)this.age + p_401057_), 0.1F, 0.3F));
    }

    public void tick() {
        super.tick();
        if (!this.level.getBlockState(BlockPos.containing(this.x, this.y, this.z)).isAir()) {
            this.remove();
        } else {
            this.setAlpha(getFadeAmount(this.getLifetimeProgress((float)this.age), 0.3F, 0.5F));
            if (Math.random() > 0.95 || this.age == 1) {
                this.setParticleSpeed((double)-0.05F + (double)0.1F * Math.random(), (double)-0.05F + (double)0.1F * Math.random(), (double)-0.05F + (double)0.1F * Math.random());
            }
        }

    }

    private float getLifetimeProgress(float p_405074_) {
        return Mth.clamp(p_405074_ / (float)this.lifetime, 0.0F, 1.0F);
    }

    private static float getFadeAmount(float p_405677_, float p_405084_, float p_405474_) {
        if (p_405677_ >= 1.0F - p_405084_) {
            return (1.0F - p_405677_) / p_405084_;
        } else {
            return p_405677_ <= p_405474_ ? p_405677_ / p_405474_ : 1.0F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class FireflyProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public FireflyProvider(SpriteSet p_401168_) {
            this.sprite = p_401168_;
        }

        public Particle createParticle(SimpleParticleType p_401135_, ClientLevel p_401156_, double p_401101_, double p_401295_, double p_401307_, double p_401308_, double p_401354_, double p_401411_) {
            FireflyParticle fireflyparticle = new FireflyParticle(p_401156_, p_401101_, p_401295_, p_401307_, (double)0.5F - p_401156_.random.nextDouble(), p_401156_.random.nextBoolean() ? p_401354_ : -p_401354_, (double)0.5F - p_401156_.random.nextDouble());
            fireflyparticle.setLifetime(p_401156_.random.nextIntBetweenInclusive(36, 180));
            fireflyparticle.scale(1.5F);
            fireflyparticle.pickSprite(this.sprite);
            fireflyparticle.setAlpha(0.0F);
            return fireflyparticle;
        }
    }
}
