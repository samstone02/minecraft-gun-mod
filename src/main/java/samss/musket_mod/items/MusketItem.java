package samss.musket_mod.items;

import samss.musket_mod.Items;
import samss.musket_mod.MusketMod;
import samss.musket_mod.enchantments.Enchantments;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtString;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MusketItem extends Item {
	//<editor-fold desc="Static members">
	public static final String ITEM_ID = "musket";
	private static final float SCARE_RADIUS = 10f;
	private static final int SHOOT_ANIMATION_TICKS = 25;
	public static final int RELOAD_ANIMATION_TICKS = 120;
	private static final class NBT {
		private static final String SHOTS_LOADED = MusketMod.MOD_ID + ".shots_loaded";
		private static final String LOADED_SHOT_ITEM_ID = MusketMod.MOD_ID + ".loaded_shot_item_id";
		private static final String USE_STATE = MusketMod.MOD_ID + ".use_state";
		private static class UseStates {
			private static final String IDLE = "IDLE";
			private static final String RELOADING = "RELOADING";
			private static final String CAN_RELOAD = "CAN_RELOAD";
			private static final String SHOOTING = "SHOOTING";
		}
	}
	//</editor-fold>
	//<editor-fold desc="Constructors">
	public MusketItem(Settings settings) {
		super(settings);
	}
	//</editor-fold>
	//<editor-fold desc="Getters">
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}
	@Override
	public int getMaxUseTime(ItemStack stack) {
		NbtCompound nbt = stack.getOrCreateNbt();
		String state = nbt.getString(NBT.USE_STATE);

		if (state.equals(NBT.UseStates.SHOOTING)) {
			MusketMod.LOGGER.info(":: getMaxUseTime. state = " + state);
			return SHOOT_ANIMATION_TICKS;
		}
		else if (state.equals(NBT.UseStates.RELOADING)) {
			MusketMod.LOGGER.info(":: getMaxUseTime. state = " + state);
			return RELOAD_ANIMATION_TICKS;
		}
		return 0;
	}
	public String getState(ItemStack stack) {
		NbtCompound nbt = stack.getOrCreateNbt();
		return nbt.getString(NBT.USE_STATE);
	}
	@Override
	public int getEnchantability() {
		return 5;
	}
	//</editor-fold>
	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return stack.getItem() == Items.MUSKET && ingredient.getItem() == net.minecraft.item.Items.NETHERITE_INGOT;
	}
	@Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		super.use(world, player, hand);
		if (world.isClient) {
			return TypedActionResult.pass(player.getStackInHand(hand));
		}

		ItemStack stack = player.getStackInHand(hand);
		NbtCompound nbt = stack.getOrCreateNbt();
		String state = nbt.getString(NBT.USE_STATE);

		if (state.equals(NBT.UseStates.CAN_RELOAD)
				|| state.equals(NBT.UseStates.SHOOTING)
				|| state.equals(NBT.UseStates.RELOADING)) {
			/* Prevent other behavior when shooting or reloading */
			MusketMod.LOGGER.info("skipping use() since in state: " + state);
			player.setCurrentHand(hand);
			return TypedActionResult.fail(player.getStackInHand(hand));
		}

		if (nbt.getInt(NBT.SHOTS_LOADED) <= 0) {
			int index = getShotIndexFromInventory(player, hand);
			if (index == -2) {
				/* No ammo found */
				world.playSound(player, player.getBlockPos(), SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.PLAYERS);

				player.setCurrentHand(hand);
				return TypedActionResult.fail(player.getStackInHand(hand));
			}

			/* Start reloading */
			nbt.put(NBT.USE_STATE, NbtString.of(NBT.UseStates.RELOADING));

			// setCurrentHand is important because it sets the useTimeLeft.
			player.setCurrentHand(hand);
			return TypedActionResult.consume(player.getStackInHand(hand));
		}

		/* Shoot the Musket */
		shoot(world, player, hand);
		nbt.put(NBT.USE_STATE, NbtString.of(NBT.UseStates.SHOOTING));

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.schedule(() -> nbt.put(NBT.USE_STATE, NbtInt.of(0)), SHOOT_ANIMATION_TICKS * 50, TimeUnit.MILLISECONDS);

		// setCurrentHand is important because it sets the useTimeLeft.
		player.setCurrentHand(hand);
		return TypedActionResult.consume(player.getStackInHand(hand));
	}
	@Override
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		super.usageTick(world, user, stack, remainingUseTicks);
		if (world.isClient) { return; }

		NbtCompound nbt = stack.getOrCreateNbt();
		String state = nbt.getString(NBT.USE_STATE);

		if (state.equals(NBT.UseStates.SHOOTING) && remainingUseTicks <= 1) {
			nbt.put(NBT.USE_STATE, NbtString.of(NBT.UseStates.IDLE));
		}
		else if (state.equals(NBT.UseStates.RELOADING) && remainingUseTicks <= 1) {
			nbt.put(NBT.USE_STATE, NbtString.of(NBT.UseStates.CAN_RELOAD));
		}
	}
	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (world.isClient) { return; }

		NbtCompound nbt = stack.getOrCreateNbt();
		String state = nbt.getString(NBT.USE_STATE);

		if (state.equals(NBT.UseStates.CAN_RELOAD)) {
			/* Load shots into Musket */
			world.playSound((PlayerEntity) user, user.getBlockPos(), SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.PLAYERS);

			reload((PlayerEntity) user, user.getActiveHand());
			nbt.put(NBT.USE_STATE, NbtString.of(NBT.UseStates.IDLE));
		}
	}
	private void shoot(World world, PlayerEntity player, Hand hand) {
		MusketShootable shot = nextShot(player, hand);
		if (shot == null) {
			return;
		}

		shot.onPlayerShoot(world, player, player.getStackInHand(hand));

		ItemStack itemStack = player.getStackInHand(hand);
		NbtCompound nbt = itemStack.getOrCreateNbt();

		int shotsLeft = nbt.getInt(NBT.SHOTS_LOADED) - 1;
		nbt.put(NBT.SHOTS_LOADED, NbtInt.of(shotsLeft));
		if (shotsLeft <= 0) {
			nbt.remove(NBT.LOADED_SHOT_ITEM_ID);
		}

		int silencingLvl = EnchantmentHelper.getLevel(Enchantments.SILENCING, itemStack);

		createMuzzleFlash(world, player, silencingLvl >= 1);

		player.getStackInHand(hand).damage(1, player, (e) -> {
			e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
		});
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

		int count = getCountToLoad(player, hand, index);

		shotStack.decrement(count);

		NbtCompound nbt = player.getStackInHand(hand).getOrCreateNbt();
		nbt.put(NBT.SHOTS_LOADED, NbtInt.of(count));
		nbt.put(NBT.LOADED_SHOT_ITEM_ID, NbtString.of(((MusketShootable) shotStack.getItem()).getId()));
	}
	private MusketShootable nextShot(PlayerEntity player, Hand hand) {
		if (!player.getStackInHand(hand).hasNbt()) {
			MusketMod.LOGGER.error("somehow we try to shoot the musket with no bullets loaded");
			return null;
		}

		NbtCompound nbt = player.getStackInHand(hand).getOrCreateNbt();
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
			return minIndex;
		}

		return -2;
	}
	private int getCountToLoad(PlayerEntity player, Hand hand, int index) {
		int count = player.getInventory().getStack(index).getCount();

		ItemStack musketStack = player.getStackInHand(hand);
		for (NbtElement enchantment : musketStack.getEnchantments()) {
			NbtCompound compound = (NbtCompound) enchantment;
			if (compound.getString("id").equals(MusketMod.MOD_ID + ":" + Enchantments.OVERCAPACITY.getEnchantmentId())) {
				MusketMod.LOGGER.info("FOUND ENCHANTMENT: " + compound.toString());
				int lvl = compound.getShort("lvl");
				if (count > lvl + 1) {
					count = lvl + 1;
				}
				return count;
			}
		}

		return 1;
	}
	public void createMuzzleFlash(World world, LivingEntity user, boolean isSilenced) {
		float pitch = (float)(Math.PI / 180.0) * user.getPitch();
		float yaw = (float)(Math.PI / 180.0) * user.getHeadYaw();

		double x = user.getX() + -MathHelper.sin(yaw) * MathHelper.cos(-pitch);
		double y = user.getEyeY() + MathHelper.sin(-pitch);
		double z = user.getZ() + MathHelper.cos(yaw) * MathHelper.cos(-pitch);

//		world.createExplosion(
//				user, x, y, z,
//				0, false, World.ExplosionSourceType.NONE);
		world.createExplosion(
				user,  Explosion.createDamageSource(world, user), null,
				x, y, z, 0, false,
				World.ExplosionSourceType.NONE,
				ParticleTypes.EXPLOSION,
				ParticleTypes.EXPLOSION_EMITTER,
				isSilenced? SoundEvents.BLOCK_TRIPWIRE_CLICK_OFF: SoundEvents.ENTITY_GENERIC_EXPLODE
		);

		if (isSilenced) {
			return;
		}

		Box box = new Box(
				user.getX() - SCARE_RADIUS, user.getY() - SCARE_RADIUS, user.getZ() - SCARE_RADIUS,
				user.getX() + SCARE_RADIUS, user.getY() + SCARE_RADIUS, user.getZ() + SCARE_RADIUS
		);
		List<AnimalEntity> animals = world.getEntitiesByType(TypeFilter.instanceOf(AnimalEntity.class), box, Objects::nonNull);
		for (AnimalEntity animal : animals) {
			animal.setAttacker(user);
		}
	}
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);

		NbtCompound nbt = stack.getOrCreateNbt();

		int shotsLoaded = nbt.getInt(NBT.SHOTS_LOADED);
		if (shotsLoaded <= 0) {
			tooltip.add(Text.translatable("tooltip.musket_mod.musket.not_loaded").formatted(Formatting.BLUE));
		}
		else {
			String loadedShotId = nbt.getString(NBT.LOADED_SHOT_ITEM_ID);
			String shotTranslationKey = Registries.ITEM.get(new Identifier(MusketMod.MOD_ID, loadedShotId)).getTranslationKey();
			tooltip.add(Text.translatable("tooltip.musket_mod.musket.is_loaded", shotsLoaded, shotTranslationKey, shotsLoaded > 1 ? "s" : "").formatted(Formatting.BLUE));
		}
	}
}