/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 */
package net.minecraft.world.level.levelgen;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

public interface PositionalRandomFactory {
    default public RandomSource m_224542_(BlockPos p_224543_) {
        return this.m_213715_(p_224543_.m_123341_(), p_224543_.m_123342_(), p_224543_.m_123343_());
    }

    default public RandomSource m_224540_(ResourceLocation p_224541_) {
        return this.m_214111_(p_224541_.toString());
    }

    public RandomSource m_214111_(String var1);

    public RandomSource m_213715_(int var1, int var2, int var3);

    @VisibleForTesting
    public void m_183502_(StringBuilder var1);
}

