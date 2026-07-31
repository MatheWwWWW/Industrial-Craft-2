/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 */
package net.minecraft.world.level.saveddata.maps;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class MapIndex
extends SavedData {
    public static final String f_164761_ = "idcounts";
    private final Object2IntMap<String> f_77878_ = new Object2IntOpenHashMap();

    public MapIndex() {
        this.f_77878_.defaultReturnValue(-1);
    }

    public static MapIndex m_164762_(CompoundTag p_164763_) {
        MapIndex $$1 = new MapIndex();
        for (String $$2 : p_164763_.m_128431_()) {
            if (!p_164763_.m_128425_($$2, 99)) continue;
            $$1.f_77878_.put((Object)$$2, p_164763_.m_128451_($$2));
        }
        return $$1;
    }

    @Override
    public CompoundTag m_7176_(CompoundTag p_77884_) {
        for (Object2IntMap.Entry $$1 : this.f_77878_.object2IntEntrySet()) {
            p_77884_.m_128405_((String)$$1.getKey(), $$1.getIntValue());
        }
        return p_77884_;
    }

    public int m_77880_() {
        int $$0 = this.f_77878_.getInt((Object)"map") + 1;
        this.f_77878_.put((Object)"map", $$0);
        this.m_77762_();
        return $$0;
    }
}

