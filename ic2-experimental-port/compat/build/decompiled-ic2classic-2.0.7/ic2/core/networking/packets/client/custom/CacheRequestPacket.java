/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.networking.packets.client.custom;

import ic2.api.tiles.tubes.IItemCache;
import ic2.core.block.transport.item.cache.ServerItemCache;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.IC2Packet;
import ic2.core.networking.packets.server.custom.CacheAnswerPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class CacheRequestPacket
extends IC2Packet {
    int[] missingIds;

    public CacheRequestPacket() {
    }

    public CacheRequestPacket(int[] missingIds) {
        this.missingIds = missingIds;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130089_(this.missingIds);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.missingIds = buffer.m_130100_();
    }

    @Override
    public void handlePacket(Player source) {
        IItemCache cache = IItemCache.getCache();
        if (cache instanceof ServerItemCache) {
            PacketManager.INSTANCE.sendToPlayer(new CacheAnswerPacket(((ServerItemCache)cache).createData(this.missingIds)), source);
        }
    }
}

