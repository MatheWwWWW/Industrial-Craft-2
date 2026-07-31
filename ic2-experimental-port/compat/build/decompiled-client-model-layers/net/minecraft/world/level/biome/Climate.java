/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.biome;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;

public class Climate {
    private static final boolean f_186776_ = false;
    private static final float f_186777_ = 10000.0f;
    @VisibleForTesting
    protected static final int f_186775_ = 7;

    public static TargetPoint m_186781_(float p_186782_, float p_186783_, float p_186784_, float p_186785_, float p_186786_, float p_186787_) {
        return new TargetPoint(Climate.m_186779_(p_186782_), Climate.m_186779_(p_186783_), Climate.m_186779_(p_186784_), Climate.m_186779_(p_186785_), Climate.m_186779_(p_186786_), Climate.m_186779_(p_186787_));
    }

    public static ParameterPoint m_186788_(float p_186789_, float p_186790_, float p_186791_, float p_186792_, float p_186793_, float p_186794_, float p_186795_) {
        return new ParameterPoint(Parameter.m_186820_(p_186789_), Parameter.m_186820_(p_186790_), Parameter.m_186820_(p_186791_), Parameter.m_186820_(p_186792_), Parameter.m_186820_(p_186793_), Parameter.m_186820_(p_186794_), Climate.m_186779_(p_186795_));
    }

    public static ParameterPoint m_186798_(Parameter p_186799_, Parameter p_186800_, Parameter p_186801_, Parameter p_186802_, Parameter p_186803_, Parameter p_186804_, float p_186805_) {
        return new ParameterPoint(p_186799_, p_186800_, p_186801_, p_186802_, p_186803_, p_186804_, Climate.m_186779_(p_186805_));
    }

    public static long m_186779_(float p_186780_) {
        return (long)(p_186780_ * 10000.0f);
    }

    public static float m_186796_(long p_186797_) {
        return (float)p_186797_ / 10000.0f;
    }

    public static Sampler m_207841_() {
        DensityFunction $$0 = DensityFunctions.m_208263_();
        return new Sampler($$0, $$0, $$0, $$0, $$0, $$0, List.of());
    }

    public static BlockPos m_207842_(List<ParameterPoint> p_207843_, Sampler p_207844_) {
        return new SpawnFinder(p_207843_, (Sampler)p_207844_).f_186978_.f_186992_();
    }

    public record TargetPoint(long f_187003_, long f_187004_, long f_187005_, long f_187006_, long f_187007_, long f_187008_) {
        @VisibleForTesting
        protected long[] m_187016_() {
            return new long[]{this.f_187003_, this.f_187004_, this.f_187005_, this.f_187006_, this.f_187007_, this.f_187008_, 0L};
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{TargetPoint.class, "temperature;humidity;continentalness;erosion;depth;weirdness", "f_187003_", "f_187004_", "f_187005_", "f_187006_", "f_187007_", "f_187008_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{TargetPoint.class, "temperature;humidity;continentalness;erosion;depth;weirdness", "f_187003_", "f_187004_", "f_187005_", "f_187006_", "f_187007_", "f_187008_"}, this);
        }

        @Override
        public final boolean equals(Object p_187022_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{TargetPoint.class, "temperature;humidity;continentalness;erosion;depth;weirdness", "f_187003_", "f_187004_", "f_187005_", "f_187006_", "f_187007_", "f_187008_"}, this, p_187022_);
        }
    }

    public record ParameterPoint(Parameter f_186863_, Parameter f_186864_, Parameter f_186865_, Parameter f_186866_, Parameter f_186867_, Parameter f_186868_, long f_186869_) {
        public static final Codec<ParameterPoint> f_186862_ = RecordCodecBuilder.create(p_186885_ -> p_186885_.group((App)Parameter.f_186812_.fieldOf("temperature").forGetter(p_186905_ -> p_186905_.f_186863_), (App)Parameter.f_186812_.fieldOf("humidity").forGetter(p_186902_ -> p_186902_.f_186864_), (App)Parameter.f_186812_.fieldOf("continentalness").forGetter(p_186897_ -> p_186897_.f_186865_), (App)Parameter.f_186812_.fieldOf("erosion").forGetter(p_186894_ -> p_186894_.f_186866_), (App)Parameter.f_186812_.fieldOf("depth").forGetter(p_186891_ -> p_186891_.f_186867_), (App)Parameter.f_186812_.fieldOf("weirdness").forGetter(p_186888_ -> p_186888_.f_186868_), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("offset").xmap(Climate::m_186779_, Climate::m_186796_).forGetter(p_186881_ -> p_186881_.f_186869_)).apply((Applicative)p_186885_, ParameterPoint::new));

