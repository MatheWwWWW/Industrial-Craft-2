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

public interface ICustomDamageItem {
    public int getCustomDamage(ItemStack var1);

    public int getMaxCustomDamage(ItemStack var1);

    public void setCustomDamage(ItemStack var1, int var2);

    public boolean applyCustomDamage(ItemStack var1, int var2, EntityLivingBase var3);
}

