/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 */
package ic2.core.block.base.misc.comparator;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.block.base.misc.comparator.BaseComparator;
import ic2.core.block.base.misc.comparator.ComparatorListener;
import ic2.core.block.base.misc.comparator.ComparatorManager;
import ic2.core.block.base.misc.comparator.DefaultComparator;
import ic2.core.inventory.base.INBTSavable;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.NBTUtils;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class ComparingList
implements INBTSavable,
INetworkDataBuffer {
    static final ComparatorListener DEFAULT = new ComparatorListener(DefaultComparator.INSTANCE);
    ComparatorManager manager;
    Direction dir;
    List<ComparatorListener> values = CollectionUtils.createList();
    int value;
    byte evaluator;

    public ComparingList(ComparatorManager manager, Direction dir) {
        this.manager = manager;
        this.dir = dir;
    }

    public void addListener(ComparatorListener listener) {
        this.values.add(listener);
        listener.dir = this.dir;
        listener.addListener();
        this.updateValue();
    }

    public void removeListeners(int index) {
        if (index < 0 || index >= this.values.size()) {
            return;
        }
        ComparatorListener listener = this.values.remove(index);
        if (listener != null) {
            listener.onRemoved();
            this.updateValue();
        }
    }

    public boolean updateValue() {
        if (this.values.isEmpty()) {
            boolean result = this.value > 0;
            this.value = 0;
            return result;
        }
        long lastValue = this.value;
        if (this.evaluator == 0) {
            this.value = 15;
            int m = this.values.size();
            for (int i = 0; i < m; ++i) {
                this.value = Math.min(this.value, this.values.get(i).getValue());
            }
        } else if (this.evaluator == 1) {
            this.value = 0;
            int m = this.values.size();
            for (int i = 0; i < m; ++i) {
                this.value = Math.max(this.value, this.values.get(i).getValue());
            }
        } else if (this.evaluator == 2) {
            this.value = 0;
            int m = this.values.size();
            for (int i = 0; i < m; ++i) {
                this.value += this.values.get(i).getValue();
            }
            this.value /= this.values.size();
        }
        return lastValue != (long)this.value;
    }

    public int getValue() {
        return this.value;
    }

    public int size() {
        return this.values.size();
    }

    public ComparatorListener get(int index) {
        return this.values.isEmpty() ? DEFAULT : this.values.get(index);
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeByte(this.evaluator);
        if (this.values.isEmpty()) {
            buffer.writeByte((byte)0);
            return;
        }
        buffer.writeByte((byte)this.values.size());
        int m = this.values.size();
        for (int i = 0; i < m; ++i) {
            ComparatorListener listener = this.values.get(i);
            buffer.writeString(listener.source.getId());
            buffer.writeByte((byte)((listener.getBaseValue() & 0xF) << 4 | listener.getValue() & 0xF));
            buffer.writeByte((byte)listener.flags);
        }
        buffer.writeByte((byte)this.value);
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.evaluator = buffer.readByte();
        this.values.clear();
        this.value = 0;
        int size = buffer.readByte();
        if (size != 0) {
            for (int i = 0; i < size; ++i) {
                String id = buffer.readString();
                byte data = buffer.readByte();
                byte flags = buffer.readByte();
                BaseComparator comp = this.manager.mappedComparator.get(id);
                if (comp == null) continue;
                ComparatorListener listener = new ComparatorListener(comp);
                this.values.add(listener);
                listener.dir = this.dir;
                listener.baseValue = data >> 4 & 0xF;
                listener.value = data & 0xF;
                listener.flags = flags;
            }
            this.value = buffer.readByte();
        }
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        NBTUtils.putByte(nbt, "max", (int)this.evaluator, 0);
        ListTag list = new ListTag();
        int m = this.values.size();
        for (int i = 0; i < m; ++i) {
            ComparatorListener listener = this.values.get(i);
            CompoundTag data = new CompoundTag();
            data.m_128359_("id", listener.source.getId());
            data.m_128344_("flags", (byte)listener.flags);
            list.add((Object)data);
        }
        NBTUtils.put(nbt, "data", list);
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        this.evaluator = nbt.m_128445_("max");
        for (CompoundTag data : NBTListWrapper.wrap(nbt.m_128437_("data", 10), CompoundTag.class)) {
            String id = data.m_128461_("id");
            int flags = data.m_128451_("flags");
            BaseComparator comp = this.manager.mappedComparator.get(id);
            if (comp == null) continue;
            ComparatorListener listener = new ComparatorListener(comp);
            listener.dir = this.dir;
            listener.flags = flags;
            this.values.add(listener);
            listener.addListener();
        }
    }

    public int getEvaluator() {
        return this.evaluator;
    }

    public void flipMax() {
        this.evaluator = (byte)(this.evaluator + 1);
        this.evaluator = (byte)(this.evaluator % 3);
        this.updateValue();
    }
}

