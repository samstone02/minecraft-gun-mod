package name.gunmod.renderers;

import name.gunmod.Gunmod;
import name.gunmod.Particles;
import name.gunmod.entities.ShellShotPelletEntity;
import name.gunmod.items.GunModItems;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

// INFO: Renders based on the texture for the
public class ShellShotPelletEntityRenderer extends EntityRenderer<ShellShotPelletEntity> {
//    public final Particle particle;
    public ShellShotPelletEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
    public Identifier getTexture(ShellShotPelletEntity entity) {
        return null;
    } // not used for this renderer

    @Override
    public void render(ShellShotPelletEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        // MinecraftClient.getInstance().world.addParticle(Particles.SHELL_SHOT_PELLET_PARTICLE, entity.getX(), entity.getY(), entity.getZ(), 0, 0, 0);
//        particle.setPos(entity.getX(), entity.getY(), entity.getZ());
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
