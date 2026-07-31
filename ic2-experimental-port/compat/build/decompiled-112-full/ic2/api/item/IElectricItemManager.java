/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.ItemStack
 */
package ic2.api.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IElectricItemManager {
    public double charge(ItemStack var1, double var2, int var4, boolean var5, boolean var6);

    public double discharge(ItemStack var1, double var2, int var4, boolean var5, boolean var6, boolean var7);

    public double getCharge(ItemStack var1);

    public double getMaxCharge(ItemStack var1);

    public boolean canUse(ItemStack var1, double var2);

    public boolean use(ItemStack var1, double var2, EntityLivingBase var4);

    public void chargeFromArmor(ItemStack var1, EntityLivingBase var2);

    public String getToolTip(ItemStack var1);

    public int getTier(ItemStack var1);
}

