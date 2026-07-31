/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.Products$P5
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Mu
 */
package net.minecraft.world.level.levelgen.structure.placement;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

public abstract class StructurePlacement {
    public static final Codec<StructurePlacement> f_205036_ = Registry.f_205930_.m_194605_().dispatch(StructurePlacement::m_203443_, StructurePlacementType::m_205049_);
    private static final int f_227021_ = 10387320;
    private final Vec3i f_227022_;
    private final FrequencyReductionMethod f_227023_;
    private final float f_227024_;
    private final int f_227025_;
    private final Optional<ExclusionZone> f_227026_;

    protected static <S extends StructurePlacement> Products.P5<RecordCodecBuilder.Mu<S>, Vec3i, FrequencyReductionMethod, Float, Integer, Optional<ExclusionZone>> m_227041_(RecordCodecBuilder.Instance<S> p_227042_) {
        return p_227042_.group((App)Vec3i.m_194650_(16).optionalFieldOf("locate_offset", (Object)Vec3i.f_123288_).forGetter(StructurePlacement::m_227072_), (App)FrequencyReductionMethod.f_227108_.optionalFieldOf("frequency_reduction_method", (Object)FrequencyReductionMethod.DEFAULT).forGetter(StructurePlacement::m_227073_), (App)Codec.floatRange((float)0.0f, (float)1.0f).optionalFieldOf("frequency", (Object)Float.valueOf(1.0f)).forGetter(StructurePlacement::m_227074_), (App)ExtraCodecs.f_144628_.fieldOf("salt").forGetter(StructurePlacement::m_227075_), (App)ExclusionZone.f_227077_.optionalFieldOf("exclusion_zone").forGetter(StructurePlacement::m_227076_));
    }

    protected StructurePlacement(Vec3i p_227028_, FrequencyReductionMethod p_227029_, float p_227030_, int p_227031_, Optional<ExclusionZone> p_227032_) {
        this.f_227022_ = p_227028_;
        this.f_227023_ = p_227029_;
        this.f_227024_ = p_227030_;
        this.f_227025_ = p_227031_;
        this.f_227026_ = p_227032_;
    }

    protected Vec3i m_227072_() {
        return this.f_227022_;
    }

    protected FrequencyReductionMethod m_227073_() {
        return this.f_227023_;
    }

    protected float m_227074_() {
        return this.f_227024_;
    }

    protected int m_227075_() {
        return this.f_227025_;
    }

    protected Optional<ExclusionZone> m_227076_() {
        return this.f_227026_;
    }

    public boolean m_227054_(ChunkGenerator p_227055_, RandomState p_227056_, long p_227057_, int p_227058_, int p_227059_) {
        if (!this.m_214090_(p_227055_, p_227056_, p_227057_, p_227058_, p_227059_)) {
            return false;
        }
        if (this.f_227024_ < 1.0f && !this.f_227023_.m_227119_(p_227057_, this.f_227025_, p_227058_, p_227059_, this.f_227024_)) {
            return false;
        }
        return !this.f_227026_.isPresent() || !this.f_227026_.get().m_227087_(p_227055_, p_227056_, p_227057_, p_227058_, p_227059_);
    }

    protected abstract boolean m_214090_(ChunkGenerator var1, RandomState var2, long var3, int var5, int var6);

    public BlockPos m_227039_(ChunkPos p_227040_) {
        return new BlockPos(p_227040_.m_45604_(), 0, p_227040_.m_45605_()).m_121955_(this.m_227072_());
    }

    public abstract StructurePlacementType<?> m_203443_();

    private static boolean m_227033_(long p_227034_, int p_227035_, int p_227036_, int p_227037_, float p_227038_) {
        WorldgenRandom $$5 = new WorldgenRandom(new LegacyRandomSource(0L));
        $$5.m_190058_(p_227034_, p_227035_, p_227036_, p_227037_);
        return $$5.m_188501_() < p_227038_;
    }

    private static boolean m_227048_(long p_227049_, int p_227050_, int p_227051_, int p_227052_, float p_227053_) {
        WorldgenRandom $$5 = new WorldgenRandom(new LegacyRandomSource(0L));
        $$5.m_190068_(p_227049_, p_227051_, p_227052_);
        return $$5.m_188500_() < (double)p_227053_;
    }

