/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Lifecycle
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 */
package net.minecraft.core;

import com.mojang.serialization.Lifecycle;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

public class DefaultedRegistry<T>
extends MappedRegistry<T> {
    private final ResourceLocation f_122309_;
    private Holder<T> f_122310_;

    public DefaultedRegistry(String p_205693_, ResourceKey<? extends Registry<T>> p_205694_, Lifecycle p_205695_, @Nullable Function<T, Holder.Reference<T>> p_205696_) {
        super(p_205694_, p_205695_, p_205696_);
        this.f_122309_ = new ResourceLocation(p_205693_);
    }

    @Override
    public Holder<T> m_203704_(int p_205698_, ResourceKey<T> p_205699_, T p_205700_, Lifecycle p_205701_) {
        Holder<T> $$4 = super.m_203704_(p_205698_, p_205699_, p_205700_, p_205701_);
        if (this.f_122309_.equals(p_205699_.m_135782_())) {
            this.f_122310_ = $$4;
        }
        return $$4;
    }

    @Override
    public int m_7447_(@Nullable T p_122324_) {
        int $$1 = super.m_7447_(p_122324_);
        return $$1 == -1 ? super.m_7447_(this.f_122310_.m_203334_()) : $$1;
    }

    @Override
    @Nonnull
    public ResourceLocation m_7981_(T p_122330_) {
        ResourceLocation $$1 = super.m_7981_(p_122330_);
        return $$1 == null ? this.f_122309_ : $$1;
    }

    @Override
    @Nonnull
    public T m_7745_(@Nullable ResourceLocation p_122328_) {
        Object $$1 = super.m_7745_(p_122328_);
        return $$1 == null ? this.f_122310_.m_203334_() : $$1;
    }

    @Override
    public Optional<T> m_6612_(@Nullable ResourceLocation p_122332_) {
        return Optional.ofNullable(super.m_7745_(p_122332_));
    }

    @Override
    @Nonnull
    public T m_7942_(int p_122317_) {
        Object $$1 = super.m_7942_(p_122317_);
        return $$1 == null ? this.f_122310_.m_203334_() : $$1;
    }

    @Override
    public Optional<Holder<T>> m_213642_(RandomSource p_235665_) {
        return super.m_213642_(p_235665_).or(() -> Optional.of(this.f_122310_));
    }

    public ResourceLocation m_122315_() {
        return this.f_122309_;
    }
}

