/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.util.Either
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  org.slf4j.Logger
 */
package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractOptionSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.TooltipAccessor;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.OptionEnum;
import org.slf4j.Logger;

public final class OptionInstance<T> {
    private static final Logger f_231472_ = LogUtils.getLogger();
    public static final Enum<Boolean> f_231471_ = new Enum(ImmutableList.of((Object)Boolean.TRUE, (Object)Boolean.FALSE), Codec.BOOL);
    private static final int f_231473_ = 200;
    private final TooltipSupplierFactory<T> f_231474_;
    final Function<T, Component> f_231475_;
    private final ValueSet<T> f_231476_;
    private final Codec<T> f_231477_;
    private final T f_231478_;
    private final Consumer<T> f_231479_;
    final Component f_231480_;
    T f_231481_;

    public static OptionInstance<Boolean> m_231528_(String p_231529_, boolean p_231530_, Consumer<Boolean> p_231531_) {
        return OptionInstance.m_231520_(p_231529_, OptionInstance.m_231498_(), p_231530_, p_231531_);
    }

    public static OptionInstance<Boolean> m_231525_(String p_231526_, boolean p_231527_) {
        return OptionInstance.m_231520_(p_231526_, OptionInstance.m_231498_(), p_231527_, p_231548_ -> {});
    }

    public static OptionInstance<Boolean> m_231516_(String p_231517_, TooltipSupplierFactory<Boolean> p_231518_, boolean p_231519_) {
        return OptionInstance.m_231520_(p_231517_, p_231518_, p_231519_, p_231513_ -> {});
    }

    public static OptionInstance<Boolean> m_231520_(String p_231521_, TooltipSupplierFactory<Boolean> p_231522_, boolean p_231523_, Consumer<Boolean> p_231524_) {
        return new OptionInstance<Boolean>(p_231521_, p_231522_, (p_231544_, p_231545_) -> p_231545_ != false ? CommonComponents.f_130653_ : CommonComponents.f_130654_, f_231471_, p_231523_, p_231524_);
    }

    public OptionInstance(String p_231492_, TooltipSupplierFactory<T> p_231493_, CaptionBasedToString<T> p_231494_, ValueSet<T> p_231495_, T p_231496_, Consumer<T> p_231497_) {
        this(p_231492_, p_231493_, p_231494_, p_231495_, p_231495_.m_213664_(), p_231496_, p_231497_);
    }

    public OptionInstance(String p_231484_, TooltipSupplierFactory<T> p_231485_, CaptionBasedToString<T> p_231486_, ValueSet<T> p_231487_, Codec<T> p_231488_, T p_231489_, Consumer<T> p_231490_) {
        this.f_231480_ = Component.m_237115_(p_231484_);
        this.f_231474_ = p_231485_;
        this.f_231475_ = p_231506_ -> p_231486_.m_231580_(this.f_231480_, p_231506_);
        this.f_231476_ = p_231487_;
        this.f_231477_ = p_231488_;
        this.f_231478_ = p_231489_;
        this.f_231479_ = p_231490_;
        this.f_231481_ = this.f_231478_;
    }

    public static <T> TooltipSupplierFactory<T> m_231498_() {
        return p_231500_ -> p_231553_ -> ImmutableList.of();
    }

    public static <T> TooltipSupplierFactory<T> m_231535_(Component p_231536_) {
        return p_231542_ -> {
            List<FormattedCharSequence> $$2 = OptionInstance.m_231501_(p_231542_, p_231536_);
            return p_231534_ -> $$2;
        };
    }

    public static <T extends OptionEnum> CaptionBasedToString<T> m_231546_() {
        return (p_231538_, p_231539_) -> p_231539_.m_216301_();
    }

    protected static List<FormattedCharSequence> m_231501_(Minecraft p_231502_, Component p_231503_) {
        return p_231502_.f_91062_.m_92923_(p_231503_, 200);
    }

    public AbstractWidget m_231507_(Options p_231508_, int p_231509_, int p_231510_, int p_231511_) {
        TooltipSupplier $$4 = (TooltipSupplier)this.f_231474_.apply(Minecraft.m_91087_());
        return this.f_231476_.m_213823_($$4, p_231508_, p_231509_, p_231510_, p_231511_).apply(this);
    }

    public T m_231551_() {
        return this.f_231481_;
    }

