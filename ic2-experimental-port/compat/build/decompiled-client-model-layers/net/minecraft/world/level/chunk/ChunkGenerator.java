/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Stopwatch
 *  com.google.common.base.Suppliers
 *  com.google.common.base.Ticker
 *  com.mojang.datafixers.Products$P1
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Mu
 *  it.unimi.dsi.fastutil.ints.IntArraySet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArraySet
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableBoolean
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.chunk;

import com.google.common.base.Stopwatch;
import com.google.common.base.Suppliers;
import com.google.common.base.Ticker;
import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.FeatureSorter;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureCheckResult;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.slf4j.Logger;

public abstract class ChunkGenerator {
    private static final Logger f_211627_ = LogUtils.getLogger();
    public static final Codec<ChunkGenerator> f_62136_ = Registry.f_122890_.m_194605_().dispatchStable(ChunkGenerator::m_6909_, Function.identity());
    protected final Registry<StructureSet> f_207955_;
    protected final BiomeSource f_62137_;
    private final Supplier<List<FeatureSorter.StepFeatureData>> f_223020_;
    protected final Optional<HolderSet<StructureSet>> f_207956_;
    private final Function<Holder<Biome>, BiomeGenerationSettings> f_223021_;
    private final Map<Structure, List<StructurePlacement>> f_223022_ = new Object2ObjectOpenHashMap();
    private final Map<ConcentricRingsStructurePlacement, CompletableFuture<List<ChunkPos>>> f_204350_ = new Object2ObjectArrayMap();
    private boolean f_207958_;

    protected static <T extends ChunkGenerator> Products.P1<RecordCodecBuilder.Mu<T>, Registry<StructureSet>> m_208005_(RecordCodecBuilder.Instance<T> p_208006_) {
        return p_208006_.group((App)RegistryOps.m_206832_(Registry.f_211073_).forGetter(p_208008_ -> p_208008_.f_207955_));
    }

    public ChunkGenerator(Registry<StructureSet> p_207960_, Optional<HolderSet<StructureSet>> p_207961_, BiomeSource p_207962_) {
        this(p_207960_, p_207961_, p_207962_, p_223234_ -> ((Biome)p_223234_.m_203334_()).m_47536_());
    }

    public ChunkGenerator(Registry<StructureSet> p_223024_, Optional<HolderSet<StructureSet>> p_223025_, BiomeSource p_223026_, Function<Holder<Biome>, BiomeGenerationSettings> p_223027_) {
        this.f_207955_ = p_223024_;
        this.f_62137_ = p_223026_;
        this.f_223021_ = p_223027_;
        this.f_207956_ = p_223025_;
        this.f_223020_ = Suppliers.memoize(() -> FeatureSorter.m_220603_(List.copyOf(p_223026_.m_207840_()), p_223216_ -> ((BiomeGenerationSettings)p_223027_.apply((Holder<Biome>)p_223216_)).m_47818_(), true));
    }

    public Stream<Holder<StructureSet>> m_207969_() {
        if (this.f_207956_.isPresent()) {
            return this.f_207956_.get().m_203614_();
        }
        return this.f_207955_.m_203611_().map(Holder::m_205706_);
    }

    private void m_223227_(RandomState p_223228_) {
        Set<Holder<Biome>> $$1 = this.f_62137_.m_207840_();
        this.m_207969_().forEach(p_223208_ -> {
            StructurePlacement $$7;
            StructureSet $$3 = (StructureSet)p_223208_.m_203334_();
            boolean $$4 = false;
            for (StructureSet.StructureSelectionEntry $$5 : $$3.f_210003_()) {
                Structure $$6 = $$5.f_210026_().m_203334_();
                if (!$$6.m_226559_().m_203614_().anyMatch($$1::contains)) continue;
                this.f_223022_.computeIfAbsent($$6, p_223230_ -> new ArrayList()).add($$3.f_210004_());
                $$4 = true;
            }
            if ($$4 && ($$7 = $$3.f_210004_()) instanceof ConcentricRingsStructurePlacement) {
                ConcentricRingsStructurePlacement $$8 = (ConcentricRingsStructurePlacement)$$7;
                this.f_204350_.put($$8, this.m_223148_((Holder<StructureSet>)p_223208_, p_223228_, $$8));
            }
        });
    }

