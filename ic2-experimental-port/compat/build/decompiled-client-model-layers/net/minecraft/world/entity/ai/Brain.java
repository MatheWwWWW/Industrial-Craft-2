/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.MapLike
 *  com.mojang.serialization.RecordBuilder
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableObject
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.ai;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;

public class Brain<E extends LivingEntity> {
    static final Logger f_21841_ = LogUtils.getLogger();
    private final Supplier<Codec<Brain<E>>> f_21842_;
    private static final int f_147338_ = 20;
    private final Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> f_21843_ = Maps.newHashMap();
    private final Map<SensorType<? extends Sensor<? super E>>, Sensor<? super E>> f_21844_ = Maps.newLinkedHashMap();
    private final Map<Integer, Map<Activity, Set<Behavior<? super E>>>> f_21845_ = Maps.newTreeMap();
    private Schedule f_21846_ = Schedule.f_38012_;
    private final Map<Activity, Set<Pair<MemoryModuleType<?>, MemoryStatus>>> f_21847_ = Maps.newHashMap();
    private final Map<Activity, Set<MemoryModuleType<?>>> f_21848_ = Maps.newHashMap();
    private Set<Activity> f_21849_ = Sets.newHashSet();
    private final Set<Activity> f_21850_ = Sets.newHashSet();
    private Activity f_21851_ = Activity.f_37979_;
    private long f_21852_ = -9999L;

    public static <E extends LivingEntity> Provider<E> m_21923_(Collection<? extends MemoryModuleType<?>> p_21924_, Collection<? extends SensorType<? extends Sensor<? super E>>> p_21925_) {
        return new Provider(p_21924_, p_21925_);
    }

    public static <E extends LivingEntity> Codec<Brain<E>> m_21946_(final Collection<? extends MemoryModuleType<?>> p_21947_, final Collection<? extends SensorType<? extends Sensor<? super E>>> p_21948_) {
        final MutableObject $$2 = new MutableObject();
        $$2.setValue((Object)new MapCodec<Brain<E>>(){

            public <T> Stream<T> keys(DynamicOps<T> p_22029_) {
                return p_21947_.stream().flatMap(p_22020_ -> p_22020_.m_26387_().map(p_147346_ -> Registry.f_122871_.m_7981_((MemoryModuleType<?>)p_22020_)).stream()).map(p_22018_ -> p_22029_.createString(p_22018_.toString()));
            }

            public <T> DataResult<Brain<E>> decode(DynamicOps<T> p_22022_, MapLike<T> p_22023_) {
                MutableObject $$22 = new MutableObject((Object)DataResult.success((Object)ImmutableList.builder()));
                p_22023_.entries().forEach(p_22015_ -> {
                    DataResult $$3 = Registry.f_122871_.m_194605_().parse(p_22022_, p_22015_.getFirst());
                    DataResult $$4 = $$3.flatMap(p_147350_ -> this.m_21996_((MemoryModuleType)p_147350_, p_22022_, (Object)p_22015_.getSecond()));
                    $$22.setValue((Object)((DataResult)$$22.getValue()).apply2(ImmutableList.Builder::add, $$4));
                });
                ImmutableList $$3 = ((DataResult)$$22.getValue()).resultOrPartial(arg_0 -> ((Logger)f_21841_).error(arg_0)).map(ImmutableList.Builder::build).orElseGet(ImmutableList::of);
                return DataResult.success(new Brain(p_21947_, p_21948_, $$3, () -> ((MutableObject)$$2).getValue()));
            }

            private <T, U> DataResult<MemoryValue<U>> m_21996_(MemoryModuleType<U> p_21997_, DynamicOps<T> p_21998_, T p_21999_) {
                return p_21997_.m_26387_().map(DataResult::success).orElseGet(() -> DataResult.error((String)("No codec for memory: " + p_21997_))).flatMap(p_22011_ -> p_22011_.parse(p_21998_, p_21999_)).map(p_21992_ -> new MemoryValue(p_21997_, Optional.of(p_21992_)));
            }

            public <T> RecordBuilder<T> encode(Brain<E> p_21985_, DynamicOps<T> p_21986_, RecordBuilder<T> p_21987_) {
                p_21985_.m_21975_().forEach(p_22007_ -> p_22007_.m_22047_(p_21986_, p_21987_));
                return p_21987_;
            }

            public /* synthetic */ RecordBuilder encode(Object object, DynamicOps dynamicOps, RecordBuilder recordBuilder) {
                return this.encode((Brain)object, dynamicOps, recordBuilder);
            }
        }.fieldOf("memories").codec());
        return (Codec)$$2.getValue();
    }

