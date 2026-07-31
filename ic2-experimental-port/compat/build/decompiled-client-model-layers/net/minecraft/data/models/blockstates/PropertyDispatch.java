/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 */
package net.minecraft.data.models.blockstates;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.data.models.blockstates.Selector;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.world.level.block.state.properties.Property;

public abstract class PropertyDispatch {
    private final Map<Selector, List<Variant>> f_125291_ = Maps.newHashMap();

    protected void m_125319_(Selector p_125320_, List<Variant> p_125321_) {
        List<Variant> $$2 = this.f_125291_.put(p_125320_, p_125321_);
        if ($$2 != null) {
            throw new IllegalStateException("Value " + p_125320_ + " is already defined");
        }
    }

    Map<Selector, List<Variant>> m_125293_() {
        this.m_125322_();
        return ImmutableMap.copyOf(this.f_125291_);
    }

    private void m_125322_() {
        List<Property<?>> $$0 = this.m_7336_();
        Stream<Selector> $$1 = Stream.of(Selector.m_125485_());
        for (Property<?> $$2 : $$0) {
            $$1 = $$1.flatMap(p_125316_ -> $$2.m_61702_().map(p_125316_::m_125486_));
        }
        List $$3 = $$1.filter(p_125318_ -> !this.f_125291_.containsKey(p_125318_)).collect(Collectors.toList());
        if (!$$3.isEmpty()) {
            throw new IllegalStateException("Missing definition for properties: " + $$3);
        }
    }

    abstract List<Property<?>> m_7336_();

