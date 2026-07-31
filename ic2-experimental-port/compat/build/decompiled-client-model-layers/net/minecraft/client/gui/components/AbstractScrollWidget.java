/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public abstract class AbstractScrollWidget
extends AbstractWidget
implements Widget,
GuiEventListener {
    private static final int f_238809_ = -1;
    private static final int f_238810_ = -6250336;
    private static final int f_238748_ = -16777216;
    private static final int f_238777_ = 4;
    private double f_238564_;
    private boolean f_238779_;

    public AbstractScrollWidget(int p_240025_, int p_240026_, int p_240027_, int p_240028_, Component p_240029_) {
        super(p_240025_, p_240026_, p_240027_, p_240028_, p_240029_);
    }

    @Override
    public boolean m_6375_(double p_240170_, double p_240171_, int p_240172_) {
        if (!this.f_93624_) {
            return false;
        }
        boolean $$3 = this.m_239606_(p_240170_, p_240171_);
        boolean $$4 = this.m_239656_() && p_240170_ >= (double)(this.f_93620_ + this.f_93618_) && p_240170_ <= (double)(this.f_93620_ + this.f_93618_ + 8) && p_240171_ >= (double)this.f_93621_ && p_240171_ < (double)(this.f_93621_ + this.f_93619_);
        this.m_93692_($$3 || $$4);
        if ($$4 && p_240172_ == 0) {
            this.f_238779_ = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean m_6348_(double p_239063_, double p_239064_, int p_239065_) {
        if (p_239065_ == 0) {
            this.f_238779_ = false;
        }
        return super.m_6348_(p_239063_, p_239064_, p_239065_);
    }

    @Override
    public boolean m_7979_(double p_239639_, double p_239640_, int p_239641_, double p_239642_, double p_239643_) {
        if (!(this.f_93624_ && this.m_93696_() && this.f_238779_)) {
            return false;
        }
        if (p_239640_ < (double)this.f_93621_) {
            this.m_240206_(0.0);
        } else if (p_239640_ > (double)(this.f_93621_ + this.f_93619_)) {
            this.m_240206_(this.m_239509_());
        } else {
            int $$5 = this.m_240211_();
            double $$6 = Math.max(1, this.m_239509_() / (this.f_93619_ - $$5));
            this.m_240206_(this.f_238564_ + p_239643_ * $$6);
        }
        return true;
    }

    @Override
    public boolean m_6050_(double p_239308_, double p_239309_, double p_239310_) {
        if (!this.f_93624_ || !this.m_93696_()) {
            return false;
        }
        this.m_240206_(this.f_238564_ - p_239310_ * this.m_239725_());
        return true;
    }

    @Override
    public void m_6303_(PoseStack p_239793_, int p_239794_, int p_239795_, float p_239796_) {
        if (!this.f_93624_) {
            return;
        }
        this.m_240048_(p_239793_);
        AbstractScrollWidget.m_239260_(this.f_93620_ + 1, this.f_93621_ + 1, this.f_93620_ + this.f_93618_ - 1, this.f_93621_ + this.f_93619_ - 1);
        p_239793_.m_85836_();
        p_239793_.m_85837_(0.0, -this.f_238564_, 0.0);
        this.m_239000_(p_239793_, p_239794_, p_239795_, p_239796_);
        p_239793_.m_85849_();
        AbstractScrollWidget.m_240060_();
        this.m_239516_(p_239793_);
    }

    private int m_240211_() {
        return Mth.m_14045_((int)((float)(this.f_93619_ * this.f_93619_) / (float)this.m_239044_()), 32, this.f_93619_);
    }

    protected void m_239516_(PoseStack p_239981_) {
        if (this.m_239656_()) {
            this.m_239245_();
        }
    }

    protected int m_239244_() {
        return 4;
    }

    protected int m_240012_() {
        return this.m_239244_() * 2;
    }

    protected double m_239030_() {
        return this.f_238564_;
    }

    protected void m_240206_(double p_240207_) {
        this.f_238564_ = Mth.m_14008_(p_240207_, 0.0, this.m_239509_());
    }

    protected int m_239509_() {
        return Math.max(0, this.m_239044_() - (this.f_93619_ - 4));
    }

    private int m_239044_() {
        return this.m_239019_() + 4;
    }

    private void m_240048_(PoseStack p_240049_) {
        int $$1 = this.m_93696_() ? -1 : -6250336;
        AbstractScrollWidget.m_93172_(p_240049_, this.f_93620_, this.f_93621_, this.f_93620_ + this.f_93618_, this.f_93621_ + this.f_93619_, $$1);
        AbstractScrollWidget.m_93172_(p_240049_, this.f_93620_ + 1, this.f_93621_ + 1, this.f_93620_ + this.f_93618_ - 1, this.f_93621_ + this.f_93619_ - 1, -16777216);
    }

    private void m_239245_() {
        int $$0 = this.m_240211_();
        int $$1 = this.f_93620_ + this.f_93618_;
        int $$2 = this.f_93620_ + this.f_93618_ + 8;
        int $$3 = Math.max(this.f_93621_, (int)this.f_238564_ * (this.f_93619_ - $$0) / this.m_239509_() + this.f_93621_);
        int $$4 = $$3 + $$0;
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        Tesselator $$5 = Tesselator.m_85913_();
        BufferBuilder $$6 = $$5.m_85915_();
        $$6.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        $$6.m_5483_($$1, $$4, 0.0).m_6122_(128, 128, 128, 255).m_5752_();
        $$6.m_5483_($$2, $$4, 0.0).m_6122_(128, 128, 128, 255).m_5752_();
        $$6.m_5483_($$2, $$3, 0.0).m_6122_(128, 128, 128, 255).m_5752_();
        $$6.m_5483_($$1, $$3, 0.0).m_6122_(128, 128, 128, 255).m_5752_();
        $$6.m_5483_($$1, $$4 - 1, 0.0).m_6122_(192, 192, 192, 255).m_5752_();
        $$6.m_5483_($$2 - 1, $$4 - 1, 0.0).m_6122_(192, 192, 192, 255).m_5752_();
        $$6.m_5483_($$2 - 1, $$3, 0.0).m_6122_(192, 192, 192, 255).m_5752_();
        $$6.m_5483_($$1, $$3, 0.0).m_6122_(192, 192, 192, 255).m_5752_();
        $$5.m_85914_();
    }

    protected boolean m_239942_(int p_239943_, int p_239944_) {
        return (double)p_239944_ - this.f_238564_ >= (double)this.f_93621_ && (double)p_239943_ - this.f_238564_ <= (double)(this.f_93621_ + this.f_93619_);
    }

    protected boolean m_239606_(double p_239607_, double p_239608_) {
        return p_239607_ >= (double)this.f_93620_ && p_239607_ < (double)(this.f_93620_ + this.f_93618_) && p_239608_ >= (double)this.f_93621_ && p_239608_ < (double)(this.f_93621_ + this.f_93619_);
    }

    protected abstract int m_239019_();

    protected abstract boolean m_239656_();

    protected abstract double m_239725_();

    protected abstract void m_239000_(PoseStack var1, int var2, int var3, float var4);
}

