package name.musket_mod.items;

import name.musket_mod.Entities;
import name.musket_mod.entities.ShellShotPelletEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ShellShotItem extends Item implements MusketShootable {
	public static final String ITEM_ID = "shell_shot";
	private static final int pelletsPerShell = 10;
	public ShellShotItem(Settings settings) {
		super(settings);
	}
	@Override
	public String getId() {
		return ITEM_ID;
	}
	@Override
	public float getDispersion() { return 15.0f; }
	@Override
	public void onPlayerShoot(World world, LivingEntity owner, ItemStack musketStack) {
		for (int i = 0; i < pelletsPerShell; i++) {
			MusketShootable.super.onPlayerShoot(world, owner, owner.getMainHandStack());
		}
	}
	@Override
	public void onEntityShoot(World world, LivingEntity owner, LivingEntity target) {
		for (int i = 0; i < pelletsPerShell; i++) {
			MusketShootable.super.onEntityShoot(world, owner, target);
		}
	}
	@Override
	public ProjectileEntity instantiateShotEntity(World world, LivingEntity owner, int durabilityLvl) {
		return new ShellShotPelletEntity(Entities.SHELL_SHOT_PELLET, owner, world, durabilityLvl);
	}
}