    public Codec<T> m_231554_() {
        return this.f_231477_;
    }

    public String toString() {
        return this.f_231480_.getString();
    }

    public void m_231514_(T p_231515_) {
        Object $$1 = this.f_231476_.m_214064_(p_231515_).orElseGet(() -> {
            f_231472_.error("Illegal option value " + p_231515_ + " for " + this.f_231480_);
            return this.f_231478_;
        });
        if (!Minecraft.m_91087_().m_91396_()) {
            this.f_231481_ = $$1;
            return;
        }
        if (!Objects.equals(this.f_231481_, $$1)) {
            this.f_231481_ = $$1;
            this.f_231479_.accept(this.f_231481_);
        }
    }

    public ValueSet<T> m_231555_() {
        return this.f_231476_;
    }

    public static interface TooltipSupplierFactory<T>
    extends Function<Minecraft, TooltipSupplier<T>> {
    }

    public static interface CaptionBasedToString<T> {
        public Component m_231580_(Component var1, T var2);
    }

    public static final class Enum<T>
    extends Record
    implements CycleableValueSet<T> {
        private final List<T> f_231625_;
        private final Codec<T> f_231626_;

        public Enum(List<T> f_231625_, Codec<T> f_231626_) {
            this.f_231625_ = f_231625_;
            this.f_231626_ = f_231626_;
        }

        @Override
        public Optional<T> m_214064_(T p_231632_) {
            return this.f_231625_.contains(p_231632_) ? Optional.of(p_231632_) : Optional.empty();
        }

        @Override
        public CycleButton.ValueListSupplier<T> m_213889_() {
            return CycleButton.ValueListSupplier.m_232504_(this.f_231625_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Enum.class, "values;codec", "f_231625_", "f_231626_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Enum.class, "values;codec", "f_231625_", "f_231626_"}, this);
        }

        @Override
        public final boolean equals(Object p_231635_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Enum.class, "values;codec", "f_231625_", "f_231626_"}, this, p_231635_);
        }

        public List<T> f_231625_() {
            return this.f_231625_;
        }

