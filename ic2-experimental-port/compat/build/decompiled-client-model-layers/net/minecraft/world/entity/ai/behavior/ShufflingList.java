/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.util.RandomSource;

public class ShufflingList<U> {
    protected final List<WeightedEntry<U>> f_147917_;
    private final RandomSource f_147918_ = RandomSource.m_216327_();

    public ShufflingList() {
        this.f_147917_ = Lists.newArrayList();
    }

    private ShufflingList(List<WeightedEntry<U>> p_147921_) {
        this.f_147917_ = Lists.newArrayList(p_147921_);
    }

    public static <U> Codec<ShufflingList<U>> m_147927_(Codec<U> p_147928_) {
        return WeightedEntry.m_147943_(p_147928_).listOf().xmap(ShufflingList::new, p_147926_ -> p_147926_.f_147917_);
    }

    public ShufflingList<U> m_147929_(U p_147930_, int p_147931_) {
        this.f_147917_.add(new WeightedEntry<U>(p_147930_, p_147931_));
        return this;
    }

    public ShufflingList<U> m_147922_() {
        this.f_147917_.forEach(p_147924_ -> p_147924_.m_147941_(this.f_147918_.m_188501_()));
        this.f_147917_.sort(Comparator.comparingDouble(WeightedEntry::m_147946_));
        return this;
    }

    public Stream<U> m_147932_() {
        return this.f_147917_.stream().map(WeightedEntry::m_147940_);
    }

    public String toString() {
        return "ShufflingList[" + this.f_147917_ + "]";
    }

    public static class WeightedEntry<T> {
        final T f_147934_;
        final int f_147935_;
        private double f_147936_;

        WeightedEntry(T p_147938_, int p_147939_) {
            this.f_147935_ = p_147939_;
            this.f_147934_ = p_147938_;
        }

        private double m_147946_() {
            return this.f_147936_;
        }

        void m_147941_(float p_147942_) {
            this.f_147936_ = -Math.pow(p_147942_, 1.0f / (float)this.f_147935_);
        }

        public T m_147940_() {
            return this.f_147934_;
        }

        public int m_147945_() {
            return this.f_147935_;
        }

        public String toString() {
            return this.f_147935_ + ":" + this.f_147934_;
        }

        public static <E> Codec<WeightedEntry<E>> m_147943_(final Codec<E> p_147944_) {
            return new Codec<WeightedEntry<E>>(){

                public <T> DataResult<Pair<WeightedEntry<E>, T>> decode(DynamicOps<T> p_147962_, T p_147963_) {
                    Dynamic $$2 = new Dynamic(p_147962_, p_147963_);
                    return $$2.get("data").flatMap(arg_0 -> ((Codec)p_147944_).parse(arg_0)).map(p_147957_ -> new WeightedEntry<Object>(p_147957_, $$2.get("weight").asInt(1))).map(p_147960_ -> Pair.of((Object)p_147960_, (Object)p_147962_.empty()));
                }

                public <T> DataResult<T> encode(WeightedEntry<E> p_147952_, DynamicOps<T> p_147953_, T p_147954_) {
                    return p_147953_.mapBuilder().add("weight", p_147953_.createInt(p_147952_.f_147935_)).add("data", p_147944_.encodeStart(p_147953_, p_147952_.f_147934_)).build(p_147954_);
                }

                public /* synthetic */ DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
                    return this.encode((WeightedEntry)object, dynamicOps, object2);
                }
            };
        }
    }
}

