package name.gunmod.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;

public class ShellShotPelletParticle extends SpriteBillboardParticle {
    private ShellShotPelletParticle(ClientWorld clientWorld, double x, double y, double z, SpriteProvider provider, double velocityX, double velocityY, double velocityZ) {
        super(clientWorld, x, y, z, velocityX, velocityY, velocityZ);

        this.velocityMultiplier = 0f;
        this.scale = 0.25f;
        this.maxAge = 1;
        this.age = 1;
        this.setSpriteForAge(provider);

        this.x = x;
        this.y = y;
        this.z = z;
        this.velocityX = 0;
        this.velocityY = 0;
        this.velocityZ = 0;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider provider;
        public Factory(SpriteProvider provider) {
            this.provider = provider;
        }
        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new ShellShotPelletParticle(world, x, y, z, this.provider, velocityX, velocityY, velocityZ);
        }
    }
}
