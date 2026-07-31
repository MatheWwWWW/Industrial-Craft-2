/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.DamageSource
 *  net.minecraftforge.common.ISpecialArmor$ArmorProperties
 */
package ic2.core.item.armor;

import ic2.api.item.ElectricItem;
import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.item.armor.jetpack.IJetpack;
import ic2.core.ref.ItemName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;

public class ItemArmorJetpackElectric
extends ItemArmorElectric
implements IJetpack {
    public ItemArmorJetpackElectric() {
        super(ItemName.jetpack_electric, "jetpack", EntityEquipmentSlot.CHEST, 30000.0, 60.0, 1);
        this.func_77656_e(27);
        this.func_77625_d(1);
        this.setNoRepair();
    }

    @Override
    public boolean drainEnergy(ItemStack pack, int amount) {
        return ElectricItem.manager.discharge(pack, amount + 6, Integer.MAX_VALUE, true, false, false) > 0.0;
    }

    @Override
    public float getPower(ItemStack stack) {
        return 0.7f;
    }

    @Override
    public float getDropPercentage(ItemStack stack) {
        return 0.05f;
    }

    @Override
    public boolean isJetpackActive(ItemStack stack) {
        return true;
    }

    @Override
    public double getChargeLevel(ItemStack stack) {
        return ElectricItem.manager.getCharge(stack) / this.getMaxCharge(stack);
    }

    @Override
    public float getHoverMultiplier(ItemStack stack, boolean upwards) {
        return 0.1f;
    }

    @Override
    public float getWorldHeightDivisor(ItemStack stack) {
        return 1.28f;
    }

    @Override
    public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        return new ISpecialArmor.ArmorProperties(0, 0.0, 0);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return 0;
    }

    @Override
    public int getEnergyPerDamage() {
        return 0;
    }

    @Override
    public double getDamageAbsorptionRatio() {
        return 0.0;
    }
}

