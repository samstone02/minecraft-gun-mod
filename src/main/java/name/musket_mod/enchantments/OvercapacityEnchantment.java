package name.musket_mod.enchantments;

import name.musket_mod.MusketMod;
import name.musket_mod.items.MusketItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class OvercapacityEnchantment extends Enchantment {
    public static String ENCHANTMENT_ID = "overcapacity";
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
    public boolean isAcceptableItem(ItemStack stack) {
        return super.isAcceptableItem(stack)
            || stack.getItem() instanceof MusketItem;
    }
}
