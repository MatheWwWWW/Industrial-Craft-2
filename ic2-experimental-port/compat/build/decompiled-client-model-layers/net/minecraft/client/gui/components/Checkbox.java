/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.components;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class Checkbox
extends AbstractButton {
    private static final ResourceLocation f_93821_ = new ResourceLocation("textures/gui/checkbox.png");
    private static final int f_168844_ = 0xE0E0E0;
    private boolean f_93822_;
    private final boolean f_93823_;

    public Checkbox(int p_93826_, int p_93827_, int p_93828_, int p_93829_, Component p_93830_, boolean p_93831_) {
        this(p_93826_, p_93827_, p_93828_, p_93829_, p_93830_, p_93831_, true);
    }

    public Checkbox(int p_93833_, int p_93834_, int p_93835_, int p_93836_, Component p_93837_, boolean p_93838_, boolean p_93839_) {
        super(p_93833_, p_93834_, p_93835_, p_93836_, p_93837_);
        this.f_93822_ = p_93838_;
        this.f_93823_ = p_93839_;
    }

    @Override
    public void m_5691_() {
        this.f_93822_ = !this.f_93822_;
    }

    public boolean m_93840_() {
        return this.f_93822_;
    }

    @Override
    public void m_142291_(NarrationElementOutput p_168846_) {
        p_168846_.m_169146_(NarratedElementType.TITLE, this.m_5646_());
        if (this.f_93623_) {
            if (this.m_93696_()) {
                p_168846_.m_169146_(NarratedElementType.USAGE, Component.m_237115_("narration.checkbox.usage.focused"));
            } else {
                p_168846_.m_169146_(NarratedElementType.USAGE, Component.m_237115_("narration.checkbox.usage.hovered"));
            }
        }
    }

    @Override
    public void m_6303_(PoseStack p_93843_, int p_93844_, int p_93845_, float p_93846_) {
        Minecraft $$4 = Minecraft.m_91087_();
        RenderSystem.m_157456_(0, f_93821_);
        RenderSystem.m_69482_();
        Font $$5 = $$4.f_91062_;
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, this.f_93625_);
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69408_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        Checkbox.m_93133_(p_93843_, this.f_93620_, this.f_93621_, this.m_93696_() ? 20.0f : 0.0f, this.f_93822_ ? 20.0f : 0.0f, 20, this.f_93619_, 64, 64);
        this.m_7906_(p_93843_, $$4, p_93844_, p_93845_);
        if (this.f_93823_) {
            Checkbox.m_93243_(p_93843_, $$5, this.m_6035_(), this.f_93620_ + 24, this.f_93621_ + (this.f_93619_ - 8) / 2, 0xE0E0E0 | Mth.m_14167_(this.f_93625_ * 255.0f) << 24);
        }
    }
}

