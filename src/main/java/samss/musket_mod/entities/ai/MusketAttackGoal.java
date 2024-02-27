package samss.musket_mod.entities.ai;

import samss.musket_mod.Items;
import samss.musket_mod.enchantments.Enchantments;
import samss.musket_mod.items.MusketItem;
import samss.musket_mod.items.MusketShootable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;

public class MusketAttackGoal<T extends HostileEntity> extends Goal {
    //<editor-fold desc="Constructed Members">
    private final T actor;
    private final double speed;
    private final int attackInterval;
    private final float squaredRange;
    //</editor-fold>
    //<editor-fold desc="Tracking Members">
    private float combatTicks;
    private boolean movingToLeft = false;
    private boolean backward = false;
    private int cooldown = 0;
    private int targetSeeingTicker = 0;
    //</editor-fold>
    public MusketAttackGoal(T actor, double speed, int attackInterval, float range) {
        this.actor = actor;
        this.speed = speed;
        this.attackInterval = attackInterval;
        this.squaredRange = range * range;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }
    @Override
    public boolean canStart() {
//        if (((MobEntity)this.actor).getTarget() == null) {
        if (this.actor.getTarget() == null) {
            return false;
        }
        return this.actor.getMainHandStack().isOf(Items.MUSKET);
    }

    @Override
    public void start() {
        super.start();
        this.actor.setAttacking(true);
    }
    @Override
    public void stop() {
        super.stop();
        this.actor.setAttacking(false);
        this.targetSeeingTicker = 0;
        this.cooldown = -1;
        this.actor.clearActiveItem();
    }
    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }
