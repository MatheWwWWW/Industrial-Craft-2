/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.items.electric;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IElectricItemManager {
    public int charge(ItemStack var1, int var2, int var3, boolean var4, boolean var5);

    public int discharge(ItemStack var1, int var2, int var3, boolean var4, boolean var5, boolean var6);

    public int getCharge(ItemStack var1);

    public int getCapacity(ItemStack var1);

    public boolean canUse(ItemStack var1, int var2);

    public boolean use(ItemStack var1, int var2, LivingEntity var3);

    public void chargeFromArmor(ItemStack var1, LivingEntity var2);

    public int getTier(ItemStack var1);

    public int getTransferLimit(ItemStack var1);

    public Component getToolTip(ItemStack var1);
}

