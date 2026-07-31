/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.entity;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntityTypeTest;
import org.slf4j.Logger;

public class EntityLookup<T extends EntityAccess> {
    private static final Logger f_156806_ = LogUtils.getLogger();
    private final Int2ObjectMap<T> f_156807_ = new Int2ObjectLinkedOpenHashMap();
    private final Map<UUID, T> f_156808_ = Maps.newHashMap();

    public <U extends T> void m_156816_(EntityTypeTest<T, U> p_156817_, Consumer<U> p_156818_) {
        for (EntityAccess $$2 : this.f_156807_.values()) {
            EntityAccess $$3 = (EntityAccess)p_156817_.m_141992_($$2);
            if ($$3 == null) continue;
            p_156818_.accept($$3);
        }
    }

    public Iterable<T> m_156811_() {
        return Iterables.unmodifiableIterable((Iterable)this.f_156807_.values());
    }

    public void m_156814_(T p_156815_) {
        UUID $$1 = p_156815_.m_20148_();
        if (this.f_156808_.containsKey($$1)) {
            f_156806_.warn("Duplicate entity UUID {}: {}", (Object)$$1, p_156815_);
            return;
        }
        this.f_156808_.put($$1, p_156815_);
        this.f_156807_.put(p_156815_.m_19879_(), p_156815_);
    }

    public void m_156822_(T p_156823_) {
        this.f_156808_.remove(p_156823_.m_20148_());
        this.f_156807_.remove(p_156823_.m_19879_());
    }

    @Nullable
    public T m_156812_(int p_156813_) {
        return (T)((EntityAccess)this.f_156807_.get(p_156813_));
    }

    @Nullable
    public T m_156819_(UUID p_156820_) {
        return (T)((EntityAccess)this.f_156808_.get(p_156820_));
    }

    public int m_156821_() {
        return this.f_156808_.size();
    }
}

