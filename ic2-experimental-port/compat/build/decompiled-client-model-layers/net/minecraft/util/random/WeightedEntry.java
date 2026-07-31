/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.util.random;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.random.Weight;

public interface WeightedEntry {
    public Weight m_142631_();

    public static <T> Wrapper<T> m_146290_(T p_146291_, int p_146292_) {
        return new Wrapper<T>(p_146291_, Weight.m_146282_(p_146292_));
    }

    public static class Wrapper<T>
    implements WeightedEntry {
        private final T f_146299_;
        private final Weight f_146300_;

        Wrapper(T p_146302_, Weight p_146303_) {
            this.f_146299_ = p_146302_;
            this.f_146300_ = p_146303_;
        }

        public T m_146310_() {
            return this.f_146299_;
        }

        @Override
        public Weight m_142631_() {
            return this.f_146300_;
        }

        public static <E> Codec<Wrapper<E>> m_146305_(Codec<E> p_146306_) {
            return RecordCodecBuilder.create(p_146309_ -> p_146309_.group((App)p_146306_.fieldOf("data").forGetter(Wrapper::m_146310_), (App)Weight.f_146274_.fieldOf("weight").forGetter(Wrapper::m_142631_)).apply((Applicative)p_146309_, Wrapper::new));
        }
    }

    public static class IntrusiveBase
    implements WeightedEntry {
        private final Weight f_146293_;

        public IntrusiveBase(int p_146295_) {
            this.f_146293_ = Weight.m_146282_(p_146295_);
        }

        public IntrusiveBase(Weight p_146297_) {
            this.f_146293_ = p_146297_;
        }

        @Override
        public Weight m_142631_() {
            return this.f_146293_;
        }
    }
}

