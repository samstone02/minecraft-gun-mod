package name.musket_mod.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;

public interface MusketShootable {
	default ProjectileEntity onShoot(World world, LivingEntity owner) {
		// TODO: Add common shot functionality
		return null;
	}
}
