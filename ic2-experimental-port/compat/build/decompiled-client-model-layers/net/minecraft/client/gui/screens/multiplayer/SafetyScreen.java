/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.multiplayer;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.WarningScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class SafetyScreen
extends WarningScreen {
    private static final Component f_99735_ = Component.m_237115_("multiplayerWarning.header").m_130940_(ChatFormatting.BOLD);
    private static final Component f_99736_ = Component.m_237115_("multiplayerWarning.message");
    private static final Component f_99737_ = Component.m_237115_("multiplayerWarning.check");
    private static final Component f_99738_ = f_99735_.m_6881_().m_130946_("\n").m_7220_(f_99736_);
    private final Screen f_232850_;

    public SafetyScreen(Screen p_99743_) {
        super(f_99735_, f_99736_, f_99737_, f_99738_);
        this.f_232850_ = p_99743_;
    }

    @Override
    protected void m_207212_(int p_210904_) {
        this.m_142416_(new Button(this.f_96543_ / 2 - 155, 100 + p_210904_, 150, 20, CommonComponents.f_130659_, p_210908_ -> {
            if (this.f_210910_.m_93840_()) {
                this.f_96541_.f_91066_.f_92083_ = true;
                this.f_96541_.f_91066_.m_92169_();
            }
            this.f_96541_.m_91152_(new JoinMultiplayerScreen(this.f_232850_));
        }));
        this.m_142416_(new Button(this.f_96543_ / 2 - 155 + 160, 100 + p_210904_, 150, 20, CommonComponents.f_130660_, p_210906_ -> this.f_96541_.m_91152_(this.f_232850_)));
    }
}