        long m_186882_(TargetPoint p_186883_) {
            return Mth.m_184643_(this.f_186863_.m_186825_(p_186883_.f_187003_)) + Mth.m_184643_(this.f_186864_.m_186825_(p_186883_.f_187004_)) + Mth.m_184643_(this.f_186865_.m_186825_(p_186883_.f_187005_)) + Mth.m_184643_(this.f_186866_.m_186825_(p_186883_.f_187006_)) + Mth.m_184643_(this.f_186867_.m_186825_(p_186883_.f_187007_)) + Mth.m_184643_(this.f_186868_.m_186825_(p_186883_.f_187008_)) + Mth.m_184643_(this.f_186869_);
        }

        protected List<Parameter> m_186879_() {
            return ImmutableList.of((Object)this.f_186863_, (Object)this.f_186864_, (Object)this.f_186865_, (Object)this.f_186866_, (Object)this.f_186867_, (Object)this.f_186868_, (Object)new Parameter(this.f_186869_, this.f_186869_));
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ParameterPoint.class, "temperature;humidity;continentalness;erosion;depth;weirdness;offset", "f_186863_", "f_186864_", "f_186865_", "f_186866_", "f_186867_", "f_186868_", "f_186869_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ParameterPoint.class, "temperature;humidity;continentalness;erosion;depth;weirdness;offset", "f_186863_", "f_186864_", "f_186865_", "f_186866_", "f_186867_", "f_186868_", "f_186869_"}, this);
        }

