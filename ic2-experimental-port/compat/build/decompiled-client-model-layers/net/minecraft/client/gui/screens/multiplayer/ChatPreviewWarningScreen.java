/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screens.multiplayer;

import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.WarningScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.chat.ChatPreviewStatus;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ChatPreviewWarningScreen
extends WarningScreen {
    private static final Component f_232829_ = Component.m_237115_("chatPreview.warning.title").m_130940_(ChatFormatting.BOLD);
    private static final Component f_232831_ = Component.m_237115_("chatPreview.warning.check");
    private final ServerData f_232833_;
    @Nullable
    private final Screen f_232834_;

    private static Component m_241944_() {
        ChatPreviewStatus $$0 = Minecraft.m_91087_().f_91066_.m_231835_().m_231551_();
        return Component.m_237110_("chatPreview.warning.content", $$0.m_216301_());
    }

    public ChatPreviewWarningScreen(@Nullable Screen p_232837_, ServerData p_232838_) {
        super(f_232829_, ChatPreviewWarningScreen.m_241944_(), f_232831_, CommonComponents.m_178398_(f_232829_, ChatPreviewWarningScreen.m_241944_()));
        this.f_232833_ = p_232838_;
        this.f_232834_ = p_232837_;
    }

    @Override
    protected void m_207212_(int p_232840_) {
        this.m_142416_(new Button(this.f_96543_ / 2 - 155, 100 + p_232840_, 150, 20, Component.m_237115_("menu.disconnect"), p_232846_ -> {
            this.f_96541_.f_91073_.m_7462_();
            this.f_96541_.m_91399_();
            this.f_96541_.m_91152_(new JoinMultiplayerScreen(new TitleScreen()));
        }));
        this.m_142416_(new Button(this.f_96543_ / 2 + 5, 100 + p_232840_, 150, 20, CommonComponents.f_130659_, p_232842_ -> {
            this.m_232848_();
            this.m_7379_();
        }));
    }

    @Override
    public boolean m_6913_() {
        return false;
    }

    private void m_232848_() {
        ServerData.ChatPreview $$0;
        if (this.f_210910_ != null && this.f_210910_.m_93840_() && ($$0 = this.f_232833_.m_233817_()) != null) {
            $$0.m_233826_();
            ServerList.m_105446_(this.f_232833_);
        }
    }

    @Override
    protected int m_214169_() {
        return this.f_96547_.f_92710_ * 3 / 2;
    }

    @Override
    public void m_7379_() {
        this.f_96541_.m_91152_(this.f_232834_);
    }
}