    private CompletableFuture<List<ChunkPos>> m_223148_(Holder<StructureSet> p_223149_, RandomState p_223150_, ConcentricRingsStructurePlacement p_223151_) {
        if (p_223151_.m_204967_() == 0) {
            return CompletableFuture.completedFuture(List.of());
        }
        return CompletableFuture.supplyAsync(Util.m_183946_("placement calculation", () -> {
            Stopwatch $$3 = Stopwatch.createStarted((Ticker)Util.f_211544_);
            ArrayList<ChunkPos> $$4 = new ArrayList<ChunkPos>();
            int $$5 = p_223151_.m_204965_();
            int $$6 = p_223151_.m_204967_();
            int $$7 = p_223151_.m_204966_();
            HolderSet<Biome> $$8 = p_223151_.m_226998_();
            RandomSource $$9 = RandomSource.m_216327_();
            $$9.m_188584_(this instanceof FlatLevelSource ? 0L : p_223150_.m_224559_());
            double $$10 = $$9.m_188500_() * Math.PI * 2.0;
            int $$11 = 0;
            int $$12 = 0;
            for (int $$13 = 0; $$13 < $$6; ++$$13) {
                double $$14 = (double)(4 * $$5 + $$5 * $$12 * 6) + ($$9.m_188500_() - 0.5) * ((double)$$5 * 2.5);
                int $$15 = (int)Math.round(Math.cos($$10) * $$14);
                int $$16 = (int)Math.round(Math.sin($$10) * $$14);
                Pair<BlockPos, Holder<Biome>> $$17 = this.f_62137_.m_220570_(SectionPos.m_175554_($$15, 8), 0, SectionPos.m_175554_($$16, 8), 112, $$8::m_203333_, $$9, p_223150_.m_224579_());
                if ($$17 != null) {
                    BlockPos $$18 = (BlockPos)$$17.getFirst();
                    $$15 = SectionPos.m_123171_($$18.m_123341_());
                    $$16 = SectionPos.m_123171_($$18.m_123343_());
                }
                $$4.add(new ChunkPos($$15, $$16));
                $$10 += Math.PI * 2 / (double)$$7;
                if (++$$11 != $$7) continue;
                $$11 = 0;
                $$7 += 2 * $$7 / (++$$12 + 1);
                $$7 = Math.min($$7, $$6 - $$13);
                $$10 += $$9.m_188500_() * Math.PI * 2.0;
            }
            double $$19 = (double)$$3.stop().elapsed(TimeUnit.MILLISECONDS) / 1000.0;
            f_211627_.debug("Calculation for {} took {}s", (Object)p_223149_, (Object)$$19);
            return $$4;
        }), Util.m_183991_());
    }

    protected abstract Codec<? extends ChunkGenerator> m_6909_();

    public Optional<ResourceKey<Codec<? extends ChunkGenerator>>> m_187743_() {
        return Registry.f_122890_.m_7854_(this.m_6909_());
    }

    public CompletableFuture<ChunkAccess> m_213908_(Registry<Biome> p_223158_, Executor p_223159_, RandomState p_223160_, Blender p_223161_, StructureManager p_223162_, ChunkAccess p_223163_) {
        return CompletableFuture.supplyAsync(Util.m_183946_("init_biomes", () -> {
            p_223163_.m_183442_(this.f_62137_, p_223160_.m_224579_());
            return p_223163_;
        }), Util.m_183991_());
    }

    public abstract void m_213679_(WorldGenRegion var1, long var2, RandomState var4, BiomeManager var5, StructureManager var6, ChunkAccess var7, GenerationStep.Carving var8);

