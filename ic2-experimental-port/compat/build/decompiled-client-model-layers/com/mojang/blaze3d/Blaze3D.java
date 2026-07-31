/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.system.MemoryUtil
 */
package com.mojang.blaze3d;

import com.mojang.blaze3d.pipeline.RenderCall;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

public class Blaze3D {
    public static void m_166118_(RenderPipeline p_166119_, float p_166120_) {
        ConcurrentLinkedQueue<RenderCall> $$2 = p_166119_.m_166192_();
    }

    public static void m_166121_(RenderPipeline p_166122_, float p_166123_) {
        ConcurrentLinkedQueue<RenderCall> $$2 = p_166122_.m_166193_();
    }

    public static void m_83639_() {
        MemoryUtil.memSet((long)0L, (int)0, (long)1L);
    }

    public static double m_83640_() {
        return GLFW.glfwGetTime();
    }
}

