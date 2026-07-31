/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableDouble
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package net.minecraft.world.level.levelgen.blending;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction8;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.FluidState;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableObject;

public class Blender {
    private static final Blender f_190137_ = new Blender(new Long2ObjectOpenHashMap(), new Long2ObjectOpenHashMap()){

        @Override
        public BlendingOutput m_207242_(int p_209724_, int p_209725_) {
            return new BlendingOutput(1.0, 0.0);
        }

        @Override
        public double m_207103_(DensityFunction.FunctionContext p_209727_, double p_209728_) {
            return p_209728_;
        }

        @Override
        public BiomeResolver m_183383_(BiomeResolver p_190232_) {
            return p_190232_;
        }
    };
    private static final NormalNoise f_190138_ = NormalNoise.m_230511_(new XoroshiroRandomSource(42L), BuiltinRegistries.f_194654_.m_123013_(Noises.f_189286_));
    private static final int f_190139_ = QuartPos.m_175404_(7) - 1;
    private static final int f_190140_ = QuartPos.m_175406_(f_190139_ + 3);
    private static final int f_190141_ = 2;
    private static final int f_190142_ = QuartPos.m_175406_(5);
    private static final double f_197017_ = 8.0;
    private final Long2ObjectOpenHashMap<BlendingData> f_224696_;
    private final Long2ObjectOpenHashMap<BlendingData> f_224697_;

    public static Blender m_190153_() {
        return f_190137_;
    }

    public static Blender m_190202_(@Nullable WorldGenRegion p_190203_) {
        if (p_190203_ == null) {
            return f_190137_;
        }
        ChunkPos $$1 = p_190203_.m_143488_();
        if (!p_190203_.m_215159_($$1, f_190140_)) {
            return f_190137_;
        }
        Long2ObjectOpenHashMap $$2 = new Long2ObjectOpenHashMap();
        Long2ObjectOpenHashMap $$3 = new Long2ObjectOpenHashMap();
        int $$4 = Mth.m_144944_(f_190140_ + 1);
        for (int $$5 = -f_190140_; $$5 <= f_190140_; ++$$5) {
            for (int $$6 = -f_190140_; $$6 <= f_190140_; ++$$6) {
                int $$8;
                int $$7;
                BlendingData $$9;
                if ($$5 * $$5 + $$6 * $$6 > $$4 || ($$9 = BlendingData.m_190304_(p_190203_, $$7 = $$1.f_45578_ + $$5, $$8 = $$1.f_45579_ + $$6)) == null) continue;
                $$2.put(ChunkPos.m_45589_($$7, $$8), (Object)$$9);
                if ($$5 < -f_190142_ || $$5 > f_190142_ || $$6 < -f_190142_ || $$6 > f_190142_) continue;
                $$3.put(ChunkPos.m_45589_($$7, $$8), (Object)$$9);
            }
        }
        if ($$2.isEmpty() && $$3.isEmpty()) {
            return f_190137_;
        }
        return new Blender((Long2ObjectOpenHashMap<BlendingData>)$$2, (Long2ObjectOpenHashMap<BlendingData>)$$3);
    }

    Blender(Long2ObjectOpenHashMap<BlendingData> p_202197_, Long2ObjectOpenHashMap<BlendingData> p_202198_) {
        this.f_224696_ = p_202197_;
        this.f_224697_ = p_202198_;
    }

    public BlendingOutput m_207242_(int p_209719_, int p_209720_) {
        int $$3;
        int $$2 = QuartPos.m_175400_(p_209719_);
        double $$4 = this.m_190174_($$2, 0, $$3 = QuartPos.m_175400_(p_209720_), BlendingData::m_190285_);
        if ($$4 != Double.MAX_VALUE) {
            return new BlendingOutput(0.0, Blender.m_190154_($$4));
        }
        MutableDouble $$5 = new MutableDouble(0.0);
        MutableDouble $$6 = new MutableDouble(0.0);
        MutableDouble $$7 = new MutableDouble(Double.POSITIVE_INFINITY);
        this.f_224696_.forEach((p_202249_, p_202250_) -> p_202250_.m_190295_(QuartPos.m_175404_(ChunkPos.m_45592_(p_202249_)), QuartPos.m_175404_(ChunkPos.m_45602_(p_202249_)), (p_190199_, p_190200_, p_190201_) -> {
            double $$8 = Mth.m_184645_($$2 - p_190199_, $$3 - p_190200_);
            if ($$8 > (double)f_190139_) {
                return;
            }
            if ($$8 < $$7.doubleValue()) {
                $$7.setValue($$8);
            }
            double $$9 = 1.0 / ($$8 * $$8 * $$8 * $$8);
            $$6.add(p_190201_ * $$9);
            $$5.add($$9);
        }));
        if ($$7.doubleValue() == Double.POSITIVE_INFINITY) {
            return new BlendingOutput(1.0, 0.0);
        }
        double $$8 = $$6.doubleValue() / $$5.doubleValue();
        double $$9 = Mth.m_14008_($$7.doubleValue() / (double)(f_190139_ + 1), 0.0, 1.0);
        $$9 = 3.0 * $$9 * $$9 - 2.0 * $$9 * $$9 * $$9;
        return new BlendingOutput($$9, Blender.m_190154_($$8));
    }

