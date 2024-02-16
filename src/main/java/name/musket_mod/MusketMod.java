package name.musket_mod;

import com.mojang.datafixers.kinds.IdF;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtInt;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

// @Environment(EnvType.SERVER) // NVM this breaks the code for some reason
public class MusketMod implements ModInitializer {
	public static final String MOD_ID = "musket_mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Identifier RELOAD_PROGRESS = new Identifier(MOD_ID, "reload_progress");

    @Override
	public void onInitialize() {
		LOGGER.info("'Musket Mod' comes in with *bang*!");

		Items.registerAll(MOD_ID);
		Entities.registerAll(MOD_ID);

//		ServerPlayNetworking.registerGlobalReceiver(RELOAD_PROGRESS, (server, player, handler, buf, responseSender) -> {
//			boolean fire = buf.readBoolean();
//			// All operations on the server or world must be executed on the server thread
//			server.execute(() -> {
//				UUID uuid = UUID.fromString(buf.readString());
//				int canReload = buf.readInt();
//
//				MusketMod.LOGGER.info("received the can_reload packet on the server");
//
////				PlayerEntity entity = (PlayerEntity) server.getOverworld().getEntity(uuid);
//				PlayerInventory inventory = player.getInventory();
//				inventory.getStack(inventory.selectedSlot).getOrCreateNbt().put(MusketMod.MOD_ID + ".can_reload", NbtInt.of(canReload));
//			});
//		});
	}
}