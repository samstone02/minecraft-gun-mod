package name.gunmod.entities;

import name.gunmod.items.GunModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ShellShotPelletEntity extends ThrownEntity {
	@Override
	protected void initDataTracker() {
		// Thrown Entities do not need data trackers
	}

	public static class ShellShotPelletEntityFactory implements EntityType.EntityFactory<ShellShotPelletEntity> {
		@Override
		public ShellShotPelletEntity create(EntityType<ShellShotPelletEntity> type, World world) {
			return new ShellShotPelletEntity(Entities.SHELL_SHOT_PELLET, world);
		}
	}
	private static final ItemStack DEFAULT_STACK = new ItemStack(GunModItems.BOLT_SHOT);
	public static final ShellShotPelletEntityFactory FACTORY = new ShellShotPelletEntityFactory();
	public static final float SHOT_SPEED = 1.0f;

	public ShellShotPelletEntity(EntityType<? extends ThrownEntity> type, World world) {
		super(type, world);
		// TODO Auto-generated constructor stub
	}
//	public ShellShotPelletEntity(EntityType<? extends SnowballEntity> type, World world, ItemStack stack) {
//		super(type, world, stack);
//		// TODO Auto-generated constructor stub
//	}
//
//	public ShellShotPelletEntity(EntityType<? extends SnowballEntity> type, LivingEntity owner, World world,
//								 ItemStack stack) {
//		super(type, owner, world, stack);
//		// TODO Auto-generated constructor stub
//	}

	public ShellShotPelletEntity(EntityType<? extends ThrownEntity> type, double x, double y, double z,
								 World world) {
		super(type, x, y, z, world);
		// TODO Auto-generated constructor stub
	}

	public ShellShotPelletEntity(EntityType<? extends ThrownEntity> type, LivingEntity owner, World world) {
		super(type, owner, world);
		// TODO Auto-generated constructor stub
	}

}
