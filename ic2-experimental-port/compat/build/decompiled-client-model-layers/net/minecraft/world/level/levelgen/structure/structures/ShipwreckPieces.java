/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.structure.structures;

import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class ShipwreckPieces {
    static final BlockPos f_229339_ = new BlockPos(4, 0, 15);
    private static final ResourceLocation[] f_229340_ = new ResourceLocation[]{new ResourceLocation("shipwreck/with_mast"), new ResourceLocation("shipwreck/sideways_full"), new ResourceLocation("shipwreck/sideways_fronthalf"), new ResourceLocation("shipwreck/sideways_backhalf"), new ResourceLocation("shipwreck/rightsideup_full"), new ResourceLocation("shipwreck/rightsideup_fronthalf"), new ResourceLocation("shipwreck/rightsideup_backhalf"), new ResourceLocation("shipwreck/with_mast_degraded"), new ResourceLocation("shipwreck/rightsideup_full_degraded"), new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"), new ResourceLocation("shipwreck/rightsideup_backhalf_degraded")};
    private static final ResourceLocation[] f_229341_ = new ResourceLocation[]{new ResourceLocation("shipwreck/with_mast"), new ResourceLocation("shipwreck/upsidedown_full"), new ResourceLocation("shipwreck/upsidedown_fronthalf"), new ResourceLocation("shipwreck/upsidedown_backhalf"), new ResourceLocation("shipwreck/sideways_full"), new ResourceLocation("shipwreck/sideways_fronthalf"), new ResourceLocation("shipwreck/sideways_backhalf"), new ResourceLocation("shipwreck/rightsideup_full"), new ResourceLocation("shipwreck/rightsideup_fronthalf"), new ResourceLocation("shipwreck/rightsideup_backhalf"), new ResourceLocation("shipwreck/with_mast_degraded"), new ResourceLocation("shipwreck/upsidedown_full_degraded"), new ResourceLocation("shipwreck/upsidedown_fronthalf_degraded"), new ResourceLocation("shipwreck/upsidedown_backhalf_degraded"), new ResourceLocation("shipwreck/sideways_full_degraded"), new ResourceLocation("shipwreck/sideways_fronthalf_degraded"), new ResourceLocation("shipwreck/sideways_backhalf_degraded"), new ResourceLocation("shipwreck/rightsideup_full_degraded"), new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"), new ResourceLocation("shipwreck/rightsideup_backhalf_degraded")};
    static final Map<String, ResourceLocation> f_229342_ = Map.of("map_chest", BuiltInLootTables.f_78693_, "treasure_chest", BuiltInLootTables.f_78695_, "supply_chest", BuiltInLootTables.f_78694_);

    public static void m_229345_(StructureTemplateManager p_229346_, BlockPos p_229347_, Rotation p_229348_, StructurePieceAccessor p_229349_, RandomSource p_229350_, boolean p_229351_) {
        ResourceLocation $$6 = Util.m_214670_(p_229351_ ? f_229340_ : f_229341_, p_229350_);
        p_229349_.m_142679_(new ShipwreckPiece(p_229346_, $$6, p_229347_, p_229348_, p_229351_));
    }

    public static class ShipwreckPiece
    extends TemplateStructurePiece {
        private final boolean f_229352_;

        public ShipwreckPiece(StructureTemplateManager p_229354_, ResourceLocation p_229355_, BlockPos p_229356_, Rotation p_229357_, boolean p_229358_) {
            super(StructurePieceType.f_210123_, 0, p_229354_, p_229355_, p_229355_.toString(), ShipwreckPiece.m_229370_(p_229357_), p_229356_);
            this.f_229352_ = p_229358_;
        }

        public ShipwreckPiece(StructureTemplateManager p_229360_, CompoundTag p_229361_) {
            super(StructurePieceType.f_210123_, p_229361_, p_229360_, p_229383_ -> ShipwreckPiece.m_229370_(Rotation.valueOf(p_229361_.m_128461_("Rot"))));
            this.f_229352_ = p_229361_.m_128471_("isBeached");
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_229373_, CompoundTag p_229374_) {
            super.m_183620_(p_229373_, p_229374_);
            p_229374_.m_128379_("isBeached", this.f_229352_);
            p_229374_.m_128359_("Rot", this.f_73657_.m_74404_().name());
        }

        private static StructurePlaceSettings m_229370_(Rotation p_229371_) {
            return new StructurePlaceSettings().m_74379_(p_229371_).m_74377_(Mirror.NONE).m_74385_(f_229339_).m_74383_(BlockIgnoreProcessor.f_74048_);
        }

        @Override
        protected void m_213704_(String p_229376_, BlockPos p_229377_, ServerLevelAccessor p_229378_, RandomSource p_229379_, BoundingBox p_229380_) {
            ResourceLocation $$5 = f_229342_.get(p_229376_);
            if ($$5 != null) {
                RandomizableContainerBlockEntity.m_222766_(p_229378_, p_229379_, p_229377_.m_7495_(), $$5);
            }
        }

        @Override
        public void m_213694_(WorldGenLevel p_229363_, StructureManager p_229364_, ChunkGenerator p_229365_, RandomSource p_229366_, BoundingBox p_229367_, ChunkPos p_229368_, BlockPos p_229369_) {
            int $$7 = p_229363_.m_151558_();
            int $$8 = 0;
            Vec3i $$9 = this.f_73656_.m_163801_();
            Heightmap.Types $$10 = this.f_229352_ ? Heightmap.Types.WORLD_SURFACE_WG : Heightmap.Types.OCEAN_FLOOR_WG;
            int $$11 = $$9.m_123341_() * $$9.m_123343_();
            if ($$11 == 0) {
                $$8 = p_229363_.m_6924_($$10, this.f_73658_.m_123341_(), this.f_73658_.m_123343_());
            } else {
                BlockPos $$12 = this.f_73658_.m_7918_($$9.m_123341_() - 1, 0, $$9.m_123343_() - 1);
                for (BlockPos $$13 : BlockPos.m_121940_(this.f_73658_, $$12)) {
                    int $$14 = p_229363_.m_6924_($$10, $$13.m_123341_(), $$13.m_123343_());
                    $$8 += $$14;
                    $$7 = Math.min($$7, $$14);
                }
                $$8 /= $$11;
            }
            int $$15 = this.f_229352_ ? $$7 - $$9.m_123342_() / 2 - p_229366_.m_188503_(3) : $$8;
            this.f_73658_ = new BlockPos(this.f_73658_.m_123341_(), $$15, this.f_73658_.m_123343_());
            super.m_213694_(p_229363_, p_229364_, p_229365_, p_229366_, p_229367_, p_229368_, p_229369_);
        }
    }
}

