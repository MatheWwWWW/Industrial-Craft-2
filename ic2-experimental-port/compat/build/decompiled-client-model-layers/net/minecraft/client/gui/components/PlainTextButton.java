/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;

public class PlainTextButton
extends Button {
    private final Font f_211751_;
    private final Component f_211752_;
    private final Component f_211753_;

    public PlainTextButton(int p_211755_, int p_211756_, int p_211757_, int p_211758_, Component p_211759_, Button.OnPress p_211760_, Font p_211761_) {
        super(p_211755_, p_211756_, p_211757_, p_211758_, p_211759_, p_211760_);
        this.f_211751_ = p_211761_;
        this.f_211752_ = p_211759_;
        this.f_211753_ = ComponentUtils.m_130750_(p_211759_.m_6881_(), Style.f_131099_.m_131162_(true));
    }

    @Override
    public void m_6303_(PoseStack p_211763_, int p_211764_, int p_211765_, float p_211766_) {
        Component $$4 = this.m_198029_() ? this.f_211753_ : this.f_211752_;
        PlainTextButton.m_93243_(p_211763_, this.f_211751_, $$4, this.f_93620_, this.f_93621_, 0xFFFFFF | Mth.m_14167_(this.f_93625_ * 255.0f) << 24);
    }
}

