/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.placement;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public abstract class RepeatingPlacement
extends PlacementModifier {
    protected abstract int m_213944_(RandomSource var1, BlockPos var2);

    @Override
    public Stream<BlockPos> m_213676_(PlacementContext p_226403_, RandomSource p_226404_, BlockPos p_226405_) {
        return IntStream.range(0, this.m_213944_(p_226404_, p_226405_)).mapToObj(p_191912_ -> p_226405_);
    }
}

