/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen.structure.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class BuriedTreasurePieces {

    public static class BuriedTreasurePiece
    extends StructurePiece {
        public BuriedTreasurePiece(BlockPos p_227366_) {
            super(StructurePieceType.f_210122_, 0, new BoundingBox(p_227366_));
        }

        public BuriedTreasurePiece(CompoundTag p_227368_) {
            super(StructurePieceType.f_210122_, p_227368_);
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_227378_, CompoundTag p_227379_) {
        }

        @Override
        public void m_213694_(WorldGenLevel p_227370_, StructureManager p_227371_, ChunkGenerator p_227372_, RandomSource p_227373_, BoundingBox p_227374_, ChunkPos p_227375_, BlockPos p_227376_) {
            int $$7 = p_227370_.m_6924_(Heightmap.Types.OCEAN_FLOOR_WG, this.f_73383_.m_162395_(), this.f_73383_.m_162398_());
            BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos(this.f_73383_.m_162395_(), $$7, this.f_73383_.m_162398_());
            while ($$8.m_123342_() > p_227370_.m_141937_()) {
                BlockState $$9 = p_227370_.m_8055_($$8);
                BlockState $$10 = p_227370_.m_8055_((BlockPos)$$8.m_7495_());
                if ($$10 == Blocks.f_50062_.m_49966_() || $$10 == Blocks.f_50069_.m_49966_() || $$10 == Blocks.f_50334_.m_49966_() || $$10 == Blocks.f_50122_.m_49966_() || $$10 == Blocks.f_50228_.m_49966_()) {
                    BlockState $$11 = $$9.m_60795_() || this.m_227380_($$9) ? Blocks.f_49992_.m_49966_() : $$9;
                    for (Direction $$12 : Direction.values()) {
                        Vec3i $$13 = $$8.m_121945_($$12);
                        BlockState $$14 = p_227370_.m_8055_((BlockPos)$$13);
                        if (!$$14.m_60795_() && !this.m_227380_($$14)) continue;
                        BlockPos $$15 = ((BlockPos)$$13).m_7495_();
                        BlockState $$16 = p_227370_.m_8055_($$15);
                        if (($$16.m_60795_() || this.m_227380_($$16)) && $$12 != Direction.UP) {
                            p_227370_.m_7731_((BlockPos)$$13, $$10, 3);
                            continue;
                        }
                        p_227370_.m_7731_((BlockPos)$$13, $$11, 3);
                    }
                    this.f_73383_ = new BoundingBox($$8);
                    this.m_226762_(p_227370_, p_227374_, p_227373_, $$8, BuiltInLootTables.f_78692_, null);
                    return;
                }
                $$8.m_122184_(0, -1, 0);
            }
        }

        private boolean m_227380_(BlockState p_227381_) {
            return p_227381_ == Blocks.f_49990_.m_49966_() || p_227381_ == Blocks.f_49991_.m_49966_();
        }
    }
}

