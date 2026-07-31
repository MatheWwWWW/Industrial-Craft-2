/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.realms.RealmsScreen;

public class RealmsParentalConsentScreen
extends RealmsScreen {
    private static final Component f_88856_ = Component.m_237115_("mco.account.privacyinfo");
    private final Screen f_88857_;
    private MultiLineLabel f_88858_ = MultiLineLabel.f_94331_;

    public RealmsParentalConsentScreen(Screen p_88861_) {
        super(GameNarrator.f_93310_);
        this.f_88857_ = p_88861_;
    }

    @Override
    public void m_7856_() {
        MutableComponent $$0 = Component.m_237115_("mco.account.update");
        Component $$1 = CommonComponents.f_130660_;
        int $$2 = Math.max(this.f_96547_.m_92852_($$0), this.f_96547_.m_92852_($$1)) + 30;
        MutableComponent $$3 = Component.m_237115_("mco.account.privacy.info");
        int $$4 = (int)((double)this.f_96547_.m_92852_($$3) * 1.2);
        this.m_142416_(new Button(this.f_96543_ / 2 - $$4 / 2, RealmsParentalConsentScreen.m_120774_(11), $$4, 20, $$3, p_88873_ -> Util.m_137581_().m_137646_("https://aka.ms/MinecraftGDPR")));
        this.m_142416_(new Button(this.f_96543_ / 2 - ($$2 + 5), RealmsParentalConsentScreen.m_120774_(13), $$2, 20, $$0, p_88871_ -> Util.m_137581_().m_137646_("https://aka.ms/UpdateMojangAccount")));
        this.m_142416_(new Button(this.f_96543_ / 2 + 5, RealmsParentalConsentScreen.m_120774_(13), $$2, 20, $$1, p_88868_ -> this.f_96541_.m_91152_(this.f_88857_)));
        this.f_88858_ = MultiLineLabel.m_94341_(this.f_96547_, f_88856_, (int)Math.round((double)this.f_96543_ * 0.9));
    }

    @Override
    public Component m_142562_() {
        return f_88856_;
    }

    @Override
    public void m_6305_(PoseStack p_88863_, int p_88864_, int p_88865_, float p_88866_) {
        this.m_7333_(p_88863_);
        this.f_88858_.m_6514_(p_88863_, this.f_96543_ / 2, 15, 15, 0xFFFFFF);
        super.m_6305_(p_88863_, p_88864_, p_88865_, p_88866_);
    }
}