    public Brain(Collection<? extends MemoryModuleType<?>> p_21855_, Collection<? extends SensorType<? extends Sensor<? super E>>> p_21856_, ImmutableList<MemoryValue<?>> p_21857_, Supplier<Codec<Brain<E>>> p_21858_) {
        this.f_21842_ = p_21858_;
        for (MemoryModuleType<?> memoryModuleType : p_21855_) {
            this.f_21843_.put(memoryModuleType, Optional.empty());
        }
        for (SensorType sensorType : p_21856_) {
            this.f_21844_.put(sensorType, (Sensor<E>)sensorType.m_26827_());
        }
        for (Sensor sensor : this.f_21844_.values()) {
            for (MemoryModuleType<?> $$7 : sensor.m_7163_()) {
                this.f_21843_.put($$7, Optional.empty());
            }
        }
        for (MemoryValue memoryValue : p_21857_) {
            memoryValue.m_22042_(this);
        }
    }

    public <T> DataResult<T> m_21914_(DynamicOps<T> p_21915_) {
        return this.f_21842_.get().encodeStart(p_21915_, (Object)this);
    }

    Stream<MemoryValue<?>> m_21975_() {
        return this.f_21843_.entrySet().stream().map(p_21929_ -> MemoryValue.m_22059_((MemoryModuleType)p_21929_.getKey(), (Optional)p_21929_.getValue()));
    }

    public boolean m_21874_(MemoryModuleType<?> p_21875_) {
        return this.m_21876_(p_21875_, MemoryStatus.VALUE_PRESENT);
    }

    public <U> void m_21936_(MemoryModuleType<U> p_21937_) {
        this.m_21886_(p_21937_, Optional.empty());
    }

    public <U> void m_21879_(MemoryModuleType<U> p_21880_, @Nullable U p_21881_) {
        this.m_21886_(p_21880_, Optional.ofNullable(p_21881_));
    }

    public <U> void m_21882_(MemoryModuleType<U> p_21883_, U p_21884_, long p_21885_) {
        this.m_21941_(p_21883_, Optional.of(ExpirableValue.m_26311_(p_21884_, p_21885_)));
    }

    public <U> void m_21886_(MemoryModuleType<U> p_21887_, Optional<? extends U> p_21888_) {
        this.m_21941_(p_21887_, p_21888_.map(ExpirableValue::m_26309_));
    }

    <U> void m_21941_(MemoryModuleType<U> p_21942_, Optional<? extends ExpirableValue<?>> p_21943_) {
        if (this.f_21843_.containsKey(p_21942_)) {
            if (p_21943_.isPresent() && this.m_21918_(p_21943_.get().m_26319_())) {
                this.m_21936_(p_21942_);
            } else {
                this.f_21843_.put(p_21942_, p_21943_);
            }
        }
    }

    public <U> Optional<U> m_21952_(MemoryModuleType<U> p_21953_) {
        Optional<ExpirableValue<?>> $$1 = this.f_21843_.get(p_21953_);
        if ($$1 == null) {
            throw new IllegalStateException("Unregistered memory fetched: " + p_21953_);
        }
        return $$1.map(ExpirableValue::m_26319_);
    }

    public <U> long m_147341_(MemoryModuleType<U> p_147342_) {
        Optional<ExpirableValue<?>> $$1 = this.f_21843_.get(p_147342_);
        return $$1.map(ExpirableValue::m_148191_).orElse(0L);
    }

