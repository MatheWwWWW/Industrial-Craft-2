/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;

public abstract class AbstractWidget
extends GuiComponent
implements Widget,
GuiEventListener,
NarratableEntry {
    public static final ResourceLocation f_93617_ = new ResourceLocation("textures/gui/widgets.png");
    protected int f_93618_;
    protected int f_93619_;
    public int f_93620_;
    public int f_93621_;
    private Component f_93614_;
    protected boolean f_93622_;
    public boolean f_93623_ = true;
    public boolean f_93624_ = true;
    protected float f_93625_ = 1.0f;
    private boolean f_93616_;

    public AbstractWidget(int p_93629_, int p_93630_, int p_93631_, int p_93632_, Component p_93633_) {
        this.f_93620_ = p_93629_;
        this.f_93621_ = p_93630_;
        this.f_93618_ = p_93631_;
        this.f_93619_ = p_93632_;
        this.f_93614_ = p_93633_;
    }

    public int m_93694_() {
        return this.f_93619_;
    }

    protected int m_7202_(boolean p_93668_) {
        int $$1 = 1;
        if (!this.f_93623_) {
            $$1 = 0;
        } else if (p_93668_) {
            $$1 = 2;
        }
        return $$1;
    }

    @Override
    public void m_6305_(PoseStack p_93657_, int p_93658_, int p_93659_, float p_93660_) {
        if (!this.f_93624_) {
            return;
        }
        this.f_93622_ = p_93658_ >= this.f_93620_ && p_93659_ >= this.f_93621_ && p_93658_ < this.f_93620_ + this.f_93618_ && p_93659_ < this.f_93621_ + this.f_93619_;
        this.m_6303_(p_93657_, p_93658_, p_93659_, p_93660_);
    }

    protected MutableComponent m_5646_() {
        return AbstractWidget.m_168799_(this.m_6035_());
    }

    public static MutableComponent m_168799_(Component p_168800_) {
        return Component.m_237110_("gui.narrate.button", p_168800_);
    }

    public void m_6303_(PoseStack p_93676_, int p_93677_, int p_93678_, float p_93679_) {
        Minecraft $$4 = Minecraft.m_91087_();
        Font $$5 = $$4.f_91062_;
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_93617_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, this.f_93625_);
        int $$6 = this.m_7202_(this.m_198029_());
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69482_();
        this.m_93228_(p_93676_, this.f_93620_, this.f_93621_, 0, 46 + $$6 * 20, this.f_93618_ / 2, this.f_93619_);
        this.m_93228_(p_93676_, this.f_93620_ + this.f_93618_ / 2, this.f_93621_, 200 - this.f_93618_ / 2, 46 + $$6 * 20, this.f_93618_ / 2, this.f_93619_);
        this.m_7906_(p_93676_, $$4, p_93677_, p_93678_);
        int $$7 = this.f_93623_ ? 0xFFFFFF : 0xA0A0A0;
        AbstractWidget.m_93215_(p_93676_, $$5, this.m_6035_(), this.f_93620_ + this.f_93618_ / 2, this.f_93621_ + (this.f_93619_ - 8) / 2, $$7 | Mth.m_14167_(this.f_93625_ * 255.0f) << 24);
    }

    protected void m_7906_(PoseStack p_93661_, Minecraft p_93662_, int p_93663_, int p_93664_) {
    }

    public void m_5716_(double p_93634_, double p_93635_) {
    }

    public void m_7691_(double p_93669_, double p_93670_) {
    }

    protected void m_7212_(double p_93636_, double p_93637_, double p_93638_, double p_93639_) {
    }

    @Override
    public boolean m_6375_(double p_93641_, double p_93642_, int p_93643_) {
        boolean $$3;
        if (!this.f_93623_ || !this.f_93624_) {
            return false;
        }
        if (this.m_7972_(p_93643_) && ($$3 = this.m_93680_(p_93641_, p_93642_))) {
            this.m_7435_(Minecraft.m_91087_().m_91106_());
            this.m_5716_(p_93641_, p_93642_);
            return true;
        }
        return false;
    }

    @Override
    public boolean m_6348_(double p_93684_, double p_93685_, int p_93686_) {
        if (this.m_7972_(p_93686_)) {
            this.m_7691_(p_93684_, p_93685_);
            return true;
        }
        return false;
    }

    protected boolean m_7972_(int p_93652_) {
        return p_93652_ == 0;
    }

    @Override
    public boolean m_7979_(double p_93645_, double p_93646_, int p_93647_, double p_93648_, double p_93649_) {
        if (this.m_7972_(p_93647_)) {
            this.m_7212_(p_93645_, p_93646_, p_93648_, p_93649_);
            return true;
        }
        return false;
    }

    protected boolean m_93680_(double p_93681_, double p_93682_) {
        return this.f_93623_ && this.f_93624_ && p_93681_ >= (double)this.f_93620_ && p_93682_ >= (double)this.f_93621_ && p_93681_ < (double)(this.f_93620_ + this.f_93618_) && p_93682_ < (double)(this.f_93621_ + this.f_93619_);
    }

    public boolean m_198029_() {
        return this.f_93622_ || this.f_93616_;
    }

    @Override
    public boolean m_5755_(boolean p_93691_) {
        if (!this.f_93623_ || !this.f_93624_) {
            return false;
        }
        this.f_93616_ = !this.f_93616_;
        this.m_7207_(this.f_93616_);
        return this.f_93616_;
    }

    protected void m_7207_(boolean p_93689_) {
    }

    @Override
    public boolean m_5953_(double p_93672_, double p_93673_) {
        return this.f_93623_ && this.f_93624_ && p_93672_ >= (double)this.f_93620_ && p_93673_ >= (double)this.f_93621_ && p_93672_ < (double)(this.f_93620_ + this.f_93618_) && p_93673_ < (double)(this.f_93621_ + this.f_93619_);
    }

    public void m_7428_(PoseStack p_93653_, int p_93654_, int p_93655_) {
    }

    public void m_7435_(SoundManager p_93665_) {
        p_93665_.m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_12490_, 1.0f));
    }

    public int m_5711_() {
        return this.f_93618_;
    }

    public void m_93674_(int p_93675_) {
        this.f_93618_ = p_93675_;
    }

    public void m_93650_(float p_93651_) {
        this.f_93625_ = p_93651_;
    }

    public void m_93666_(Component p_93667_) {
        this.f_93614_ = p_93667_;
    }

    public Component m_6035_() {
        return this.f_93614_;
    }

    public boolean m_93696_() {
        return this.f_93616_;
    }

    @Override
    public boolean m_142518_() {
        return this.f_93624_ && this.f_93623_;
    }

    protected void m_93692_(boolean p_93693_) {
        this.f_93616_ = p_93693_;
    }

    @Override
    public NarratableEntry.NarrationPriority m_142684_() {
        if (this.f_93616_) {
            return NarratableEntry.NarrationPriority.FOCUSED;
        }
        if (this.f_93622_) {
            return NarratableEntry.NarrationPriority.HOVERED;
        }
        return NarratableEntry.NarrationPriority.NONE;
    }

    protected void m_168802_(NarrationElementOutput p_168803_) {
        p_168803_.m_169146_(NarratedElementType.TITLE, this.m_5646_());
        if (this.f_93623_) {
            if (this.m_93696_()) {
                p_168803_.m_169146_(NarratedElementType.USAGE, Component.m_237115_("narration.button.usage.focused"));
            } else {
                p_168803_.m_169146_(NarratedElementType.USAGE, Component.m_237115_("narration.button.usage.hovered"));
            }
        }
    }
}