    @Nullable
    public Pair<BlockPos, Holder<Structure>> m_223037_(ServerLevel p_223038_, HolderSet<Structure> p_223039_, BlockPos p_223040_, int p_223041_, boolean p_223042_) {
        Object2ObjectArrayMap $$5 = new Object2ObjectArrayMap();
        for (Holder holder : p_223039_) {
            for (StructurePlacement $$7 : this.m_223138_(holder, p_223038_.m_7726_().m_214994_())) {
                $$5.computeIfAbsent($$7, p_223127_ -> new ObjectArraySet()).add(holder);
            }
        }
        if ($$5.isEmpty()) {
            return null;
        }
        Pair<BlockPos, Holder<Structure>> $$8 = null;
        double d = Double.MAX_VALUE;
        StructureManager $$10 = p_223038_.m_215010_();
        ArrayList $$11 = new ArrayList($$5.size());
        for (Map.Entry $$12 : $$5.entrySet()) {
            StructurePlacement $$13 = (StructurePlacement)$$12.getKey();
            if ($$13 instanceof ConcentricRingsStructurePlacement) {
                BlockPos blockPos;
                double $$17;
                ConcentricRingsStructurePlacement $$14 = (ConcentricRingsStructurePlacement)$$13;
                Pair<BlockPos, Holder<Structure>> $$15 = this.m_223181_((Set)$$12.getValue(), p_223038_, $$10, p_223040_, p_223042_, $$14);
                if ($$15 == null || !(($$17 = p_223040_.m_123331_(blockPos = (BlockPos)$$15.getFirst())) < d)) continue;
                d = $$17;
                $$8 = $$15;
                continue;
            }
            if (!($$13 instanceof RandomSpreadStructurePlacement)) continue;
            $$11.add($$12);
        }
        if (!$$11.isEmpty()) {
            int $$18 = SectionPos.m_123171_(p_223040_.m_123341_());
            int $$19 = SectionPos.m_123171_(p_223040_.m_123343_());
            for (int $$20 = 0; $$20 <= p_223041_; ++$$20) {
                boolean $$21 = false;
                for (Map.Entry entry : $$11) {
                    RandomSpreadStructurePlacement $$23 = (RandomSpreadStructurePlacement)entry.getKey();
                    Pair<BlockPos, Holder<Structure>> $$24 = ChunkGenerator.m_223188_((Set)entry.getValue(), p_223038_, $$10, $$18, $$19, $$20, p_223042_, p_223038_.m_7328_(), $$23);
                    if ($$24 == null) continue;
                    $$21 = true;
                    double $$25 = p_223040_.m_123331_((Vec3i)$$24.getFirst());
                    if (!($$25 < d)) continue;
                    d = $$25;
                    $$8 = $$24;
                }
                if (!$$21) continue;
                return $$8;
            }
        }
        return $$8;
    }

    @Nullable
    private Pair<BlockPos, Holder<Structure>> m_223181_(Set<Holder<Structure>> p_223182_, ServerLevel p_223183_, StructureManager p_223184_, BlockPos p_223185_, boolean p_223186_, ConcentricRingsStructurePlacement p_223187_) {
        List<ChunkPos> $$6 = this.m_223119_(p_223187_, p_223183_.m_7726_().m_214994_());
        if ($$6 == null) {
            throw new IllegalStateException("Somehow tried to find structures for a placement that doesn't exist");
        }
        Pair<BlockPos, Holder<Structure>> $$7 = null;
        double $$8 = Double.MAX_VALUE;
        BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
        for (ChunkPos $$10 : $$6) {
            Pair<BlockPos, Holder<Structure>> $$13;
            $$9.m_122178_(SectionPos.m_175554_($$10.f_45578_, 8), 32, SectionPos.m_175554_($$10.f_45579_, 8));
            double $$11 = $$9.m_123331_(p_223185_);
            boolean $$12 = $$7 == null || $$11 < $$8;
            if (!$$12 || ($$13 = ChunkGenerator.m_223198_(p_223182_, p_223183_, p_223184_, p_223186_, p_223187_, $$10)) == null) continue;
            $$7 = $$13;
            $$8 = $$11;
        }
        return $$7;
    }