        @Override
        public final boolean equals(Object p_186899_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ParameterPoint.class, "temperature;humidity;continentalness;erosion;depth;weirdness;offset", "f_186863_", "f_186864_", "f_186865_", "f_186866_", "f_186867_", "f_186868_", "f_186869_"}, this, p_186899_);
        }
    }

    public record Parameter(long f_186813_, long f_186814_) {
        public static final Codec<Parameter> f_186812_ = ExtraCodecs.m_184361_(Codec.floatRange((float)-2.0f, (float)2.0f), "min", "max", (p_186833_, p_186834_) -> {
            if (p_186833_.compareTo((Float)p_186834_) > 0) {
                return DataResult.error((String)("Cannon construct interval, min > max (" + p_186833_ + " > " + p_186834_ + ")"));
            }
            return DataResult.success((Object)new Parameter(Climate.m_186779_(p_186833_.floatValue()), Climate.m_186779_(p_186834_.floatValue())));
        }, p_186841_ -> Float.valueOf(Climate.m_186796_(p_186841_.f_186813_())), p_186839_ -> Float.valueOf(Climate.m_186796_(p_186839_.f_186814_())));

        public static Parameter m_186820_(float p_186821_) {
            return Parameter.m_186822_(p_186821_, p_186821_);
        }

        public static Parameter m_186822_(float p_186823_, float p_186824_) {
            if (p_186823_ > p_186824_) {
                throw new IllegalArgumentException("min > max: " + p_186823_ + " " + p_186824_);
            }
            return new Parameter(Climate.m_186779_(p_186823_), Climate.m_186779_(p_186824_));
        }

        public static Parameter m_186829_(Parameter p_186830_, Parameter p_186831_) {
            if (p_186830_.f_186813_() > p_186831_.f_186814_()) {
                throw new IllegalArgumentException("min > max: " + p_186830_ + " " + p_186831_);
            }
            return new Parameter(p_186830_.f_186813_(), p_186831_.f_186814_());
        }

        @Override
        public String toString() {
            return this.f_186813_ == this.f_186814_ ? String.format(Locale.ROOT, "%d", this.f_186813_) : String.format(Locale.ROOT, "[%d-%d]", this.f_186813_, this.f_186814_);
        }

        public long m_186825_(long p_186826_) {
            long $$1 = p_186826_ - this.f_186814_;
            long $$2 = this.f_186813_ - p_186826_;
            if ($$1 > 0L) {
                return $$1;
            }
            return Math.max($$2, 0L);
        }

        public long m_186827_(Parameter p_186828_) {
            long $$1 = p_186828_.f_186813_() - this.f_186814_;
            long $$2 = this.f_186813_ - p_186828_.f_186814_();
            if ($$1 > 0L) {
                return $$1;
            }
            return Math.max($$2, 0L);
        }

        public Parameter m_186836_(@Nullable Parameter p_186837_) {
            return p_186837_ == null ? this : new Parameter(Math.min(this.f_186813_, p_186837_.f_186813_()), Math.max(this.f_186814_, p_186837_.f_186814_()));
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Parameter.class, "min;max", "f_186813_", "f_186814_"}, this);
        }

        @Override
        public final boolean equals(Object p_186843_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Parameter.class, "min;max", "f_186813_", "f_186814_"}, this, p_186843_);
        }
    }

    public record Sampler(DensityFunction f_207845_, DensityFunction f_207846_, DensityFunction f_207847_, DensityFunction f_207848_, DensityFunction f_207849_, DensityFunction f_207850_, List<ParameterPoint> f_207851_) {
        public TargetPoint m_183445_(int p_186975_, int p_186976_, int p_186977_) {
            int $$3 = QuartPos.m_175402_(p_186975_);
            int $$4 = QuartPos.m_175402_(p_186976_);
            int $$5 = QuartPos.m_175402_(p_186977_);
            DensityFunction.SinglePointContext $$6 = new DensityFunction.SinglePointContext($$3, $$4, $$5);
            return Climate.m_186781_((float)this.f_207845_.m_207386_($$6), (float)this.f_207846_.m_207386_($$6), (float)this.f_207847_.m_207386_($$6), (float)this.f_207848_.m_207386_($$6), (float)this.f_207849_.m_207386_($$6), (float)this.f_207850_.m_207386_($$6));
        }

        public BlockPos m_183230_() {
            if (this.f_207851_.isEmpty()) {
                return BlockPos.f_121853_;
            }
            return Climate.m_207842_(this.f_207851_, this);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Sampler.class, "temperature;humidity;continentalness;erosion;depth;weirdness;spawnTarget", "f_207845_", "f_207846_", "f_207847_", "f_207848_", "f_207849_", "f_207850_", "f_207851_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Sampler.class, "temperature;humidity;continentalness;erosion;depth;weirdness;spawnTarget", "f_207845_", "f_207846_", "f_207847_", "f_207848_", "f_207849_", "f_207850_", "f_207851_"}, this);
        }

        @Override
        public final boolean equals(Object p_207865_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Sampler.class, "temperature;humidity;continentalness;erosion;depth;weirdness;spawnTarget", "f_207845_", "f_207846_", "f_207847_", "f_207848_", "f_207849_", "f_207850_", "f_207851_"}, this, p_207865_);
        }
    }

    static class SpawnFinder {
        Result f_186978_;

        SpawnFinder(List<ParameterPoint> p_207872_, Sampler p_207873_) {
            this.f_186978_ = SpawnFinder.m_207879_(p_207872_, p_207873_, 0, 0);
            this.m_207874_(p_207872_, p_207873_, 2048.0f, 512.0f);
            this.m_207874_(p_207872_, p_207873_, 512.0f, 32.0f);
        }

        private void m_207874_(List<ParameterPoint> p_207875_, Sampler p_207876_, float p_207877_, float p_207878_) {
            float $$4 = 0.0f;
            float $$5 = p_207878_;
            BlockPos $$6 = this.f_186978_.f_186992_();
            while ($$5 <= p_207877_) {
                int $$8;
                int $$7 = $$6.m_123341_() + (int)(Math.sin($$4) * (double)$$5);
                Result $$9 = SpawnFinder.m_207879_(p_207875_, p_207876_, $$7, $$8 = $$6.m_123343_() + (int)(Math.cos($$4) * (double)$$5));
                if ($$9.f_186993_() < this.f_186978_.f_186993_()) {
                    this.f_186978_ = $$9;
                }
                if (!((double)($$4 += p_207878_ / $$5) > Math.PI * 2)) continue;
                $$4 = 0.0f;
                $$5 += p_207878_;
            }
        }

        private static Result m_207879_(List<ParameterPoint> p_207880_, Sampler p_207881_, int p_207882_, int p_207883_) {
            double $$4 = Mth.m_144952_(2500.0);
            int $$5 = 2;
            long $$6 = (long)((double)Mth.m_14207_(10000.0f) * Math.pow((double)(Mth.m_184643_(p_207882_) + Mth.m_184643_(p_207883_)) / $$4, 2.0));
            TargetPoint $$7 = p_207881_.m_183445_(QuartPos.m_175400_(p_207882_), 0, QuartPos.m_175400_(p_207883_));
            TargetPoint $$8 = new TargetPoint($$7.f_187003_(), $$7.f_187004_(), $$7.f_187005_(), $$7.f_187006_(), 0L, $$7.f_187008_());
            long $$9 = Long.MAX_VALUE;
            for (ParameterPoint $$10 : p_207880_) {
                $$9 = Math.min($$9, $$10.m_186882_($$8));
            }
            return new Result(new BlockPos(p_207882_, 0, p_207883_), $$6 + $$9);
        }

        record Result(BlockPos f_186992_, long f_186993_) {
            @Override
            public final String toString() {
                return ObjectMethods.bootstrap("toString", new MethodHandle[]{Result.class, "location;fitness", "f_186992_", "f_186993_"}, this);
            }

            @Override
            public final int hashCode() {
                return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Result.class, "location;fitness", "f_186992_", "f_186993_"}, this);
            }

            @Override
            public final boolean equals(Object p_187000_) {
                return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Result.class, "location;fitness", "f_186992_", "f_186993_"}, this, p_187000_);
            }
        }
    }

    public static class ParameterList<T> {
        private final List<Pair<ParameterPoint, T>> f_186846_;
        private final RTree<T> f_186847_;

        public ParameterList(List<Pair<ParameterPoint, T>> p_186849_) {
            this.f_186846_ = p_186849_;
            this.f_186847_ = RTree.m_186935_(p_186849_);
        }

        public List<Pair<ParameterPoint, T>> m_186850_() {
            return this.f_186846_;
        }

        public T m_204252_(TargetPoint p_204253_) {
            return this.m_186851_(p_204253_);
        }

        @VisibleForTesting
        public T m_204254_(TargetPoint p_204255_) {
            Iterator<Pair<ParameterPoint, T>> $$1 = this.m_186850_().iterator();
            Pair<ParameterPoint, T> $$2 = $$1.next();
            long $$3 = ((ParameterPoint)$$2.getFirst()).m_186882_(p_204255_);
            Object $$4 = $$2.getSecond();
            while ($$1.hasNext()) {
                Pair<ParameterPoint, T> $$5 = $$1.next();
                long $$6 = ((ParameterPoint)$$5.getFirst()).m_186882_(p_204255_);
                if ($$6 >= $$3) continue;
                $$3 = $$6;
                $$4 = $$5.getSecond();
            }
            return (T)$$4;
        }

        public T m_186851_(TargetPoint p_186852_) {
            return this.m_186853_(p_186852_, RTree.Node::m_186959_);
        }

        protected T m_186853_(TargetPoint p_186854_, DistanceMetric<T> p_186855_) {
            return this.f_186847_.m_186930_(p_186854_, p_186855_);
        }
    }

    protected static final class RTree<T> {
        private static final int f_186909_ = 10;
        private final Node<T> f_186910_;
        private final ThreadLocal<Leaf<T>> f_186911_ = new ThreadLocal();

        private RTree(Node<T> p_186913_) {
            this.f_186910_ = p_186913_;
        }

        public static <T> RTree<T> m_186935_(List<Pair<ParameterPoint, T>> p_186936_) {
            if (p_186936_.isEmpty()) {
                throw new IllegalArgumentException("Need at least one value to build the search tree.");
            }
            int $$1 = ((ParameterPoint)p_186936_.get(0).getFirst()).m_186879_().size();
            if ($$1 != 7) {
                throw new IllegalStateException("Expecting parameter space to be 7, got " + $$1);
            }
            List $$2 = p_186936_.stream().map(p_186934_ -> new Leaf<Object>((ParameterPoint)p_186934_.getFirst(), p_186934_.getSecond())).collect(Collectors.toCollection(ArrayList::new));
            return new RTree<T>(RTree.m_186920_($$1, $$2));
        }

        private static <T> Node<T> m_186920_(int p_186921_, List<? extends Node<T>> p_186922_) {
            if (p_186922_.isEmpty()) {
                throw new IllegalStateException("Need at least one child to build a node");
            }
            if (p_186922_.size() == 1) {
                return p_186922_.get(0);
            }
            if (p_186922_.size() <= 10) {
                p_186922_.sort(Comparator.comparingLong(p_186916_ -> {
                    long $$2 = 0L;
                    for (int $$3 = 0; $$3 < p_186921_; ++$$3) {
                        Parameter $$4 = p_186916_.f_186956_[$$3];
                        $$2 += Math.abs(($$4.f_186813_() + $$4.f_186814_()) / 2L);
                    }
                    return $$2;
                }));
                return new SubTree(p_186922_);
            }
            long $$2 = Long.MAX_VALUE;
            int $$3 = -1;
            List<SubTree<T>> $$4 = null;
            for (int $$5 = 0; $$5 < p_186921_; ++$$5) {
                RTree.m_186937_(p_186922_, p_186921_, $$5, false);
                List<SubTree<T>> $$6 = RTree.m_186944_(p_186922_);
                long $$7 = 0L;
                for (SubTree<T> $$8 : $$6) {
                    $$7 += RTree.m_186942_($$8.f_186956_);
                }
                if ($$2 <= $$7) continue;
                $$2 = $$7;
                $$3 = $$5;
                $$4 = $$6;
            }
            RTree.m_186937_($$4, p_186921_, $$3, true);
            return new SubTree($$4.stream().map(p_186919_ -> RTree.m_186920_(p_186921_, Arrays.asList(p_186919_.f_186965_))).collect(Collectors.toList()));
        }

        private static <T> void m_186937_(List<? extends Node<T>> p_186938_, int p_186939_, int p_186940_, boolean p_186941_) {
            Comparator<Node<Node<T>>> $$4 = RTree.m_186923_(p_186940_, p_186941_);
            for (int $$5 = 1; $$5 < p_186939_; ++$$5) {
                $$4 = $$4.thenComparing(RTree.m_186923_((p_186940_ + $$5) % p_186939_, p_186941_));
            }
            p_186938_.sort($$4);
        }

        private static <T> Comparator<Node<T>> m_186923_(int p_186924_, boolean p_186925_) {
            return Comparator.comparingLong(p_186929_ -> {
                Parameter $$3 = p_186929_.f_186956_[p_186924_];
                long $$4 = ($$3.f_186813_() + $$3.f_186814_()) / 2L;
                return p_186925_ ? Math.abs($$4) : $$4;
            });
        }

        private static <T> List<SubTree<T>> m_186944_(List<? extends Node<T>> p_186945_) {
            ArrayList $$1 = Lists.newArrayList();
            ArrayList $$2 = Lists.newArrayList();
            int $$3 = (int)Math.pow(10.0, Math.floor(Math.log((double)p_186945_.size() - 0.01) / Math.log(10.0)));
            for (Node<T> $$4 : p_186945_) {
                $$2.add($$4);
                if ($$2.size() < $$3) continue;
                $$1.add(new SubTree($$2));
                $$2 = Lists.newArrayList();
            }
            if (!$$2.isEmpty()) {
                $$1.add(new SubTree($$2));
            }
            return $$1;
        }

        private static long m_186942_(Parameter[] p_186943_) {
            long $$1 = 0L;
            for (Parameter $$2 : p_186943_) {
                $$1 += Math.abs($$2.f_186814_() - $$2.f_186813_());
            }
            return $$1;
        }

        static <T> List<Parameter> m_186946_(List<? extends Node<T>> p_186947_) {
            if (p_186947_.isEmpty()) {
                throw new IllegalArgumentException("SubTree needs at least one child");
            }
            int $$1 = 7;
            ArrayList $$2 = Lists.newArrayList();
            for (int $$3 = 0; $$3 < 7; ++$$3) {
                $$2.add(null);
            }
            for (Node<T> $$4 : p_186947_) {
                for (int $$5 = 0; $$5 < 7; ++$$5) {
                    $$2.set($$5, $$4.f_186956_[$$5].m_186836_((Parameter)$$2.get($$5)));
                }
            }
            return $$2;
        }

        public T m_186930_(TargetPoint p_186931_, DistanceMetric<T> p_186932_) {
            long[] $$2 = p_186931_.m_187016_();
            Leaf<T> $$3 = this.f_186910_.m_183370_($$2, this.f_186911_.get(), p_186932_);
            this.f_186911_.set($$3);
            return $$3.f_186948_;
        }

        static abstract class Node<T> {
            protected final Parameter[] f_186956_;

            protected Node(List<Parameter> p_186958_) {
                this.f_186956_ = p_186958_.toArray(new Parameter[0]);
            }

            protected abstract Leaf<T> m_183370_(long[] var1, @Nullable Leaf<T> var2, DistanceMetric<T> var3);

            protected long m_186959_(long[] p_186960_) {
                long $$1 = 0L;
                for (int $$2 = 0; $$2 < 7; ++$$2) {
                    $$1 += Mth.m_184643_(this.f_186956_[$$2].m_186825_(p_186960_[$$2]));
                }
                return $$1;
            }

            public String toString() {
                return Arrays.toString(this.f_186956_);
            }
        }

        static final class SubTree<T>
        extends Node<T> {
            final Node<T>[] f_186965_;

            protected SubTree(List<? extends Node<T>> p_186967_) {
                this(RTree.m_186946_(p_186967_), p_186967_);
            }

            protected SubTree(List<Parameter> p_186969_, List<? extends Node<T>> p_186970_) {
                super(p_186969_);
                this.f_186965_ = p_186970_.toArray(new Node[0]);
            }

            @Override
            protected Leaf<T> m_183370_(long[] p_186972_, @Nullable Leaf<T> p_186973_, DistanceMetric<T> p_186974_) {
                long $$3 = p_186973_ == null ? Long.MAX_VALUE : p_186974_.m_186809_(p_186973_, p_186972_);
                Leaf<T> $$4 = p_186973_;
                for (Node<T> $$5 : this.f_186965_) {
                    long $$8;
                    long $$6 = p_186974_.m_186809_($$5, p_186972_);
                    if ($$3 <= $$6) continue;
                    Leaf<T> $$7 = $$5.m_183370_(p_186972_, $$4, p_186974_);
                    long l = $$8 = $$5 == $$7 ? $$6 : p_186974_.m_186809_($$7, p_186972_);
                    if ($$3 <= $$8) continue;
                    $$3 = $$8;
                    $$4 = $$7;
                }
                return $$4;
            }
        }

        static final class Leaf<T>
        extends Node<T> {
            final T f_186948_;

            Leaf(ParameterPoint p_186950_, T p_186951_) {
                super(p_186950_.m_186879_());
                this.f_186948_ = p_186951_;
            }

            @Override
            protected Leaf<T> m_183370_(long[] p_186953_, @Nullable Leaf<T> p_186954_, DistanceMetric<T> p_186955_) {
                return this;
            }
        }
    }

    static interface DistanceMetric<T> {
        public long m_186809_(RTree.Node<T> var1, long[] var2);
    }
}

