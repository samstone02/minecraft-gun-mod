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
	@Override
	public String getId() {
		return ITEM_ID;
	}
	public ProjectileEntity instantiateShotEntity(World world, LivingEntity owner) {
		return new BoltShotEntity(Entities.BOLT_SHOT, owner, world);
	}
}