    private static double m_190154_(double p_190155_) {
        double $$1 = 1.0;
        double $$2 = p_190155_ + 0.5;
        double $$3 = Mth.m_14109_($$2, 8.0);
        return 1.0 * (32.0 * ($$2 - 128.0) - 3.0 * ($$2 - 120.0) * $$3 + 3.0 * $$3 * $$3) / (128.0 * (32.0 - 3.0 * $$3));
    }

    public double m_207103_(DensityFunction.FunctionContext p_209721_, double p_209722_) {
        int $$4;
        int $$3;
        int $$2 = QuartPos.m_175400_(p_209721_.m_207115_());
        double $$5 = this.m_190174_($$2, $$3 = p_209721_.m_207114_() / 8, $$4 = QuartPos.m_175400_(p_209721_.m_207113_()), BlendingData::m_190333_);
        if ($$5 != Double.MAX_VALUE) {
            return $$5;
        }
        MutableDouble $$6 = new MutableDouble(0.0);
        MutableDouble $$7 = new MutableDouble(0.0);
        MutableDouble $$8 = new MutableDouble(Double.POSITIVE_INFINITY);
        this.f_224697_.forEach((p_202241_, p_202242_) -> p_202242_.m_190289_(QuartPos.m_175404_(ChunkPos.m_45592_(p_202241_)), QuartPos.m_175404_(ChunkPos.m_45602_(p_202241_)), $$3 - 1, $$3 + 1, (p_202230_, p_202231_, p_202232_, p_202233_) -> {
            double $$10 = Mth.m_184648_($$2 - p_202230_, ($$3 - p_202231_) * 2, $$4 - p_202232_);
            if ($$10 > 2.0) {
                return;
            }
            if ($$10 < $$8.doubleValue()) {
                $$8.setValue($$10);
            }
            double $$11 = 1.0 / ($$10 * $$10 * $$10 * $$10);
            $$7.add(p_202233_ * $$11);
            $$6.add($$11);
        }));
        if ($$8.doubleValue() == Double.POSITIVE_INFINITY) {
            return p_209722_;
        }
        double $$9 = $$7.doubleValue() / $$6.doubleValue();
        double $$10 = Mth.m_14008_($$8.doubleValue() / 3.0, 0.0, 1.0);
        return Mth.m_14139_($$10, $$9, p_209722_);
    }

    private double m_190174_(int p_190175_, int p_190176_, int p_190177_, CellValueGetter p_190178_) {
        int $$4 = QuartPos.m_175406_(p_190175_);
        int $$5 = QuartPos.m_175406_(p_190177_);
        boolean $$6 = (p_190175_ & 3) == 0;
        boolean $$7 = (p_190177_ & 3) == 0;
        double $$8 = this.m_190211_(p_190178_, $$4, $$5, p_190175_, p_190176_, p_190177_);
        if ($$8 == Double.MAX_VALUE) {
            if ($$6 && $$7) {
                $$8 = this.m_190211_(p_190178_, $$4 - 1, $$5 - 1, p_190175_, p_190176_, p_190177_);
            }
            if ($$8 == Double.MAX_VALUE) {
                if ($$6) {
                    $$8 = this.m_190211_(p_190178_, $$4 - 1, $$5, p_190175_, p_190176_, p_190177_);
                }
                if ($$8 == Double.MAX_VALUE && $$7) {
                    $$8 = this.m_190211_(p_190178_, $$4, $$5 - 1, p_190175_, p_190176_, p_190177_);
                }
            }
        }
        return $$8;
    }

    private double m_190211_(CellValueGetter p_190212_, int p_190213_, int p_190214_, int p_190215_, int p_190216_, int p_190217_) {
        BlendingData $$6 = (BlendingData)this.f_224696_.get(ChunkPos.m_45589_(p_190213_, p_190214_));
        if ($$6 != null) {
            return p_190212_.m_190233_($$6, p_190215_ - QuartPos.m_175404_(p_190213_), p_190216_, p_190217_ - QuartPos.m_175404_(p_190214_));
        }
        return Double.MAX_VALUE;
    }

