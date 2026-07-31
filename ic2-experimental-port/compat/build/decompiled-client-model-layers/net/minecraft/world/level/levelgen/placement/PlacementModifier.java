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
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public abstract class PlacementModifier {
    public static final Codec<PlacementModifier> f_191842_ = Registry.f_194570_.m_194605_().dispatch(PlacementModifier::m_183327_, PlacementModifierType::m_191869_);

    public abstract Stream<BlockPos> m_213676_(PlacementContext var1, RandomSource var2, BlockPos var3);

    public abstract PlacementModifierType<?> m_183327_();
}

