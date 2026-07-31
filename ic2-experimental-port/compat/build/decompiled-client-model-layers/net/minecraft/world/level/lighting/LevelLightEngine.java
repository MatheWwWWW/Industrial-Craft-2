/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.lighting;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.BlockLightEngine;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraft.world.level.lighting.LayerLightEventListener;
import net.minecraft.world.level.lighting.LightEventListener;
import net.minecraft.world.level.lighting.SkyLightEngine;

public class LevelLightEngine
implements LightEventListener {
    public static final int f_164443_ = 15;
    public static final int f_164444_ = 1;
    protected final LevelHeightAccessor f_164445_;
    @Nullable
    private final LayerLightEngine<?, ?> f_75802_;
    @Nullable
    private final LayerLightEngine<?, ?> f_75803_;

    public LevelLightEngine(LightChunkGetter p_75805_, boolean p_75806_, boolean p_75807_) {
        this.f_164445_ = p_75805_.m_7653_();
        this.f_75802_ = p_75806_ ? new BlockLightEngine(p_75805_) : null;
        this.f_75803_ = p_75807_ ? new SkyLightEngine(p_75805_) : null;
    }

    @Override
    public void m_7174_(BlockPos p_75823_) {
        if (this.f_75802_ != null) {
            this.f_75802_.m_7174_(p_75823_);
        }
        if (this.f_75803_ != null) {
            this.f_75803_.m_7174_(p_75823_);
        }
    }

    @Override
    public void m_8116_(BlockPos p_75824_, int p_75825_) {
        if (this.f_75802_ != null) {
            this.f_75802_.m_8116_(p_75824_, p_75825_);
        }
    }

    @Override
    public boolean m_75643_() {
        if (this.f_75803_ != null && this.f_75803_.m_75643_()) {
            return true;
        }
        return this.f_75802_ != null && this.f_75802_.m_75643_();
    }

    @Override
    public int m_5738_(int p_75809_, boolean p_75810_, boolean p_75811_) {
        if (this.f_75802_ != null && this.f_75803_ != null) {
            int $$3 = p_75809_ / 2;
            int $$4 = this.f_75802_.m_5738_($$3, p_75810_, p_75811_);
            int $$5 = p_75809_ - $$3 + $$4;
            int $$6 = this.f_75803_.m_5738_($$5, p_75810_, p_75811_);
            if ($$4 == 0 && $$6 > 0) {
                return this.f_75802_.m_5738_($$6, p_75810_, p_75811_);
            }
            return $$6;
        }
        if (this.f_75802_ != null) {
            return this.f_75802_.m_5738_(p_75809_, p_75810_, p_75811_);
        }
        if (this.f_75803_ != null) {
            return this.f_75803_.m_5738_(p_75809_, p_75810_, p_75811_);
        }
        return p_75809_;
    }

    @Override
    public void m_6191_(SectionPos p_75827_, boolean p_75828_) {
        if (this.f_75802_ != null) {
            this.f_75802_.m_6191_(p_75827_, p_75828_);
        }
        if (this.f_75803_ != null) {
            this.f_75803_.m_6191_(p_75827_, p_75828_);
        }
    }

    @Override
    public void m_6460_(ChunkPos p_75812_, boolean p_75813_) {
        if (this.f_75802_ != null) {
            this.f_75802_.m_6460_(p_75812_, p_75813_);
        }
        if (this.f_75803_ != null) {
            this.f_75803_.m_6460_(p_75812_, p_75813_);
        }
    }

    public LayerLightEventListener m_75814_(LightLayer p_75815_) {
        if (p_75815_ == LightLayer.BLOCK) {
            if (this.f_75802_ == null) {
                return LayerLightEventListener.DummyLightLayerEventListener.INSTANCE;
            }
            return this.f_75802_;
        }
        if (this.f_75803_ == null) {
            return LayerLightEventListener.DummyLightLayerEventListener.INSTANCE;
        }
        return this.f_75803_;
    }

    public String m_75816_(LightLayer p_75817_, SectionPos p_75818_) {
        if (p_75817_ == LightLayer.BLOCK) {
            if (this.f_75802_ != null) {
                return this.f_75802_.m_6647_(p_75818_.m_123252_());
            }
        } else if (this.f_75803_ != null) {
            return this.f_75803_.m_6647_(p_75818_.m_123252_());
        }
        return "n/a";
    }

    public void m_5687_(LightLayer p_75819_, SectionPos p_75820_, @Nullable DataLayer p_75821_, boolean p_75822_) {
        if (p_75819_ == LightLayer.BLOCK) {
            if (this.f_75802_ != null) {
                this.f_75802_.m_75660_(p_75820_.m_123252_(), p_75821_, p_75822_);
            }
        } else if (this.f_75803_ != null) {
            this.f_75803_.m_75660_(p_75820_.m_123252_(), p_75821_, p_75822_);
        }
    }

    public void m_6462_(ChunkPos p_75829_, boolean p_75830_) {
        if (this.f_75802_ != null) {
            this.f_75802_.m_75699_(p_75829_, p_75830_);
        }
        if (this.f_75803_ != null) {
            this.f_75803_.m_75699_(p_75829_, p_75830_);
        }
    }

    public int m_75831_(BlockPos p_75832_, int p_75833_) {
        int $$2 = this.f_75803_ == null ? 0 : this.f_75803_.m_7768_(p_75832_) - p_75833_;
        int $$3 = this.f_75802_ == null ? 0 : this.f_75802_.m_7768_(p_75832_);
        return Math.max($$3, $$2);
    }

    public int m_164446_() {
        return this.f_164445_.m_151559_() + 2;
    }

    public int m_164447_() {
        return this.f_164445_.m_151560_() - 1;
    }

    public int m_164448_() {
        return this.m_164447_() + this.m_164446_();
    }
}

