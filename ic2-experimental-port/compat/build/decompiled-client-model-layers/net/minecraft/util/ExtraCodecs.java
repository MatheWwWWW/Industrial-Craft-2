/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Suppliers
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Codec$ResultFunction
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Decoder
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.Lifecycle
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.MapLike
 *  com.mojang.serialization.RecordBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package net.minecraft.util;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.HolderSet;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.mutable.MutableObject;

public class ExtraCodecs {
    public static final Codec<UUID> f_216157_ = UUIDUtil.f_235867_;
    public static final Codec<Integer> f_144628_ = ExtraCodecs.m_144633_(0, Integer.MAX_VALUE, p_184429_ -> "Value must be non-negative: " + p_184429_);
    public static final Codec<Integer> f_144629_ = ExtraCodecs.m_144633_(1, Integer.MAX_VALUE, p_184375_ -> "Value must be positive: " + p_184375_);
    public static final Codec<Float> f_184349_ = ExtraCodecs.m_184350_(0.0f, Float.MAX_VALUE, p_184373_ -> "Value must be positive: " + p_184373_);
    public static final Codec<Pattern> f_216158_ = Codec.STRING.comapFlatMap(p_216188_ -> {
        try {
            return DataResult.success((Object)Pattern.compile(p_216188_));
        }
        catch (PatternSyntaxException $$1) {
            return DataResult.error((String)("Invalid regex pattern '" + p_216188_ + "': " + $$1.getMessage()));
        }
    }, Pattern::pattern);
    public static final Codec<Instant> f_216159_ = ExtraCodecs.m_216170_(DateTimeFormatter.ISO_INSTANT);
    public static final Codec<byte[]> f_216160_ = Codec.STRING.comapFlatMap(p_216184_ -> {
        try {
            return DataResult.success((Object)Base64.getDecoder().decode((String)p_216184_));
        }
        catch (IllegalArgumentException $$1) {
            return DataResult.error((String)"Malformed base64 string");
        }
    }, p_216180_ -> Base64.getEncoder().encodeToString((byte[])p_216180_));
    public static final Codec<TagOrElementLocation> f_216161_ = Codec.STRING.comapFlatMap(p_216169_ -> p_216169_.startsWith("#") ? ResourceLocation.m_135837_(p_216169_.substring(1)).map(p_216182_ -> new TagOrElementLocation((ResourceLocation)p_216182_, true)) : ResourceLocation.m_135837_(p_216169_).map(p_216165_ -> new TagOrElementLocation((ResourceLocation)p_216165_, false)), TagOrElementLocation::m_216202_);
    public static final Function<Optional<Long>, OptionalLong> f_216162_ = p_216176_ -> p_216176_.map(OptionalLong::of).orElseGet(OptionalLong::empty);
    public static final Function<OptionalLong, Optional<Long>> f_216163_ = p_216178_ -> p_216178_.isPresent() ? Optional.of(p_216178_.getAsLong()) : Optional.empty();

    public static <F, S> Codec<Either<F, S>> m_144639_(Codec<F> p_144640_, Codec<S> p_144641_) {
        return new XorCodec<F, S>(p_144640_, p_144641_);
    }

