/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer.chunk;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;

public class RenderChunkRegion
implements BlockAndTintGetter {
    private final int f_112899_;
    private final int f_112900_;
    protected final RenderChunk[][] f_112905_;
    protected final Level f_112908_;

    RenderChunkRegion(Level p_200456_, int p_200457_, int p_200458_, RenderChunk[][] p_200459_) {
        this.f_112908_ = p_200456_;
        this.f_112899_ = p_200457_;
        this.f_112900_ = p_200458_;
        this.f_112905_ = p_200459_;
    }

    @Override
    public BlockState m_8055_(BlockPos p_112947_) {
        int $$1 = SectionPos.m_123171_(p_112947_.m_123341_()) - this.f_112899_;
        int $$2 = SectionPos.m_123171_(p_112947_.m_123343_()) - this.f_112900_;
        return this.f_112905_[$$1][$$2].m_200453_(p_112947_);
    }

    @Override
    public FluidState m_6425_(BlockPos p_112943_) {
        int $$1 = SectionPos.m_123171_(p_112943_.m_123341_()) - this.f_112899_;
        int $$2 = SectionPos.m_123171_(p_112943_.m_123343_()) - this.f_112900_;
        return this.f_112905_[$$1][$$2].m_200453_(p_112943_).m_60819_();
    }

    @Override
    public float m_7717_(Direction p_112940_, boolean p_112941_) {
        return this.f_112908_.m_7717_(p_112940_, p_112941_);
    }

    @Override
    public LevelLightEngine m_5518_() {
        return this.f_112908_.m_5518_();
    }

    @Override
    @Nullable
    public BlockEntity m_7702_(BlockPos p_112945_) {
        int $$1 = SectionPos.m_123171_(p_112945_.m_123341_()) - this.f_112899_;
        int $$2 = SectionPos.m_123171_(p_112945_.m_123343_()) - this.f_112900_;
        return this.f_112905_[$$1][$$2].m_200451_(p_112945_);
    }

    @Override
    public int m_6171_(BlockPos p_112937_, ColorResolver p_112938_) {
        return this.f_112908_.m_6171_(p_112937_, p_112938_);
    }

    @Override
    public int m_141937_() {
        return this.f_112908_.m_141937_();
    }

    @Override
    public int m_141928_() {
        return this.f_112908_.m_141928_();
    }
}

