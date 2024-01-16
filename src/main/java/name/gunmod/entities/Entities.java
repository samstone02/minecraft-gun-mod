package name.gunmod.entities;

import name.gunmod.Gunmod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Entities {
    public static final EntityType<BoltShotEntity> BOLT_SHOT = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Gunmod.MOD_ID, "bolt_shot"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, BoltShotEntity.FACTORY)
                    .dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );;

    private static boolean isRegistered = false;

    public static void registerAll(String modId) {
        if (isRegistered) {
            return;
        }

        isRegistered = true;
    }
}
