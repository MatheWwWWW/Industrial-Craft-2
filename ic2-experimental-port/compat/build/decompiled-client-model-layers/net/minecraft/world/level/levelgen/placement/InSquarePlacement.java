/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class InSquarePlacement
extends PlacementModifier {
    private static final InSquarePlacement f_191712_ = new InSquarePlacement();
    public static final Codec<InSquarePlacement> f_191711_ = Codec.unit(() -> f_191712_);

    public static InSquarePlacement m_191715_() {
        return f_191712_;
    }

    @Override
    public Stream<BlockPos> m_213676_(PlacementContext p_226348_, RandomSource p_226349_, BlockPos p_226350_) {
        int $$3 = p_226349_.m_188503_(16) + p_226350_.m_123341_();
        int $$4 = p_226349_.m_188503_(16) + p_226350_.m_123343_();
        return Stream.of(new BlockPos($$3, p_226350_.m_123342_(), $$4));
    }

    @Override
    public PlacementModifierType<?> m_183327_() {
        return PlacementModifierType.f_191860_;
    }
}

