/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.stats;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class Stat<T>
extends ObjectiveCriteria {
    private final StatFormatter f_12852_;
    private final T f_12853_;
    private final StatType<T> f_12854_;

    protected Stat(StatType<T> p_12856_, T p_12857_, StatFormatter p_12858_) {
        super(Stat.m_12862_(p_12856_, p_12857_));
        this.f_12854_ = p_12856_;
        this.f_12852_ = p_12858_;
        this.f_12853_ = p_12857_;
    }

    public static <T> String m_12862_(StatType<T> p_12863_, T p_12864_) {
        return Stat.m_12865_(Registry.f_122867_.m_7981_(p_12863_)) + ":" + Stat.m_12865_(p_12863_.m_12893_().m_7981_(p_12864_));
    }

    private static <T> String m_12865_(@Nullable ResourceLocation p_12866_) {
        return p_12866_.toString().replace(':', '.');
    }

    public StatType<T> m_12859_() {
        return this.f_12854_;
    }

    public T m_12867_() {
        return this.f_12853_;
    }

    public String m_12860_(int p_12861_) {
        return this.f_12852_.m_12886_(p_12861_);
    }

    public boolean equals(Object p_12869_) {
        return this == p_12869_ || p_12869_ instanceof Stat && Objects.equals(this.m_83620_(), ((Stat)p_12869_).m_83620_());
    }

    public int hashCode() {
        return this.m_83620_().hashCode();
    }

    public String toString() {
        return "Stat{name=" + this.m_83620_() + ", formatter=" + this.f_12852_ + "}";
    }
}

