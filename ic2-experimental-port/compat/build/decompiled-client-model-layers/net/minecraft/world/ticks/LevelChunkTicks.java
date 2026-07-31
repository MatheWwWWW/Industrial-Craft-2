/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet
 *  javax.annotation.Nullable
 */
package net.minecraft.world.ticks;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.ticks.SavedTick;
import net.minecraft.world.ticks.ScheduledTick;
import net.minecraft.world.ticks.SerializableTickContainer;
import net.minecraft.world.ticks.TickContainerAccess;

public class LevelChunkTicks<T>
implements SerializableTickContainer<T>,
TickContainerAccess<T> {
    private final Queue<ScheduledTick<T>> f_193163_ = new PriorityQueue(ScheduledTick.f_193373_);
    @Nullable
    private List<SavedTick<T>> f_193164_;
    private final Set<ScheduledTick<?>> f_193165_ = new ObjectOpenCustomHashSet(ScheduledTick.f_193375_);
    @Nullable
    private BiConsumer<LevelChunkTicks<T>, ScheduledTick<T>> f_193166_;

    public LevelChunkTicks() {
    }

    public LevelChunkTicks(List<SavedTick<T>> p_193169_) {
        this.f_193164_ = p_193169_;
        for (SavedTick<T> $$1 : p_193169_) {
            this.f_193165_.add(ScheduledTick.m_193397_($$1.f_193311_(), $$1.f_193312_()));
        }
    }

    public void m_193181_(@Nullable BiConsumer<LevelChunkTicks<T>, ScheduledTick<T>> p_193182_) {
        this.f_193166_ = p_193182_;
    }

    @Nullable
    public ScheduledTick<T> m_193189_() {
        return this.f_193163_.peek();
    }

    @Nullable
    public ScheduledTick<T> m_193195_() {
        ScheduledTick<T> $$0 = this.f_193163_.poll();
        if ($$0 != null) {
            this.f_193165_.remove($$0);
        }
        return $$0;
    }

    @Override
    public void m_183393_(ScheduledTick<T> p_193177_) {
        if (this.f_193165_.add(p_193177_)) {
            this.m_193193_(p_193177_);
        }
    }

    private void m_193193_(ScheduledTick<T> p_193194_) {
        this.f_193163_.add(p_193194_);
        if (this.f_193166_ != null) {
            this.f_193166_.accept(this, p_193194_);
        }
    }

    @Override
    public boolean m_183582_(BlockPos p_193179_, T p_193180_) {
        return this.f_193165_.contains(ScheduledTick.m_193397_(p_193180_, p_193179_));
    }

    public void m_193183_(Predicate<ScheduledTick<T>> p_193184_) {
        Iterator $$1 = this.f_193163_.iterator();
        while ($$1.hasNext()) {
            ScheduledTick $$2 = (ScheduledTick)$$1.next();
            if (!p_193184_.test($$2)) continue;
            $$1.remove();
            this.f_193165_.remove($$2);
        }
    }

    public Stream<ScheduledTick<T>> m_193196_() {
        return this.f_193163_.stream();
    }

    @Override
    public int m_183574_() {
        return this.f_193163_.size() + (this.f_193164_ != null ? this.f_193164_.size() : 0);
    }

    @Override
    public ListTag m_183237_(long p_193174_, Function<T, String> p_193175_) {
        ListTag $$2 = new ListTag();
        if (this.f_193164_ != null) {
            for (SavedTick<Object> savedTick : this.f_193164_) {
                $$2.add(savedTick.m_193343_(p_193175_));
            }
        }
        for (ScheduledTick scheduledTick : this.f_193163_) {
            $$2.add(SavedTick.m_193331_(scheduledTick, p_193175_, p_193174_));
        }
        return $$2;
    }

    public void m_193171_(long p_193172_) {
        if (this.f_193164_ != null) {
            int $$1 = -this.f_193164_.size();
            for (SavedTick<T> $$2 : this.f_193164_) {
                this.m_193193_($$2.m_193328_(p_193172_, $$1++));
            }
        }
        this.f_193164_ = null;
    }

    public static <T> LevelChunkTicks<T> m_193185_(ListTag p_193186_, Function<String, Optional<T>> p_193187_, ChunkPos p_193188_) {
        ImmutableList.Builder $$3 = ImmutableList.builder();
        SavedTick.m_193350_(p_193186_, p_193187_, p_193188_, arg_0 -> ((ImmutableList.Builder)$$3).add(arg_0));
        return new LevelChunkTicks<T>($$3.build());
    }

    @Override
    public /* synthetic */ Tag m_183237_(long l, Function function) {
        return this.m_183237_(l, function);
    }
}

