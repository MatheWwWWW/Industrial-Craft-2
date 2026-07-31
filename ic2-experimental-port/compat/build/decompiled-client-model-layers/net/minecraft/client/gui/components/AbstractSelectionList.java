/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.components;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public abstract class AbstractSelectionList<E extends Entry<E>>
extends AbstractContainerEventHandler
implements Widget,
NarratableEntry {
    protected final Minecraft f_93386_;
    protected final int f_93387_;
    private final List<E> f_93385_ = new TrackedList();
    protected int f_93388_;
    protected int f_93389_;
    protected int f_93390_;
    protected int f_93391_;
    protected int f_93392_;
    protected int f_93393_;
    protected boolean f_93394_ = true;
    private double f_93396_;
    private boolean f_93397_ = true;
    private boolean f_93398_;
    protected int f_93395_;
    private boolean f_93399_;
    @Nullable
    private E f_93400_;
    private boolean f_93401_ = true;
    private boolean f_93402_ = true;
    @Nullable
    private E f_168789_;

    public AbstractSelectionList(Minecraft p_93404_, int p_93405_, int p_93406_, int p_93407_, int p_93408_, int p_93409_) {
        this.f_93386_ = p_93404_;
        this.f_93388_ = p_93405_;
        this.f_93389_ = p_93406_;
        this.f_93390_ = p_93407_;
        this.f_93391_ = p_93408_;
        this.f_93387_ = p_93409_;
        this.f_93393_ = 0;
        this.f_93392_ = p_93405_;
    }

    public void m_93471_(boolean p_93472_) {
        this.f_93397_ = p_93472_;
    }

    protected void m_93473_(boolean p_93474_, int p_93475_) {
        this.f_93398_ = p_93474_;
        this.f_93395_ = p_93475_;
        if (!p_93474_) {
            this.f_93395_ = 0;
        }
    }

    public int m_5759_() {
        return 220;
    }

    @Nullable
    public E m_93511_() {
        return this.f_93400_;
    }

    public void m_6987_(@Nullable E p_93462_) {
        this.f_93400_ = p_93462_;
    }

    public void m_93488_(boolean p_93489_) {
        this.f_93401_ = p_93489_;
    }

    public void m_93496_(boolean p_93497_) {
        this.f_93402_ = p_93497_;
    }

    @Nullable
    public E m_7222_() {
        return (E)((Entry)super.m_7222_());
    }

    public final List<E> m_6702_() {
        return this.f_93385_;
    }

    protected final void m_93516_() {
        this.f_93385_.clear();
    }

    protected void m_5988_(Collection<E> p_93470_) {
        this.f_93385_.clear();
        this.f_93385_.addAll(p_93470_);
    }

    protected E m_93500_(int p_93501_) {
        return (E)((Entry)this.m_6702_().get(p_93501_));
    }

    protected int m_7085_(E p_93487_) {
        this.f_93385_.add(p_93487_);
        return this.f_93385_.size() - 1;
    }

    protected void m_239857_(E p_239858_) {
        double $$1 = (double)this.m_93518_() - this.m_93517_();
        this.f_93385_.add(0, p_239858_);
        this.m_93410_((double)this.m_93518_() - $$1);
    }

    protected boolean m_239045_(E p_239046_) {
        double $$1 = (double)this.m_93518_() - this.m_93517_();
        boolean $$2 = this.m_93502_(p_239046_);
        this.m_93410_((double)this.m_93518_() - $$1);
        return $$2;
    }

    protected int m_5773_() {
        return this.m_6702_().size();
    }

    protected boolean m_7987_(int p_93504_) {
        return Objects.equals(this.m_93511_(), this.m_6702_().get(p_93504_));
    }

    @Nullable
    protected final E m_93412_(double p_93413_, double p_93414_) {
        int $$2 = this.m_5759_() / 2;
        int $$3 = this.f_93393_ + this.f_93388_ / 2;
        int $$4 = $$3 - $$2;
        int $$5 = $$3 + $$2;
        int $$6 = Mth.m_14107_(p_93414_ - (double)this.f_93390_) - this.f_93395_ + (int)this.m_93517_() - 4;
        int $$7 = $$6 / this.f_93387_;
        if (p_93413_ < (double)this.m_5756_() && p_93413_ >= (double)$$4 && p_93413_ <= (double)$$5 && $$7 >= 0 && $$6 >= 0 && $$7 < this.m_5773_()) {
            return (E)((Entry)this.m_6702_().get($$7));
        }
        return null;
    }

    public void m_93437_(int p_93438_, int p_93439_, int p_93440_, int p_93441_) {
        this.f_93388_ = p_93438_;
        this.f_93389_ = p_93439_;
        this.f_93390_ = p_93440_;
        this.f_93391_ = p_93441_;
        this.f_93393_ = 0;
        this.f_93392_ = p_93438_;
    }

    public void m_93507_(int p_93508_) {
        this.f_93393_ = p_93508_;
        this.f_93392_ = p_93508_ + this.f_93388_;
    }

    protected int m_5775_() {
        return this.m_5773_() * this.f_93387_ + this.f_93395_;
    }

    protected void m_6205_(int p_93431_, int p_93432_) {
    }

    protected void m_7154_(PoseStack p_93458_, int p_93459_, int p_93460_, Tesselator p_93461_) {
    }

    protected void m_7733_(PoseStack p_93442_) {
    }

    protected void m_7415_(PoseStack p_93443_, int p_93444_, int p_93445_) {
    }

    @Override
    public void m_6305_(PoseStack p_93447_, int p_93448_, int p_93449_, float p_93450_) {
        int $$14;
        this.m_7733_(p_93447_);
        int $$4 = this.m_5756_();
        int $$5 = $$4 + 6;
        Tesselator $$6 = Tesselator.m_85913_();
        BufferBuilder $$7 = $$6.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172820_);
        this.f_168789_ = this.m_5953_(p_93448_, p_93449_) ? this.m_93412_(p_93448_, p_93449_) : null;
        Object v0 = this.f_168789_;
        if (this.f_93401_) {
            RenderSystem.m_157456_(0, GuiComponent.f_93096_);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            float $$8 = 32.0f;
            $$7.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
            $$7.m_5483_(this.f_93393_, this.f_93391_, 0.0).m_7421_((float)this.f_93393_ / 32.0f, (float)(this.f_93391_ + (int)this.m_93517_()) / 32.0f).m_6122_(32, 32, 32, 255).m_5752_();
            $$7.m_5483_(this.f_93392_, this.f_93391_, 0.0).m_7421_((float)this.f_93392_ / 32.0f, (float)(this.f_93391_ + (int)this.m_93517_()) / 32.0f).m_6122_(32, 32, 32, 255).m_5752_();
            $$7.m_5483_(this.f_93392_, this.f_93390_, 0.0).m_7421_((float)this.f_93392_ / 32.0f, (float)(this.f_93390_ + (int)this.m_93517_()) / 32.0f).m_6122_(32, 32, 32, 255).m_5752_();
            $$7.m_5483_(this.f_93393_, this.f_93390_, 0.0).m_7421_((float)this.f_93393_ / 32.0f, (float)(this.f_93390_ + (int)this.m_93517_()) / 32.0f).m_6122_(32, 32, 32, 255).m_5752_();
            $$6.m_85914_();
        }
        int $$9 = this.m_5747_();
        int $$10 = this.f_93390_ + 4 - (int)this.m_93517_();
        if (this.f_93398_) {
            this.m_7154_(p_93447_, $$9, $$10, $$6);
        }
        this.m_239227_(p_93447_, p_93448_, p_93449_, p_93450_);
        if (this.f_93402_) {
            RenderSystem.m_157427_(GameRenderer::m_172820_);
            RenderSystem.m_157456_(0, GuiComponent.f_93096_);
            RenderSystem.m_69482_();
            RenderSystem.m_69456_(519);
            float $$11 = 32.0f;
            int $$12 = -100;
            $$7.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
            $$7.m_5483_(this.f_93393_, this.f_93390_, -100.0).m_7421_(0.0f, (float)this.f_93390_ / 32.0f).m_6122_(64, 64, 64, 255).m_5752_();
            $$7.m_5483_(this.f_93393_ + this.f_93388_, this.f_93390_, -100.0).m_7421_((float)this.f_93388_ / 32.0f, (float)this.f_93390_ / 32.0f).m_6122_(64, 64, 64, 255).m_5752_();
            $$7.m_5483_(this.f_93393_ + this.f_93388_, 0.0, -100.0).m_7421_((float)this.f_93388_ / 32.0f, 0.0f).m_6122_(64, 64, 64, 255).m_5752_();
            $$7.m_5483_(this.f_93393_, 0.0, -100.0).m_7421_(0.0f, 0.0f).m_6122_(64, 64, 64, 255).m_5752_();
            $$7.m_5483_(this.f_93393_, this.f_93389_, -100.0).m_7421_(0.0f, (float)this.f_93389_ / 32.0f).m_6122_(64, 64, 64, 255).m_5752_();
            $$7.m_5483_(this.f_93393_ + this.f_93388_, this.f_93389_, -100.0).m_7421_((float)this.f_93388_ / 32.0f, (float)this.f_93389_ / 32.0f).m_6122_(64, 64, 64, 255).m_5752_();
            $$7.m_5483_(this.f_93393_ + this.f_93388_, this.f_93391_, -100.0).m_7421_((float)this.f_93388_ / 32.0f, (float)this.f_93391_ / 32.0f).m_6122_(64, 64, 64, 255).m_5752_();
            $$7.m_5483_(this.f_93393_, this.f_93391_, -100.0).m_7421_(0.0f, (float)this.f_93391_ / 32.0f).m_6122_(64, 64, 64, 255).m_5752_();
            $$6.m_85914_();
            RenderSystem.m_69456_(515);
            RenderSystem.m_69465_();
            RenderSystem.m_69478_();
            RenderSystem.m_69416_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
            RenderSystem.m_69472_();
            RenderSystem.m_157427_(GameRenderer::m_172811_);
            int $$13 = 4;
            $$7.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
            $$7.m_5483_(this.f_93393_, this.f_93390_ + 4, 0.0).m_6122_(0, 0, 0, 0).m_5752_();
            $$7.m_5483_(this.f_93392_, this.f_93390_ + 4, 0.0).m_6122_(0, 0, 0, 0).m_5752_();
            $$7.m_5483_(this.f_93392_, this.f_93390_, 0.0).m_6122_(0, 0, 0, 255).m_5752_();
            $$7.m_5483_(this.f_93393_, this.f_93390_, 0.0).m_6122_(0, 0, 0, 255).m_5752_();
            $$7.m_5483_(this.f_93393_, this.f_93391_, 0.0).m_6122_(0, 0, 0, 255).m_5752_();
            $$7.m_5483_(this.f_93392_, this.f_93391_, 0.0).m_6122_(0, 0, 0, 255).m_5752_();
            $$7.m_5483_(this.f_93392_, this.f_93391_ - 4, 0.0).m_6122_(0, 0, 0, 0).m_5752_();
            $$7.m_5483_(this.f_93393_, this.f_93391_ - 4, 0.0).m_6122_(0, 0, 0, 0).m_5752_();
            $$6.m_85914_();
        }
        if (($$14 = this.m_93518_()) > 0) {
            RenderSystem.m_69472_();
            RenderSystem.m_157427_(GameRenderer::m_172811_);
            int $$15 = (int)((float)((this.f_93391_ - this.f_93390_) * (this.f_93391_ - this.f_93390_)) / (float)this.m_5775_());
            $$15 = Mth.m_14045_($$15, 32, this.f_93391_ - this.f_93390_ - 8);
            int $$16 = (int)this.m_93517_() * (this.f_93391_ - this.f_93390_ - $$15) / $$14 + this.f_93390_;
            if ($$16 < this.f_93390_) {
                $$16 = this.f_93390_;
            }
            $$7.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
            $$7.m_5483_($$4, this.f_93391_, 0.0).m_6122_(0, 0, 0, 255).m_5752_();
            $$7.m_5483_($$5, this.f_93391_, 0.0).m_6122_(0, 0, 0, 255).m_5752_();
            $$7.m_5483_($$5, this.f_93390_, 0.0).m_6122_(0, 0, 0, 255).m_5752_();
            $$7.m_5483_($$4, this.f_93390_, 0.0).m_6122_(0, 0, 0, 255).m_5752_();
            $$7.m_5483_($$4, $$16 + $$15, 0.0).m_6122_(128, 128, 128, 255).m_5752_();
            $$7.m_5483_($$5, $$16 + $$15, 0.0).m_6122_(128, 128, 128, 255).m_5752_();
            $$7.m_5483_($$5, $$16, 0.0).m_6122_(128, 128, 128, 255).m_5752_();
            $$7.m_5483_($$4, $$16, 0.0).m_6122_(128, 128, 128, 255).m_5752_();
            $$7.m_5483_($$4, $$16 + $$15 - 1, 0.0).m_6122_(192, 192, 192, 255).m_5752_();
            $$7.m_5483_($$5 - 1, $$16 + $$15 - 1, 0.0).m_6122_(192, 192, 192, 255).m_5752_();
            $$7.m_5483_($$5 - 1, $$16, 0.0).m_6122_(192, 192, 192, 255).m_5752_();
            $$7.m_5483_($$4, $$16, 0.0).m_6122_(192, 192, 192, 255).m_5752_();
            $$6.m_85914_();
        }
        this.m_7415_(p_93447_, p_93448_, p_93449_);
        RenderSystem.m_69493_();
        RenderSystem.m_69461_();
    }

    protected void m_93494_(E p_93495_) {
        this.m_93410_(this.m_6702_().indexOf(p_93495_) * this.f_93387_ + this.f_93387_ / 2 - (this.f_93391_ - this.f_93390_) / 2);
    }

    protected void m_93498_(E p_93499_) {
        int $$3;
        int $$1 = this.m_7610_(this.m_6702_().indexOf(p_93499_));
        int $$2 = $$1 - this.f_93390_ - 4 - this.f_93387_;
        if ($$2 < 0) {
            this.m_93429_($$2);
        }
        if (($$3 = this.f_93391_ - $$1 - this.f_93387_ - this.f_93387_) < 0) {
            this.m_93429_(-$$3);
        }
    }

    private void m_93429_(int p_93430_) {
        this.m_93410_(this.m_93517_() + (double)p_93430_);
    }

    public double m_93517_() {
        return this.f_93396_;
    }

    public void m_93410_(double p_93411_) {
        this.f_93396_ = Mth.m_14008_(p_93411_, 0.0, this.m_93518_());
    }

    public int m_93518_() {
        return Math.max(0, this.m_5775_() - (this.f_93391_ - this.f_93390_ - 4));
    }

    public int m_168793_() {
        return (int)this.m_93517_() - this.f_93389_ - this.f_93395_;
    }

    protected void m_93481_(double p_93482_, double p_93483_, int p_93484_) {
        this.f_93399_ = p_93484_ == 0 && p_93482_ >= (double)this.m_5756_() && p_93482_ < (double)(this.m_5756_() + 6);
    }

    protected int m_5756_() {
        return this.f_93388_ / 2 + 124;
    }

    @Override
    public boolean m_6375_(double p_93420_, double p_93421_, int p_93422_) {
        this.m_93481_(p_93420_, p_93421_, p_93422_);
        if (!this.m_5953_(p_93420_, p_93421_)) {
            return false;
        }
        E $$3 = this.m_93412_(p_93420_, p_93421_);
        if ($$3 != null) {
            if ($$3.m_6375_(p_93420_, p_93421_, p_93422_)) {
                this.m_7522_((GuiEventListener)$$3);
                this.m_7897_(true);
                return true;
            }
        } else if (p_93422_ == 0) {
            this.m_6205_((int)(p_93420_ - (double)(this.f_93393_ + this.f_93388_ / 2 - this.m_5759_() / 2)), (int)(p_93421_ - (double)this.f_93390_) + (int)this.m_93517_() - 4);
            return true;
        }
        return this.f_93399_;
    }

    @Override
    public boolean m_6348_(double p_93491_, double p_93492_, int p_93493_) {
        if (this.m_7222_() != null) {
            this.m_7222_().m_6348_(p_93491_, p_93492_, p_93493_);
        }
        return false;
    }

    @Override
    public boolean m_7979_(double p_93424_, double p_93425_, int p_93426_, double p_93427_, double p_93428_) {
        if (super.m_7979_(p_93424_, p_93425_, p_93426_, p_93427_, p_93428_)) {
            return true;
        }
        if (p_93426_ != 0 || !this.f_93399_) {
            return false;
        }
        if (p_93425_ < (double)this.f_93390_) {
            this.m_93410_(0.0);
        } else if (p_93425_ > (double)this.f_93391_) {
            this.m_93410_(this.m_93518_());
        } else {
            double $$5 = Math.max(1, this.m_93518_());
            int $$6 = this.f_93391_ - this.f_93390_;
            int $$7 = Mth.m_14045_((int)((float)($$6 * $$6) / (float)this.m_5775_()), 32, $$6 - 8);
            double $$8 = Math.max(1.0, $$5 / (double)($$6 - $$7));
            this.m_93410_(this.m_93517_() + p_93428_ * $$8);
        }
        return true;
    }

    @Override
    public boolean m_6050_(double p_93416_, double p_93417_, double p_93418_) {
        this.m_93410_(this.m_93517_() - p_93418_ * (double)this.f_93387_ / 2.0);
        return true;
    }

    @Override
    public boolean m_7933_(int p_93434_, int p_93435_, int p_93436_) {
        if (super.m_7933_(p_93434_, p_93435_, p_93436_)) {
            return true;
        }
        if (p_93434_ == 264) {
            this.m_6778_(SelectionDirection.DOWN);
            return true;
        }
        if (p_93434_ == 265) {
            this.m_6778_(SelectionDirection.UP);
            return true;
        }
        return false;
    }

    protected void m_6778_(SelectionDirection p_93463_) {
        this.m_93464_(p_93463_, p_93510_ -> true);
    }

    protected void m_93519_() {
        E $$0 = this.m_93511_();
        if ($$0 != null) {
            this.m_6987_($$0);
            this.m_93498_($$0);
        }
    }

    protected boolean m_93464_(SelectionDirection p_93465_, Predicate<E> p_93466_) {
        int $$2;
        int n = $$2 = p_93465_ == SelectionDirection.UP ? -1 : 1;
        if (!this.m_6702_().isEmpty()) {
            int $$4;
            int $$3 = this.m_6702_().indexOf(this.m_93511_());
            while ($$3 != ($$4 = Mth.m_14045_($$3 + $$2, 0, this.m_5773_() - 1))) {
                Entry $$5 = (Entry)this.m_6702_().get($$4);
                if (p_93466_.test($$5)) {
                    this.m_6987_($$5);
                    this.m_93498_($$5);
                    return true;
                }
                $$3 = $$4;
            }
        }
        return false;
    }

    @Override
    public boolean m_5953_(double p_93479_, double p_93480_) {
        return p_93480_ >= (double)this.f_93390_ && p_93480_ <= (double)this.f_93391_ && p_93479_ >= (double)this.f_93393_ && p_93479_ <= (double)this.f_93392_;
    }

    protected void m_239227_(PoseStack p_239228_, int p_239229_, int p_239230_, float p_239231_) {
        int $$4 = this.m_5747_();
        int $$5 = this.m_5759_();
        int $$6 = this.f_93387_ - 4;
        int $$7 = this.m_5773_();
        for (int $$8 = 0; $$8 < $$7; ++$$8) {
            int $$9 = this.m_7610_($$8);
            int $$10 = this.m_93485_($$8);
            if ($$10 < this.f_93390_ || $$9 > this.f_93391_) continue;
            this.m_238964_(p_239228_, p_239229_, p_239230_, p_239231_, $$8, $$4, $$9, $$5, $$6);
        }
    }

    protected void m_238964_(PoseStack p_238965_, int p_238966_, int p_238967_, float p_238968_, int p_238969_, int p_238970_, int p_238971_, int p_238972_, int p_238973_) {
        E $$9 = this.m_93500_(p_238969_);
        if (this.f_93397_ && this.m_7987_(p_238969_)) {
            int $$10 = this.m_5694_() ? -1 : -8355712;
            this.m_240140_(p_238965_, p_238971_, p_238972_, p_238973_, $$10, -16777216);
        }
        ((Entry)$$9).m_6311_(p_238965_, p_238969_, p_238971_, p_238970_, p_238972_, p_238973_, p_238966_, p_238967_, Objects.equals(this.f_168789_, $$9), p_238968_);
    }

    protected void m_240140_(PoseStack p_240141_, int p_240142_, int p_240143_, int p_240144_, int p_240145_, int p_240146_) {
        int $$6 = this.f_93393_ + (this.f_93388_ - p_240143_) / 2;
        int $$7 = this.f_93393_ + (this.f_93388_ + p_240143_) / 2;
        AbstractSelectionList.m_93172_(p_240141_, $$6, p_240142_ - 2, $$7, p_240142_ + p_240144_ + 2, p_240145_);
        AbstractSelectionList.m_93172_(p_240141_, $$6 + 1, p_240142_ - 1, $$7 - 1, p_240142_ + p_240144_ + 1, p_240146_);
    }

    public int m_5747_() {
        return this.f_93393_ + this.f_93388_ / 2 - this.m_5759_() / 2 + 2;
    }

    public int m_93520_() {
        return this.m_5747_() + this.m_5759_();
    }

    protected int m_7610_(int p_93512_) {
        return this.f_93390_ + 4 - (int)this.m_93517_() + p_93512_ * this.f_93387_ + this.f_93395_;
    }

    private int m_93485_(int p_93486_) {
        return this.m_7610_(p_93486_) + this.f_93387_;
    }

    protected boolean m_5694_() {
        return false;
    }

    @Override
    public NarratableEntry.NarrationPriority m_142684_() {
        if (this.m_5694_()) {
            return NarratableEntry.NarrationPriority.FOCUSED;
        }
        if (this.f_168789_ != null) {
            return NarratableEntry.NarrationPriority.HOVERED;
        }
        return NarratableEntry.NarrationPriority.NONE;
    }

    @Nullable
    protected E m_93514_(int p_93515_) {
        Entry $$1 = (Entry)this.f_93385_.get(p_93515_);
        if (this.m_93502_((Entry)this.f_93385_.get(p_93515_))) {
            return (E)$$1;
        }
        return null;
    }

    protected boolean m_93502_(E p_93503_) {
        boolean $$1 = this.f_93385_.remove(p_93503_);
        if ($$1 && p_93503_ == this.m_93511_()) {
            this.m_6987_(null);
        }
        return $$1;
    }

    @Nullable
    protected E m_168795_() {
        return this.f_168789_;
    }

    void m_93505_(Entry<E> p_93506_) {
        p_93506_.f_93521_ = this;
    }

    protected void m_168790_(NarrationElementOutput p_168791_, E p_168792_) {
        int $$3;
        List<E> $$2 = this.m_6702_();
        if ($$2.size() > 1 && ($$3 = $$2.indexOf(p_168792_)) != -1) {
            p_168791_.m_169146_(NarratedElementType.POSITION, Component.m_237110_("narrator.position.list", $$3 + 1, $$2.size()));
        }
    }

    @Override
    @Nullable
    public /* synthetic */ GuiEventListener m_7222_() {
        return this.m_7222_();
    }

    class TrackedList
    extends AbstractList<E> {
        private final List<E> f_93550_ = Lists.newArrayList();

        TrackedList() {
        }

        @Override
        public E get(int p_93557_) {
            return (Entry)this.f_93550_.get(p_93557_);
        }

        @Override
        public int size() {
            return this.f_93550_.size();
        }

        @Override
        public E set(int p_93559_, E p_93560_) {
            Entry $$2 = (Entry)this.f_93550_.set(p_93559_, p_93560_);
            AbstractSelectionList.this.m_93505_(p_93560_);
            return $$2;
        }

        @Override
        public void add(int p_93567_, E p_93568_) {
            this.f_93550_.add(p_93567_, p_93568_);
            AbstractSelectionList.this.m_93505_(p_93568_);
        }

        @Override
        public E remove(int p_93565_) {
            return (Entry)this.f_93550_.remove(p_93565_);
        }

        @Override
        public /* synthetic */ Object remove(int n) {
            return this.remove(n);
        }

        @Override
        public /* synthetic */ void add(int n, Object object) {
            this.add(n, (E)((Entry)object));
        }

        @Override
        public /* synthetic */ Object set(int n, Object object) {
            return this.set(n, (E)((Entry)object));
        }

        @Override
        public /* synthetic */ Object get(int n) {
            return this.get(n);
        }
    }

    public static abstract class Entry<E extends Entry<E>>
    implements GuiEventListener {
        @Deprecated
        AbstractSelectionList<E> f_93521_;

        public abstract void m_6311_(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10);

        @Override
        public boolean m_5953_(double p_93537_, double p_93538_) {
            return Objects.equals(this.f_93521_.m_93412_(p_93537_, p_93538_), this);
        }
    }

    protected static final class SelectionDirection
    extends Enum<SelectionDirection> {
        public static final /* enum */ SelectionDirection UP = new SelectionDirection();
        public static final /* enum */ SelectionDirection DOWN = new SelectionDirection();
        private static final /* synthetic */ SelectionDirection[] $VALUES;

        public static SelectionDirection[] values() {
            return (SelectionDirection[])$VALUES.clone();
        }

        public static SelectionDirection valueOf(String p_93547_) {
            return Enum.valueOf(SelectionDirection.class, p_93547_);
        }

        private static /* synthetic */ SelectionDirection[] m_168796_() {
            return new SelectionDirection[]{UP, DOWN};
        }

        static {
            $VALUES = SelectionDirection.m_168796_();
        }
    }
}

