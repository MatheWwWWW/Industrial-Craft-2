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
import com.mojang.realmsclient.dto.Subscription;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.realms.RealmsScreen;
import org.slf4j.Logger;

public class RealmsSubscriptionInfoScreen
extends RealmsScreen {
    static final Logger f_89963_ = LogUtils.getLogger();
    private static final Component f_89964_ = Component.m_237115_("mco.configure.world.subscription.title");
    private static final Component f_89965_ = Component.m_237115_("mco.configure.world.subscription.start");
    private static final Component f_89966_ = Component.m_237115_("mco.configure.world.subscription.timeleft");
    private static final Component f_89967_ = Component.m_237115_("mco.configure.world.subscription.recurring.daysleft");
    private static final Component f_89968_ = Component.m_237115_("mco.configure.world.subscription.expired");
    private static final Component f_89969_ = Component.m_237115_("mco.configure.world.subscription.less_than_a_day");
    private static final Component f_89970_ = Component.m_237115_("mco.configure.world.subscription.month");
    private static final Component f_89971_ = Component.m_237115_("mco.configure.world.subscription.months");
    private static final Component f_89972_ = Component.m_237115_("mco.configure.world.subscription.day");
    private static final Component f_89973_ = Component.m_237115_("mco.configure.world.subscription.days");
    private static final Component f_182537_ = Component.m_237115_("mco.configure.world.subscription.unknown");
    private final Screen f_89974_;
    final RealmsServer f_89975_;
    final Screen f_89976_;
    private Component f_89960_ = f_182537_;
    private Component f_89961_ = f_182537_;
    @Nullable
    private Subscription.SubscriptionType f_89962_;
    private static final String f_167548_ = "https://aka.ms/ExtendJavaRealms";

    public RealmsSubscriptionInfoScreen(Screen p_89979_, RealmsServer p_89980_, Screen p_89981_) {
        super(GameNarrator.f_93310_);
        this.f_89974_ = p_89979_;
        this.f_89975_ = p_89980_;
        this.f_89976_ = p_89981_;
    }

    @Override
    public void m_7856_() {
        this.m_89989_(this.f_89975_.f_87473_);
        this.f_96541_.f_91068_.m_90926_(true);
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, RealmsSubscriptionInfoScreen.m_120774_(6), 200, 20, Component.m_237115_("mco.configure.world.subscription.extend"), p_90010_ -> {
            String $$1 = "https://aka.ms/ExtendJavaRealms?subscriptionId=" + this.f_89975_.f_87474_ + "&profileId=" + this.f_96541_.m_91094_().m_92545_();
            this.f_96541_.f_91068_.m_90911_($$1);
            Util.m_137581_().m_137646_($$1);
        }));
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, RealmsSubscriptionInfoScreen.m_120774_(12), 200, 20, CommonComponents.f_130660_, p_90006_ -> this.f_96541_.m_91152_(this.f_89974_)));
        if (this.f_89975_.f_87482_) {
            this.m_142416_(new Button(this.f_96543_ / 2 - 100, RealmsSubscriptionInfoScreen.m_120774_(10), 200, 20, Component.m_237115_("mco.configure.world.delete.button"), p_89999_ -> {
                MutableComponent $$1 = Component.m_237115_("mco.configure.world.delete.question.line1");
                MutableComponent $$2 = Component.m_237115_("mco.configure.world.delete.question.line2");
                this.f_96541_.m_91152_(new RealmsLongConfirmationScreen(this::m_90011_, RealmsLongConfirmationScreen.Type.Warning, $$1, $$2, true));
            }));
        }
    }

    @Override
    public Component m_142562_() {
        return CommonComponents.m_178396_(f_89964_, f_89965_, this.f_89961_, f_89966_, this.f_89960_);
    }

    private void m_90011_(boolean p_90012_) {
        if (p_90012_) {
            new Thread("Realms-delete-realm"){

                @Override
                public void run() {
                    try {
                        RealmsClient $$0 = RealmsClient.m_87169_();
                        $$0.m_87254_(RealmsSubscriptionInfoScreen.this.f_89975_.f_87473_);
                    }
                    catch (RealmsServiceException $$1) {
                        f_89963_.error("Couldn't delete world", (Throwable)$$1);
                    }
                    RealmsSubscriptionInfoScreen.this.f_96541_.execute(() -> RealmsSubscriptionInfoScreen.this.f_96541_.m_91152_(RealmsSubscriptionInfoScreen.this.f_89976_));
                }
            }.start();
        }
        this.f_96541_.m_91152_(this);
    }

    private void m_89989_(long p_89990_) {
        RealmsClient $$1 = RealmsClient.m_87169_();
        try {
            Subscription $$2 = $$1.m_87248_(p_89990_);
            this.f_89960_ = this.m_89983_($$2.f_87667_);
            this.f_89961_ = RealmsSubscriptionInfoScreen.m_182538_($$2.f_87666_);
            this.f_89962_ = $$2.f_87668_;
        }
        catch (RealmsServiceException $$3) {
            f_89963_.error("Couldn't get subscription");
            this.f_96541_.m_91152_(new RealmsGenericErrorScreen($$3, this.f_89974_));
        }
    }

    private static Component m_182538_(long p_182539_) {
        GregorianCalendar $$1 = new GregorianCalendar(TimeZone.getDefault());
        $$1.setTimeInMillis(p_182539_);
        return Component.m_237113_(DateFormat.getDateTimeInstance().format($$1.getTime()));
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    @Override
    public boolean m_7933_(int p_89986_, int p_89987_, int p_89988_) {
        if (p_89986_ == 256) {
            this.f_96541_.m_91152_(this.f_89974_);
            return true;
        }
        return super.m_7933_(p_89986_, p_89987_, p_89988_);
    }

    @Override
    public void m_6305_(PoseStack p_89992_, int p_89993_, int p_89994_, float p_89995_) {
        this.m_7333_(p_89992_);
        int $$4 = this.f_96543_ / 2 - 100;
        RealmsSubscriptionInfoScreen.m_93215_(p_89992_, this.f_96547_, f_89964_, this.f_96543_ / 2, 17, 0xFFFFFF);
        this.f_96547_.m_92889_(p_89992_, f_89965_, $$4, RealmsSubscriptionInfoScreen.m_120774_(0), 0xA0A0A0);
        this.f_96547_.m_92889_(p_89992_, this.f_89961_, $$4, RealmsSubscriptionInfoScreen.m_120774_(1), 0xFFFFFF);
        if (this.f_89962_ == Subscription.SubscriptionType.NORMAL) {
            this.f_96547_.m_92889_(p_89992_, f_89966_, $$4, RealmsSubscriptionInfoScreen.m_120774_(3), 0xA0A0A0);
        } else if (this.f_89962_ == Subscription.SubscriptionType.RECURRING) {
            this.f_96547_.m_92889_(p_89992_, f_89967_, $$4, RealmsSubscriptionInfoScreen.m_120774_(3), 0xA0A0A0);
        }
        this.f_96547_.m_92889_(p_89992_, this.f_89960_, $$4, RealmsSubscriptionInfoScreen.m_120774_(4), 0xFFFFFF);
        super.m_6305_(p_89992_, p_89993_, p_89994_, p_89995_);
    }

    private Component m_89983_(int p_89984_) {
        if (p_89984_ < 0 && this.f_89975_.f_87482_) {
            return f_89968_;
        }
        if (p_89984_ <= 1) {
            return f_89969_;
        }
        int $$1 = p_89984_ / 30;
        int $$2 = p_89984_ % 30;
        MutableComponent $$3 = Component.m_237119_();
        if ($$1 > 0) {
            $$3.m_130946_(Integer.toString($$1)).m_130946_(" ");
            if ($$1 == 1) {
                $$3.m_7220_(f_89970_);
            } else {
                $$3.m_7220_(f_89971_);
            }
        }
        if ($$2 > 0) {
            if ($$1 > 0) {
                $$3.m_130946_(", ");
            }
            $$3.m_130946_(Integer.toString($$2)).m_130946_(" ");
            if ($$2 == 1) {
                $$3.m_7220_(f_89972_);
            } else {
                $$3.m_7220_(f_89973_);
            }
        }
        return $$3;
    }
}

