/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.system.MemoryUtil
 *  org.slf4j.Logger
 */
package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.SharedConstants;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;

@DontObfuscate
public class TextureUtil {
    private static final Logger f_85278_ = LogUtils.getLogger();
    public static final int f_157132_ = 0;
    private static final int f_157131_ = 8192;

    public static int m_85280_() {
        RenderSystem.m_187555_();
        if (SharedConstants.f_136183_) {
            int[] $$0 = new int[ThreadLocalRandom.current().nextInt(15) + 1];
            GlStateManager.m_84305_($$0);
            int $$1 = GlStateManager.m_84111_();
            GlStateManager.m_84365_($$0);
            return $$1;
        }
        return GlStateManager.m_84111_();
    }

    public static void m_85281_(int p_85282_) {
        RenderSystem.m_187555_();
        GlStateManager.m_84541_(p_85282_);
    }

    public static void m_85283_(int p_85284_, int p_85285_, int p_85286_) {
        TextureUtil.m_85297_(NativeImage.InternalGlFormat.RGBA, p_85284_, 0, p_85285_, p_85286_);
    }

    public static void m_85292_(NativeImage.InternalGlFormat p_85293_, int p_85294_, int p_85295_, int p_85296_) {
        TextureUtil.m_85297_(p_85293_, p_85294_, 0, p_85295_, p_85296_);
    }

    public static void m_85287_(int p_85288_, int p_85289_, int p_85290_, int p_85291_) {
        TextureUtil.m_85297_(NativeImage.InternalGlFormat.RGBA, p_85288_, p_85289_, p_85290_, p_85291_);
    }

    public static void m_85297_(NativeImage.InternalGlFormat p_85298_, int p_85299_, int p_85300_, int p_85301_, int p_85302_) {
        RenderSystem.m_187555_();
        TextureUtil.m_85309_(p_85299_);
        if (p_85300_ >= 0) {
            GlStateManager.m_84331_(3553, 33085, p_85300_);
            GlStateManager.m_84331_(3553, 33082, 0);
            GlStateManager.m_84331_(3553, 33083, p_85300_);
            GlStateManager.m_84160_(3553, 34049, 0.0f);
        }
        for (int $$5 = 0; $$5 <= p_85300_; ++$$5) {
            GlStateManager.m_84209_(3553, $$5, p_85298_.m_85191_(), p_85301_ >> $$5, p_85302_ >> $$5, 0, 6408, 5121, null);
        }
    }

    private static void m_85309_(int p_85310_) {
        RenderSystem.m_187555_();
        GlStateManager.m_84544_(p_85310_);
    }

    public static ByteBuffer m_85303_(InputStream p_85304_) throws IOException {
        ByteBuffer $$4;
        if (p_85304_ instanceof FileInputStream) {
            FileInputStream $$1 = (FileInputStream)p_85304_;
            FileChannel $$2 = $$1.getChannel();
            ByteBuffer $$3 = MemoryUtil.memAlloc((int)((int)$$2.size() + 1));
            while ($$2.read($$3) != -1) {
            }
        } else {
            $$4 = MemoryUtil.memAlloc((int)8192);
            ReadableByteChannel $$5 = Channels.newChannel(p_85304_);
            while ($$5.read($$4) != -1) {
                if ($$4.remaining() != 0) continue;
                $$4 = MemoryUtil.memRealloc((ByteBuffer)$$4, (int)($$4.capacity() * 2));
            }
        }
        return $$4;
    }

    public static void m_157134_(String p_157135_, int p_157136_, int p_157137_, int p_157138_, int p_157139_) {
        RenderSystem.m_187554_();
        TextureUtil.m_85309_(p_157136_);
        for (int $$5 = 0; $$5 <= p_157137_; ++$$5) {
            String $$6 = p_157135_ + "_" + $$5 + ".png";
            int $$7 = p_157138_ >> $$5;
            int $$8 = p_157139_ >> $$5;
            try (NativeImage $$9 = new NativeImage($$7, $$8, false);){
                $$9.m_85045_($$5, false);
                $$9.m_166406_($$6);
                f_85278_.debug("Exported png to: {}", (Object)new File($$6).getAbsolutePath());
                continue;
            }
            catch (IOException $$10) {
                f_85278_.debug("Unable to write: ", (Throwable)$$10);
            }
        }
    }

    public static void m_85305_(IntBuffer p_85306_, int p_85307_, int p_85308_) {
        RenderSystem.m_187554_();
        GL11.glPixelStorei((int)3312, (int)0);
        GL11.glPixelStorei((int)3313, (int)0);
        GL11.glPixelStorei((int)3314, (int)0);
        GL11.glPixelStorei((int)3315, (int)0);
        GL11.glPixelStorei((int)3316, (int)0);
        GL11.glPixelStorei((int)3317, (int)4);
        GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)p_85307_, (int)p_85308_, (int)0, (int)32993, (int)33639, (IntBuffer)p_85306_);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
    }
}

