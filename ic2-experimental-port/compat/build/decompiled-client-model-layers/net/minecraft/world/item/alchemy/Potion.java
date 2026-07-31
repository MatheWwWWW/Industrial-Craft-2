/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item.alchemy;

import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;

public class Potion {
    @Nullable
    private final String f_43481_;
    private final ImmutableList<MobEffectInstance> f_43482_;

    public static Potion m_43489_(String p_43490_) {
        return Registry.f_122828_.m_7745_(ResourceLocation.m_135820_(p_43490_));
    }

    public Potion(MobEffectInstance ... p_43487_) {
        this((String)null, p_43487_);
    }

    public Potion(@Nullable String p_43484_, MobEffectInstance ... p_43485_) {
        this.f_43481_ = p_43484_;
        this.f_43482_ = ImmutableList.copyOf((Object[])p_43485_);
    }

    public String m_43492_(String p_43493_) {
        return p_43493_ + (this.f_43481_ == null ? Registry.f_122828_.m_7981_(this).m_135815_() : this.f_43481_);
    }

    public List<MobEffectInstance> m_43488_() {
        return this.f_43482_;
    }

    public boolean m_43491_() {
        if (!this.f_43482_.isEmpty()) {
            for (MobEffectInstance $$0 : this.f_43482_) {
                if (!$$0.m_19544_().m_8093_()) continue;
                return true;
            }
        }
        return false;
    }
}

