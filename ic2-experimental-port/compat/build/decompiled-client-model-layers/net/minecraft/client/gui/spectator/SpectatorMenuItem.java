/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.spectator;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.network.chat.Component;

public interface SpectatorMenuItem {
    public void m_7608_(SpectatorMenu var1);

    public Component m_7869_();

    public void m_6252_(PoseStack var1, float var2, int var3);

    public boolean m_7304_();
}

