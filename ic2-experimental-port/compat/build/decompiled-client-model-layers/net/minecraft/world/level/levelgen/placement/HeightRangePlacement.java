/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.placement;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class HeightRangePlacement
extends PlacementModifier {
    public static final Codec<HeightRangePlacement> f_191673_ = RecordCodecBuilder.create(p_191679_ -> p_191679_.group((App)HeightProvider.f_161970_.fieldOf("height").forGetter(p_191686_ -> p_191686_.f_191674_)).apply((Applicative)p_191679_, HeightRangePlacement::new));
    private final HeightProvider f_191674_;

    private HeightRangePlacement(HeightProvider p_191677_) {
        this.f_191674_ = p_191677_;
    }

    public static HeightRangePlacement m_191683_(HeightProvider p_191684_) {
        return new HeightRangePlacement(p_191684_);
    }

    public static HeightRangePlacement m_191680_(VerticalAnchor p_191681_, VerticalAnchor p_191682_) {
        return HeightRangePlacement.m_191683_(UniformHeight.m_162034_(p_191681_, p_191682_));
    }

    public static HeightRangePlacement m_191692_(VerticalAnchor p_191693_, VerticalAnchor p_191694_) {
        return HeightRangePlacement.m_191683_(TrapezoidHeight.m_162006_(p_191693_, p_191694_));
    }

    @Override
    public Stream<BlockPos> m_213676_(PlacementContext p_226340_, RandomSource p_226341_, BlockPos p_226342_) {
        return Stream.of(p_226342_.m_175288_(this.f_191674_.m_213859_(p_226341_, p_226340_)));
    }

    @Override
    public PlacementModifierType<?> m_183327_() {
        return PlacementModifierType.f_191859_;
    }
}

