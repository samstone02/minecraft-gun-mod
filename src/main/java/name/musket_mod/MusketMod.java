package name.musket_mod;

import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// There are two threads in Minecraft: Client and Server.
// The client handles things like rendering.
// The server (aka main) handles everything else (states, blocks, entities, commands, etc).
// @Environment is used by Fabric to clarify the environment. Not strictly required, though.
// @Environment(EnvType.SERVER) // NVM this breaks the code for some reason
public class MusketMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "musket_mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-
		// ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("'Musket Mod' comes in with *bang*!");

		Items.registerAll(MOD_ID);
		Entities.registerAll(MOD_ID);
	}
}