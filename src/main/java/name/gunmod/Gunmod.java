package name.gunmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.gunmod.entities.RoundshotEntity;
import name.gunmod.items.BoltshotItem;
import name.gunmod.items.RoundshotItem;
import name.gunmod.items.ShellshotItem;

public class Gunmod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("gun-mod");
	
    @Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		Registry.register(Registries.ITEM, new Identifier("gun-mod", "musket"), MusketItem.OBJECT);
		Registry.register(Registries.ITEM, new Identifier("gun-mod", "boltshot"), BoltshotItem.OBJECT);
		Registry.register(Registries.ITEM, new Identifier("gun-mod", "roundshot"), RoundshotItem.OBJECT);
		Registry.register(Registries.ITEM, new Identifier("gun-mod", "shellshot"), ShellshotItem.OBJECT);
		
		// Item Groups are used for Creative Inventory tabs
	    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
	    	content.add(MusketItem.OBJECT);
	    });
	    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
	    	content.add(BoltshotItem.OBJECT);
	    });
	    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
	    	content.add(RoundshotItem.OBJECT);
	    });
	    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
	    	content.add(ShellshotItem.OBJECT);
	    });
	}
}