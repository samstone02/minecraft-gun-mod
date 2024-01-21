package name.musket_mod.entities;

import name.musket_mod.Entities;
import name.musket_mod.Items;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;

public class ShellShotPelletEntity extends ThrownItemEntity {
	public static final ShellShotPelletEntityFactory FACTORY = new ShellShotPelletEntityFactory();
	public static final float SHOT_SPEED = 1.0f;
	public ShellShotPelletEntity(EntityType<? extends ThrownItemEntity> type, World world) {
		super(type, world);
	}
	public ShellShotPelletEntity(EntityType<? extends ThrownItemEntity> type, LivingEntity owner, World world) {
		super(type, owner, world);
	}
	@Override
	protected Item getDefaultItem() {
		return Items.SHELL_SHOT_PELLET;
	}
	public static class ShellShotPelletEntityFactory implements EntityType.EntityFactory<ShellShotPelletEntity> {
		@Override
		public ShellShotPelletEntity create(EntityType<ShellShotPelletEntity> type, World world) {
			return new ShellShotPelletEntity(Entities.SHELL_SHOT_PELLET, world);
		}
	}
}
