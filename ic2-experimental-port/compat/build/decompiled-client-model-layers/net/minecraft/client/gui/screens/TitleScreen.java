/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.Runnables
 *  com.mojang.authlib.minecraft.BanDetails
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens;

import com.google.common.util.concurrent.Runnables;
import com.mojang.authlib.minecraft.BanDetails;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Vector3f;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import java.io.IOException;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.LanguageSelectScreen;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import org.slf4j.Logger;

public class TitleScreen
extends Screen {
    private static final Logger f_96717_ = LogUtils.getLogger();
    private static final String f_169439_ = "Demo_World";
    public static final Component f_169438_ = Component.m_237113_("Copyright Mojang AB. Do not distribute!");
    public static final CubeMap f_96716_ = new CubeMap(new ResourceLocation("textures/gui/title/background/panorama"));
    private static final ResourceLocation f_96718_ = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
    private static final ResourceLocation f_96719_ = new ResourceLocation("textures/gui/accessibility.png");
    private final boolean f_96720_;
    @Nullable
    private String f_96721_;
    private Button f_96722_;
    private static final ResourceLocation f_96723_ = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation f_96724_ = new ResourceLocation("textures/gui/title/edition.png");
    @Nullable
    private RealmsNotificationsScreen f_96726_;
    private final PanoramaRenderer f_96729_ = new PanoramaRenderer(f_96716_);
    private final boolean f_96714_;
    private long f_96715_;
    @Nullable
    private WarningLabel f_232768_;

    public TitleScreen() {
        this(false);
    }

    public TitleScreen(boolean p_96733_) {
        super(Component.m_237115_("narrator.screen.title"));
        this.f_96714_ = p_96733_;
        this.f_96720_ = (double)RandomSource.m_216327_().m_188501_() < 1.0E-4;
    }

    private boolean m_96789_() {
        return this.f_96541_.f_91066_.m_231822_().m_231551_() != false && this.f_96726_ != null;
    }

    @Override
    public void m_86600_() {
        if (this.m_96789_()) {
            this.f_96726_.m_86600_();
        }
        this.f_96541_.m_231416_().m_232208_(this);
    }

    public static CompletableFuture<Void> m_96754_(TextureManager p_96755_, Executor p_96756_) {
        return CompletableFuture.allOf(p_96755_.m_118501_(f_96723_, p_96756_), p_96755_.m_118501_(f_96724_, p_96756_), p_96755_.m_118501_(f_96718_, p_96756_), f_96716_.m_108854_(p_96755_, p_96756_));
    }

    @Override
    public boolean m_7043_() {
        return false;
    }

    @Override
    public boolean m_6913_() {
        return false;
    }

    @Override
    protected void m_7856_() {
        if (this.f_96721_ == null) {
            this.f_96721_ = this.f_96541_.m_91310_().m_118867_();
        }
        int $$0 = this.f_96547_.m_92852_(f_169438_);
        int $$1 = this.f_96543_ - $$0 - 2;
        int $$2 = 24;
        int $$3 = this.f_96544_ / 4 + 48;
        if (this.f_96541_.m_91402_()) {
            this.m_96772_($$3, 24);
        } else {
            this.m_96763_($$3, 24);
        }
        this.m_142416_(new ImageButton(this.f_96543_ / 2 - 124, $$3 + 72 + 12, 20, 20, 0, 106, 20, Button.f_93617_, 256, 256, p_96791_ -> this.f_96541_.m_91152_(new LanguageSelectScreen((Screen)this, this.f_96541_.f_91066_, this.f_96541_.m_91102_())), Component.m_237115_("narrator.button.language")));
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, $$3 + 72 + 12, 98, 20, Component.m_237115_("menu.options"), p_96788_ -> this.f_96541_.m_91152_(new OptionsScreen(this, this.f_96541_.f_91066_))));
        this.m_142416_(new Button(this.f_96543_ / 2 + 2, $$3 + 72 + 12, 98, 20, Component.m_237115_("menu.quit"), p_96786_ -> this.f_96541_.m_91395_()));
        this.m_142416_(new ImageButton(this.f_96543_ / 2 + 104, $$3 + 72 + 12, 20, 20, 0, 0, 20, f_96719_, 32, 64, p_96784_ -> this.f_96541_.m_91152_(new AccessibilityOptionsScreen(this, this.f_96541_.f_91066_)), Component.m_237115_("narrator.button.accessibility")));
        this.m_142416_(new PlainTextButton($$1, this.f_96544_ - 10, $$0, 10, f_169438_, p_211790_ -> this.f_96541_.m_91152_(new WinScreen(false, Runnables.doNothing())), this.f_96547_));
        this.f_96541_.m_91372_(false);
        if (this.f_96541_.f_91066_.m_231822_().m_231551_().booleanValue() && this.f_96726_ == null) {
            this.f_96726_ = new RealmsNotificationsScreen();
        }
        if (this.m_96789_()) {
            this.f_96726_.m_6575_(this.f_96541_, this.f_96543_, this.f_96544_);
        }
        if (!this.f_96541_.m_91103_()) {
            this.f_232768_ = new WarningLabel(this.f_96547_, MultiLineLabel.m_94345_(this.f_96547_, Component.m_237115_("title.32bit.deprecation"), 350, 2), this.f_96543_ / 2, $$3 - 24);
        }
    }

    private void m_96763_(int p_96764_, int p_96765_) {
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, p_96764_, 200, 20, Component.m_237115_("menu.singleplayer"), p_232779_ -> this.f_96541_.m_91152_(new SelectWorldScreen(this))));
        final Component $$2 = this.m_240255_();
        boolean $$3 = $$2 == null;
        Button.OnTooltip $$4 = $$2 == null ? Button.f_93716_ : new Button.OnTooltip(){

            @Override
            public void m_93752_(Button p_169458_, PoseStack p_169459_, int p_169460_, int p_169461_) {
                TitleScreen.this.m_96617_(p_169459_, TitleScreen.this.f_96541_.f_91062_.m_92923_($$2, Math.max(TitleScreen.this.f_96543_ / 2 - 43, 170)), p_169460_, p_169461_);
            }

            @Override
            public void m_142753_(Consumer<Component> p_169456_) {
                p_169456_.accept($$2);
            }
        };
        this.m_142416_(new Button((int)(this.f_96543_ / 2 - 100), (int)(p_96764_ + p_96765_ * 1), (int)200, (int)20, (Component)Component.m_237115_((String)"menu.multiplayer"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, m_96775_(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((TitleScreen)this), (Button.OnTooltip)$$4)).f_93623_ = $$3;
        this.m_142416_(new Button((int)(this.f_96543_ / 2 - 100), (int)(p_96764_ + p_96765_ * 2), (int)200, (int)20, (Component)Component.m_237115_((String)"menu.online"), (Button.OnPress)(Button.OnPress)LambdaMetafactory.metafactory(null, null, null, (Lnet/minecraft/client/gui/components/Button;)V, m_210871_(net.minecraft.client.gui.components.Button ), (Lnet/minecraft/client/gui/components/Button;)V)((TitleScreen)this), (Button.OnTooltip)$$4)).f_93623_ = $$3;
    }

    @Nullable
    private Component m_240255_() {
        if (this.f_96541_.m_91400_()) {
            return null;
        }
        BanDetails $$0 = this.f_96541_.m_239210_();
        if ($$0 != null) {
            if ($$0.expires() != null) {
                return Component.m_237115_("title.multiplayer.disabled.banned.temporary");
            }
            return Component.m_237115_("title.multiplayer.disabled.banned.permanent");
        }
        return Component.m_237115_("title.multiplayer.disabled");
    }

    private void m_96772_(int p_96773_, int p_96774_) {
        boolean $$2 = this.m_96792_();
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, p_96773_, 200, 20, Component.m_237115_("menu.playdemo"), p_232773_ -> {
            if ($$2) {
                this.f_96541_.m_231466_().m_233133_(this, f_169439_);
            } else {
                RegistryAccess.Frozen $$2 = RegistryAccess.m_206197_().m_203557_();
                this.f_96541_.m_231466_().m_233157_(f_169439_, MinecraftServer.f_129743_, $$2, WorldPresets.m_226461_($$2));
            }
        }));
        this.f_96722_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, p_96773_ + p_96774_ * 1, 200, 20, Component.m_237115_("menu.resetdemo"), p_232770_ -> {
            LevelStorageSource $$1 = this.f_96541_.m_91392_();
            try (LevelStorageSource.LevelStorageAccess $$2 = $$1.m_78260_(f_169439_);){
                LevelSummary $$3 = $$2.m_78308_();
                if ($$3 != null) {
                    this.f_96541_.m_91152_(new ConfirmScreen(this::m_96777_, Component.m_237115_("selectWorld.deleteQuestion"), Component.m_237110_("selectWorld.deleteWarning", $$3.m_78361_()), Component.m_237115_("selectWorld.deleteButton"), CommonComponents.f_130656_));
                }
            }
            catch (IOException $$4) {
                SystemToast.m_94852_(this.f_96541_, f_169439_);
                f_96717_.warn("Failed to access demo world", (Throwable)$$4);
            }
        }));
        this.f_96722_.f_93623_ = $$2;
    }

    private boolean m_96792_() {
        boolean bl;
        block8: {
            LevelStorageSource.LevelStorageAccess $$0 = this.f_96541_.m_91392_().m_78260_(f_169439_);
            try {
                boolean bl2 = bl = $$0.m_78308_() != null;
                if ($$0 == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if ($$0 != null) {
                        try {
                            $$0.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (IOException $$1) {
                    SystemToast.m_94852_(this.f_96541_, f_169439_);
                    f_96717_.warn("Failed to read demo world data", (Throwable)$$1);
                    return false;
                }
            }
            $$0.close();
        }
        return bl;
    }

    private void m_96793_() {
        this.f_96541_.m_91152_(new RealmsMainScreen(this));
    }

    @Override
    public void m_6305_(PoseStack p_96739_, int p_96740_, int p_96741_, float p_96742_) {
        if (this.f_96715_ == 0L && this.f_96714_) {
            this.f_96715_ = Util.m_137550_();
        }
        float $$4 = this.f_96714_ ? (float)(Util.m_137550_() - this.f_96715_) / 1000.0f : 1.0f;
        this.f_96729_.m_110003_(p_96742_, Mth.m_14036_($$4, 0.0f, 1.0f));
        int $$5 = 274;
        int $$6 = this.f_96543_ / 2 - 137;
        int $$7 = 30;
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_96718_);
        RenderSystem.m_69478_();
        RenderSystem.m_69408_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, this.f_96714_ ? (float)Mth.m_14167_(Mth.m_14036_($$4, 0.0f, 1.0f)) : 1.0f);
        TitleScreen.m_93160_(p_96739_, 0, 0, this.f_96543_, this.f_96544_, 0.0f, 0.0f, 16, 128, 16, 128);
        float $$8 = this.f_96714_ ? Mth.m_14036_($$4 - 1.0f, 0.0f, 1.0f) : 1.0f;
        int $$9 = Mth.m_14167_($$8 * 255.0f) << 24;
        if (($$9 & 0xFC000000) == 0) {
            return;
        }
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157456_(0, f_96723_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, $$8);
        if (this.f_96720_) {
            this.m_93101_($$6, 30, (p_232776_, p_232777_) -> {
                this.m_93228_(p_96739_, p_232776_ + 0, (int)p_232777_, 0, 0, 99, 44);
                this.m_93228_(p_96739_, p_232776_ + 99, (int)p_232777_, 129, 0, 27, 44);
                this.m_93228_(p_96739_, p_232776_ + 99 + 26, (int)p_232777_, 126, 0, 3, 44);
                this.m_93228_(p_96739_, p_232776_ + 99 + 26 + 3, (int)p_232777_, 99, 0, 26, 44);
                this.m_93228_(p_96739_, p_232776_ + 155, (int)p_232777_, 0, 45, 155, 44);
            });
        } else {
            this.m_93101_($$6, 30, (p_210862_, p_210863_) -> {
                this.m_93228_(p_96739_, p_210862_ + 0, (int)p_210863_, 0, 0, 155, 44);
                this.m_93228_(p_96739_, p_210862_ + 155, (int)p_210863_, 0, 45, 155, 44);
            });
        }
        RenderSystem.m_157456_(0, f_96724_);
        TitleScreen.m_93133_(p_96739_, $$6 + 88, 67, 0.0f, 0.0f, 98, 14, 128, 16);
        if (this.f_232768_ != null) {
            this.f_232768_.m_232790_(p_96739_, $$9);
        }
        if (this.f_96721_ != null) {
            p_96739_.m_85836_();
            p_96739_.m_85837_(this.f_96543_ / 2 + 90, 70.0, 0.0);
            p_96739_.m_85845_(Vector3f.f_122227_.m_122240_(-20.0f));
            float $$10 = 1.8f - Mth.m_14154_(Mth.m_14031_((float)(Util.m_137550_() % 1000L) / 1000.0f * ((float)Math.PI * 2)) * 0.1f);
            $$10 = $$10 * 100.0f / (float)(this.f_96547_.m_92895_(this.f_96721_) + 32);
            p_96739_.m_85841_($$10, $$10, $$10);
            TitleScreen.m_93208_(p_96739_, this.f_96547_, this.f_96721_, 0, -8, 0xFFFF00 | $$9);
            p_96739_.m_85849_();
        }
        String $$11 = "Minecraft " + SharedConstants.m_183709_().getName();
        $$11 = this.f_96541_.m_91402_() ? $$11 + " Demo" : $$11 + (String)("release".equalsIgnoreCase(this.f_96541_.m_91389_()) ? "" : "/" + this.f_96541_.m_91389_());
        if (Minecraft.m_193589_().m_184597_()) {
            $$11 = $$11 + I18n.m_118938_("menu.modded", new Object[0]);
        }
        TitleScreen.m_93236_(p_96739_, this.f_96547_, $$11, 2, this.f_96544_ - 10, 0xFFFFFF | $$9);
        for (GuiEventListener guiEventListener : this.m_6702_()) {
            if (!(guiEventListener instanceof AbstractWidget)) continue;
            ((AbstractWidget)guiEventListener).m_93650_($$8);
        }
        super.m_6305_(p_96739_, p_96740_, p_96741_, p_96742_);
        if (this.m_96789_() && $$8 >= 1.0f) {
            RenderSystem.m_69482_();
            this.f_96726_.m_6305_(p_96739_, p_96740_, p_96741_, p_96742_);
        }
    }

    @Override
    public boolean m_6375_(double p_96735_, double p_96736_, int p_96737_) {
        if (super.m_6375_(p_96735_, p_96736_, p_96737_)) {
            return true;
        }
        return this.m_96789_() && this.f_96726_.m_6375_(p_96735_, p_96736_, p_96737_);
    }

    @Override
    public void m_7861_() {
        if (this.f_96726_ != null) {
            this.f_96726_.m_7861_();
        }
    }

    private void m_96777_(boolean p_96778_) {
        if (p_96778_) {
            try (LevelStorageSource.LevelStorageAccess $$1 = this.f_96541_.m_91392_().m_78260_(f_169439_);){
                $$1.m_78311_();
            }
            catch (IOException $$2) {
                SystemToast.m_94866_(this.f_96541_, f_169439_);
                f_96717_.warn("Failed to delete demo world", (Throwable)$$2);
            }
        }
        this.f_96541_.m_91152_(this);
    }

    private /* synthetic */ void m_210871_(Button p_210872_) {
        this.m_96793_();
    }

    private /* synthetic */ void m_96775_(Button p_96776_) {
        Screen $$1 = this.f_96541_.f_91066_.f_92083_ ? new JoinMultiplayerScreen(this) : new SafetyScreen(this);
        this.f_96541_.m_91152_($$1);
    }

    record WarningLabel(Font f_232780_, MultiLineLabel f_232781_, int f_232782_, int f_232783_) {
        public void m_232790_(PoseStack p_232791_, int p_232792_) {
            this.f_232781_.m_207298_(p_232791_, this.f_232782_, this.f_232783_, this.f_232780_.f_92710_, 2, 0x55200000);
            this.f_232781_.m_6514_(p_232791_, this.f_232782_, this.f_232783_, this.f_232780_.f_92710_, 0xFFFFFF | p_232792_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{WarningLabel.class, "font;label;x;y", "f_232780_", "f_232781_", "f_232782_", "f_232783_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{WarningLabel.class, "font;label;x;y", "f_232780_", "f_232781_", "f_232782_", "f_232783_"}, this);
        }

        @Override
        public final boolean equals(Object p_232797_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{WarningLabel.class, "font;label;x;y", "f_232780_", "f_232781_", "f_232782_", "f_232783_"}, this, p_232797_);
        }
    }
}

