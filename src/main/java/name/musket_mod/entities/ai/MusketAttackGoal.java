package name.musket_mod.entities.ai;

import name.musket_mod.Items;
import name.musket_mod.items.MusketItem;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;

public class MusketAttackGoal<T extends HostileEntity> extends Goal {
    private T actor;
    private double speed;
    private float attackInterval;
    private float rangeSquared;
    public MusketAttackGoal(T actor, double speed, float attackInterval, float range) {
        this.actor = actor;
        this.speed = speed;
        this.attackInterval = attackInterval;
        this.rangeSquared = range * range;
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
        super.start();
        this.actor.setAttacking(false);
    }
    @Override
    public void tick() {
        // TODO: SHOOTING LOGIC HERE

        // MOVE TOWARDS PLAYER
        double d = Math.pow(this.actor.getPos().distanceTo(this.actor.getTarget().getPos()), 2);

        if (d < rangeSquared) {
            this.actor.getNavigation().startMovingTo(this.actor.getTarget(), this.speed);
            this.actor.setTarget(null);
        }

        // MOVE IN RANDOM DIRECTION
    }
}