    @Nullable
    private static Pair<BlockPos, Holder<Structure>> m_223188_(Set<Holder<Structure>> p_223189_, LevelReader p_223190_, StructureManager p_223191_, int p_223192_, int p_223193_, int p_223194_, boolean p_223195_, long p_223196_, RandomSpreadStructurePlacement p_223197_) {
        int $$9 = p_223197_.m_205003_();
        for (int $$10 = -p_223194_; $$10 <= p_223194_; ++$$10) {
            boolean $$11 = $$10 == -p_223194_ || $$10 == p_223194_;
            for (int $$12 = -p_223194_; $$12 <= p_223194_; ++$$12) {
                int $$15;
                int $$14;
                ChunkPos $$16;
                Pair<BlockPos, Holder<Structure>> $$17;
                boolean $$13;
                boolean bl = $$13 = $$12 == -p_223194_ || $$12 == p_223194_;
                if (!$$11 && !$$13 || ($$17 = ChunkGenerator.m_223198_(p_223189_, p_223190_, p_223191_, p_223195_, p_223197_, $$16 = p_223197_.m_227008_(p_223196_, $$14 = p_223192_ + $$9 * $$10, $$15 = p_223193_ + $$9 * $$12))) == null) continue;
                return $$17;
            }
        }
        return null;
    }

    @Nullable
    private static Pair<BlockPos, Holder<Structure>> m_223198_(Set<Holder<Structure>> p_223199_, LevelReader p_223200_, StructureManager p_223201_, boolean p_223202_, StructurePlacement p_223203_, ChunkPos p_223204_) {
        for (Holder<Structure> $$6 : p_223199_) {
            StructureCheckResult $$7 = p_223201_.m_220473_(p_223204_, $$6.m_203334_(), p_223202_);
            if ($$7 == StructureCheckResult.START_NOT_PRESENT) continue;
            if (!p_223202_ && $$7 == StructureCheckResult.START_PRESENT) {
                return Pair.of((Object)p_223203_.m_227039_(p_223204_), $$6);
            }
            ChunkAccess $$8 = p_223200_.m_46819_(p_223204_.f_45578_, p_223204_.f_45579_, ChunkStatus.f_62315_);
            StructureStart $$9 = p_223201_.m_220512_(SectionPos.m_175562_($$8), $$6.m_203334_(), $$8);
            if ($$9 == null || !$$9.m_73603_() || p_223202_ && !ChunkGenerator.m_223059_(p_223201_, $$9)) continue;
            return Pair.of((Object)p_223203_.m_227039_($$9.m_163625_()), $$6);
        }
        return null;
    }

    private static boolean m_223059_(StructureManager p_223060_, StructureStart p_223061_) {
        if (p_223061_.m_73606_()) {
            p_223060_.m_220484_(p_223061_);
            return true;
        }
        return false;
    }

