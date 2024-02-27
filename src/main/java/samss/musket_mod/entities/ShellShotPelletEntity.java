package samss.musket_mod.entities;

import samss.musket_mod.Entities;
import samss.musket_mod.Items;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;

public class ShellShotPelletEntity extends MusketShotEntity {
	public static final Factory FACTORY = new Factory();
	public ShellShotPelletEntity(EntityType<? extends ThrownItemEntity> type, World world) {
		super(type, world);
	}
	public ShellShotPelletEntity(EntityType<? extends ThrownItemEntity> type, LivingEntity owner, World world, int additionalDurability) {
		super(type, owner, world, additionalDurability);
	}
	@Override protected Item getDefaultItem() { return Items.SHELL_SHOT_PELLET; }
	@Override public float getShotSpeed() { return 3.0f; }
	@Override public float getBaseDamage() { return 12.0f; }
	public static class Factory implements EntityType.EntityFactory<ShellShotPelletEntity> {
		@Override
		public ShellShotPelletEntity create(EntityType<ShellShotPelletEntity> type, World world) {
			return new ShellShotPelletEntity(Entities.SHELL_SHOT_PELLET, world);
		}
	}
}
