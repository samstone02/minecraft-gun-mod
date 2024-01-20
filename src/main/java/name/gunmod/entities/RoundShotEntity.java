package name.gunmod.entities;

import name.gunmod.Particles;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RoundShotEntity extends ThrownEntity {
	public static final Factory FACTORY = new Factory();
	public static final float SHOT_SPEED = 2.0f;
	protected RoundShotEntity(EntityType<? extends ThrownEntity> entityType, World world) {
		super(entityType, world);
	}

	protected RoundShotEntity(EntityType<? extends ThrownEntity> type, double x, double y, double z, World world) {
		super(type, x, y, z, world);
	}

	protected RoundShotEntity(EntityType<? extends ThrownEntity> type, LivingEntity owner, World world) {
		super(type, owner, world);
	}

	@Override
	protected void initDataTracker() { /* Thrown Entities do not need data trackers */ }

	public static class Factory implements EntityType.EntityFactory<RoundShotEntity> {
		@Override
		public RoundShotEntity create(EntityType<RoundShotEntity> type, World world) {
			return new RoundShotEntity(Entities.ROUND_SHOT, world);
		}
	}
}
