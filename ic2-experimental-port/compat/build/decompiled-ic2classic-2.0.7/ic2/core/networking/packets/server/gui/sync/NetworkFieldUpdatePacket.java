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

import ic2.core.networking.NetworkManager;
import ic2.core.networking.buffers.InputBuffer;
import ic2.core.networking.buffers.OutputBuffer;
import ic2.core.networking.misc.NetworkUtils;
import ic2.core.networking.packets.IC2Packet;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class NetworkFieldUpdatePacket
extends IC2Packet {
    Map<BlockPos, List<NetworkManager.FieldData>> data;

    public NetworkFieldUpdatePacket() {
    }

    public NetworkFieldUpdatePacket(Map<BlockPos, List<NetworkManager.FieldData>> data) {
        this.data = data;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        NetworkUtils.writeData(new OutputBuffer(buffer), this.data);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.data = NetworkUtils.readData(new InputBuffer(buffer));
    }

    @Override
    public void handlePacket(Player source) {
        for (Map.Entry<BlockPos, List<NetworkManager.FieldData>> entry : this.data.entrySet()) {
            BlockEntity tile = source.f_19853_.m_7702_(entry.getKey());
            if (tile == null) continue;
            NetworkUtils.applyFields(tile, entry.getValue(), false, source);
        }
    }
}

