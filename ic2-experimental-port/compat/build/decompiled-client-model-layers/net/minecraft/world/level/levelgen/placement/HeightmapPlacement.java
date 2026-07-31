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
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class HeightmapPlacement
extends PlacementModifier {
    public static final Codec<HeightmapPlacement> f_191695_ = RecordCodecBuilder.create(p_191701_ -> p_191701_.group((App)Heightmap.Types.f_64274_.fieldOf("heightmap").forGetter(p_191705_ -> p_191705_.f_191696_)).apply((Applicative)p_191701_, HeightmapPlacement::new));
    private final Heightmap.Types f_191696_;

    private HeightmapPlacement(Heightmap.Types p_191699_) {
        this.f_191696_ = p_191699_;
    }

    public static HeightmapPlacement m_191702_(Heightmap.Types p_191703_) {
        return new HeightmapPlacement(p_191703_);
    }

    @Override
    public Stream<BlockPos> m_213676_(PlacementContext p_226344_, RandomSource p_226345_, BlockPos p_226346_) {
        int $$4;
        int $$3 = p_226346_.m_123341_();
        int $$5 = p_226344_.m_191824_(this.f_191696_, $$3, $$4 = p_226346_.m_123343_());
        if ($$5 > p_226344_.m_191830_()) {
            return Stream.of(new BlockPos($$3, $$5, $$4));
        }
        return Stream.of(new BlockPos[0]);
    }

    @Override
    public PlacementModifierType<?> m_183327_() {
        return PlacementModifierType.f_191858_;
    }
}

