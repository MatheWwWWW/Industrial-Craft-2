/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.advancements;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.advancements.AdvancementTab;
import net.minecraft.client.gui.screens.advancements.AdvancementWidgetType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public class AdvancementWidget
extends GuiComponent {
    private static final ResourceLocation f_97239_ = new ResourceLocation("textures/gui/advancements/widgets.png");
    private static final int f_169542_ = 26;
    private static final int f_169543_ = 0;
    private static final int f_169544_ = 200;
    private static final int f_169545_ = 26;
    private static final int f_169546_ = 8;
    private static final int f_169547_ = 5;
    private static final int f_169548_ = 26;
    private static final int f_169549_ = 3;
    private static final int f_169550_ = 5;
    private static final int f_169551_ = 32;
    private static final int f_169552_ = 9;
    private static final int f_169553_ = 163;
    private static final int[] f_97240_ = new int[]{0, 10, -10, 25, -25};
    private final AdvancementTab f_97241_;
    private final Advancement f_97242_;
    private final DisplayInfo f_97243_;
    private final FormattedCharSequence f_97244_;
    private final int f_97245_;
    private final List<FormattedCharSequence> f_97246_;
    private final Minecraft f_97247_;
    @Nullable
    private AdvancementWidget f_97248_;
    private final List<AdvancementWidget> f_97249_ = Lists.newArrayList();
    @Nullable
    private AdvancementProgress f_97250_;
    private final int f_97251_;
    private final int f_97252_;

    public AdvancementWidget(AdvancementTab p_97255_, Minecraft p_97256_, Advancement p_97257_, DisplayInfo p_97258_) {
        this.f_97241_ = p_97255_;
        this.f_97242_ = p_97257_;
        this.f_97243_ = p_97258_;
        this.f_97247_ = p_97256_;
        this.f_97244_ = Language.m_128107_().m_5536_(p_97256_.f_91062_.m_92854_(p_97258_.m_14977_(), 163));
        this.f_97251_ = Mth.m_14143_(p_97258_.m_14993_() * 28.0f);
        this.f_97252_ = Mth.m_14143_(p_97258_.m_14994_() * 27.0f);
        int $$4 = p_97257_.m_138326_();
        int $$5 = String.valueOf($$4).length();
        int $$6 = $$4 > 1 ? p_97256_.f_91062_.m_92895_("  ") + p_97256_.f_91062_.m_92895_("0") * $$5 * 2 + p_97256_.f_91062_.m_92895_("/") : 0;
        int $$7 = 29 + p_97256_.f_91062_.m_92724_(this.f_97244_) + $$6;
        this.f_97246_ = Language.m_128107_().m_128112_(this.m_97308_(ComponentUtils.m_130750_(p_97258_.m_14985_().m_6881_(), Style.f_131099_.m_131140_(p_97258_.m_14992_().m_15552_())), $$7));
        for (FormattedCharSequence $$8 : this.f_97246_) {
            $$7 = Math.max($$7, p_97256_.f_91062_.m_92724_($$8));
        }
        this.f_97245_ = $$7 + 3 + 5;
    }

    private static float m_97303_(StringSplitter p_97304_, List<FormattedText> p_97305_) {
        return (float)p_97305_.stream().mapToDouble(p_97304_::m_92384_).max().orElse(0.0);
    }

    private List<FormattedText> m_97308_(Component p_97309_, int p_97310_) {
        StringSplitter $$2 = this.f_97247_.f_91062_.m_92865_();
        List<FormattedText> $$3 = null;
        float $$4 = Float.MAX_VALUE;
        for (int $$5 : f_97240_) {
            List<FormattedText> $$6 = $$2.m_92414_(p_97309_, p_97310_ - $$5, Style.f_131099_);
            float $$7 = Math.abs(AdvancementWidget.m_97303_($$2, $$6) - (float)p_97310_);
            if ($$7 <= 10.0f) {
                return $$6;
            }
            if (!($$7 < $$4)) continue;
            $$4 = $$7;
            $$3 = $$6;
        }
        return $$3;
    }

    @Nullable
    private AdvancementWidget m_97311_(Advancement p_97312_) {
        while ((p_97312_ = p_97312_.m_138319_()) != null && p_97312_.m_138320_() == null) {
        }
        if (p_97312_ == null || p_97312_.m_138320_() == null) {
            return null;
        }
        return this.f_97241_.m_97180_(p_97312_);
    }

    public void m_97298_(PoseStack p_97299_, int p_97300_, int p_97301_, boolean p_97302_) {
        if (this.f_97248_ != null) {
            int $$9;
            int $$4 = p_97300_ + this.f_97248_.f_97251_ + 13;
            int $$5 = p_97300_ + this.f_97248_.f_97251_ + 26 + 4;
            int $$6 = p_97301_ + this.f_97248_.f_97252_ + 13;
            int $$7 = p_97300_ + this.f_97251_ + 13;
            int $$8 = p_97301_ + this.f_97252_ + 13;
            int n = $$9 = p_97302_ ? -16777216 : -1;
            if (p_97302_) {
                this.m_93154_(p_97299_, $$5, $$4, $$6 - 1, $$9);
                this.m_93154_(p_97299_, $$5 + 1, $$4, $$6, $$9);
                this.m_93154_(p_97299_, $$5, $$4, $$6 + 1, $$9);
                this.m_93154_(p_97299_, $$7, $$5 - 1, $$8 - 1, $$9);
                this.m_93154_(p_97299_, $$7, $$5 - 1, $$8, $$9);
                this.m_93154_(p_97299_, $$7, $$5 - 1, $$8 + 1, $$9);
                this.m_93222_(p_97299_, $$5 - 1, $$8, $$6, $$9);
                this.m_93222_(p_97299_, $$5 + 1, $$8, $$6, $$9);
            } else {
                this.m_93154_(p_97299_, $$5, $$4, $$6, $$9);
                this.m_93154_(p_97299_, $$7, $$5, $$8, $$9);
                this.m_93222_(p_97299_, $$5, $$8, $$6, $$9);
            }
        }
        for (AdvancementWidget $$10 : this.f_97249_) {
            $$10.m_97298_(p_97299_, p_97300_, p_97301_, p_97302_);
        }
    }

    public void m_97266_(PoseStack p_97267_, int p_97268_, int p_97269_) {
        if (!this.f_97243_.m_14997_() || this.f_97250_ != null && this.f_97250_.m_8193_()) {
            AdvancementWidgetType $$5;
            float $$3;
            float f = $$3 = this.f_97250_ == null ? 0.0f : this.f_97250_.m_8213_();
            if ($$3 >= 1.0f) {
                AdvancementWidgetType $$4 = AdvancementWidgetType.OBTAINED;
            } else {
                $$5 = AdvancementWidgetType.UNOBTAINED;
            }
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157456_(0, f_97239_);
            this.m_93228_(p_97267_, p_97268_ + this.f_97251_ + 3, p_97269_ + this.f_97252_, this.f_97243_.m_14992_().m_15551_(), 128 + $$5.m_97325_() * 26, 26, 26);
            this.f_97247_.m_91291_().m_115218_(this.f_97243_.m_14990_(), p_97268_ + this.f_97251_ + 8, p_97269_ + this.f_97252_ + 5);
        }
        for (AdvancementWidget $$6 : this.f_97249_) {
            $$6.m_97266_(p_97267_, p_97268_, p_97269_);
        }
    }

    public int m_169554_() {
        return this.f_97245_;
    }

    public void m_97264_(AdvancementProgress p_97265_) {
        this.f_97250_ = p_97265_;
    }

    public void m_97306_(AdvancementWidget p_97307_) {
        this.f_97249_.add(p_97307_);
    }

    public void m_97270_(PoseStack p_97271_, int p_97272_, int p_97273_, float p_97274_, int p_97275_, int p_97276_) {
        int $$27;
        AdvancementWidgetType $$23;
        AdvancementWidgetType $$22;
        AdvancementWidgetType $$21;
        boolean $$6 = p_97275_ + p_97272_ + this.f_97251_ + this.f_97245_ + 26 >= this.f_97241_.m_97190_().f_96543_;
        String $$7 = this.f_97250_ == null ? null : this.f_97250_.m_8218_();
        int $$8 = $$7 == null ? 0 : this.f_97247_.f_91062_.m_92895_($$7);
        boolean $$9 = 113 - p_97273_ - this.f_97252_ - 26 <= 6 + this.f_97246_.size() * this.f_97247_.f_91062_.f_92710_;
        float $$10 = this.f_97250_ == null ? 0.0f : this.f_97250_.m_8213_();
        int $$11 = Mth.m_14143_($$10 * (float)this.f_97245_);
        if ($$10 >= 1.0f) {
            $$11 = this.f_97245_ / 2;
            AdvancementWidgetType $$12 = AdvancementWidgetType.OBTAINED;
            AdvancementWidgetType $$13 = AdvancementWidgetType.OBTAINED;
            AdvancementWidgetType $$14 = AdvancementWidgetType.OBTAINED;
        } else if ($$11 < 2) {
            $$11 = this.f_97245_ / 2;
            AdvancementWidgetType $$15 = AdvancementWidgetType.UNOBTAINED;
            AdvancementWidgetType $$16 = AdvancementWidgetType.UNOBTAINED;
            AdvancementWidgetType $$17 = AdvancementWidgetType.UNOBTAINED;
        } else if ($$11 > this.f_97245_ - 2) {
            $$11 = this.f_97245_ / 2;
            AdvancementWidgetType $$18 = AdvancementWidgetType.OBTAINED;
            AdvancementWidgetType $$19 = AdvancementWidgetType.OBTAINED;
            AdvancementWidgetType $$20 = AdvancementWidgetType.UNOBTAINED;
        } else {
            $$21 = AdvancementWidgetType.OBTAINED;
            $$22 = AdvancementWidgetType.UNOBTAINED;
            $$23 = AdvancementWidgetType.UNOBTAINED;
        }
        int $$24 = this.f_97245_ - $$11;
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_97239_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_69478_();
        int $$25 = p_97273_ + this.f_97252_;
        if ($$6) {
            int $$26 = p_97272_ + this.f_97251_ - this.f_97245_ + 26 + 6;
        } else {
            $$27 = p_97272_ + this.f_97251_;
        }
        int $$28 = 32 + this.f_97246_.size() * this.f_97247_.f_91062_.f_92710_;
        if (!this.f_97246_.isEmpty()) {
            if ($$9) {
                this.m_97287_(p_97271_, $$27, $$25 + 26 - $$28, this.f_97245_, $$28, 10, 200, 26, 0, 52);
            } else {
                this.m_97287_(p_97271_, $$27, $$25, this.f_97245_, $$28, 10, 200, 26, 0, 52);
            }
        }
        this.m_93228_(p_97271_, $$27, $$25, 0, $$21.m_97325_() * 26, $$11, 26);
        this.m_93228_(p_97271_, $$27 + $$11, $$25, 200 - $$24, $$22.m_97325_() * 26, $$24, 26);
        this.m_93228_(p_97271_, p_97272_ + this.f_97251_ + 3, p_97273_ + this.f_97252_, this.f_97243_.m_14992_().m_15551_(), 128 + $$23.m_97325_() * 26, 26, 26);
        if ($$6) {
            this.f_97247_.f_91062_.m_92744_(p_97271_, this.f_97244_, $$27 + 5, p_97273_ + this.f_97252_ + 9, -1);
            if ($$7 != null) {
                this.f_97247_.f_91062_.m_92750_(p_97271_, $$7, p_97272_ + this.f_97251_ - $$8, p_97273_ + this.f_97252_ + 9, -1);
            }
        } else {
            this.f_97247_.f_91062_.m_92744_(p_97271_, this.f_97244_, p_97272_ + this.f_97251_ + 32, p_97273_ + this.f_97252_ + 9, -1);
            if ($$7 != null) {
                this.f_97247_.f_91062_.m_92750_(p_97271_, $$7, p_97272_ + this.f_97251_ + this.f_97245_ - $$8 - 5, p_97273_ + this.f_97252_ + 9, -1);
            }
        }
        if ($$9) {
            for (int $$29 = 0; $$29 < this.f_97246_.size(); ++$$29) {
                this.f_97247_.f_91062_.m_92877_(p_97271_, this.f_97246_.get($$29), $$27 + 5, $$25 + 26 - $$28 + 7 + $$29 * this.f_97247_.f_91062_.f_92710_, -5592406);
            }
        } else {
            for (int $$30 = 0; $$30 < this.f_97246_.size(); ++$$30) {
                this.f_97247_.f_91062_.m_92877_(p_97271_, this.f_97246_.get($$30), $$27 + 5, p_97273_ + this.f_97252_ + 9 + 17 + $$30 * this.f_97247_.f_91062_.f_92710_, -5592406);
            }
        }
        this.f_97247_.m_91291_().m_115218_(this.f_97243_.m_14990_(), p_97272_ + this.f_97251_ + 8, p_97273_ + this.f_97252_ + 5);
    }

    protected void m_97287_(PoseStack p_97288_, int p_97289_, int p_97290_, int p_97291_, int p_97292_, int p_97293_, int p_97294_, int p_97295_, int p_97296_, int p_97297_) {
        this.m_93228_(p_97288_, p_97289_, p_97290_, p_97296_, p_97297_, p_97293_, p_97293_);
        this.m_97277_(p_97288_, p_97289_ + p_97293_, p_97290_, p_97291_ - p_97293_ - p_97293_, p_97293_, p_97296_ + p_97293_, p_97297_, p_97294_ - p_97293_ - p_97293_, p_97295_);
        this.m_93228_(p_97288_, p_97289_ + p_97291_ - p_97293_, p_97290_, p_97296_ + p_97294_ - p_97293_, p_97297_, p_97293_, p_97293_);
        this.m_93228_(p_97288_, p_97289_, p_97290_ + p_97292_ - p_97293_, p_97296_, p_97297_ + p_97295_ - p_97293_, p_97293_, p_97293_);
        this.m_97277_(p_97288_, p_97289_ + p_97293_, p_97290_ + p_97292_ - p_97293_, p_97291_ - p_97293_ - p_97293_, p_97293_, p_97296_ + p_97293_, p_97297_ + p_97295_ - p_97293_, p_97294_ - p_97293_ - p_97293_, p_97295_);
        this.m_93228_(p_97288_, p_97289_ + p_97291_ - p_97293_, p_97290_ + p_97292_ - p_97293_, p_97296_ + p_97294_ - p_97293_, p_97297_ + p_97295_ - p_97293_, p_97293_, p_97293_);
        this.m_97277_(p_97288_, p_97289_, p_97290_ + p_97293_, p_97293_, p_97292_ - p_97293_ - p_97293_, p_97296_, p_97297_ + p_97293_, p_97294_, p_97295_ - p_97293_ - p_97293_);
        this.m_97277_(p_97288_, p_97289_ + p_97293_, p_97290_ + p_97293_, p_97291_ - p_97293_ - p_97293_, p_97292_ - p_97293_ - p_97293_, p_97296_ + p_97293_, p_97297_ + p_97293_, p_97294_ - p_97293_ - p_97293_, p_97295_ - p_97293_ - p_97293_);
        this.m_97277_(p_97288_, p_97289_ + p_97291_ - p_97293_, p_97290_ + p_97293_, p_97293_, p_97292_ - p_97293_ - p_97293_, p_97296_ + p_97294_ - p_97293_, p_97297_ + p_97293_, p_97294_, p_97295_ - p_97293_ - p_97293_);
    }

    protected void m_97277_(PoseStack p_97278_, int p_97279_, int p_97280_, int p_97281_, int p_97282_, int p_97283_, int p_97284_, int p_97285_, int p_97286_) {
        for (int $$9 = 0; $$9 < p_97281_; $$9 += p_97285_) {
            int $$10 = p_97279_ + $$9;
            int $$11 = Math.min(p_97285_, p_97281_ - $$9);
            for (int $$12 = 0; $$12 < p_97282_; $$12 += p_97286_) {
                int $$13 = p_97280_ + $$12;
                int $$14 = Math.min(p_97286_, p_97282_ - $$12);
                this.m_93228_(p_97278_, $$10, $$13, p_97283_, p_97284_, $$11, $$14);
            }
        }
    }

    public boolean m_97259_(int p_97260_, int p_97261_, int p_97262_, int p_97263_) {
        if (this.f_97243_.m_14997_() && (this.f_97250_ == null || !this.f_97250_.m_8193_())) {
            return false;
        }
        int $$4 = p_97260_ + this.f_97251_;
        int $$5 = $$4 + 26;
        int $$6 = p_97261_ + this.f_97252_;
        int $$7 = $$6 + 26;
        return p_97262_ >= $$4 && p_97262_ <= $$5 && p_97263_ >= $$6 && p_97263_ <= $$7;
    }

    public void m_97313_() {
        if (this.f_97248_ == null && this.f_97242_.m_138319_() != null) {
            this.f_97248_ = this.m_97311_(this.f_97242_);
            if (this.f_97248_ != null) {
                this.f_97248_.m_97306_(this);
            }
        }
    }

    public int m_97314_() {
        return this.f_97252_;
    }

    public int m_97315_() {
        return this.f_97251_;
    }
}

