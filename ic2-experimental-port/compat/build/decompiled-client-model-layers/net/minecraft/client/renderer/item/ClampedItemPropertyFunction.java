/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.item;

import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ClampedItemPropertyFunction
extends ItemPropertyFunction {
    @Override
    @Deprecated
    default public float m_141951_(ItemStack p_174560_, @Nullable ClientLevel p_174561_, @Nullable LivingEntity p_174562_, int p_174563_) {
        return Mth.m_14036_(this.m_142187_(p_174560_, p_174561_, p_174562_, p_174563_), 0.0f, 1.0f);
    }

    public float m_142187_(ItemStack var1, @Nullable ClientLevel var2, @Nullable LivingEntity var3, int var4);
}