        @Override
        public Codec<T> m_213664_() {
            return this.f_231626_;
        }
    }

    static interface ValueSet<T> {
        public Function<OptionInstance<T>, AbstractWidget> m_213823_(TooltipSupplier<T> var1, Options var2, int var3, int var4, int var5);

        public Optional<T> m_214064_(T var1);

        public Codec<T> m_213664_();
    }

    @FunctionalInterface
    public static interface TooltipSupplier<T>
    extends Function<T, List<FormattedCharSequence>> {
    }

    public static final class UnitDouble
    extends java.lang.Enum<UnitDouble>
    implements SliderableValueSet<Double> {
        public static final /* enum */ UnitDouble INSTANCE = new UnitDouble();
        private static final /* synthetic */ UnitDouble[] $VALUES;

        public static UnitDouble[] values() {
            return (UnitDouble[])$VALUES.clone();
        }

        public static UnitDouble valueOf(String p_231763_) {
            return java.lang.Enum.valueOf(UnitDouble.class, p_231763_);
        }

        @Override
        public Optional<Double> m_214064_(Double p_231747_) {
            return p_231747_ >= 0.0 && p_231747_ <= 1.0 ? Optional.of(p_231747_) : Optional.empty();
        }

        @Override
        public double m_213640_(Double p_231756_) {
            return p_231756_;
        }

        @Override
        public Double m_213729_(double p_231741_) {
            return p_231741_;
        }

        public <R> SliderableValueSet<R> m_231750_(final DoubleFunction<? extends R> p_231751_, final ToDoubleFunction<? super R> p_231752_) {
            return new SliderableValueSet<R>(){

                @Override
                public Optional<R> m_214064_(R p_231773_) {
                    return this.m_214064_(p_231752_.applyAsDouble(p_231773_)).map(p_231751_::apply);
                }

                @Override
                public double m_213640_(R p_231777_) {
                    return this.m_213640_(p_231752_.applyAsDouble(p_231777_));
                }

                @Override
                public R m_213729_(double p_231775_) {
                    return p_231751_.apply(this.m_213729_(p_231775_));
                }

                @Override
                public Codec<R> m_213664_() {
                    return this.m_213664_().xmap(p_231751_::apply, p_231752_::applyAsDouble);
                }
            };
        }

        @Override
        public Codec<Double> m_213664_() {
            return Codec.either((Codec)Codec.doubleRange((double)0.0, (double)1.0), (Codec)Codec.BOOL).xmap(p_231743_ -> (Double)p_231743_.map(p_231760_ -> p_231760_, p_231745_ -> p_231745_ != false ? 1.0 : 0.0), Either::left);
        }

        @Override
        public /* synthetic */ Object m_213729_(double d) {
            return this.m_213729_(d);
        }

        private static /* synthetic */ UnitDouble[] m_231739_() {
            return new UnitDouble[]{INSTANCE};
        }

        static {
            $VALUES = UnitDouble.m_231739_();
        }
    }

    public static final class ClampingLazyMaxIntRange
    extends Record
    implements IntRangeBase,
    SliderableOrCyclableValueSet<Integer> {
        private final int f_231583_;
        private final IntSupplier f_231584_;

        public ClampingLazyMaxIntRange(int f_231583_, IntSupplier f_231584_) {
            this.f_231583_ = f_231583_;
            this.f_231584_ = f_231584_;
        }

        @Override
        public Optional<Integer> m_214064_(Integer p_231590_) {
            return Optional.of(Mth.m_14045_(p_231590_, this.m_214123_(), this.m_214118_()));
        }

        @Override
        public int m_214118_() {
            return this.f_231584_.getAsInt();
        }

        @Override
        public Codec<Integer> m_213664_() {
            Function<Integer, DataResult> $$0 = p_231596_ -> {
                int $$1 = this.f_231584_.getAsInt() + 1;
                if (p_231596_.compareTo(this.f_231583_) >= 0 && p_231596_.compareTo($$1) <= 0) {
                    return DataResult.success((Object)p_231596_);
                }
                return DataResult.error((String)("Value " + p_231596_ + " outside of range [" + this.f_231583_ + ":" + $$1 + "]"), (Object)p_231596_);
            };
            return Codec.INT.flatXmap($$0, $$0);
        }

        @Override
        public boolean m_214105_() {
            return true;
        }

        @Override
        public CycleButton.ValueListSupplier<Integer> m_213889_() {
            return CycleButton.ValueListSupplier.m_232504_(IntStream.range(this.f_231583_, this.m_214118_() + 1).boxed().toList());
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ClampingLazyMaxIntRange.class, "minInclusive;maxSupplier", "f_231583_", "f_231584_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ClampingLazyMaxIntRange.class, "minInclusive;maxSupplier", "f_231583_", "f_231584_"}, this);
        }

        @Override
        public final boolean equals(Object p_231599_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ClampingLazyMaxIntRange.class, "minInclusive;maxSupplier", "f_231583_", "f_231584_"}, this, p_231599_);
        }

        @Override
        public int m_214123_() {
            return this.f_231583_;
        }

        public IntSupplier f_231584_() {
            return this.f_231584_;
        }
    }

    public static final class IntRange
    extends Record
    implements IntRangeBase {
        private final int f_231639_;
        private final int f_231640_;

        public IntRange(int f_231639_, int f_231640_) {
            this.f_231639_ = f_231639_;
            this.f_231640_ = f_231640_;
        }

        @Override
        public Optional<Integer> m_214064_(Integer p_231645_) {
            return p_231645_.compareTo(this.m_214123_()) >= 0 && p_231645_.compareTo(this.m_214118_()) <= 0 ? Optional.of(p_231645_) : Optional.empty();
        }

        @Override
        public Codec<Integer> m_213664_() {
            return Codec.intRange((int)this.f_231639_, (int)(this.f_231640_ + 1));
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{IntRange.class, "minInclusive;maxInclusive", "f_231639_", "f_231640_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{IntRange.class, "minInclusive;maxInclusive", "f_231639_", "f_231640_"}, this);
        }

        @Override
        public final boolean equals(Object p_231651_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{IntRange.class, "minInclusive;maxInclusive", "f_231639_", "f_231640_"}, this, p_231651_);
        }

        @Override
        public int m_214123_() {
            return this.f_231639_;
        }

        @Override
        public int m_214118_() {
            return this.f_231640_;
        }
    }

    static interface IntRangeBase
    extends SliderableValueSet<Integer> {
        public int m_214123_();

        public int m_214118_();

        @Override
        default public double m_213640_(Integer p_231663_) {
            return Mth.m_184637_(p_231663_.intValue(), this.m_214123_(), this.m_214118_(), 0.0f, 1.0f);
        }

        @Override
        default public Integer m_213729_(double p_231656_) {
            return Mth.m_14107_(Mth.m_144914_(p_231656_, 0.0, 1.0, this.m_214123_(), this.m_214118_()));
        }

        default public <R> SliderableValueSet<R> m_231657_(final IntFunction<? extends R> p_231658_, final ToIntFunction<? super R> p_231659_) {
            return new SliderableValueSet<R>(){

                @Override
                public Optional<R> m_214064_(R p_231674_) {
                    return this.m_214064_(p_231659_.applyAsInt(p_231674_)).map(p_231658_::apply);
                }

                @Override
                public double m_213640_(R p_231678_) {
                    return this.m_213640_(p_231659_.applyAsInt(p_231678_));
                }

                @Override
                public R m_213729_(double p_231676_) {
                    return p_231658_.apply(this.m_213729_(p_231676_));
                }

                @Override
                public Codec<R> m_213664_() {
                    return this.m_213664_().xmap(p_231658_::apply, p_231659_::applyAsInt);
                }
            };
        }

        @Override
        default public /* synthetic */ Object m_213729_(double d) {
            return this.m_213729_(d);
        }
    }

    static final class OptionInstanceSliderButton<N>
    extends AbstractOptionSliderButton
    implements TooltipAccessor {
        private final OptionInstance<N> f_231697_;
        private final SliderableValueSet<N> f_231698_;
        private final TooltipSupplier<N> f_231699_;

        OptionInstanceSliderButton(Options p_231701_, int p_231702_, int p_231703_, int p_231704_, int p_231705_, OptionInstance<N> p_231706_, SliderableValueSet<N> p_231707_, TooltipSupplier<N> p_231708_) {
            super(p_231701_, p_231702_, p_231703_, p_231704_, p_231705_, p_231707_.m_213640_(p_231706_.m_231551_()));
            this.f_231697_ = p_231706_;
            this.f_231698_ = p_231707_;
            this.f_231699_ = p_231708_;
            this.m_5695_();
        }

        @Override
        protected void m_5695_() {
            this.m_93666_(this.f_231697_.f_231475_.apply(this.f_231697_.m_231551_()));
        }

        @Override
        protected void m_5697_() {
            this.f_231697_.m_231514_(this.f_231698_.m_213729_(this.f_93577_));
            this.f_93377_.m_92169_();
        }

        @Override
        public List<FormattedCharSequence> m_141932_() {
            return (List)this.f_231699_.apply(this.f_231698_.m_213729_(this.f_93577_));
        }
    }

    public static final class LazyEnum<T>
    extends Record
    implements CycleableValueSet<T> {
        private final Supplier<List<T>> f_231680_;
        private final Function<T, Optional<T>> f_231681_;
        private final Codec<T> f_231682_;

        public LazyEnum(Supplier<List<T>> f_231680_, Function<T, Optional<T>> f_231681_, Codec<T> f_231682_) {
            this.f_231680_ = f_231680_;
            this.f_231681_ = f_231681_;
            this.f_231682_ = f_231682_;
        }

        @Override
        public Optional<T> m_214064_(T p_231689_) {
            return this.f_231681_.apply(p_231689_);
        }

        @Override
        public CycleButton.ValueListSupplier<T> m_213889_() {
            return CycleButton.ValueListSupplier.m_232504_((Collection)this.f_231680_.get());
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{LazyEnum.class, "values;validateValue;codec", "f_231680_", "f_231681_", "f_231682_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{LazyEnum.class, "values;validateValue;codec", "f_231680_", "f_231681_", "f_231682_"}, this);
        }

        @Override
        public final boolean equals(Object p_231693_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{LazyEnum.class, "values;validateValue;codec", "f_231680_", "f_231681_", "f_231682_"}, this, p_231693_);
        }

        public Supplier<List<T>> f_231680_() {
            return this.f_231680_;
        }

        public Function<T, Optional<T>> f_231681_() {
            return this.f_231681_;
        }

        @Override
        public Codec<T> m_213664_() {
            return this.f_231682_;
        }
    }

    public static final class AltEnum<T>
    extends Record
    implements CycleableValueSet<T> {
        private final List<T> f_231557_;
        private final List<T> f_231558_;
        private final BooleanSupplier f_231559_;
        private final CycleableValueSet.ValueSetter<T> f_231560_;
        private final Codec<T> f_231561_;

        public AltEnum(List<T> f_231557_, List<T> f_231558_, BooleanSupplier f_231559_, CycleableValueSet.ValueSetter<T> f_231560_, Codec<T> f_231561_) {
            this.f_231557_ = f_231557_;
            this.f_231558_ = f_231558_;
            this.f_231559_ = f_231559_;
            this.f_231560_ = f_231560_;
            this.f_231561_ = f_231561_;
        }

        @Override
        public CycleButton.ValueListSupplier<T> m_213889_() {
            return CycleButton.ValueListSupplier.m_168970_(this.f_231559_, this.f_231557_, this.f_231558_);
        }

        @Override
        public Optional<T> m_214064_(T p_231570_) {
            return (this.f_231559_.getAsBoolean() ? this.f_231558_ : this.f_231557_).contains(p_231570_) ? Optional.of(p_231570_) : Optional.empty();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{AltEnum.class, "values;altValues;altCondition;valueSetter;codec", "f_231557_", "f_231558_", "f_231559_", "f_231560_", "f_231561_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{AltEnum.class, "values;altValues;altCondition;valueSetter;codec", "f_231557_", "f_231558_", "f_231559_", "f_231560_", "f_231561_"}, this);
        }

        @Override
        public final boolean equals(Object p_231576_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{AltEnum.class, "values;altValues;altCondition;valueSetter;codec", "f_231557_", "f_231558_", "f_231559_", "f_231560_", "f_231561_"}, this, p_231576_);
        }

        public List<T> f_231557_() {
            return this.f_231557_;
        }

        public List<T> f_231558_() {
            return this.f_231558_;
        }

        public BooleanSupplier f_231559_() {
            return this.f_231559_;
        }

        @Override
        public CycleableValueSet.ValueSetter<T> m_213569_() {
            return this.f_231560_;
        }

        @Override
        public Codec<T> m_213664_() {
            return this.f_231561_;
        }
    }

    static interface SliderableOrCyclableValueSet<T>
    extends CycleableValueSet<T>,
    SliderableValueSet<T> {
        public boolean m_214105_();

        @Override
        default public Function<OptionInstance<T>, AbstractWidget> m_213823_(TooltipSupplier<T> p_231713_, Options p_231714_, int p_231715_, int p_231716_, int p_231717_) {
            if (this.m_214105_()) {
                return CycleableValueSet.super.m_213823_(p_231713_, p_231714_, p_231715_, p_231716_, p_231717_);
            }
            return SliderableValueSet.super.m_213823_(p_231713_, p_231714_, p_231715_, p_231716_, p_231717_);
        }
    }

    static interface CycleableValueSet<T>
    extends ValueSet<T> {
        public CycleButton.ValueListSupplier<T> m_213889_();

        default public ValueSetter<T> m_213569_() {
            return OptionInstance::m_231514_;
        }

        @Override
        default public Function<OptionInstance<T>, AbstractWidget> m_213823_(TooltipSupplier<T> p_231612_, Options p_231613_, int p_231614_, int p_231615_, int p_231616_) {
            return p_231610_ -> CycleButton.m_168894_(p_231610_.f_231475_).m_232500_(this.m_213889_()).m_232498_(p_231612_).m_168948_(p_231610_.f_231481_).m_168936_(p_231614_, p_231615_, p_231616_, 20, p_231610_.f_231480_, (p_231620_, p_231621_) -> {
                this.m_213569_().m_231622_((OptionInstance<Object>)p_231610_, p_231621_);
                p_231613_.m_92169_();
            });
        }

        public static interface ValueSetter<T> {
            public void m_231622_(OptionInstance<T> var1, T var2);
        }
    }

    static interface SliderableValueSet<T>
    extends ValueSet<T> {
        public double m_213640_(T var1);

        public T m_213729_(double var1);

        @Override
        default public Function<OptionInstance<T>, AbstractWidget> m_213823_(TooltipSupplier<T> p_231719_, Options p_231720_, int p_231721_, int p_231722_, int p_231723_) {
            return p_231730_ -> new OptionInstanceSliderButton(p_231720_, p_231721_, p_231722_, p_231723_, 20, p_231730_, this, p_231719_);
        }
    }
}

