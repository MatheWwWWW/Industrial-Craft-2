/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.structure.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public class SwampHutPiece
extends ScatteredFeaturePiece {
    private boolean f_229949_;
    private boolean f_229950_;

    public SwampHutPiece(RandomSource p_229952_, int p_229953_, int p_229954_) {
        super(StructurePieceType.f_210105_, p_229953_, 64, p_229954_, 7, 7, 9, SwampHutPiece.m_226760_(p_229952_));
    }

    public SwampHutPiece(CompoundTag p_229956_) {
        super(StructurePieceType.f_210105_, p_229956_);
        this.f_229949_ = p_229956_.m_128471_("Witch");
        this.f_229950_ = p_229956_.m_128471_("Cat");
    }

    @Override
    protected void m_183620_(StructurePieceSerializationContext p_229969_, CompoundTag p_229970_) {
        super.m_183620_(p_229969_, p_229970_);
        p_229970_.m_128379_("Witch", this.f_229949_);
        p_229970_.m_128379_("Cat", this.f_229950_);
    }

    @Override
    public void m_213694_(WorldGenLevel p_229961_, StructureManager p_229962_, ChunkGenerator p_229963_, RandomSource p_229964_, BoundingBox p_229965_, ChunkPos p_229966_, BlockPos p_229967_) {
        BlockPos.MutableBlockPos $$13;
        if (!this.m_72803_(p_229961_, p_229965_, 0)) {
            return;
        }
        this.m_73441_(p_229961_, p_229965_, 1, 1, 1, 5, 1, 7, Blocks.f_50741_.m_49966_(), Blocks.f_50741_.m_49966_(), false);
        this.m_73441_(p_229961_, p_229965_, 1, 4, 2, 5, 4, 7, Blocks.f_50741_.m_49966_(), Blocks.f_50741_.m_49966_(), false);
        this.m_73441_(p_229961_, p_229965_, 2, 1, 0, 4, 1, 0, Blocks.f_50741_.m_49966_(), Blocks.f_50741_.m_49966_(), false);
        this.m_73441_(p_229961_, p_229965_, 2, 2, 2, 3, 3, 2, Blocks.f_50741_.m_49966_(), Blocks.f_50741_.m_49966_(), false);
        this.m_73441_(p_229961_, p_229965_, 1, 2, 3, 1, 3, 6, Blocks.f_50741_.m_49966_(), Blocks.f_50741_.m_49966_(), false);
        this.m_73441_(p_229961_, p_229965_, 5, 2, 3, 5, 3, 6, Blocks.f_50741_.m_49966_(), Blocks.f_50741_.m_49966_(), false);
        this.m_73441_(p_229961_, p_229965_, 2, 2, 7, 4, 3, 7, Blocks.f_50741_.m_49966_(), Blocks.f_50741_.m_49966_(), false);
        this.m_73441_(p_229961_, p_229965_, 1, 0, 2, 1, 3, 2, Blocks.f_49999_.m_49966_(), Blocks.f_49999_.m_49966_(), false);
        this.m_73441_(p_229961_, p_229965_, 5, 0, 2, 5, 3, 2, Blocks.f_49999_.m_49966_(), Blocks.f_49999_.m_49966_(), false);
        this.m_73441_(p_229961_, p_229965_, 1, 0, 7, 1, 3, 7, Blocks.f_49999_.m_49966_(), Blocks.f_49999_.m_49966_(), false);
        this.m_73441_(p_229961_, p_229965_, 5, 0, 7, 5, 3, 7, Blocks.f_49999_.m_49966_(), Blocks.f_49999_.m_49966_(), false);
        this.m_73434_(p_229961_, Blocks.f_50132_.m_49966_(), 2, 3, 2, p_229965_);
        this.m_73434_(p_229961_, Blocks.f_50132_.m_49966_(), 3, 3, 7, p_229965_);
        this.m_73434_(p_229961_, Blocks.f_50016_.m_49966_(), 1, 3, 4, p_229965_);
        this.m_73434_(p_229961_, Blocks.f_50016_.m_49966_(), 5, 3, 4, p_229965_);
        this.m_73434_(p_229961_, Blocks.f_50016_.m_49966_(), 5, 3, 5, p_229965_);
        this.m_73434_(p_229961_, Blocks.f_50245_.m_49966_(), 1, 3, 5, p_229965_);
        this.m_73434_(p_229961_, Blocks.f_50091_.m_49966_(), 3, 2, 6, p_229965_);
        this.m_73434_(p_229961_, Blocks.f_50256_.m_49966_(), 4, 2, 6, p_229965_);
        this.m_73434_(p_229961_, Blocks.f_50132_.m_49966_(), 1, 2, 1, p_229965_);
        this.m_73434_(p_229961_, Blocks.f_50132_.m_49966_(), 5, 2, 1, p_229965_);
        BlockState $$7 = (BlockState)Blocks.f_50269_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.NORTH);
        BlockState $$8 = (BlockState)Blocks.f_50269_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.EAST);
        BlockState $$9 = (BlockState)Blocks.f_50269_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.WEST);
        BlockState $$10 = (BlockState)Blocks.f_50269_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.SOUTH);
        this.m_73441_(p_229961_, p_229965_, 0, 4, 1, 6, 4, 1, $$7, $$7, false);
        this.m_73441_(p_229961_, p_229965_, 0, 4, 2, 0, 4, 7, $$8, $$8, false);
        this.m_73441_(p_229961_, p_229965_, 6, 4, 2, 6, 4, 7, $$9, $$9, false);
        this.m_73441_(p_229961_, p_229965_, 0, 4, 8, 6, 4, 8, $$10, $$10, false);
        this.m_73434_(p_229961_, (BlockState)$$7.m_61124_(StairBlock.f_56843_, StairsShape.OUTER_RIGHT), 0, 4, 1, p_229965_);
        this.m_73434_(p_229961_, (BlockState)$$7.m_61124_(StairBlock.f_56843_, StairsShape.OUTER_LEFT), 6, 4, 1, p_229965_);
        this.m_73434_(p_229961_, (BlockState)$$10.m_61124_(StairBlock.f_56843_, StairsShape.OUTER_LEFT), 0, 4, 8, p_229965_);
        this.m_73434_(p_229961_, (BlockState)$$10.m_61124_(StairBlock.f_56843_, StairsShape.OUTER_RIGHT), 6, 4, 8, p_229965_);
        for (int $$11 = 2; $$11 <= 7; $$11 += 5) {
            for (int $$12 = 1; $$12 <= 5; $$12 += 4) {
                this.m_73528_(p_229961_, Blocks.f_49999_.m_49966_(), $$12, -1, $$11, p_229965_);
            }
        }
        if (!this.f_229949_ && p_229965_.m_71051_($$13 = this.m_163582_(2, 2, 5))) {
            this.f_229949_ = true;
            Witch $$14 = EntityType.f_20495_.m_20615_(p_229961_.m_6018_());
            $$14.m_21530_();
            $$14.m_7678_((double)$$13.m_123341_() + 0.5, $$13.m_123342_(), (double)$$13.m_123343_() + 0.5, 0.0f, 0.0f);
            $$14.m_6518_(p_229961_, p_229961_.m_6436_($$13), MobSpawnType.STRUCTURE, null, null);
            p_229961_.m_47205_($$14);
        }
        this.m_229957_(p_229961_, p_229965_);
    }

    private void m_229957_(ServerLevelAccessor p_229958_, BoundingBox p_229959_) {
        BlockPos.MutableBlockPos $$2;
        if (!this.f_229950_ && p_229959_.m_71051_($$2 = this.m_163582_(2, 2, 5))) {
            this.f_229950_ = true;
            Cat $$3 = EntityType.f_20553_.m_20615_(p_229958_.m_6018_());
            $$3.m_21530_();
            $$3.m_7678_((double)$$2.m_123341_() + 0.5, $$2.m_123342_(), (double)$$2.m_123343_() + 0.5, 0.0f, 0.0f);
            $$3.m_6518_(p_229958_, p_229958_.m_6436_($$2), MobSpawnType.STRUCTURE, null, null);
            p_229958_.m_47205_($$3);
        }
    }
}

