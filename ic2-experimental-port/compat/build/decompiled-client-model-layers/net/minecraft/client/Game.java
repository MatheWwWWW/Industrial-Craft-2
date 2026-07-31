/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.bridge.Bridge
 *  com.mojang.bridge.game.GameSession
 *  com.mojang.bridge.game.GameVersion
 *  com.mojang.bridge.game.Language
 *  com.mojang.bridge.game.PerformanceMetrics
 *  com.mojang.bridge.game.RunningGame
 *  com.mojang.bridge.launcher.Launcher
 *  com.mojang.bridge.launcher.SessionEventListener
 *  javax.annotation.Nullable
 */
package net.minecraft.client;

import com.mojang.bridge.Bridge;
import com.mojang.bridge.game.GameSession;
import com.mojang.bridge.game.GameVersion;
import com.mojang.bridge.game.Language;
import com.mojang.bridge.game.PerformanceMetrics;
import com.mojang.bridge.game.RunningGame;
import com.mojang.bridge.launcher.Launcher;
import com.mojang.bridge.launcher.SessionEventListener;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Session;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.FrameTimer;

public class Game
implements RunningGame {
    private final Minecraft f_90734_;
    @Nullable
    private final Launcher f_90735_;
    private SessionEventListener f_90736_ = SessionEventListener.NONE;

    public Game(Minecraft p_90738_) {
        this.f_90734_ = p_90738_;
        this.f_90735_ = Bridge.getLauncher();
        if (this.f_90735_ != null) {
            this.f_90735_.registerGame((RunningGame)this);
        }
    }

    public GameVersion getVersion() {
        return SharedConstants.m_183709_();
    }

    public Language getSelectedLanguage() {
        return this.f_90734_.m_91102_().m_118983_();
    }

    @Nullable
    public GameSession getCurrentSession() {
        ClientLevel $$0 = this.f_90734_.f_91073_;
        return $$0 == null ? null : new Session($$0, this.f_90734_.f_91074_, this.f_90734_.f_91074_.f_108617_);
    }

    public PerformanceMetrics getPerformanceMetrics() {
        FrameTimer $$0 = this.f_90734_.m_91293_();
        long $$1 = Integer.MAX_VALUE;
        long $$2 = Integer.MIN_VALUE;
        long $$3 = 0L;
        for (long $$4 : $$0.m_13764_()) {
            $$1 = Math.min($$1, $$4);
            $$2 = Math.max($$2, $$4);
            $$3 += $$4;
        }
        return new Metrics((int)$$1, (int)$$2, (int)($$3 / (long)$$0.m_13764_().length), $$0.m_13764_().length);
    }

    public void setSessionEventListener(SessionEventListener p_90746_) {
        this.f_90736_ = p_90746_;
    }

    public void m_90739_() {
        this.f_90736_.onStartGameSession(this.getCurrentSession());
    }

    public void m_90740_() {
        this.f_90736_.onLeaveGameSession(this.getCurrentSession());
    }

    static class Metrics
    implements PerformanceMetrics {
        private final int f_90747_;
        private final int f_90748_;
        private final int f_90749_;
        private final int f_90750_;

        public Metrics(int p_90752_, int p_90753_, int p_90754_, int p_90755_) {
            this.f_90747_ = p_90752_;
            this.f_90748_ = p_90753_;
            this.f_90749_ = p_90754_;
            this.f_90750_ = p_90755_;
        }

        public int getMinTime() {
            return this.f_90747_;
        }

        public int getMaxTime() {
            return this.f_90748_;
        }

        public int getAverageTime() {
            return this.f_90749_;
        }

        public int getSampleCount() {
            return this.f_90750_;
        }
    }
}

