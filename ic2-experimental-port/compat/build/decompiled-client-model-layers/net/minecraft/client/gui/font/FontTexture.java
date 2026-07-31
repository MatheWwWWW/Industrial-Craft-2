/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.font;

import com.mojang.blaze3d.font.SheetGlyphInfo;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import javax.annotation.Nullable;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public class FontTexture
extends AbstractTexture {
    private static final int f_169092_ = 256;
    private final ResourceLocation f_95091_;
    private final RenderType f_95092_;
    private final RenderType f_95093_;
    private final RenderType f_181373_;
    private final boolean f_95094_;
    private final Node f_95095_;

    public FontTexture(ResourceLocation p_95097_, boolean p_95098_) {
        this.f_95091_ = p_95097_;
        this.f_95094_ = p_95098_;
        this.f_95095_ = new Node(0, 0, 256, 256);
        TextureUtil.m_85292_(p_95098_ ? NativeImage.InternalGlFormat.RGBA : NativeImage.InternalGlFormat.RED, this.m_117963_(), 256, 256);
        this.f_95092_ = p_95098_ ? RenderType.m_110497_(p_95097_) : RenderType.m_173237_(p_95097_);
        this.f_95093_ = p_95098_ ? RenderType.m_110500_(p_95097_) : RenderType.m_173240_(p_95097_);
        this.f_181373_ = p_95098_ ? RenderType.m_181444_(p_95097_) : RenderType.m_181446_(p_95097_);
    }

    @Override
    public void m_6704_(ResourceManager p_95101_) {
    }

    @Override
    public void close() {
        this.m_117964_();
    }

    @Nullable
    public BakedGlyph m_232568_(SheetGlyphInfo p_232569_) {
        if (p_232569_.m_213965_() != this.f_95094_) {
            return null;
        }
        Node $$1 = this.f_95095_.m_232570_(p_232569_);
        if ($$1 != null) {
            this.m_117966_();
            p_232569_.m_213958_($$1.f_95105_, $$1.f_95106_);
            float $$2 = 256.0f;
            float $$3 = 256.0f;
            float $$4 = 0.01f;
            return new BakedGlyph(this.f_95092_, this.f_95093_, this.f_181373_, ((float)$$1.f_95105_ + 0.01f) / 256.0f, ((float)$$1.f_95105_ - 0.01f + (float)p_232569_.m_213962_()) / 256.0f, ((float)$$1.f_95106_ + 0.01f) / 256.0f, ((float)$$1.f_95106_ - 0.01f + (float)p_232569_.m_213961_()) / 256.0f, p_232569_.m_231094_(), p_232569_.m_231095_(), p_232569_.m_231096_(), p_232569_.m_231097_());
        }
        return null;
    }

    public ResourceLocation m_95099_() {
        return this.f_95091_;
    }

    static class Node {
        final int f_95105_;
        final int f_95106_;
        private final int f_95107_;
        private final int f_95108_;
        @Nullable
        private Node f_95109_;
        @Nullable
        private Node f_95110_;
        private boolean f_95111_;

        Node(int p_95113_, int p_95114_, int p_95115_, int p_95116_) {
            this.f_95105_ = p_95113_;
            this.f_95106_ = p_95114_;
            this.f_95107_ = p_95115_;
            this.f_95108_ = p_95116_;
        }

        @Nullable
        Node m_232570_(SheetGlyphInfo p_232571_) {
            if (this.f_95109_ != null && this.f_95110_ != null) {
                Node $$1 = this.f_95109_.m_232570_(p_232571_);
                if ($$1 == null) {
                    $$1 = this.f_95110_.m_232570_(p_232571_);
                }
                return $$1;
            }
            if (this.f_95111_) {
                return null;
            }
            int $$2 = p_232571_.m_213962_();
            int $$3 = p_232571_.m_213961_();
            if ($$2 > this.f_95107_ || $$3 > this.f_95108_) {
                return null;
            }
            if ($$2 == this.f_95107_ && $$3 == this.f_95108_) {
                this.f_95111_ = true;
                return this;
            }
            int $$4 = this.f_95107_ - $$2;
            int $$5 = this.f_95108_ - $$3;
            if ($$4 > $$5) {
                this.f_95109_ = new Node(this.f_95105_, this.f_95106_, $$2, this.f_95108_);
                this.f_95110_ = new Node(this.f_95105_ + $$2 + 1, this.f_95106_, this.f_95107_ - $$2 - 1, this.f_95108_);
            } else {
                this.f_95109_ = new Node(this.f_95105_, this.f_95106_, this.f_95107_, $$3);
                this.f_95110_ = new Node(this.f_95105_, this.f_95106_ + $$3 + 1, this.f_95107_, this.f_95108_ - $$3 - 1);
            }
            return this.f_95109_.m_232570_(p_232571_);
        }
    }
}