    public void m_213609_(WorldGenLevel p_223087_, ChunkAccess p_223088_, StructureManager p_223089_) {
        ChunkPos $$3 = p_223088_.m_7697_();
        if (SharedConstants.m_183707_($$3)) {
            return;
        }
        SectionPos $$4 = SectionPos.m_123196_($$3, p_223087_.m_151560_());
        BlockPos $$5 = $$4.m_123249_();
        Registry<Structure> $$6 = p_223087_.m_5962_().m_175515_(Registry.f_235725_);
        Map<Integer, List<Structure>> $$7 = $$6.m_123024_().collect(Collectors.groupingBy(p_223103_ -> p_223103_.m_226619_().ordinal()));
        List<FeatureSorter.StepFeatureData> $$8 = this.f_223020_.get();
        WorldgenRandom $$9 = new WorldgenRandom(new XoroshiroRandomSource(RandomSupport.m_224599_()));
        long $$10 = $$9.m_64690_(p_223087_.m_7328_(), $$5.m_123341_(), $$5.m_123343_());
        ObjectArraySet $$11 = new ObjectArraySet();
        ChunkPos.m_45596_($$4.m_123251_(), 1).forEach(arg_0 -> ChunkGenerator.m_223090_(p_223087_, (Set)$$11, arg_0));
        $$11.retainAll(this.f_62137_.m_207840_());
        int $$12 = $$8.size();
        try {
            Registry<PlacedFeature> $$13 = p_223087_.m_5962_().m_175515_(Registry.f_194567_);
            int $$14 = Math.max(GenerationStep.Decoration.values().length, $$12);
            for (int $$15 = 0; $$15 < $$14; ++$$15) {
                int $$16 = 0;
                if (p_223089_.m_220467_()) {
                    List $$17 = $$7.getOrDefault($$15, Collections.emptyList());
                    for (Structure $$18 : $$17) {
                        $$9.m_190064_($$10, $$16, $$15);
                        Supplier<String> $$19 = () -> $$6.m_7854_($$18).map(Object::toString).orElseGet($$18::toString);
                        try {
                            p_223087_.m_143497_($$19);
                            p_223089_.m_220504_($$4, $$18).forEach(p_223086_ -> p_223086_.m_226850_(p_223087_, p_223089_, this, $$9, ChunkGenerator.m_187717_(p_223088_), $$3));
                        }
                        catch (Exception $$20) {
                            CrashReport $$21 = CrashReport.m_127521_($$20, "Feature placement");
                            $$21.m_127514_("Feature").m_128165_("Description", $$19::get);
                            throw new ReportedException($$21);
                        }
                        ++$$16;
                    }
                }
                if ($$15 >= $$12) continue;
                IntArraySet $$22 = new IntArraySet();
                for (Holder $$23 : $$11) {
                    List<HolderSet<PlacedFeature>> $$24 = this.f_223021_.apply($$23).m_47818_();
                    if ($$15 >= $$24.size()) continue;
                    HolderSet<PlacedFeature> $$25 = $$24.get($$15);
                    FeatureSorter.StepFeatureData $$26 = $$8.get($$15);
                    $$25.m_203614_().map(Holder::m_203334_).forEach(arg_0 -> ChunkGenerator.m_223171_((IntSet)$$22, $$26, arg_0));
                }
                int $$27 = $$22.size();
                int[] $$28 = $$22.toIntArray();
                Arrays.sort($$28);
                FeatureSorter.StepFeatureData $$29 = $$8.get($$15);
                for (int $$30 = 0; $$30 < $$27; ++$$30) {
                    int $$31 = $$28[$$30];
                    PlacedFeature $$32 = $$29.f_220624_().get($$31);
                    Supplier<String> $$33 = () -> $$13.m_7854_($$32).map(Object::toString).orElseGet($$32::toString);
                    $$9.m_190064_($$10, $$31, $$15);
                    try {
                        p_223087_.m_143497_($$33);
                        $$32.m_226377_(p_223087_, this, $$9, $$5);
                        continue;
                    }
                    catch (Exception $$34) {
                        CrashReport $$35 = CrashReport.m_127521_($$34, "Feature placement");
                        $$35.m_127514_("Feature").m_128165_("Description", $$33::get);
                        throw new ReportedException($$35);
                    }
                }
            }
            p_223087_.m_143497_(null);
        }
        catch (Exception $$36) {
            CrashReport $$37 = CrashReport.m_127521_($$36, "Biome decoration");
            $$37.m_127514_("Generation").m_128159_("CenterX", $$3.f_45578_).m_128159_("CenterZ", $$3.f_45579_).m_128159_("Seed", $$10);
            throw new ReportedException($$37);
        }
    }

