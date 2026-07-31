package ru.mot.ic2exfidelity.legacy;

import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.ref.Ic2ArmorMaterials;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** Zero-armour chest energy storage with the exact 2.8.222 EU parameters. */
public final class LegacyEnergyPack extends ItemArmorElectric {
    private final String armorTexture;

    public LegacyEnergyPack(
            Item.Properties properties,
            double capacity,
            double transferLimit,
            int tier,
            String armorTexture) {
        super(Ic2ArmorMaterials.CF_PACK, EquipmentSlot.CHEST, properties, capacity, transferLimit, tier);
        this.armorTexture = "ic2:textures/armor/" + armorTexture + "_1.png";
    }

    @Override
    public int getEnergyPerDamage() {
        return 0;
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return true;
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return armorTexture;
    }
}
