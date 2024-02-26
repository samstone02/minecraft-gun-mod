package name.musket_mod.entities;

import name.musket_mod.Entities;
import name.musket_mod.Items;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class RoundShotEntity extends MusketShotEntity {
	public static final Factory FACTORY = new Factory();
	//<editor-fold desc="Constructors">
	public RoundShotEntity(EntityType<? extends ThrownItemEntity> type, World world) {
		super(type, world);
	}
	public RoundShotEntity(EntityType<? extends ThrownItemEntity> type, LivingEntity owner, World world, int additionalDurability) {
		super(type, owner, world, additionalDurability);
	}
	//</editor-fold>
	//<editor-fold desc="Constructors">
	@Override protected Item getDefaultItem() { return Items.ROUND_SHOT; }
	@Override public float getShotSpeed() { return 3.0f; }
	@Override public float getBaseDamage() { return 9f; }
	//</editor-fold>
	public static class Factory implements EntityType.EntityFactory<RoundShotEntity> {
		@Override
		public RoundShotEntity create(EntityType<RoundShotEntity> type, World world) {
			return new RoundShotEntity(Entities.ROUND_SHOT, world);
		}
	}
}
