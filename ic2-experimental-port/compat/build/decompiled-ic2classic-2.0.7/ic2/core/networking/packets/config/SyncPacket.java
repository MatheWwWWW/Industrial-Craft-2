/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.networking.packets.config;

import ic2.core.IC2;
import ic2.core.networking.packets.IC2Packet;
import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.config.ConfigHandler;
import ic2.core.utils.config.impl.ReloadMode;
import ic2.core.utils.config.impl.internal.ReadBuffer;
import ic2.core.utils.config.impl.internal.WriteBuffer;
import ic2.core.utils.config.utils.SyncType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class SyncPacket
extends IC2Packet {
    String identifier;
    SyncType type;
    Map<String, byte[]> entries = new Object2ObjectLinkedOpenHashMap();

    public SyncPacket() {
    }

    public SyncPacket(String identifier, SyncType type, Map<String, byte[]> entries) {
        this.identifier = identifier;
        this.type = type;
        this.entries = entries;
    }

    public static SyncPacket create(ConfigHandler handler, SyncType type, boolean forceSync) {
        if (!handler.isLoaded()) {
            return null;
        }
        Object2ObjectLinkedOpenHashMap data = new Object2ObjectLinkedOpenHashMap();
        ByteBuf buf = Unpooled.buffer();
        WriteBuffer buffer = new WriteBuffer(new FriendlyByteBuf(buf));
        for (Map.Entry<String, ConfigEntry<?>> entry : handler.getConfig().getSyncedEntries(type).entrySet()) {
            ConfigEntry<?> value = entry.getValue();
            if (!forceSync && !value.hasChanged()) continue;
            buf.clear();
            value.serialize(buffer);
            byte[] configData = new byte[buf.writerIndex()];
            buf.readBytes(configData);
            data.put(entry.getKey(), configData);
            value.onSynced();
        }
        return data.isEmpty() ? null : new SyncPacket(handler.getConfigIdentifer(), type, (Map<String, byte[]>)data);
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130070_(this.identifier);
        buffer.m_130068_((Enum)this.type);
        buffer.m_130130_(this.entries.size());
        for (Map.Entry<String, byte[]> entry : this.entries.entrySet()) {
            buffer.m_130070_(entry.getKey());
            buffer.m_130087_(entry.getValue());
        }
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.identifier = buffer.m_130136_(Short.MAX_VALUE);
        this.type = (SyncType)buffer.m_130066_(SyncType.class);
        int size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            this.entries.put(buffer.m_130136_(Short.MAX_VALUE), buffer.m_130052_());
        }
    }

    @Override
    public void handlePacket(Player player) {
        ReloadMode mode = this.processEntry(player);
        if (mode != null) {
            player.m_213846_((Component)Component.m_237113_((String)mode.getMessage()));
        }
    }

    public ReloadMode processEntry(Player player) {
        if (this.entries.isEmpty()) {
            return null;
        }
        ConfigHandler cfg = IC2.FILE_WATCHER.getConfig(this.identifier);
        if (cfg == null) {
            IC2.LOGGER.warn("Received packet for [" + this.identifier + "] which didn't exist!");
            return null;
        }
        Map<String, ConfigEntry<?>> mapped = cfg.getConfig().getSyncedEntries(this.type);
        boolean hasChanged = false;
        UUID owner = player.m_20148_();
        ReloadMode mode = null;
        for (Map.Entry<String, byte[]> dataEntry : this.entries.entrySet()) {
            ConfigEntry<?> entry = mapped.get(dataEntry.getKey());
            if (entry == null) continue;
            entry.deserialize(new ReadBuffer(new FriendlyByteBuf(Unpooled.wrappedBuffer((byte[])dataEntry.getValue()))), owner);
            if (entry.hasChanged()) {
                hasChanged = true;
                mode = ReloadMode.or(mode, entry.getReloadState());
            }
            entry.onSynced();
        }
        if (hasChanged) {
            cfg.onSynced();
            return mode;
        }
        return null;
    }
}

