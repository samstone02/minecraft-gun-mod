package name.musket_mod.entities;

import name.musket_mod.Items;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.EntityFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BoltShotEntity extends MusketShotEntity {
	public static final Factory FACTORY = new Factory();
	//<editor-fold desc="Constructors">
	public BoltShotEntity(EntityType<? extends ThrownItemEntity> type, World world) {
		super(type, world);
	}
	public BoltShotEntity(EntityType<? extends ThrownItemEntity> type, LivingEntity owner, World world, int durabilityLvl) {
		super(type, owner, world, 4 + durabilityLvl * 3);
	}
	//</editor-fold>
	//<editor-fold desc="Getters">
	@Override protected Item getDefaultItem() { return Items.BOLT_SHOT; }
	@Override public float getShotSpeed() { return  3.5f; }
	@Override public float getBaseDamage() { return  12f; }
	//</editor-fold>
	public static class Factory implements EntityFactory<BoltShotEntity> {
		@Override
		public BoltShotEntity create(EntityType<BoltShotEntity> type, World world) {
			return new BoltShotEntity(type, world);
		}
	}
}
