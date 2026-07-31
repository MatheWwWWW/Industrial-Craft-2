/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.lighting.LevelLightEngine;

public interface BlockAndTintGetter
extends BlockGetter {
    public float m_7717_(Direction var1, boolean var2);

    public LevelLightEngine m_5518_();

    public int m_6171_(BlockPos var1, ColorResolver var2);

    default public int m_45517_(LightLayer p_45518_, BlockPos p_45519_) {
        return this.m_5518_().m_75814_(p_45518_).m_7768_(p_45519_);
    }

    default public int m_45524_(BlockPos p_45525_, int p_45526_) {
        return this.m_5518_().m_75831_(p_45525_, p_45526_);
    }

    default public boolean m_45527_(BlockPos p_45528_) {
        return this.m_45517_(LightLayer.SKY, p_45528_) >= this.m_7469_();
    }
}

