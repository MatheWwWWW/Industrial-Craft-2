/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsPlayerScreen;
import javax.annotation.Nullable;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.realms.RealmsScreen;
import org.slf4j.Logger;

public class RealmsInviteScreen
extends RealmsScreen {
    private static final Logger f_88693_ = LogUtils.getLogger();
    private static final Component f_88694_ = Component.m_237115_("mco.configure.world.invite.profile.name");
    private static final Component f_88695_ = Component.m_237115_("mco.configure.world.players.error");
    private EditBox f_88696_;
    private final RealmsServer f_88697_;
    private final RealmsConfigureWorldScreen f_88698_;
    private final Screen f_88699_;
    @Nullable
    private Component f_88700_;

    public RealmsInviteScreen(RealmsConfigureWorldScreen p_88703_, Screen p_88704_, RealmsServer p_88705_) {
        super(GameNarrator.f_93310_);
        this.f_88698_ = p_88703_;
        this.f_88699_ = p_88704_;
        this.f_88697_ = p_88705_;
    }

    @Override
    public void m_86600_() {
        this.f_88696_.m_94120_();
    }

    @Override
    public void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_88696_ = new EditBox(this.f_96541_.f_91062_, this.f_96543_ / 2 - 100, RealmsInviteScreen.m_120774_(2), 200, 20, null, Component.m_237115_("mco.configure.world.invite.profile.name"));
        this.m_7787_(this.f_88696_);
        this.m_94718_(this.f_88696_);
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, RealmsInviteScreen.m_120774_(10), 200, 20, Component.m_237115_("mco.configure.world.buttons.invite"), p_88721_ -> this.m_88724_()));
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, RealmsInviteScreen.m_120774_(12), 200, 20, CommonComponents.f_130656_, p_88716_ -> this.f_96541_.m_91152_(this.f_88699_)));
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    private void m_88724_() {
        RealmsClient $$0 = RealmsClient.m_87169_();
        if (this.f_88696_.m_94155_() == null || this.f_88696_.m_94155_().isEmpty()) {
            this.m_88717_(f_88695_);
            return;
        }
        try {
            RealmsServer $$1 = $$0.m_87212_(this.f_88697_.f_87473_, this.f_88696_.m_94155_().trim());
            if ($$1 != null) {
                this.f_88697_.f_87480_ = $$1.f_87480_;
                this.f_96541_.m_91152_(new RealmsPlayerScreen(this.f_88698_, this.f_88697_));
            } else {
                this.m_88717_(f_88695_);
            }
        }
        catch (Exception $$2) {
            f_88693_.error("Couldn't invite user");
            this.m_88717_(f_88695_);
        }
    }

    private void m_88717_(Component p_88718_) {
        this.f_88700_ = p_88718_;
        this.f_96541_.m_240477_().m_168785_(p_88718_);
    }

    @Override
    public boolean m_7933_(int p_88707_, int p_88708_, int p_88709_) {
        if (p_88707_ == 256) {
            this.f_96541_.m_91152_(this.f_88699_);
            return true;
        }
        return super.m_7933_(p_88707_, p_88708_, p_88709_);
    }

    @Override
    public void m_6305_(PoseStack p_88711_, int p_88712_, int p_88713_, float p_88714_) {
        this.m_7333_(p_88711_);
        this.f_96547_.m_92889_(p_88711_, f_88694_, this.f_96543_ / 2 - 100, RealmsInviteScreen.m_120774_(1), 0xA0A0A0);
        if (this.f_88700_ != null) {
            RealmsInviteScreen.m_93215_(p_88711_, this.f_96547_, this.f_88700_, this.f_96543_ / 2, RealmsInviteScreen.m_120774_(5), 0xFF0000);
        }
        this.f_88696_.m_6305_(p_88711_, p_88712_, p_88713_, p_88714_);
        super.m_6305_(p_88711_, p_88712_, p_88713_, p_88714_);
    }
}