    public BiomeResolver m_183383_(BiomeResolver p_190204_) {
        return (p_204669_, p_204670_, p_204671_, p_204672_) -> {
            Holder<Biome> $$5 = this.m_224706_(p_204669_, p_204670_, p_204671_);
            if ($$5 == null) {
                return p_190204_.m_203407_(p_204669_, p_204670_, p_204671_, p_204672_);
            }
            return $$5;
        };
    }

    @Nullable
    private Holder<Biome> m_224706_(int p_224707_, int p_224708_, int p_224709_) {
        MutableDouble $$3 = new MutableDouble(Double.POSITIVE_INFINITY);
        MutableObject $$4 = new MutableObject();
        this.f_224696_.forEach((p_224716_, p_224717_) -> p_224717_.m_224748_(QuartPos.m_175404_(ChunkPos.m_45592_(p_224716_)), p_224708_, QuartPos.m_175404_(ChunkPos.m_45602_(p_224716_)), (p_224723_, p_224724_, p_224725_) -> {
            double $$7 = Mth.m_184645_(p_224707_ - p_224723_, p_224709_ - p_224724_);
            if ($$7 > (double)f_190139_) {
                return;
            }
            if ($$7 < $$3.doubleValue()) {
                $$4.setValue((Object)p_224725_);
                $$3.setValue($$7);
            }
        }));
        if ($$3.doubleValue() == Double.POSITIVE_INFINITY) {
            return null;
        }
        double $$5 = f_190138_.m_75380_(p_224707_, 0.0, p_224709_) * 12.0;
        double $$6 = Mth.m_14008_(($$3.doubleValue() + $$5) / (double)(f_190139_ + 1), 0.0, 1.0);
        if ($$6 > 0.5) {
            return null;
        }
        return (Holder)$$4.getValue();
    }

    public static void m_197031_(WorldGenRegion p_197032_, ChunkAccess p_197033_) {
        ChunkPos $$2 = p_197033_.m_7697_();
        boolean $$3 = p_197033_.m_187675_();
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        BlockPos $$5 = new BlockPos($$2.m_45604_(), 0, $$2.m_45605_());
        BlendingData $$6 = p_197033_.m_183407_();
        if ($$6 == null) {
            return;
        }
        int $$7 = $$6.m_224743_().m_141937_();
        int $$8 = $$6.m_224743_().m_151558_() - 1;
        if ($$3) {
            for (int $$9 = 0; $$9 < 16; ++$$9) {
                for (int $$10 = 0; $$10 < 16; ++$$10) {
                    Blender.m_197040_(p_197033_, $$4.m_122154_($$5, $$9, $$7 - 1, $$10));
                    Blender.m_197040_(p_197033_, $$4.m_122154_($$5, $$9, $$7, $$10));
                    Blender.m_197040_(p_197033_, $$4.m_122154_($$5, $$9, $$8, $$10));
                    Blender.m_197040_(p_197033_, $$4.m_122154_($$5, $$9, $$8 + 1, $$10));
                }
            }
        }
        for (Direction $$11 : Direction.Plane.HORIZONTAL) {
            if (p_197032_.m_6325_($$2.f_45578_ + $$11.m_122429_(), $$2.f_45579_ + $$11.m_122431_()).m_187675_() == $$3) continue;
            int $$12 = $$11 == Direction.EAST ? 15 : 0;
            int $$13 = $$11 == Direction.WEST ? 0 : 15;
            int $$14 = $$11 == Direction.SOUTH ? 15 : 0;
            int $$15 = $$11 == Direction.NORTH ? 0 : 15;
            for (int $$16 = $$12; $$16 <= $$13; ++$$16) {
                for (int $$17 = $$14; $$17 <= $$15; ++$$17) {
                    int $$18 = Math.min($$8, p_197033_.m_5885_(Heightmap.Types.MOTION_BLOCKING, $$16, $$17)) + 1;
                    for (int $$19 = $$7; $$19 < $$18; ++$$19) {
                        Blender.m_197040_(p_197033_, $$4.m_122154_($$5, $$16, $$19, $$17));
                    }
                }
            }
        }
    }

    private static void m_197040_(ChunkAccess p_197041_, BlockPos p_197042_) {
        FluidState $$3;
        BlockState $$2 = p_197041_.m_8055_(p_197042_);
        if ($$2.m_204336_(BlockTags.f_13035_)) {
            p_197041_.m_8113_(p_197042_);
        }
        if (!($$3 = p_197041_.m_6425_(p_197042_)).m_76178_()) {
            p_197041_.m_8113_(p_197042_);
        }
    }

