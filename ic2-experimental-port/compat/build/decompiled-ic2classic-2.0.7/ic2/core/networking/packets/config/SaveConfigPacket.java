/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.client.server.IntegratedServer
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.server.ServerLifecycleHooks
 */
package ic2.core.networking.packets.config;

import ic2.core.IC2;
import ic2.core.networking.packets.IC2Packet;
import ic2.core.utils.config.config.ConfigHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.ServerLifecycleHooks;

public class SaveConfigPacket
extends IC2Packet {
    String identifier;
    String data;

    public SaveConfigPacket() {
    }

    public SaveConfigPacket(String identifier, String data) {
        this.identifier = identifier;
        this.data = data;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130072_(this.identifier, Short.MAX_VALUE);
        buffer.m_130072_(this.data, 262144);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.identifier = buffer.m_130136_(Short.MAX_VALUE);
        this.data = buffer.m_130136_(262144);
    }

    @Override
    public void handlePacket(Player player) {
        if (!this.canIgnorePermissionCheck() && !player.m_20310_(4)) {
            IC2.LOGGER.warn("Don't have Permission to Change configs");
            return;
        }
        ConfigHandler handler = IC2.FILE_WATCHER.getConfig(this.identifier);
        if (handler == null) {
            return;
        }
        try {
            ConfigHandler.load(handler, handler.getConfig(), (List<String>)ObjectArrayList.wrap((Object[])this.data.split("\n")), false);
            handler.onSynced();
            handler.save();
            IC2.LOGGER.info("Saved [" + this.identifier + "] Config");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean canIgnorePermissionCheck() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        return !server.m_6982_() && server instanceof IntegratedServer && ((IntegratedServer)server).m_6992_();
    }
}