    public boolean m_223141_(Holder<StructureSet> p_223142_, RandomState p_223143_, long p_223144_, int p_223145_, int p_223146_, int p_223147_) {
        StructureSet $$6 = p_223142_.m_203334_();
        if ($$6 == null) {
            return false;
        }
        StructurePlacement $$7 = $$6.f_210004_();
        for (int $$8 = p_223145_ - p_223147_; $$8 <= p_223145_ + p_223147_; ++$$8) {
            for (int $$9 = p_223146_ - p_223147_; $$9 <= p_223146_ + p_223147_; ++$$9) {
                if (!$$7.m_227054_(this, p_223143_, p_223144_, $$8, $$9)) continue;
                return true;
            }
        }
        return false;
    }

    private static BoundingBox m_187717_(ChunkAccess p_187718_) {
        ChunkPos $$1 = p_187718_.m_7697_();
        int $$2 = $$1.m_45604_();
        int $$3 = $$1.m_45605_();
        LevelHeightAccessor $$4 = p_187718_.m_183618_();
        int $$5 = $$4.m_141937_() + 1;
        int $$6 = $$4.m_151558_() - 1;
        return new BoundingBox($$2, $$5, $$3, $$2 + 15, $$6, $$3 + 15);
    }

    public abstract void m_214194_(WorldGenRegion var1, StructureManager var2, RandomState var3, ChunkAccess var4);

    public abstract void m_6929_(WorldGenRegion var1);

    public int m_142051_(LevelHeightAccessor p_156157_) {
        return 64;
    }

    public BiomeSource m_62218_() {
        return this.f_62137_;
    }

    public abstract int m_6331_();

    public WeightedRandomList<MobSpawnSettings.SpawnerData> m_223133_(Holder<Biome> p_223134_, StructureManager p_223135_, MobCategory p_223136_, BlockPos p_223137_) {
        Map<Structure, LongSet> $$4 = p_223135_.m_220522_(p_223137_);
        for (Map.Entry<Structure, LongSet> $$5 : $$4.entrySet()) {
            Structure $$6 = $$5.getKey();
            StructureSpawnOverride $$7 = $$6.m_226612_().get(p_223136_);
            if ($$7 == null) continue;
            MutableBoolean $$8 = new MutableBoolean(false);
            Predicate<StructureStart> $$9 = $$7.f_210043_() == StructureSpawnOverride.BoundingBoxType.PIECE ? p_223065_ -> p_223135_.m_220497_(p_223137_, (StructureStart)p_223065_) : p_223130_ -> p_223130_.m_73601_().m_71051_(p_223137_);
            p_223135_.m_220480_($$6, $$5.getValue(), p_223220_ -> {
                if ($$8.isFalse() && $$9.test((StructureStart)p_223220_)) {
                    $$8.setTrue();
                }
            });
            if (!$$8.isTrue()) continue;
            return $$7.f_210044_();
        }
        return p_223134_.m_203334_().m_47518_().m_151798_(p_223136_);
    }

