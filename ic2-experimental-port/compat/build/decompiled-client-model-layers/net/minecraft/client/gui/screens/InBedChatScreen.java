/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;

public class InBedChatScreen
extends ChatScreen {
    private Button f_242488_;

    public InBedChatScreen() {
        super("");
    }

    @Override
    protected void m_7856_() {
        super.m_7856_();
        this.f_242488_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 40, 200, 20, Component.m_237115_("multiplayer.stopSleeping"), p_96074_ -> this.m_96077_()));
    }

    @Override
    public void m_6305_(PoseStack p_242941_, int p_242857_, int p_242871_, float p_242925_) {
        this.f_242488_.f_93624_ = this.m_242596_() == null;
        super.m_6305_(p_242941_, p_242857_, p_242871_, p_242925_);
    }

    @Override
    public void m_7379_() {
        this.m_96077_();
    }

    @Override
    public boolean m_7933_(int p_96070_, int p_96071_, int p_96072_) {
        if (p_96070_ == 256) {
            this.m_96077_();
        } else if (p_96070_ == 257 || p_96070_ == 335) {
            if (this.m_241797_(this.f_95573_.m_94155_(), true)) {
                this.f_96541_.m_91152_(null);
                this.f_95573_.m_94144_("");
                this.f_96541_.f_91065_.m_93076_().m_93810_();
            }
            return true;
        }
        return super.m_7933_(p_96070_, p_96071_, p_96072_);
    }

    private void m_96077_() {
        ClientPacketListener $$0 = this.f_96541_.f_91074_.f_108617_;
        $$0.m_104955_(new ServerboundPlayerCommandPacket(this.f_96541_.f_91074_, ServerboundPlayerCommandPacket.Action.STOP_SLEEPING));
    }

    public void m_193839_() {
        if (this.f_95573_.m_94155_().isEmpty()) {
            this.f_96541_.m_91152_(null);
        } else {
            this.f_96541_.m_91152_(new ChatScreen(this.f_95573_.m_94155_()));
        }
    }
}

