/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.multiplayer;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.WarningScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class Realms32bitWarningScreen
extends WarningScreen {
    private static final Component f_210892_ = Component.m_237115_("title.32bit.deprecation.realms.header").m_130940_(ChatFormatting.BOLD);
    private static final Component f_210893_ = Component.m_237115_("title.32bit.deprecation.realms");
    private static final Component f_210894_ = Component.m_237115_("title.32bit.deprecation.realms.check");
    private static final Component f_210895_ = f_210892_.m_6881_().m_130946_("\n").m_7220_(f_210893_);
    private final Screen f_232849_;

    public Realms32bitWarningScreen(Screen p_210898_) {
        super(f_210892_, f_210893_, f_210894_, f_210895_);
        this.f_232849_ = p_210898_;
    }

    @Override
    protected void m_207212_(int p_210900_) {
        this.m_142416_(new Button(this.f_96543_ / 2 - 75, 100 + p_210900_, 150, 20, CommonComponents.f_130655_, p_210902_ -> {
            if (this.f_210910_.m_93840_()) {
                this.f_96541_.f_91066_.f_210816_ = true;
                this.f_96541_.f_91066_.m_92169_();
            }
            this.f_96541_.m_91152_(this.f_232849_);
        }));
    }
}

