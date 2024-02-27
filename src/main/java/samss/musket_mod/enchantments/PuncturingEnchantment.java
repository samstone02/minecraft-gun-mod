package samss.musket_mod.enchantments;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class PuncturingEnchantment extends MusketEnchantment {
    protected PuncturingEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }
    @Override
    public int getMinPower(int level) { return 10 * (level - 1); }
    @Override
    public int getMaxPower(int level) {
        return 100;
    }
    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public String getEnchantmentId() {
        return "puncturing";
    }
}
