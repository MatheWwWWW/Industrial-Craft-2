/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.platform.GlStateManager;

public class VertexFormatElement {
    private final Type f_86030_;
    private final Usage f_86031_;
    private final int f_86032_;
    private final int f_86033_;
    private final int f_86034_;

    public VertexFormatElement(int p_86037_, Type p_86038_, Usage p_86039_, int p_86040_) {
        if (!this.m_86042_(p_86037_, p_86039_)) {
            throw new IllegalStateException("Multiple vertex elements of the same type other than UVs are not supported");
        }
        this.f_86031_ = p_86039_;
        this.f_86030_ = p_86038_;
        this.f_86032_ = p_86037_;
        this.f_86033_ = p_86040_;
        this.f_86034_ = p_86038_.m_86074_() * this.f_86033_;
    }

    private boolean m_86042_(int p_86043_, Usage p_86044_) {
        return p_86043_ == 0 || p_86044_ == Usage.UV;
    }

    public final Type m_86041_() {
        return this.f_86030_;
    }

    public final Usage m_86048_() {
        return this.f_86031_;
    }

    public final int m_166969_() {
        return this.f_86033_;
    }

    public final int m_86049_() {
        return this.f_86032_;
    }

    public String toString() {
        return this.f_86033_ + "," + this.f_86031_.m_86097_() + "," + this.f_86030_.m_86075_();
    }

    public final int m_86050_() {
        return this.f_86034_;
    }

    public final boolean m_166970_() {
        return this.f_86031_ == Usage.POSITION;
    }

    public boolean equals(Object p_86053_) {
        if (this == p_86053_) {
            return true;
        }
        if (p_86053_ == null || this.getClass() != p_86053_.getClass()) {
            return false;
        }
        VertexFormatElement $$1 = (VertexFormatElement)p_86053_;
        if (this.f_86033_ != $$1.f_86033_) {
            return false;
        }
        if (this.f_86032_ != $$1.f_86032_) {
            return false;
        }
        if (this.f_86030_ != $$1.f_86030_) {
            return false;
        }
        return this.f_86031_ == $$1.f_86031_;
    }

    public int hashCode() {
        int $$0 = this.f_86030_.hashCode();
        $$0 = 31 * $$0 + this.f_86031_.hashCode();
        $$0 = 31 * $$0 + this.f_86032_;
        $$0 = 31 * $$0 + this.f_86033_;
        return $$0;
    }

    public void m_166965_(int p_166966_, long p_166967_, int p_166968_) {
        this.f_86031_.m_166981_(this.f_86033_, this.f_86030_.m_86076_(), p_166968_, p_166967_, this.f_86032_, p_166966_);
    }

    public void m_166963_(int p_166964_) {
        this.f_86031_.m_166978_(this.f_86032_, p_166964_);
    }