    private static boolean m_227060_(long p_227061_, int p_227062_, int p_227063_, int p_227064_, float p_227065_) {
        WorldgenRandom $$5 = new WorldgenRandom(new LegacyRandomSource(0L));
        $$5.m_190058_(p_227061_, p_227063_, p_227064_, 10387320);
        return $$5.m_188501_() < p_227065_;
    }

    private static boolean m_227066_(long p_227067_, int p_227068_, int p_227069_, int p_227070_, float p_227071_) {
        int $$5 = p_227069_ >> 4;
        int $$6 = p_227070_ >> 4;
        WorldgenRandom $$7 = new WorldgenRandom(new LegacyRandomSource(0L));
        $$7.m_188584_((long)($$5 ^ $$6 << 4) ^ p_227067_);
        $$7.m_188502_();
        return $$7.m_188503_((int)(1.0f / p_227071_)) == 0;
    }

    public static final class FrequencyReductionMethod
    extends Enum<FrequencyReductionMethod>
    implements StringRepresentable {
        public static final /* enum */ FrequencyReductionMethod DEFAULT = new FrequencyReductionMethod("default", StructurePlacement::m_227033_);
        public static final /* enum */ FrequencyReductionMethod LEGACY_TYPE_1 = new FrequencyReductionMethod("legacy_type_1", StructurePlacement::m_227066_);
        public static final /* enum */ FrequencyReductionMethod LEGACY_TYPE_2 = new FrequencyReductionMethod("legacy_type_2", StructurePlacement::m_227060_);
        public static final /* enum */ FrequencyReductionMethod LEGACY_TYPE_3 = new FrequencyReductionMethod("legacy_type_3", StructurePlacement::m_227048_);
        public static final Codec<FrequencyReductionMethod> f_227108_;
        private final String f_227109_;
        private final FrequencyReducer f_227110_;
        private static final /* synthetic */ FrequencyReductionMethod[] $VALUES;

        public static FrequencyReductionMethod[] values() {
            return (FrequencyReductionMethod[])$VALUES.clone();
        }

        public static FrequencyReductionMethod valueOf(String p_227127_) {
            return Enum.valueOf(FrequencyReductionMethod.class, p_227127_);
        }

        private FrequencyReductionMethod(String p_227116_, FrequencyReducer p_227117_) {
            this.f_227109_ = p_227116_;
            this.f_227110_ = p_227117_;
        }

        public boolean m_227119_(long p_227120_, int p_227121_, int p_227122_, int p_227123_, float p_227124_) {
            return this.f_227110_.m_227098_(p_227120_, p_227121_, p_227122_, p_227123_, p_227124_);
        }

        @Override
        public String m_7912_() {
            return this.f_227109_;
        }

        private static /* synthetic */ FrequencyReductionMethod[] m_227118_() {
            return new FrequencyReductionMethod[]{DEFAULT, LEGACY_TYPE_1, LEGACY_TYPE_2, LEGACY_TYPE_3};
        }

        static {
            $VALUES = FrequencyReductionMethod.m_227118_();
            f_227108_ = StringRepresentable.m_216439_(FrequencyReductionMethod::values);
        }
    }

    @Deprecated
    public record ExclusionZone(Holder<StructureSet> f_227078_, int f_227079_) {
        public static final Codec<ExclusionZone> f_227077_ = RecordCodecBuilder.create(p_227086_ -> p_227086_.group((App)RegistryFileCodec.m_135592_(Registry.f_211073_, StructureSet.f_210001_, false).fieldOf("other_set").forGetter(ExclusionZone::f_227078_), (App)Codec.intRange((int)1, (int)16).fieldOf("chunk_count").forGetter(ExclusionZone::f_227079_)).apply((Applicative)p_227086_, ExclusionZone::new));

        boolean m_227087_(ChunkGenerator p_227088_, RandomState p_227089_, long p_227090_, int p_227091_, int p_227092_) {
            return p_227088_.m_223141_(this.f_227078_, p_227089_, p_227090_, p_227091_, p_227092_, this.f_227079_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ExclusionZone.class, "otherSet;chunkCount", "f_227078_", "f_227079_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ExclusionZone.class, "otherSet;chunkCount", "f_227078_", "f_227079_"}, this);
        }

        @Override
        public final boolean equals(Object p_227095_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ExclusionZone.class, "otherSet;chunkCount", "f_227078_", "f_227079_"}, this, p_227095_);
        }
    }

    @FunctionalInterface
    public static interface FrequencyReducer {
        public boolean m_227098_(long var1, int var3, int var4, int var5, float var6);
    }
}

