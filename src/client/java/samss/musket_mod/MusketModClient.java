package samss.musket_mod;

import samss.musket_mod.renderers.BoltShotEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

// @Environment(EnvType.CLIENT) // NVM this breaks the code for some reason
public class MusketModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

        EntityRendererRegistry.register(Entities.BOLT_SHOT, BoltShotEntityRenderer::new);
		EntityRendererRegistry.register(Entities.ROUND_SHOT, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(Entities.SHELL_SHOT_PELLET, FlyingItemEntityRenderer::new);
	}
}