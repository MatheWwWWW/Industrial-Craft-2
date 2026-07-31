/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class InstantenousMobEffect
extends MobEffect {
    public InstantenousMobEffect(MobEffectCategory p_19440_, int p_19441_) {
        super(p_19440_, p_19441_);
    }

    @Override
    public boolean m_8093_() {
        return true;
    }

    @Override
    public boolean m_6584_(int p_19444_, int p_19445_) {
        return p_19444_ >= 1;
    }
}