    public static <T1 extends Comparable<T1>> C1<T1> m_125294_(Property<T1> p_125295_) {
        return new C1<T1>(p_125295_);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> C2<T1, T2> m_125296_(Property<T1> p_125297_, Property<T2> p_125298_) {
        return new C2<T1, T2>(p_125297_, p_125298_);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> C3<T1, T2, T3> m_125299_(Property<T1> p_125300_, Property<T2> p_125301_, Property<T3> p_125302_) {
        return new C3<T1, T2, T3>(p_125300_, p_125301_, p_125302_);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> C4<T1, T2, T3, T4> m_125303_(Property<T1> p_125304_, Property<T2> p_125305_, Property<T3> p_125306_, Property<T4> p_125307_) {
        return new C4<T1, T2, T3, T4>(p_125304_, p_125305_, p_125306_, p_125307_);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> C5<T1, T2, T3, T4, T5> m_125308_(Property<T1> p_125309_, Property<T2> p_125310_, Property<T3> p_125311_, Property<T4> p_125312_, Property<T5> p_125313_) {
        return new C5<T1, T2, T3, T4, T5>(p_125309_, p_125310_, p_125311_, p_125312_, p_125313_);
    }

    public static class C1<T1 extends Comparable<T1>>
    extends PropertyDispatch {
        private final Property<T1> f_125323_;

        C1(Property<T1> p_125325_) {
            this.f_125323_ = p_125325_;
        }

        @Override
        public List<Property<?>> m_7336_() {
            return ImmutableList.of(this.f_125323_);
        }

        public C1<T1> m_125332_(T1 p_125333_, List<Variant> p_125334_) {
            Selector $$2 = Selector.m_125490_(this.f_125323_.m_61699_(p_125333_));
            this.m_125319_($$2, p_125334_);
            return this;
        }

        public C1<T1> m_125329_(T1 p_125330_, Variant p_125331_) {
            return this.m_125332_(p_125330_, Collections.singletonList(p_125331_));
        }

        public PropertyDispatch m_125335_(Function<T1, Variant> p_125336_) {
            this.f_125323_.m_6908_().forEach(p_125340_ -> this.m_125329_(p_125340_, (Variant)p_125336_.apply(p_125340_)));
            return this;
        }

        public PropertyDispatch m_176313_(Function<T1, List<Variant>> p_176314_) {
            this.f_125323_.m_6908_().forEach(p_176312_ -> this.m_125332_(p_176312_, (List)p_176314_.apply(p_176312_)));
            return this;
        }
    }

    public static class C2<T1 extends Comparable<T1>, T2 extends Comparable<T2>>
    extends PropertyDispatch {
        private final Property<T1> f_125341_;
        private final Property<T2> f_125342_;

        C2(Property<T1> p_125344_, Property<T2> p_125345_) {
            this.f_125341_ = p_125344_;
            this.f_125342_ = p_125345_;
        }

        @Override
        public List<Property<?>> m_7336_() {
            return ImmutableList.of(this.f_125341_, this.f_125342_);
        }

        public C2<T1, T2> m_125354_(T1 p_125355_, T2 p_125356_, List<Variant> p_125357_) {
            Selector $$3 = Selector.m_125490_(this.f_125341_.m_61699_(p_125355_), this.f_125342_.m_61699_(p_125356_));
            this.m_125319_($$3, p_125357_);
            return this;
        }

        public C2<T1, T2> m_125350_(T1 p_125351_, T2 p_125352_, Variant p_125353_) {
            return this.m_125354_(p_125351_, p_125352_, Collections.singletonList(p_125353_));
        }

        public PropertyDispatch m_125362_(BiFunction<T1, T2, Variant> p_125363_) {
            this.f_125341_.m_6908_().forEach(p_125376_ -> this.f_125342_.m_6908_().forEach(p_176322_ -> this.m_125350_(p_125376_, p_176322_, (Variant)p_125363_.apply(p_125376_, p_176322_))));
            return this;
        }

        public PropertyDispatch m_125372_(BiFunction<T1, T2, List<Variant>> p_125373_) {
            this.f_125341_.m_6908_().forEach(p_125366_ -> this.f_125342_.m_6908_().forEach(p_176318_ -> this.m_125354_(p_125366_, p_176318_, (List)p_125373_.apply(p_125366_, p_176318_))));
            return this;
        }
    }

    public static class C3<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>>
    extends PropertyDispatch {
        private final Property<T1> f_125377_;
        private final Property<T2> f_125378_;
        private final Property<T3> f_125379_;

        C3(Property<T1> p_125381_, Property<T2> p_125382_, Property<T3> p_125383_) {
            this.f_125377_ = p_125381_;
            this.f_125378_ = p_125382_;
            this.f_125379_ = p_125383_;
        }

        @Override
        public List<Property<?>> m_7336_() {
            return ImmutableList.of(this.f_125377_, this.f_125378_, this.f_125379_);
        }

        public C3<T1, T2, T3> m_125396_(T1 p_125397_, T2 p_125398_, T3 p_125399_, List<Variant> p_125400_) {
            Selector $$4 = Selector.m_125490_(this.f_125377_.m_61699_(p_125397_), this.f_125378_.m_61699_(p_125398_), this.f_125379_.m_61699_(p_125399_));
            this.m_125319_($$4, p_125400_);
            return this;
        }

        public C3<T1, T2, T3> m_125391_(T1 p_125392_, T2 p_125393_, T3 p_125394_, Variant p_125395_) {
            return this.m_125396_(p_125392_, p_125393_, p_125394_, Collections.singletonList(p_125395_));
        }

        public PropertyDispatch m_125389_(TriFunction<T1, T2, T3, Variant> p_125390_) {
            this.f_125377_.m_6908_().forEach(p_125404_ -> this.f_125378_.m_6908_().forEach(p_176343_ -> this.f_125379_.m_6908_().forEach(p_176339_ -> this.m_125391_(p_125404_, p_176343_, p_176339_, (Variant)p_125390_.m_125475_(p_125404_, p_176343_, p_176339_)))));
            return this;
        }

        public PropertyDispatch m_176344_(TriFunction<T1, T2, T3, List<Variant>> p_176345_) {
            this.f_125377_.m_6908_().forEach(p_176334_ -> this.f_125378_.m_6908_().forEach(p_176331_ -> this.f_125379_.m_6908_().forEach(p_176327_ -> this.m_125396_(p_176334_, p_176331_, p_176327_, (List)p_176345_.m_125475_(p_176334_, p_176331_, p_176327_)))));
            return this;
        }
    }

    public static class C4<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>>
    extends PropertyDispatch {
        private final Property<T1> f_125414_;
        private final Property<T2> f_125415_;
        private final Property<T3> f_125416_;
        private final Property<T4> f_125417_;

        C4(Property<T1> p_125419_, Property<T2> p_125420_, Property<T3> p_125421_, Property<T4> p_125422_) {
            this.f_125414_ = p_125419_;
            this.f_125415_ = p_125420_;
            this.f_125416_ = p_125421_;
            this.f_125417_ = p_125422_;
        }

        @Override
        public List<Property<?>> m_7336_() {
            return ImmutableList.of(this.f_125414_, this.f_125415_, this.f_125416_, this.f_125417_);
        }

        public C4<T1, T2, T3, T4> m_125435_(T1 p_125436_, T2 p_125437_, T3 p_125438_, T4 p_125439_, List<Variant> p_125440_) {
            Selector $$5 = Selector.m_125490_(this.f_125414_.m_61699_(p_125436_), this.f_125415_.m_61699_(p_125437_), this.f_125416_.m_61699_(p_125438_), this.f_125417_.m_61699_(p_125439_));
            this.m_125319_($$5, p_125440_);
            return this;
        }

        public C4<T1, T2, T3, T4> m_125429_(T1 p_125430_, T2 p_125431_, T3 p_125432_, T4 p_125433_, Variant p_125434_) {
            return this.m_125435_(p_125430_, p_125431_, p_125432_, p_125433_, Collections.singletonList(p_125434_));
        }

        public PropertyDispatch m_176361_(QuadFunction<T1, T2, T3, T4, Variant> p_176362_) {
            this.f_125414_.m_6908_().forEach(p_176385_ -> this.f_125415_.m_6908_().forEach(p_176380_ -> this.f_125416_.m_6908_().forEach(p_176376_ -> this.f_125417_.m_6908_().forEach(p_176371_ -> this.m_125429_(p_176385_, p_176380_, p_176376_, p_176371_, (Variant)p_176362_.m_176446_(p_176385_, p_176380_, p_176376_, p_176371_))))));
            return this;
        }

        public PropertyDispatch m_176381_(QuadFunction<T1, T2, T3, T4, List<Variant>> p_176382_) {
            this.f_125414_.m_6908_().forEach(p_176365_ -> this.f_125415_.m_6908_().forEach(p_176360_ -> this.f_125416_.m_6908_().forEach(p_176356_ -> this.f_125417_.m_6908_().forEach(p_176351_ -> this.m_125435_(p_176365_, p_176360_, p_176356_, p_176351_, (List)p_176382_.m_176446_(p_176365_, p_176360_, p_176356_, p_176351_))))));
            return this;
        }
    }

    public static class C5<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>>
    extends PropertyDispatch {
        private final Property<T1> f_125442_;
        private final Property<T2> f_125443_;
        private final Property<T3> f_125444_;
        private final Property<T4> f_125445_;
        private final Property<T5> f_125446_;

        C5(Property<T1> p_125448_, Property<T2> p_125449_, Property<T3> p_125450_, Property<T4> p_125451_, Property<T5> p_125452_) {
            this.f_125442_ = p_125448_;
            this.f_125443_ = p_125449_;
            this.f_125444_ = p_125450_;
            this.f_125445_ = p_125451_;
            this.f_125446_ = p_125452_;
        }

        @Override
        public List<Property<?>> m_7336_() {
            return ImmutableList.of(this.f_125442_, this.f_125443_, this.f_125444_, this.f_125445_, this.f_125446_);
        }

        public C5<T1, T2, T3, T4, T5> m_125467_(T1 p_125468_, T2 p_125469_, T3 p_125470_, T4 p_125471_, T5 p_125472_, List<Variant> p_125473_) {
            Selector $$6 = Selector.m_125490_(this.f_125442_.m_61699_(p_125468_), this.f_125443_.m_61699_(p_125469_), this.f_125444_.m_61699_(p_125470_), this.f_125445_.m_61699_(p_125471_), this.f_125446_.m_61699_(p_125472_));
            this.m_125319_($$6, p_125473_);
            return this;
        }

        public C5<T1, T2, T3, T4, T5> m_125460_(T1 p_125461_, T2 p_125462_, T3 p_125463_, T4 p_125464_, T5 p_125465_, Variant p_125466_) {
            return this.m_125467_(p_125461_, p_125462_, p_125463_, p_125464_, p_125465_, Collections.singletonList(p_125466_));
        }

        public PropertyDispatch m_176408_(PentaFunction<T1, T2, T3, T4, T5, Variant> p_176409_) {
            this.f_125442_.m_6908_().forEach(p_176439_ -> this.f_125443_.m_6908_().forEach(p_176434_ -> this.f_125444_.m_6908_().forEach(p_176430_ -> this.f_125445_.m_6908_().forEach(p_176425_ -> this.f_125446_.m_6908_().forEach(p_176419_ -> this.m_125460_(p_176439_, p_176434_, p_176430_, p_176425_, p_176419_, (Variant)p_176409_.m_176440_(p_176439_, p_176434_, p_176430_, p_176425_, p_176419_)))))));
            return this;
        }

        public PropertyDispatch m_176435_(PentaFunction<T1, T2, T3, T4, T5, List<Variant>> p_176436_) {
            this.f_125442_.m_6908_().forEach(p_176412_ -> this.f_125443_.m_6908_().forEach(p_176407_ -> this.f_125444_.m_6908_().forEach(p_176403_ -> this.f_125445_.m_6908_().forEach(p_176398_ -> this.f_125446_.m_6908_().forEach(p_176392_ -> this.m_125467_(p_176412_, p_176407_, p_176403_, p_176398_, p_176392_, (List)p_176436_.m_176440_(p_176412_, p_176407_, p_176403_, p_176398_, p_176392_)))))));
            return this;
        }
    }

    @FunctionalInterface
    public static interface PentaFunction<P1, P2, P3, P4, P5, R> {
        public R m_176440_(P1 var1, P2 var2, P3 var3, P4 var4, P5 var5);
    }

    @FunctionalInterface
    public static interface QuadFunction<P1, P2, P3, P4, R> {
        public R m_176446_(P1 var1, P2 var2, P3 var3, P4 var4);
    }

    @FunctionalInterface
    public static interface TriFunction<P1, P2, P3, R> {
        public R m_125475_(P1 var1, P2 var2, P3 var3);
    }
}

