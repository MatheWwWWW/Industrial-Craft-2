/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterators
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.core;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.IdMap;

public class IdMapper<T>
implements IdMap<T> {
    private int f_122653_;
    private final Object2IntMap<T> f_122654_;
    private final List<T> f_122655_;

    public IdMapper() {
        this(512);
    }

    public IdMapper(int p_122658_) {
        this.f_122655_ = Lists.newArrayListWithExpectedSize((int)p_122658_);
        this.f_122654_ = new Object2IntOpenCustomHashMap(p_122658_, Util.m_137583_());
        this.f_122654_.defaultReturnValue(-1);
    }

    public void m_122664_(T p_122665_, int p_122666_) {
        this.f_122654_.put(p_122665_, p_122666_);
        while (this.f_122655_.size() <= p_122666_) {
            this.f_122655_.add(null);
        }
        this.f_122655_.set(p_122666_, p_122665_);
        if (this.f_122653_ <= p_122666_) {
            this.f_122653_ = p_122666_ + 1;
        }
    }

    public void m_122667_(T p_122668_) {
        this.m_122664_(p_122668_, this.f_122653_);
    }

    @Override
    public int m_7447_(T p_122663_) {
        return this.f_122654_.getInt(p_122663_);
    }

    @Override
    @Nullable
    public final T m_7942_(int p_122661_) {
        if (p_122661_ >= 0 && p_122661_ < this.f_122655_.size()) {
            return this.f_122655_.get(p_122661_);
        }
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.filter(this.f_122655_.iterator(), Objects::nonNull);
    }

    public boolean m_175380_(int p_175381_) {
        return this.m_7942_(p_175381_) != null;
    }

    @Override
    public int m_13562_() {
        return this.f_122654_.size();
    }
}

