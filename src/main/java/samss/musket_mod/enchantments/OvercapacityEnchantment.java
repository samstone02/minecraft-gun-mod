package samss.musket_mod.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class OvercapacityEnchantment extends MusketEnchantment {
    protected OvercapacityEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }
    @Override
    public int getMinPower(int level) {
        return 15 + 15 * (level - 1);
    }
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
        return "overcapacity";
    }
}
