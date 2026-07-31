/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.realms;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.realms.RealmsScreen;

public class DisconnectedRealmsScreen
extends RealmsScreen {
    private final Component f_120648_;
    private MultiLineLabel f_120649_ = MultiLineLabel.f_94331_;
    private final Screen f_120650_;
    private int f_120651_;

    public DisconnectedRealmsScreen(Screen p_120653_, Component p_120654_, Component p_120655_) {
        super(p_120654_);
        this.f_120650_ = p_120653_;
        this.f_120648_ = p_120655_;
    }

    @Override
    public void m_7856_() {
        Minecraft $$0 = Minecraft.m_91087_();
        $$0.m_91372_(false);
        $$0.m_91100_().m_235009_();
        this.f_120649_ = MultiLineLabel.m_94341_(this.f_96547_, this.f_120648_, this.f_96543_ - 50);
        this.f_120651_ = this.f_120649_.m_5770_() * this.f_96547_.f_92710_;
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 2 + this.f_120651_ / 2 + this.f_96547_.f_92710_, 200, 20, CommonComponents.f_130660_, p_120663_ -> $$0.m_91152_(this.f_120650_)));
    }

    @Override
    public Component m_142562_() {
        return Component.m_237119_().m_7220_(this.f_96539_).m_130946_(": ").m_7220_(this.f_120648_);
    }

    @Override
    public void m_7379_() {
        Minecraft.m_91087_().m_91152_(this.f_120650_);
    }

    @Override
    public void m_6305_(PoseStack p_120657_, int p_120658_, int p_120659_, float p_120660_) {
        this.m_7333_(p_120657_);
        DisconnectedRealmsScreen.m_93215_(p_120657_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, this.f_96544_ / 2 - this.f_120651_ / 2 - this.f_96547_.f_92710_ * 2, 0xAAAAAA);
        this.f_120649_.m_6276_(p_120657_, this.f_96543_ / 2, this.f_96544_ / 2 - this.f_120651_ / 2);
        super.m_6305_(p_120657_, p_120658_, p_120659_, p_120660_);
    }
}

