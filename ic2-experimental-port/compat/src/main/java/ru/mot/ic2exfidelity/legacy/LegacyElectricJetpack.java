package ru.mot.ic2exfidelity.legacy;

import ic2.api.item.ElectricItem;
import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.item.armor.jetpack.IJetpack;
import ic2.core.ref.Ic2ArmorMaterials;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** Exact IC2 2.8 electric-jetpack energy and flight parameters. */
public final class LegacyElectricJetpack extends ItemArmorElectric implements IJetpack {
    public LegacyElectricJetpack(Item.Properties properties) {
        super(Ic2ArmorMaterials.CF_PACK, EquipmentSlot.CHEST, properties, 30_000.0, 60.0, 1);
    }

    @Override
    public boolean drainEnergy(ItemStack stack, int amount) {
        return ElectricItem.manager.discharge(
                stack, amount + IJetpack.EU_ENERGY_INCREASE,
                Integer.MAX_VALUE, true, false, false) > 0.0;
    }

    @Override
    public float getPower(ItemStack stack) {
        return 0.7F;
    }

    @Override
    public float getDropPercentage(ItemStack stack) {
        return 0.05F;
    }

    @Override
    public boolean isJetpackActive(ItemStack stack) {
        return true;
    }

    @Override
    public double getChargeLevel(ItemStack stack) {
        return ElectricItem.manager.getCharge(stack) / getMaxCharge(stack);
    }

    @Override
    public float getHoverMultiplier(ItemStack stack, boolean upwards) {
        return 0.1F;
    }

    @Override
    public float getWorldHeightDivisor(ItemStack stack) {
        return 1.28F;
    }

    @Override
    public int getEnergyPerDamage() {
        return 0;
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "ic2:textures/armor/jetpack_1.png";
    }
}
