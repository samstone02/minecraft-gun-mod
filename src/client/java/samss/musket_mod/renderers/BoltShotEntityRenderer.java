package samss.musket_mod.renderers;

import samss.musket_mod.MusketMod;
import samss.musket_mod.entities.BoltShotEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@Environment(value = EnvType.CLIENT)
public class BoltShotEntityRenderer extends EntityRenderer<BoltShotEntity> {
	public static final Identifier TEXTURE = new Identifier(MusketMod.MOD_ID, "textures/entity/bolt_shot.png");
	public BoltShotEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
	}
	@Override
	public Identifier getTexture(BoltShotEntity entity) {
		return TEXTURE;
	}
	@Override
	public void render(BoltShotEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
		matrixStack.push();
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw() - 90.0f)));
		matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch())));
		boolean j = false;
		float h = 0.0f;
		float k = 0.5f;
		float l = 0.0f;
		float m = 0.15625f;
		float n = 0.0f;
		float o = 0.15625f;
		float p = 0.15625f;
		float q = 0.3125f;
		float r = 0.05625f;
//		float s = (float)((PersistentProjectileEntity)persistentProjectileEntity).shake - g;
//		if (s > 0.0f) {
//			float t = -MathHelper.sin((float)(s * 3.0f)) * s;
//			matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(t));
//		}
		matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(45.0f));
		matrixStack.scale(0.05625f, 0.05625f, 0.05625f);
		matrixStack.translate(-4.0f, 0.0f, 0.0f);
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(this.getTexture(entity)));
		MatrixStack.Entry entry = matrixStack.peek();
		Matrix4f matrix4f = entry.getPositionMatrix();
		Matrix3f matrix3f = entry.getNormalMatrix();
		this.vertex(matrix4f, matrix3f, vertexConsumer, -7, -2, -2, 0.0f, 0.15625f, -1, 0, 0, light);
		this.vertex(matrix4f, matrix3f, vertexConsumer, -7, -2, 2, 0.15625f, 0.15625f, -1, 0, 0, light);
		this.vertex(matrix4f, matrix3f, vertexConsumer, -7, 2, 2, 0.15625f, 0.3125f, -1, 0, 0, light);
		this.vertex(matrix4f, matrix3f, vertexConsumer, -7, 2, -2, 0.0f, 0.3125f, -1, 0, 0, light);
		this.vertex(matrix4f, matrix3f, vertexConsumer, -7, 2, -2, 0.0f, 0.15625f, 1, 0, 0, light);
		this.vertex(matrix4f, matrix3f, vertexConsumer, -7, 2, 2, 0.15625f, 0.15625f, 1, 0, 0, light);
		this.vertex(matrix4f, matrix3f, vertexConsumer, -7, -2, 2, 0.15625f, 0.3125f, 1, 0, 0, light);
		this.vertex(matrix4f, matrix3f, vertexConsumer, -7, -2, -2, 0.0f, 0.3125f, 1, 0, 0, light);
		for (int u = 0; u < 4; ++u) {
			matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0f));
			this.vertex(matrix4f, matrix3f, vertexConsumer, -8, -2, 0, 0.0f, 0.0f, 0, 1, 0, light);
			this.vertex(matrix4f, matrix3f, vertexConsumer, 8, -2, 0, 0.5f, 0.0f, 0, 1, 0, light);
			this.vertex(matrix4f, matrix3f, vertexConsumer, 8, 2, 0, 0.5f, 0.15625f, 0, 1, 0, light);
			this.vertex(matrix4f, matrix3f, vertexConsumer, -8, 2, 0, 0.0f, 0.15625f, 0, 1, 0, light);
		}
		matrixStack.pop();
		super.render(entity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
	}

	public void vertex(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexConsumer, int x, int y, int z, float u, float v, int normalX, int normalZ, int normalY, int light) {
		vertexConsumer.vertex(positionMatrix, x, y, z).color(255, 255, 255, 255).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, normalX, normalY, normalZ).next();
	}
}
