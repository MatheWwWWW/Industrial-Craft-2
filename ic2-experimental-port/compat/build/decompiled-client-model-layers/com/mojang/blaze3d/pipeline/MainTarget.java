/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package com.mojang.blaze3d.pipeline;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Objects;

public class MainTarget
extends RenderTarget {
    public static final int f_166132_ = 854;
    public static final int f_166133_ = 480;
    static final Dimension f_166134_ = new Dimension(854, 480);

    public MainTarget(int p_166137_, int p_166138_) {
        super(true);
        RenderSystem.m_187555_();
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> this.m_166141_(p_166137_, p_166138_));
        } else {
            this.m_166141_(p_166137_, p_166138_);
        }
    }

    private void m_166141_(int p_166142_, int p_166143_) {
        RenderSystem.m_187555_();
        Dimension $$2 = this.m_166146_(p_166142_, p_166143_);
        this.f_83920_ = GlStateManager.m_84543_();
        GlStateManager.m_84486_(36160, this.f_83920_);
        GlStateManager.m_84544_(this.f_83923_);
        GlStateManager.m_84331_(3553, 10241, 9728);
        GlStateManager.m_84331_(3553, 10240, 9728);
        GlStateManager.m_84331_(3553, 10242, 33071);
        GlStateManager.m_84331_(3553, 10243, 33071);
        GlStateManager.m_84173_(36160, 36064, 3553, this.f_83923_, 0);
        GlStateManager.m_84544_(this.f_83924_);
        GlStateManager.m_84331_(3553, 34892, 0);
        GlStateManager.m_84331_(3553, 10241, 9728);
        GlStateManager.m_84331_(3553, 10240, 9728);
        GlStateManager.m_84331_(3553, 10242, 33071);
        GlStateManager.m_84331_(3553, 10243, 33071);
        GlStateManager.m_84173_(36160, 36096, 3553, this.f_83924_, 0);
        GlStateManager.m_84544_(0);
        this.f_83917_ = $$2.f_166168_;
        this.f_83918_ = $$2.f_166169_;
        this.f_83915_ = $$2.f_166168_;
        this.f_83916_ = $$2.f_166169_;
        this.m_83949_();
        GlStateManager.m_84486_(36160, 0);
    }

    private Dimension m_166146_(int p_166147_, int p_166148_) {
        RenderSystem.m_187555_();
        this.f_83923_ = TextureUtil.m_85280_();
        this.f_83924_ = TextureUtil.m_85280_();
        AttachmentState $$2 = AttachmentState.NONE;
        for (Dimension $$3 : Dimension.m_166173_(p_166147_, p_166148_)) {
            $$2 = AttachmentState.NONE;
            if (this.m_166139_($$3)) {
                $$2 = $$2.m_166163_(AttachmentState.COLOR);
            }
            if (this.m_166144_($$3)) {
                $$2 = $$2.m_166163_(AttachmentState.DEPTH);
            }
            if ($$2 != AttachmentState.COLOR_DEPTH) continue;
            return $$3;
        }
        throw new RuntimeException("Unrecoverable GL_OUT_OF_MEMORY (allocated attachments = " + $$2.name() + ")");
    }

    private boolean m_166139_(Dimension p_166140_) {
        RenderSystem.m_187555_();
        GlStateManager.m_84118_();
        GlStateManager.m_84544_(this.f_83923_);
        GlStateManager.m_84209_(3553, 0, 32856, p_166140_.f_166168_, p_166140_.f_166169_, 0, 6408, 5121, null);
        return GlStateManager.m_84118_() != 1285;
    }

    private boolean m_166144_(Dimension p_166145_) {
        RenderSystem.m_187555_();
        GlStateManager.m_84118_();
        GlStateManager.m_84544_(this.f_83924_);
        GlStateManager.m_84209_(3553, 0, 6402, p_166145_.f_166168_, p_166145_.f_166169_, 0, 6402, 5126, null);
        return GlStateManager.m_84118_() != 1285;
    }

    static class Dimension {
        public final int f_166168_;
        public final int f_166169_;

        Dimension(int p_166171_, int p_166172_) {
            this.f_166168_ = p_166171_;
            this.f_166169_ = p_166172_;
        }

        static List<Dimension> m_166173_(int p_166174_, int p_166175_) {
            RenderSystem.m_187555_();
            int $$2 = RenderSystem.m_69839_();
            if (p_166174_ <= 0 || p_166174_ > $$2 || p_166175_ <= 0 || p_166175_ > $$2) {
                return ImmutableList.of((Object)f_166134_);
            }
            return ImmutableList.of((Object)new Dimension(p_166174_, p_166175_), (Object)f_166134_);
        }

        public boolean equals(Object p_166177_) {
            if (this == p_166177_) {
                return true;
            }
            if (p_166177_ == null || this.getClass() != p_166177_.getClass()) {
                return false;
            }
            Dimension $$1 = (Dimension)p_166177_;
            return this.f_166168_ == $$1.f_166168_ && this.f_166169_ == $$1.f_166169_;
        }

        public int hashCode() {
            return Objects.hash(this.f_166168_, this.f_166169_);
        }

        public String toString() {
            return this.f_166168_ + "x" + this.f_166169_;
        }
    }

    static final class AttachmentState
    extends Enum<AttachmentState> {
        public static final /* enum */ AttachmentState NONE = new AttachmentState();
        public static final /* enum */ AttachmentState COLOR = new AttachmentState();
        public static final /* enum */ AttachmentState DEPTH = new AttachmentState();
        public static final /* enum */ AttachmentState COLOR_DEPTH = new AttachmentState();
        private static final AttachmentState[] f_166156_;
        private static final /* synthetic */ AttachmentState[] $VALUES;

        public static AttachmentState[] values() {
            return (AttachmentState[])$VALUES.clone();
        }

        public static AttachmentState valueOf(String p_166166_) {
            return Enum.valueOf(AttachmentState.class, p_166166_);
        }

        AttachmentState m_166163_(AttachmentState p_166164_) {
            return f_166156_[this.ordinal() | p_166164_.ordinal()];
        }

        private static /* synthetic */ AttachmentState[] m_166162_() {
            return new AttachmentState[]{NONE, COLOR, DEPTH, COLOR_DEPTH};
        }

        static {
            $VALUES = AttachmentState.m_166162_();
            f_166156_ = AttachmentState.values();
        }
    }
}

