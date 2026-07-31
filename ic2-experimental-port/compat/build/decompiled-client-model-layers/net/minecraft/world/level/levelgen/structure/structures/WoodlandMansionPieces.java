/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class WoodlandMansionPieces {
    public static void m_229985_(StructureTemplateManager p_229986_, BlockPos p_229987_, Rotation p_229988_, List<WoodlandMansionPiece> p_229989_, RandomSource p_229990_) {
        MansionGrid $$5 = new MansionGrid(p_229990_);
        MansionPiecePlacer $$6 = new MansionPiecePlacer(p_229986_, p_229990_);
        $$6.m_230080_(p_229987_, p_229988_, p_229989_, $$5);
    }

    public static void main(String[] p_229992_) {
        RandomSource $$1 = RandomSource.m_216327_();
        long $$2 = $$1.m_188505_();
        System.out.println("Seed: " + $$2);
        $$1.m_188584_($$2);
        MansionGrid $$3 = new MansionGrid($$1);
        $$3.m_230044_();
    }

    static class MansionGrid {
        private static final int f_230020_ = 11;
        private static final int f_230021_ = 0;
        private static final int f_230022_ = 1;
        private static final int f_230023_ = 2;
        private static final int f_230024_ = 3;
        private static final int f_230025_ = 4;
        private static final int f_230026_ = 5;
        private static final int f_230027_ = 65536;
        private static final int f_230028_ = 131072;
        private static final int f_230029_ = 262144;
        private static final int f_230030_ = 0x100000;
        private static final int f_230031_ = 0x200000;
        private static final int f_230032_ = 0x400000;
        private static final int f_230033_ = 0x800000;
        private static final int f_230034_ = 983040;
        private static final int f_230035_ = 65535;
        private final RandomSource f_230036_;
        final SimpleGrid f_230037_;
        final SimpleGrid f_230038_;
        final SimpleGrid[] f_230039_;
        final int f_230040_;
        final int f_230041_;

        public MansionGrid(RandomSource p_230043_) {
            this.f_230036_ = p_230043_;
            int $$1 = 11;
            this.f_230040_ = 7;
            this.f_230041_ = 4;
            this.f_230037_ = new SimpleGrid(11, 11, 5);
            this.f_230037_.m_230179_(this.f_230040_, this.f_230041_, this.f_230040_ + 1, this.f_230041_ + 1, 3);
            this.f_230037_.m_230179_(this.f_230040_ - 1, this.f_230041_, this.f_230040_ - 1, this.f_230041_ + 1, 2);
            this.f_230037_.m_230179_(this.f_230040_ + 2, this.f_230041_ - 2, this.f_230040_ + 3, this.f_230041_ + 3, 5);
            this.f_230037_.m_230179_(this.f_230040_ + 1, this.f_230041_ - 2, this.f_230040_ + 1, this.f_230041_ - 1, 1);
            this.f_230037_.m_230179_(this.f_230040_ + 1, this.f_230041_ + 2, this.f_230040_ + 1, this.f_230041_ + 3, 1);
            this.f_230037_.m_230170_(this.f_230040_ - 1, this.f_230041_ - 1, 1);
            this.f_230037_.m_230170_(this.f_230040_ - 1, this.f_230041_ + 2, 1);
            this.f_230037_.m_230179_(0, 0, 11, 1, 5);
            this.f_230037_.m_230179_(0, 9, 11, 11, 5);
            this.m_230057_(this.f_230037_, this.f_230040_, this.f_230041_ - 2, Direction.WEST, 6);
            this.m_230057_(this.f_230037_, this.f_230040_, this.f_230041_ + 3, Direction.WEST, 6);
            this.m_230057_(this.f_230037_, this.f_230040_ - 2, this.f_230041_ - 1, Direction.WEST, 3);
            this.m_230057_(this.f_230037_, this.f_230040_ - 2, this.f_230041_ + 2, Direction.WEST, 3);
            while (this.m_230045_(this.f_230037_)) {
            }
            this.f_230039_ = new SimpleGrid[3];
            this.f_230039_[0] = new SimpleGrid(11, 11, 5);
            this.f_230039_[1] = new SimpleGrid(11, 11, 5);
            this.f_230039_[2] = new SimpleGrid(11, 11, 5);
            this.m_230063_(this.f_230037_, this.f_230039_[0]);
            this.m_230063_(this.f_230037_, this.f_230039_[1]);
            this.f_230039_[0].m_230179_(this.f_230040_ + 1, this.f_230041_, this.f_230040_ + 1, this.f_230041_ + 1, 0x800000);
            this.f_230039_[1].m_230179_(this.f_230040_ + 1, this.f_230041_, this.f_230040_ + 1, this.f_230041_ + 1, 0x800000);
            this.f_230038_ = new SimpleGrid(this.f_230037_.f_230160_, this.f_230037_.f_230161_, 5);
            this.m_230066_();
            this.m_230063_(this.f_230038_, this.f_230039_[2]);
        }

        public static boolean m_230047_(SimpleGrid p_230048_, int p_230049_, int p_230050_) {
            int $$3 = p_230048_.m_230167_(p_230049_, p_230050_);
            return $$3 == 1 || $$3 == 2 || $$3 == 3 || $$3 == 4;
        }

        public boolean m_230051_(SimpleGrid p_230052_, int p_230053_, int p_230054_, int p_230055_, int p_230056_) {
            return (this.f_230039_[p_230055_].m_230167_(p_230053_, p_230054_) & 0xFFFF) == p_230056_;
        }

        @Nullable
        public Direction m_230067_(SimpleGrid p_230068_, int p_230069_, int p_230070_, int p_230071_, int p_230072_) {
            for (Direction $$5 : Direction.Plane.HORIZONTAL) {
                if (!this.m_230051_(p_230068_, p_230069_ + $$5.m_122429_(), p_230070_ + $$5.m_122431_(), p_230071_, p_230072_)) continue;
                return $$5;
            }
            return null;
        }

        private void m_230057_(SimpleGrid p_230058_, int p_230059_, int p_230060_, Direction p_230061_, int p_230062_) {
            if (p_230062_ <= 0) {
                return;
            }
            p_230058_.m_230170_(p_230059_, p_230060_, 1);
            p_230058_.m_230174_(p_230059_ + p_230061_.m_122429_(), p_230060_ + p_230061_.m_122431_(), 0, 1);
            for (int $$5 = 0; $$5 < 8; ++$$5) {
                Direction $$6 = Direction.m_122407_(this.f_230036_.m_188503_(4));
                if ($$6 == p_230061_.m_122424_() || $$6 == Direction.EAST && this.f_230036_.m_188499_()) continue;
                int $$7 = p_230059_ + p_230061_.m_122429_();
                int $$8 = p_230060_ + p_230061_.m_122431_();
                if (p_230058_.m_230167_($$7 + $$6.m_122429_(), $$8 + $$6.m_122431_()) != 0 || p_230058_.m_230167_($$7 + $$6.m_122429_() * 2, $$8 + $$6.m_122431_() * 2) != 0) continue;
                this.m_230057_(p_230058_, p_230059_ + p_230061_.m_122429_() + $$6.m_122429_(), p_230060_ + p_230061_.m_122431_() + $$6.m_122431_(), $$6, p_230062_ - 1);
                break;
            }
            Direction $$9 = p_230061_.m_122427_();
            Direction $$10 = p_230061_.m_122428_();
            p_230058_.m_230174_(p_230059_ + $$9.m_122429_(), p_230060_ + $$9.m_122431_(), 0, 2);
            p_230058_.m_230174_(p_230059_ + $$10.m_122429_(), p_230060_ + $$10.m_122431_(), 0, 2);
            p_230058_.m_230174_(p_230059_ + p_230061_.m_122429_() + $$9.m_122429_(), p_230060_ + p_230061_.m_122431_() + $$9.m_122431_(), 0, 2);
            p_230058_.m_230174_(p_230059_ + p_230061_.m_122429_() + $$10.m_122429_(), p_230060_ + p_230061_.m_122431_() + $$10.m_122431_(), 0, 2);
            p_230058_.m_230174_(p_230059_ + p_230061_.m_122429_() * 2, p_230060_ + p_230061_.m_122431_() * 2, 0, 2);
            p_230058_.m_230174_(p_230059_ + $$9.m_122429_() * 2, p_230060_ + $$9.m_122431_() * 2, 0, 2);
            p_230058_.m_230174_(p_230059_ + $$10.m_122429_() * 2, p_230060_ + $$10.m_122431_() * 2, 0, 2);
        }

        private boolean m_230045_(SimpleGrid p_230046_) {
            boolean $$1 = false;
            for (int $$2 = 0; $$2 < p_230046_.f_230161_; ++$$2) {
                for (int $$3 = 0; $$3 < p_230046_.f_230160_; ++$$3) {
                    if (p_230046_.m_230167_($$3, $$2) != 0) continue;
                    int $$4 = 0;
                    $$4 += MansionGrid.m_230047_(p_230046_, $$3 + 1, $$2) ? 1 : 0;
                    $$4 += MansionGrid.m_230047_(p_230046_, $$3 - 1, $$2) ? 1 : 0;
                    $$4 += MansionGrid.m_230047_(p_230046_, $$3, $$2 + 1) ? 1 : 0;
                    if (($$4 += MansionGrid.m_230047_(p_230046_, $$3, $$2 - 1) ? 1 : 0) >= 3) {
                        p_230046_.m_230170_($$3, $$2, 2);
                        $$1 = true;
                        continue;
                    }
                    if ($$4 != 2) continue;
                    int $$5 = 0;
                    $$5 += MansionGrid.m_230047_(p_230046_, $$3 + 1, $$2 + 1) ? 1 : 0;
                    $$5 += MansionGrid.m_230047_(p_230046_, $$3 - 1, $$2 + 1) ? 1 : 0;
                    $$5 += MansionGrid.m_230047_(p_230046_, $$3 + 1, $$2 - 1) ? 1 : 0;
                    if (($$5 += MansionGrid.m_230047_(p_230046_, $$3 - 1, $$2 - 1) ? 1 : 0) > 1) continue;
                    p_230046_.m_230170_($$3, $$2, 2);
                    $$1 = true;
                }
            }
            return $$1;
        }

        private void m_230066_() {
            ArrayList $$0 = Lists.newArrayList();
            SimpleGrid $$1 = this.f_230039_[1];
            for (int $$2 = 0; $$2 < this.f_230038_.f_230161_; ++$$2) {
                for (int $$3 = 0; $$3 < this.f_230038_.f_230160_; ++$$3) {
                    int $$4 = $$1.m_230167_($$3, $$2);
                    int $$5 = $$4 & 0xF0000;
                    if ($$5 != 131072 || ($$4 & 0x200000) != 0x200000) continue;
                    $$0.add(new Tuple<Integer, Integer>($$3, $$2));
                }
            }
            if ($$0.isEmpty()) {
                this.f_230038_.m_230179_(0, 0, this.f_230038_.f_230160_, this.f_230038_.f_230161_, 5);
                return;
            }
            Tuple $$6 = (Tuple)$$0.get(this.f_230036_.m_188503_($$0.size()));
            int $$7 = $$1.m_230167_((Integer)$$6.m_14418_(), (Integer)$$6.m_14419_());
            $$1.m_230170_((Integer)$$6.m_14418_(), (Integer)$$6.m_14419_(), $$7 | 0x400000);
            Direction $$8 = this.m_230067_(this.f_230037_, (Integer)$$6.m_14418_(), (Integer)$$6.m_14419_(), 1, $$7 & 0xFFFF);
            int $$9 = (Integer)$$6.m_14418_() + $$8.m_122429_();
            int $$10 = (Integer)$$6.m_14419_() + $$8.m_122431_();
            for (int $$11 = 0; $$11 < this.f_230038_.f_230161_; ++$$11) {
                for (int $$12 = 0; $$12 < this.f_230038_.f_230160_; ++$$12) {
                    if (!MansionGrid.m_230047_(this.f_230037_, $$12, $$11)) {
                        this.f_230038_.m_230170_($$12, $$11, 5);
                        continue;
                    }
                    if ($$12 == (Integer)$$6.m_14418_() && $$11 == (Integer)$$6.m_14419_()) {
                        this.f_230038_.m_230170_($$12, $$11, 3);
                        continue;
                    }
                    if ($$12 != $$9 || $$11 != $$10) continue;
                    this.f_230038_.m_230170_($$12, $$11, 3);
                    this.f_230039_[2].m_230170_($$12, $$11, 0x800000);
                }
            }
            ArrayList $$13 = Lists.newArrayList();
            for (Direction $$14 : Direction.Plane.HORIZONTAL) {
                if (this.f_230038_.m_230167_($$9 + $$14.m_122429_(), $$10 + $$14.m_122431_()) != 0) continue;
                $$13.add($$14);
            }
            if ($$13.isEmpty()) {
                this.f_230038_.m_230179_(0, 0, this.f_230038_.f_230160_, this.f_230038_.f_230161_, 5);
                $$1.m_230170_((Integer)$$6.m_14418_(), (Integer)$$6.m_14419_(), $$7);
                return;
            }
            Direction $$15 = (Direction)$$13.get(this.f_230036_.m_188503_($$13.size()));
            this.m_230057_(this.f_230038_, $$9 + $$15.m_122429_(), $$10 + $$15.m_122431_(), $$15, 4);
            while (this.m_230045_(this.f_230038_)) {
            }
        }

        private void m_230063_(SimpleGrid p_230064_, SimpleGrid p_230065_) {
            ObjectArrayList $$2 = new ObjectArrayList();
            for (int $$3 = 0; $$3 < p_230064_.f_230161_; ++$$3) {
                for (int $$4 = 0; $$4 < p_230064_.f_230160_; ++$$4) {
                    if (p_230064_.m_230167_($$4, $$3) != 2) continue;
                    $$2.add(new Tuple<Integer, Integer>($$4, $$3));
                }
            }
            Util.m_214673_($$2, this.f_230036_);
            int $$5 = 10;
            for (Tuple $$6 : $$2) {
                int $$8;
                int $$7 = (Integer)$$6.m_14418_();
                if (p_230065_.m_230167_($$7, $$8 = ((Integer)$$6.m_14419_()).intValue()) != 0) continue;
                int $$9 = $$7;
                int $$10 = $$7;
                int $$11 = $$8;
                int $$12 = $$8;
                int $$13 = 65536;
                if (p_230065_.m_230167_($$7 + 1, $$8) == 0 && p_230065_.m_230167_($$7, $$8 + 1) == 0 && p_230065_.m_230167_($$7 + 1, $$8 + 1) == 0 && p_230064_.m_230167_($$7 + 1, $$8) == 2 && p_230064_.m_230167_($$7, $$8 + 1) == 2 && p_230064_.m_230167_($$7 + 1, $$8 + 1) == 2) {
                    ++$$10;
                    ++$$12;
                    $$13 = 262144;
                } else if (p_230065_.m_230167_($$7 - 1, $$8) == 0 && p_230065_.m_230167_($$7, $$8 + 1) == 0 && p_230065_.m_230167_($$7 - 1, $$8 + 1) == 0 && p_230064_.m_230167_($$7 - 1, $$8) == 2 && p_230064_.m_230167_($$7, $$8 + 1) == 2 && p_230064_.m_230167_($$7 - 1, $$8 + 1) == 2) {
                    --$$9;
                    ++$$12;
                    $$13 = 262144;
                } else if (p_230065_.m_230167_($$7 - 1, $$8) == 0 && p_230065_.m_230167_($$7, $$8 - 1) == 0 && p_230065_.m_230167_($$7 - 1, $$8 - 1) == 0 && p_230064_.m_230167_($$7 - 1, $$8) == 2 && p_230064_.m_230167_($$7, $$8 - 1) == 2 && p_230064_.m_230167_($$7 - 1, $$8 - 1) == 2) {
                    --$$9;
                    --$$11;
                    $$13 = 262144;
                } else if (p_230065_.m_230167_($$7 + 1, $$8) == 0 && p_230064_.m_230167_($$7 + 1, $$8) == 2) {
                    ++$$10;
                    $$13 = 131072;
                } else if (p_230065_.m_230167_($$7, $$8 + 1) == 0 && p_230064_.m_230167_($$7, $$8 + 1) == 2) {
                    ++$$12;
                    $$13 = 131072;
                } else if (p_230065_.m_230167_($$7 - 1, $$8) == 0 && p_230064_.m_230167_($$7 - 1, $$8) == 2) {
                    --$$9;
                    $$13 = 131072;
                } else if (p_230065_.m_230167_($$7, $$8 - 1) == 0 && p_230064_.m_230167_($$7, $$8 - 1) == 2) {
                    --$$11;
                    $$13 = 131072;
                }
                int $$14 = this.f_230036_.m_188499_() ? $$9 : $$10;
                int $$15 = this.f_230036_.m_188499_() ? $$11 : $$12;
                int $$16 = 0x200000;
                if (!p_230064_.m_230185_($$14, $$15, 1)) {
                    $$14 = $$14 == $$9 ? $$10 : $$9;
                    int n = $$15 = $$15 == $$11 ? $$12 : $$11;
                    if (!p_230064_.m_230185_($$14, $$15, 1)) {
                        int n2 = $$15 = $$15 == $$11 ? $$12 : $$11;
                        if (!p_230064_.m_230185_($$14, $$15, 1)) {
                            $$14 = $$14 == $$9 ? $$10 : $$9;
                            int n3 = $$15 = $$15 == $$11 ? $$12 : $$11;
                            if (!p_230064_.m_230185_($$14, $$15, 1)) {
                                $$16 = 0;
                                $$14 = $$9;
                                $$15 = $$11;
                            }
                        }
                    }
                }
                for (int $$17 = $$11; $$17 <= $$12; ++$$17) {
                    for (int $$18 = $$9; $$18 <= $$10; ++$$18) {
                        if ($$18 == $$14 && $$17 == $$15) {
                            p_230065_.m_230170_($$18, $$17, 0x100000 | $$16 | $$13 | $$5);
                            continue;
                        }
                        p_230065_.m_230170_($$18, $$17, $$13 | $$5);
                    }
                }
                ++$$5;
            }
        }

        public void m_230044_() {
            for (int $$0 = 0; $$0 < 2; ++$$0) {
                SimpleGrid $$1 = $$0 == 0 ? this.f_230037_ : this.f_230038_;
                for (int $$2 = 0; $$2 < $$1.f_230161_; ++$$2) {
                    for (int $$3 = 0; $$3 < $$1.f_230160_; ++$$3) {
                        int $$4 = $$1.m_230167_($$3, $$2);
                        if ($$4 == 1) {
                            System.out.print("+");
                            continue;
                        }
                        if ($$4 == 4) {
                            System.out.print("x");
                            continue;
                        }
                        if ($$4 == 2) {
                            System.out.print("X");
                            continue;
                        }
                        if ($$4 == 3) {
                            System.out.print("O");
                            continue;
                        }
                        if ($$4 == 5) {
                            System.out.print("#");
                            continue;
                        }
                        System.out.print(" ");
                    }
                    System.out.println("");
                }
                System.out.println("");
            }
        }
    }

    static class MansionPiecePlacer {
        private final StructureTemplateManager f_230073_;
        private final RandomSource f_230074_;
        private int f_230075_;
        private int f_230076_;

        public MansionPiecePlacer(StructureTemplateManager p_230078_, RandomSource p_230079_) {
            this.f_230073_ = p_230078_;
            this.f_230074_ = p_230079_;
        }

        public void m_230080_(BlockPos p_230081_, Rotation p_230082_, List<WoodlandMansionPiece> p_230083_, MansionGrid p_230084_) {
            PlacementData $$4 = new PlacementData();
            $$4.f_230139_ = p_230081_;
            $$4.f_230138_ = p_230082_;
            $$4.f_230140_ = "wall_flat";
            PlacementData $$5 = new PlacementData();
            this.m_230085_(p_230083_, $$4);
            $$5.f_230139_ = $$4.f_230139_.m_6630_(8);
            $$5.f_230138_ = $$4.f_230138_;
            $$5.f_230140_ = "wall_window";
            if (!p_230083_.isEmpty()) {
                // empty if block
            }
            SimpleGrid $$6 = p_230084_.f_230037_;
            SimpleGrid $$7 = p_230084_.f_230038_;
            this.f_230075_ = p_230084_.f_230040_ + 1;
            this.f_230076_ = p_230084_.f_230041_ + 1;
            int $$8 = p_230084_.f_230040_ + 1;
            int $$9 = p_230084_.f_230041_;
            this.m_230088_(p_230083_, $$4, $$6, Direction.SOUTH, this.f_230075_, this.f_230076_, $$8, $$9);
            this.m_230088_(p_230083_, $$5, $$6, Direction.SOUTH, this.f_230075_, this.f_230076_, $$8, $$9);
            PlacementData $$10 = new PlacementData();
            $$10.f_230139_ = $$4.f_230139_.m_6630_(19);
            $$10.f_230138_ = $$4.f_230138_;
            $$10.f_230140_ = "wall_window";
            boolean $$11 = false;
            for (int $$12 = 0; $$12 < $$7.f_230161_ && !$$11; ++$$12) {
                for (int $$13 = $$7.f_230160_ - 1; $$13 >= 0 && !$$11; --$$13) {
                    if (!MansionGrid.m_230047_($$7, $$13, $$12)) continue;
                    $$10.f_230139_ = $$10.f_230139_.m_5484_(p_230082_.m_55954_(Direction.SOUTH), 8 + ($$12 - this.f_230076_) * 8);
                    $$10.f_230139_ = $$10.f_230139_.m_5484_(p_230082_.m_55954_(Direction.EAST), ($$13 - this.f_230075_) * 8);
                    this.m_230129_(p_230083_, $$10);
                    this.m_230088_(p_230083_, $$10, $$7, Direction.SOUTH, $$13, $$12, $$13, $$12);
                    $$11 = true;
                }
            }
            this.m_230102_(p_230083_, p_230081_.m_6630_(16), p_230082_, $$6, $$7);
            this.m_230102_(p_230083_, p_230081_.m_6630_(27), p_230082_, $$7, null);
            if (!p_230083_.isEmpty()) {
                // empty if block
            }
            FloorRoomCollection[] $$14 = new FloorRoomCollection[]{new FirstFloorRoomCollection(), new SecondFloorRoomCollection(), new ThirdFloorRoomCollection()};
            for (int $$15 = 0; $$15 < 3; ++$$15) {
                BlockPos $$16 = p_230081_.m_6630_(8 * $$15 + ($$15 == 2 ? 3 : 0));
                SimpleGrid $$17 = p_230084_.f_230039_[$$15];
                SimpleGrid $$18 = $$15 == 2 ? $$7 : $$6;
                String $$19 = $$15 == 0 ? "carpet_south_1" : "carpet_south_2";
                String $$20 = $$15 == 0 ? "carpet_west_1" : "carpet_west_2";
                for (int $$21 = 0; $$21 < $$18.f_230161_; ++$$21) {
                    for (int $$22 = 0; $$22 < $$18.f_230160_; ++$$22) {
                        if ($$18.m_230167_($$22, $$21) != 1) continue;
                        BlockPos $$23 = $$16.m_5484_(p_230082_.m_55954_(Direction.SOUTH), 8 + ($$21 - this.f_230076_) * 8);
                        $$23 = $$23.m_5484_(p_230082_.m_55954_(Direction.EAST), ($$22 - this.f_230075_) * 8);
                        p_230083_.add(new WoodlandMansionPiece(this.f_230073_, "corridor_floor", $$23, p_230082_));
                        if ($$18.m_230167_($$22, $$21 - 1) == 1 || ($$17.m_230167_($$22, $$21 - 1) & 0x800000) == 0x800000) {
                            p_230083_.add(new WoodlandMansionPiece(this.f_230073_, "carpet_north", $$23.m_5484_(p_230082_.m_55954_(Direction.EAST), 1).m_7494_(), p_230082_));
                        }
                        if ($$18.m_230167_($$22 + 1, $$21) == 1 || ($$17.m_230167_($$22 + 1, $$21) & 0x800000) == 0x800000) {
                            p_230083_.add(new WoodlandMansionPiece(this.f_230073_, "carpet_east", $$23.m_5484_(p_230082_.m_55954_(Direction.SOUTH), 1).m_5484_(p_230082_.m_55954_(Direction.EAST), 5).m_7494_(), p_230082_));
                        }
                        if ($$18.m_230167_($$22, $$21 + 1) == 1 || ($$17.m_230167_($$22, $$21 + 1) & 0x800000) == 0x800000) {
                            p_230083_.add(new WoodlandMansionPiece(this.f_230073_, $$19, $$23.m_5484_(p_230082_.m_55954_(Direction.SOUTH), 5).m_5484_(p_230082_.m_55954_(Direction.WEST), 1), p_230082_));
                        }
                        if ($$18.m_230167_($$22 - 1, $$21) != 1 && ($$17.m_230167_($$22 - 1, $$21) & 0x800000) != 0x800000) continue;
                        p_230083_.add(new WoodlandMansionPiece(this.f_230073_, $$20, $$23.m_5484_(p_230082_.m_55954_(Direction.WEST), 1).m_5484_(p_230082_.m_55954_(Direction.NORTH), 1), p_230082_));
                    }
                }
                String $$24 = $$15 == 0 ? "indoors_wall_1" : "indoors_wall_2";
                String $$25 = $$15 == 0 ? "indoors_door_1" : "indoors_door_2";
                ArrayList $$26 = Lists.newArrayList();
                for (int $$27 = 0; $$27 < $$18.f_230161_; ++$$27) {
                    for (int $$28 = 0; $$28 < $$18.f_230160_; ++$$28) {
                        boolean $$29;
                        boolean bl = $$29 = $$15 == 2 && $$18.m_230167_($$28, $$27) == 3;
                        if ($$18.m_230167_($$28, $$27) != 2 && !$$29) continue;
                        int $$30 = $$17.m_230167_($$28, $$27);
                        int $$31 = $$30 & 0xF0000;
                        int $$32 = $$30 & 0xFFFF;
                        $$29 = $$29 && ($$30 & 0x800000) == 0x800000;
                        $$26.clear();
                        if (($$30 & 0x200000) == 0x200000) {
                            for (Direction $$33 : Direction.Plane.HORIZONTAL) {
                                if ($$18.m_230167_($$28 + $$33.m_122429_(), $$27 + $$33.m_122431_()) != 1) continue;
                                $$26.add($$33);
                            }
                        }
                        Direction $$34 = null;
                        if (!$$26.isEmpty()) {
                            $$34 = (Direction)$$26.get(this.f_230074_.m_188503_($$26.size()));
                        } else if (($$30 & 0x100000) == 0x100000) {
                            $$34 = Direction.UP;
                        }
                        BlockPos $$35 = $$16.m_5484_(p_230082_.m_55954_(Direction.SOUTH), 8 + ($$27 - this.f_230076_) * 8);
                        $$35 = $$35.m_5484_(p_230082_.m_55954_(Direction.EAST), -1 + ($$28 - this.f_230075_) * 8);
                        if (MansionGrid.m_230047_($$18, $$28 - 1, $$27) && !p_230084_.m_230051_($$18, $$28 - 1, $$27, $$15, $$32)) {
                            p_230083_.add(new WoodlandMansionPiece(this.f_230073_, $$34 == Direction.WEST ? $$25 : $$24, $$35, p_230082_));
                        }
                        if ($$18.m_230167_($$28 + 1, $$27) == 1 && !$$29) {
                            BlockPos $$36 = $$35.m_5484_(p_230082_.m_55954_(Direction.EAST), 8);
                            p_230083_.add(new WoodlandMansionPiece(this.f_230073_, $$34 == Direction.EAST ? $$25 : $$24, $$36, p_230082_));
                        }
                        if (MansionGrid.m_230047_($$18, $$28, $$27 + 1) && !p_230084_.m_230051_($$18, $$28, $$27 + 1, $$15, $$32)) {
                            BlockPos $$37 = $$35.m_5484_(p_230082_.m_55954_(Direction.SOUTH), 7);
                            $$37 = $$37.m_5484_(p_230082_.m_55954_(Direction.EAST), 7);
                            p_230083_.add(new WoodlandMansionPiece(this.f_230073_, $$34 == Direction.SOUTH ? $$25 : $$24, $$37, p_230082_.m_55952_(Rotation.CLOCKWISE_90)));
                        }
                        if ($$18.m_230167_($$28, $$27 - 1) == 1 && !$$29) {
                            BlockPos $$38 = $$35.m_5484_(p_230082_.m_55954_(Direction.NORTH), 1);
                            $$38 = $$38.m_5484_(p_230082_.m_55954_(Direction.EAST), 7);
                            p_230083_.add(new WoodlandMansionPiece(this.f_230073_, $$34 == Direction.NORTH ? $$25 : $$24, $$38, p_230082_.m_55952_(Rotation.CLOCKWISE_90)));
                        }
                        if ($$31 == 65536) {
                            this.m_230108_(p_230083_, $$35, p_230082_, $$34, $$14[$$15]);
                            continue;
                        }
                        if ($$31 == 131072 && $$34 != null) {
                            Direction $$39 = p_230084_.m_230067_($$18, $$28, $$27, $$15, $$32);
                            boolean $$40 = ($$30 & 0x400000) == 0x400000;
                            this.m_230121_(p_230083_, $$35, p_230082_, $$39, $$34, $$14[$$15], $$40);
                            continue;
                        }
                        if ($$31 == 262144 && $$34 != null && $$34 != Direction.UP) {
                            Direction $$41 = $$34.m_122427_();
                            if (!p_230084_.m_230051_($$18, $$28 + $$41.m_122429_(), $$27 + $$41.m_122431_(), $$15, $$32)) {
                                $$41 = $$41.m_122424_();
                            }
                            this.m_230114_(p_230083_, $$35, p_230082_, $$41, $$34, $$14[$$15]);
                            continue;
                        }
                        if ($$31 != 262144 || $$34 != Direction.UP) continue;
                        this.m_230097_(p_230083_, $$35, p_230082_, $$14[$$15]);
                    }
                }
            }
        }

        private void m_230088_(List<WoodlandMansionPiece> p_230089_, PlacementData p_230090_, SimpleGrid p_230091_, Direction p_230092_, int p_230093_, int p_230094_, int p_230095_, int p_230096_) {
            int $$8 = p_230093_;
            int $$9 = p_230094_;
            Direction $$10 = p_230092_;
            do {
                if (!MansionGrid.m_230047_(p_230091_, $$8 + p_230092_.m_122429_(), $$9 + p_230092_.m_122431_())) {
                    this.m_230132_(p_230089_, p_230090_);
                    p_230092_ = p_230092_.m_122427_();
                    if ($$8 == p_230095_ && $$9 == p_230096_ && $$10 == p_230092_) continue;
                    this.m_230129_(p_230089_, p_230090_);
                    continue;
                }
                if (MansionGrid.m_230047_(p_230091_, $$8 + p_230092_.m_122429_(), $$9 + p_230092_.m_122431_()) && MansionGrid.m_230047_(p_230091_, $$8 + p_230092_.m_122429_() + p_230092_.m_122428_().m_122429_(), $$9 + p_230092_.m_122431_() + p_230092_.m_122428_().m_122431_())) {
                    this.m_230135_(p_230089_, p_230090_);
                    $$8 += p_230092_.m_122429_();
                    $$9 += p_230092_.m_122431_();
                    p_230092_ = p_230092_.m_122428_();
                    continue;
                }
                if (($$8 += p_230092_.m_122429_()) == p_230095_ && ($$9 += p_230092_.m_122431_()) == p_230096_ && $$10 == p_230092_) continue;
                this.m_230129_(p_230089_, p_230090_);
            } while ($$8 != p_230095_ || $$9 != p_230096_ || $$10 != p_230092_);
        }

        private void m_230102_(List<WoodlandMansionPiece> p_230103_, BlockPos p_230104_, Rotation p_230105_, SimpleGrid p_230106_, @Nullable SimpleGrid p_230107_) {
            for (int $$5 = 0; $$5 < p_230106_.f_230161_; ++$$5) {
                for (int $$6 = 0; $$6 < p_230106_.f_230160_; ++$$6) {
                    boolean $$8;
                    BlockPos $$7 = p_230104_;
                    $$7 = $$7.m_5484_(p_230105_.m_55954_(Direction.SOUTH), 8 + ($$5 - this.f_230076_) * 8);
                    $$7 = $$7.m_5484_(p_230105_.m_55954_(Direction.EAST), ($$6 - this.f_230075_) * 8);
                    boolean bl = $$8 = p_230107_ != null && MansionGrid.m_230047_(p_230107_, $$6, $$5);
                    if (!MansionGrid.m_230047_(p_230106_, $$6, $$5) || $$8) continue;
                    p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "roof", $$7.m_6630_(3), p_230105_));
                    if (!MansionGrid.m_230047_(p_230106_, $$6 + 1, $$5)) {
                        BlockPos $$9 = $$7.m_5484_(p_230105_.m_55954_(Direction.EAST), 6);
                        p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "roof_front", $$9, p_230105_));
                    }
                    if (!MansionGrid.m_230047_(p_230106_, $$6 - 1, $$5)) {
                        BlockPos $$10 = $$7.m_5484_(p_230105_.m_55954_(Direction.EAST), 0);
                        $$10 = $$10.m_5484_(p_230105_.m_55954_(Direction.SOUTH), 7);
                        p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "roof_front", $$10, p_230105_.m_55952_(Rotation.CLOCKWISE_180)));
                    }
                    if (!MansionGrid.m_230047_(p_230106_, $$6, $$5 - 1)) {
                        BlockPos $$11 = $$7.m_5484_(p_230105_.m_55954_(Direction.WEST), 1);
                        p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "roof_front", $$11, p_230105_.m_55952_(Rotation.COUNTERCLOCKWISE_90)));
                    }
                    if (MansionGrid.m_230047_(p_230106_, $$6, $$5 + 1)) continue;
                    BlockPos $$12 = $$7.m_5484_(p_230105_.m_55954_(Direction.EAST), 6);
                    $$12 = $$12.m_5484_(p_230105_.m_55954_(Direction.SOUTH), 6);
                    p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "roof_front", $$12, p_230105_.m_55952_(Rotation.CLOCKWISE_90)));
                }
            }
            if (p_230107_ != null) {
                for (int $$13 = 0; $$13 < p_230106_.f_230161_; ++$$13) {
                    for (int $$14 = 0; $$14 < p_230106_.f_230160_; ++$$14) {
                        BlockPos $$15 = p_230104_;
                        $$15 = $$15.m_5484_(p_230105_.m_55954_(Direction.SOUTH), 8 + ($$13 - this.f_230076_) * 8);
                        $$15 = $$15.m_5484_(p_230105_.m_55954_(Direction.EAST), ($$14 - this.f_230075_) * 8);
                        boolean $$16 = MansionGrid.m_230047_(p_230107_, $$14, $$13);
                        if (!MansionGrid.m_230047_(p_230106_, $$14, $$13) || !$$16) continue;
                        if (!MansionGrid.m_230047_(p_230106_, $$14 + 1, $$13)) {
                            BlockPos $$17 = $$15.m_5484_(p_230105_.m_55954_(Direction.EAST), 7);
                            p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "small_wall", $$17, p_230105_));
                        }
                        if (!MansionGrid.m_230047_(p_230106_, $$14 - 1, $$13)) {
                            BlockPos $$18 = $$15.m_5484_(p_230105_.m_55954_(Direction.WEST), 1);
                            $$18 = $$18.m_5484_(p_230105_.m_55954_(Direction.SOUTH), 6);
                            p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "small_wall", $$18, p_230105_.m_55952_(Rotation.CLOCKWISE_180)));
                        }
                        if (!MansionGrid.m_230047_(p_230106_, $$14, $$13 - 1)) {
                            BlockPos $$19 = $$15.m_5484_(p_230105_.m_55954_(Direction.WEST), 0);
                            $$19 = $$19.m_5484_(p_230105_.m_55954_(Direction.NORTH), 1);
                            p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "small_wall", $$19, p_230105_.m_55952_(Rotation.COUNTERCLOCKWISE_90)));
                        }
                        if (!MansionGrid.m_230047_(p_230106_, $$14, $$13 + 1)) {
                            BlockPos $$20 = $$15.m_5484_(p_230105_.m_55954_(Direction.EAST), 6);
                            $$20 = $$20.m_5484_(p_230105_.m_55954_(Direction.SOUTH), 7);
                            p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "small_wall", $$20, p_230105_.m_55952_(Rotation.CLOCKWISE_90)));
                        }
                        if (!MansionGrid.m_230047_(p_230106_, $$14 + 1, $$13)) {
                            if (!MansionGrid.m_230047_(p_230106_, $$14, $$13 - 1)) {
                                BlockPos $$21 = $$15.m_5484_(p_230105_.m_55954_(Direction.EAST), 7);
                                $$21 = $$21.m_5484_(p_230105_.m_55954_(Direction.NORTH), 2);
                                p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "small_wall_corner", $$21, p_230105_));
                            }
                            if (!MansionGrid.m_230047_(p_230106_, $$14, $$13 + 1)) {
                                BlockPos $$22 = $$15.m_5484_(p_230105_.m_55954_(Direction.EAST), 8);
                                $$22 = $$22.m_5484_(p_230105_.m_55954_(Direction.SOUTH), 7);
                                p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "small_wall_corner", $$22, p_230105_.m_55952_(Rotation.CLOCKWISE_90)));
                            }
                        }
                        if (MansionGrid.m_230047_(p_230106_, $$14 - 1, $$13)) continue;
                        if (!MansionGrid.m_230047_(p_230106_, $$14, $$13 - 1)) {
                            BlockPos $$23 = $$15.m_5484_(p_230105_.m_55954_(Direction.WEST), 2);
                            $$23 = $$23.m_5484_(p_230105_.m_55954_(Direction.NORTH), 1);
                            p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "small_wall_corner", $$23, p_230105_.m_55952_(Rotation.COUNTERCLOCKWISE_90)));
                        }
                        if (MansionGrid.m_230047_(p_230106_, $$14, $$13 + 1)) continue;
                        BlockPos $$24 = $$15.m_5484_(p_230105_.m_55954_(Direction.WEST), 1);
                        $$24 = $$24.m_5484_(p_230105_.m_55954_(Direction.SOUTH), 8);
                        p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "small_wall_corner", $$24, p_230105_.m_55952_(Rotation.CLOCKWISE_180)));
                    }
                }
            }
            for (int $$25 = 0; $$25 < p_230106_.f_230161_; ++$$25) {
                for (int $$26 = 0; $$26 < p_230106_.f_230160_; ++$$26) {
                    boolean $$28;
                    BlockPos $$27 = p_230104_;
                    $$27 = $$27.m_5484_(p_230105_.m_55954_(Direction.SOUTH), 8 + ($$25 - this.f_230076_) * 8);
                    $$27 = $$27.m_5484_(p_230105_.m_55954_(Direction.EAST), ($$26 - this.f_230075_) * 8);
                    boolean bl = $$28 = p_230107_ != null && MansionGrid.m_230047_(p_230107_, $$26, $$25);
                    if (!MansionGrid.m_230047_(p_230106_, $$26, $$25) || $$28) continue;
                    if (!MansionGrid.m_230047_(p_230106_, $$26 + 1, $$25)) {
                        BlockPos $$29 = $$27.m_5484_(p_230105_.m_55954_(Direction.EAST), 6);
                        if (!MansionGrid.m_230047_(p_230106_, $$26, $$25 + 1)) {
                            BlockPos $$30 = $$29.m_5484_(p_230105_.m_55954_(Direction.SOUTH), 6);
                            p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "roof_corner", $$30, p_230105_));
                        } else if (MansionGrid.m_230047_(p_230106_, $$26 + 1, $$25 + 1)) {
                            BlockPos $$31 = $$29.m_5484_(p_230105_.m_55954_(Direction.SOUTH), 5);
                            p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "roof_inner_corner", $$31, p_230105_));
                        }
                        if (!MansionGrid.m_230047_(p_230106_, $$26, $$25 - 1)) {
                            p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "roof_corner", $$29, p_230105_.m_55952_(Rotation.COUNTERCLOCKWISE_90)));
                        } else if (MansionGrid.m_230047_(p_230106_, $$26 + 1, $$25 - 1)) {
                            BlockPos $$32 = $$27.m_5484_(p_230105_.m_55954_(Direction.EAST), 9);
                            $$32 = $$32.m_5484_(p_230105_.m_55954_(Direction.NORTH), 2);
                            p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "roof_inner_corner", $$32, p_230105_.m_55952_(Rotation.CLOCKWISE_90)));
                        }
                    }
                    if (MansionGrid.m_230047_(p_230106_, $$26 - 1, $$25)) continue;
                    BlockPos $$33 = $$27.m_5484_(p_230105_.m_55954_(Direction.EAST), 0);
                    $$33 = $$33.m_5484_(p_230105_.m_55954_(Direction.SOUTH), 0);
                    if (!MansionGrid.m_230047_(p_230106_, $$26, $$25 + 1)) {
                        BlockPos $$34 = $$33.m_5484_(p_230105_.m_55954_(Direction.SOUTH), 6);
                        p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "roof_corner", $$34, p_230105_.m_55952_(Rotation.CLOCKWISE_90)));
                    } else if (MansionGrid.m_230047_(p_230106_, $$26 - 1, $$25 + 1)) {
                        BlockPos $$35 = $$33.m_5484_(p_230105_.m_55954_(Direction.SOUTH), 8);
                        $$35 = $$35.m_5484_(p_230105_.m_55954_(Direction.WEST), 3);
                        p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "roof_inner_corner", $$35, p_230105_.m_55952_(Rotation.COUNTERCLOCKWISE_90)));
                    }
                    if (!MansionGrid.m_230047_(p_230106_, $$26, $$25 - 1)) {
                        p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "roof_corner", $$33, p_230105_.m_55952_(Rotation.CLOCKWISE_180)));
                        continue;
                    }
                    if (!MansionGrid.m_230047_(p_230106_, $$26 - 1, $$25 - 1)) continue;
                    BlockPos $$36 = $$33.m_5484_(p_230105_.m_55954_(Direction.SOUTH), 1);
                    p_230103_.add(new WoodlandMansionPiece(this.f_230073_, "roof_inner_corner", $$36, p_230105_.m_55952_(Rotation.CLOCKWISE_180)));
                }
            }
        }

        private void m_230085_(List<WoodlandMansionPiece> p_230086_, PlacementData p_230087_) {
            Direction $$2 = p_230087_.f_230138_.m_55954_(Direction.WEST);
            p_230086_.add(new WoodlandMansionPiece(this.f_230073_, "entrance", p_230087_.f_230139_.m_5484_($$2, 9), p_230087_.f_230138_));
            p_230087_.f_230139_ = p_230087_.f_230139_.m_5484_(p_230087_.f_230138_.m_55954_(Direction.SOUTH), 16);
        }

        private void m_230129_(List<WoodlandMansionPiece> p_230130_, PlacementData p_230131_) {
            p_230130_.add(new WoodlandMansionPiece(this.f_230073_, p_230131_.f_230140_, p_230131_.f_230139_.m_5484_(p_230131_.f_230138_.m_55954_(Direction.EAST), 7), p_230131_.f_230138_));
            p_230131_.f_230139_ = p_230131_.f_230139_.m_5484_(p_230131_.f_230138_.m_55954_(Direction.SOUTH), 8);
        }

        private void m_230132_(List<WoodlandMansionPiece> p_230133_, PlacementData p_230134_) {
            p_230134_.f_230139_ = p_230134_.f_230139_.m_5484_(p_230134_.f_230138_.m_55954_(Direction.SOUTH), -1);
            p_230133_.add(new WoodlandMansionPiece(this.f_230073_, "wall_corner", p_230134_.f_230139_, p_230134_.f_230138_));
            p_230134_.f_230139_ = p_230134_.f_230139_.m_5484_(p_230134_.f_230138_.m_55954_(Direction.SOUTH), -7);
            p_230134_.f_230139_ = p_230134_.f_230139_.m_5484_(p_230134_.f_230138_.m_55954_(Direction.WEST), -6);
            p_230134_.f_230138_ = p_230134_.f_230138_.m_55952_(Rotation.CLOCKWISE_90);
        }

        private void m_230135_(List<WoodlandMansionPiece> p_230136_, PlacementData p_230137_) {
            p_230137_.f_230139_ = p_230137_.f_230139_.m_5484_(p_230137_.f_230138_.m_55954_(Direction.SOUTH), 6);
            p_230137_.f_230139_ = p_230137_.f_230139_.m_5484_(p_230137_.f_230138_.m_55954_(Direction.EAST), 8);
            p_230137_.f_230138_ = p_230137_.f_230138_.m_55952_(Rotation.COUNTERCLOCKWISE_90);
        }

        private void m_230108_(List<WoodlandMansionPiece> p_230109_, BlockPos p_230110_, Rotation p_230111_, Direction p_230112_, FloorRoomCollection p_230113_) {
            Rotation $$5 = Rotation.NONE;
            String $$6 = p_230113_.m_214126_(this.f_230074_);
            if (p_230112_ != Direction.EAST) {
                if (p_230112_ == Direction.NORTH) {
                    $$5 = $$5.m_55952_(Rotation.COUNTERCLOCKWISE_90);
                } else if (p_230112_ == Direction.WEST) {
                    $$5 = $$5.m_55952_(Rotation.CLOCKWISE_180);
                } else if (p_230112_ == Direction.SOUTH) {
                    $$5 = $$5.m_55952_(Rotation.CLOCKWISE_90);
                } else {
                    $$6 = p_230113_.m_214127_(this.f_230074_);
                }
            }
            BlockPos $$7 = StructureTemplate.m_74587_(new BlockPos(1, 0, 0), Mirror.NONE, $$5, 7, 7);
            $$5 = $$5.m_55952_(p_230111_);
            $$7 = $$7.m_7954_(p_230111_);
            BlockPos $$8 = p_230110_.m_7918_($$7.m_123341_(), 0, $$7.m_123343_());
            p_230109_.add(new WoodlandMansionPiece(this.f_230073_, $$6, $$8, $$5));
        }

        private void m_230121_(List<WoodlandMansionPiece> p_230122_, BlockPos p_230123_, Rotation p_230124_, Direction p_230125_, Direction p_230126_, FloorRoomCollection p_230127_, boolean p_230128_) {
            if (p_230126_ == Direction.EAST && p_230125_ == Direction.SOUTH) {
                BlockPos $$7 = p_230123_.m_5484_(p_230124_.m_55954_(Direction.EAST), 1);
                p_230122_.add(new WoodlandMansionPiece(this.f_230073_, p_230127_.m_213986_(this.f_230074_, p_230128_), $$7, p_230124_));
            } else if (p_230126_ == Direction.EAST && p_230125_ == Direction.NORTH) {
                BlockPos $$8 = p_230123_.m_5484_(p_230124_.m_55954_(Direction.EAST), 1);
                $$8 = $$8.m_5484_(p_230124_.m_55954_(Direction.SOUTH), 6);
                p_230122_.add(new WoodlandMansionPiece(this.f_230073_, p_230127_.m_213986_(this.f_230074_, p_230128_), $$8, p_230124_, Mirror.LEFT_RIGHT));
            } else if (p_230126_ == Direction.WEST && p_230125_ == Direction.NORTH) {
                BlockPos $$9 = p_230123_.m_5484_(p_230124_.m_55954_(Direction.EAST), 7);
                $$9 = $$9.m_5484_(p_230124_.m_55954_(Direction.SOUTH), 6);
                p_230122_.add(new WoodlandMansionPiece(this.f_230073_, p_230127_.m_213986_(this.f_230074_, p_230128_), $$9, p_230124_.m_55952_(Rotation.CLOCKWISE_180)));
            } else if (p_230126_ == Direction.WEST && p_230125_ == Direction.SOUTH) {
                BlockPos $$10 = p_230123_.m_5484_(p_230124_.m_55954_(Direction.EAST), 7);
                p_230122_.add(new WoodlandMansionPiece(this.f_230073_, p_230127_.m_213986_(this.f_230074_, p_230128_), $$10, p_230124_, Mirror.FRONT_BACK));
            } else if (p_230126_ == Direction.SOUTH && p_230125_ == Direction.EAST) {
                BlockPos $$11 = p_230123_.m_5484_(p_230124_.m_55954_(Direction.EAST), 1);
                p_230122_.add(new WoodlandMansionPiece(this.f_230073_, p_230127_.m_213986_(this.f_230074_, p_230128_), $$11, p_230124_.m_55952_(Rotation.CLOCKWISE_90), Mirror.LEFT_RIGHT));
            } else if (p_230126_ == Direction.SOUTH && p_230125_ == Direction.WEST) {
                BlockPos $$12 = p_230123_.m_5484_(p_230124_.m_55954_(Direction.EAST), 7);
                p_230122_.add(new WoodlandMansionPiece(this.f_230073_, p_230127_.m_213986_(this.f_230074_, p_230128_), $$12, p_230124_.m_55952_(Rotation.CLOCKWISE_90)));
            } else if (p_230126_ == Direction.NORTH && p_230125_ == Direction.WEST) {
                BlockPos $$13 = p_230123_.m_5484_(p_230124_.m_55954_(Direction.EAST), 7);
                $$13 = $$13.m_5484_(p_230124_.m_55954_(Direction.SOUTH), 6);
                p_230122_.add(new WoodlandMansionPiece(this.f_230073_, p_230127_.m_213986_(this.f_230074_, p_230128_), $$13, p_230124_.m_55952_(Rotation.CLOCKWISE_90), Mirror.FRONT_BACK));
            } else if (p_230126_ == Direction.NORTH && p_230125_ == Direction.EAST) {
                BlockPos $$14 = p_230123_.m_5484_(p_230124_.m_55954_(Direction.EAST), 1);
                $$14 = $$14.m_5484_(p_230124_.m_55954_(Direction.SOUTH), 6);
                p_230122_.add(new WoodlandMansionPiece(this.f_230073_, p_230127_.m_213986_(this.f_230074_, p_230128_), $$14, p_230124_.m_55952_(Rotation.COUNTERCLOCKWISE_90)));
            } else if (p_230126_ == Direction.SOUTH && p_230125_ == Direction.NORTH) {
                BlockPos $$15 = p_230123_.m_5484_(p_230124_.m_55954_(Direction.EAST), 1);
                $$15 = $$15.m_5484_(p_230124_.m_55954_(Direction.NORTH), 8);
                p_230122_.add(new WoodlandMansionPiece(this.f_230073_, p_230127_.m_213985_(this.f_230074_, p_230128_), $$15, p_230124_));
            } else if (p_230126_ == Direction.NORTH && p_230125_ == Direction.SOUTH) {
                BlockPos $$16 = p_230123_.m_5484_(p_230124_.m_55954_(Direction.EAST), 7);
                $$16 = $$16.m_5484_(p_230124_.m_55954_(Direction.SOUTH), 14);
                p_230122_.add(new WoodlandMansionPiece(this.f_230073_, p_230127_.m_213985_(this.f_230074_, p_230128_), $$16, p_230124_.m_55952_(Rotation.CLOCKWISE_180)));
            } else if (p_230126_ == Direction.WEST && p_230125_ == Direction.EAST) {
                BlockPos $$17 = p_230123_.m_5484_(p_230124_.m_55954_(Direction.EAST), 15);
                p_230122_.add(new WoodlandMansionPiece(this.f_230073_, p_230127_.m_213985_(this.f_230074_, p_230128_), $$17, p_230124_.m_55952_(Rotation.CLOCKWISE_90)));
            } else if (p_230126_ == Direction.EAST && p_230125_ == Direction.WEST) {
                BlockPos $$18 = p_230123_.m_5484_(p_230124_.m_55954_(Direction.WEST), 7);
                $$18 = $$18.m_5484_(p_230124_.m_55954_(Direction.SOUTH), 6);
                p_230122_.add(new WoodlandMansionPiece(this.f_230073_, p_230127_.m_213985_(this.f_230074_, p_230128_), $$18, p_230124_.m_55952_(Rotation.COUNTERCLOCKWISE_90)));
            } else if (p_230126_ == Direction.UP && p_230125_ == Direction.EAST) {
                BlockPos $$19 = p_230123_.m_5484_(p_230124_.m_55954_(Direction.EAST), 15);
                p_230122_.add(new WoodlandMansionPiece(this.f_230073_, p_230127_.m_214128_(this.f_230074_), $$19, p_230124_.m_55952_(Rotation.CLOCKWISE_90)));
            } else if (p_230126_ == Direction.UP && p_230125_ == Direction.SOUTH) {
                BlockPos $$20 = p_230123_.m_5484_(p_230124_.m_55954_(Direction.EAST), 1);
                $$20 = $$20.m_5484_(p_230124_.m_55954_(Direction.NORTH), 0);
                p_230122_.add(new WoodlandMansionPiece(this.f_230073_, p_230127_.m_214128_(this.f_230074_), $$20, p_230124_));
            }
        }

        private void m_230114_(List<WoodlandMansionPiece> p_230115_, BlockPos p_230116_, Rotation p_230117_, Direction p_230118_, Direction p_230119_, FloorRoomCollection p_230120_) {
            int $$6 = 0;
            int $$7 = 0;
            Rotation $$8 = p_230117_;
            Mirror $$9 = Mirror.NONE;
            if (p_230119_ == Direction.EAST && p_230118_ == Direction.SOUTH) {
                $$6 = -7;
            } else if (p_230119_ == Direction.EAST && p_230118_ == Direction.NORTH) {
                $$6 = -7;
                $$7 = 6;
                $$9 = Mirror.LEFT_RIGHT;
            } else if (p_230119_ == Direction.NORTH && p_230118_ == Direction.EAST) {
                $$6 = 1;
                $$7 = 14;
                $$8 = p_230117_.m_55952_(Rotation.COUNTERCLOCKWISE_90);
            } else if (p_230119_ == Direction.NORTH && p_230118_ == Direction.WEST) {
                $$6 = 7;
                $$7 = 14;
                $$8 = p_230117_.m_55952_(Rotation.COUNTERCLOCKWISE_90);
                $$9 = Mirror.LEFT_RIGHT;
            } else if (p_230119_ == Direction.SOUTH && p_230118_ == Direction.WEST) {
                $$6 = 7;
                $$7 = -8;
                $$8 = p_230117_.m_55952_(Rotation.CLOCKWISE_90);
            } else if (p_230119_ == Direction.SOUTH && p_230118_ == Direction.EAST) {
                $$6 = 1;
                $$7 = -8;
                $$8 = p_230117_.m_55952_(Rotation.CLOCKWISE_90);
                $$9 = Mirror.LEFT_RIGHT;
            } else if (p_230119_ == Direction.WEST && p_230118_ == Direction.NORTH) {
                $$6 = 15;
                $$7 = 6;
                $$8 = p_230117_.m_55952_(Rotation.CLOCKWISE_180);
            } else if (p_230119_ == Direction.WEST && p_230118_ == Direction.SOUTH) {
                $$6 = 15;
                $$9 = Mirror.FRONT_BACK;
            }
            BlockPos $$10 = p_230116_.m_5484_(p_230117_.m_55954_(Direction.EAST), $$6);
            $$10 = $$10.m_5484_(p_230117_.m_55954_(Direction.SOUTH), $$7);
            p_230115_.add(new WoodlandMansionPiece(this.f_230073_, p_230120_.m_214124_(this.f_230074_), $$10, $$8, $$9));
        }

        private void m_230097_(List<WoodlandMansionPiece> p_230098_, BlockPos p_230099_, Rotation p_230100_, FloorRoomCollection p_230101_) {
            BlockPos $$4 = p_230099_.m_5484_(p_230100_.m_55954_(Direction.EAST), 1);
            p_230098_.add(new WoodlandMansionPiece(this.f_230073_, p_230101_.m_214125_(this.f_230074_), $$4, p_230100_, Mirror.NONE));
        }
    }

    static class ThirdFloorRoomCollection
    extends SecondFloorRoomCollection {
        ThirdFloorRoomCollection() {
        }
    }

    static class SecondFloorRoomCollection
    extends FloorRoomCollection {
        SecondFloorRoomCollection() {
        }

        @Override
        public String m_214126_(RandomSource p_230144_) {
            return "1x1_b" + (p_230144_.m_188503_(4) + 1);
        }

        @Override
        public String m_214127_(RandomSource p_230149_) {
            return "1x1_as" + (p_230149_.m_188503_(4) + 1);
        }

        @Override
        public String m_213986_(RandomSource p_230146_, boolean p_230147_) {
            if (p_230147_) {
                return "1x2_c_stairs";
            }
            return "1x2_c" + (p_230146_.m_188503_(4) + 1);
        }

        @Override
        public String m_213985_(RandomSource p_230151_, boolean p_230152_) {
            if (p_230152_) {
                return "1x2_d_stairs";
            }
            return "1x2_d" + (p_230151_.m_188503_(5) + 1);
        }

        @Override
        public String m_214128_(RandomSource p_230154_) {
            return "1x2_se" + (p_230154_.m_188503_(1) + 1);
        }

        @Override
        public String m_214124_(RandomSource p_230156_) {
            return "2x2_b" + (p_230156_.m_188503_(5) + 1);
        }

        @Override
        public String m_214125_(RandomSource p_230158_) {
            return "2x2_s1";
        }
    }

    static class FirstFloorRoomCollection
    extends FloorRoomCollection {
        FirstFloorRoomCollection() {
        }

        @Override
        public String m_214126_(RandomSource p_229995_) {
            return "1x1_a" + (p_229995_.m_188503_(5) + 1);
        }

        @Override
        public String m_214127_(RandomSource p_230000_) {
            return "1x1_as" + (p_230000_.m_188503_(4) + 1);
        }

        @Override
        public String m_213986_(RandomSource p_229997_, boolean p_229998_) {
            return "1x2_a" + (p_229997_.m_188503_(9) + 1);
        }

        @Override
        public String m_213985_(RandomSource p_230002_, boolean p_230003_) {
            return "1x2_b" + (p_230002_.m_188503_(5) + 1);
        }

        @Override
        public String m_214128_(RandomSource p_230005_) {
            return "1x2_s" + (p_230005_.m_188503_(2) + 1);
        }

        @Override
        public String m_214124_(RandomSource p_230007_) {
            return "2x2_a" + (p_230007_.m_188503_(4) + 1);
        }

        @Override
        public String m_214125_(RandomSource p_230009_) {
            return "2x2_s1";
        }
    }

    static abstract class FloorRoomCollection {
        FloorRoomCollection() {
        }

        public abstract String m_214126_(RandomSource var1);

        public abstract String m_214127_(RandomSource var1);

        public abstract String m_213986_(RandomSource var1, boolean var2);

        public abstract String m_213985_(RandomSource var1, boolean var2);

        public abstract String m_214128_(RandomSource var1);

        public abstract String m_214124_(RandomSource var1);

        public abstract String m_214125_(RandomSource var1);
    }

    static class SimpleGrid {
        private final int[][] f_230159_;
        final int f_230160_;
        final int f_230161_;
        private final int f_230162_;

        public SimpleGrid(int p_230164_, int p_230165_, int p_230166_) {
            this.f_230160_ = p_230164_;
            this.f_230161_ = p_230165_;
            this.f_230162_ = p_230166_;
            this.f_230159_ = new int[p_230164_][p_230165_];
        }

        public void m_230170_(int p_230171_, int p_230172_, int p_230173_) {
            if (p_230171_ >= 0 && p_230171_ < this.f_230160_ && p_230172_ >= 0 && p_230172_ < this.f_230161_) {
                this.f_230159_[p_230171_][p_230172_] = p_230173_;
            }
        }

        public void m_230179_(int p_230180_, int p_230181_, int p_230182_, int p_230183_, int p_230184_) {
            for (int $$5 = p_230181_; $$5 <= p_230183_; ++$$5) {
                for (int $$6 = p_230180_; $$6 <= p_230182_; ++$$6) {
                    this.m_230170_($$6, $$5, p_230184_);
                }
            }
        }

        public int m_230167_(int p_230168_, int p_230169_) {
            if (p_230168_ >= 0 && p_230168_ < this.f_230160_ && p_230169_ >= 0 && p_230169_ < this.f_230161_) {
                return this.f_230159_[p_230168_][p_230169_];
            }
            return this.f_230162_;
        }

        public void m_230174_(int p_230175_, int p_230176_, int p_230177_, int p_230178_) {
            if (this.m_230167_(p_230175_, p_230176_) == p_230177_) {
                this.m_230170_(p_230175_, p_230176_, p_230178_);
            }
        }

        public boolean m_230185_(int p_230186_, int p_230187_, int p_230188_) {
            return this.m_230167_(p_230186_ - 1, p_230187_) == p_230188_ || this.m_230167_(p_230186_ + 1, p_230187_) == p_230188_ || this.m_230167_(p_230186_, p_230187_ + 1) == p_230188_ || this.m_230167_(p_230186_, p_230187_ - 1) == p_230188_;
        }
    }

    static class PlacementData {
        public Rotation f_230138_;
        public BlockPos f_230139_;
        public String f_230140_;

        PlacementData() {
        }
    }

    public static class WoodlandMansionPiece
    extends TemplateStructurePiece {
        public WoodlandMansionPiece(StructureTemplateManager p_230191_, String p_230192_, BlockPos p_230193_, Rotation p_230194_) {
            this(p_230191_, p_230192_, p_230193_, p_230194_, Mirror.NONE);
        }

        public WoodlandMansionPiece(StructureTemplateManager p_230196_, String p_230197_, BlockPos p_230198_, Rotation p_230199_, Mirror p_230200_) {
            super(StructurePieceType.f_210120_, 0, p_230196_, WoodlandMansionPiece.m_230210_(p_230197_), p_230197_, WoodlandMansionPiece.m_230204_(p_230200_, p_230199_), p_230198_);
        }

        public WoodlandMansionPiece(StructureTemplateManager p_230202_, CompoundTag p_230203_) {
            super(StructurePieceType.f_210120_, p_230203_, p_230202_, (ResourceLocation p_230220_) -> WoodlandMansionPiece.m_230204_(Mirror.valueOf(p_230203_.m_128461_("Mi")), Rotation.valueOf(p_230203_.m_128461_("Rot"))));
        }

        @Override
        protected ResourceLocation m_142415_() {
            return WoodlandMansionPiece.m_230210_(this.f_163658_);
        }

        private static ResourceLocation m_230210_(String p_230211_) {
            return new ResourceLocation("woodland_mansion/" + p_230211_);
        }

        private static StructurePlaceSettings m_230204_(Mirror p_230205_, Rotation p_230206_) {
            return new StructurePlaceSettings().m_74392_(true).m_74379_(p_230206_).m_74377_(p_230205_).m_74383_(BlockIgnoreProcessor.f_74046_);
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_230208_, CompoundTag p_230209_) {
            super.m_183620_(p_230208_, p_230209_);
            p_230209_.m_128359_("Rot", this.f_73657_.m_74404_().name());
            p_230209_.m_128359_("Mi", this.f_73657_.m_74401_().name());
        }

        @Override
        protected void m_213704_(String p_230213_, BlockPos p_230214_, ServerLevelAccessor p_230215_, RandomSource p_230216_, BoundingBox p_230217_) {
            if (p_230213_.startsWith("Chest")) {
                Rotation $$5 = this.f_73657_.m_74404_();
                BlockState $$6 = Blocks.f_50087_.m_49966_();
                if ("ChestWest".equals(p_230213_)) {
                    $$6 = (BlockState)$$6.m_61124_(ChestBlock.f_51478_, $$5.m_55954_(Direction.WEST));
                } else if ("ChestEast".equals(p_230213_)) {
                    $$6 = (BlockState)$$6.m_61124_(ChestBlock.f_51478_, $$5.m_55954_(Direction.EAST));
                } else if ("ChestSouth".equals(p_230213_)) {
                    $$6 = (BlockState)$$6.m_61124_(ChestBlock.f_51478_, $$5.m_55954_(Direction.SOUTH));
                } else if ("ChestNorth".equals(p_230213_)) {
                    $$6 = (BlockState)$$6.m_61124_(ChestBlock.f_51478_, $$5.m_55954_(Direction.NORTH));
                }
                this.m_226762_(p_230215_, p_230217_, p_230216_, p_230214_, BuiltInLootTables.f_78689_, $$6);
            } else {
                ArrayList<Mob> $$7 = new ArrayList<Mob>();
                switch (p_230213_) {
                    case "Mage": {
                        $$7.add(EntityType.f_20568_.m_20615_(p_230215_.m_6018_()));
                        break;
                    }
                    case "Warrior": {
                        $$7.add(EntityType.f_20493_.m_20615_(p_230215_.m_6018_()));
                        break;
                    }
                    case "Group of Allays": {
                        int $$8 = p_230215_.m_213780_().m_188503_(3) + 1;
                        for (int $$9 = 0; $$9 < $$8; ++$$9) {
                            $$7.add(EntityType.f_217014_.m_20615_(p_230215_.m_6018_()));
                        }
                        break;
                    }
                    default: {
                        return;
                    }
                }
                for (Mob $$10 : $$7) {
                    $$10.m_21530_();
                    $$10.m_20035_(p_230214_, 0.0f, 0.0f);
                    $$10.m_6518_(p_230215_, p_230215_.m_6436_($$10.m_20183_()), MobSpawnType.STRUCTURE, null, null);
                    p_230215_.m_47205_($$10);
                    p_230215_.m_7731_(p_230214_, Blocks.f_50016_.m_49966_(), 2);
                }
            }
        }
    }
}

