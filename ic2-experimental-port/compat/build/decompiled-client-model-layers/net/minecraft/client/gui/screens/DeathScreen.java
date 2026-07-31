/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class DeathScreen
extends Screen {
    private int f_95906_;
    private final Component f_95907_;
    private final boolean f_95908_;
    private Component f_95909_;
    private final List<Button> f_169295_ = Lists.newArrayList();

    public DeathScreen(@Nullable Component p_95911_, boolean p_95912_) {
        super(Component.m_237115_(p_95912_ ? "deathScreen.title.hardcore" : "deathScreen.title"));
        this.f_95907_ = p_95911_;
        this.f_95908_ = p_95912_;
    }

    @Override
    protected void m_7856_() {
        this.f_95906_ = 0;
        this.f_169295_.clear();
        this.f_169295_.add(this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 72, 200, 20, this.f_95908_ ? Component.m_237115_("deathScreen.spectate") : Component.m_237115_("deathScreen.respawn"), p_95930_ -> {
            this.f_96541_.f_91074_.m_7583_();
            this.f_96541_.m_91152_(null);
        })));
        this.f_169295_.add(this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 96, 200, 20, Component.m_237115_("deathScreen.titleScreen"), p_95925_ -> {
            if (this.f_95908_) {
                this.m_95934_();
                return;
            }
            ConfirmScreen $$1 = new ConfirmScreen(this::m_95931_, Component.m_237115_("deathScreen.quit.confirm"), CommonComponents.f_237098_, Component.m_237115_("deathScreen.titleScreen"), Component.m_237115_("deathScreen.respawn"));
            this.f_96541_.m_91152_($$1);
            $$1.m_95663_(20);
        })));
        for (Button $$0 : this.f_169295_) {
            $$0.f_93623_ = false;
        }
        this.f_95909_ = Component.m_237115_("deathScreen.score").m_130946_(": ").m_7220_(Component.m_237113_(Integer.toString(this.f_96541_.f_91074_.m_36344_())).m_130940_(ChatFormatting.YELLOW));
    }

    @Override
    public boolean m_6913_() {
        return false;
    }

    private void m_95931_(boolean p_95932_) {
        if (p_95932_) {
            this.m_95934_();
        } else {
            this.f_96541_.f_91074_.m_7583_();
            this.f_96541_.m_91152_(null);
        }
    }

    private void m_95934_() {
        if (this.f_96541_.f_91073_ != null) {
            this.f_96541_.f_91073_.m_7462_();
        }
        this.f_96541_.m_91320_(new GenericDirtMessageScreen(Component.m_237115_("menu.savingLevel")));
        this.f_96541_.m_91152_(new TitleScreen());
    }

    @Override
    public void m_6305_(PoseStack p_95920_, int p_95921_, int p_95922_, float p_95923_) {
        this.m_93179_(p_95920_, 0, 0, this.f_96543_, this.f_96544_, 0x60500000, -1602211792);
        p_95920_.m_85836_();
        p_95920_.m_85841_(2.0f, 2.0f, 2.0f);
        DeathScreen.m_93215_(p_95920_, this.f_96547_, this.f_96539_, this.f_96543_ / 2 / 2, 30, 0xFFFFFF);
        p_95920_.m_85849_();
        if (this.f_95907_ != null) {
            DeathScreen.m_93215_(p_95920_, this.f_96547_, this.f_95907_, this.f_96543_ / 2, 85, 0xFFFFFF);
        }
        DeathScreen.m_93215_(p_95920_, this.f_96547_, this.f_95909_, this.f_96543_ / 2, 100, 0xFFFFFF);
        if (this.f_95907_ != null && p_95922_ > 85 && p_95922_ < 85 + this.f_96547_.f_92710_) {
            Style $$4 = this.m_95917_(p_95921_);
            this.m_96570_(p_95920_, $$4, p_95921_, p_95922_);
        }
        super.m_6305_(p_95920_, p_95921_, p_95922_, p_95923_);
    }

    @Nullable
    private Style m_95917_(int p_95918_) {
        if (this.f_95907_ == null) {
            return null;
        }
        int $$1 = this.f_96541_.f_91062_.m_92852_(this.f_95907_);
        int $$2 = this.f_96543_ / 2 - $$1 / 2;
        int $$3 = this.f_96543_ / 2 + $$1 / 2;
        if (p_95918_ < $$2 || p_95918_ > $$3) {
            return null;
        }
        return this.f_96541_.f_91062_.m_92865_().m_92386_(this.f_95907_, p_95918_ - $$2);
    }

    @Override
    public boolean m_6375_(double p_95914_, double p_95915_, int p_95916_) {
        Style $$3;
        if (this.f_95907_ != null && p_95915_ > 85.0 && p_95915_ < (double)(85 + this.f_96547_.f_92710_) && ($$3 = this.m_95917_((int)p_95914_)) != null && $$3.m_131182_() != null && $$3.m_131182_().m_130622_() == ClickEvent.Action.OPEN_URL) {
            this.m_5561_($$3);
            return false;
        }
        return super.m_6375_(p_95914_, p_95915_, p_95916_);
    }

    @Override
    public boolean m_7043_() {
        return false;
    }

    @Override
    public void m_86600_() {
        super.m_86600_();
        ++this.f_95906_;
        if (this.f_95906_ == 20) {
            for (Button $$0 : this.f_169295_) {
                $$0.f_93623_ = true;
            }
        }
    }
}

