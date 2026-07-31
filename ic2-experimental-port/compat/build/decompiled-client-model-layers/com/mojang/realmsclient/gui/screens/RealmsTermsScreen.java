/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.util.task.GetServerDetailsTask;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.realms.RealmsScreen;
import org.slf4j.Logger;

public class RealmsTermsScreen
extends RealmsScreen {
    private static final Logger f_90022_ = LogUtils.getLogger();
    private static final Component f_90023_ = Component.m_237115_("mco.terms.title");
    private static final Component f_90024_ = Component.m_237115_("mco.terms.sentence.1");
    private static final Component f_90025_ = Component.m_237113_(" ").m_7220_(Component.m_237115_("mco.terms.sentence.2").m_130948_(Style.f_131099_.m_131162_(true)));
    private final Screen f_90026_;
    private final RealmsMainScreen f_90027_;
    private final RealmsServer f_90028_;
    private boolean f_90029_;
    private final String f_90030_ = "https://aka.ms/MinecraftRealmsTerms";

    public RealmsTermsScreen(Screen p_90033_, RealmsMainScreen p_90034_, RealmsServer p_90035_) {
        super(f_90023_);
        this.f_90026_ = p_90033_;
        this.f_90027_ = p_90034_;
        this.f_90028_ = p_90035_;
    }

    @Override
    public void m_7856_() {
        this.f_96541_.f_91068_.m_90926_(true);
        int $$0 = this.f_96543_ / 4 - 2;
        this.m_142416_(new Button(this.f_96543_ / 4, RealmsTermsScreen.m_120774_(12), $$0, 20, Component.m_237115_("mco.terms.buttons.agree"), p_90054_ -> this.m_90056_()));
        this.m_142416_(new Button(this.f_96543_ / 2 + 4, RealmsTermsScreen.m_120774_(12), $$0, 20, Component.m_237115_("mco.terms.buttons.disagree"), p_90050_ -> this.f_96541_.m_91152_(this.f_90026_)));
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    @Override
    public boolean m_7933_(int p_90041_, int p_90042_, int p_90043_) {
        if (p_90041_ == 256) {
            this.f_96541_.m_91152_(this.f_90026_);
            return true;
        }
        return super.m_7933_(p_90041_, p_90042_, p_90043_);
    }

    private void m_90056_() {
        RealmsClient $$0 = RealmsClient.m_87169_();
        try {
            $$0.m_87262_();
            this.f_96541_.m_91152_(new RealmsLongRunningMcoTaskScreen(this.f_90026_, new GetServerDetailsTask(this.f_90027_, this.f_90026_, this.f_90028_, new ReentrantLock())));
        }
        catch (RealmsServiceException $$1) {
            f_90022_.error("Couldn't agree to TOS");
        }
    }

    @Override
    public boolean m_6375_(double p_90037_, double p_90038_, int p_90039_) {
        if (this.f_90029_) {
            this.f_96541_.f_91068_.m_90911_("https://aka.ms/MinecraftRealmsTerms");
            Util.m_137581_().m_137646_("https://aka.ms/MinecraftRealmsTerms");
            return true;
        }
        return super.m_6375_(p_90037_, p_90038_, p_90039_);
    }

    @Override
    public Component m_142562_() {
        return CommonComponents.m_178398_(super.m_142562_(), f_90024_).m_130946_(" ").m_7220_(f_90025_);
    }

    @Override
    public void m_6305_(PoseStack p_90045_, int p_90046_, int p_90047_, float p_90048_) {
        this.m_7333_(p_90045_);
        RealmsTermsScreen.m_93215_(p_90045_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 17, 0xFFFFFF);
        this.f_96547_.m_92889_(p_90045_, f_90024_, this.f_96543_ / 2 - 120, RealmsTermsScreen.m_120774_(5), 0xFFFFFF);
        int $$4 = this.f_96547_.m_92852_(f_90024_);
        int $$5 = this.f_96543_ / 2 - 121 + $$4;
        int $$6 = RealmsTermsScreen.m_120774_(5);
        int $$7 = $$5 + this.f_96547_.m_92852_(f_90025_) + 1;
        int $$8 = $$6 + 1 + this.f_96547_.f_92710_;
        this.f_90029_ = $$5 <= p_90046_ && p_90046_ <= $$7 && $$6 <= p_90047_ && p_90047_ <= $$8;
        this.f_96547_.m_92889_(p_90045_, f_90025_, this.f_96543_ / 2 - 120 + $$4, RealmsTermsScreen.m_120774_(5), this.f_90029_ ? 7107012 : 0x3366BB);
        super.m_6305_(p_90045_, p_90046_, p_90047_, p_90048_);
    }
}

