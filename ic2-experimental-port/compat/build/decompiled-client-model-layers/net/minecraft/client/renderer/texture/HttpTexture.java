/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.io.FileUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

public class HttpTexture
extends SimpleTexture {
    private static final Logger f_117993_ = LogUtils.getLogger();
    private static final int f_181889_ = 64;
    private static final int f_181890_ = 64;
    private static final int f_181891_ = 32;
    @Nullable
    private final File f_117994_;
    private final String f_117995_;
    private final boolean f_117996_;
    @Nullable
    private final Runnable f_117997_;
    @Nullable
    private CompletableFuture<?> f_117998_;
    private boolean f_117999_;

    public HttpTexture(@Nullable File p_118002_, String p_118003_, ResourceLocation p_118004_, boolean p_118005_, @Nullable Runnable p_118006_) {
        super(p_118004_);
        this.f_117994_ = p_118002_;
        this.f_117995_ = p_118003_;
        this.f_117996_ = p_118005_;
        this.f_117997_ = p_118006_;
    }

    private void m_118010_(NativeImage p_118011_) {
        if (this.f_117997_ != null) {
            this.f_117997_.run();
        }
        Minecraft.m_91087_().execute(() -> {
            this.f_117999_ = true;
            if (!RenderSystem.m_69586_()) {
                RenderSystem.m_69879_(() -> this.m_118020_(p_118011_));
            } else {
                this.m_118020_(p_118011_);
            }
        });
    }

    private void m_118020_(NativeImage p_118021_) {
        TextureUtil.m_85283_(this.m_117963_(), p_118021_.m_84982_(), p_118021_.m_85084_());
        p_118021_.m_85040_(0, 0, 0, true);
    }

    @Override
    public void m_6704_(ResourceManager p_118009_) throws IOException {
        NativeImage $$3;
        Minecraft.m_91087_().execute(() -> {
            if (!this.f_117999_) {
                try {
                    super.m_6704_(p_118009_);
                }
                catch (IOException $$1) {
                    f_117993_.warn("Failed to load texture: {}", (Object)this.f_118129_, (Object)$$1);
                }
                this.f_117999_ = true;
            }
        });
        if (this.f_117998_ != null) {
            return;
        }
        if (this.f_117994_ != null && this.f_117994_.isFile()) {
            f_117993_.debug("Loading http texture from local cache ({})", (Object)this.f_117994_);
            FileInputStream $$1 = new FileInputStream(this.f_117994_);
            NativeImage $$2 = this.m_118018_($$1);
        } else {
            $$3 = null;
        }
        if ($$3 != null) {
            this.m_118010_($$3);
            return;
        }
        this.f_117998_ = CompletableFuture.runAsync(() -> {
            HttpURLConnection $$0 = null;
            f_117993_.debug("Downloading http texture from {} to {}", (Object)this.f_117995_, (Object)this.f_117994_);
            try {
                InputStream $$2;
                $$0 = (HttpURLConnection)new URL(this.f_117995_).openConnection(Minecraft.m_91087_().m_91096_());
                $$0.setDoInput(true);
                $$0.setDoOutput(false);
                $$0.connect();
                if ($$0.getResponseCode() / 100 != 2) {
                    return;
                }
                if (this.f_117994_ != null) {
                    FileUtils.copyInputStreamToFile((InputStream)$$0.getInputStream(), (File)this.f_117994_);
                    FileInputStream $$1 = new FileInputStream(this.f_117994_);
                } else {
                    $$2 = $$0.getInputStream();
                }
                Minecraft.m_91087_().execute(() -> {
                    NativeImage $$1 = this.m_118018_($$2);
                    if ($$1 != null) {
                        this.m_118010_($$1);
                    }
                });
            }
            catch (Exception $$3) {
                f_117993_.error("Couldn't download http texture", (Throwable)$$3);
            }
            finally {
                if ($$0 != null) {
                    $$0.disconnect();
                }
            }
        }, Util.m_183991_());
    }

    @Nullable
    private NativeImage m_118018_(InputStream p_118019_) {
        NativeImage $$1 = null;
        try {
            $$1 = NativeImage.m_85058_(p_118019_);
            if (this.f_117996_) {
                $$1 = this.m_118032_($$1);
            }
        }
        catch (Exception $$2) {
            f_117993_.warn("Error while loading the skin texture", (Throwable)$$2);
        }
        return $$1;
    }

    @Nullable
    private NativeImage m_118032_(NativeImage p_118033_) {
        boolean $$3;
        int $$1 = p_118033_.m_85084_();
        int $$2 = p_118033_.m_84982_();
        if ($$2 != 64 || $$1 != 32 && $$1 != 64) {
            p_118033_.close();
            f_117993_.warn("Discarding incorrectly sized ({}x{}) skin texture from {}", new Object[]{$$2, $$1, this.f_117995_});
            return null;
        }
        boolean bl = $$3 = $$1 == 32;
        if ($$3) {
            NativeImage $$4 = new NativeImage(64, 64, true);
            $$4.m_85054_(p_118033_);
            p_118033_.close();
            p_118033_ = $$4;
            p_118033_.m_84997_(0, 32, 64, 32, 0);
            p_118033_.m_85025_(4, 16, 16, 32, 4, 4, true, false);
            p_118033_.m_85025_(8, 16, 16, 32, 4, 4, true, false);
            p_118033_.m_85025_(0, 20, 24, 32, 4, 12, true, false);
            p_118033_.m_85025_(4, 20, 16, 32, 4, 12, true, false);
            p_118033_.m_85025_(8, 20, 8, 32, 4, 12, true, false);
            p_118033_.m_85025_(12, 20, 16, 32, 4, 12, true, false);
            p_118033_.m_85025_(44, 16, -8, 32, 4, 4, true, false);
            p_118033_.m_85025_(48, 16, -8, 32, 4, 4, true, false);
            p_118033_.m_85025_(40, 20, 0, 32, 4, 12, true, false);
            p_118033_.m_85025_(44, 20, -8, 32, 4, 12, true, false);
            p_118033_.m_85025_(48, 20, -16, 32, 4, 12, true, false);
            p_118033_.m_85025_(52, 20, -8, 32, 4, 12, true, false);
        }
        HttpTexture.m_118022_(p_118033_, 0, 0, 32, 16);
        if ($$3) {
            HttpTexture.m_118012_(p_118033_, 32, 0, 64, 32);
        }
        HttpTexture.m_118022_(p_118033_, 0, 16, 64, 32);
        HttpTexture.m_118022_(p_118033_, 16, 48, 48, 64);
        return p_118033_;
    }

    private static void m_118012_(NativeImage p_118013_, int p_118014_, int p_118015_, int p_118016_, int p_118017_) {
        for (int $$5 = p_118014_; $$5 < p_118016_; ++$$5) {
            for (int $$6 = p_118015_; $$6 < p_118017_; ++$$6) {
                int $$7 = p_118013_.m_84985_($$5, $$6);
                if (($$7 >> 24 & 0xFF) >= 128) continue;
                return;
            }
        }
        for (int $$8 = p_118014_; $$8 < p_118016_; ++$$8) {
            for (int $$9 = p_118015_; $$9 < p_118017_; ++$$9) {
                p_118013_.m_84988_($$8, $$9, p_118013_.m_84985_($$8, $$9) & 0xFFFFFF);
            }
        }
    }

    private static void m_118022_(NativeImage p_118023_, int p_118024_, int p_118025_, int p_118026_, int p_118027_) {
        for (int $$5 = p_118024_; $$5 < p_118026_; ++$$5) {
            for (int $$6 = p_118025_; $$6 < p_118027_; ++$$6) {
                p_118023_.m_84988_($$5, $$6, p_118023_.m_84985_($$5, $$6) | 0xFF000000);
            }
        }
    }
}

