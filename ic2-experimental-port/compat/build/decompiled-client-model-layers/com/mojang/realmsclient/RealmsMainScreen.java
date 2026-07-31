/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.util.concurrent.RateLimiter
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Vector3f;
import com.mojang.realmsclient.KeyCombo;
import com.mojang.realmsclient.client.Ping;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.PingResult;
import com.mojang.realmsclient.dto.RealmsNews;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerPlayerList;
import com.mojang.realmsclient.dto.RegionPingResult;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RealmsDataFetcher;
import com.mojang.realmsclient.gui.RealmsNewsManager;
import com.mojang.realmsclient.gui.RealmsServerList;
import com.mojang.realmsclient.gui.screens.RealmsClientOutdatedScreen;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsCreateRealmScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsParentalConsentScreen;
import com.mojang.realmsclient.gui.screens.RealmsPendingInvitesScreen;
import com.mojang.realmsclient.gui.task.DataFetcher;
import com.mojang.realmsclient.util.RealmsPersistence;
import com.mojang.realmsclient.util.RealmsTextureManager;
import com.mojang.realmsclient.util.task.GetServerDetailsTask;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import org.slf4j.Logger;

public class RealmsMainScreen
extends RealmsScreen {
    static final Logger f_86257_ = LogUtils.getLogger();
    private static final ResourceLocation f_86300_ = new ResourceLocation("realms", "textures/gui/realms/on_icon.png");
    private static final ResourceLocation f_86301_ = new ResourceLocation("realms", "textures/gui/realms/off_icon.png");
    private static final ResourceLocation f_86302_ = new ResourceLocation("realms", "textures/gui/realms/expired_icon.png");
    private static final ResourceLocation f_86303_ = new ResourceLocation("realms", "textures/gui/realms/expires_soon_icon.png");
    private static final ResourceLocation f_86304_ = new ResourceLocation("realms", "textures/gui/realms/leave_icon.png");
    private static final ResourceLocation f_86305_ = new ResourceLocation("realms", "textures/gui/realms/invitation_icons.png");
    private static final ResourceLocation f_86306_ = new ResourceLocation("realms", "textures/gui/realms/invite_icon.png");
    static final ResourceLocation f_86307_ = new ResourceLocation("realms", "textures/gui/realms/world_icon.png");
    private static final ResourceLocation f_86308_ = new ResourceLocation("realms", "textures/gui/title/realms.png");
    private static final ResourceLocation f_86309_ = new ResourceLocation("realms", "textures/gui/realms/configure_icon.png");
    private static final ResourceLocation f_86311_ = new ResourceLocation("realms", "textures/gui/realms/news_icon.png");
    private static final ResourceLocation f_86312_ = new ResourceLocation("realms", "textures/gui/realms/popup.png");
    private static final ResourceLocation f_86231_ = new ResourceLocation("realms", "textures/gui/realms/darken.png");
    static final ResourceLocation f_86232_ = new ResourceLocation("realms", "textures/gui/realms/cross_icon.png");
    private static final ResourceLocation f_86233_ = new ResourceLocation("realms", "textures/gui/realms/trial_icon.png");
    static final ResourceLocation f_86234_ = new ResourceLocation("minecraft", "textures/gui/widgets.png");
    static final Component f_86235_ = Component.m_237115_("mco.invites.nopending");
    static final Component f_86236_ = Component.m_237115_("mco.invites.pending");
    static final List<Component> f_86237_ = ImmutableList.of((Object)Component.m_237115_("mco.trial.message.line1"), (Object)Component.m_237115_("mco.trial.message.line2"));
    static final Component f_86238_ = Component.m_237115_("mco.selectServer.uninitialized");
    static final Component f_86239_ = Component.m_237115_("mco.selectServer.expiredList");
    static final Component f_86240_ = Component.m_237115_("mco.selectServer.expiredRenew");
    static final Component f_86241_ = Component.m_237115_("mco.selectServer.expiredTrial");
    static final Component f_86242_ = Component.m_237115_("mco.selectServer.expiredSubscribe");
    static final Component f_86243_ = Component.m_237115_("mco.selectServer.minigame").m_130946_(" ");
    private static final Component f_86244_ = Component.m_237115_("mco.selectServer.popup");
    private static final Component f_86245_ = Component.m_237115_("mco.selectServer.expired");
    private static final Component f_86246_ = Component.m_237115_("mco.selectServer.expires.soon");
    private static final Component f_86247_ = Component.m_237115_("mco.selectServer.expires.day");
    private static final Component f_86248_ = Component.m_237115_("mco.selectServer.open");
    private static final Component f_86249_ = Component.m_237115_("mco.selectServer.closed");
    private static final Component f_86250_ = Component.m_237115_("mco.selectServer.leave");
    private static final Component f_86251_ = Component.m_237115_("mco.selectServer.configure");
    private static final Component f_86253_ = Component.m_237115_("mco.news");
    static final Component f_167175_ = Component.m_237110_("gui.narrate.button", f_86238_);
    static final Component f_167173_ = CommonComponents.m_178391_(f_86237_);
    private static List<ResourceLocation> f_86254_ = ImmutableList.of();
    @Nullable
    private DataFetcher.Subscription f_238705_;
    private RealmsServerList f_238533_;
    static boolean f_86256_;
    private static int f_86274_;
    static volatile boolean f_86275_;
    static volatile boolean f_86276_;
    static volatile boolean f_86277_;
    @Nullable
    static Screen f_86278_;
    private static boolean f_86279_;
    private final RateLimiter f_86280_;
    private boolean f_86281_;
    final Screen f_86282_;
    RealmSelectionList f_86283_;
    private boolean f_167174_;
    private Button f_86285_;
    private Button f_86286_;
    private Button f_86287_;
    private Button f_86288_;
    private Button f_86289_;
    @Nullable
    private List<Component> f_86290_;
    private List<RealmsServer> f_86291_ = ImmutableList.of();
    volatile int f_86292_;
    int f_86293_;
    private boolean f_86294_;
    boolean f_86295_;
    private boolean f_86296_;
    private volatile boolean f_86297_;
    private volatile boolean f_86298_;
    private volatile boolean f_86299_;
    volatile boolean f_86258_;
    @Nullable
    volatile String f_86259_;
    private int f_86260_;
    private int f_86261_;
    private boolean f_86262_;
    private List<KeyCombo> f_86263_;
    long f_212359_;
    private ReentrantLock f_86265_ = new ReentrantLock();
    private MultiLineLabel f_86266_ = MultiLineLabel.f_94331_;
    HoveredElement f_86267_;
    private Button f_86268_;
    private PendingInvitesButton f_86269_;
    private Button f_86270_;
    private Button f_86271_;
    private Button f_86272_;
    private Button f_86273_;

    public RealmsMainScreen(Screen p_86315_) {
        super(GameNarrator.f_93310_);
        this.f_86282_ = p_86315_;
        this.f_86280_ = RateLimiter.create((double)0.01666666753590107);
    }

    private boolean m_86318_() {
        if (!RealmsMainScreen.m_86321_() || !this.f_86294_) {
            return false;
        }
        if (this.f_86297_ && !this.f_86298_) {
            return true;
        }
        for (RealmsServer $$0 : this.f_86291_) {
            if (!$$0.f_87479_.equals(this.f_96541_.m_91094_().m_92545_())) continue;
            return false;
        }
        return true;
    }

    public boolean m_86528_() {
        if (!RealmsMainScreen.m_86321_() || !this.f_86294_) {
            return false;
        }
        if (this.f_86295_) {
            return true;
        }
        return this.f_86291_.isEmpty();
    }

    @Override
    public void m_7856_() {
        this.f_86263_ = Lists.newArrayList((Object[])new KeyCombo[]{new KeyCombo(new char[]{'3', '2', '1', '4', '5', '6'}, () -> {
            f_86256_ = !f_86256_;
        }), new KeyCombo(new char[]{'9', '8', '7', '1', '2', '3'}, () -> {
            if (RealmsClient.f_87157_ == RealmsClient.Environment.STAGE) {
                this.m_86351_();
            } else {
                this.m_86345_();
            }
        }), new KeyCombo(new char[]{'9', '8', '7', '4', '5', '6'}, () -> {
            if (RealmsClient.f_87157_ == RealmsClient.Environment.LOCAL) {
                this.m_86351_();
            } else {
                this.m_86348_();
            }
        })});
        if (f_86278_ != null) {
            this.f_96541_.m_91152_(f_86278_);
            return;
        }
        this.f_86265_ = new ReentrantLock();
        if (f_86277_ && !RealmsMainScreen.m_86321_()) {
            this.m_86342_();
        }
        this.m_86336_();
        if (!this.f_86281_) {
            this.f_96541_.m_91372_(false);
        }
        this.f_96541_.f_91068_.m_90926_(true);
        this.f_86299_ = false;
        this.m_86570_();
        this.f_86283_ = new RealmSelectionList();
        if (f_86274_ != -1) {
            this.f_86283_.m_93410_(f_86274_);
        }
        this.m_7787_(this.f_86283_);
        this.f_167174_ = true;
        this.m_94725_(this.f_86283_);
        this.f_86266_ = MultiLineLabel.m_94341_(this.f_96547_, f_86244_, 100);
        RealmsNewsManager $$0 = this.f_96541_.m_239420_().f_238737_;
        this.f_86258_ = $$0.m_239499_();
        this.f_86259_ = $$0.m_240058_();
        if (this.f_238533_ == null) {
            this.f_238533_ = new RealmsServerList(this.f_96541_);
        }
        if (this.f_238705_ != null) {
            this.f_238705_.m_240009_();
        }
    }

    private static boolean m_86321_() {
        return f_86276_ && f_86275_;
    }

    public void m_86570_() {
        this.f_86289_ = this.m_142416_(new Button(this.f_96543_ / 2 - 202, this.f_96544_ - 32, 90, 20, Component.m_237115_("mco.selectServer.leave"), p_86679_ -> this.m_86669_(this.m_193481_())));
        this.f_86288_ = this.m_142416_(new Button(this.f_96543_ / 2 - 190, this.f_96544_ - 32, 90, 20, Component.m_237115_("mco.selectServer.configure"), p_86672_ -> this.m_86656_(this.m_193481_())));
        this.f_86285_ = this.m_142416_(new Button(this.f_96543_ / 2 - 93, this.f_96544_ - 32, 90, 20, Component.m_237115_("mco.selectServer.play"), p_86659_ -> this.m_86515_(this.m_193481_(), this)));
        this.f_86286_ = this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ - 32, 90, 20, CommonComponents.f_130660_, p_86647_ -> {
            if (!this.f_86296_) {
                this.f_96541_.m_91152_(this.f_86282_);
            }
        }));
        this.f_86287_ = this.m_142416_(new Button(this.f_96543_ / 2 + 100, this.f_96544_ - 32, 90, 20, Component.m_237115_("mco.selectServer.expiredRenew"), p_86622_ -> this.m_193499_(this.m_193481_())));
        this.f_86270_ = this.m_142416_(new NewsButton());
        this.f_86268_ = this.m_142416_(new Button(this.f_96543_ - 90, 6, 80, 20, Component.m_237115_("mco.selectServer.purchase"), p_86597_ -> {
            this.f_86295_ = !this.f_86295_;
        }));
        this.f_86269_ = this.m_142416_(new PendingInvitesButton());
        this.f_86273_ = this.m_142416_(new CloseButton());
        this.f_86271_ = this.m_142416_(new Button(this.f_96543_ / 2 + 52, this.m_86366_() + 137 - 20, 98, 20, Component.m_237115_("mco.selectServer.trial"), p_86565_ -> {
            if (!this.f_86297_ || this.f_86298_) {
                return;
            }
            Util.m_137581_().m_137646_("https://aka.ms/startjavarealmstrial");
            this.f_96541_.m_91152_(this.f_86282_);
        }));
        this.f_86272_ = this.m_142416_(new Button(this.f_96543_ / 2 + 52, this.m_86366_() + 160 - 20, 98, 20, Component.m_237115_("mco.selectServer.buy"), p_231255_ -> Util.m_137581_().m_137646_("https://aka.ms/BuyJavaRealms")));
        this.m_86513_(null);
    }

    void m_86513_(@Nullable RealmsServer p_86514_) {
        boolean $$1;
        this.f_86286_.f_93623_ = true;
        if (!RealmsMainScreen.m_86321_() || !this.f_86294_) {
            RealmsMainScreen.m_202376_(this.f_86285_, this.f_86287_, this.f_86288_, this.f_86271_, this.f_86272_, this.f_86273_, this.f_86270_, this.f_86269_, this.f_86268_, this.f_86289_);
            return;
        }
        this.f_86285_.f_93624_ = true;
        this.f_86285_.f_93623_ = this.m_86562_(p_86514_) && !this.m_86528_();
        this.f_86287_.f_93624_ = this.m_86594_(p_86514_);
        this.f_86288_.f_93624_ = this.m_86619_(p_86514_);
        this.f_86289_.f_93624_ = this.m_86644_(p_86514_);
        this.f_86271_.f_93624_ = $$1 = this.m_86528_() && this.f_86297_ && !this.f_86298_;
        this.f_86271_.f_93623_ = $$1;
        this.f_86272_.f_93624_ = this.m_86528_();
        this.f_86273_.f_93624_ = this.m_86528_() && this.f_86295_;
        this.f_86287_.f_93623_ = !this.m_86528_();
        this.f_86288_.f_93623_ = !this.m_86528_();
        this.f_86289_.f_93623_ = !this.m_86528_();
        this.f_86270_.f_93623_ = true;
        this.f_86270_.f_93624_ = this.f_86259_ != null;
        this.f_86269_.f_93623_ = true;
        this.f_86269_.f_93624_ = true;
        this.f_86268_.f_93623_ = !this.m_86528_();
    }

    private boolean m_86324_() {
        return (!this.m_86528_() || this.f_86295_) && RealmsMainScreen.m_86321_() && this.f_86294_;
    }

    boolean m_86562_(@Nullable RealmsServer p_86563_) {
        return p_86563_ != null && !p_86563_.f_87482_ && p_86563_.f_87477_ == RealmsServer.State.OPEN;
    }

    private boolean m_86594_(@Nullable RealmsServer p_86595_) {
        return p_86595_ != null && p_86595_.f_87482_ && this.m_86683_(p_86595_);
    }

    private boolean m_86619_(@Nullable RealmsServer p_86620_) {
        return p_86620_ != null && this.m_86683_(p_86620_);
    }

    private boolean m_86644_(@Nullable RealmsServer p_86645_) {
        return p_86645_ != null && !this.m_86683_(p_86645_);
    }

    @Override
    public void m_86600_() {
        super.m_86600_();
        if (this.f_86269_ != null) {
            this.f_86269_.m_86821_();
        }
        this.f_86296_ = false;
        ++this.f_86293_;
        boolean $$0 = RealmsMainScreen.m_86321_();
        if (this.f_238705_ == null && $$0) {
            this.f_238705_ = this.m_86354_(this.f_96541_.m_239420_());
        } else if (this.f_238705_ != null && !$$0) {
            this.f_238705_ = null;
        }
        if (this.f_238705_ != null) {
            this.f_238705_.m_239355_();
        }
        if (this.m_86528_()) {
            ++this.f_86261_;
        }
        if (this.f_86268_ != null) {
            this.f_86268_.f_93623_ = this.f_86268_.f_93624_ = this.m_86324_();
        }
    }

    private DataFetcher.Subscription m_86354_(RealmsDataFetcher p_238836_) {
        DataFetcher.Subscription $$1 = p_238836_.f_238549_.m_239139_();
        $$1.m_239441_(p_238836_.f_87797_, p_238837_ -> {
            boolean $$4;
            List<RealmsServer> $$1 = this.f_238533_.m_239868_((List<RealmsServer>)p_238837_);
            RealmsServer $$2 = this.m_193481_();
            ServerEntry $$3 = null;
            this.f_86283_.m_7178_();
            boolean bl = $$4 = !this.f_86294_;
            if ($$4) {
                this.f_86294_ = true;
            }
            boolean $$5 = false;
            for (RealmsServer $$6 : $$1) {
                if (!this.m_86688_($$6)) continue;
                $$5 = true;
            }
            this.f_86291_ = $$1;
            if (this.m_86318_()) {
                this.f_86283_.m_7085_(new TrialEntry());
            }
            for (RealmsServer $$7 : this.f_86291_) {
                ServerEntry $$8 = new ServerEntry($$7);
                this.f_86283_.m_7085_($$8);
                if ($$2 == null || $$2.f_87473_ != $$7.f_87473_) continue;
                $$3 = $$8;
            }
            if (!f_86279_ && $$5) {
                f_86279_ = true;
                this.m_86327_();
            }
            if ($$4) {
                this.m_86513_(null);
            } else {
                this.f_86283_.m_6987_($$3);
            }
        });
        $$1.m_239441_(p_238836_.f_238709_, p_240510_ -> {
            this.f_86292_ = p_240510_;
            if (this.f_86292_ > 0 && this.f_86280_.tryAcquire(1)) {
                this.f_96541_.m_240477_().m_168785_(Component.m_237110_("mco.configure.world.invite.narration", this.f_86292_));
            }
        });
        $$1.m_239441_(p_238836_.f_87799_, p_238839_ -> {
            if (this.f_86298_) {
                return;
            }
            if (p_238839_ != this.f_86297_ && this.m_86528_()) {
                this.f_86297_ = p_238839_;
                this.f_86299_ = false;
            } else {
                this.f_86297_ = p_238839_;
            }
        });
        $$1.m_239441_(p_238836_.f_87800_, p_238847_ -> {
            block0: for (RealmsServerPlayerList $$1 : p_238847_.f_87592_) {
                for (RealmsServer $$2 : this.f_86291_) {
                    if ($$2.f_87473_ != $$1.f_87582_) continue;
                    $$2.m_87506_($$1);
                    continue block0;
                }
            }
        });
        $$1.m_239441_(p_238836_.f_238681_, p_231355_ -> {
            p_238849_.f_238737_.m_239190_((RealmsNews)p_231355_);
            this.f_86258_ = p_238849_.f_238737_.m_239499_();
            this.f_86259_ = p_238849_.f_238737_.m_240058_();
            this.m_86513_(null);
        });
        return $$1;
    }

    void m_240107_() {
        if (this.f_238705_ != null) {
            this.f_238705_.m_240120_();
        }
    }

    private void m_86327_() {
        new Thread(() -> {
            List<RegionPingResult> $$0 = Ping.m_87125_();
            RealmsClient $$1 = RealmsClient.m_87169_();
            PingResult $$2 = new PingResult();
            $$2.f_87438_ = $$0;
            $$2.f_87439_ = this.m_86330_();
            try {
                $$1.m_87199_($$2);
            }
            catch (Throwable $$3) {
                f_86257_.warn("Could not send ping result to Realms: ", $$3);
            }
        }).start();
    }

    private List<Long> m_86330_() {
        ArrayList $$0 = Lists.newArrayList();
        for (RealmsServer $$1 : this.f_86291_) {
            if (!this.m_86688_($$1)) continue;
            $$0.add($$1.f_87473_);
        }
        return $$0;
    }

    @Override
    public void m_7861_() {
        this.f_96541_.f_91068_.m_90926_(false);
    }

    public void m_167190_(boolean p_167191_) {
        this.f_86298_ = p_167191_;
    }

    void m_193499_(@Nullable RealmsServer p_193500_) {
        if (p_193500_ != null) {
            String $$1 = "https://aka.ms/ExtendJavaRealms?subscriptionId=" + p_193500_.f_87474_ + "&profileId=" + this.f_96541_.m_91094_().m_92545_() + "&ref=" + (p_193500_.f_87483_ ? "expiredTrial" : "expiredRealm");
            this.f_96541_.f_91068_.m_90911_($$1);
            Util.m_137581_().m_137646_($$1);
        }
    }

    private void m_86336_() {
        if (!f_86277_) {
            f_86277_ = true;
            new Thread("MCO Compatability Checker #1"){

                @Override
                public void run() {
                    RealmsClient $$0 = RealmsClient.m_87169_();
                    try {
                        RealmsClient.CompatibleVersionResponse $$1 = $$0.m_87259_();
                        if ($$1 != RealmsClient.CompatibleVersionResponse.COMPATIBLE) {
                            f_86278_ = new RealmsClientOutdatedScreen(RealmsMainScreen.this.f_86282_);
                            RealmsMainScreen.this.f_96541_.execute(() -> RealmsMainScreen.this.f_96541_.m_91152_(f_86278_));
                            return;
                        }
                        RealmsMainScreen.this.m_86342_();
                    }
                    catch (RealmsServiceException $$2) {
                        f_86277_ = false;
                        f_86257_.error("Couldn't connect to realms", (Throwable)$$2);
                        if ($$2.f_87773_ == 401) {
                            f_86278_ = new RealmsGenericErrorScreen(Component.m_237115_("mco.error.invalid.session.title"), Component.m_237115_("mco.error.invalid.session.message"), RealmsMainScreen.this.f_86282_);
                            RealmsMainScreen.this.f_96541_.execute(() -> RealmsMainScreen.this.f_96541_.m_91152_(f_86278_));
                        }
                        RealmsMainScreen.this.f_96541_.execute(() -> RealmsMainScreen.this.f_96541_.m_91152_(new RealmsGenericErrorScreen($$2, RealmsMainScreen.this.f_86282_)));
                    }
                }
            }.start();
        }
    }

    void m_86342_() {
        new Thread("MCO Compatability Checker #1"){

            @Override
            public void run() {
                RealmsClient $$0 = RealmsClient.m_87169_();
                try {
                    Boolean $$1 = $$0.m_87247_();
                    if ($$1.booleanValue()) {
                        f_86257_.info("Realms is available for this user");
                        f_86275_ = true;
                    } else {
                        f_86257_.info("Realms is not available for this user");
                        f_86275_ = false;
                        RealmsMainScreen.this.f_96541_.execute(() -> RealmsMainScreen.this.f_96541_.m_91152_(new RealmsParentalConsentScreen(RealmsMainScreen.this.f_86282_)));
                    }
                    f_86276_ = true;
                }
                catch (RealmsServiceException $$2) {
                    f_86257_.error("Couldn't connect to realms", (Throwable)$$2);
                    RealmsMainScreen.this.f_96541_.execute(() -> RealmsMainScreen.this.f_96541_.m_91152_(new RealmsGenericErrorScreen($$2, RealmsMainScreen.this.f_86282_)));
                }
            }
        }.start();
    }

    private void m_86345_() {
        if (RealmsClient.f_87157_ != RealmsClient.Environment.STAGE) {
            new Thread("MCO Stage Availability Checker #1"){

                @Override
                public void run() {
                    RealmsClient $$0 = RealmsClient.m_87169_();
                    try {
                        Boolean $$1 = $$0.m_87253_();
                        if ($$1.booleanValue()) {
                            RealmsClient.m_87206_();
                            f_86257_.info("Switched to stage");
                            RealmsMainScreen.this.m_240107_();
                        }
                    }
                    catch (RealmsServiceException $$2) {
                        f_86257_.error("Couldn't connect to Realms: {}", (Object)$$2.toString());
                    }
                }
            }.start();
        }
    }

    private void m_86348_() {
        if (RealmsClient.f_87157_ != RealmsClient.Environment.LOCAL) {
            new Thread("MCO Local Availability Checker #1"){

                @Override
                public void run() {
                    RealmsClient $$0 = RealmsClient.m_87169_();
                    try {
                        Boolean $$1 = $$0.m_87253_();
                        if ($$1.booleanValue()) {
                            RealmsClient.m_87229_();
                            f_86257_.info("Switched to local");
                            RealmsMainScreen.this.m_240107_();
                        }
                    }
                    catch (RealmsServiceException $$2) {
                        f_86257_.error("Couldn't connect to Realms: {}", (Object)$$2.toString());
                    }
                }
            }.start();
        }
    }

    private void m_86351_() {
        RealmsClient.m_87221_();
        this.m_240107_();
    }

    void m_86656_(@Nullable RealmsServer p_86657_) {
        if (p_86657_ != null && (this.f_96541_.m_91094_().m_92545_().equals(p_86657_.f_87479_) || f_86256_)) {
            this.m_86357_();
            this.f_96541_.m_91152_(new RealmsConfigureWorldScreen(this, p_86657_.f_87473_));
        }
    }

    void m_86669_(@Nullable RealmsServer p_86670_) {
        if (p_86670_ != null && !this.f_96541_.m_91094_().m_92545_().equals(p_86670_.f_87479_)) {
            this.m_86357_();
            MutableComponent $$1 = Component.m_237115_("mco.configure.world.leave.question.line1");
            MutableComponent $$2 = Component.m_237115_("mco.configure.world.leave.question.line2");
            this.f_96541_.m_91152_(new RealmsLongConfirmationScreen(p_231253_ -> this.m_193493_(p_231253_, p_86670_), RealmsLongConfirmationScreen.Type.Info, $$1, $$2, true));
        }
    }

    private void m_86357_() {
        f_86274_ = (int)this.f_86283_.m_93517_();
    }

    @Nullable
    private RealmsServer m_193481_() {
        if (this.f_86283_ == null) {
            return null;
        }
        Entry $$0 = (Entry)this.f_86283_.m_93511_();
        return $$0 != null ? $$0.m_183377_() : null;
    }

    private void m_193493_(boolean p_193494_, final RealmsServer p_193495_) {
        if (p_193494_) {
            new Thread("Realms-leave-server"){

                @Override
                public void run() {
                    try {
                        RealmsClient $$0 = RealmsClient.m_87169_();
                        $$0.m_87222_(p_193495_.f_87473_);
                        RealmsMainScreen.this.f_96541_.execute(() -> RealmsMainScreen.this.m_86676_(p_193495_));
                    }
                    catch (RealmsServiceException $$1) {
                        f_86257_.error("Couldn't configure world");
                        RealmsMainScreen.this.f_96541_.execute(() -> RealmsMainScreen.this.f_96541_.m_91152_(new RealmsGenericErrorScreen($$1, (Screen)RealmsMainScreen.this)));
                    }
                }
            }.start();
        }
        this.f_96541_.m_91152_(this);
    }

    void m_86676_(RealmsServer p_86677_) {
        this.f_86291_ = this.f_238533_.m_240076_(p_86677_);
        this.f_86283_.m_6702_().removeIf(p_231250_ -> {
            RealmsServer $$2 = p_231250_.m_183377_();
            return $$2 != null && $$2.f_87473_ == p_238844_.f_87473_;
        });
        this.f_86283_.m_6987_((Entry)null);
        this.m_86513_(null);
        this.f_86285_.f_93623_ = false;
    }

    public void m_193498_() {
        if (this.f_86283_ != null) {
            this.f_86283_.m_6987_((Entry)null);
        }
    }

    @Override
    public boolean m_7933_(int p_86401_, int p_86402_, int p_86403_) {
        if (p_86401_ == 256) {
            this.f_86263_.forEach(KeyCombo::m_86227_);
            this.m_86360_();
            return true;
        }
        return super.m_7933_(p_86401_, p_86402_, p_86403_);
    }

    void m_86360_() {
        if (this.m_86528_() && this.f_86295_) {
            this.f_86295_ = false;
        } else {
            this.f_96541_.m_91152_(this.f_86282_);
        }
    }

    @Override
    public boolean m_5534_(char p_86388_, int p_86389_) {
        this.f_86263_.forEach(p_231245_ -> p_231245_.m_86228_(p_86388_));
        return true;
    }

    @Override
    public void m_6305_(PoseStack p_86413_, int p_86414_, int p_86415_, float p_86416_) {
        this.f_86267_ = HoveredElement.NONE;
        this.f_86290_ = null;
        this.m_7333_(p_86413_);
        this.f_86283_.m_6305_(p_86413_, p_86414_, p_86415_, p_86416_);
        this.m_86408_(p_86413_, this.f_96543_ / 2 - 50, 7);
        if (RealmsClient.f_87157_ == RealmsClient.Environment.STAGE) {
            this.m_86574_(p_86413_);
        }
        if (RealmsClient.f_87157_ == RealmsClient.Environment.LOCAL) {
            this.m_86531_(p_86413_);
        }
        if (this.m_86528_()) {
            this.m_202329_(p_86413_);
        } else {
            if (this.f_86299_) {
                this.m_86513_(null);
                if (!this.f_167174_) {
                    this.m_7787_(this.f_86283_);
                    this.f_167174_ = true;
                }
                this.f_86285_.f_93623_ = this.m_86562_(this.m_193481_());
            }
            this.f_86299_ = false;
        }
        super.m_6305_(p_86413_, p_86414_, p_86415_, p_86416_);
        if (this.f_86290_ != null) {
            this.m_86441_(p_86413_, this.f_86290_, p_86414_, p_86415_);
        }
        if (this.f_86297_ && !this.f_86298_ && this.m_86528_()) {
            RenderSystem.m_157456_(0, f_86233_);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            int $$4 = 8;
            int $$5 = 8;
            int $$6 = 0;
            if ((Util.m_137550_() / 800L & 1L) == 1L) {
                $$6 = 8;
            }
            GuiComponent.m_93133_(p_86413_, this.f_86271_.f_93620_ + this.f_86271_.m_5711_() - 8 - 4, this.f_86271_.f_93621_ + this.f_86271_.m_93694_() / 2 - 4, 0.0f, $$6, 8, 8, 8, 16);
        }
    }

    private void m_86408_(PoseStack p_86409_, int p_86410_, int p_86411_) {
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_86308_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        p_86409_.m_85836_();
        p_86409_.m_85841_(0.5f, 0.5f, 0.5f);
        GuiComponent.m_93133_(p_86409_, p_86410_ * 2, p_86411_ * 2 - 5, 0.0f, 0.0f, 200, 50, 200, 50);
        p_86409_.m_85849_();
    }

    @Override
    public boolean m_6375_(double p_86397_, double p_86398_, int p_86399_) {
        if (this.m_86393_(p_86397_, p_86398_) && this.f_86295_) {
            this.f_86295_ = false;
            this.f_86296_ = true;
            return true;
        }
        return super.m_6375_(p_86397_, p_86398_, p_86399_);
    }

    private boolean m_86393_(double p_86394_, double p_86395_) {
        int $$2 = this.m_86363_();
        int $$3 = this.m_86366_();
        return p_86394_ < (double)($$2 - 5) || p_86394_ > (double)($$2 + 315) || p_86395_ < (double)($$3 - 5) || p_86395_ > (double)($$3 + 171);
    }

    private void m_202329_(PoseStack p_202330_) {
        int $$1 = this.m_86363_();
        int $$2 = this.m_86366_();
        if (!this.f_86299_) {
            this.f_86260_ = 0;
            this.f_86261_ = 0;
            this.f_86262_ = true;
            this.m_86513_(null);
            if (this.f_167174_) {
                this.m_169411_(this.f_86283_);
                this.f_167174_ = false;
            }
            this.f_96541_.m_240477_().m_168785_(f_86244_);
        }
        if (this.f_86294_) {
            this.f_86299_ = true;
        }
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 0.7f);
        RenderSystem.m_69478_();
        RenderSystem.m_157456_(0, f_86231_);
        boolean $$3 = false;
        int $$4 = 32;
        GuiComponent.m_93133_(p_202330_, 0, 32, 0.0f, 0.0f, this.f_96543_, this.f_96544_ - 40 - 32, 310, 166);
        RenderSystem.m_69461_();
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_157456_(0, f_86312_);
        GuiComponent.m_93133_(p_202330_, $$1, $$2, 0.0f, 0.0f, 310, 166, 310, 166);
        if (!f_86254_.isEmpty()) {
            RenderSystem.m_157456_(0, f_86254_.get(this.f_86260_));
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            GuiComponent.m_93133_(p_202330_, $$1 + 7, $$2 + 7, 0.0f, 0.0f, 195, 152, 195, 152);
            if (this.f_86261_ % 95 < 5) {
                if (!this.f_86262_) {
                    this.f_86260_ = (this.f_86260_ + 1) % f_86254_.size();
                    this.f_86262_ = true;
                }
            } else {
                this.f_86262_ = false;
            }
        }
        this.f_86266_.m_6508_(p_202330_, this.f_96543_ / 2 + 52, $$2 + 7, 10, 0x4C4C4C);
    }

    int m_86363_() {
        return (this.f_96543_ - 310) / 2;
    }

    int m_86366_() {
        return this.f_96544_ / 2 - 80;
    }

    void m_86424_(PoseStack p_86425_, int p_86426_, int p_86427_, int p_86428_, int p_86429_, boolean p_86430_, boolean p_86431_) {
        boolean $$20;
        boolean $$14;
        boolean $$9;
        int $$7 = this.f_86292_;
        boolean $$8 = this.m_86571_(p_86426_, p_86427_);
        boolean bl = $$9 = p_86431_ && p_86430_;
        if ($$9) {
            float $$10 = 0.25f + (1.0f + Mth.m_14031_((float)this.f_86293_ * 0.5f)) * 0.25f;
            int $$11 = 0xFF000000 | (int)($$10 * 64.0f) << 16 | (int)($$10 * 64.0f) << 8 | (int)($$10 * 64.0f) << 0;
            this.m_93179_(p_86425_, p_86428_ - 2, p_86429_ - 2, p_86428_ + 18, p_86429_ + 18, $$11, $$11);
            $$11 = 0xFF000000 | (int)($$10 * 255.0f) << 16 | (int)($$10 * 255.0f) << 8 | (int)($$10 * 255.0f) << 0;
            this.m_93179_(p_86425_, p_86428_ - 2, p_86429_ - 2, p_86428_ + 18, p_86429_ - 1, $$11, $$11);
            this.m_93179_(p_86425_, p_86428_ - 2, p_86429_ - 2, p_86428_ - 1, p_86429_ + 18, $$11, $$11);
            this.m_93179_(p_86425_, p_86428_ + 17, p_86429_ - 2, p_86428_ + 18, p_86429_ + 18, $$11, $$11);
            this.m_93179_(p_86425_, p_86428_ - 2, p_86429_ + 17, p_86428_ + 18, p_86429_ + 18, $$11, $$11);
        }
        RenderSystem.m_157456_(0, f_86306_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        boolean $$12 = p_86431_ && p_86430_;
        float $$13 = $$12 ? 16.0f : 0.0f;
        GuiComponent.m_93133_(p_86425_, p_86428_, p_86429_ - 6, $$13, 0.0f, 15, 25, 31, 25);
        boolean bl2 = $$14 = p_86431_ && $$7 != 0;
        if ($$14) {
            int $$15 = (Math.min($$7, 6) - 1) * 8;
            int $$16 = (int)(Math.max(0.0f, Math.max(Mth.m_14031_((float)(10 + this.f_86293_) * 0.57f), Mth.m_14089_((float)this.f_86293_ * 0.35f))) * -6.0f);
            RenderSystem.m_157456_(0, f_86305_);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            float $$17 = $$8 ? 8.0f : 0.0f;
            GuiComponent.m_93133_(p_86425_, p_86428_ + 4, p_86429_ + 4 + $$16, $$15, $$17, 8, 8, 48, 16);
        }
        int $$18 = p_86426_ + 12;
        int $$19 = p_86427_;
        boolean bl3 = $$20 = p_86431_ && $$8;
        if ($$20) {
            Component $$21 = $$7 == 0 ? f_86235_ : f_86236_;
            int $$22 = this.f_96547_.m_92852_($$21);
            this.m_93179_(p_86425_, $$18 - 3, $$19 - 3, $$18 + $$22 + 3, $$19 + 8 + 3, -1073741824, -1073741824);
            this.f_96547_.m_92763_(p_86425_, $$21, $$18, $$19, -1);
        }
    }

    private boolean m_86571_(double p_86572_, double p_86573_) {
        int $$2 = this.f_96543_ / 2 + 50;
        int $$3 = this.f_96543_ / 2 + 66;
        int $$4 = 11;
        int $$5 = 23;
        if (this.f_86292_ != 0) {
            $$2 -= 3;
            $$3 += 3;
            $$4 -= 5;
            $$5 += 5;
        }
        return (double)$$2 <= p_86572_ && p_86572_ <= (double)$$3 && (double)$$4 <= p_86573_ && p_86573_ <= (double)$$5;
    }

    public void m_86515_(@Nullable RealmsServer p_86516_, Screen p_86517_) {
        if (p_86516_ != null) {
            try {
                if (!this.f_86265_.tryLock(1L, TimeUnit.SECONDS)) {
                    return;
                }
                if (this.f_86265_.getHoldCount() > 1) {
                    return;
                }
            }
            catch (InterruptedException $$2) {
                return;
            }
            this.f_86281_ = true;
            this.f_96541_.m_91152_(new RealmsLongRunningMcoTaskScreen(p_86517_, new GetServerDetailsTask(this, p_86517_, p_86516_, this.f_86265_)));
        }
    }

    boolean m_86683_(RealmsServer p_86684_) {
        return p_86684_.f_87479_ != null && p_86684_.f_87479_.equals(this.f_96541_.m_91094_().m_92545_());
    }

    private boolean m_86688_(RealmsServer p_86689_) {
        return this.m_86683_(p_86689_) && !p_86689_.f_87482_;
    }

    void m_86576_(PoseStack p_86577_, int p_86578_, int p_86579_, int p_86580_, int p_86581_) {
        RenderSystem.m_157456_(0, f_86302_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        GuiComponent.m_93133_(p_86577_, p_86578_, p_86579_, 0.0f, 0.0f, 10, 28, 10, 28);
        if (p_86580_ >= p_86578_ && p_86580_ <= p_86578_ + 9 && p_86581_ >= p_86579_ && p_86581_ <= p_86579_ + 27 && p_86581_ < this.f_96544_ - 40 && p_86581_ > 32 && !this.m_86528_()) {
            this.m_86526_(f_86245_);
        }
    }

    void m_86537_(PoseStack p_86538_, int p_86539_, int p_86540_, int p_86541_, int p_86542_, int p_86543_) {
        RenderSystem.m_157456_(0, f_86303_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.f_86293_ % 20 < 10) {
            GuiComponent.m_93133_(p_86538_, p_86539_, p_86540_, 0.0f, 0.0f, 10, 28, 20, 28);
        } else {
            GuiComponent.m_93133_(p_86538_, p_86539_, p_86540_, 10.0f, 0.0f, 10, 28, 20, 28);
        }
        if (p_86541_ >= p_86539_ && p_86541_ <= p_86539_ + 9 && p_86542_ >= p_86540_ && p_86542_ <= p_86540_ + 27 && p_86542_ < this.f_96544_ - 40 && p_86542_ > 32 && !this.m_86528_()) {
            if (p_86543_ <= 0) {
                this.m_86526_(f_86246_);
            } else if (p_86543_ == 1) {
                this.m_86526_(f_86247_);
            } else {
                this.m_86526_(Component.m_237110_("mco.selectServer.expires.days", p_86543_));
            }
        }
    }

    void m_86601_(PoseStack p_86602_, int p_86603_, int p_86604_, int p_86605_, int p_86606_) {
        RenderSystem.m_157456_(0, f_86300_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        GuiComponent.m_93133_(p_86602_, p_86603_, p_86604_, 0.0f, 0.0f, 10, 28, 10, 28);
        if (p_86605_ >= p_86603_ && p_86605_ <= p_86603_ + 9 && p_86606_ >= p_86604_ && p_86606_ <= p_86604_ + 27 && p_86606_ < this.f_96544_ - 40 && p_86606_ > 32 && !this.m_86528_()) {
            this.m_86526_(f_86248_);
        }
    }

    void m_86626_(PoseStack p_86627_, int p_86628_, int p_86629_, int p_86630_, int p_86631_) {
        RenderSystem.m_157456_(0, f_86301_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        GuiComponent.m_93133_(p_86627_, p_86628_, p_86629_, 0.0f, 0.0f, 10, 28, 10, 28);
        if (p_86630_ >= p_86628_ && p_86630_ <= p_86628_ + 9 && p_86631_ >= p_86629_ && p_86631_ <= p_86629_ + 27 && p_86631_ < this.f_96544_ - 40 && p_86631_ > 32 && !this.m_86528_()) {
            this.m_86526_(f_86249_);
        }
    }

    void m_86648_(PoseStack p_86649_, int p_86650_, int p_86651_, int p_86652_, int p_86653_) {
        boolean $$5 = false;
        if (p_86652_ >= p_86650_ && p_86652_ <= p_86650_ + 28 && p_86653_ >= p_86651_ && p_86653_ <= p_86651_ + 28 && p_86653_ < this.f_96544_ - 40 && p_86653_ > 32 && !this.m_86528_()) {
            $$5 = true;
        }
        RenderSystem.m_157456_(0, f_86304_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        float $$6 = $$5 ? 28.0f : 0.0f;
        GuiComponent.m_93133_(p_86649_, p_86650_, p_86651_, $$6, 0.0f, 28, 28, 56, 28);
        if ($$5) {
            this.m_86526_(f_86250_);
            this.f_86267_ = HoveredElement.LEAVE;
        }
    }

    void m_86661_(PoseStack p_86662_, int p_86663_, int p_86664_, int p_86665_, int p_86666_) {
        boolean $$5 = false;
        if (p_86665_ >= p_86663_ && p_86665_ <= p_86663_ + 28 && p_86666_ >= p_86664_ && p_86666_ <= p_86664_ + 28 && p_86666_ < this.f_96544_ - 40 && p_86666_ > 32 && !this.m_86528_()) {
            $$5 = true;
        }
        RenderSystem.m_157456_(0, f_86309_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        float $$6 = $$5 ? 28.0f : 0.0f;
        GuiComponent.m_93133_(p_86662_, p_86663_, p_86664_, $$6, 0.0f, 28, 28, 56, 28);
        if ($$5) {
            this.m_86526_(f_86251_);
            this.f_86267_ = HoveredElement.CONFIGURE;
        }
    }

    protected void m_86441_(PoseStack p_86442_, List<Component> p_86443_, int p_86444_, int p_86445_) {
        if (p_86443_.isEmpty()) {
            return;
        }
        int $$4 = 0;
        int $$5 = 0;
        for (Component $$6 : p_86443_) {
            int $$7 = this.f_96547_.m_92852_($$6);
            if ($$7 <= $$5) continue;
            $$5 = $$7;
        }
        int $$8 = p_86444_ - $$5 - 5;
        int $$9 = p_86445_;
        if ($$8 < 0) {
            $$8 = p_86444_ + 12;
        }
        for (Component $$10 : p_86443_) {
            int $$11 = $$9 - ($$4 == 0 ? 3 : 0) + $$4;
            this.m_93179_(p_86442_, $$8 - 3, $$11, $$8 + $$5 + 3, $$9 + 8 + 3 + $$4, -1073741824, -1073741824);
            this.f_96547_.m_92763_(p_86442_, $$10, $$8, $$9 + $$4, 0xFFFFFF);
            $$4 += 10;
        }
    }

    void m_86432_(PoseStack p_86433_, int p_86434_, int p_86435_, boolean p_86436_, int p_86437_, int p_86438_, boolean p_86439_, boolean p_86440_) {
        boolean $$8 = false;
        if (p_86434_ >= p_86437_ && p_86434_ <= p_86437_ + 20 && p_86435_ >= p_86438_ && p_86435_ <= p_86438_ + 20) {
            $$8 = true;
        }
        RenderSystem.m_157456_(0, f_86311_);
        if (p_86440_) {
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            RenderSystem.m_157429_(0.5f, 0.5f, 0.5f, 1.0f);
        }
        boolean $$9 = p_86440_ && p_86439_;
        float $$10 = $$9 ? 20.0f : 0.0f;
        GuiComponent.m_93133_(p_86433_, p_86437_, p_86438_, $$10, 0.0f, 20, 20, 40, 20);
        if ($$8 && p_86440_) {
            this.m_86526_(f_86253_);
        }
        if (p_86436_ && p_86440_) {
            int $$11 = $$8 ? 0 : (int)(Math.max(0.0f, Math.max(Mth.m_14031_((float)(10 + this.f_86293_) * 0.57f), Mth.m_14089_((float)this.f_86293_ * 0.35f))) * -6.0f);
            RenderSystem.m_157456_(0, f_86305_);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            GuiComponent.m_93133_(p_86433_, p_86437_ + 10, p_86438_ + 2 + $$11, 40.0f, 0.0f, 8, 8, 48, 16);
        }
    }

    private void m_86531_(PoseStack p_86532_) {
        String $$1 = "LOCAL!";
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        p_86532_.m_85836_();
        p_86532_.m_85837_(this.f_96543_ / 2 - 25, 20.0, 0.0);
        p_86532_.m_85845_(Vector3f.f_122227_.m_122240_(-20.0f));
        p_86532_.m_85841_(1.5f, 1.5f, 1.5f);
        this.f_96547_.m_92883_(p_86532_, "LOCAL!", 0.0f, 0.0f, 0x7FFF7F);
        p_86532_.m_85849_();
    }

    private void m_86574_(PoseStack p_86575_) {
        String $$1 = "STAGE!";
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        p_86575_.m_85836_();
        p_86575_.m_85837_(this.f_96543_ / 2 - 25, 20.0, 0.0);
        p_86575_.m_85845_(Vector3f.f_122227_.m_122240_(-20.0f));
        p_86575_.m_85841_(1.5f, 1.5f, 1.5f);
        this.f_96547_.m_92883_(p_86575_, "STAGE!", 0.0f, 0.0f, -256);
        p_86575_.m_85849_();
    }

    public RealmsMainScreen m_86660_() {
        RealmsMainScreen $$0 = new RealmsMainScreen(this.f_86282_);
        $$0.m_6575_(this.f_96541_, this.f_96543_, this.f_96544_);
        return $$0;
    }

    public static void m_86406_(ResourceManager p_86407_) {
        Set<ResourceLocation> $$1 = p_86407_.m_214159_("textures/gui/images", p_193492_ -> p_193492_.m_135815_().endsWith(".png")).keySet();
        f_86254_ = $$1.stream().filter(p_231247_ -> p_231247_.m_135827_().equals("realms")).toList();
    }

    void m_86526_(Component ... p_86527_) {
        this.f_86290_ = Arrays.asList(p_86527_);
    }

    private void m_86518_(Button p_86519_) {
        this.f_96541_.m_91152_(new RealmsPendingInvitesScreen(this.f_86282_));
    }

    static {
        f_86274_ = -1;
    }

    class RealmSelectionList
    extends RealmsObjectSelectionList<Entry> {
        public RealmSelectionList() {
            super(RealmsMainScreen.this.f_96543_, RealmsMainScreen.this.f_96544_, 32, RealmsMainScreen.this.f_96544_ - 40, 36);
        }

        @Override
        public boolean m_5694_() {
            return RealmsMainScreen.this.m_7222_() == this;
        }

        @Override
        public boolean m_7933_(int p_86840_, int p_86841_, int p_86842_) {
            if (p_86840_ == 257 || p_86840_ == 32 || p_86840_ == 335) {
                Entry $$3 = (Entry)this.m_93511_();
                if ($$3 == null) {
                    return super.m_7933_(p_86840_, p_86841_, p_86842_);
                }
                return $$3.m_6375_(0.0, 0.0, 0);
            }
            return super.m_7933_(p_86840_, p_86841_, p_86842_);
        }

        @Override
        public boolean m_6375_(double p_86828_, double p_86829_, int p_86830_) {
            if (p_86830_ == 0 && p_86828_ < (double)this.m_5756_() && p_86829_ >= (double)this.f_93390_ && p_86829_ <= (double)this.f_93391_) {
                int $$3 = RealmsMainScreen.this.f_86283_.m_5747_();
                int $$4 = this.m_5756_();
                int $$5 = (int)Math.floor(p_86829_ - (double)this.f_93390_) - this.f_93395_ + (int)this.m_93517_() - 4;
                int $$6 = $$5 / this.f_93387_;
                if (p_86828_ >= (double)$$3 && p_86828_ <= (double)$$4 && $$6 >= 0 && $$5 >= 0 && $$6 < this.m_5773_()) {
                    this.m_7980_($$5, $$6, p_86828_, p_86829_, this.f_93388_);
                    this.m_7109_($$6);
                }
                return true;
            }
            return super.m_6375_(p_86828_, p_86829_, p_86830_);
        }

        @Override
        public void m_6987_(@Nullable Entry p_86849_) {
            super.m_6987_(p_86849_);
            if (p_86849_ != null) {
                RealmsMainScreen.this.m_86513_(p_86849_.m_183377_());
            } else {
                RealmsMainScreen.this.m_86513_(null);
            }
        }

        @Override
        public void m_7980_(int p_86834_, int p_86835_, double p_86836_, double p_86837_, int p_86838_) {
            Entry $$5 = (Entry)this.m_93500_(p_86835_);
            if ($$5 instanceof TrialEntry) {
                RealmsMainScreen.this.f_86295_ = true;
                return;
            }
            RealmsServer $$6 = $$5.m_183377_();
            if ($$6 == null) {
                return;
            }
            if ($$6.f_87477_ == RealmsServer.State.UNINITIALIZED) {
                Minecraft.m_91087_().m_91152_(new RealmsCreateRealmScreen($$6, RealmsMainScreen.this));
                return;
            }
            if (RealmsMainScreen.this.f_86267_ == HoveredElement.CONFIGURE) {
                RealmsMainScreen.this.m_86656_($$6);
            } else if (RealmsMainScreen.this.f_86267_ == HoveredElement.LEAVE) {
                RealmsMainScreen.this.m_86669_($$6);
            } else if (RealmsMainScreen.this.f_86267_ == HoveredElement.EXPIRED) {
                RealmsMainScreen.this.m_193499_($$6);
            } else if (RealmsMainScreen.this.m_86562_($$6)) {
                if (Util.m_137550_() - RealmsMainScreen.this.f_212359_ < 250L && this.m_7987_(p_86835_)) {
                    RealmsMainScreen.this.m_86515_($$6, RealmsMainScreen.this);
                }
                RealmsMainScreen.this.f_212359_ = Util.m_137550_();
            }
        }

        @Override
        public int m_5775_() {
            return this.m_5773_() * 36;
        }

        @Override
        public int m_5759_() {
            return 300;
        }
    }

    class NewsButton
    extends Button {
        public NewsButton() {
            super(RealmsMainScreen.this.f_96543_ - 115, 6, 20, 20, Component.m_237115_("mco.news"), p_86804_ -> {
                if (p_86803_.f_86259_ == null) {
                    return;
                }
                Util.m_137581_().m_137646_(p_86803_.f_86259_);
                if (p_86803_.f_86258_) {
                    RealmsPersistence.RealmsPersistenceData $$2 = RealmsPersistence.m_90171_();
                    $$2.f_90176_ = false;
                    p_86803_.f_86258_ = false;
                    RealmsPersistence.m_90172_($$2);
                }
            });
        }

        @Override
        public void m_6303_(PoseStack p_86806_, int p_86807_, int p_86808_, float p_86809_) {
            RealmsMainScreen.this.m_86432_(p_86806_, p_86807_, p_86808_, RealmsMainScreen.this.f_86258_, this.f_93620_, this.f_93621_, this.m_198029_(), this.f_93623_);
        }
    }

    class PendingInvitesButton
    extends Button {
        public PendingInvitesButton() {
            super(RealmsMainScreen.this.f_96543_ / 2 + 47, 6, 22, 22, CommonComponents.f_237098_, RealmsMainScreen.this::m_86518_);
        }

        public void m_86821_() {
            this.m_93666_(RealmsMainScreen.this.f_86292_ == 0 ? f_86235_ : f_86236_);
        }

        @Override
        public void m_6303_(PoseStack p_86817_, int p_86818_, int p_86819_, float p_86820_) {
            RealmsMainScreen.this.m_86424_(p_86817_, p_86818_, p_86819_, this.f_93620_, this.f_93621_, this.m_198029_(), this.f_93623_);
        }
    }

    class CloseButton
    extends Button {
        public CloseButton() {
            super(RealmsMainScreen.this.m_86363_() + 4, RealmsMainScreen.this.m_86366_() + 4, 12, 12, Component.m_237115_("mco.selectServer.close"), p_86775_ -> RealmsMainScreen.this.m_86360_());
        }

        @Override
        public void m_6303_(PoseStack p_86777_, int p_86778_, int p_86779_, float p_86780_) {
            RenderSystem.m_157456_(0, f_86232_);
            RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
            float $$4 = this.m_198029_() ? 12.0f : 0.0f;
            CloseButton.m_93133_(p_86777_, this.f_93620_, this.f_93621_, 0.0f, $$4, 12, 12, 12, 24);
            if (this.m_5953_(p_86778_, p_86779_)) {
                RealmsMainScreen.this.m_86526_(this.m_6035_());
            }
        }
    }

    abstract class Entry
    extends ObjectSelectionList.Entry<Entry> {
        Entry() {
        }

        @Nullable
        public abstract RealmsServer m_183377_();
    }

    static final class HoveredElement
    extends Enum<HoveredElement> {
        public static final /* enum */ HoveredElement NONE = new HoveredElement();
        public static final /* enum */ HoveredElement EXPIRED = new HoveredElement();
        public static final /* enum */ HoveredElement LEAVE = new HoveredElement();
        public static final /* enum */ HoveredElement CONFIGURE = new HoveredElement();
        private static final /* synthetic */ HoveredElement[] $VALUES;

        public static HoveredElement[] values() {
            return (HoveredElement[])$VALUES.clone();
        }

        public static HoveredElement valueOf(String p_86797_) {
            return Enum.valueOf(HoveredElement.class, p_86797_);
        }

        private static /* synthetic */ HoveredElement[] m_167227_() {
            return new HoveredElement[]{NONE, EXPIRED, LEAVE, CONFIGURE};
        }

        static {
            $VALUES = HoveredElement.m_167227_();
        }
    }

    class TrialEntry
    extends Entry {
        TrialEntry() {
        }

        @Override
        public void m_6311_(PoseStack p_86921_, int p_86922_, int p_86923_, int p_86924_, int p_86925_, int p_86926_, int p_86927_, int p_86928_, boolean p_86929_, float p_86930_) {
            this.m_86913_(p_86921_, p_86922_, p_86924_, p_86923_, p_86927_, p_86928_);
        }

        @Override
        public boolean m_6375_(double p_86910_, double p_86911_, int p_86912_) {
            RealmsMainScreen.this.f_86295_ = true;
            return true;
        }

        private void m_86913_(PoseStack p_86914_, int p_86915_, int p_86916_, int p_86917_, int p_86918_, int p_86919_) {
            int $$6 = p_86917_ + 8;
            int $$7 = 0;
            boolean $$8 = false;
            if (p_86916_ <= p_86918_ && p_86918_ <= (int)RealmsMainScreen.this.f_86283_.m_93517_() && p_86917_ <= p_86919_ && p_86919_ <= p_86917_ + 32) {
                $$8 = true;
            }
            int $$9 = 0x7FFF7F;
            if ($$8 && !RealmsMainScreen.this.m_86528_()) {
                $$9 = 6077788;
            }
            for (Component $$10 : f_86237_) {
                GuiComponent.m_93215_(p_86914_, RealmsMainScreen.this.f_96547_, $$10, RealmsMainScreen.this.f_96543_ / 2, $$6 + $$7, $$9);
                $$7 += 10;
            }
        }

        @Override
        public Component m_142172_() {
            return f_167173_;
        }

        @Override
        @Nullable
        public RealmsServer m_183377_() {
            return null;
        }
    }

    class ServerEntry
    extends Entry {
        private static final int f_167228_ = 36;
        private final RealmsServer f_86853_;

        public ServerEntry(RealmsServer p_86856_) {
            this.f_86853_ = p_86856_;
        }

        @Override
        public void m_6311_(PoseStack p_86866_, int p_86867_, int p_86868_, int p_86869_, int p_86870_, int p_86871_, int p_86872_, int p_86873_, boolean p_86874_, float p_86875_) {
            this.m_86878_(this.f_86853_, p_86866_, p_86869_, p_86868_, p_86872_, p_86873_);
        }

        @Override
        public boolean m_6375_(double p_86858_, double p_86859_, int p_86860_) {
            if (this.f_86853_.f_87477_ == RealmsServer.State.UNINITIALIZED) {
                RealmsMainScreen.this.f_96541_.m_91152_(new RealmsCreateRealmScreen(this.f_86853_, RealmsMainScreen.this));
            }
            return true;
        }

        private void m_86878_(RealmsServer p_86879_, PoseStack p_86880_, int p_86881_, int p_86882_, int p_86883_, int p_86884_) {
            this.m_86885_(p_86879_, p_86880_, p_86881_ + 36, p_86882_, p_86883_, p_86884_);
        }

        private void m_86885_(RealmsServer p_86886_, PoseStack p_86887_, int p_86888_, int p_86889_, int p_86890_, int p_86891_) {
            if (p_86886_.f_87477_ == RealmsServer.State.UNINITIALIZED) {
                RenderSystem.m_157456_(0, f_86307_);
                RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
                GuiComponent.m_93133_(p_86887_, p_86888_ + 10, p_86889_ + 6, 0.0f, 0.0f, 40, 20, 40, 20);
                float $$6 = 0.5f + (1.0f + Mth.m_14031_((float)RealmsMainScreen.this.f_86293_ * 0.25f)) * 0.25f;
                int $$7 = 0xFF000000 | (int)(127.0f * $$6) << 16 | (int)(255.0f * $$6) << 8 | (int)(127.0f * $$6);
                GuiComponent.m_93215_(p_86887_, RealmsMainScreen.this.f_96547_, f_86238_, p_86888_ + 10 + 40 + 75, p_86889_ + 12, $$7);
                return;
            }
            int $$8 = 225;
            int $$9 = 2;
            if (p_86886_.f_87482_) {
                RealmsMainScreen.this.m_86576_(p_86887_, p_86888_ + 225 - 14, p_86889_ + 2, p_86890_, p_86891_);
            } else if (p_86886_.f_87477_ == RealmsServer.State.CLOSED) {
                RealmsMainScreen.this.m_86626_(p_86887_, p_86888_ + 225 - 14, p_86889_ + 2, p_86890_, p_86891_);
            } else if (RealmsMainScreen.this.m_86683_(p_86886_) && p_86886_.f_87484_ < 7) {
                RealmsMainScreen.this.m_86537_(p_86887_, p_86888_ + 225 - 14, p_86889_ + 2, p_86890_, p_86891_, p_86886_.f_87484_);
            } else if (p_86886_.f_87477_ == RealmsServer.State.OPEN) {
                RealmsMainScreen.this.m_86601_(p_86887_, p_86888_ + 225 - 14, p_86889_ + 2, p_86890_, p_86891_);
            }
            if (!RealmsMainScreen.this.m_86683_(p_86886_) && !f_86256_) {
                RealmsMainScreen.this.m_86648_(p_86887_, p_86888_ + 225, p_86889_ + 2, p_86890_, p_86891_);
            } else {
                RealmsMainScreen.this.m_86661_(p_86887_, p_86888_ + 225, p_86889_ + 2, p_86890_, p_86891_);
            }
            if (!"0".equals(p_86886_.f_87490_.f_87579_)) {
                String $$10 = ChatFormatting.GRAY + p_86886_.f_87490_.f_87579_;
                RealmsMainScreen.this.f_96547_.m_92883_(p_86887_, $$10, p_86888_ + 207 - RealmsMainScreen.this.f_96547_.m_92895_($$10), p_86889_ + 3, 0x808080);
                if (p_86890_ >= p_86888_ + 207 - RealmsMainScreen.this.f_96547_.m_92895_($$10) && p_86890_ <= p_86888_ + 207 && p_86891_ >= p_86889_ + 1 && p_86891_ <= p_86889_ + 10 && p_86891_ < RealmsMainScreen.this.f_96544_ - 40 && p_86891_ > 32 && !RealmsMainScreen.this.m_86528_()) {
                    RealmsMainScreen.this.m_86526_(Component.m_237113_(p_86886_.f_87490_.f_87580_));
                }
            }
            if (RealmsMainScreen.this.m_86683_(p_86886_) && p_86886_.f_87482_) {
                Component $$14;
                Component $$13;
                RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.m_69478_();
                RenderSystem.m_157456_(0, f_86234_);
                RenderSystem.m_69408_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                if (p_86886_.f_87483_) {
                    Component $$11 = f_86241_;
                    Component $$12 = f_86242_;
                } else {
                    $$13 = f_86239_;
                    $$14 = f_86240_;
                }
                int $$15 = RealmsMainScreen.this.f_96547_.m_92852_($$14) + 17;
                int $$16 = 16;
                int $$17 = p_86888_ + RealmsMainScreen.this.f_96547_.m_92852_($$13) + 8;
                int $$18 = p_86889_ + 13;
                boolean $$19 = false;
                if (p_86890_ >= $$17 && p_86890_ < $$17 + $$15 && p_86891_ > $$18 && p_86891_ <= $$18 + 16 && p_86891_ < RealmsMainScreen.this.f_96544_ - 40 && p_86891_ > 32 && !RealmsMainScreen.this.m_86528_()) {
                    $$19 = true;
                    RealmsMainScreen.this.f_86267_ = HoveredElement.EXPIRED;
                }
                int $$20 = $$19 ? 2 : 1;
                GuiComponent.m_93133_(p_86887_, $$17, $$18, 0.0f, 46 + $$20 * 20, $$15 / 2, 8, 256, 256);
                GuiComponent.m_93133_(p_86887_, $$17 + $$15 / 2, $$18, 200 - $$15 / 2, 46 + $$20 * 20, $$15 / 2, 8, 256, 256);
                GuiComponent.m_93133_(p_86887_, $$17, $$18 + 8, 0.0f, 46 + $$20 * 20 + 12, $$15 / 2, 8, 256, 256);
                GuiComponent.m_93133_(p_86887_, $$17 + $$15 / 2, $$18 + 8, 200 - $$15 / 2, 46 + $$20 * 20 + 12, $$15 / 2, 8, 256, 256);
                RenderSystem.m_69461_();
                int $$21 = p_86889_ + 11 + 5;
                int $$22 = $$19 ? 0xFFFFA0 : 0xFFFFFF;
                RealmsMainScreen.this.f_96547_.m_92889_(p_86887_, $$13, p_86888_ + 2, $$21 + 1, 15553363);
                GuiComponent.m_93215_(p_86887_, RealmsMainScreen.this.f_96547_, $$14, $$17 + $$15 / 2, $$21 + 1, $$22);
            } else {
                if (p_86886_.f_87485_ == RealmsServer.WorldType.MINIGAME) {
                    int $$23 = 0xCCAC5C;
                    int $$24 = RealmsMainScreen.this.f_96547_.m_92852_(f_86243_);
                    RealmsMainScreen.this.f_96547_.m_92889_(p_86887_, f_86243_, p_86888_ + 2, p_86889_ + 12, 0xCCAC5C);
                    RealmsMainScreen.this.f_96547_.m_92883_(p_86887_, p_86886_.m_87517_(), p_86888_ + 2 + $$24, p_86889_ + 12, 0x6C6C6C);
                } else {
                    RealmsMainScreen.this.f_96547_.m_92883_(p_86887_, p_86886_.m_87494_(), p_86888_ + 2, p_86889_ + 12, 0x6C6C6C);
                }
                if (!RealmsMainScreen.this.m_86683_(p_86886_)) {
                    RealmsMainScreen.this.f_96547_.m_92883_(p_86887_, p_86886_.f_87478_, p_86888_ + 2, p_86889_ + 12 + 11, 0x4C4C4C);
                }
            }
            RealmsMainScreen.this.f_96547_.m_92883_(p_86887_, p_86886_.m_87512_(), p_86888_ + 2, p_86889_ + 1, 0xFFFFFF);
            RealmsTextureManager.m_90187_(p_86886_.f_87479_, () -> {
                RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
                PlayerFaceRenderer.m_240071_(p_86887_, p_86888_ - 36, p_86889_, 32);
            });
        }

        @Override
        public Component m_142172_() {
            if (this.f_86853_.f_87477_ == RealmsServer.State.UNINITIALIZED) {
                return f_167175_;
            }
            return Component.m_237110_("narrator.select", this.f_86853_.f_87475_);
        }

        @Override
        @Nullable
        public RealmsServer m_183377_() {
            return this.f_86853_;
        }
    }
}

