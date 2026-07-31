/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package net.minecraft.world.level.levelgen;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.BelowZeroRetrogen;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.apache.commons.lang3.mutable.MutableObject;

public final class NoiseBasedChunkGenerator
extends ChunkGenerator {
    public static final Codec<NoiseBasedChunkGenerator> f_64314_ = RecordCodecBuilder.create(p_224323_ -> NoiseBasedChunkGenerator.m_208005_(p_224323_).and(p_224323_.group((App)RegistryOps.m_206832_(Registry.f_194568_).forGetter(p_188716_ -> p_188716_.f_188604_), (App)BiomeSource.f_47888_.fieldOf("biome_source").forGetter(p_188711_ -> p_188711_.f_62137_), (App)NoiseGeneratorSettings.f_64431_.fieldOf("settings").forGetter(p_224278_ -> p_224278_.f_64318_))).apply((Applicative)p_224323_, p_224323_.stable(NoiseBasedChunkGenerator::new)));
    private static final BlockState f_64321_ = Blocks.f_50016_.m_49966_();
    protected final BlockState f_64316_;
    private final Registry<NormalNoise.NoiseParameters> f_188604_;
    protected final Holder<NoiseGeneratorSettings> f_64318_;
    private final Aquifer.FluidPicker f_188607_;

    public NoiseBasedChunkGenerator(Registry<StructureSet> p_224206_, Registry<NormalNoise.NoiseParameters> p_224207_, BiomeSource p_224208_, Holder<NoiseGeneratorSettings> p_224209_) {
        super(p_224206_, Optional.empty(), p_224208_);
        this.f_188604_ = p_224207_;
        this.f_64318_ = p_224209_;
        NoiseGeneratorSettings $$4 = this.f_64318_.m_203334_();
        this.f_64316_ = $$4.f_64440_();
        Aquifer.FluidStatus $$5 = new Aquifer.FluidStatus(-54, Blocks.f_49991_.m_49966_());
        int $$6 = $$4.f_64444_();
        Aquifer.FluidStatus $$7 = new Aquifer.FluidStatus($$6, $$4.f_64441_());
        Aquifer.FluidStatus $$8 = new Aquifer.FluidStatus(DimensionType.f_156653_ * 2, Blocks.f_50016_.m_49966_());
        this.f_188607_ = (p_224274_, p_224275_, p_224276_) -> {
            if (p_224275_ < Math.min(-54, $$6)) {
                return $$5;
            }
            return $$7;
        };
    }

    @Override
    public CompletableFuture<ChunkAccess> m_213908_(Registry<Biome> p_224297_, Executor p_224298_, RandomState p_224299_, Blender p_224300_, StructureManager p_224301_, ChunkAccess p_224302_) {
        return CompletableFuture.supplyAsync(Util.m_183946_("init_biomes", () -> {
            this.m_224291_(p_224300_, p_224299_, p_224301_, p_224302_);
            return p_224302_;
        }), Util.m_183991_());
    }

    private void m_224291_(Blender p_224292_, RandomState p_224293_, StructureManager p_224294_, ChunkAccess p_224295_) {
        NoiseChunk $$4 = p_224295_.m_223012_(p_224340_ -> this.m_224256_((ChunkAccess)p_224340_, p_224294_, p_224292_, p_224293_));
        BiomeResolver $$5 = BelowZeroRetrogen.m_204531_(p_224292_.m_183383_(this.f_62137_), p_224295_);
        p_224295_.m_183442_($$5, $$4.m_224359_(p_224293_.m_224578_(), this.f_64318_.m_203334_().f_224370_()));
    }

    private NoiseChunk m_224256_(ChunkAccess p_224257_, StructureManager p_224258_, Blender p_224259_, RandomState p_224260_) {
        return NoiseChunk.m_224352_(p_224257_, p_224260_, Beardifier.m_223937_(p_224258_, p_224257_.m_7697_()), this.f_64318_.m_203334_(), this.f_188607_, p_224259_);
    }

    @Override
    protected Codec<? extends ChunkGenerator> m_6909_() {
        return f_64314_;
    }

    public Holder<NoiseGeneratorSettings> m_224341_() {
        return this.f_64318_;
    }

    public boolean m_224221_(ResourceKey<NoiseGeneratorSettings> p_224222_) {
        return this.f_64318_.m_203565_(p_224222_);
    }

    @Override
    public int m_214096_(int p_224216_, int p_224217_, Heightmap.Types p_224218_, LevelHeightAccessor p_224219_, RandomState p_224220_) {
        return this.m_224239_(p_224219_, p_224220_, p_224216_, p_224217_, null, p_224218_.m_64299_()).orElse(p_224219_.m_141937_());
    }

    @Override
    public NoiseColumn m_214184_(int p_224211_, int p_224212_, LevelHeightAccessor p_224213_, RandomState p_224214_) {
        MutableObject $$4 = new MutableObject();
        this.m_224239_(p_224213_, p_224214_, p_224211_, p_224212_, (MutableObject<NoiseColumn>)$$4, null);
        return (NoiseColumn)$$4.getValue();
    }

    @Override
    public void m_213600_(List<String> p_224304_, RandomState p_224305_, BlockPos p_224306_) {
        DecimalFormat $$3 = new DecimalFormat("0.000");
        NoiseRouter $$4 = p_224305_.m_224578_();
        DensityFunction.SinglePointContext $$5 = new DensityFunction.SinglePointContext(p_224306_.m_123341_(), p_224306_.m_123342_(), p_224306_.m_123343_());
        double $$6 = $$4.f_209389_().m_207386_($$5);
        p_224304_.add("NoiseRouter T: " + $$3.format($$4.f_209384_().m_207386_($$5)) + " V: " + $$3.format($$4.f_224392_().m_207386_($$5)) + " C: " + $$3.format($$4.f_209386_().m_207386_($$5)) + " E: " + $$3.format($$4.f_209387_().m_207386_($$5)) + " D: " + $$3.format($$4.f_209388_().m_207386_($$5)) + " W: " + $$3.format($$6) + " PV: " + $$3.format(NoiseRouterData.m_224435_((float)$$6)) + " AS: " + $$3.format($$4.f_209390_().m_207386_($$5)) + " N: " + $$3.format($$4.f_209391_().m_207386_($$5)));
    }

    private OptionalInt m_224239_(LevelHeightAccessor p_224240_, RandomState p_224241_, int p_224242_, int p_224243_, @Nullable MutableObject<NoiseColumn> p_224244_, @Nullable Predicate<BlockState> p_224245_) {
        BlockState[] $$12;
        NoiseSettings $$6 = this.f_64318_.m_203334_().f_64439_().m_224530_(p_224240_);
        int $$7 = $$6.m_189212_();
        int $$8 = $$6.f_158688_();
        int $$9 = Mth.m_14042_($$8, $$7);
        int $$10 = Mth.m_14042_($$6.f_64508_(), $$7);
        if ($$10 <= 0) {
            return OptionalInt.empty();
        }
        if (p_224244_ == null) {
            Object $$11 = null;
        } else {
            $$12 = new BlockState[$$6.f_64508_()];
            p_224244_.setValue((Object)new NoiseColumn($$8, $$12));
        }
        int $$13 = $$6.m_189213_();
        int $$14 = Math.floorDiv(p_224242_, $$13);
        int $$15 = Math.floorDiv(p_224243_, $$13);
        int $$16 = Math.floorMod(p_224242_, $$13);
        int $$17 = Math.floorMod(p_224243_, $$13);
        int $$18 = $$14 * $$13;
        int $$19 = $$15 * $$13;
        double $$20 = (double)$$16 / (double)$$13;
        double $$21 = (double)$$17 / (double)$$13;
        NoiseChunk $$22 = new NoiseChunk(1, p_224241_, $$18, $$19, $$6, DensityFunctions.BeardifierMarker.INSTANCE, this.f_64318_.m_203334_(), this.f_188607_, Blender.m_190153_());
        $$22.m_188791_();
        $$22.m_188749_(0);
        for (int $$23 = $$10 - 1; $$23 >= 0; --$$23) {
            $$22.m_188810_($$23, 0);
            for (int $$24 = $$7 - 1; $$24 >= 0; --$$24) {
                BlockState $$28;
                int $$25 = ($$9 + $$23) * $$7 + $$24;
                double $$26 = (double)$$24 / (double)$$7;
                $$22.m_209191_($$25, $$26);
                $$22.m_209230_(p_224242_, $$20);
                $$22.m_209241_(p_224243_, $$21);
                BlockState $$27 = $$22.m_209247_();
                BlockState blockState = $$28 = $$27 == null ? this.f_64316_ : $$27;
                if ($$12 != null) {
                    int $$29 = $$23 * $$7 + $$24;
                    $$12[$$29] = $$28;
                }
                if (p_224245_ == null || !p_224245_.test($$28)) continue;
                $$22.m_209248_();
                return OptionalInt.of($$25 + 1);
            }
        }
        $$22.m_209248_();
        return OptionalInt.empty();
    }

    @Override
    public void m_214194_(WorldGenRegion p_224232_, StructureManager p_224233_, RandomState p_224234_, ChunkAccess p_224235_) {
        if (SharedConstants.m_183707_(p_224235_.m_7697_())) {
            return;
        }
        WorldGenerationContext $$4 = new WorldGenerationContext(this, p_224232_);
        this.m_224261_(p_224235_, $$4, p_224234_, p_224233_, p_224232_.m_7062_(), p_224232_.m_5962_().m_175515_(Registry.f_122885_), Blender.m_190202_(p_224232_));
    }

    @VisibleForTesting
    public void m_224261_(ChunkAccess p_224262_, WorldGenerationContext p_224263_, RandomState p_224264_, StructureManager p_224265_, BiomeManager p_224266_, Registry<Biome> p_224267_, Blender p_224268_) {
        NoiseChunk $$7 = p_224262_.m_223012_(p_224321_ -> this.m_224256_((ChunkAccess)p_224321_, p_224265_, p_224268_, p_224264_));
        NoiseGeneratorSettings $$8 = this.f_64318_.m_203334_();
        p_224264_.m_224580_().m_224648_(p_224264_, p_224266_, p_224267_, $$8.f_209354_(), p_224263_, p_224262_, $$7, $$8.f_188871_());
    }

    @Override
    public void m_213679_(WorldGenRegion p_224224_, long p_224225_, RandomState p_224226_, BiomeManager p_224227_, StructureManager p_224228_, ChunkAccess p_224229_, GenerationStep.Carving p_224230_) {
        BiomeManager $$7 = p_224227_.m_186687_((p_224281_, p_224282_, p_224283_) -> this.f_62137_.m_203407_(p_224281_, p_224282_, p_224283_, p_224226_.m_224579_()));
        WorldgenRandom $$8 = new WorldgenRandom(new LegacyRandomSource(RandomSupport.m_224599_()));
        int $$9 = 8;
        ChunkPos $$10 = p_224229_.m_7697_();
        NoiseChunk $$11 = p_224229_.m_223012_(p_224250_ -> this.m_224256_((ChunkAccess)p_224250_, p_224228_, Blender.m_190202_(p_224224_), p_224226_));
        Aquifer $$12 = $$11.m_188817_();
        CarvingContext $$13 = new CarvingContext(this, p_224224_.m_5962_(), p_224229_.m_183618_(), $$11, p_224226_, this.f_64318_.m_203334_().f_188871_());
        CarvingMask $$14 = ((ProtoChunk)p_224229_).m_183613_(p_224230_);
        for (int $$15 = -8; $$15 <= 8; ++$$15) {
            for (int $$16 = -8; $$16 <= 8; ++$$16) {
                ChunkPos $$17 = new ChunkPos($$10.f_45578_ + $$15, $$10.f_45579_ + $$16);
                ChunkAccess $$18 = p_224224_.m_6325_($$17.f_45578_, $$17.f_45579_);
                BiomeGenerationSettings $$19 = $$18.m_223014_(() -> this.m_223131_(this.f_62137_.m_203407_(QuartPos.m_175400_($$17.m_45604_()), 0, QuartPos.m_175400_($$17.m_45605_()), p_224226_.m_224579_())));
                Iterable<Holder<ConfiguredWorldCarver<?>>> $$20 = $$19.m_204187_(p_224230_);
                int $$21 = 0;
                for (Holder<ConfiguredWorldCarver<?>> $$22 : $$20) {
                    ConfiguredWorldCarver<?> $$23 = $$22.m_203334_();
                    $$8.m_190068_(p_224225_ + (long)$$21, $$17.f_45578_, $$17.f_45579_);
                    if ($$23.m_224896_($$8)) {
                        $$23.m_224898_($$13, p_224229_, $$7::m_204214_, $$8, $$12, $$17, $$14);
                    }
                    ++$$21;
                }
            }
        }
    }

    @Override
    public CompletableFuture<ChunkAccess> m_213974_(Executor p_224312_, Blender p_224313_, RandomState p_224314_, StructureManager p_224315_, ChunkAccess p_224316_) {
        NoiseSettings $$5 = this.f_64318_.m_203334_().f_64439_().m_224530_(p_224316_.m_183618_());
        int $$6 = $$5.f_158688_();
        int $$7 = Mth.m_14042_($$6, $$5.m_189212_());
        int $$8 = Mth.m_14042_($$5.f_64508_(), $$5.m_189212_());
        if ($$8 <= 0) {
            return CompletableFuture.completedFuture(p_224316_);
        }
        int $$9 = p_224316_.m_151564_($$8 * $$5.m_189212_() - 1 + $$6);
        int $$10 = p_224316_.m_151564_($$6);
        HashSet $$11 = Sets.newHashSet();
        for (int $$12 = $$9; $$12 >= $$10; --$$12) {
            LevelChunkSection $$13 = p_224316_.m_183278_($$12);
            $$13.m_62981_();
            $$11.add($$13);
        }
        return CompletableFuture.supplyAsync(Util.m_183946_("wgen_fill_noise", () -> this.m_224284_(p_224313_, p_224315_, p_224314_, p_224316_, $$7, $$8)), Util.m_183991_()).whenCompleteAsync((p_224309_, p_224310_) -> {
            for (LevelChunkSection $$3 : $$11) {
                $$3.m_63006_();
            }
        }, p_224312_);
    }

    private ChunkAccess m_224284_(Blender p_224285_, StructureManager p_224286_, RandomState p_224287_, ChunkAccess p_224288_, int p_224289_, int p_224290_) {
        NoiseChunk $$6 = p_224288_.m_223012_(p_224255_ -> this.m_224256_((ChunkAccess)p_224255_, p_224286_, p_224285_, p_224287_));
        Heightmap $$7 = p_224288_.m_6005_(Heightmap.Types.OCEAN_FLOOR_WG);
        Heightmap $$8 = p_224288_.m_6005_(Heightmap.Types.WORLD_SURFACE_WG);
        ChunkPos $$9 = p_224288_.m_7697_();
        int $$10 = $$9.m_45604_();
        int $$11 = $$9.m_45605_();
        Aquifer $$12 = $$6.m_188817_();
        $$6.m_188791_();
        BlockPos.MutableBlockPos $$13 = new BlockPos.MutableBlockPos();
        int $$14 = $$6.m_224362_();
        int $$15 = $$6.m_224363_();
        int $$16 = 16 / $$14;
        int $$17 = 16 / $$14;
        for (int $$18 = 0; $$18 < $$16; ++$$18) {
            $$6.m_188749_($$18);
            for (int $$19 = 0; $$19 < $$17; ++$$19) {
                LevelChunkSection $$20 = p_224288_.m_183278_(p_224288_.m_151559_() - 1);
                for (int $$21 = p_224290_ - 1; $$21 >= 0; --$$21) {
                    $$6.m_188810_($$21, $$19);
                    for (int $$22 = $$15 - 1; $$22 >= 0; --$$22) {
                        int $$23 = (p_224289_ + $$21) * $$15 + $$22;
                        int $$24 = $$23 & 0xF;
                        int $$25 = p_224288_.m_151564_($$23);
                        if (p_224288_.m_151564_($$20.m_63017_()) != $$25) {
                            $$20 = p_224288_.m_183278_($$25);
                        }
                        double $$26 = (double)$$22 / (double)$$15;
                        $$6.m_209191_($$23, $$26);
                        for (int $$27 = 0; $$27 < $$14; ++$$27) {
                            int $$28 = $$10 + $$18 * $$14 + $$27;
                            int $$29 = $$28 & 0xF;
                            double $$30 = (double)$$27 / (double)$$14;
                            $$6.m_209230_($$28, $$30);
                            for (int $$31 = 0; $$31 < $$14; ++$$31) {
                                int $$32 = $$11 + $$19 * $$14 + $$31;
                                int $$33 = $$32 & 0xF;
                                double $$34 = (double)$$31 / (double)$$14;
                                $$6.m_209241_($$32, $$34);
                                BlockState $$35 = $$6.m_209247_();
                                if ($$35 == null) {
                                    $$35 = this.f_64316_;
                                }
                                if (($$35 = this.m_198231_($$6, $$28, $$23, $$32, $$35)) == f_64321_ || SharedConstants.m_183707_(p_224288_.m_7697_())) continue;
                                if ($$35.m_60791_() != 0 && p_224288_ instanceof ProtoChunk) {
                                    $$13.m_122178_($$28, $$23, $$32);
                                    ((ProtoChunk)p_224288_).m_63277_($$13);
                                }
                                $$20.m_62991_($$29, $$24, $$33, $$35, false);
                                $$7.m_64249_($$29, $$23, $$33, $$35);
                                $$8.m_64249_($$29, $$23, $$33, $$35);
                                if (!$$12.m_142203_() || $$35.m_60819_().m_76178_()) continue;
                                $$13.m_122178_($$28, $$23, $$32);
                                p_224288_.m_8113_($$13);
                            }
                        }
                    }
                }
            }
            $$6.m_188804_();
        }
        $$6.m_209248_();
        return p_224288_;
    }

    private BlockState m_198231_(NoiseChunk p_198232_, int p_198233_, int p_198234_, int p_198235_, BlockState p_198236_) {
        return p_198236_;
    }

    @Override
    public int m_6331_() {
        return this.f_64318_.m_203334_().f_64439_().f_64508_();
    }

    @Override
    public int m_6337_() {
        return this.f_64318_.m_203334_().f_64444_();
    }

    @Override
    public int m_142062_() {
        return this.f_64318_.m_203334_().f_64439_().f_158688_();
    }

    @Override
    public void m_6929_(WorldGenRegion p_64379_) {
        if (this.f_64318_.m_203334_().f_64445_()) {
            return;
        }
        ChunkPos $$1 = p_64379_.m_143488_();
        Holder<Biome> $$2 = p_64379_.m_204166_($$1.m_45615_().m_175288_(p_64379_.m_151558_() - 1));
        WorldgenRandom $$3 = new WorldgenRandom(new LegacyRandomSource(RandomSupport.m_224599_()));
        $$3.m_64690_(p_64379_.m_7328_(), $$1.m_45604_(), $$1.m_45605_());
        NaturalSpawner.m_220450_(p_64379_, $$2, $$1, $$3);
    }
}

