package name.musket_mod.items;

import name.musket_mod.MusketMod;
import name.musket_mod.enchantments.Enchantments;
import name.musket_mod.enchantments.OvercapacityEnchantment;
import name.musket_mod.entities.MusketShotEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public interface MusketShootable {
	default void onPlayerShoot(World world, LivingEntity owner, ItemStack musketStack) {
		float velocityModifier = 1;
		for (NbtElement enchantment : musketStack.getEnchantments()) {
			NbtCompound compound = (NbtCompound) enchantment;
			if (compound.getString("id").equals(MusketMod.MOD_ID + ":" + Enchantments.VELOCITY.getEnchantmentId())) {
				int lvl = compound.getShort("lvl");
				velocityModifier = Enchantments.VELOCITY.getVelocityModifierForLevel(lvl);
			}
		}
		ProjectileEntity shot = instantiateShotEntity(world, owner);
		shot.setPos(owner.getX(), owner.getEyeY() - 0.15f, owner.getZ());
		shot.setVelocity(owner, owner.getPitch(), owner.getYaw(), 0.0f, ((MusketShotEntity) shot).getShotSpeed() * velocityModifier, 1.0f);
		world.spawnEntity(shot);
	}
	default void onEntityShoot(World world, LivingEntity owner, LivingEntity target) {
		ProjectileEntity shot = instantiateShotEntity(world, owner);

		shot.setPos(owner.getX(), owner.getEyeY() - 0.15f, owner.getZ());
		double d = target.getX() - owner.getX();
		double e = target.getBodyY(0.3333333333333333) - owner.getEyeY();
		double f = target.getZ() - owner.getZ();

		double g = Math.sqrt(d * d + f * f);
		shot.setVelocity(d, e + g * 0.1, f, ((MusketShotEntity) shot).getShotSpeed(), (float)(14 - world.getDifficulty().getId() * 2));

		owner.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (owner.getRandom().nextFloat() * 0.4F + 0.8F));
		world.spawnEntity(shot);
	}
	String getId();
	ProjectileEntity instantiateShotEntity(World world, LivingEntity owner);
}
