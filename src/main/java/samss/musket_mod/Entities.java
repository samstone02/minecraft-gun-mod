package samss.musket_mod;

import samss.musket_mod.entities.BoltShotEntity;
import samss.musket_mod.entities.RoundShotEntity;
import samss.musket_mod.entities.ShellShotPelletEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Entities {
    public static final EntityType<BoltShotEntity> BOLT_SHOT = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(MusketMod.MOD_ID, "bolt_shot"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, BoltShotEntity.FACTORY)
                    .dimensions(EntityDimensions.fixed(0.4f, 0.4f)).build()
    );
    public static final EntityType<RoundShotEntity> ROUND_SHOT = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(MusketMod.MOD_ID, "round_shot"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, RoundShotEntity.FACTORY)
                    .dimensions(EntityDimensions.fixed(0.2f, 0.2f)).build()
    );
    public static final EntityType<ShellShotPelletEntity> SHELL_SHOT_PELLET = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(MusketMod.MOD_ID, "shell_shot_pellet"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ShellShotPelletEntity.FACTORY)
                    .dimensions(EntityDimensions.fixed(0.2f, 0.2f)).build()
    );

    private static boolean isRegistered = false;

    public static void registerAll(String modId) {
        if (isRegistered) {
            return;
        }

        isRegistered = true;
    }
}
