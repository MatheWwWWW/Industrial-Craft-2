/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.structures.OceanRuinStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class OceanRuinPieces {
    private static final ResourceLocation[] f_228972_ = new ResourceLocation[]{new ResourceLocation("underwater_ruin/warm_1"), new ResourceLocation("underwater_ruin/warm_2"), new ResourceLocation("underwater_ruin/warm_3"), new ResourceLocation("underwater_ruin/warm_4"), new ResourceLocation("underwater_ruin/warm_5"), new ResourceLocation("underwater_ruin/warm_6"), new ResourceLocation("underwater_ruin/warm_7"), new ResourceLocation("underwater_ruin/warm_8")};
    private static final ResourceLocation[] f_228973_ = new ResourceLocation[]{new ResourceLocation("underwater_ruin/brick_1"), new ResourceLocation("underwater_ruin/brick_2"), new ResourceLocation("underwater_ruin/brick_3"), new ResourceLocation("underwater_ruin/brick_4"), new ResourceLocation("underwater_ruin/brick_5"), new ResourceLocation("underwater_ruin/brick_6"), new ResourceLocation("underwater_ruin/brick_7"), new ResourceLocation("underwater_ruin/brick_8")};
    private static final ResourceLocation[] f_228974_ = new ResourceLocation[]{new ResourceLocation("underwater_ruin/cracked_1"), new ResourceLocation("underwater_ruin/cracked_2"), new ResourceLocation("underwater_ruin/cracked_3"), new ResourceLocation("underwater_ruin/cracked_4"), new ResourceLocation("underwater_ruin/cracked_5"), new ResourceLocation("underwater_ruin/cracked_6"), new ResourceLocation("underwater_ruin/cracked_7"), new ResourceLocation("underwater_ruin/cracked_8")};
    private static final ResourceLocation[] f_228975_ = new ResourceLocation[]{new ResourceLocation("underwater_ruin/mossy_1"), new ResourceLocation("underwater_ruin/mossy_2"), new ResourceLocation("underwater_ruin/mossy_3"), new ResourceLocation("underwater_ruin/mossy_4"), new ResourceLocation("underwater_ruin/mossy_5"), new ResourceLocation("underwater_ruin/mossy_6"), new ResourceLocation("underwater_ruin/mossy_7"), new ResourceLocation("underwater_ruin/mossy_8")};
    private static final ResourceLocation[] f_228976_ = new ResourceLocation[]{new ResourceLocation("underwater_ruin/big_brick_1"), new ResourceLocation("underwater_ruin/big_brick_2"), new ResourceLocation("underwater_ruin/big_brick_3"), new ResourceLocation("underwater_ruin/big_brick_8")};
    private static final ResourceLocation[] f_228977_ = new ResourceLocation[]{new ResourceLocation("underwater_ruin/big_mossy_1"), new ResourceLocation("underwater_ruin/big_mossy_2"), new ResourceLocation("underwater_ruin/big_mossy_3"), new ResourceLocation("underwater_ruin/big_mossy_8")};
    private static final ResourceLocation[] f_228978_ = new ResourceLocation[]{new ResourceLocation("underwater_ruin/big_cracked_1"), new ResourceLocation("underwater_ruin/big_cracked_2"), new ResourceLocation("underwater_ruin/big_cracked_3"), new ResourceLocation("underwater_ruin/big_cracked_8")};
    private static final ResourceLocation[] f_228979_ = new ResourceLocation[]{new ResourceLocation("underwater_ruin/big_warm_4"), new ResourceLocation("underwater_ruin/big_warm_5"), new ResourceLocation("underwater_ruin/big_warm_6"), new ResourceLocation("underwater_ruin/big_warm_7")};

    private static ResourceLocation m_228982_(RandomSource p_228983_) {
        return Util.m_214670_(f_228972_, p_228983_);
    }

    private static ResourceLocation m_229010_(RandomSource p_229011_) {
        return Util.m_214670_(f_228979_, p_229011_);
    }

    public static void m_228994_(StructureTemplateManager p_228995_, BlockPos p_228996_, Rotation p_228997_, StructurePieceAccessor p_228998_, RandomSource p_228999_, OceanRuinStructure p_229000_) {
        boolean $$6 = p_228999_.m_188501_() <= p_229000_.f_229056_;
        float $$7 = $$6 ? 0.9f : 0.8f;
        OceanRuinPieces.m_229001_(p_228995_, p_228996_, p_228997_, p_228998_, p_228999_, p_229000_, $$6, $$7);
        if ($$6 && p_228999_.m_188501_() <= p_229000_.f_229057_) {
            OceanRuinPieces.m_228987_(p_228995_, p_228999_, p_228997_, p_228996_, p_229000_, p_228998_);
        }
    }

    private static void m_228987_(StructureTemplateManager p_228988_, RandomSource p_228989_, Rotation p_228990_, BlockPos p_228991_, OceanRuinStructure p_228992_, StructurePieceAccessor p_228993_) {
        BlockPos $$6 = new BlockPos(p_228991_.m_123341_(), 90, p_228991_.m_123343_());
        BlockPos $$7 = StructureTemplate.m_74593_(new BlockPos(15, 0, 15), Mirror.NONE, p_228990_, BlockPos.f_121853_).m_121955_($$6);
        BoundingBox $$8 = BoundingBox.m_162375_($$6, $$7);
        BlockPos $$9 = new BlockPos(Math.min($$6.m_123341_(), $$7.m_123341_()), $$6.m_123342_(), Math.min($$6.m_123343_(), $$7.m_123343_()));
        List<BlockPos> $$10 = OceanRuinPieces.m_228984_(p_228989_, $$9);
        int $$11 = Mth.m_216271_(p_228989_, 4, 8);
        for (int $$12 = 0; $$12 < $$11; ++$$12) {
            Rotation $$15;
            BlockPos $$16;
            int $$13;
            BlockPos $$14;
            BoundingBox $$17;
            if ($$10.isEmpty() || ($$17 = BoundingBox.m_162375_($$14 = $$10.remove($$13 = p_228989_.m_188503_($$10.size())), $$16 = StructureTemplate.m_74593_(new BlockPos(5, 0, 6), Mirror.NONE, $$15 = Rotation.m_221990_(p_228989_), BlockPos.f_121853_).m_121955_($$14))).m_71049_($$8)) continue;
            OceanRuinPieces.m_229001_(p_228988_, $$14, $$15, p_228993_, p_228989_, p_228992_, false, 0.8f);
        }
    }

    private static List<BlockPos> m_228984_(RandomSource p_228985_, BlockPos p_228986_) {
        ArrayList $$2 = Lists.newArrayList();
        $$2.add(p_228986_.m_7918_(-16 + Mth.m_216271_(p_228985_, 1, 8), 0, 16 + Mth.m_216271_(p_228985_, 1, 7)));
        $$2.add(p_228986_.m_7918_(-16 + Mth.m_216271_(p_228985_, 1, 8), 0, Mth.m_216271_(p_228985_, 1, 7)));
        $$2.add(p_228986_.m_7918_(-16 + Mth.m_216271_(p_228985_, 1, 8), 0, -16 + Mth.m_216271_(p_228985_, 4, 8)));
        $$2.add(p_228986_.m_7918_(Mth.m_216271_(p_228985_, 1, 7), 0, 16 + Mth.m_216271_(p_228985_, 1, 7)));
        $$2.add(p_228986_.m_7918_(Mth.m_216271_(p_228985_, 1, 7), 0, -16 + Mth.m_216271_(p_228985_, 4, 6)));
        $$2.add(p_228986_.m_7918_(16 + Mth.m_216271_(p_228985_, 1, 7), 0, 16 + Mth.m_216271_(p_228985_, 3, 8)));
        $$2.add(p_228986_.m_7918_(16 + Mth.m_216271_(p_228985_, 1, 7), 0, Mth.m_216271_(p_228985_, 1, 7)));
        $$2.add(p_228986_.m_7918_(16 + Mth.m_216271_(p_228985_, 1, 7), 0, -16 + Mth.m_216271_(p_228985_, 4, 8)));
        return $$2;
    }

    private static void m_229001_(StructureTemplateManager p_229002_, BlockPos p_229003_, Rotation p_229004_, StructurePieceAccessor p_229005_, RandomSource p_229006_, OceanRuinStructure p_229007_, boolean p_229008_, float p_229009_) {
        switch (p_229007_.f_229055_) {
            default: {
                ResourceLocation $$8 = p_229008_ ? OceanRuinPieces.m_229010_(p_229006_) : OceanRuinPieces.m_228982_(p_229006_);
                p_229005_.m_142679_(new OceanRuinPiece(p_229002_, $$8, p_229003_, p_229004_, p_229009_, p_229007_.f_229055_, p_229008_));
                break;
            }
            case COLD: {
                ResourceLocation[] $$9 = p_229008_ ? f_228976_ : f_228973_;
                ResourceLocation[] $$10 = p_229008_ ? f_228978_ : f_228974_;
                ResourceLocation[] $$11 = p_229008_ ? f_228977_ : f_228975_;
                int $$12 = p_229006_.m_188503_($$9.length);
                p_229005_.m_142679_(new OceanRuinPiece(p_229002_, $$9[$$12], p_229003_, p_229004_, p_229009_, p_229007_.f_229055_, p_229008_));
                p_229005_.m_142679_(new OceanRuinPiece(p_229002_, $$10[$$12], p_229003_, p_229004_, 0.7f, p_229007_.f_229055_, p_229008_));
                p_229005_.m_142679_(new OceanRuinPiece(p_229002_, $$11[$$12], p_229003_, p_229004_, 0.5f, p_229007_.f_229055_, p_229008_));
            }
        }
    }

    public static class OceanRuinPiece
    extends TemplateStructurePiece {
        private final OceanRuinStructure.Type f_229014_;
        private final float f_229015_;
        private final boolean f_229016_;

        public OceanRuinPiece(StructureTemplateManager p_229018_, ResourceLocation p_229019_, BlockPos p_229020_, Rotation p_229021_, float p_229022_, OceanRuinStructure.Type p_229023_, boolean p_229024_) {
            super(StructurePieceType.f_210102_, 0, p_229018_, p_229019_, p_229019_.toString(), OceanRuinPiece.m_229036_(p_229021_), p_229020_);
            this.f_229015_ = p_229022_;
            this.f_229014_ = p_229023_;
            this.f_229016_ = p_229024_;
        }

        public OceanRuinPiece(StructureTemplateManager p_229026_, CompoundTag p_229027_) {
            super(StructurePieceType.f_210102_, p_229027_, p_229026_, p_229053_ -> OceanRuinPiece.m_229036_(Rotation.valueOf(p_229027_.m_128461_("Rot"))));
            this.f_229015_ = p_229027_.m_128457_("Integrity");
            this.f_229014_ = OceanRuinStructure.Type.valueOf(p_229027_.m_128461_("BiomeType"));
            this.f_229016_ = p_229027_.m_128471_("IsLarge");
        }

        private static StructurePlaceSettings m_229036_(Rotation p_229037_) {
            return new StructurePlaceSettings().m_74379_(p_229037_).m_74377_(Mirror.NONE).m_74383_(BlockIgnoreProcessor.f_74048_);
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_229039_, CompoundTag p_229040_) {
            super.m_183620_(p_229039_, p_229040_);
            p_229040_.m_128359_("Rot", this.f_73657_.m_74404_().name());
            p_229040_.m_128350_("Integrity", this.f_229015_);
            p_229040_.m_128359_("BiomeType", this.f_229014_.toString());
            p_229040_.m_128379_("IsLarge", this.f_229016_);
        }

        @Override
        protected void m_213704_(String p_229046_, BlockPos p_229047_, ServerLevelAccessor p_229048_, RandomSource p_229049_, BoundingBox p_229050_) {
            if ("chest".equals(p_229046_)) {
                p_229048_.m_7731_(p_229047_, (BlockState)Blocks.f_50087_.m_49966_().m_61124_(ChestBlock.f_51480_, p_229048_.m_6425_(p_229047_).m_205070_(FluidTags.f_13131_)), 2);
                BlockEntity $$5 = p_229048_.m_7702_(p_229047_);
                if ($$5 instanceof ChestBlockEntity) {
                    ((ChestBlockEntity)$$5).m_59626_(this.f_229016_ ? BuiltInLootTables.f_78691_ : BuiltInLootTables.f_78690_, p_229049_.m_188505_());
                }
            } else if ("drowned".equals(p_229046_)) {
                Drowned $$6 = EntityType.f_20562_.m_20615_(p_229048_.m_6018_());
                $$6.m_21530_();
                $$6.m_20035_(p_229047_, 0.0f, 0.0f);
                $$6.m_6518_(p_229048_, p_229048_.m_6436_(p_229047_), MobSpawnType.STRUCTURE, null, null);
                p_229048_.m_47205_($$6);
                if (p_229047_.m_123342_() > p_229048_.m_5736_()) {
                    p_229048_.m_7731_(p_229047_, Blocks.f_50016_.m_49966_(), 2);
                } else {
                    p_229048_.m_7731_(p_229047_, Blocks.f_49990_.m_49966_(), 2);
                }
            }
        }

        @Override
        public void m_213694_(WorldGenLevel p_229029_, StructureManager p_229030_, ChunkGenerator p_229031_, RandomSource p_229032_, BoundingBox p_229033_, ChunkPos p_229034_, BlockPos p_229035_) {
            this.f_73657_.m_74394_().m_74383_(new BlockRotProcessor(this.f_229015_)).m_74383_(BlockIgnoreProcessor.f_74048_);
            int $$7 = p_229029_.m_6924_(Heightmap.Types.OCEAN_FLOOR_WG, this.f_73658_.m_123341_(), this.f_73658_.m_123343_());
            this.f_73658_ = new BlockPos(this.f_73658_.m_123341_(), $$7, this.f_73658_.m_123343_());
            BlockPos $$8 = StructureTemplate.m_74593_(new BlockPos(this.f_73656_.m_163801_().m_123341_() - 1, 0, this.f_73656_.m_163801_().m_123343_() - 1), Mirror.NONE, this.f_73657_.m_74404_(), BlockPos.f_121853_).m_121955_(this.f_73658_);
            this.f_73658_ = new BlockPos(this.f_73658_.m_123341_(), this.m_229041_(this.f_73658_, p_229029_, $$8), this.f_73658_.m_123343_());
            super.m_213694_(p_229029_, p_229030_, p_229031_, p_229032_, p_229033_, p_229034_, p_229035_);
        }

        private int m_229041_(BlockPos p_229042_, BlockGetter p_229043_, BlockPos p_229044_) {
            int $$3 = p_229042_.m_123342_();
            int $$4 = 512;
            int $$5 = $$3 - 1;
            int $$6 = 0;
            for (BlockPos $$7 : BlockPos.m_121940_(p_229042_, p_229044_)) {
                int $$8 = $$7.m_123341_();
                int $$9 = $$7.m_123343_();
                int $$10 = p_229042_.m_123342_() - 1;
                BlockPos.MutableBlockPos $$11 = new BlockPos.MutableBlockPos($$8, $$10, $$9);
                BlockState $$12 = p_229043_.m_8055_($$11);
                FluidState $$13 = p_229043_.m_6425_($$11);
                while (($$12.m_60795_() || $$13.m_205070_(FluidTags.f_13131_) || $$12.m_204336_(BlockTags.f_13047_)) && $$10 > p_229043_.m_141937_() + 1) {
                    $$11.m_122178_($$8, --$$10, $$9);
                    $$12 = p_229043_.m_8055_($$11);
                    $$13 = p_229043_.m_6425_($$11);
                }
                $$4 = Math.min($$4, $$10);
                if ($$10 >= $$5 - 2) continue;
                ++$$6;
            }
            int $$14 = Math.abs(p_229042_.m_123341_() - p_229044_.m_123341_());
            if ($$5 - $$4 > 2 && $$6 > $$14 - 2) {
                $$3 = $$4 + 1;
            }
            return $$3;
        }
    }
}

