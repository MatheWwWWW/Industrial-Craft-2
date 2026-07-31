/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.networking.packets.server;

import ic2.core.block.rendering.world.impl.BlockHighlighter;
import ic2.core.networking.packets.IC2Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class HighlightPacket
extends IC2Packet {
    BlockPos pos;
    int ticks;
    int color;
    boolean doubleSided;

    public HighlightPacket() {
    }

    public HighlightPacket(BlockPos pos, int ticks, int color, boolean doubleSided) {
        this.pos = pos;
        this.ticks = ticks;
        this.color = color;
        this.doubleSided = doubleSided;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeLong(this.pos.m_121878_());
        buffer.m_130130_(this.ticks);
        buffer.writeInt(this.color);
        buffer.writeBoolean(this.doubleSided);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.pos = BlockPos.m_122022_((long)buffer.readLong());
        this.ticks = buffer.m_130242_();
        this.color = buffer.readInt();
        this.doubleSided = buffer.readBoolean();
    }

    @Override
    public void handlePacket(Player source) {
        BlockHighlighter.INSTANCE.addHighlight(this.pos, this.ticks, this.color, this.doubleSided);
    }
}

