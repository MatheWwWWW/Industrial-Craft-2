/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block.state.properties;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.StateHolder;

public abstract class Property<T extends Comparable<T>> {
    private final Class<T> f_61686_;
    private final String f_61687_;
    @Nullable
    private Integer f_61688_;
    private final Codec<T> f_61689_ = Codec.STRING.comapFlatMap(p_61698_ -> this.m_6215_((String)p_61698_).map(DataResult::success).orElseGet(() -> DataResult.error((String)("Unable to read property: " + this + " with value: " + p_61698_))), this::m_6940_);
    private final Codec<Value<T>> f_61690_ = this.f_61689_.xmap(this::m_61699_, Value::f_61713_);

    protected Property(String p_61692_, Class<T> p_61693_) {
        this.f_61686_ = p_61693_;
        this.f_61687_ = p_61692_;
    }

    public Value<T> m_61699_(T p_61700_) {
        return new Value<T>(this, p_61700_);
    }

    public Value<T> m_61694_(StateHolder<?, ?> p_61695_) {
        return new Value(this, p_61695_.m_61143_(this));
    }

    public Stream<Value<T>> m_61702_() {
        return this.m_6908_().stream().map(this::m_61699_);
    }

    public Codec<T> m_156037_() {
        return this.f_61689_;
    }

    public Codec<Value<T>> m_61705_() {
        return this.f_61690_;
    }

    public String m_61708_() {
        return this.f_61687_;
    }

    public Class<T> m_61709_() {
        return this.f_61686_;
    }

    public abstract Collection<T> m_6908_();

    public abstract String m_6940_(T var1);

    public abstract Optional<T> m_6215_(String var1);

    public String toString() {
        return MoreObjects.toStringHelper((Object)this).add("name", (Object)this.f_61687_).add("clazz", this.f_61686_).add("values", this.m_6908_()).toString();
    }

    public boolean equals(Object p_61707_) {
        if (this == p_61707_) {
            return true;
        }
        if (p_61707_ instanceof Property) {
            Property $$1 = (Property)p_61707_;
            return this.f_61686_.equals($$1.f_61686_) && this.f_61687_.equals($$1.f_61687_);
        }
        return false;
    }

    public final int hashCode() {
        if (this.f_61688_ == null) {
            this.f_61688_ = this.m_6310_();
        }
        return this.f_61688_;
    }

    public int m_6310_() {
        return 31 * this.f_61686_.hashCode() + this.f_61687_.hashCode();
    }

    public <U, S extends StateHolder<?, S>> DataResult<S> m_156031_(DynamicOps<U> p_156032_, S p_156033_, U p_156034_) {
        DataResult $$3 = this.f_61689_.parse(p_156032_, p_156034_);
        return $$3.map(p_156030_ -> (StateHolder)p_156033_.m_61124_(this, p_156030_)).setPartial(p_156033_);
    }

    public record Value<T extends Comparable<T>>(Property<T> f_61712_, T f_61713_) {
        public Value {
            if (!f_61712_.m_6908_().contains(f_61713_)) {
                throw new IllegalArgumentException("Value " + f_61713_ + " does not belong to property " + f_61712_);
            }
        }

        @Override
        public String toString() {
            return this.f_61712_.m_61708_() + "=" + this.f_61712_.m_6940_(this.f_61713_);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Value.class, "property;value", "f_61712_", "f_61713_"}, this);
        }

        @Override
        public final boolean equals(Object p_61724_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Value.class, "property;value", "f_61712_", "f_61713_"}, this, p_61724_);
        }
    }
}

