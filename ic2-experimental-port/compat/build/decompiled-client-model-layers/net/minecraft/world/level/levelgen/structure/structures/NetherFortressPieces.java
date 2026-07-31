/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class NetherFortressPieces {
    private static final int f_228001_ = 30;
    private static final int f_228002_ = 10;
    public static final int f_228000_ = 64;
    static final PieceWeight[] f_228003_ = new PieceWeight[]{new PieceWeight(BridgeStraight.class, 30, 0, true), new PieceWeight(BridgeCrossing.class, 10, 4), new PieceWeight(RoomCrossing.class, 10, 4), new PieceWeight(StairsRoom.class, 10, 3), new PieceWeight(MonsterThrone.class, 5, 2), new PieceWeight(CastleEntrance.class, 5, 1)};
    static final PieceWeight[] f_228004_ = new PieceWeight[]{new PieceWeight(CastleSmallCorridorPiece.class, 25, 0, true), new PieceWeight(CastleSmallCorridorCrossingPiece.class, 15, 5), new PieceWeight(CastleSmallCorridorRightTurnPiece.class, 5, 10), new PieceWeight(CastleSmallCorridorLeftTurnPiece.class, 5, 10), new PieceWeight(CastleCorridorStairsPiece.class, 10, 3, true), new PieceWeight(CastleCorridorTBalconyPiece.class, 7, 2), new PieceWeight(CastleStalkRoom.class, 5, 2)};

    static NetherBridgePiece m_228007_(PieceWeight p_228008_, StructurePieceAccessor p_228009_, RandomSource p_228010_, int p_228011_, int p_228012_, int p_228013_, Direction p_228014_, int p_228015_) {
        Class<? extends NetherBridgePiece> $$8 = p_228008_.f_228434_;
        NetherBridgePiece $$9 = null;
        if ($$8 == BridgeStraight.class) {
            $$9 = BridgeStraight.m_228105_(p_228009_, p_228010_, p_228011_, p_228012_, p_228013_, p_228014_, p_228015_);
        } else if ($$8 == BridgeCrossing.class) {
            $$9 = BridgeCrossing.m_228046_(p_228009_, p_228011_, p_228012_, p_228013_, p_228014_, p_228015_);
        } else if ($$8 == RoomCrossing.class) {
            $$9 = RoomCrossing.m_228472_(p_228009_, p_228011_, p_228012_, p_228013_, p_228014_, p_228015_);
        } else if ($$8 == StairsRoom.class) {
            $$9 = StairsRoom.m_228500_(p_228009_, p_228011_, p_228012_, p_228013_, p_228015_, p_228014_);
        } else if ($$8 == MonsterThrone.class) {
            $$9 = MonsterThrone.m_228369_(p_228009_, p_228011_, p_228012_, p_228013_, p_228015_, p_228014_);
        } else if ($$8 == CastleEntrance.class) {
            $$9 = CastleEntrance.m_228191_(p_228009_, p_228010_, p_228011_, p_228012_, p_228013_, p_228014_, p_228015_);
        } else if ($$8 == CastleSmallCorridorPiece.class) {
            $$9 = CastleSmallCorridorPiece.m_228282_(p_228009_, p_228011_, p_228012_, p_228013_, p_228014_, p_228015_);
        } else if ($$8 == CastleSmallCorridorRightTurnPiece.class) {
            $$9 = CastleSmallCorridorRightTurnPiece.m_228312_(p_228009_, p_228010_, p_228011_, p_228012_, p_228013_, p_228014_, p_228015_);
        } else if ($$8 == CastleSmallCorridorLeftTurnPiece.class) {
            $$9 = CastleSmallCorridorLeftTurnPiece.m_228250_(p_228009_, p_228010_, p_228011_, p_228012_, p_228013_, p_228014_, p_228015_);
        } else if ($$8 == CastleCorridorStairsPiece.class) {
            $$9 = CastleCorridorStairsPiece.m_228134_(p_228009_, p_228011_, p_228012_, p_228013_, p_228014_, p_228015_);
        } else if ($$8 == CastleCorridorTBalconyPiece.class) {
            $$9 = CastleCorridorTBalconyPiece.m_228162_(p_228009_, p_228011_, p_228012_, p_228013_, p_228014_, p_228015_);
        } else if ($$8 == CastleSmallCorridorCrossingPiece.class) {
            $$9 = CastleSmallCorridorCrossingPiece.m_228220_(p_228009_, p_228011_, p_228012_, p_228013_, p_228014_, p_228015_);
        } else if ($$8 == CastleStalkRoom.class) {
            $$9 = CastleStalkRoom.m_228344_(p_228009_, p_228011_, p_228012_, p_228013_, p_228014_, p_228015_);
        }
        return $$9;
    }

    static class PieceWeight {
        public final Class<? extends NetherBridgePiece> f_228434_;
        public final int f_228435_;
        public int f_228436_;
        public final int f_228437_;
        public final boolean f_228438_;

        public PieceWeight(Class<? extends NetherBridgePiece> p_228444_, int p_228445_, int p_228446_, boolean p_228447_) {
            this.f_228434_ = p_228444_;
            this.f_228435_ = p_228445_;
            this.f_228437_ = p_228446_;
            this.f_228438_ = p_228447_;
        }

        public PieceWeight(Class<? extends NetherBridgePiece> p_228440_, int p_228441_, int p_228442_) {
            this(p_228440_, p_228441_, p_228442_, false);
        }

        public boolean m_228449_(int p_228450_) {
            return this.f_228437_ == 0 || this.f_228436_ < this.f_228437_;
        }

        public boolean m_228448_() {
            return this.f_228437_ == 0 || this.f_228436_ < this.f_228437_;
        }
    }

    public static class BridgeStraight
    extends NetherBridgePiece {
        private static final int f_228083_ = 5;
        private static final int f_228084_ = 10;
        private static final int f_228085_ = 19;

        public BridgeStraight(int p_228087_, RandomSource p_228088_, BoundingBox p_228089_, Direction p_228090_) {
            super(StructurePieceType.f_210131_, p_228087_, p_228089_);
            this.m_73519_(p_228090_);
        }

        public BridgeStraight(CompoundTag p_228092_) {
            super(StructurePieceType.f_210131_, p_228092_);
        }

        @Override
        public void m_214092_(StructurePiece p_228102_, StructurePieceAccessor p_228103_, RandomSource p_228104_) {
            this.m_228401_((StartPiece)p_228102_, p_228103_, p_228104_, 1, 3, false);
        }

        public static BridgeStraight m_228105_(StructurePieceAccessor p_228106_, RandomSource p_228107_, int p_228108_, int p_228109_, int p_228110_, Direction p_228111_, int p_228112_) {
            BoundingBox $$7 = BoundingBox.m_71031_(p_228108_, p_228109_, p_228110_, -1, -3, 0, 5, 10, 19, p_228111_);
            if (!BridgeStraight.m_228386_($$7) || p_228106_.m_141921_($$7) != null) {
                return null;
            }
            return new BridgeStraight(p_228112_, p_228107_, $$7, p_228111_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228094_, StructureManager p_228095_, ChunkGenerator p_228096_, RandomSource p_228097_, BoundingBox p_228098_, ChunkPos p_228099_, BlockPos p_228100_) {
            this.m_73441_(p_228094_, p_228098_, 0, 3, 0, 4, 4, 18, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228094_, p_228098_, 1, 5, 0, 3, 7, 18, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            this.m_73441_(p_228094_, p_228098_, 0, 5, 0, 0, 5, 18, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228094_, p_228098_, 4, 5, 0, 4, 5, 18, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228094_, p_228098_, 0, 2, 0, 4, 2, 5, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228094_, p_228098_, 0, 2, 13, 4, 2, 18, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228094_, p_228098_, 0, 0, 0, 4, 1, 3, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228094_, p_228098_, 0, 0, 15, 4, 1, 18, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            for (int $$7 = 0; $$7 <= 4; ++$$7) {
                for (int $$8 = 0; $$8 <= 2; ++$$8) {
                    this.m_73528_(p_228094_, Blocks.f_50197_.m_49966_(), $$7, -1, $$8, p_228098_);
                    this.m_73528_(p_228094_, Blocks.f_50197_.m_49966_(), $$7, -1, 18 - $$8, p_228098_);
                }
            }
            BlockState $$9 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52311_, true);
            BlockState $$10 = (BlockState)$$9.m_61124_(FenceBlock.f_52310_, true);
            BlockState $$11 = (BlockState)$$9.m_61124_(FenceBlock.f_52312_, true);
            this.m_73441_(p_228094_, p_228098_, 0, 1, 1, 0, 4, 1, $$10, $$10, false);
            this.m_73441_(p_228094_, p_228098_, 0, 3, 4, 0, 4, 4, $$10, $$10, false);
            this.m_73441_(p_228094_, p_228098_, 0, 3, 14, 0, 4, 14, $$10, $$10, false);
            this.m_73441_(p_228094_, p_228098_, 0, 1, 17, 0, 4, 17, $$10, $$10, false);
            this.m_73441_(p_228094_, p_228098_, 4, 1, 1, 4, 4, 1, $$11, $$11, false);
            this.m_73441_(p_228094_, p_228098_, 4, 3, 4, 4, 4, 4, $$11, $$11, false);
            this.m_73441_(p_228094_, p_228098_, 4, 3, 14, 4, 4, 14, $$11, $$11, false);
            this.m_73441_(p_228094_, p_228098_, 4, 1, 17, 4, 4, 17, $$11, $$11, false);
        }
    }

    public static class BridgeCrossing
    extends NetherBridgePiece {
        private static final int f_228018_ = 19;
        private static final int f_228019_ = 10;
        private static final int f_228020_ = 19;

        public BridgeCrossing(int p_228026_, BoundingBox p_228027_, Direction p_228028_) {
            super(StructurePieceType.f_210129_, p_228026_, p_228027_);
            this.m_73519_(p_228028_);
        }

        protected BridgeCrossing(int p_228022_, int p_228023_, Direction p_228024_) {
            super(StructurePieceType.f_210129_, 0, StructurePiece.m_163541_(p_228022_, 64, p_228023_, p_228024_, 19, 10, 19));
            this.m_73519_(p_228024_);
        }

        protected BridgeCrossing(StructurePieceType p_228030_, CompoundTag p_228031_) {
            super(p_228030_, p_228031_);
        }

        public BridgeCrossing(CompoundTag p_228033_) {
            this(StructurePieceType.f_210129_, p_228033_);
        }

        @Override
        public void m_214092_(StructurePiece p_228043_, StructurePieceAccessor p_228044_, RandomSource p_228045_) {
            this.m_228401_((StartPiece)p_228043_, p_228044_, p_228045_, 8, 3, false);
            this.m_228420_((StartPiece)p_228043_, p_228044_, p_228045_, 3, 8, false);
            this.m_228427_((StartPiece)p_228043_, p_228044_, p_228045_, 3, 8, false);
        }

        public static BridgeCrossing m_228046_(StructurePieceAccessor p_228047_, int p_228048_, int p_228049_, int p_228050_, Direction p_228051_, int p_228052_) {
            BoundingBox $$6 = BoundingBox.m_71031_(p_228048_, p_228049_, p_228050_, -8, -3, 0, 19, 10, 19, p_228051_);
            if (!BridgeCrossing.m_228386_($$6) || p_228047_.m_141921_($$6) != null) {
                return null;
            }
            return new BridgeCrossing(p_228052_, $$6, p_228051_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228035_, StructureManager p_228036_, ChunkGenerator p_228037_, RandomSource p_228038_, BoundingBox p_228039_, ChunkPos p_228040_, BlockPos p_228041_) {
            this.m_73441_(p_228035_, p_228039_, 7, 3, 0, 11, 4, 18, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 0, 3, 7, 18, 4, 11, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 8, 5, 0, 10, 7, 18, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 0, 5, 8, 18, 7, 10, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 7, 5, 0, 7, 5, 7, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 7, 5, 11, 7, 5, 18, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 11, 5, 0, 11, 5, 7, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 11, 5, 11, 11, 5, 18, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 0, 5, 7, 7, 5, 7, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 11, 5, 7, 18, 5, 7, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 0, 5, 11, 7, 5, 11, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 11, 5, 11, 18, 5, 11, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 7, 2, 0, 11, 2, 5, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 7, 2, 13, 11, 2, 18, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 7, 0, 0, 11, 1, 3, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 7, 0, 15, 11, 1, 18, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            for (int $$7 = 7; $$7 <= 11; ++$$7) {
                for (int $$8 = 0; $$8 <= 2; ++$$8) {
                    this.m_73528_(p_228035_, Blocks.f_50197_.m_49966_(), $$7, -1, $$8, p_228039_);
                    this.m_73528_(p_228035_, Blocks.f_50197_.m_49966_(), $$7, -1, 18 - $$8, p_228039_);
                }
            }
            this.m_73441_(p_228035_, p_228039_, 0, 2, 7, 5, 2, 11, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 13, 2, 7, 18, 2, 11, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 0, 0, 7, 3, 1, 11, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228035_, p_228039_, 15, 0, 7, 18, 1, 11, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            for (int $$9 = 0; $$9 <= 2; ++$$9) {
                for (int $$10 = 7; $$10 <= 11; ++$$10) {
                    this.m_73528_(p_228035_, Blocks.f_50197_.m_49966_(), $$9, -1, $$10, p_228039_);
                    this.m_73528_(p_228035_, Blocks.f_50197_.m_49966_(), 18 - $$9, -1, $$10, p_228039_);
                }
            }
        }
    }

    public static class RoomCrossing
    extends NetherBridgePiece {
        private static final int f_228451_ = 7;
        private static final int f_228452_ = 9;
        private static final int f_228453_ = 7;

        public RoomCrossing(int p_228455_, BoundingBox p_228456_, Direction p_228457_) {
            super(StructurePieceType.f_210141_, p_228455_, p_228456_);
            this.m_73519_(p_228457_);
        }

        public RoomCrossing(CompoundTag p_228459_) {
            super(StructurePieceType.f_210141_, p_228459_);
        }

        @Override
        public void m_214092_(StructurePiece p_228469_, StructurePieceAccessor p_228470_, RandomSource p_228471_) {
            this.m_228401_((StartPiece)p_228469_, p_228470_, p_228471_, 2, 0, false);
            this.m_228420_((StartPiece)p_228469_, p_228470_, p_228471_, 0, 2, false);
            this.m_228427_((StartPiece)p_228469_, p_228470_, p_228471_, 0, 2, false);
        }

        public static RoomCrossing m_228472_(StructurePieceAccessor p_228473_, int p_228474_, int p_228475_, int p_228476_, Direction p_228477_, int p_228478_) {
            BoundingBox $$6 = BoundingBox.m_71031_(p_228474_, p_228475_, p_228476_, -2, 0, 0, 7, 9, 7, p_228477_);
            if (!RoomCrossing.m_228386_($$6) || p_228473_.m_141921_($$6) != null) {
                return null;
            }
            return new RoomCrossing(p_228478_, $$6, p_228477_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228461_, StructureManager p_228462_, ChunkGenerator p_228463_, RandomSource p_228464_, BoundingBox p_228465_, ChunkPos p_228466_, BlockPos p_228467_) {
            this.m_73441_(p_228461_, p_228465_, 0, 0, 0, 6, 1, 6, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228461_, p_228465_, 0, 2, 0, 6, 7, 6, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            this.m_73441_(p_228461_, p_228465_, 0, 2, 0, 1, 6, 0, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228461_, p_228465_, 0, 2, 6, 1, 6, 6, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228461_, p_228465_, 5, 2, 0, 6, 6, 0, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228461_, p_228465_, 5, 2, 6, 6, 6, 6, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228461_, p_228465_, 0, 2, 0, 0, 6, 1, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228461_, p_228465_, 0, 2, 5, 0, 6, 6, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228461_, p_228465_, 6, 2, 0, 6, 6, 1, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228461_, p_228465_, 6, 2, 5, 6, 6, 6, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            BlockState $$7 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52312_, true)).m_61124_(FenceBlock.f_52310_, true);
            BlockState $$8 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52311_, true);
            this.m_73441_(p_228461_, p_228465_, 2, 6, 0, 4, 6, 0, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228461_, p_228465_, 2, 5, 0, 4, 5, 0, $$7, $$7, false);
            this.m_73441_(p_228461_, p_228465_, 2, 6, 6, 4, 6, 6, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228461_, p_228465_, 2, 5, 6, 4, 5, 6, $$7, $$7, false);
            this.m_73441_(p_228461_, p_228465_, 0, 6, 2, 0, 6, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228461_, p_228465_, 0, 5, 2, 0, 5, 4, $$8, $$8, false);
            this.m_73441_(p_228461_, p_228465_, 6, 6, 2, 6, 6, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228461_, p_228465_, 6, 5, 2, 6, 5, 4, $$8, $$8, false);
            for (int $$9 = 0; $$9 <= 6; ++$$9) {
                for (int $$10 = 0; $$10 <= 6; ++$$10) {
                    this.m_73528_(p_228461_, Blocks.f_50197_.m_49966_(), $$9, -1, $$10, p_228465_);
                }
            }
        }
    }

    public static class StairsRoom
    extends NetherBridgePiece {
        private static final int f_228479_ = 7;
        private static final int f_228480_ = 11;
        private static final int f_228481_ = 7;

        public StairsRoom(int p_228483_, BoundingBox p_228484_, Direction p_228485_) {
            super(StructurePieceType.f_210142_, p_228483_, p_228484_);
            this.m_73519_(p_228485_);
        }

        public StairsRoom(CompoundTag p_228487_) {
            super(StructurePieceType.f_210142_, p_228487_);
        }

        @Override
        public void m_214092_(StructurePiece p_228497_, StructurePieceAccessor p_228498_, RandomSource p_228499_) {
            this.m_228427_((StartPiece)p_228497_, p_228498_, p_228499_, 6, 2, false);
        }

        public static StairsRoom m_228500_(StructurePieceAccessor p_228501_, int p_228502_, int p_228503_, int p_228504_, int p_228505_, Direction p_228506_) {
            BoundingBox $$6 = BoundingBox.m_71031_(p_228502_, p_228503_, p_228504_, -2, 0, 0, 7, 11, 7, p_228506_);
            if (!StairsRoom.m_228386_($$6) || p_228501_.m_141921_($$6) != null) {
                return null;
            }
            return new StairsRoom(p_228505_, $$6, p_228506_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228489_, StructureManager p_228490_, ChunkGenerator p_228491_, RandomSource p_228492_, BoundingBox p_228493_, ChunkPos p_228494_, BlockPos p_228495_) {
            this.m_73441_(p_228489_, p_228493_, 0, 0, 0, 6, 1, 6, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228489_, p_228493_, 0, 2, 0, 6, 10, 6, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            this.m_73441_(p_228489_, p_228493_, 0, 2, 0, 1, 8, 0, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228489_, p_228493_, 5, 2, 0, 6, 8, 0, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228489_, p_228493_, 0, 2, 1, 0, 8, 6, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228489_, p_228493_, 6, 2, 1, 6, 8, 6, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228489_, p_228493_, 1, 2, 6, 5, 8, 6, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            BlockState $$7 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52312_, true)).m_61124_(FenceBlock.f_52310_, true);
            BlockState $$8 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52311_, true);
            this.m_73441_(p_228489_, p_228493_, 0, 3, 2, 0, 5, 4, $$8, $$8, false);
            this.m_73441_(p_228489_, p_228493_, 6, 3, 2, 6, 5, 2, $$8, $$8, false);
            this.m_73441_(p_228489_, p_228493_, 6, 3, 4, 6, 5, 4, $$8, $$8, false);
            this.m_73434_(p_228489_, Blocks.f_50197_.m_49966_(), 5, 2, 5, p_228493_);
            this.m_73441_(p_228489_, p_228493_, 4, 2, 5, 4, 3, 5, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228489_, p_228493_, 3, 2, 5, 3, 4, 5, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228489_, p_228493_, 2, 2, 5, 2, 5, 5, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228489_, p_228493_, 1, 2, 5, 1, 6, 5, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228489_, p_228493_, 1, 7, 1, 5, 7, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228489_, p_228493_, 6, 8, 2, 6, 8, 4, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            this.m_73441_(p_228489_, p_228493_, 2, 6, 0, 4, 8, 0, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228489_, p_228493_, 2, 5, 0, 4, 5, 0, $$7, $$7, false);
            for (int $$9 = 0; $$9 <= 6; ++$$9) {
                for (int $$10 = 0; $$10 <= 6; ++$$10) {
                    this.m_73528_(p_228489_, Blocks.f_50197_.m_49966_(), $$9, -1, $$10, p_228493_);
                }
            }
        }
    }

    public static class MonsterThrone
    extends NetherBridgePiece {
        private static final int f_228351_ = 7;
        private static final int f_228352_ = 8;
        private static final int f_228353_ = 9;
        private boolean f_228354_;

        public MonsterThrone(int p_228356_, BoundingBox p_228357_, Direction p_228358_) {
            super(StructurePieceType.f_210140_, p_228356_, p_228357_);
            this.m_73519_(p_228358_);
        }

        public MonsterThrone(CompoundTag p_228360_) {
            super(StructurePieceType.f_210140_, p_228360_);
            this.f_228354_ = p_228360_.m_128471_("Mob");
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_228377_, CompoundTag p_228378_) {
            super.m_183620_(p_228377_, p_228378_);
            p_228378_.m_128379_("Mob", this.f_228354_);
        }

        public static MonsterThrone m_228369_(StructurePieceAccessor p_228370_, int p_228371_, int p_228372_, int p_228373_, int p_228374_, Direction p_228375_) {
            BoundingBox $$6 = BoundingBox.m_71031_(p_228371_, p_228372_, p_228373_, -2, 0, 0, 7, 8, 9, p_228375_);
            if (!MonsterThrone.m_228386_($$6) || p_228370_.m_141921_($$6) != null) {
                return null;
            }
            return new MonsterThrone(p_228374_, $$6, p_228375_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228362_, StructureManager p_228363_, ChunkGenerator p_228364_, RandomSource p_228365_, BoundingBox p_228366_, ChunkPos p_228367_, BlockPos p_228368_) {
            BlockPos.MutableBlockPos $$9;
            this.m_73441_(p_228362_, p_228366_, 0, 2, 0, 6, 7, 7, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            this.m_73441_(p_228362_, p_228366_, 1, 0, 0, 5, 1, 7, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228362_, p_228366_, 1, 2, 1, 5, 2, 7, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228362_, p_228366_, 1, 3, 2, 5, 3, 7, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228362_, p_228366_, 1, 4, 3, 5, 4, 7, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228362_, p_228366_, 1, 2, 0, 1, 4, 2, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228362_, p_228366_, 5, 2, 0, 5, 4, 2, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228362_, p_228366_, 1, 5, 2, 1, 5, 3, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228362_, p_228366_, 5, 5, 2, 5, 5, 3, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228362_, p_228366_, 0, 5, 3, 0, 5, 8, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228362_, p_228366_, 6, 5, 3, 6, 5, 8, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228362_, p_228366_, 1, 5, 8, 5, 5, 8, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            BlockState $$7 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52312_, true)).m_61124_(FenceBlock.f_52310_, true);
            BlockState $$8 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52311_, true);
            this.m_73434_(p_228362_, (BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52312_, true), 1, 6, 3, p_228366_);
            this.m_73434_(p_228362_, (BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52310_, true), 5, 6, 3, p_228366_);
            this.m_73434_(p_228362_, (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52310_, true)).m_61124_(FenceBlock.f_52309_, true), 0, 6, 3, p_228366_);
            this.m_73434_(p_228362_, (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52312_, true)).m_61124_(FenceBlock.f_52309_, true), 6, 6, 3, p_228366_);
            this.m_73441_(p_228362_, p_228366_, 0, 6, 4, 0, 6, 7, $$8, $$8, false);
            this.m_73441_(p_228362_, p_228366_, 6, 6, 4, 6, 6, 7, $$8, $$8, false);
            this.m_73434_(p_228362_, (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52310_, true)).m_61124_(FenceBlock.f_52311_, true), 0, 6, 8, p_228366_);
            this.m_73434_(p_228362_, (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52312_, true)).m_61124_(FenceBlock.f_52311_, true), 6, 6, 8, p_228366_);
            this.m_73441_(p_228362_, p_228366_, 1, 6, 8, 5, 6, 8, $$7, $$7, false);
            this.m_73434_(p_228362_, (BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52310_, true), 1, 7, 8, p_228366_);
            this.m_73441_(p_228362_, p_228366_, 2, 7, 8, 4, 7, 8, $$7, $$7, false);
            this.m_73434_(p_228362_, (BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52312_, true), 5, 7, 8, p_228366_);
            this.m_73434_(p_228362_, (BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52310_, true), 2, 8, 8, p_228366_);
            this.m_73434_(p_228362_, $$7, 3, 8, 8, p_228366_);
            this.m_73434_(p_228362_, (BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52312_, true), 4, 8, 8, p_228366_);
            if (!this.f_228354_ && p_228366_.m_71051_($$9 = this.m_163582_(3, 5, 5))) {
                this.f_228354_ = true;
                p_228362_.m_7731_($$9, Blocks.f_50085_.m_49966_(), 2);
                BlockEntity $$10 = p_228362_.m_7702_($$9);
                if ($$10 instanceof SpawnerBlockEntity) {
                    ((SpawnerBlockEntity)$$10).m_59801_().m_45462_(EntityType.f_20551_);
                }
            }
            for (int $$11 = 0; $$11 <= 6; ++$$11) {
                for (int $$12 = 0; $$12 <= 6; ++$$12) {
                    this.m_73528_(p_228362_, Blocks.f_50197_.m_49966_(), $$11, -1, $$12, p_228366_);
                }
            }
        }
    }

    public static class CastleEntrance
    extends NetherBridgePiece {
        private static final int f_228169_ = 13;
        private static final int f_228170_ = 14;
        private static final int f_228171_ = 13;

        public CastleEntrance(int p_228173_, RandomSource p_228174_, BoundingBox p_228175_, Direction p_228176_) {
            super(StructurePieceType.f_210134_, p_228173_, p_228175_);
            this.m_73519_(p_228176_);
        }

        public CastleEntrance(CompoundTag p_228178_) {
            super(StructurePieceType.f_210134_, p_228178_);
        }

        @Override
        public void m_214092_(StructurePiece p_228188_, StructurePieceAccessor p_228189_, RandomSource p_228190_) {
            this.m_228401_((StartPiece)p_228188_, p_228189_, p_228190_, 5, 3, true);
        }

        public static CastleEntrance m_228191_(StructurePieceAccessor p_228192_, RandomSource p_228193_, int p_228194_, int p_228195_, int p_228196_, Direction p_228197_, int p_228198_) {
            BoundingBox $$7 = BoundingBox.m_71031_(p_228194_, p_228195_, p_228196_, -5, -3, 0, 13, 14, 13, p_228197_);
            if (!CastleEntrance.m_228386_($$7) || p_228192_.m_141921_($$7) != null) {
                return null;
            }
            return new CastleEntrance(p_228198_, p_228193_, $$7, p_228197_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228180_, StructureManager p_228181_, ChunkGenerator p_228182_, RandomSource p_228183_, BoundingBox p_228184_, ChunkPos p_228185_, BlockPos p_228186_) {
            this.m_73441_(p_228180_, p_228184_, 0, 3, 0, 12, 4, 12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 0, 5, 0, 12, 13, 12, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 0, 5, 0, 1, 12, 12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 11, 5, 0, 12, 12, 12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 2, 5, 11, 4, 12, 12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 8, 5, 11, 10, 12, 12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 5, 9, 11, 7, 12, 12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 2, 5, 0, 4, 12, 1, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 8, 5, 0, 10, 12, 1, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 5, 9, 0, 7, 12, 1, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 2, 11, 2, 10, 12, 10, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 5, 8, 0, 7, 8, 0, Blocks.f_50198_.m_49966_(), Blocks.f_50198_.m_49966_(), false);
            BlockState $$7 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52312_, true)).m_61124_(FenceBlock.f_52310_, true);
            BlockState $$8 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52311_, true);
            for (int $$9 = 1; $$9 <= 11; $$9 += 2) {
                this.m_73441_(p_228180_, p_228184_, $$9, 10, 0, $$9, 11, 0, $$7, $$7, false);
                this.m_73441_(p_228180_, p_228184_, $$9, 10, 12, $$9, 11, 12, $$7, $$7, false);
                this.m_73441_(p_228180_, p_228184_, 0, 10, $$9, 0, 11, $$9, $$8, $$8, false);
                this.m_73441_(p_228180_, p_228184_, 12, 10, $$9, 12, 11, $$9, $$8, $$8, false);
                this.m_73434_(p_228180_, Blocks.f_50197_.m_49966_(), $$9, 13, 0, p_228184_);
                this.m_73434_(p_228180_, Blocks.f_50197_.m_49966_(), $$9, 13, 12, p_228184_);
                this.m_73434_(p_228180_, Blocks.f_50197_.m_49966_(), 0, 13, $$9, p_228184_);
                this.m_73434_(p_228180_, Blocks.f_50197_.m_49966_(), 12, 13, $$9, p_228184_);
                if ($$9 == 11) continue;
                this.m_73434_(p_228180_, $$7, $$9 + 1, 13, 0, p_228184_);
                this.m_73434_(p_228180_, $$7, $$9 + 1, 13, 12, p_228184_);
                this.m_73434_(p_228180_, $$8, 0, 13, $$9 + 1, p_228184_);
                this.m_73434_(p_228180_, $$8, 12, 13, $$9 + 1, p_228184_);
            }
            this.m_73434_(p_228180_, (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52310_, true), 0, 13, 0, p_228184_);
            this.m_73434_(p_228180_, (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52311_, true)).m_61124_(FenceBlock.f_52310_, true), 0, 13, 12, p_228184_);
            this.m_73434_(p_228180_, (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52311_, true)).m_61124_(FenceBlock.f_52312_, true), 12, 13, 12, p_228184_);
            this.m_73434_(p_228180_, (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52312_, true), 12, 13, 0, p_228184_);
            for (int $$10 = 3; $$10 <= 9; $$10 += 2) {
                this.m_73441_(p_228180_, p_228184_, 1, 7, $$10, 1, 8, $$10, (BlockState)$$8.m_61124_(FenceBlock.f_52312_, true), (BlockState)$$8.m_61124_(FenceBlock.f_52312_, true), false);
                this.m_73441_(p_228180_, p_228184_, 11, 7, $$10, 11, 8, $$10, (BlockState)$$8.m_61124_(FenceBlock.f_52310_, true), (BlockState)$$8.m_61124_(FenceBlock.f_52310_, true), false);
            }
            this.m_73441_(p_228180_, p_228184_, 4, 2, 0, 8, 2, 12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 0, 2, 4, 12, 2, 8, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 4, 0, 0, 8, 1, 3, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 4, 0, 9, 8, 1, 12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 0, 0, 4, 3, 1, 8, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 9, 0, 4, 12, 1, 8, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            for (int $$11 = 4; $$11 <= 8; ++$$11) {
                for (int $$12 = 0; $$12 <= 2; ++$$12) {
                    this.m_73528_(p_228180_, Blocks.f_50197_.m_49966_(), $$11, -1, $$12, p_228184_);
                    this.m_73528_(p_228180_, Blocks.f_50197_.m_49966_(), $$11, -1, 12 - $$12, p_228184_);
                }
            }
            for (int $$13 = 0; $$13 <= 2; ++$$13) {
                for (int $$14 = 4; $$14 <= 8; ++$$14) {
                    this.m_73528_(p_228180_, Blocks.f_50197_.m_49966_(), $$13, -1, $$14, p_228184_);
                    this.m_73528_(p_228180_, Blocks.f_50197_.m_49966_(), 12 - $$13, -1, $$14, p_228184_);
                }
            }
            this.m_73441_(p_228180_, p_228184_, 5, 5, 5, 7, 5, 7, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228180_, p_228184_, 6, 1, 6, 6, 4, 6, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            this.m_73434_(p_228180_, Blocks.f_50197_.m_49966_(), 6, 0, 6, p_228184_);
            this.m_73434_(p_228180_, Blocks.f_49991_.m_49966_(), 6, 5, 6, p_228184_);
            BlockPos.MutableBlockPos $$15 = this.m_163582_(6, 5, 6);
            if (p_228184_.m_71051_($$15)) {
                p_228180_.m_186469_($$15, Fluids.f_76195_, 0);
            }
        }
    }

    public static class CastleSmallCorridorPiece
    extends NetherBridgePiece {
        private static final int f_228261_ = 5;
        private static final int f_228262_ = 7;
        private static final int f_228263_ = 5;

        public CastleSmallCorridorPiece(int p_228265_, BoundingBox p_228266_, Direction p_228267_) {
            super(StructurePieceType.f_210137_, p_228265_, p_228266_);
            this.m_73519_(p_228267_);
        }

        public CastleSmallCorridorPiece(CompoundTag p_228269_) {
            super(StructurePieceType.f_210137_, p_228269_);
        }

        @Override
        public void m_214092_(StructurePiece p_228279_, StructurePieceAccessor p_228280_, RandomSource p_228281_) {
            this.m_228401_((StartPiece)p_228279_, p_228280_, p_228281_, 1, 0, true);
        }

        public static CastleSmallCorridorPiece m_228282_(StructurePieceAccessor p_228283_, int p_228284_, int p_228285_, int p_228286_, Direction p_228287_, int p_228288_) {
            BoundingBox $$6 = BoundingBox.m_71031_(p_228284_, p_228285_, p_228286_, -1, 0, 0, 5, 7, 5, p_228287_);
            if (!CastleSmallCorridorPiece.m_228386_($$6) || p_228283_.m_141921_($$6) != null) {
                return null;
            }
            return new CastleSmallCorridorPiece(p_228288_, $$6, p_228287_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228271_, StructureManager p_228272_, ChunkGenerator p_228273_, RandomSource p_228274_, BoundingBox p_228275_, ChunkPos p_228276_, BlockPos p_228277_) {
            this.m_73441_(p_228271_, p_228275_, 0, 0, 0, 4, 1, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228271_, p_228275_, 0, 2, 0, 4, 5, 4, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            BlockState $$7 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52311_, true);
            this.m_73441_(p_228271_, p_228275_, 0, 2, 0, 0, 5, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228271_, p_228275_, 4, 2, 0, 4, 5, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228271_, p_228275_, 0, 3, 1, 0, 4, 1, $$7, $$7, false);
            this.m_73441_(p_228271_, p_228275_, 0, 3, 3, 0, 4, 3, $$7, $$7, false);
            this.m_73441_(p_228271_, p_228275_, 4, 3, 1, 4, 4, 1, $$7, $$7, false);
            this.m_73441_(p_228271_, p_228275_, 4, 3, 3, 4, 4, 3, $$7, $$7, false);
            this.m_73441_(p_228271_, p_228275_, 0, 6, 0, 4, 6, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            for (int $$8 = 0; $$8 <= 4; ++$$8) {
                for (int $$9 = 0; $$9 <= 4; ++$$9) {
                    this.m_73528_(p_228271_, Blocks.f_50197_.m_49966_(), $$8, -1, $$9, p_228275_);
                }
            }
        }
    }

    public static class CastleSmallCorridorRightTurnPiece
    extends NetherBridgePiece {
        private static final int f_228289_ = 5;
        private static final int f_228290_ = 7;
        private static final int f_228291_ = 5;
        private boolean f_228292_;

        public CastleSmallCorridorRightTurnPiece(int p_228294_, RandomSource p_228295_, BoundingBox p_228296_, Direction p_228297_) {
            super(StructurePieceType.f_210138_, p_228294_, p_228296_);
            this.m_73519_(p_228297_);
            this.f_228292_ = p_228295_.m_188503_(3) == 0;
        }

        public CastleSmallCorridorRightTurnPiece(CompoundTag p_228299_) {
            super(StructurePieceType.f_210138_, p_228299_);
            this.f_228292_ = p_228299_.m_128471_("Chest");
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_228321_, CompoundTag p_228322_) {
            super.m_183620_(p_228321_, p_228322_);
            p_228322_.m_128379_("Chest", this.f_228292_);
        }

        @Override
        public void m_214092_(StructurePiece p_228309_, StructurePieceAccessor p_228310_, RandomSource p_228311_) {
            this.m_228427_((StartPiece)p_228309_, p_228310_, p_228311_, 0, 1, true);
        }

        public static CastleSmallCorridorRightTurnPiece m_228312_(StructurePieceAccessor p_228313_, RandomSource p_228314_, int p_228315_, int p_228316_, int p_228317_, Direction p_228318_, int p_228319_) {
            BoundingBox $$7 = BoundingBox.m_71031_(p_228315_, p_228316_, p_228317_, -1, 0, 0, 5, 7, 5, p_228318_);
            if (!CastleSmallCorridorRightTurnPiece.m_228386_($$7) || p_228313_.m_141921_($$7) != null) {
                return null;
            }
            return new CastleSmallCorridorRightTurnPiece(p_228319_, p_228314_, $$7, p_228318_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228301_, StructureManager p_228302_, ChunkGenerator p_228303_, RandomSource p_228304_, BoundingBox p_228305_, ChunkPos p_228306_, BlockPos p_228307_) {
            this.m_73441_(p_228301_, p_228305_, 0, 0, 0, 4, 1, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228301_, p_228305_, 0, 2, 0, 4, 5, 4, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            BlockState $$7 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52312_, true)).m_61124_(FenceBlock.f_52310_, true);
            BlockState $$8 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52311_, true);
            this.m_73441_(p_228301_, p_228305_, 0, 2, 0, 0, 5, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228301_, p_228305_, 0, 3, 1, 0, 4, 1, $$8, $$8, false);
            this.m_73441_(p_228301_, p_228305_, 0, 3, 3, 0, 4, 3, $$8, $$8, false);
            this.m_73441_(p_228301_, p_228305_, 4, 2, 0, 4, 5, 0, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228301_, p_228305_, 1, 2, 4, 4, 5, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228301_, p_228305_, 1, 3, 4, 1, 4, 4, $$7, $$7, false);
            this.m_73441_(p_228301_, p_228305_, 3, 3, 4, 3, 4, 4, $$7, $$7, false);
            if (this.f_228292_ && p_228305_.m_71051_(this.m_163582_(1, 2, 3))) {
                this.f_228292_ = false;
                this.m_213787_(p_228301_, p_228305_, p_228304_, 1, 2, 3, BuiltInLootTables.f_78760_);
            }
            this.m_73441_(p_228301_, p_228305_, 0, 6, 0, 4, 6, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            for (int $$9 = 0; $$9 <= 4; ++$$9) {
                for (int $$10 = 0; $$10 <= 4; ++$$10) {
                    this.m_73528_(p_228301_, Blocks.f_50197_.m_49966_(), $$9, -1, $$10, p_228305_);
                }
            }
        }
    }

    public static class CastleSmallCorridorLeftTurnPiece
    extends NetherBridgePiece {
        private static final int f_228227_ = 5;
        private static final int f_228228_ = 7;
        private static final int f_228229_ = 5;
        private boolean f_228230_;

        public CastleSmallCorridorLeftTurnPiece(int p_228232_, RandomSource p_228233_, BoundingBox p_228234_, Direction p_228235_) {
            super(StructurePieceType.f_210136_, p_228232_, p_228234_);
            this.m_73519_(p_228235_);
            this.f_228230_ = p_228233_.m_188503_(3) == 0;
        }

        public CastleSmallCorridorLeftTurnPiece(CompoundTag p_228237_) {
            super(StructurePieceType.f_210136_, p_228237_);
            this.f_228230_ = p_228237_.m_128471_("Chest");
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_228259_, CompoundTag p_228260_) {
            super.m_183620_(p_228259_, p_228260_);
            p_228260_.m_128379_("Chest", this.f_228230_);
        }

        @Override
        public void m_214092_(StructurePiece p_228247_, StructurePieceAccessor p_228248_, RandomSource p_228249_) {
            this.m_228420_((StartPiece)p_228247_, p_228248_, p_228249_, 0, 1, true);
        }

        public static CastleSmallCorridorLeftTurnPiece m_228250_(StructurePieceAccessor p_228251_, RandomSource p_228252_, int p_228253_, int p_228254_, int p_228255_, Direction p_228256_, int p_228257_) {
            BoundingBox $$7 = BoundingBox.m_71031_(p_228253_, p_228254_, p_228255_, -1, 0, 0, 5, 7, 5, p_228256_);
            if (!CastleSmallCorridorLeftTurnPiece.m_228386_($$7) || p_228251_.m_141921_($$7) != null) {
                return null;
            }
            return new CastleSmallCorridorLeftTurnPiece(p_228257_, p_228252_, $$7, p_228256_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228239_, StructureManager p_228240_, ChunkGenerator p_228241_, RandomSource p_228242_, BoundingBox p_228243_, ChunkPos p_228244_, BlockPos p_228245_) {
            this.m_73441_(p_228239_, p_228243_, 0, 0, 0, 4, 1, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228239_, p_228243_, 0, 2, 0, 4, 5, 4, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            BlockState $$7 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52312_, true)).m_61124_(FenceBlock.f_52310_, true);
            BlockState $$8 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52311_, true);
            this.m_73441_(p_228239_, p_228243_, 4, 2, 0, 4, 5, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228239_, p_228243_, 4, 3, 1, 4, 4, 1, $$8, $$8, false);
            this.m_73441_(p_228239_, p_228243_, 4, 3, 3, 4, 4, 3, $$8, $$8, false);
            this.m_73441_(p_228239_, p_228243_, 0, 2, 0, 0, 5, 0, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228239_, p_228243_, 0, 2, 4, 3, 5, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228239_, p_228243_, 1, 3, 4, 1, 4, 4, $$7, $$7, false);
            this.m_73441_(p_228239_, p_228243_, 3, 3, 4, 3, 4, 4, $$7, $$7, false);
            if (this.f_228230_ && p_228243_.m_71051_(this.m_163582_(3, 2, 3))) {
                this.f_228230_ = false;
                this.m_213787_(p_228239_, p_228243_, p_228242_, 3, 2, 3, BuiltInLootTables.f_78760_);
            }
            this.m_73441_(p_228239_, p_228243_, 0, 6, 0, 4, 6, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            for (int $$9 = 0; $$9 <= 4; ++$$9) {
                for (int $$10 = 0; $$10 <= 4; ++$$10) {
                    this.m_73528_(p_228239_, Blocks.f_50197_.m_49966_(), $$9, -1, $$10, p_228243_);
                }
            }
        }
    }

    public static class CastleCorridorStairsPiece
    extends NetherBridgePiece {
        private static final int f_228113_ = 5;
        private static final int f_228114_ = 14;
        private static final int f_228115_ = 10;

        public CastleCorridorStairsPiece(int p_228117_, BoundingBox p_228118_, Direction p_228119_) {
            super(StructurePieceType.f_210132_, p_228117_, p_228118_);
            this.m_73519_(p_228119_);
        }

        public CastleCorridorStairsPiece(CompoundTag p_228121_) {
            super(StructurePieceType.f_210132_, p_228121_);
        }

        @Override
        public void m_214092_(StructurePiece p_228131_, StructurePieceAccessor p_228132_, RandomSource p_228133_) {
            this.m_228401_((StartPiece)p_228131_, p_228132_, p_228133_, 1, 0, true);
        }

        public static CastleCorridorStairsPiece m_228134_(StructurePieceAccessor p_228135_, int p_228136_, int p_228137_, int p_228138_, Direction p_228139_, int p_228140_) {
            BoundingBox $$6 = BoundingBox.m_71031_(p_228136_, p_228137_, p_228138_, -1, -7, 0, 5, 14, 10, p_228139_);
            if (!CastleCorridorStairsPiece.m_228386_($$6) || p_228135_.m_141921_($$6) != null) {
                return null;
            }
            return new CastleCorridorStairsPiece(p_228140_, $$6, p_228139_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228123_, StructureManager p_228124_, ChunkGenerator p_228125_, RandomSource p_228126_, BoundingBox p_228127_, ChunkPos p_228128_, BlockPos p_228129_) {
            BlockState $$7 = (BlockState)Blocks.f_50199_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.SOUTH);
            BlockState $$8 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52311_, true);
            for (int $$9 = 0; $$9 <= 9; ++$$9) {
                int $$10 = Math.max(1, 7 - $$9);
                int $$11 = Math.min(Math.max($$10 + 5, 14 - $$9), 13);
                int $$12 = $$9;
                this.m_73441_(p_228123_, p_228127_, 0, 0, $$12, 4, $$10, $$12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
                this.m_73441_(p_228123_, p_228127_, 1, $$10 + 1, $$12, 3, $$11 - 1, $$12, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
                if ($$9 <= 6) {
                    this.m_73434_(p_228123_, $$7, 1, $$10 + 1, $$12, p_228127_);
                    this.m_73434_(p_228123_, $$7, 2, $$10 + 1, $$12, p_228127_);
                    this.m_73434_(p_228123_, $$7, 3, $$10 + 1, $$12, p_228127_);
                }
                this.m_73441_(p_228123_, p_228127_, 0, $$11, $$12, 4, $$11, $$12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
                this.m_73441_(p_228123_, p_228127_, 0, $$10 + 1, $$12, 0, $$11 - 1, $$12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
                this.m_73441_(p_228123_, p_228127_, 4, $$10 + 1, $$12, 4, $$11 - 1, $$12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
                if (($$9 & 1) == 0) {
                    this.m_73441_(p_228123_, p_228127_, 0, $$10 + 2, $$12, 0, $$10 + 3, $$12, $$8, $$8, false);
                    this.m_73441_(p_228123_, p_228127_, 4, $$10 + 2, $$12, 4, $$10 + 3, $$12, $$8, $$8, false);
                }
                for (int $$13 = 0; $$13 <= 4; ++$$13) {
                    this.m_73528_(p_228123_, Blocks.f_50197_.m_49966_(), $$13, -1, $$12, p_228127_);
                }
            }
        }
    }

    public static class CastleCorridorTBalconyPiece
    extends NetherBridgePiece {
        private static final int f_228141_ = 9;
        private static final int f_228142_ = 7;
        private static final int f_228143_ = 9;

        public CastleCorridorTBalconyPiece(int p_228145_, BoundingBox p_228146_, Direction p_228147_) {
            super(StructurePieceType.f_210133_, p_228145_, p_228146_);
            this.m_73519_(p_228147_);
        }

        public CastleCorridorTBalconyPiece(CompoundTag p_228149_) {
            super(StructurePieceType.f_210133_, p_228149_);
        }

        @Override
        public void m_214092_(StructurePiece p_228159_, StructurePieceAccessor p_228160_, RandomSource p_228161_) {
            int $$3 = 1;
            Direction $$4 = this.m_73549_();
            if ($$4 == Direction.WEST || $$4 == Direction.NORTH) {
                $$3 = 5;
            }
            this.m_228420_((StartPiece)p_228159_, p_228160_, p_228161_, 0, $$3, p_228161_.m_188503_(8) > 0);
            this.m_228427_((StartPiece)p_228159_, p_228160_, p_228161_, 0, $$3, p_228161_.m_188503_(8) > 0);
        }

        public static CastleCorridorTBalconyPiece m_228162_(StructurePieceAccessor p_228163_, int p_228164_, int p_228165_, int p_228166_, Direction p_228167_, int p_228168_) {
            BoundingBox $$6 = BoundingBox.m_71031_(p_228164_, p_228165_, p_228166_, -3, 0, 0, 9, 7, 9, p_228167_);
            if (!CastleCorridorTBalconyPiece.m_228386_($$6) || p_228163_.m_141921_($$6) != null) {
                return null;
            }
            return new CastleCorridorTBalconyPiece(p_228168_, $$6, p_228167_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228151_, StructureManager p_228152_, ChunkGenerator p_228153_, RandomSource p_228154_, BoundingBox p_228155_, ChunkPos p_228156_, BlockPos p_228157_) {
            BlockState $$7 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52311_, true);
            BlockState $$8 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52312_, true)).m_61124_(FenceBlock.f_52310_, true);
            this.m_73441_(p_228151_, p_228155_, 0, 0, 0, 8, 1, 8, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228151_, p_228155_, 0, 2, 0, 8, 5, 8, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            this.m_73441_(p_228151_, p_228155_, 0, 6, 0, 8, 6, 5, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228151_, p_228155_, 0, 2, 0, 2, 5, 0, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228151_, p_228155_, 6, 2, 0, 8, 5, 0, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228151_, p_228155_, 1, 3, 0, 1, 4, 0, $$8, $$8, false);
            this.m_73441_(p_228151_, p_228155_, 7, 3, 0, 7, 4, 0, $$8, $$8, false);
            this.m_73441_(p_228151_, p_228155_, 0, 2, 4, 8, 2, 8, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228151_, p_228155_, 1, 1, 4, 2, 2, 4, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            this.m_73441_(p_228151_, p_228155_, 6, 1, 4, 7, 2, 4, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            this.m_73441_(p_228151_, p_228155_, 1, 3, 8, 7, 3, 8, $$8, $$8, false);
            this.m_73434_(p_228151_, (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52310_, true)).m_61124_(FenceBlock.f_52311_, true), 0, 3, 8, p_228155_);
            this.m_73434_(p_228151_, (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52312_, true)).m_61124_(FenceBlock.f_52311_, true), 8, 3, 8, p_228155_);
            this.m_73441_(p_228151_, p_228155_, 0, 3, 6, 0, 3, 7, $$7, $$7, false);
            this.m_73441_(p_228151_, p_228155_, 8, 3, 6, 8, 3, 7, $$7, $$7, false);
            this.m_73441_(p_228151_, p_228155_, 0, 3, 4, 0, 5, 5, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228151_, p_228155_, 8, 3, 4, 8, 5, 5, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228151_, p_228155_, 1, 3, 5, 2, 5, 5, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228151_, p_228155_, 6, 3, 5, 7, 5, 5, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228151_, p_228155_, 1, 4, 5, 1, 5, 5, $$8, $$8, false);
            this.m_73441_(p_228151_, p_228155_, 7, 4, 5, 7, 5, 5, $$8, $$8, false);
            for (int $$9 = 0; $$9 <= 5; ++$$9) {
                for (int $$10 = 0; $$10 <= 8; ++$$10) {
                    this.m_73528_(p_228151_, Blocks.f_50197_.m_49966_(), $$10, -1, $$9, p_228155_);
                }
            }
        }
    }

    public static class CastleSmallCorridorCrossingPiece
    extends NetherBridgePiece {
        private static final int f_228199_ = 5;
        private static final int f_228200_ = 7;
        private static final int f_228201_ = 5;

        public CastleSmallCorridorCrossingPiece(int p_228203_, BoundingBox p_228204_, Direction p_228205_) {
            super(StructurePieceType.f_210135_, p_228203_, p_228204_);
            this.m_73519_(p_228205_);
        }

        public CastleSmallCorridorCrossingPiece(CompoundTag p_228207_) {
            super(StructurePieceType.f_210135_, p_228207_);
        }

        @Override
        public void m_214092_(StructurePiece p_228217_, StructurePieceAccessor p_228218_, RandomSource p_228219_) {
            this.m_228401_((StartPiece)p_228217_, p_228218_, p_228219_, 1, 0, true);
            this.m_228420_((StartPiece)p_228217_, p_228218_, p_228219_, 0, 1, true);
            this.m_228427_((StartPiece)p_228217_, p_228218_, p_228219_, 0, 1, true);
        }

        public static CastleSmallCorridorCrossingPiece m_228220_(StructurePieceAccessor p_228221_, int p_228222_, int p_228223_, int p_228224_, Direction p_228225_, int p_228226_) {
            BoundingBox $$6 = BoundingBox.m_71031_(p_228222_, p_228223_, p_228224_, -1, 0, 0, 5, 7, 5, p_228225_);
            if (!CastleSmallCorridorCrossingPiece.m_228386_($$6) || p_228221_.m_141921_($$6) != null) {
                return null;
            }
            return new CastleSmallCorridorCrossingPiece(p_228226_, $$6, p_228225_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228209_, StructureManager p_228210_, ChunkGenerator p_228211_, RandomSource p_228212_, BoundingBox p_228213_, ChunkPos p_228214_, BlockPos p_228215_) {
            this.m_73441_(p_228209_, p_228213_, 0, 0, 0, 4, 1, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228209_, p_228213_, 0, 2, 0, 4, 5, 4, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            this.m_73441_(p_228209_, p_228213_, 0, 2, 0, 0, 5, 0, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228209_, p_228213_, 4, 2, 0, 4, 5, 0, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228209_, p_228213_, 0, 2, 4, 0, 5, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228209_, p_228213_, 4, 2, 4, 4, 5, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228209_, p_228213_, 0, 6, 0, 4, 6, 4, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            for (int $$7 = 0; $$7 <= 4; ++$$7) {
                for (int $$8 = 0; $$8 <= 4; ++$$8) {
                    this.m_73528_(p_228209_, Blocks.f_50197_.m_49966_(), $$7, -1, $$8, p_228213_);
                }
            }
        }
    }

    public static class CastleStalkRoom
    extends NetherBridgePiece {
        private static final int f_228323_ = 13;
        private static final int f_228324_ = 14;
        private static final int f_228325_ = 13;

        public CastleStalkRoom(int p_228327_, BoundingBox p_228328_, Direction p_228329_) {
            super(StructurePieceType.f_210139_, p_228327_, p_228328_);
            this.m_73519_(p_228329_);
        }

        public CastleStalkRoom(CompoundTag p_228331_) {
            super(StructurePieceType.f_210139_, p_228331_);
        }

        @Override
        public void m_214092_(StructurePiece p_228341_, StructurePieceAccessor p_228342_, RandomSource p_228343_) {
            this.m_228401_((StartPiece)p_228341_, p_228342_, p_228343_, 5, 3, true);
            this.m_228401_((StartPiece)p_228341_, p_228342_, p_228343_, 5, 11, true);
        }

        public static CastleStalkRoom m_228344_(StructurePieceAccessor p_228345_, int p_228346_, int p_228347_, int p_228348_, Direction p_228349_, int p_228350_) {
            BoundingBox $$6 = BoundingBox.m_71031_(p_228346_, p_228347_, p_228348_, -5, -3, 0, 13, 14, 13, p_228349_);
            if (!CastleStalkRoom.m_228386_($$6) || p_228345_.m_141921_($$6) != null) {
                return null;
            }
            return new CastleStalkRoom(p_228350_, $$6, p_228349_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228333_, StructureManager p_228334_, ChunkGenerator p_228335_, RandomSource p_228336_, BoundingBox p_228337_, ChunkPos p_228338_, BlockPos p_228339_) {
            this.m_73441_(p_228333_, p_228337_, 0, 3, 0, 12, 4, 12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 0, 5, 0, 12, 13, 12, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 0, 5, 0, 1, 12, 12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 11, 5, 0, 12, 12, 12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 2, 5, 11, 4, 12, 12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 8, 5, 11, 10, 12, 12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 5, 9, 11, 7, 12, 12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 2, 5, 0, 4, 12, 1, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 8, 5, 0, 10, 12, 1, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 5, 9, 0, 7, 12, 1, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 2, 11, 2, 10, 12, 10, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            BlockState $$7 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52312_, true)).m_61124_(FenceBlock.f_52310_, true);
            BlockState $$8 = (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52311_, true);
            BlockState $$9 = (BlockState)$$8.m_61124_(FenceBlock.f_52312_, true);
            BlockState $$10 = (BlockState)$$8.m_61124_(FenceBlock.f_52310_, true);
            for (int $$11 = 1; $$11 <= 11; $$11 += 2) {
                this.m_73441_(p_228333_, p_228337_, $$11, 10, 0, $$11, 11, 0, $$7, $$7, false);
                this.m_73441_(p_228333_, p_228337_, $$11, 10, 12, $$11, 11, 12, $$7, $$7, false);
                this.m_73441_(p_228333_, p_228337_, 0, 10, $$11, 0, 11, $$11, $$8, $$8, false);
                this.m_73441_(p_228333_, p_228337_, 12, 10, $$11, 12, 11, $$11, $$8, $$8, false);
                this.m_73434_(p_228333_, Blocks.f_50197_.m_49966_(), $$11, 13, 0, p_228337_);
                this.m_73434_(p_228333_, Blocks.f_50197_.m_49966_(), $$11, 13, 12, p_228337_);
                this.m_73434_(p_228333_, Blocks.f_50197_.m_49966_(), 0, 13, $$11, p_228337_);
                this.m_73434_(p_228333_, Blocks.f_50197_.m_49966_(), 12, 13, $$11, p_228337_);
                if ($$11 == 11) continue;
                this.m_73434_(p_228333_, $$7, $$11 + 1, 13, 0, p_228337_);
                this.m_73434_(p_228333_, $$7, $$11 + 1, 13, 12, p_228337_);
                this.m_73434_(p_228333_, $$8, 0, 13, $$11 + 1, p_228337_);
                this.m_73434_(p_228333_, $$8, 12, 13, $$11 + 1, p_228337_);
            }
            this.m_73434_(p_228333_, (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52310_, true), 0, 13, 0, p_228337_);
            this.m_73434_(p_228333_, (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52311_, true)).m_61124_(FenceBlock.f_52310_, true), 0, 13, 12, p_228337_);
            this.m_73434_(p_228333_, (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52311_, true)).m_61124_(FenceBlock.f_52312_, true), 12, 13, 12, p_228337_);
            this.m_73434_(p_228333_, (BlockState)((BlockState)Blocks.f_50198_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52312_, true), 12, 13, 0, p_228337_);
            for (int $$12 = 3; $$12 <= 9; $$12 += 2) {
                this.m_73441_(p_228333_, p_228337_, 1, 7, $$12, 1, 8, $$12, $$9, $$9, false);
                this.m_73441_(p_228333_, p_228337_, 11, 7, $$12, 11, 8, $$12, $$10, $$10, false);
            }
            BlockState $$13 = (BlockState)Blocks.f_50199_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.NORTH);
            for (int $$14 = 0; $$14 <= 6; ++$$14) {
                int $$15 = $$14 + 4;
                for (int $$16 = 5; $$16 <= 7; ++$$16) {
                    this.m_73434_(p_228333_, $$13, $$16, 5 + $$14, $$15, p_228337_);
                }
                if ($$15 >= 5 && $$15 <= 8) {
                    this.m_73441_(p_228333_, p_228337_, 5, 5, $$15, 7, $$14 + 4, $$15, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
                } else if ($$15 >= 9 && $$15 <= 10) {
                    this.m_73441_(p_228333_, p_228337_, 5, 8, $$15, 7, $$14 + 4, $$15, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
                }
                if ($$14 < 1) continue;
                this.m_73441_(p_228333_, p_228337_, 5, 6 + $$14, $$15, 7, 9 + $$14, $$15, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            }
            for (int $$17 = 5; $$17 <= 7; ++$$17) {
                this.m_73434_(p_228333_, $$13, $$17, 12, 11, p_228337_);
            }
            this.m_73441_(p_228333_, p_228337_, 5, 6, 7, 5, 7, 7, $$10, $$10, false);
            this.m_73441_(p_228333_, p_228337_, 7, 6, 7, 7, 7, 7, $$9, $$9, false);
            this.m_73441_(p_228333_, p_228337_, 5, 13, 12, 7, 13, 12, Blocks.f_50016_.m_49966_(), Blocks.f_50016_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 2, 5, 2, 3, 5, 3, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 2, 5, 9, 3, 5, 10, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 2, 5, 4, 2, 5, 8, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 9, 5, 2, 10, 5, 3, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 9, 5, 9, 10, 5, 10, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 10, 5, 4, 10, 5, 8, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            BlockState $$18 = (BlockState)$$13.m_61124_(StairBlock.f_56841_, Direction.EAST);
            BlockState $$19 = (BlockState)$$13.m_61124_(StairBlock.f_56841_, Direction.WEST);
            this.m_73434_(p_228333_, $$19, 4, 5, 2, p_228337_);
            this.m_73434_(p_228333_, $$19, 4, 5, 3, p_228337_);
            this.m_73434_(p_228333_, $$19, 4, 5, 9, p_228337_);
            this.m_73434_(p_228333_, $$19, 4, 5, 10, p_228337_);
            this.m_73434_(p_228333_, $$18, 8, 5, 2, p_228337_);
            this.m_73434_(p_228333_, $$18, 8, 5, 3, p_228337_);
            this.m_73434_(p_228333_, $$18, 8, 5, 9, p_228337_);
            this.m_73434_(p_228333_, $$18, 8, 5, 10, p_228337_);
            this.m_73441_(p_228333_, p_228337_, 3, 4, 4, 4, 4, 8, Blocks.f_50135_.m_49966_(), Blocks.f_50135_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 8, 4, 4, 9, 4, 8, Blocks.f_50135_.m_49966_(), Blocks.f_50135_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 3, 5, 4, 4, 5, 8, Blocks.f_50200_.m_49966_(), Blocks.f_50200_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 8, 5, 4, 9, 5, 8, Blocks.f_50200_.m_49966_(), Blocks.f_50200_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 4, 2, 0, 8, 2, 12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 0, 2, 4, 12, 2, 8, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 4, 0, 0, 8, 1, 3, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 4, 0, 9, 8, 1, 12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 0, 0, 4, 3, 1, 8, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            this.m_73441_(p_228333_, p_228337_, 9, 0, 4, 12, 1, 8, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            for (int $$20 = 4; $$20 <= 8; ++$$20) {
                for (int $$21 = 0; $$21 <= 2; ++$$21) {
                    this.m_73528_(p_228333_, Blocks.f_50197_.m_49966_(), $$20, -1, $$21, p_228337_);
                    this.m_73528_(p_228333_, Blocks.f_50197_.m_49966_(), $$20, -1, 12 - $$21, p_228337_);
                }
            }
            for (int $$22 = 0; $$22 <= 2; ++$$22) {
                for (int $$23 = 4; $$23 <= 8; ++$$23) {
                    this.m_73528_(p_228333_, Blocks.f_50197_.m_49966_(), $$22, -1, $$23, p_228337_);
                    this.m_73528_(p_228333_, Blocks.f_50197_.m_49966_(), 12 - $$22, -1, $$23, p_228337_);
                }
            }
        }
    }

    public static class BridgeEndFiller
    extends NetherBridgePiece {
        private static final int f_228053_ = 5;
        private static final int f_228054_ = 10;
        private static final int f_228055_ = 8;
        private final int f_228056_;

        public BridgeEndFiller(int p_228058_, RandomSource p_228059_, BoundingBox p_228060_, Direction p_228061_) {
            super(StructurePieceType.f_210130_, p_228058_, p_228060_);
            this.m_73519_(p_228061_);
            this.f_228056_ = p_228059_.m_188502_();
        }

        public BridgeEndFiller(CompoundTag p_228063_) {
            super(StructurePieceType.f_210130_, p_228063_);
            this.f_228056_ = p_228063_.m_128451_("Seed");
        }

        public static BridgeEndFiller m_228072_(StructurePieceAccessor p_228073_, RandomSource p_228074_, int p_228075_, int p_228076_, int p_228077_, Direction p_228078_, int p_228079_) {
            BoundingBox $$7 = BoundingBox.m_71031_(p_228075_, p_228076_, p_228077_, -1, -3, 0, 5, 10, 8, p_228078_);
            if (!BridgeEndFiller.m_228386_($$7) || p_228073_.m_141921_($$7) != null) {
                return null;
            }
            return new BridgeEndFiller(p_228079_, p_228074_, $$7, p_228078_);
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_228081_, CompoundTag p_228082_) {
            super.m_183620_(p_228081_, p_228082_);
            p_228082_.m_128405_("Seed", this.f_228056_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228065_, StructureManager p_228066_, ChunkGenerator p_228067_, RandomSource p_228068_, BoundingBox p_228069_, ChunkPos p_228070_, BlockPos p_228071_) {
            RandomSource $$7 = RandomSource.m_216335_(this.f_228056_);
            for (int $$8 = 0; $$8 <= 4; ++$$8) {
                for (int $$9 = 3; $$9 <= 4; ++$$9) {
                    int $$10 = $$7.m_188503_(8);
                    this.m_73441_(p_228065_, p_228069_, $$8, $$9, 0, $$8, $$9, $$10, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
                }
            }
            int $$11 = $$7.m_188503_(8);
            this.m_73441_(p_228065_, p_228069_, 0, 5, 0, 0, 5, $$11, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            int $$12 = $$7.m_188503_(8);
            this.m_73441_(p_228065_, p_228069_, 4, 5, 0, 4, 5, $$12, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            for (int $$13 = 0; $$13 <= 4; ++$$13) {
                int $$14 = $$7.m_188503_(5);
                this.m_73441_(p_228065_, p_228069_, $$13, 2, 0, $$13, 2, $$14, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
            }
            for (int $$15 = 0; $$15 <= 4; ++$$15) {
                for (int $$16 = 0; $$16 <= 1; ++$$16) {
                    int $$17 = $$7.m_188503_(3);
                    this.m_73441_(p_228065_, p_228069_, $$15, $$16, 0, $$15, $$16, $$17, Blocks.f_50197_.m_49966_(), Blocks.f_50197_.m_49966_(), false);
                }
            }
        }
    }

    public static class StartPiece
    extends BridgeCrossing {
        public PieceWeight f_228507_;
        public List<PieceWeight> f_228508_;
        public List<PieceWeight> f_228509_;
        public final List<StructurePiece> f_228510_ = Lists.newArrayList();

        public StartPiece(RandomSource p_228512_, int p_228513_, int p_228514_) {
            super(p_228513_, p_228514_, StartPiece.m_226760_(p_228512_));
            this.f_228508_ = Lists.newArrayList();
            for (PieceWeight $$3 : f_228003_) {
                $$3.f_228436_ = 0;
                this.f_228508_.add($$3);
            }
            this.f_228509_ = Lists.newArrayList();
            for (PieceWeight $$4 : f_228004_) {
                $$4.f_228436_ = 0;
                this.f_228509_.add($$4);
            }
        }

        public StartPiece(CompoundTag p_228516_) {
            super(StructurePieceType.f_210143_, p_228516_);
        }
    }

    static abstract class NetherBridgePiece
    extends StructurePiece {
        protected NetherBridgePiece(StructurePieceType p_228380_, int p_228381_, BoundingBox p_228382_) {
            super(p_228380_, p_228381_, p_228382_);
        }

        public NetherBridgePiece(StructurePieceType p_228384_, CompoundTag p_228385_) {
            super(p_228384_, p_228385_);
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_228389_, CompoundTag p_228390_) {
        }

        private int m_228418_(List<PieceWeight> p_228419_) {
            boolean $$1 = false;
            int $$2 = 0;
            for (PieceWeight $$3 : p_228419_) {
                if ($$3.f_228437_ > 0 && $$3.f_228436_ < $$3.f_228437_) {
                    $$1 = true;
                }
                $$2 += $$3.f_228435_;
            }
            return $$1 ? $$2 : -1;
        }

        private NetherBridgePiece m_228408_(StartPiece p_228409_, List<PieceWeight> p_228410_, StructurePieceAccessor p_228411_, RandomSource p_228412_, int p_228413_, int p_228414_, int p_228415_, Direction p_228416_, int p_228417_) {
            int $$9 = this.m_228418_(p_228410_);
            boolean $$10 = $$9 > 0 && p_228417_ <= 30;
            int $$11 = 0;
            block0: while ($$11 < 5 && $$10) {
                ++$$11;
                int $$12 = p_228412_.m_188503_($$9);
                for (PieceWeight $$13 : p_228410_) {
                    if (($$12 -= $$13.f_228435_) >= 0) continue;
                    if (!$$13.m_228449_(p_228417_) || $$13 == p_228409_.f_228507_ && !$$13.f_228438_) continue block0;
                    NetherBridgePiece $$14 = NetherFortressPieces.m_228007_($$13, p_228411_, p_228412_, p_228413_, p_228414_, p_228415_, p_228416_, p_228417_);
                    if ($$14 == null) continue;
                    ++$$13.f_228436_;
                    p_228409_.f_228507_ = $$13;
                    if (!$$13.m_228448_()) {
                        p_228410_.remove($$13);
                    }
                    return $$14;
                }
            }
            return BridgeEndFiller.m_228072_(p_228411_, p_228412_, p_228413_, p_228414_, p_228415_, p_228416_, p_228417_);
        }

        private StructurePiece m_228391_(StartPiece p_228392_, StructurePieceAccessor p_228393_, RandomSource p_228394_, int p_228395_, int p_228396_, int p_228397_, @Nullable Direction p_228398_, int p_228399_, boolean p_228400_) {
            NetherBridgePiece $$10;
            if (Math.abs(p_228395_ - p_228392_.m_73547_().m_162395_()) > 112 || Math.abs(p_228397_ - p_228392_.m_73547_().m_162398_()) > 112) {
                return BridgeEndFiller.m_228072_(p_228393_, p_228394_, p_228395_, p_228396_, p_228397_, p_228398_, p_228399_);
            }
            List<PieceWeight> $$9 = p_228392_.f_228508_;
            if (p_228400_) {
                $$9 = p_228392_.f_228509_;
            }
            if (($$10 = this.m_228408_(p_228392_, $$9, p_228393_, p_228394_, p_228395_, p_228396_, p_228397_, p_228398_, p_228399_ + 1)) != null) {
                p_228393_.m_142679_($$10);
                p_228392_.f_228510_.add($$10);
            }
            return $$10;
        }

        @Nullable
        protected StructurePiece m_228401_(StartPiece p_228402_, StructurePieceAccessor p_228403_, RandomSource p_228404_, int p_228405_, int p_228406_, boolean p_228407_) {
            Direction $$6 = this.m_73549_();
            if ($$6 != null) {
                switch ($$6) {
                    case NORTH: {
                        return this.m_228391_(p_228402_, p_228403_, p_228404_, this.f_73383_.m_162395_() + p_228405_, this.f_73383_.m_162396_() + p_228406_, this.f_73383_.m_162398_() - 1, $$6, this.m_73548_(), p_228407_);
                    }
                    case SOUTH: {
                        return this.m_228391_(p_228402_, p_228403_, p_228404_, this.f_73383_.m_162395_() + p_228405_, this.f_73383_.m_162396_() + p_228406_, this.f_73383_.m_162401_() + 1, $$6, this.m_73548_(), p_228407_);
                    }
                    case WEST: {
                        return this.m_228391_(p_228402_, p_228403_, p_228404_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() + p_228406_, this.f_73383_.m_162398_() + p_228405_, $$6, this.m_73548_(), p_228407_);
                    }
                    case EAST: {
                        return this.m_228391_(p_228402_, p_228403_, p_228404_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() + p_228406_, this.f_73383_.m_162398_() + p_228405_, $$6, this.m_73548_(), p_228407_);
                    }
                }
            }
            return null;
        }

        @Nullable
        protected StructurePiece m_228420_(StartPiece p_228421_, StructurePieceAccessor p_228422_, RandomSource p_228423_, int p_228424_, int p_228425_, boolean p_228426_) {
            Direction $$6 = this.m_73549_();
            if ($$6 != null) {
                switch ($$6) {
                    case NORTH: {
                        return this.m_228391_(p_228421_, p_228422_, p_228423_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() + p_228424_, this.f_73383_.m_162398_() + p_228425_, Direction.WEST, this.m_73548_(), p_228426_);
                    }
                    case SOUTH: {
                        return this.m_228391_(p_228421_, p_228422_, p_228423_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() + p_228424_, this.f_73383_.m_162398_() + p_228425_, Direction.WEST, this.m_73548_(), p_228426_);
                    }
                    case WEST: {
                        return this.m_228391_(p_228421_, p_228422_, p_228423_, this.f_73383_.m_162395_() + p_228425_, this.f_73383_.m_162396_() + p_228424_, this.f_73383_.m_162398_() - 1, Direction.NORTH, this.m_73548_(), p_228426_);
                    }
                    case EAST: {
                        return this.m_228391_(p_228421_, p_228422_, p_228423_, this.f_73383_.m_162395_() + p_228425_, this.f_73383_.m_162396_() + p_228424_, this.f_73383_.m_162398_() - 1, Direction.NORTH, this.m_73548_(), p_228426_);
                    }
                }
            }
            return null;
        }

        @Nullable
        protected StructurePiece m_228427_(StartPiece p_228428_, StructurePieceAccessor p_228429_, RandomSource p_228430_, int p_228431_, int p_228432_, boolean p_228433_) {
            Direction $$6 = this.m_73549_();
            if ($$6 != null) {
                switch ($$6) {
                    case NORTH: {
                        return this.m_228391_(p_228428_, p_228429_, p_228430_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() + p_228431_, this.f_73383_.m_162398_() + p_228432_, Direction.EAST, this.m_73548_(), p_228433_);
                    }
                    case SOUTH: {
                        return this.m_228391_(p_228428_, p_228429_, p_228430_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() + p_228431_, this.f_73383_.m_162398_() + p_228432_, Direction.EAST, this.m_73548_(), p_228433_);
                    }
                    case WEST: {
                        return this.m_228391_(p_228428_, p_228429_, p_228430_, this.f_73383_.m_162395_() + p_228432_, this.f_73383_.m_162396_() + p_228431_, this.f_73383_.m_162401_() + 1, Direction.SOUTH, this.m_73548_(), p_228433_);
                    }
                    case EAST: {
                        return this.m_228391_(p_228428_, p_228429_, p_228430_, this.f_73383_.m_162395_() + p_228432_, this.f_73383_.m_162396_() + p_228431_, this.f_73383_.m_162401_() + 1, Direction.SOUTH, this.m_73548_(), p_228433_);
                    }
                }
            }
            return null;
        }

        protected static boolean m_228386_(BoundingBox p_228387_) {
            return p_228387_ != null && p_228387_.m_162396_() > 10;
        }
    }
}

