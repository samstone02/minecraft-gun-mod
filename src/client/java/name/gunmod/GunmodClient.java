package name.gunmod;

import name.gunmod.entities.Entities;
import name.gunmod.particles.RoundShotParticle;
import name.gunmod.particles.ShellShotPelletParticle;
import name.gunmod.renderers.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

// @Environment(EnvType.CLIENT) // NVM this breaks the code for some reason
public class GunmodClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

        EntityRendererRegistry.register(Entities.BOLT_SHOT, BoltShotEntityRenderer::new);
		EntityRendererRegistry.register(Entities.ROUND_SHOT, RoundShotEntityRenderer::new);
		EntityRendererRegistry.register(Entities.SHELL_SHOT_PELLET, ShellShotPelletEntityRenderer::new);

		ParticleFactoryRegistry.getInstance().register(Particles.ROUND_SHOT_PARTICLE, RoundShotParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(Particles.SHELL_SHOT_PELLET_PARTICLE, ShellShotPelletParticle.Factory::new);
	}
}