package name.musket_mod.mixins;

import name.musket_mod.Items;
import name.musket_mod.animation_parameters.MusketAPs;
import name.musket_mod.items.MusketItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Inject(method = "renderFirstPersonItem(" +
            "Lnet/minecraft/client/network/AbstractClientPlayerEntity;" +
            "FFLnet/minecraft/util/Hand;" +
            "FLnet/minecraft/item/ItemStack;" +
            "FLnet/minecraft/client/util/math/MatrixStack;" +
            "Lnet/minecraft/client/render/VertexConsumerProvider;" +
            "I)V", at = @At("HEAD")
    )
    public void renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand,
                                      float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices,
                                      VertexConsumerProvider vertexConsumers, int light, CallbackInfo callable) {
        if (player.isUsingItem() && item.getItem() == Items.MUSKET) {
            MusketItem musket = (MusketItem) item.getItem();
            ItemStack stack = player.getStackInHand(hand);

            float progress = (1f * stack.getMaxUseTime() - player.getItemUseTimeLeft()) / stack.getMaxUseTime();

            if (musket.getState(stack).equals("SHOOTING")) {
                shootAnimation(progress, matrices);
            }
            else if (musket.getState(stack).equals("RELOADING")) {
                reloadAnimation(progress, matrices);
            }
        }
    }
    public void shootAnimation(float progress, MatrixStack matrices) {
        if (progress < MusketAPs.RECOIL_THRESHOLD) {
            float position = progress * MusketAPs.RECOIL_TRANSLATION_CONVERSION;
            float rotation = progress * MusketAPs.RECOIL_ROTATION_CONVERSION;
            matrices.translate(0, 0, position);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(rotation));
        }
        else {
            float pos = (progress - MusketAPs.RECOIL_THRESHOLD) * MusketAPs.RECOVERY_TRANSLATION_CONVERSION;
            float rotation = (progress - MusketAPs.RECOIL_THRESHOLD) * MusketAPs.RECOVERY_ROTATION_CONVERSION;
            matrices.translate(0, 0, (MusketAPs.RECOIL_THRESHOLD * MusketAPs.RECOIL_TRANSLATION_CONVERSION) - pos);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((MusketAPs.RECOIL_THRESHOLD * MusketAPs.RECOIL_ROTATION_CONVERSION) - rotation));
        }
    }
    public void reloadAnimation(float progress, MatrixStack matrices) {
        if (progress < MusketAPs.RELOAD_THRESHOLD) {
            float moveX = progress * MusketAPs.RELOAD_WINDUP_TRANSLATION_CONVERSION_X;
            float moveY = progress * MusketAPs.RELOAD_WINDUP_TRANSLATION_CONVERSION_Y;
            float rotateX = progress * MusketAPs.READY_RELOAD_ROTATION_CONVERSION_X;
            float rotateY = progress * MusketAPs.READY_RELOAD_ROTATION_CONVERSION_Y;

            matrices.translate(moveX, moveY, -0.8);

            matrices.translate(1.0, -0.5, -0.25);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotateY));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(rotateX));
            matrices.translate(-1.0, 0.5, 0.25);
        }
        else {
            float posX = MusketAPs.RELOAD_THRESHOLD * MusketAPs.RELOAD_WINDUP_TRANSLATION_CONVERSION_X;
            float posY = MusketAPs.RELOAD_THRESHOLD * MusketAPs.RELOAD_WINDUP_TRANSLATION_CONVERSION_Y;
            float rotationX = MusketAPs.RELOAD_THRESHOLD * MusketAPs.READY_RELOAD_ROTATION_CONVERSION_X;
            float rotationY = MusketAPs.RELOAD_THRESHOLD * MusketAPs.READY_RELOAD_ROTATION_CONVERSION_Y;

            float offset = MusketAPs.SHAKE_INTENSITY * MathHelper.sin(MusketAPs.SHAKE_SPEED * (MusketAPs.RELOAD_THRESHOLD - progress));

            matrices.translate(posX, posY + offset, -0.8);

            matrices.translate(1.0, -0.5, -0.25);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotationY));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(rotationX));
            matrices.translate(-1.0, 0.5, 0.25);
        }
    }
}