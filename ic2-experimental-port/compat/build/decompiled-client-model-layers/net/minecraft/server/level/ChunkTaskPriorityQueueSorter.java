/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.util.Either
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkTaskPriorityQueue;
import net.minecraft.util.Unit;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.util.thread.StrictQueue;
import net.minecraft.world.level.ChunkPos;
import org.slf4j.Logger;

public class ChunkTaskPriorityQueueSorter
implements ChunkHolder.LevelChangeListener,
AutoCloseable {
    private static final Logger f_140549_ = LogUtils.getLogger();
    private final Map<ProcessorHandle<?>, ChunkTaskPriorityQueue<? extends Function<ProcessorHandle<Unit>, ?>>> f_140550_;
    private final Set<ProcessorHandle<?>> f_140551_;
    private final ProcessorMailbox<StrictQueue.IntRunnable> f_140552_;

    public ChunkTaskPriorityQueueSorter(List<ProcessorHandle<?>> p_140555_, Executor p_140556_, int p_140557_) {
        this.f_140550_ = p_140555_.stream().collect(Collectors.toMap(Function.identity(), p_140561_ -> new ChunkTaskPriorityQueue(p_140561_.m_7326_() + "_queue", p_140557_)));
        this.f_140551_ = Sets.newHashSet(p_140555_);
        this.f_140552_ = new ProcessorMailbox<StrictQueue.IntRunnable>(new StrictQueue.FixedPriorityQueue(4), p_140556_, "sorter");
    }

    public boolean m_201909_() {
        return this.f_140552_.m_201938_() || this.f_140550_.values().stream().anyMatch(ChunkTaskPriorityQueue::m_201908_);
    }

    public static <T> Message<T> m_143181_(Function<ProcessorHandle<Unit>, T> p_143182_, long p_143183_, IntSupplier p_143184_) {
        return new Message<T>(p_143182_, p_143183_, p_143184_);
    }

    public static Message<Runnable> m_140624_(Runnable p_140625_, long p_140626_, IntSupplier p_140627_) {
        return new Message<Runnable>(p_140634_ -> () -> {
            p_140625_.run();
            p_140634_.m_6937_(Unit.INSTANCE);
        }, p_140626_, p_140627_);
    }

    public static Message<Runnable> m_140642_(ChunkHolder p_140643_, Runnable p_140644_) {
        return ChunkTaskPriorityQueueSorter.m_140624_(p_140644_, p_140643_.m_140092_().m_45588_(), p_140643_::m_140094_);
    }

    public static <T> Message<T> m_143156_(ChunkHolder p_143157_, Function<ProcessorHandle<Unit>, T> p_143158_) {
        return ChunkTaskPriorityQueueSorter.m_143181_(p_143158_, p_143157_.m_140092_().m_45588_(), p_143157_::m_140094_);
    }

    public static Release m_140628_(Runnable p_140629_, long p_140630_, boolean p_140631_) {
        return new Release(p_140629_, p_140630_, p_140631_);
    }

    public <T> ProcessorHandle<Message<T>> m_140604_(ProcessorHandle<T> p_140605_, boolean p_140606_) {
        return (ProcessorHandle)this.f_140552_.m_18720_(p_140610_ -> new StrictQueue.IntRunnable(0, () -> {
            this.m_140652_(p_140605_);
            p_140610_.m_6937_(ProcessorHandle.m_18714_("chunk priority sorter around " + p_140605_.m_7326_(), p_143176_ -> this.m_140589_(p_140605_, p_143176_.f_140664_, p_143176_.f_140665_, p_143176_.f_140666_, p_140606_)));
        })).join();
    }

    public ProcessorHandle<Release> m_140567_(ProcessorHandle<Runnable> p_140568_) {
        return (ProcessorHandle)this.f_140552_.m_18720_(p_140581_ -> new StrictQueue.IntRunnable(0, () -> p_140581_.m_6937_(ProcessorHandle.m_18714_("chunk priority sorter around " + p_140568_.m_7326_(), p_143165_ -> this.m_140569_(p_140568_, p_143165_.f_140683_, p_143165_.f_140682_, p_143165_.f_140684_))))).join();
    }

    @Override
    public void m_6250_(ChunkPos p_140616_, IntSupplier p_140617_, int p_140618_, IntConsumer p_140619_) {
        this.f_140552_.m_6937_(new StrictQueue.IntRunnable(0, () -> {
            int $$4 = p_140617_.getAsInt();
            this.f_140550_.values().forEach(p_143155_ -> p_143155_.m_140521_($$4, p_140616_, p_140618_));
            p_140619_.accept(p_140618_);
        }));
    }

    private <T> void m_140569_(ProcessorHandle<T> p_140570_, long p_140571_, Runnable p_140572_, boolean p_140573_) {
        this.f_140552_.m_6937_(new StrictQueue.IntRunnable(1, () -> {
            ChunkTaskPriorityQueue $$4 = this.m_140652_(p_140570_);
            $$4.m_140530_(p_140571_, p_140573_);
            if (this.f_140551_.remove(p_140570_)) {
                this.m_140645_($$4, p_140570_);
            }
            p_140572_.run();
        }));
    }

    private <T> void m_140589_(ProcessorHandle<T> p_140590_, Function<ProcessorHandle<Unit>, T> p_140591_, long p_140592_, IntSupplier p_140593_, boolean p_140594_) {
        this.f_140552_.m_6937_(new StrictQueue.IntRunnable(2, () -> {
            ChunkTaskPriorityQueue $$5 = this.m_140652_(p_140590_);
            int $$6 = p_140593_.getAsInt();
            $$5.m_140535_(Optional.of(p_140591_), p_140592_, $$6);
            if (p_140594_) {
                $$5.m_140535_(Optional.empty(), p_140592_, $$6);
            }
            if (this.f_140551_.remove(p_140590_)) {
                this.m_140645_($$5, p_140590_);
            }
        }));
    }

    private <T> void m_140645_(ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> p_140646_, ProcessorHandle<T> p_140647_) {
        this.f_140552_.m_6937_(new StrictQueue.IntRunnable(3, () -> {
            Stream<Either<Either, Runnable>> $$2 = p_140646_.m_140518_();
            if ($$2 == null) {
                this.f_140551_.add(p_140647_);
            } else {
                CompletableFuture.allOf((CompletableFuture[])$$2.map(p_143172_ -> (CompletableFuture)p_143172_.map(p_140647_::m_18720_, p_143180_ -> {
                    p_143180_.run();
                    return CompletableFuture.completedFuture(Unit.INSTANCE);
                })).toArray(CompletableFuture[]::new)).thenAccept(p_212894_ -> this.m_140645_(p_140646_, p_140647_));
            }
        }));
    }

    private <T> ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> m_140652_(ProcessorHandle<T> p_140653_) {
        ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> $$1 = this.f_140550_.get(p_140653_);
        if ($$1 == null) {
            throw Util.m_137570_(new IllegalArgumentException("No queue for: " + p_140653_));
        }
        return $$1;
    }

    @VisibleForTesting
    public String m_140558_() {
        return this.f_140550_.entrySet().stream().map(p_212898_ -> ((ProcessorHandle)p_212898_.getKey()).m_7326_() + "=[" + ((ChunkTaskPriorityQueue)p_212898_.getValue()).m_140539_().stream().map(p_212896_ -> p_212896_ + ":" + new ChunkPos((long)p_212896_)).collect(Collectors.joining(",")) + "]").collect(Collectors.joining(",")) + ", s=" + this.f_140551_.size();
    }

    @Override
    public void close() {
        this.f_140550_.keySet().forEach(ProcessorHandle::close);
    }

    public static final class Message<T> {
        final Function<ProcessorHandle<Unit>, T> f_140664_;
        final long f_140665_;
        final IntSupplier f_140666_;

        Message(Function<ProcessorHandle<Unit>, T> p_140668_, long p_140669_, IntSupplier p_140670_) {
            this.f_140664_ = p_140668_;
            this.f_140665_ = p_140669_;
            this.f_140666_ = p_140670_;
        }
    }

    public static final class Release {
        final Runnable f_140682_;
        final long f_140683_;
        final boolean f_140684_;

        Release(Runnable p_140686_, long p_140687_, boolean p_140688_) {
            this.f_140682_ = p_140686_;
            this.f_140683_ = p_140687_;
            this.f_140684_ = p_140688_;
        }
    }
}

