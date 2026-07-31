/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.advancements;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.advancements.AdvancementTabType;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class AdvancementTab
extends GuiComponent {
    private final Minecraft f_97126_;
    private final AdvancementsScreen f_97127_;
    private final AdvancementTabType f_97128_;
    private final int f_97129_;
    private final Advancement f_97130_;
    private final DisplayInfo f_97131_;
    private final ItemStack f_97132_;
    private final Component f_97133_;
    private final AdvancementWidget f_97134_;
    private final Map<Advancement, AdvancementWidget> f_97135_ = Maps.newLinkedHashMap();
    private double f_97136_;
    private double f_97137_;
    private int f_97138_ = Integer.MAX_VALUE;
    private int f_97139_ = Integer.MAX_VALUE;
    private int f_97140_ = Integer.MIN_VALUE;
    private int f_97141_ = Integer.MIN_VALUE;
    private float f_97142_;
    private boolean f_97143_;

    public AdvancementTab(Minecraft p_97145_, AdvancementsScreen p_97146_, AdvancementTabType p_97147_, int p_97148_, Advancement p_97149_, DisplayInfo p_97150_) {
        this.f_97126_ = p_97145_;
        this.f_97127_ = p_97146_;
        this.f_97128_ = p_97147_;
        this.f_97129_ = p_97148_;
        this.f_97130_ = p_97149_;
        this.f_97131_ = p_97150_;
        this.f_97132_ = p_97150_.m_14990_();
        this.f_97133_ = p_97150_.m_14977_();
        this.f_97134_ = new AdvancementWidget(this, p_97145_, p_97149_, p_97150_);
        this.m_97175_(this.f_97134_, p_97149_);
    }

    public AdvancementTabType m_169538_() {
        return this.f_97128_;
    }

    public int m_169539_() {
        return this.f_97129_;
    }

    public Advancement m_97182_() {
        return this.f_97130_;
    }

    public Component m_97189_() {
        return this.f_97133_;
    }

    public DisplayInfo m_169540_() {
        return this.f_97131_;
    }

    public void m_97165_(PoseStack p_97166_, int p_97167_, int p_97168_, boolean p_97169_) {
        this.f_97128_.m_97225_(p_97166_, this, p_97167_, p_97168_, p_97169_, this.f_97129_);
    }

    public void m_97159_(int p_97160_, int p_97161_, ItemRenderer p_97162_) {
        this.f_97128_.m_97219_(p_97160_, p_97161_, this.f_97129_, p_97162_, this.f_97132_);
    }

    public void m_97163_(PoseStack p_97164_) {
        if (!this.f_97143_) {
            this.f_97136_ = 117 - (this.f_97140_ + this.f_97138_) / 2;
            this.f_97137_ = 56 - (this.f_97141_ + this.f_97139_) / 2;
            this.f_97143_ = true;
        }
        p_97164_.m_85836_();
        p_97164_.m_85837_(0.0, 0.0, 950.0);
        RenderSystem.m_69482_();
        RenderSystem.m_69444_(false, false, false, false);
        AdvancementTab.m_93172_(p_97164_, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.m_69444_(true, true, true, true);
        p_97164_.m_85837_(0.0, 0.0, -950.0);
        RenderSystem.m_69456_(518);
        AdvancementTab.m_93172_(p_97164_, 234, 113, 0, 0, -16777216);
        RenderSystem.m_69456_(515);
        ResourceLocation $$1 = this.f_97131_.m_14991_();
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        if ($$1 != null) {
            RenderSystem.m_157456_(0, $$1);
        } else {
            RenderSystem.m_157456_(0, TextureManager.f_118466_);
        }
        int $$2 = Mth.m_14107_(this.f_97136_);
        int $$3 = Mth.m_14107_(this.f_97137_);
        int $$4 = $$2 % 16;
        int $$5 = $$3 % 16;
        for (int $$6 = -1; $$6 <= 15; ++$$6) {
            for (int $$7 = -1; $$7 <= 8; ++$$7) {
                AdvancementTab.m_93133_(p_97164_, $$4 + 16 * $$6, $$5 + 16 * $$7, 0.0f, 0.0f, 16, 16, 16, 16);
            }
        }
        this.f_97134_.m_97298_(p_97164_, $$2, $$3, true);
        this.f_97134_.m_97298_(p_97164_, $$2, $$3, false);
        this.f_97134_.m_97266_(p_97164_, $$2, $$3);
        RenderSystem.m_69456_(518);
        p_97164_.m_85837_(0.0, 0.0, -950.0);
        RenderSystem.m_69444_(false, false, false, false);
        AdvancementTab.m_93172_(p_97164_, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.m_69444_(true, true, true, true);
        RenderSystem.m_69456_(515);
        p_97164_.m_85849_();
    }

    public void m_97183_(PoseStack p_97184_, int p_97185_, int p_97186_, int p_97187_, int p_97188_) {
        p_97184_.m_85836_();
        p_97184_.m_85837_(0.0, 0.0, -200.0);
        AdvancementTab.m_93172_(p_97184_, 0, 0, 234, 113, Mth.m_14143_(this.f_97142_ * 255.0f) << 24);
        boolean $$5 = false;
        int $$6 = Mth.m_14107_(this.f_97136_);
        int $$7 = Mth.m_14107_(this.f_97137_);
        if (p_97185_ > 0 && p_97185_ < 234 && p_97186_ > 0 && p_97186_ < 113) {
            for (AdvancementWidget $$8 : this.f_97135_.values()) {
                if (!$$8.m_97259_($$6, $$7, p_97185_, p_97186_)) continue;
                $$5 = true;
                $$8.m_97270_(p_97184_, $$6, $$7, this.f_97142_, p_97187_, p_97188_);
                break;
            }
        }
        p_97184_.m_85849_();
        this.f_97142_ = $$5 ? Mth.m_14036_(this.f_97142_ + 0.02f, 0.0f, 0.3f) : Mth.m_14036_(this.f_97142_ - 0.04f, 0.0f, 1.0f);
    }

    public boolean m_97154_(int p_97155_, int p_97156_, double p_97157_, double p_97158_) {
        return this.f_97128_.m_97213_(p_97155_, p_97156_, this.f_97129_, p_97157_, p_97158_);
    }

    @Nullable
    public static AdvancementTab m_97170_(Minecraft p_97171_, AdvancementsScreen p_97172_, int p_97173_, Advancement p_97174_) {
        if (p_97174_.m_138320_() == null) {
            return null;
        }
        for (AdvancementTabType $$4 : AdvancementTabType.values()) {
            if (p_97173_ >= $$4.m_97210_()) {
                p_97173_ -= $$4.m_97210_();
                continue;
            }
            return new AdvancementTab(p_97171_, p_97172_, $$4, p_97173_, p_97174_, p_97174_.m_138320_());
        }
        return null;
    }

    public void m_97151_(double p_97152_, double p_97153_) {
        if (this.f_97140_ - this.f_97138_ > 234) {
            this.f_97136_ = Mth.m_14008_(this.f_97136_ + p_97152_, -(this.f_97140_ - 234), 0.0);
        }
        if (this.f_97141_ - this.f_97139_ > 113) {
            this.f_97137_ = Mth.m_14008_(this.f_97137_ + p_97153_, -(this.f_97141_ - 113), 0.0);
        }
    }

    public void m_97178_(Advancement p_97179_) {
        if (p_97179_.m_138320_() == null) {
            return;
        }
        AdvancementWidget $$1 = new AdvancementWidget(this, this.f_97126_, p_97179_, p_97179_.m_138320_());
        this.m_97175_($$1, p_97179_);
    }

    private void m_97175_(AdvancementWidget p_97176_, Advancement p_97177_) {
        this.f_97135_.put(p_97177_, p_97176_);
        int $$2 = p_97176_.m_97315_();
        int $$3 = $$2 + 28;
        int $$4 = p_97176_.m_97314_();
        int $$5 = $$4 + 27;
        this.f_97138_ = Math.min(this.f_97138_, $$2);
        this.f_97140_ = Math.max(this.f_97140_, $$3);
        this.f_97139_ = Math.min(this.f_97139_, $$4);
        this.f_97141_ = Math.max(this.f_97141_, $$5);
        for (AdvancementWidget $$6 : this.f_97135_.values()) {
            $$6.m_97313_();
        }
    }

    @Nullable
    public AdvancementWidget m_97180_(Advancement p_97181_) {
        return this.f_97135_.get(p_97181_);
    }

    public AdvancementsScreen m_97190_() {
        return this.f_97127_;
    }
}

