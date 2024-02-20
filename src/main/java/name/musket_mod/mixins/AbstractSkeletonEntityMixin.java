package name.musket_mod.mixins;

import name.musket_mod.Items;
import name.musket_mod.MusketMod;
import name.musket_mod.entities.ai.MusketAttackGoal;
import name.musket_mod.items.MusketItem;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.lang.reflect.Field;

@Mixin(AbstractSkeletonEntity.class)
public class AbstractSkeletonEntityMixin {
    @Inject(method = "updateAttackType()V",
            at = @At(value = "TAIL", shift = At.Shift.BEFORE),
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void updateAttackType(CallbackInfo callback, ItemStack itemStack) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        AbstractSkeletonEntity injectee = (AbstractSkeletonEntity)(Object) this;

        if (injectee.getWorld() != null && !injectee.getWorld().isClient) {
            MusketMod.LOGGER.info("we are doing");

            itemStack = injectee.getStackInHand(ProjectileUtil.getHandPossiblyHolding(injectee, Items.MUSKET));

            Field field1 = injectee.getClass().getField("goalSelector");
            field1.setAccessible(true);
            GoalSelector goalSelector = (GoalSelector) field1.get(injectee);

            MusketMod.LOGGER.info("accessed targetSelector");
            MusketMod.LOGGER.info(itemStack.toString());

            if (itemStack.isOf(Items.MUSKET)) {
                MusketMod.LOGGER.info("adding musket attack goal");
                goalSelector.add(4, new MusketAttackGoal<>(injectee, 1.0, MusketItem.RELOAD_ANIMATION_TICKS, 25f));
            }
        }
    }
}
