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
import com.mojang.realmsclient.dto.WorldTemplate;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RealmsWorldSlotButton;
import com.mojang.realmsclient.gui.screens.RealmsBackupScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsPlayerScreen;
import com.mojang.realmsclient.gui.screens.RealmsResetWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsSelectWorldTemplateScreen;
import com.mojang.realmsclient.gui.screens.RealmsSettingsScreen;
import com.mojang.realmsclient.gui.screens.RealmsSlotOptionsScreen;
import com.mojang.realmsclient.gui.screens.RealmsSubscriptionInfoScreen;
import com.mojang.realmsclient.util.task.CloseServerTask;
import com.mojang.realmsclient.util.task.OpenServerTask;
import com.mojang.realmsclient.util.task.SwitchMinigameTask;
import com.mojang.realmsclient.util.task.SwitchSlotTask;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class RealmsConfigureWorldScreen
extends RealmsScreen {
    private static final Logger f_88395_ = LogUtils.getLogger();
    private static final ResourceLocation f_88396_ = new ResourceLocation("realms", "textures/gui/realms/on_icon.png");
    private static final ResourceLocation f_88397_ = new ResourceLocation("realms", "textures/gui/realms/off_icon.png");
    private static final ResourceLocation f_88398_ = new ResourceLocation("realms", "textures/gui/realms/expired_icon.png");
    private static final ResourceLocation f_88399_ = new ResourceLocation("realms", "textures/gui/realms/expires_soon_icon.png");
    private static final Component f_167372_ = Component.m_237115_("mco.configure.worlds.title");
    private static final Component f_88400_ = Component.m_237115_("mco.configure.world.title");
    private static final Component f_88402_ = Component.m_237115_("mco.configure.current.minigame").m_130946_(": ");
    private static final Component f_88403_ = Component.m_237115_("mco.selectServer.expired");
    private static final Component f_88404_ = Component.m_237115_("mco.selectServer.expires.soon");
    private static final Component f_88405_ = Component.m_237115_("mco.selectServer.expires.day");
    private static final Component f_88406_ = Component.m_237115_("mco.selectServer.open");
    private static final Component f_88407_ = Component.m_237115_("mco.selectServer.closed");
    private static final int f_167373_ = 80;
    private static final int f_167374_ = 5;
    @Nullable
    private Component f_88408_;
    private final RealmsMainScreen f_88380_;
    @Nullable
    private RealmsServer f_88381_;
    private final long f_88382_;
    private int f_88383_;
    private int f_88384_;
    private Button f_88385_;
    private Button f_88386_;
    private Button f_88387_;
    private Button f_88388_;
    private Button f_88389_;
    private Button f_88390_;
    private Button f_88391_;
    private boolean f_88392_;
    private int f_88393_;
    private int f_88394_;
    private final List<RealmsWorldSlotButton> f_167375_ = Lists.newArrayList();

    public RealmsConfigureWorldScreen(RealmsMainScreen p_88411_, long p_88412_) {
        super(f_88400_);
        this.f_88380_ = p_88411_;
        this.f_88382_ = p_88412_;
    }

    @Override
    public void m_7856_() {
        if (this.f_88381_ == null) {
            this.m_88426_(this.f_88382_);
        }
        this.f_88383_ = this.f_96543_ / 2 - 187;
        this.f_88384_ = this.f_96543_ / 2 + 190;
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_88385_ = this.m_142416_(new Button(this.m_88465_(0, 3), RealmsConfigureWorldScreen.m_120774_(0), 100, 20, Component.m_237115_("mco.configure.world.buttons.players"), p_88532_ -> this.f_96541_.m_91152_(new RealmsPlayerScreen(this, this.f_88381_))));
        this.f_88386_ = this.m_142416_(new Button(this.m_88465_(1, 3), RealmsConfigureWorldScreen.m_120774_(0), 100, 20, Component.m_237115_("mco.configure.world.buttons.settings"), p_88530_ -> this.f_96541_.m_91152_(new RealmsSettingsScreen(this, this.f_88381_.clone()))));
        this.f_88387_ = this.m_142416_(new Button(this.m_88465_(2, 3), RealmsConfigureWorldScreen.m_120774_(0), 100, 20, Component.m_237115_("mco.configure.world.buttons.subscription"), p_88527_ -> this.f_96541_.m_91152_(new RealmsSubscriptionInfoScreen(this, this.f_88381_.clone(), this.f_88380_))));
        this.f_167375_.clear();
        for (int $$0 = 1; $$0 < 5; ++$$0) {
            this.f_167375_.add(this.m_167385_($$0));
        }
        this.f_88391_ = this.m_142416_(new Button(this.m_88463_(0), RealmsConfigureWorldScreen.m_120774_(13) - 5, 100, 20, Component.m_237115_("mco.configure.world.buttons.switchminigame"), p_88524_ -> this.f_96541_.m_91152_(new RealmsSelectWorldTemplateScreen(Component.m_237115_("mco.template.title.minigame"), this::m_167394_, RealmsServer.WorldType.MINIGAME))));
        this.f_88388_ = this.m_142416_(new Button(this.m_88463_(0), RealmsConfigureWorldScreen.m_120774_(13) - 5, 90, 20, Component.m_237115_("mco.configure.world.buttons.options"), p_88522_ -> this.f_96541_.m_91152_(new RealmsSlotOptionsScreen(this, this.f_88381_.f_87481_.get(this.f_88381_.f_87486_).clone(), this.f_88381_.f_87485_, this.f_88381_.f_87486_))));
        this.f_88389_ = this.m_142416_(new Button(this.m_88463_(1), RealmsConfigureWorldScreen.m_120774_(13) - 5, 90, 20, Component.m_237115_("mco.configure.world.backup"), p_88514_ -> this.f_96541_.m_91152_(new RealmsBackupScreen(this, this.f_88381_.clone(), this.f_88381_.f_87486_))));
        this.f_88390_ = this.m_142416_(new Button(this.m_88463_(2), RealmsConfigureWorldScreen.m_120774_(13) - 5, 90, 20, Component.m_237115_("mco.configure.world.buttons.resetworld"), p_88496_ -> this.f_96541_.m_91152_(new RealmsResetWorldScreen(this, this.f_88381_.clone(), () -> this.f_96541_.execute(() -> this.f_96541_.m_91152_(this.m_88486_())), () -> this.f_96541_.m_91152_(this.m_88486_())))));
        this.m_142416_(new Button(this.f_88384_ - 80 + 8, RealmsConfigureWorldScreen.m_120774_(13) - 5, 70, 20, CommonComponents.f_130660_, p_167407_ -> this.m_88525_()));
        this.f_88389_.f_93623_ = true;
        if (this.f_88381_ == null) {
            this.m_88536_();
            this.m_88535_();
            this.f_88385_.f_93623_ = false;
            this.f_88386_.f_93623_ = false;
            this.f_88387_.f_93623_ = false;
        } else {
            this.m_88528_();
            if (this.m_88534_()) {
                this.m_88535_();
            } else {
                this.m_88536_();
            }
        }
    }

    private RealmsWorldSlotButton m_167385_(int p_167386_) {
        int $$1 = this.m_88487_(p_167386_);
        int $$2 = RealmsConfigureWorldScreen.m_120774_(5) + 5;
        RealmsWorldSlotButton $$3 = new RealmsWorldSlotButton($$1, $$2, 80, 80, () -> this.f_88381_, p_167399_ -> {
            this.f_88408_ = p_167399_;
        }, p_167386_, p_167389_ -> {
            RealmsWorldSlotButton.State $$2 = ((RealmsWorldSlotButton)p_167389_).m_87937_();
            if ($$2 != null) {
                switch ($$2.f_87982_) {
                    case NOTHING: {
                        break;
                    }
                    case JOIN: {
                        this.m_88438_(this.f_88381_);
                        break;
                    }
                    case SWITCH_SLOT: {
                        if ($$2.f_87981_) {
                            this.m_88533_();
                            break;
                        }
                        if ($$2.f_87980_) {
                            this.m_88468_(p_167386_, this.f_88381_);
                            break;
                        }
                        this.m_88420_(p_167386_, this.f_88381_);
                        break;
                    }
                    default: {
                        throw new IllegalStateException("Unknown action " + $$2.f_87982_);
                    }
                }
            }
        });
        return this.m_142416_($$3);
    }

    private int m_88463_(int p_88464_) {
        return this.f_88383_ + p_88464_ * 95;
    }

    private int m_88465_(int p_88466_, int p_88467_) {
        return this.f_96543_ / 2 - (p_88467_ * 105 - 5) / 2 + p_88466_ * 105;
    }

    @Override
    public void m_86600_() {
        super.m_86600_();
        ++this.f_88393_;
        --this.f_88394_;
        if (this.f_88394_ < 0) {
            this.f_88394_ = 0;
        }
        this.f_167375_.forEach(RealmsWorldSlotButton::m_87968_);
    }

    @Override
    public void m_6305_(PoseStack p_88429_, int p_88430_, int p_88431_, float p_88432_) {
        this.f_88408_ = null;
        this.m_7333_(p_88429_);
        RealmsConfigureWorldScreen.m_93215_(p_88429_, this.f_96547_, f_167372_, this.f_96543_ / 2, RealmsConfigureWorldScreen.m_120774_(4), 0xFFFFFF);
        super.m_6305_(p_88429_, p_88430_, p_88431_, p_88432_);
        if (this.f_88381_ == null) {
            RealmsConfigureWorldScreen.m_93215_(p_88429_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 17, 0xFFFFFF);
            return;
        }
        String $$4 = this.f_88381_.m_87512_();
        int $$5 = this.f_96547_.m_92895_($$4);
        int $$6 = this.f_88381_.f_87477_ == RealmsServer.State.CLOSED ? 0xA0A0A0 : 0x7FFF7F;
        int $$7 = this.f_96547_.m_92852_(this.f_96539_);
        RealmsConfigureWorldScreen.m_93215_(p_88429_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 12, 0xFFFFFF);
        RealmsConfigureWorldScreen.m_93208_(p_88429_, this.f_96547_, $$4, this.f_96543_ / 2, 24, $$6);
        int $$8 = Math.min(this.m_88465_(2, 3) + 80 - 11, this.f_96543_ / 2 + $$5 / 2 + $$7 / 2 + 10);
        this.m_88489_(p_88429_, $$8, 7, p_88430_, p_88431_);
        if (this.m_88534_()) {
            this.f_96547_.m_92889_(p_88429_, f_88402_.m_6881_().m_130946_(this.f_88381_.m_87517_()), this.f_88383_ + 80 + 20 + 10, RealmsConfigureWorldScreen.m_120774_(13), 0xFFFFFF);
        }
        if (this.f_88408_ != null) {
            this.m_88433_(p_88429_, this.f_88408_, p_88430_, p_88431_);
        }
    }

    private int m_88487_(int p_88488_) {
        return this.f_88383_ + (p_88488_ - 1) * 98;
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    @Override
    public boolean m_7933_(int p_88417_, int p_88418_, int p_88419_) {
        if (p_88417_ == 256) {
            this.m_88525_();
            return true;
        }
        return super.m_7933_(p_88417_, p_88418_, p_88419_);
    }

    private void m_88525_() {
        if (this.f_88392_) {
            this.f_88380_.m_193498_();
        }
        this.f_96541_.m_91152_(this.f_88380_);
    }

    private void m_88426_(long p_88427_) {
        new Thread(() -> {
            RealmsClient $$1 = RealmsClient.m_87169_();
            try {
                RealmsServer $$2 = $$1.m_87174_(p_88427_);
                this.f_96541_.execute(() -> {
                    this.f_88381_ = $$2;
                    this.m_88528_();
                    if (this.m_88534_()) {
                        this.m_88484_(this.f_88391_);
                    } else {
                        this.m_88484_(this.f_88388_);
                        this.m_88484_(this.f_88389_);
                        this.m_88484_(this.f_88390_);
                    }
                });
            }
            catch (RealmsServiceException $$3) {
                f_88395_.error("Couldn't get own world");
                this.f_96541_.execute(() -> this.f_96541_.m_91152_(new RealmsGenericErrorScreen(Component.m_130674_($$3.getMessage()), (Screen)this.f_88380_)));
            }
        }).start();
    }

    private void m_88528_() {
        this.f_88385_.f_93623_ = !this.f_88381_.f_87482_;
        this.f_88386_.f_93623_ = !this.f_88381_.f_87482_;
        this.f_88387_.f_93623_ = true;
        this.f_88391_.f_93623_ = !this.f_88381_.f_87482_;
        this.f_88388_.f_93623_ = !this.f_88381_.f_87482_;
        this.f_88390_.f_93623_ = !this.f_88381_.f_87482_;
    }

    private void m_88438_(RealmsServer p_88439_) {
        if (this.f_88381_.f_87477_ == RealmsServer.State.OPEN) {
            this.f_88380_.m_86515_(p_88439_, new RealmsConfigureWorldScreen(this.f_88380_.m_86660_(), this.f_88382_));
        } else {
            this.m_88459_(true, new RealmsConfigureWorldScreen(this.f_88380_.m_86660_(), this.f_88382_));
        }
    }

    private void m_88533_() {
        RealmsSelectWorldTemplateScreen $$0 = new RealmsSelectWorldTemplateScreen(Component.m_237115_("mco.template.title.minigame"), this::m_167394_, RealmsServer.WorldType.MINIGAME);
        $$0.m_89682_(Component.m_237115_("mco.minigame.world.info.line1"), Component.m_237115_("mco.minigame.world.info.line2"));
        this.f_96541_.m_91152_($$0);
    }

    private void m_88420_(int p_88421_, RealmsServer p_88422_) {
        MutableComponent $$2 = Component.m_237115_("mco.configure.world.slot.switch.question.line1");
        MutableComponent $$3 = Component.m_237115_("mco.configure.world.slot.switch.question.line2");
        this.f_96541_.m_91152_(new RealmsLongConfirmationScreen(p_167405_ -> {
            if (p_167405_) {
                this.f_96541_.m_91152_(new RealmsLongRunningMcoTaskScreen(this.f_88380_, new SwitchSlotTask(p_181333_.f_87473_, p_88421_, () -> this.f_96541_.execute(() -> this.f_96541_.m_91152_(this.m_88486_())))));
            } else {
                this.f_96541_.m_91152_(this);
            }
        }, RealmsLongConfirmationScreen.Type.Info, $$2, $$3, true));
    }

    private void m_88468_(int p_88469_, RealmsServer p_88470_) {
        MutableComponent $$2 = Component.m_237115_("mco.configure.world.slot.switch.question.line1");
        MutableComponent $$3 = Component.m_237115_("mco.configure.world.slot.switch.question.line2");
        this.f_96541_.m_91152_(new RealmsLongConfirmationScreen(p_167393_ -> {
            if (p_167393_) {
                RealmsResetWorldScreen $$3 = new RealmsResetWorldScreen(this, p_88470_, Component.m_237115_("mco.configure.world.switch.slot"), Component.m_237115_("mco.configure.world.switch.slot.subtitle"), 0xA0A0A0, CommonComponents.f_130656_, () -> this.f_96541_.execute(() -> this.f_96541_.m_91152_(this.m_88486_())), () -> this.f_96541_.m_91152_(this.m_88486_()));
                $$3.m_89343_(p_88469_);
                $$3.m_89389_(Component.m_237115_("mco.create.world.reset.title"));
                this.f_96541_.m_91152_($$3);
            } else {
                this.f_96541_.m_91152_(this);
            }
        }, RealmsLongConfirmationScreen.Type.Info, $$2, $$3, true));
    }

    protected void m_88433_(PoseStack p_88434_, @Nullable Component p_88435_, int p_88436_, int p_88437_) {
        int $$4 = p_88436_ + 12;
        int $$5 = p_88437_ - 12;
        int $$6 = this.f_96547_.m_92852_(p_88435_);
        if ($$4 + $$6 + 3 > this.f_88384_) {
            $$4 = $$4 - $$6 - 20;
        }
        this.m_93179_(p_88434_, $$4 - 3, $$5 - 3, $$4 + $$6 + 3, $$5 + 8 + 3, -1073741824, -1073741824);
        this.f_96547_.m_92763_(p_88434_, p_88435_, $$4, $$5, 0xFFFFFF);
    }

    private void m_88489_(PoseStack p_88490_, int p_88491_, int p_88492_, int p_88493_, int p_88494_) {
        if (this.f_88381_.f_87482_) {
            this.m_88498_(p_88490_, p_88491_, p_88492_, p_88493_, p_88494_);
        } else if (this.f_88381_.f_87477_ == RealmsServer.State.CLOSED) {
            this.m_88515_(p_88490_, p_88491_, p_88492_, p_88493_, p_88494_);
        } else if (this.f_88381_.f_87477_ == RealmsServer.State.OPEN) {
            if (this.f_88381_.f_87484_ < 7) {
                this.m_88473_(p_88490_, p_88491_, p_88492_, p_88493_, p_88494_, this.f_88381_.f_87484_);
            } else {
                this.m_88507_(p_88490_, p_88491_, p_88492_, p_88493_, p_88494_);
            }
        }
    }

    private void m_88498_(PoseStack p_88499_, int p_88500_, int p_88501_, int p_88502_, int p_88503_) {
        RenderSystem.m_157456_(0, f_88398_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        GuiComponent.m_93133_(p_88499_, p_88500_, p_88501_, 0.0f, 0.0f, 10, 28, 10, 28);
        if (p_88502_ >= p_88500_ && p_88502_ <= p_88500_ + 9 && p_88503_ >= p_88501_ && p_88503_ <= p_88501_ + 27) {
            this.f_88408_ = f_88403_;
        }
    }

    private void m_88473_(PoseStack p_88474_, int p_88475_, int p_88476_, int p_88477_, int p_88478_, int p_88479_) {
        RenderSystem.m_157456_(0, f_88399_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.f_88393_ % 20 < 10) {
            GuiComponent.m_93133_(p_88474_, p_88475_, p_88476_, 0.0f, 0.0f, 10, 28, 20, 28);
        } else {
            GuiComponent.m_93133_(p_88474_, p_88475_, p_88476_, 10.0f, 0.0f, 10, 28, 20, 28);
        }
        if (p_88477_ >= p_88475_ && p_88477_ <= p_88475_ + 9 && p_88478_ >= p_88476_ && p_88478_ <= p_88476_ + 27) {
            this.f_88408_ = p_88479_ <= 0 ? f_88404_ : (p_88479_ == 1 ? f_88405_ : Component.m_237110_("mco.selectServer.expires.days", p_88479_));
        }
    }

    private void m_88507_(PoseStack p_88508_, int p_88509_, int p_88510_, int p_88511_, int p_88512_) {
        RenderSystem.m_157456_(0, f_88396_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        GuiComponent.m_93133_(p_88508_, p_88509_, p_88510_, 0.0f, 0.0f, 10, 28, 10, 28);
        if (p_88511_ >= p_88509_ && p_88511_ <= p_88509_ + 9 && p_88512_ >= p_88510_ && p_88512_ <= p_88510_ + 27) {
            this.f_88408_ = f_88406_;
        }
    }

    private void m_88515_(PoseStack p_88516_, int p_88517_, int p_88518_, int p_88519_, int p_88520_) {
        RenderSystem.m_157456_(0, f_88397_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        GuiComponent.m_93133_(p_88516_, p_88517_, p_88518_, 0.0f, 0.0f, 10, 28, 10, 28);
        if (p_88519_ >= p_88517_ && p_88519_ <= p_88517_ + 9 && p_88520_ >= p_88518_ && p_88520_ <= p_88518_ + 27) {
            this.f_88408_ = f_88407_;
        }
    }

    private boolean m_88534_() {
        return this.f_88381_ != null && this.f_88381_.f_87485_ == RealmsServer.WorldType.MINIGAME;
    }

    private void m_88535_() {
        this.m_88450_(this.f_88388_);
        this.m_88450_(this.f_88389_);
        this.m_88450_(this.f_88390_);
    }

    private void m_88450_(Button p_88451_) {
        p_88451_.f_93624_ = false;
        this.m_169411_(p_88451_);
    }

    private void m_88484_(Button p_88485_) {
        p_88485_.f_93624_ = true;
        this.m_142416_(p_88485_);
    }

    private void m_88536_() {
        this.m_88450_(this.f_88391_);
    }

    public void m_88444_(RealmsWorldOptions p_88445_) {
        RealmsWorldOptions $$1 = this.f_88381_.f_87481_.get(this.f_88381_.f_87486_);
        p_88445_.f_87608_ = $$1.f_87608_;
        p_88445_.f_87609_ = $$1.f_87609_;
        RealmsClient $$2 = RealmsClient.m_87169_();
        try {
            $$2.m_87179_(this.f_88381_.f_87473_, this.f_88381_.f_87486_, p_88445_);
            this.f_88381_.f_87481_.put(this.f_88381_.f_87486_, p_88445_);
        }
        catch (RealmsServiceException $$3) {
            f_88395_.error("Couldn't save slot settings");
            this.f_96541_.m_91152_(new RealmsGenericErrorScreen($$3, (Screen)this));
            return;
        }
        this.f_96541_.m_91152_(this);
    }

    public void m_88454_(String p_88455_, String p_88456_) {
        String $$2 = p_88456_.trim().isEmpty() ? null : p_88456_;
        RealmsClient $$3 = RealmsClient.m_87169_();
        try {
            $$3.m_87215_(this.f_88381_.f_87473_, p_88455_, $$2);
            this.f_88381_.m_87508_(p_88455_);
            this.f_88381_.m_87515_($$2);
        }
        catch (RealmsServiceException $$4) {
            f_88395_.error("Couldn't save settings");
            this.f_96541_.m_91152_(new RealmsGenericErrorScreen($$4, (Screen)this));
            return;
        }
        this.f_96541_.m_91152_(this);
    }

    public void m_88459_(boolean p_88460_, Screen p_88461_) {
        this.f_96541_.m_91152_(new RealmsLongRunningMcoTaskScreen(p_88461_, new OpenServerTask(this.f_88381_, this, this.f_88380_, p_88460_, this.f_96541_)));
    }

    public void m_88452_(Screen p_88453_) {
        this.f_96541_.m_91152_(new RealmsLongRunningMcoTaskScreen(p_88453_, new CloseServerTask(this.f_88381_, this)));
    }

    public void m_88413_() {
        this.f_88392_ = true;
    }

    private void m_167394_(@Nullable WorldTemplate p_167395_) {
        if (p_167395_ != null && WorldTemplate.WorldTemplateType.MINIGAME == p_167395_.f_87734_) {
            this.f_96541_.m_91152_(new RealmsLongRunningMcoTaskScreen(this.f_88380_, new SwitchMinigameTask(this.f_88381_.f_87473_, p_167395_, this.m_88486_())));
        } else {
            this.f_96541_.m_91152_(this);
        }
    }

    public RealmsConfigureWorldScreen m_88486_() {
        return new RealmsConfigureWorldScreen(this.f_88380_, this.f_88382_);
    }
}

