/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import org.slf4j.Logger;

public class WinScreen
extends Screen {
    private static final Logger f_96863_ = LogUtils.getLogger();
    private static final ResourceLocation f_96864_ = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation f_96865_ = new ResourceLocation("textures/gui/title/edition.png");
    private static final ResourceLocation f_96866_ = new ResourceLocation("textures/misc/vignette.png");
    private static final Component f_169463_ = Component.m_237113_("============").m_130940_(ChatFormatting.WHITE);
    private static final String f_169464_ = "           ";
    private static final String f_96867_ = "" + ChatFormatting.WHITE + ChatFormatting.OBFUSCATED + ChatFormatting.GREEN + ChatFormatting.AQUA;
    private static final int f_169465_ = 274;
    private static final float f_169466_ = 5.0f;
    private static final float f_181393_ = 15.0f;
    private final boolean f_96868_;
    private final Runnable f_96869_;
    private float f_169467_;
    private List<FormattedCharSequence> f_96871_;
    private IntSet f_96872_;
    private int f_96873_;
    private boolean f_181391_;
    private final IntSet f_181392_ = new IntOpenHashSet();
    private float f_96874_;
    private final float f_169462_;

    public WinScreen(boolean p_96877_, Runnable p_96878_) {
        super(GameNarrator.f_93310_);
        this.f_96868_ = p_96877_;
        this.f_96869_ = p_96878_;
        this.f_169462_ = !p_96877_ ? 0.75f : 0.5f;
        this.f_96874_ = this.f_169462_;
    }

    private float m_181399_() {
        if (this.f_181391_) {
            return this.f_169462_ * (5.0f + (float)this.f_181392_.size() * 15.0f);
        }
        return this.f_169462_;
    }

    @Override
    public void m_86600_() {
        this.f_96541_.m_91397_().m_120183_();
        this.f_96541_.m_91106_().m_120389_(false);
        float $$0 = this.f_96873_ + this.f_96544_ + this.f_96544_ + 24;
        if (this.f_169467_ > $$0) {
            this.m_96895_();
        }
    }

    @Override
    public boolean m_7933_(int p_169469_, int p_169470_, int p_169471_) {
        if (p_169469_ == 341 || p_169469_ == 345) {
            this.f_181392_.add(p_169469_);
        } else if (p_169469_ == 32) {
            this.f_181391_ = true;
        }
        this.f_96874_ = this.m_181399_();
        return super.m_7933_(p_169469_, p_169470_, p_169471_);
    }

    @Override
    public boolean m_7920_(int p_169476_, int p_169477_, int p_169478_) {
        if (p_169476_ == 32) {
            this.f_181391_ = false;
        } else if (p_169476_ == 341 || p_169476_ == 345) {
            this.f_181392_.remove(p_169476_);
        }
        this.f_96874_ = this.m_181399_();
        return super.m_7920_(p_169476_, p_169477_, p_169478_);
    }

    @Override
    public void m_7379_() {
        this.m_96895_();
    }

    private void m_96895_() {
        this.f_96869_.run();
        this.f_96541_.m_91152_(null);
    }

    @Override
    protected void m_7856_() {
        if (this.f_96871_ != null) {
            return;
        }
        this.f_96871_ = Lists.newArrayList();
        this.f_96872_ = new IntOpenHashSet();
        if (this.f_96868_) {
            this.m_197398_("texts/end.txt", this::m_232817_);
        }
        this.m_197398_("texts/credits.json", this::m_232819_);
        if (this.f_96868_) {
            this.m_197398_("texts/postcredits.txt", this::m_232817_);
        }
        this.f_96873_ = this.f_96871_.size() * 12;
    }

    private void m_197398_(String p_197399_, CreditsReader p_197400_) {
        try (BufferedReader $$2 = this.f_96541_.m_91098_().m_215597_(new ResourceLocation(p_197399_));){
            p_197400_.m_232821_($$2);
        }
        catch (Exception $$3) {
            f_96863_.error("Couldn't load credits", (Throwable)$$3);
        }
    }

    private void m_232817_(Reader p_232818_) throws IOException {
        Object $$3;
        BufferedReader $$1 = new BufferedReader(p_232818_);
        RandomSource $$2 = RandomSource.m_216335_(8124371L);
        while (($$3 = $$1.readLine()) != null) {
            int $$4;
            $$3 = ((String)$$3).replaceAll("PLAYERNAME", this.f_96541_.m_91094_().m_92546_());
            while (($$4 = ((String)$$3).indexOf(f_96867_)) != -1) {
                String $$5 = ((String)$$3).substring(0, $$4);
                String $$6 = ((String)$$3).substring($$4 + f_96867_.length());
                $$3 = $$5 + ChatFormatting.WHITE + ChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, $$2.m_188503_(4) + 3) + $$6;
            }
            this.m_181397_((String)$$3);
            this.m_169482_();
        }
        for (int $$7 = 0; $$7 < 8; ++$$7) {
            this.m_169482_();
        }
    }

    private void m_232819_(Reader p_232820_) {
        JsonArray $$1 = GsonHelper.m_144765_(p_232820_);
        for (JsonElement $$2 : $$1) {
            JsonObject $$3 = $$2.getAsJsonObject();
            String $$4 = $$3.get("section").getAsString();
            this.m_169472_(f_169463_, true);
            this.m_169472_(Component.m_237113_($$4).m_130940_(ChatFormatting.YELLOW), true);
            this.m_169472_(f_169463_, true);
            this.m_169482_();
            this.m_169482_();
            JsonArray $$5 = $$3.getAsJsonArray("titles");
            for (JsonElement $$6 : $$5) {
                JsonObject $$7 = $$6.getAsJsonObject();
                String $$8 = $$7.get("title").getAsString();
                JsonArray $$9 = $$7.getAsJsonArray("names");
                this.m_169472_(Component.m_237113_($$8).m_130940_(ChatFormatting.GRAY), false);
                for (JsonElement $$10 : $$9) {
                    String $$11 = $$10.getAsString();
                    this.m_169472_(Component.m_237113_(f_169464_).m_130946_($$11).m_130940_(ChatFormatting.WHITE), false);
                }
                this.m_169482_();
                this.m_169482_();
            }
        }
    }

    private void m_169482_() {
        this.f_96871_.add(FormattedCharSequence.f_13691_);
    }

    private void m_181397_(String p_181398_) {
        this.f_96871_.addAll(this.f_96541_.f_91062_.m_92923_(Component.m_237113_(p_181398_), 274));
    }

    private void m_169472_(Component p_169473_, boolean p_169474_) {
        if (p_169474_) {
            this.f_96872_.add(this.f_96871_.size());
        }
        this.f_96871_.add(p_169473_.m_7532_());
    }

    private void m_169483_() {
        RenderSystem.m_157427_(GameRenderer::m_172820_);
        RenderSystem.m_157456_(0, GuiComponent.f_93096_);
        int $$0 = this.f_96543_;
        float $$1 = -this.f_169467_ * 0.5f;
        float $$2 = (float)this.f_96544_ - 0.5f * this.f_169467_;
        float $$3 = 0.015625f;
        float $$4 = this.f_169467_ / this.f_169462_;
        float $$5 = $$4 * 0.02f;
        float $$6 = (float)(this.f_96873_ + this.f_96544_ + this.f_96544_ + 24) / this.f_169462_;
        float $$7 = ($$6 - 20.0f - $$4) * 0.005f;
        if ($$7 < $$5) {
            $$5 = $$7;
        }
        if ($$5 > 1.0f) {
            $$5 = 1.0f;
        }
        $$5 *= $$5;
        $$5 = $$5 * 96.0f / 255.0f;
        Tesselator $$8 = Tesselator.m_85913_();
        BufferBuilder $$9 = $$8.m_85915_();
        $$9.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
        $$9.m_5483_(0.0, this.f_96544_, this.m_93252_()).m_7421_(0.0f, $$1 * 0.015625f).m_85950_($$5, $$5, $$5, 1.0f).m_5752_();
        $$9.m_5483_($$0, this.f_96544_, this.m_93252_()).m_7421_((float)$$0 * 0.015625f, $$1 * 0.015625f).m_85950_($$5, $$5, $$5, 1.0f).m_5752_();
        $$9.m_5483_($$0, 0.0, this.m_93252_()).m_7421_((float)$$0 * 0.015625f, $$2 * 0.015625f).m_85950_($$5, $$5, $$5, 1.0f).m_5752_();
        $$9.m_5483_(0.0, 0.0, this.m_93252_()).m_7421_(0.0f, $$2 * 0.015625f).m_85950_($$5, $$5, $$5, 1.0f).m_5752_();
        $$8.m_85914_();
    }

    @Override
    public void m_6305_(PoseStack p_96884_, int p_96885_, int p_96886_, float p_96887_) {
        this.f_169467_ += p_96887_ * this.f_96874_;
        this.m_169483_();
        int $$4 = this.f_96543_ / 2 - 137;
        int $$5 = this.f_96544_ + 50;
        float $$6 = -this.f_169467_;
        p_96884_.m_85836_();
        p_96884_.m_85837_(0.0, $$6, 0.0);
        RenderSystem.m_157456_(0, f_96864_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_69478_();
        this.m_93101_($$4, $$5, (p_96890_, p_96891_) -> {
            this.m_93228_(p_96884_, p_96890_ + 0, (int)p_96891_, 0, 0, 155, 44);
            this.m_93228_(p_96884_, p_96890_ + 155, (int)p_96891_, 0, 45, 155, 44);
        });
        RenderSystem.m_69461_();
        RenderSystem.m_157456_(0, f_96865_);
        WinScreen.m_93133_(p_96884_, $$4 + 88, $$5 + 37, 0.0f, 0.0f, 98, 14, 128, 16);
        int $$7 = $$5 + 100;
        for (int $$8 = 0; $$8 < this.f_96871_.size(); ++$$8) {
            float $$9;
            if ($$8 == this.f_96871_.size() - 1 && ($$9 = (float)$$7 + $$6 - (float)(this.f_96544_ / 2 - 6)) < 0.0f) {
                p_96884_.m_85837_(0.0, -$$9, 0.0);
            }
            if ((float)$$7 + $$6 + 12.0f + 8.0f > 0.0f && (float)$$7 + $$6 < (float)this.f_96544_) {
                FormattedCharSequence $$10 = this.f_96871_.get($$8);
                if (this.f_96872_.contains($$8)) {
                    this.f_96547_.m_92744_(p_96884_, $$10, $$4 + (274 - this.f_96547_.m_92724_($$10)) / 2, $$7, 0xFFFFFF);
                } else {
                    this.f_96547_.m_92744_(p_96884_, $$10, $$4, $$7, 0xFFFFFF);
                }
            }
            $$7 += 12;
        }
        p_96884_.m_85849_();
        RenderSystem.m_157427_(GameRenderer::m_172820_);
        RenderSystem.m_157456_(0, f_96866_);
        RenderSystem.m_69478_();
        RenderSystem.m_69408_(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR);
        int $$11 = this.f_96543_;
        int $$12 = this.f_96544_;
        Tesselator $$13 = Tesselator.m_85913_();
        BufferBuilder $$14 = $$13.m_85915_();
        $$14.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
        $$14.m_5483_(0.0, $$12, this.m_93252_()).m_7421_(0.0f, 1.0f).m_85950_(1.0f, 1.0f, 1.0f, 1.0f).m_5752_();
        $$14.m_5483_($$11, $$12, this.m_93252_()).m_7421_(1.0f, 1.0f).m_85950_(1.0f, 1.0f, 1.0f, 1.0f).m_5752_();
        $$14.m_5483_($$11, 0.0, this.m_93252_()).m_7421_(1.0f, 0.0f).m_85950_(1.0f, 1.0f, 1.0f, 1.0f).m_5752_();
        $$14.m_5483_(0.0, 0.0, this.m_93252_()).m_7421_(0.0f, 0.0f).m_85950_(1.0f, 1.0f, 1.0f, 1.0f).m_5752_();
        $$13.m_85914_();
        RenderSystem.m_69461_();
        super.m_6305_(p_96884_, p_96885_, p_96886_, p_96887_);
    }

    @FunctionalInterface
    static interface CreditsReader {
        public void m_232821_(Reader var1) throws IOException;
    }
}

