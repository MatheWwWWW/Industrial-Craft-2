/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  javax.annotation.Nullable
 */
package com.mojang.blaze3d.vertex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class VertexFormat {
    private final ImmutableList<VertexFormatElement> f_86012_;
    private final ImmutableMap<String, VertexFormatElement> f_166905_;
    private final IntList f_86013_ = new IntArrayList();
    private final int f_86014_;
    @Nullable
    private VertexBuffer f_231232_;

    public VertexFormat(ImmutableMap<String, VertexFormatElement> p_166910_) {
        this.f_166905_ = p_166910_;
        this.f_86012_ = p_166910_.values().asList();
        int $$1 = 0;
        for (VertexFormatElement $$2 : p_166910_.values()) {
            this.f_86013_.add($$1);
            $$1 += $$2.m_86050_();
        }
        this.f_86014_ = $$1;
    }

    public String toString() {
        return "format: " + this.f_166905_.size() + " elements: " + this.f_166905_.entrySet().stream().map(Object::toString).collect(Collectors.joining(" "));
    }

    public int m_86017_() {
        return this.m_86020_() / 4;
    }

    public int m_86020_() {
        return this.f_86014_;
    }

    public ImmutableList<VertexFormatElement> m_86023_() {
        return this.f_86012_;
    }

    public ImmutableList<String> m_166911_() {
        return this.f_166905_.keySet().asList();
    }

    public boolean equals(Object p_86026_) {
        if (this == p_86026_) {
            return true;
        }
        if (p_86026_ == null || this.getClass() != p_86026_.getClass()) {
            return false;
        }
        VertexFormat $$1 = (VertexFormat)p_86026_;
        if (this.f_86014_ != $$1.f_86014_) {
            return false;
        }
        return this.f_166905_.equals($$1.f_166905_);
    }

    public int hashCode() {
        return this.f_166905_.hashCode();
    }

    public void m_166912_() {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(this::m_166916_);
            return;
        }
        this.m_166916_();
    }

    private void m_166916_() {
        int $$0 = this.m_86020_();
        ImmutableList<VertexFormatElement> $$1 = this.m_86023_();
        for (int $$2 = 0; $$2 < $$1.size(); ++$$2) {
            ((VertexFormatElement)$$1.get($$2)).m_166965_($$2, this.f_86013_.getInt($$2), $$0);
        }
    }

    public void m_86024_() {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(this::m_166917_);
            return;
        }
        this.m_166917_();
    }

    private void m_166917_() {
        ImmutableList<VertexFormatElement> $$0 = this.m_86023_();
        for (int $$1 = 0; $$1 < $$0.size(); ++$$1) {
            VertexFormatElement $$2 = (VertexFormatElement)$$0.get($$1);
            $$2.m_166963_($$1);
        }
    }

    public VertexBuffer m_231233_() {
        VertexBuffer $$0 = this.f_231232_;
        if ($$0 == null) {
            this.f_231232_ = $$0 = new VertexBuffer();
        }
        return $$0;
    }

    public static final class Mode
    extends Enum<Mode> {
        public static final /* enum */ Mode LINES = new Mode(4, 2, 2, false);
        public static final /* enum */ Mode LINE_STRIP = new Mode(5, 2, 1, true);
        public static final /* enum */ Mode DEBUG_LINES = new Mode(1, 2, 2, false);
        public static final /* enum */ Mode DEBUG_LINE_STRIP = new Mode(3, 2, 1, true);
        public static final /* enum */ Mode TRIANGLES = new Mode(4, 3, 3, false);
        public static final /* enum */ Mode TRIANGLE_STRIP = new Mode(5, 3, 1, true);
        public static final /* enum */ Mode TRIANGLE_FAN = new Mode(6, 3, 1, true);
        public static final /* enum */ Mode QUADS = new Mode(4, 4, 4, false);
        public final int f_166946_;
        public final int f_166947_;
        public final int f_166948_;
        public final boolean f_231234_;
        private static final /* synthetic */ Mode[] $VALUES;

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }

        public static Mode valueOf(String p_166961_) {
            return Enum.valueOf(Mode.class, p_166961_);
        }

        private Mode(int p_231238_, int p_231239_, int p_231240_, boolean p_231241_) {
            this.f_166946_ = p_231238_;
            this.f_166947_ = p_231239_;
            this.f_166948_ = p_231240_;
            this.f_231234_ = p_231241_;
        }

        public int m_166958_(int p_166959_) {
            int $$3;
            switch (this) {
                case LINE_STRIP: 
                case DEBUG_LINES: 
                case DEBUG_LINE_STRIP: 
                case TRIANGLES: 
                case TRIANGLE_STRIP: 
                case TRIANGLE_FAN: {
                    int $$1 = p_166959_;
                    break;
                }
                case LINES: 
                case QUADS: {
                    int $$2 = p_166959_ / 4 * 6;
                    break;
                }
                default: {
                    $$3 = 0;
                }
            }
            return $$3;
        }

        private static /* synthetic */ Mode[] m_166957_() {
            return new Mode[]{LINES, LINE_STRIP, DEBUG_LINES, DEBUG_LINE_STRIP, TRIANGLES, TRIANGLE_STRIP, TRIANGLE_FAN, QUADS};
        }

        static {
            $VALUES = Mode.m_166957_();
        }
    }

    public static final class IndexType
    extends Enum<IndexType> {
        public static final /* enum */ IndexType BYTE = new IndexType(5121, 1);
        public static final /* enum */ IndexType SHORT = new IndexType(5123, 2);
        public static final /* enum */ IndexType INT = new IndexType(5125, 4);
        public final int f_166923_;
        public final int f_166924_;
        private static final /* synthetic */ IndexType[] $VALUES;

        public static IndexType[] values() {
            return (IndexType[])$VALUES.clone();
        }

        public static IndexType valueOf(String p_166936_) {
            return Enum.valueOf(IndexType.class, p_166936_);
        }

        private IndexType(int p_166930_, int p_166931_) {
            this.f_166923_ = p_166930_;
            this.f_166924_ = p_166931_;
        }

        public static IndexType m_166933_(int p_166934_) {
            if ((p_166934_ & 0xFFFF0000) != 0) {
                return INT;
            }
            if ((p_166934_ & 0xFF00) != 0) {
                return SHORT;
            }
            return BYTE;
        }

        private static /* synthetic */ IndexType[] m_166932_() {
            return new IndexType[]{BYTE, SHORT, INT};
        }

        static {
            $VALUES = IndexType.m_166932_();
        }
    }
}

