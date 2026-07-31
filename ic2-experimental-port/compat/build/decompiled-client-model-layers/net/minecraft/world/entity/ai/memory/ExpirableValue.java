/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.entity.ai.memory;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.util.VisibleForDebug;

public class ExpirableValue<T> {
    private final T f_26296_;
    private long f_26297_;

    public ExpirableValue(T p_26299_, long p_26300_) {
        this.f_26296_ = p_26299_;
        this.f_26297_ = p_26300_;
    }

    public void m_26301_() {
        if (this.m_26321_()) {
            --this.f_26297_;
        }
    }

    public static <T> ExpirableValue<T> m_26309_(T p_26310_) {
        return new ExpirableValue<T>(p_26310_, Long.MAX_VALUE);
    }

    public static <T> ExpirableValue<T> m_26311_(T p_26312_, long p_26313_) {
        return new ExpirableValue<T>(p_26312_, p_26313_);
    }

    public long m_148191_() {
        return this.f_26297_;
    }

    public T m_26319_() {
        return this.f_26296_;
    }

    public boolean m_26320_() {
        return this.f_26297_ <= 0L;
    }

    public String toString() {
        return this.f_26296_ + (String)(this.m_26321_() ? " (ttl: " + this.f_26297_ + ")" : "");
    }

    @VisibleForDebug
    public boolean m_26321_() {
        return this.f_26297_ != Long.MAX_VALUE;
    }

    public static <T> Codec<ExpirableValue<T>> m_26304_(Codec<T> p_26305_) {
        return RecordCodecBuilder.create(p_26308_ -> p_26308_.group((App)p_26305_.fieldOf("value").forGetter(p_148193_ -> p_148193_.f_26296_), (App)Codec.LONG.optionalFieldOf("ttl").forGetter(p_148187_ -> p_148187_.m_26321_() ? Optional.of(p_148187_.f_26297_) : Optional.empty())).apply((Applicative)p_26308_, (p_148189_, p_148190_) -> new ExpirableValue<Object>(p_148189_, p_148190_.orElse(Long.MAX_VALUE))));
    }
}

