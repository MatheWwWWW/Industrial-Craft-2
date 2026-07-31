/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.armor.jetpack;

import net.minecraft.world.item.ItemStack;

public interface IJetpack {
    public static final int EU_ENERGY_INCREASE = 6;

    public boolean drainEnergy(ItemStack var1, int var2);

    public float getPower(ItemStack var1);

    public float getDropPercentage(ItemStack var1);

    public double getChargeLevel(ItemStack var1);

    public boolean isJetpackActive(ItemStack var1);

    public float getHoverMultiplier(ItemStack var1, boolean var2);

    public float getWorldHeightDivisor(ItemStack var1);
}

