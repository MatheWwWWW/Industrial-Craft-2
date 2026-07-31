/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

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

