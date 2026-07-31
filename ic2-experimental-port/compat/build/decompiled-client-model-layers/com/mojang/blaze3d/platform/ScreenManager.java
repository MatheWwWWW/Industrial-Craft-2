/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  javax.annotation.Nullable
 *  org.lwjgl.PointerBuffer
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.glfw.GLFWMonitorCallback
 *  org.slf4j.Logger
 */
package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.MonitorCreator;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;
import org.slf4j.Logger;

public class ScreenManager {
    private static final Logger f_212357_ = LogUtils.getLogger();
    private final Long2ObjectMap<Monitor> f_85262_ = new Long2ObjectOpenHashMap();
    private final MonitorCreator f_85263_;

    public ScreenManager(MonitorCreator p_85265_) {
        RenderSystem.m_187551_();
        this.f_85263_ = p_85265_;
        GLFW.glfwSetMonitorCallback(this::m_85273_);
        PointerBuffer $$1 = GLFW.glfwGetMonitors();
        if ($$1 != null) {
            for (int $$2 = 0; $$2 < $$1.limit(); ++$$2) {
                long $$3 = $$1.get($$2);
                this.f_85262_.put($$3, (Object)p_85265_.m_84956_($$3));
            }
        }
    }

    private void m_85273_(long p_85274_, int p_85275_) {
        RenderSystem.m_187554_();
        if (p_85275_ == 262145) {
            this.f_85262_.put(p_85274_, (Object)this.f_85263_.m_84956_(p_85274_));
            f_212357_.debug("Monitor {} connected. Current monitors: {}", (Object)p_85274_, this.f_85262_);
        } else if (p_85275_ == 262146) {
            this.f_85262_.remove(p_85274_);
            f_212357_.debug("Monitor {} disconnected. Current monitors: {}", (Object)p_85274_, this.f_85262_);
        }
    }

    @Nullable
    public Monitor m_85271_(long p_85272_) {
        RenderSystem.m_187551_();
        return (Monitor)this.f_85262_.get(p_85272_);
    }

    @Nullable
    public Monitor m_85276_(Window p_85277_) {
        long $$1 = GLFW.glfwGetWindowMonitor((long)p_85277_.m_85439_());
        if ($$1 != 0L) {
            return this.m_85271_($$1);
        }
        int $$2 = p_85277_.m_85447_();
        int $$3 = $$2 + p_85277_.m_85443_();
        int $$4 = p_85277_.m_85448_();
        int $$5 = $$4 + p_85277_.m_85444_();
        int $$6 = -1;
        Monitor $$7 = null;
        long $$8 = GLFW.glfwGetPrimaryMonitor();
        f_212357_.debug("Selecting monitor - primary: {}, current monitors: {}", (Object)$$8, this.f_85262_);
        for (Monitor $$9 : this.f_85262_.values()) {
            int $$19;
            int $$10 = $$9.m_84951_();
            int $$11 = $$10 + $$9.m_84950_().m_85332_();
            int $$12 = $$9.m_84952_();
            int $$13 = $$12 + $$9.m_84950_().m_85335_();
            int $$14 = ScreenManager.m_85267_($$2, $$10, $$11);
            int $$15 = ScreenManager.m_85267_($$3, $$10, $$11);
            int $$16 = ScreenManager.m_85267_($$4, $$12, $$13);
            int $$17 = ScreenManager.m_85267_($$5, $$12, $$13);
            int $$18 = Math.max(0, $$15 - $$14);
            int $$20 = $$18 * ($$19 = Math.max(0, $$17 - $$16));
            if ($$20 > $$6) {
                $$7 = $$9;
                $$6 = $$20;
                continue;
            }
            if ($$20 != $$6 || $$8 != $$9.m_84954_()) continue;
            f_212357_.debug("Primary monitor {} is preferred to monitor {}", (Object)$$9, (Object)$$7);
            $$7 = $$9;
        }
        f_212357_.debug("Selected monitor: {}", $$7);
        return $$7;
    }

    public static int m_85267_(int p_85268_, int p_85269_, int p_85270_) {
        if (p_85268_ < p_85269_) {
            return p_85269_;
        }
        if (p_85268_ > p_85270_) {
            return p_85270_;
        }
        return p_85268_;
    }

    public void m_85266_() {
        RenderSystem.m_187554_();
        GLFWMonitorCallback $$0 = GLFW.glfwSetMonitorCallback(null);
        if ($$0 != null) {
            $$0.free();
        }
    }
}

