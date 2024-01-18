package name.gunmod.renderers;

import name.gunmod.entities.ShellShotPelletEntity;
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

// INFO: Renders based on the texture for the
public class ShellShotPelletEntityRenderer extends EntityRenderer<ShellShotPelletEntity> {
    public static final ItemStack STACK = new ItemStack(GunModItems.ROUND_SHOT);
    public ShellShotPelletEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
    public Identifier getTexture(ShellShotPelletEntity entity) {
        return null;
    } // not used for this renderer

    @Override
    public void render(ShellShotPelletEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        MinecraftClient.getInstance().getItemRenderer().renderItem(
                STACK, ModelTransformationMode.FIXED, light,
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,
                MinecraftClient.getInstance().world, 123
        );
//        MinecraftClient.getInstance().
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
