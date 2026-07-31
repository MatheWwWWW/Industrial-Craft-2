/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.networking.packets.server;

import ic2.core.networking.packets.IC2Packet;
import ic2.core.platform.player.PlayerHandler;
import java.util.Stack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class PollGuiPacket
extends IC2Packet {
    @Override
    public void write(FriendlyByteBuf buffer) {
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
    }

    @Override
    public void handlePacket(Player source) {
        Stack<PlayerHandler.GuiEntry> entries = PlayerHandler.getClientHandler().cachedGUIs;
        if (entries.isEmpty()) {
            return;
        }
        PlayerHandler.GuiEntry entry = entries.pop();
        source.f_36096_ = entry.getContainer();
        if (entry.getScreen() == null) {
            return;
        }
        Minecraft.m_91087_().m_91152_(entry.getScreen());
    }
}

