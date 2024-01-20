package name.gunmod;

import name.gunmod.entities.Entities;
import name.gunmod.items.GunModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// There are two threads in Minecraft: Client and Server.
// The client handles things like rendering.
// The server (aka main) handles everything else (states, blocks, entities, commands, etc).
// @Environment is used by Fabric to clarify the environment. Not strictly required, though.
// @Environment(EnvType.SERVER) // NVM this breaks the code for some reason
public class Gunmod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "gun-mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-
		// ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello, " + MOD_ID + "!");

		GunModItems.registerAll(MOD_ID);
		Entities.registerAll(MOD_ID);
		Particles.registerAll(MOD_ID);
	}
}