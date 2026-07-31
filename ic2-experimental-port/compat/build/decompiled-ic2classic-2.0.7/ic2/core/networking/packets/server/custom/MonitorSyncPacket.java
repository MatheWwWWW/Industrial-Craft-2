/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.networking.packets.server.custom;

import ic2.core.block.cables.mointor.MonitorTileEntity;
import ic2.core.networking.packets.IC2Packet;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MonitorSyncPacket
extends IC2Packet {
    BlockPos pos;
    byte[] array;

    public MonitorSyncPacket() {
    }

    public MonitorSyncPacket(BlockPos pos, byte[] array) {
        this.pos = pos;
        this.array = array;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeLong(this.pos.m_121878_());
        buffer.m_130087_(this.array);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.pos = BlockPos.m_122022_((long)buffer.readLong());
        this.array = buffer.m_130052_();
    }

    @Override
    public void handlePacket(Player source) {
        BlockEntity tile = source.f_19853_.m_7702_(this.pos);
        if (tile instanceof MonitorTileEntity) {
            ((MonitorTileEntity)tile).dataManager.onSyncReceived(new FriendlyByteBuf(Unpooled.wrappedBuffer((byte[])this.array)));
        }
    }
}

