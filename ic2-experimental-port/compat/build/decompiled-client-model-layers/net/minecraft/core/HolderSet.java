/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Either
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.core;

import com.mojang.datafixers.util.Either;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface HolderSet<T>
extends Iterable<Holder<T>> {
    public Stream<Holder<T>> m_203614_();

    public int m_203632_();

    public Either<TagKey<T>, List<Holder<T>>> m_203440_();

    public Optional<Holder<T>> m_213653_(RandomSource var1);

    public Holder<T> m_203662_(int var1);

    public boolean m_203333_(Holder<T> var1);

    public boolean m_207277_(Registry<T> var1);

    @SafeVarargs
    public static <T> Direct<T> m_205809_(Holder<T> ... p_205810_) {
        return new Direct<T>(List.of(p_205810_));
    }

    public static <T> Direct<T> m_205800_(List<? extends Holder<T>> p_205801_) {
        return new Direct(List.copyOf(p_205801_));
    }

    @SafeVarargs
    public static <E, T> Direct<T> m_205806_(Function<E, Holder<T>> p_205807_, E ... p_205808_) {
        return HolderSet.m_205800_(Stream.of(p_205808_).map(p_205807_).toList());
    }

    public static <E, T> Direct<T> m_205803_(Function<E, Holder<T>> p_205804_, List<E> p_205805_) {
        return HolderSet.m_205800_(p_205805_.stream().map(p_205804_).toList());
    }

    public static class Direct<T>
    extends ListBacked<T> {
        private final List<Holder<T>> f_205811_;
        @Nullable
        private Set<Holder<T>> f_205812_;

        Direct(List<Holder<T>> p_205814_) {
            this.f_205811_ = p_205814_;
        }

        @Override
        protected List<Holder<T>> m_203661_() {
            return this.f_205811_;
        }

        @Override
        public Either<TagKey<T>, List<Holder<T>>> m_203440_() {
            return Either.right(this.f_205811_);
        }

        @Override
        public boolean m_203333_(Holder<T> p_205816_) {
            if (this.f_205812_ == null) {
                this.f_205812_ = Set.copyOf(this.f_205811_);
            }
            return this.f_205812_.contains(p_205816_);
        }

        public String toString() {
            return "DirectSet[" + this.f_205811_ + "]";
        }
    }

    public static class Named<T>
    extends ListBacked<T> {
        private final Registry<T> f_211044_;
        private final TagKey<T> f_205829_;
        private List<Holder<T>> f_205830_ = List.of();

        Named(Registry<T> p_211046_, TagKey<T> p_211047_) {
            this.f_211044_ = p_211046_;
            this.f_205829_ = p_211047_;
        }

        void m_205835_(List<Holder<T>> p_205836_) {
            this.f_205830_ = List.copyOf(p_205836_);
        }

        public TagKey<T> m_205839_() {
            return this.f_205829_;
        }

        @Override
        protected List<Holder<T>> m_203661_() {
            return this.f_205830_;
        }

        @Override
        public Either<TagKey<T>, List<Holder<T>>> m_203440_() {
            return Either.left(this.f_205829_);
        }

        @Override
        public boolean m_203333_(Holder<T> p_205834_) {
            return p_205834_.m_203656_(this.f_205829_);
        }

        public String toString() {
            return "NamedSet(" + this.f_205829_ + ")[" + this.f_205830_ + "]";
        }

        @Override
        public boolean m_207277_(Registry<T> p_211049_) {
            return this.f_211044_ == p_211049_;
        }
    }

    public static abstract class ListBacked<T>
    implements HolderSet<T> {
        protected abstract List<Holder<T>> m_203661_();

        @Override
        public int m_203632_() {
            return this.m_203661_().size();
        }

        @Override
        public Spliterator<Holder<T>> spliterator() {
            return this.m_203661_().spliterator();
        }

        @Override
        @NotNull
        public Iterator<Holder<T>> iterator() {
            return this.m_203661_().iterator();
        }

        @Override
        public Stream<Holder<T>> m_203614_() {
            return this.m_203661_().stream();
        }

        @Override
        public Optional<Holder<T>> m_213653_(RandomSource p_235714_) {
            return Util.m_214676_(this.m_203661_(), p_235714_);
        }

        @Override
        public Holder<T> m_203662_(int p_205823_) {
            return this.m_203661_().get(p_205823_);
        }

        @Override
        public boolean m_207277_(Registry<T> p_211043_) {
            return true;
        }
    }
}

