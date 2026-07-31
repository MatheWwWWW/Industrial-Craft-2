/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableSortedMap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Decoder
 *  com.mojang.serialization.Encoder
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.MapDecoder
 *  com.mojang.serialization.MapEncoder
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.state;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapDecoder;
import com.mojang.serialization.MapEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;

public class StateDefinition<O, S extends StateHolder<O, S>> {
    static final Pattern f_61046_ = Pattern.compile("^[a-z0-9_]+$");
    private final O f_61047_;
    private final ImmutableSortedMap<String, Property<?>> f_61048_;
    private final ImmutableList<S> f_61049_;

    protected StateDefinition(Function<O, S> p_61052_, O p_61053_, Factory<O, S> p_61054_, Map<String, Property<?>> p_61055_) {
        this.f_61047_ = p_61053_;
        this.f_61048_ = ImmutableSortedMap.copyOf(p_61055_);
        Supplier<StateHolder> $$4 = () -> (StateHolder)p_61052_.apply(p_61053_);
        MapCodec<StateHolder> $$5 = MapCodec.of((MapEncoder)Encoder.empty(), (MapDecoder)Decoder.unit($$4));
        for (Map.Entry $$6 : this.f_61048_.entrySet()) {
            $$5 = StateDefinition.m_61076_($$5, $$4, (String)$$6.getKey(), (Property)$$6.getValue());
        }
        MapCodec<StateHolder> $$7 = $$5;
        LinkedHashMap $$8 = Maps.newLinkedHashMap();
        ArrayList $$9 = Lists.newArrayList();
        Stream<List<List<Object>>> $$10 = Stream.of(Collections.emptyList());
        for (Property $$11 : this.f_61048_.values()) {
            $$10 = $$10.flatMap(p_61072_ -> $$11.m_6908_().stream().map(p_155961_ -> {
                ArrayList $$3 = Lists.newArrayList((Iterable)p_61072_);
                $$3.add(Pair.of((Object)$$11, (Object)p_155961_));
                return $$3;
            }));
        }
        $$10.forEach(p_61063_ -> {
            ImmutableMap $$6 = (ImmutableMap)p_61063_.stream().collect(ImmutableMap.toImmutableMap(Pair::getFirst, Pair::getSecond));
            StateHolder $$7 = (StateHolder)p_61054_.m_61106_(p_61053_, $$6, $$7);
            $$8.put($$6, $$7);
            $$9.add($$7);
        });
        for (StateHolder $$12 : $$9) {
            $$12.m_61133_($$8);
        }
        this.f_61049_ = ImmutableList.copyOf((Collection)$$9);
    }

    private static <S extends StateHolder<?, S>, T extends Comparable<T>> MapCodec<S> m_61076_(MapCodec<S> p_61077_, Supplier<S> p_61078_, String p_61079_, Property<T> p_61080_) {
        return Codec.mapPair(p_61077_, (MapCodec)p_61080_.m_61705_().fieldOf(p_61079_).orElseGet(p_187541_ -> {}, () -> p_61080_.m_61694_((StateHolder)p_61078_.get()))).xmap(p_187536_ -> (StateHolder)((StateHolder)p_187536_.getFirst()).m_61124_(p_61080_, ((Property.Value)p_187536_.getSecond()).f_61713_()), p_187533_ -> Pair.of((Object)p_187533_, p_61080_.m_61694_((StateHolder<?, ?>)p_187533_)));
    }

    public ImmutableList<S> m_61056_() {
        return this.f_61049_;
    }

    public S m_61090_() {
        return (S)((StateHolder)this.f_61049_.get(0));
    }

    public O m_61091_() {
        return this.f_61047_;
    }

    public Collection<Property<?>> m_61092_() {
        return this.f_61048_.values();
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object)this).add("block", this.f_61047_).add("properties", this.f_61048_.values().stream().map(Property::m_61708_).collect(Collectors.toList())).toString();
    }

    @Nullable
    public Property<?> m_61081_(String p_61082_) {
        return (Property)this.f_61048_.get((Object)p_61082_);
    }

    public static interface Factory<O, S> {
        public S m_61106_(O var1, ImmutableMap<Property<?>, Comparable<?>> var2, MapCodec<S> var3);
    }

    public static class Builder<O, S extends StateHolder<O, S>> {
        private final O f_61095_;
        private final Map<String, Property<?>> f_61096_ = Maps.newHashMap();

        public Builder(O p_61098_) {
            this.f_61095_ = p_61098_;
        }

        public Builder<O, S> m_61104_(Property<?> ... p_61105_) {
            for (Property<?> $$1 : p_61105_) {
                this.m_61099_($$1);
                this.f_61096_.put($$1.m_61708_(), $$1);
            }
            return this;
        }

        private <T extends Comparable<T>> void m_61099_(Property<T> p_61100_) {
            String $$1 = p_61100_.m_61708_();
            if (!f_61046_.matcher($$1).matches()) {
                throw new IllegalArgumentException(this.f_61095_ + " has invalidly named property: " + $$1);
            }
            Collection<T> $$2 = p_61100_.m_6908_();
            if ($$2.size() <= 1) {
                throw new IllegalArgumentException(this.f_61095_ + " attempted use property " + $$1 + " with <= 1 possible values");
            }
            for (Comparable $$3 : $$2) {
                String $$4 = p_61100_.m_6940_($$3);
                if (f_61046_.matcher($$4).matches()) continue;
                throw new IllegalArgumentException(this.f_61095_ + " has property: " + $$1 + " with invalidly named value: " + $$4);
            }
            if (this.f_61096_.containsKey($$1)) {
                throw new IllegalArgumentException(this.f_61095_ + " has duplicate property: " + $$1);
            }
        }

        public StateDefinition<O, S> m_61101_(Function<O, S> p_61102_, Factory<O, S> p_61103_) {
            return new StateDefinition<O, S>(p_61102_, this.f_61095_, p_61103_, this.f_61096_);
        }
    }
}

