/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling;

import java.util.function.Supplier;
import net.minecraft.util.profiling.InactiveProfiler;
import net.minecraft.util.profiling.metrics.MetricCategory;

public interface ProfilerFiller {
    public static final String f_145958_ = "root";

    public void m_7242_();

    public void m_7241_();

    public void m_6180_(String var1);

    public void m_6521_(Supplier<String> var1);

    public void m_7238_();

    public void m_6182_(String var1);

    public void m_6523_(Supplier<String> var1);

    public void m_142259_(MetricCategory var1);

    default public void m_6174_(String p_18585_) {
        this.m_183275_(p_18585_, 1);
    }

    public void m_183275_(String var1, int var2);

    default public void m_6525_(Supplier<String> p_18586_) {
        this.m_183536_(p_18586_, 1);
    }

    public void m_183536_(Supplier<String> var1, int var2);

    public static ProfilerFiller m_18578_(final ProfilerFiller p_18579_, final ProfilerFiller p_18580_) {
        if (p_18579_ == InactiveProfiler.f_18554_) {
            return p_18580_;
        }
        if (p_18580_ == InactiveProfiler.f_18554_) {
            return p_18579_;
        }
        return new ProfilerFiller(){

            @Override
            public void m_7242_() {
                p_18579_.m_7242_();
                p_18580_.m_7242_();
            }

            @Override
            public void m_7241_() {
                p_18579_.m_7241_();
                p_18580_.m_7241_();
            }

            @Override
            public void m_6180_(String p_18594_) {
                p_18579_.m_6180_(p_18594_);
                p_18580_.m_6180_(p_18594_);
            }

            @Override
            public void m_6521_(Supplier<String> p_18596_) {
                p_18579_.m_6521_(p_18596_);
                p_18580_.m_6521_(p_18596_);
            }

            @Override
            public void m_142259_(MetricCategory p_145961_) {
                p_18579_.m_142259_(p_145961_);
                p_18580_.m_142259_(p_145961_);
            }

            @Override
            public void m_7238_() {
                p_18579_.m_7238_();
                p_18580_.m_7238_();
            }

            @Override
            public void m_6182_(String p_18599_) {
                p_18579_.m_6182_(p_18599_);
                p_18580_.m_6182_(p_18599_);
            }

            @Override
            public void m_6523_(Supplier<String> p_18601_) {
                p_18579_.m_6523_(p_18601_);
                p_18580_.m_6523_(p_18601_);
            }

            @Override
            public void m_183275_(String p_185263_, int p_185264_) {
                p_18579_.m_183275_(p_185263_, p_185264_);
                p_18580_.m_183275_(p_185263_, p_185264_);
            }

            @Override
            public void m_183536_(Supplier<String> p_185266_, int p_185267_) {
                p_18579_.m_183536_(p_185266_, p_185267_);
                p_18580_.m_183536_(p_185266_, p_185267_);
            }
        };
    }
}

