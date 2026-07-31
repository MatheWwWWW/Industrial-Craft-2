/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 */
package ic2.api.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface IKineticRotor {
    public int getDiameter(ItemStack var1);

    public ResourceLocation getRotorRenderTexture(ItemStack var1);

    public float getEfficiency(ItemStack var1);

    public int getMinWindStrength(ItemStack var1);

    public int getMaxWindStrength(ItemStack var1);

    public boolean isAcceptedType(ItemStack var1, GearboxType var2);

    public static enum GearboxType {
        WATER,
        WIND;

    }
}

