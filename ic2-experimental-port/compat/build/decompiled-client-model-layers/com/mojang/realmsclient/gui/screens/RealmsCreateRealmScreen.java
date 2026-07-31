/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsResetWorldScreen;
import com.mojang.realmsclient.util.task.WorldCreationTask;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.realms.RealmsScreen;

public class RealmsCreateRealmScreen
extends RealmsScreen {
    private static final Component f_88564_ = Component.m_237115_("mco.configure.world.name");
    private static final Component f_88565_ = Component.m_237115_("mco.configure.world.description");
    private final RealmsServer f_88566_;
    private final RealmsMainScreen f_88567_;
    private EditBox f_88568_;
    private EditBox f_88569_;
    private Button f_88570_;

    public RealmsCreateRealmScreen(RealmsServer p_88574_, RealmsMainScreen p_88575_) {
        super(Component.m_237115_("mco.selectServer.create"));
        this.f_88566_ = p_88574_;
        this.f_88567_ = p_88575_;
    }

    @Override
    public void m_86600_() {
        if (this.f_88568_ != null) {
            this.f_88568_.m_94120_();
        }
        if (this.f_88569_ != null) {
            this.f_88569_.m_94120_();
        }
    }

    @Override
    public void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_88570_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 120 + 17, 97, 20, Component.m_237115_("mco.create.world"), p_88592_ -> this.m_88595_()));
        this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ / 4 + 120 + 17, 95, 20, CommonComponents.f_130656_, p_88589_ -> this.f_96541_.m_91152_(this.f_88567_)));
        this.f_88570_.f_93623_ = false;
        this.f_88568_ = new EditBox(this.f_96541_.f_91062_, this.f_96543_ / 2 - 100, 65, 200, 20, null, Component.m_237115_("mco.configure.world.name"));
        this.m_7787_(this.f_88568_);
        this.m_94718_(this.f_88568_);
        this.f_88569_ = new EditBox(this.f_96541_.f_91062_, this.f_96543_ / 2 - 100, 115, 200, 20, null, Component.m_237115_("mco.configure.world.description"));
        this.m_7787_(this.f_88569_);
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    @Override
    public boolean m_5534_(char p_88577_, int p_88578_) {
        boolean $$2 = super.m_5534_(p_88577_, p_88578_);
        this.f_88570_.f_93623_ = this.m_88596_();
        return $$2;
    }

    @Override
    public boolean m_7933_(int p_88580_, int p_88581_, int p_88582_) {
        if (p_88580_ == 256) {
            this.f_96541_.m_91152_(this.f_88567_);
            return true;
        }
        boolean $$3 = super.m_7933_(p_88580_, p_88581_, p_88582_);
        this.f_88570_.f_93623_ = this.m_88596_();
        return $$3;
    }

    private void m_88595_() {
        if (this.m_88596_()) {
            RealmsResetWorldScreen $$0 = new RealmsResetWorldScreen(this.f_88567_, this.f_88566_, Component.m_237115_("mco.selectServer.create"), Component.m_237115_("mco.create.world.subtitle"), 0xA0A0A0, Component.m_237115_("mco.create.world.skip"), () -> this.f_96541_.execute(() -> this.f_96541_.m_91152_(this.f_88567_.m_86660_())), () -> this.f_96541_.m_91152_(this.f_88567_.m_86660_()));
            $$0.m_89389_(Component.m_237115_("mco.create.world.reset.title"));
            this.f_96541_.m_91152_(new RealmsLongRunningMcoTaskScreen(this.f_88567_, new WorldCreationTask(this.f_88566_.f_87473_, this.f_88568_.m_94155_(), this.f_88569_.m_94155_(), $$0)));
        }
    }

    private boolean m_88596_() {
        return !this.f_88568_.m_94155_().trim().isEmpty();
    }

    @Override
    public void m_6305_(PoseStack p_88584_, int p_88585_, int p_88586_, float p_88587_) {
        this.m_7333_(p_88584_);
        RealmsCreateRealmScreen.m_93215_(p_88584_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 11, 0xFFFFFF);
        this.f_96547_.m_92889_(p_88584_, f_88564_, this.f_96543_ / 2 - 100, 52.0f, 0xA0A0A0);
        this.f_96547_.m_92889_(p_88584_, f_88565_, this.f_96543_ / 2 - 100, 102.0f, 0xA0A0A0);
        if (this.f_88568_ != null) {
            this.f_88568_.m_6305_(p_88584_, p_88585_, p_88586_, p_88587_);
        }
        if (this.f_88569_ != null) {
            this.f_88569_.m_6305_(p_88584_, p_88585_, p_88586_, p_88587_);
        }
        super.m_6305_(p_88584_, p_88585_, p_88586_, p_88587_);
    }
}

