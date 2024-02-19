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
	@Override
	public String getId() {
		return ITEM_ID;
	}
	@Override
	public ProjectileEntity instantiateShotEntity(World world, LivingEntity owner) {
		return new RoundShotEntity(Entities.ROUND_SHOT, owner, world);
	}
}
