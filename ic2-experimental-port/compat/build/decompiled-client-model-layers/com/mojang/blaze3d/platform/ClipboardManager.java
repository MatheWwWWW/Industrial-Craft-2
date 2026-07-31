/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.glfw.GLFWErrorCallback
 *  org.lwjgl.glfw.GLFWErrorCallbackI
 *  org.lwjgl.system.MemoryUtil
 */
package com.mojang.blaze3d.platform;

import com.google.common.base.Charsets;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import net.minecraft.util.StringDecomposer;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.system.MemoryUtil;

public class ClipboardManager {
    public static final int f_166218_ = 65545;
    private final ByteBuffer f_83986_ = BufferUtils.createByteBuffer((int)8192);

    public String m_83995_(long p_83996_, GLFWErrorCallbackI p_83997_) {
        GLFWErrorCallback $$2 = GLFW.glfwSetErrorCallback((GLFWErrorCallbackI)p_83997_);
        String $$3 = GLFW.glfwGetClipboardString((long)p_83996_);
        $$3 = $$3 != null ? StringDecomposer.m_14304_($$3) : "";
        GLFWErrorCallback $$4 = GLFW.glfwSetErrorCallback((GLFWErrorCallbackI)$$2);
        if ($$4 != null) {
            $$4.free();
        }
        return $$3;
    }

    private static void m_83991_(long p_83992_, ByteBuffer p_83993_, byte[] p_83994_) {
        p_83993_.clear();
        p_83993_.put(p_83994_);
        p_83993_.put((byte)0);
        p_83993_.flip();
        GLFW.glfwSetClipboardString((long)p_83992_, (ByteBuffer)p_83993_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void m_83988_(long p_83989_, String p_83990_) {
        byte[] $$2 = p_83990_.getBytes(Charsets.UTF_8);
        int $$3 = $$2.length + 1;
        if ($$3 < this.f_83986_.capacity()) {
            ClipboardManager.m_83991_(p_83989_, this.f_83986_, $$2);
        } else {
            ByteBuffer $$4 = MemoryUtil.memAlloc((int)$$3);
            try {
                ClipboardManager.m_83991_(p_83989_, $$4, $$2);
            }
            finally {
                MemoryUtil.memFree((Buffer)$$4);
            }
        }
    }
}

