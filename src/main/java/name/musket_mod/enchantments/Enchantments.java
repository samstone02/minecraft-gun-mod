package name.musket_mod.enchantments;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Enchantments {
    public static OvercapacityEnchantment OVERCAPACITY = new OvercapacityEnchantment();
    public static void registerAll(String modId) {
        Registry.register(Registries.ENCHANTMENT, new Identifier(modId, OvercapacityEnchantment.ENCHANTMENT_ID), OVERCAPACITY);
    }
}
