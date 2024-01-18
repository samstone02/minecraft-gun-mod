package name.gunmod.items;

import name.gunmod.entities.BoltShotEntity;
import name.gunmod.entities.Entities;
import name.gunmod.entities.MusketShootable;
import name.gunmod.entities.ShellShotPelletEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ShellShotItem extends Item implements MusketShootable {
	public static final String ITEM_ID = "shell_shot";
	private static final int pelletsPerShell = 5;
	public ShellShotItem(Settings settings) {
		super(settings);
	}

	public ProjectileEntity onShoot(World world, LivingEntity owner) {
		for (int i = 0; i < pelletsPerShell; i++) {
			ShellShotPelletEntity shot = new ShellShotPelletEntity(Entities.SHELL_SHOT_PELLET, owner, world);
			shot.setPos(owner.getX(), owner.getEyeY() - 0.15f, owner.getZ());
			shot.setVelocity(owner, owner.getPitch(), owner.getYaw(), 0.0f, ShellShotPelletEntity.SHOT_STRENGTH, 15.0f);
			world.spawnEntity(shot);
		}
		return null; // TODO: Update return type of onShoot? A list?
	}
}