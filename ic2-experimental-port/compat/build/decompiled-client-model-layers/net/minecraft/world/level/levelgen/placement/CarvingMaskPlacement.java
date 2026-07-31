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
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class CarvingMaskPlacement
extends PlacementModifier {
    public static final Codec<CarvingMaskPlacement> f_191585_ = GenerationStep.Carving.f_64194_.fieldOf("step").xmap(CarvingMaskPlacement::new, p_191593_ -> p_191593_.f_191586_).codec();
    private final GenerationStep.Carving f_191586_;

    private CarvingMaskPlacement(GenerationStep.Carving p_191589_) {
        this.f_191586_ = p_191589_;
    }

    public static CarvingMaskPlacement m_191590_(GenerationStep.Carving p_191591_) {
        return new CarvingMaskPlacement(p_191591_);
    }

    @Override
    public Stream<BlockPos> m_213676_(PlacementContext p_226325_, RandomSource p_226326_, BlockPos p_226327_) {
        ChunkPos $$3 = new ChunkPos(p_226327_);
        return p_226325_.m_191821_($$3, this.f_191586_).m_187589_($$3);
    }

    @Override
    public PlacementModifierType<?> m_183327_() {
        return PlacementModifierType.f_191862_;
    }
}

