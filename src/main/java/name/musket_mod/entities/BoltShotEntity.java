package name.musket_mod.entities;

import name.musket_mod.DamageTypes;
import name.musket_mod.Items;
import name.musket_mod.MusketMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.EntityFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class BoltShotEntity extends PersistentProjectileEntity
		implements MusketShotEntity {
	private static final ItemStack DEFAULT_STACK = new ItemStack(Items.BOLT_SHOT);
	public static final EntityFactory<BoltShotEntity> FACTORY = new Factory();
	public static final float SHOT_SPEED = 6.0f;
	public static final float BASE_DAMAGE_VALUE = 12f;
	private int durability = 5; // a bolt's durability equals how many "fragile" blocks (glass, glowstone) it can break.
	public BoltShotEntity(EntityType<? extends PersistentProjectileEntity> type, World world) {
		this(type, world, DEFAULT_STACK);
	}
	public BoltShotEntity(EntityType<? extends PersistentProjectileEntity> type, LivingEntity owner, World world) {
		this(type, owner, world, DEFAULT_STACK);
	}
	public BoltShotEntity(EntityType<? extends PersistentProjectileEntity> type, World world, ItemStack stack) {
		super(type, world, stack);
	}
	public BoltShotEntity(EntityType<? extends PersistentProjectileEntity> type, double x, double y, double z, World world, ItemStack stack) {
		super(type, x, y, z, world, stack);
	}
	public BoltShotEntity(EntityType<? extends PersistentProjectileEntity> type, LivingEntity owner, World world, ItemStack stack) {
		super(type, owner, world, stack);
	}
	@Override
	public void onCollision(HitResult hitResult) {
		if (this.getWorld().isClient) {
			// NOTE: If we don't ensure that the server thread is running this function
			// then we can get rendering bugs where there is no breaking particles when firing the shots.
			// For some reason, setting the environment type to server works,
			// but the shot entity is not discarded upon collision. Don't ask my why.
			return;
		}
		super.onCollision(hitResult);
		if (this.durability <= 0) {
			this.discard();
		}
	}
	@Override
	public void onEntityHit(EntityHitResult hitResult) {
		super.onEntityHit(hitResult);
		DamageSource source = DamageTypes.of(this.getWorld(), DamageTypes.MUSKET_SHOT);
		hitResult.getEntity().damage(source, BASE_DAMAGE_VALUE);
		hitResult.getEntity().onDamaged(source);
	}
	@Override
	public void onBlockHit(BlockHitResult hitResult) {
		super.onBlockHit(hitResult);
		if (this.getOwner() == null) return; // on world start, if an entity exists then it will cause crash since it has no owner
		int durabilityUse = MusketShotEntity.super.onBlockHit(hitResult, this.getWorld(),
				(this.getOwner().isPlayer())? (PlayerEntity)this.getOwner() : null);
		durability -= durabilityUse;
	}
	public static class Factory implements EntityFactory<BoltShotEntity> {
		@Override
		public BoltShotEntity create(EntityType<BoltShotEntity> type, World world) {
			return new BoltShotEntity(type, world);
		}
	}
}