//    @Override
//    public void tick() {
//        LivingEntity target = this.actor.getTarget();
//
//        if (target == null) {
//            MusketMod.LOGGER.info("TICK :: TARGET IS NULL");
//            return;
//        }
//
//        double d = Math.pow(this.actor.getPos().distanceTo(target.getPos()), 2);
//        boolean canSee = this.actor.getVisibilityCache().canSee(target);
//
//        boolean isLoosingInterest = this.loseInterestTicker > 0;
//        if (canSee != isLoosingInterest) {
//            this.loseInterestTicker = 0;
//        }
//        this.loseInterestTicker = isLoosingInterest ? ++this.loseInterestTicker : --this.loseInterestTicker;
//
//        if (bl) {
//            ++this.targetSeeingTicker;
//        } else {
//            --this.targetSeeingTicker;
//        }
//
//        if (d > (double)this.squaredRange || this.loseInterestTicker < 20) {
//            /* CONTINUE COMBAT */
//            this.combatTicks = -1;
//
//            this.actor.getNavigation().startMovingTo(target, this.speed);
//        } else {
//            /* DON'T COMBAT */
//            this.actor.getNavigation().stop();
//            ++this.combatTicks;
//        }
//
//        if (this.combatTicks >= 20) {
//            if ((double)((LivingEntity)this.actor).getRandom().nextFloat() < 0.3) {
//                boolean bl4 = this.movingToLeft = !this.movingToLeft;
//            }
//            if ((double)((LivingEntity)this.actor).getRandom().nextFloat() < 0.3) {
//                this.backward = !this.backward;
//            }
//            this.combatTicks = 0;
//        }
//
//        if (this.combatTicks > -1) {
//            if (d > (double)(this.squaredRange * 0.75f)) {
//                this.backward = false;
//            } else if (d < (double)(this.squaredRange * 0.25f)) {
//                this.backward = true;
//            }
//            this.actor.getMoveControl().strafeTo(this.backward ? -0.5f : 0.5f, this.movingToLeft ? 0.5f : -0.5f);
//            Entity entity = this.actor.getControllingVehicle();
//            if (entity instanceof MobEntity) {
//                MobEntity mobEntity = (MobEntity)entity;
//                mobEntity.lookAtEntity(target, 30.0f, 30.0f);
//            }
//            this.actor.lookAtEntity(target, 30.0f, 30.0f);
//        } else {
//            this.actor.getLookControl().lookAt(target, 30.0f, 30.0f);
//        }
//
//        if (((LivingEntity)this.actor).isUsingItem()) {
//            int i;
//            if (!canSee && this.loseInterestTicker < -60) {
//                this.actor.clearActiveItem();
//            } else if (canSee && (i = this.actor.getItemUseTime()) >= 20) {
//                this.actor.clearActiveItem();
//                ((RangedAttackMob)this.actor).shootAt(target, 1);
//                this.cooldown = this.attackInterval;
//            }
//        } else if (--this.cooldown <= 0 && this.loseInterestTicker >= -60) {
//            this.actor.setCurrentHand(ProjectileUtil.getHandPossiblyHolding(this.actor, Items.MUSKET));
//        }
//    }
    @Override
    public void tick() {
        LivingEntity target = this.actor.getTarget();
        if (target == null) {
            return;
        }

        double d = this.actor.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
        boolean canSee = this.actor.getVisibilityCache().canSee(target);
        boolean targetSeeking = this.targetSeeingTicker > 0;

        if (canSee != targetSeeking) {
            this.targetSeeingTicker = 0;
        }

        if (canSee) {
            ++this.targetSeeingTicker;
        } else {
            --this.targetSeeingTicker;
        }

        if (!(d > (double)this.squaredRange) && this.targetSeeingTicker >= 20) {
            this.actor.getNavigation().stop();
            ++this.combatTicks;
        } else {
            this.actor.getNavigation().startMovingTo(target, this.speed);
            this.combatTicks = -1;
        }

        if (this.combatTicks >= 20) {
            if ((double)this.actor.getRandom().nextFloat() < 0.3) {
                this.movingToLeft = !this.movingToLeft;
            }

            if ((double)this.actor.getRandom().nextFloat() < 0.3) {
                this.backward = !this.backward;
            }

            this.combatTicks = 0;
        }

        if (this.combatTicks > -1) {
            if (d > (double)(this.squaredRange * 0.75F)) {
                this.backward = false;
            } else if (d < (double)(this.squaredRange * 0.25F)) {
                this.backward = true;
            }

            this.actor.getMoveControl().strafeTo(this.backward ? -0.5F : 0.5F, this.movingToLeft ? 0.5F : -0.5F);
            Entity var7 = this.actor.getControllingVehicle();
            if (var7 instanceof MobEntity) {
                MobEntity mobEntity = (MobEntity)var7;
                mobEntity.lookAtEntity(target, 30.0F, 30.0F);
            }

            this.actor.lookAtEntity(target, 30.0F, 30.0F);
        } else {
            this.actor.getLookControl().lookAt(target, 30.0F, 30.0F);
        }

        if (this.actor.isUsingItem()) {
            if (!canSee && this.targetSeeingTicker < -60) {
                this.actor.clearActiveItem();
            } else if (canSee) {
                int i = this.actor.getItemUseTime();
                if (i >= 20) {
                    this.actor.clearActiveItem();
                    shootMusket((AbstractSkeletonEntity) this.actor, target);
                    this.cooldown = this.attackInterval;
                }
            }
        } else if (--this.cooldown <= 0 && this.targetSeeingTicker >= -60) {
            this.actor.setCurrentHand(ProjectileUtil.getHandPossiblyHolding(this.actor, Items.MUSKET));
        }
    }
    public void shootMusket(AbstractSkeletonEntity skeleton, LivingEntity target) {
        ItemStack musketStack = skeleton.getStackInHand(ProjectileUtil.getHandPossiblyHolding(skeleton, Items.MUSKET));
        ItemStack shotStack = skeleton.getOffHandStack();
        if (shotStack == null || !(shotStack.getItem() instanceof MusketShootable)) {
            shotStack = new ItemStack(Items.ROUND_SHOT);
        }

        int silencingLvl = EnchantmentHelper.getLevel(Enchantments.SILENCING, musketStack);

        ((MusketItem) musketStack.getItem()).createMuzzleFlash(skeleton.getWorld(), skeleton, silencingLvl >= 1);
        ((MusketShootable) shotStack.getItem()).onEntityShoot(skeleton.getWorld(), skeleton, target);
    }
}
