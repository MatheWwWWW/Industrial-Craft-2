/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 */
package net.minecraft.util.profiling.jfr;

import com.mojang.logging.LogUtils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.profiling.jfr.parse.JfrStatsParser;
import net.minecraft.util.profiling.jfr.parse.JfrStatsResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

public class SummaryReporter {
    private static final Logger f_185394_ = LogUtils.getLogger();
    private final Runnable f_185395_;

    protected SummaryReporter(Runnable p_185398_) {
        this.f_185395_ = p_185398_;
    }

    /*
     * WARNING - void declaration
     */
    public void m_185400_(@Nullable Path p_185401_) {
        if (p_185401_ == null) {
            return;
        }
        this.f_185395_.run();
        SummaryReporter.m_201932_(() -> "Dumped flight recorder profiling to " + p_185401_);
        try {
            JfrStatsResult $$1 = JfrStatsParser.m_185447_(p_185401_);
        }
        catch (Throwable $$2) {
            SummaryReporter.m_201934_(() -> "Failed to parse JFR recording", $$2);
            return;
        }
        try {
            void $$3;
            SummaryReporter.m_201932_(((JfrStatsResult)$$3)::m_185510_);
            Path $$4 = p_185401_.resolveSibling("jfr-report-" + StringUtils.substringBefore((String)p_185401_.getFileName().toString(), (String)".jfr") + ".json");
            Files.writeString($$4, (CharSequence)$$3.m_185510_(), StandardOpenOption.CREATE);
            SummaryReporter.m_201932_(() -> "Dumped recording summary to " + $$4);
        }
        catch (Throwable $$5) {
            SummaryReporter.m_201934_(() -> "Failed to output JFR report", $$5);
        }
    }

    private static void m_201932_(Supplier<String> p_201933_) {
        if (LogUtils.isLoggerActive()) {
            f_185394_.info(p_201933_.get());
        } else {
            Bootstrap.m_135875_(p_201933_.get());
        }
    }

    private static void m_201934_(Supplier<String> p_201935_, Throwable p_201936_) {
        if (LogUtils.isLoggerActive()) {
            f_185394_.warn(p_201935_.get(), p_201936_);
        } else {
            Bootstrap.m_135875_(p_201935_.get());
            p_201936_.printStackTrace(Bootstrap.f_135866_);
        }
    }
}

