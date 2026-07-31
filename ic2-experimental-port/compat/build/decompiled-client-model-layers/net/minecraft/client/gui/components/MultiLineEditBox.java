/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.components;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import java.util.function.Consumer;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.components.MultilineTextField;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class MultiLineEditBox
extends AbstractScrollWidget {
    private static final int f_238826_ = 1;
    private static final int f_238688_ = -3092272;
    private static final String f_238647_ = "_";
    private static final int f_238712_ = -2039584;
    private static final int f_238758_ = -857677600;
    private final Font f_238790_;
    private final Component f_238653_;
    private final MultilineTextField f_238540_;
    private int f_238824_;

    public MultiLineEditBox(Font p_239008_, int p_239009_, int p_239010_, int p_239011_, int p_239012_, Component p_239013_, Component p_239014_) {
        super(p_239009_, p_239010_, p_239011_, p_239012_, p_239014_);
        this.f_238790_ = p_239008_;
        this.f_238653_ = p_239013_;
        this.f_238540_ = new MultilineTextField(p_239008_, p_239011_ - this.m_240012_());
        this.f_238540_.m_239257_(this::m_239911_);
    }

    public void m_239313_(int p_239314_) {
        this.f_238540_.m_240162_(p_239314_);
    }

    public void m_239273_(Consumer<String> p_239274_) {
        this.f_238540_.m_239919_(p_239274_);
    }

    public void m_240159_(String p_240160_) {
        this.f_238540_.m_239677_(p_240160_);
    }

    public String m_239249_() {
        return this.f_238540_.m_239618_();
    }

    public void m_239213_() {
        ++this.f_238824_;
    }

    @Override
    public void m_142291_(NarrationElementOutput p_240122_) {
        p_240122_.m_169146_(NarratedElementType.TITLE, Component.m_237110_("narration.edit_box", this.m_239249_()));
    }

    @Override
    public boolean m_6375_(double p_239101_, double p_239102_, int p_239103_) {
        if (super.m_6375_(p_239101_, p_239102_, p_239103_)) {
            return true;
        }
        if (this.m_239606_(p_239101_, p_239102_) && p_239103_ == 0) {
            this.f_238540_.m_239950_(Screen.m_96638_());
            this.m_239275_(p_239101_, p_239102_);
            return true;
        }
        return false;
    }

    @Override
    public boolean m_7979_(double p_238978_, double p_238979_, int p_238980_, double p_238981_, double p_238982_) {
        if (super.m_7979_(p_238978_, p_238979_, p_238980_, p_238981_, p_238982_)) {
            return true;
        }
        if (this.m_239606_(p_238978_, p_238979_) && p_238980_ == 0) {
            this.f_238540_.m_239950_(true);
            this.m_239275_(p_238978_, p_238979_);
            this.f_238540_.m_239950_(Screen.m_96638_());
            return true;
        }
        return false;
    }

    @Override
    public boolean m_7933_(int p_239433_, int p_239434_, int p_239435_) {
        return this.f_238540_.m_239711_(p_239433_);
    }

    @Override
    public boolean m_5534_(char p_239387_, int p_239388_) {
        if (!(this.f_93624_ && this.m_93696_() && SharedConstants.m_136188_(p_239387_))) {
            return false;
        }
        this.f_238540_.m_240015_(Character.toString(p_239387_));
        return true;
    }

    @Override
    protected void m_239000_(PoseStack p_239001_, int p_239002_, int p_239003_, float p_239004_) {
        String $$4 = this.f_238540_.m_239618_();
        if ($$4.isEmpty() && !this.m_93696_()) {
            this.f_238790_.m_92857_(this.f_238653_, this.f_93620_ + this.m_239244_(), this.f_93621_ + this.m_239244_(), this.f_93618_ - this.m_240012_(), -857677600);
            return;
        }
        int $$5 = this.f_238540_.m_239456_();
        boolean $$6 = this.m_93696_() && this.f_238824_ / 6 % 2 == 0;
        boolean $$7 = $$5 < $$4.length();
        int $$8 = 0;
        int $$9 = 0;
        int $$10 = this.f_93621_ + this.m_239244_();
        for (MultilineTextField.StringView $$11 : this.f_238540_.m_239290_()) {
            boolean $$12 = this.m_239942_($$10, $$10 + this.f_238790_.f_92710_);
            if ($$6 && $$7 && $$5 >= $$11.f_238590_() && $$5 <= $$11.f_238654_()) {
                if ($$12) {
                    $$8 = this.f_238790_.m_92750_(p_239001_, $$4.substring($$11.f_238590_(), $$5), this.f_93620_ + this.m_239244_(), $$10, -2039584) - 1;
                    GuiComponent.m_93172_(p_239001_, $$8, $$10 - 1, $$8 + 1, $$10 + 1 + this.f_238790_.f_92710_, -3092272);
                    this.f_238790_.m_92750_(p_239001_, $$4.substring($$5, $$11.f_238654_()), $$8, $$10, -2039584);
                }
            } else {
                if ($$12) {
                    $$8 = this.f_238790_.m_92750_(p_239001_, $$4.substring($$11.f_238590_(), $$11.f_238654_()), this.f_93620_ + this.m_239244_(), $$10, -2039584) - 1;
                }
                $$9 = $$10;
            }
            $$10 += this.f_238790_.f_92710_;
        }
        if ($$6 && !$$7 && this.m_239942_($$9, $$9 + this.f_238790_.f_92710_)) {
            this.f_238790_.m_92750_(p_239001_, f_238647_, $$8, $$9, -3092272);
        }
        if (this.f_238540_.m_239344_()) {
            MultilineTextField.StringView $$13 = this.f_238540_.m_239982_();
            int $$14 = this.f_93620_ + this.m_239244_();
            $$10 = this.f_93621_ + this.m_239244_();
            for (MultilineTextField.StringView $$15 : this.f_238540_.m_239290_()) {
                if ($$13.f_238590_() > $$15.f_238654_()) {
                    $$10 += this.f_238790_.f_92710_;
                    continue;
                }
                if ($$15.f_238590_() > $$13.f_238654_()) break;
                if (this.m_239942_($$10, $$10 + this.f_238790_.f_92710_)) {
                    int $$18;
                    int $$16 = this.f_238790_.m_92895_($$4.substring($$15.f_238590_(), Math.max($$13.f_238590_(), $$15.f_238590_())));
                    if ($$13.f_238654_() > $$15.f_238654_()) {
                        int $$17 = this.f_93618_ - this.m_239244_();
                    } else {
                        $$18 = this.f_238790_.m_92895_($$4.substring($$15.f_238590_(), $$13.f_238654_()));
                    }
                    this.m_239486_(p_239001_, $$14 + $$16, $$10, $$14 + $$18, $$10 + this.f_238790_.f_92710_);
                }
                $$10 += this.f_238790_.f_92710_;
            }
        }
    }

    @Override
    protected void m_239516_(PoseStack p_239517_) {
        super.m_239516_(p_239517_);
        if (this.f_238540_.m_239629_()) {
            int $$1 = this.f_238540_.m_239390_();
            MutableComponent $$2 = Component.m_237110_("gui.multiLineEditBox.character_limit", this.f_238540_.m_239618_().length(), $$1);
            MultiLineEditBox.m_93243_(p_239517_, this.f_238790_, $$2, this.f_93620_ + this.f_93618_ - this.f_238790_.m_92852_($$2), this.f_93621_ + this.f_93619_ + 4, 0xA0A0A0);
        }
    }

    @Override
    public int m_239019_() {
        return this.f_238790_.f_92710_ * this.f_238540_.m_239340_();
    }

    @Override
    protected boolean m_239656_() {
        return (double)this.f_238540_.m_239340_() > this.m_239745_();
    }

    @Override
    protected double m_239725_() {
        return (double)this.f_238790_.f_92710_ / 2.0;
    }

    private void m_239486_(PoseStack p_239487_, int p_239488_, int p_239489_, int p_239490_, int p_239491_) {
        Matrix4f $$5 = p_239487_.m_85850_().m_85861_();
        Tesselator $$6 = Tesselator.m_85913_();
        BufferBuilder $$7 = $$6.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172808_);
        RenderSystem.m_157429_(0.0f, 0.0f, 1.0f, 1.0f);
        RenderSystem.m_69472_();
        RenderSystem.m_69479_();
        RenderSystem.m_69835_(GlStateManager.LogicOp.OR_REVERSE);
        $$7.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);
        $$7.m_85982_($$5, p_239488_, p_239491_, 0.0f).m_5752_();
        $$7.m_85982_($$5, p_239490_, p_239491_, 0.0f).m_5752_();
        $$7.m_85982_($$5, p_239490_, p_239489_, 0.0f).m_5752_();
        $$7.m_85982_($$5, p_239488_, p_239489_, 0.0f).m_5752_();
        $$6.m_85914_();
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_69462_();
        RenderSystem.m_69493_();
    }

    private void m_239911_() {
        double $$0 = this.m_239030_();
        MultilineTextField.StringView $$1 = this.f_238540_.m_239144_((int)($$0 / (double)this.f_238790_.f_92710_));
        if (this.f_238540_.m_239456_() <= $$1.f_238590_()) {
            $$0 = this.f_238540_.m_239268_() * this.f_238790_.f_92710_;
        } else {
            MultilineTextField.StringView $$2 = this.f_238540_.m_239144_((int)(($$0 + (double)this.f_93619_) / (double)this.f_238790_.f_92710_) - 1);
            if (this.f_238540_.m_239456_() > $$2.f_238654_()) {
                $$0 = this.f_238540_.m_239268_() * this.f_238790_.f_92710_ - this.f_93619_ + this.f_238790_.f_92710_ + this.m_240012_();
            }
        }
        this.m_240206_($$0);
    }

    private double m_239745_() {
        return (double)(this.f_93619_ - this.m_240012_()) / (double)this.f_238790_.f_92710_;
    }

    private void m_239275_(double p_239276_, double p_239277_) {
        double $$2 = p_239276_ - (double)this.f_93620_ - (double)this.m_239244_();
        double $$3 = p_239277_ - (double)this.f_93621_ - (double)this.m_239244_() + this.m_239030_();
        this.f_238540_.m_239578_($$2, $$3);
    }
}

