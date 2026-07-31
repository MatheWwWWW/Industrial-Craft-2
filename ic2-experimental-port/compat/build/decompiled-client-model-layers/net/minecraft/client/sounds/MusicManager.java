/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.sounds;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class MusicManager {
    private static final int f_174979_ = 100;
    private final RandomSource f_120177_ = RandomSource.m_216327_();
    private final Minecraft f_120178_;
    @Nullable
    private SoundInstance f_120179_;
    private int f_120180_ = 100;

    public MusicManager(Minecraft p_120182_) {
        this.f_120178_ = p_120182_;
    }

    public void m_120183_() {
        Music $$0 = this.f_120178_.m_91107_();
        if (this.f_120179_ != null) {
            if (!$$0.m_11631_().m_11660_().equals(this.f_120179_.m_7904_()) && $$0.m_11642_()) {
                this.f_120178_.m_91106_().m_120399_(this.f_120179_);
                this.f_120180_ = Mth.m_216271_(this.f_120177_, 0, $$0.m_11636_() / 2);
            }
            if (!this.f_120178_.m_91106_().m_120403_(this.f_120179_)) {
                this.f_120179_ = null;
                this.f_120180_ = Math.min(this.f_120180_, Mth.m_216271_(this.f_120177_, $$0.m_11636_(), $$0.m_11639_()));
            }
        }
        this.f_120180_ = Math.min(this.f_120180_, $$0.m_11639_());
        if (this.f_120179_ == null && this.f_120180_-- <= 0) {
            this.m_120184_($$0);
        }
    }

    public void m_120184_(Music p_120185_) {
        this.f_120179_ = SimpleSoundInstance.m_119745_(p_120185_.m_11631_());
        if (this.f_120179_.m_5891_() != SoundManager.f_120344_) {
            this.f_120178_.m_91106_().m_120367_(this.f_120179_);
        }
        this.f_120180_ = Integer.MAX_VALUE;
    }

    public void m_120186_() {
        if (this.f_120179_ != null) {
            this.f_120178_.m_91106_().m_120399_(this.f_120179_);
            this.f_120179_ = null;
        }
        this.f_120180_ += 100;
    }

    public boolean m_120187_(Music p_120188_) {
        if (this.f_120179_ == null) {
            return false;
        }
        return p_120188_.m_11631_().m_11660_().equals(this.f_120179_.m_7904_());
    }
}