    public static void m_197034_(WorldGenLevel p_197035_, ProtoChunk p_197036_) {
        ChunkPos $$2 = p_197036_.m_7697_();
        ImmutableMap.Builder $$3 = ImmutableMap.builder();
        for (Direction8 $$4 : Direction8.values()) {
            int $$6;
            int $$5 = $$2.f_45578_ + $$4.m_235697_();
            BlendingData $$7 = p_197035_.m_6325_($$5, $$6 = $$2.f_45579_ + $$4.m_235698_()).m_183407_();
            if ($$7 == null) continue;
            $$3.put((Object)$$4, (Object)$$7);
        }
        ImmutableMap $$8 = $$3.build();
        if (!p_197036_.m_187675_() && $$8.isEmpty()) {
            return;
        }
        DistanceGetter $$9 = Blender.m_224726_(p_197036_.m_183407_(), (Map<Direction8, BlendingData>)$$8);
        CarvingMask.Mask $$10 = (p_202262_, p_202263_, p_202264_) -> {
            double $$6;
            double $$5;
            double $$4 = (double)p_202262_ + 0.5 + f_190138_.m_75380_(p_202262_, p_202263_, p_202264_) * 4.0;
            return $$9.m_197061_($$4, $$5 = (double)p_202263_ + 0.5 + f_190138_.m_75380_(p_202263_, p_202264_, p_202262_) * 4.0, $$6 = (double)p_202264_ + 0.5 + f_190138_.m_75380_(p_202264_, p_202262_, p_202263_) * 4.0) < 4.0;
        };
        Stream.of(GenerationStep.Carving.values()).map(p_197036_::m_183613_).forEach(p_202259_ -> p_202259_.m_196710_($$10));
    }

    public static DistanceGetter m_224726_(@Nullable BlendingData p_224727_, Map<Direction8, BlendingData> p_224728_) {
        ArrayList $$2 = Lists.newArrayList();
        if (p_224727_ != null) {
            $$2.add(Blender.m_224729_(null, p_224727_));
        }
        p_224728_.forEach((p_224734_, p_224735_) -> $$2.add(Blender.m_224729_(p_224734_, p_224735_)));
        return (p_202267_, p_202268_, p_202269_) -> {
            double $$4 = Double.POSITIVE_INFINITY;
            for (DistanceGetter $$5 : $$2) {
                double $$6 = $$5.m_197061_(p_202267_, p_202268_, p_202269_);
                if (!($$6 < $$4)) continue;
                $$4 = $$6;
            }
            return $$4;
        };
    }

    private static DistanceGetter m_224729_(@Nullable Direction8 p_224730_, BlendingData p_224731_) {
        double $$2 = 0.0;
        double $$3 = 0.0;
        if (p_224730_ != null) {
            for (Direction $$4 : p_224730_.m_122593_()) {
                $$2 += (double)($$4.m_122429_() * 16);
                $$3 += (double)($$4.m_122431_() * 16);
            }
        }
        double $$5 = $$2;
        double $$6 = $$3;
        double $$7 = (double)p_224731_.m_224743_().m_141928_() / 2.0;
        double $$8 = (double)p_224731_.m_224743_().m_141937_() + $$7;
        return (p_224703_, p_224704_, p_224705_) -> Blender.m_197024_(p_224703_ - 8.0 - $$5, p_224704_ - $$8, p_224705_ - 8.0 - $$6, 8.0, $$7, 8.0);
    }

    private static double m_197024_(double p_197025_, double p_197026_, double p_197027_, double p_197028_, double p_197029_, double p_197030_) {
        double $$6 = Math.abs(p_197025_) - p_197028_;
        double $$7 = Math.abs(p_197026_) - p_197029_;
        double $$8 = Math.abs(p_197027_) - p_197030_;
        return Mth.m_184648_(Math.max(0.0, $$6), Math.max(0.0, $$7), Math.max(0.0, $$8));
    }

    static interface CellValueGetter {
        public double m_190233_(BlendingData var1, int var2, int var3, int var4);
    }

    public record BlendingOutput(double f_209729_, double f_209730_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{BlendingOutput.class, "alpha;blendingOffset", "f_209729_", "f_209730_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{BlendingOutput.class, "alpha;blendingOffset", "f_209729_", "f_209730_"}, this);
        }

        @Override
        public final boolean equals(Object p_209737_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{BlendingOutput.class, "alpha;blendingOffset", "f_209729_", "f_209730_"}, this, p_209737_);
        }
    }

    public static interface DistanceGetter {
        public double m_197061_(double var1, double var3, double var5);
    }
}

