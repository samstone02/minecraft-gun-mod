package name.gunmod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.gunmod.entities.AbstractShotEntity;
import name.gunmod.items.BoltshotItem;
import name.gunmod.items.RoundshotItem;
import name.gunmod.items.ShellshotItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.VariantPredicates.Predicate;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MusketItem extends Item {
	/* The Musket Object */
	public static final MusketItem OBJECT = new MusketItem(new FabricItemSettings());
	public static final Logger LOGGER = LoggerFactory.getLogger("gun-mod");
	
	private int shellsLoaded = 0;

	
	public MusketItem(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}
	
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
    	if (shellsLoaded > 0) {
    		shellsLoaded--;
    		// spawn projectile base on what was removed
    		// sound effect
    		// scare animals
    		// world.getEntityCollisions(playerEntity, null);
    	}
    	else {
    			Set<Item> set = new HashSet<Item>();
    			set.add(BoltshotItem.OBJECT);
    			set.add(RoundshotItem.OBJECT);
    			set.add(ShellshotItem.OBJECT);
    			if (playerEntity.getInventory().containsAny(set)) {
    				LOGGER.info("Found bullet in inventory.");
    			}
    			else {
    				LOGGER.info("Did not find bullet in inventory.");
    			}
    	}
        playerEntity.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0F, 1.0F);
        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }

}
