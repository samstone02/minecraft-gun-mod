package name.musket_mod.entities;

import name.musket_mod.DamageTypes;
import name.musket_mod.Entities;
import name.musket_mod.Items;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class RoundShotEntity extends ThrownItemEntity implements MusketShotEntity {
	public static final Factory FACTORY = new Factory();
	public static final float BASE_DAMAGE_VALUE = 7f;
	//<editor-fold desc="Constructors">
	public RoundShotEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}
	public RoundShotEntity(EntityType<? extends ThrownItemEntity> type, LivingEntity owner, World world) {
		super(type, owner, world);
	}
	//</editor-fold>
	@Override
	protected Item getDefaultItem() { return Items.ROUND_SHOT; }
	public float getShotSpeed() { return 4.0f; }
	@Override
	public void onCollision(HitResult hitResult) {
		if (this.getWorld().isClient) {
			return;
		}
		super.onCollision(hitResult);
		this.discard();
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
		MusketShotEntity.super.onBlockHit(hitResult, this.getWorld(),
				(this.getOwner().isPlayer())? (PlayerEntity)this.getOwner() : null);
	}
	public static class Factory implements EntityType.EntityFactory<RoundShotEntity> {
		@Override
		public RoundShotEntity create(EntityType<RoundShotEntity> type, World world) {
			return new RoundShotEntity(Entities.ROUND_SHOT, world);
		}
	}
}
