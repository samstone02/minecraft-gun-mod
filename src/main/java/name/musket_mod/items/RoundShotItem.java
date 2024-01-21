package name.musket_mod.items;

import name.musket_mod.Entities;
import name.musket_mod.entities.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class RoundShotItem extends Item implements MusketShootable {
	public static final String ITEM_ID = "round_shot";
	public RoundShotItem(Settings settings) {
		super(settings);
	}

	public ProjectileEntity onShoot(World world, LivingEntity owner) {
		RoundShotEntity shot = new RoundShotEntity(Entities.ROUND_SHOT, owner, world);
		shot.setPos(owner.getX(), owner.getEyeY() - 0.15f, owner.getZ());
		shot.setVelocity(owner, owner.getPitch(), owner.getYaw(), 0.0f, RoundShotEntity.SHOT_SPEED, 1.0f);
		world.spawnEntity(shot);

		return null; // TODO: Update return type of onShoot?
	}
}
