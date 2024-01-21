package name.musket_mod.entities;

import name.musket_mod.Entities;
import name.musket_mod.Items;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class RoundShotEntity extends ThrownItemEntity {
	public static final Factory FACTORY = new Factory();
	public static final float SHOT_SPEED = 2.0f;
	public RoundShotEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}
	public RoundShotEntity(EntityType<? extends ThrownItemEntity> type, LivingEntity owner, World world) {
		super(type, owner, world);
	}
	@Override
	protected Item getDefaultItem() {
		return Items.ROUND_SHOT;
	}
	public static class Factory implements EntityType.EntityFactory<RoundShotEntity> {
		@Override
		public RoundShotEntity create(EntityType<RoundShotEntity> type, World world) {
			return new RoundShotEntity(Entities.ROUND_SHOT, world);
		}
	}
}
