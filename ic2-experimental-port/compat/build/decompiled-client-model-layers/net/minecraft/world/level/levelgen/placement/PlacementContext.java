/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.placement;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class PlacementContext
extends WorldGenerationContext {
    private final WorldGenLevel f_191814_;
    private final ChunkGenerator f_191815_;
    private final Optional<PlacedFeature> f_191816_;

    public PlacementContext(WorldGenLevel p_191818_, ChunkGenerator p_191819_, Optional<PlacedFeature> p_191820_) {
        super(p_191819_, p_191818_);
        this.f_191814_ = p_191818_;
        this.f_191815_ = p_191819_;
        this.f_191816_ = p_191820_;
    }

    public int m_191824_(Heightmap.Types p_191825_, int p_191826_, int p_191827_) {
        return this.f_191814_.m_6924_(p_191825_, p_191826_, p_191827_);
    }

    public CarvingMask m_191821_(ChunkPos p_191822_, GenerationStep.Carving p_191823_) {
        return ((ProtoChunk)this.f_191814_.m_6325_(p_191822_.f_45578_, p_191822_.f_45579_)).m_183613_(p_191823_);
    }

    public BlockState m_191828_(BlockPos p_191829_) {
        return this.f_191814_.m_8055_(p_191829_);
    }

    public int m_191830_() {
        return this.f_191814_.m_141937_();
    }

    public WorldGenLevel m_191831_() {
        return this.f_191814_;
    }

    public Optional<PlacedFeature> m_191832_() {
        return this.f_191816_;
    }

    public ChunkGenerator m_191833_() {
        return this.f_191815_;
    }
}

