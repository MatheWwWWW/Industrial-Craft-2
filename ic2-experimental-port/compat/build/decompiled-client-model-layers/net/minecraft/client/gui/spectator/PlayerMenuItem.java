/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 */
package net.minecraft.client.gui.spectator;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundTeleportToEntityPacket;
import net.minecraft.resources.ResourceLocation;

public class PlayerMenuItem
implements SpectatorMenuItem {
    private final GameProfile f_101752_;
    private final ResourceLocation f_101753_;
    private final Component f_101754_;

    public PlayerMenuItem(GameProfile p_101756_) {
        this.f_101752_ = p_101756_;
        Minecraft $$1 = Minecraft.m_91087_();
        this.f_101753_ = $$1.m_91109_().m_240306_(p_101756_);
        this.f_101754_ = Component.m_237113_(p_101756_.getName());
    }

    @Override
    public void m_7608_(SpectatorMenu p_101762_) {
        Minecraft.m_91087_().m_91403_().m_104955_(new ServerboundTeleportToEntityPacket(this.f_101752_.getId()));
    }

    @Override
    public Component m_7869_() {
        return this.f_101754_;
    }

    @Override
    public void m_6252_(PoseStack p_101758_, float p_101759_, int p_101760_) {
        RenderSystem.m_157456_(0, this.f_101753_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, (float)p_101760_ / 255.0f);
        PlayerFaceRenderer.m_240071_(p_101758_, 2, 2, 12);
    }

    @Override
    public boolean m_7304_() {
        return true;
    }
}

