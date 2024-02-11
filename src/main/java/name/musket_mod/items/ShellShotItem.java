package name.musket_mod.items;

import name.musket_mod.Entities;
import name.musket_mod.entities.ShellShotPelletEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ShellShotItem extends Item implements MusketShootable {
	public static final String ITEM_ID = "shell_shot";
	private static final int pelletsPerShell = 10;
	private static final float DIVERGENCE = 10.0f;
	public ShellShotItem(Settings settings) {
		super(settings);
	}
	public ProjectileEntity onShoot(World world, LivingEntity owner) {
		for (int i = 0; i < pelletsPerShell; i++) {
			ShellShotPelletEntity shot = new ShellShotPelletEntity(Entities.SHELL_SHOT_PELLET, owner, world);
			shot.setPos(owner.getX(), owner.getEyeY() - 0.15f, owner.getZ());
			shot.setVelocity(owner, owner.getPitch(), owner.getYaw(), 0.0f, ShellShotPelletEntity.SHOT_SPEED, DIVERGENCE);
			world.spawnEntity(shot);
		}
		return null;
		// TODO: Why isn't the shell shot working properly? It seems like only one entity is colliding with enemies...
	}
	@Override
	public String getId() {
		return ITEM_ID;
	}
}
