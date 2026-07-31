/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.lwjgl.opengl.ARBTimerQuery
 *  org.lwjgl.opengl.GL
 *  org.lwjgl.opengl.GL32C
 */
package com.mojang.blaze3d.systems;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Optional;
import javax.annotation.Nullable;
import org.lwjgl.opengl.ARBTimerQuery;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL32C;

public class TimerQuery {
    private int f_231138_;

    public static Optional<TimerQuery> m_231140_() {
        return TimerQueryLazyLoader.f_231152_;
    }

    public void m_231141_() {
        RenderSystem.m_187554_();
        if (this.f_231138_ != 0) {
            throw new IllegalStateException("Current profile not ended");
        }
        this.f_231138_ = GL32C.glGenQueries();
        GL32C.glBeginQuery((int)35007, (int)this.f_231138_);
    }

    public FrameProfile m_231142_() {
        RenderSystem.m_187554_();
        if (this.f_231138_ == 0) {
            throw new IllegalStateException("endProfile called before beginProfile");
        }
        GL32C.glEndQuery((int)35007);
        FrameProfile $$0 = new FrameProfile(this.f_231138_);
        this.f_231138_ = 0;
        return $$0;
    }

    static class TimerQueryLazyLoader {
        static final Optional<TimerQuery> f_231152_ = Optional.ofNullable(TimerQueryLazyLoader.m_231155_());

        private TimerQueryLazyLoader() {
        }

        @Nullable
        private static TimerQuery m_231155_() {
            if (!GL.getCapabilities().GL_ARB_timer_query) {
                return null;
            }
            return new TimerQuery();
        }
    }

    public static class FrameProfile {
        private static final long f_231143_ = 0L;
        private static final long f_231144_ = -1L;
        private final int f_231145_;
        private long f_231146_;

        FrameProfile(int p_231148_) {
            this.f_231145_ = p_231148_;
        }

        public void m_231149_() {
            RenderSystem.m_187554_();
            if (this.f_231146_ != 0L) {
                return;
            }
            this.f_231146_ = -1L;
            GL32C.glDeleteQueries((int)this.f_231145_);
        }

        public boolean m_231150_() {
            RenderSystem.m_187554_();
            if (this.f_231146_ != 0L) {
                return true;
            }
            if (1 == GL32C.glGetQueryObjecti((int)this.f_231145_, (int)34919)) {
                this.f_231146_ = ARBTimerQuery.glGetQueryObjecti64((int)this.f_231145_, (int)34918);
                GL32C.glDeleteQueries((int)this.f_231145_);
                return true;
            }
            return false;
        }

        public long m_231151_() {
            RenderSystem.m_187554_();
            if (this.f_231146_ == 0L) {
                this.f_231146_ = ARBTimerQuery.glGetQueryObjecti64((int)this.f_231145_, (int)34918);
                GL32C.glDeleteQueries((int)this.f_231145_);
            }
            return this.f_231146_;
        }
    }
}

