/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.structure.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class DesertPyramidPiece
extends ScatteredFeaturePiece {
    public static final int f_227395_ = 21;
    public static final int f_227396_ = 21;
    private final boolean[] f_227397_ = new boolean[4];

    public DesertPyramidPiece(RandomSource p_227399_, int p_227400_, int p_227401_) {
        super(StructurePieceType.f_210106_, p_227400_, 64, p_227401_, 21, 15, 21, DesertPyramidPiece.m_226760_(p_227399_));
    }

    public DesertPyramidPiece(CompoundTag p_227403_) {
        super(StructurePieceType.f_210106_, p_227403_);
        this.f_227397_[0] = p_227403_.m_128471_("hasPlacedChest0");
        this.f_227397_[1] = p_227403_.m_128471_("hasPlacedChest1");
        this.f_227397_[2] = p_227403_.m_128471_("hasPlacedChest2");
        this.f_227397_[3] = p_227403_.m_128471_("hasPlacedChest3");
    }

    @Override
    protected void m_183620_(StructurePieceSerializationContext p_227413_, CompoundTag p_227414_) {
        super.m_183620_(p_227413_, p_227414_);
        p_227414_.m_128379_("hasPlacedChest0", this.f_227397_[0]);
        p_227414_.m_128379_("hasPlacedChest1", this.f_227397_[1]);
        p_227414_.m_128379_("hasPlacedChest2", this.f_227397_[2]);
        p_227414_.m_128379_("hasPlacedChest3", this.f_227397_[3]);
    }

    @Override
    public void m_213694_(WorldGenLevel p_227405_, StructureManager p_227406_, ChunkGenerator p_227407_, RandomSource p_227408_, BoundingBox p_227409_, ChunkPos p_227410_, BlockPos p_227411_) {
        if (!this.m_192467_(p_227405_, -p_227408_.m_188503_(3))) {
            return;
        }
        this.m_73441_(p_227405_, p_227409_, 0, -4, 0, this.f_72787_ - 1, 0, this.f_72789_ - 1, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
        for (int $$7 = 1; $$7 <= 9; ++$$7) {
            this.m_73441_(p_227405_, p_227409_, $$7, $$7, $$7, this.f_72787_ - 1 - $$7, $$7, this.f_72789_ - 1 - $$7, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
            this.m_73441_(p_227405_, p_227409_, $$7 + 1, $$7, $$7 + 1, this.f_72787_ - 2 - $$7, $$7, this.f_72789_ - 2 - $$7, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
        }
        for (int $$8 = 0; $$8 < this.f_72787_; ++$$8) {
            for (int $$9 = 0; $$9 < this.f_72789_; ++$$9) {
                int $$10 = -5;
                this.m_73528_(p_227405_, Blocks.f_50062_.m_49966_(), $$8, -5, $$9, p_227409_);
            }
        }
        BlockState $$11 = (BlockState)Blocks.f_50263_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.NORTH);
        BlockState $$12 = (BlockState)Blocks.f_50263_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.SOUTH);
        BlockState $$13 = (BlockState)Blocks.f_50263_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.EAST);
        BlockState $$14 = (BlockState)Blocks.f_50263_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.WEST);
        this.m_73441_(p_227405_, p_227409_, 0, 0, 0, 4, 9, 4, Blocks.f_50062_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 1, 10, 1, 3, 10, 3, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
        this.m_73434_(p_227405_, $$11, 2, 10, 0, p_227409_);
        this.m_73434_(p_227405_, $$12, 2, 10, 4, p_227409_);
        this.m_73434_(p_227405_, $$13, 0, 10, 2, p_227409_);
        this.m_73434_(p_227405_, $$14, 4, 10, 2, p_227409_);
        this.m_73441_(p_227405_, p_227409_, this.f_72787_ - 5, 0, 0, this.f_72787_ - 1, 9, 4, Blocks.f_50062_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, this.f_72787_ - 4, 10, 1, this.f_72787_ - 2, 10, 3, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
        this.m_73434_(p_227405_, $$11, this.f_72787_ - 3, 10, 0, p_227409_);
        this.m_73434_(p_227405_, $$12, this.f_72787_ - 3, 10, 4, p_227409_);
        this.m_73434_(p_227405_, $$13, this.f_72787_ - 5, 10, 2, p_227409_);
        this.m_73434_(p_227405_, $$14, this.f_72787_ - 1, 10, 2, p_227409_);
        this.m_73441_(p_227405_, p_227409_, 8, 0, 0, 12, 4, 4, Blocks.f_50062_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 9, 1, 0, 11, 3, 4, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
        this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), 9, 1, 1, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), 9, 2, 1, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), 9, 3, 1, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), 10, 3, 1, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), 11, 3, 1, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), 11, 2, 1, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), 11, 1, 1, p_227409_);
        this.m_73441_(p_227405_, p_227409_, 4, 1, 1, 8, 3, 3, Blocks.f_50062_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 4, 1, 2, 8, 2, 2, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 12, 1, 1, 16, 3, 3, Blocks.f_50062_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 12, 1, 2, 16, 2, 2, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 5, 4, 5, this.f_72787_ - 6, 4, this.f_72789_ - 6, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 9, 4, 9, 11, 4, 11, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 8, 1, 8, 8, 3, 8, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 12, 1, 8, 12, 3, 8, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 8, 1, 12, 8, 3, 12, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 12, 1, 12, 12, 3, 12, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 1, 1, 5, 4, 4, 11, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, this.f_72787_ - 5, 1, 5, this.f_72787_ - 2, 4, 11, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 6, 7, 9, 6, 7, 11, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, this.f_72787_ - 7, 7, 9, this.f_72787_ - 7, 7, 11, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 5, 5, 9, 5, 7, 11, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, this.f_72787_ - 6, 5, 9, this.f_72787_ - 6, 7, 11, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
        this.m_73434_(p_227405_, Blocks.f_50016_.m_49966_(), 5, 5, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50016_.m_49966_(), 5, 6, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50016_.m_49966_(), 6, 6, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50016_.m_49966_(), this.f_72787_ - 6, 5, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50016_.m_49966_(), this.f_72787_ - 6, 6, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50016_.m_49966_(), this.f_72787_ - 7, 6, 10, p_227409_);
        this.m_73441_(p_227405_, p_227409_, 2, 4, 4, 2, 6, 4, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, this.f_72787_ - 3, 4, 4, this.f_72787_ - 3, 6, 4, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
        this.m_73434_(p_227405_, $$11, 2, 4, 5, p_227409_);
        this.m_73434_(p_227405_, $$11, 2, 3, 4, p_227409_);
        this.m_73434_(p_227405_, $$11, this.f_72787_ - 3, 4, 5, p_227409_);
        this.m_73434_(p_227405_, $$11, this.f_72787_ - 3, 3, 4, p_227409_);
        this.m_73441_(p_227405_, p_227409_, 1, 1, 3, 2, 2, 3, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, this.f_72787_ - 3, 1, 3, this.f_72787_ - 2, 2, 3, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
        this.m_73434_(p_227405_, Blocks.f_50062_.m_49966_(), 1, 1, 2, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50062_.m_49966_(), this.f_72787_ - 2, 1, 2, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50406_.m_49966_(), 1, 2, 2, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50406_.m_49966_(), this.f_72787_ - 2, 2, 2, p_227409_);
        this.m_73434_(p_227405_, $$14, 2, 1, 2, p_227409_);
        this.m_73434_(p_227405_, $$13, this.f_72787_ - 3, 1, 2, p_227409_);
        this.m_73441_(p_227405_, p_227409_, 4, 3, 5, 4, 3, 17, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, this.f_72787_ - 5, 3, 5, this.f_72787_ - 5, 3, 17, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 3, 1, 5, 4, 2, 16, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, this.f_72787_ - 6, 1, 5, this.f_72787_ - 5, 2, 16, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
        for (int $$15 = 5; $$15 <= 17; $$15 += 2) {
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), 4, 1, $$15, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50063_.m_49966_(), 4, 2, $$15, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), this.f_72787_ - 5, 1, $$15, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50063_.m_49966_(), this.f_72787_ - 5, 2, $$15, p_227409_);
        }
        this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), 10, 0, 7, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), 10, 0, 8, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), 9, 0, 9, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), 11, 0, 9, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), 8, 0, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), 12, 0, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), 7, 0, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), 13, 0, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), 9, 0, 11, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), 11, 0, 11, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), 10, 0, 12, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), 10, 0, 13, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50298_.m_49966_(), 10, 0, 10, p_227409_);
        for (int $$16 = 0; $$16 <= this.f_72787_ - 1; $$16 += this.f_72787_ - 1) {
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$16, 2, 1, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$16, 2, 2, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$16, 2, 3, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$16, 3, 1, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$16, 3, 2, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$16, 3, 3, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$16, 4, 1, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50063_.m_49966_(), $$16, 4, 2, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$16, 4, 3, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$16, 5, 1, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$16, 5, 2, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$16, 5, 3, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$16, 6, 1, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50063_.m_49966_(), $$16, 6, 2, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$16, 6, 3, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$16, 7, 1, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$16, 7, 2, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$16, 7, 3, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$16, 8, 1, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$16, 8, 2, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$16, 8, 3, p_227409_);
        }
        for (int $$17 = 2; $$17 <= this.f_72787_ - 3; $$17 += this.f_72787_ - 3 - 2) {
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$17 - 1, 2, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$17, 2, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$17 + 1, 2, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$17 - 1, 3, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$17, 3, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$17 + 1, 3, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$17 - 1, 4, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50063_.m_49966_(), $$17, 4, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$17 + 1, 4, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$17 - 1, 5, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$17, 5, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$17 + 1, 5, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$17 - 1, 6, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50063_.m_49966_(), $$17, 6, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$17 + 1, 6, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$17 - 1, 7, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$17, 7, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), $$17 + 1, 7, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$17 - 1, 8, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$17, 8, 0, p_227409_);
            this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), $$17 + 1, 8, 0, p_227409_);
        }
        this.m_73441_(p_227405_, p_227409_, 8, 4, 0, 12, 6, 0, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
        this.m_73434_(p_227405_, Blocks.f_50016_.m_49966_(), 8, 6, 0, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50016_.m_49966_(), 12, 6, 0, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), 9, 5, 0, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50063_.m_49966_(), 10, 5, 0, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50288_.m_49966_(), 11, 5, 0, p_227409_);
        this.m_73441_(p_227405_, p_227409_, 8, -14, 8, 12, -11, 12, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 8, -10, 8, 12, -10, 12, Blocks.f_50063_.m_49966_(), Blocks.f_50063_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 8, -9, 8, 12, -9, 12, Blocks.f_50064_.m_49966_(), Blocks.f_50064_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 8, -8, 8, 12, -1, 12, Blocks.f_50062_.m_49966_(), Blocks.f_50062_.m_49966_(), false);
        this.m_73441_(p_227405_, p_227409_, 9, -11, 9, 11, -1, 11, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
        this.m_73434_(p_227405_, Blocks.f_50165_.m_49966_(), 10, -11, 10, p_227409_);
        this.m_73441_(p_227405_, p_227409_, 9, -13, 9, 11, -13, 11, Blocks.f_50077_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
        this.m_73434_(p_227405_, Blocks.f_50016_.m_49966_(), 8, -11, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50016_.m_49966_(), 8, -10, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50063_.m_49966_(), 7, -10, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), 7, -11, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50016_.m_49966_(), 12, -11, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50016_.m_49966_(), 12, -10, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50063_.m_49966_(), 13, -10, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), 13, -11, 10, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50016_.m_49966_(), 10, -11, 8, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50016_.m_49966_(), 10, -10, 8, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50063_.m_49966_(), 10, -10, 7, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), 10, -11, 7, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50016_.m_49966_(), 10, -11, 12, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50016_.m_49966_(), 10, -10, 12, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50063_.m_49966_(), 10, -10, 13, p_227409_);
        this.m_73434_(p_227405_, Blocks.f_50064_.m_49966_(), 10, -11, 13, p_227409_);
        for (Direction $$18 : Direction.Plane.HORIZONTAL) {
            if (this.f_227397_[$$18.m_122416_()]) continue;
            int $$19 = $$18.m_122429_() * 2;
            int $$20 = $$18.m_122431_() * 2;
            this.f_227397_[$$18.m_122416_()] = this.m_213787_(p_227405_, p_227409_, p_227408_, 10 + $$19, -11, 10 + $$20, BuiltInLootTables.f_78764_);
        }
    }
}

