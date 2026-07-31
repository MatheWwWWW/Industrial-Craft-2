/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.util.profiling;

import com.mojang.logging.LogUtils;
import java.io.File;
import java.util.function.LongSupplier;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.util.profiling.ActiveProfiler;
import net.minecraft.util.profiling.InactiveProfiler;
import net.minecraft.util.profiling.ProfileCollector;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

public class SingleTickProfiler {
    private static final Logger f_18621_ = LogUtils.getLogger();
    private final LongSupplier f_18622_;
    private final long f_18623_;
    private int f_18624_;
    private final File f_18625_;
    private ProfileCollector f_18626_ = InactiveProfiler.f_18554_;

    public SingleTickProfiler(LongSupplier p_145963_, String p_145964_, long p_145965_) {
        this.f_18622_ = p_145963_;
        this.f_18625_ = new File("debug", p_145964_);
        this.f_18623_ = p_145965_;
    }

    public ProfilerFiller m_18628_() {
        this.f_18626_ = new ActiveProfiler(this.f_18622_, () -> this.f_18624_, false);
        ++this.f_18624_;
        return this.f_18626_;
    }

    public void m_18634_() {
        if (this.f_18626_ == InactiveProfiler.f_18554_) {
            return;
        }
        ProfileResults $$0 = this.f_18626_.m_5948_();
        this.f_18626_ = InactiveProfiler.f_18554_;
        if ($$0.m_18577_() >= this.f_18623_) {
            File $$1 = new File(this.f_18625_, "tick-results-" + Util.m_241986_() + ".txt");
            $$0.m_142444_($$1.toPath());
            f_18621_.info("Recorded long tick -- wrote info to: {}", (Object)$$1.getAbsolutePath());
        }
    }

    @Nullable
    public static SingleTickProfiler m_18632_(String p_18633_) {
        return null;
    }

    public static ProfilerFiller m_18629_(ProfilerFiller p_18630_, @Nullable SingleTickProfiler p_18631_) {
        if (p_18631_ != null) {
            return ProfilerFiller.m_18578_(p_18631_.m_18628_(), p_18630_);
        }
        return p_18630_;
    }
}

