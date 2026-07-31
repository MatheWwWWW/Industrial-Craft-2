/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

public class DynamicTexture
extends AbstractTexture {
    private static final Logger f_117976_ = LogUtils.getLogger();
    @Nullable
    private NativeImage f_117977_;

    public DynamicTexture(NativeImage p_117984_) {
        this.f_117977_ = p_117984_;
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> {
                TextureUtil.m_85283_(this.m_117963_(), this.f_117977_.m_84982_(), this.f_117977_.m_85084_());
                this.m_117985_();
            });
        } else {
            TextureUtil.m_85283_(this.m_117963_(), this.f_117977_.m_84982_(), this.f_117977_.m_85084_());
            this.m_117985_();
        }
    }

    public DynamicTexture(int p_117980_, int p_117981_, boolean p_117982_) {
        RenderSystem.m_187553_();
        this.f_117977_ = new NativeImage(p_117980_, p_117981_, p_117982_);
        TextureUtil.m_85283_(this.m_117963_(), this.f_117977_.m_84982_(), this.f_117977_.m_85084_());
    }

    @Override
    public void m_6704_(ResourceManager p_117987_) {
    }

    public void m_117985_() {
        if (this.f_117977_ != null) {
            this.m_117966_();
            this.f_117977_.m_85040_(0, 0, 0, false);
        } else {
            f_117976_.warn("Trying to upload disposed texture {}", (Object)this.m_117963_());
        }
    }

    @Nullable
    public NativeImage m_117991_() {
        return this.f_117977_;
    }

    public void m_117988_(NativeImage p_117989_) {
        if (this.f_117977_ != null) {
            this.f_117977_.close();
        }
        this.f_117977_ = p_117989_;
    }

    @Override
    public void close() {
        if (this.f_117977_ != null) {
            this.f_117977_.close();
            this.m_117964_();
            this.f_117977_ = null;
        }
    }
}

