/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 */
package ic2.core.block.machines.logic.crafter;

import ic2.core.inventory.base.INBTSavable;
import ic2.core.utils.collection.NBTListWrapper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public class Snapshot
implements INBTSavable {
    INBTSavable[] array;

    public Snapshot(int size) {
        this.array = new INBTSavable[size];
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        ListTag list = new ListTag();
        int m = this.array.length;
        for (int i = 0; i < m; ++i) {
            if (this.array[i] == null) continue;
            CompoundTag data = new CompoundTag();
            data.m_128344_("index", (byte)i);
            data.m_128365_("data", (Tag)this.array[i].save(new CompoundTag()));
            list.add((Object)data);
        }
        nbt.m_128365_("data", (Tag)list);
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        for (CompoundTag data : NBTListWrapper.wrap(nbt.m_128437_("data", 10), CompoundTag.class)) {
            int index = data.m_128451_("index");
            if (this.array[index] == null) continue;
            this.array[index].load(data.m_128469_("data"));
        }
    }

    public void set(int index, INBTSavable savable) {
        this.array[index] = savable;
    }

    public <T> T get(int index) {
        return (T)this.array[index];
    }

    public <T> T get(int index, Class<T> clz) {
        return (T)this.array[index];
    }
}

