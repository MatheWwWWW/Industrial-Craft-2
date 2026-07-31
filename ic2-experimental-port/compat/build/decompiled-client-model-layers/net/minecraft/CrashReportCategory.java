/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.CrashReportDetail;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class CrashReportCategory {
    private final String f_128137_;
    private final List<Entry> f_128138_ = Lists.newArrayList();
    private StackTraceElement[] f_128139_ = new StackTraceElement[0];

    public CrashReportCategory(String p_178936_) {
        this.f_128137_ = p_178936_;
    }

    public static String m_178937_(LevelHeightAccessor p_178938_, double p_178939_, double p_178940_, double p_178941_) {
        return String.format(Locale.ROOT, "%.2f,%.2f,%.2f - %s", p_178939_, p_178940_, p_178941_, CrashReportCategory.m_178947_(p_178938_, new BlockPos(p_178939_, p_178940_, p_178941_)));
    }

    public static String m_178947_(LevelHeightAccessor p_178948_, BlockPos p_178949_) {
        return CrashReportCategory.m_178942_(p_178948_, p_178949_.m_123341_(), p_178949_.m_123342_(), p_178949_.m_123343_());
    }

    public static String m_178942_(LevelHeightAccessor p_178943_, int p_178944_, int p_178945_, int p_178946_) {
        StringBuilder $$4 = new StringBuilder();
        try {
            $$4.append(String.format(Locale.ROOT, "World: (%d,%d,%d)", p_178944_, p_178945_, p_178946_));
        }
        catch (Throwable $$5) {
            $$4.append("(Error finding world loc)");
        }
        $$4.append(", ");
        try {
            int $$6 = SectionPos.m_123171_(p_178944_);
            int $$7 = SectionPos.m_123171_(p_178945_);
            int $$8 = SectionPos.m_123171_(p_178946_);
            int $$9 = p_178944_ & 0xF;
            int $$10 = p_178945_ & 0xF;
            int $$11 = p_178946_ & 0xF;
            int $$12 = SectionPos.m_123223_($$6);
            int $$13 = p_178943_.m_141937_();
            int $$14 = SectionPos.m_123223_($$8);
            int $$15 = SectionPos.m_123223_($$6 + 1) - 1;
            int $$16 = p_178943_.m_151558_() - 1;
            int $$17 = SectionPos.m_123223_($$8 + 1) - 1;
            $$4.append(String.format(Locale.ROOT, "Section: (at %d,%d,%d in %d,%d,%d; chunk contains blocks %d,%d,%d to %d,%d,%d)", $$9, $$10, $$11, $$6, $$7, $$8, $$12, $$13, $$14, $$15, $$16, $$17));
        }
        catch (Throwable $$18) {
            $$4.append("(Error finding chunk loc)");
        }
        $$4.append(", ");
        try {
            int $$19 = p_178944_ >> 9;
            int $$20 = p_178946_ >> 9;
            int $$21 = $$19 << 5;
            int $$22 = $$20 << 5;
            int $$23 = ($$19 + 1 << 5) - 1;
            int $$24 = ($$20 + 1 << 5) - 1;
            int $$25 = $$19 << 9;
            int $$26 = p_178943_.m_141937_();
            int $$27 = $$20 << 9;
            int $$28 = ($$19 + 1 << 9) - 1;
            int $$29 = p_178943_.m_151558_() - 1;
            int $$30 = ($$20 + 1 << 9) - 1;
            $$4.append(String.format(Locale.ROOT, "Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,%d,%d to %d,%d,%d)", $$19, $$20, $$21, $$22, $$23, $$24, $$25, $$26, $$27, $$28, $$29, $$30));
        }
        catch (Throwable $$31) {
            $$4.append("(Error finding world loc)");
        }
        return $$4.toString();
    }

    public CrashReportCategory m_128165_(String p_128166_, CrashReportDetail<String> p_128167_) {
        try {
            this.m_128159_(p_128166_, p_128167_.call());
        }
        catch (Throwable $$2) {
            this.m_128162_(p_128166_, $$2);
        }
        return this;
    }

    public CrashReportCategory m_128159_(String p_128160_, Object p_128161_) {
        this.f_128138_.add(new Entry(p_128160_, p_128161_));
        return this;
    }

    public void m_128162_(String p_128163_, Throwable p_128164_) {
        this.m_128159_(p_128163_, p_128164_);
    }

    public int m_128148_(int p_128149_) {
        StackTraceElement[] $$1 = Thread.currentThread().getStackTrace();
        if ($$1.length <= 0) {
            return 0;
        }
        this.f_128139_ = new StackTraceElement[$$1.length - 3 - p_128149_];
        System.arraycopy($$1, 3 + p_128149_, this.f_128139_, 0, this.f_128139_.length);
        return this.f_128139_.length;
    }

    public boolean m_128156_(StackTraceElement p_128157_, StackTraceElement p_128158_) {
        if (this.f_128139_.length == 0 || p_128157_ == null) {
            return false;
        }
        StackTraceElement $$2 = this.f_128139_[0];
        if (!($$2.isNativeMethod() == p_128157_.isNativeMethod() && $$2.getClassName().equals(p_128157_.getClassName()) && $$2.getFileName().equals(p_128157_.getFileName()) && $$2.getMethodName().equals(p_128157_.getMethodName()))) {
            return false;
        }
        if (p_128158_ != null != this.f_128139_.length > 1) {
            return false;
        }
        if (p_128158_ != null && !this.f_128139_[1].equals(p_128158_)) {
            return false;
        }
        this.f_128139_[0] = p_128157_;
        return true;
    }

    public void m_128174_(int p_128175_) {
        StackTraceElement[] $$1 = new StackTraceElement[this.f_128139_.length - p_128175_];
        System.arraycopy(this.f_128139_, 0, $$1, 0, $$1.length);
        this.f_128139_ = $$1;
    }

    public void m_128168_(StringBuilder p_128169_) {
        p_128169_.append("-- ").append(this.f_128137_).append(" --\n");
        p_128169_.append("Details:");
        for (Entry $$1 : this.f_128138_) {
            p_128169_.append("\n\t");
            p_128169_.append($$1.m_128183_());
            p_128169_.append(": ");
            p_128169_.append($$1.m_128184_());
        }
        if (this.f_128139_ != null && this.f_128139_.length > 0) {
            p_128169_.append("\nStacktrace:");
            for (StackTraceElement $$2 : this.f_128139_) {
                p_128169_.append("\n\tat ");
                p_128169_.append($$2);
            }
        }
    }

    public StackTraceElement[] m_128143_() {
        return this.f_128139_;
    }

    public static void m_178950_(CrashReportCategory p_178951_, LevelHeightAccessor p_178952_, BlockPos p_178953_, @Nullable BlockState p_178954_) {
        if (p_178954_ != null) {
            p_178951_.m_128165_("Block", p_178954_::toString);
        }
        p_178951_.m_128165_("Block location", () -> CrashReportCategory.m_178947_(p_178952_, p_178953_));
    }

    static class Entry {
        private final String f_128178_;
        private final String f_128179_;

        public Entry(String p_128181_, @Nullable Object p_128182_) {
            this.f_128178_ = p_128181_;
            if (p_128182_ == null) {
                this.f_128179_ = "~~NULL~~";
            } else if (p_128182_ instanceof Throwable) {
                Throwable $$2 = (Throwable)p_128182_;
                this.f_128179_ = "~~ERROR~~ " + $$2.getClass().getSimpleName() + ": " + $$2.getMessage();
            } else {
                this.f_128179_ = p_128182_.toString();
            }
        }

        public String m_128183_() {
            return this.f_128178_;
        }

        public String m_128184_() {
            return this.f_128179_;
        }
    }
}

