package name.gunmod.items;

import name.gunmod.Gunmod;
import name.gunmod.entities.AbstractShotEntity;
import name.gunmod.entities.BoltShotEntity;
import name.gunmod.entities.Entities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MusketItem extends Item {
	public static final String ITEM_ID = "musket";
	
	private int shotsLoaded = 1000; // TODO: DEFAULT = 0
	private float shotStrength = 2.0f;
	private String loadedShotItemId = BoltshotItem.ITEM_ID; // null
	private AbstractShotEntity shot;
	private ItemStack shotStack; // TODO: use an item stack to store how many shots are loaded?

	public MusketItem(Settings settings) {
		super(settings);
	}
	
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
    	if (shotsLoaded > 0) {
    		shotsLoaded--;

			Gunmod.LOGGER.info("gunmod: spawning/shooting a bullet -> " + loadedShotItemId);

			ProjectileEntity shot = InstantiateLoadedShot(playerEntity, world, null);
			shot.setPos(playerEntity.getX(), playerEntity.getEyeY(), playerEntity.getZ());
			shot.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, shotStrength, 1.0f);
			world.spawnEntity(shot);
    	}
//    	else {
//    		
//			List<Integer> list = new ArrayList<Integer>();
//			list.add(playerEntity.getInventory().indexOf(new ItemStack(Registries.ITEM.getEntry(BoltshotItem.OBJECT))));
//			list.add(playerEntity.getInventory().indexOf(new ItemStack(Registries.ITEM.getEntry(RoundshotItem.OBJECT))));
//			list.add(playerEntity.getInventory().indexOf(new ItemStack(Registries.ITEM.getEntry(ShellshotItem.OBJECT))));
//			int minIndex = Collections.min(list);
//			
//    		LOGGER.info("mind index: " + minIndex);
//			
//			// -1 is the lowest inventory value
//			if (minIndex >= PlayerInventory.NOT_FOUND) {
//				LOGGER.info("Found bullet in inventory.");
//				LOGGER.info(Integer.toString(minIndex));
//				playerEntity.getInventory().removeStack(minIndex, 1);
//			}
//			else {
//				LOGGER.info("Did not find bullet in inventory.");
//			}
//    	}
        playerEntity.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0F, 1.0F);
        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }

	private ProjectileEntity InstantiateLoadedShot(LivingEntity owner, World world, ItemStack stack) {
		if (loadedShotItemId == null || loadedShotItemId.isEmpty()) {
			// throw new Exception(Gunmod.MOD_ID + "-> MusketItem::InstantiateLoadedShot. shotType has not been initialized.");
			return null;
		}
		if (loadedShotItemId.equals(BoltshotItem.ITEM_ID)) {
			return new BoltShotEntity(Entities.BOLT_SHOT, owner, world);
		}
		if (loadedShotItemId.equals(RoundshotItem.ITEM_ID)) {
			return new BoltShotEntity(Entities.BOLT_SHOT, owner, world);
		}
		if (loadedShotItemId.equals(ShellshotItem.ITEM_ID)) {
			return new BoltShotEntity(Entities.BOLT_SHOT, owner, world);
		}
		return null;
	}
}
