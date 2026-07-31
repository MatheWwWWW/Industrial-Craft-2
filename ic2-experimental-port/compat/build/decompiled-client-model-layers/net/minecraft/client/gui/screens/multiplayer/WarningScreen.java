/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.multiplayer;

import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class WarningScreen
extends Screen {
    private final Component f_210912_;
    @Nullable
    private final Component f_210913_;
    private final Component f_210914_;
    @Nullable
    protected Checkbox f_210910_;
    private MultiLineLabel f_210915_ = MultiLineLabel.f_94331_;

    protected WarningScreen(Component p_239894_, Component p_239895_, Component p_239896_) {
        this(p_239894_, p_239895_, null, p_239896_);
    }

    protected WarningScreen(Component p_232852_, Component p_232853_, @Nullable Component p_232854_, Component p_232855_) {
        super(p_232852_);
        this.f_210912_ = p_232853_;
        this.f_210913_ = p_232854_;
        this.f_210914_ = p_232855_;
    }

    protected abstract void m_207212_(int var1);

    @Override
    protected void m_7856_() {
        super.m_7856_();
        this.f_210915_ = MultiLineLabel.m_94341_(this.f_96547_, this.f_210912_, this.f_96543_ - 100);
        int $$0 = (this.f_210915_.m_5770_() + 1) * this.m_214169_();
        if (this.f_210913_ != null) {
            int $$1 = this.f_96547_.m_92852_(this.f_210913_);
            this.f_210910_ = new Checkbox(this.f_96543_ / 2 - $$1 / 2 - 8, 76 + $$0, $$1 + 24, 20, this.f_210913_, false);
            this.m_142416_(this.f_210910_);
        }
        this.m_207212_($$0);
    }

    @Override
    public Component m_142562_() {
        return this.f_210914_;
    }

    @Override
    public void m_6305_(PoseStack p_210924_, int p_210925_, int p_210926_, float p_210927_) {
        this.m_7333_(p_210924_);
        this.m_239056_(p_210924_);
        int $$4 = this.f_96543_ / 2 - this.f_210915_.m_214161_() / 2;
        this.f_210915_.m_6516_(p_210924_, $$4, 70, this.m_214169_(), 0xFFFFFF);
        super.m_6305_(p_210924_, p_210925_, p_210926_, p_210927_);
    }

    protected void m_239056_(PoseStack p_239251_) {
        WarningScreen.m_93243_(p_239251_, this.f_96547_, this.f_96539_, 25, 30, 0xFFFFFF);
    }

    protected int m_214169_() {
        return this.f_96547_.f_92710_ * 2;
    }
}

