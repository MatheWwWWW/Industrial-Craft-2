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
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class RandomOffsetPlacement
extends PlacementModifier {
    public static final Codec<RandomOffsetPlacement> f_191870_ = RecordCodecBuilder.create(p_191883_ -> p_191883_.group((App)IntProvider.m_146545_(-16, 16).fieldOf("xz_spread").forGetter(p_191894_ -> p_191894_.f_191871_), (App)IntProvider.m_146545_(-16, 16).fieldOf("y_spread").forGetter(p_191885_ -> p_191885_.f_191872_)).apply((Applicative)p_191883_, RandomOffsetPlacement::new));
    private final IntProvider f_191871_;
    private final IntProvider f_191872_;

    public static RandomOffsetPlacement m_191879_(IntProvider p_191880_, IntProvider p_191881_) {
        return new RandomOffsetPlacement(p_191880_, p_191881_);
    }

    public static RandomOffsetPlacement m_191877_(IntProvider p_191878_) {
        return new RandomOffsetPlacement(ConstantInt.m_146483_(0), p_191878_);
    }

    public static RandomOffsetPlacement m_191891_(IntProvider p_191892_) {
        return new RandomOffsetPlacement(p_191892_, ConstantInt.m_146483_(0));
    }

    private RandomOffsetPlacement(IntProvider p_191875_, IntProvider p_191876_) {
        this.f_191871_ = p_191875_;
        this.f_191872_ = p_191876_;
    }

    @Override
    public Stream<BlockPos> m_213676_(PlacementContext p_226393_, RandomSource p_226394_, BlockPos p_226395_) {
        int $$3 = p_226395_.m_123341_() + this.f_191871_.m_214085_(p_226394_);
        int $$4 = p_226395_.m_123342_() + this.f_191872_.m_214085_(p_226394_);
        int $$5 = p_226395_.m_123343_() + this.f_191871_.m_214085_(p_226394_);
        return Stream.of(new BlockPos($$3, $$4, $$5));
    }

    @Override
    public PlacementModifierType<?> m_183327_() {
        return PlacementModifierType.f_191861_;
    }
}

