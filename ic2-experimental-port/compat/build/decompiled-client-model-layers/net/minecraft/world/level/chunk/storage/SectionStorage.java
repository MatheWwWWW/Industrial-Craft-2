/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.OptionalDynamic
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.chunk.storage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.OptionalDynamic;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.storage.IOWorker;
import org.slf4j.Logger;

public class SectionStorage<R>
implements AutoCloseable {
    private static final Logger f_63772_ = LogUtils.getLogger();
    private static final String f_156617_ = "Sections";
    private final IOWorker f_63773_;
    private final Long2ObjectMap<Optional<R>> f_63774_ = new Long2ObjectOpenHashMap();
    private final LongLinkedOpenHashSet f_63775_ = new LongLinkedOpenHashSet();
    private final Function<Runnable, Codec<R>> f_63776_;
    private final Function<Runnable, R> f_63777_;
    private final DataFixer f_63778_;
    private final DataFixTypes f_63779_;
    private final RegistryAccess f_223507_;
    protected final LevelHeightAccessor f_156618_;

    public SectionStorage(Path p_223509_, Function<Runnable, Codec<R>> p_223510_, Function<Runnable, R> p_223511_, DataFixer p_223512_, DataFixTypes p_223513_, boolean p_223514_, RegistryAccess p_223515_, LevelHeightAccessor p_223516_) {
        this.f_63776_ = p_223510_;
        this.f_63777_ = p_223511_;
        this.f_63778_ = p_223512_;
        this.f_63779_ = p_223513_;
        this.f_223507_ = p_223515_;
        this.f_156618_ = p_223516_;
        this.f_63773_ = new IOWorker(p_223509_, p_223514_, p_223509_.getFileName().toString());
    }

    protected void m_6202_(BooleanSupplier p_63812_) {
        while (this.m_202164_() && p_63812_.getAsBoolean()) {
            ChunkPos $$1 = SectionPos.m_123184_(this.f_63775_.firstLong()).m_123251_();
            this.m_63825_($$1);
        }
    }

    public boolean m_202164_() {
        return !this.f_63775_.isEmpty();
    }

    @Nullable
    protected Optional<R> m_63818_(long p_63819_) {
        return (Optional)this.f_63774_.get(p_63819_);
    }

    protected Optional<R> m_63823_(long p_63824_) {
        if (this.m_156630_(p_63824_)) {
            return Optional.empty();
        }
        Optional<R> $$1 = this.m_63818_(p_63824_);
        if ($$1 != null) {
            return $$1;
        }
        this.m_63814_(SectionPos.m_123184_(p_63824_).m_123251_());
        $$1 = this.m_63818_(p_63824_);
        if ($$1 == null) {
            throw Util.m_137570_(new IllegalStateException());
        }
        return $$1;
    }

    protected boolean m_156630_(long p_156631_) {
        int $$1 = SectionPos.m_123223_(SectionPos.m_123225_(p_156631_));
        return this.f_156618_.m_151562_($$1);
    }

    protected R m_63827_(long p_63828_) {
        if (this.m_156630_(p_63828_)) {
            throw Util.m_137570_(new IllegalArgumentException("sectionPos out of bounds"));
        }
        Optional<R> $$1 = this.m_63823_(p_63828_);
        if ($$1.isPresent()) {
            return $$1.get();
        }
        R $$2 = this.f_63777_.apply(() -> this.m_5838_(p_63828_));
        this.f_63774_.put(p_63828_, Optional.of($$2));
        return $$2;
    }

    private void m_63814_(ChunkPos p_63815_) {
        Optional<CompoundTag> $$1 = this.m_223532_(p_63815_).join();
        RegistryOps<Tag> $$2 = RegistryOps.m_206821_(NbtOps.f_128958_, this.f_223507_);
        this.m_63801_(p_63815_, $$2, $$1.orElse(null));
    }

    private CompletableFuture<Optional<CompoundTag>> m_223532_(ChunkPos p_223533_) {
        return this.f_63773_.m_156587_(p_223533_).exceptionally(p_223526_ -> {
            if (p_223526_ instanceof IOException) {
                IOException $$2 = (IOException)p_223526_;
                f_63772_.error("Error reading chunk {} data from disk", (Object)p_223533_, (Object)$$2);
                return Optional.empty();
            }
            throw new CompletionException((Throwable)p_223526_);
        });
    }

    private <T> void m_63801_(ChunkPos p_63802_, DynamicOps<T> p_63803_, @Nullable T p_63804_) {
        if (p_63804_ == null) {
            for (int $$3 = this.f_156618_.m_151560_(); $$3 < this.f_156618_.m_151561_(); ++$$3) {
                this.f_63774_.put(SectionStorage.m_156627_(p_63802_, $$3), Optional.empty());
            }
        } else {
            int $$6;
            Dynamic $$4 = new Dynamic(p_63803_, p_63804_);
            int $$5 = SectionStorage.m_63805_($$4);
            boolean $$7 = $$5 != ($$6 = SharedConstants.m_183709_().getWorldVersion());
            Dynamic $$8 = this.f_63778_.update(this.f_63779_.m_14504_(), $$4, $$5, $$6);
            OptionalDynamic $$9 = $$8.get(f_156617_);
            for (int $$10 = this.f_156618_.m_151560_(); $$10 < this.f_156618_.m_151561_(); ++$$10) {
                long $$11 = SectionStorage.m_156627_(p_63802_, $$10);
                Optional $$12 = $$9.get(Integer.toString($$10)).result().flatMap(p_223519_ -> this.f_63776_.apply(() -> this.m_5838_($$11)).parse(p_223519_).resultOrPartial(arg_0 -> ((Logger)f_63772_).error(arg_0)));
                this.f_63774_.put($$11, $$12);
                $$12.ifPresent(p_223523_ -> {
                    this.m_5839_($$11);
                    if ($$7) {
                        this.m_5838_($$11);
                    }
                });
            }
        }
    }

    private void m_63825_(ChunkPos p_63826_) {
        RegistryOps<Tag> $$1 = RegistryOps.m_206821_(NbtOps.f_128958_, this.f_223507_);
        Dynamic<Tag> $$2 = this.m_63798_(p_63826_, $$1);
        Tag $$3 = (Tag)$$2.getValue();
        if ($$3 instanceof CompoundTag) {
            this.f_63773_.m_63538_(p_63826_, (CompoundTag)$$3);
        } else {
            f_63772_.error("Expected compound tag, got {}", (Object)$$3);
        }
    }

    private <T> Dynamic<T> m_63798_(ChunkPos p_63799_, DynamicOps<T> p_63800_) {
        HashMap $$2 = Maps.newHashMap();
        for (int $$3 = this.f_156618_.m_151560_(); $$3 < this.f_156618_.m_151561_(); ++$$3) {
            long $$4 = SectionStorage.m_156627_(p_63799_, $$3);
            this.f_63775_.remove($$4);
            Optional $$5 = (Optional)this.f_63774_.get($$4);
            if ($$5 == null || !$$5.isPresent()) continue;
            DataResult $$6 = this.f_63776_.apply(() -> this.m_5838_($$4)).encodeStart(p_63800_, $$5.get());
            String $$7 = Integer.toString($$3);
            $$6.resultOrPartial(arg_0 -> ((Logger)f_63772_).error(arg_0)).ifPresent(p_223531_ -> $$2.put(p_63800_.createString($$7), p_223531_));
        }
        return new Dynamic(p_63800_, p_63800_.createMap((Map)ImmutableMap.of((Object)p_63800_.createString(f_156617_), (Object)p_63800_.createMap((Map)$$2), (Object)p_63800_.createString("DataVersion"), (Object)p_63800_.createInt(SharedConstants.m_183709_().getWorldVersion()))));
    }

    private static long m_156627_(ChunkPos p_156628_, int p_156629_) {
        return SectionPos.m_123209_(p_156628_.f_45578_, p_156629_, p_156628_.f_45579_);
    }

    protected void m_5839_(long p_63813_) {
    }

    protected void m_5838_(long p_63788_) {
        Optional $$1 = (Optional)this.f_63774_.get(p_63788_);
        if ($$1 == null || !$$1.isPresent()) {
            f_63772_.warn("No data for position: {}", (Object)SectionPos.m_123184_(p_63788_));
            return;
        }
        this.f_63775_.add(p_63788_);
    }

    private static int m_63805_(Dynamic<?> p_63806_) {
        return p_63806_.get("DataVersion").asInt(1945);
    }

    public void m_63796_(ChunkPos p_63797_) {
        if (this.m_202164_()) {
            for (int $$1 = this.f_156618_.m_151560_(); $$1 < this.f_156618_.m_151561_(); ++$$1) {
                long $$2 = SectionStorage.m_156627_(p_63797_, $$1);
                if (!this.f_63775_.contains($$2)) continue;
                this.m_63825_(p_63797_);
                return;
            }
        }
    }

    @Override
    public void close() throws IOException {
        this.f_63773_.close();
    }
}

