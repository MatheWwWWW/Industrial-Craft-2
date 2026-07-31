/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.base.misc.comparator;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.block.base.misc.comparator.BaseComparator;
import ic2.core.block.base.misc.comparator.ComparatorListener;
import ic2.core.block.base.misc.comparator.ComparingList;
import ic2.core.inventory.base.INBTSavable;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.NBTUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ComparatorManager
implements INetworkDataBuffer,
INBTSavable {
    Map<String, BaseComparator> mappedComparator = CollectionUtils.createMap();
    List<BaseComparator> comparators = CollectionUtils.createList();
    List<BaseComparator> updating = CollectionUtils.createList();
    ComparingList[] values = new ComparingList[]{new ComparingList(this, Direction.DOWN), new ComparingList(this, Direction.UP), new ComparingList(this, Direction.NORTH), new ComparingList(this, Direction.SOUTH), new ComparingList(this, Direction.WEST), new ComparingList(this, Direction.EAST), new ComparingList(this, null)};
    boolean forceAll = false;
    boolean ignore_update = true;
    BlockEntity tile;

    public ComparatorManager(BlockEntity tile) {
        this.tile = tile;
    }

    public void addComparator(BaseComparator comparator) {
        if (this.mappedComparator.putIfAbsent(comparator.getId(), comparator) != null) {
            return;
        }
        this.comparators.add(comparator);
        comparator.setManager(this);
    }

    public boolean update(boolean force) {
        int i;
        List<BaseComparator> list;
        if (!force && this.invClock(10)) {
            return false;
        }
        List<BaseComparator> list2 = list = this.forceAll ? this.comparators : this.updating;
        if (list.isEmpty()) {
            return false;
        }
        long time = this.tile.m_58904_().m_46467_();
        boolean updated = false;
        int m = list.size();
        for (i = 0; i < m; ++i) {
            if (!list.get(i).updateValue(time, this.forceAll)) continue;
            updated = true;
        }
        if (updated || this.ignore_update) {
            updated = false;
            for (i = 0; i < 7; ++i) {
                if (!this.values[i].updateValue()) continue;
                updated = true;
            }
            if (updated && !this.ignore_update) {
                this.tile.m_58904_().m_46717_(this.tile.m_58899_(), this.tile.m_58900_().m_60734_());
            }
            return true;
        }
        return false;
    }

    public void add(int index, int side) {
        if (index < 0 || index >= this.comparators.size()) {
            return;
        }
        this.values[side].addListener(new ComparatorListener(this.comparators.get(index)));
        this.update(true);
        this.tile.m_58904_().m_46717_(this.tile.m_58899_(), this.tile.m_58900_().m_60734_());
    }

    public void remove(int index, int side) {
        this.values[side].removeListeners(index);
        this.update(true);
        this.tile.m_58904_().m_46717_(this.tile.m_58899_(), this.tile.m_58900_().m_60734_());
    }

    public void setFlag(int index, int side, int flag) {
        ComparingList list = this.values[side];
        if (list.size() <= 0) {
            return;
        }
        ComparatorListener listener = list.get(index);
        listener.flags ^= 1 << flag;
        listener.onComparatorChanged();
        list.updateValue();
        this.update(true);
        this.tile.m_58904_().m_46717_(this.tile.m_58899_(), this.tile.m_58900_().m_60734_());
    }

    public void flipMax(int value) {
        this.values[value].flipMax();
        this.update(true);
        this.tile.m_58904_().m_46717_(this.tile.m_58899_(), this.tile.m_58900_().m_60734_());
    }

    @OnlyIn(value=Dist.CLIENT)
    public ComparingList getList(int index) {
        return this.values[index];
    }

    public int getValue(Direction dir) {
        return this.values[dir == null ? 6 : dir.m_122411_()].getValue();
    }

    final boolean invClock(int value) {
        return (this.tile.m_58904_().m_46467_() ^ this.tile.m_58899_().m_121878_()) % (long)value != 0L;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        int i;
        int m = this.comparators.size();
        for (i = 0; i < m; ++i) {
            this.comparators.get(i).write(buffer);
        }
        for (i = 0; i < 7; ++i) {
            this.values[i].write(buffer);
        }
    }

    @Override
    public void read(IInputBuffer buffer) {
        int i;
        int m = this.comparators.size();
        for (i = 0; i < m; ++i) {
            this.comparators.get(i).read(buffer);
        }
        for (i = 0; i < 7; ++i) {
            this.values[i].read(buffer);
        }
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        ListTag list = new ListTag();
        for (int i = 0; i < 7; ++i) {
            CompoundTag data = this.values[i].save(new CompoundTag());
            if (data.m_128456_()) continue;
            data.m_128344_("slot", (byte)i);
            list.add((Object)data);
        }
        NBTUtils.put(nbt, "comparator_data", list);
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        this.ignore_update = true;
        for (CompoundTag data : NBTListWrapper.wrap(nbt.m_128437_("comparator_data", 10), CompoundTag.class)) {
            this.values[data.m_128451_("slot")].load(data);
        }
    }

    public boolean hasComparators() {
        return this.comparators.size() > 0;
    }

    public int size() {
        return this.comparators.size();
    }

    public List<BaseComparator> getComparators() {
        ObjectList comps = CollectionUtils.createList();
        for (int i = 0; i < this.comparators.size(); ++i) {
            BaseComparator comp = this.comparators.get(i);
            if (!comp.visible()) continue;
            comps.add((BaseComparator)comp);
        }
        return comps;
    }

    public int getIndex(BaseComparator base) {
        return this.comparators.indexOf(base);
    }

    public BaseComparator getComparator(int index) {
        return index < 0 || index >= this.comparators.size() ? null : this.comparators.get(index);
    }

    public boolean shouldSync() {
        return this.forceAll;
    }

    public void setSync(boolean value) {
        this.forceAll = value;
    }

    public void onLoaded() {
        this.forceAll = true;
        this.update(true);
        this.forceAll = false;
        this.ignore_update = false;
    }
}

