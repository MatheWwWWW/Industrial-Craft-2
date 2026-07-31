/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.structure.pools;

import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class EmptyPoolElement
extends StructurePoolElement {
    public static final Codec<EmptyPoolElement> f_210174_ = Codec.unit(() -> f_210175_);
    public static final EmptyPoolElement f_210175_ = new EmptyPoolElement();

    private EmptyPoolElement() {
        super(StructureTemplatePool.Projection.TERRAIN_MATCHING);
    }

    @Override
    public Vec3i m_213577_(StructureTemplateManager p_227169_, Rotation p_227170_) {
        return Vec3i.f_123288_;
    }

    @Override
    public List<StructureTemplate.StructureBlockInfo> m_213638_(StructureTemplateManager p_227176_, BlockPos p_227177_, Rotation p_227178_, RandomSource p_227179_) {
        return Collections.emptyList();
    }

    @Override
    public BoundingBox m_214015_(StructureTemplateManager p_227172_, BlockPos p_227173_, Rotation p_227174_) {
        throw new IllegalStateException("Invalid call to EmtyPoolElement.getBoundingBox, filter me!");
    }

    @Override
    public boolean m_213695_(StructureTemplateManager p_227158_, WorldGenLevel p_227159_, StructureManager p_227160_, ChunkGenerator p_227161_, BlockPos p_227162_, BlockPos p_227163_, Rotation p_227164_, BoundingBox p_227165_, RandomSource p_227166_, boolean p_227167_) {
        return true;
    }

    @Override
    public StructurePoolElementType<?> m_207234_() {
        return StructurePoolElementType.f_210545_;
    }

    public String toString() {
        return "Empty";
    }
}

