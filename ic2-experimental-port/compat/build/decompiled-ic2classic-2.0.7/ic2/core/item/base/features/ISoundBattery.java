/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.base.features;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface ISoundBattery {
    public boolean wantsToPlay(ItemStack var1);

    public ResourceLocation getSound(ItemStack var1);
}