    public static final class Usage
    extends Enum<Usage> {
        public static final /* enum */ Usage POSITION = new Usage("Position", (p_167043_, p_167044_, p_167045_, p_167046_, p_167047_, p_167048_) -> {
            GlStateManager.m_84565_(p_167048_);
            GlStateManager.m_84238_(p_167048_, p_167043_, p_167044_, false, p_167045_, p_167046_);
        }, (p_167040_, p_167041_) -> GlStateManager.m_84086_(p_167041_));
        public static final /* enum */ Usage NORMAL = new Usage("Normal", (p_167033_, p_167034_, p_167035_, p_167036_, p_167037_, p_167038_) -> {
            GlStateManager.m_84565_(p_167038_);
            GlStateManager.m_84238_(p_167038_, p_167033_, p_167034_, true, p_167035_, p_167036_);
        }, (p_167030_, p_167031_) -> GlStateManager.m_84086_(p_167031_));
        public static final /* enum */ Usage COLOR = new Usage("Vertex Color", (p_167023_, p_167024_, p_167025_, p_167026_, p_167027_, p_167028_) -> {
            GlStateManager.m_84565_(p_167028_);
            GlStateManager.m_84238_(p_167028_, p_167023_, p_167024_, true, p_167025_, p_167026_);
        }, (p_167020_, p_167021_) -> GlStateManager.m_84086_(p_167021_));
        public static final /* enum */ Usage UV = new Usage("UV", (p_167013_, p_167014_, p_167015_, p_167016_, p_167017_, p_167018_) -> {
            GlStateManager.m_84565_(p_167018_);
            if (p_167014_ == 5126) {
                GlStateManager.m_84238_(p_167018_, p_167013_, p_167014_, false, p_167015_, p_167016_);
            } else {
                GlStateManager.m_157108_(p_167018_, p_167013_, p_167014_, p_167015_, p_167016_);
            }
        }, (p_167010_, p_167011_) -> GlStateManager.m_84086_(p_167011_));
        public static final /* enum */ Usage PADDING = new Usage("Padding", (p_167003_, p_167004_, p_167005_, p_167006_, p_167007_, p_167008_) -> {}, (p_167000_, p_167001_) -> {});
        public static final /* enum */ Usage GENERIC = new Usage("Generic", (p_166993_, p_166994_, p_166995_, p_166996_, p_166997_, p_166998_) -> {
            GlStateManager.m_84565_(p_166998_);
            GlStateManager.m_84238_(p_166998_, p_166993_, p_166994_, false, p_166995_, p_166996_);
        }, (p_166990_, p_166991_) -> GlStateManager.m_84086_(p_166991_));
        private final String f_86086_;
        private final SetupState f_86087_;
        private final ClearState f_86088_;
        private static final /* synthetic */ Usage[] $VALUES;

        public static Usage[] values() {
            return (Usage[])$VALUES.clone();
        }

        public static Usage valueOf(String p_86160_) {
            return Enum.valueOf(Usage.class, p_86160_);
        }

        private Usage(String p_166975_, SetupState p_166976_, ClearState p_166977_) {
            this.f_86086_ = p_166975_;
            this.f_86087_ = p_166976_;
            this.f_86088_ = p_166977_;
        }

        void m_166981_(int p_166982_, int p_166983_, int p_166984_, long p_166985_, int p_166986_, int p_166987_) {
            this.f_86087_.m_167052_(p_166982_, p_166983_, p_166984_, p_166985_, p_166986_, p_166987_);
        }

        public void m_166978_(int p_166979_, int p_166980_) {
            this.f_86088_.m_167049_(p_166979_, p_166980_);
        }

        public String m_86097_() {
            return this.f_86086_;
        }

        private static /* synthetic */ Usage[] m_166988_() {
            return new Usage[]{POSITION, NORMAL, COLOR, UV, PADDING, GENERIC};
        }

        static {
            $VALUES = Usage.m_166988_();
        }

        @FunctionalInterface
        static interface SetupState {
            public void m_167052_(int var1, int var2, int var3, long var4, int var6, int var7);
        }

        @FunctionalInterface
        static interface ClearState {
            public void m_167049_(int var1, int var2);
        }
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type FLOAT = new Type(4, "Float", 5126);
        public static final /* enum */ Type UBYTE = new Type(1, "Unsigned Byte", 5121);
        public static final /* enum */ Type BYTE = new Type(1, "Byte", 5120);
        public static final /* enum */ Type USHORT = new Type(2, "Unsigned Short", 5123);
        public static final /* enum */ Type SHORT = new Type(2, "Short", 5122);
        public static final /* enum */ Type UINT = new Type(4, "Unsigned Int", 5125);
        public static final /* enum */ Type INT = new Type(4, "Int", 5124);
        private final int f_86063_;
        private final String f_86064_;
        private final int f_86065_;
        private static final /* synthetic */ Type[] $VALUES;

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String p_86078_) {
            return Enum.valueOf(Type.class, p_86078_);
        }

        private Type(int p_86071_, String p_86072_, int p_86073_) {
            this.f_86063_ = p_86071_;
            this.f_86064_ = p_86072_;
            this.f_86065_ = p_86073_;
        }

        public int m_86074_() {
            return this.f_86063_;
        }

        public String m_86075_() {
            return this.f_86064_;
        }

        public int m_86076_() {
            return this.f_86065_;
        }

        private static /* synthetic */ Type[] m_166971_() {
            return new Type[]{FLOAT, UBYTE, BYTE, USHORT, SHORT, UINT, INT};
        }

        static {
            $VALUES = Type.m_166971_();
        }
    }
}

