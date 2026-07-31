/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Either
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.chunk;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import net.minecraft.util.profiling.jfr.callback.ProfiledDuration;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.BelowZeroRetrogen;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class ChunkStatus {
    public static final int f_187758_ = 8;
    private static final EnumSet<Heightmap.Types> f_62327_ = EnumSet.of(Heightmap.Types.OCEAN_FLOOR_WG, Heightmap.Types.WORLD_SURFACE_WG);
    public static final EnumSet<Heightmap.Types> f_62328_ = EnumSet.of(Heightmap.Types.OCEAN_FLOOR, Heightmap.Types.WORLD_SURFACE, Heightmap.Types.MOTION_BLOCKING, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES);
    private static final LoadingTask f_62329_ = (p_223343_, p_223344_, p_223345_, p_223346_, p_223347_, p_223348_) -> {
        if (p_223348_ instanceof ProtoChunk) {
            ProtoChunk $$6 = (ProtoChunk)p_223348_;
            if (!p_223348_.m_6415_().m_62427_(p_223343_)) {
                $$6.m_7150_(p_223343_);
            }
        }
        return CompletableFuture.completedFuture(Either.left((Object)p_223348_));
    };
    public static final ChunkStatus f_62314_ = ChunkStatus.m_62414_("empty", null, -1, f_62327_, ChunkType.PROTOCHUNK, (p_156307_, p_156308_, p_156309_, p_156310_, p_156311_) -> {});
    public static final ChunkStatus f_62315_ = ChunkStatus.m_62406_("structure_starts", f_62314_, 0, f_62327_, ChunkType.PROTOCHUNK, (p_223361_, p_223362_, p_223363_, p_223364_, p_223365_, p_223366_, p_223367_, p_223368_, p_223369_, p_223370_) -> {
        if (!p_223369_.m_6415_().m_62427_(p_223361_)) {
            if (p_223363_.m_7654_().m_129910_().m_5961_().m_224677_()) {
                p_223364_.m_223164_(p_223363_.m_5962_(), p_223363_.m_7726_().m_214994_(), p_223363_.m_215010_(), p_223369_, p_223365_, p_223363_.m_7328_());
            }
            if (p_223369_ instanceof ProtoChunk) {
                ProtoChunk $$10 = (ProtoChunk)p_223369_;
                $$10.m_7150_(p_223361_);
            }
            p_223363_.m_196557_(p_223369_);
        }
        return CompletableFuture.completedFuture(Either.left((Object)p_223369_));
    }, (p_223325_, p_223326_, p_223327_, p_223328_, p_223329_, p_223330_) -> {
        if (!p_223330_.m_6415_().m_62427_(p_223325_)) {
            if (p_223330_ instanceof ProtoChunk) {
                ProtoChunk $$6 = (ProtoChunk)p_223330_;
                $$6.m_7150_(p_223325_);
            }
            p_223326_.m_196557_(p_223330_);
        }
        return CompletableFuture.completedFuture(Either.left((Object)p_223330_));
    });
    public static final ChunkStatus f_62316_ = ChunkStatus.m_62414_("structure_references", f_62315_, 8, f_62327_, ChunkType.PROTOCHUNK, (p_196843_, p_196844_, p_196845_, p_196846_, p_196847_) -> {
        WorldGenRegion $$5 = new WorldGenRegion(p_196844_, p_196846_, p_196843_, -1);
        p_196845_.m_223076_($$5, p_196844_.m_215010_().m_220468_($$5), p_196847_);
    });
    public static final ChunkStatus f_62317_ = ChunkStatus.m_62399_("biomes", f_62316_, 8, f_62327_, ChunkType.PROTOCHUNK, (p_223350_, p_223351_, p_223352_, p_223353_, p_223354_, p_223355_, p_223356_, p_223357_, p_223358_, p_223359_) -> {
        if (p_223359_ || !p_223358_.m_6415_().m_62427_(p_223350_)) {
            WorldGenRegion $$10 = new WorldGenRegion(p_223352_, p_223357_, p_223350_, -1);
            return p_223353_.m_213908_(p_223352_.m_5962_().m_175515_(Registry.f_122885_), p_223351_, p_223352_.m_7726_().m_214994_(), Blender.m_190202_($$10), p_223352_.m_215010_().m_220468_($$10), p_223358_).thenApply(p_196819_ -> {
                if (p_196819_ instanceof ProtoChunk) {
                    ((ProtoChunk)p_196819_).m_7150_(p_223350_);
                }
                return Either.left((Object)p_196819_);
            });
        }
        return CompletableFuture.completedFuture(Either.left((Object)p_223358_));
    });
    public static final ChunkStatus f_62318_ = ChunkStatus.m_62399_("noise", f_62317_, 8, f_62327_, ChunkType.PROTOCHUNK, (p_223332_, p_223333_, p_223334_, p_223335_, p_223336_, p_223337_, p_223338_, p_223339_, p_223340_, p_223341_) -> {
        if (p_223341_ || !p_223340_.m_6415_().m_62427_(p_223332_)) {
            WorldGenRegion $$10 = new WorldGenRegion(p_223334_, p_223339_, p_223332_, 0);
            return p_223335_.m_213974_(p_223333_, Blender.m_190202_($$10), p_223334_.m_7726_().m_214994_(), p_223334_.m_215010_().m_220468_($$10), p_223340_).thenApply(p_196792_ -> {
                if (p_196792_ instanceof ProtoChunk) {
                    ProtoChunk $$2 = (ProtoChunk)p_196792_;
                    BelowZeroRetrogen $$3 = $$2.m_183376_();
                    if ($$3 != null) {
                        BelowZeroRetrogen.m_188474_($$2);
                        if ($$3.m_198220_()) {
                            $$3.m_198221_($$2);
                        }
                    }
                    $$2.m_7150_(p_223332_);
                }
                return Either.left((Object)p_196792_);
            });
        }
        return CompletableFuture.completedFuture(Either.left((Object)p_223340_));
    });
    public static final ChunkStatus f_62319_ = ChunkStatus.m_62414_("surface", f_62318_, 8, f_62327_, ChunkType.PROTOCHUNK, (p_156247_, p_156248_, p_156249_, p_156250_, p_156251_) -> {
        WorldGenRegion $$5 = new WorldGenRegion(p_156248_, p_156250_, p_156247_, 0);
        p_156249_.m_214194_($$5, p_156248_.m_215010_().m_220468_($$5), p_156248_.m_7726_().m_214994_(), p_156251_);
    });
    public static final ChunkStatus f_62320_ = ChunkStatus.m_62414_("carvers", f_62319_, 8, f_62327_, ChunkType.PROTOCHUNK, (p_187819_, p_187820_, p_187821_, p_187822_, p_187823_) -> {
        WorldGenRegion $$5 = new WorldGenRegion(p_187820_, p_187822_, p_187819_, 0);
        if (p_187823_ instanceof ProtoChunk) {
            ProtoChunk $$6 = (ProtoChunk)p_187823_;
            Blender.m_197034_($$5, $$6);
        }
        p_187821_.m_213679_($$5, p_187820_.m_7328_(), p_187820_.m_7726_().m_214994_(), p_187820_.m_7062_(), p_187820_.m_215010_().m_220468_($$5), p_187823_, GenerationStep.Carving.AIR);
    });
    public static final ChunkStatus f_62321_ = ChunkStatus.m_62414_("liquid_carvers", f_62320_, 8, f_62328_, ChunkType.PROTOCHUNK, (p_196805_, p_196806_, p_196807_, p_196808_, p_196809_) -> {});
    public static final ChunkStatus f_62322_ = ChunkStatus.m_62406_("features", f_62321_, 8, f_62328_, ChunkType.PROTOCHUNK, (p_223314_, p_223315_, p_223316_, p_223317_, p_223318_, p_223319_, p_223320_, p_223321_, p_223322_, p_223323_) -> {
        ProtoChunk $$10 = (ProtoChunk)p_223322_;
        $$10.m_63209_(p_223319_);
        if (p_223323_ || !p_223322_.m_6415_().m_62427_(p_223314_)) {
            Heightmap.m_64256_(p_223322_, EnumSet.of(Heightmap.Types.MOTION_BLOCKING, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Heightmap.Types.OCEAN_FLOOR, Heightmap.Types.WORLD_SURFACE));
            WorldGenRegion $$11 = new WorldGenRegion(p_223316_, p_223321_, p_223314_, 1);
            p_223317_.m_213609_($$11, p_223322_, p_223316_.m_215010_().m_220468_($$11));
            Blender.m_197031_($$11, p_223322_);
            $$10.m_7150_(p_223314_);
        }
        return p_223319_.m_215136_(p_223322_).thenApply(Either::left);
    }, (p_223307_, p_223308_, p_223309_, p_223310_, p_223311_, p_223312_) -> p_223310_.m_215136_(p_223312_).thenApply(Either::left));
    public static final ChunkStatus f_62323_ = ChunkStatus.m_62406_("light", f_62322_, 1, f_62328_, ChunkType.PROTOCHUNK, (p_223296_, p_223297_, p_223298_, p_223299_, p_223300_, p_223301_, p_223302_, p_223303_, p_223304_, p_223305_) -> ChunkStatus.m_62388_(p_223296_, p_223301_, p_223304_), (p_223289_, p_223290_, p_223291_, p_223292_, p_223293_, p_223294_) -> ChunkStatus.m_62388_(p_223289_, p_223292_, p_223294_));
    public static final ChunkStatus f_62324_ = ChunkStatus.m_62414_("spawn", f_62323_, 0, f_62328_, ChunkType.PROTOCHUNK, (p_196758_, p_196759_, p_196760_, p_196761_, p_196762_) -> {
        if (!p_196762_.m_187679_()) {
            p_196760_.m_6929_(new WorldGenRegion(p_196759_, p_196761_, p_196758_, -1));
        }
    });
    public static final ChunkStatus f_62325_ = ChunkStatus.m_62414_("heightmaps", f_62324_, 0, f_62328_, ChunkType.PROTOCHUNK, (p_223254_, p_223255_, p_223256_, p_223257_, p_223258_) -> {});
    public static final ChunkStatus f_62326_ = ChunkStatus.m_62406_("full", f_62325_, 0, f_62328_, ChunkType.LEVELCHUNK, (p_223267_, p_223268_, p_223269_, p_223270_, p_223271_, p_223272_, p_223273_, p_223274_, p_223275_, p_223276_) -> (CompletableFuture)p_223273_.apply(p_223275_), (p_223260_, p_223261_, p_223262_, p_223263_, p_223264_, p_223265_) -> (CompletableFuture)p_223264_.apply(p_223265_));
    private static final List<ChunkStatus> f_62330_ = ImmutableList.of((Object)f_62326_, (Object)f_62322_, (Object)f_62321_, (Object)f_62317_, (Object)f_62315_, (Object)f_62315_, (Object)f_62315_, (Object)f_62315_, (Object)f_62315_, (Object)f_62315_, (Object)f_62315_, (Object)f_62315_, (Object[])new ChunkStatus[0]);
    private static final IntList f_62331_ = (IntList)Util.m_137469_(new IntArrayList(ChunkStatus.m_62349_().size()), p_223278_ -> {
        int $$1 = 0;
        for (int $$2 = ChunkStatus.m_62349_().size() - 1; $$2 >= 0; --$$2) {
            while ($$1 + 1 < f_62330_.size() && $$2 <= f_62330_.get($$1 + 1).m_62445_()) {
                ++$$1;
            }
            p_223278_.add(0, $$1);
        }
    });
    private final String f_62332_;
    private final int f_62333_;
    private final ChunkStatus f_62334_;
    private final GenerationTask f_62335_;
    private final LoadingTask f_62336_;
    private final int f_62337_;
    private final ChunkType f_62338_;
    private final EnumSet<Heightmap.Types> f_62339_;

    private static CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_62388_(ChunkStatus p_62389_, ThreadedLevelLightEngine p_62390_, ChunkAccess p_62391_) {
        boolean $$3 = ChunkStatus.m_62392_(p_62389_, p_62391_);
        if (!p_62391_.m_6415_().m_62427_(p_62389_)) {
            ((ProtoChunk)p_62391_).m_7150_(p_62389_);
        }
        return p_62390_.m_9353_(p_62391_, $$3).thenApply(Either::left);
    }

    private static ChunkStatus m_62414_(String p_62415_, @Nullable ChunkStatus p_62416_, int p_62417_, EnumSet<Heightmap.Types> p_62418_, ChunkType p_62419_, SimpleGenerationTask p_62420_) {
        return ChunkStatus.m_62399_(p_62415_, p_62416_, p_62417_, p_62418_, p_62419_, p_62420_);
    }

    private static ChunkStatus m_62399_(String p_62400_, @Nullable ChunkStatus p_62401_, int p_62402_, EnumSet<Heightmap.Types> p_62403_, ChunkType p_62404_, GenerationTask p_62405_) {
        return ChunkStatus.m_62406_(p_62400_, p_62401_, p_62402_, p_62403_, p_62404_, p_62405_, f_62329_);
    }

    private static ChunkStatus m_62406_(String p_62407_, @Nullable ChunkStatus p_62408_, int p_62409_, EnumSet<Heightmap.Types> p_62410_, ChunkType p_62411_, GenerationTask p_62412_, LoadingTask p_62413_) {
        return Registry.m_122961_(Registry.f_122833_, p_62407_, new ChunkStatus(p_62407_, p_62408_, p_62409_, p_62410_, p_62411_, p_62412_, p_62413_));
    }

    public static List<ChunkStatus> m_62349_() {
        ChunkStatus $$1;
        ArrayList $$0 = Lists.newArrayList();
        for ($$1 = f_62326_; $$1.m_62482_() != $$1; $$1 = $$1.m_62482_()) {
            $$0.add($$1);
        }
        $$0.add($$1);
        Collections.reverse($$0);
        return $$0;
    }

    private static boolean m_62392_(ChunkStatus p_62393_, ChunkAccess p_62394_) {
        return p_62394_.m_6415_().m_62427_(p_62393_) && p_62394_.m_6332_();
    }

    public static ChunkStatus m_156185_(int p_156186_) {
        if (p_156186_ >= f_62330_.size()) {
            return f_62314_;
        }
        if (p_156186_ < 0) {
            return f_62326_;
        }
        return f_62330_.get(p_156186_);
    }

    public static int m_62421_() {
        return f_62330_.size();
    }

    public static int m_62370_(ChunkStatus p_62371_) {
        return f_62331_.getInt(p_62371_.m_62445_());
    }

    ChunkStatus(String p_62342_, @Nullable ChunkStatus p_62343_, int p_62344_, EnumSet<Heightmap.Types> p_62345_, ChunkType p_62346_, GenerationTask p_62347_, LoadingTask p_62348_) {
        this.f_62332_ = p_62342_;
        this.f_62334_ = p_62343_ == null ? this : p_62343_;
        this.f_62335_ = p_62347_;
        this.f_62336_ = p_62348_;
        this.f_62337_ = p_62344_;
        this.f_62338_ = p_62346_;
        this.f_62339_ = p_62345_;
        this.f_62333_ = p_62343_ == null ? 0 : p_62343_.m_62445_() + 1;
    }

    public int m_62445_() {
        return this.f_62333_;
    }

    public String m_62467_() {
        return this.f_62332_;
    }

    public ChunkStatus m_62482_() {
        return this.f_62334_;
    }

    public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_223279_(Executor p_223280_, ServerLevel p_223281_, ChunkGenerator p_223282_, StructureTemplateManager p_223283_, ThreadedLevelLightEngine p_223284_, Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> p_223285_, List<ChunkAccess> p_223286_, boolean p_223287_) {
        ChunkAccess $$8 = p_223286_.get(p_223286_.size() / 2);
        ProfiledDuration $$9 = JvmProfiler.f_185340_.m_183559_($$8.m_7697_(), p_223281_.m_46472_(), this.f_62332_);
        CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> $$10 = this.f_62335_.m_214024_(this, p_223280_, p_223281_, p_223282_, p_223283_, p_223284_, p_223285_, p_223286_, $$8, p_223287_);
        return $$9 != null ? $$10.thenApply(p_223252_ -> {
            $$9.m_185413_();
            return p_223252_;
        }) : $$10;
    }

    public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_223244_(ServerLevel p_223245_, StructureTemplateManager p_223246_, ThreadedLevelLightEngine p_223247_, Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> p_223248_, ChunkAccess p_223249_) {
        return this.f_62336_.m_223381_(this, p_223245_, p_223246_, p_223247_, p_223248_, p_223249_);
    }

    public int m_62488_() {
        return this.f_62337_;
    }

    public ChunkType m_62494_() {
        return this.f_62338_;
    }

    public static ChunkStatus m_62397_(String p_62398_) {
        return Registry.f_122833_.m_7745_(ResourceLocation.m_135820_(p_62398_));
    }

    public EnumSet<Heightmap.Types> m_62500_() {
        return this.f_62339_;
    }

    public boolean m_62427_(ChunkStatus p_62428_) {
        return this.m_62445_() >= p_62428_.m_62445_();
    }

    public String toString() {
        return Registry.f_122833_.m_7981_(this).toString();
    }

    public static final class ChunkType
    extends Enum<ChunkType> {
        public static final /* enum */ ChunkType PROTOCHUNK = new ChunkType();
        public static final /* enum */ ChunkType LEVELCHUNK = new ChunkType();
        private static final /* synthetic */ ChunkType[] $VALUES;

        public static ChunkType[] values() {
            return (ChunkType[])$VALUES.clone();
        }

        public static ChunkType valueOf(String p_62520_) {
            return Enum.valueOf(ChunkType.class, p_62520_);
        }

        private static /* synthetic */ ChunkType[] m_156312_() {
            return new ChunkType[]{PROTOCHUNK, LEVELCHUNK};
        }

        static {
            $VALUES = ChunkType.m_156312_();
        }
    }

    static interface GenerationTask {
        public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_214024_(ChunkStatus var1, Executor var2, ServerLevel var3, ChunkGenerator var4, StructureTemplateManager var5, ThreadedLevelLightEngine var6, Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> var7, List<ChunkAccess> var8, ChunkAccess var9, boolean var10);
    }

    static interface LoadingTask {
        public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_223381_(ChunkStatus var1, ServerLevel var2, StructureTemplateManager var3, ThreadedLevelLightEngine var4, Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> var5, ChunkAccess var6);
    }

    static interface SimpleGenerationTask
    extends GenerationTask {
        @Override
        default public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_214024_(ChunkStatus p_223389_, Executor p_223390_, ServerLevel p_223391_, ChunkGenerator p_223392_, StructureTemplateManager p_223393_, ThreadedLevelLightEngine p_223394_, Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> p_223395_, List<ChunkAccess> p_223396_, ChunkAccess p_223397_, boolean p_223398_) {
            if (p_223398_ || !p_223397_.m_6415_().m_62427_(p_223389_)) {
                this.m_156322_(p_223389_, p_223391_, p_223392_, p_223396_, p_223397_);
                if (p_223397_ instanceof ProtoChunk) {
                    ProtoChunk $$10 = (ProtoChunk)p_223397_;
                    $$10.m_7150_(p_223389_);
                }
            }
            return CompletableFuture.completedFuture(Either.left((Object)p_223397_));
        }

        public void m_156322_(ChunkStatus var1, ServerLevel var2, ChunkGenerator var3, List<ChunkAccess> var4, ChunkAccess var5);
    }
}

