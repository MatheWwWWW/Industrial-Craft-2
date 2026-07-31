/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class GravityProcessor
extends StructureProcessor {
    public static final Codec<GravityProcessor> f_74100_ = RecordCodecBuilder.create(p_74116_ -> p_74116_.group((App)Heightmap.Types.f_64274_.fieldOf("heightmap").orElse((Object)Heightmap.Types.WORLD_SURFACE_WG).forGetter(p_163729_ -> p_163729_.f_74101_), (App)Codec.INT.fieldOf("offset").orElse((Object)0).forGetter(p_163727_ -> p_163727_.f_74102_)).apply((Applicative)p_74116_, GravityProcessor::new));
    private final Heightmap.Types f_74101_;
    private final int f_74102_;

    public GravityProcessor(Heightmap.Types p_74105_, int p_74106_) {
        this.f_74101_ = p_74105_;
        this.f_74102_ = p_74106_;
    }

    @Override
    @Nullable
    public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_74109_, BlockPos p_74110_, BlockPos p_74111_, StructureTemplate.StructureBlockInfo p_74112_, StructureTemplate.StructureBlockInfo p_74113_, StructurePlaceSettings p_74114_) {
        Heightmap.Types $$9;
        if (p_74109_ instanceof ServerLevel) {
            if (this.f_74101_ == Heightmap.Types.WORLD_SURFACE_WG) {
                Heightmap.Types $$6 = Heightmap.Types.WORLD_SURFACE;
            } else if (this.f_74101_ == Heightmap.Types.OCEAN_FLOOR_WG) {
                Heightmap.Types $$7 = Heightmap.Types.OCEAN_FLOOR;
            } else {
                Heightmap.Types $$8 = this.f_74101_;
            }
        } else {
            $$9 = this.f_74101_;
        }
        int $$10 = p_74109_.m_6924_($$9, p_74113_.f_74675_.m_123341_(), p_74113_.f_74675_.m_123343_()) + this.f_74102_;
        int $$11 = p_74112_.f_74675_.m_123342_();
        return new StructureTemplate.StructureBlockInfo(new BlockPos(p_74113_.f_74675_.m_123341_(), $$10 + $$11, p_74113_.f_74675_.m_123343_()), p_74113_.f_74676_, p_74113_.f_74677_);
    }

    @Override
    protected StructureProcessorType<?> m_6953_() {
        return StructureProcessorType.f_74458_;
    }
}

