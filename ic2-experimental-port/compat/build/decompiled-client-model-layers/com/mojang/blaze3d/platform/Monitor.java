/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.glfw.GLFWVidMode
 *  org.lwjgl.glfw.GLFWVidMode$Buffer
 */
package com.mojang.blaze3d.platform;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.VideoMode;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public final class Monitor {
    private final long f_84936_;
    private final List<VideoMode> f_84937_;
    private VideoMode f_84938_;
    private int f_84939_;
    private int f_84940_;

    public Monitor(long p_84942_) {
        this.f_84936_ = p_84942_;
        this.f_84937_ = Lists.newArrayList();
        this.m_84943_();
    }

    public void m_84943_() {
        RenderSystem.m_187551_();
        this.f_84937_.clear();
        GLFWVidMode.Buffer $$0 = GLFW.glfwGetVideoModes((long)this.f_84936_);
        for (int $$1 = $$0.limit() - 1; $$1 >= 0; --$$1) {
            $$0.position($$1);
            VideoMode $$2 = new VideoMode($$0);
            if ($$2.m_85336_() < 8 || $$2.m_85337_() < 8 || $$2.m_85338_() < 8) continue;
            this.f_84937_.add($$2);
        }
        int[] $$3 = new int[1];
        int[] $$4 = new int[1];
        GLFW.glfwGetMonitorPos((long)this.f_84936_, (int[])$$3, (int[])$$4);
        this.f_84939_ = $$3[0];
        this.f_84940_ = $$4[0];
        GLFWVidMode $$5 = GLFW.glfwGetVideoMode((long)this.f_84936_);
        this.f_84938_ = new VideoMode($$5);
    }

    public VideoMode m_84948_(Optional<VideoMode> p_84949_) {
        RenderSystem.m_187551_();
        if (p_84949_.isPresent()) {
            VideoMode $$1 = p_84949_.get();
            for (VideoMode $$2 : this.f_84937_) {
                if (!$$2.equals($$1)) continue;
                return $$2;
            }
        }
        return this.m_84950_();
    }

    public int m_84946_(VideoMode p_84947_) {
        RenderSystem.m_187551_();
        return this.f_84937_.indexOf(p_84947_);
    }

    public VideoMode m_84950_() {
        return this.f_84938_;
    }

    public int m_84951_() {
        return this.f_84939_;
    }

    public int m_84952_() {
        return this.f_84940_;
    }

    public VideoMode m_84944_(int p_84945_) {
        return this.f_84937_.get(p_84945_);
    }

    public int m_84953_() {
        return this.f_84937_.size();
    }

    public long m_84954_() {
        return this.f_84936_;
    }

    public String toString() {
        return String.format(Locale.ROOT, "Monitor[%s %sx%s %s]", this.f_84936_, this.f_84939_, this.f_84940_, this.f_84938_);
    }
}