    public void m_223164_(RegistryAccess p_223165_, RandomState p_223166_, StructureManager p_223167_, ChunkAccess p_223168_, StructureTemplateManager p_223169_, long p_223170_) {
        ChunkPos $$6 = p_223168_.m_7697_();
        SectionPos $$7 = SectionPos.m_175562_(p_223168_);
        this.m_207969_().forEach(p_223075_ -> {
            StructurePlacement $$9 = ((StructureSet)p_223075_.m_203334_()).f_210004_();
            List<StructureSet.StructureSelectionEntry> $$10 = ((StructureSet)p_223075_.m_203334_()).f_210003_();
            for (StructureSet.StructureSelectionEntry $$11 : $$10) {
                StructureStart $$12 = p_223167_.m_220512_($$7, $$11.f_210026_().m_203334_(), p_223168_);
                if ($$12 == null || !$$12.m_73603_()) continue;
                return;
            }
            if (!$$9.m_227054_(this, p_223166_, p_223170_, p_223072_.f_45578_, p_223072_.f_45579_)) {
                return;
            }
            if ($$10.size() == 1) {
                this.m_223104_($$10.get(0), p_223167_, p_223165_, p_223166_, p_223169_, p_223170_, p_223168_, $$6, $$7);
                return;
            }
            ArrayList<StructureSet.StructureSelectionEntry> $$13 = new ArrayList<StructureSet.StructureSelectionEntry>($$10.size());
            $$13.addAll($$10);
            WorldgenRandom $$14 = new WorldgenRandom(new LegacyRandomSource(0L));
            $$14.m_190068_(p_223170_, p_223072_.f_45578_, p_223072_.f_45579_);
            int $$15 = 0;
            for (StructureSet.StructureSelectionEntry $$16 : $$13) {
                $$15 += $$16.f_210027_();
            }
            while (!$$13.isEmpty()) {
                StructureSet.StructureSelectionEntry $$19;
                int $$17 = $$14.m_188503_($$15);
                int $$18 = 0;
                Iterator iterator = $$13.iterator();
                while (iterator.hasNext() && ($$17 -= ($$19 = (StructureSet.StructureSelectionEntry)iterator.next()).f_210027_()) >= 0) {
                    ++$$18;
                }
                StructureSet.StructureSelectionEntry $$20 = (StructureSet.StructureSelectionEntry)$$13.get($$18);
                if (this.m_223104_($$20, p_223167_, p_223165_, p_223166_, p_223169_, p_223170_, p_223168_, $$6, $$7)) {
                    return;
                }
                $$13.remove($$18);
                $$15 -= $$20.f_210027_();
            }
        });
    }

    private boolean m_223104_(StructureSet.StructureSelectionEntry p_223105_, StructureManager p_223106_, RegistryAccess p_223107_, RandomState p_223108_, StructureTemplateManager p_223109_, long p_223110_, ChunkAccess p_223111_, ChunkPos p_223112_, SectionPos p_223113_) {
        Structure $$9 = p_223105_.f_210026_().m_203334_();
        int $$10 = ChunkGenerator.m_223054_(p_223106_, p_223111_, p_223113_, $$9);
        HolderSet<Biome> $$11 = $$9.m_226559_();
        Predicate<Holder<Biome>> $$12 = $$11::m_203333_;
        StructureStart $$13 = $$9.m_226596_(p_223107_, this, this.f_62137_, p_223108_, p_223109_, p_223110_, p_223112_, $$10, p_223111_, $$12);
        if ($$13.m_73603_()) {
            p_223106_.m_220516_(p_223113_, $$9, $$13, p_223111_);
            return true;
        }
        return false;
    }

    private static int m_223054_(StructureManager p_223055_, ChunkAccess p_223056_, SectionPos p_223057_, Structure p_223058_) {
        StructureStart $$4 = p_223055_.m_220512_(p_223057_, p_223058_, p_223056_);
        return $$4 != null ? $$4.m_73608_() : 0;
    }

