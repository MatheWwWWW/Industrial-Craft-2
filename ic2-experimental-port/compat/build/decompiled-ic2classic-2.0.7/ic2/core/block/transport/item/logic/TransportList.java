/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 */
package ic2.core.block.transport.item.logic;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.tiles.tubes.TransportedItem;
import ic2.core.utils.collection.NBTListWrapper;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class TransportList
extends ObjectArrayList<TransportedItem>
implements INetworkDataBuffer {
    private static final long serialVersionUID = -6688957890025524600L;
    IntSet set = new IntOpenHashSet();
    int syncLimit;

    public TransportList(int syncLimit) {
        this.syncLimit = syncLimit;
    }

    public boolean add(TransportedItem k) {
        if (!this.set.add(k.getId())) {
            return false;
        }
        return super.add((Object)k);
    }

    public TransportedItem remove(int index) {
        TransportedItem item = (TransportedItem)super.remove(index);
        this.removeItem(item);
        return item;
    }

    private void removeItem(TransportedItem item) {
        if (item != null) {
            this.set.remove(item.getId());
        }
    }

    public boolean removeAll(Collection<?> c) {
        Object[] a = this.a;
        int j = 0;
        for (int i = 0; i < this.size; ++i) {
            if (!c.contains(a[i])) {
                a[j++] = a[i];
                continue;
            }
            this.removeItem((TransportedItem)a[i]);
        }
        Arrays.fill(a, j, this.size, null);
        boolean modified = this.size != j;
        this.size = j;
        return modified;
    }

    public void clear() {
        super.clear();
        this.set.clear();
    }

    @Override
    public void write(IOutputBuffer buffer) {
        int actualSize = Math.min(this.syncLimit, this.size);
        buffer.writeVarInt(actualSize);
        for (int i = 0; i < actualSize; ++i) {
            ((TransportedItem)this.get(i)).write(buffer);
        }
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.clear();
        int size = buffer.readVarInt();
        for (int i = 0; i < size; ++i) {
            this.add(TransportedItem.readFromNetwork(buffer));
        }
    }

    public ListTag write() {
        ListTag list = new ListTag();
        for (int i = 0; i < this.size; ++i) {
            list.add((Object)((TransportedItem)this.get(i)).writeToNBT(new CompoundTag()));
        }
        return list;
    }

    public void read(ListTag list) {
        for (CompoundTag nbt : NBTListWrapper.wrap(list, CompoundTag.class)) {
            TransportedItem item = TransportedItem.read(nbt);
            if (item == null) continue;
            this.add(item);
        }
    }
}

