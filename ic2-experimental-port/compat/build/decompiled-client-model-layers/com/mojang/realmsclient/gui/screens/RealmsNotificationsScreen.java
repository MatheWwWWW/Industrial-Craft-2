/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsNews;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RealmsDataFetcher;
import com.mojang.realmsclient.gui.task.DataFetcher;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;

public class RealmsNotificationsScreen
extends RealmsScreen {
    private static final ResourceLocation f_88821_ = new ResourceLocation("realms", "textures/gui/realms/invite_icon.png");
    private static final ResourceLocation f_88822_ = new ResourceLocation("realms", "textures/gui/realms/trial_icon.png");
    private static final ResourceLocation f_88823_ = new ResourceLocation("realms", "textures/gui/realms/news_notification_mainscreen.png");
    @Nullable
    private DataFetcher.Subscription f_238623_;
    private volatile int f_88825_;
    static boolean f_88826_;
    private static boolean f_88827_;
    static boolean f_88828_;
    private static boolean f_88829_;

    public RealmsNotificationsScreen() {
        super(GameNarrator.f_93310_);
    }

    @Override
    public void m_7856_() {
        this.m_88850_();
        this.f_96541_.f_91068_.m_90926_(true);
        if (this.f_238623_ != null) {
            this.f_238623_.m_240009_();
        }
    }

    @Override
    public void m_86600_() {
        boolean $$0;
        boolean bl = $$0 = this.m_88848_() && this.m_88849_() && f_88828_;
        if (this.f_238623_ == null && $$0) {
            this.f_238623_ = this.m_88847_(this.f_96541_.m_239420_());
        } else if (this.f_238623_ != null && !$$0) {
            this.f_238623_ = null;
        }
        if (this.f_238623_ != null) {
            this.f_238623_.m_239355_();
        }
    }

    private DataFetcher.Subscription m_88847_(RealmsDataFetcher p_238855_) {
        DataFetcher.Subscription $$1 = p_238855_.f_238549_.m_239139_();
        $$1.m_239441_(p_238855_.f_238709_, p_239521_ -> {
            this.f_88825_ = p_239521_;
        });
        $$1.m_239441_(p_238855_.f_87799_, p_239494_ -> {
            f_88827_ = p_239494_;
        });
        $$1.m_239441_(p_238855_.f_238681_, p_238946_ -> {
            p_238945_.f_238737_.m_239190_((RealmsNews)p_238946_);
            f_88829_ = p_238945_.f_238737_.m_239499_();
        });
        return $$1;
    }

    private boolean m_88848_() {
        return this.f_96541_.f_91066_.m_231822_().m_231551_();
    }

    private boolean m_88849_() {
        return this.f_96541_.f_91080_ instanceof TitleScreen;
    }

    private void m_88850_() {
        if (!f_88826_) {
            f_88826_ = true;
            new Thread("Realms Notification Availability checker #1"){

                @Override
                public void run() {
                    RealmsClient $$0 = RealmsClient.m_87169_();
                    try {
                        RealmsClient.CompatibleVersionResponse $$1 = $$0.m_87259_();
                        if ($$1 != RealmsClient.CompatibleVersionResponse.COMPATIBLE) {
                            return;
                        }
                    }
                    catch (RealmsServiceException $$2) {
                        if ($$2.f_87773_ != 401) {
                            f_88826_ = false;
                        }
                        return;
                    }
                    f_88828_ = true;
                }
            }.start();
        }
    }

    @Override
    public void m_6305_(PoseStack p_88837_, int p_88838_, int p_88839_, float p_88840_) {
        if (f_88828_) {
            this.m_88832_(p_88837_, p_88838_, p_88839_);
        }
        super.m_6305_(p_88837_, p_88838_, p_88839_, p_88840_);
    }

    private void m_88832_(PoseStack p_88833_, int p_88834_, int p_88835_) {
        int $$3 = this.f_88825_;
        int $$4 = 24;
        int $$5 = this.f_96544_ / 4 + 48;
        int $$6 = this.f_96543_ / 2 + 80;
        int $$7 = $$5 + 48 + 2;
        int $$8 = 0;
        if (f_88829_) {
            RenderSystem.m_157456_(0, f_88823_);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            p_88833_.m_85836_();
            p_88833_.m_85841_(0.4f, 0.4f, 0.4f);
            GuiComponent.m_93133_(p_88833_, (int)((double)($$6 + 2 - $$8) * 2.5), (int)((double)$$7 * 2.5), 0.0f, 0.0f, 40, 40, 40, 40);
            p_88833_.m_85849_();
            $$8 += 14;
        }
        if ($$3 != 0) {
            RenderSystem.m_157456_(0, f_88821_);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            GuiComponent.m_93133_(p_88833_, $$6 - $$8, $$7 - 6, 0.0f, 0.0f, 15, 25, 31, 25);
            $$8 += 16;
        }
        if (f_88827_) {
            RenderSystem.m_157456_(0, f_88822_);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            int $$9 = 0;
            if ((Util.m_137550_() / 800L & 1L) == 1L) {
                $$9 = 8;
            }
            GuiComponent.m_93133_(p_88833_, $$6 + 4 - $$8, $$7 + 4, 0.0f, $$9, 8, 8, 8, 16);
        }
    }
}

