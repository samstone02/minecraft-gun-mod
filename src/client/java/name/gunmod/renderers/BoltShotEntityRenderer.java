package name.gunmod.renderers;

import name.gunmod.Gunmod;
import name.gunmod.entities.BoltShotEntity;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class BoltShotEntityRenderer extends ProjectileEntityRenderer<BoltShotEntity> {
	public static final Identifier TEXTURE = new Identifier(Gunmod.MOD_ID, "textures/entity/bolt_shot.png");
	
	public BoltShotEntityRenderer(Context context) {
		super(context);
	}


	// getTexture is not used for ThrownEntities.
	// getTexture _is_ used for PersistentProjectileEntities.
	@Override
	public Identifier getTexture(BoltShotEntity entity) {
		return TEXTURE;
	}
}
