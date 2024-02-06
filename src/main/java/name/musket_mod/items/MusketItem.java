package name.musket_mod.items;

import name.musket_mod.Items;
import name.musket_mod.MusketMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MusketItem extends Item {
	public static final String ITEM_ID = "musket";
	private static final float SCARE_RADIUS = 10f;
	private ItemStack shotStack = ItemStack.EMPTY;
	private int currentAnimationTicks = 0;
	private int state = 0; // 0 = 1 none, 1 = shooting, 2 = reloading
	public MusketItem(Settings settings) {
		super(settings);
	}
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}
	public int getState() {
		return state;
	}
	private static final int SHOOT_ANIMATION_TICKS = 25;
	private static final int RELOAD_ANIMATION_TICKS = 160;
	@Override
	public int getMaxUseTime(ItemStack stack) {
		if (state == 1) {
			return SHOOT_ANIMATION_TICKS;
		}
		else if (state == 2) {
			return RELOAD_ANIMATION_TICKS;
		}
		return 0;
	}
	public int getCurrentAnimationTime() {
		return currentAnimationTicks;
	}
	/**
	 * 	@summary Tick called by held item renderer mixin for animation purposes
	 */
	public void animationTick() {
		if (state == 1 && currentAnimationTicks <= 0) {
			state = 0;
			currentAnimationTicks = 0;
		}
		if (currentAnimationTicks > 0) {
			currentAnimationTicks--;
		}
	}
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (world.isClient) { return TypedActionResult.pass(player.getStackInHand(hand)); }

		if (state == 1) {
			MusketMod.LOGGER.info("animation shoot: " + state);
			player.setCurrentHand(hand);
			return TypedActionResult.consume(player.getStackInHand(hand));
		}
		if (state == 2) {
			MusketMod.LOGGER.info("animation reload: " + state);
			player.setCurrentHand(hand);
			return TypedActionResult.consume(player.getStackInHand(hand));
		}

    	if (shotStack.getCount() > 0) {
			MusketMod.LOGGER.info("shoot: " + state);
			state = 1;
			currentAnimationTicks = SHOOT_ANIMATION_TICKS;
			return shoot(world, player, hand);
    	}

		MusketMod.LOGGER.info("reload: " + state);
		state = 2;
		currentAnimationTicks = RELOAD_ANIMATION_TICKS;
		player.setCurrentHand(hand);
		return TypedActionResult.consume(player.getStackInHand(hand));
    }
	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (world.isClient) { return; }

		if (state == 2) {
			if (currentAnimationTicks <= 0) {
				// TODO: play reload sound...
				reload((PlayerEntity) user, user.getActiveHand());
			}
			state = 0;
		}
	}
	private TypedActionResult<ItemStack> shoot(World world, PlayerEntity player, Hand hand) {
		MusketShootable shot = nextShot();
		shot.onShoot(world, player);

		float pitch = (float)(Math.PI / 180.0) * player.getPitch();
		float yaw = (float)(Math.PI / 180.0) * player.getHeadYaw();

		double x = player.getX() + -MathHelper.sin(yaw) * MathHelper.cos(-pitch);
		double y = player.getEyeY() + MathHelper.sin(-pitch);
		double z = player.getZ() + MathHelper.cos(yaw) * MathHelper.cos(-pitch);
		world.createExplosion(
				player, x, y, z,
				0, false, World.ExplosionSourceType.NONE);

		Box box = new Box(
				player.getX() - SCARE_RADIUS, player.getY() - SCARE_RADIUS, player.getZ() - SCARE_RADIUS,
				player.getX() + SCARE_RADIUS, player.getY() + SCARE_RADIUS, player.getZ() + SCARE_RADIUS);
		List<AnimalEntity> animals = world.getEntitiesByType(TypeFilter.instanceOf(AnimalEntity.class), box, Objects::nonNull);
		for (AnimalEntity animal : animals) {
			animal.setAttacker(player);
		}

		player.setCurrentHand(hand);
		return TypedActionResult.consume(player.getStackInHand(hand));
	}
	private void reload(PlayerEntity player, Hand hand) {
		// handle shot in offhand
		if (player.getStackInHand(Hand.OFF_HAND).isOf(Items.BOLT_SHOT)
				|| player.getStackInHand(Hand.OFF_HAND).isOf(Items.ROUND_SHOT)
				|| player.getStackInHand(Hand.OFF_HAND).isOf(Items.SHELL_SHOT)) {
			MusketMod.LOGGER.info("found musket fire-able in offhand");
			ItemStack offHandStack = player.getStackInHand(Hand.OFF_HAND);
			offHandStack.decrement(1);
			shotStack = new ItemStack(offHandStack.getItem(), 1);

			player.setCurrentHand(hand);
			return;
		}

		List<Integer> list = new ArrayList<>();
		list.add(player.getInventory().indexOf(new ItemStack(Items.BOLT_SHOT)));
		list.add(player.getInventory().indexOf(new ItemStack(Items.ROUND_SHOT)));
		list.add(player.getInventory().indexOf(new ItemStack(Items.SHELL_SHOT)));
		list.removeIf(i -> i == -1);

		if (list.isEmpty()) {
			MusketMod.LOGGER.info("no shots in inventory.");
			return;
		}

		int minIndex = Collections.min(list);

		if (minIndex > PlayerInventory.NOT_FOUND) {
			MusketMod.LOGGER.info(Integer.toString(minIndex));
			shotStack = player.getInventory().removeStack(minIndex, 1);
			player.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0F, 1.0F);

			player.setCurrentHand(hand);
		}
	}
	private MusketShootable nextShot() {
		MusketShootable shot = (MusketShootable)shotStack.getItem();
		shotStack.decrement(1);
		return shot;
	}
}