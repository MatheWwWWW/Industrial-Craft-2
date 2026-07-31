/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

public class LoadingOverlay
extends Overlay {
    static final ResourceLocation f_96160_ = new ResourceLocation("textures/gui/title/mojangstudios.png");
    private static final int f_169316_ = FastColor.ARGB32.m_13660_(255, 239, 50, 61);
    private static final int f_169317_ = FastColor.ARGB32.m_13660_(255, 0, 0, 0);
    private static final IntSupplier f_96161_ = () -> Minecraft.m_91087_().f_91066_.m_231838_().m_231551_() != false ? f_169317_ : f_169316_;
    private static final int f_169318_ = 240;
    private static final float f_169319_ = 60.0f;
    private static final int f_169320_ = 60;
    private static final int f_169321_ = 120;
    private static final float f_169322_ = 0.0625f;
    private static final float f_169323_ = 0.95f;
    public static final long f_169314_ = 1000L;
    public static final long f_169315_ = 500L;
    private final Minecraft f_96163_;
    private final ReloadInstance f_96164_;
    private final Consumer<Optional<Throwable>> f_96165_;
    private final boolean f_96166_;
    private float f_96167_;
    private long f_96168_ = -1L;
    private long f_96169_ = -1L;

    public LoadingOverlay(Minecraft p_96172_, ReloadInstance p_96173_, Consumer<Optional<Throwable>> p_96174_, boolean p_96175_) {
        this.f_96163_ = p_96172_;
        this.f_96164_ = p_96173_;
        this.f_96165_ = p_96174_;
        this.f_96166_ = p_96175_;
    }

    public static void m_96189_(Minecraft p_96190_) {
        p_96190_.m_91097_().m_118495_(f_96160_, new LogoTexture());
    }

    private static int m_169324_(int p_169325_, int p_169326_) {
        return p_169325_ & 0xFFFFFF | p_169326_ << 24;
    }

    @Override
    public void m_6305_(PoseStack p_96178_, int p_96179_, int p_96180_, float p_96181_) {
        float $$17;
        float $$8;
        int $$4 = this.f_96163_.m_91268_().m_85445_();
        int $$5 = this.f_96163_.m_91268_().m_85446_();
        long $$6 = Util.m_137550_();
        if (this.f_96166_ && this.f_96169_ == -1L) {
            this.f_96169_ = $$6;
        }
        float $$7 = this.f_96168_ > -1L ? (float)($$6 - this.f_96168_) / 1000.0f : -1.0f;
        float f = $$8 = this.f_96169_ > -1L ? (float)($$6 - this.f_96169_) / 500.0f : -1.0f;
        if ($$7 >= 1.0f) {
            if (this.f_96163_.f_91080_ != null) {
                this.f_96163_.f_91080_.m_6305_(p_96178_, 0, 0, p_96181_);
            }
            int $$9 = Mth.m_14167_((1.0f - Mth.m_14036_($$7 - 1.0f, 0.0f, 1.0f)) * 255.0f);
            LoadingOverlay.m_93172_(p_96178_, 0, 0, $$4, $$5, LoadingOverlay.m_169324_(f_96161_.getAsInt(), $$9));
            float $$10 = 1.0f - Mth.m_14036_($$7 - 1.0f, 0.0f, 1.0f);
        } else if (this.f_96166_) {
            if (this.f_96163_.f_91080_ != null && $$8 < 1.0f) {
                this.f_96163_.f_91080_.m_6305_(p_96178_, p_96179_, p_96180_, p_96181_);
            }
            int $$11 = Mth.m_14165_(Mth.m_14008_($$8, 0.15, 1.0) * 255.0);
            LoadingOverlay.m_93172_(p_96178_, 0, 0, $$4, $$5, LoadingOverlay.m_169324_(f_96161_.getAsInt(), $$11));
            float $$12 = Mth.m_14036_($$8, 0.0f, 1.0f);
        } else {
            int $$13 = f_96161_.getAsInt();
            float $$14 = (float)($$13 >> 16 & 0xFF) / 255.0f;
            float $$15 = (float)($$13 >> 8 & 0xFF) / 255.0f;
            float $$16 = (float)($$13 & 0xFF) / 255.0f;
            GlStateManager.m_84318_($$14, $$15, $$16, 1.0f);
            GlStateManager.m_84266_(16384, Minecraft.f_91002_);
            $$17 = 1.0f;
        }
        int $$18 = (int)((double)this.f_96163_.m_91268_().m_85445_() * 0.5);
        int $$19 = (int)((double)this.f_96163_.m_91268_().m_85446_() * 0.5);
        double $$20 = Math.min((double)this.f_96163_.m_91268_().m_85445_() * 0.75, (double)this.f_96163_.m_91268_().m_85446_()) * 0.25;
        int $$21 = (int)($$20 * 0.5);
        double $$22 = $$20 * 4.0;
        int $$23 = (int)($$22 * 0.5);
        RenderSystem.m_157456_(0, f_96160_);
        RenderSystem.m_69478_();
        RenderSystem.m_69403_(32774);
        RenderSystem.m_69405_(770, 1);
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, $$17);
        LoadingOverlay.m_93160_(p_96178_, $$18 - $$23, $$19 - $$21, $$23, (int)$$20, -0.0625f, 0.0f, 120, 60, 120, 120);
        LoadingOverlay.m_93160_(p_96178_, $$18, $$19 - $$21, $$23, (int)$$20, 0.0625f, 60.0f, 120, 60, 120, 120);
        RenderSystem.m_69453_();
        RenderSystem.m_69461_();
        int $$24 = (int)((double)this.f_96163_.m_91268_().m_85446_() * 0.8325);
        float $$25 = this.f_96164_.m_7750_();
        this.f_96167_ = Mth.m_14036_(this.f_96167_ * 0.95f + $$25 * 0.050000012f, 0.0f, 1.0f);
        if ($$7 < 1.0f) {
            this.m_96182_(p_96178_, $$4 / 2 - $$23, $$24 - 5, $$4 / 2 + $$23, $$24 + 5, 1.0f - Mth.m_14036_($$7, 0.0f, 1.0f));
        }
        if ($$7 >= 2.0f) {
            this.f_96163_.m_91150_(null);
        }
        if (this.f_96168_ == -1L && this.f_96164_.m_7746_() && (!this.f_96166_ || $$8 >= 2.0f)) {
            try {
                this.f_96164_.m_7748_();
                this.f_96165_.accept(Optional.empty());
            }
            catch (Throwable $$26) {
                this.f_96165_.accept(Optional.of($$26));
            }
            this.f_96168_ = Util.m_137550_();
            if (this.f_96163_.f_91080_ != null) {
                this.f_96163_.f_91080_.m_6575_(this.f_96163_, this.f_96163_.m_91268_().m_85445_(), this.f_96163_.m_91268_().m_85446_());
            }
        }
    }

    private void m_96182_(PoseStack p_96183_, int p_96184_, int p_96185_, int p_96186_, int p_96187_, float p_96188_) {
        int $$6 = Mth.m_14167_((float)(p_96186_ - p_96184_ - 2) * this.f_96167_);
        int $$7 = Math.round(p_96188_ * 255.0f);
        int $$8 = FastColor.ARGB32.m_13660_($$7, 255, 255, 255);
        LoadingOverlay.m_93172_(p_96183_, p_96184_ + 2, p_96185_ + 2, p_96184_ + $$6, p_96187_ - 2, $$8);
        LoadingOverlay.m_93172_(p_96183_, p_96184_ + 1, p_96185_, p_96186_ - 1, p_96185_ + 1, $$8);
        LoadingOverlay.m_93172_(p_96183_, p_96184_ + 1, p_96187_, p_96186_ - 1, p_96187_ - 1, $$8);
        LoadingOverlay.m_93172_(p_96183_, p_96184_, p_96185_, p_96184_ + 1, p_96187_, $$8);
        LoadingOverlay.m_93172_(p_96183_, p_96186_, p_96185_, p_96186_ - 1, p_96187_, $$8);
    }

    @Override
    public boolean m_7859_() {
        return true;
    }

    static class LogoTexture
    extends SimpleTexture {
        public LogoTexture() {
            super(f_96160_);
        }

        @Override
        protected SimpleTexture.TextureImage m_6335_(ResourceManager p_96194_) {
            SimpleTexture.TextureImage textureImage;
            block8: {
                Minecraft $$1 = Minecraft.m_91087_();
                VanillaPackResources $$2 = $$1.m_91100_().m_118555_();
                InputStream $$3 = $$2.m_8031_(PackType.CLIENT_RESOURCES, f_96160_);
                try {
                    textureImage = new SimpleTexture.TextureImage(new TextureMetadataSection(true, true), NativeImage.m_85058_($$3));
                    if ($$3 == null) break block8;
                }
                catch (Throwable throwable) {
                    try {
                        if ($$3 != null) {
                            try {
                                $$3.close();
                            }
                            catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    }
                    catch (IOException $$4) {
                        return new SimpleTexture.TextureImage($$4);
                    }
                }
                $$3.close();
            }
            return textureImage;
        }
    }
}

