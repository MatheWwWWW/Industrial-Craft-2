/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.system.MemoryUtil
 */
package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.lwjgl.system.MemoryUtil;

public class GlUtil {
    public static ByteBuffer m_166247_(int p_166248_) {
        return MemoryUtil.memAlloc((int)p_166248_);
    }

    public static void m_166251_(Buffer p_166252_) {
        MemoryUtil.memFree((Buffer)p_166252_);
    }

    public static String m_84818_() {
        return GlStateManager.m_84089_(7936);
    }

    public static String m_84819_() {
        return GLX.m_69339_();
    }

    public static String m_84820_() {
        return GlStateManager.m_84089_(7937);
    }

    public static String m_84821_() {
        return GlStateManager.m_84089_(7938);
    }
}