    @Deprecated
    @VisibleForDebug
    public Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> m_147339_() {
        return this.f_21843_;
    }

    public <U> boolean m_21938_(MemoryModuleType<U> p_21939_, U p_21940_) {
        if (!this.m_21874_(p_21939_)) {
            return false;
        }
        return this.m_21952_(p_21939_).filter(p_21922_ -> p_21922_.equals(p_21940_)).isPresent();
    }

    public boolean m_21876_(MemoryModuleType<?> p_21877_, MemoryStatus p_21878_) {
        Optional<ExpirableValue<?>> $$2 = this.f_21843_.get(p_21877_);
        if ($$2 == null) {
            return false;
        }
        return p_21878_ == MemoryStatus.REGISTERED || p_21878_ == MemoryStatus.VALUE_PRESENT && $$2.isPresent() || p_21878_ == MemoryStatus.VALUE_ABSENT && !$$2.isPresent();
    }

    public Schedule m_21932_() {
        return this.f_21846_;
    }

    public void m_21912_(Schedule p_21913_) {
        this.f_21846_ = p_21913_;
    }

    public void m_21930_(Set<Activity> p_21931_) {
        this.f_21849_ = p_21931_;
    }

    @Deprecated
    @VisibleForDebug
    public Set<Activity> m_147340_() {
        return this.f_21850_;
    }

    @Deprecated
    @VisibleForDebug
    public List<Behavior<? super E>> m_21956_() {
        ObjectArrayList $$0 = new ObjectArrayList();
        for (Map<Activity, Set<Behavior<E>>> $$1 : this.f_21845_.values()) {
            for (Set<Behavior<E>> $$2 : $$1.values()) {
                for (Behavior<E> $$3 : $$2) {
                    if ($$3.m_22536_() != Behavior.Status.RUNNING) continue;
                    $$0.add($$3);
                }
            }
        }
        return $$0;
    }

    public void m_21962_() {
        this.m_21960_(this.f_21851_);
    }

    public Optional<Activity> m_21968_() {
        for (Activity $$0 : this.f_21850_) {
            if (this.f_21849_.contains($$0)) continue;
            return Optional.of($$0);
        }
        return Optional.empty();
    }

    public void m_21889_(Activity p_21890_) {
        if (this.m_21969_(p_21890_)) {
            this.m_21960_(p_21890_);
        } else {
            this.m_21962_();
        }
    }

    private void m_21960_(Activity p_21961_) {
        if (this.m_21954_(p_21961_)) {
            return;
        }
        this.m_21966_(p_21961_);
        this.f_21850_.clear();
        this.f_21850_.addAll(this.f_21849_);
        this.f_21850_.add(p_21961_);
    }

    private void m_21966_(Activity p_21967_) {
        for (Activity $$1 : this.f_21850_) {
            Set<MemoryModuleType<?>> $$2;
            if ($$1 == p_21967_ || ($$2 = this.f_21848_.get($$1)) == null) continue;
            for (MemoryModuleType<?> $$3 : $$2) {
                this.m_21936_($$3);
            }
        }
    }

    public void m_21862_(long p_21863_, long p_21864_) {
        if (p_21864_ - this.f_21852_ > 20L) {
            this.f_21852_ = p_21864_;
            Activity $$2 = this.m_21932_().m_38019_((int)(p_21863_ % 24000L));
            if (!this.f_21850_.contains($$2)) {
                this.m_21889_($$2);
            }
        }
    }

    public void m_21926_(List<Activity> p_21927_) {
        for (Activity $$1 : p_21927_) {
            if (!this.m_21969_($$1)) continue;
            this.m_21960_($$1);
            break;
        }
    }

    public void m_21944_(Activity p_21945_) {
        this.f_21851_ = p_21945_;
    }

    public void m_21891_(Activity p_21892_, int p_21893_, ImmutableList<? extends Behavior<? super E>> p_21894_) {
        this.m_21900_(p_21892_, this.m_21859_(p_21893_, p_21894_));
    }

