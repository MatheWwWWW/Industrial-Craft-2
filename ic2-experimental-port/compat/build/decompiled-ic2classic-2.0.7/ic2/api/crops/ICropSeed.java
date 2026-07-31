/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.crops;

import ic2.api.crops.ICrop;
import net.minecraft.world.item.ItemStack;

public interface ICropSeed {
    public static final int[] SCAN_COST = new int[]{10, 90, 900, 9000};
    public static final String[] TIERS = new String[]{"0", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI"};

    public ICrop getCrop(ItemStack var1);

    public int getScanLevel(ItemStack var1);

    public void setScanLevel(ItemStack var1, int var2);

    default public void increaseScanLevel(ItemStack stack) {
        this.setScanLevel(stack, this.getScanLevel(stack) + 1);
    }

    public int getGrowth(ItemStack var1);

    public void setGrowth(ItemStack var1, int var2);

    public int getGain(ItemStack var1);

    public void setGain(ItemStack var1, int var2);

    public int getResistance(ItemStack var1);

    public void setResistance(ItemStack var1, int var2);
}

