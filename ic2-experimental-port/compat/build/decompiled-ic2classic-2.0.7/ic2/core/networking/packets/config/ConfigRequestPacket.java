/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 *  net.minecraft.client.server.IntegratedServer
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.server.ServerLifecycleHooks
 */
package ic2.core.networking.packets.config;

import ic2.core.IC2;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.IC2Packet;
import ic2.core.networking.packets.config.ConfigAnswerPacket;
import ic2.core.utils.config.config.ConfigHandler;
import ic2.core.utils.config.utils.MultilinePolicy;
import io.netty.buffer.Unpooled;
import java.util.UUID;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.ServerLifecycleHooks;

public class ConfigRequestPacket
extends IC2Packet {
    UUID id;
    String identifier;

    public ConfigRequestPacket() {
    }

    public ConfigRequestPacket(UUID id, String identifier) {
        this.id = id;
        this.identifier = identifier;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130077_(this.id);
        buffer.m_130072_(this.identifier, Short.MAX_VALUE);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.id = buffer.m_130259_();
        this.identifier = buffer.m_130136_(Short.MAX_VALUE);
    }

    @Override
    public void handlePacket(Player player) {
        if (!this.canIgnorePermissionCheck() && !player.m_20310_(4)) {
            return;
        }
        ConfigHandler handler = IC2.FILE_WATCHER.getConfig(this.identifier);
        if (handler == null) {
            return;
        }
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.m_130072_(handler.getConfig().serialize(MultilinePolicy.DISABLED), 262144);
        byte[] data = new byte[buf.writerIndex()];
        buf.readBytes(data);
        PacketManager.INSTANCE.sendToPlayer(new ConfigAnswerPacket(this.id, data), player);
    }

    private boolean canIgnorePermissionCheck() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        return !server.m_6982_() && server instanceof IntegratedServer && ((IntegratedServer)server).m_6992_();
    }
}

