package name.musket_mod.enchantments;

import name.musket_mod.items.MusketItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class OvercapacityEnchantment extends Enchantment {
    public static String ENCHANTMENT_ID = "overcapacity";
    protected OvercapacityEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, null, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
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
        return stack.getItem() instanceof MusketItem;
    }
}