    public static <P, I> Codec<I> m_184361_(Codec<P> p_184362_, String p_184363_, String p_184364_, BiFunction<P, P, DataResult<I>> p_184365_, Function<I, P> p_184366_, Function<I, P> p_184367_) {
        Codec $$6 = Codec.list(p_184362_).comapFlatMap(p_184398_ -> Util.m_143795_(p_184398_, 2).flatMap(p_184445_ -> {
            Object $$2 = p_184445_.get(0);
            Object $$3 = p_184445_.get(1);
            return (DataResult)p_184365_.apply($$2, $$3);
        }), p_184459_ -> ImmutableList.of(p_184366_.apply(p_184459_), p_184367_.apply(p_184459_)));
        Codec $$7 = RecordCodecBuilder.create(p_184360_ -> p_184360_.group((App)p_184362_.fieldOf(p_184363_).forGetter(Pair::getFirst), (App)p_184362_.fieldOf(p_184364_).forGetter(Pair::getSecond)).apply((Applicative)p_184360_, Pair::of)).comapFlatMap(p_184392_ -> (DataResult)p_184365_.apply(p_184392_.getFirst(), p_184392_.getSecond()), p_184449_ -> Pair.of(p_184366_.apply(p_184449_), p_184367_.apply(p_184449_)));
        Codec $$8 = new EitherCodec($$6, $$7).xmap(p_184355_ -> p_184355_.map(p_184461_ -> p_184461_, p_184455_ -> p_184455_), Either::left);
        return Codec.either(p_184362_, (Codec)$$8).comapFlatMap(p_184389_ -> (DataResult)p_184389_.map(p_184395_ -> (DataResult)p_184365_.apply(p_184395_, p_184395_), DataResult::success), p_184411_ -> {
            Object $$4;
            Object $$3 = p_184366_.apply(p_184411_);
            if (Objects.equals($$3, $$4 = p_184367_.apply(p_184411_))) {
                return Either.left($$3);
            }
            return Either.right((Object)p_184411_);
        });
    }

    public static <A> Codec.ResultFunction<A> m_184381_(final A p_184382_) {
        return new Codec.ResultFunction<A>(){

            public <T> DataResult<Pair<A, T>> apply(DynamicOps<T> p_184466_, T p_184467_, DataResult<Pair<A, T>> p_184468_) {
                MutableObject $$3 = new MutableObject();
                Optional $$4 = p_184468_.resultOrPartial(arg_0 -> ((MutableObject)$$3).setValue(arg_0));
                if ($$4.isPresent()) {
                    return p_184468_;
                }
                return DataResult.error((String)("(" + (String)$$3.getValue() + " -> using default)"), (Object)Pair.of((Object)p_184382_, p_184467_));
            }

            public <T> DataResult<T> coApply(DynamicOps<T> p_184470_, A p_184471_, DataResult<T> p_184472_) {
                return p_184472_;
            }

            public String toString() {
                return "OrElsePartial[" + p_184382_ + "]";
            }
        };
    }

    public static <E> Codec<E> m_184421_(ToIntFunction<E> p_184422_, IntFunction<E> p_184423_, int p_184424_) {
        return Codec.INT.flatXmap(p_184414_ -> Optional.ofNullable(p_184423_.apply((int)p_184414_)).map(DataResult::success).orElseGet(() -> DataResult.error((String)("Unknown element id: " + p_184414_))), p_184420_ -> {
            int $$3 = p_184422_.applyAsInt(p_184420_);
            return $$3 == p_184424_ ? DataResult.error((String)("Element with unknown id: " + p_184420_)) : DataResult.success((Object)$$3);
        });
    }

    public static <E> Codec<E> m_184405_(Function<E, String> p_184406_, Function<String, E> p_184407_) {
        return Codec.STRING.flatXmap(p_184404_ -> Optional.ofNullable(p_184407_.apply((String)p_184404_)).map(DataResult::success).orElseGet(() -> DataResult.error((String)("Unknown element name:" + p_184404_))), p_184401_ -> Optional.ofNullable((String)p_184406_.apply(p_184401_)).map(DataResult::success).orElseGet(() -> DataResult.error((String)("Element with unknown name: " + p_184401_))));
    }

