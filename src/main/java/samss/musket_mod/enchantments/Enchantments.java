package samss.musket_mod.enchantments;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Enchantments {
    public static OvercapacityEnchantment OVERCAPACITY = new OvercapacityEnchantment();
    public static PuncturingEnchantment PUNCTURING = new PuncturingEnchantment();
    public static SilencingEnchantment SILENCING = new SilencingEnchantment();
    public static VelocityEnchantment VELOCITY = new VelocityEnchantment();
    public static void registerAll(String modId) {
        Registry.register(Registries.ENCHANTMENT, new Identifier(modId, OVERCAPACITY.getEnchantmentId()), OVERCAPACITY);
        Registry.register(Registries.ENCHANTMENT, new Identifier(modId, PUNCTURING.getEnchantmentId()), PUNCTURING);
        Registry.register(Registries.ENCHANTMENT, new Identifier(modId, SILENCING.getEnchantmentId()), SILENCING);
        Registry.register(Registries.ENCHANTMENT, new Identifier(modId, VELOCITY.getEnchantmentId()), VELOCITY);
    }
}
