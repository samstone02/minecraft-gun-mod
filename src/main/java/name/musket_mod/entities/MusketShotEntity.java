package name.musket_mod.entities;

import name.musket_mod.DamageTypes;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public abstract class MusketShotEntity extends ThrownItemEntity {
    protected int durability = 1;
    public MusketShotEntity(EntityType<? extends ThrownItemEntity> type, World world) {
        super(type, world);
    }
    public MusketShotEntity(EntityType<? extends ThrownItemEntity> type, LivingEntity owner, World world, int additionalDurability) {
        super(type, owner, world);
        this.durability += additionalDurability;
    }
    @Override
    public void onCollision(HitResult hitResult) {
        if (this.getWorld().isClient) {
            return;
        }
        super.onCollision(hitResult);
        if (this.durability <= 0) {
            this.discard();
        }
    }
    @Override
    public void onBlockHit(BlockHitResult hitResult) {
        super.onBlockHit(hitResult);

        World world = getWorld();
        Entity owner = getOwner();

        if (world.isClient) {
            return;
        }
        if (!(owner instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = (PlayerEntity) owner;

        Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
        if (isBlockShatterable(block)) {
            block.onBreak(world, hitResult.getBlockPos(), block.getDefaultState(), player);
            block.onBroken(world, hitResult.getBlockPos(), block.getDefaultState());
            world.breakBlock(hitResult.getBlockPos(), false);
        }
        if (isBlockCrackable(block)) {
            Block replacement = getBlockCracked(block);
            block.onBreak(world, hitResult.getBlockPos(), block.getDefaultState(), player);
            block.onBroken(world, hitResult.getBlockPos(), block.getDefaultState());
            world.breakBlock(hitResult.getBlockPos(), false);
            world.setBlockState(hitResult.getBlockPos(), replacement.getDefaultState());
        }
        this.durability -= 1;

        this.setVelocity(this.getVelocity().normalize());
    }
    @Override
    public void onEntityHit(EntityHitResult hitResult) {
        super.onEntityHit(hitResult);
        DamageSource source = DamageTypes.of(this.getWorld(), DamageTypes.MUSKET_SHOT);
        hitResult.getEntity().damage(source, getBaseDamage());
        hitResult.getEntity().onDamaged(source);
        this.durability = 0;
    }
    static boolean isBlockShatterable(Block block) {
        return block instanceof StainedGlassBlock
            || block instanceof StainedGlassPaneBlock
            || block instanceof TintedGlassBlock
            || block instanceof AmethystBlock
            || block instanceof IceBlock
            || block.equals(Registries.BLOCK.get(new Identifier("minecraft", "glowstone")))
            || block.equals(Registries.BLOCK.get(new Identifier("minecraft", "glass")))
            || block.equals(Registries.BLOCK.get(new Identifier("minecraft", "glass_pane")));
    }
    static boolean isBlockCrackable(Block block) {
        return block.equals(Registries.BLOCK.get(new Identifier("minecraft", "dirt")))
            || block.equals(Registries.BLOCK.get(new Identifier("minecraft", "grass_block")))
            || block.equals(Registries.BLOCK.get(new Identifier("minecraft", "stone")))
            || block.equals(Registries.BLOCK.get(new Identifier("minecraft", "stone_bricks")))
            || block.equals(Registries.BLOCK.get(new Identifier("minecraft", "deepslate")))
            || block.equals(Registries.BLOCK.get(new Identifier("minecraft", "deepslate_bricks")))
            || block.equals(Registries.BLOCK.get(new Identifier("minecraft", "deepslate_tiles")))
            || block.equals(Registries.BLOCK.get(new Identifier("minecraft", "nether_bricks")))
            || block.equals(Registries.BLOCK.get(new Identifier("minecraft", "polished_blackstone_bricks")))
            ||  block.equals(Registries.BLOCK.get(new Identifier("minecraft", "infested_stone_bricks")));
    }
    static Block getBlockCracked(Block block) {
        if (block.equals(Registries.BLOCK.get(new Identifier("minecraft", "dirt")))
                || block.equals(Registries.BLOCK.get(new Identifier("minecraft", "grass_block")))) {
            return Registries.BLOCK.get(new Identifier("minecraft", "coarse_dirt"));
        }
        if (block.equals(Registries.BLOCK.get(new Identifier("minecraft", "stone")))) {
            return Registries.BLOCK.get(new Identifier("minecraft", "cobblestone"));
        }
        if (block.equals(Registries.BLOCK.get(new Identifier("minecraft", "stone_bricks")))) {
            return Registries.BLOCK.get(new Identifier("minecraft", "cracked_stone_bricks"));
        }
        if (block.equals(Registries.BLOCK.get(new Identifier("minecraft", "deepslate")))) {
            return Registries.BLOCK.get(new Identifier("minecraft", "cobbled_deepslate"));
        }
        if (block.equals(Registries.BLOCK.get(new Identifier("minecraft", "deepslate_bricks")))) {
            return Registries.BLOCK.get(new Identifier("minecraft", "cracked_deepslate_bricks"));
        }
        if (block.equals(Registries.BLOCK.get(new Identifier("minecraft", "deepslate_tiles")))) {
            return Registries.BLOCK.get(new Identifier("minecraft", "cracked_deepslate_tiles"));
        }
        if (block.equals(Registries.BLOCK.get(new Identifier("minecraft", "nether_bricks")))) {
            return Registries.BLOCK.get(new Identifier("minecraft", "cracked_nether_bricks"));
        }
        if (block.equals(Registries.BLOCK.get(new Identifier("minecraft", "polished_blackstone_bricks")))) {
            return Registries.BLOCK.get(new Identifier("minecraft", "cracked_polished_blackstone_bricks"));
        }
        if (block.equals(Registries.BLOCK.get(new Identifier("minecraft", "infested_stone_bricks")))) {
            return Registries.BLOCK.get(new Identifier("minecraft", "infested_cracked_stone_bricks"));
        }
        return null;
    }
    public abstract float getShotSpeed();
    public abstract float getBaseDamage();
}
