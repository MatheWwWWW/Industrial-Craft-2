/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.core;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

public interface HolderLookup<T> {
    public Optional<Holder<T>> m_213562_(ResourceKey<T> var1);

    public Stream<ResourceKey<T>> m_214062_();

    public Optional<? extends HolderSet<T>> m_213810_(TagKey<T> var1);

    public Stream<TagKey<T>> m_214063_();

    public static <T> HolderLookup<T> m_235701_(Registry<T> p_235702_) {
        return new RegistryLookup<T>(p_235702_);
    }

    public static class RegistryLookup<T>
    implements HolderLookup<T> {
        protected final Registry<T> f_235703_;

        public RegistryLookup(Registry<T> p_235705_) {
            this.f_235703_ = p_235705_;
        }

        @Override
        public Optional<Holder<T>> m_213562_(ResourceKey<T> p_235708_) {
            return this.f_235703_.m_203636_(p_235708_);
        }

        @Override
        public Stream<ResourceKey<T>> m_214062_() {
            return this.f_235703_.m_6579_().stream().map(Map.Entry::getKey);
        }

        @Override
        public Optional<? extends HolderSet<T>> m_213810_(TagKey<T> p_235710_) {
            return this.f_235703_.m_203431_(p_235710_);
        }

        @Override
        public Stream<TagKey<T>> m_214063_() {
            return this.f_235703_.m_203613_();
        }
    }
}

