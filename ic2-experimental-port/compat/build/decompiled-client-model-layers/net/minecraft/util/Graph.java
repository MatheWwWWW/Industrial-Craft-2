/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 */
package net.minecraft.util;

import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public final class Graph {
    private Graph() {
    }

    public static <T> boolean m_184556_(Map<T, Set<T>> p_184557_, Set<T> p_184558_, Set<T> p_184559_, Consumer<T> p_184560_, T p_184561_) {
        if (p_184558_.contains(p_184561_)) {
            return false;
        }
        if (p_184559_.contains(p_184561_)) {
            return true;
        }
        p_184559_.add(p_184561_);
        for (Object $$5 : (Set)p_184557_.getOrDefault(p_184561_, (Set<T>)ImmutableSet.of())) {
            if (!Graph.m_184556_(p_184557_, p_184558_, p_184559_, p_184560_, $$5)) continue;
            return true;
        }
        p_184559_.remove(p_184561_);
        p_184558_.add(p_184561_);
        p_184560_.accept(p_184561_);
        return false;
    }
}

