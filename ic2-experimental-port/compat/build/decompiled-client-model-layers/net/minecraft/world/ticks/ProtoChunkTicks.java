/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet
 */
package net.minecraft.world.ticks;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.ticks.SavedTick;
import net.minecraft.world.ticks.ScheduledTick;
import net.minecraft.world.ticks.SerializableTickContainer;
import net.minecraft.world.ticks.TickContainerAccess;

public class ProtoChunkTicks<T>
implements SerializableTickContainer<T>,
TickContainerAccess<T> {
    private final List<SavedTick<T>> f_193291_ = Lists.newArrayList();
    private final Set<SavedTick<?>> f_193292_ = new ObjectOpenCustomHashSet(SavedTick.f_193310_);

    @Override
    public void m_183393_(ScheduledTick<T> p_193298_) {
        SavedTick<T> $$1 = new SavedTick<T>(p_193298_.f_193376_(), p_193298_.f_193377_(), 0, p_193298_.f_193379_());
        this.m_193295_($$1);
    }

    private void m_193295_(SavedTick<T> p_193296_) {
        if (this.f_193292_.add(p_193296_)) {
            this.f_193291_.add(p_193296_);
        }
    }

    @Override
    public boolean m_183582_(BlockPos p_193300_, T p_193301_) {
        return this.f_193292_.contains(SavedTick.m_193335_(p_193301_, p_193300_));
    }

    @Override
    public int m_183574_() {
        return this.f_193291_.size();
    }

    @Override
    public Tag m_183237_(long p_193308_, Function<T, String> p_193309_) {
        ListTag $$2 = new ListTag();
        for (SavedTick<T> $$3 : this.f_193291_) {
            $$2.add($$3.m_193343_(p_193309_));
        }
        return $$2;
    }

    public List<SavedTick<T>> m_193306_() {
        return List.copyOf(this.f_193291_);
    }

    public static <T> ProtoChunkTicks<T> m_193302_(ListTag p_193303_, Function<String, Optional<T>> p_193304_, ChunkPos p_193305_) {
        ProtoChunkTicks<T> $$3 = new ProtoChunkTicks<T>();
        SavedTick.m_193350_(p_193303_, p_193304_, p_193305_, $$3::m_193295_);
        return $$3;
    }
}

