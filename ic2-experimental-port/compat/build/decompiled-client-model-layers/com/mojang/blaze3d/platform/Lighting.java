/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.Util;

public class Lighting {
    private static final Vector3f f_84919_ = Util.m_137469_(new Vector3f(0.2f, 1.0f, -0.7f), Vector3f::m_122278_);
    private static final Vector3f f_84920_ = Util.m_137469_(new Vector3f(-0.2f, 1.0f, 0.7f), Vector3f::m_122278_);
    private static final Vector3f f_84921_ = Util.m_137469_(new Vector3f(0.2f, 1.0f, -0.7f), Vector3f::m_122278_);
    private static final Vector3f f_84922_ = Util.m_137469_(new Vector3f(-0.2f, -1.0f, 0.7f), Vector3f::m_122278_);
    private static final Vector3f f_166381_ = Util.m_137469_(new Vector3f(0.2f, -1.0f, -1.0f), Vector3f::m_122278_);
    private static final Vector3f f_166382_ = Util.m_137469_(new Vector3f(-0.2f, -1.0f, 0.0f), Vector3f::m_122278_);

    public static void m_84925_(Matrix4f p_84926_) {
        RenderSystem.m_69914_(f_84921_, f_84922_, p_84926_);
    }

    public static void m_84928_(Matrix4f p_84929_) {
        RenderSystem.m_69914_(f_84919_, f_84920_, p_84929_);
    }

    public static void m_84930_() {
        RenderSystem.m_69911_(f_84919_, f_84920_);
    }

    public static void m_84931_() {
        RenderSystem.m_69908_(f_84919_, f_84920_);
    }

    public static void m_166384_() {
        RenderSystem.m_157450_(f_166381_, f_166382_);
    }
}

