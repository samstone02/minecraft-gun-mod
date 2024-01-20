package name.gunmod.renderers;

import name.gunmod.Particles;
import name.gunmod.entities.RoundShotEntity;
import name.gunmod.items.GunModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class RoundShotEntityRenderer extends EntityRenderer<RoundShotEntity> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/round_shot.png");
    public RoundShotEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
    @Override
    public Identifier getTexture(RoundShotEntity entity) { return TEXTURE; }
    @Override
    public void render(RoundShotEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
//        MinecraftClient.getInstance().getItemRenderer().renderItem(
//                new ItemStack(GunModItems.ROUND_SHOT),
//                ModelTransformationMode.FIXED,
//                light,
//                OverlayTexture.DEFAULT_UV,
//                matrices,
//                vertexConsumers,
//                MinecraftClient.getInstance().world,
//                123
//        );
        MinecraftClient.getInstance().particleManager.addParticle(Particles.ROUND_SHOT_PARTICLE, entity.getX(), entity.getY(), entity.getZ(), 0, 0, 0);
//        MinecraftClient.getInstance().player.getHeadYaw();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
