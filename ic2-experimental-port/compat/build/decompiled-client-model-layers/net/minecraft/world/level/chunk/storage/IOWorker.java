/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Either
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.chunk.storage;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.io.IOException;
import java.nio.file.Path;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.visitors.CollectFields;
import net.minecraft.nbt.visitors.FieldSelector;
import net.minecraft.util.Unit;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.util.thread.StrictQueue;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.storage.ChunkScanAccess;
import net.minecraft.world.level.chunk.storage.RegionFileStorage;
import org.slf4j.Logger;

public class IOWorker
implements ChunkScanAccess,
AutoCloseable {
    private static final Logger f_63515_ = LogUtils.getLogger();
    private final AtomicBoolean f_63516_ = new AtomicBoolean();
    private final ProcessorMailbox<StrictQueue.IntRunnable> f_63517_;
    private final RegionFileStorage f_63518_;
    private final Map<ChunkPos, PendingStore> f_63519_ = Maps.newLinkedHashMap();
    private final Long2ObjectLinkedOpenHashMap<CompletableFuture<BitSet>> f_223459_ = new Long2ObjectLinkedOpenHashMap();
    private static final int f_223460_ = 1024;

    protected IOWorker(Path p_196930_, boolean p_196931_, String p_196932_) {
        this.f_63518_ = new RegionFileStorage(p_196930_, p_196931_);
        this.f_63517_ = new ProcessorMailbox<StrictQueue.IntRunnable>(new StrictQueue.FixedPriorityQueue(Priority.values().length), Util.m_183992_(), "IOWorker-" + p_196932_);
    }

    public boolean m_223471_(ChunkPos p_223472_, int p_223473_) {
        ChunkPos $$2 = new ChunkPos(p_223472_.f_45578_ - p_223473_, p_223472_.f_45579_ - p_223473_);
        ChunkPos $$3 = new ChunkPos(p_223472_.f_45578_ + p_223473_, p_223472_.f_45579_ + p_223473_);
        for (int $$4 = $$2.m_45610_(); $$4 <= $$3.m_45610_(); ++$$4) {
            for (int $$5 = $$2.m_45612_(); $$5 <= $$3.m_45612_(); ++$$5) {
                BitSet $$6 = this.m_223463_($$4, $$5).join();
                if ($$6.isEmpty()) continue;
                ChunkPos $$7 = ChunkPos.m_220337_($$4, $$5);
                int $$8 = Math.max($$2.f_45578_ - $$7.f_45578_, 0);
                int $$9 = Math.max($$2.f_45579_ - $$7.f_45579_, 0);
                int $$10 = Math.min($$3.f_45578_ - $$7.f_45578_, 31);
                int $$11 = Math.min($$3.f_45579_ - $$7.f_45579_, 31);
                for (int $$12 = $$8; $$12 <= $$10; ++$$12) {
                    for (int $$13 = $$9; $$13 <= $$11; ++$$13) {
                        int $$14 = $$13 * 32 + $$12;
                        if (!$$6.get($$14)) continue;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private CompletableFuture<BitSet> m_223463_(int p_223464_, int p_223465_) {
        long $$2 = ChunkPos.m_45589_(p_223464_, p_223465_);
        Long2ObjectLinkedOpenHashMap<CompletableFuture<BitSet>> long2ObjectLinkedOpenHashMap = this.f_223459_;
        synchronized (long2ObjectLinkedOpenHashMap) {
            CompletableFuture<BitSet> $$3 = (CompletableFuture<BitSet>)this.f_223459_.getAndMoveToFirst($$2);
            if ($$3 == null) {
                $$3 = this.m_223489_(p_223464_, p_223465_);
                this.f_223459_.putAndMoveToFirst($$2, $$3);
                if (this.f_223459_.size() > 1024) {
                    this.f_223459_.removeLast();
                }
            }
            return $$3;
        }
    }

    private CompletableFuture<BitSet> m_223489_(int p_223490_, int p_223491_) {
        return CompletableFuture.supplyAsync(() -> {
            ChunkPos $$2 = ChunkPos.m_220337_(p_223490_, p_223491_);
            ChunkPos $$3 = ChunkPos.m_220340_(p_223490_, p_223491_);
            BitSet $$4 = new BitSet();
            ChunkPos.m_45599_($$2, $$3).forEach(p_223480_ -> {
                CompoundTag $$5;
                CollectFields $$2 = new CollectFields(new FieldSelector(IntTag.f_128670_, "DataVersion"), new FieldSelector(CompoundTag.f_128326_, "blending_data"));
                try {
                    this.m_196358_((ChunkPos)p_223480_, $$2).join();
                }
                catch (Exception $$3) {
                    f_63515_.warn("Failed to scan chunk {}", p_223480_, (Object)$$3);
                    return;
                }
                Tag $$4 = $$2.m_197713_();
                if ($$4 instanceof CompoundTag && this.m_223484_($$5 = (CompoundTag)$$4)) {
                    int $$6 = p_223480_.m_45614_() * 32 + p_223480_.m_45613_();
                    $$4.set($$6);
                }
            });
            return $$4;
        }, Util.m_183991_());
    }

    private boolean m_223484_(CompoundTag p_223485_) {
        if (!p_223485_.m_128425_("DataVersion", 99) || p_223485_.m_128451_("DataVersion") < 3088) {
            return true;
        }
        return p_223485_.m_128425_("blending_data", 10);
    }

    public CompletableFuture<Void> m_63538_(ChunkPos p_63539_, @Nullable CompoundTag p_63540_) {
        return this.m_63545_(() -> {
            PendingStore $$2 = this.f_63519_.computeIfAbsent(p_63539_, p_223488_ -> new PendingStore(p_63540_));
            $$2.f_63565_ = p_63540_;
            return Either.left($$2.f_63566_);
        }).thenCompose(Function.identity());
    }

    public CompletableFuture<Optional<CompoundTag>> m_156587_(ChunkPos p_156588_) {
        return this.m_63545_(() -> {
            PendingStore $$1 = this.f_63519_.get(p_156588_);
            if ($$1 != null) {
                return Either.left(Optional.ofNullable($$1.f_63565_));
            }
            try {
                CompoundTag $$2 = this.f_63518_.m_63706_(p_156588_);
                return Either.left(Optional.ofNullable($$2));
            }
            catch (Exception $$3) {
                f_63515_.warn("Failed to read chunk {}", (Object)p_156588_, (Object)$$3);
                return Either.right((Object)$$3);
            }
        });
    }

    public CompletableFuture<Void> m_182498_(boolean p_182499_) {
        CompletionStage $$1 = this.m_63545_(() -> Either.left(CompletableFuture.allOf((CompletableFuture[])this.f_63519_.values().stream().map(p_223475_ -> p_223475_.f_63566_).toArray(CompletableFuture[]::new)))).thenCompose(Function.identity());
        if (p_182499_) {
            return ((CompletableFuture)$$1).thenCompose(p_182494_ -> this.m_63545_(() -> {
                try {
                    this.f_63518_.m_63705_();
                    return Either.left(null);
                }
                catch (Exception $$0) {
                    f_63515_.warn("Failed to synchronize chunks", (Throwable)$$0);
                    return Either.right((Object)$$0);
                }
            }));
        }
        return ((CompletableFuture)$$1).thenCompose(p_223477_ -> this.m_63545_(() -> Either.left(null)));
    }

    @Override
    public CompletableFuture<Void> m_196358_(ChunkPos p_196939_, StreamTagVisitor p_196940_) {
        return this.m_63545_(() -> {
            try {
                PendingStore $$2 = this.f_63519_.get(p_196939_);
                if ($$2 != null) {
                    if ($$2.f_63565_ != null) {
                        $$2.f_63565_.m_197573_(p_196940_);
                    }
                } else {
                    this.f_63518_.m_196956_(p_196939_, p_196940_);
                }
                return Either.left(null);
            }
            catch (Exception $$3) {
                f_63515_.warn("Failed to bulk scan chunk {}", (Object)p_196939_, (Object)$$3);
                return Either.right((Object)$$3);
            }
        });
    }

    private <T> CompletableFuture<T> m_63545_(Supplier<Either<T, Exception>> p_63546_) {
        return this.f_63517_.m_18722_(p_223483_ -> new StrictQueue.IntRunnable(Priority.FOREGROUND.ordinal(), () -> this.m_223468_(p_223483_, (Supplier)p_63546_)));
    }

    private void m_63553_() {
        if (this.f_63519_.isEmpty()) {
            return;
        }
        Iterator<Map.Entry<ChunkPos, PendingStore>> $$0 = this.f_63519_.entrySet().iterator();
        Map.Entry<ChunkPos, PendingStore> $$1 = $$0.next();
        $$0.remove();
        this.m_63535_($$1.getKey(), $$1.getValue());
        this.m_63561_();
    }

    private void m_63561_() {
        this.f_63517_.m_6937_(new StrictQueue.IntRunnable(Priority.BACKGROUND.ordinal(), this::m_63553_));
    }

    private void m_63535_(ChunkPos p_63536_, PendingStore p_63537_) {
        try {
            this.f_63518_.m_63708_(p_63536_, p_63537_.f_63565_);
            p_63537_.f_63566_.complete(null);
        }
        catch (Exception $$2) {
            f_63515_.error("Failed to store chunk {}", (Object)p_63536_, (Object)$$2);
            p_63537_.f_63566_.completeExceptionally($$2);
        }
    }

    @Override
    public void close() throws IOException {
        if (!this.f_63516_.compareAndSet(false, true)) {
            return;
        }
        this.f_63517_.m_18720_(p_223467_ -> new StrictQueue.IntRunnable(Priority.SHUTDOWN.ordinal(), () -> p_223467_.m_6937_(Unit.INSTANCE))).join();
        this.f_63517_.close();
        try {
            this.f_63518_.close();
        }
        catch (Exception $$0) {
            f_63515_.error("Failed to close storage", (Throwable)$$0);
        }
    }

    private /* synthetic */ void m_223468_(ProcessorHandle p_223469_, Supplier p_223470_) {
        if (!this.f_63516_.get()) {
            p_223469_.m_6937_((Either)p_223470_.get());
        }
        this.m_63561_();
    }

    static final class Priority
    extends Enum<Priority> {
        public static final /* enum */ Priority FOREGROUND = new Priority();
        public static final /* enum */ Priority BACKGROUND = new Priority();
        public static final /* enum */ Priority SHUTDOWN = new Priority();
        private static final /* synthetic */ Priority[] $VALUES;

        public static Priority[] values() {
            return (Priority[])$VALUES.clone();
        }

        public static Priority valueOf(String p_63584_) {
            return Enum.valueOf(Priority.class, p_63584_);
        }

        private static /* synthetic */ Priority[] m_156595_() {
            return new Priority[]{FOREGROUND, BACKGROUND, SHUTDOWN};
        }

        static {
            $VALUES = Priority.m_156595_();
        }
    }

    static class PendingStore {
        @Nullable
        CompoundTag f_63565_;
        final CompletableFuture<Void> f_63566_ = new CompletableFuture();

        public PendingStore(@Nullable CompoundTag p_63568_) {
            this.f_63565_ = p_63568_;
        }
    }
}

