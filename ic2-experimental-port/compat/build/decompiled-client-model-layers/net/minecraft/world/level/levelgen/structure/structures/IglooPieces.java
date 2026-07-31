/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class IglooPieces {
    public static final int f_227540_ = 90;
    static final ResourceLocation f_227541_ = new ResourceLocation("igloo/top");
    private static final ResourceLocation f_227542_ = new ResourceLocation("igloo/middle");
    private static final ResourceLocation f_227543_ = new ResourceLocation("igloo/bottom");
    static final Map<ResourceLocation, BlockPos> f_227544_ = ImmutableMap.of((Object)f_227541_, (Object)new BlockPos(3, 5, 5), (Object)f_227542_, (Object)new BlockPos(1, 3, 1), (Object)f_227543_, (Object)new BlockPos(3, 6, 7));
    static final Map<ResourceLocation, BlockPos> f_227545_ = ImmutableMap.of((Object)f_227541_, (Object)BlockPos.f_121853_, (Object)f_227542_, (Object)new BlockPos(2, -3, 4), (Object)f_227543_, (Object)new BlockPos(0, -3, -2));

    public static void m_227548_(StructureTemplateManager p_227549_, BlockPos p_227550_, Rotation p_227551_, StructurePieceAccessor p_227552_, RandomSource p_227553_) {
        if (p_227553_.m_188500_() < 0.5) {
            int $$5 = p_227553_.m_188503_(8) + 4;
            p_227552_.m_142679_(new IglooPiece(p_227549_, f_227543_, p_227550_, p_227551_, $$5 * 3));
            for (int $$6 = 0; $$6 < $$5 - 1; ++$$6) {
                p_227552_.m_142679_(new IglooPiece(p_227549_, f_227542_, p_227550_, p_227551_, $$6 * 3));
            }
        }
        p_227552_.m_142679_(new IglooPiece(p_227549_, f_227541_, p_227550_, p_227551_, 0));
    }

    public static class IglooPiece
    extends TemplateStructurePiece {
        public IglooPiece(StructureTemplateManager p_227555_, ResourceLocation p_227556_, BlockPos p_227557_, Rotation p_227558_, int p_227559_) {
            super(StructurePieceType.f_210103_, 0, p_227555_, p_227556_, p_227556_.toString(), IglooPiece.m_227575_(p_227558_, p_227556_), IglooPiece.m_227563_(p_227556_, p_227557_, p_227559_));
        }

        public IglooPiece(StructureTemplateManager p_227561_, CompoundTag p_227562_) {
            super(StructurePieceType.f_210103_, p_227562_, p_227561_, p_227589_ -> IglooPiece.m_227575_(Rotation.valueOf(p_227562_.m_128461_("Rot")), p_227589_));
        }

        private static StructurePlaceSettings m_227575_(Rotation p_227576_, ResourceLocation p_227577_) {
            return new StructurePlaceSettings().m_74379_(p_227576_).m_74377_(Mirror.NONE).m_74385_(f_227544_.get(p_227577_)).m_74383_(BlockIgnoreProcessor.f_74046_);
        }

        private static BlockPos m_227563_(ResourceLocation p_227564_, BlockPos p_227565_, int p_227566_) {
            return p_227565_.m_121955_(f_227545_.get(p_227564_)).m_6625_(p_227566_);
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_227579_, CompoundTag p_227580_) {
            super.m_183620_(p_227579_, p_227580_);
            p_227580_.m_128359_("Rot", this.f_73657_.m_74404_().name());
        }

        @Override
        protected void m_213704_(String p_227582_, BlockPos p_227583_, ServerLevelAccessor p_227584_, RandomSource p_227585_, BoundingBox p_227586_) {
            if (!"chest".equals(p_227582_)) {
                return;
            }
            p_227584_.m_7731_(p_227583_, Blocks.f_50016_.m_49966_(), 3);
            BlockEntity $$5 = p_227584_.m_7702_(p_227583_.m_7495_());
            if ($$5 instanceof ChestBlockEntity) {
                ((ChestBlockEntity)$$5).m_59626_(BuiltInLootTables.f_78688_, p_227585_.m_188505_());
            }
        }

        @Override
        public void m_213694_(WorldGenLevel p_227568_, StructureManager p_227569_, ChunkGenerator p_227570_, RandomSource p_227571_, BoundingBox p_227572_, ChunkPos p_227573_, BlockPos p_227574_) {
            BlockPos $$13;
            BlockState $$14;
            ResourceLocation $$7 = new ResourceLocation(this.f_163658_);
            StructurePlaceSettings $$8 = IglooPiece.m_227575_(this.f_73657_.m_74404_(), $$7);
            BlockPos $$9 = f_227545_.get($$7);
            BlockPos $$10 = this.f_73658_.m_121955_(StructureTemplate.m_74563_($$8, new BlockPos(3 - $$9.m_123341_(), 0, -$$9.m_123343_())));
            int $$11 = p_227568_.m_6924_(Heightmap.Types.WORLD_SURFACE_WG, $$10.m_123341_(), $$10.m_123343_());
            BlockPos $$12 = this.f_73658_;
            this.f_73658_ = this.f_73658_.m_7918_(0, $$11 - 90 - 1, 0);
            super.m_213694_(p_227568_, p_227569_, p_227570_, p_227571_, p_227572_, p_227573_, p_227574_);
            if ($$7.equals(f_227541_) && !($$14 = p_227568_.m_8055_(($$13 = this.f_73658_.m_121955_(StructureTemplate.m_74563_($$8, new BlockPos(3, 0, 5)))).m_7495_())).m_60795_() && !$$14.m_60713_(Blocks.f_50155_)) {
                p_227568_.m_7731_($$13, Blocks.f_50127_.m_49966_(), 3);
            }
            this.f_73658_ = $$12;
        }
    }
}

