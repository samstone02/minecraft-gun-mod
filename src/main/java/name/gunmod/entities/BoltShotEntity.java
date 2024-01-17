package name.gunmod.entities;

import name.gunmod.Gunmod;
import name.gunmod.items.GunModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.EntityFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class BoltShotEntity extends PersistentProjectileEntity {
	public static class BoltshotEntityFactory implements EntityFactory<BoltShotEntity> {
		@Override
		public BoltShotEntity create(EntityType<BoltShotEntity> type, World world) {
			return new BoltShotEntity(type, world);
		}
	}
	private static final ItemStack DEFAULT_STACK = new ItemStack(GunModItems.BOLT_SHOT);
	public static final EntityFactory<BoltShotEntity> FACTORY = new BoltshotEntityFactory();
	public static final float SHOT_STRENGTH = 2.0f;

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
		// TODO: Figure out why it's only colliding with air?
//		Gunmod.LOGGER.info("Bolt Shot collided with something!");
//		Gunmod.LOGGER.info(state.NAME);
//		Gunmod.LOGGER.info(state.PROPERTIES);
//		Gunmod.LOGGER.info(state.getBlock().getName().getString());
//		Gunmod.LOGGER.info(state.toString());
//		Gunmod.LOGGER.info(state.getBlock().getTranslationKey());
//		if (state.isOf(Blocks.GLASS)
//		||	state.isOf(Blocks.WHITE_STAINED_GLASS)
//		||	state.isOf(Blocks.GLOWSTONE)) {
//			Gunmod.LOGGER.info("Collided with glass or glowstone");
////			state.getBlock().onBreak(getEntityWorld(), null, state, null);
//		}
//		else {
////			Gunmod.LOGGER.info("Collided with a non-breakable block.");
//		}
	}

}
