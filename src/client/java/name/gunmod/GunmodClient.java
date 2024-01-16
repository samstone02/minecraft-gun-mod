package name.gunmod;

import name.gunmod.entities.Entities;
import name.gunmod.renderers.BoltShotEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

// @Environment(EnvType.CLIENT) // NVM this breaks the code for some reason
public class GunmodClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		// List<PrintWriter> pws = new ArrayList<PrintWriter>();
//		Entities.registerAll(Gunmod.MOD_ID);
//
        EntityRendererRegistry.register(Entities.BOLT_SHOT, BoltShotEntityRenderer::new);
	}
}