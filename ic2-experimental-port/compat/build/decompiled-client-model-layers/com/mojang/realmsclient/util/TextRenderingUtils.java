/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package com.mojang.realmsclient.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;

public class TextRenderingUtils {
    private TextRenderingUtils() {
    }

    @VisibleForTesting
    protected static List<String> m_90248_(String p_90249_) {
        return Arrays.asList(p_90249_.split("\\n"));
    }

    public static List<Line> m_90256_(String p_90257_, LineSegment ... p_90258_) {
        return TextRenderingUtils.m_90253_(p_90257_, Arrays.asList(p_90258_));
    }

    private static List<Line> m_90253_(String p_90254_, List<LineSegment> p_90255_) {
        List<String> $$2 = TextRenderingUtils.m_90248_(p_90254_);
        return TextRenderingUtils.m_90259_($$2, p_90255_);
    }

    private static List<Line> m_90259_(List<String> p_90260_, List<LineSegment> p_90261_) {
        int $$2 = 0;
        ArrayList $$3 = Lists.newArrayList();
        for (String $$4 : p_90260_) {
            ArrayList $$5 = Lists.newArrayList();
            List<String> $$6 = TextRenderingUtils.m_90250_($$4, "%link");
            for (String $$7 : $$6) {
                if ("%link".equals($$7)) {
                    $$5.add(p_90261_.get($$2++));
                    continue;
                }
                $$5.add(LineSegment.m_90279_($$7));
            }
            $$3.add(new Line($$5));
        }
        return $$3;
    }

    public static List<String> m_90250_(String p_90251_, String p_90252_) {
        int $$4;
        if (p_90252_.isEmpty()) {
            throw new IllegalArgumentException("Delimiter cannot be the empty string");
        }
        ArrayList $$2 = Lists.newArrayList();
        int $$3 = 0;
        while (($$4 = p_90251_.indexOf(p_90252_, $$3)) != -1) {
            if ($$4 > $$3) {
                $$2.add(p_90251_.substring($$3, $$4));
            }
            $$2.add(p_90252_);
            $$3 = $$4 + p_90252_.length();
        }
        if ($$3 < p_90251_.length()) {
            $$2.add(p_90251_.substring($$3));
        }
        return $$2;
    }

    public static class LineSegment {
        private final String f_90269_;
        @Nullable
        private final String f_90270_;
        @Nullable
        private final String f_90271_;

        private LineSegment(String p_90273_) {
            this.f_90269_ = p_90273_;
            this.f_90270_ = null;
            this.f_90271_ = null;
        }

        private LineSegment(String p_90275_, @Nullable String p_90276_, @Nullable String p_90277_) {
            this.f_90269_ = p_90275_;
            this.f_90270_ = p_90276_;
            this.f_90271_ = p_90277_;
        }

        public boolean equals(Object p_90287_) {
            if (this == p_90287_) {
                return true;
            }
            if (p_90287_ == null || this.getClass() != p_90287_.getClass()) {
                return false;
            }
            LineSegment $$1 = (LineSegment)p_90287_;
            return Objects.equals(this.f_90269_, $$1.f_90269_) && Objects.equals(this.f_90270_, $$1.f_90270_) && Objects.equals(this.f_90271_, $$1.f_90271_);
        }

        public int hashCode() {
            return Objects.hash(this.f_90269_, this.f_90270_, this.f_90271_);
        }

        public String toString() {
            return "Segment{fullText='" + this.f_90269_ + "', linkTitle='" + this.f_90270_ + "', linkUrl='" + this.f_90271_ + "'}";
        }

        public String m_90278_() {
            return this.m_90284_() ? this.f_90270_ : this.f_90269_;
        }

        public boolean m_90284_() {
            return this.f_90270_ != null;
        }

        public String m_90285_() {
            if (!this.m_90284_()) {
                throw new IllegalStateException("Not a link: " + this);
            }
            return this.f_90271_;
        }

        public static LineSegment m_90281_(String p_90282_, String p_90283_) {
            return new LineSegment(null, p_90282_, p_90283_);
        }

        @VisibleForTesting
        protected static LineSegment m_90279_(String p_90280_) {
            return new LineSegment(p_90280_);
        }
    }

    public static class Line {
        public final List<LineSegment> f_90262_;

        Line(LineSegment ... p_167625_) {
            this(Arrays.asList(p_167625_));
        }

        Line(List<LineSegment> p_90264_) {
            this.f_90262_ = p_90264_;
        }

        public String toString() {
            return "Line{segments=" + this.f_90262_ + "}";
        }

        public boolean equals(Object p_90266_) {
            if (this == p_90266_) {
                return true;
            }
            if (p_90266_ == null || this.getClass() != p_90266_.getClass()) {
                return false;
            }
            Line $$1 = (Line)p_90266_;
            return Objects.equals(this.f_90262_, $$1.f_90262_);
        }

        public int hashCode() {
            return Objects.hash(this.f_90262_);
        }
    }
}

