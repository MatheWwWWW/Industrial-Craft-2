/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Either
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Keyable
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 */
package net.minecraft.world.level.levelgen.structure;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public abstract class Structure {
    public static final Codec<Structure> f_226553_ = Registry.f_235740_.m_194605_().dispatch(Structure::m_213658_, StructureType::m_226884_);
    public static final Codec<Holder<Structure>> f_226554_ = RegistryFileCodec.m_135589_(Registry.f_235725_, f_226553_);
    protected final StructureSettings f_226555_;

    public static <S extends Structure> RecordCodecBuilder<S, StructureSettings> m_226567_(RecordCodecBuilder.Instance<S> p_226568_) {
        return StructureSettings.f_226688_.forGetter(p_226595_ -> p_226595_.f_226555_);
    }

    public static <S extends Structure> Codec<S> m_226607_(Function<StructureSettings, S> p_226608_) {
        return RecordCodecBuilder.create(p_226611_ -> p_226611_.group(Structure.m_226567_(p_226611_)).apply((Applicative)p_226611_, p_226608_));
    }

    protected Structure(StructureSettings p_226558_) {
        this.f_226555_ = p_226558_;
    }

    public HolderSet<Biome> m_226559_() {
        return this.f_226555_.f_226689_;
    }

    public Map<MobCategory, StructureSpawnOverride> m_226612_() {
        return this.f_226555_.f_226690_;
    }

    public GenerationStep.Decoration m_226619_() {
        return this.f_226555_.f_226691_;
    }

    public TerrainAdjustment m_226620_() {
        return this.f_226555_.f_226692_;
    }

    public BoundingBox m_226569_(BoundingBox p_226570_) {
        if (this.m_226620_() != TerrainAdjustment.NONE) {
            return p_226570_.m_191961_(12);
        }
        return p_226570_;
    }

    public StructureStart m_226596_(RegistryAccess p_226597_, ChunkGenerator p_226598_, BiomeSource p_226599_, RandomState p_226600_, StructureTemplateManager p_226601_, long p_226602_, ChunkPos p_226603_, int p_226604_, LevelHeightAccessor p_226605_, Predicate<Holder<Biome>> p_226606_) {
        StructurePiecesBuilder $$11;
        StructureStart $$12;
        Optional<GenerationStub> $$10 = this.m_214086_(new GenerationContext(p_226597_, p_226598_, p_226599_, p_226600_, p_226601_, p_226602_, p_226603_, p_226605_, p_226606_));
        if ($$10.isPresent() && Structure.m_226589_($$10.get(), p_226598_, p_226600_, p_226606_) && ($$12 = new StructureStart(this, p_226603_, p_226604_, ($$11 = $$10.get().m_226677_()).m_192780_())).m_73603_()) {
            return $$12;
        }
        return StructureStart.f_73561_;
    }

    protected static Optional<GenerationStub> m_226585_(GenerationContext p_226586_, Heightmap.Types p_226587_, Consumer<StructurePiecesBuilder> p_226588_) {
        ChunkPos $$3 = p_226586_.f_226628_();
        int $$4 = $$3.m_151390_();
        int $$5 = $$3.m_151393_();
        int $$6 = p_226586_.f_226622_().m_223235_($$4, $$5, p_226587_, p_226586_.f_226629_(), p_226586_.f_226624_());
        return Optional.of(new GenerationStub(new BlockPos($$4, $$6, $$5), p_226588_));
    }

    private static boolean m_226589_(GenerationStub p_226590_, ChunkGenerator p_226591_, RandomState p_226592_, Predicate<Holder<Biome>> p_226593_) {
        BlockPos $$4 = p_226590_.f_226669_();
        return p_226593_.test(p_226591_.m_62218_().m_203407_(QuartPos.m_175400_($$4.m_123341_()), QuartPos.m_175400_($$4.m_123342_()), QuartPos.m_175400_($$4.m_123343_()), p_226592_.m_224579_()));
    }

    public void m_214110_(WorldGenLevel p_226560_, StructureManager p_226561_, ChunkGenerator p_226562_, RandomSource p_226563_, BoundingBox p_226564_, ChunkPos p_226565_, PiecesContainer p_226566_) {
    }

    private static int[] m_226613_(GenerationContext p_226614_, int p_226615_, int p_226616_, int p_226617_, int p_226618_) {
        ChunkGenerator $$5 = p_226614_.f_226622_();
        LevelHeightAccessor $$6 = p_226614_.f_226629_();
        RandomState $$7 = p_226614_.f_226624_();
        return new int[]{$$5.m_223235_(p_226615_, p_226617_, Heightmap.Types.WORLD_SURFACE_WG, $$6, $$7), $$5.m_223235_(p_226615_, p_226617_ + p_226618_, Heightmap.Types.WORLD_SURFACE_WG, $$6, $$7), $$5.m_223235_(p_226615_ + p_226616_, p_226617_, Heightmap.Types.WORLD_SURFACE_WG, $$6, $$7), $$5.m_223235_(p_226615_ + p_226616_, p_226617_ + p_226618_, Heightmap.Types.WORLD_SURFACE_WG, $$6, $$7)};
    }

    protected static int m_226572_(GenerationContext p_226573_, int p_226574_, int p_226575_) {
        ChunkPos $$3 = p_226573_.f_226628_();
        int $$4 = $$3.m_45604_();
        int $$5 = $$3.m_45605_();
        return Structure.m_226576_(p_226573_, $$4, $$5, p_226574_, p_226575_);
    }

    protected static int m_226576_(GenerationContext p_226577_, int p_226578_, int p_226579_, int p_226580_, int p_226581_) {
        int[] $$5 = Structure.m_226613_(p_226577_, p_226578_, p_226580_, p_226579_, p_226581_);
        return Math.min(Math.min($$5[0], $$5[1]), Math.min($$5[2], $$5[3]));
    }

    @Deprecated
    protected BlockPos m_226582_(GenerationContext p_226583_, Rotation p_226584_) {
        int $$2 = 5;
        int $$3 = 5;
        if (p_226584_ == Rotation.CLOCKWISE_90) {
            $$2 = -5;
        } else if (p_226584_ == Rotation.CLOCKWISE_180) {
            $$2 = -5;
            $$3 = -5;
        } else if (p_226584_ == Rotation.COUNTERCLOCKWISE_90) {
            $$3 = -5;
        }
        ChunkPos $$4 = p_226583_.f_226628_();
        int $$5 = $$4.m_151382_(7);
        int $$6 = $$4.m_151391_(7);
        return new BlockPos($$5, Structure.m_226576_(p_226583_, $$5, $$6, $$2, $$3), $$6);
    }

    public abstract Optional<GenerationStub> m_214086_(GenerationContext var1);

    public abstract StructureType<?> m_213658_();

    public record StructureSettings(HolderSet<Biome> f_226689_, Map<MobCategory, StructureSpawnOverride> f_226690_, GenerationStep.Decoration f_226691_, TerrainAdjustment f_226692_) {
        public static final MapCodec<StructureSettings> f_226688_ = RecordCodecBuilder.mapCodec(p_226701_ -> p_226701_.group((App)RegistryCodecs.m_206277_(Registry.f_122885_).fieldOf("biomes").forGetter(StructureSettings::f_226689_), (App)Codec.simpleMap(MobCategory.f_21584_, StructureSpawnOverride.f_210042_, (Keyable)StringRepresentable.m_14357_(MobCategory.values())).fieldOf("spawn_overrides").forGetter(StructureSettings::f_226690_), (App)GenerationStep.Decoration.f_224188_.fieldOf("step").forGetter(StructureSettings::f_226691_), (App)TerrainAdjustment.f_226918_.optionalFieldOf("terrain_adaptation", (Object)TerrainAdjustment.NONE).forGetter(StructureSettings::f_226692_)).apply((Applicative)p_226701_, StructureSettings::new));

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{StructureSettings.class, "biomes;spawnOverrides;step;terrainAdaptation", "f_226689_", "f_226690_", "f_226691_", "f_226692_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{StructureSettings.class, "biomes;spawnOverrides;step;terrainAdaptation", "f_226689_", "f_226690_", "f_226691_", "f_226692_"}, this);
        }

        @Override
        public final boolean equals(Object p_226706_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{StructureSettings.class, "biomes;spawnOverrides;step;terrainAdaptation", "f_226689_", "f_226690_", "f_226691_", "f_226692_"}, this, p_226706_);
        }
    }

    public record GenerationContext(RegistryAccess f_226621_, ChunkGenerator f_226622_, BiomeSource f_226623_, RandomState f_226624_, StructureTemplateManager f_226625_, WorldgenRandom f_226626_, long f_226627_, ChunkPos f_226628_, LevelHeightAccessor f_226629_, Predicate<Holder<Biome>> f_226630_) {
        public GenerationContext(RegistryAccess p_226632_, ChunkGenerator p_226633_, BiomeSource p_226634_, RandomState p_226635_, StructureTemplateManager p_226636_, long p_226637_, ChunkPos p_226638_, LevelHeightAccessor p_226639_, Predicate<Holder<Biome>> p_226640_) {
            this(p_226632_, p_226633_, p_226634_, p_226635_, p_226636_, GenerationContext.m_226653_(p_226637_, p_226638_), p_226637_, p_226638_, p_226639_, p_226640_);
        }

        private static WorldgenRandom m_226653_(long p_226654_, ChunkPos p_226655_) {
            WorldgenRandom $$2 = new WorldgenRandom(new LegacyRandomSource(0L));
            $$2.m_190068_(p_226654_, p_226655_.f_45578_, p_226655_.f_45579_);
            return $$2;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{GenerationContext.class, "registryAccess;chunkGenerator;biomeSource;randomState;structureTemplateManager;random;seed;chunkPos;heightAccessor;validBiome", "f_226621_", "f_226622_", "f_226623_", "f_226624_", "f_226625_", "f_226626_", "f_226627_", "f_226628_", "f_226629_", "f_226630_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{GenerationContext.class, "registryAccess;chunkGenerator;biomeSource;randomState;structureTemplateManager;random;seed;chunkPos;heightAccessor;validBiome", "f_226621_", "f_226622_", "f_226623_", "f_226624_", "f_226625_", "f_226626_", "f_226627_", "f_226628_", "f_226629_", "f_226630_"}, this);
        }

        @Override
        public final boolean equals(Object p_226661_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{GenerationContext.class, "registryAccess;chunkGenerator;biomeSource;randomState;structureTemplateManager;random;seed;chunkPos;heightAccessor;validBiome", "f_226621_", "f_226622_", "f_226623_", "f_226624_", "f_226625_", "f_226626_", "f_226627_", "f_226628_", "f_226629_", "f_226630_"}, this, p_226661_);
        }
    }

    public record GenerationStub(BlockPos f_226669_, Either<Consumer<StructurePiecesBuilder>, StructurePiecesBuilder> f_226670_) {
        public GenerationStub(BlockPos p_226675_, Consumer<StructurePiecesBuilder> p_226676_) {
            this(p_226675_, (Either<Consumer<StructurePiecesBuilder>, StructurePiecesBuilder>)Either.left(p_226676_));
        }

        public StructurePiecesBuilder m_226677_() {
            return (StructurePiecesBuilder)this.f_226670_.map(p_226681_ -> {
                StructurePiecesBuilder $$1 = new StructurePiecesBuilder();
                p_226681_.accept($$1);
                return $$1;
            }, p_226679_ -> p_226679_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{GenerationStub.class, "position;generator", "f_226669_", "f_226670_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{GenerationStub.class, "position;generator", "f_226669_", "f_226670_"}, this);
        }

        @Override
        public final boolean equals(Object p_226685_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{GenerationStub.class, "position;generator", "f_226669_", "f_226670_"}, this, p_226685_);
        }
    }
}

