/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.crops;

import ic2.api.crops.CropCard;
import net.minecraft.world.item.ItemStack;

public interface ICropSeed {
    public CropCard getCropFromStack(ItemStack var1);

    public void setCropFromStack(ItemStack var1, CropCard var2);

    public int getGrowthFromStack(ItemStack var1);

    public void setGrowthFromStack(ItemStack var1, int var2);

    public int getGainFromStack(ItemStack var1);

    public void setGainFromStack(ItemStack var1, int var2);

    public int getResistanceFromStack(ItemStack var1);

    public void setResistanceFromStack(ItemStack var1, int var2);

    public int getScannedFromStack(ItemStack var1);

    public void setScannedFromStack(ItemStack var1, int var2);

    public void incrementScannedFromStack(ItemStack var1);
}

