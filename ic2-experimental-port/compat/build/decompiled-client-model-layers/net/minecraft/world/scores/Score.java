/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.scores;

import java.util.Comparator;
import javax.annotation.Nullable;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;

public class Score {
    public static final Comparator<Score> f_83380_ = (p_83396_, p_83397_) -> {
        if (p_83396_.m_83400_() > p_83397_.m_83400_()) {
            return 1;
        }
        if (p_83396_.m_83400_() < p_83397_.m_83400_()) {
            return -1;
        }
        return p_83397_.m_83405_().compareToIgnoreCase(p_83396_.m_83405_());
    };
    private final Scoreboard f_83381_;
    @Nullable
    private final Objective f_83382_;
    private final String f_83383_;
    private int f_83384_;
    private boolean f_83385_;
    private boolean f_83386_;

    public Score(Scoreboard p_83389_, Objective p_83390_, String p_83391_) {
        this.f_83381_ = p_83389_;
        this.f_83382_ = p_83390_;
        this.f_83383_ = p_83391_;
        this.f_83385_ = true;
        this.f_83386_ = true;
    }

    public void m_83393_(int p_83394_) {
        if (this.f_83382_.m_83321_().m_83621_()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.m_83402_(this.m_83400_() + p_83394_);
    }

    public void m_83392_() {
        this.m_83393_(1);
    }

    public int m_83400_() {
        return this.f_83384_;
    }

    public void m_83401_() {
        this.m_83402_(0);
    }

    public void m_83402_(int p_83403_) {
        int $$1 = this.f_83384_;
        this.f_83384_ = p_83403_;
        if ($$1 != p_83403_ || this.f_83386_) {
            this.f_83386_ = false;
            this.m_83406_().m_5734_(this);
        }
    }

    @Nullable
    public Objective m_83404_() {
        return this.f_83382_;
    }

    public String m_83405_() {
        return this.f_83383_;
    }

    public Scoreboard m_83406_() {
        return this.f_83381_;
    }

    public boolean m_83407_() {
        return this.f_83385_;
    }

    public void m_83398_(boolean p_83399_) {
        this.f_83385_ = p_83399_;
    }
}

