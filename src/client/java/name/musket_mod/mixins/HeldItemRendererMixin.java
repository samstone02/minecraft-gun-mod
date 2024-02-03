package name.musket_mod.mixins;

import name.musket_mod.Items;
import name.musket_mod.items.MusketItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Inject(method = "renderFirstPersonItem(" +
            "Lnet/minecraft/client/network/AbstractClientPlayerEntity;" +
            "FFLnet/minecraft/util/Hand;" +
            "FLnet/minecraft/item/ItemStack;" +
            "FLnet/minecraft/client/util/math/MatrixStack;" +
            "Lnet/minecraft/client/render/VertexConsumerProvider;" +
            "I)V",
            at = @At("HEAD")

////                    value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEquipOffset(" +
//                "Lnet/minecraft/client/util/math/MatrixStack;" +
//                "Lnet/minecraft/util/Arm;" +
//                "F)V", shift = At.Shift.BEFORE, ordinal = 2)
    )
    public void renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand,
                                      float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices,
                                      VertexConsumerProvider vertexConsumers, int light, CallbackInfo callable) {
        if (item.getItem() == Items.MUSKET) {
            MusketItem musket = (MusketItem) item.getItem();
            if (musket.getState() == 1) {
                shootAnimation(player, item, musket, matrices);
            }
            else if (musket.getState() == 2) {
                reloadAnimation(player, item, musket, matrices);
            }
            musket.animationTick();
        }
    }

    public void shootAnimation(PlayerEntity player, ItemStack itemStack, MusketItem musket, MatrixStack matrices) {
        float progress = (float)(musket.getMaxUseTime(itemStack) - musket.getCurrentAnimationTime()) / musket.getMaxUseTime(itemStack);
//        MusketMod.LOGGER.info("shoot progress: " + progress);
        progress *= 0.1f;

        matrices.translate(0,  0, progress);
//        Quaternionf q = new Quaternionf();
//        q.rotateLocalX(progress);
    }

    public void reloadAnimation(PlayerEntity player, ItemStack itemStack, MusketItem musket, MatrixStack matrices) {
        float progress = (float)(musket.getMaxUseTime(itemStack) - musket.getCurrentAnimationTime()) / musket.getMaxUseTime(itemStack);
//        MusketMod.LOGGER.info("reload progress: " + progress);
        progress *= 0.05f;

        matrices.translate(progress, progress, 0);
//        Quaternionf q = new Quaternionf();
//        q.rotateLocalX(progress);
    }
//
//    @Redirect(method = "renderFirstPersonItem(" +
//            "Lnet/minecraft/client/network/AbstractClientPlayerEntity;" +
//            "FFLnet/minecraft/util/Hand;" +
//            "FLnet/minecraft/item/ItemStack;" +
//            "FLnet/minecraft/client/util/math/MatrixStack;" +
//            "Lnet/minecraft/client/render/VertexConsumerProvider;" +
//            "I)V",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getUseAction()Lnet/minecraft/util/UseAction;", ordinal = 0)
//    )
//    public UseAction testInvokeRedirect(ItemStack item, AbstractClientPlayerEntity player) {
//        MusketMod.LOGGER.info("Client-side mixin test inject: " + item.isEmpty() + item.isOf(net.minecraft.item.Items.FILLED_MAP) + item.isOf(net.minecraft.item.Items.CROSSBOW) + player.isUsingItem());
//        return UseAction.NONE;
//    }
}