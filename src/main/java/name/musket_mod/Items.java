package name.musket_mod;

import name.musket_mod.items.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Items {
    public static final MusketItem MUSKET = new MusketItem(new FabricItemSettings().maxCount(1).maxDamage(5).fireproof());
    public static final BoltShotItem BOLT_SHOT = new BoltShotItem(new FabricItemSettings());
    public static final RoundShotItem ROUND_SHOT = new RoundShotItem(new FabricItemSettings());
    public static final ShellShotItem SHELL_SHOT = new ShellShotItem(new FabricItemSettings());
    public static final ShellShotPelletItem SHELL_SHOT_PELLET = new ShellShotPelletItem(new FabricItemSettings());

    public static void registerAll(String modId) {
        Registry.register(Registries.ITEM, new Identifier(modId, BoltShotItem.ITEM_ID), BOLT_SHOT);
        Registry.register(Registries.ITEM, new Identifier(modId, RoundShotItem.ITEM_ID), ROUND_SHOT);
        Registry.register(Registries.ITEM, new Identifier(modId, ShellShotItem.ITEM_ID), SHELL_SHOT);
        Registry.register(Registries.ITEM, new Identifier(modId, ShellShotPelletItem.ITEM_ID), SHELL_SHOT_PELLET);
        Registry.register(Registries.ITEM, new Identifier(modId, MusketItem.ITEM_ID), MUSKET);

        // Creative Inventory Groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(MUSKET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(BOLT_SHOT));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ROUND_SHOT));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(SHELL_SHOT));
    }
}
