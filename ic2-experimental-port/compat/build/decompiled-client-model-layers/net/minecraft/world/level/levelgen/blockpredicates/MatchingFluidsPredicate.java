/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.level.levelgen.blockpredicates.StateTestingPredicate;
import net.minecraft.world.level.material.Fluid;

class MatchingFluidsPredicate
extends StateTestingPredicate {
    private final HolderSet<Fluid> f_190493_;
    public static final Codec<MatchingFluidsPredicate> f_190492_ = RecordCodecBuilder.create(p_190504_ -> MatchingFluidsPredicate.m_190546_(p_190504_).and((App)RegistryCodecs.m_206277_(Registry.f_122899_).fieldOf("fluids").forGetter(p_204698_ -> p_204698_.f_190493_)).apply((Applicative)p_190504_, MatchingFluidsPredicate::new));

    public MatchingFluidsPredicate(Vec3i p_204695_, HolderSet<Fluid> p_204696_) {
        super(p_204695_);
        this.f_190493_ = p_204696_;
    }

    @Override
    protected boolean m_183454_(BlockState p_190500_) {
        return p_190500_.m_60819_().m_205072_(this.f_190493_);
    }

    @Override
    public BlockPredicateType<?> m_183575_() {
        return BlockPredicateType.f_190437_;
    }
}

