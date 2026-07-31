/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.realms.RealmsScreen;

public class RealmsClientOutdatedScreen
extends RealmsScreen {
    private static final Component f_88360_ = Component.m_237115_("mco.client.incompatible.title");
    private static final Component[] f_231302_ = new Component[]{Component.m_237115_("mco.client.incompatible.msg.line1"), Component.m_237115_("mco.client.incompatible.msg.line2"), Component.m_237115_("mco.client.incompatible.msg.line3")};
    private static final Component[] f_88361_ = new Component[]{Component.m_237115_("mco.client.incompatible.msg.line1"), Component.m_237115_("mco.client.incompatible.msg.line2")};
    private final Screen f_88362_;

    public RealmsClientOutdatedScreen(Screen p_231304_) {
        super(f_88360_);
        this.f_88362_ = p_231304_;
    }

    @Override
    public void m_7856_() {
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, RealmsClientOutdatedScreen.m_120774_(12), 200, 20, CommonComponents.f_130660_, p_88378_ -> this.f_96541_.m_91152_(this.f_88362_)));
    }

    @Override
    public void m_6305_(PoseStack p_88373_, int p_88374_, int p_88375_, float p_88376_) {
        this.m_7333_(p_88373_);
        RealmsClientOutdatedScreen.m_93215_(p_88373_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, RealmsClientOutdatedScreen.m_120774_(3), 0xFF0000);
        Component[] $$4 = this.m_231305_();
        for (int $$5 = 0; $$5 < $$4.length; ++$$5) {
            RealmsClientOutdatedScreen.m_93215_(p_88373_, this.f_96547_, $$4[$$5], this.f_96543_ / 2, RealmsClientOutdatedScreen.m_120774_(5) + $$5 * 12, 0xFFFFFF);
        }
        super.m_6305_(p_88373_, p_88374_, p_88375_, p_88376_);
    }

    private Component[] m_231305_() {
        if (this.f_96541_.m_91309_().getVersion().isStable()) {
            return f_88361_;
        }
        return f_231302_;
    }

    @Override
    public boolean m_7933_(int p_88369_, int p_88370_, int p_88371_) {
        if (p_88369_ == 257 || p_88369_ == 335 || p_88369_ == 256) {
            this.f_96541_.m_91152_(this.f_88362_);
            return true;
        }
        return super.m_7933_(p_88369_, p_88370_, p_88371_);
    }
}

