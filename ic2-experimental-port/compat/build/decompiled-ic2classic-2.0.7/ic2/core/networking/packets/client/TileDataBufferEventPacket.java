/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.api.distmarker.Dist
 */
package ic2.core.networking.packets.client;

import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.tile.INetworkDataEventListener;
import ic2.core.networking.NetworkManager;
import ic2.core.networking.buffers.InputBuffer;
import ic2.core.networking.buffers.OutputBuffer;
import ic2.core.networking.packets.IC2Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;

public class TileDataBufferEventPacket
extends IC2Packet {
    BlockPos pos;
    boolean client;
    String id;
    INetworkDataBuffer dataBuffer;

    public TileDataBufferEventPacket() {
    }

    public TileDataBufferEventPacket(BlockEntity tile, String id, INetworkDataBuffer dataBuffer, boolean client) {
        this.pos = tile.m_58899_();
        this.id = id;
        this.dataBuffer = dataBuffer;
        this.client = client;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeLong(this.pos.m_121878_());
        buffer.writeBoolean(this.client);
        buffer.m_130070_(this.id);
        buffer.m_130085_(NetworkManager.getID(this.dataBuffer));
        this.dataBuffer.write(new OutputBuffer(buffer));
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.pos = BlockPos.m_122022_((long)buffer.readLong());
        this.client = buffer.readBoolean();
        this.id = buffer.m_130136_(Short.MAX_VALUE);
        this.dataBuffer = NetworkManager.createBuffer(buffer.m_130281_());
        this.dataBuffer.read(new InputBuffer(buffer));
    }

    @Override
    public void handlePacket(Player source) {
        if (this.dataBuffer == null) {
            return;
        }
        BlockEntity tile = source.f_19853_.m_7702_(this.pos);
        if (!(tile instanceof INetworkDataEventListener)) {
            return;
        }
        ((INetworkDataEventListener)tile).onDataBufferReceived(source, this.id, this.dataBuffer, this.client ? Dist.CLIENT : Dist.DEDICATED_SERVER);
    }
}

