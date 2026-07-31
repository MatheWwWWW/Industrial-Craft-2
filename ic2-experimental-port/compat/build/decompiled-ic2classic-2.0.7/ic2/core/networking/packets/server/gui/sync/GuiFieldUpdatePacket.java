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
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class GuiFieldUpdatePacket
extends IC2Packet {
    List<NetworkManager.FieldData> fields;
    BlockPos position;

    public GuiFieldUpdatePacket() {
    }

    public GuiFieldUpdatePacket(BlockEntity tile, List<NetworkManager.FieldData> fields) {
        this.position = tile.m_58899_();
        this.fields = fields;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeLong(this.position.m_121878_());
        NetworkUtils.writeEntries(new OutputBuffer(buffer), this.fields);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.position = BlockPos.m_122022_((long)buffer.readLong());
        this.fields = NetworkUtils.readEntries(new InputBuffer(buffer));
    }

    @Override
    public void handlePacket(Player source) {
        BlockEntity tile = source.f_19853_.m_7702_(this.position);
        if (tile == null) {
            return;
        }
        NetworkUtils.applyFields(tile, this.fields, true, source);
    }
}

