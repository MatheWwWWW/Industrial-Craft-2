/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface IWindmillBlade {
    public int getRadius(ItemStack var1);

    public float getEffectiveness(ItemStack var1);

    public ResourceLocation getTexture(ItemStack var1);
}

