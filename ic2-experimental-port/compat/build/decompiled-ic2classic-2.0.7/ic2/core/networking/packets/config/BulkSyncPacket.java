/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.networking.packets.config;

import ic2.core.networking.packets.IC2Packet;
import ic2.core.networking.packets.config.SyncPacket;
import ic2.core.utils.config.config.ConfigHandler;
import ic2.core.utils.config.impl.ReloadMode;
import ic2.core.utils.config.utils.SyncType;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class BulkSyncPacket
extends IC2Packet {
    List<SyncPacket> packets = new ObjectArrayList();

    public BulkSyncPacket() {
    }

    public BulkSyncPacket(List<SyncPacket> packets) {
        this.packets = packets;
    }

    public static BulkSyncPacket create(Collection<ConfigHandler> toSync, SyncType type, boolean forceSync) {
        ObjectArrayList result = new ObjectArrayList();
        for (ConfigHandler handler : toSync) {
            SyncPacket packet = SyncPacket.create(handler, type, forceSync);
            if (packet == null) continue;
            result.add(packet);
        }
        return result.isEmpty() ? null : new BulkSyncPacket((List<SyncPacket>)result);
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.packets.size());
        for (SyncPacket packet : this.packets) {
            packet.write(buffer);
        }
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        int size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            SyncPacket packet = new SyncPacket();
            packet.read(buffer);
            this.packets.add(packet);
        }
    }

    @Override
    public void handlePacket(Player player) {
        ReloadMode result = null;
        for (SyncPacket packet : this.packets) {
            result = ReloadMode.or(result, packet.processEntry(player));
        }
        if (result != null) {
            player.m_213846_((Component)Component.m_237113_((String)result.getMessage()));
        }
    }
}

