/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Suppliers
 *  com.google.common.collect.Maps
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture$Type
 *  com.mojang.logging.LogUtils
 *  com.mojang.util.UUIDTypeAdapter
 *  javax.annotation.Nullable
 *  org.apache.commons.codec.binary.Base64
 *  org.apache.commons.io.IOUtils
 *  org.lwjgl.BufferUtils
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.util;

import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.util.RealmsUtil;
import com.mojang.realmsclient.util.SkinProcessor;
import com.mojang.util.UUIDTypeAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.slf4j.Logger;

public class RealmsTextureManager {
    private static final Map<String, RealmsTexture> f_90178_ = Maps.newHashMap();
    static final Map<String, Boolean> f_90179_ = Maps.newHashMap();
    static final Map<String, String> f_90180_ = Maps.newHashMap();
    static final Logger f_90181_ = LogUtils.getLogger();
    private static final ResourceLocation f_90182_ = new ResourceLocation("textures/gui/presets/isles.png");

    public static void m_90190_(String p_90191_, @Nullable String p_90192_) {
        if (p_90192_ == null) {
            RenderSystem.m_157456_(0, f_90182_);
            return;
        }
        int $$2 = RealmsTextureManager.m_90196_(p_90191_, p_90192_);
        RenderSystem.m_157453_(0, $$2);
    }

    public static void m_90187_(String p_90188_, Runnable p_90189_) {
        RealmsTextureManager.m_90185_(p_90188_);
        p_90189_.run();
    }

    private static void m_90193_(UUID p_90194_) {
        RenderSystem.m_157456_(0, DefaultPlayerSkin.m_118627_(p_90194_));
    }

