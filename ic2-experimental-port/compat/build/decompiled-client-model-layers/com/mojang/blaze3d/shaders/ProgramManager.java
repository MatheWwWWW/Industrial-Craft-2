/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package com.mojang.blaze3d.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.Shader;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import org.slf4j.Logger;

public class ProgramManager {
    private static final Logger f_85575_ = LogUtils.getLogger();

    public static void m_85578_(int p_85579_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84478_(p_85579_);
    }

    public static void m_166621_(Shader p_166622_) {
        RenderSystem.m_187554_();
        p_166622_.m_108964_().m_85543_();
        p_166622_.m_108962_().m_85543_();
        GlStateManager.m_84484_(p_166622_.m_108943_());
    }

    public static int m_85577_() throws IOException {
        RenderSystem.m_187554_();
        int $$0 = GlStateManager.m_84531_();
        if ($$0 <= 0) {
            throw new IOException("Could not create shader program (returned program ID " + $$0 + ")");
        }
        return $$0;
    }

    public static void m_166623_(Shader p_166624_) {
        RenderSystem.m_187554_();
        p_166624_.m_142662_();
        GlStateManager.m_84490_(p_166624_.m_108943_());
        int $$1 = GlStateManager.m_84381_(p_166624_.m_108943_(), 35714);
        if ($$1 == 0) {
            f_85575_.warn("Error encountered when linking program containing VS {} and FS {}. Log output:", (Object)p_166624_.m_108962_().m_85551_(), (Object)p_166624_.m_108964_().m_85551_());
            f_85575_.warn(GlStateManager.m_84498_(p_166624_.m_108943_(), 32768));
        }
    }
}

