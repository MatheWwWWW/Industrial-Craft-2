/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.networking.packets.server.gui.sync;

import ic2.api.network.tile.INetworkEventListener;
import ic2.core.networking.packets.IC2Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ServerTileEventPacket
extends IC2Packet {
    BlockPos pos;
    int key;
    int value;

    public ServerTileEventPacket() {
    }

    public ServerTileEventPacket(BlockEntity tile, int key, int value) {
        this.pos = tile.m_58899_();
        this.key = key;
        this.value = value;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeLong(this.pos.m_121878_());
        buffer.writeInt(this.key);
        buffer.writeInt(this.value);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.pos = BlockPos.m_122022_((long)buffer.readLong());
        this.key = buffer.readInt();
        this.value = buffer.readInt();
    }

    @Override
    public void handlePacket(Player source) {
        BlockEntity tile = source.f_19853_.m_7702_(this.pos);
        if (!(tile instanceof INetworkEventListener)) {
            return;
        }
        ((INetworkEventListener)tile).onServerDataReceived(this.key, this.value);
    }
}

