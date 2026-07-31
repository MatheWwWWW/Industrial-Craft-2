/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.platform.rendering.features.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface ICustomItemModelTransform {
    public boolean hasCustomTransform(ItemStack var1);

    public ResourceLocation getCustomTransform(ItemStack var1);
}

