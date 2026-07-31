/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.util;

import com.mojang.logging.LogUtils;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import org.slf4j.Logger;

public class ThreadingDetector {
    private static final Logger f_199407_ = LogUtils.getLogger();
    private final String f_199408_;
    private final Semaphore f_199409_ = new Semaphore(1);
    private final Lock f_199410_ = new ReentrantLock();
    @Nullable
    private volatile Thread f_199411_;
    @Nullable
    private volatile ReportedException f_199412_;

    public ThreadingDetector(String p_199415_) {
        this.f_199408_ = p_199415_;
    }

    public void m_199416_() {
        block6: {
            boolean $$0 = false;
            try {
                this.f_199410_.lock();
                if (this.f_199409_.tryAcquire()) break block6;
                this.f_199411_ = Thread.currentThread();
                $$0 = true;
                this.f_199410_.unlock();
                try {
                    this.f_199409_.acquire();
                }
                catch (InterruptedException $$1) {
                    Thread.currentThread().interrupt();
                }
                throw this.f_199412_;
            }
            finally {
                if (!$$0) {
                    this.f_199410_.unlock();
                }
            }
        }
    }

    public void m_199422_() {
        try {
            this.f_199410_.lock();
            Thread $$0 = this.f_199411_;
            if ($$0 != null) {
                ReportedException $$1;
                this.f_199412_ = $$1 = ThreadingDetector.m_199417_(this.f_199408_, $$0);
                this.f_199409_.release();
                throw $$1;
            }
            this.f_199409_.release();
        }
        finally {
            this.f_199410_.unlock();
        }
    }

    public static ReportedException m_199417_(String p_199418_, @Nullable Thread p_199419_) {
        String $$2 = Stream.of(Thread.currentThread(), p_199419_).filter(Objects::nonNull).map(ThreadingDetector::m_199420_).collect(Collectors.joining("\n"));
        String $$3 = "Accessing " + p_199418_ + " from multiple threads";
        CrashReport $$4 = new CrashReport($$3, new IllegalStateException($$3));
        CrashReportCategory $$5 = $$4.m_127514_("Thread dumps");
        $$5.m_128159_("Thread dumps", $$2);
        f_199407_.error("Thread dumps: \n" + $$2);
        return new ReportedException($$4);
    }

    private static String m_199420_(Thread p_199421_) {
        return p_199421_.getName() + ": \n\tat " + Arrays.stream(p_199421_.getStackTrace()).map(Object::toString).collect(Collectors.joining("\n\tat "));
    }
}

