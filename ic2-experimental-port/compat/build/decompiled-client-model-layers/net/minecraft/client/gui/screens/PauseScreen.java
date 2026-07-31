/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.RealmsMainScreen;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class PauseScreen
extends Screen {
    private static final String f_169332_ = "https://aka.ms/snapshotfeedback?ref=game";
    private static final String f_169333_ = "https://aka.ms/javafeedback?ref=game";
    private static final String f_169334_ = "https://aka.ms/snapshotbugs?ref=game";
    private final boolean f_96306_;

    public PauseScreen(boolean p_96308_) {
        super(p_96308_ ? Component.m_237115_("menu.game") : Component.m_237115_("menu.paused"));
        this.f_96306_ = p_96308_;
    }

    @Override
    protected void m_7856_() {
        if (this.f_96306_) {
            this.m_96338_();
        }
    }

    private void m_96338_() {
        int $$0 = -16;
        int $$1 = 98;
        this.m_142416_(new Button(this.f_96543_ / 2 - 102, this.f_96544_ / 4 + 24 + -16, 204, 20, Component.m_237115_("menu.returnToGame"), p_96337_ -> {
            this.f_96541_.m_91152_(null);
            this.f_96541_.f_91067_.m_91601_();
        }));
        this.m_142416_(new Button(this.f_96543_ / 2 - 102, this.f_96544_ / 4 + 48 + -16, 98, 20, Component.m_237115_("gui.advancements"), p_96335_ -> this.f_96541_.m_91152_(new AdvancementsScreen(this.f_96541_.f_91074_.f_108617_.m_105145_()))));
        this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ / 4 + 48 + -16, 98, 20, Component.m_237115_("gui.stats"), p_96333_ -> this.f_96541_.m_91152_(new StatsScreen(this, this.f_96541_.f_91074_.m_108630_()))));
        String $$2 = SharedConstants.m_183709_().isStable() ? f_169333_ : f_169332_;
        this.m_142416_(new Button(this.f_96543_ / 2 - 102, this.f_96544_ / 4 + 72 + -16, 98, 20, Component.m_237115_("menu.sendFeedback"), p_96318_ -> this.f_96541_.m_91152_(new ConfirmLinkScreen(p_169337_ -> {
            if (p_169337_) {
                Util.m_137581_().m_137646_($$2);
            }
            this.f_96541_.m_91152_(this);
        }, $$2, true))));
        Button $$3 = this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ / 4 + 72 + -16, 98, 20, Component.m_237115_("menu.reportBugs"), p_96331_ -> this.f_96541_.m_91152_(new ConfirmLinkScreen(p_169339_ -> {
            if (p_169339_) {
                Util.m_137581_().m_137646_(f_169334_);
            }
            this.f_96541_.m_91152_(this);
        }, f_169334_, true))));
        $$3.f_93623_ = !SharedConstants.m_183709_().m_183476_().m_193002_();
        this.m_142416_(new Button(this.f_96543_ / 2 - 102, this.f_96544_ / 4 + 96 + -16, 98, 20, Component.m_237115_("menu.options"), p_96323_ -> this.f_96541_.m_91152_(new OptionsScreen(this, this.f_96541_.f_91066_))));
        if (this.f_96541_.m_91091_() && !this.f_96541_.m_91092_().m_6992_()) {
            this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ / 4 + 96 + -16, 98, 20, Component.m_237115_("menu.shareToLan"), p_96321_ -> this.f_96541_.m_91152_(new ShareToLanScreen(this))));
        } else {
            this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ / 4 + 96 + -16, 98, 20, Component.m_237115_("menu.playerReporting"), p_238870_ -> this.f_96541_.m_91152_(new SocialInteractionsScreen())));
        }
        MutableComponent $$4 = this.f_96541_.m_91090_() ? Component.m_237115_("menu.returnToMenu") : Component.m_237115_("menu.disconnect");
        this.m_142416_(new Button(this.f_96543_ / 2 - 102, this.f_96544_ / 4 + 120 + -16, 204, 20, $$4, p_96315_ -> {
            boolean $$1 = this.f_96541_.m_91090_();
            boolean $$2 = this.f_96541_.m_91294_();
            p_96315_.f_93623_ = false;
            this.f_96541_.f_91073_.m_7462_();
            if ($$1) {
                this.f_96541_.m_91320_(new GenericDirtMessageScreen(Component.m_237115_("menu.savingLevel")));
            } else {
                this.f_96541_.m_91399_();
            }
            TitleScreen $$3 = new TitleScreen();
            if ($$1) {
                this.f_96541_.m_91152_($$3);
            } else if ($$2) {
                this.f_96541_.m_91152_(new RealmsMainScreen($$3));
            } else {
                this.f_96541_.m_91152_(new JoinMultiplayerScreen($$3));
            }
        }));
    }

    @Override
    public void m_86600_() {
        super.m_86600_();
    }

    @Override
    public void m_6305_(PoseStack p_96310_, int p_96311_, int p_96312_, float p_96313_) {
        if (this.f_96306_) {
            this.m_7333_(p_96310_);
            PauseScreen.m_93215_(p_96310_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 40, 0xFFFFFF);
        } else {
            PauseScreen.m_93215_(p_96310_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 10, 0xFFFFFF);
        }
        super.m_6305_(p_96310_, p_96311_, p_96312_, p_96313_);
    }
}

