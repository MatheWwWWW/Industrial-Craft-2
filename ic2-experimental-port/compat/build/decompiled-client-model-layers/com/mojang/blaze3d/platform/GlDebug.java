/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.EvictingQueue
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.lwjgl.opengl.ARBDebugOutput
 *  org.lwjgl.opengl.GL
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GLCapabilities
 *  org.lwjgl.opengl.GLDebugMessageARBCallback
 *  org.lwjgl.opengl.GLDebugMessageARBCallbackI
 *  org.lwjgl.opengl.GLDebugMessageCallback
 *  org.lwjgl.opengl.GLDebugMessageCallbackI
 *  org.lwjgl.opengl.KHRDebug
 *  org.slf4j.Logger
 */
package com.mojang.blaze3d.platform;

import com.google.common.collect.EvictingQueue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.DebugMemoryUntracker;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import javax.annotation.Nullable;
import org.lwjgl.opengl.ARBDebugOutput;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLDebugMessageARBCallback;
import org.lwjgl.opengl.GLDebugMessageARBCallbackI;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.GLDebugMessageCallbackI;
import org.lwjgl.opengl.KHRDebug;
import org.slf4j.Logger;

public class GlDebug {
    private static final Logger f_84028_ = LogUtils.getLogger();
    private static final int f_166220_ = 10;
    private static final Queue<LogEntry> f_166221_ = EvictingQueue.create((int)10);
    @Nullable
    private static volatile LogEntry f_166222_;
    private static final List<Integer> f_84032_;
    private static final List<Integer> f_84033_;
    private static boolean f_166223_;

    private static String m_84036_(int p_84037_) {
        return "Unknown (0x" + Integer.toHexString(p_84037_).toUpperCase() + ")";
    }

    public static String m_84055_(int p_84056_) {
        switch (p_84056_) {
            case 33350: {
                return "API";
            }
            case 33351: {
                return "WINDOW SYSTEM";
            }
            case 33352: {
                return "SHADER COMPILER";
            }
            case 33353: {
                return "THIRD PARTY";
            }
            case 33354: {
                return "APPLICATION";
            }
            case 33355: {
                return "OTHER";
            }
        }
        return GlDebug.m_84036_(p_84056_);
    }

    public static String m_84057_(int p_84058_) {
        switch (p_84058_) {
            case 33356: {
                return "ERROR";
            }
            case 33357: {
                return "DEPRECATED BEHAVIOR";
            }
            case 33358: {
                return "UNDEFINED BEHAVIOR";
            }
            case 33359: {
                return "PORTABILITY";
            }
            case 33360: {
                return "PERFORMANCE";
            }
            case 33361: {
                return "OTHER";
            }
            case 33384: {
                return "MARKER";
            }
        }
        return GlDebug.m_84036_(p_84058_);
    }

    public static String m_84059_(int p_84060_) {
        switch (p_84060_) {
            case 37190: {
                return "HIGH";
            }
            case 37191: {
                return "MEDIUM";
            }
            case 37192: {
                return "LOW";
            }
            case 33387: {
                return "NOTIFICATION";
            }
        }
        return GlDebug.m_84036_(p_84060_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    private static void m_84038_(int p_84039_, int p_84040_, int p_84041_, int p_84042_, int p_84043_, long p_84044_, long p_84045_) {
        void $$9;
        String $$7 = GLDebugMessageCallback.getMessage((int)p_84043_, (long)p_84044_);
        Queue<LogEntry> queue = f_166221_;
        synchronized (queue) {
            LogEntry $$8 = f_166222_;
            if ($$8 == null || !$$8.m_166239_(p_84039_, p_84040_, p_84041_, p_84042_, $$7)) {
                $$8 = new LogEntry(p_84039_, p_84040_, p_84041_, p_84042_, $$7);
                f_166221_.add($$8);
                f_166222_ = $$8;
            } else {
                ++$$8.f_166232_;
            }
        }
        f_84028_.info("OpenGL debug message: {}", (Object)$$9);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static List<String> m_166225_() {
        Queue<LogEntry> queue = f_166221_;
        synchronized (queue) {
            ArrayList $$0 = Lists.newArrayListWithCapacity((int)f_166221_.size());
            for (LogEntry $$1 : f_166221_) {
                $$0.add($$1 + " x " + $$1.f_166232_);
            }
            return $$0;
        }
    }

    public static boolean m_166226_() {
        return f_166223_;
    }

    public static void m_84049_(int p_84050_, boolean p_84051_) {
        RenderSystem.m_187551_();
        if (p_84050_ <= 0) {
            return;
        }
        GLCapabilities $$2 = GL.getCapabilities();
        if ($$2.GL_KHR_debug) {
            f_166223_ = true;
            GL11.glEnable((int)37600);
            if (p_84051_) {
                GL11.glEnable((int)33346);
            }
            for (int $$3 = 0; $$3 < f_84032_.size(); ++$$3) {
                boolean $$4 = $$3 < p_84050_;
                KHRDebug.glDebugMessageControl((int)4352, (int)4352, (int)f_84032_.get($$3), (int[])null, (boolean)$$4);
            }
            KHRDebug.glDebugMessageCallback((GLDebugMessageCallbackI)((GLDebugMessageCallbackI)GLX.m_69370_(GLDebugMessageCallback.create(GlDebug::m_84038_), DebugMemoryUntracker::m_84003_)), (long)0L);
        } else if ($$2.GL_ARB_debug_output) {
            f_166223_ = true;
            if (p_84051_) {
                GL11.glEnable((int)33346);
            }
            for (int $$5 = 0; $$5 < f_84033_.size(); ++$$5) {
                boolean $$6 = $$5 < p_84050_;
                ARBDebugOutput.glDebugMessageControlARB((int)4352, (int)4352, (int)f_84033_.get($$5), (int[])null, (boolean)$$6);
            }
            ARBDebugOutput.glDebugMessageCallbackARB((GLDebugMessageARBCallbackI)((GLDebugMessageARBCallbackI)GLX.m_69370_(GLDebugMessageARBCallback.create(GlDebug::m_84038_), DebugMemoryUntracker::m_84003_)), (long)0L);
        }
    }

    static {
        f_84032_ = ImmutableList.of((Object)37190, (Object)37191, (Object)37192, (Object)33387);
        f_84033_ = ImmutableList.of((Object)37190, (Object)37191, (Object)37192);
    }

    static class LogEntry {
        private final int f_166227_;
        private final int f_166228_;
        private final int f_166229_;
        private final int f_166230_;
        private final String f_166231_;
        int f_166232_ = 1;

        LogEntry(int p_166234_, int p_166235_, int p_166236_, int p_166237_, String p_166238_) {
            this.f_166227_ = p_166236_;
            this.f_166228_ = p_166234_;
            this.f_166229_ = p_166235_;
            this.f_166230_ = p_166237_;
            this.f_166231_ = p_166238_;
        }

        boolean m_166239_(int p_166240_, int p_166241_, int p_166242_, int p_166243_, String p_166244_) {
            return p_166241_ == this.f_166229_ && p_166240_ == this.f_166228_ && p_166242_ == this.f_166227_ && p_166243_ == this.f_166230_ && p_166244_.equals(this.f_166231_);
        }

        public String toString() {
            return "id=" + this.f_166227_ + ", source=" + GlDebug.m_84055_(this.f_166228_) + ", type=" + GlDebug.m_84057_(this.f_166229_) + ", severity=" + GlDebug.m_84059_(this.f_166230_) + ", message='" + this.f_166231_ + "'";
        }
    }
}

