/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class EntityBasedExplosionDamageCalculator
extends ExplosionDamageCalculator {
    private final Entity f_45892_;

    public EntityBasedExplosionDamageCalculator(Entity p_45894_) {
        this.f_45892_ = p_45894_;
    }

    @Override
    public Optional<Float> m_6617_(Explosion p_45902_, BlockGetter p_45903_, BlockPos p_45904_, BlockState p_45905_, FluidState p_45906_) {
        return super.m_6617_(p_45902_, p_45903_, p_45904_, p_45905_, p_45906_).map(p_45913_ -> Float.valueOf(this.f_45892_.m_7077_(p_45902_, p_45903_, p_45904_, p_45905_, p_45906_, p_45913_.floatValue())));
    }

    @Override
    public boolean m_6714_(Explosion p_45896_, BlockGetter p_45897_, BlockPos p_45898_, BlockState p_45899_, float p_45900_) {
        return this.f_45892_.m_7349_(p_45896_, p_45897_, p_45898_, p_45899_, p_45900_);
    }
}

