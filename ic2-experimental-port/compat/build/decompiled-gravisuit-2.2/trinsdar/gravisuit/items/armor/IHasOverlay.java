/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.utils.helpers.StackUtil
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 */
package trinsdar.gravisuit.items.armor;

import ic2.core.utils.helpers.StackUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IHasOverlay {
    default public boolean isEnabled(ItemStack stack) {
        return true;
    }

    default public CompoundTag getArmorNBT(ItemStack stack, boolean create) {
        return create ? stack.m_41784_() : StackUtil.getNbtData((ItemStack)stack);
    }
}

