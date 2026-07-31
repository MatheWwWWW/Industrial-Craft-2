/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.item;

import ic2.api.item.IElectricItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IElectricItemManager {
    public double charge(ItemStack var1, double var2, int var4, boolean var5, boolean var6);

    public double discharge(ItemStack var1, double var2, int var4, boolean var5, boolean var6, boolean var7);

    public double getCharge(ItemStack var1);

    public double getStackCharge(ItemStack var1);

    public double getMaxCharge(ItemStack var1);

    default public double getChargeLevel(ItemStack itemStack) {
        return Math.max(0.0, Math.min(1.0, this.getCharge(itemStack) / this.getMaxCharge(itemStack)));
    }

    default public double getStackChargeLevel(ItemStack itemStack) {
        IElectricItem iElectricItem = (IElectricItem)itemStack.m_41720_();
        if (!1.$assertionsDisabled && !(iElectricItem.getMaxCharge(itemStack) > 0.0)) {
            throw new AssertionError();
        }
        return Math.max(0.0, Math.min(1.0, this.getStackCharge(itemStack) / iElectricItem.getMaxCharge(itemStack)));
    }

    public boolean canUse(ItemStack var1, double var2);

    public boolean use(ItemStack var1, double var2, LivingEntity var4);

    public void chargeFromArmor(ItemStack var1, LivingEntity var2);

    public String getToolTip(ItemStack var1);

    public int getTier(ItemStack var1);

    static {
        if (1.$assertionsDisabled) {
            // empty if block
        }
    }
}

