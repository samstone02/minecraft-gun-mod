package name.musket_mod;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class DamageTypes {
    public static RegistryKey<DamageType> MUSKET_SHOT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(MusketMod.MOD_ID, "musket_shot"));
    public static DamageSource of(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }
}