    public void m_21895_(Activity p_21896_, int p_21897_, ImmutableList<? extends Behavior<? super E>> p_21898_, MemoryModuleType<?> p_21899_) {
        ImmutableSet $$4 = ImmutableSet.of((Object)Pair.of(p_21899_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        ImmutableSet $$5 = ImmutableSet.of(p_21899_);
        this.m_21907_(p_21896_, (ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>>)this.m_21859_(p_21897_, p_21898_), (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)$$4, (Set<MemoryModuleType<?>>)$$5);
    }

    public void m_21900_(Activity p_21901_, ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> p_21902_) {
        this.m_21907_(p_21901_, p_21902_, (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)ImmutableSet.of(), Sets.newHashSet());
    }

    public void m_21903_(Activity p_21904_, ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> p_21905_, Set<Pair<MemoryModuleType<?>, MemoryStatus>> p_21906_) {
        this.m_21907_(p_21904_, p_21905_, p_21906_, Sets.newHashSet());
    }

    public void m_21907_(Activity p_21908_, ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> p_21909_, Set<Pair<MemoryModuleType<?>, MemoryStatus>> p_21910_, Set<MemoryModuleType<?>> p_21911_) {
        this.f_21847_.put(p_21908_, p_21910_);
        if (!p_21911_.isEmpty()) {
            this.f_21848_.put(p_21908_, p_21911_);
        }
        for (Pair $$4 : p_21909_) {
            this.f_21845_.computeIfAbsent((Integer)$$4.getFirst(), p_21917_ -> Maps.newHashMap()).computeIfAbsent(p_21908_, p_21972_ -> Sets.newLinkedHashSet()).add((Behavior)$$4.getSecond());
        }
    }

    @VisibleForTesting
    public void m_147343_() {
        this.f_21845_.clear();
    }

    public boolean m_21954_(Activity p_21955_) {
        return this.f_21850_.contains(p_21955_);
    }

    public Brain<E> m_21973_() {
        Brain<E> $$0 = new Brain<E>(this.f_21843_.keySet(), this.f_21844_.keySet(), ImmutableList.of(), this.f_21842_);
        for (Map.Entry<MemoryModuleType<?>, Optional<ExpirableValue<?>>> $$1 : this.f_21843_.entrySet()) {
            MemoryModuleType<?> $$2 = $$1.getKey();
            if (!$$1.getValue().isPresent()) continue;
            $$0.f_21843_.put($$2, $$1.getValue());
        }
        return $$0;
    }

    public void m_21865_(ServerLevel p_21866_, E p_21867_) {
        this.m_21976_();
        this.m_21949_(p_21866_, p_21867_);
        this.m_21957_(p_21866_, p_21867_);
        this.m_21963_(p_21866_, p_21867_);
    }

    private void m_21949_(ServerLevel p_21950_, E p_21951_) {
        for (Sensor<E> $$2 : this.f_21844_.values()) {
            $$2.m_26806_(p_21950_, p_21951_);
        }
    }

    private void m_21976_() {
        for (Map.Entry<MemoryModuleType<?>, Optional<ExpirableValue<?>>> $$0 : this.f_21843_.entrySet()) {
            if (!$$0.getValue().isPresent()) continue;
            ExpirableValue<?> $$1 = $$0.getValue().get();
            if ($$1.m_26320_()) {
                this.m_21936_($$0.getKey());
            }
            $$1.m_26301_();
        }
    }

    public void m_21933_(ServerLevel p_21934_, E p_21935_) {
        long $$2 = ((LivingEntity)p_21935_).f_19853_.m_46467_();
        for (Behavior<E> $$3 : this.m_21956_()) {
            $$3.m_22562_(p_21934_, p_21935_, $$2);
        }
    }

    private void m_21957_(ServerLevel p_21958_, E p_21959_) {
        long $$2 = p_21958_.m_46467_();
        for (Map<Activity, Set<Behavior<E>>> $$3 : this.f_21845_.values()) {
            for (Map.Entry<Activity, Set<Behavior<E>>> $$4 : $$3.entrySet()) {
                Activity $$5 = $$4.getKey();
                if (!this.f_21850_.contains($$5)) continue;
                Set<Behavior<E>> $$6 = $$4.getValue();
                for (Behavior<E> $$7 : $$6) {
                    if ($$7.m_22536_() != Behavior.Status.STOPPED) continue;
                    $$7.m_22554_(p_21958_, p_21959_, $$2);
                }
            }
        }
    }

    private void m_21963_(ServerLevel p_21964_, E p_21965_) {
        long $$2 = p_21964_.m_46467_();
        for (Behavior<E> $$3 : this.m_21956_()) {
            $$3.m_22558_(p_21964_, p_21965_, $$2);
        }
    }

    private boolean m_21969_(Activity p_21970_) {
        if (!this.f_21847_.containsKey(p_21970_)) {
            return false;
        }
        for (Pair<MemoryModuleType<?>, MemoryStatus> $$1 : this.f_21847_.get(p_21970_)) {
            MemoryStatus $$3;
            MemoryModuleType $$2 = (MemoryModuleType)$$1.getFirst();
            if (this.m_21876_($$2, $$3 = (MemoryStatus)((Object)$$1.getSecond()))) continue;
            return false;
        }
        return true;
    }

    private boolean m_21918_(Object p_21919_) {
        return p_21919_ instanceof Collection && ((Collection)p_21919_).isEmpty();
    }

    ImmutableList<? extends Pair<Integer, ? extends Behavior<? super E>>> m_21859_(int p_21860_, ImmutableList<? extends Behavior<? super E>> p_21861_) {
        int $$2 = p_21860_;
        ImmutableList.Builder $$3 = ImmutableList.builder();
        for (Behavior $$4 : p_21861_) {
            $$3.add((Object)Pair.of((Object)$$2++, (Object)$$4));
        }
        return $$3.build();
    }

    public static final class Provider<E extends LivingEntity> {
        private final Collection<? extends MemoryModuleType<?>> f_22062_;
        private final Collection<? extends SensorType<? extends Sensor<? super E>>> f_22063_;
        private final Codec<Brain<E>> f_22064_;

        Provider(Collection<? extends MemoryModuleType<?>> p_22066_, Collection<? extends SensorType<? extends Sensor<? super E>>> p_22067_) {
            this.f_22062_ = p_22066_;
            this.f_22063_ = p_22067_;
            this.f_22064_ = Brain.m_21946_(p_22066_, p_22067_);
        }

        public Brain<E> m_22073_(Dynamic<?> p_22074_) {
            return this.f_22064_.parse(p_22074_).resultOrPartial(arg_0 -> ((Logger)f_21841_).error(arg_0)).orElseGet(() -> new Brain(this.f_22062_, this.f_22063_, ImmutableList.of(), () -> this.f_22064_));
        }
    }

    static final class MemoryValue<U> {
        private final MemoryModuleType<U> f_22030_;
        private final Optional<? extends ExpirableValue<U>> f_22031_;

        static <U> MemoryValue<U> m_22059_(MemoryModuleType<U> p_22060_, Optional<? extends ExpirableValue<?>> p_22061_) {
            return new MemoryValue<U>(p_22060_, p_22061_);
        }

        MemoryValue(MemoryModuleType<U> p_22033_, Optional<? extends ExpirableValue<U>> p_22034_) {
            this.f_22030_ = p_22033_;
            this.f_22031_ = p_22034_;
        }

        void m_22042_(Brain<?> p_22043_) {
            p_22043_.m_21941_(this.f_22030_, this.f_22031_);
        }

        public <T> void m_22047_(DynamicOps<T> p_22048_, RecordBuilder<T> p_22049_) {
            this.f_22030_.m_26387_().ifPresent(p_22053_ -> this.f_22031_.ifPresent(p_147355_ -> p_22049_.add(Registry.f_122871_.m_194605_().encodeStart(p_22048_, this.f_22030_), p_22053_.encodeStart(p_22048_, p_147355_))));
        }
    }
}