    public static <E> Codec<E> m_184425_(final Codec<E> p_184426_, final Codec<E> p_184427_) {
        return new Codec<E>(){

            public <T> DataResult<T> encode(E p_184483_, DynamicOps<T> p_184484_, T p_184485_) {
                if (p_184484_.compressMaps()) {
                    return p_184427_.encode(p_184483_, p_184484_, p_184485_);
                }
                return p_184426_.encode(p_184483_, p_184484_, p_184485_);
            }

            public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> p_184480_, T p_184481_) {
                if (p_184480_.compressMaps()) {
                    return p_184427_.decode(p_184480_, p_184481_);
                }
                return p_184426_.decode(p_184480_, p_184481_);
            }

            public String toString() {
                return p_184426_ + " orCompressed " + p_184427_;
            }
        };
    }

    public static <E> Codec<E> m_184368_(Codec<E> p_184369_, final Function<E, Lifecycle> p_184370_, final Function<E, Lifecycle> p_184371_) {
        return p_184369_.mapResult(new Codec.ResultFunction<E>(){

            public <T> DataResult<Pair<E, T>> apply(DynamicOps<T> p_184497_, T p_184498_, DataResult<Pair<E, T>> p_184499_) {
                return p_184499_.result().map(p_184495_ -> p_184499_.setLifecycle((Lifecycle)p_184370_.apply(p_184495_.getFirst()))).orElse(p_184499_);
            }

            public <T> DataResult<T> coApply(DynamicOps<T> p_184501_, E p_184502_, DataResult<T> p_184503_) {
                return p_184503_.setLifecycle((Lifecycle)p_184371_.apply(p_184502_));
            }

            public String toString() {
                return "WithLifecycle[" + p_184370_ + " " + p_184371_ + "]";
            }
        });
    }

    private static <N extends Number> Function<N, DataResult<N>> m_144644_(N p_144645_, N p_144646_, Function<N, String> p_144647_) {
        return p_184438_ -> {
            if (((Comparable)((Object)p_184438_)).compareTo(p_144645_) >= 0 && ((Comparable)((Object)p_184438_)).compareTo(p_144646_) <= 0) {
                return DataResult.success((Object)p_184438_);
            }
            return DataResult.error((String)((String)p_144647_.apply(p_184438_)));
        };
    }

    private static Codec<Integer> m_144633_(int p_144634_, int p_144635_, Function<Integer, String> p_144636_) {
        Function<Integer, DataResult<Integer>> $$3 = ExtraCodecs.m_144644_(p_144634_, p_144635_, p_144636_);
        return Codec.INT.flatXmap($$3, $$3);
    }

    private static <N extends Number> Function<N, DataResult<N>> m_184430_(N p_184431_, N p_184432_, Function<N, String> p_184433_) {
        return p_184380_ -> {
            if (((Comparable)((Object)p_184380_)).compareTo(p_184431_) > 0 && ((Comparable)((Object)p_184380_)).compareTo(p_184432_) <= 0) {
                return DataResult.success((Object)p_184380_);
            }
            return DataResult.error((String)((String)p_184433_.apply(p_184380_)));
        };
    }

    private static Codec<Float> m_184350_(float p_184351_, float p_184352_, Function<Float, String> p_184353_) {
        Function<Float, DataResult<Float>> $$3 = ExtraCodecs.m_184430_(Float.valueOf(p_184351_), Float.valueOf(p_184352_), p_184353_);
        return Codec.FLOAT.flatXmap($$3, $$3);
    }

    public static <T> Function<List<T>, DataResult<List<T>>> m_144632_() {
        return p_184442_ -> {
            if (p_184442_.isEmpty()) {
                return DataResult.error((String)"List must have contents");
            }
            return DataResult.success((Object)p_184442_);
        };
    }

    public static <T> Codec<List<T>> m_144637_(Codec<List<T>> p_144638_) {
        return p_144638_.flatXmap(ExtraCodecs.m_144632_(), ExtraCodecs.m_144632_());
    }

    public static <T> Function<HolderSet<T>, DataResult<HolderSet<T>>> m_203981_() {
        return p_203975_ -> {
            if (p_203975_.m_203440_().right().filter(List::isEmpty).isPresent()) {
                return DataResult.error((String)"List must have contents");
            }
            return DataResult.success((Object)p_203975_);
        };
    }

    public static <T> Codec<HolderSet<T>> m_203982_(Codec<HolderSet<T>> p_203983_) {
        return p_203983_.flatXmap(ExtraCodecs.m_203981_(), ExtraCodecs.m_203981_());
    }

    public static <A> Codec<A> m_184415_(Supplier<Codec<A>> p_184416_) {
        return new LazyInitializedCodec<A>(p_184416_);
    }

    public static <E> MapCodec<E> m_203976_(Function<DynamicOps<?>, DataResult<E>> p_203977_) {
        class ContextRetrievalCodec
        extends MapCodec<E> {
            final /* synthetic */ Function f_203986_;

            ContextRetrievalCodec(Function function) {
                this.f_203986_ = function;
            }

            public <T> RecordBuilder<T> encode(E p_203993_, DynamicOps<T> p_203994_, RecordBuilder<T> p_203995_) {
                return p_203995_;
            }

            public <T> DataResult<E> decode(DynamicOps<T> p_203990_, MapLike<T> p_203991_) {
                return (DataResult)this.f_203986_.apply(p_203990_);
            }

            public String toString() {
                return "ContextRetrievalCodec[" + this.f_203986_ + "]";
            }

            public <T> Stream<T> keys(DynamicOps<T> p_203997_) {
                return Stream.empty();
            }
        }
        return new ContextRetrievalCodec(p_203977_);
    }

    public static <E, L extends Collection<E>, T> Function<L, DataResult<L>> m_203984_(Function<E, T> p_203985_) {
        return p_203980_ -> {
            Iterator $$2 = p_203980_.iterator();
            if ($$2.hasNext()) {
                Object $$3 = p_203985_.apply($$2.next());
                while ($$2.hasNext()) {
                    Object $$4 = $$2.next();
                    Object $$5 = p_203985_.apply($$4);
                    if ($$5 == $$3) continue;
                    return DataResult.error((String)("Mixed type list: element " + $$4 + " had type " + $$5 + ", but list is of type " + $$3));
                }
            }
            return DataResult.success((Object)p_203980_, (Lifecycle)Lifecycle.stable());
        };
    }

    public static <A> Codec<A> m_216185_(final Codec<A> p_216186_) {
        return Codec.of(p_216186_, (Decoder)new Decoder<A>(){

            public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> p_216193_, T p_216194_) {
                try {
                    return p_216186_.decode(p_216193_, p_216194_);
                }
                catch (Exception $$2) {
                    return DataResult.error((String)("Cauch exception decoding " + p_216194_ + ": " + $$2.getMessage()));
                }
            }
        });
    }

    public static Codec<Instant> m_216170_(DateTimeFormatter p_216171_) {
        return Codec.STRING.comapFlatMap(p_216174_ -> {
            try {
                return DataResult.success((Object)Instant.from(p_216171_.parse((CharSequence)p_216174_)));
            }
            catch (Exception $$2) {
                return DataResult.error((String)$$2.getMessage());
            }
        }, p_216171_::format);
    }

    public static MapCodec<OptionalLong> m_216166_(MapCodec<Optional<Long>> p_216167_) {
        return p_216167_.xmap(f_216162_, f_216163_);
    }

    static final class XorCodec<F, S>
    implements Codec<Either<F, S>> {
        private final Codec<F> f_144657_;
        private final Codec<S> f_144658_;

        public XorCodec(Codec<F> p_144660_, Codec<S> p_144661_) {
            this.f_144657_ = p_144660_;
            this.f_144658_ = p_144661_;
        }

        public <T> DataResult<Pair<Either<F, S>, T>> decode(DynamicOps<T> p_144679_, T p_144680_) {
            DataResult $$2 = this.f_144657_.decode(p_144679_, p_144680_).map(p_144673_ -> p_144673_.mapFirst(Either::left));
            DataResult $$3 = this.f_144658_.decode(p_144679_, p_144680_).map(p_144667_ -> p_144667_.mapFirst(Either::right));
            Optional $$4 = $$2.result();
            Optional $$5 = $$3.result();
            if ($$4.isPresent() && $$5.isPresent()) {
                return DataResult.error((String)("Both alternatives read successfully, can not pick the correct one; first: " + $$4.get() + " second: " + $$5.get()), (Object)((Pair)$$4.get()));
            }
            return $$4.isPresent() ? $$2 : $$3;
        }

        public <T> DataResult<T> encode(Either<F, S> p_144663_, DynamicOps<T> p_144664_, T p_144665_) {
            return (DataResult)p_144663_.map(p_144677_ -> this.f_144657_.encode(p_144677_, p_144664_, p_144665_), p_144671_ -> this.f_144658_.encode(p_144671_, p_144664_, p_144665_));
        }

        public boolean equals(Object p_144686_) {
            if (this == p_144686_) {
                return true;
            }
            if (p_144686_ == null || this.getClass() != p_144686_.getClass()) {
                return false;
            }
            XorCodec $$1 = (XorCodec)p_144686_;
            return Objects.equals(this.f_144657_, $$1.f_144657_) && Objects.equals(this.f_144658_, $$1.f_144658_);
        }

        public int hashCode() {
            return Objects.hash(this.f_144657_, this.f_144658_);
        }

        public String toString() {
            return "XorCodec[" + this.f_144657_ + ", " + this.f_144658_ + "]";
        }

        public /* synthetic */ DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
            return this.encode((Either)object, dynamicOps, object2);
        }
    }

    static final class EitherCodec<F, S>
    implements Codec<Either<F, S>> {
        private final Codec<F> f_184505_;
        private final Codec<S> f_184506_;

        public EitherCodec(Codec<F> p_184508_, Codec<S> p_184509_) {
            this.f_184505_ = p_184508_;
            this.f_184506_ = p_184509_;
        }

        public <T> DataResult<Pair<Either<F, S>, T>> decode(DynamicOps<T> p_184530_, T p_184531_) {
            DataResult $$2 = this.f_184505_.decode(p_184530_, p_184531_).map(p_184524_ -> p_184524_.mapFirst(Either::left));
            if (!$$2.error().isPresent()) {
                return $$2;
            }
            DataResult $$3 = this.f_184506_.decode(p_184530_, p_184531_).map(p_184515_ -> p_184515_.mapFirst(Either::right));
            if (!$$3.error().isPresent()) {
                return $$3;
            }
            return $$2.apply2((p_184517_, p_184518_) -> p_184518_, $$3);
        }

        public <T> DataResult<T> encode(Either<F, S> p_184511_, DynamicOps<T> p_184512_, T p_184513_) {
            return (DataResult)p_184511_.map(p_184528_ -> this.f_184505_.encode(p_184528_, p_184512_, p_184513_), p_184522_ -> this.f_184506_.encode(p_184522_, p_184512_, p_184513_));
        }

        public boolean equals(Object p_184537_) {
            if (this == p_184537_) {
                return true;
            }
            if (p_184537_ == null || this.getClass() != p_184537_.getClass()) {
                return false;
            }
            EitherCodec $$1 = (EitherCodec)p_184537_;
            return Objects.equals(this.f_184505_, $$1.f_184505_) && Objects.equals(this.f_184506_, $$1.f_184506_);
        }

        public int hashCode() {
            return Objects.hash(this.f_184505_, this.f_184506_);
        }

        public String toString() {
            return "EitherCodec[" + this.f_184505_ + ", " + this.f_184506_ + "]";
        }

        public /* synthetic */ DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
            return this.encode((Either)object, dynamicOps, object2);
        }
    }

    record LazyInitializedCodec<A>(Supplier<Codec<A>> f_184540_) implements Codec<A>
    {
        LazyInitializedCodec(Supplier<Codec<A>> f_184540_) {
            this.f_184540_ = f_184540_ = Suppliers.memoize(() -> f_184540_.get());
        }

        public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> p_184545_, T p_184546_) {
            return this.f_184540_.get().decode(p_184545_, p_184546_);
        }

        public <T> DataResult<T> encode(A p_184548_, DynamicOps<T> p_184549_, T p_184550_) {
            return this.f_184540_.get().encode(p_184548_, p_184549_, p_184550_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{LazyInitializedCodec.class, "delegate", "f_184540_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{LazyInitializedCodec.class, "delegate", "f_184540_"}, this);
        }

        @Override
        public final boolean equals(Object p_184552_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{LazyInitializedCodec.class, "delegate", "f_184540_"}, this, p_184552_);
        }
    }

    public record TagOrElementLocation(ResourceLocation f_216195_, boolean f_216196_) {
        @Override
        public String toString() {
            return this.m_216202_();
        }

        private String m_216202_() {
            return this.f_216196_ ? "#" + this.f_216195_ : this.f_216195_.toString();
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{TagOrElementLocation.class, "id;tag", "f_216195_", "f_216196_"}, this);
        }

        @Override
        public final boolean equals(Object p_216204_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{TagOrElementLocation.class, "id;tag", "f_216195_", "f_216196_"}, this, p_216204_);
        }
    }
}

