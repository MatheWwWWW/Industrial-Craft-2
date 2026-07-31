/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Streams
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.dedicated;

import com.google.common.collect.Streams;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.Util;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.GameRules;
import org.slf4j.Logger;

public class ServerWatchdog
implements Runnable {
    private static final Logger f_139781_ = LogUtils.getLogger();
    private static final long f_142880_ = 10000L;
    private static final int f_142881_ = 1;
    private final DedicatedServer f_139782_;
    private final long f_139783_;

    public ServerWatchdog(DedicatedServer p_139786_) {
        this.f_139782_ = p_139786_;
        this.f_139783_ = p_139786_.m_139669_();
    }

    @Override
    public void run() {
        while (this.f_139782_.m_130010_()) {
            long $$0 = this.f_139782_.m_129932_();
            long $$1 = Util.m_137550_();
            long $$2 = $$1 - $$0;
            if ($$2 > this.f_139783_) {
                f_139781_.error(LogUtils.FATAL_MARKER, "A single server tick took {} seconds (should be max {})", (Object)String.format(Locale.ROOT, "%.2f", Float.valueOf((float)$$2 / 1000.0f)), (Object)String.format(Locale.ROOT, "%.2f", Float.valueOf(0.05f)));
                f_139781_.error(LogUtils.FATAL_MARKER, "Considering it to be crashed, server will forcibly shutdown.");
                ThreadMXBean $$3 = ManagementFactory.getThreadMXBean();
                ThreadInfo[] $$4 = $$3.dumpAllThreads(true, true);
                StringBuilder $$5 = new StringBuilder();
                Error $$6 = new Error("Watchdog");
                for (ThreadInfo $$7 : $$4) {
                    if ($$7.getThreadId() == this.f_139782_.m_6304_().getId()) {
                        $$6.setStackTrace($$7.getStackTrace());
                    }
                    $$5.append($$7);
                    $$5.append("\n");
                }
                CrashReport $$8 = new CrashReport("Watching Server", $$6);
                this.f_139782_.m_177935_($$8.m_178626_());
                CrashReportCategory $$9 = $$8.m_127514_("Thread Dump");
                $$9.m_128159_("Threads", $$5);
                CrashReportCategory $$10 = $$8.m_127514_("Performance stats");
                $$10.m_128165_("Random tick rate", () -> this.f_139782_.m_129910_().m_5470_().m_46170_(GameRules.f_46143_).toString());
                $$10.m_128165_("Level stats", () -> Streams.stream(this.f_139782_.m_129785_()).map(p_142883_ -> p_142883_.m_46472_() + ": " + p_142883_.m_8590_()).collect(Collectors.joining(",\n")));
                Bootstrap.m_135875_("Crash report:\n" + $$8.m_127526_());
                File $$11 = new File(new File(this.f_139782_.m_6237_(), "crash-reports"), "crash-" + Util.m_241986_() + "-server.txt");
                if ($$8.m_127512_($$11)) {
                    f_139781_.error("This crash report has been saved to: {}", (Object)$$11.getAbsolutePath());
                } else {
                    f_139781_.error("We were unable to save this crash report to disk.");
                }
                this.m_139787_();
            }
            try {
                Thread.sleep($$0 + this.f_139783_ - $$1);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    private void m_139787_() {
        try {
            Timer $$0 = new Timer();
            $$0.schedule(new TimerTask(){

                @Override
                public void run() {
                    Runtime.getRuntime().halt(1);
                }
            }, 10000L);
            System.exit(1);
        }
        catch (Throwable $$1) {
            Runtime.getRuntime().halt(1);
        }
    }
}

