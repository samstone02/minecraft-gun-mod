package name.musket_mod.enchantments;

import name.musket_mod.MusketMod;
import name.musket_mod.items.MusketItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class OvercapacityEnchantment extends Enchantment {
    public static String ENCHANTMENT_ID = "overcapacity";
    protected OvercapacityEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }
    @Override
    public int getMinPower(int level) {
        return 1;
    }
    @Override
    public int getMaxLevel() {
        return 2;
    }
    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        MusketMod.LOGGER.info("In Overcap enchant: " + stack.getItem().getName());
        MusketMod.LOGGER.info((stack.getItem() instanceof MusketItem) + "");
        return super.isAcceptableItem(stack)
            || stack.getItem() instanceof MusketItem;
    }
}
