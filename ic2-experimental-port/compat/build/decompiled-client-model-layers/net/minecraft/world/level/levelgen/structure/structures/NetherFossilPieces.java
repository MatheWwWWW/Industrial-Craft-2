/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.structure.structures;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class NetherFossilPieces {
    private static final ResourceLocation[] f_228531_ = new ResourceLocation[]{new ResourceLocation("nether_fossils/fossil_1"), new ResourceLocation("nether_fossils/fossil_2"), new ResourceLocation("nether_fossils/fossil_3"), new ResourceLocation("nether_fossils/fossil_4"), new ResourceLocation("nether_fossils/fossil_5"), new ResourceLocation("nether_fossils/fossil_6"), new ResourceLocation("nether_fossils/fossil_7"), new ResourceLocation("nether_fossils/fossil_8"), new ResourceLocation("nether_fossils/fossil_9"), new ResourceLocation("nether_fossils/fossil_10"), new ResourceLocation("nether_fossils/fossil_11"), new ResourceLocation("nether_fossils/fossil_12"), new ResourceLocation("nether_fossils/fossil_13"), new ResourceLocation("nether_fossils/fossil_14")};

    public static void m_228534_(StructureTemplateManager p_228535_, StructurePieceAccessor p_228536_, RandomSource p_228537_, BlockPos p_228538_) {
        Rotation $$4 = Rotation.m_221990_(p_228537_);
        p_228536_.m_142679_(new NetherFossilPiece(p_228535_, Util.m_214670_(f_228531_, p_228537_), p_228538_, $$4));
    }

    public static class NetherFossilPiece
    extends TemplateStructurePiece {
        public NetherFossilPiece(StructureTemplateManager p_228540_, ResourceLocation p_228541_, BlockPos p_228542_, Rotation p_228543_) {
            super(StructurePieceType.f_210124_, 0, p_228540_, p_228541_, p_228541_.toString(), NetherFossilPiece.m_228555_(p_228543_), p_228542_);
        }

        public NetherFossilPiece(StructureTemplateManager p_228545_, CompoundTag p_228546_) {
            super(StructurePieceType.f_210124_, p_228546_, p_228545_, (ResourceLocation p_228568_) -> NetherFossilPiece.m_228555_(Rotation.valueOf(p_228546_.m_128461_("Rot"))));
        }

        private static StructurePlaceSettings m_228555_(Rotation p_228556_) {
            return new StructurePlaceSettings().m_74379_(p_228556_).m_74377_(Mirror.NONE).m_74383_(BlockIgnoreProcessor.f_74048_);
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_228558_, CompoundTag p_228559_) {
            super.m_183620_(p_228558_, p_228559_);
            p_228559_.m_128359_("Rot", this.f_73657_.m_74404_().name());
        }

        @Override
        protected void m_213704_(String p_228561_, BlockPos p_228562_, ServerLevelAccessor p_228563_, RandomSource p_228564_, BoundingBox p_228565_) {
        }

        @Override
        public void m_213694_(WorldGenLevel p_228548_, StructureManager p_228549_, ChunkGenerator p_228550_, RandomSource p_228551_, BoundingBox p_228552_, ChunkPos p_228553_, BlockPos p_228554_) {
            p_228552_.m_162386_(this.f_73656_.m_74633_(this.f_73657_, this.f_73658_));
            super.m_213694_(p_228548_, p_228549_, p_228550_, p_228551_, p_228552_, p_228553_, p_228554_);
        }
    }
}

