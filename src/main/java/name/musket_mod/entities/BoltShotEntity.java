package name.musket_mod.entities;

import name.musket_mod.DamageTypes;
import name.musket_mod.Items;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.EntityFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class BoltShotEntity extends PersistentProjectileEntity
		implements MusketShotEntity {
	private static final ItemStack DEFAULT_STACK = new ItemStack(Items.BOLT_SHOT);
	public static final EntityFactory<BoltShotEntity> FACTORY = new Factory();
	public static final float BASE_DAMAGE_VALUE = 12f;
	private int durability = 5; // a bolt's durability equals how many "fragile" blocks (glass, glowstone) it can break.
	//<editor-fold desc="Constructors">
	public BoltShotEntity(EntityType<? extends PersistentProjectileEntity> type, World world) {
		this(type, world, DEFAULT_STACK);
	}
	public BoltShotEntity(EntityType<? extends PersistentProjectileEntity> type, LivingEntity owner, World world, int durabilityLvl) {
		this(type, owner, world, DEFAULT_STACK, durabilityLvl);
	}
	public BoltShotEntity(EntityType<? extends PersistentProjectileEntity> type, World world, ItemStack stack) {
		super(type, world, stack);
	}
	public BoltShotEntity(EntityType<? extends PersistentProjectileEntity> type, LivingEntity owner, World world, ItemStack stack, int durabilityLvl) {
		super(type, owner, world, stack);
		this.durability += durabilityLvl * 3;
	}
	//</editor-fold>
	public float getShotSpeed() { return  4.0f; }
	@Override
	public void onCollision(HitResult hitResult) {
		if (this.getWorld().isClient) {
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
		this.durability = 0;
	}
	@Override
	public void onBlockHit(BlockHitResult hitResult) {
		super.onBlockHit(hitResult);
		if (this.getOwner() == null) return; // on world start, if an entity exists then it will cause crash since it has no owner
		this.durability -= MusketShotEntity.super.onBlockHit(hitResult, this.getWorld(),
				(this.getOwner().isPlayer())? (PlayerEntity)this.getOwner() : null);
	}
	public static class Factory implements EntityFactory<BoltShotEntity> {
		@Override
		public BoltShotEntity create(EntityType<BoltShotEntity> type, World world) {
			return new BoltShotEntity(type, world);
		}
	}
}
