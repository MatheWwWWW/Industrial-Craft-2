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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.level.levelgen.blockpredicates.StateTestingPredicate;

class MatchingBlocksPredicate
extends StateTestingPredicate {
    private final HolderSet<Block> f_190480_;
    public static final Codec<MatchingBlocksPredicate> f_190479_ = RecordCodecBuilder.create(p_190491_ -> MatchingBlocksPredicate.m_190546_(p_190491_).and((App)RegistryCodecs.m_206277_(Registry.f_122901_).fieldOf("blocks").forGetter(p_204693_ -> p_204693_.f_190480_)).apply((Applicative)p_190491_, MatchingBlocksPredicate::new));

    public MatchingBlocksPredicate(Vec3i p_204690_, HolderSet<Block> p_204691_) {
        super(p_204690_);
        this.f_190480_ = p_204691_;
    }

    @Override
    protected boolean m_183454_(BlockState p_190487_) {
        return p_190487_.m_204341_(this.f_190480_);
    }

    @Override
    public BlockPredicateType<?> m_183575_() {
        return BlockPredicateType.f_190436_;
    }
}

