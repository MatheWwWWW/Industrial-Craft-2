/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  it.unimi.dsi.fastutil.longs.LongList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.networking.packets.server;

import ic2.core.block.rendering.world.impl.BlockHighlighter;
import ic2.core.networking.packets.IC2Packet;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class MultiHighlightPacket
extends IC2Packet {
    LongList list = new LongArrayList();
    int ticks;
    int color;
    boolean doubleSided;

    public MultiHighlightPacket() {
    }

    public MultiHighlightPacket(LongList list, int ticks, int color, boolean doubleSided) {
        this.list = list;
        this.ticks = ticks;
        this.color = color;
        this.doubleSided = doubleSided;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.list.size());
        int m = this.list.size();
        for (int i = 0; i < m; ++i) {
            buffer.writeLong(this.list.getLong(i));
        }
        buffer.m_130130_(this.ticks);
        buffer.writeInt(this.color);
        buffer.writeBoolean(this.doubleSided);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        int size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            this.list.add(buffer.readLong());
        }
        this.ticks = buffer.m_130242_();
        this.color = buffer.readInt();
        this.doubleSided = buffer.readBoolean();
    }

    @Override
    public void handlePacket(Player source) {
        int m = this.list.size();
        for (int i = 0; i < m; ++i) {
            BlockHighlighter.INSTANCE.addHighlight(BlockPos.m_122022_((long)this.list.getLong(i)), this.ticks, this.color, this.doubleSided);
        }
    }
}

