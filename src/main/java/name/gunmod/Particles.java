package name.gunmod;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Particles {
    public static final DefaultParticleType ROUND_SHOT_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType SHELL_SHOT_PELLET_PARTICLE = FabricParticleTypes.simple();

    public static void registerAll(String modId) {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(modId, "round_shot"), ROUND_SHOT_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(modId, "shell_shot_pellet"), SHELL_SHOT_PELLET_PARTICLE);
    }
}
