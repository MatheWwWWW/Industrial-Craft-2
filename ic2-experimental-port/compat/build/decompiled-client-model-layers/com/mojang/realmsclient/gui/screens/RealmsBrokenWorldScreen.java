/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RealmsWorldSlotButton;
import com.mojang.realmsclient.gui.screens.RealmsDownloadLatestWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsResetWorldScreen;
import com.mojang.realmsclient.util.RealmsTextureManager;
import com.mojang.realmsclient.util.task.OpenServerTask;
import com.mojang.realmsclient.util.task.SwitchSlotTask;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.Mth;
import org.slf4j.Logger;

public class RealmsBrokenWorldScreen
extends RealmsScreen {
    private static final Logger f_88283_ = LogUtils.getLogger();
    private static final int f_167363_ = 80;
    private final Screen f_88284_;
    private final RealmsMainScreen f_88285_;
    @Nullable
    private RealmsServer f_88286_;
    private final long f_88287_;
    private final Component[] f_88289_ = new Component[]{Component.m_237115_("mco.brokenworld.message.line1"), Component.m_237115_("mco.brokenworld.message.line2")};
    private int f_88290_;
    private int f_88291_;
    private final List<Integer> f_88292_ = Lists.newArrayList();
    private int f_88293_;

    public RealmsBrokenWorldScreen(Screen p_88296_, RealmsMainScreen p_88297_, long p_88298_, boolean p_88299_) {
        super(p_88299_ ? Component.m_237115_("mco.brokenworld.minigame.title") : Component.m_237115_("mco.brokenworld.title"));
        this.f_88284_ = p_88296_;
        this.f_88285_ = p_88297_;
        this.f_88287_ = p_88298_;
    }

    @Override
    public void m_7856_() {
        this.f_88290_ = this.f_96543_ / 2 - 150;
        this.f_88291_ = this.f_96543_ / 2 + 190;
        this.m_142416_(new Button(this.f_88291_ - 80 + 8, RealmsBrokenWorldScreen.m_120774_(13) - 5, 70, 20, CommonComponents.f_130660_, p_88333_ -> this.m_88351_()));
        if (this.f_88286_ == null) {
            this.m_88313_(this.f_88287_);
        } else {
            this.m_88350_();
        }
        this.f_96541_.f_91068_.m_90926_(true);
    }

    @Override
    public Component m_142562_() {
        return ComponentUtils.m_178433_(Stream.concat(Stream.of(this.f_96539_), Stream.of(this.f_88289_)).collect(Collectors.toList()), Component.m_237113_(" "));
    }

    private void m_88350_() {
        for (Map.Entry<Integer, RealmsWorldOptions> $$0 : this.f_88286_.f_87481_.entrySet()) {
            Button $$4;
            boolean $$2;
            int $$1 = $$0.getKey();
            boolean bl = $$2 = $$1 != this.f_88286_.f_87486_ || this.f_88286_.f_87485_ == RealmsServer.WorldType.MINIGAME;
            if ($$2) {
                Button $$3 = new Button(this.m_88301_($$1), RealmsBrokenWorldScreen.m_120774_(8), 80, 20, Component.m_237115_("mco.brokenworld.play"), p_88347_ -> {
                    if (this.f_88286_.f_87481_.get((Object)Integer.valueOf((int)p_88346_)).f_87611_) {
                        RealmsResetWorldScreen $$2 = new RealmsResetWorldScreen(this, this.f_88286_, Component.m_237115_("mco.configure.world.switch.slot"), Component.m_237115_("mco.configure.world.switch.slot.subtitle"), 0xA0A0A0, CommonComponents.f_130656_, this::m_88300_, () -> {
                            this.f_96541_.m_91152_(this);
                            this.m_88300_();
                        });
                        $$2.m_89343_($$1);
                        $$2.m_89389_(Component.m_237115_("mco.create.world.reset.title"));
                        this.f_96541_.m_91152_($$2);
                    } else {
                        this.f_96541_.m_91152_(new RealmsLongRunningMcoTaskScreen(this.f_88284_, new SwitchSlotTask(this.f_88286_.f_87473_, $$1, this::m_88300_)));
                    }
                });
            } else {
                $$4 = new Button(this.m_88301_($$1), RealmsBrokenWorldScreen.m_120774_(8), 80, 20, Component.m_237115_("mco.brokenworld.download"), p_88339_ -> {
                    MutableComponent $$2 = Component.m_237115_("mco.configure.world.restore.download.question.line1");
                    MutableComponent $$3 = Component.m_237115_("mco.configure.world.restore.download.question.line2");
                    this.f_96541_.m_91152_(new RealmsLongConfirmationScreen(p_167370_ -> {
                        if (p_167370_) {
                            this.m_88335_($$1);
                        } else {
                            this.f_96541_.m_91152_(this);
                        }
                    }, RealmsLongConfirmationScreen.Type.Info, $$2, $$3, true));
                });
            }
            if (this.f_88292_.contains($$1)) {
                $$4.f_93623_ = false;
                $$4.m_93666_(Component.m_237115_("mco.brokenworld.downloaded"));
            }
            this.m_142416_($$4);
            this.m_142416_(new Button(this.m_88301_($$1), RealmsBrokenWorldScreen.m_120774_(10), 80, 20, Component.m_237115_("mco.brokenworld.reset"), p_88309_ -> {
                RealmsResetWorldScreen $$2 = new RealmsResetWorldScreen(this, this.f_88286_, this::m_88300_, () -> {
                    this.f_96541_.m_91152_(this);
                    this.m_88300_();
                });
                if ($$1 != this.f_88286_.f_87486_ || this.f_88286_.f_87485_ == RealmsServer.WorldType.MINIGAME) {
                    $$2.m_89343_($$1);
                }
                this.f_96541_.m_91152_($$2);
            }));
        }
    }

    @Override
    public void m_86600_() {
        ++this.f_88293_;
    }

    @Override
    public void m_6305_(PoseStack p_88316_, int p_88317_, int p_88318_, float p_88319_) {
        this.m_7333_(p_88316_);
        super.m_6305_(p_88316_, p_88317_, p_88318_, p_88319_);
        RealmsBrokenWorldScreen.m_93215_(p_88316_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 17, 0xFFFFFF);
        for (int $$4 = 0; $$4 < this.f_88289_.length; ++$$4) {
            RealmsBrokenWorldScreen.m_93215_(p_88316_, this.f_96547_, this.f_88289_[$$4], this.f_96543_ / 2, RealmsBrokenWorldScreen.m_120774_(-1) + 3 + $$4 * 12, 0xA0A0A0);
        }
        if (this.f_88286_ == null) {
            return;
        }
        for (Map.Entry<Integer, RealmsWorldOptions> $$5 : this.f_88286_.f_87481_.entrySet()) {
            if ($$5.getValue().f_87609_ != null && $$5.getValue().f_87608_ != -1L) {
                this.m_88320_(p_88316_, this.m_88301_($$5.getKey()), RealmsBrokenWorldScreen.m_120774_(1) + 5, p_88317_, p_88318_, this.f_88286_.f_87486_ == $$5.getKey() && !this.m_88352_(), $$5.getValue().m_87626_($$5.getKey()), $$5.getKey(), $$5.getValue().f_87608_, $$5.getValue().f_87609_, $$5.getValue().f_87611_);
                continue;
            }
            this.m_88320_(p_88316_, this.m_88301_($$5.getKey()), RealmsBrokenWorldScreen.m_120774_(1) + 5, p_88317_, p_88318_, this.f_88286_.f_87486_ == $$5.getKey() && !this.m_88352_(), $$5.getValue().m_87626_($$5.getKey()), $$5.getKey(), -1L, null, $$5.getValue().f_87611_);
        }
    }

    private int m_88301_(int p_88302_) {
        return this.f_88290_ + (p_88302_ - 1) * 110;
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    @Override
    public boolean m_7933_(int p_88304_, int p_88305_, int p_88306_) {
        if (p_88304_ == 256) {
            this.m_88351_();
            return true;
        }
        return super.m_7933_(p_88304_, p_88305_, p_88306_);
    }

    private void m_88351_() {
        this.f_96541_.m_91152_(this.f_88284_);
    }

    private void m_88313_(long p_88314_) {
        new Thread(() -> {
            RealmsClient $$1 = RealmsClient.m_87169_();
            try {
                this.f_88286_ = $$1.m_87174_(p_88314_);
                this.m_88350_();
            }
            catch (RealmsServiceException $$2) {
                f_88283_.error("Couldn't get own world");
                this.f_96541_.m_91152_(new RealmsGenericErrorScreen(Component.m_130674_($$2.getMessage()), this.f_88284_));
            }
        }).start();
    }

    public void m_88300_() {
        new Thread(() -> {
            RealmsClient $$0 = RealmsClient.m_87169_();
            if (this.f_88286_.f_87477_ == RealmsServer.State.CLOSED) {
                this.f_96541_.execute(() -> this.f_96541_.m_91152_(new RealmsLongRunningMcoTaskScreen(this, new OpenServerTask(this.f_88286_, this, this.f_88285_, true, this.f_96541_))));
            } else {
                try {
                    RealmsServer $$1 = $$0.m_87174_(this.f_88287_);
                    this.f_96541_.execute(() -> this.f_88285_.m_86660_().m_86515_($$1, this));
                }
                catch (RealmsServiceException $$2) {
                    f_88283_.error("Couldn't get own world");
                    this.f_96541_.execute(() -> this.f_96541_.m_91152_(this.f_88284_));
                }
            }
        }).start();
    }

    private void m_88335_(int p_88336_) {
        RealmsClient $$1 = RealmsClient.m_87169_();
        try {
            WorldDownload $$2 = $$1.m_87209_(this.f_88286_.f_87473_, p_88336_);
            RealmsDownloadLatestWorldScreen $$3 = new RealmsDownloadLatestWorldScreen(this, $$2, this.f_88286_.m_87495_(p_88336_), p_88312_ -> {
                if (p_88312_) {
                    this.f_88292_.add(p_88336_);
                    this.m_169413_();
                    this.m_88350_();
                } else {
                    this.f_96541_.m_91152_(this);
                }
            });
            this.f_96541_.m_91152_($$3);
        }
        catch (RealmsServiceException $$4) {
            f_88283_.error("Couldn't download world data");
            this.f_96541_.m_91152_(new RealmsGenericErrorScreen($$4, (Screen)this));
        }
    }

    private boolean m_88352_() {
        return this.f_88286_ != null && this.f_88286_.f_87485_ == RealmsServer.WorldType.MINIGAME;
    }

    private void m_88320_(PoseStack p_88321_, int p_88322_, int p_88323_, int p_88324_, int p_88325_, boolean p_88326_, String p_88327_, int p_88328_, long p_88329_, @Nullable String p_88330_, boolean p_88331_) {
        if (p_88331_) {
            RenderSystem.m_157456_(0, RealmsWorldSlotButton.f_87918_);
        } else if (p_88330_ != null && p_88329_ != -1L) {
            RealmsTextureManager.m_90190_(String.valueOf(p_88329_), p_88330_);
        } else if (p_88328_ == 1) {
            RenderSystem.m_157456_(0, RealmsWorldSlotButton.f_87919_);
        } else if (p_88328_ == 2) {
            RenderSystem.m_157456_(0, RealmsWorldSlotButton.f_87920_);
        } else if (p_88328_ == 3) {
            RenderSystem.m_157456_(0, RealmsWorldSlotButton.f_87921_);
        } else {
            RealmsTextureManager.m_90190_(String.valueOf(this.f_88286_.f_87488_), this.f_88286_.f_87489_);
        }
        if (!p_88326_) {
            RenderSystem.m_157429_(0.56f, 0.56f, 0.56f, 1.0f);
        } else if (p_88326_) {
            float $$11 = 0.9f + 0.1f * Mth.m_14089_((float)this.f_88293_ * 0.2f);
            RenderSystem.m_157429_($$11, $$11, $$11, 1.0f);
        }
        GuiComponent.m_93133_(p_88321_, p_88322_ + 3, p_88323_ + 3, 0.0f, 0.0f, 74, 74, 74, 74);
        RenderSystem.m_157456_(0, RealmsWorldSlotButton.f_87917_);
        if (p_88326_) {
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            RenderSystem.m_157429_(0.56f, 0.56f, 0.56f, 1.0f);
        }
        GuiComponent.m_93133_(p_88321_, p_88322_, p_88323_, 0.0f, 0.0f, 80, 80, 80, 80);
        RealmsBrokenWorldScreen.m_93208_(p_88321_, this.f_96547_, p_88327_, p_88322_ + 40, p_88323_ + 66, 0xFFFFFF);
    }
}

