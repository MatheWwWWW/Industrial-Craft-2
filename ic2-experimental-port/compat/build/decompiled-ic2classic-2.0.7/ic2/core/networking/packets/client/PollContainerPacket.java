/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.networking.packets.client;

import ic2.api.network.tile.INetworkFieldProvider;
import ic2.core.IC2;
import ic2.core.inventory.container.ContainerHasGui;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.IC2Packet;
import ic2.core.networking.packets.server.PollGuiPacket;
import ic2.core.platform.player.PlayerHandler;
import java.util.Stack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class PollContainerPacket
extends IC2Packet {
    @Override
    public void write(FriendlyByteBuf buffer) {
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
    }

    @Override
    public void handlePacket(Player source) {
        BlockEntity tile;
        Stack<PlayerHandler.GuiEntry> entries = PlayerHandler.getHandler((Player)source).cachedGUIs;
        if (entries.isEmpty() || !(source instanceof ServerPlayer)) {
            return;
        }
        ServerPlayer player = (ServerPlayer)source;
        PlayerHandler.GuiEntry entry = entries.pop();
        player.f_8940_ = entry.getContainer().f_38840_;
        player.f_36096_ = entry.getContainer();
        player.m_143399_(player.f_36096_);
        if (player.f_36096_ instanceof ContainerHasGui && ((ContainerHasGui)player.f_36096_).getHolder() instanceof BlockEntity && (tile = (PlayerHandler.getHandler((Player)source).trackedTile = (BlockEntity)((ContainerHasGui)player.f_36096_).getHolder())) instanceof INetworkFieldProvider) {
            IC2.NETWORKING.get(true).sendInitialGuiData((INetworkFieldProvider)tile, (Player)player);
        }
        PacketManager.INSTANCE.sendToPlayer(new PollGuiPacket(), (Player)player);
    }
}

