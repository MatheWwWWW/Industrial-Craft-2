/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.platform.rendering.features.item;

import ic2.core.platform.rendering.features.item.ICustomItemModelTransform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface IToolModel
extends ICustomItemModelTransform {
    @Override
    default public boolean hasCustomTransform(ItemStack stack) {
        return true;
    }

    @Override
    default public ResourceLocation getCustomTransform(ItemStack stack) {
        return new ResourceLocation("minecraft:models/item/handheld");
    }
}

