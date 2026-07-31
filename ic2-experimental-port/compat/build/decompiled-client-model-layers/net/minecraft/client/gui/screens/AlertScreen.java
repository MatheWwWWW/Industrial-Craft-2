/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class AlertScreen
extends Screen {
    private static final int f_238636_ = 90;
    private final Component f_238618_;
    private MultiLineLabel f_95516_ = MultiLineLabel.f_94331_;
    private final Runnable f_95515_;
    private final Component f_95514_;
    private final boolean f_238724_;

    public AlertScreen(Runnable p_95519_, Component p_95520_, Component p_95521_) {
        this(p_95519_, p_95520_, p_95521_, CommonComponents.f_130660_, true);
    }

    public AlertScreen(Runnable p_239327_, Component p_239328_, Component p_239329_, Component p_239330_, boolean p_239331_) {
        super(p_239328_);
        this.f_95515_ = p_239327_;
        this.f_238618_ = p_239329_;
        this.f_95514_ = p_239330_;
        this.f_238724_ = p_239331_;
    }

    @Override
    public Component m_142562_() {
        return CommonComponents.m_178398_(super.m_142562_(), this.f_238618_);
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        this.f_95516_ = MultiLineLabel.m_94341_(this.f_96547_, this.f_238618_, this.f_96543_ - 50);
        int $$0 = this.f_95516_.m_5770_() * this.f_96547_.f_92710_;
        int $$1 = Mth.m_14045_(90 + $$0 + 12, this.f_96544_ / 6 + 96, this.f_96544_ - 24);
        int $$2 = 150;
        this.m_142416_(new Button((this.f_96543_ - 150) / 2, $$1, 150, 20, this.f_95514_, p_95533_ -> this.f_95515_.run()));
    }

    @Override
    public void m_6305_(PoseStack p_95528_, int p_95529_, int p_95530_, float p_95531_) {
        this.m_7333_(p_95528_);
        AlertScreen.m_93215_(p_95528_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 70, 0xFFFFFF);
        this.f_95516_.m_6276_(p_95528_, this.f_96543_ / 2, 90);
        super.m_6305_(p_95528_, p_95529_, p_95530_, p_95531_);
    }

    @Override
    public boolean m_6913_() {
        return this.f_238724_;
    }
}

