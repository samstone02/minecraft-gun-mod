package name.gunmod.items;

import name.gunmod.entities.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class RoundshotItem extends Item implements MusketShootable {
	public static final String ITEM_ID = "round_shot";
	public RoundshotItem(Settings settings) {
		super(settings);
	}

	public ProjectileEntity onShoot(World world, LivingEntity owner) {
		ShellShotPelletEntity shot = new ShellShotPelletEntity(Entities.ROUND_SHOT, owner, world);
		shot.setPos(owner.getX(), owner.getEyeY() - 0.15f, owner.getZ());
		shot.setVelocity(owner, owner.getPitch(), owner.getYaw(), 0.0f, RoundShotEntity.SHOT_SPEED, 1.0f);
		world.spawnEntity(shot);

		return null; // TODO: Update return type of onShoot?
	}
}