    public void m_223076_(WorldGenLevel p_223077_, StructureManager p_223078_, ChunkAccess p_223079_) {
        int $$3 = 8;
        ChunkPos $$4 = p_223079_.m_7697_();
        int $$5 = $$4.f_45578_;
        int $$6 = $$4.f_45579_;
        int $$7 = $$4.m_45604_();
        int $$8 = $$4.m_45605_();
        SectionPos $$9 = SectionPos.m_175562_(p_223079_);
        for (int $$10 = $$5 - 8; $$10 <= $$5 + 8; ++$$10) {
            for (int $$11 = $$6 - 8; $$11 <= $$6 + 8; ++$$11) {
                long $$12 = ChunkPos.m_45589_($$10, $$11);
                for (StructureStart $$13 : p_223077_.m_6325_($$10, $$11).m_6633_().values()) {
                    try {
                        if (!$$13.m_73603_() || !$$13.m_73601_().m_71019_($$7, $$8, $$7 + 15, $$8 + 15)) continue;
                        p_223078_.m_220507_($$9, $$13.m_226861_(), $$12, p_223079_);
                        DebugPackets.m_133711_(p_223077_, $$13);
                    }
                    catch (Exception $$14) {
                        CrashReport $$15 = CrashReport.m_127521_($$14, "Generating structure reference");
                        CrashReportCategory $$16 = $$15.m_127514_("Structure");
                        Optional<Registry<Structure>> $$17 = p_223077_.m_5962_().m_6632_(Registry.f_235725_);
                        $$16.m_128165_("Id", () -> $$17.map(p_223118_ -> p_223118_.m_7981_($$13.m_226861_()).toString()).orElse("UNKNOWN"));
                        $$16.m_128165_("Name", () -> Registry.f_235740_.m_7981_($$13.m_226861_().m_213658_()).toString());
                        $$16.m_128165_("Class", () -> $$13.m_226861_().getClass().getCanonicalName());
                        throw new ReportedException($$15);
                    }
                }
            }
        }
    }

    public abstract CompletableFuture<ChunkAccess> m_213974_(Executor var1, Blender var2, RandomState var3, StructureManager var4, ChunkAccess var5);

    public abstract int m_6337_();

    public abstract int m_142062_();

    public abstract int m_214096_(int var1, int var2, Heightmap.Types var3, LevelHeightAccessor var4, RandomState var5);

    public abstract NoiseColumn m_214184_(int var1, int var2, LevelHeightAccessor var3, RandomState var4);

    public int m_223221_(int p_223222_, int p_223223_, Heightmap.Types p_223224_, LevelHeightAccessor p_223225_, RandomState p_223226_) {
        return this.m_214096_(p_223222_, p_223223_, p_223224_, p_223225_, p_223226_);
    }

    public int m_223235_(int p_223236_, int p_223237_, Heightmap.Types p_223238_, LevelHeightAccessor p_223239_, RandomState p_223240_) {
        return this.m_214096_(p_223236_, p_223237_, p_223238_, p_223239_, p_223240_) - 1;
    }

    public void m_223100_(RandomState p_223101_) {
        if (!this.f_207958_) {
            this.m_223227_(p_223101_);
            this.f_207958_ = true;
        }
    }

    @Nullable
    public List<ChunkPos> m_223119_(ConcentricRingsStructurePlacement p_223120_, RandomState p_223121_) {
        this.m_223100_(p_223121_);
        CompletableFuture<List<ChunkPos>> $$2 = this.f_204350_.get(p_223120_);
        return $$2 != null ? $$2.join() : null;
    }

    private List<StructurePlacement> m_223138_(Holder<Structure> p_223139_, RandomState p_223140_) {
        this.m_223100_(p_223140_);
        return this.f_223022_.getOrDefault(p_223139_.m_203334_(), List.of());
    }

    public abstract void m_213600_(List<String> var1, RandomState var2, BlockPos var3);

    @Deprecated
    public BiomeGenerationSettings m_223131_(Holder<Biome> p_223132_) {
        return this.f_223021_.apply(p_223132_);
    }

    private static /* synthetic */ void m_223171_(IntSet p_223172_, FeatureSorter.StepFeatureData p_223173_, PlacedFeature p_223174_) {
        p_223172_.add(p_223173_.f_220625_().applyAsInt(p_223174_));
    }

    private static /* synthetic */ void m_223090_(WorldGenLevel p_223091_, Set p_223092_, ChunkPos p_223093_) {
        ChunkAccess $$3 = p_223091_.m_6325_(p_223093_.f_45578_, p_223093_.f_45579_);
        for (LevelChunkSection $$4 : $$3.m_7103_()) {
            $$4.m_187996_().m_196879_(p_223092_::add);
        }
    }
}

