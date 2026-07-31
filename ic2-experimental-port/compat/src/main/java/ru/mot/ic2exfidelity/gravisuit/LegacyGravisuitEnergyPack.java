package ru.mot.ic2exfidelity.gravisuit;

import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.ref.Ic2ArmorMaterials;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** Advanced and ultimate lappacks with Gravisuit 2.2's exact EU values. */
public final class LegacyGravisuitEnergyPack extends ItemArmorElectric {
    private final String armorTexture;

    public LegacyGravisuitEnergyPack(
            Item.Properties properties,
            double capacity,
            double transferLimit,
            int tier,
            String armorTexture) {
        super(Ic2ArmorMaterials.CF_PACK, EquipmentSlot.CHEST, properties,
                capacity, transferLimit, tier);
        this.armorTexture = armorTexture;
    }

    @Override
    public int getEnergyPerDamage() {
        return 0;
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return true;
    }

    public String getArmorTexture(
            ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return armorTexture;
    }
}
