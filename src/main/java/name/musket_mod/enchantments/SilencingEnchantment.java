package name.musket_mod.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class SilencingEnchantment extends MusketEnchantment {
    protected SilencingEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }
    @Override
    public int getMinPower(int level) { return 30; }
    @Override
    public int getMaxPower(int level) {
        return 100;
    }
    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public String getEnchantmentId() {
        return "silencing";
    }
}
