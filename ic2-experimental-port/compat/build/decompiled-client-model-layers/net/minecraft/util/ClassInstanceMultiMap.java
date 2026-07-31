/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Iterators
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 */
package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassInstanceMultiMap<T>
extends AbstractCollection<T> {
    private final Map<Class<?>, List<T>> f_13527_ = Maps.newHashMap();
    private final Class<T> f_13528_;
    private final List<T> f_13529_ = Lists.newArrayList();

    public ClassInstanceMultiMap(Class<T> p_13531_) {
        this.f_13528_ = p_13531_;
        this.f_13527_.put(p_13531_, this.f_13529_);
    }

    @Override
    public boolean add(T p_13536_) {
        boolean $$1 = false;
        for (Map.Entry<Class<?>, List<T>> $$2 : this.f_13527_.entrySet()) {
            if (!$$2.getKey().isInstance(p_13536_)) continue;
            $$1 |= $$2.getValue().add(p_13536_);
        }
        return $$1;
    }

    @Override
    public boolean remove(Object p_13543_) {
        boolean $$1 = false;
        for (Map.Entry<Class<?>, List<T>> $$2 : this.f_13527_.entrySet()) {
            if (!$$2.getKey().isInstance(p_13543_)) continue;
            List<T> $$3 = $$2.getValue();
            $$1 |= $$3.remove(p_13543_);
        }
        return $$1;
    }

    @Override
    public boolean contains(Object p_13540_) {
        return this.m_13533_(p_13540_.getClass()).contains(p_13540_);
    }

    public <S> Collection<S> m_13533_(Class<S> p_13534_) {
        if (!this.f_13528_.isAssignableFrom(p_13534_)) {
            throw new IllegalArgumentException("Don't know how to search for " + p_13534_);
        }
        List $$1 = this.f_13527_.computeIfAbsent(p_13534_, p_13538_ -> this.f_13529_.stream().filter(p_13538_::isInstance).collect(Collectors.toList()));
        return Collections.unmodifiableCollection($$1);
    }

    @Override
    public Iterator<T> iterator() {
        if (this.f_13529_.isEmpty()) {
            return Collections.emptyIterator();
        }
        return Iterators.unmodifiableIterator(this.f_13529_.iterator());
    }

    public List<T> m_13532_() {
        return ImmutableList.copyOf(this.f_13529_);
    }

    @Override
    public int size() {
        return this.f_13529_.size();
    }
}

