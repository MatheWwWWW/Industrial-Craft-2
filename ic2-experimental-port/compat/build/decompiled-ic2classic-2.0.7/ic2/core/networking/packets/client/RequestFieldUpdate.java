/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.networking.packets.client;

import ic2.api.network.tile.INetworkFieldProvider;
import ic2.core.IC2;
import ic2.core.networking.packets.IC2Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class RequestFieldUpdate
extends IC2Packet {
    boolean gui;
    BlockPos pos;

    public RequestFieldUpdate() {
    }

    public RequestFieldUpdate(BlockEntity tile, boolean gui) {
        this.pos = tile.m_58899_();
        this.gui = gui;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.gui);
        buffer.writeLong(this.pos.m_121878_());
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.gui = buffer.readBoolean();
        this.pos = BlockPos.m_122022_((long)buffer.readLong());
    }

    @Override
    public void handlePacket(Player source) {
        BlockEntity tile = source.f_19853_.m_7702_(this.pos);
        if (!(tile instanceof INetworkFieldProvider)) {
            return;
        }
        if (this.gui) {
            IC2.NETWORKING.get().sendInitialGuiData((INetworkFieldProvider)tile, source);
            return;
        }
        IC2.NETWORKING.get().requestInitialData((INetworkFieldProvider)tile);
    }
}

