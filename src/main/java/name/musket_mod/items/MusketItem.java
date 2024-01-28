package name.musket_mod.items;

import name.musket_mod.Items;
import name.musket_mod.MusketMod;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MusketItem extends Item {
	public static final String ITEM_ID = "musket";
	private final float SCARE_RADIUS = 10f;
	private ItemStack shotStack = ItemStack.EMPTY;
	public MusketItem(Settings settings) {
		super(settings);
	}
	
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (world.isClient) {
			return TypedActionResult.pass(player.getStackInHand(hand));
		}

		/* Shoot */
    	if (shotStack.getCount() > 0) {
			MusketShootable shot = nextShot();
			shot.onShoot(world, player);

			float pitch = (float)(Math.PI / 180.0) * player.getPitch();
			float yaw = (float)(Math.PI / 180.0) * player.getHeadYaw();

			double x = player.getX() + -MathHelper.sin(yaw) * MathHelper.cos(-pitch);
			double y = player.getEyeY() + MathHelper.sin(-pitch);
			double z = player.getZ() + MathHelper.cos(yaw) * MathHelper.cos(-pitch);

			MusketMod.LOGGER.info("" + x);
			MusketMod.LOGGER.info("" + y);
			MusketMod.LOGGER.info("" + z);

			world.createExplosion(
					player, x, y, z,
					0, false, World.ExplosionSourceType.NONE);

			Box box = new Box(
					player.getX() - SCARE_RADIUS, player.getY() - SCARE_RADIUS, player.getZ() - SCARE_RADIUS,
					player.getX() + SCARE_RADIUS, player.getY() + SCARE_RADIUS, player.getZ() + SCARE_RADIUS
			);
			List<AnimalEntity> animals = world.getEntitiesByType(TypeFilter.instanceOf(AnimalEntity.class), box, Objects::nonNull);

			for (AnimalEntity animal : animals) {
				animal.setAttacker(player);
			}

			return TypedActionResult.success(player.getStackInHand(hand));
    	}

		/* Reload */

		// handle shot in offhand
		if (player.getStackInHand(Hand.OFF_HAND).isOf(Items.BOLT_SHOT)
				|| player.getStackInHand(Hand.OFF_HAND).isOf(Items.ROUND_SHOT)
				|| player.getStackInHand(Hand.OFF_HAND).isOf(Items.SHELL_SHOT)) {
			MusketMod.LOGGER.info("found musket fire-able in offhand");
			ItemStack offHandStack = player.getStackInHand(Hand.OFF_HAND);
			offHandStack.decrement(1);
			shotStack = new ItemStack(offHandStack.getItem(), 1);
			return TypedActionResult.success(player.getStackInHand(hand));
		}

		List<Integer> list = new ArrayList<>();
		list.add(player.getInventory().indexOf(new ItemStack(Items.BOLT_SHOT)));
		list.add(player.getInventory().indexOf(new ItemStack(Items.ROUND_SHOT)));
		list.add(player.getInventory().indexOf(new ItemStack(Items.SHELL_SHOT)));
		list.removeIf(i -> i == -1);

		if (list.isEmpty()) {
			MusketMod.LOGGER.info("no shots in inventory.");
			return TypedActionResult.fail(player.getStackInHand(hand));
		}

		int minIndex = Collections.min(list);

		MusketMod.LOGGER.info("mind index: " + minIndex);
		MusketMod.LOGGER.info("mind index: " + list);

		if (minIndex > PlayerInventory.NOT_FOUND) {
			MusketMod.LOGGER.info("Found bullet in inventory.");
			MusketMod.LOGGER.info(Integer.toString(minIndex));
			shotStack = player.getInventory().removeStack(minIndex, 1);
			player.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0F, 1.0F);
			return TypedActionResult.success(player.getStackInHand(hand));
		}

		// player.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0F, 1.0F);
        return TypedActionResult.fail(player.getStackInHand(hand));
    }

	private MusketShootable nextShot() {
		MusketShootable shot = (MusketShootable)shotStack.getItem();
		shotStack.decrement(1);
		return shot;
	}
}
