/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.placement;

import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public abstract class PlacementFilter
extends PlacementModifier {
    @Override
    public final Stream<BlockPos> m_213676_(PlacementContext p_226386_, RandomSource p_226387_, BlockPos p_226388_) {
        if (this.m_213917_(p_226386_, p_226387_, p_226388_)) {
            return Stream.of(p_226388_);
        }
        return Stream.of(new BlockPos[0]);
    }

    protected abstract boolean m_213917_(PlacementContext var1, RandomSource var2, BlockPos var3);
}

