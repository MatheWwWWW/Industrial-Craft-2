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
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

public class SimpleTexture
extends AbstractTexture {
    static final Logger f_118130_ = LogUtils.getLogger();
    protected final ResourceLocation f_118129_;

    public SimpleTexture(ResourceLocation p_118133_) {
        this.f_118129_ = p_118133_;
    }

    @Override
    public void m_6704_(ResourceManager p_118135_) throws IOException {
        boolean $$6;
        boolean $$5;
        TextureImage $$1 = this.m_6335_(p_118135_);
        $$1.m_118159_();
        TextureMetadataSection $$2 = $$1.m_118154_();
        if ($$2 != null) {
            boolean $$3 = $$2.m_119115_();
            boolean $$4 = $$2.m_119116_();
        } else {
            $$5 = false;
            $$6 = false;
        }
        NativeImage $$7 = $$1.m_118158_();
        if (!RenderSystem.m_69587_()) {
            RenderSystem.m_69879_(() -> this.m_118136_($$7, $$5, $$6));
        } else {
            this.m_118136_($$7, $$5, $$6);
        }
    }

    private void m_118136_(NativeImage p_118137_, boolean p_118138_, boolean p_118139_) {
        TextureUtil.m_85287_(this.m_117963_(), 0, p_118137_.m_84982_(), p_118137_.m_85084_());
        p_118137_.m_85013_(0, 0, 0, 0, 0, p_118137_.m_84982_(), p_118137_.m_85084_(), p_118138_, p_118139_, false, true);
    }

    protected TextureImage m_6335_(ResourceManager p_118140_) {
        return TextureImage.m_118155_(p_118140_, this.f_118129_);
    }

    protected static class TextureImage
    implements Closeable {
        @Nullable
        private final TextureMetadataSection f_118146_;
        @Nullable
        private final NativeImage f_118147_;
        @Nullable
        private final IOException f_118148_;

        public TextureImage(IOException p_118153_) {
            this.f_118148_ = p_118153_;
            this.f_118146_ = null;
            this.f_118147_ = null;
        }

        public TextureImage(@Nullable TextureMetadataSection p_118150_, NativeImage p_118151_) {
            this.f_118148_ = null;
            this.f_118146_ = p_118150_;
            this.f_118147_ = p_118151_;
        }

        /*
         * WARNING - void declaration
         */
        public static TextureImage m_118155_(ResourceManager p_118156_, ResourceLocation p_118157_) {
            try {
                void $$5;
                Resource $$2 = p_118156_.m_215593_(p_118157_);
                try (InputStream $$3 = $$2.m_215507_();){
                    NativeImage $$4 = NativeImage.m_85058_($$3);
                }
                TextureMetadataSection $$6 = null;
                try {
                    $$6 = $$2.m_215509_().m_214059_(TextureMetadataSection.f_119108_).orElse(null);
                }
                catch (RuntimeException $$7) {
                    f_118130_.warn("Failed reading metadata of: {}", (Object)p_118157_, (Object)$$7);
                }
                return new TextureImage($$6, (NativeImage)$$5);
            }
            catch (IOException $$8) {
                return new TextureImage($$8);
            }
        }

        @Nullable
        public TextureMetadataSection m_118154_() {
            return this.f_118146_;
        }

        public NativeImage m_118158_() throws IOException {
            if (this.f_118148_ != null) {
                throw this.f_118148_;
            }
            return this.f_118147_;
        }

        @Override
        public void close() {
            if (this.f_118147_ != null) {
                this.f_118147_.close();
            }
        }

        public void m_118159_() throws IOException {
            if (this.f_118148_ != null) {
                throw this.f_118148_;
            }
        }
    }
}

