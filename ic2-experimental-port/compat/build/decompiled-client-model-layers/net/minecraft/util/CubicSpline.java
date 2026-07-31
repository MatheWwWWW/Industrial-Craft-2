/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Either
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.floats.FloatArrayList
 *  it.unimi.dsi.fastutil.floats.FloatList
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package net.minecraft.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.ToFloatFunction;
import net.minecraft.util.VisibleForDebug;
import org.apache.commons.lang3.mutable.MutableObject;

public interface CubicSpline<C, I extends ToFloatFunction<C>>
extends ToFloatFunction<C> {
    @VisibleForDebug
    public String m_183628_();

    public CubicSpline<C, I> m_211396_(CoordinateVisitor<I> var1);

    public static <C, I extends ToFloatFunction<C>> Codec<CubicSpline<C, I>> m_184262_(Codec<I> p_184263_) {
        record Point<C, I extends ToFloatFunction<C>>(float f_184273_, CubicSpline<C, I> f_184274_, float f_184275_) {
            @Override
            public final String toString() {
                return ObjectMethods.bootstrap("toString", new MethodHandle[]{Point.class, "location;value;derivative", "f_184273_", "f_184274_", "f_184275_"}, this);
            }

            @Override
            public final int hashCode() {
                return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Point.class, "location;value;derivative", "f_184273_", "f_184274_", "f_184275_"}, this);
            }

            @Override
            public final boolean equals(Object p_184284_) {
                return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Point.class, "location;value;derivative", "f_184273_", "f_184274_", "f_184275_"}, this, p_184284_);
            }
        }
        MutableObject $$1 = new MutableObject();
        Codec $$2 = RecordCodecBuilder.create(p_184270_ -> p_184270_.group((App)Codec.FLOAT.fieldOf("location").forGetter(Point::f_184273_), (App)ExtraCodecs.m_184415_(() -> ((MutableObject)$$1).getValue()).fieldOf("value").forGetter(Point::f_184274_), (App)Codec.FLOAT.fieldOf("derivative").forGetter(Point::f_184275_)).apply((Applicative)p_184270_, (p_184242_, p_184243_, p_184244_) -> new Point((float)p_184242_, p_184243_, (float)p_184244_)));
        Codec $$3 = RecordCodecBuilder.create(p_184267_ -> p_184267_.group((App)p_184263_.fieldOf("coordinate").forGetter(Multipoint::f_184319_), (App)ExtraCodecs.m_144637_($$2.listOf()).fieldOf("points").forGetter(p_184272_ -> IntStream.range(0, p_184272_.f_184320_.length).mapToObj(p_184249_ -> new Point(p_184272_.f_184320_()[p_184249_], p_184272_.f_184321_().get(p_184249_), p_184272_.f_184322_()[p_184249_])).toList())).apply((Applicative)p_184267_, (p_184258_, p_184259_) -> {
            float[] $$2 = new float[p_184259_.size()];
            ImmutableList.Builder $$3 = ImmutableList.builder();
            float[] $$4 = new float[p_184259_.size()];
            for (int $$5 = 0; $$5 < p_184259_.size(); ++$$5) {
                Point $$6 = (Point)p_184259_.get($$5);
                $$2[$$5] = $$6.f_184273_();
                $$3.add($$6.f_184274_());
                $$4[$$5] = $$6.f_184275_();
            }
            return Multipoint.m_216143_(p_184258_, $$2, $$3.build(), $$4);
        }));
        $$1.setValue((Object)Codec.either((Codec)Codec.FLOAT, (Codec)$$3).xmap(p_184261_ -> (CubicSpline)p_184261_.map(Constant::new, p_184246_ -> p_184246_), p_184251_ -> {
            Either either;
            if (p_184251_ instanceof Constant) {
                Constant $$1 = (Constant)p_184251_;
                either = Either.left((Object)Float.valueOf($$1.f_184308_()));
            } else {
                either = Either.right((Object)((Multipoint)p_184251_));
            }
            return either;
        }));
        return (Codec)$$1.getValue();
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> m_184239_(float p_184240_) {
        return new Constant(p_184240_);
    }

    public static <C, I extends ToFloatFunction<C>> Builder<C, I> m_184252_(I p_184253_) {
        return new Builder(p_184253_);
    }

    public static <C, I extends ToFloatFunction<C>> Builder<C, I> m_184254_(I p_184255_, ToFloatFunction<Float> p_184256_) {
        return new Builder(p_184255_, p_184256_);
    }

    @VisibleForDebug
    public record Constant<C, I extends ToFloatFunction<C>>(float f_184308_) implements CubicSpline<C, I>
    {
        @Override
        public float m_183321_(C p_184313_) {
            return this.f_184308_;
        }

        @Override
        public String m_183628_() {
            return String.format(Locale.ROOT, "k=%.3f", Float.valueOf(this.f_184308_));
        }

        @Override
        public float m_213850_() {
            return this.f_184308_;
        }

        @Override
        public float m_213849_() {
            return this.f_184308_;
        }

        @Override
        public CubicSpline<C, I> m_211396_(CoordinateVisitor<I> p_211581_) {
            return this;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Constant.class, "value", "f_184308_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Constant.class, "value", "f_184308_"}, this);
        }

        @Override
        public final boolean equals(Object p_184316_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Constant.class, "value", "f_184308_"}, this, p_184316_);
        }
    }

    public static final class Builder<C, I extends ToFloatFunction<C>> {
        private final I f_184287_;
        private final ToFloatFunction<Float> f_184288_;
        private final FloatList f_184289_ = new FloatArrayList();
        private final List<CubicSpline<C, I>> f_184290_ = Lists.newArrayList();
        private final FloatList f_184291_ = new FloatArrayList();

        protected Builder(I p_184293_) {
            this(p_184293_, ToFloatFunction.f_216471_);
        }

        protected Builder(I p_184295_, ToFloatFunction<Float> p_184296_) {
            this.f_184287_ = p_184295_;
            this.f_184288_ = p_184296_;
        }

        public Builder<C, I> m_216114_(float p_216115_, float p_216116_) {
            return this.m_184302_(p_216115_, new Constant(this.f_184288_.m_183321_(Float.valueOf(p_216116_))), 0.0f);
        }

        public Builder<C, I> m_184298_(float p_184299_, float p_184300_, float p_184301_) {
            return this.m_184302_(p_184299_, new Constant(this.f_184288_.m_183321_(Float.valueOf(p_184300_))), p_184301_);
        }

        public Builder<C, I> m_216117_(float p_216118_, CubicSpline<C, I> p_216119_) {
            return this.m_184302_(p_216118_, p_216119_, 0.0f);
        }

        private Builder<C, I> m_184302_(float p_184303_, CubicSpline<C, I> p_184304_, float p_184305_) {
            if (!this.f_184289_.isEmpty() && p_184303_ <= this.f_184289_.getFloat(this.f_184289_.size() - 1)) {
                throw new IllegalArgumentException("Please register points in ascending order");
            }
            this.f_184289_.add(p_184303_);
            this.f_184290_.add(p_184304_);
            this.f_184291_.add(p_184305_);
            return this;
        }

        public CubicSpline<C, I> m_184297_() {
            if (this.f_184289_.isEmpty()) {
                throw new IllegalStateException("No elements added");
            }
            return Multipoint.m_216143_(this.f_184287_, this.f_184289_.toFloatArray(), ImmutableList.copyOf(this.f_184290_), this.f_184291_.toFloatArray());
        }
    }

    @VisibleForDebug
    public static final class Multipoint<C, I extends ToFloatFunction<C>>
    extends Record
    implements CubicSpline<C, I> {
        private final I f_184319_;
        private final float[] f_184320_;
        private final List<CubicSpline<C, I>> f_184321_;
        private final float[] f_184322_;
        private final float f_216124_;
        private final float f_216125_;

        public Multipoint(I f_184319_, float[] f_184320_, List<CubicSpline<C, I>> f_184321_, float[] f_184322_, float f_216124_, float f_216125_) {
            Multipoint.m_216151_(f_184320_, f_184321_, f_184322_);
            this.f_184319_ = f_184319_;
            this.f_184320_ = f_184320_;
            this.f_184321_ = f_184321_;
            this.f_184322_ = f_184322_;
            this.f_216124_ = f_216124_;
            this.f_216125_ = f_216125_;
        }

        static <C, I extends ToFloatFunction<C>> Multipoint<C, I> m_216143_(I p_216144_, float[] p_216145_, List<CubicSpline<C, I>> p_216146_, float[] p_216147_) {
            Multipoint.m_216151_(p_216145_, p_216146_, p_216147_);
            int $$4 = p_216145_.length - 1;
            float $$5 = Float.POSITIVE_INFINITY;
            float $$6 = Float.NEGATIVE_INFINITY;
            float $$7 = p_216144_.m_213850_();
            float $$8 = p_216144_.m_213849_();
            if ($$7 < p_216145_[0]) {
                float $$9 = Multipoint.m_216133_($$7, p_216145_, p_216146_.get(0).m_213850_(), p_216147_, 0);
                float $$10 = Multipoint.m_216133_($$7, p_216145_, p_216146_.get(0).m_213849_(), p_216147_, 0);
                $$5 = Math.min($$5, Math.min($$9, $$10));
                $$6 = Math.max($$6, Math.max($$9, $$10));
            }
            if ($$8 > p_216145_[$$4]) {
                float $$11 = Multipoint.m_216133_($$8, p_216145_, p_216146_.get($$4).m_213850_(), p_216147_, $$4);
                float $$12 = Multipoint.m_216133_($$8, p_216145_, p_216146_.get($$4).m_213849_(), p_216147_, $$4);
                $$5 = Math.min($$5, Math.min($$11, $$12));
                $$6 = Math.max($$6, Math.max($$11, $$12));
            }
            for (CubicSpline<C, I> $$13 : p_216146_) {
                $$5 = Math.min($$5, $$13.m_213850_());
                $$6 = Math.max($$6, $$13.m_213849_());
            }
            for (int $$14 = 0; $$14 < $$4; ++$$14) {
                float $$15 = p_216145_[$$14];
                float $$16 = p_216145_[$$14 + 1];
                float $$17 = $$16 - $$15;
                CubicSpline<C, I> $$18 = p_216146_.get($$14);
                CubicSpline<C, I> $$19 = p_216146_.get($$14 + 1);
                float $$20 = $$18.m_213850_();
                float $$21 = $$18.m_213849_();
                float $$22 = $$19.m_213850_();
                float $$23 = $$19.m_213849_();
                float $$24 = p_216147_[$$14];
                float $$25 = p_216147_[$$14 + 1];
                if ($$24 == 0.0f && $$25 == 0.0f) continue;
                float $$26 = $$24 * $$17;
                float $$27 = $$25 * $$17;
                float $$28 = Math.min($$20, $$22);
                float $$29 = Math.max($$21, $$23);
                float $$30 = $$26 - $$23 + $$20;
                float $$31 = $$26 - $$22 + $$21;
                float $$32 = -$$27 + $$22 - $$21;
                float $$33 = -$$27 + $$23 - $$20;
                float $$34 = Math.min($$30, $$32);
                float $$35 = Math.max($$31, $$33);
                $$5 = Math.min($$5, $$28 + 0.25f * $$34);
                $$6 = Math.max($$6, $$29 + 0.25f * $$35);
            }
            return new Multipoint<C, I>(p_216144_, p_216145_, p_216146_, p_216147_, $$5, $$6);
        }

        private static float m_216133_(float p_216134_, float[] p_216135_, float p_216136_, float[] p_216137_, int p_216138_) {
            float $$5 = p_216137_[p_216138_];
            if ($$5 == 0.0f) {
                return p_216136_;
            }
            return p_216136_ + $$5 * (p_216134_ - p_216135_[p_216138_]);
        }

        private static <C, I extends ToFloatFunction<C>> void m_216151_(float[] p_216152_, List<CubicSpline<C, I>> p_216153_, float[] p_216154_) {
            if (p_216152_.length != p_216153_.size() || p_216152_.length != p_216154_.length) {
                throw new IllegalArgumentException("All lengths must be equal, got: " + p_216152_.length + " " + p_216153_.size() + " " + p_216154_.length);
            }
            if (p_216152_.length == 0) {
                throw new IllegalArgumentException("Cannot create a multipoint spline with no points");
            }
        }

        @Override
        public float m_183321_(C p_184340_) {
            float $$1 = this.f_184319_.m_183321_(p_184340_);
            int $$2 = Multipoint.m_216148_(this.f_184320_, $$1);
            int $$3 = this.f_184320_.length - 1;
            if ($$2 < 0) {
                return Multipoint.m_216133_($$1, this.f_184320_, this.f_184321_.get(0).m_183321_(p_184340_), this.f_184322_, 0);
            }
            if ($$2 == $$3) {
                return Multipoint.m_216133_($$1, this.f_184320_, this.f_184321_.get($$3).m_183321_(p_184340_), this.f_184322_, $$3);
            }
            float $$4 = this.f_184320_[$$2];
            float $$5 = this.f_184320_[$$2 + 1];
            float $$6 = ($$1 - $$4) / ($$5 - $$4);
            ToFloatFunction $$7 = this.f_184321_.get($$2);
            ToFloatFunction $$8 = this.f_184321_.get($$2 + 1);
            float $$9 = this.f_184322_[$$2];
            float $$10 = this.f_184322_[$$2 + 1];
            float $$11 = $$7.m_183321_(p_184340_);
            float $$12 = $$8.m_183321_(p_184340_);
            float $$13 = $$9 * ($$5 - $$4) - ($$12 - $$11);
            float $$14 = -$$10 * ($$5 - $$4) + ($$12 - $$11);
            float $$15 = Mth.m_14179_($$6, $$11, $$12) + $$6 * (1.0f - $$6) * Mth.m_14179_($$6, $$13, $$14);
            return $$15;
        }

        private static int m_216148_(float[] p_216149_, float p_216150_) {
            return Mth.m_14049_(0, p_216149_.length, p_216142_ -> p_216150_ < p_216149_[p_216142_]) - 1;
        }

        @Override
        @VisibleForTesting
        public String m_183628_() {
            return "Spline{coordinate=" + this.f_184319_ + ", locations=" + this.m_184334_(this.f_184320_) + ", derivatives=" + this.m_184334_(this.f_184322_) + ", values=" + this.f_184321_.stream().map(CubicSpline::m_183628_).collect(Collectors.joining(", ", "[", "]")) + "}";
        }

        private String m_184334_(float[] p_184335_) {
            return "[" + IntStream.range(0, p_184335_.length).mapToDouble(p_184338_ -> p_184335_[p_184338_]).mapToObj(p_184330_ -> String.format(Locale.ROOT, "%.3f", p_184330_)).collect(Collectors.joining(", ")) + "]";
        }

        @Override
        public CubicSpline<C, I> m_211396_(CoordinateVisitor<I> p_211585_) {
            return Multipoint.m_216143_((ToFloatFunction)p_211585_.m_216122_(this.f_184319_), this.f_184320_, this.f_184321_().stream().map(p_211588_ -> p_211588_.m_211396_(p_211585_)).toList(), this.f_184322_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Multipoint.class, "coordinate;locations;values;derivatives;minValue;maxValue", "f_184319_", "f_184320_", "f_184321_", "f_184322_", "f_216124_", "f_216125_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Multipoint.class, "coordinate;locations;values;derivatives;minValue;maxValue", "f_184319_", "f_184320_", "f_184321_", "f_184322_", "f_216124_", "f_216125_"}, this);
        }

        @Override
        public final boolean equals(Object p_184346_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Multipoint.class, "coordinate;locations;values;derivatives;minValue;maxValue", "f_184319_", "f_184320_", "f_184321_", "f_184322_", "f_216124_", "f_216125_"}, this, p_184346_);
        }

        public I f_184319_() {
            return this.f_184319_;
        }

        public float[] f_184320_() {
            return this.f_184320_;
        }

        public List<CubicSpline<C, I>> f_184321_() {
            return this.f_184321_;
        }

        public float[] f_184322_() {
            return this.f_184322_;
        }

        @Override
        public float m_213850_() {
            return this.f_216124_;
        }

        @Override
        public float m_213849_() {
            return this.f_216125_;
        }
    }

    public static interface CoordinateVisitor<I> {
        public I m_216122_(I var1);
    }
}

