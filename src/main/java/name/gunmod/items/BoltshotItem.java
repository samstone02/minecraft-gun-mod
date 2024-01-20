package name.gunmod.items;

import name.gunmod.entities.BoltShotEntity;
import name.gunmod.entities.Entities;
import name.gunmod.entities.MusketShootable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BoltshotItem extends Item implements MusketShootable {
	public static final String ITEM_ID = "bolt_shot";
	public BoltshotItem(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}

	public ProjectileEntity onShoot(World world, LivingEntity owner) {
		BoltShotEntity shot = new BoltShotEntity(Entities.BOLT_SHOT, owner, world);
		shot.setPos(owner.getX(), owner.getEyeY() - 0.15f, owner.getZ());
		shot.setVelocity(owner, owner.getPitch(), owner.getYaw(), 0.0f, BoltShotEntity.SHOT_SPEED, 1.0f);
		world.spawnEntity(shot);
		return shot;
	}
}
