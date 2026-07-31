/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DatapackLoadFailureScreen
extends Screen {
    private MultiLineLabel f_95891_ = MultiLineLabel.f_94331_;
    private final Runnable f_95892_;

    public DatapackLoadFailureScreen(Runnable p_95894_) {
        super(Component.m_237115_("datapackFailure.title"));
        this.f_95892_ = p_95894_;
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        this.f_95891_ = MultiLineLabel.m_94341_(this.f_96547_, this.m_96636_(), this.f_96543_ - 50);
        this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ / 6 + 96, 150, 20, Component.m_237115_("datapackFailure.safeMode"), p_95905_ -> this.f_95892_.run()));
        this.m_142416_(new Button(this.f_96543_ / 2 - 155 + 160, this.f_96544_ / 6 + 96, 150, 20, Component.m_237115_("gui.toTitle"), p_95901_ -> this.f_96541_.m_91152_(null)));
    }

    @Override
    public void m_6305_(PoseStack p_95896_, int p_95897_, int p_95898_, float p_95899_) {
        this.m_7333_(p_95896_);
        this.f_95891_.m_6276_(p_95896_, this.f_96543_ / 2, 70);
        super.m_6305_(p_95896_, p_95897_, p_95898_, p_95899_);
    }

    @Override
    public boolean m_6913_() {
        return false;
    }
}

