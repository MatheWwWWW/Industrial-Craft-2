/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.LevelData;

public interface WritableLevelData
extends LevelData {
    public void m_6395_(int var1);

    public void m_6397_(int var1);

    public void m_6400_(int var1);

    public void m_7113_(float var1);

    default public void m_7250_(BlockPos p_78649_, float p_78650_) {
        this.m_6395_(p_78649_.m_123341_());
        this.m_6397_(p_78649_.m_123342_());
        this.m_6400_(p_78649_.m_123343_());
        this.m_7113_(p_78650_);
    }
}

