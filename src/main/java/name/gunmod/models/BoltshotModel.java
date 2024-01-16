package name.gunmod.models;

public class BoltshotModel {
	
}

//package name.gunmod.models;
//
//import java.util.function.Function;
//
//import name.gunmod.entities.BoltshotEntity;
//import net.minecraft.client.model.Model;
//import net.minecraft.client.model.ModelData;
//import net.minecraft.client.model.ModelPart;
//import net.minecraft.client.model.ModelPartBuilder;
//import net.minecraft.client.model.ModelPartData;
//import net.minecraft.client.model.ModelTransform;
//import net.minecraft.client.model.TexturedModelData;
//import net.minecraft.client.render.RenderLayer;
//import net.minecraft.client.render.VertexConsumer;
//import net.minecraft.client.render.entity.model.EntityModel;
//import net.minecraft.client.render.entity.model.EntityModelPartNames;
//import net.minecraft.client.util.math.MatrixStack;
//import net.minecraft.entity.Entity;
//import net.minecraft.util.Identifier;
//
//public class BoltshotModel extends EntityModel<BoltshotEntity> {
//	
//    private final ModelPart base;
//
//	public BoltshotModel(ModelPart modelPart) {
//		this.base = modelPart.getChild(EntityModelPartNames.CUBE);
//		ArrowEntityModel x = null;
//	}
//	
//    public static TexturedModelData getTexturedModelData() {
//        ModelData modelData = new ModelData();
//    	ModelPartData modelPartData = modelData.getRoot();
//        modelPartData.addChild(EntityModelPartNames.CUBE, ModelPartBuilder.create().uv(0, 0).cuboid(-6F, 12F, -6F, 12F, 12F, 12F), ModelTransform.pivot(0F, 0F, 0F));
//    }
//
//	@Override
//	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
//			float blue, float alpha) {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public void setAngles(BoltshotEntity entity, float limbAngle, float limbDistance, float animationProgress,
//			float headYaw, float headPitch) {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
