package name.gunmod.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ShotshellEntity extends PersistentProjectileEntity {

	public ShotshellEntity(EntityType<? extends PersistentProjectileEntity> type, World world, ItemStack stack) {
		super(type, world, stack);
		// TODO Auto-generated constructor stub
	}

	public ShotshellEntity(EntityType<? extends PersistentProjectileEntity> type, LivingEntity owner, World world,
			ItemStack stack) {
		super(type, owner, world, stack);
		// TODO Auto-generated constructor stub
	}

	public ShotshellEntity(EntityType<? extends PersistentProjectileEntity> type, double x, double y, double z,
			World world, ItemStack stack) {
		super(type, x, y, z, world, stack);
		// TODO Auto-generated constructor stub
	}

}
