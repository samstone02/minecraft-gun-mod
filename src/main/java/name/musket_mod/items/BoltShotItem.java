package name.musket_mod.items;

import name.musket_mod.entities.BoltShotEntity;
import name.musket_mod.Entities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BoltShotItem extends Item implements MusketShootable {
	public static final String ITEM_ID = "bolt_shot";
	public BoltShotItem(Settings settings) {
		super(settings);
	}
	public ProjectileEntity onShoot(World world, LivingEntity owner) {
		BoltShotEntity shot = new BoltShotEntity(Entities.BOLT_SHOT, owner, world);
		shot.setPos(owner.getX(), owner.getEyeY() - 0.15f, owner.getZ());
		shot.setVelocity(owner, owner.getPitch(), owner.getYaw(), 0.0f, BoltShotEntity.SHOT_SPEED, 1.0f);
		world.spawnEntity(shot);
		return shot;
	}
}
