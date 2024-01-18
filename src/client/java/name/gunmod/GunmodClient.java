package name.gunmod;

import name.gunmod.entities.Entities;
import name.gunmod.renderers.BoltShotEntityRenderer;
import name.gunmod.renderers.ShellShotPelletEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

// @Environment(EnvType.CLIENT) // NVM this breaks the code for some reason
public class GunmodClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

        EntityRendererRegistry.register(Entities.BOLT_SHOT, BoltShotEntityRenderer::new);
		EntityRendererRegistry.register(Entities.SHELL_SHOT_PELLET, ShellShotPelletEntityRenderer::new);
	}
}