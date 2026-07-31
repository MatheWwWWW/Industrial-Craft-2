/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.booleans.BooleanConsumer
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.realms.RealmsScreen;

public class RealmsConfirmScreen
extends RealmsScreen {
    protected BooleanConsumer f_88545_;
    private final Component f_88546_;
    private final Component f_88547_;

    public RealmsConfirmScreen(BooleanConsumer p_88550_, Component p_88551_, Component p_88552_) {
        super(GameNarrator.f_93310_);
        this.f_88545_ = p_88550_;
        this.f_88546_ = p_88551_;
        this.f_88547_ = p_88552_;
    }

    @Override
    public void m_7856_() {
        this.m_142416_(new Button(this.f_96543_ / 2 - 105, RealmsConfirmScreen.m_120774_(9), 100, 20, CommonComponents.f_130657_, p_88562_ -> this.f_88545_.accept(true)));
        this.m_142416_(new Button(this.f_96543_ / 2 + 5, RealmsConfirmScreen.m_120774_(9), 100, 20, CommonComponents.f_130658_, p_88559_ -> this.f_88545_.accept(false)));
    }

    @Override
    public void m_6305_(PoseStack p_88554_, int p_88555_, int p_88556_, float p_88557_) {
        this.m_7333_(p_88554_);
        RealmsConfirmScreen.m_93215_(p_88554_, this.f_96547_, this.f_88546_, this.f_96543_ / 2, RealmsConfirmScreen.m_120774_(3), 0xFFFFFF);
        RealmsConfirmScreen.m_93215_(p_88554_, this.f_96547_, this.f_88547_, this.f_96543_ / 2, RealmsConfirmScreen.m_120774_(5), 0xFFFFFF);
        super.m_6305_(p_88554_, p_88555_, p_88556_, p_88557_);
    }
}

