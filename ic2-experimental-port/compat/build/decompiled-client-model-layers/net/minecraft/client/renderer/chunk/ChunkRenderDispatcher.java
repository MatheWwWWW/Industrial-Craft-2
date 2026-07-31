/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Queues
 *  com.google.common.collect.Sets
 *  com.google.common.primitives.Doubles
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.ObjectArraySet
 *  it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap
 *  it.unimi.dsi.fastutil.objects.ReferenceArraySet
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.primitives.Doubles;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ChunkBufferBuilderPack;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.client.renderer.chunk.RenderRegionCache;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.chunk.VisibilitySet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class ChunkRenderDispatcher {
    private static final Logger f_112672_ = LogUtils.getLogger();
    private static final int f_173707_ = 4;
    private static final VertexFormat f_173708_ = DefaultVertexFormat.f_85811_;
    private static final int f_194400_ = 2;
    private final PriorityBlockingQueue<RenderChunk.ChunkCompileTask> f_194401_ = Queues.newPriorityBlockingQueue();
    private final Queue<RenderChunk.ChunkCompileTask> f_194402_ = Queues.newLinkedBlockingDeque();
    private int f_194403_ = 2;
    private final Queue<ChunkBufferBuilderPack> f_112674_;
    private final Queue<Runnable> f_112675_ = Queues.newConcurrentLinkedQueue();
    private volatile int f_112676_;
    private volatile int f_112677_;
    final ChunkBufferBuilderPack f_112678_;
    private final ProcessorMailbox<Runnable> f_112679_;
    private final Executor f_112680_;
    ClientLevel f_112681_;
    final LevelRenderer f_112682_;
    private Vec3 f_112683_ = Vec3.f_82478_;

    public ChunkRenderDispatcher(ClientLevel p_194405_, LevelRenderer p_194406_, Executor p_194407_, boolean p_194408_, ChunkBufferBuilderPack p_194409_) {
        this.f_112681_ = p_194405_;
        this.f_112682_ = p_194406_;
        int $$5 = Math.max(1, (int)((double)Runtime.getRuntime().maxMemory() * 0.3) / (RenderType.m_110506_().stream().mapToInt(RenderType::m_110507_).sum() * 4) - 1);
        int $$6 = Runtime.getRuntime().availableProcessors();
        int $$7 = p_194408_ ? $$6 : Math.min($$6, 4);
        int $$8 = Math.max(1, Math.min($$7, $$5));
        this.f_112678_ = p_194409_;
        ArrayList $$9 = Lists.newArrayListWithExpectedSize((int)$$8);
        try {
            for (int $$10 = 0; $$10 < $$8; ++$$10) {
                $$9.add(new ChunkBufferBuilderPack());
            }
        }
        catch (OutOfMemoryError $$11) {
            f_112672_.warn("Allocated only {}/{} buffers", (Object)$$9.size(), (Object)$$8);
            int $$12 = Math.min($$9.size() * 2 / 3, $$9.size() - 1);
            for (int $$13 = 0; $$13 < $$12; ++$$13) {
                $$9.remove($$9.size() - 1);
            }
            System.gc();
        }
        this.f_112674_ = Queues.newArrayDeque((Iterable)$$9);
        this.f_112677_ = this.f_112674_.size();
        this.f_112680_ = p_194407_;
        this.f_112679_ = ProcessorMailbox.m_18751_(p_194407_, "Chunk Renderer");
        this.f_112679_.m_6937_(this::m_112734_);
    }

    public void m_194410_(ClientLevel p_194411_) {
        this.f_112681_ = p_194411_;
    }

    private void m_112734_() {
        if (this.f_112674_.isEmpty()) {
            return;
        }
        RenderChunk.ChunkCompileTask $$0 = this.m_194418_();
        if ($$0 == null) {
            return;
        }
        ChunkBufferBuilderPack $$1 = this.f_112674_.poll();
        this.f_112676_ = this.f_194401_.size() + this.f_194402_.size();
        this.f_112677_ = this.f_112674_.size();
        ((CompletableFuture)CompletableFuture.supplyAsync(Util.m_183946_($$0.m_183497_(), () -> $$0.m_5869_($$1)), this.f_112680_).thenCompose(p_194416_ -> p_194416_)).whenComplete((p_234458_, p_234459_) -> {
            if (p_234459_ != null) {
                Minecraft.m_91087_().m_231412_(CrashReport.m_127521_(p_234459_, "Batching chunks"));
                return;
            }
            this.f_112679_.m_6937_(() -> {
                if (p_234458_ == ChunkTaskResult.SUCCESSFUL) {
                    $$1.m_108838_();
                } else {
                    $$1.m_108841_();
                }
                this.f_112674_.add($$1);
                this.f_112677_ = this.f_112674_.size();
                this.m_112734_();
            });
        });
    }

    @Nullable
    private RenderChunk.ChunkCompileTask m_194418_() {
        RenderChunk.ChunkCompileTask $$0;
        if (this.f_194403_ <= 0 && ($$0 = this.f_194402_.poll()) != null) {
            this.f_194403_ = 2;
            return $$0;
        }
        RenderChunk.ChunkCompileTask $$1 = this.f_194401_.poll();
        if ($$1 != null) {
            --this.f_194403_;
            return $$1;
        }
        this.f_194403_ = 2;
        return this.f_194402_.poll();
    }

    public String m_112719_() {
        return String.format(Locale.ROOT, "pC: %03d, pU: %02d, aB: %02d", this.f_112676_, this.f_112675_.size(), this.f_112677_);
    }

    public int m_173712_() {
        return this.f_112676_;
    }

    public int m_173713_() {
        return this.f_112675_.size();
    }

    public int m_173714_() {
        return this.f_112677_;
    }

    public void m_112693_(Vec3 p_112694_) {
        this.f_112683_ = p_112694_;
    }

    public Vec3 m_112727_() {
        return this.f_112683_;
    }

    public void m_194417_() {
        Runnable $$0;
        while (($$0 = this.f_112675_.poll()) != null) {
            $$0.run();
        }
    }

    public void m_200431_(RenderChunk p_200432_, RenderRegionCache p_200433_) {
        p_200432_.m_200439_(p_200433_);
    }

    public void m_112731_() {
        this.m_112735_();
    }

    public void m_112709_(RenderChunk.ChunkCompileTask p_112710_) {
        this.f_112679_.m_6937_(() -> {
            if (p_234464_.f_194420_) {
                this.f_194401_.offer(p_112710_);
            } else {
                this.f_194402_.offer(p_112710_);
            }
            this.f_112676_ = this.f_194401_.size() + this.f_194402_.size();
            this.m_112734_();
        });
    }

    public CompletableFuture<Void> m_234450_(BufferBuilder.RenderedBuffer p_234451_, VertexBuffer p_234452_) {
        return CompletableFuture.runAsync(() -> {
            if (p_234452_.m_231230_()) {
                return;
            }
            p_234452_.m_85921_();
            p_234452_.m_231221_(p_234451_);
            VertexBuffer.m_85931_();
        }, this.f_112675_::add);
    }

    private void m_112735_() {
        while (!this.f_194401_.isEmpty()) {
            RenderChunk.ChunkCompileTask $$0 = this.f_194401_.poll();
            if ($$0 == null) continue;
            $$0.m_6204_();
        }
        while (!this.f_194402_.isEmpty()) {
            RenderChunk.ChunkCompileTask $$1 = this.f_194402_.poll();
            if ($$1 == null) continue;
            $$1.m_6204_();
        }
        this.f_112676_ = 0;
    }

    public boolean m_112732_() {
        return this.f_112676_ == 0 && this.f_112675_.isEmpty();
    }

    public void m_112733_() {
        this.m_112735_();
        this.f_112679_.close();
        this.f_112674_.clear();
    }

    public class RenderChunk {
        public static final int f_173716_ = 16;
        public final int f_173717_;
        public final AtomicReference<CompiledChunk> f_112784_ = new AtomicReference<CompiledChunk>(CompiledChunk.f_112748_);
        final AtomicInteger f_202433_ = new AtomicInteger(0);
        @Nullable
        private RebuildTask f_112787_;
        @Nullable
        private ResortTransparencyTask f_112788_;
        private final Set<BlockEntity> f_112789_ = Sets.newHashSet();
        private final Map<RenderType, VertexBuffer> f_112790_ = RenderType.m_110506_().stream().collect(Collectors.toMap(p_112837_ -> p_112837_, p_112834_ -> new VertexBuffer()));
        private AABB f_112785_;
        private boolean f_112792_ = true;
        final BlockPos.MutableBlockPos f_112793_ = new BlockPos.MutableBlockPos(-1, -1, -1);
        private final BlockPos.MutableBlockPos[] f_112794_ = Util.m_137469_(new BlockPos.MutableBlockPos[6], p_112831_ -> {
            for (int $$1 = 0; $$1 < ((BlockPos.MutableBlockPos[])p_112831_).length; ++$$1) {
                p_112831_[$$1] = new BlockPos.MutableBlockPos();
            }
        });
        private boolean f_112795_;

        public RenderChunk(int p_202436_, int p_202437_, int p_202438_, int p_202439_) {
            this.f_173717_ = p_202436_;
            this.m_112801_(p_202437_, p_202438_, p_202439_);
        }

        private boolean m_112822_(BlockPos p_112823_) {
            return ChunkRenderDispatcher.this.f_112681_.m_6522_(SectionPos.m_123171_(p_112823_.m_123341_()), SectionPos.m_123171_(p_112823_.m_123343_()), ChunkStatus.f_62326_, false) != null;
        }

        public boolean m_112798_() {
            int $$0 = 24;
            if (this.m_112832_() > 576.0) {
                return this.m_112822_(this.f_112794_[Direction.WEST.ordinal()]) && this.m_112822_(this.f_112794_[Direction.NORTH.ordinal()]) && this.m_112822_(this.f_112794_[Direction.EAST.ordinal()]) && this.m_112822_(this.f_112794_[Direction.SOUTH.ordinal()]);
            }
            return true;
        }

        public AABB m_202440_() {
            return this.f_112785_;
        }

        public VertexBuffer m_112807_(RenderType p_112808_) {
            return this.f_112790_.get(p_112808_);
        }

        public void m_112801_(int p_112802_, int p_112803_, int p_112804_) {
            this.m_112846_();
            this.f_112793_.m_122178_(p_112802_, p_112803_, p_112804_);
            this.f_112785_ = new AABB(p_112802_, p_112803_, p_112804_, p_112802_ + 16, p_112803_ + 16, p_112804_ + 16);
            for (Direction $$3 : Direction.values()) {
                this.f_112794_[$$3.ordinal()].m_122190_(this.f_112793_).m_122175_($$3, 16);
            }
        }

        protected double m_112832_() {
            Camera $$0 = Minecraft.m_91087_().f_91063_.m_109153_();
            double $$1 = this.f_112785_.f_82288_ + 8.0 - $$0.m_90583_().f_82479_;
            double $$2 = this.f_112785_.f_82289_ + 8.0 - $$0.m_90583_().f_82480_;
            double $$3 = this.f_112785_.f_82290_ + 8.0 - $$0.m_90583_().f_82481_;
            return $$1 * $$1 + $$2 * $$2 + $$3 * $$3;
        }

        void m_112805_(BufferBuilder p_112806_) {
            p_112806_.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85811_);
        }

        public CompiledChunk m_112835_() {
            return this.f_112784_.get();
        }

        private void m_112846_() {
            this.m_194419_();
            this.f_112784_.set(CompiledChunk.f_112748_);
            this.f_112792_ = true;
        }

        public void m_112838_() {
            this.m_112846_();
            this.f_112790_.values().forEach(VertexBuffer::close);
        }

        public BlockPos m_112839_() {
            return this.f_112793_;
        }

        public void m_112828_(boolean p_112829_) {
            boolean $$1 = this.f_112792_;
            this.f_112792_ = true;
            this.f_112795_ = p_112829_ | ($$1 && this.f_112795_);
        }

        public void m_112840_() {
            this.f_112792_ = false;
            this.f_112795_ = false;
        }

        public boolean m_112841_() {
            return this.f_112792_;
        }

        public boolean m_112842_() {
            return this.f_112792_ && this.f_112795_;
        }

        public BlockPos m_112824_(Direction p_112825_) {
            return this.f_112794_[p_112825_.ordinal()];
        }

        public boolean m_112809_(RenderType p_112810_, ChunkRenderDispatcher p_112811_) {
            CompiledChunk $$2 = this.m_112835_();
            if (this.f_112788_ != null) {
                this.f_112788_.m_6204_();
            }
            if (!$$2.f_112749_.contains(p_112810_)) {
                return false;
            }
            this.f_112788_ = new ResortTransparencyTask(this.m_112832_(), $$2);
            p_112811_.m_112709_(this.f_112788_);
            return true;
        }

        protected boolean m_194419_() {
            boolean $$0 = false;
            if (this.f_112787_ != null) {
                this.f_112787_.m_6204_();
                this.f_112787_ = null;
                $$0 = true;
            }
            if (this.f_112788_ != null) {
                this.f_112788_.m_6204_();
                this.f_112788_ = null;
            }
            return $$0;
        }

        public ChunkCompileTask m_200437_(RenderRegionCache p_200438_) {
            boolean $$5;
            boolean $$1 = this.m_194419_();
            BlockPos $$2 = this.f_112793_.m_7949_();
            boolean $$3 = true;
            RenderChunkRegion $$4 = p_200438_.m_200465_(ChunkRenderDispatcher.this.f_112681_, $$2.m_7918_(-1, -1, -1), $$2.m_7918_(16, 16, 16), 1);
            boolean bl = $$5 = this.f_112784_.get() == CompiledChunk.f_112748_;
            if ($$5 && $$1) {
                this.f_202433_.incrementAndGet();
            }
            this.f_112787_ = new RebuildTask(this.m_112832_(), $$4, !$$5 || this.f_202433_.get() > 2);
            return this.f_112787_;
        }

        public void m_200434_(ChunkRenderDispatcher p_200435_, RenderRegionCache p_200436_) {
            ChunkCompileTask $$2 = this.m_200437_(p_200436_);
            p_200435_.m_112709_($$2);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * WARNING - void declaration
         */
        void m_234465_(Collection<BlockEntity> p_234466_) {
            void $$3;
            HashSet $$1 = Sets.newHashSet(p_234466_);
            Set<BlockEntity> set = this.f_112789_;
            synchronized (set) {
                HashSet $$2 = Sets.newHashSet(this.f_112789_);
                $$1.removeAll(this.f_112789_);
                $$2.removeAll(p_234466_);
                this.f_112789_.clear();
                this.f_112789_.addAll(p_234466_);
            }
            ChunkRenderDispatcher.this.f_112682_.m_109762_((Collection<BlockEntity>)$$3, $$1);
        }

        public void m_200439_(RenderRegionCache p_200440_) {
            ChunkCompileTask $$1 = this.m_200437_(p_200440_);
            $$1.m_5869_(ChunkRenderDispatcher.this.f_112678_);
        }

        class ResortTransparencyTask
        extends ChunkCompileTask {
            private final CompiledChunk f_112886_;

            public ResortTransparencyTask(double p_112889_, CompiledChunk p_112890_) {
                super(p_112889_, true);
                this.f_112886_ = p_112890_;
            }

            @Override
            protected String m_183497_() {
                return "rend_chk_sort";
            }

            @Override
            public CompletableFuture<ChunkTaskResult> m_5869_(ChunkBufferBuilderPack p_112893_) {
                if (this.f_112848_.get()) {
                    return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                }
                if (!RenderChunk.this.m_112798_()) {
                    this.f_112848_.set(true);
                    return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                }
                if (this.f_112848_.get()) {
                    return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                }
                Vec3 $$1 = ChunkRenderDispatcher.this.m_112727_();
                float $$2 = (float)$$1.f_82479_;
                float $$3 = (float)$$1.f_82480_;
                float $$4 = (float)$$1.f_82481_;
                BufferBuilder.SortState $$5 = this.f_112886_.f_112754_;
                if ($$5 == null || this.f_112886_.m_112758_(RenderType.m_110466_())) {
                    return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                }
                BufferBuilder $$6 = p_112893_.m_108839_(RenderType.m_110466_());
                RenderChunk.this.m_112805_($$6);
                $$6.m_166775_($$5);
                $$6.m_166771_($$2 - (float)RenderChunk.this.f_112793_.m_123341_(), $$3 - (float)RenderChunk.this.f_112793_.m_123342_(), $$4 - (float)RenderChunk.this.f_112793_.m_123343_());
                this.f_112886_.f_112754_ = $$6.m_166770_();
                BufferBuilder.RenderedBuffer $$7 = $$6.m_231175_();
                if (this.f_112848_.get()) {
                    $$7.m_231200_();
                    return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                }
                CompletionStage $$8 = ChunkRenderDispatcher.this.m_234450_($$7, RenderChunk.this.m_112807_(RenderType.m_110466_())).thenApply(p_112898_ -> ChunkTaskResult.CANCELLED);
                return ((CompletableFuture)$$8).handle((p_234491_, p_234492_) -> {
                    if (p_234492_ != null && !(p_234492_ instanceof CancellationException) && !(p_234492_ instanceof InterruptedException)) {
                        Minecraft.m_91087_().m_231412_(CrashReport.m_127521_(p_234492_, "Rendering chunk"));
                    }
                    return this.f_112848_.get() ? ChunkTaskResult.CANCELLED : ChunkTaskResult.SUCCESSFUL;
                });
            }

            @Override
            public void m_6204_() {
                this.f_112848_.set(true);
            }
        }

        abstract class ChunkCompileTask
        implements Comparable<ChunkCompileTask> {
            protected final double f_112847_;
            protected final AtomicBoolean f_112848_ = new AtomicBoolean(false);
            protected final boolean f_194420_;

            public ChunkCompileTask(double p_194423_, boolean p_194424_) {
                this.f_112847_ = p_194423_;
                this.f_194420_ = p_194424_;
            }

            public abstract CompletableFuture<ChunkTaskResult> m_5869_(ChunkBufferBuilderPack var1);

            public abstract void m_6204_();

            protected abstract String m_183497_();

            @Override
            public int compareTo(ChunkCompileTask p_112855_) {
                return Doubles.compare((double)this.f_112847_, (double)p_112855_.f_112847_);
            }

            @Override
            public /* synthetic */ int compareTo(Object object) {
                return this.compareTo((ChunkCompileTask)object);
            }
        }

        class RebuildTask
        extends ChunkCompileTask {
            @Nullable
            protected RenderChunkRegion f_112858_;

            public RebuildTask(double p_194427_, RenderChunkRegion p_194428_, boolean p_194429_) {
                super(p_194427_, p_194429_);
                this.f_112858_ = p_194428_;
            }

            @Override
            protected String m_183497_() {
                return "rend_chk_rebuild";
            }

            @Override
            public CompletableFuture<ChunkTaskResult> m_5869_(ChunkBufferBuilderPack p_112872_) {
                if (this.f_112848_.get()) {
                    return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                }
                if (!RenderChunk.this.m_112798_()) {
                    this.f_112858_ = null;
                    RenderChunk.this.m_112828_(false);
                    this.f_112848_.set(true);
                    return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                }
                if (this.f_112848_.get()) {
                    return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                }
                Vec3 $$1 = ChunkRenderDispatcher.this.m_112727_();
                float $$2 = (float)$$1.f_82479_;
                float $$3 = (float)$$1.f_82480_;
                float $$4 = (float)$$1.f_82481_;
                CompileResults $$5 = this.m_234467_($$2, $$3, $$4, p_112872_);
                RenderChunk.this.m_234465_($$5.f_234484_);
                if (this.f_112848_.get()) {
                    $$5.f_234486_.values().forEach(BufferBuilder.RenderedBuffer::m_231200_);
                    return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                }
                CompiledChunk $$6 = new CompiledChunk();
                $$6.f_112753_ = $$5.f_234487_;
                $$6.f_112752_.addAll($$5.f_234485_);
                $$6.f_112754_ = $$5.f_234488_;
                ArrayList $$7 = Lists.newArrayList();
                $$5.f_234486_.forEach((p_234482_, p_234483_) -> {
                    $$7.add(ChunkRenderDispatcher.this.m_234450_((BufferBuilder.RenderedBuffer)p_234483_, RenderChunk.this.m_112807_((RenderType)p_234482_)));
                    p_234481_.f_112749_.add((RenderType)p_234482_);
                });
                return Util.m_143840_($$7).handle((p_234474_, p_234475_) -> {
                    if (p_234475_ != null && !(p_234475_ instanceof CancellationException) && !(p_234475_ instanceof InterruptedException)) {
                        Minecraft.m_91087_().m_231412_(CrashReport.m_127521_(p_234475_, "Rendering chunk"));
                    }
                    if (this.f_112848_.get()) {
                        return ChunkTaskResult.CANCELLED;
                    }
                    RenderChunk.this.f_112784_.set($$6);
                    RenderChunk.this.f_202433_.set(0);
                    ChunkRenderDispatcher.this.f_112682_.m_194352_(RenderChunk.this);
                    return ChunkTaskResult.SUCCESSFUL;
                });
            }

            private CompileResults m_234467_(float p_234468_, float p_234469_, float p_234470_, ChunkBufferBuilderPack p_234471_) {
                CompileResults $$4 = new CompileResults();
                boolean $$5 = true;
                BlockPos $$6 = RenderChunk.this.f_112793_.m_7949_();
                BlockPos $$7 = $$6.m_7918_(15, 15, 15);
                VisGraph $$8 = new VisGraph();
                RenderChunkRegion $$9 = this.f_112858_;
                this.f_112858_ = null;
                PoseStack $$10 = new PoseStack();
                if ($$9 != null) {
                    BufferBuilder $$23;
                    ModelBlockRenderer.m_111000_();
                    ReferenceArraySet $$11 = new ReferenceArraySet(RenderType.m_110506_().size());
                    RandomSource $$12 = RandomSource.m_216327_();
                    BlockRenderDispatcher $$13 = Minecraft.m_91087_().m_91289_();
                    for (BlockPos $$14 : BlockPos.m_121940_($$6, $$7)) {
                        BlockState $$17;
                        FluidState $$18;
                        BlockEntity $$16;
                        BlockState $$15 = $$9.m_8055_($$14);
                        if ($$15.m_60804_($$9, $$14)) {
                            $$8.m_112971_($$14);
                        }
                        if ($$15.m_155947_() && ($$16 = $$9.m_7702_($$14)) != null) {
                            this.m_234476_($$4, $$16);
                        }
                        if (!($$18 = ($$17 = $$9.m_8055_($$14)).m_60819_()).m_76178_()) {
                            RenderType $$19 = ItemBlockRenderTypes.m_109287_($$18);
                            BufferBuilder $$20 = p_234471_.m_108839_($$19);
                            if ($$11.add($$19)) {
                                RenderChunk.this.m_112805_($$20);
                            }
                            $$13.m_234363_($$14, $$9, $$20, $$17, $$18);
                        }
                        if ($$15.m_60799_() == RenderShape.INVISIBLE) continue;
                        RenderType $$21 = ItemBlockRenderTypes.m_109282_($$15);
                        BufferBuilder $$22 = p_234471_.m_108839_($$21);
                        if ($$11.add($$21)) {
                            RenderChunk.this.m_112805_($$22);
                        }
                        $$10.m_85836_();
                        $$10.m_85837_($$14.m_123341_() & 0xF, $$14.m_123342_() & 0xF, $$14.m_123343_() & 0xF);
                        $$13.m_234355_($$15, $$14, $$9, $$10, $$22, true, $$12);
                        $$10.m_85849_();
                    }
                    if ($$11.contains(RenderType.m_110466_()) && !($$23 = p_234471_.m_108839_(RenderType.m_110466_())).m_231164_()) {
                        $$23.m_166771_(p_234468_ - (float)$$6.m_123341_(), p_234469_ - (float)$$6.m_123342_(), p_234470_ - (float)$$6.m_123343_());
                        $$4.f_234488_ = $$23.m_166770_();
                    }
                    for (RenderType $$24 : $$11) {
                        BufferBuilder.RenderedBuffer $$25 = p_234471_.m_108839_($$24).m_231168_();
                        if ($$25 == null) continue;
                        $$4.f_234486_.put($$24, $$25);
                    }
                    ModelBlockRenderer.m_111077_();
                }
                $$4.f_234487_ = $$8.m_112958_();
                return $$4;
            }

            private <E extends BlockEntity> void m_234476_(CompileResults p_234477_, E p_234478_) {
                BlockEntityRenderer<E> $$2 = Minecraft.m_91087_().m_167982_().m_112265_(p_234478_);
                if ($$2 != null) {
                    p_234477_.f_234485_.add(p_234478_);
                    if ($$2.m_5932_(p_234478_)) {
                        p_234477_.f_234484_.add(p_234478_);
                    }
                }
            }

            @Override
            public void m_6204_() {
                this.f_112858_ = null;
                if (this.f_112848_.compareAndSet(false, true)) {
                    RenderChunk.this.m_112828_(false);
                }
            }

            static final class CompileResults {
                public final List<BlockEntity> f_234484_ = new ArrayList<BlockEntity>();
                public final List<BlockEntity> f_234485_ = new ArrayList<BlockEntity>();
                public final Map<RenderType, BufferBuilder.RenderedBuffer> f_234486_ = new Reference2ObjectArrayMap();
                public VisibilitySet f_234487_ = new VisibilitySet();
                @Nullable
                public BufferBuilder.SortState f_234488_;

                CompileResults() {
                }
            }
        }
    }

    static final class ChunkTaskResult
    extends Enum<ChunkTaskResult> {
        public static final /* enum */ ChunkTaskResult SUCCESSFUL = new ChunkTaskResult();
        public static final /* enum */ ChunkTaskResult CANCELLED = new ChunkTaskResult();
        private static final /* synthetic */ ChunkTaskResult[] $VALUES;

        public static ChunkTaskResult[] values() {
            return (ChunkTaskResult[])$VALUES.clone();
        }

        public static ChunkTaskResult valueOf(String p_112746_) {
            return Enum.valueOf(ChunkTaskResult.class, p_112746_);
        }

        private static /* synthetic */ ChunkTaskResult[] m_173715_() {
            return new ChunkTaskResult[]{SUCCESSFUL, CANCELLED};
        }

        static {
            $VALUES = ChunkTaskResult.m_173715_();
        }
    }

    public static class CompiledChunk {
        public static final CompiledChunk f_112748_ = new CompiledChunk(){

            @Override
            public boolean m_7259_(Direction p_112782_, Direction p_112783_) {
                return false;
            }
        };
        final Set<RenderType> f_112749_ = new ObjectArraySet(RenderType.m_110506_().size());
        final List<BlockEntity> f_112752_ = Lists.newArrayList();
        VisibilitySet f_112753_ = new VisibilitySet();
        @Nullable
        BufferBuilder.SortState f_112754_;

        public boolean m_112757_() {
            return this.f_112749_.isEmpty();
        }

        public boolean m_112758_(RenderType p_112759_) {
            return !this.f_112749_.contains(p_112759_);
        }

        public List<BlockEntity> m_112773_() {
            return this.f_112752_;
        }

        public boolean m_7259_(Direction p_112771_, Direction p_112772_) {
            return this.f_112753_.m_112983_(p_112771_, p_112772_);
        }
    }
}

