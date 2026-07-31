/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.gui.screens;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.LockIconButton;
import net.minecraft.client.gui.screens.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.ChatOptionsScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.LanguageSelectScreen;
import net.minecraft.client.gui.screens.OnlineOptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SkinCustomizationScreen;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import net.minecraft.client.gui.screens.VideoSettingsScreen;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ServerboundLockDifficultyPacket;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.Difficulty;

public class OptionsScreen
extends Screen {
    private final Screen f_96235_;
    private final Options f_96236_;
    private CycleButton<Difficulty> f_96237_;
    private LockIconButton f_96238_;

    public OptionsScreen(Screen p_96242_, Options p_96243_) {
        super(Component.m_237115_("options.title"));
        this.f_96235_ = p_96242_;
        this.f_96236_ = p_96243_;
    }

    @Override
    protected void m_7856_() {
        int $$0 = 0;
        for (OptionInstance $$1 : new OptionInstance[]{this.f_96236_.m_231837_()}) {
            int $$2 = this.f_96543_ / 2 - 155 + $$0 % 2 * 160;
            int $$3 = this.f_96544_ / 6 - 12 + 24 * ($$0 >> 1);
            this.m_142416_($$1.m_231507_(this.f_96541_.f_91066_, $$2, $$3, 150));
            ++$$0;
        }
        if (this.f_96541_.f_91073_ != null && this.f_96541_.m_91091_()) {
            this.f_96237_ = this.m_142416_(OptionsScreen.m_193846_($$0, this.f_96543_, this.f_96544_, "options.difficulty", this.f_96541_));
            if (!this.f_96541_.f_91073_.m_6106_().m_5466_()) {
                this.f_96237_.m_93674_(this.f_96237_.m_5711_() - 20);
                this.f_96238_ = this.m_142416_(new LockIconButton(this.f_96237_.f_93620_ + this.f_96237_.m_5711_(), this.f_96237_.f_93621_, p_193857_ -> this.f_96541_.m_91152_(new ConfirmScreen(this::m_96260_, Component.m_237115_("difficulty.lock.title"), Component.m_237110_("difficulty.lock.question", this.f_96541_.f_91073_.m_6106_().m_5472_().m_19033_())))));
                this.f_96238_.m_94309_(this.f_96541_.f_91073_.m_6106_().m_5474_());
                this.f_96238_.f_93623_ = !this.f_96238_.m_94302_();
                this.f_96237_.f_93623_ = !this.f_96238_.m_94302_();
            } else {
                this.f_96237_.f_93623_ = false;
            }
        } else {
            this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ / 6 - 12 + 24 * ($$0 >> 1), 150, 20, Component.m_237115_("options.online"), p_96278_ -> this.f_96541_.m_91152_(new OnlineOptionsScreen(this, this.f_96236_))));
        }
        this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ / 6 + 48 - 6, 150, 20, Component.m_237115_("options.skinCustomisation"), p_96276_ -> this.f_96541_.m_91152_(new SkinCustomizationScreen(this, this.f_96236_))));
        this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ / 6 + 48 - 6, 150, 20, Component.m_237115_("options.sounds"), p_96274_ -> this.f_96541_.m_91152_(new SoundOptionsScreen(this, this.f_96236_))));
        this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ / 6 + 72 - 6, 150, 20, Component.m_237115_("options.video"), p_96272_ -> this.f_96541_.m_91152_(new VideoSettingsScreen(this, this.f_96236_))));
        this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ / 6 + 72 - 6, 150, 20, Component.m_237115_("options.controls"), p_96270_ -> this.f_96541_.m_91152_(new ControlsScreen(this, this.f_96236_))));
        this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ / 6 + 96 - 6, 150, 20, Component.m_237115_("options.language"), p_96268_ -> this.f_96541_.m_91152_(new LanguageSelectScreen((Screen)this, this.f_96236_, this.f_96541_.m_91102_()))));
        this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ / 6 + 96 - 6, 150, 20, Component.m_237115_("options.chat.title"), p_96266_ -> this.f_96541_.m_91152_(new ChatOptionsScreen(this, this.f_96236_))));
        this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ / 6 + 120 - 6, 150, 20, Component.m_237115_("options.resourcepack"), p_96263_ -> this.f_96541_.m_91152_(new PackSelectionScreen(this, this.f_96541_.m_91099_(), this::m_96244_, this.f_96541_.m_91101_(), Component.m_237115_("resourcePack.title")))));
        this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ / 6 + 120 - 6, 150, 20, Component.m_237115_("options.accessibility.title"), p_96259_ -> this.f_96541_.m_91152_(new AccessibilityOptionsScreen(this, this.f_96236_))));
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 6 + 168, 200, 20, CommonComponents.f_130655_, p_96257_ -> this.f_96541_.m_91152_(this.f_96235_)));
    }

    public static CycleButton<Difficulty> m_193846_(int p_193847_, int p_193848_, int p_193849_, String p_193850_, Minecraft p_193851_) {
        return CycleButton.m_168894_(Difficulty::m_19033_).m_168961_((Difficulty[])Difficulty.values()).m_168948_(p_193851_.f_91073_.m_46791_()).m_168936_(p_193848_ / 2 - 155 + p_193847_ % 2 * 160, p_193849_ / 6 - 12 + 24 * (p_193847_ >> 1), 150, 20, Component.m_237115_(p_193850_), (p_193854_, p_193855_) -> p_193851_.m_91403_().m_104955_(new ServerboundChangeDifficultyPacket((Difficulty)((Object)p_193855_))));
    }

    private void m_96244_(PackRepository p_96245_) {
        ImmutableList $$1 = ImmutableList.copyOf(this.f_96236_.f_92117_);
        this.f_96236_.f_92117_.clear();
        this.f_96236_.f_92118_.clear();
        for (Pack $$2 : p_96245_.m_10524_()) {
            if ($$2.m_10450_()) continue;
            this.f_96236_.f_92117_.add($$2.m_10446_());
            if ($$2.m_10443_().m_10489_()) continue;
            this.f_96236_.f_92118_.add($$2.m_10446_());
        }
        this.f_96236_.m_92169_();
        ImmutableList $$3 = ImmutableList.copyOf(this.f_96236_.f_92117_);
        if (!$$3.equals($$1)) {
            this.f_96541_.m_91391_();
        }
    }

    private void m_96260_(boolean p_96261_) {
        this.f_96541_.m_91152_(this);
        if (p_96261_ && this.f_96541_.f_91073_ != null) {
            this.f_96541_.m_91403_().m_104955_(new ServerboundLockDifficultyPacket(true));
            this.f_96238_.m_94309_(true);
            this.f_96238_.f_93623_ = false;
            this.f_96237_.f_93623_ = false;
        }
    }

    @Override
    public void m_7861_() {
        this.f_96236_.m_92169_();
    }

    @Override
    public void m_6305_(PoseStack p_96249_, int p_96250_, int p_96251_, float p_96252_) {
        this.m_7333_(p_96249_);
        OptionsScreen.m_93215_(p_96249_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 15, 0xFFFFFF);
        super.m_6305_(p_96249_, p_96250_, p_96251_, p_96252_);
    }
}

