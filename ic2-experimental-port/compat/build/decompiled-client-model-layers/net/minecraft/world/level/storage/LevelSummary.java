/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.world.level.storage;

import java.nio.file.Path;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.WorldVersion;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.StringUtil;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.storage.LevelVersion;
import org.apache.commons.lang3.StringUtils;

public class LevelSummary
implements Comparable<LevelSummary> {
    private final LevelSettings f_78344_;
    private final LevelVersion f_78345_;
    private final String f_78346_;
    private final boolean f_193019_;
    private final boolean f_78348_;
    private final Path f_78349_;
    @Nullable
    private Component f_78350_;

    public LevelSummary(LevelSettings p_230869_, LevelVersion p_230870_, String p_230871_, boolean p_230872_, boolean p_230873_, Path p_230874_) {
        this.f_78344_ = p_230869_;
        this.f_78345_ = p_230870_;
        this.f_78346_ = p_230871_;
        this.f_78348_ = p_230873_;
        this.f_78349_ = p_230874_;
        this.f_193019_ = p_230872_;
    }

    public String m_78358_() {
        return this.f_78346_;
    }

    public String m_78361_() {
        return StringUtils.isEmpty((CharSequence)this.f_78344_.m_46917_()) ? this.f_78346_ : this.f_78344_.m_46917_();
    }

    public Path m_230875_() {
        return this.f_78349_;
    }

    public boolean m_193020_() {
        return this.f_193019_;
    }

    public long m_78366_() {
        return this.f_78345_.m_78392_();
    }

    @Override
    public int compareTo(LevelSummary p_78360_) {
        if (this.f_78345_.m_78392_() < p_78360_.f_78345_.m_78392_()) {
            return 1;
        }
        if (this.f_78345_.m_78392_() > p_78360_.f_78345_.m_78392_()) {
            return -1;
        }
        return this.f_78346_.compareTo(p_78360_.f_78346_);
    }

    public LevelSettings m_164913_() {
        return this.f_78344_;
    }

    public GameType m_78367_() {
        return this.f_78344_.m_46929_();
    }

    public boolean m_78368_() {
        return this.f_78344_.m_46930_();
    }

    public boolean m_78369_() {
        return this.f_78344_.m_46932_();
    }

    public MutableComponent m_78370_() {
        if (StringUtil.m_14408_(this.f_78345_.m_78393_())) {
            return Component.m_237115_("selectWorld.versionUnknown");
        }
        return Component.m_237113_(this.f_78345_.m_78393_());
    }

    public LevelVersion m_78371_() {
        return this.f_78345_;
    }

    public boolean m_78372_() {
        return this.m_78373_() || !SharedConstants.m_183709_().isStable() && !this.f_78345_.m_78395_() || this.m_164914_().m_164931_();
    }

    public boolean m_78373_() {
        return this.f_78345_.m_193029_().m_193006_() > SharedConstants.m_183709_().m_183476_().m_193006_();
    }

    public BackupStatus m_164914_() {
        WorldVersion $$0 = SharedConstants.m_183709_();
        int $$1 = $$0.m_183476_().m_193006_();
        int $$2 = this.f_78345_.m_193029_().m_193006_();
        if (!$$0.isStable() && $$2 < $$1) {
            return BackupStatus.UPGRADE_TO_SNAPSHOT;
        }
        if ($$2 > $$1) {
            return BackupStatus.DOWNGRADE;
        }
        return BackupStatus.NONE;
    }

    public boolean m_78375_() {
        return this.f_78348_;
    }

    public boolean m_164916_() {
        if (this.m_78375_() || this.m_193020_()) {
            return true;
        }
        return !this.m_193021_();
    }

    public boolean m_193021_() {
        return SharedConstants.m_183709_().m_183476_().m_193003_(this.f_78345_.m_193029_());
    }

    public Component m_78376_() {
        if (this.f_78350_ == null) {
            this.f_78350_ = this.m_78377_();
        }
        return this.f_78350_;
    }

    private Component m_78377_() {
        MutableComponent $$0;
        if (this.m_78375_()) {
            return Component.m_237115_("selectWorld.locked").m_130940_(ChatFormatting.RED);
        }
        if (this.m_193020_()) {
            return Component.m_237115_("selectWorld.conversion").m_130940_(ChatFormatting.RED);
        }
        if (!this.m_193021_()) {
            return Component.m_237115_("selectWorld.incompatible_series").m_130940_(ChatFormatting.RED);
        }
        MutableComponent mutableComponent = $$0 = this.m_78368_() ? Component.m_237119_().m_7220_(Component.m_237115_("gameMode.hardcore").m_130940_(ChatFormatting.DARK_RED)) : Component.m_237115_("gameMode." + this.m_78367_().m_46405_());
        if (this.m_78369_()) {
            $$0.m_130946_(", ").m_7220_(Component.m_237115_("selectWorld.cheats"));
        }
        MutableComponent $$1 = this.m_78370_();
        MutableComponent $$2 = Component.m_237113_(", ").m_7220_(Component.m_237115_("selectWorld.version")).m_130946_(" ");
        if (this.m_78372_()) {
            $$2.m_7220_($$1.m_130940_(this.m_78373_() ? ChatFormatting.RED : ChatFormatting.ITALIC));
        } else {
            $$2.m_7220_($$1);
        }
        $$0.m_7220_($$2);
        return $$0;
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.compareTo((LevelSummary)object);
    }

    public static final class BackupStatus
    extends Enum<BackupStatus> {
        public static final /* enum */ BackupStatus NONE = new BackupStatus(false, false, "");
        public static final /* enum */ BackupStatus DOWNGRADE = new BackupStatus(true, true, "downgrade");
        public static final /* enum */ BackupStatus UPGRADE_TO_SNAPSHOT = new BackupStatus(true, false, "snapshot");
        private final boolean f_164920_;
        private final boolean f_164921_;
        private final String f_164922_;
        private static final /* synthetic */ BackupStatus[] $VALUES;

        public static BackupStatus[] values() {
            return (BackupStatus[])$VALUES.clone();
        }

        public static BackupStatus valueOf(String p_164936_) {
            return Enum.valueOf(BackupStatus.class, p_164936_);
        }

        private BackupStatus(boolean p_164928_, boolean p_164929_, String p_164930_) {
            this.f_164920_ = p_164928_;
            this.f_164921_ = p_164929_;
            this.f_164922_ = p_164930_;
        }

        public boolean m_164931_() {
            return this.f_164920_;
        }

        public boolean m_164932_() {
            return this.f_164921_;
        }

        public String m_164933_() {
            return this.f_164922_;
        }

        private static /* synthetic */ BackupStatus[] m_164934_() {
            return new BackupStatus[]{NONE, DOWNGRADE, UPGRADE_TO_SNAPSHOT};
        }

        static {
            $VALUES = BackupStatus.m_164934_();
        }
    }
}

