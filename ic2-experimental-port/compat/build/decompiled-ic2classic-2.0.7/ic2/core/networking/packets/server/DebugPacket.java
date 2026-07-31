/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtIo
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.networking.packets.server;

import ic2.core.networking.packets.IC2Packet;
import ic2.core.platform.events.DebugHighlighter;
import ic2.core.utils.helpers.NBTUtils;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class DebugPacket
extends IC2Packet {
    BlockState state;
    CompoundTag tileData;
    long[] data;

    public DebugPacket() {
    }

    public DebugPacket(BlockState state, CompoundTag tileData, long[] data) {
        this.state = state;
        this.tileData = tileData;
        this.data = data;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130079_(NbtUtils.m_129202_((BlockState)this.state));
        buffer.writeBoolean(this.tileData != null);
        if (this.tileData != null) {
            buffer.m_130079_(this.tileData);
        }
        buffer.m_130091_(this.data);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.state = NbtUtils.m_129241_((CompoundTag)buffer.m_130260_());
        if (buffer.readBoolean()) {
            this.tileData = buffer.m_130260_();
        }
        this.data = buffer.m_130105_(this.data);
    }

    @Override
    public void handlePacket(Player source) {
        ArrayList<Component> debugTest = new ArrayList<Component>();
        debugTest.add((Component)Component.m_237113_((String)"Debug Data:"));
        debugTest.add((Component)Component.m_237113_((String)""));
        debugTest.add((Component)Component.m_237113_((String)"Block: ").m_7220_((Component)this.state.m_60734_().m_49954_()));
        debugTest.addAll(NBTUtils.convertState(this.state));
        if (this.tileData != null) {
            debugTest.add((Component)Component.m_237113_((String)""));
            debugTest.add((Component)Component.m_237113_((String)"TileData: "));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                NbtIo.m_128941_((CompoundTag)this.tileData, (DataOutput)new DataOutputStream(out));
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.addBytes("NBTSize: ", out.size(), debugTest);
            this.addBytes("All Network Bytes: ", this.data[0], debugTest);
            this.addBytes("Initial Network Bytes: ", this.data[1], debugTest);
            this.addBytes("Initial Gui Bytes: ", this.data[2], debugTest);
            debugTest.addAll(NBTUtils.convertNBT(this.tileData));
        }
        DebugHighlighter.INSTANCE.storeDebug(debugTest, this.tileData);
    }

    private void addBytes(String prefix, long bytes, List<Component> list) {
        if (bytes > 10000L) {
            list.add((Component)Component.m_237113_((String)(prefix + bytes / 1000L + " KB")).m_130940_(ChatFormatting.GOLD));
        } else {
            list.add((Component)Component.m_237113_((String)(prefix + bytes + " Bytes")).m_130940_(ChatFormatting.GOLD));
        }
    }
}

