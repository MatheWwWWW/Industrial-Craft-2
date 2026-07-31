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
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class StrongholdPieces {
    private static final int f_229404_ = 3;
    private static final int f_229405_ = 3;
    private static final int f_229406_ = 50;
    private static final int f_229407_ = 10;
    private static final boolean f_229408_ = true;
    public static final int f_229403_ = 64;
    private static final PieceWeight[] f_229409_ = new PieceWeight[]{new PieceWeight(Straight.class, 40, 0), new PieceWeight(PrisonHall.class, 5, 5), new PieceWeight(LeftTurn.class, 20, 0), new PieceWeight(RightTurn.class, 20, 0), new PieceWeight(RoomCrossing.class, 10, 6), new PieceWeight(StraightStairsDown.class, 5, 5), new PieceWeight(StairsDown.class, 5, 5), new PieceWeight(FiveCrossing.class, 5, 4), new PieceWeight(ChestCorridor.class, 5, 4), new PieceWeight(Library.class, 10, 2){

        @Override
        public boolean m_214198_(int p_229450_) {
            return super.m_214198_(p_229450_) && p_229450_ > 4;
        }
    }, new PieceWeight(PortalRoom.class, 20, 1){

        @Override
        public boolean m_214198_(int p_229456_) {
            return super.m_214198_(p_229456_) && p_229456_ > 5;
        }
    }};
    private static List<PieceWeight> f_229410_;
    static Class<? extends StrongholdPiece> f_229411_;
    private static int f_229412_;
    static final SmoothStoneSelector f_229413_;

    public static void m_229416_() {
        f_229410_ = Lists.newArrayList();
        for (PieceWeight $$0 : f_229409_) {
            $$0.f_229616_ = 0;
            f_229410_.add($$0);
        }
        f_229411_ = null;
    }

    private static boolean m_229435_() {
        boolean $$0 = false;
        f_229412_ = 0;
        for (PieceWeight $$1 : f_229410_) {
            if ($$1.f_229617_ > 0 && $$1.f_229616_ < $$1.f_229617_) {
                $$0 = true;
            }
            f_229412_ += $$1.f_229615_;
        }
        return $$0;
    }

    private static StrongholdPiece m_229426_(Class<? extends StrongholdPiece> p_229427_, StructurePieceAccessor p_229428_, RandomSource p_229429_, int p_229430_, int p_229431_, int p_229432_, @Nullable Direction p_229433_, int p_229434_) {
        StrongholdPiece $$8 = null;
        if (p_229427_ == Straight.class) {
            $$8 = Straight.m_229831_(p_229428_, p_229429_, p_229430_, p_229431_, p_229432_, p_229433_, p_229434_);
        } else if (p_229427_ == PrisonHall.class) {
            $$8 = PrisonHall.m_229678_(p_229428_, p_229429_, p_229430_, p_229431_, p_229432_, p_229433_, p_229434_);
        } else if (p_229427_ == LeftTurn.class) {
            $$8 = LeftTurn.m_229575_(p_229428_, p_229429_, p_229430_, p_229431_, p_229432_, p_229433_, p_229434_);
        } else if (p_229427_ == RightTurn.class) {
            $$8 = RightTurn.m_229705_(p_229428_, p_229429_, p_229430_, p_229431_, p_229432_, p_229433_, p_229434_);
        } else if (p_229427_ == RoomCrossing.class) {
            $$8 = RoomCrossing.m_229736_(p_229428_, p_229429_, p_229430_, p_229431_, p_229432_, p_229433_, p_229434_);
        } else if (p_229427_ == StraightStairsDown.class) {
            $$8 = StraightStairsDown.m_229864_(p_229428_, p_229429_, p_229430_, p_229431_, p_229432_, p_229433_, p_229434_);
        } else if (p_229427_ == StairsDown.class) {
            $$8 = StairsDown.m_229786_(p_229428_, p_229429_, p_229430_, p_229431_, p_229432_, p_229433_, p_229434_);
        } else if (p_229427_ == FiveCrossing.class) {
            $$8 = FiveCrossing.m_229545_(p_229428_, p_229429_, p_229430_, p_229431_, p_229432_, p_229433_, p_229434_);
        } else if (p_229427_ == ChestCorridor.class) {
            $$8 = ChestCorridor.m_229483_(p_229428_, p_229429_, p_229430_, p_229431_, p_229432_, p_229433_, p_229434_);
        } else if (p_229427_ == Library.class) {
            $$8 = Library.m_229603_(p_229428_, p_229429_, p_229430_, p_229431_, p_229432_, p_229433_, p_229434_);
        } else if (p_229427_ == PortalRoom.class) {
            $$8 = PortalRoom.m_229646_(p_229428_, p_229430_, p_229431_, p_229432_, p_229433_, p_229434_);
        }
        return $$8;
    }

    private static StrongholdPiece m_229417_(StartPiece p_229418_, StructurePieceAccessor p_229419_, RandomSource p_229420_, int p_229421_, int p_229422_, int p_229423_, Direction p_229424_, int p_229425_) {
        if (!StrongholdPieces.m_229435_()) {
            return null;
        }
        if (f_229411_ != null) {
            StrongholdPiece $$8 = StrongholdPieces.m_229426_(f_229411_, p_229419_, p_229420_, p_229421_, p_229422_, p_229423_, p_229424_, p_229425_);
            f_229411_ = null;
            if ($$8 != null) {
                return $$8;
            }
        }
        int $$9 = 0;
        block0: while ($$9 < 5) {
            ++$$9;
            int $$10 = p_229420_.m_188503_(f_229412_);
            for (PieceWeight $$11 : f_229410_) {
                if (($$10 -= $$11.f_229615_) >= 0) continue;
                if (!$$11.m_214198_(p_229425_) || $$11 == p_229418_.f_229797_) continue block0;
                StrongholdPiece $$12 = StrongholdPieces.m_229426_($$11.f_229614_, p_229419_, p_229420_, p_229421_, p_229422_, p_229423_, p_229424_, p_229425_);
                if ($$12 == null) continue;
                ++$$11.f_229616_;
                p_229418_.f_229797_ = $$11;
                if (!$$11.m_229622_()) {
                    f_229410_.remove($$11);
                }
                return $$12;
            }
        }
        BoundingBox $$13 = FillerCorridor.m_229509_(p_229419_, p_229420_, p_229421_, p_229422_, p_229423_, p_229424_);
        if ($$13 != null && $$13.m_162396_() > 1) {
            return new FillerCorridor(p_229425_, $$13, p_229424_);
        }
        return null;
    }

    static StructurePiece m_229436_(StartPiece p_229437_, StructurePieceAccessor p_229438_, RandomSource p_229439_, int p_229440_, int p_229441_, int p_229442_, @Nullable Direction p_229443_, int p_229444_) {
        if (p_229444_ > 50) {
            return null;
        }
        if (Math.abs(p_229440_ - p_229437_.m_73547_().m_162395_()) > 112 || Math.abs(p_229442_ - p_229437_.m_73547_().m_162398_()) > 112) {
            return null;
        }
        StrongholdPiece $$8 = StrongholdPieces.m_229417_(p_229437_, p_229438_, p_229439_, p_229440_, p_229441_, p_229442_, p_229443_, p_229444_ + 1);
        if ($$8 != null) {
            p_229438_.m_142679_($$8);
            p_229437_.f_229799_.add($$8);
        }
        return $$8;
    }

    static {
        f_229413_ = new SmoothStoneSelector();
    }

    static class PieceWeight {
        public final Class<? extends StrongholdPiece> f_229614_;
        public final int f_229615_;
        public int f_229616_;
        public final int f_229617_;

        public PieceWeight(Class<? extends StrongholdPiece> p_229619_, int p_229620_, int p_229621_) {
            this.f_229614_ = p_229619_;
            this.f_229615_ = p_229620_;
            this.f_229617_ = p_229621_;
        }

        public boolean m_214198_(int p_229623_) {
            return this.f_229617_ == 0 || this.f_229616_ < this.f_229617_;
        }

        public boolean m_229622_() {
            return this.f_229617_ == 0 || this.f_229616_ < this.f_229617_;
        }
    }

    public static class Straight
    extends StrongholdPiece {
        private static final int f_229807_ = 5;
        private static final int f_229808_ = 5;
        private static final int f_229809_ = 7;
        private final boolean f_229810_;
        private final boolean f_229811_;

        public Straight(int p_229813_, RandomSource p_229814_, BoundingBox p_229815_, Direction p_229816_) {
            super(StructurePieceType.f_210099_, p_229813_, p_229815_);
            this.m_73519_(p_229816_);
            this.f_229872_ = this.m_229899_(p_229814_);
            this.f_229810_ = p_229814_.m_188503_(2) == 0;
            this.f_229811_ = p_229814_.m_188503_(2) == 0;
        }

        public Straight(CompoundTag p_229818_) {
            super(StructurePieceType.f_210099_, p_229818_);
            this.f_229810_ = p_229818_.m_128471_("Left");
            this.f_229811_ = p_229818_.m_128471_("Right");
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_229840_, CompoundTag p_229841_) {
            super.m_183620_(p_229840_, p_229841_);
            p_229841_.m_128379_("Left", this.f_229810_);
            p_229841_.m_128379_("Right", this.f_229811_);
        }

        @Override
        public void m_214092_(StructurePiece p_229828_, StructurePieceAccessor p_229829_, RandomSource p_229830_) {
            this.m_229893_((StartPiece)p_229828_, p_229829_, p_229830_, 1, 1);
            if (this.f_229810_) {
                this.m_229901_((StartPiece)p_229828_, p_229829_, p_229830_, 1, 2);
            }
            if (this.f_229811_) {
                this.m_229907_((StartPiece)p_229828_, p_229829_, p_229830_, 1, 2);
            }
        }

        public static Straight m_229831_(StructurePieceAccessor p_229832_, RandomSource p_229833_, int p_229834_, int p_229835_, int p_229836_, Direction p_229837_, int p_229838_) {
            BoundingBox $$7 = BoundingBox.m_71031_(p_229834_, p_229835_, p_229836_, -1, -1, 0, 5, 5, 7, p_229837_);
            if (!Straight.m_229888_($$7) || p_229832_.m_141921_($$7) != null) {
                return null;
            }
            return new Straight(p_229838_, p_229833_, $$7, p_229837_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_229820_, StructureManager p_229821_, ChunkGenerator p_229822_, RandomSource p_229823_, BoundingBox p_229824_, ChunkPos p_229825_, BlockPos p_229826_) {
            this.m_226776_(p_229820_, p_229824_, 0, 0, 0, 4, 4, 6, true, p_229823_, f_229413_);
            this.m_229880_(p_229820_, p_229823_, p_229824_, this.f_229872_, 1, 1, 0);
            this.m_229880_(p_229820_, p_229823_, p_229824_, StrongholdPiece.SmallDoorType.OPENING, 1, 1, 6);
            BlockState $$7 = (BlockState)Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.EAST);
            BlockState $$8 = (BlockState)Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.WEST);
            this.m_226803_(p_229820_, p_229824_, p_229823_, 0.1f, 1, 2, 1, $$7);
            this.m_226803_(p_229820_, p_229824_, p_229823_, 0.1f, 3, 2, 1, $$8);
            this.m_226803_(p_229820_, p_229824_, p_229823_, 0.1f, 1, 2, 5, $$7);
            this.m_226803_(p_229820_, p_229824_, p_229823_, 0.1f, 3, 2, 5, $$8);
            if (this.f_229810_) {
                this.m_73441_(p_229820_, p_229824_, 0, 1, 2, 0, 3, 4, f_73382_, f_73382_, false);
            }
            if (this.f_229811_) {
                this.m_73441_(p_229820_, p_229824_, 4, 1, 2, 4, 3, 4, f_73382_, f_73382_, false);
            }
        }
    }

    public static class PrisonHall
    extends StrongholdPiece {
        protected static final int f_229656_ = 9;
        protected static final int f_229657_ = 5;
        protected static final int f_229658_ = 11;

        public PrisonHall(int p_229660_, RandomSource p_229661_, BoundingBox p_229662_, Direction p_229663_) {
            super(StructurePieceType.f_210150_, p_229660_, p_229662_);
            this.m_73519_(p_229663_);
            this.f_229872_ = this.m_229899_(p_229661_);
        }

        public PrisonHall(CompoundTag p_229665_) {
            super(StructurePieceType.f_210150_, p_229665_);
        }

        @Override
        public void m_214092_(StructurePiece p_229675_, StructurePieceAccessor p_229676_, RandomSource p_229677_) {
            this.m_229893_((StartPiece)p_229675_, p_229676_, p_229677_, 1, 1);
        }

        public static PrisonHall m_229678_(StructurePieceAccessor p_229679_, RandomSource p_229680_, int p_229681_, int p_229682_, int p_229683_, Direction p_229684_, int p_229685_) {
            BoundingBox $$7 = BoundingBox.m_71031_(p_229681_, p_229682_, p_229683_, -1, -1, 0, 9, 5, 11, p_229684_);
            if (!PrisonHall.m_229888_($$7) || p_229679_.m_141921_($$7) != null) {
                return null;
            }
            return new PrisonHall(p_229685_, p_229680_, $$7, p_229684_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_229667_, StructureManager p_229668_, ChunkGenerator p_229669_, RandomSource p_229670_, BoundingBox p_229671_, ChunkPos p_229672_, BlockPos p_229673_) {
            this.m_226776_(p_229667_, p_229671_, 0, 0, 0, 8, 4, 10, true, p_229670_, f_229413_);
            this.m_229880_(p_229667_, p_229670_, p_229671_, this.f_229872_, 1, 1, 0);
            this.m_73441_(p_229667_, p_229671_, 1, 1, 10, 3, 3, 10, f_73382_, f_73382_, false);
            this.m_226776_(p_229667_, p_229671_, 4, 1, 1, 4, 3, 1, false, p_229670_, f_229413_);
            this.m_226776_(p_229667_, p_229671_, 4, 1, 3, 4, 3, 3, false, p_229670_, f_229413_);
            this.m_226776_(p_229667_, p_229671_, 4, 1, 7, 4, 3, 7, false, p_229670_, f_229413_);
            this.m_226776_(p_229667_, p_229671_, 4, 1, 9, 4, 3, 9, false, p_229670_, f_229413_);
            for (int $$7 = 1; $$7 <= 3; ++$$7) {
                this.m_73434_(p_229667_, (BlockState)((BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52309_, true)).m_61124_(IronBarsBlock.f_52311_, true), 4, $$7, 4, p_229671_);
                this.m_73434_(p_229667_, (BlockState)((BlockState)((BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52309_, true)).m_61124_(IronBarsBlock.f_52311_, true)).m_61124_(IronBarsBlock.f_52310_, true), 4, $$7, 5, p_229671_);
                this.m_73434_(p_229667_, (BlockState)((BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52309_, true)).m_61124_(IronBarsBlock.f_52311_, true), 4, $$7, 6, p_229671_);
                this.m_73434_(p_229667_, (BlockState)((BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52312_, true)).m_61124_(IronBarsBlock.f_52310_, true), 5, $$7, 5, p_229671_);
                this.m_73434_(p_229667_, (BlockState)((BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52312_, true)).m_61124_(IronBarsBlock.f_52310_, true), 6, $$7, 5, p_229671_);
                this.m_73434_(p_229667_, (BlockState)((BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52312_, true)).m_61124_(IronBarsBlock.f_52310_, true), 7, $$7, 5, p_229671_);
            }
            this.m_73434_(p_229667_, (BlockState)((BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52309_, true)).m_61124_(IronBarsBlock.f_52311_, true), 4, 3, 2, p_229671_);
            this.m_73434_(p_229667_, (BlockState)((BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52309_, true)).m_61124_(IronBarsBlock.f_52311_, true), 4, 3, 8, p_229671_);
            BlockState $$8 = (BlockState)Blocks.f_50166_.m_49966_().m_61124_(DoorBlock.f_52726_, Direction.WEST);
            BlockState $$9 = (BlockState)((BlockState)Blocks.f_50166_.m_49966_().m_61124_(DoorBlock.f_52726_, Direction.WEST)).m_61124_(DoorBlock.f_52730_, DoubleBlockHalf.UPPER);
            this.m_73434_(p_229667_, $$8, 4, 1, 2, p_229671_);
            this.m_73434_(p_229667_, $$9, 4, 2, 2, p_229671_);
            this.m_73434_(p_229667_, $$8, 4, 1, 8, p_229671_);
            this.m_73434_(p_229667_, $$9, 4, 2, 8, p_229671_);
        }
    }

    public static class LeftTurn
    extends Turn {
        public LeftTurn(int p_229557_, RandomSource p_229558_, BoundingBox p_229559_, Direction p_229560_) {
            super(StructurePieceType.f_210147_, p_229557_, p_229559_);
            this.m_73519_(p_229560_);
            this.f_229872_ = this.m_229899_(p_229558_);
        }

        public LeftTurn(CompoundTag p_229562_) {
            super(StructurePieceType.f_210147_, p_229562_);
        }

        @Override
        public void m_214092_(StructurePiece p_229572_, StructurePieceAccessor p_229573_, RandomSource p_229574_) {
            Direction $$3 = this.m_73549_();
            if ($$3 == Direction.NORTH || $$3 == Direction.EAST) {
                this.m_229901_((StartPiece)p_229572_, p_229573_, p_229574_, 1, 1);
            } else {
                this.m_229907_((StartPiece)p_229572_, p_229573_, p_229574_, 1, 1);
            }
        }

        public static LeftTurn m_229575_(StructurePieceAccessor p_229576_, RandomSource p_229577_, int p_229578_, int p_229579_, int p_229580_, Direction p_229581_, int p_229582_) {
            BoundingBox $$7 = BoundingBox.m_71031_(p_229578_, p_229579_, p_229580_, -1, -1, 0, 5, 5, 5, p_229581_);
            if (!LeftTurn.m_229888_($$7) || p_229576_.m_141921_($$7) != null) {
                return null;
            }
            return new LeftTurn(p_229582_, p_229577_, $$7, p_229581_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_229564_, StructureManager p_229565_, ChunkGenerator p_229566_, RandomSource p_229567_, BoundingBox p_229568_, ChunkPos p_229569_, BlockPos p_229570_) {
            this.m_226776_(p_229564_, p_229568_, 0, 0, 0, 4, 4, 4, true, p_229567_, f_229413_);
            this.m_229880_(p_229564_, p_229567_, p_229568_, this.f_229872_, 1, 1, 0);
            Direction $$7 = this.m_73549_();
            if ($$7 == Direction.NORTH || $$7 == Direction.EAST) {
                this.m_73441_(p_229564_, p_229568_, 0, 1, 1, 0, 3, 3, f_73382_, f_73382_, false);
            } else {
                this.m_73441_(p_229564_, p_229568_, 4, 1, 1, 4, 3, 3, f_73382_, f_73382_, false);
            }
        }
    }

    public static class RightTurn
    extends Turn {
        public RightTurn(int p_229687_, RandomSource p_229688_, BoundingBox p_229689_, Direction p_229690_) {
            super(StructurePieceType.f_210095_, p_229687_, p_229689_);
            this.m_73519_(p_229690_);
            this.f_229872_ = this.m_229899_(p_229688_);
        }

        public RightTurn(CompoundTag p_229692_) {
            super(StructurePieceType.f_210095_, p_229692_);
        }

        @Override
        public void m_214092_(StructurePiece p_229702_, StructurePieceAccessor p_229703_, RandomSource p_229704_) {
            Direction $$3 = this.m_73549_();
            if ($$3 == Direction.NORTH || $$3 == Direction.EAST) {
                this.m_229907_((StartPiece)p_229702_, p_229703_, p_229704_, 1, 1);
            } else {
                this.m_229901_((StartPiece)p_229702_, p_229703_, p_229704_, 1, 1);
            }
        }

        public static RightTurn m_229705_(StructurePieceAccessor p_229706_, RandomSource p_229707_, int p_229708_, int p_229709_, int p_229710_, Direction p_229711_, int p_229712_) {
            BoundingBox $$7 = BoundingBox.m_71031_(p_229708_, p_229709_, p_229710_, -1, -1, 0, 5, 5, 5, p_229711_);
            if (!RightTurn.m_229888_($$7) || p_229706_.m_141921_($$7) != null) {
                return null;
            }
            return new RightTurn(p_229712_, p_229707_, $$7, p_229711_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_229694_, StructureManager p_229695_, ChunkGenerator p_229696_, RandomSource p_229697_, BoundingBox p_229698_, ChunkPos p_229699_, BlockPos p_229700_) {
            this.m_226776_(p_229694_, p_229698_, 0, 0, 0, 4, 4, 4, true, p_229697_, f_229413_);
            this.m_229880_(p_229694_, p_229697_, p_229698_, this.f_229872_, 1, 1, 0);
            Direction $$7 = this.m_73549_();
            if ($$7 == Direction.NORTH || $$7 == Direction.EAST) {
                this.m_73441_(p_229694_, p_229698_, 4, 1, 1, 4, 3, 3, f_73382_, f_73382_, false);
            } else {
                this.m_73441_(p_229694_, p_229698_, 0, 1, 1, 0, 3, 3, f_73382_, f_73382_, false);
            }
        }
    }

    public static class RoomCrossing
    extends StrongholdPiece {
        protected static final int f_229713_ = 11;
        protected static final int f_229714_ = 7;
        protected static final int f_229715_ = 11;
        protected final int f_229716_;

        public RoomCrossing(int p_229718_, RandomSource p_229719_, BoundingBox p_229720_, Direction p_229721_) {
            super(StructurePieceType.f_210096_, p_229718_, p_229720_);
            this.m_73519_(p_229721_);
            this.f_229872_ = this.m_229899_(p_229719_);
            this.f_229716_ = p_229719_.m_188503_(5);
        }

        public RoomCrossing(CompoundTag p_229723_) {
            super(StructurePieceType.f_210096_, p_229723_);
            this.f_229716_ = p_229723_.m_128451_("Type");
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_229745_, CompoundTag p_229746_) {
            super.m_183620_(p_229745_, p_229746_);
            p_229746_.m_128405_("Type", this.f_229716_);
        }

        @Override
        public void m_214092_(StructurePiece p_229733_, StructurePieceAccessor p_229734_, RandomSource p_229735_) {
            this.m_229893_((StartPiece)p_229733_, p_229734_, p_229735_, 4, 1);
            this.m_229901_((StartPiece)p_229733_, p_229734_, p_229735_, 1, 4);
            this.m_229907_((StartPiece)p_229733_, p_229734_, p_229735_, 1, 4);
        }

        public static RoomCrossing m_229736_(StructurePieceAccessor p_229737_, RandomSource p_229738_, int p_229739_, int p_229740_, int p_229741_, Direction p_229742_, int p_229743_) {
            BoundingBox $$7 = BoundingBox.m_71031_(p_229739_, p_229740_, p_229741_, -4, -1, 0, 11, 7, 11, p_229742_);
            if (!RoomCrossing.m_229888_($$7) || p_229737_.m_141921_($$7) != null) {
                return null;
            }
            return new RoomCrossing(p_229743_, p_229738_, $$7, p_229742_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_229725_, StructureManager p_229726_, ChunkGenerator p_229727_, RandomSource p_229728_, BoundingBox p_229729_, ChunkPos p_229730_, BlockPos p_229731_) {
            this.m_226776_(p_229725_, p_229729_, 0, 0, 0, 10, 6, 10, true, p_229728_, f_229413_);
            this.m_229880_(p_229725_, p_229728_, p_229729_, this.f_229872_, 4, 1, 0);
            this.m_73441_(p_229725_, p_229729_, 4, 1, 10, 6, 3, 10, f_73382_, f_73382_, false);
            this.m_73441_(p_229725_, p_229729_, 0, 1, 4, 0, 3, 6, f_73382_, f_73382_, false);
            this.m_73441_(p_229725_, p_229729_, 10, 1, 4, 10, 3, 6, f_73382_, f_73382_, false);
            switch (this.f_229716_) {
                default: {
                    break;
                }
                case 0: {
                    this.m_73434_(p_229725_, Blocks.f_50222_.m_49966_(), 5, 1, 5, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50222_.m_49966_(), 5, 2, 5, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50222_.m_49966_(), 5, 3, 5, p_229729_);
                    this.m_73434_(p_229725_, (BlockState)Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.WEST), 4, 3, 5, p_229729_);
                    this.m_73434_(p_229725_, (BlockState)Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.EAST), 6, 3, 5, p_229729_);
                    this.m_73434_(p_229725_, (BlockState)Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.SOUTH), 5, 3, 4, p_229729_);
                    this.m_73434_(p_229725_, (BlockState)Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.NORTH), 5, 3, 6, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50405_.m_49966_(), 4, 1, 4, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50405_.m_49966_(), 4, 1, 5, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50405_.m_49966_(), 4, 1, 6, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50405_.m_49966_(), 6, 1, 4, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50405_.m_49966_(), 6, 1, 5, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50405_.m_49966_(), 6, 1, 6, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50405_.m_49966_(), 5, 1, 4, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50405_.m_49966_(), 5, 1, 6, p_229729_);
                    break;
                }
                case 1: {
                    for (int $$7 = 0; $$7 < 5; ++$$7) {
                        this.m_73434_(p_229725_, Blocks.f_50222_.m_49966_(), 3, 1, 3 + $$7, p_229729_);
                        this.m_73434_(p_229725_, Blocks.f_50222_.m_49966_(), 7, 1, 3 + $$7, p_229729_);
                        this.m_73434_(p_229725_, Blocks.f_50222_.m_49966_(), 3 + $$7, 1, 3, p_229729_);
                        this.m_73434_(p_229725_, Blocks.f_50222_.m_49966_(), 3 + $$7, 1, 7, p_229729_);
                    }
                    this.m_73434_(p_229725_, Blocks.f_50222_.m_49966_(), 5, 1, 5, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50222_.m_49966_(), 5, 2, 5, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50222_.m_49966_(), 5, 3, 5, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_49990_.m_49966_(), 5, 4, 5, p_229729_);
                    break;
                }
                case 2: {
                    for (int $$8 = 1; $$8 <= 9; ++$$8) {
                        this.m_73434_(p_229725_, Blocks.f_50652_.m_49966_(), 1, 3, $$8, p_229729_);
                        this.m_73434_(p_229725_, Blocks.f_50652_.m_49966_(), 9, 3, $$8, p_229729_);
                    }
                    for (int $$9 = 1; $$9 <= 9; ++$$9) {
                        this.m_73434_(p_229725_, Blocks.f_50652_.m_49966_(), $$9, 3, 1, p_229729_);
                        this.m_73434_(p_229725_, Blocks.f_50652_.m_49966_(), $$9, 3, 9, p_229729_);
                    }
                    this.m_73434_(p_229725_, Blocks.f_50652_.m_49966_(), 5, 1, 4, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50652_.m_49966_(), 5, 1, 6, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50652_.m_49966_(), 5, 3, 4, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50652_.m_49966_(), 5, 3, 6, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50652_.m_49966_(), 4, 1, 5, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50652_.m_49966_(), 6, 1, 5, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50652_.m_49966_(), 4, 3, 5, p_229729_);
                    this.m_73434_(p_229725_, Blocks.f_50652_.m_49966_(), 6, 3, 5, p_229729_);
                    for (int $$10 = 1; $$10 <= 3; ++$$10) {
                        this.m_73434_(p_229725_, Blocks.f_50652_.m_49966_(), 4, $$10, 4, p_229729_);
                        this.m_73434_(p_229725_, Blocks.f_50652_.m_49966_(), 6, $$10, 4, p_229729_);
                        this.m_73434_(p_229725_, Blocks.f_50652_.m_49966_(), 4, $$10, 6, p_229729_);
                        this.m_73434_(p_229725_, Blocks.f_50652_.m_49966_(), 6, $$10, 6, p_229729_);
                    }
                    this.m_73434_(p_229725_, Blocks.f_50081_.m_49966_(), 5, 3, 5, p_229729_);
                    for (int $$11 = 2; $$11 <= 8; ++$$11) {
                        this.m_73434_(p_229725_, Blocks.f_50705_.m_49966_(), 2, 3, $$11, p_229729_);
                        this.m_73434_(p_229725_, Blocks.f_50705_.m_49966_(), 3, 3, $$11, p_229729_);
                        if ($$11 <= 3 || $$11 >= 7) {
                            this.m_73434_(p_229725_, Blocks.f_50705_.m_49966_(), 4, 3, $$11, p_229729_);
                            this.m_73434_(p_229725_, Blocks.f_50705_.m_49966_(), 5, 3, $$11, p_229729_);
                            this.m_73434_(p_229725_, Blocks.f_50705_.m_49966_(), 6, 3, $$11, p_229729_);
                        }
                        this.m_73434_(p_229725_, Blocks.f_50705_.m_49966_(), 7, 3, $$11, p_229729_);
                        this.m_73434_(p_229725_, Blocks.f_50705_.m_49966_(), 8, 3, $$11, p_229729_);
                    }
                    BlockState $$12 = (BlockState)Blocks.f_50155_.m_49966_().m_61124_(LadderBlock.f_54337_, Direction.WEST);
                    this.m_73434_(p_229725_, $$12, 9, 1, 3, p_229729_);
                    this.m_73434_(p_229725_, $$12, 9, 2, 3, p_229729_);
                    this.m_73434_(p_229725_, $$12, 9, 3, 3, p_229729_);
                    this.m_213787_(p_229725_, p_229729_, p_229728_, 3, 4, 8, BuiltInLootTables.f_78762_);
                }
            }
        }
    }

    public static class StraightStairsDown
    extends StrongholdPiece {
        private static final int f_229842_ = 5;
        private static final int f_229843_ = 11;
        private static final int f_229844_ = 8;

        public StraightStairsDown(int p_229846_, RandomSource p_229847_, BoundingBox p_229848_, Direction p_229849_) {
            super(StructurePieceType.f_210100_, p_229846_, p_229848_);
            this.m_73519_(p_229849_);
            this.f_229872_ = this.m_229899_(p_229847_);
        }

        public StraightStairsDown(CompoundTag p_229851_) {
            super(StructurePieceType.f_210100_, p_229851_);
        }

        @Override
        public void m_214092_(StructurePiece p_229861_, StructurePieceAccessor p_229862_, RandomSource p_229863_) {
            this.m_229893_((StartPiece)p_229861_, p_229862_, p_229863_, 1, 1);
        }

        public static StraightStairsDown m_229864_(StructurePieceAccessor p_229865_, RandomSource p_229866_, int p_229867_, int p_229868_, int p_229869_, Direction p_229870_, int p_229871_) {
            BoundingBox $$7 = BoundingBox.m_71031_(p_229867_, p_229868_, p_229869_, -1, -7, 0, 5, 11, 8, p_229870_);
            if (!StraightStairsDown.m_229888_($$7) || p_229865_.m_141921_($$7) != null) {
                return null;
            }
            return new StraightStairsDown(p_229871_, p_229866_, $$7, p_229870_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_229853_, StructureManager p_229854_, ChunkGenerator p_229855_, RandomSource p_229856_, BoundingBox p_229857_, ChunkPos p_229858_, BlockPos p_229859_) {
            this.m_226776_(p_229853_, p_229857_, 0, 0, 0, 4, 10, 7, true, p_229856_, f_229413_);
            this.m_229880_(p_229853_, p_229856_, p_229857_, this.f_229872_, 1, 7, 0);
            this.m_229880_(p_229853_, p_229856_, p_229857_, StrongholdPiece.SmallDoorType.OPENING, 1, 1, 7);
            BlockState $$7 = (BlockState)Blocks.f_50157_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.SOUTH);
            for (int $$8 = 0; $$8 < 6; ++$$8) {
                this.m_73434_(p_229853_, $$7, 1, 6 - $$8, 1 + $$8, p_229857_);
                this.m_73434_(p_229853_, $$7, 2, 6 - $$8, 1 + $$8, p_229857_);
                this.m_73434_(p_229853_, $$7, 3, 6 - $$8, 1 + $$8, p_229857_);
                if ($$8 >= 5) continue;
                this.m_73434_(p_229853_, Blocks.f_50222_.m_49966_(), 1, 5 - $$8, 1 + $$8, p_229857_);
                this.m_73434_(p_229853_, Blocks.f_50222_.m_49966_(), 2, 5 - $$8, 1 + $$8, p_229857_);
                this.m_73434_(p_229853_, Blocks.f_50222_.m_49966_(), 3, 5 - $$8, 1 + $$8, p_229857_);
            }
        }
    }

    public static class StairsDown
    extends StrongholdPiece {
        private static final int f_229754_ = 5;
        private static final int f_229755_ = 11;
        private static final int f_229756_ = 5;
        private final boolean f_229757_;

        public StairsDown(StructurePieceType p_229764_, int p_229765_, int p_229766_, int p_229767_, Direction p_229768_) {
            super(p_229764_, p_229765_, StairsDown.m_163541_(p_229766_, 64, p_229767_, p_229768_, 5, 11, 5));
            this.f_229757_ = true;
            this.m_73519_(p_229768_);
            this.f_229872_ = StrongholdPiece.SmallDoorType.OPENING;
        }

        public StairsDown(int p_229759_, RandomSource p_229760_, BoundingBox p_229761_, Direction p_229762_) {
            super(StructurePieceType.f_210097_, p_229759_, p_229761_);
            this.f_229757_ = false;
            this.m_73519_(p_229762_);
            this.f_229872_ = this.m_229899_(p_229760_);
        }

        public StairsDown(StructurePieceType p_229770_, CompoundTag p_229771_) {
            super(p_229770_, p_229771_);
            this.f_229757_ = p_229771_.m_128471_("Source");
        }

        public StairsDown(CompoundTag p_229773_) {
            this(StructurePieceType.f_210097_, p_229773_);
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_229795_, CompoundTag p_229796_) {
            super.m_183620_(p_229795_, p_229796_);
            p_229796_.m_128379_("Source", this.f_229757_);
        }

        @Override
        public void m_214092_(StructurePiece p_229783_, StructurePieceAccessor p_229784_, RandomSource p_229785_) {
            if (this.f_229757_) {
                f_229411_ = FiveCrossing.class;
            }
            this.m_229893_((StartPiece)p_229783_, p_229784_, p_229785_, 1, 1);
        }

        public static StairsDown m_229786_(StructurePieceAccessor p_229787_, RandomSource p_229788_, int p_229789_, int p_229790_, int p_229791_, Direction p_229792_, int p_229793_) {
            BoundingBox $$7 = BoundingBox.m_71031_(p_229789_, p_229790_, p_229791_, -1, -7, 0, 5, 11, 5, p_229792_);
            if (!StairsDown.m_229888_($$7) || p_229787_.m_141921_($$7) != null) {
                return null;
            }
            return new StairsDown(p_229793_, p_229788_, $$7, p_229792_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_229775_, StructureManager p_229776_, ChunkGenerator p_229777_, RandomSource p_229778_, BoundingBox p_229779_, ChunkPos p_229780_, BlockPos p_229781_) {
            this.m_226776_(p_229775_, p_229779_, 0, 0, 0, 4, 10, 4, true, p_229778_, f_229413_);
            this.m_229880_(p_229775_, p_229778_, p_229779_, this.f_229872_, 1, 7, 0);
            this.m_229880_(p_229775_, p_229778_, p_229779_, StrongholdPiece.SmallDoorType.OPENING, 1, 1, 4);
            this.m_73434_(p_229775_, Blocks.f_50222_.m_49966_(), 2, 6, 1, p_229779_);
            this.m_73434_(p_229775_, Blocks.f_50222_.m_49966_(), 1, 5, 1, p_229779_);
            this.m_73434_(p_229775_, Blocks.f_50405_.m_49966_(), 1, 6, 1, p_229779_);
            this.m_73434_(p_229775_, Blocks.f_50222_.m_49966_(), 1, 5, 2, p_229779_);
            this.m_73434_(p_229775_, Blocks.f_50222_.m_49966_(), 1, 4, 3, p_229779_);
            this.m_73434_(p_229775_, Blocks.f_50405_.m_49966_(), 1, 5, 3, p_229779_);
            this.m_73434_(p_229775_, Blocks.f_50222_.m_49966_(), 2, 4, 3, p_229779_);
            this.m_73434_(p_229775_, Blocks.f_50222_.m_49966_(), 3, 3, 3, p_229779_);
            this.m_73434_(p_229775_, Blocks.f_50405_.m_49966_(), 3, 4, 3, p_229779_);
            this.m_73434_(p_229775_, Blocks.f_50222_.m_49966_(), 3, 3, 2, p_229779_);
            this.m_73434_(p_229775_, Blocks.f_50222_.m_49966_(), 3, 2, 1, p_229779_);
            this.m_73434_(p_229775_, Blocks.f_50405_.m_49966_(), 3, 3, 1, p_229779_);
            this.m_73434_(p_229775_, Blocks.f_50222_.m_49966_(), 2, 2, 1, p_229779_);
            this.m_73434_(p_229775_, Blocks.f_50222_.m_49966_(), 1, 1, 1, p_229779_);
            this.m_73434_(p_229775_, Blocks.f_50405_.m_49966_(), 1, 2, 1, p_229779_);
            this.m_73434_(p_229775_, Blocks.f_50222_.m_49966_(), 1, 1, 2, p_229779_);
            this.m_73434_(p_229775_, Blocks.f_50405_.m_49966_(), 1, 1, 3, p_229779_);
        }
    }

    public static class FiveCrossing
    extends StrongholdPiece {
        protected static final int f_229519_ = 10;
        protected static final int f_229520_ = 9;
        protected static final int f_229521_ = 11;
        private final boolean f_229522_;
        private final boolean f_229523_;
        private final boolean f_229524_;
        private final boolean f_229525_;

        public FiveCrossing(int p_229527_, RandomSource p_229528_, BoundingBox p_229529_, Direction p_229530_) {
            super(StructurePieceType.f_210146_, p_229527_, p_229529_);
            this.m_73519_(p_229530_);
            this.f_229872_ = this.m_229899_(p_229528_);
            this.f_229522_ = p_229528_.m_188499_();
            this.f_229523_ = p_229528_.m_188499_();
            this.f_229524_ = p_229528_.m_188499_();
            this.f_229525_ = p_229528_.m_188503_(3) > 0;
        }

        public FiveCrossing(CompoundTag p_229532_) {
            super(StructurePieceType.f_210146_, p_229532_);
            this.f_229522_ = p_229532_.m_128471_("leftLow");
            this.f_229523_ = p_229532_.m_128471_("leftHigh");
            this.f_229524_ = p_229532_.m_128471_("rightLow");
            this.f_229525_ = p_229532_.m_128471_("rightHigh");
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_229554_, CompoundTag p_229555_) {
            super.m_183620_(p_229554_, p_229555_);
            p_229555_.m_128379_("leftLow", this.f_229522_);
            p_229555_.m_128379_("leftHigh", this.f_229523_);
            p_229555_.m_128379_("rightLow", this.f_229524_);
            p_229555_.m_128379_("rightHigh", this.f_229525_);
        }

        @Override
        public void m_214092_(StructurePiece p_229542_, StructurePieceAccessor p_229543_, RandomSource p_229544_) {
            int $$3 = 3;
            int $$4 = 5;
            Direction $$5 = this.m_73549_();
            if ($$5 == Direction.WEST || $$5 == Direction.NORTH) {
                $$3 = 8 - $$3;
                $$4 = 8 - $$4;
            }
            this.m_229893_((StartPiece)p_229542_, p_229543_, p_229544_, 5, 1);
            if (this.f_229522_) {
                this.m_229901_((StartPiece)p_229542_, p_229543_, p_229544_, $$3, 1);
            }
            if (this.f_229523_) {
                this.m_229901_((StartPiece)p_229542_, p_229543_, p_229544_, $$4, 7);
            }
            if (this.f_229524_) {
                this.m_229907_((StartPiece)p_229542_, p_229543_, p_229544_, $$3, 1);
            }
            if (this.f_229525_) {
                this.m_229907_((StartPiece)p_229542_, p_229543_, p_229544_, $$4, 7);
            }
        }

        public static FiveCrossing m_229545_(StructurePieceAccessor p_229546_, RandomSource p_229547_, int p_229548_, int p_229549_, int p_229550_, Direction p_229551_, int p_229552_) {
            BoundingBox $$7 = BoundingBox.m_71031_(p_229548_, p_229549_, p_229550_, -4, -3, 0, 10, 9, 11, p_229551_);
            if (!FiveCrossing.m_229888_($$7) || p_229546_.m_141921_($$7) != null) {
                return null;
            }
            return new FiveCrossing(p_229552_, p_229547_, $$7, p_229551_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_229534_, StructureManager p_229535_, ChunkGenerator p_229536_, RandomSource p_229537_, BoundingBox p_229538_, ChunkPos p_229539_, BlockPos p_229540_) {
            this.m_226776_(p_229534_, p_229538_, 0, 0, 0, 9, 8, 10, true, p_229537_, f_229413_);
            this.m_229880_(p_229534_, p_229537_, p_229538_, this.f_229872_, 4, 3, 0);
            if (this.f_229522_) {
                this.m_73441_(p_229534_, p_229538_, 0, 3, 1, 0, 5, 3, f_73382_, f_73382_, false);
            }
            if (this.f_229524_) {
                this.m_73441_(p_229534_, p_229538_, 9, 3, 1, 9, 5, 3, f_73382_, f_73382_, false);
            }
            if (this.f_229523_) {
                this.m_73441_(p_229534_, p_229538_, 0, 5, 7, 0, 7, 9, f_73382_, f_73382_, false);
            }
            if (this.f_229525_) {
                this.m_73441_(p_229534_, p_229538_, 9, 5, 7, 9, 7, 9, f_73382_, f_73382_, false);
            }
            this.m_73441_(p_229534_, p_229538_, 5, 1, 10, 7, 3, 10, f_73382_, f_73382_, false);
            this.m_226776_(p_229534_, p_229538_, 1, 2, 1, 8, 2, 6, false, p_229537_, f_229413_);
            this.m_226776_(p_229534_, p_229538_, 4, 1, 5, 4, 4, 9, false, p_229537_, f_229413_);
            this.m_226776_(p_229534_, p_229538_, 8, 1, 5, 8, 4, 9, false, p_229537_, f_229413_);
            this.m_226776_(p_229534_, p_229538_, 1, 4, 7, 3, 4, 9, false, p_229537_, f_229413_);
            this.m_226776_(p_229534_, p_229538_, 1, 3, 5, 3, 3, 6, false, p_229537_, f_229413_);
            this.m_73441_(p_229534_, p_229538_, 1, 3, 4, 3, 3, 4, Blocks.f_50405_.m_49966_(), Blocks.f_50405_.m_49966_(), false);
            this.m_73441_(p_229534_, p_229538_, 1, 4, 6, 3, 4, 6, Blocks.f_50405_.m_49966_(), Blocks.f_50405_.m_49966_(), false);
            this.m_226776_(p_229534_, p_229538_, 5, 1, 7, 7, 1, 8, false, p_229537_, f_229413_);
            this.m_73441_(p_229534_, p_229538_, 5, 1, 9, 7, 1, 9, Blocks.f_50405_.m_49966_(), Blocks.f_50405_.m_49966_(), false);
            this.m_73441_(p_229534_, p_229538_, 5, 2, 7, 7, 2, 7, Blocks.f_50405_.m_49966_(), Blocks.f_50405_.m_49966_(), false);
            this.m_73441_(p_229534_, p_229538_, 4, 5, 7, 4, 5, 9, Blocks.f_50405_.m_49966_(), Blocks.f_50405_.m_49966_(), false);
            this.m_73441_(p_229534_, p_229538_, 8, 5, 7, 8, 5, 9, Blocks.f_50405_.m_49966_(), Blocks.f_50405_.m_49966_(), false);
            this.m_73441_(p_229534_, p_229538_, 5, 5, 7, 7, 5, 9, (BlockState)Blocks.f_50405_.m_49966_().m_61124_(SlabBlock.f_56353_, SlabType.DOUBLE), (BlockState)Blocks.f_50405_.m_49966_().m_61124_(SlabBlock.f_56353_, SlabType.DOUBLE), false);
            this.m_73434_(p_229534_, (BlockState)Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.SOUTH), 6, 5, 6, p_229538_);
        }
    }

    public static class ChestCorridor
    extends StrongholdPiece {
        private static final int f_229460_ = 5;
        private static final int f_229461_ = 5;
        private static final int f_229462_ = 7;
        private boolean f_229463_;

        public ChestCorridor(int p_229465_, RandomSource p_229466_, BoundingBox p_229467_, Direction p_229468_) {
            super(StructurePieceType.f_210144_, p_229465_, p_229467_);
            this.m_73519_(p_229468_);
            this.f_229872_ = this.m_229899_(p_229466_);
        }

        public ChestCorridor(CompoundTag p_229470_) {
            super(StructurePieceType.f_210144_, p_229470_);
            this.f_229463_ = p_229470_.m_128471_("Chest");
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_229492_, CompoundTag p_229493_) {
            super.m_183620_(p_229492_, p_229493_);
            p_229493_.m_128379_("Chest", this.f_229463_);
        }

        @Override
        public void m_214092_(StructurePiece p_229480_, StructurePieceAccessor p_229481_, RandomSource p_229482_) {
            this.m_229893_((StartPiece)p_229480_, p_229481_, p_229482_, 1, 1);
        }

        public static ChestCorridor m_229483_(StructurePieceAccessor p_229484_, RandomSource p_229485_, int p_229486_, int p_229487_, int p_229488_, Direction p_229489_, int p_229490_) {
            BoundingBox $$7 = BoundingBox.m_71031_(p_229486_, p_229487_, p_229488_, -1, -1, 0, 5, 5, 7, p_229489_);
            if (!ChestCorridor.m_229888_($$7) || p_229484_.m_141921_($$7) != null) {
                return null;
            }
            return new ChestCorridor(p_229490_, p_229485_, $$7, p_229489_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_229472_, StructureManager p_229473_, ChunkGenerator p_229474_, RandomSource p_229475_, BoundingBox p_229476_, ChunkPos p_229477_, BlockPos p_229478_) {
            this.m_226776_(p_229472_, p_229476_, 0, 0, 0, 4, 4, 6, true, p_229475_, f_229413_);
            this.m_229880_(p_229472_, p_229475_, p_229476_, this.f_229872_, 1, 1, 0);
            this.m_229880_(p_229472_, p_229475_, p_229476_, StrongholdPiece.SmallDoorType.OPENING, 1, 1, 6);
            this.m_73441_(p_229472_, p_229476_, 3, 1, 2, 3, 1, 4, Blocks.f_50222_.m_49966_(), Blocks.f_50222_.m_49966_(), false);
            this.m_73434_(p_229472_, Blocks.f_50411_.m_49966_(), 3, 1, 1, p_229476_);
            this.m_73434_(p_229472_, Blocks.f_50411_.m_49966_(), 3, 1, 5, p_229476_);
            this.m_73434_(p_229472_, Blocks.f_50411_.m_49966_(), 3, 2, 2, p_229476_);
            this.m_73434_(p_229472_, Blocks.f_50411_.m_49966_(), 3, 2, 4, p_229476_);
            for (int $$7 = 2; $$7 <= 4; ++$$7) {
                this.m_73434_(p_229472_, Blocks.f_50411_.m_49966_(), 2, 1, $$7, p_229476_);
            }
            if (!this.f_229463_ && p_229476_.m_71051_(this.m_163582_(3, 2, 3))) {
                this.f_229463_ = true;
                this.m_213787_(p_229472_, p_229476_, p_229475_, 3, 2, 3, BuiltInLootTables.f_78763_);
            }
        }
    }

    public static class Library
    extends StrongholdPiece {
        protected static final int f_229583_ = 14;
        protected static final int f_229584_ = 6;
        protected static final int f_229585_ = 11;
        protected static final int f_229586_ = 15;
        private final boolean f_229587_;

        public Library(int p_229589_, RandomSource p_229590_, BoundingBox p_229591_, Direction p_229592_) {
            super(StructurePieceType.f_210148_, p_229589_, p_229591_);
            this.m_73519_(p_229592_);
            this.f_229872_ = this.m_229899_(p_229590_);
            this.f_229587_ = p_229591_.m_71057_() > 6;
        }

        public Library(CompoundTag p_229594_) {
            super(StructurePieceType.f_210148_, p_229594_);
            this.f_229587_ = p_229594_.m_128471_("Tall");
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_229612_, CompoundTag p_229613_) {
            super.m_183620_(p_229612_, p_229613_);
            p_229613_.m_128379_("Tall", this.f_229587_);
        }

        public static Library m_229603_(StructurePieceAccessor p_229604_, RandomSource p_229605_, int p_229606_, int p_229607_, int p_229608_, Direction p_229609_, int p_229610_) {
            BoundingBox $$7 = BoundingBox.m_71031_(p_229606_, p_229607_, p_229608_, -4, -1, 0, 14, 11, 15, p_229609_);
            if (!(Library.m_229888_($$7) && p_229604_.m_141921_($$7) == null || Library.m_229888_($$7 = BoundingBox.m_71031_(p_229606_, p_229607_, p_229608_, -4, -1, 0, 14, 6, 15, p_229609_)) && p_229604_.m_141921_($$7) == null)) {
                return null;
            }
            return new Library(p_229610_, p_229605_, $$7, p_229609_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_229596_, StructureManager p_229597_, ChunkGenerator p_229598_, RandomSource p_229599_, BoundingBox p_229600_, ChunkPos p_229601_, BlockPos p_229602_) {
            int $$7 = 11;
            if (!this.f_229587_) {
                $$7 = 6;
            }
            this.m_226776_(p_229596_, p_229600_, 0, 0, 0, 13, $$7 - 1, 14, true, p_229599_, f_229413_);
            this.m_229880_(p_229596_, p_229599_, p_229600_, this.f_229872_, 4, 1, 0);
            this.m_226788_(p_229596_, p_229600_, p_229599_, 0.07f, 2, 1, 1, 11, 4, 13, Blocks.f_50033_.m_49966_(), Blocks.f_50033_.m_49966_(), false, false);
            boolean $$8 = true;
            int $$9 = 12;
            for (int $$10 = 1; $$10 <= 13; ++$$10) {
                if (($$10 - 1) % 4 == 0) {
                    this.m_73441_(p_229596_, p_229600_, 1, 1, $$10, 1, 4, $$10, Blocks.f_50705_.m_49966_(), Blocks.f_50705_.m_49966_(), false);
                    this.m_73441_(p_229596_, p_229600_, 12, 1, $$10, 12, 4, $$10, Blocks.f_50705_.m_49966_(), Blocks.f_50705_.m_49966_(), false);
                    this.m_73434_(p_229596_, (BlockState)Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.EAST), 2, 3, $$10, p_229600_);
                    this.m_73434_(p_229596_, (BlockState)Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.WEST), 11, 3, $$10, p_229600_);
                    if (!this.f_229587_) continue;
                    this.m_73441_(p_229596_, p_229600_, 1, 6, $$10, 1, 9, $$10, Blocks.f_50705_.m_49966_(), Blocks.f_50705_.m_49966_(), false);
                    this.m_73441_(p_229596_, p_229600_, 12, 6, $$10, 12, 9, $$10, Blocks.f_50705_.m_49966_(), Blocks.f_50705_.m_49966_(), false);
                    continue;
                }
                this.m_73441_(p_229596_, p_229600_, 1, 1, $$10, 1, 4, $$10, Blocks.f_50078_.m_49966_(), Blocks.f_50078_.m_49966_(), false);
                this.m_73441_(p_229596_, p_229600_, 12, 1, $$10, 12, 4, $$10, Blocks.f_50078_.m_49966_(), Blocks.f_50078_.m_49966_(), false);
                if (!this.f_229587_) continue;
                this.m_73441_(p_229596_, p_229600_, 1, 6, $$10, 1, 9, $$10, Blocks.f_50078_.m_49966_(), Blocks.f_50078_.m_49966_(), false);
                this.m_73441_(p_229596_, p_229600_, 12, 6, $$10, 12, 9, $$10, Blocks.f_50078_.m_49966_(), Blocks.f_50078_.m_49966_(), false);
            }
            for (int $$11 = 3; $$11 < 12; $$11 += 2) {
                this.m_73441_(p_229596_, p_229600_, 3, 1, $$11, 4, 3, $$11, Blocks.f_50078_.m_49966_(), Blocks.f_50078_.m_49966_(), false);
                this.m_73441_(p_229596_, p_229600_, 6, 1, $$11, 7, 3, $$11, Blocks.f_50078_.m_49966_(), Blocks.f_50078_.m_49966_(), false);
                this.m_73441_(p_229596_, p_229600_, 9, 1, $$11, 10, 3, $$11, Blocks.f_50078_.m_49966_(), Blocks.f_50078_.m_49966_(), false);
            }
            if (this.f_229587_) {
                this.m_73441_(p_229596_, p_229600_, 1, 5, 1, 3, 5, 13, Blocks.f_50705_.m_49966_(), Blocks.f_50705_.m_49966_(), false);
                this.m_73441_(p_229596_, p_229600_, 10, 5, 1, 12, 5, 13, Blocks.f_50705_.m_49966_(), Blocks.f_50705_.m_49966_(), false);
                this.m_73441_(p_229596_, p_229600_, 4, 5, 1, 9, 5, 2, Blocks.f_50705_.m_49966_(), Blocks.f_50705_.m_49966_(), false);
                this.m_73441_(p_229596_, p_229600_, 4, 5, 12, 9, 5, 13, Blocks.f_50705_.m_49966_(), Blocks.f_50705_.m_49966_(), false);
                this.m_73434_(p_229596_, Blocks.f_50705_.m_49966_(), 9, 5, 11, p_229600_);
                this.m_73434_(p_229596_, Blocks.f_50705_.m_49966_(), 8, 5, 11, p_229600_);
                this.m_73434_(p_229596_, Blocks.f_50705_.m_49966_(), 9, 5, 10, p_229600_);
                BlockState $$12 = (BlockState)((BlockState)Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52312_, true)).m_61124_(FenceBlock.f_52310_, true);
                BlockState $$13 = (BlockState)((BlockState)Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52311_, true);
                this.m_73441_(p_229596_, p_229600_, 3, 6, 3, 3, 6, 11, $$13, $$13, false);
                this.m_73441_(p_229596_, p_229600_, 10, 6, 3, 10, 6, 9, $$13, $$13, false);
                this.m_73441_(p_229596_, p_229600_, 4, 6, 2, 9, 6, 2, $$12, $$12, false);
                this.m_73441_(p_229596_, p_229600_, 4, 6, 12, 7, 6, 12, $$12, $$12, false);
                this.m_73434_(p_229596_, (BlockState)((BlockState)Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52310_, true), 3, 6, 2, p_229600_);
                this.m_73434_(p_229596_, (BlockState)((BlockState)Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52311_, true)).m_61124_(FenceBlock.f_52310_, true), 3, 6, 12, p_229600_);
                this.m_73434_(p_229596_, (BlockState)((BlockState)Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52312_, true), 10, 6, 2, p_229600_);
                for (int $$14 = 0; $$14 <= 2; ++$$14) {
                    this.m_73434_(p_229596_, (BlockState)((BlockState)Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52311_, true)).m_61124_(FenceBlock.f_52312_, true), 8 + $$14, 6, 12 - $$14, p_229600_);
                    if ($$14 == 2) continue;
                    this.m_73434_(p_229596_, (BlockState)((BlockState)Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52309_, true)).m_61124_(FenceBlock.f_52310_, true), 8 + $$14, 6, 11 - $$14, p_229600_);
                }
                BlockState $$15 = (BlockState)Blocks.f_50155_.m_49966_().m_61124_(LadderBlock.f_54337_, Direction.SOUTH);
                this.m_73434_(p_229596_, $$15, 10, 1, 13, p_229600_);
                this.m_73434_(p_229596_, $$15, 10, 2, 13, p_229600_);
                this.m_73434_(p_229596_, $$15, 10, 3, 13, p_229600_);
                this.m_73434_(p_229596_, $$15, 10, 4, 13, p_229600_);
                this.m_73434_(p_229596_, $$15, 10, 5, 13, p_229600_);
                this.m_73434_(p_229596_, $$15, 10, 6, 13, p_229600_);
                this.m_73434_(p_229596_, $$15, 10, 7, 13, p_229600_);
                int $$16 = 7;
                int $$17 = 7;
                BlockState $$18 = (BlockState)Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52310_, true);
                this.m_73434_(p_229596_, $$18, 6, 9, 7, p_229600_);
                BlockState $$19 = (BlockState)Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52312_, true);
                this.m_73434_(p_229596_, $$19, 7, 9, 7, p_229600_);
                this.m_73434_(p_229596_, $$18, 6, 8, 7, p_229600_);
                this.m_73434_(p_229596_, $$19, 7, 8, 7, p_229600_);
                BlockState $$20 = (BlockState)((BlockState)$$13.m_61124_(FenceBlock.f_52312_, true)).m_61124_(FenceBlock.f_52310_, true);
                this.m_73434_(p_229596_, $$20, 6, 7, 7, p_229600_);
                this.m_73434_(p_229596_, $$20, 7, 7, 7, p_229600_);
                this.m_73434_(p_229596_, $$18, 5, 7, 7, p_229600_);
                this.m_73434_(p_229596_, $$19, 8, 7, 7, p_229600_);
                this.m_73434_(p_229596_, (BlockState)$$18.m_61124_(FenceBlock.f_52309_, true), 6, 7, 6, p_229600_);
                this.m_73434_(p_229596_, (BlockState)$$18.m_61124_(FenceBlock.f_52311_, true), 6, 7, 8, p_229600_);
                this.m_73434_(p_229596_, (BlockState)$$19.m_61124_(FenceBlock.f_52309_, true), 7, 7, 6, p_229600_);
                this.m_73434_(p_229596_, (BlockState)$$19.m_61124_(FenceBlock.f_52311_, true), 7, 7, 8, p_229600_);
                BlockState $$21 = Blocks.f_50081_.m_49966_();
                this.m_73434_(p_229596_, $$21, 5, 8, 7, p_229600_);
                this.m_73434_(p_229596_, $$21, 8, 8, 7, p_229600_);
                this.m_73434_(p_229596_, $$21, 6, 8, 6, p_229600_);
                this.m_73434_(p_229596_, $$21, 6, 8, 8, p_229600_);
                this.m_73434_(p_229596_, $$21, 7, 8, 6, p_229600_);
                this.m_73434_(p_229596_, $$21, 7, 8, 8, p_229600_);
            }
            this.m_213787_(p_229596_, p_229600_, p_229599_, 3, 3, 5, BuiltInLootTables.f_78761_);
            if (this.f_229587_) {
                this.m_73434_(p_229596_, f_73382_, 12, 9, 1, p_229600_);
                this.m_213787_(p_229596_, p_229600_, p_229599_, 12, 8, 1, BuiltInLootTables.f_78761_);
            }
        }
    }

    public static class PortalRoom
    extends StrongholdPiece {
        protected static final int f_229624_ = 11;
        protected static final int f_229625_ = 8;
        protected static final int f_229626_ = 16;
        private boolean f_229627_;

        public PortalRoom(int p_229629_, BoundingBox p_229630_, Direction p_229631_) {
            super(StructurePieceType.f_210149_, p_229629_, p_229630_);
            this.m_73519_(p_229631_);
        }

        public PortalRoom(CompoundTag p_229633_) {
            super(StructurePieceType.f_210149_, p_229633_);
            this.f_229627_ = p_229633_.m_128471_("Mob");
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_229654_, CompoundTag p_229655_) {
            super.m_183620_(p_229654_, p_229655_);
            p_229655_.m_128379_("Mob", this.f_229627_);
        }

        @Override
        public void m_214092_(StructurePiece p_229643_, StructurePieceAccessor p_229644_, RandomSource p_229645_) {
            if (p_229643_ != null) {
                ((StartPiece)p_229643_).f_229798_ = this;
            }
        }

        public static PortalRoom m_229646_(StructurePieceAccessor p_229647_, int p_229648_, int p_229649_, int p_229650_, Direction p_229651_, int p_229652_) {
            BoundingBox $$6 = BoundingBox.m_71031_(p_229648_, p_229649_, p_229650_, -4, -1, 0, 11, 8, 16, p_229651_);
            if (!PortalRoom.m_229888_($$6) || p_229647_.m_141921_($$6) != null) {
                return null;
            }
            return new PortalRoom(p_229652_, $$6, p_229651_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_229635_, StructureManager p_229636_, ChunkGenerator p_229637_, RandomSource p_229638_, BoundingBox p_229639_, ChunkPos p_229640_, BlockPos p_229641_) {
            BlockPos.MutableBlockPos $$22;
            this.m_226776_(p_229635_, p_229639_, 0, 0, 0, 10, 7, 15, false, p_229638_, f_229413_);
            this.m_229880_(p_229635_, p_229638_, p_229639_, StrongholdPiece.SmallDoorType.GRATES, 4, 1, 0);
            int $$7 = 6;
            this.m_226776_(p_229635_, p_229639_, 1, $$7, 1, 1, $$7, 14, false, p_229638_, f_229413_);
            this.m_226776_(p_229635_, p_229639_, 9, $$7, 1, 9, $$7, 14, false, p_229638_, f_229413_);
            this.m_226776_(p_229635_, p_229639_, 2, $$7, 1, 8, $$7, 2, false, p_229638_, f_229413_);
            this.m_226776_(p_229635_, p_229639_, 2, $$7, 14, 8, $$7, 14, false, p_229638_, f_229413_);
            this.m_226776_(p_229635_, p_229639_, 1, 1, 1, 2, 1, 4, false, p_229638_, f_229413_);
            this.m_226776_(p_229635_, p_229639_, 8, 1, 1, 9, 1, 4, false, p_229638_, f_229413_);
            this.m_73441_(p_229635_, p_229639_, 1, 1, 1, 1, 1, 3, Blocks.f_49991_.m_49966_(), Blocks.f_49991_.m_49966_(), false);
            this.m_73441_(p_229635_, p_229639_, 9, 1, 1, 9, 1, 3, Blocks.f_49991_.m_49966_(), Blocks.f_49991_.m_49966_(), false);
            this.m_226776_(p_229635_, p_229639_, 3, 1, 8, 7, 1, 12, false, p_229638_, f_229413_);
            this.m_73441_(p_229635_, p_229639_, 4, 1, 9, 6, 1, 11, Blocks.f_49991_.m_49966_(), Blocks.f_49991_.m_49966_(), false);
            BlockState $$8 = (BlockState)((BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52309_, true)).m_61124_(IronBarsBlock.f_52311_, true);
            BlockState $$9 = (BlockState)((BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52312_, true)).m_61124_(IronBarsBlock.f_52310_, true);
            for (int $$10 = 3; $$10 < 14; $$10 += 2) {
                this.m_73441_(p_229635_, p_229639_, 0, 3, $$10, 0, 4, $$10, $$8, $$8, false);
                this.m_73441_(p_229635_, p_229639_, 10, 3, $$10, 10, 4, $$10, $$8, $$8, false);
            }
            for (int $$11 = 2; $$11 < 9; $$11 += 2) {
                this.m_73441_(p_229635_, p_229639_, $$11, 3, 15, $$11, 4, 15, $$9, $$9, false);
            }
            BlockState $$12 = (BlockState)Blocks.f_50194_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.NORTH);
            this.m_226776_(p_229635_, p_229639_, 4, 1, 5, 6, 1, 7, false, p_229638_, f_229413_);
            this.m_226776_(p_229635_, p_229639_, 4, 2, 6, 6, 2, 7, false, p_229638_, f_229413_);
            this.m_226776_(p_229635_, p_229639_, 4, 3, 7, 6, 3, 7, false, p_229638_, f_229413_);
            for (int $$13 = 4; $$13 <= 6; ++$$13) {
                this.m_73434_(p_229635_, $$12, $$13, 1, 4, p_229639_);
                this.m_73434_(p_229635_, $$12, $$13, 2, 5, p_229639_);
                this.m_73434_(p_229635_, $$12, $$13, 3, 6, p_229639_);
            }
            BlockState $$14 = (BlockState)Blocks.f_50258_.m_49966_().m_61124_(EndPortalFrameBlock.f_53042_, Direction.NORTH);
            BlockState $$15 = (BlockState)Blocks.f_50258_.m_49966_().m_61124_(EndPortalFrameBlock.f_53042_, Direction.SOUTH);
            BlockState $$16 = (BlockState)Blocks.f_50258_.m_49966_().m_61124_(EndPortalFrameBlock.f_53042_, Direction.EAST);
            BlockState $$17 = (BlockState)Blocks.f_50258_.m_49966_().m_61124_(EndPortalFrameBlock.f_53042_, Direction.WEST);
            boolean $$18 = true;
            boolean[] $$19 = new boolean[12];
            for (int $$20 = 0; $$20 < $$19.length; ++$$20) {
                $$19[$$20] = p_229638_.m_188501_() > 0.9f;
                $$18 &= $$19[$$20];
            }
            this.m_73434_(p_229635_, (BlockState)$$14.m_61124_(EndPortalFrameBlock.f_53043_, $$19[0]), 4, 3, 8, p_229639_);
            this.m_73434_(p_229635_, (BlockState)$$14.m_61124_(EndPortalFrameBlock.f_53043_, $$19[1]), 5, 3, 8, p_229639_);
            this.m_73434_(p_229635_, (BlockState)$$14.m_61124_(EndPortalFrameBlock.f_53043_, $$19[2]), 6, 3, 8, p_229639_);
            this.m_73434_(p_229635_, (BlockState)$$15.m_61124_(EndPortalFrameBlock.f_53043_, $$19[3]), 4, 3, 12, p_229639_);
            this.m_73434_(p_229635_, (BlockState)$$15.m_61124_(EndPortalFrameBlock.f_53043_, $$19[4]), 5, 3, 12, p_229639_);
            this.m_73434_(p_229635_, (BlockState)$$15.m_61124_(EndPortalFrameBlock.f_53043_, $$19[5]), 6, 3, 12, p_229639_);
            this.m_73434_(p_229635_, (BlockState)$$16.m_61124_(EndPortalFrameBlock.f_53043_, $$19[6]), 3, 3, 9, p_229639_);
            this.m_73434_(p_229635_, (BlockState)$$16.m_61124_(EndPortalFrameBlock.f_53043_, $$19[7]), 3, 3, 10, p_229639_);
            this.m_73434_(p_229635_, (BlockState)$$16.m_61124_(EndPortalFrameBlock.f_53043_, $$19[8]), 3, 3, 11, p_229639_);
            this.m_73434_(p_229635_, (BlockState)$$17.m_61124_(EndPortalFrameBlock.f_53043_, $$19[9]), 7, 3, 9, p_229639_);
            this.m_73434_(p_229635_, (BlockState)$$17.m_61124_(EndPortalFrameBlock.f_53043_, $$19[10]), 7, 3, 10, p_229639_);
            this.m_73434_(p_229635_, (BlockState)$$17.m_61124_(EndPortalFrameBlock.f_53043_, $$19[11]), 7, 3, 11, p_229639_);
            if ($$18) {
                BlockState $$21 = Blocks.f_50257_.m_49966_();
                this.m_73434_(p_229635_, $$21, 4, 3, 9, p_229639_);
                this.m_73434_(p_229635_, $$21, 5, 3, 9, p_229639_);
                this.m_73434_(p_229635_, $$21, 6, 3, 9, p_229639_);
                this.m_73434_(p_229635_, $$21, 4, 3, 10, p_229639_);
                this.m_73434_(p_229635_, $$21, 5, 3, 10, p_229639_);
                this.m_73434_(p_229635_, $$21, 6, 3, 10, p_229639_);
                this.m_73434_(p_229635_, $$21, 4, 3, 11, p_229639_);
                this.m_73434_(p_229635_, $$21, 5, 3, 11, p_229639_);
                this.m_73434_(p_229635_, $$21, 6, 3, 11, p_229639_);
            }
            if (!this.f_229627_ && p_229639_.m_71051_($$22 = this.m_163582_(5, 3, 6))) {
                this.f_229627_ = true;
                p_229635_.m_7731_($$22, Blocks.f_50085_.m_49966_(), 2);
                BlockEntity $$23 = p_229635_.m_7702_($$22);
                if ($$23 instanceof SpawnerBlockEntity) {
                    ((SpawnerBlockEntity)$$23).m_59801_().m_45462_(EntityType.f_20523_);
                }
            }
        }
    }

    static abstract class StrongholdPiece
    extends StructurePiece {
        protected SmallDoorType f_229872_ = SmallDoorType.OPENING;

        protected StrongholdPiece(StructurePieceType p_229874_, int p_229875_, BoundingBox p_229876_) {
            super(p_229874_, p_229875_, p_229876_);
        }

        public StrongholdPiece(StructurePieceType p_229878_, CompoundTag p_229879_) {
            super(p_229878_, p_229879_);
            this.f_229872_ = SmallDoorType.valueOf(p_229879_.m_128461_("EntryDoor"));
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_229891_, CompoundTag p_229892_) {
            p_229892_.m_128359_("EntryDoor", this.f_229872_.name());
        }

        protected void m_229880_(WorldGenLevel p_229881_, RandomSource p_229882_, BoundingBox p_229883_, SmallDoorType p_229884_, int p_229885_, int p_229886_, int p_229887_) {
            switch (p_229884_) {
                case OPENING: {
                    this.m_73441_(p_229881_, p_229883_, p_229885_, p_229886_, p_229887_, p_229885_ + 3 - 1, p_229886_ + 3 - 1, p_229887_, f_73382_, f_73382_, false);
                    break;
                }
                case WOOD_DOOR: {
                    this.m_73434_(p_229881_, Blocks.f_50222_.m_49966_(), p_229885_, p_229886_, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, Blocks.f_50222_.m_49966_(), p_229885_, p_229886_ + 1, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, Blocks.f_50222_.m_49966_(), p_229885_, p_229886_ + 2, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, Blocks.f_50222_.m_49966_(), p_229885_ + 1, p_229886_ + 2, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, Blocks.f_50222_.m_49966_(), p_229885_ + 2, p_229886_ + 2, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, Blocks.f_50222_.m_49966_(), p_229885_ + 2, p_229886_ + 1, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, Blocks.f_50222_.m_49966_(), p_229885_ + 2, p_229886_, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, Blocks.f_50154_.m_49966_(), p_229885_ + 1, p_229886_, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, (BlockState)Blocks.f_50154_.m_49966_().m_61124_(DoorBlock.f_52730_, DoubleBlockHalf.UPPER), p_229885_ + 1, p_229886_ + 1, p_229887_, p_229883_);
                    break;
                }
                case GRATES: {
                    this.m_73434_(p_229881_, Blocks.f_50627_.m_49966_(), p_229885_ + 1, p_229886_, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, Blocks.f_50627_.m_49966_(), p_229885_ + 1, p_229886_ + 1, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, (BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52312_, true), p_229885_, p_229886_, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, (BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52312_, true), p_229885_, p_229886_ + 1, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, (BlockState)((BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52310_, true)).m_61124_(IronBarsBlock.f_52312_, true), p_229885_, p_229886_ + 2, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, (BlockState)((BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52310_, true)).m_61124_(IronBarsBlock.f_52312_, true), p_229885_ + 1, p_229886_ + 2, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, (BlockState)((BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52310_, true)).m_61124_(IronBarsBlock.f_52312_, true), p_229885_ + 2, p_229886_ + 2, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, (BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52310_, true), p_229885_ + 2, p_229886_ + 1, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, (BlockState)Blocks.f_50183_.m_49966_().m_61124_(IronBarsBlock.f_52310_, true), p_229885_ + 2, p_229886_, p_229887_, p_229883_);
                    break;
                }
                case IRON_DOOR: {
                    this.m_73434_(p_229881_, Blocks.f_50222_.m_49966_(), p_229885_, p_229886_, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, Blocks.f_50222_.m_49966_(), p_229885_, p_229886_ + 1, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, Blocks.f_50222_.m_49966_(), p_229885_, p_229886_ + 2, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, Blocks.f_50222_.m_49966_(), p_229885_ + 1, p_229886_ + 2, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, Blocks.f_50222_.m_49966_(), p_229885_ + 2, p_229886_ + 2, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, Blocks.f_50222_.m_49966_(), p_229885_ + 2, p_229886_ + 1, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, Blocks.f_50222_.m_49966_(), p_229885_ + 2, p_229886_, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, Blocks.f_50166_.m_49966_(), p_229885_ + 1, p_229886_, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, (BlockState)Blocks.f_50166_.m_49966_().m_61124_(DoorBlock.f_52730_, DoubleBlockHalf.UPPER), p_229885_ + 1, p_229886_ + 1, p_229887_, p_229883_);
                    this.m_73434_(p_229881_, (BlockState)Blocks.f_50124_.m_49966_().m_61124_(ButtonBlock.f_54117_, Direction.NORTH), p_229885_ + 2, p_229886_ + 1, p_229887_ + 1, p_229883_);
                    this.m_73434_(p_229881_, (BlockState)Blocks.f_50124_.m_49966_().m_61124_(ButtonBlock.f_54117_, Direction.SOUTH), p_229885_ + 2, p_229886_ + 1, p_229887_ - 1, p_229883_);
                }
            }
        }

        protected SmallDoorType m_229899_(RandomSource p_229900_) {
            int $$1 = p_229900_.m_188503_(5);
            switch ($$1) {
                default: {
                    return SmallDoorType.OPENING;
                }
                case 2: {
                    return SmallDoorType.WOOD_DOOR;
                }
                case 3: {
                    return SmallDoorType.GRATES;
                }
                case 4: 
            }
            return SmallDoorType.IRON_DOOR;
        }

        @Nullable
        protected StructurePiece m_229893_(StartPiece p_229894_, StructurePieceAccessor p_229895_, RandomSource p_229896_, int p_229897_, int p_229898_) {
            Direction $$5 = this.m_73549_();
            if ($$5 != null) {
                switch ($$5) {
                    case NORTH: {
                        return StrongholdPieces.m_229436_(p_229894_, p_229895_, p_229896_, this.f_73383_.m_162395_() + p_229897_, this.f_73383_.m_162396_() + p_229898_, this.f_73383_.m_162398_() - 1, $$5, this.m_73548_());
                    }
                    case SOUTH: {
                        return StrongholdPieces.m_229436_(p_229894_, p_229895_, p_229896_, this.f_73383_.m_162395_() + p_229897_, this.f_73383_.m_162396_() + p_229898_, this.f_73383_.m_162401_() + 1, $$5, this.m_73548_());
                    }
                    case WEST: {
                        return StrongholdPieces.m_229436_(p_229894_, p_229895_, p_229896_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() + p_229898_, this.f_73383_.m_162398_() + p_229897_, $$5, this.m_73548_());
                    }
                    case EAST: {
                        return StrongholdPieces.m_229436_(p_229894_, p_229895_, p_229896_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() + p_229898_, this.f_73383_.m_162398_() + p_229897_, $$5, this.m_73548_());
                    }
                }
            }
            return null;
        }

        @Nullable
        protected StructurePiece m_229901_(StartPiece p_229902_, StructurePieceAccessor p_229903_, RandomSource p_229904_, int p_229905_, int p_229906_) {
            Direction $$5 = this.m_73549_();
            if ($$5 != null) {
                switch ($$5) {
                    case NORTH: {
                        return StrongholdPieces.m_229436_(p_229902_, p_229903_, p_229904_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() + p_229905_, this.f_73383_.m_162398_() + p_229906_, Direction.WEST, this.m_73548_());
                    }
                    case SOUTH: {
                        return StrongholdPieces.m_229436_(p_229902_, p_229903_, p_229904_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() + p_229905_, this.f_73383_.m_162398_() + p_229906_, Direction.WEST, this.m_73548_());
                    }
                    case WEST: {
                        return StrongholdPieces.m_229436_(p_229902_, p_229903_, p_229904_, this.f_73383_.m_162395_() + p_229906_, this.f_73383_.m_162396_() + p_229905_, this.f_73383_.m_162398_() - 1, Direction.NORTH, this.m_73548_());
                    }
                    case EAST: {
                        return StrongholdPieces.m_229436_(p_229902_, p_229903_, p_229904_, this.f_73383_.m_162395_() + p_229906_, this.f_73383_.m_162396_() + p_229905_, this.f_73383_.m_162398_() - 1, Direction.NORTH, this.m_73548_());
                    }
                }
            }
            return null;
        }

        @Nullable
        protected StructurePiece m_229907_(StartPiece p_229908_, StructurePieceAccessor p_229909_, RandomSource p_229910_, int p_229911_, int p_229912_) {
            Direction $$5 = this.m_73549_();
            if ($$5 != null) {
                switch ($$5) {
                    case NORTH: {
                        return StrongholdPieces.m_229436_(p_229908_, p_229909_, p_229910_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() + p_229911_, this.f_73383_.m_162398_() + p_229912_, Direction.EAST, this.m_73548_());
                    }
                    case SOUTH: {
                        return StrongholdPieces.m_229436_(p_229908_, p_229909_, p_229910_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() + p_229911_, this.f_73383_.m_162398_() + p_229912_, Direction.EAST, this.m_73548_());
                    }
                    case WEST: {
                        return StrongholdPieces.m_229436_(p_229908_, p_229909_, p_229910_, this.f_73383_.m_162395_() + p_229912_, this.f_73383_.m_162396_() + p_229911_, this.f_73383_.m_162401_() + 1, Direction.SOUTH, this.m_73548_());
                    }
                    case EAST: {
                        return StrongholdPieces.m_229436_(p_229908_, p_229909_, p_229910_, this.f_73383_.m_162395_() + p_229912_, this.f_73383_.m_162396_() + p_229911_, this.f_73383_.m_162401_() + 1, Direction.SOUTH, this.m_73548_());
                    }
                }
            }
            return null;
        }

        protected static boolean m_229888_(BoundingBox p_229889_) {
            return p_229889_ != null && p_229889_.m_162396_() > 10;
        }

        protected static final class SmallDoorType
        extends Enum<SmallDoorType> {
            public static final /* enum */ SmallDoorType OPENING = new SmallDoorType();
            public static final /* enum */ SmallDoorType WOOD_DOOR = new SmallDoorType();
            public static final /* enum */ SmallDoorType GRATES = new SmallDoorType();
            public static final /* enum */ SmallDoorType IRON_DOOR = new SmallDoorType();
            private static final /* synthetic */ SmallDoorType[] $VALUES;

            public static SmallDoorType[] values() {
                return (SmallDoorType[])$VALUES.clone();
            }

            public static SmallDoorType valueOf(String p_229924_) {
                return Enum.valueOf(SmallDoorType.class, p_229924_);
            }

            private static /* synthetic */ SmallDoorType[] m_229922_() {
                return new SmallDoorType[]{OPENING, WOOD_DOOR, GRATES, IRON_DOOR};
            }

            static {
                $VALUES = SmallDoorType.m_229922_();
            }
        }
    }

    public static class StartPiece
    extends StairsDown {
        public PieceWeight f_229797_;
        @Nullable
        public PortalRoom f_229798_;
        public final List<StructurePiece> f_229799_ = Lists.newArrayList();

        public StartPiece(RandomSource p_229801_, int p_229802_, int p_229803_) {
            super(StructurePieceType.f_210098_, 0, p_229802_, p_229803_, StartPiece.m_226760_(p_229801_));
        }

        public StartPiece(CompoundTag p_229805_) {
            super(StructurePieceType.f_210098_, p_229805_);
        }

        @Override
        public BlockPos m_142171_() {
            if (this.f_229798_ != null) {
                return this.f_229798_.m_142171_();
            }
            return super.m_142171_();
        }
    }

    public static class FillerCorridor
    extends StrongholdPiece {
        private final int f_229494_;

        public FillerCorridor(int p_229496_, BoundingBox p_229497_, Direction p_229498_) {
            super(StructurePieceType.f_210145_, p_229496_, p_229497_);
            this.m_73519_(p_229498_);
            this.f_229494_ = p_229498_ == Direction.NORTH || p_229498_ == Direction.SOUTH ? p_229497_.m_71058_() : p_229497_.m_71056_();
        }

        public FillerCorridor(CompoundTag p_229500_) {
            super(StructurePieceType.f_210145_, p_229500_);
            this.f_229494_ = p_229500_.m_128451_("Steps");
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_229517_, CompoundTag p_229518_) {
            super.m_183620_(p_229517_, p_229518_);
            p_229518_.m_128405_("Steps", this.f_229494_);
        }

        public static BoundingBox m_229509_(StructurePieceAccessor p_229510_, RandomSource p_229511_, int p_229512_, int p_229513_, int p_229514_, Direction p_229515_) {
            int $$6 = 3;
            BoundingBox $$7 = BoundingBox.m_71031_(p_229512_, p_229513_, p_229514_, -1, -1, 0, 5, 5, 4, p_229515_);
            StructurePiece $$8 = p_229510_.m_141921_($$7);
            if ($$8 == null) {
                return null;
            }
            if ($$8.m_73547_().m_162396_() == $$7.m_162396_()) {
                for (int $$9 = 2; $$9 >= 1; --$$9) {
                    $$7 = BoundingBox.m_71031_(p_229512_, p_229513_, p_229514_, -1, -1, 0, 5, 5, $$9, p_229515_);
                    if ($$8.m_73547_().m_71049_($$7)) continue;
                    return BoundingBox.m_71031_(p_229512_, p_229513_, p_229514_, -1, -1, 0, 5, 5, $$9 + 1, p_229515_);
                }
            }
            return null;
        }

        @Override
        public void m_213694_(WorldGenLevel p_229502_, StructureManager p_229503_, ChunkGenerator p_229504_, RandomSource p_229505_, BoundingBox p_229506_, ChunkPos p_229507_, BlockPos p_229508_) {
            for (int $$7 = 0; $$7 < this.f_229494_; ++$$7) {
                this.m_73434_(p_229502_, Blocks.f_50222_.m_49966_(), 0, 0, $$7, p_229506_);
                this.m_73434_(p_229502_, Blocks.f_50222_.m_49966_(), 1, 0, $$7, p_229506_);
                this.m_73434_(p_229502_, Blocks.f_50222_.m_49966_(), 2, 0, $$7, p_229506_);
                this.m_73434_(p_229502_, Blocks.f_50222_.m_49966_(), 3, 0, $$7, p_229506_);
                this.m_73434_(p_229502_, Blocks.f_50222_.m_49966_(), 4, 0, $$7, p_229506_);
                for (int $$8 = 1; $$8 <= 3; ++$$8) {
                    this.m_73434_(p_229502_, Blocks.f_50222_.m_49966_(), 0, $$8, $$7, p_229506_);
                    this.m_73434_(p_229502_, Blocks.f_50627_.m_49966_(), 1, $$8, $$7, p_229506_);
                    this.m_73434_(p_229502_, Blocks.f_50627_.m_49966_(), 2, $$8, $$7, p_229506_);
                    this.m_73434_(p_229502_, Blocks.f_50627_.m_49966_(), 3, $$8, $$7, p_229506_);
                    this.m_73434_(p_229502_, Blocks.f_50222_.m_49966_(), 4, $$8, $$7, p_229506_);
                }
                this.m_73434_(p_229502_, Blocks.f_50222_.m_49966_(), 0, 4, $$7, p_229506_);
                this.m_73434_(p_229502_, Blocks.f_50222_.m_49966_(), 1, 4, $$7, p_229506_);
                this.m_73434_(p_229502_, Blocks.f_50222_.m_49966_(), 2, 4, $$7, p_229506_);
                this.m_73434_(p_229502_, Blocks.f_50222_.m_49966_(), 3, 4, $$7, p_229506_);
                this.m_73434_(p_229502_, Blocks.f_50222_.m_49966_(), 4, 4, $$7, p_229506_);
            }
        }
    }

    static class SmoothStoneSelector
    extends StructurePiece.BlockSelector {
        SmoothStoneSelector() {
        }

        @Override
        public void m_213766_(RandomSource p_229749_, int p_229750_, int p_229751_, int p_229752_, boolean p_229753_) {
            float $$5;
            this.f_73553_ = p_229753_ ? (($$5 = p_229749_.m_188501_()) < 0.2f ? Blocks.f_50224_.m_49966_() : ($$5 < 0.5f ? Blocks.f_50223_.m_49966_() : ($$5 < 0.55f ? Blocks.f_50176_.m_49966_() : Blocks.f_50222_.m_49966_()))) : Blocks.f_50627_.m_49966_();
        }
    }

    public static abstract class Turn
    extends StrongholdPiece {
        protected static final int f_229926_ = 5;
        protected static final int f_229927_ = 5;
        protected static final int f_229928_ = 5;

        protected Turn(StructurePieceType p_229930_, int p_229931_, BoundingBox p_229932_) {
            super(p_229930_, p_229931_, p_229932_);
        }

        public Turn(StructurePieceType p_229934_, CompoundTag p_229935_) {
            super(p_229934_, p_229935_);
        }
    }
}

