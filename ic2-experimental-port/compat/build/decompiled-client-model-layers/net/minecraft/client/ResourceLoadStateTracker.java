/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.server.packs.PackResources;
import org.slf4j.Logger;

public class ResourceLoadStateTracker {
    private static final Logger f_168551_ = LogUtils.getLogger();
    @Nullable
    private ReloadState f_168552_;
    private int f_168553_;

    public void m_168557_(ReloadReason p_168558_, List<PackResources> p_168559_) {
        ++this.f_168553_;
        if (this.f_168552_ != null && !this.f_168552_.f_168587_) {
            f_168551_.warn("Reload already ongoing, replacing");
        }
        this.f_168552_ = new ReloadState(p_168558_, (List)p_168559_.stream().map(PackResources::m_8017_).collect(ImmutableList.toImmutableList()));
    }

    public void m_168560_(Throwable p_168561_) {
        if (this.f_168552_ == null) {
            f_168551_.warn("Trying to signal reload recovery, but nothing was started");
            this.f_168552_ = new ReloadState(ReloadReason.UNKNOWN, (List<String>)ImmutableList.of());
        }
        this.f_168552_.f_168586_ = new RecoveryInfo(p_168561_);
    }

    public void m_168556_() {
        if (this.f_168552_ == null) {
            f_168551_.warn("Trying to finish reload, but nothing was started");
        } else {
            this.f_168552_.f_168587_ = true;
        }
    }

    public void m_168562_(CrashReport p_168563_) {
        CrashReportCategory $$1 = p_168563_.m_127514_("Last reload");
        $$1.m_128159_("Reload number", this.f_168553_);
        if (this.f_168552_ != null) {
            this.f_168552_.m_168592_($$1);
        }
    }

    static class ReloadState {
        private final ReloadReason f_168584_;
        private final List<String> f_168585_;
        @Nullable
        RecoveryInfo f_168586_;
        boolean f_168587_;

        ReloadState(ReloadReason p_168589_, List<String> p_168590_) {
            this.f_168584_ = p_168589_;
            this.f_168585_ = p_168590_;
        }

        public void m_168592_(CrashReportCategory p_168593_) {
            p_168593_.m_128159_("Reload reason", this.f_168584_.f_168573_);
            p_168593_.m_128159_("Finished", this.f_168587_ ? "Yes" : "No");
            p_168593_.m_128165_("Packs", () -> String.join((CharSequence)", ", this.f_168585_));
            if (this.f_168586_ != null) {
                this.f_168586_.m_168568_(p_168593_);
            }
        }
    }

    public static final class ReloadReason
    extends Enum<ReloadReason> {
        public static final /* enum */ ReloadReason INITIAL = new ReloadReason("initial");
        public static final /* enum */ ReloadReason MANUAL = new ReloadReason("manual");
        public static final /* enum */ ReloadReason UNKNOWN = new ReloadReason("unknown");
        final String f_168573_;
        private static final /* synthetic */ ReloadReason[] $VALUES;

        public static ReloadReason[] values() {
            return (ReloadReason[])$VALUES.clone();
        }

        public static ReloadReason valueOf(String p_168582_) {
            return Enum.valueOf(ReloadReason.class, p_168582_);
        }

        private ReloadReason(String p_168579_) {
            this.f_168573_ = p_168579_;
        }

        private static /* synthetic */ ReloadReason[] m_168580_() {
            return new ReloadReason[]{INITIAL, MANUAL, UNKNOWN};
        }

        static {
            $VALUES = ReloadReason.m_168580_();
        }
    }

    static class RecoveryInfo {
        private final Throwable f_168564_;

        RecoveryInfo(Throwable p_168566_) {
            this.f_168564_ = p_168566_;
        }

        public void m_168568_(CrashReportCategory p_168569_) {
            p_168569_.m_128159_("Recovery", "Yes");
            p_168569_.m_128165_("Recovery reason", () -> {
                StringWriter $$0 = new StringWriter();
                this.f_168564_.printStackTrace(new PrintWriter($$0));
                return $$0.toString();
            });
        }
    }
}

