/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 */
package net.minecraft.stats;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.world.entity.player.Player;

public class StatsCounter {
    protected final Object2IntMap<Stat<?>> f_13013_ = Object2IntMaps.synchronize((Object2IntMap)new Object2IntOpenHashMap());

    public StatsCounter() {
        this.f_13013_.defaultReturnValue(0);
    }

    public void m_13023_(Player p_13024_, Stat<?> p_13025_, int p_13026_) {
        int $$3 = (int)Math.min((long)this.m_13015_(p_13025_) + (long)p_13026_, Integer.MAX_VALUE);
        this.m_6085_(p_13024_, p_13025_, $$3);
    }

    public void m_6085_(Player p_13020_, Stat<?> p_13021_, int p_13022_) {
        this.f_13013_.put(p_13021_, p_13022_);
    }

    public <T> int m_13017_(StatType<T> p_13018_, T p_13019_) {
        return p_13018_.m_12897_(p_13019_) ? this.m_13015_(p_13018_.m_12902_(p_13019_)) : 0;
    }

    public int m_13015_(Stat<?> p_13016_) {
        return this.f_13013_.getInt(p_13016_);
    }
}

