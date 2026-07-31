/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Either
 *  javax.annotation.Nullable
 */
package net.minecraft.core;

import com.mojang.datafixers.util.Either;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public interface Holder<T> {
    public T m_203334_();

    public boolean m_203633_();

    public boolean m_203373_(ResourceLocation var1);

    public boolean m_203565_(ResourceKey<T> var1);

    public boolean m_203425_(Predicate<ResourceKey<T>> var1);

    public boolean m_203656_(TagKey<T> var1);

    public Stream<TagKey<T>> m_203616_();

    public Either<ResourceKey<T>, T> m_203439_();

    public Optional<ResourceKey<T>> m_203543_();

    public Kind m_203376_();

    public boolean m_203401_(Registry<T> var1);

    public static <T> Holder<T> m_205709_(T p_205710_) {
        return new Direct<T>(p_205710_);
    }

    public static <T> Holder<T> m_205706_(Holder<? extends T> p_205707_) {
        return p_205707_;
    }

    public static final class Direct<T>
    extends Record
    implements Holder<T> {
        private final T f_205714_;

        public Direct(T f_205714_) {
            this.f_205714_ = f_205714_;
        }

        @Override
        public boolean m_203633_() {
            return true;
        }

        @Override
        public boolean m_203373_(ResourceLocation p_205727_) {
            return false;
        }

        @Override
        public boolean m_203565_(ResourceKey<T> p_205725_) {
            return false;
        }

        @Override
        public boolean m_203656_(TagKey<T> p_205719_) {
            return false;
        }

        @Override
        public boolean m_203425_(Predicate<ResourceKey<T>> p_205723_) {
            return false;
        }

        @Override
        public Either<ResourceKey<T>, T> m_203439_() {
            return Either.right(this.f_205714_);
        }

        @Override
        public Optional<ResourceKey<T>> m_203543_() {
            return Optional.empty();
        }

        @Override
        public Kind m_203376_() {
            return Kind.DIRECT;
        }

        @Override
        public String toString() {
            return "Direct{" + this.f_205714_ + "}";
        }

        @Override
        public boolean m_203401_(Registry<T> p_205721_) {
            return true;
        }

        @Override
        public Stream<TagKey<T>> m_203616_() {
            return Stream.of(new TagKey[0]);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Direct.class, "value", "f_205714_"}, this);
        }

        @Override
        public final boolean equals(Object p_205733_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Direct.class, "value", "f_205714_"}, this, p_205733_);
        }

        @Override
        public T m_203334_() {
            return this.f_205714_;
        }
    }

    public static class Reference<T>
    implements Holder<T> {
        private final Registry<T> f_205748_;
        private Set<TagKey<T>> f_205749_ = Set.of();
        private final Type f_205750_;
        @Nullable
        private ResourceKey<T> f_205751_;
        @Nullable
        private T f_205752_;

        private Reference(Type p_205754_, Registry<T> p_205755_, @Nullable ResourceKey<T> p_205756_, @Nullable T p_205757_) {
            this.f_205748_ = p_205755_;
            this.f_205750_ = p_205754_;
            this.f_205751_ = p_205756_;
            this.f_205752_ = p_205757_;
        }

        public static <T> Reference<T> m_205766_(Registry<T> p_205767_, ResourceKey<T> p_205768_) {
            return new Reference<Object>(Type.STAND_ALONE, p_205767_, p_205768_, null);
        }

        @Deprecated
        public static <T> Reference<T> m_205763_(Registry<T> p_205764_, @Nullable T p_205765_) {
            return new Reference<T>(Type.INTRUSIVE, p_205764_, null, p_205765_);
        }

        public ResourceKey<T> m_205785_() {
            if (this.f_205751_ == null) {
                throw new IllegalStateException("Trying to access unbound value '" + this.f_205752_ + "' from registry " + this.f_205748_);
            }
            return this.f_205751_;
        }

        @Override
        public T m_203334_() {
            if (this.f_205752_ == null) {
                throw new IllegalStateException("Trying to access unbound value '" + this.f_205751_ + "' from registry " + this.f_205748_);
            }
            return this.f_205752_;
        }

        @Override
        public boolean m_203373_(ResourceLocation p_205779_) {
            return this.m_205785_().m_135782_().equals(p_205779_);
        }

        @Override
        public boolean m_203565_(ResourceKey<T> p_205774_) {
            return this.m_205785_() == p_205774_;
        }

        @Override
        public boolean m_203656_(TagKey<T> p_205760_) {
            return this.f_205749_.contains(p_205760_);
        }

        @Override
        public boolean m_203425_(Predicate<ResourceKey<T>> p_205772_) {
            return p_205772_.test(this.m_205785_());
        }

        @Override
        public boolean m_203401_(Registry<T> p_205762_) {
            return this.f_205748_ == p_205762_;
        }

        @Override
        public Either<ResourceKey<T>, T> m_203439_() {
            return Either.left(this.m_205785_());
        }

        @Override
        public Optional<ResourceKey<T>> m_203543_() {
            return Optional.of(this.m_205785_());
        }

        @Override
        public Kind m_203376_() {
            return Kind.REFERENCE;
        }

        @Override
        public boolean m_203633_() {
            return this.f_205751_ != null && this.f_205752_ != null;
        }

        void m_205775_(ResourceKey<T> p_205776_, T p_205777_) {
            if (this.f_205751_ != null && p_205776_ != this.f_205751_) {
                throw new IllegalStateException("Can't change holder key: existing=" + this.f_205751_ + ", new=" + p_205776_);
            }
            if (this.f_205750_ == Type.INTRUSIVE && this.f_205752_ != p_205777_) {
                throw new IllegalStateException("Can't change holder " + p_205776_ + " value: existing=" + this.f_205752_ + ", new=" + p_205777_);
            }
            this.f_205751_ = p_205776_;
            this.f_205752_ = p_205777_;
        }

        void m_205769_(Collection<TagKey<T>> p_205770_) {
            this.f_205749_ = Set.copyOf(p_205770_);
        }

        @Override
        public Stream<TagKey<T>> m_203616_() {
            return this.f_205749_.stream();
        }

        public String toString() {
            return "Reference{" + this.f_205751_ + "=" + this.f_205752_ + "}";
        }

        static final class Type
        extends Enum<Type> {
            public static final /* enum */ Type STAND_ALONE = new Type();
            public static final /* enum */ Type INTRUSIVE = new Type();
            private static final /* synthetic */ Type[] $VALUES;

            public static Type[] values() {
                return (Type[])$VALUES.clone();
            }

            public static Type valueOf(String p_205796_) {
                return Enum.valueOf(Type.class, p_205796_);
            }

            private static /* synthetic */ Type[] m_205794_() {
                return new Type[]{STAND_ALONE, INTRUSIVE};
            }

            static {
                $VALUES = Type.m_205794_();
            }
        }
    }

    public static final class Kind
    extends Enum<Kind> {
        public static final /* enum */ Kind REFERENCE = new Kind();
        public static final /* enum */ Kind DIRECT = new Kind();
        private static final /* synthetic */ Kind[] $VALUES;

        public static Kind[] values() {
            return (Kind[])$VALUES.clone();
        }

        public static Kind valueOf(String p_205746_) {
            return Enum.valueOf(Kind.class, p_205746_);
        }

        private static /* synthetic */ Kind[] m_205744_() {
            return new Kind[]{REFERENCE, DIRECT};
        }

        static {
            $VALUES = Kind.m_205744_();
        }
    }
}

