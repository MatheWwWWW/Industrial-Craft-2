/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectListIterator
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.level;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ChunkTaskPriorityQueueSorter;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.slf4j.Logger;

public class ThreadedLevelLightEngine
extends LevelLightEngine
implements AutoCloseable {
    private static final Logger f_9296_ = LogUtils.getLogger();
    private final ProcessorMailbox<Runnable> f_9297_;
    private final ObjectList<Pair<TaskType, Runnable>> f_9298_ = new ObjectArrayList();
    private final ChunkMap f_9299_;
    private final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> f_9300_;
    private volatile int f_9301_ = 5;
    private final AtomicBoolean f_9302_ = new AtomicBoolean();

    public ThreadedLevelLightEngine(LightChunkGetter p_9305_, ChunkMap p_9306_, boolean p_9307_, ProcessorMailbox<Runnable> p_9308_, ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> p_9309_) {
        super(p_9305_, true, p_9307_);
        this.f_9299_ = p_9306_;
        this.f_9300_ = p_9309_;
        this.f_9297_ = p_9308_;
    }

    @Override
    public void close() {
    }

    @Override
    public int m_5738_(int p_9324_, boolean p_9325_, boolean p_9326_) {
        throw Util.m_137570_(new UnsupportedOperationException("Ran automatically on a different thread!"));
    }

    @Override
    public void m_8116_(BlockPos p_9359_, int p_9360_) {
        throw Util.m_137570_(new UnsupportedOperationException("Ran automatically on a different thread!"));
    }

    @Override
    public void m_7174_(BlockPos p_9357_) {
        BlockPos $$1 = p_9357_.m_7949_();
        this.m_9312_(SectionPos.m_123171_(p_9357_.m_123341_()), SectionPos.m_123171_(p_9357_.m_123343_()), TaskType.POST_UPDATE, Util.m_137474_(() -> super.m_7174_($$1), () -> "checkBlock " + $$1));
    }

    protected void m_9330_(ChunkPos p_9331_) {
        this.m_9317_(p_9331_.f_45578_, p_9331_.f_45579_, () -> 0, TaskType.PRE_UPDATE, Util.m_137474_(() -> {
            super.m_6462_(p_9331_, false);
            super.m_6460_(p_9331_, false);
            for (int $$1 = this.m_164447_(); $$1 < this.m_164448_(); ++$$1) {
                super.m_5687_(LightLayer.BLOCK, SectionPos.m_123196_(p_9331_, $$1), null, true);
                super.m_5687_(LightLayer.SKY, SectionPos.m_123196_(p_9331_, $$1), null, true);
            }
            for (int $$2 = this.f_164445_.m_151560_(); $$2 < this.f_164445_.m_151561_(); ++$$2) {
                super.m_6191_(SectionPos.m_123196_(p_9331_, $$2), true);
            }
        }, () -> "updateChunkStatus " + p_9331_ + " true"));
    }

    @Override
    public void m_6191_(SectionPos p_9364_, boolean p_9365_) {
        this.m_9317_(p_9364_.m_123170_(), p_9364_.m_123222_(), () -> 0, TaskType.PRE_UPDATE, Util.m_137474_(() -> super.m_6191_(p_9364_, p_9365_), () -> "updateSectionStatus " + p_9364_ + " " + p_9365_));
    }

    @Override
    public void m_6460_(ChunkPos p_9336_, boolean p_9337_) {
        this.m_9312_(p_9336_.f_45578_, p_9336_.f_45579_, TaskType.PRE_UPDATE, Util.m_137474_(() -> super.m_6460_(p_9336_, p_9337_), () -> "enableLight " + p_9336_ + " " + p_9337_));
    }

    @Override
    public void m_5687_(LightLayer p_9339_, SectionPos p_9340_, @Nullable DataLayer p_9341_, boolean p_9342_) {
        this.m_9317_(p_9340_.m_123170_(), p_9340_.m_123222_(), () -> 0, TaskType.PRE_UPDATE, Util.m_137474_(() -> super.m_5687_(p_9339_, p_9340_, p_9341_, p_9342_), () -> "queueData " + p_9340_));
    }

    private void m_9312_(int p_9313_, int p_9314_, TaskType p_9315_, Runnable p_9316_) {
        this.m_9317_(p_9313_, p_9314_, this.f_9299_.m_140371_(ChunkPos.m_45589_(p_9313_, p_9314_)), p_9315_, p_9316_);
    }

    private void m_9317_(int p_9318_, int p_9319_, IntSupplier p_9320_, TaskType p_9321_, Runnable p_9322_) {
        this.f_9300_.m_6937_(ChunkTaskPriorityQueueSorter.m_140624_(() -> {
            this.f_9298_.add((Object)Pair.of((Object)((Object)p_9321_), (Object)p_9322_));
            if (this.f_9298_.size() >= this.f_9301_) {
                this.m_9366_();
            }
        }, ChunkPos.m_45589_(p_9318_, p_9319_), p_9320_));
    }

    @Override
    public void m_6462_(ChunkPos p_9370_, boolean p_9371_) {
        this.m_9317_(p_9370_.f_45578_, p_9370_.f_45579_, () -> 0, TaskType.PRE_UPDATE, Util.m_137474_(() -> super.m_6462_(p_9370_, p_9371_), () -> "retainData " + p_9370_));
    }

    public CompletableFuture<ChunkAccess> m_215136_(ChunkAccess p_215137_) {
        ChunkPos $$1 = p_215137_.m_7697_();
        return CompletableFuture.supplyAsync(Util.m_214655_(() -> {
            super.m_6462_($$1, true);
            return p_215137_;
        }, () -> "retainData: " + $$1), p_215152_ -> this.m_9312_(p_215151_.f_45578_, p_215151_.f_45579_, TaskType.PRE_UPDATE, p_215152_));
    }

    public CompletableFuture<ChunkAccess> m_9353_(ChunkAccess p_9354_, boolean p_9355_) {
        ChunkPos $$2 = p_9354_.m_7697_();
        p_9354_.m_8094_(false);
        this.m_9312_($$2.f_45578_, $$2.f_45579_, TaskType.PRE_UPDATE, Util.m_137474_(() -> {
            LevelChunkSection[] $$3 = p_9354_.m_7103_();
            for (int $$4 = 0; $$4 < p_9354_.m_151559_(); ++$$4) {
                LevelChunkSection $$5 = $$3[$$4];
                if ($$5.m_188008_()) continue;
                int $$6 = this.f_164445_.m_151568_($$4);
                super.m_6191_(SectionPos.m_123196_($$2, $$6), false);
            }
            super.m_6460_($$2, true);
            if (!p_9355_) {
                p_9354_.m_6267_().forEach(p_215147_ -> super.m_8116_((BlockPos)p_215147_, p_9354_.m_7146_((BlockPos)p_215147_)));
            }
        }, () -> "lightChunk " + $$2 + " " + p_9355_));
        return CompletableFuture.supplyAsync(() -> {
            p_9354_.m_8094_(true);
            super.m_6462_($$2, false);
            this.f_9299_.m_140375_($$2);
            return p_9354_;
        }, p_215135_ -> this.m_9312_(p_215134_.f_45578_, p_215134_.f_45579_, TaskType.POST_UPDATE, p_215135_));
    }

    public void m_9409_() {
        if ((!this.f_9298_.isEmpty() || super.m_75643_()) && this.f_9302_.compareAndSet(false, true)) {
            this.f_9297_.m_6937_(() -> {
                this.m_9366_();
                this.f_9302_.set(false);
            });
        }
    }

    private void m_9366_() {
        int $$2;
        int $$0 = Math.min(this.f_9298_.size(), this.f_9301_);
        ObjectListIterator $$1 = this.f_9298_.iterator();
        for ($$2 = 0; $$1.hasNext() && $$2 < $$0; ++$$2) {
            Pair $$3 = (Pair)$$1.next();
            if ($$3.getFirst() != TaskType.PRE_UPDATE) continue;
            ((Runnable)$$3.getSecond()).run();
        }
        $$1.back($$2);
        super.m_5738_(Integer.MAX_VALUE, true, true);
        for ($$2 = 0; $$1.hasNext() && $$2 < $$0; ++$$2) {
            Pair $$4 = (Pair)$$1.next();
            if ($$4.getFirst() == TaskType.POST_UPDATE) {
                ((Runnable)$$4.getSecond()).run();
            }
            $$1.remove();
        }
    }

    public void m_9310_(int p_9311_) {
        this.f_9301_ = p_9311_;
    }

    static final class TaskType
    extends Enum<TaskType> {
        public static final /* enum */ TaskType PRE_UPDATE = new TaskType();
        public static final /* enum */ TaskType POST_UPDATE = new TaskType();
        private static final /* synthetic */ TaskType[] $VALUES;

        public static TaskType[] values() {
            return (TaskType[])$VALUES.clone();
        }

        public static TaskType valueOf(String p_9418_) {
            return Enum.valueOf(TaskType.class, p_9418_);
        }

        private static /* synthetic */ TaskType[] m_143478_() {
            return new TaskType[]{PRE_UPDATE, POST_UPDATE};
        }

        static {
            $VALUES = TaskType.m_143478_();
        }
    }
}

