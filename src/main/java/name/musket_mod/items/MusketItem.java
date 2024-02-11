package name.musket_mod.items;

import name.musket_mod.Items;
import name.musket_mod.MusketMod;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MusketItem extends Item {
	public static final String ITEM_ID = "musket";
	private static final float SCARE_RADIUS = 10f;
	private int currentAnimationTicks = 0;
	private static final int SHOOT_ANIMATION_TICKS = 25;
	private static final int RELOAD_ANIMATION_TICKS = 160;
	private static final class NBT {
		private static final String SHOTS_LOADED = MusketMod.MOD_ID + ".shots_loaded";
		private static final String LOADED_SHOT_ITEM_ID = MusketMod.MOD_ID + ".loaded_shot_item_id";
		private static final String USE_STATE = MusketMod.MOD_ID + ".use_state"; // 0 = 1 none, 1 = shooting, 2 = reloading
	}
	public MusketItem(Settings settings) {
		super(settings);
	}
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}
	@Override
	public int getMaxUseTime(ItemStack stack) {
		ensureNbt(stack);
		int state = stack.getNbt().getInt(NBT.USE_STATE);

		if (state == 1) {
			return SHOOT_ANIMATION_TICKS;
		}
		else if (state == 2) {
			return RELOAD_ANIMATION_TICKS;
		}
		return 0;
	}
	public int getState(ItemStack stack) {
		ensureNbt(stack);
		return stack.getNbt().getInt(NBT.USE_STATE);
	}
	public int getCurrentAnimationTime() {
		return currentAnimationTicks;
	}
	/**
	 * 	@summary Tick called by held item renderer mixin for animation purposes
	 */
	public void animationTick(ItemStack stack) {
		ensureNbt(stack);
		int state = stack.getNbt().getInt(NBT.USE_STATE);

		if (state == 1 && currentAnimationTicks <= 0) {
			NbtCompound nbt = stack.getNbt();
			nbt.putInt(NBT.USE_STATE, 0);
			MusketMod.LOGGER.info("we have reset animation state to zero.");
			currentAnimationTicks = 0;
		}
		else if (currentAnimationTicks > 0) {
			currentAnimationTicks--;
		}
	}
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (world.isClient) { return TypedActionResult.pass(player.getStackInHand(hand)); }

		ItemStack stack = player.getStackInHand(hand);
		ensureNbt(stack);
		int state = stack.getNbt().getInt(NBT.USE_STATE);
		MusketMod.LOGGER.info("USE STATE: " + state);

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

		NbtCompound nbt = player.getStackInHand(hand).getNbt();
		if (nbt == null || nbt.getInt(NBT.SHOTS_LOADED) <= 0) {
			int index = getShotIndexFromInventory(player, hand);
			MusketMod.LOGGER.info("index: " + index);
			if (index == -2) {
				// TODO: play unable to reload sound
				player.setCurrentHand(hand);
				return TypedActionResult.fail(player.getStackInHand(hand));
			}

			/* Start reloading */
			MusketMod.LOGGER.info("reload: " + state);
			stack.getNbt().putInt(NBT.USE_STATE, 2);
			currentAnimationTicks = RELOAD_ANIMATION_TICKS;

			player.setCurrentHand(hand);
			return TypedActionResult.consume(player.getStackInHand(hand));
		}

		/* Shoot the Musket */
		shoot(world, player, hand);

		MusketMod.LOGGER.info("shoot: " + state);
		stack.getNbt().putInt(NBT.USE_STATE, 1);
		currentAnimationTicks = SHOOT_ANIMATION_TICKS;

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.schedule(() -> stack.getNbt().putInt(NBT.USE_STATE, 0), SHOOT_ANIMATION_TICKS, TimeUnit.MILLISECONDS);

		player.setCurrentHand(hand);
		return TypedActionResult.consume(player.getStackInHand(hand));
    }
	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (world.isClient) { return; }

		ensureNbt(stack);
		int state = stack.getNbt().getInt(NBT.USE_STATE);

		if (state == 2) {
			if (currentAnimationTicks <= 0) {
				// TODO: play reload sound...
				reload((PlayerEntity) user, user.getActiveHand());
			}
			stack.getNbt().putInt(NBT.USE_STATE, 0);
		}
	}
	private void shoot(World world, PlayerEntity player, Hand hand) {
		MusketShootable shot = nextShot(player, hand);
		if (shot == null) {
			return;
		}

		shot.onShoot(world, player);

		ItemStack itemStack = player.getStackInHand(hand);
		ensureNbt(itemStack);
		itemStack.getNbt().putInt(NBT.SHOTS_LOADED, itemStack.getNbt().getInt(NBT.SHOTS_LOADED) - 1);
		itemStack.getNbt().putString(NBT.LOADED_SHOT_ITEM_ID, "");

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
	}
	private void reload(PlayerEntity player, Hand hand) {
		int index = getShotIndexFromInventory(player, hand);

		if (index == -2) {
			MusketMod.LOGGER.error("for some reason we tried to reload with no bullets in the inventory...");
			return;
		}

		ItemStack shotStack = player.getInventory().getStack(index);
		if (!(shotStack.getItem() instanceof MusketShootable)) {
			MusketMod.LOGGER.error("TRIED TO LOAD A NON-SHOT");
			return;
		}

		shotStack.decrement(1);

		NbtCompound nbt = player.getStackInHand(hand).getNbt();
		nbt.putInt(NBT.SHOTS_LOADED, 1);
		nbt.putString(NBT.LOADED_SHOT_ITEM_ID, ((MusketShootable) shotStack.getItem()).getId());
	}
	private MusketShootable nextShot(PlayerEntity player, Hand hand) {
		if (!player.getStackInHand(hand).hasNbt()) {
			MusketMod.LOGGER.error("somehow we try to shoot the musket with no bullets loaded");
			return null;
		}

		NbtCompound nbt = player.getStackInHand(hand).getNbt();
		String loadedShotId = nbt.getString(NBT.LOADED_SHOT_ITEM_ID);
		Item item = Registries.ITEM.get(new Identifier(MusketMod.MOD_ID, loadedShotId));

		if (item instanceof MusketShootable) {
			return (MusketShootable) item;
		}

		MusketMod.LOGGER.error("SOMETHING OTHER THAN A BULLET IS IN THE GUN!");
		return null;
	}
	/**
	 * @summary -2 means not found, -1 means offhand
	 */
	private int getShotIndexFromInventory(PlayerEntity player, Hand hand) {
		if (player.getStackInHand(Hand.OFF_HAND).isOf(Items.BOLT_SHOT)
				|| player.getStackInHand(Hand.OFF_HAND).isOf(Items.ROUND_SHOT)
				|| player.getStackInHand(Hand.OFF_HAND).isOf(Items.SHELL_SHOT)) {
			return -1;
		}

		List<Integer> list = new ArrayList<>();
		list.add(player.getInventory().indexOf(new ItemStack(Items.BOLT_SHOT)));
		list.add(player.getInventory().indexOf(new ItemStack(Items.ROUND_SHOT)));
		list.add(player.getInventory().indexOf(new ItemStack(Items.SHELL_SHOT)));
		list.removeIf(i -> i == -1);

		if (list.isEmpty()) {
			player.setCurrentHand(hand);
			return -2;
		}

		int minIndex = Collections.min(list);

		if (minIndex > PlayerInventory.NOT_FOUND) {
			player.setCurrentHand(hand);
			return minIndex;
		}

		return -2;
	}
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		NbtCompound nbt = stack.getNbt();
		if (nbt == null) {
			tooltip.add(Text.translatable("tooltip.musket_mod.musket.empty").formatted(Formatting.WHITE));
			return;
		}

		int shotsLoaded = nbt.getInt(NBT.SHOTS_LOADED);
		if (shotsLoaded <= 0) {
			tooltip.add(Text.translatable("tooltip.musket_mod.musket.empty").formatted(Formatting.WHITE));
		}
		else {
			String loadedShotId = nbt.getString(NBT.LOADED_SHOT_ITEM_ID);
			String shotTranslationKey = Registries.ITEM.get(new Identifier(MusketMod.MOD_ID, loadedShotId)).getTranslationKey();

			tooltip.add(Text.translatable("tooltip.musket_mod.musket.shots_loaded").formatted(Formatting.WHITE));
			tooltip.add(Text.literal(nbt.getInt("musket_mod.shots_loaded") + "").formatted(Formatting.RED));
			tooltip.add(Text.translatable(shotTranslationKey).formatted(Formatting.WHITE));
		}
	}
	private void ensureNbt(ItemStack stack) {
		if (!stack.hasNbt()) {
			stack.setNbt(new NbtCompound());
		}
	}
}