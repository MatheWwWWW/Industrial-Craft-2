/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.WorldTemplate;
import com.mojang.realmsclient.dto.WorldTemplatePaginatedList;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsResetNormalWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsSelectFileToUploadScreen;
import com.mojang.realmsclient.gui.screens.RealmsSelectWorldTemplateScreen;
import com.mojang.realmsclient.util.WorldGenerationInfo;
import com.mojang.realmsclient.util.task.LongRunningTask;
import com.mojang.realmsclient.util.task.ResettingGeneratedWorldTask;
import com.mojang.realmsclient.util.task.ResettingTemplateWorldTask;
import com.mojang.realmsclient.util.task.SwitchSlotTask;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class RealmsResetWorldScreen
extends RealmsScreen {
    static final Logger f_89314_ = LogUtils.getLogger();
    private final Screen f_89315_;
    private final RealmsServer f_89316_;
    private Component f_89320_ = Component.m_237115_("mco.reset.world.warning");
    private Component f_89321_ = CommonComponents.f_130656_;
    private int f_89322_ = 0xFF0000;
    private static final ResourceLocation f_89323_ = new ResourceLocation("realms", "textures/gui/realms/slot_frame.png");
    private static final ResourceLocation f_89324_ = new ResourceLocation("realms", "textures/gui/realms/upload.png");
    private static final ResourceLocation f_89325_ = new ResourceLocation("realms", "textures/gui/realms/adventure.png");
    private static final ResourceLocation f_89326_ = new ResourceLocation("realms", "textures/gui/realms/survival_spawn.png");
    private static final ResourceLocation f_89300_ = new ResourceLocation("realms", "textures/gui/realms/new_world.png");
    private static final ResourceLocation f_89301_ = new ResourceLocation("realms", "textures/gui/realms/experience.png");
    private static final ResourceLocation f_89302_ = new ResourceLocation("realms", "textures/gui/realms/inspiration.png");
    WorldTemplatePaginatedList f_89303_;
    WorldTemplatePaginatedList f_89304_;
    WorldTemplatePaginatedList f_89305_;
    WorldTemplatePaginatedList f_89306_;
    public int f_89313_ = -1;
    private Component f_89310_ = Component.m_237115_("mco.reset.world.resetting.screen.title");
    private final Runnable f_89311_;
    private final Runnable f_89312_;

    public RealmsResetWorldScreen(Screen p_167448_, RealmsServer p_167449_, Component p_167450_, Runnable p_167451_, Runnable p_167452_) {
        super(p_167450_);
        this.f_89315_ = p_167448_;
        this.f_89316_ = p_167449_;
        this.f_89311_ = p_167451_;
        this.f_89312_ = p_167452_;
    }

    public RealmsResetWorldScreen(Screen p_89329_, RealmsServer p_89330_, Runnable p_89331_, Runnable p_89332_) {
        this(p_89329_, p_89330_, Component.m_237115_("mco.reset.world.title"), p_89331_, p_89332_);
    }

    public RealmsResetWorldScreen(Screen p_89334_, RealmsServer p_89335_, Component p_89336_, Component p_89337_, int p_89338_, Component p_89339_, Runnable p_89340_, Runnable p_89341_) {
        this(p_89334_, p_89335_, p_89336_, p_89340_, p_89341_);
        this.f_89320_ = p_89337_;
        this.f_89322_ = p_89338_;
        this.f_89321_ = p_89339_;
    }

    public void m_89343_(int p_89344_) {
        this.f_89313_ = p_89344_;
    }

    public void m_89389_(Component p_89390_) {
        this.f_89310_ = p_89390_;
    }

    @Override
    public void m_7856_() {
        this.m_142416_(new Button(this.f_96543_ / 2 - 40, RealmsResetWorldScreen.m_120774_(14) - 10, 80, 20, this.f_89321_, p_89419_ -> this.f_96541_.m_91152_(this.f_89315_)));
        new Thread("Realms-reset-world-fetcher"){

            @Override
            public void run() {
                RealmsClient $$0 = RealmsClient.m_87169_();
                try {
                    WorldTemplatePaginatedList $$1 = $$0.m_87170_(1, 10, RealmsServer.WorldType.NORMAL);
                    WorldTemplatePaginatedList $$2 = $$0.m_87170_(1, 10, RealmsServer.WorldType.ADVENTUREMAP);
                    WorldTemplatePaginatedList $$3 = $$0.m_87170_(1, 10, RealmsServer.WorldType.EXPERIENCE);
                    WorldTemplatePaginatedList $$4 = $$0.m_87170_(1, 10, RealmsServer.WorldType.INSPIRATION);
                    RealmsResetWorldScreen.this.f_96541_.execute(() -> {
                        RealmsResetWorldScreen.this.f_89303_ = $$1;
                        RealmsResetWorldScreen.this.f_89304_ = $$2;
                        RealmsResetWorldScreen.this.f_89305_ = $$3;
                        RealmsResetWorldScreen.this.f_89306_ = $$4;
                    });
                }
                catch (RealmsServiceException $$5) {
                    f_89314_.error("Couldn't fetch templates in reset world", (Throwable)$$5);
                }
            }
        }.start();
        this.m_175073_(new RealmsLabel(this.f_89320_, this.f_96543_ / 2, 22, this.f_89322_));
        this.m_142416_(new FrameButton(this.m_89392_(1), RealmsResetWorldScreen.m_120774_(0) + 10, Component.m_237115_("mco.reset.world.generate"), f_89300_, p_89417_ -> this.f_96541_.m_91152_(new RealmsResetNormalWorldScreen(this::m_167455_, this.f_96539_))));
        this.m_142416_(new FrameButton(this.m_89392_(2), RealmsResetWorldScreen.m_120774_(0) + 10, Component.m_237115_("mco.reset.world.upload"), f_89324_, p_89415_ -> this.f_96541_.m_91152_(new RealmsSelectFileToUploadScreen(this.f_89316_.f_87473_, this.f_89313_ != -1 ? this.f_89313_ : this.f_89316_.f_87486_, this, this.f_89312_))));
        this.m_142416_(new FrameButton(this.m_89392_(3), RealmsResetWorldScreen.m_120774_(0) + 10, Component.m_237115_("mco.reset.world.template"), f_89326_, p_89412_ -> this.f_96541_.m_91152_(new RealmsSelectWorldTemplateScreen(Component.m_237115_("mco.reset.world.template"), this::m_167453_, RealmsServer.WorldType.NORMAL, this.f_89303_))));
        this.m_142416_(new FrameButton(this.m_89392_(1), RealmsResetWorldScreen.m_120774_(6) + 20, Component.m_237115_("mco.reset.world.adventure"), f_89325_, p_89407_ -> this.f_96541_.m_91152_(new RealmsSelectWorldTemplateScreen(Component.m_237115_("mco.reset.world.adventure"), this::m_167453_, RealmsServer.WorldType.ADVENTUREMAP, this.f_89304_))));
        this.m_142416_(new FrameButton(this.m_89392_(2), RealmsResetWorldScreen.m_120774_(6) + 20, Component.m_237115_("mco.reset.world.experience"), f_89301_, p_89402_ -> this.f_96541_.m_91152_(new RealmsSelectWorldTemplateScreen(Component.m_237115_("mco.reset.world.experience"), this::m_167453_, RealmsServer.WorldType.EXPERIENCE, this.f_89305_))));
        this.m_142416_(new FrameButton(this.m_89392_(3), RealmsResetWorldScreen.m_120774_(6) + 20, Component.m_237115_("mco.reset.world.inspiration"), f_89302_, p_89381_ -> this.f_96541_.m_91152_(new RealmsSelectWorldTemplateScreen(Component.m_237115_("mco.reset.world.inspiration"), this::m_167453_, RealmsServer.WorldType.INSPIRATION, this.f_89306_))));
    }

    @Override
    public Component m_142562_() {
        return CommonComponents.m_178398_(this.m_96636_(), this.m_175075_());
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    @Override
    public boolean m_7933_(int p_89346_, int p_89347_, int p_89348_) {
        if (p_89346_ == 256) {
            this.f_96541_.m_91152_(this.f_89315_);
            return true;
        }
        return super.m_7933_(p_89346_, p_89347_, p_89348_);
    }

    private int m_89392_(int p_89393_) {
        return this.f_96543_ / 2 - 130 + (p_89393_ - 1) * 100;
    }

    @Override
    public void m_6305_(PoseStack p_89350_, int p_89351_, int p_89352_, float p_89353_) {
        this.m_7333_(p_89350_);
        RealmsResetWorldScreen.m_93215_(p_89350_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 7, 0xFFFFFF);
        super.m_6305_(p_89350_, p_89351_, p_89352_, p_89353_);
    }

    void m_89354_(PoseStack p_89355_, int p_89356_, int p_89357_, Component p_89358_, ResourceLocation p_89359_, boolean p_89360_, boolean p_89361_) {
        RenderSystem.m_157456_(0, p_89359_);
        if (p_89360_) {
            RenderSystem.m_157429_(0.56f, 0.56f, 0.56f, 1.0f);
        } else {
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        }
        GuiComponent.m_93133_(p_89355_, p_89356_ + 2, p_89357_ + 14, 0.0f, 0.0f, 56, 56, 56, 56);
        RenderSystem.m_157456_(0, f_89323_);
        if (p_89360_) {
            RenderSystem.m_157429_(0.56f, 0.56f, 0.56f, 1.0f);
        } else {
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        }
        GuiComponent.m_93133_(p_89355_, p_89356_, p_89357_ + 12, 0.0f, 0.0f, 60, 60, 60, 60);
        int $$7 = p_89360_ ? 0xA0A0A0 : 0xFFFFFF;
        RealmsResetWorldScreen.m_93215_(p_89355_, this.f_96547_, p_89358_, p_89356_ + 30, p_89357_, $$7);
    }

    private void m_167457_(LongRunningTask p_167458_) {
        this.f_96541_.m_91152_(new RealmsLongRunningMcoTaskScreen(this.f_89315_, p_167458_));
    }

    public void m_89382_(Runnable p_89383_) {
        this.m_167457_(new SwitchSlotTask(this.f_89316_.f_87473_, this.f_89313_, () -> this.f_96541_.execute(p_89383_)));
    }

    private void m_167453_(@Nullable WorldTemplate p_167454_) {
        this.f_96541_.m_91152_(this);
        if (p_167454_ != null) {
            this.m_167464_(() -> this.m_167457_(new ResettingTemplateWorldTask(p_167454_, this.f_89316_.f_87473_, this.f_89310_, this.f_89311_)));
        }
    }

    private void m_167455_(@Nullable WorldGenerationInfo p_167456_) {
        this.f_96541_.m_91152_(this);
        if (p_167456_ != null) {
            this.m_167464_(() -> this.m_167457_(new ResettingGeneratedWorldTask(p_167456_, this.f_89316_.f_87473_, this.f_89310_, this.f_89311_)));
        }
    }

    private void m_167464_(Runnable p_167465_) {
        if (this.f_89313_ == -1) {
            p_167465_.run();
        } else {
            this.m_89382_(p_167465_);
        }
    }

    class FrameButton
    extends Button {
        private final ResourceLocation f_89436_;

        public FrameButton(int p_89439_, int p_89440_, Component p_89441_, ResourceLocation p_89442_, Button.OnPress p_89443_) {
            super(p_89439_, p_89440_, 60, 72, p_89441_, p_89443_);
            this.f_89436_ = p_89442_;
        }

        @Override
        public void m_6303_(PoseStack p_89445_, int p_89446_, int p_89447_, float p_89448_) {
            RealmsResetWorldScreen.this.m_89354_(p_89445_, this.f_93620_, this.f_93621_, this.m_6035_(), this.f_89436_, this.m_198029_(), this.m_5953_(p_89446_, p_89447_));
        }
    }
}

