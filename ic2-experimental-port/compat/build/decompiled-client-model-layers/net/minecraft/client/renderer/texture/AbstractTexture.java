/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.concurrent.Executor;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public abstract class AbstractTexture
implements AutoCloseable {
    public static final int f_174680_ = -1;
    protected int f_117950_ = -1;
    protected boolean f_117951_;
    protected boolean f_117952_;

    public void m_117960_(boolean p_117961_, boolean p_117962_) {
        int $$5;
        int $$4;
        RenderSystem.m_187555_();
        this.f_117951_ = p_117961_;
        this.f_117952_ = p_117962_;
        if (p_117961_) {
            int $$2 = p_117962_ ? 9987 : 9729;
            int $$3 = 9729;
        } else {
            $$4 = p_117962_ ? 9986 : 9728;
            $$5 = 9728;
        }
        this.m_117966_();
        GlStateManager.m_84331_(3553, 10241, $$4);
        GlStateManager.m_84331_(3553, 10240, $$5);
    }

    public int m_117963_() {
        RenderSystem.m_187555_();
        if (this.f_117950_ == -1) {
            this.f_117950_ = TextureUtil.m_85280_();
        }
        return this.f_117950_;
    }

    public void m_117964_() {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> {
                if (this.f_117950_ != -1) {
                    TextureUtil.m_85281_(this.f_117950_);
                    this.f_117950_ = -1;
                }
            });
        } else if (this.f_117950_ != -1) {
            TextureUtil.m_85281_(this.f_117950_);
            this.f_117950_ = -1;
        }
    }

    public abstract void m_6704_(ResourceManager var1) throws IOException;

    public void m_117966_() {
        if (!RenderSystem.m_69587_()) {
            RenderSystem.m_69879_(() -> GlStateManager.m_84544_(this.m_117963_()));
        } else {
            GlStateManager.m_84544_(this.m_117963_());
        }
    }

    public void m_6479_(TextureManager p_117956_, ResourceManager p_117957_, ResourceLocation p_117958_, Executor p_117959_) {
        p_117956_.m_118495_(p_117958_, this);
    }

    @Override
    public void close() {
    }
}