    private static void m_90185_(final String p_90186_) {
        UUID $$1 = UUIDTypeAdapter.fromString((String)p_90186_);
        if (f_90178_.containsKey(p_90186_)) {
            int $$2 = RealmsTextureManager.f_90178_.get((Object)p_90186_).f_90206_;
            RenderSystem.m_157453_(0, $$2);
            return;
        }
        if (f_90179_.containsKey(p_90186_)) {
            if (!f_90179_.get(p_90186_).booleanValue()) {
                RealmsTextureManager.m_90193_($$1);
            } else if (f_90180_.containsKey(p_90186_)) {
                int $$3 = RealmsTextureManager.m_90196_(p_90186_, f_90180_.get(p_90186_));
                RenderSystem.m_157453_(0, $$3);
            } else {
                RealmsTextureManager.m_90193_($$1);
            }
            return;
        }
        f_90179_.put(p_90186_, false);
        RealmsTextureManager.m_90193_($$1);
        Thread $$4 = new Thread("Realms Texture Downloader"){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                block17: {
                    block16: {
                        ByteArrayOutputStream $$7;
                        BufferedImage $$6;
                        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> $$0 = RealmsUtil.m_90225_(p_90186_);
                        if (!$$0.containsKey(MinecraftProfileTexture.Type.SKIN)) break block16;
                        MinecraftProfileTexture $$1 = $$0.get(MinecraftProfileTexture.Type.SKIN);
                        String $$2 = $$1.getUrl();
                        HttpURLConnection $$3 = null;
                        f_90181_.debug("Downloading http texture from {}", (Object)$$2);
                        try {
                            $$3 = (HttpURLConnection)new URL($$2).openConnection(Minecraft.m_91087_().m_91096_());
                            $$3.setDoInput(true);
                            $$3.setDoOutput(false);
                            $$3.connect();
                            if ($$3.getResponseCode() / 100 != 2) {
                                f_90179_.remove(p_90186_);
                                return;
                            }
                            try {
                                BufferedImage $$4 = ImageIO.read($$3.getInputStream());
                            }
                            catch (Exception $$5) {
                                f_90179_.remove(p_90186_);
                                if ($$3 != null) {
                                    $$3.disconnect();
                                }
                                return;
                            }
                            finally {
                                IOUtils.closeQuietly((InputStream)$$3.getInputStream());
                            }
                            $$6 = new SkinProcessor().m_90241_($$6);
                            $$7 = new ByteArrayOutputStream();
                        }
                        catch (Exception $$8) {
                            f_90181_.error("Couldn't download http texture", (Throwable)$$8);
                            f_90179_.remove(p_90186_);
                        }
                        finally {
                            if ($$3 != null) {
                                $$3.disconnect();
                            }
                        }
                        ImageIO.write((RenderedImage)$$6, "png", $$7);
                        f_90180_.put(p_90186_, new Base64().encodeToString($$7.toByteArray()));
                        f_90179_.put(p_90186_, true);
                        break block17;
                    }
                    f_90179_.put(p_90186_, true);
                }
            }
        };
        $$4.setDaemon(true);
        $$4.start();
    }

    private static int m_90196_(String p_90197_, String p_90198_) {
        int $$4;
        RealmsTexture $$2 = f_90178_.get(p_90197_);
        if ($$2 != null && $$2.f_90205_.equals(p_90198_)) {
            return $$2.f_90206_;
        }
        if ($$2 != null) {
            int $$3 = $$2.f_90206_;
        } else {
            $$4 = GlStateManager.m_84111_();
        }
        TextureData $$5 = TextureData.m_193528_(p_90198_);
        RenderSystem.m_69388_(33984);
        RenderSystem.m_157184_($$4);
        TextureUtil.m_85305_($$5.f_193520_, $$5.f_193518_, $$5.f_193519_);
        f_90178_.put(p_90197_, new RealmsTexture(p_90198_, $$4));
        return $$4;
    }

    public static class RealmsTexture {
        final String f_90205_;
        final int f_90206_;

        public RealmsTexture(String p_90208_, int p_90209_) {
            this.f_90205_ = p_90208_;
            this.f_90206_ = p_90209_;
        }
    }

    static class TextureData {
        final int f_193518_;
        final int f_193519_;
        final IntBuffer f_193520_;
        private static final Supplier<TextureData> f_193521_ = Suppliers.memoize(() -> {
            int $$0 = 16;
            int $$1 = 16;
            IntBuffer $$2 = BufferUtils.createIntBuffer((int)256);
            int $$3 = -16777216;
            int $$4 = -524040;
            for (int $$5 = 0; $$5 < 16; ++$$5) {
                for (int $$6 = 0; $$6 < 16; ++$$6) {
                    if ($$5 < 8 ^ $$6 < 8) {
                        $$2.put($$6 + $$5 * 16, -524040);
                        continue;
                    }
                    $$2.put($$6 + $$5 * 16, -16777216);
                }
            }
            return new TextureData(16, 16, $$2);
        });

        private TextureData(int p_193524_, int p_193525_, IntBuffer p_193526_) {
            this.f_193518_ = p_193524_;
            this.f_193519_ = p_193525_;
            this.f_193520_ = p_193526_;
        }

        public static TextureData m_193528_(String p_193529_) {
            try {
                ByteArrayInputStream $$1 = new ByteArrayInputStream(new Base64().decode(p_193529_));
                BufferedImage $$2 = ImageIO.read($$1);
                if ($$2 != null) {
                    int $$3 = $$2.getWidth();
                    int $$4 = $$2.getHeight();
                    int[] $$5 = new int[$$3 * $$4];
                    $$2.getRGB(0, 0, $$3, $$4, $$5, 0, $$3);
                    IntBuffer $$6 = BufferUtils.createIntBuffer((int)($$3 * $$4));
                    $$6.put($$5);
                    $$6.flip();
                    return new TextureData($$3, $$4, $$6);
                }
                f_90181_.warn("Unknown image format: {}", (Object)p_193529_);
            }
            catch (IOException $$7) {
                f_90181_.warn("Failed to load world image: {}", (Object)p_193529_, (Object)$$7);
            }
            return f_193521_.get();
        }
    }
}

