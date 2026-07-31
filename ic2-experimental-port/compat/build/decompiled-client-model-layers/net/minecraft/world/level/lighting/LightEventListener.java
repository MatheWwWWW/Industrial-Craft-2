/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.lighting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;

public interface LightEventListener {
    public void m_7174_(BlockPos var1);

    public void m_8116_(BlockPos var1, int var2);

    public boolean m_75643_();

    public int m_5738_(int var1, boolean var2, boolean var3);

    default public void m_75834_(BlockPos p_75835_, boolean p_75836_) {
        this.m_6191_(SectionPos.m_123199_(p_75835_), p_75836_);
    }

    public void m_6191_(SectionPos var1, boolean var2);

    public void m_6460_(ChunkPos var1, boolean var2);
}

