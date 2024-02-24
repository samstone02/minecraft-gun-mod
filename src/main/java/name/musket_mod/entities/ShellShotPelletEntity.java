package name.musket_mod.entities;

import name.musket_mod.DamageTypes;
import name.musket_mod.Entities;
import name.musket_mod.Items;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;

public class ShellShotPelletEntity extends ThrownItemEntity implements MusketShotEntity {
	public static final ShellShotPelletEntityFactory FACTORY = new ShellShotPelletEntityFactory();
	public static final float BASE_DAMAGE_VALUE = 4f;
	private int durability = 1;
	public ShellShotPelletEntity(EntityType<? extends ThrownItemEntity> type, World world, int durabilityLvl) {
		super(type, world);
		this.durability += durabilityLvl;
	}
	public ShellShotPelletEntity(EntityType<? extends ThrownItemEntity> type, LivingEntity owner, World world, int durabilityLvl) {
		super(type, owner, world);
		this.durability += durabilityLvl;
	}
	@Override
	protected Item getDefaultItem() {
		return Items.SHELL_SHOT_PELLET;
	}
	@Override
	public float getShotSpeed() { return 3.0f; }
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
		if (!(hitResult.getEntity() instanceof LivingEntity)) {
			return;
		}

		LivingEntity living = (LivingEntity)hitResult.getEntity();
		living.setHealth(living.getHealth() - BASE_DAMAGE_VALUE);
		DamageSource source = DamageTypes.of(this.getWorld(), DamageTypes.MUSKET_SHOT);
		living.onDamaged(source);
		this.durability = 0;
	}
	@Override
	public void onBlockHit(BlockHitResult hitResult) {
		super.onBlockHit(hitResult);
		if (this.getOwner() == null) return; // on world start, if an entity exists then it will cause crash since it has no owner
		this.durability -= MusketShotEntity.super.onBlockHit(hitResult, this.getWorld(),
				(this.getOwner().isPlayer())? (PlayerEntity)this.getOwner() : null);
		this.setVelocity(this.getVelocity().normalize()); // Reduce velocity so that it doesn't "clip" through blocks
	}
	public static class ShellShotPelletEntityFactory implements EntityType.EntityFactory<ShellShotPelletEntity> {
		@Override
		public ShellShotPelletEntity create(EntityType<ShellShotPelletEntity> type, World world) {
			return new ShellShotPelletEntity(Entities.SHELL_SHOT_PELLET, world, 1);
		}
	}
}
