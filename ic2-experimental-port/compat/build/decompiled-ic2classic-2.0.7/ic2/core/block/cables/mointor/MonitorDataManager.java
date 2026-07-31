/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.block.cables.mointor;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.tiles.display.IDisplayInfo;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.server.custom.MonitorSyncPacket;
import ic2.core.utils.collection.CollectionUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import java.util.List;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MonitorDataManager
implements INetworkDataBuffer {
    List<IDisplayInfo> infos = CollectionUtils.createList();
    List<Tag> data = CollectionUtils.createList();

    public void addData(IDisplayInfo info) {
        this.infos.add(info);
        this.data.add(info.getServerData());
    }

    public boolean isEmpty() {
        return this.infos.isEmpty();
    }

    public int size() {
        return this.infos.size();
    }

    public IDisplayInfo get(int index) {
        return this.infos.get(index);
    }

    public void clear() {
        this.infos.clear();
        this.data.clear();
    }

    public void onSyncReceived(FriendlyByteBuf buffer) {
        int size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            int slot = buffer.m_130242_();
            if (slot >= this.infos.size()) {
                return;
            }
            this.infos.set(slot, IDisplayInfo.REGISTRY.deserialize(buffer));
        }
    }

    public boolean tick(BlockEntity tile) {
        IntArrayList list = new IntArrayList();
        int m = this.infos.size();
        for (int i = 0; i < m; ++i) {
            IDisplayInfo info = this.infos.get(i);
            if (!info.isValid()) {
                this.infos.clear();
                this.data.clear();
                return true;
            }
            Tag newData = info.getServerData();
            if (newData.equals(this.data.get(i))) continue;
            this.data.set(i, newData);
            list.add(i);
        }
        if (!list.isEmpty()) {
            ByteBuf buf = Unpooled.buffer();
            FriendlyByteBuf buffer = new FriendlyByteBuf(buf);
            buffer.m_130130_(list.size());
            int m2 = list.size();
            for (int i = 0; i < m2; ++i) {
                int slot = list.getInt(i);
                buffer.m_130130_(slot);
                IDisplayInfo.REGISTRY.serialize(this.infos.get(slot), buffer);
            }
            byte[] data = new byte[buf.writerIndex()];
            buf.readBytes(data);
            PacketManager.INSTANCE.sendToAllChunkWatchers(tile.m_58904_().m_46745_(tile.m_58899_()), new MonitorSyncPacket(tile.m_58899_(), data));
        }
        return false;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeVarInt(this.infos.size());
        int m = this.infos.size();
        for (int i = 0; i < m; ++i) {
            this.serialize(this.infos.get(i), buffer);
        }
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.infos.clear();
        int m = buffer.readVarInt();
        for (int i = 0; i < m; ++i) {
            this.infos.add(this.deserialize(buffer));
        }
    }

    private void serialize(IDisplayInfo info, IOutputBuffer buffer) {
        ByteBuf buf = Unpooled.buffer();
        IDisplayInfo.REGISTRY.serialize(info, new FriendlyByteBuf(buf));
        byte[] data = new byte[buf.writerIndex()];
        buf.readBytes(data);
        buffer.writeBytes(data);
    }

    private IDisplayInfo deserialize(IInputBuffer buffer) {
        return IDisplayInfo.REGISTRY.deserialize(new FriendlyByteBuf(Unpooled.wrappedBuffer((byte[])buffer.readBytes())));
    }
}

