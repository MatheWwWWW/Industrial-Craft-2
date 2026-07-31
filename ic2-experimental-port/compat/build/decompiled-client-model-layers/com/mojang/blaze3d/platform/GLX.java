/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  org.lwjgl.Version
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.glfw.GLFWErrorCallback
 *  org.lwjgl.glfw.GLFWErrorCallbackI
 *  org.lwjgl.glfw.GLFWVidMode
 *  org.slf4j.Logger
 *  oshi.SystemInfo
 *  oshi.hardware.CentralProcessor
 */
package com.mojang.blaze3d.platform;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.platform.GlDebug;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import net.minecraft.client.renderer.GameRenderer;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.slf4j.Logger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

@DontObfuscate
public class GLX {
    private static final Logger f_69332_ = LogUtils.getLogger();
    private static String f_69335_;

    public static String m_69359_() {
        RenderSystem.m_187554_();
        if (GLFW.glfwGetCurrentContext() == 0L) {
            return "NO CONTEXT";
        }
        return GlStateManager.m_84089_(7937) + " GL version " + GlStateManager.m_84089_(7938) + ", " + GlStateManager.m_84089_(7936);
    }

    public static int m_69341_(Window p_69342_) {
        RenderSystem.m_187554_();
        long $$1 = GLFW.glfwGetWindowMonitor((long)p_69342_.m_85439_());
        if ($$1 == 0L) {
            $$1 = GLFW.glfwGetPrimaryMonitor();
        }
        GLFWVidMode $$2 = $$1 == 0L ? null : GLFW.glfwGetVideoMode((long)$$1);
        return $$2 == null ? 0 : $$2.refreshRate();
    }

    public static String m_69340_() {
        RenderSystem.m_187551_();
        return Version.getVersion();
    }

    /*
     * WARNING - void declaration
     */
    public static LongSupplier m_69346_() {
        void $$4;
        RenderSystem.m_187551_();
        Window.m_85407_((p_242032_, p_242033_) -> {
            throw new IllegalStateException(String.format(Locale.ROOT, "GLFW error before init: [0x%X]%s", p_242032_, p_242033_));
        });
        ArrayList $$0 = Lists.newArrayList();
        GLFWErrorCallback $$1 = GLFW.glfwSetErrorCallback((p_69365_, p_69366_) -> $$0.add(String.format(Locale.ROOT, "GLFW error during init: [0x%X]%s", p_69365_, p_69366_)));
        if (GLFW.glfwInit()) {
            LongSupplier $$2 = () -> (long)(GLFW.glfwGetTime() * 1.0E9);
            for (String $$3 : $$0) {
                f_69332_.error("GLFW error collected during initialization: {}", (Object)$$3);
            }
        } else {
            throw new IllegalStateException("Failed to initialize GLFW, errors: " + Joiner.on((String)",").join((Iterable)$$0));
        }
        RenderSystem.m_69900_((GLFWErrorCallbackI)$$1);
        return $$4;
    }

    public static void m_69352_(GLFWErrorCallbackI p_69353_) {
        RenderSystem.m_187551_();
        GLFWErrorCallback $$1 = GLFW.glfwSetErrorCallback((GLFWErrorCallbackI)p_69353_);
        if ($$1 != null) {
            $$1.free();
        }
    }

    public static boolean m_69355_(Window p_69356_) {
        return GLFW.glfwWindowShouldClose((long)p_69356_.m_85439_());
    }

    public static void m_69343_(int p_69344_, boolean p_69345_) {
        RenderSystem.m_187551_();
        try {
            CentralProcessor $$2 = new SystemInfo().getHardware().getProcessor();
            f_69335_ = String.format(Locale.ROOT, "%dx %s", $$2.getLogicalProcessorCount(), $$2.getProcessorIdentifier().getName()).replaceAll("\\s+", " ");
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        GlDebug.m_84049_(p_69344_, p_69345_);
    }

    public static String m_69339_() {
        return f_69335_ == null ? "<unknown>" : f_69335_;
    }

    public static void m_69347_(int p_69348_, boolean p_69349_, boolean p_69350_, boolean p_69351_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84110_();
        GlStateManager.m_84298_(false);
        GlStateManager.m_84094_();
        RenderSystem.m_157427_(GameRenderer::m_172757_);
        Tesselator $$4 = RenderSystem.m_69883_();
        BufferBuilder $$5 = $$4.m_85915_();
        RenderSystem.m_69832_(4.0f);
        $$5.m_166779_(VertexFormat.Mode.LINES, DefaultVertexFormat.f_166851_);
        if (p_69349_) {
            $$5.m_5483_(0.0, 0.0, 0.0).m_6122_(0, 0, 0, 255).m_5601_(1.0f, 0.0f, 0.0f).m_5752_();
            $$5.m_5483_(p_69348_, 0.0, 0.0).m_6122_(0, 0, 0, 255).m_5601_(1.0f, 0.0f, 0.0f).m_5752_();
        }
        if (p_69350_) {
            $$5.m_5483_(0.0, 0.0, 0.0).m_6122_(0, 0, 0, 255).m_5601_(0.0f, 1.0f, 0.0f).m_5752_();
            $$5.m_5483_(0.0, p_69348_, 0.0).m_6122_(0, 0, 0, 255).m_5601_(0.0f, 1.0f, 0.0f).m_5752_();
        }
        if (p_69351_) {
            $$5.m_5483_(0.0, 0.0, 0.0).m_6122_(0, 0, 0, 255).m_5601_(0.0f, 0.0f, 1.0f).m_5752_();
            $$5.m_5483_(0.0, 0.0, p_69348_).m_6122_(0, 0, 0, 255).m_5601_(0.0f, 0.0f, 1.0f).m_5752_();
        }
        $$4.m_85914_();
        RenderSystem.m_69832_(2.0f);
        $$5.m_166779_(VertexFormat.Mode.LINES, DefaultVertexFormat.f_166851_);
        if (p_69349_) {
            $$5.m_5483_(0.0, 0.0, 0.0).m_6122_(255, 0, 0, 255).m_5601_(1.0f, 0.0f, 0.0f).m_5752_();
            $$5.m_5483_(p_69348_, 0.0, 0.0).m_6122_(255, 0, 0, 255).m_5601_(1.0f, 0.0f, 0.0f).m_5752_();
        }
        if (p_69350_) {
            $$5.m_5483_(0.0, 0.0, 0.0).m_6122_(0, 255, 0, 255).m_5601_(0.0f, 1.0f, 0.0f).m_5752_();
            $$5.m_5483_(0.0, p_69348_, 0.0).m_6122_(0, 255, 0, 255).m_5601_(0.0f, 1.0f, 0.0f).m_5752_();
        }
        if (p_69351_) {
            $$5.m_5483_(0.0, 0.0, 0.0).m_6122_(127, 127, 255, 255).m_5601_(0.0f, 0.0f, 1.0f).m_5752_();
            $$5.m_5483_(0.0, 0.0, p_69348_).m_6122_(127, 127, 255, 255).m_5601_(0.0f, 0.0f, 1.0f).m_5752_();
        }
        $$4.m_85914_();
        RenderSystem.m_69832_(1.0f);
        GlStateManager.m_84091_();
        GlStateManager.m_84298_(true);
        GlStateManager.m_84109_();
    }

    public static <T> T m_69373_(Supplier<T> p_69374_) {
        return p_69374_.get();
    }

    public static <T> T m_69370_(T p_69371_, Consumer<T> p_69372_) {
        p_69372_.accept(p_69371_);
        return p_69371_;
    }
}

