/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ArrayTable
 *  com.google.common.collect.HashBasedTable
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Table
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.MapCodec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.state;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.properties.Property;

public abstract class StateHolder<O, S> {
    public static final String f_155962_ = "Name";
    public static final String f_155963_ = "Properties";
    private static final Function<Map.Entry<Property<?>, Comparable<?>>, String> f_61110_ = new Function<Map.Entry<Property<?>, Comparable<?>>, String>(){

        @Override
        public String apply(@Nullable Map.Entry<Property<?>, Comparable<?>> p_61155_) {
            if (p_61155_ == null) {
                return "<NULL>";
            }
            Property<?> $$1 = p_61155_.getKey();
            return $$1.m_61708_() + "=" + this.m_61151_($$1, p_61155_.getValue());
        }

        private <T extends Comparable<T>> String m_61151_(Property<T> p_61152_, Comparable<?> p_61153_) {
            return p_61152_.m_6940_(p_61153_);
        }

        @Override
        public /* synthetic */ Object apply(@Nullable Object object) {
            return this.apply((Map.Entry)object);
        }
    };
    protected final O f_61112_;
    private final ImmutableMap<Property<?>, Comparable<?>> f_61111_;
    private Table<Property<?>, Comparable<?>, S> f_61114_;
    protected final MapCodec<S> f_61113_;

    protected StateHolder(O p_61117_, ImmutableMap<Property<?>, Comparable<?>> p_61118_, MapCodec<S> p_61119_) {
        this.f_61112_ = p_61117_;
        this.f_61111_ = p_61118_;
        this.f_61113_ = p_61119_;
    }

    public <T extends Comparable<T>> S m_61122_(Property<T> p_61123_) {
        return this.m_61124_(p_61123_, (Comparable)StateHolder.m_61130_(p_61123_.m_6908_(), this.m_61143_(p_61123_)));
    }

    protected static <T> T m_61130_(Collection<T> p_61131_, T p_61132_) {
        Iterator<T> $$2 = p_61131_.iterator();
        while ($$2.hasNext()) {
            if (!$$2.next().equals(p_61132_)) continue;
            if ($$2.hasNext()) {
                return $$2.next();
            }
            return p_61131_.iterator().next();
        }
        return $$2.next();
    }

    public String toString() {
        StringBuilder $$0 = new StringBuilder();
        $$0.append(this.f_61112_);
        if (!this.m_61148_().isEmpty()) {
            $$0.append('[');
            $$0.append(this.m_61148_().entrySet().stream().map(f_61110_).collect(Collectors.joining(",")));
            $$0.append(']');
        }
        return $$0.toString();
    }

    public Collection<Property<?>> m_61147_() {
        return Collections.unmodifiableCollection(this.f_61111_.keySet());
    }

    public <T extends Comparable<T>> boolean m_61138_(Property<T> p_61139_) {
        return this.f_61111_.containsKey(p_61139_);
    }

    public <T extends Comparable<T>> T m_61143_(Property<T> p_61144_) {
        Comparable $$1 = (Comparable)this.f_61111_.get(p_61144_);
        if ($$1 == null) {
            throw new IllegalArgumentException("Cannot get property " + p_61144_ + " as it does not exist in " + this.f_61112_);
        }
        return (T)((Comparable)p_61144_.m_61709_().cast($$1));
    }

    public <T extends Comparable<T>> Optional<T> m_61145_(Property<T> p_61146_) {
        Comparable $$1 = (Comparable)this.f_61111_.get(p_61146_);
        if ($$1 == null) {
            return Optional.empty();
        }
        return Optional.of((Comparable)p_61146_.m_61709_().cast($$1));
    }

    public <T extends Comparable<T>, V extends T> S m_61124_(Property<T> p_61125_, V p_61126_) {
        Comparable $$2 = (Comparable)this.f_61111_.get(p_61125_);
        if ($$2 == null) {
            throw new IllegalArgumentException("Cannot set property " + p_61125_ + " as it does not exist in " + this.f_61112_);
        }
        if ($$2 == p_61126_) {
            return (S)this;
        }
        Object $$3 = this.f_61114_.get(p_61125_, p_61126_);
        if ($$3 == null) {
            throw new IllegalArgumentException("Cannot set property " + p_61125_ + " to " + p_61126_ + " on " + this.f_61112_ + ", it is not an allowed value");
        }
        return (S)$$3;
    }

    public void m_61133_(Map<Map<Property<?>, Comparable<?>>, S> p_61134_) {
        if (this.f_61114_ != null) {
            throw new IllegalStateException();
        }
        HashBasedTable $$1 = HashBasedTable.create();
        for (Map.Entry $$2 : this.f_61111_.entrySet()) {
            Property $$3 = (Property)$$2.getKey();
            for (Comparable $$4 : $$3.m_6908_()) {
                if ($$4 == $$2.getValue()) continue;
                $$1.put((Object)$$3, (Object)$$4, p_61134_.get(this.m_61140_($$3, $$4)));
            }
        }
        this.f_61114_ = $$1.isEmpty() ? $$1 : ArrayTable.create((Table)$$1);
    }

    private Map<Property<?>, Comparable<?>> m_61140_(Property<?> p_61141_, Comparable<?> p_61142_) {
        HashMap $$2 = Maps.newHashMap(this.f_61111_);
        $$2.put(p_61141_, p_61142_);
        return $$2;
    }

    public ImmutableMap<Property<?>, Comparable<?>> m_61148_() {
        return this.f_61111_;
    }

    protected static <O, S extends StateHolder<O, S>> Codec<S> m_61127_(Codec<O> p_61128_, Function<O, S> p_61129_) {
        return p_61128_.dispatch(f_155962_, p_61121_ -> p_61121_.f_61112_, p_187547_ -> {
            StateHolder $$2 = (StateHolder)p_61129_.apply(p_187547_);
            if ($$2.m_61148_().isEmpty()) {
                return Codec.unit((Object)$$2);
            }
            return $$2.f_61113_.codec().optionalFieldOf(f_155963_).xmap(p_187544_ -> p_187544_.orElse($$2), Optional::of).codec();
        });
    }
}

