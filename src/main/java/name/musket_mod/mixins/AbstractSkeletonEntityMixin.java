package name.musket_mod.mixins;

import name.musket_mod.Items;
import name.musket_mod.MusketMod;
import name.musket_mod.entities.ai.MusketAttackGoal;
import name.musket_mod.items.MusketItem;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.world.Difficulty;
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
    public void updateAttackType(CallbackInfo callback, ItemStack itemStack) throws NoSuchFieldException, IllegalAccessException {
        MusketMod.LOGGER.info("injecting");

        AbstractSkeletonEntity injectee = (AbstractSkeletonEntity)(Object) this;

        itemStack = injectee.getStackInHand(ProjectileUtil.getHandPossiblyHolding(injectee, Items.MUSKET));

        Field field1 = injectee.getClass().getField("targetSelector");
        field1.setAccessible(true);
        GoalSelector goalSelector = (GoalSelector) field1.get(injectee);

        MusketMod.LOGGER.info("accessed targetSelector");
        MusketMod.LOGGER.info(itemStack.toString());

        if (itemStack.isOf(Items.MUSKET)) {

            int i = 20;
            if (injectee.getWorld().getDifficulty() != Difficulty.HARD) {
                i = 40;
            }
//            self.bowAttackGoal.setAttackInterval(i);
//            goalSelector.add(4, this.bowAttackGoal);
            try {
                goalSelector.add(4, new MusketAttackGoal<>(injectee, 1.0, MusketItem.RELOAD_ANIMATION_TICKS, 25f));

                MusketMod.LOGGER.info("added musket attack goal");
            }
            catch (Exception ex) {
                MusketMod.LOGGER.info(ex.toString());
            }
        } else {
            try {
                Field field2 = injectee.getClass().getField("meleeAttackGoal");
                field2.setAccessible(true);
                MeleeAttackGoal meleeAttackGoal = (MeleeAttackGoal) field2.get(injectee);

                goalSelector.add(4, meleeAttackGoal);
            } catch (Exception ex) {
                MusketMod.LOGGER.info(ex.toString());
            }
        }
    }
}
