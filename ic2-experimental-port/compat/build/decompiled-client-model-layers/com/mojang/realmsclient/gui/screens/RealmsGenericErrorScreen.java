/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.realmsclient.exception.RealmsServiceException;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.realms.RealmsScreen;

public class RealmsGenericErrorScreen
extends RealmsScreen {
    private final Screen f_88665_;
    private final Pair<Component, Component> f_200947_;
    private MultiLineLabel f_200948_ = MultiLineLabel.f_94331_;

    public RealmsGenericErrorScreen(RealmsServiceException p_88669_, Screen p_88670_) {
        super(GameNarrator.f_93310_);
        this.f_88665_ = p_88670_;
        this.f_200947_ = RealmsGenericErrorScreen.m_200949_(p_88669_);
    }

    public RealmsGenericErrorScreen(Component p_88672_, Screen p_88673_) {
        super(GameNarrator.f_93310_);
        this.f_88665_ = p_88673_;
        this.f_200947_ = RealmsGenericErrorScreen.m_200951_(p_88672_);
    }

    public RealmsGenericErrorScreen(Component p_88675_, Component p_88676_, Screen p_88677_) {
        super(GameNarrator.f_93310_);
        this.f_88665_ = p_88677_;
        this.f_200947_ = RealmsGenericErrorScreen.m_200953_(p_88675_, p_88676_);
    }

    private static Pair<Component, Component> m_200949_(RealmsServiceException p_200950_) {
        if (p_200950_.f_200941_ == null) {
            return Pair.of((Object)Component.m_237113_("An error occurred (" + p_200950_.f_87773_ + "):"), (Object)Component.m_237113_(p_200950_.f_200940_));
        }
        String $$1 = "mco.errorMessage." + p_200950_.f_200941_.m_87305_();
        return Pair.of((Object)Component.m_237113_("Realms (" + p_200950_.f_200941_ + "):"), (Object)(I18n.m_118936_($$1) ? Component.m_237115_($$1) : Component.m_130674_(p_200950_.f_200941_.m_87302_())));
    }

    private static Pair<Component, Component> m_200951_(Component p_200952_) {
        return Pair.of((Object)Component.m_237113_("An error occurred: "), (Object)p_200952_);
    }

    private static Pair<Component, Component> m_200953_(Component p_200954_, Component p_200955_) {
        return Pair.of((Object)p_200954_, (Object)p_200955_);
    }

    @Override
    public void m_7856_() {
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 52, 200, 20, Component.m_237113_("Ok"), p_88686_ -> this.f_96541_.m_91152_(this.f_88665_)));
        this.f_200948_ = MultiLineLabel.m_94341_(this.f_96547_, (FormattedText)this.f_200947_.getSecond(), this.f_96543_ * 3 / 4);
    }

    @Override
    public Component m_142562_() {
        return Component.m_237119_().m_7220_((Component)this.f_200947_.getFirst()).m_130946_(": ").m_7220_((Component)this.f_200947_.getSecond());
    }

    @Override
    public void m_6305_(PoseStack p_88679_, int p_88680_, int p_88681_, float p_88682_) {
        this.m_7333_(p_88679_);
        RealmsGenericErrorScreen.m_93215_(p_88679_, this.f_96547_, (Component)this.f_200947_.getFirst(), this.f_96543_ / 2, 80, 0xFFFFFF);
        this.f_200948_.m_6514_(p_88679_, this.f_96543_ / 2, 100, this.f_96541_.f_91062_.f_92710_, 0xFF0000);
        super.m_6305_(p_88679_, p_88680_, p_88681_, p_88682_);
    }
}

