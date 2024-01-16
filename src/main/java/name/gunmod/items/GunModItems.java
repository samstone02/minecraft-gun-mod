package name.gunmod.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class GunModItems {
    public static final MusketItem MUSKET = new MusketItem(new FabricItemSettings());
    public static final BoltshotItem BOLT_SHOT = new BoltshotItem(new FabricItemSettings());
    public static final RoundshotItem ROUND_SHOT = new RoundshotItem(new FabricItemSettings());
    public static final ShellshotItem SHELL_SHOT = new ShellshotItem(new FabricItemSettings());

    public static void registerAll(String modId) {
        Registry.register(Registries.ITEM, new Identifier(modId, MusketItem.ITEM_ID), MUSKET);
        Registry.register(Registries.ITEM, new Identifier(modId, BoltshotItem.ITEM_ID), BOLT_SHOT);
        Registry.register(Registries.ITEM, new Identifier(modId, RoundshotItem.ITEM_ID), ROUND_SHOT);
        Registry.register(Registries.ITEM, new Identifier(modId, ShellshotItem.ITEM_ID), SHELL_SHOT);

        // Creative Inventory Groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(MUSKET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(BOLT_SHOT));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ROUND_SHOT));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(SHELL_SHOT));
    }
}
