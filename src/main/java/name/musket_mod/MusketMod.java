package name.musket_mod;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// @Environment(EnvType.SERVER) // NVM this breaks the code for some reason
public class MusketMod implements ModInitializer {
	public static final String MOD_ID = "musket_mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
	public void onInitialize() {
		LOGGER.info("'Musket Mod' comes in with *bang*!");

		Items.registerAll(MOD_ID);
		Entities.registerAll(MOD_ID);
	}
}