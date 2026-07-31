/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 */
package ic2.core.networking.buffers.data;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class NBTBuffer
implements INetworkDataBuffer {
    CompoundTag nbt;

    public NBTBuffer() {
    }

    public NBTBuffer(String subId, Tag tag) {
        this.nbt = new CompoundTag();
        this.nbt.m_128365_(subId, tag);
    }

    public NBTBuffer(CompoundTag nbt) {
        this.nbt = nbt;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeNBTData(this.nbt);
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.nbt = buffer.readNBTData();
    }

    public CompoundTag getNBT() {
        return this.nbt;
    }
}

