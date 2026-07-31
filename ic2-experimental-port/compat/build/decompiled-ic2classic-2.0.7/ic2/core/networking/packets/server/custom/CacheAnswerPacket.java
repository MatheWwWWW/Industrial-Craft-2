/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.networking.packets.server.custom;

import ic2.api.tiles.tubes.IItemCache;
import ic2.core.block.transport.item.cache.ClientItemCache;
import ic2.core.networking.packets.IC2Packet;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class CacheAnswerPacket
extends IC2Packet {
    byte[] answerData;

    public CacheAnswerPacket() {
    }

    public CacheAnswerPacket(byte[] answerData) {
        this.answerData = answerData;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130087_(this.answerData);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.answerData = buffer.m_130052_();
    }

    @Override
    public void handlePacket(Player source) {
        IItemCache cache = IItemCache.getCache();
        if (cache instanceof ClientItemCache) {
            ((ClientItemCache)cache).onDataReceived(new FriendlyByteBuf(Unpooled.wrappedBuffer((byte[])this.answerData)));
        }
    }
}

