/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public class OceanMonumentPieces {
    private OceanMonumentPieces() {
    }

    static class FitDoubleYZRoom
    implements MonumentRoomFitter {
        FitDoubleYZRoom() {
        }

        @Override
        public boolean m_213925_(RoomDefinition p_228613_) {
            if (p_228613_.f_228938_[Direction.NORTH.m_122411_()] && !p_228613_.f_228937_[Direction.NORTH.m_122411_()].f_228939_ && p_228613_.f_228938_[Direction.UP.m_122411_()] && !p_228613_.f_228937_[Direction.UP.m_122411_()].f_228939_) {
                RoomDefinition $$1 = p_228613_.f_228937_[Direction.NORTH.m_122411_()];
                return $$1.f_228938_[Direction.UP.m_122411_()] && !$$1.f_228937_[Direction.UP.m_122411_()].f_228939_;
            }
            return false;
        }

        @Override
        public OceanMonumentPiece m_214153_(Direction p_228615_, RoomDefinition p_228616_, RandomSource p_228617_) {
            p_228616_.f_228939_ = true;
            p_228616_.f_228937_[Direction.NORTH.m_122411_()].f_228939_ = true;
            p_228616_.f_228937_[Direction.UP.m_122411_()].f_228939_ = true;
            p_228616_.f_228937_[Direction.NORTH.m_122411_()].f_228937_[Direction.UP.m_122411_()].f_228939_ = true;
            return new OceanMonumentDoubleYZRoom(p_228615_, p_228616_);
        }
    }

    static class FitDoubleXYRoom
    implements MonumentRoomFitter {
        FitDoubleXYRoom() {
        }

        @Override
        public boolean m_213925_(RoomDefinition p_228599_) {
            if (p_228599_.f_228938_[Direction.EAST.m_122411_()] && !p_228599_.f_228937_[Direction.EAST.m_122411_()].f_228939_ && p_228599_.f_228938_[Direction.UP.m_122411_()] && !p_228599_.f_228937_[Direction.UP.m_122411_()].f_228939_) {
                RoomDefinition $$1 = p_228599_.f_228937_[Direction.EAST.m_122411_()];
                return $$1.f_228938_[Direction.UP.m_122411_()] && !$$1.f_228937_[Direction.UP.m_122411_()].f_228939_;
            }
            return false;
        }

        @Override
        public OceanMonumentPiece m_214153_(Direction p_228601_, RoomDefinition p_228602_, RandomSource p_228603_) {
            p_228602_.f_228939_ = true;
            p_228602_.f_228937_[Direction.EAST.m_122411_()].f_228939_ = true;
            p_228602_.f_228937_[Direction.UP.m_122411_()].f_228939_ = true;
            p_228602_.f_228937_[Direction.EAST.m_122411_()].f_228937_[Direction.UP.m_122411_()].f_228939_ = true;
            return new OceanMonumentDoubleXYRoom(p_228601_, p_228602_);
        }
    }

    static class FitDoubleZRoom
    implements MonumentRoomFitter {
        FitDoubleZRoom() {
        }

        @Override
        public boolean m_213925_(RoomDefinition p_228620_) {
            return p_228620_.f_228938_[Direction.NORTH.m_122411_()] && !p_228620_.f_228937_[Direction.NORTH.m_122411_()].f_228939_;
        }

        @Override
        public OceanMonumentPiece m_214153_(Direction p_228622_, RoomDefinition p_228623_, RandomSource p_228624_) {
            RoomDefinition $$3 = p_228623_;
            if (!p_228623_.f_228938_[Direction.NORTH.m_122411_()] || p_228623_.f_228937_[Direction.NORTH.m_122411_()].f_228939_) {
                $$3 = p_228623_.f_228937_[Direction.SOUTH.m_122411_()];
            }
            $$3.f_228939_ = true;
            $$3.f_228937_[Direction.NORTH.m_122411_()].f_228939_ = true;
            return new OceanMonumentDoubleZRoom(p_228622_, $$3);
        }
    }

    static class FitDoubleXRoom
    implements MonumentRoomFitter {
        FitDoubleXRoom() {
        }

        @Override
        public boolean m_213925_(RoomDefinition p_228592_) {
            return p_228592_.f_228938_[Direction.EAST.m_122411_()] && !p_228592_.f_228937_[Direction.EAST.m_122411_()].f_228939_;
        }

        @Override
        public OceanMonumentPiece m_214153_(Direction p_228594_, RoomDefinition p_228595_, RandomSource p_228596_) {
            p_228595_.f_228939_ = true;
            p_228595_.f_228937_[Direction.EAST.m_122411_()].f_228939_ = true;
            return new OceanMonumentDoubleXRoom(p_228594_, p_228595_);
        }
    }

    static class FitDoubleYRoom
    implements MonumentRoomFitter {
        FitDoubleYRoom() {
        }

        @Override
        public boolean m_213925_(RoomDefinition p_228606_) {
            return p_228606_.f_228938_[Direction.UP.m_122411_()] && !p_228606_.f_228937_[Direction.UP.m_122411_()].f_228939_;
        }

        @Override
        public OceanMonumentPiece m_214153_(Direction p_228608_, RoomDefinition p_228609_, RandomSource p_228610_) {
            p_228609_.f_228939_ = true;
            p_228609_.f_228937_[Direction.UP.m_122411_()].f_228939_ = true;
            return new OceanMonumentDoubleYRoom(p_228608_, p_228609_);
        }
    }

    static class FitSimpleTopRoom
    implements MonumentRoomFitter {
        FitSimpleTopRoom() {
        }

        @Override
        public boolean m_213925_(RoomDefinition p_228634_) {
            return !p_228634_.f_228938_[Direction.WEST.m_122411_()] && !p_228634_.f_228938_[Direction.EAST.m_122411_()] && !p_228634_.f_228938_[Direction.NORTH.m_122411_()] && !p_228634_.f_228938_[Direction.SOUTH.m_122411_()] && !p_228634_.f_228938_[Direction.UP.m_122411_()];
        }

        @Override
        public OceanMonumentPiece m_214153_(Direction p_228636_, RoomDefinition p_228637_, RandomSource p_228638_) {
            p_228637_.f_228939_ = true;
            return new OceanMonumentSimpleTopRoom(p_228636_, p_228637_);
        }
    }

    static class FitSimpleRoom
    implements MonumentRoomFitter {
        FitSimpleRoom() {
        }

        @Override
        public boolean m_213925_(RoomDefinition p_228627_) {
            return true;
        }

        @Override
        public OceanMonumentPiece m_214153_(Direction p_228629_, RoomDefinition p_228630_, RandomSource p_228631_) {
            p_228630_.f_228939_ = true;
            return new OceanMonumentSimpleRoom(p_228629_, p_228630_, p_228631_);
        }
    }

    static interface MonumentRoomFitter {
        public boolean m_213925_(RoomDefinition var1);

        public OceanMonumentPiece m_214153_(Direction var1, RoomDefinition var2, RandomSource var3);
    }

    static class RoomDefinition {
        final int f_228936_;
        final RoomDefinition[] f_228937_ = new RoomDefinition[6];
        final boolean[] f_228938_ = new boolean[6];
        boolean f_228939_;
        boolean f_228940_;
        private int f_228941_;

        public RoomDefinition(int p_228943_) {
            this.f_228936_ = p_228943_;
        }

        public void m_228947_(Direction p_228948_, RoomDefinition p_228949_) {
            this.f_228937_[p_228948_.m_122411_()] = p_228949_;
            p_228949_.f_228937_[p_228948_.m_122424_().m_122411_()] = this;
        }

        public void m_228944_() {
            for (int $$0 = 0; $$0 < 6; ++$$0) {
                this.f_228938_[$$0] = this.f_228937_[$$0] != null;
            }
        }

        public boolean m_228945_(int p_228946_) {
            if (this.f_228940_) {
                return true;
            }
            this.f_228941_ = p_228946_;
            for (int $$1 = 0; $$1 < 6; ++$$1) {
                if (this.f_228937_[$$1] == null || !this.f_228938_[$$1] || this.f_228937_[$$1].f_228941_ == p_228946_ || !this.f_228937_[$$1].m_228945_(p_228946_)) continue;
                return true;
            }
            return false;
        }

        public boolean m_228950_() {
            return this.f_228936_ >= 75;
        }

        public int m_228951_() {
            int $$0 = 0;
            for (int $$1 = 0; $$1 < 6; ++$$1) {
                if (!this.f_228938_[$$1]) continue;
                ++$$0;
            }
            return $$0;
        }
    }

    public static class OceanMonumentPenthouse
    extends OceanMonumentPiece {
        public OceanMonumentPenthouse(Direction p_228790_, BoundingBox p_228791_) {
            super(StructurePieceType.f_210115_, p_228790_, 1, p_228791_);
        }

        public OceanMonumentPenthouse(CompoundTag p_228793_) {
            super(StructurePieceType.f_210115_, p_228793_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228795_, StructureManager p_228796_, ChunkGenerator p_228797_, RandomSource p_228798_, BoundingBox p_228799_, ChunkPos p_228800_, BlockPos p_228801_) {
            this.m_73441_(p_228795_, p_228799_, 2, -1, 2, 11, -1, 11, f_228805_, f_228805_, false);
            this.m_73441_(p_228795_, p_228799_, 0, -1, 0, 1, -1, 11, f_228804_, f_228804_, false);
            this.m_73441_(p_228795_, p_228799_, 12, -1, 0, 13, -1, 11, f_228804_, f_228804_, false);
            this.m_73441_(p_228795_, p_228799_, 2, -1, 0, 11, -1, 1, f_228804_, f_228804_, false);
            this.m_73441_(p_228795_, p_228799_, 2, -1, 12, 11, -1, 13, f_228804_, f_228804_, false);
            this.m_73441_(p_228795_, p_228799_, 0, 0, 0, 0, 0, 13, f_228805_, f_228805_, false);
            this.m_73441_(p_228795_, p_228799_, 13, 0, 0, 13, 0, 13, f_228805_, f_228805_, false);
            this.m_73441_(p_228795_, p_228799_, 1, 0, 0, 12, 0, 0, f_228805_, f_228805_, false);
            this.m_73441_(p_228795_, p_228799_, 1, 0, 13, 12, 0, 13, f_228805_, f_228805_, false);
            for (int $$7 = 2; $$7 <= 11; $$7 += 3) {
                this.m_73434_(p_228795_, f_228808_, 0, 0, $$7, p_228799_);
                this.m_73434_(p_228795_, f_228808_, 13, 0, $$7, p_228799_);
                this.m_73434_(p_228795_, f_228808_, $$7, 0, 0, p_228799_);
            }
            this.m_73441_(p_228795_, p_228799_, 2, 0, 3, 4, 0, 9, f_228805_, f_228805_, false);
            this.m_73441_(p_228795_, p_228799_, 9, 0, 3, 11, 0, 9, f_228805_, f_228805_, false);
            this.m_73441_(p_228795_, p_228799_, 4, 0, 9, 9, 0, 11, f_228805_, f_228805_, false);
            this.m_73434_(p_228795_, f_228805_, 5, 0, 8, p_228799_);
            this.m_73434_(p_228795_, f_228805_, 8, 0, 8, p_228799_);
            this.m_73434_(p_228795_, f_228805_, 10, 0, 10, p_228799_);
            this.m_73434_(p_228795_, f_228805_, 3, 0, 10, p_228799_);
            this.m_73441_(p_228795_, p_228799_, 3, 0, 3, 3, 0, 7, f_228806_, f_228806_, false);
            this.m_73441_(p_228795_, p_228799_, 10, 0, 3, 10, 0, 7, f_228806_, f_228806_, false);
            this.m_73441_(p_228795_, p_228799_, 6, 0, 10, 7, 0, 10, f_228806_, f_228806_, false);
            int $$8 = 3;
            for (int $$9 = 0; $$9 < 2; ++$$9) {
                for (int $$10 = 2; $$10 <= 8; $$10 += 3) {
                    this.m_73441_(p_228795_, p_228799_, $$8, 0, $$10, $$8, 2, $$10, f_228805_, f_228805_, false);
                }
                $$8 = 10;
            }
            this.m_73441_(p_228795_, p_228799_, 5, 0, 10, 5, 2, 10, f_228805_, f_228805_, false);
            this.m_73441_(p_228795_, p_228799_, 8, 0, 10, 8, 2, 10, f_228805_, f_228805_, false);
            this.m_73441_(p_228795_, p_228799_, 6, -1, 7, 7, -1, 8, f_228806_, f_228806_, false);
            this.m_228880_(p_228795_, p_228799_, 6, -1, 3, 7, -1, 4);
            this.m_228843_(p_228795_, p_228799_, 6, 1, 6);
        }
    }

    public static class OceanMonumentWingRoom
    extends OceanMonumentPiece {
        private int f_228921_;

        public OceanMonumentWingRoom(Direction p_228923_, BoundingBox p_228924_, int p_228925_) {
            super(StructurePieceType.f_210118_, p_228923_, 1, p_228924_);
            this.f_228921_ = p_228925_ & 1;
        }

        public OceanMonumentWingRoom(CompoundTag p_228927_) {
            super(StructurePieceType.f_210118_, p_228927_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228929_, StructureManager p_228930_, ChunkGenerator p_228931_, RandomSource p_228932_, BoundingBox p_228933_, ChunkPos p_228934_, BlockPos p_228935_) {
            if (this.f_228921_ == 0) {
                for (int $$7 = 0; $$7 < 4; ++$$7) {
                    this.m_73441_(p_228929_, p_228933_, 10 - $$7, 3 - $$7, 20 - $$7, 12 + $$7, 3 - $$7, 20, f_228805_, f_228805_, false);
                }
                this.m_73441_(p_228929_, p_228933_, 7, 0, 6, 15, 0, 16, f_228805_, f_228805_, false);
                this.m_73441_(p_228929_, p_228933_, 6, 0, 6, 6, 3, 20, f_228805_, f_228805_, false);
                this.m_73441_(p_228929_, p_228933_, 16, 0, 6, 16, 3, 20, f_228805_, f_228805_, false);
                this.m_73441_(p_228929_, p_228933_, 7, 1, 7, 7, 1, 20, f_228805_, f_228805_, false);
                this.m_73441_(p_228929_, p_228933_, 15, 1, 7, 15, 1, 20, f_228805_, f_228805_, false);
                this.m_73441_(p_228929_, p_228933_, 7, 1, 6, 9, 3, 6, f_228805_, f_228805_, false);
                this.m_73441_(p_228929_, p_228933_, 13, 1, 6, 15, 3, 6, f_228805_, f_228805_, false);
                this.m_73441_(p_228929_, p_228933_, 8, 1, 7, 9, 1, 7, f_228805_, f_228805_, false);
                this.m_73441_(p_228929_, p_228933_, 13, 1, 7, 14, 1, 7, f_228805_, f_228805_, false);
                this.m_73441_(p_228929_, p_228933_, 9, 0, 5, 13, 0, 5, f_228805_, f_228805_, false);
                this.m_73441_(p_228929_, p_228933_, 10, 0, 7, 12, 0, 7, f_228806_, f_228806_, false);
                this.m_73441_(p_228929_, p_228933_, 8, 0, 10, 8, 0, 12, f_228806_, f_228806_, false);
                this.m_73441_(p_228929_, p_228933_, 14, 0, 10, 14, 0, 12, f_228806_, f_228806_, false);
                for (int $$8 = 18; $$8 >= 7; $$8 -= 3) {
                    this.m_73434_(p_228929_, f_228808_, 6, 3, $$8, p_228933_);
                    this.m_73434_(p_228929_, f_228808_, 16, 3, $$8, p_228933_);
                }
                this.m_73434_(p_228929_, f_228808_, 10, 0, 10, p_228933_);
                this.m_73434_(p_228929_, f_228808_, 12, 0, 10, p_228933_);
                this.m_73434_(p_228929_, f_228808_, 10, 0, 12, p_228933_);
                this.m_73434_(p_228929_, f_228808_, 12, 0, 12, p_228933_);
                this.m_73434_(p_228929_, f_228808_, 8, 3, 6, p_228933_);
                this.m_73434_(p_228929_, f_228808_, 14, 3, 6, p_228933_);
                this.m_73434_(p_228929_, f_228805_, 4, 2, 4, p_228933_);
                this.m_73434_(p_228929_, f_228808_, 4, 1, 4, p_228933_);
                this.m_73434_(p_228929_, f_228805_, 4, 0, 4, p_228933_);
                this.m_73434_(p_228929_, f_228805_, 18, 2, 4, p_228933_);
                this.m_73434_(p_228929_, f_228808_, 18, 1, 4, p_228933_);
                this.m_73434_(p_228929_, f_228805_, 18, 0, 4, p_228933_);
                this.m_73434_(p_228929_, f_228805_, 4, 2, 18, p_228933_);
                this.m_73434_(p_228929_, f_228808_, 4, 1, 18, p_228933_);
                this.m_73434_(p_228929_, f_228805_, 4, 0, 18, p_228933_);
                this.m_73434_(p_228929_, f_228805_, 18, 2, 18, p_228933_);
                this.m_73434_(p_228929_, f_228808_, 18, 1, 18, p_228933_);
                this.m_73434_(p_228929_, f_228805_, 18, 0, 18, p_228933_);
                this.m_73434_(p_228929_, f_228805_, 9, 7, 20, p_228933_);
                this.m_73434_(p_228929_, f_228805_, 13, 7, 20, p_228933_);
                this.m_73441_(p_228929_, p_228933_, 6, 0, 21, 7, 4, 21, f_228805_, f_228805_, false);
                this.m_73441_(p_228929_, p_228933_, 15, 0, 21, 16, 4, 21, f_228805_, f_228805_, false);
                this.m_228843_(p_228929_, p_228933_, 11, 2, 16);
            } else if (this.f_228921_ == 1) {
                this.m_73441_(p_228929_, p_228933_, 9, 3, 18, 13, 3, 20, f_228805_, f_228805_, false);
                this.m_73441_(p_228929_, p_228933_, 9, 0, 18, 9, 2, 18, f_228805_, f_228805_, false);
                this.m_73441_(p_228929_, p_228933_, 13, 0, 18, 13, 2, 18, f_228805_, f_228805_, false);
                int $$9 = 9;
                int $$10 = 20;
                int $$11 = 5;
                for (int $$12 = 0; $$12 < 2; ++$$12) {
                    this.m_73434_(p_228929_, f_228805_, $$9, 6, 20, p_228933_);
                    this.m_73434_(p_228929_, f_228808_, $$9, 5, 20, p_228933_);
                    this.m_73434_(p_228929_, f_228805_, $$9, 4, 20, p_228933_);
                    $$9 = 13;
                }
                this.m_73441_(p_228929_, p_228933_, 7, 3, 7, 15, 3, 14, f_228805_, f_228805_, false);
                $$9 = 10;
                for (int $$13 = 0; $$13 < 2; ++$$13) {
                    this.m_73441_(p_228929_, p_228933_, $$9, 0, 10, $$9, 6, 10, f_228805_, f_228805_, false);
                    this.m_73441_(p_228929_, p_228933_, $$9, 0, 12, $$9, 6, 12, f_228805_, f_228805_, false);
                    this.m_73434_(p_228929_, f_228808_, $$9, 0, 10, p_228933_);
                    this.m_73434_(p_228929_, f_228808_, $$9, 0, 12, p_228933_);
                    this.m_73434_(p_228929_, f_228808_, $$9, 4, 10, p_228933_);
                    this.m_73434_(p_228929_, f_228808_, $$9, 4, 12, p_228933_);
                    $$9 = 12;
                }
                $$9 = 8;
                for (int $$14 = 0; $$14 < 2; ++$$14) {
                    this.m_73441_(p_228929_, p_228933_, $$9, 0, 7, $$9, 2, 7, f_228805_, f_228805_, false);
                    this.m_73441_(p_228929_, p_228933_, $$9, 0, 14, $$9, 2, 14, f_228805_, f_228805_, false);
                    $$9 = 14;
                }
                this.m_73441_(p_228929_, p_228933_, 8, 3, 8, 8, 3, 13, f_228806_, f_228806_, false);
                this.m_73441_(p_228929_, p_228933_, 14, 3, 8, 14, 3, 13, f_228806_, f_228806_, false);
                this.m_228843_(p_228929_, p_228933_, 11, 5, 13);
            }
        }
    }

    public static class OceanMonumentCoreRoom
    extends OceanMonumentPiece {
        public OceanMonumentCoreRoom(Direction p_228699_, RoomDefinition p_228700_) {
            super(StructurePieceType.f_210108_, 1, p_228699_, p_228700_, 2, 2, 2);
        }

        public OceanMonumentCoreRoom(CompoundTag p_228702_) {
            super(StructurePieceType.f_210108_, p_228702_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228704_, StructureManager p_228705_, ChunkGenerator p_228706_, RandomSource p_228707_, BoundingBox p_228708_, ChunkPos p_228709_, BlockPos p_228710_) {
            this.m_228849_(p_228704_, p_228708_, 1, 8, 0, 14, 8, 14, f_228804_);
            int $$7 = 7;
            BlockState $$8 = f_228805_;
            this.m_73441_(p_228704_, p_228708_, 0, 7, 0, 0, 7, 15, $$8, $$8, false);
            this.m_73441_(p_228704_, p_228708_, 15, 7, 0, 15, 7, 15, $$8, $$8, false);
            this.m_73441_(p_228704_, p_228708_, 1, 7, 0, 15, 7, 0, $$8, $$8, false);
            this.m_73441_(p_228704_, p_228708_, 1, 7, 15, 14, 7, 15, $$8, $$8, false);
            for (int $$9 = 1; $$9 <= 6; ++$$9) {
                BlockState $$10 = f_228805_;
                if ($$9 == 2 || $$9 == 6) {
                    $$10 = f_228804_;
                }
                for (int $$11 = 0; $$11 <= 15; $$11 += 15) {
                    this.m_73441_(p_228704_, p_228708_, $$11, $$9, 0, $$11, $$9, 1, $$10, $$10, false);
                    this.m_73441_(p_228704_, p_228708_, $$11, $$9, 6, $$11, $$9, 9, $$10, $$10, false);
                    this.m_73441_(p_228704_, p_228708_, $$11, $$9, 14, $$11, $$9, 15, $$10, $$10, false);
                }
                this.m_73441_(p_228704_, p_228708_, 1, $$9, 0, 1, $$9, 0, $$10, $$10, false);
                this.m_73441_(p_228704_, p_228708_, 6, $$9, 0, 9, $$9, 0, $$10, $$10, false);
                this.m_73441_(p_228704_, p_228708_, 14, $$9, 0, 14, $$9, 0, $$10, $$10, false);
                this.m_73441_(p_228704_, p_228708_, 1, $$9, 15, 14, $$9, 15, $$10, $$10, false);
            }
            this.m_73441_(p_228704_, p_228708_, 6, 3, 6, 9, 6, 9, f_228806_, f_228806_, false);
            this.m_73441_(p_228704_, p_228708_, 7, 4, 7, 8, 5, 8, Blocks.f_50074_.m_49966_(), Blocks.f_50074_.m_49966_(), false);
            for (int $$12 = 3; $$12 <= 6; $$12 += 3) {
                for (int $$13 = 6; $$13 <= 9; $$13 += 3) {
                    this.m_73434_(p_228704_, f_228808_, $$13, $$12, 6, p_228708_);
                    this.m_73434_(p_228704_, f_228808_, $$13, $$12, 9, p_228708_);
                }
            }
            this.m_73441_(p_228704_, p_228708_, 5, 1, 6, 5, 2, 6, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 5, 1, 9, 5, 2, 9, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 10, 1, 6, 10, 2, 6, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 10, 1, 9, 10, 2, 9, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 6, 1, 5, 6, 2, 5, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 9, 1, 5, 9, 2, 5, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 6, 1, 10, 6, 2, 10, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 9, 1, 10, 9, 2, 10, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 5, 2, 5, 5, 6, 5, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 5, 2, 10, 5, 6, 10, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 10, 2, 5, 10, 6, 5, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 10, 2, 10, 10, 6, 10, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 5, 7, 1, 5, 7, 6, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 10, 7, 1, 10, 7, 6, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 5, 7, 9, 5, 7, 14, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 10, 7, 9, 10, 7, 14, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 1, 7, 5, 6, 7, 5, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 1, 7, 10, 6, 7, 10, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 9, 7, 5, 14, 7, 5, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 9, 7, 10, 14, 7, 10, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 2, 1, 2, 2, 1, 3, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 3, 1, 2, 3, 1, 2, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 13, 1, 2, 13, 1, 3, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 12, 1, 2, 12, 1, 2, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 2, 1, 12, 2, 1, 13, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 3, 1, 13, 3, 1, 13, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 13, 1, 12, 13, 1, 13, f_228805_, f_228805_, false);
            this.m_73441_(p_228704_, p_228708_, 12, 1, 13, 12, 1, 13, f_228805_, f_228805_, false);
        }
    }

    public static class OceanMonumentDoubleYZRoom
    extends OceanMonumentPiece {
        public OceanMonumentDoubleYZRoom(Direction p_228751_, RoomDefinition p_228752_) {
            super(StructurePieceType.f_210112_, 1, p_228751_, p_228752_, 1, 2, 2);
        }

        public OceanMonumentDoubleYZRoom(CompoundTag p_228754_) {
            super(StructurePieceType.f_210112_, p_228754_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228756_, StructureManager p_228757_, ChunkGenerator p_228758_, RandomSource p_228759_, BoundingBox p_228760_, ChunkPos p_228761_, BlockPos p_228762_) {
            RoomDefinition $$7 = this.f_228803_.f_228937_[Direction.NORTH.m_122411_()];
            RoomDefinition $$8 = this.f_228803_;
            RoomDefinition $$9 = $$7.f_228937_[Direction.UP.m_122411_()];
            RoomDefinition $$10 = $$8.f_228937_[Direction.UP.m_122411_()];
            if (this.f_228803_.f_228936_ / 25 > 0) {
                this.m_228859_(p_228756_, p_228760_, 0, 8, $$7.f_228938_[Direction.DOWN.m_122411_()]);
                this.m_228859_(p_228756_, p_228760_, 0, 0, $$8.f_228938_[Direction.DOWN.m_122411_()]);
            }
            if ($$10.f_228937_[Direction.UP.m_122411_()] == null) {
                this.m_228849_(p_228756_, p_228760_, 1, 8, 1, 6, 8, 7, f_228804_);
            }
            if ($$9.f_228937_[Direction.UP.m_122411_()] == null) {
                this.m_228849_(p_228756_, p_228760_, 1, 8, 8, 6, 8, 14, f_228804_);
            }
            for (int $$11 = 1; $$11 <= 7; ++$$11) {
                BlockState $$12 = f_228805_;
                if ($$11 == 2 || $$11 == 6) {
                    $$12 = f_228804_;
                }
                this.m_73441_(p_228756_, p_228760_, 0, $$11, 0, 0, $$11, 15, $$12, $$12, false);
                this.m_73441_(p_228756_, p_228760_, 7, $$11, 0, 7, $$11, 15, $$12, $$12, false);
                this.m_73441_(p_228756_, p_228760_, 1, $$11, 0, 6, $$11, 0, $$12, $$12, false);
                this.m_73441_(p_228756_, p_228760_, 1, $$11, 15, 6, $$11, 15, $$12, $$12, false);
            }
            for (int $$13 = 1; $$13 <= 7; ++$$13) {
                BlockState $$14 = f_228806_;
                if ($$13 == 2 || $$13 == 6) {
                    $$14 = f_228808_;
                }
                this.m_73441_(p_228756_, p_228760_, 3, $$13, 7, 4, $$13, 8, $$14, $$14, false);
            }
            if ($$8.f_228938_[Direction.SOUTH.m_122411_()]) {
                this.m_228880_(p_228756_, p_228760_, 3, 1, 0, 4, 2, 0);
            }
            if ($$8.f_228938_[Direction.EAST.m_122411_()]) {
                this.m_228880_(p_228756_, p_228760_, 7, 1, 3, 7, 2, 4);
            }
            if ($$8.f_228938_[Direction.WEST.m_122411_()]) {
                this.m_228880_(p_228756_, p_228760_, 0, 1, 3, 0, 2, 4);
            }
            if ($$7.f_228938_[Direction.NORTH.m_122411_()]) {
                this.m_228880_(p_228756_, p_228760_, 3, 1, 15, 4, 2, 15);
            }
            if ($$7.f_228938_[Direction.WEST.m_122411_()]) {
                this.m_228880_(p_228756_, p_228760_, 0, 1, 11, 0, 2, 12);
            }
            if ($$7.f_228938_[Direction.EAST.m_122411_()]) {
                this.m_228880_(p_228756_, p_228760_, 7, 1, 11, 7, 2, 12);
            }
            if ($$10.f_228938_[Direction.SOUTH.m_122411_()]) {
                this.m_228880_(p_228756_, p_228760_, 3, 5, 0, 4, 6, 0);
            }
            if ($$10.f_228938_[Direction.EAST.m_122411_()]) {
                this.m_228880_(p_228756_, p_228760_, 7, 5, 3, 7, 6, 4);
                this.m_73441_(p_228756_, p_228760_, 5, 4, 2, 6, 4, 5, f_228805_, f_228805_, false);
                this.m_73441_(p_228756_, p_228760_, 6, 1, 2, 6, 3, 2, f_228805_, f_228805_, false);
                this.m_73441_(p_228756_, p_228760_, 6, 1, 5, 6, 3, 5, f_228805_, f_228805_, false);
            }
            if ($$10.f_228938_[Direction.WEST.m_122411_()]) {
                this.m_228880_(p_228756_, p_228760_, 0, 5, 3, 0, 6, 4);
                this.m_73441_(p_228756_, p_228760_, 1, 4, 2, 2, 4, 5, f_228805_, f_228805_, false);
                this.m_73441_(p_228756_, p_228760_, 1, 1, 2, 1, 3, 2, f_228805_, f_228805_, false);
                this.m_73441_(p_228756_, p_228760_, 1, 1, 5, 1, 3, 5, f_228805_, f_228805_, false);
            }
            if ($$9.f_228938_[Direction.NORTH.m_122411_()]) {
                this.m_228880_(p_228756_, p_228760_, 3, 5, 15, 4, 6, 15);
            }
            if ($$9.f_228938_[Direction.WEST.m_122411_()]) {
                this.m_228880_(p_228756_, p_228760_, 0, 5, 11, 0, 6, 12);
                this.m_73441_(p_228756_, p_228760_, 1, 4, 10, 2, 4, 13, f_228805_, f_228805_, false);
                this.m_73441_(p_228756_, p_228760_, 1, 1, 10, 1, 3, 10, f_228805_, f_228805_, false);
                this.m_73441_(p_228756_, p_228760_, 1, 1, 13, 1, 3, 13, f_228805_, f_228805_, false);
            }
            if ($$9.f_228938_[Direction.EAST.m_122411_()]) {
                this.m_228880_(p_228756_, p_228760_, 7, 5, 11, 7, 6, 12);
                this.m_73441_(p_228756_, p_228760_, 5, 4, 10, 6, 4, 13, f_228805_, f_228805_, false);
                this.m_73441_(p_228756_, p_228760_, 6, 1, 10, 6, 3, 10, f_228805_, f_228805_, false);
                this.m_73441_(p_228756_, p_228760_, 6, 1, 13, 6, 3, 13, f_228805_, f_228805_, false);
            }
        }
    }

    public static class OceanMonumentDoubleXYRoom
    extends OceanMonumentPiece {
        public OceanMonumentDoubleXYRoom(Direction p_228725_, RoomDefinition p_228726_) {
            super(StructurePieceType.f_210110_, 1, p_228725_, p_228726_, 2, 2, 1);
        }

        public OceanMonumentDoubleXYRoom(CompoundTag p_228728_) {
            super(StructurePieceType.f_210110_, p_228728_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228730_, StructureManager p_228731_, ChunkGenerator p_228732_, RandomSource p_228733_, BoundingBox p_228734_, ChunkPos p_228735_, BlockPos p_228736_) {
            RoomDefinition $$7 = this.f_228803_.f_228937_[Direction.EAST.m_122411_()];
            RoomDefinition $$8 = this.f_228803_;
            RoomDefinition $$9 = $$8.f_228937_[Direction.UP.m_122411_()];
            RoomDefinition $$10 = $$7.f_228937_[Direction.UP.m_122411_()];
            if (this.f_228803_.f_228936_ / 25 > 0) {
                this.m_228859_(p_228730_, p_228734_, 8, 0, $$7.f_228938_[Direction.DOWN.m_122411_()]);
                this.m_228859_(p_228730_, p_228734_, 0, 0, $$8.f_228938_[Direction.DOWN.m_122411_()]);
            }
            if ($$9.f_228937_[Direction.UP.m_122411_()] == null) {
                this.m_228849_(p_228730_, p_228734_, 1, 8, 1, 7, 8, 6, f_228804_);
            }
            if ($$10.f_228937_[Direction.UP.m_122411_()] == null) {
                this.m_228849_(p_228730_, p_228734_, 8, 8, 1, 14, 8, 6, f_228804_);
            }
            for (int $$11 = 1; $$11 <= 7; ++$$11) {
                BlockState $$12 = f_228805_;
                if ($$11 == 2 || $$11 == 6) {
                    $$12 = f_228804_;
                }
                this.m_73441_(p_228730_, p_228734_, 0, $$11, 0, 0, $$11, 7, $$12, $$12, false);
                this.m_73441_(p_228730_, p_228734_, 15, $$11, 0, 15, $$11, 7, $$12, $$12, false);
                this.m_73441_(p_228730_, p_228734_, 1, $$11, 0, 15, $$11, 0, $$12, $$12, false);
                this.m_73441_(p_228730_, p_228734_, 1, $$11, 7, 14, $$11, 7, $$12, $$12, false);
            }
            this.m_73441_(p_228730_, p_228734_, 2, 1, 3, 2, 7, 4, f_228805_, f_228805_, false);
            this.m_73441_(p_228730_, p_228734_, 3, 1, 2, 4, 7, 2, f_228805_, f_228805_, false);
            this.m_73441_(p_228730_, p_228734_, 3, 1, 5, 4, 7, 5, f_228805_, f_228805_, false);
            this.m_73441_(p_228730_, p_228734_, 13, 1, 3, 13, 7, 4, f_228805_, f_228805_, false);
            this.m_73441_(p_228730_, p_228734_, 11, 1, 2, 12, 7, 2, f_228805_, f_228805_, false);
            this.m_73441_(p_228730_, p_228734_, 11, 1, 5, 12, 7, 5, f_228805_, f_228805_, false);
            this.m_73441_(p_228730_, p_228734_, 5, 1, 3, 5, 3, 4, f_228805_, f_228805_, false);
            this.m_73441_(p_228730_, p_228734_, 10, 1, 3, 10, 3, 4, f_228805_, f_228805_, false);
            this.m_73441_(p_228730_, p_228734_, 5, 7, 2, 10, 7, 5, f_228805_, f_228805_, false);
            this.m_73441_(p_228730_, p_228734_, 5, 5, 2, 5, 7, 2, f_228805_, f_228805_, false);
            this.m_73441_(p_228730_, p_228734_, 10, 5, 2, 10, 7, 2, f_228805_, f_228805_, false);
            this.m_73441_(p_228730_, p_228734_, 5, 5, 5, 5, 7, 5, f_228805_, f_228805_, false);
            this.m_73441_(p_228730_, p_228734_, 10, 5, 5, 10, 7, 5, f_228805_, f_228805_, false);
            this.m_73434_(p_228730_, f_228805_, 6, 6, 2, p_228734_);
            this.m_73434_(p_228730_, f_228805_, 9, 6, 2, p_228734_);
            this.m_73434_(p_228730_, f_228805_, 6, 6, 5, p_228734_);
            this.m_73434_(p_228730_, f_228805_, 9, 6, 5, p_228734_);
            this.m_73441_(p_228730_, p_228734_, 5, 4, 3, 6, 4, 4, f_228805_, f_228805_, false);
            this.m_73441_(p_228730_, p_228734_, 9, 4, 3, 10, 4, 4, f_228805_, f_228805_, false);
            this.m_73434_(p_228730_, f_228808_, 5, 4, 2, p_228734_);
            this.m_73434_(p_228730_, f_228808_, 5, 4, 5, p_228734_);
            this.m_73434_(p_228730_, f_228808_, 10, 4, 2, p_228734_);
            this.m_73434_(p_228730_, f_228808_, 10, 4, 5, p_228734_);
            if ($$8.f_228938_[Direction.SOUTH.m_122411_()]) {
                this.m_228880_(p_228730_, p_228734_, 3, 1, 0, 4, 2, 0);
            }
            if ($$8.f_228938_[Direction.NORTH.m_122411_()]) {
                this.m_228880_(p_228730_, p_228734_, 3, 1, 7, 4, 2, 7);
            }
            if ($$8.f_228938_[Direction.WEST.m_122411_()]) {
                this.m_228880_(p_228730_, p_228734_, 0, 1, 3, 0, 2, 4);
            }
            if ($$7.f_228938_[Direction.SOUTH.m_122411_()]) {
                this.m_228880_(p_228730_, p_228734_, 11, 1, 0, 12, 2, 0);
            }
            if ($$7.f_228938_[Direction.NORTH.m_122411_()]) {
                this.m_228880_(p_228730_, p_228734_, 11, 1, 7, 12, 2, 7);
            }
            if ($$7.f_228938_[Direction.EAST.m_122411_()]) {
                this.m_228880_(p_228730_, p_228734_, 15, 1, 3, 15, 2, 4);
            }
            if ($$9.f_228938_[Direction.SOUTH.m_122411_()]) {
                this.m_228880_(p_228730_, p_228734_, 3, 5, 0, 4, 6, 0);
            }
            if ($$9.f_228938_[Direction.NORTH.m_122411_()]) {
                this.m_228880_(p_228730_, p_228734_, 3, 5, 7, 4, 6, 7);
            }
            if ($$9.f_228938_[Direction.WEST.m_122411_()]) {
                this.m_228880_(p_228730_, p_228734_, 0, 5, 3, 0, 6, 4);
            }
            if ($$10.f_228938_[Direction.SOUTH.m_122411_()]) {
                this.m_228880_(p_228730_, p_228734_, 11, 5, 0, 12, 6, 0);
            }
            if ($$10.f_228938_[Direction.NORTH.m_122411_()]) {
                this.m_228880_(p_228730_, p_228734_, 11, 5, 7, 12, 6, 7);
            }
            if ($$10.f_228938_[Direction.EAST.m_122411_()]) {
                this.m_228880_(p_228730_, p_228734_, 15, 5, 3, 15, 6, 4);
            }
        }
    }

    public static class OceanMonumentDoubleZRoom
    extends OceanMonumentPiece {
        public OceanMonumentDoubleZRoom(Direction p_228764_, RoomDefinition p_228765_) {
            super(StructurePieceType.f_210113_, 1, p_228764_, p_228765_, 1, 1, 2);
        }

        public OceanMonumentDoubleZRoom(CompoundTag p_228767_) {
            super(StructurePieceType.f_210113_, p_228767_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228769_, StructureManager p_228770_, ChunkGenerator p_228771_, RandomSource p_228772_, BoundingBox p_228773_, ChunkPos p_228774_, BlockPos p_228775_) {
            RoomDefinition $$7 = this.f_228803_.f_228937_[Direction.NORTH.m_122411_()];
            RoomDefinition $$8 = this.f_228803_;
            if (this.f_228803_.f_228936_ / 25 > 0) {
                this.m_228859_(p_228769_, p_228773_, 0, 8, $$7.f_228938_[Direction.DOWN.m_122411_()]);
                this.m_228859_(p_228769_, p_228773_, 0, 0, $$8.f_228938_[Direction.DOWN.m_122411_()]);
            }
            if ($$8.f_228937_[Direction.UP.m_122411_()] == null) {
                this.m_228849_(p_228769_, p_228773_, 1, 4, 1, 6, 4, 7, f_228804_);
            }
            if ($$7.f_228937_[Direction.UP.m_122411_()] == null) {
                this.m_228849_(p_228769_, p_228773_, 1, 4, 8, 6, 4, 14, f_228804_);
            }
            this.m_73441_(p_228769_, p_228773_, 0, 3, 0, 0, 3, 15, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 7, 3, 0, 7, 3, 15, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 1, 3, 0, 7, 3, 0, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 1, 3, 15, 6, 3, 15, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 0, 2, 0, 0, 2, 15, f_228804_, f_228804_, false);
            this.m_73441_(p_228769_, p_228773_, 7, 2, 0, 7, 2, 15, f_228804_, f_228804_, false);
            this.m_73441_(p_228769_, p_228773_, 1, 2, 0, 7, 2, 0, f_228804_, f_228804_, false);
            this.m_73441_(p_228769_, p_228773_, 1, 2, 15, 6, 2, 15, f_228804_, f_228804_, false);
            this.m_73441_(p_228769_, p_228773_, 0, 1, 0, 0, 1, 15, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 7, 1, 0, 7, 1, 15, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 1, 1, 0, 7, 1, 0, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 1, 1, 15, 6, 1, 15, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 1, 1, 1, 1, 1, 2, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 6, 1, 1, 6, 1, 2, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 1, 3, 1, 1, 3, 2, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 6, 3, 1, 6, 3, 2, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 1, 1, 13, 1, 1, 14, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 6, 1, 13, 6, 1, 14, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 1, 3, 13, 1, 3, 14, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 6, 3, 13, 6, 3, 14, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 2, 1, 6, 2, 3, 6, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 5, 1, 6, 5, 3, 6, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 2, 1, 9, 2, 3, 9, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 5, 1, 9, 5, 3, 9, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 3, 2, 6, 4, 2, 6, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 3, 2, 9, 4, 2, 9, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 2, 2, 7, 2, 2, 8, f_228805_, f_228805_, false);
            this.m_73441_(p_228769_, p_228773_, 5, 2, 7, 5, 2, 8, f_228805_, f_228805_, false);
            this.m_73434_(p_228769_, f_228808_, 2, 2, 5, p_228773_);
            this.m_73434_(p_228769_, f_228808_, 5, 2, 5, p_228773_);
            this.m_73434_(p_228769_, f_228808_, 2, 2, 10, p_228773_);
            this.m_73434_(p_228769_, f_228808_, 5, 2, 10, p_228773_);
            this.m_73434_(p_228769_, f_228805_, 2, 3, 5, p_228773_);
            this.m_73434_(p_228769_, f_228805_, 5, 3, 5, p_228773_);
            this.m_73434_(p_228769_, f_228805_, 2, 3, 10, p_228773_);
            this.m_73434_(p_228769_, f_228805_, 5, 3, 10, p_228773_);
            if ($$8.f_228938_[Direction.SOUTH.m_122411_()]) {
                this.m_228880_(p_228769_, p_228773_, 3, 1, 0, 4, 2, 0);
            }
            if ($$8.f_228938_[Direction.EAST.m_122411_()]) {
                this.m_228880_(p_228769_, p_228773_, 7, 1, 3, 7, 2, 4);
            }
            if ($$8.f_228938_[Direction.WEST.m_122411_()]) {
                this.m_228880_(p_228769_, p_228773_, 0, 1, 3, 0, 2, 4);
            }
            if ($$7.f_228938_[Direction.NORTH.m_122411_()]) {
                this.m_228880_(p_228769_, p_228773_, 3, 1, 15, 4, 2, 15);
            }
            if ($$7.f_228938_[Direction.WEST.m_122411_()]) {
                this.m_228880_(p_228769_, p_228773_, 0, 1, 11, 0, 2, 12);
            }
            if ($$7.f_228938_[Direction.EAST.m_122411_()]) {
                this.m_228880_(p_228769_, p_228773_, 7, 1, 11, 7, 2, 12);
            }
        }
    }

    public static class OceanMonumentDoubleXRoom
    extends OceanMonumentPiece {
        public OceanMonumentDoubleXRoom(Direction p_228712_, RoomDefinition p_228713_) {
            super(StructurePieceType.f_210109_, 1, p_228712_, p_228713_, 2, 1, 1);
        }

        public OceanMonumentDoubleXRoom(CompoundTag p_228715_) {
            super(StructurePieceType.f_210109_, p_228715_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228717_, StructureManager p_228718_, ChunkGenerator p_228719_, RandomSource p_228720_, BoundingBox p_228721_, ChunkPos p_228722_, BlockPos p_228723_) {
            RoomDefinition $$7 = this.f_228803_.f_228937_[Direction.EAST.m_122411_()];
            RoomDefinition $$8 = this.f_228803_;
            if (this.f_228803_.f_228936_ / 25 > 0) {
                this.m_228859_(p_228717_, p_228721_, 8, 0, $$7.f_228938_[Direction.DOWN.m_122411_()]);
                this.m_228859_(p_228717_, p_228721_, 0, 0, $$8.f_228938_[Direction.DOWN.m_122411_()]);
            }
            if ($$8.f_228937_[Direction.UP.m_122411_()] == null) {
                this.m_228849_(p_228717_, p_228721_, 1, 4, 1, 7, 4, 6, f_228804_);
            }
            if ($$7.f_228937_[Direction.UP.m_122411_()] == null) {
                this.m_228849_(p_228717_, p_228721_, 8, 4, 1, 14, 4, 6, f_228804_);
            }
            this.m_73441_(p_228717_, p_228721_, 0, 3, 0, 0, 3, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228717_, p_228721_, 15, 3, 0, 15, 3, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228717_, p_228721_, 1, 3, 0, 15, 3, 0, f_228805_, f_228805_, false);
            this.m_73441_(p_228717_, p_228721_, 1, 3, 7, 14, 3, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228717_, p_228721_, 0, 2, 0, 0, 2, 7, f_228804_, f_228804_, false);
            this.m_73441_(p_228717_, p_228721_, 15, 2, 0, 15, 2, 7, f_228804_, f_228804_, false);
            this.m_73441_(p_228717_, p_228721_, 1, 2, 0, 15, 2, 0, f_228804_, f_228804_, false);
            this.m_73441_(p_228717_, p_228721_, 1, 2, 7, 14, 2, 7, f_228804_, f_228804_, false);
            this.m_73441_(p_228717_, p_228721_, 0, 1, 0, 0, 1, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228717_, p_228721_, 15, 1, 0, 15, 1, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228717_, p_228721_, 1, 1, 0, 15, 1, 0, f_228805_, f_228805_, false);
            this.m_73441_(p_228717_, p_228721_, 1, 1, 7, 14, 1, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228717_, p_228721_, 5, 1, 0, 10, 1, 4, f_228805_, f_228805_, false);
            this.m_73441_(p_228717_, p_228721_, 6, 2, 0, 9, 2, 3, f_228804_, f_228804_, false);
            this.m_73441_(p_228717_, p_228721_, 5, 3, 0, 10, 3, 4, f_228805_, f_228805_, false);
            this.m_73434_(p_228717_, f_228808_, 6, 2, 3, p_228721_);
            this.m_73434_(p_228717_, f_228808_, 9, 2, 3, p_228721_);
            if ($$8.f_228938_[Direction.SOUTH.m_122411_()]) {
                this.m_228880_(p_228717_, p_228721_, 3, 1, 0, 4, 2, 0);
            }
            if ($$8.f_228938_[Direction.NORTH.m_122411_()]) {
                this.m_228880_(p_228717_, p_228721_, 3, 1, 7, 4, 2, 7);
            }
            if ($$8.f_228938_[Direction.WEST.m_122411_()]) {
                this.m_228880_(p_228717_, p_228721_, 0, 1, 3, 0, 2, 4);
            }
            if ($$7.f_228938_[Direction.SOUTH.m_122411_()]) {
                this.m_228880_(p_228717_, p_228721_, 11, 1, 0, 12, 2, 0);
            }
            if ($$7.f_228938_[Direction.NORTH.m_122411_()]) {
                this.m_228880_(p_228717_, p_228721_, 11, 1, 7, 12, 2, 7);
            }
            if ($$7.f_228938_[Direction.EAST.m_122411_()]) {
                this.m_228880_(p_228717_, p_228721_, 15, 1, 3, 15, 2, 4);
            }
        }
    }

    public static class OceanMonumentDoubleYRoom
    extends OceanMonumentPiece {
        public OceanMonumentDoubleYRoom(Direction p_228738_, RoomDefinition p_228739_) {
            super(StructurePieceType.f_210111_, 1, p_228738_, p_228739_, 1, 2, 1);
        }

        public OceanMonumentDoubleYRoom(CompoundTag p_228741_) {
            super(StructurePieceType.f_210111_, p_228741_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228743_, StructureManager p_228744_, ChunkGenerator p_228745_, RandomSource p_228746_, BoundingBox p_228747_, ChunkPos p_228748_, BlockPos p_228749_) {
            if (this.f_228803_.f_228936_ / 25 > 0) {
                this.m_228859_(p_228743_, p_228747_, 0, 0, this.f_228803_.f_228938_[Direction.DOWN.m_122411_()]);
            }
            RoomDefinition $$7 = this.f_228803_.f_228937_[Direction.UP.m_122411_()];
            if ($$7.f_228937_[Direction.UP.m_122411_()] == null) {
                this.m_228849_(p_228743_, p_228747_, 1, 8, 1, 6, 8, 6, f_228804_);
            }
            this.m_73441_(p_228743_, p_228747_, 0, 4, 0, 0, 4, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228743_, p_228747_, 7, 4, 0, 7, 4, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228743_, p_228747_, 1, 4, 0, 6, 4, 0, f_228805_, f_228805_, false);
            this.m_73441_(p_228743_, p_228747_, 1, 4, 7, 6, 4, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228743_, p_228747_, 2, 4, 1, 2, 4, 2, f_228805_, f_228805_, false);
            this.m_73441_(p_228743_, p_228747_, 1, 4, 2, 1, 4, 2, f_228805_, f_228805_, false);
            this.m_73441_(p_228743_, p_228747_, 5, 4, 1, 5, 4, 2, f_228805_, f_228805_, false);
            this.m_73441_(p_228743_, p_228747_, 6, 4, 2, 6, 4, 2, f_228805_, f_228805_, false);
            this.m_73441_(p_228743_, p_228747_, 2, 4, 5, 2, 4, 6, f_228805_, f_228805_, false);
            this.m_73441_(p_228743_, p_228747_, 1, 4, 5, 1, 4, 5, f_228805_, f_228805_, false);
            this.m_73441_(p_228743_, p_228747_, 5, 4, 5, 5, 4, 6, f_228805_, f_228805_, false);
            this.m_73441_(p_228743_, p_228747_, 6, 4, 5, 6, 4, 5, f_228805_, f_228805_, false);
            RoomDefinition $$8 = this.f_228803_;
            for (int $$9 = 1; $$9 <= 5; $$9 += 4) {
                int $$10 = 0;
                if ($$8.f_228938_[Direction.SOUTH.m_122411_()]) {
                    this.m_73441_(p_228743_, p_228747_, 2, $$9, $$10, 2, $$9 + 2, $$10, f_228805_, f_228805_, false);
                    this.m_73441_(p_228743_, p_228747_, 5, $$9, $$10, 5, $$9 + 2, $$10, f_228805_, f_228805_, false);
                    this.m_73441_(p_228743_, p_228747_, 3, $$9 + 2, $$10, 4, $$9 + 2, $$10, f_228805_, f_228805_, false);
                } else {
                    this.m_73441_(p_228743_, p_228747_, 0, $$9, $$10, 7, $$9 + 2, $$10, f_228805_, f_228805_, false);
                    this.m_73441_(p_228743_, p_228747_, 0, $$9 + 1, $$10, 7, $$9 + 1, $$10, f_228804_, f_228804_, false);
                }
                $$10 = 7;
                if ($$8.f_228938_[Direction.NORTH.m_122411_()]) {
                    this.m_73441_(p_228743_, p_228747_, 2, $$9, $$10, 2, $$9 + 2, $$10, f_228805_, f_228805_, false);
                    this.m_73441_(p_228743_, p_228747_, 5, $$9, $$10, 5, $$9 + 2, $$10, f_228805_, f_228805_, false);
                    this.m_73441_(p_228743_, p_228747_, 3, $$9 + 2, $$10, 4, $$9 + 2, $$10, f_228805_, f_228805_, false);
                } else {
                    this.m_73441_(p_228743_, p_228747_, 0, $$9, $$10, 7, $$9 + 2, $$10, f_228805_, f_228805_, false);
                    this.m_73441_(p_228743_, p_228747_, 0, $$9 + 1, $$10, 7, $$9 + 1, $$10, f_228804_, f_228804_, false);
                }
                int $$11 = 0;
                if ($$8.f_228938_[Direction.WEST.m_122411_()]) {
                    this.m_73441_(p_228743_, p_228747_, $$11, $$9, 2, $$11, $$9 + 2, 2, f_228805_, f_228805_, false);
                    this.m_73441_(p_228743_, p_228747_, $$11, $$9, 5, $$11, $$9 + 2, 5, f_228805_, f_228805_, false);
                    this.m_73441_(p_228743_, p_228747_, $$11, $$9 + 2, 3, $$11, $$9 + 2, 4, f_228805_, f_228805_, false);
                } else {
                    this.m_73441_(p_228743_, p_228747_, $$11, $$9, 0, $$11, $$9 + 2, 7, f_228805_, f_228805_, false);
                    this.m_73441_(p_228743_, p_228747_, $$11, $$9 + 1, 0, $$11, $$9 + 1, 7, f_228804_, f_228804_, false);
                }
                $$11 = 7;
                if ($$8.f_228938_[Direction.EAST.m_122411_()]) {
                    this.m_73441_(p_228743_, p_228747_, $$11, $$9, 2, $$11, $$9 + 2, 2, f_228805_, f_228805_, false);
                    this.m_73441_(p_228743_, p_228747_, $$11, $$9, 5, $$11, $$9 + 2, 5, f_228805_, f_228805_, false);
                    this.m_73441_(p_228743_, p_228747_, $$11, $$9 + 2, 3, $$11, $$9 + 2, 4, f_228805_, f_228805_, false);
                } else {
                    this.m_73441_(p_228743_, p_228747_, $$11, $$9, 0, $$11, $$9 + 2, 7, f_228805_, f_228805_, false);
                    this.m_73441_(p_228743_, p_228747_, $$11, $$9 + 1, 0, $$11, $$9 + 1, 7, f_228804_, f_228804_, false);
                }
                $$8 = $$7;
            }
        }
    }

    public static class OceanMonumentSimpleTopRoom
    extends OceanMonumentPiece {
        public OceanMonumentSimpleTopRoom(Direction p_228909_, RoomDefinition p_228910_) {
            super(StructurePieceType.f_210117_, 1, p_228909_, p_228910_, 1, 1, 1);
        }

        public OceanMonumentSimpleTopRoom(CompoundTag p_228912_) {
            super(StructurePieceType.f_210117_, p_228912_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228914_, StructureManager p_228915_, ChunkGenerator p_228916_, RandomSource p_228917_, BoundingBox p_228918_, ChunkPos p_228919_, BlockPos p_228920_) {
            if (this.f_228803_.f_228936_ / 25 > 0) {
                this.m_228859_(p_228914_, p_228918_, 0, 0, this.f_228803_.f_228938_[Direction.DOWN.m_122411_()]);
            }
            if (this.f_228803_.f_228937_[Direction.UP.m_122411_()] == null) {
                this.m_228849_(p_228914_, p_228918_, 1, 4, 1, 6, 4, 6, f_228804_);
            }
            for (int $$7 = 1; $$7 <= 6; ++$$7) {
                for (int $$8 = 1; $$8 <= 6; ++$$8) {
                    if (p_228917_.m_188503_(3) == 0) continue;
                    int $$9 = 2 + (p_228917_.m_188503_(4) == 0 ? 0 : 1);
                    BlockState $$10 = Blocks.f_50057_.m_49966_();
                    this.m_73441_(p_228914_, p_228918_, $$7, $$9, $$8, $$7, 3, $$8, $$10, $$10, false);
                }
            }
            this.m_73441_(p_228914_, p_228918_, 0, 1, 0, 0, 1, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228914_, p_228918_, 7, 1, 0, 7, 1, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228914_, p_228918_, 1, 1, 0, 6, 1, 0, f_228805_, f_228805_, false);
            this.m_73441_(p_228914_, p_228918_, 1, 1, 7, 6, 1, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228914_, p_228918_, 0, 2, 0, 0, 2, 7, f_228806_, f_228806_, false);
            this.m_73441_(p_228914_, p_228918_, 7, 2, 0, 7, 2, 7, f_228806_, f_228806_, false);
            this.m_73441_(p_228914_, p_228918_, 1, 2, 0, 6, 2, 0, f_228806_, f_228806_, false);
            this.m_73441_(p_228914_, p_228918_, 1, 2, 7, 6, 2, 7, f_228806_, f_228806_, false);
            this.m_73441_(p_228914_, p_228918_, 0, 3, 0, 0, 3, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228914_, p_228918_, 7, 3, 0, 7, 3, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228914_, p_228918_, 1, 3, 0, 6, 3, 0, f_228805_, f_228805_, false);
            this.m_73441_(p_228914_, p_228918_, 1, 3, 7, 6, 3, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228914_, p_228918_, 0, 1, 3, 0, 2, 4, f_228806_, f_228806_, false);
            this.m_73441_(p_228914_, p_228918_, 7, 1, 3, 7, 2, 4, f_228806_, f_228806_, false);
            this.m_73441_(p_228914_, p_228918_, 3, 1, 0, 4, 2, 0, f_228806_, f_228806_, false);
            this.m_73441_(p_228914_, p_228918_, 3, 1, 7, 4, 2, 7, f_228806_, f_228806_, false);
            if (this.f_228803_.f_228938_[Direction.SOUTH.m_122411_()]) {
                this.m_228880_(p_228914_, p_228918_, 3, 1, 0, 4, 2, 0);
            }
        }
    }

    public static class OceanMonumentSimpleRoom
    extends OceanMonumentPiece {
        private int f_228893_;

        public OceanMonumentSimpleRoom(Direction p_228895_, RoomDefinition p_228896_, RandomSource p_228897_) {
            super(StructurePieceType.f_210116_, 1, p_228895_, p_228896_, 1, 1, 1);
            this.f_228893_ = p_228897_.m_188503_(3);
        }

        public OceanMonumentSimpleRoom(CompoundTag p_228899_) {
            super(StructurePieceType.f_210116_, p_228899_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228901_, StructureManager p_228902_, ChunkGenerator p_228903_, RandomSource p_228904_, BoundingBox p_228905_, ChunkPos p_228906_, BlockPos p_228907_) {
            boolean $$7;
            if (this.f_228803_.f_228936_ / 25 > 0) {
                this.m_228859_(p_228901_, p_228905_, 0, 0, this.f_228803_.f_228938_[Direction.DOWN.m_122411_()]);
            }
            if (this.f_228803_.f_228937_[Direction.UP.m_122411_()] == null) {
                this.m_228849_(p_228901_, p_228905_, 1, 4, 1, 6, 4, 6, f_228804_);
            }
            boolean bl = $$7 = this.f_228893_ != 0 && p_228904_.m_188499_() && !this.f_228803_.f_228938_[Direction.DOWN.m_122411_()] && !this.f_228803_.f_228938_[Direction.UP.m_122411_()] && this.f_228803_.m_228951_() > 1;
            if (this.f_228893_ == 0) {
                this.m_73441_(p_228901_, p_228905_, 0, 1, 0, 2, 1, 2, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 0, 3, 0, 2, 3, 2, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 0, 2, 0, 0, 2, 2, f_228804_, f_228804_, false);
                this.m_73441_(p_228901_, p_228905_, 1, 2, 0, 2, 2, 0, f_228804_, f_228804_, false);
                this.m_73434_(p_228901_, f_228808_, 1, 2, 1, p_228905_);
                this.m_73441_(p_228901_, p_228905_, 5, 1, 0, 7, 1, 2, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 5, 3, 0, 7, 3, 2, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 7, 2, 0, 7, 2, 2, f_228804_, f_228804_, false);
                this.m_73441_(p_228901_, p_228905_, 5, 2, 0, 6, 2, 0, f_228804_, f_228804_, false);
                this.m_73434_(p_228901_, f_228808_, 6, 2, 1, p_228905_);
                this.m_73441_(p_228901_, p_228905_, 0, 1, 5, 2, 1, 7, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 0, 3, 5, 2, 3, 7, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 0, 2, 5, 0, 2, 7, f_228804_, f_228804_, false);
                this.m_73441_(p_228901_, p_228905_, 1, 2, 7, 2, 2, 7, f_228804_, f_228804_, false);
                this.m_73434_(p_228901_, f_228808_, 1, 2, 6, p_228905_);
                this.m_73441_(p_228901_, p_228905_, 5, 1, 5, 7, 1, 7, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 5, 3, 5, 7, 3, 7, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 7, 2, 5, 7, 2, 7, f_228804_, f_228804_, false);
                this.m_73441_(p_228901_, p_228905_, 5, 2, 7, 6, 2, 7, f_228804_, f_228804_, false);
                this.m_73434_(p_228901_, f_228808_, 6, 2, 6, p_228905_);
                if (this.f_228803_.f_228938_[Direction.SOUTH.m_122411_()]) {
                    this.m_73441_(p_228901_, p_228905_, 3, 3, 0, 4, 3, 0, f_228805_, f_228805_, false);
                } else {
                    this.m_73441_(p_228901_, p_228905_, 3, 3, 0, 4, 3, 1, f_228805_, f_228805_, false);
                    this.m_73441_(p_228901_, p_228905_, 3, 2, 0, 4, 2, 0, f_228804_, f_228804_, false);
                    this.m_73441_(p_228901_, p_228905_, 3, 1, 0, 4, 1, 1, f_228805_, f_228805_, false);
                }
                if (this.f_228803_.f_228938_[Direction.NORTH.m_122411_()]) {
                    this.m_73441_(p_228901_, p_228905_, 3, 3, 7, 4, 3, 7, f_228805_, f_228805_, false);
                } else {
                    this.m_73441_(p_228901_, p_228905_, 3, 3, 6, 4, 3, 7, f_228805_, f_228805_, false);
                    this.m_73441_(p_228901_, p_228905_, 3, 2, 7, 4, 2, 7, f_228804_, f_228804_, false);
                    this.m_73441_(p_228901_, p_228905_, 3, 1, 6, 4, 1, 7, f_228805_, f_228805_, false);
                }
                if (this.f_228803_.f_228938_[Direction.WEST.m_122411_()]) {
                    this.m_73441_(p_228901_, p_228905_, 0, 3, 3, 0, 3, 4, f_228805_, f_228805_, false);
                } else {
                    this.m_73441_(p_228901_, p_228905_, 0, 3, 3, 1, 3, 4, f_228805_, f_228805_, false);
                    this.m_73441_(p_228901_, p_228905_, 0, 2, 3, 0, 2, 4, f_228804_, f_228804_, false);
                    this.m_73441_(p_228901_, p_228905_, 0, 1, 3, 1, 1, 4, f_228805_, f_228805_, false);
                }
                if (this.f_228803_.f_228938_[Direction.EAST.m_122411_()]) {
                    this.m_73441_(p_228901_, p_228905_, 7, 3, 3, 7, 3, 4, f_228805_, f_228805_, false);
                } else {
                    this.m_73441_(p_228901_, p_228905_, 6, 3, 3, 7, 3, 4, f_228805_, f_228805_, false);
                    this.m_73441_(p_228901_, p_228905_, 7, 2, 3, 7, 2, 4, f_228804_, f_228804_, false);
                    this.m_73441_(p_228901_, p_228905_, 6, 1, 3, 7, 1, 4, f_228805_, f_228805_, false);
                }
            } else if (this.f_228893_ == 1) {
                this.m_73441_(p_228901_, p_228905_, 2, 1, 2, 2, 3, 2, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 2, 1, 5, 2, 3, 5, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 5, 1, 5, 5, 3, 5, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 5, 1, 2, 5, 3, 2, f_228805_, f_228805_, false);
                this.m_73434_(p_228901_, f_228808_, 2, 2, 2, p_228905_);
                this.m_73434_(p_228901_, f_228808_, 2, 2, 5, p_228905_);
                this.m_73434_(p_228901_, f_228808_, 5, 2, 5, p_228905_);
                this.m_73434_(p_228901_, f_228808_, 5, 2, 2, p_228905_);
                this.m_73441_(p_228901_, p_228905_, 0, 1, 0, 1, 3, 0, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 0, 1, 1, 0, 3, 1, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 0, 1, 7, 1, 3, 7, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 0, 1, 6, 0, 3, 6, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 6, 1, 7, 7, 3, 7, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 7, 1, 6, 7, 3, 6, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 6, 1, 0, 7, 3, 0, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 7, 1, 1, 7, 3, 1, f_228805_, f_228805_, false);
                this.m_73434_(p_228901_, f_228804_, 1, 2, 0, p_228905_);
                this.m_73434_(p_228901_, f_228804_, 0, 2, 1, p_228905_);
                this.m_73434_(p_228901_, f_228804_, 1, 2, 7, p_228905_);
                this.m_73434_(p_228901_, f_228804_, 0, 2, 6, p_228905_);
                this.m_73434_(p_228901_, f_228804_, 6, 2, 7, p_228905_);
                this.m_73434_(p_228901_, f_228804_, 7, 2, 6, p_228905_);
                this.m_73434_(p_228901_, f_228804_, 6, 2, 0, p_228905_);
                this.m_73434_(p_228901_, f_228804_, 7, 2, 1, p_228905_);
                if (!this.f_228803_.f_228938_[Direction.SOUTH.m_122411_()]) {
                    this.m_73441_(p_228901_, p_228905_, 1, 3, 0, 6, 3, 0, f_228805_, f_228805_, false);
                    this.m_73441_(p_228901_, p_228905_, 1, 2, 0, 6, 2, 0, f_228804_, f_228804_, false);
                    this.m_73441_(p_228901_, p_228905_, 1, 1, 0, 6, 1, 0, f_228805_, f_228805_, false);
                }
                if (!this.f_228803_.f_228938_[Direction.NORTH.m_122411_()]) {
                    this.m_73441_(p_228901_, p_228905_, 1, 3, 7, 6, 3, 7, f_228805_, f_228805_, false);
                    this.m_73441_(p_228901_, p_228905_, 1, 2, 7, 6, 2, 7, f_228804_, f_228804_, false);
                    this.m_73441_(p_228901_, p_228905_, 1, 1, 7, 6, 1, 7, f_228805_, f_228805_, false);
                }
                if (!this.f_228803_.f_228938_[Direction.WEST.m_122411_()]) {
                    this.m_73441_(p_228901_, p_228905_, 0, 3, 1, 0, 3, 6, f_228805_, f_228805_, false);
                    this.m_73441_(p_228901_, p_228905_, 0, 2, 1, 0, 2, 6, f_228804_, f_228804_, false);
                    this.m_73441_(p_228901_, p_228905_, 0, 1, 1, 0, 1, 6, f_228805_, f_228805_, false);
                }
                if (!this.f_228803_.f_228938_[Direction.EAST.m_122411_()]) {
                    this.m_73441_(p_228901_, p_228905_, 7, 3, 1, 7, 3, 6, f_228805_, f_228805_, false);
                    this.m_73441_(p_228901_, p_228905_, 7, 2, 1, 7, 2, 6, f_228804_, f_228804_, false);
                    this.m_73441_(p_228901_, p_228905_, 7, 1, 1, 7, 1, 6, f_228805_, f_228805_, false);
                }
            } else if (this.f_228893_ == 2) {
                this.m_73441_(p_228901_, p_228905_, 0, 1, 0, 0, 1, 7, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 7, 1, 0, 7, 1, 7, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 1, 1, 0, 6, 1, 0, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 1, 1, 7, 6, 1, 7, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 0, 2, 0, 0, 2, 7, f_228806_, f_228806_, false);
                this.m_73441_(p_228901_, p_228905_, 7, 2, 0, 7, 2, 7, f_228806_, f_228806_, false);
                this.m_73441_(p_228901_, p_228905_, 1, 2, 0, 6, 2, 0, f_228806_, f_228806_, false);
                this.m_73441_(p_228901_, p_228905_, 1, 2, 7, 6, 2, 7, f_228806_, f_228806_, false);
                this.m_73441_(p_228901_, p_228905_, 0, 3, 0, 0, 3, 7, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 7, 3, 0, 7, 3, 7, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 1, 3, 0, 6, 3, 0, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 1, 3, 7, 6, 3, 7, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 0, 1, 3, 0, 2, 4, f_228806_, f_228806_, false);
                this.m_73441_(p_228901_, p_228905_, 7, 1, 3, 7, 2, 4, f_228806_, f_228806_, false);
                this.m_73441_(p_228901_, p_228905_, 3, 1, 0, 4, 2, 0, f_228806_, f_228806_, false);
                this.m_73441_(p_228901_, p_228905_, 3, 1, 7, 4, 2, 7, f_228806_, f_228806_, false);
                if (this.f_228803_.f_228938_[Direction.SOUTH.m_122411_()]) {
                    this.m_228880_(p_228901_, p_228905_, 3, 1, 0, 4, 2, 0);
                }
                if (this.f_228803_.f_228938_[Direction.NORTH.m_122411_()]) {
                    this.m_228880_(p_228901_, p_228905_, 3, 1, 7, 4, 2, 7);
                }
                if (this.f_228803_.f_228938_[Direction.WEST.m_122411_()]) {
                    this.m_228880_(p_228901_, p_228905_, 0, 1, 3, 0, 2, 4);
                }
                if (this.f_228803_.f_228938_[Direction.EAST.m_122411_()]) {
                    this.m_228880_(p_228901_, p_228905_, 7, 1, 3, 7, 2, 4);
                }
            }
            if ($$7) {
                this.m_73441_(p_228901_, p_228905_, 3, 1, 3, 4, 1, 4, f_228805_, f_228805_, false);
                this.m_73441_(p_228901_, p_228905_, 3, 2, 3, 4, 2, 4, f_228804_, f_228804_, false);
                this.m_73441_(p_228901_, p_228905_, 3, 3, 3, 4, 3, 4, f_228805_, f_228805_, false);
            }
        }
    }

    public static class OceanMonumentEntryRoom
    extends OceanMonumentPiece {
        public OceanMonumentEntryRoom(Direction p_228777_, RoomDefinition p_228778_) {
            super(StructurePieceType.f_210114_, 1, p_228777_, p_228778_, 1, 1, 1);
        }

        public OceanMonumentEntryRoom(CompoundTag p_228780_) {
            super(StructurePieceType.f_210114_, p_228780_);
        }

        @Override
        public void m_213694_(WorldGenLevel p_228782_, StructureManager p_228783_, ChunkGenerator p_228784_, RandomSource p_228785_, BoundingBox p_228786_, ChunkPos p_228787_, BlockPos p_228788_) {
            this.m_73441_(p_228782_, p_228786_, 0, 3, 0, 2, 3, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228782_, p_228786_, 5, 3, 0, 7, 3, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228782_, p_228786_, 0, 2, 0, 1, 2, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228782_, p_228786_, 6, 2, 0, 7, 2, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228782_, p_228786_, 0, 1, 0, 0, 1, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228782_, p_228786_, 7, 1, 0, 7, 1, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228782_, p_228786_, 0, 1, 7, 7, 3, 7, f_228805_, f_228805_, false);
            this.m_73441_(p_228782_, p_228786_, 1, 1, 0, 2, 3, 0, f_228805_, f_228805_, false);
            this.m_73441_(p_228782_, p_228786_, 5, 1, 0, 6, 3, 0, f_228805_, f_228805_, false);
            if (this.f_228803_.f_228938_[Direction.NORTH.m_122411_()]) {
                this.m_228880_(p_228782_, p_228786_, 3, 1, 7, 4, 2, 7);
            }
            if (this.f_228803_.f_228938_[Direction.WEST.m_122411_()]) {
                this.m_228880_(p_228782_, p_228786_, 0, 1, 3, 1, 2, 4);
            }
            if (this.f_228803_.f_228938_[Direction.EAST.m_122411_()]) {
                this.m_228880_(p_228782_, p_228786_, 6, 1, 3, 7, 2, 4);
            }
        }
    }

    public static class MonumentBuilding
    extends OceanMonumentPiece {
        private static final int f_228639_ = 58;
        private static final int f_228640_ = 22;
        private static final int f_228641_ = 58;
        public static final int f_228646_ = 29;
        private static final int f_228642_ = 61;
        private RoomDefinition f_228643_;
        private RoomDefinition f_228644_;
        private final List<OceanMonumentPiece> f_228645_ = Lists.newArrayList();

        public MonumentBuilding(RandomSource p_228648_, int p_228649_, int p_228650_, Direction p_228651_) {
            super(StructurePieceType.f_210107_, p_228651_, 0, MonumentBuilding.m_163541_(p_228649_, 39, p_228650_, p_228651_, 58, 23, 58));
            this.m_73519_(p_228651_);
            List<RoomDefinition> $$4 = this.m_228672_(p_228648_);
            this.f_228643_.f_228939_ = true;
            this.f_228645_.add(new OceanMonumentEntryRoom(p_228651_, this.f_228643_));
            this.f_228645_.add(new OceanMonumentCoreRoom(p_228651_, this.f_228644_));
            ArrayList $$5 = Lists.newArrayList();
            $$5.add(new FitDoubleXYRoom());
            $$5.add(new FitDoubleYZRoom());
            $$5.add(new FitDoubleZRoom());
            $$5.add(new FitDoubleXRoom());
            $$5.add(new FitDoubleYRoom());
            $$5.add(new FitSimpleTopRoom());
            $$5.add(new FitSimpleRoom());
            block0: for (RoomDefinition roomDefinition : $$4) {
                if (roomDefinition.f_228939_ || roomDefinition.m_228950_()) continue;
                for (MonumentRoomFitter $$7 : $$5) {
                    if (!$$7.m_213925_(roomDefinition)) continue;
                    this.f_228645_.add($$7.m_214153_(p_228651_, roomDefinition, p_228648_));
                    continue block0;
                }
            }
            BlockPos.MutableBlockPos $$8 = this.m_163582_(9, 0, 22);
            for (OceanMonumentPiece $$9 : this.f_228645_) {
                $$9.m_73547_().m_162373_($$8);
            }
            BoundingBox boundingBox = BoundingBox.m_162375_(this.m_163582_(1, 1, 1), this.m_163582_(23, 8, 21));
            BoundingBox $$11 = BoundingBox.m_162375_(this.m_163582_(34, 1, 1), this.m_163582_(56, 8, 21));
            BoundingBox $$12 = BoundingBox.m_162375_(this.m_163582_(22, 13, 22), this.m_163582_(35, 17, 35));
            int $$13 = p_228648_.m_188502_();
            this.f_228645_.add(new OceanMonumentWingRoom(p_228651_, boundingBox, $$13++));
            this.f_228645_.add(new OceanMonumentWingRoom(p_228651_, $$11, $$13++));
            this.f_228645_.add(new OceanMonumentPenthouse(p_228651_, $$12));
        }

        public MonumentBuilding(CompoundTag p_228653_) {
            super(StructurePieceType.f_210107_, p_228653_);
        }

        private List<RoomDefinition> m_228672_(RandomSource p_228673_) {
            RoomDefinition[] $$1 = new RoomDefinition[75];
            for (int $$2 = 0; $$2 < 5; ++$$2) {
                for (int $$3 = 0; $$3 < 4; ++$$3) {
                    boolean $$4 = false;
                    int $$5 = MonumentBuilding.m_228889_($$2, 0, $$3);
                    $$1[$$5] = new RoomDefinition($$5);
                }
            }
            for (int $$6 = 0; $$6 < 5; ++$$6) {
                for (int $$7 = 0; $$7 < 4; ++$$7) {
                    boolean $$8 = true;
                    int $$9 = MonumentBuilding.m_228889_($$6, 1, $$7);
                    $$1[$$9] = new RoomDefinition($$9);
                }
            }
            for (int $$10 = 1; $$10 < 4; ++$$10) {
                for (int $$11 = 0; $$11 < 2; ++$$11) {
                    int $$12 = 2;
                    int $$13 = MonumentBuilding.m_228889_($$10, 2, $$11);
                    $$1[$$13] = new RoomDefinition($$13);
                }
            }
            this.f_228643_ = $$1[f_228820_];
            for (int $$14 = 0; $$14 < 5; ++$$14) {
                for (int $$15 = 0; $$15 < 5; ++$$15) {
                    for (int $$16 = 0; $$16 < 3; ++$$16) {
                        int $$17 = MonumentBuilding.m_228889_($$14, $$16, $$15);
                        if ($$1[$$17] == null) continue;
                        for (Direction $$18 : Direction.values()) {
                            int $$22;
                            int $$19 = $$14 + $$18.m_122429_();
                            int $$20 = $$16 + $$18.m_122430_();
                            int $$21 = $$15 + $$18.m_122431_();
                            if ($$19 < 0 || $$19 >= 5 || $$21 < 0 || $$21 >= 5 || $$20 < 0 || $$20 >= 3 || $$1[$$22 = MonumentBuilding.m_228889_($$19, $$20, $$21)] == null) continue;
                            if ($$21 == $$15) {
                                $$1[$$17].m_228947_($$18, $$1[$$22]);
                                continue;
                            }
                            $$1[$$17].m_228947_($$18.m_122424_(), $$1[$$22]);
                        }
                    }
                }
            }
            RoomDefinition $$23 = new RoomDefinition(1003);
            RoomDefinition $$24 = new RoomDefinition(1001);
            RoomDefinition $$25 = new RoomDefinition(1002);
            $$1[f_228821_].m_228947_(Direction.UP, $$23);
            $$1[f_228822_].m_228947_(Direction.SOUTH, $$24);
            $$1[f_228823_].m_228947_(Direction.SOUTH, $$25);
            $$23.f_228939_ = true;
            $$24.f_228939_ = true;
            $$25.f_228939_ = true;
            this.f_228643_.f_228940_ = true;
            this.f_228644_ = $$1[MonumentBuilding.m_228889_(p_228673_.m_188503_(4), 0, 2)];
            this.f_228644_.f_228939_ = true;
            this.f_228644_.f_228937_[Direction.EAST.m_122411_()].f_228939_ = true;
            this.f_228644_.f_228937_[Direction.NORTH.m_122411_()].f_228939_ = true;
            this.f_228644_.f_228937_[Direction.EAST.m_122411_()].f_228937_[Direction.NORTH.m_122411_()].f_228939_ = true;
            this.f_228644_.f_228937_[Direction.UP.m_122411_()].f_228939_ = true;
            this.f_228644_.f_228937_[Direction.EAST.m_122411_()].f_228937_[Direction.UP.m_122411_()].f_228939_ = true;
            this.f_228644_.f_228937_[Direction.NORTH.m_122411_()].f_228937_[Direction.UP.m_122411_()].f_228939_ = true;
            this.f_228644_.f_228937_[Direction.EAST.m_122411_()].f_228937_[Direction.NORTH.m_122411_()].f_228937_[Direction.UP.m_122411_()].f_228939_ = true;
            ObjectArrayList $$26 = new ObjectArrayList();
            for (RoomDefinition $$27 : $$1) {
                if ($$27 == null) continue;
                $$27.m_228944_();
                $$26.add((Object)$$27);
            }
            $$23.m_228944_();
            Util.m_214673_($$26, p_228673_);
            int $$28 = 1;
            for (RoomDefinition $$29 : $$26) {
                int $$30 = 0;
                for (int $$31 = 0; $$30 < 2 && $$31 < 5; ++$$31) {
                    int $$32 = p_228673_.m_188503_(6);
                    if (!$$29.f_228938_[$$32]) continue;
                    int $$33 = Direction.m_122376_($$32).m_122424_().m_122411_();
                    $$29.f_228938_[$$32] = false;
                    $$29.f_228937_[$$32].f_228938_[$$33] = false;
                    if ($$29.m_228945_($$28++) && $$29.f_228937_[$$32].m_228945_($$28++)) {
                        ++$$30;
                        continue;
                    }
                    $$29.f_228938_[$$32] = true;
                    $$29.f_228937_[$$32].f_228938_[$$33] = true;
                }
            }
            $$26.add((Object)$$23);
            $$26.add((Object)$$24);
            $$26.add((Object)$$25);
            return $$26;
        }

        @Override
        public void m_213694_(WorldGenLevel p_228659_, StructureManager p_228660_, ChunkGenerator p_228661_, RandomSource p_228662_, BoundingBox p_228663_, ChunkPos p_228664_, BlockPos p_228665_) {
            int $$7 = Math.max(p_228659_.m_5736_(), 64) - this.f_73383_.m_162396_();
            this.m_228880_(p_228659_, p_228663_, 0, 0, 0, 58, $$7, 58);
            this.m_228666_(false, 0, p_228659_, p_228662_, p_228663_);
            this.m_228666_(true, 33, p_228659_, p_228662_, p_228663_);
            this.m_228654_(p_228659_, p_228662_, p_228663_);
            this.m_228674_(p_228659_, p_228662_, p_228663_);
            this.m_228678_(p_228659_, p_228662_, p_228663_);
            this.m_228682_(p_228659_, p_228662_, p_228663_);
            this.m_228686_(p_228659_, p_228662_, p_228663_);
            this.m_228690_(p_228659_, p_228662_, p_228663_);
            for (int $$8 = 0; $$8 < 7; ++$$8) {
                int $$9 = 0;
                while ($$9 < 7) {
                    if ($$9 == 0 && $$8 == 3) {
                        $$9 = 6;
                    }
                    int $$10 = $$8 * 9;
                    int $$11 = $$9 * 9;
                    for (int $$12 = 0; $$12 < 4; ++$$12) {
                        for (int $$13 = 0; $$13 < 4; ++$$13) {
                            this.m_73434_(p_228659_, f_228805_, $$10 + $$12, 0, $$11 + $$13, p_228663_);
                            this.m_73528_(p_228659_, f_228805_, $$10 + $$12, -1, $$11 + $$13, p_228663_);
                        }
                    }
                    if ($$8 == 0 || $$8 == 6) {
                        ++$$9;
                        continue;
                    }
                    $$9 += 6;
                }
            }
            for (int $$14 = 0; $$14 < 5; ++$$14) {
                this.m_228880_(p_228659_, p_228663_, -1 - $$14, 0 + $$14 * 2, -1 - $$14, -1 - $$14, 23, 58 + $$14);
                this.m_228880_(p_228659_, p_228663_, 58 + $$14, 0 + $$14 * 2, -1 - $$14, 58 + $$14, 23, 58 + $$14);
                this.m_228880_(p_228659_, p_228663_, 0 - $$14, 0 + $$14 * 2, -1 - $$14, 57 + $$14, 23, -1 - $$14);
                this.m_228880_(p_228659_, p_228663_, 0 - $$14, 0 + $$14 * 2, 58 + $$14, 57 + $$14, 23, 58 + $$14);
            }
            for (OceanMonumentPiece $$15 : this.f_228645_) {
                if (!$$15.m_73547_().m_71049_(p_228663_)) continue;
                $$15.m_213694_(p_228659_, p_228660_, p_228661_, p_228662_, p_228663_, p_228664_, p_228665_);
            }
        }

        private void m_228666_(boolean p_228667_, int p_228668_, WorldGenLevel p_228669_, RandomSource p_228670_, BoundingBox p_228671_) {
            int $$5 = 24;
            if (this.m_228865_(p_228671_, p_228668_, 0, p_228668_ + 23, 20)) {
                this.m_73441_(p_228669_, p_228671_, p_228668_ + 0, 0, 0, p_228668_ + 24, 0, 20, f_228804_, f_228804_, false);
                this.m_228880_(p_228669_, p_228671_, p_228668_ + 0, 1, 0, p_228668_ + 24, 10, 20);
                for (int $$6 = 0; $$6 < 4; ++$$6) {
                    this.m_73441_(p_228669_, p_228671_, p_228668_ + $$6, $$6 + 1, $$6, p_228668_ + $$6, $$6 + 1, 20, f_228805_, f_228805_, false);
                    this.m_73441_(p_228669_, p_228671_, p_228668_ + $$6 + 7, $$6 + 5, $$6 + 7, p_228668_ + $$6 + 7, $$6 + 5, 20, f_228805_, f_228805_, false);
                    this.m_73441_(p_228669_, p_228671_, p_228668_ + 17 - $$6, $$6 + 5, $$6 + 7, p_228668_ + 17 - $$6, $$6 + 5, 20, f_228805_, f_228805_, false);
                    this.m_73441_(p_228669_, p_228671_, p_228668_ + 24 - $$6, $$6 + 1, $$6, p_228668_ + 24 - $$6, $$6 + 1, 20, f_228805_, f_228805_, false);
                    this.m_73441_(p_228669_, p_228671_, p_228668_ + $$6 + 1, $$6 + 1, $$6, p_228668_ + 23 - $$6, $$6 + 1, $$6, f_228805_, f_228805_, false);
                    this.m_73441_(p_228669_, p_228671_, p_228668_ + $$6 + 8, $$6 + 5, $$6 + 7, p_228668_ + 16 - $$6, $$6 + 5, $$6 + 7, f_228805_, f_228805_, false);
                }
                this.m_73441_(p_228669_, p_228671_, p_228668_ + 4, 4, 4, p_228668_ + 6, 4, 20, f_228804_, f_228804_, false);
                this.m_73441_(p_228669_, p_228671_, p_228668_ + 7, 4, 4, p_228668_ + 17, 4, 6, f_228804_, f_228804_, false);
                this.m_73441_(p_228669_, p_228671_, p_228668_ + 18, 4, 4, p_228668_ + 20, 4, 20, f_228804_, f_228804_, false);
                this.m_73441_(p_228669_, p_228671_, p_228668_ + 11, 8, 11, p_228668_ + 13, 8, 20, f_228804_, f_228804_, false);
                this.m_73434_(p_228669_, f_228807_, p_228668_ + 12, 9, 12, p_228671_);
                this.m_73434_(p_228669_, f_228807_, p_228668_ + 12, 9, 15, p_228671_);
                this.m_73434_(p_228669_, f_228807_, p_228668_ + 12, 9, 18, p_228671_);
                int $$7 = p_228668_ + (p_228667_ ? 19 : 5);
                int $$8 = p_228668_ + (p_228667_ ? 5 : 19);
                for (int $$9 = 20; $$9 >= 5; $$9 -= 3) {
                    this.m_73434_(p_228669_, f_228807_, $$7, 5, $$9, p_228671_);
                }
                for (int $$10 = 19; $$10 >= 7; $$10 -= 3) {
                    this.m_73434_(p_228669_, f_228807_, $$8, 5, $$10, p_228671_);
                }
                for (int $$11 = 0; $$11 < 4; ++$$11) {
                    int $$12 = p_228667_ ? p_228668_ + 24 - (17 - $$11 * 3) : p_228668_ + 17 - $$11 * 3;
                    this.m_73434_(p_228669_, f_228807_, $$12, 5, 5, p_228671_);
                }
                this.m_73434_(p_228669_, f_228807_, $$8, 5, 5, p_228671_);
                this.m_73441_(p_228669_, p_228671_, p_228668_ + 11, 1, 12, p_228668_ + 13, 7, 12, f_228804_, f_228804_, false);
                this.m_73441_(p_228669_, p_228671_, p_228668_ + 12, 1, 11, p_228668_ + 12, 7, 13, f_228804_, f_228804_, false);
            }
        }

        private void m_228654_(WorldGenLevel p_228655_, RandomSource p_228656_, BoundingBox p_228657_) {
            if (this.m_228865_(p_228657_, 22, 5, 35, 17)) {
                this.m_228880_(p_228655_, p_228657_, 25, 0, 0, 32, 8, 20);
                for (int $$3 = 0; $$3 < 4; ++$$3) {
                    this.m_73441_(p_228655_, p_228657_, 24, 2, 5 + $$3 * 4, 24, 4, 5 + $$3 * 4, f_228805_, f_228805_, false);
                    this.m_73441_(p_228655_, p_228657_, 22, 4, 5 + $$3 * 4, 23, 4, 5 + $$3 * 4, f_228805_, f_228805_, false);
                    this.m_73434_(p_228655_, f_228805_, 25, 5, 5 + $$3 * 4, p_228657_);
                    this.m_73434_(p_228655_, f_228805_, 26, 6, 5 + $$3 * 4, p_228657_);
                    this.m_73434_(p_228655_, f_228808_, 26, 5, 5 + $$3 * 4, p_228657_);
                    this.m_73441_(p_228655_, p_228657_, 33, 2, 5 + $$3 * 4, 33, 4, 5 + $$3 * 4, f_228805_, f_228805_, false);
                    this.m_73441_(p_228655_, p_228657_, 34, 4, 5 + $$3 * 4, 35, 4, 5 + $$3 * 4, f_228805_, f_228805_, false);
                    this.m_73434_(p_228655_, f_228805_, 32, 5, 5 + $$3 * 4, p_228657_);
                    this.m_73434_(p_228655_, f_228805_, 31, 6, 5 + $$3 * 4, p_228657_);
                    this.m_73434_(p_228655_, f_228808_, 31, 5, 5 + $$3 * 4, p_228657_);
                    this.m_73441_(p_228655_, p_228657_, 27, 6, 5 + $$3 * 4, 30, 6, 5 + $$3 * 4, f_228804_, f_228804_, false);
                }
            }
        }

        private void m_228674_(WorldGenLevel p_228675_, RandomSource p_228676_, BoundingBox p_228677_) {
            if (this.m_228865_(p_228677_, 15, 20, 42, 21)) {
                this.m_73441_(p_228675_, p_228677_, 15, 0, 21, 42, 0, 21, f_228804_, f_228804_, false);
                this.m_228880_(p_228675_, p_228677_, 26, 1, 21, 31, 3, 21);
                this.m_73441_(p_228675_, p_228677_, 21, 12, 21, 36, 12, 21, f_228804_, f_228804_, false);
                this.m_73441_(p_228675_, p_228677_, 17, 11, 21, 40, 11, 21, f_228804_, f_228804_, false);
                this.m_73441_(p_228675_, p_228677_, 16, 10, 21, 41, 10, 21, f_228804_, f_228804_, false);
                this.m_73441_(p_228675_, p_228677_, 15, 7, 21, 42, 9, 21, f_228804_, f_228804_, false);
                this.m_73441_(p_228675_, p_228677_, 16, 6, 21, 41, 6, 21, f_228804_, f_228804_, false);
                this.m_73441_(p_228675_, p_228677_, 17, 5, 21, 40, 5, 21, f_228804_, f_228804_, false);
                this.m_73441_(p_228675_, p_228677_, 21, 4, 21, 36, 4, 21, f_228804_, f_228804_, false);
                this.m_73441_(p_228675_, p_228677_, 22, 3, 21, 26, 3, 21, f_228804_, f_228804_, false);
                this.m_73441_(p_228675_, p_228677_, 31, 3, 21, 35, 3, 21, f_228804_, f_228804_, false);
                this.m_73441_(p_228675_, p_228677_, 23, 2, 21, 25, 2, 21, f_228804_, f_228804_, false);
                this.m_73441_(p_228675_, p_228677_, 32, 2, 21, 34, 2, 21, f_228804_, f_228804_, false);
                this.m_73441_(p_228675_, p_228677_, 28, 4, 20, 29, 4, 21, f_228805_, f_228805_, false);
                this.m_73434_(p_228675_, f_228805_, 27, 3, 21, p_228677_);
                this.m_73434_(p_228675_, f_228805_, 30, 3, 21, p_228677_);
                this.m_73434_(p_228675_, f_228805_, 26, 2, 21, p_228677_);
                this.m_73434_(p_228675_, f_228805_, 31, 2, 21, p_228677_);
                this.m_73434_(p_228675_, f_228805_, 25, 1, 21, p_228677_);
                this.m_73434_(p_228675_, f_228805_, 32, 1, 21, p_228677_);
                for (int $$3 = 0; $$3 < 7; ++$$3) {
                    this.m_73434_(p_228675_, f_228806_, 28 - $$3, 6 + $$3, 21, p_228677_);
                    this.m_73434_(p_228675_, f_228806_, 29 + $$3, 6 + $$3, 21, p_228677_);
                }
                for (int $$4 = 0; $$4 < 4; ++$$4) {
                    this.m_73434_(p_228675_, f_228806_, 28 - $$4, 9 + $$4, 21, p_228677_);
                    this.m_73434_(p_228675_, f_228806_, 29 + $$4, 9 + $$4, 21, p_228677_);
                }
                this.m_73434_(p_228675_, f_228806_, 28, 12, 21, p_228677_);
                this.m_73434_(p_228675_, f_228806_, 29, 12, 21, p_228677_);
                for (int $$5 = 0; $$5 < 3; ++$$5) {
                    this.m_73434_(p_228675_, f_228806_, 22 - $$5 * 2, 8, 21, p_228677_);
                    this.m_73434_(p_228675_, f_228806_, 22 - $$5 * 2, 9, 21, p_228677_);
                    this.m_73434_(p_228675_, f_228806_, 35 + $$5 * 2, 8, 21, p_228677_);
                    this.m_73434_(p_228675_, f_228806_, 35 + $$5 * 2, 9, 21, p_228677_);
                }
                this.m_228880_(p_228675_, p_228677_, 15, 13, 21, 42, 15, 21);
                this.m_228880_(p_228675_, p_228677_, 15, 1, 21, 15, 6, 21);
                this.m_228880_(p_228675_, p_228677_, 16, 1, 21, 16, 5, 21);
                this.m_228880_(p_228675_, p_228677_, 17, 1, 21, 20, 4, 21);
                this.m_228880_(p_228675_, p_228677_, 21, 1, 21, 21, 3, 21);
                this.m_228880_(p_228675_, p_228677_, 22, 1, 21, 22, 2, 21);
                this.m_228880_(p_228675_, p_228677_, 23, 1, 21, 24, 1, 21);
                this.m_228880_(p_228675_, p_228677_, 42, 1, 21, 42, 6, 21);
                this.m_228880_(p_228675_, p_228677_, 41, 1, 21, 41, 5, 21);
                this.m_228880_(p_228675_, p_228677_, 37, 1, 21, 40, 4, 21);
                this.m_228880_(p_228675_, p_228677_, 36, 1, 21, 36, 3, 21);
                this.m_228880_(p_228675_, p_228677_, 33, 1, 21, 34, 1, 21);
                this.m_228880_(p_228675_, p_228677_, 35, 1, 21, 35, 2, 21);
            }
        }

        private void m_228678_(WorldGenLevel p_228679_, RandomSource p_228680_, BoundingBox p_228681_) {
            if (this.m_228865_(p_228681_, 21, 21, 36, 36)) {
                this.m_73441_(p_228679_, p_228681_, 21, 0, 22, 36, 0, 36, f_228804_, f_228804_, false);
                this.m_228880_(p_228679_, p_228681_, 21, 1, 22, 36, 23, 36);
                for (int $$3 = 0; $$3 < 4; ++$$3) {
                    this.m_73441_(p_228679_, p_228681_, 21 + $$3, 13 + $$3, 21 + $$3, 36 - $$3, 13 + $$3, 21 + $$3, f_228805_, f_228805_, false);
                    this.m_73441_(p_228679_, p_228681_, 21 + $$3, 13 + $$3, 36 - $$3, 36 - $$3, 13 + $$3, 36 - $$3, f_228805_, f_228805_, false);
                    this.m_73441_(p_228679_, p_228681_, 21 + $$3, 13 + $$3, 22 + $$3, 21 + $$3, 13 + $$3, 35 - $$3, f_228805_, f_228805_, false);
                    this.m_73441_(p_228679_, p_228681_, 36 - $$3, 13 + $$3, 22 + $$3, 36 - $$3, 13 + $$3, 35 - $$3, f_228805_, f_228805_, false);
                }
                this.m_73441_(p_228679_, p_228681_, 25, 16, 25, 32, 16, 32, f_228804_, f_228804_, false);
                this.m_73441_(p_228679_, p_228681_, 25, 17, 25, 25, 19, 25, f_228805_, f_228805_, false);
                this.m_73441_(p_228679_, p_228681_, 32, 17, 25, 32, 19, 25, f_228805_, f_228805_, false);
                this.m_73441_(p_228679_, p_228681_, 25, 17, 32, 25, 19, 32, f_228805_, f_228805_, false);
                this.m_73441_(p_228679_, p_228681_, 32, 17, 32, 32, 19, 32, f_228805_, f_228805_, false);
                this.m_73434_(p_228679_, f_228805_, 26, 20, 26, p_228681_);
                this.m_73434_(p_228679_, f_228805_, 27, 21, 27, p_228681_);
                this.m_73434_(p_228679_, f_228808_, 27, 20, 27, p_228681_);
                this.m_73434_(p_228679_, f_228805_, 26, 20, 31, p_228681_);
                this.m_73434_(p_228679_, f_228805_, 27, 21, 30, p_228681_);
                this.m_73434_(p_228679_, f_228808_, 27, 20, 30, p_228681_);
                this.m_73434_(p_228679_, f_228805_, 31, 20, 31, p_228681_);
                this.m_73434_(p_228679_, f_228805_, 30, 21, 30, p_228681_);
                this.m_73434_(p_228679_, f_228808_, 30, 20, 30, p_228681_);
                this.m_73434_(p_228679_, f_228805_, 31, 20, 26, p_228681_);
                this.m_73434_(p_228679_, f_228805_, 30, 21, 27, p_228681_);
                this.m_73434_(p_228679_, f_228808_, 30, 20, 27, p_228681_);
                this.m_73441_(p_228679_, p_228681_, 28, 21, 27, 29, 21, 27, f_228804_, f_228804_, false);
                this.m_73441_(p_228679_, p_228681_, 27, 21, 28, 27, 21, 29, f_228804_, f_228804_, false);
                this.m_73441_(p_228679_, p_228681_, 28, 21, 30, 29, 21, 30, f_228804_, f_228804_, false);
                this.m_73441_(p_228679_, p_228681_, 30, 21, 28, 30, 21, 29, f_228804_, f_228804_, false);
            }
        }

        private void m_228682_(WorldGenLevel p_228683_, RandomSource p_228684_, BoundingBox p_228685_) {
            if (this.m_228865_(p_228685_, 0, 21, 6, 58)) {
                this.m_73441_(p_228683_, p_228685_, 0, 0, 21, 6, 0, 57, f_228804_, f_228804_, false);
                this.m_228880_(p_228683_, p_228685_, 0, 1, 21, 6, 7, 57);
                this.m_73441_(p_228683_, p_228685_, 4, 4, 21, 6, 4, 53, f_228804_, f_228804_, false);
                for (int $$3 = 0; $$3 < 4; ++$$3) {
                    this.m_73441_(p_228683_, p_228685_, $$3, $$3 + 1, 21, $$3, $$3 + 1, 57 - $$3, f_228805_, f_228805_, false);
                }
                for (int $$4 = 23; $$4 < 53; $$4 += 3) {
                    this.m_73434_(p_228683_, f_228807_, 5, 5, $$4, p_228685_);
                }
                this.m_73434_(p_228683_, f_228807_, 5, 5, 52, p_228685_);
                for (int $$5 = 0; $$5 < 4; ++$$5) {
                    this.m_73441_(p_228683_, p_228685_, $$5, $$5 + 1, 21, $$5, $$5 + 1, 57 - $$5, f_228805_, f_228805_, false);
                }
                this.m_73441_(p_228683_, p_228685_, 4, 1, 52, 6, 3, 52, f_228804_, f_228804_, false);
                this.m_73441_(p_228683_, p_228685_, 5, 1, 51, 5, 3, 53, f_228804_, f_228804_, false);
            }
            if (this.m_228865_(p_228685_, 51, 21, 58, 58)) {
                this.m_73441_(p_228683_, p_228685_, 51, 0, 21, 57, 0, 57, f_228804_, f_228804_, false);
                this.m_228880_(p_228683_, p_228685_, 51, 1, 21, 57, 7, 57);
                this.m_73441_(p_228683_, p_228685_, 51, 4, 21, 53, 4, 53, f_228804_, f_228804_, false);
                for (int $$6 = 0; $$6 < 4; ++$$6) {
                    this.m_73441_(p_228683_, p_228685_, 57 - $$6, $$6 + 1, 21, 57 - $$6, $$6 + 1, 57 - $$6, f_228805_, f_228805_, false);
                }
                for (int $$7 = 23; $$7 < 53; $$7 += 3) {
                    this.m_73434_(p_228683_, f_228807_, 52, 5, $$7, p_228685_);
                }
                this.m_73434_(p_228683_, f_228807_, 52, 5, 52, p_228685_);
                this.m_73441_(p_228683_, p_228685_, 51, 1, 52, 53, 3, 52, f_228804_, f_228804_, false);
                this.m_73441_(p_228683_, p_228685_, 52, 1, 51, 52, 3, 53, f_228804_, f_228804_, false);
            }
            if (this.m_228865_(p_228685_, 0, 51, 57, 57)) {
                this.m_73441_(p_228683_, p_228685_, 7, 0, 51, 50, 0, 57, f_228804_, f_228804_, false);
                this.m_228880_(p_228683_, p_228685_, 7, 1, 51, 50, 10, 57);
                for (int $$8 = 0; $$8 < 4; ++$$8) {
                    this.m_73441_(p_228683_, p_228685_, $$8 + 1, $$8 + 1, 57 - $$8, 56 - $$8, $$8 + 1, 57 - $$8, f_228805_, f_228805_, false);
                }
            }
        }

        private void m_228686_(WorldGenLevel p_228687_, RandomSource p_228688_, BoundingBox p_228689_) {
            if (this.m_228865_(p_228689_, 7, 21, 13, 50)) {
                this.m_73441_(p_228687_, p_228689_, 7, 0, 21, 13, 0, 50, f_228804_, f_228804_, false);
                this.m_228880_(p_228687_, p_228689_, 7, 1, 21, 13, 10, 50);
                this.m_73441_(p_228687_, p_228689_, 11, 8, 21, 13, 8, 53, f_228804_, f_228804_, false);
                for (int $$3 = 0; $$3 < 4; ++$$3) {
                    this.m_73441_(p_228687_, p_228689_, $$3 + 7, $$3 + 5, 21, $$3 + 7, $$3 + 5, 54, f_228805_, f_228805_, false);
                }
                for (int $$4 = 21; $$4 <= 45; $$4 += 3) {
                    this.m_73434_(p_228687_, f_228807_, 12, 9, $$4, p_228689_);
                }
            }
            if (this.m_228865_(p_228689_, 44, 21, 50, 54)) {
                this.m_73441_(p_228687_, p_228689_, 44, 0, 21, 50, 0, 50, f_228804_, f_228804_, false);
                this.m_228880_(p_228687_, p_228689_, 44, 1, 21, 50, 10, 50);
                this.m_73441_(p_228687_, p_228689_, 44, 8, 21, 46, 8, 53, f_228804_, f_228804_, false);
                for (int $$5 = 0; $$5 < 4; ++$$5) {
                    this.m_73441_(p_228687_, p_228689_, 50 - $$5, $$5 + 5, 21, 50 - $$5, $$5 + 5, 54, f_228805_, f_228805_, false);
                }
                for (int $$6 = 21; $$6 <= 45; $$6 += 3) {
                    this.m_73434_(p_228687_, f_228807_, 45, 9, $$6, p_228689_);
                }
            }
            if (this.m_228865_(p_228689_, 8, 44, 49, 54)) {
                this.m_73441_(p_228687_, p_228689_, 14, 0, 44, 43, 0, 50, f_228804_, f_228804_, false);
                this.m_228880_(p_228687_, p_228689_, 14, 1, 44, 43, 10, 50);
                for (int $$7 = 12; $$7 <= 45; $$7 += 3) {
                    this.m_73434_(p_228687_, f_228807_, $$7, 9, 45, p_228689_);
                    this.m_73434_(p_228687_, f_228807_, $$7, 9, 52, p_228689_);
                    if ($$7 != 12 && $$7 != 18 && $$7 != 24 && $$7 != 33 && $$7 != 39 && $$7 != 45) continue;
                    this.m_73434_(p_228687_, f_228807_, $$7, 9, 47, p_228689_);
                    this.m_73434_(p_228687_, f_228807_, $$7, 9, 50, p_228689_);
                    this.m_73434_(p_228687_, f_228807_, $$7, 10, 45, p_228689_);
                    this.m_73434_(p_228687_, f_228807_, $$7, 10, 46, p_228689_);
                    this.m_73434_(p_228687_, f_228807_, $$7, 10, 51, p_228689_);
                    this.m_73434_(p_228687_, f_228807_, $$7, 10, 52, p_228689_);
                    this.m_73434_(p_228687_, f_228807_, $$7, 11, 47, p_228689_);
                    this.m_73434_(p_228687_, f_228807_, $$7, 11, 50, p_228689_);
                    this.m_73434_(p_228687_, f_228807_, $$7, 12, 48, p_228689_);
                    this.m_73434_(p_228687_, f_228807_, $$7, 12, 49, p_228689_);
                }
                for (int $$8 = 0; $$8 < 3; ++$$8) {
                    this.m_73441_(p_228687_, p_228689_, 8 + $$8, 5 + $$8, 54, 49 - $$8, 5 + $$8, 54, f_228804_, f_228804_, false);
                }
                this.m_73441_(p_228687_, p_228689_, 11, 8, 54, 46, 8, 54, f_228805_, f_228805_, false);
                this.m_73441_(p_228687_, p_228689_, 14, 8, 44, 43, 8, 53, f_228804_, f_228804_, false);
            }
        }

        private void m_228690_(WorldGenLevel p_228691_, RandomSource p_228692_, BoundingBox p_228693_) {
            if (this.m_228865_(p_228693_, 14, 21, 20, 43)) {
                this.m_73441_(p_228691_, p_228693_, 14, 0, 21, 20, 0, 43, f_228804_, f_228804_, false);
                this.m_228880_(p_228691_, p_228693_, 14, 1, 22, 20, 14, 43);
                this.m_73441_(p_228691_, p_228693_, 18, 12, 22, 20, 12, 39, f_228804_, f_228804_, false);
                this.m_73441_(p_228691_, p_228693_, 18, 12, 21, 20, 12, 21, f_228805_, f_228805_, false);
                for (int $$3 = 0; $$3 < 4; ++$$3) {
                    this.m_73441_(p_228691_, p_228693_, $$3 + 14, $$3 + 9, 21, $$3 + 14, $$3 + 9, 43 - $$3, f_228805_, f_228805_, false);
                }
                for (int $$4 = 23; $$4 <= 39; $$4 += 3) {
                    this.m_73434_(p_228691_, f_228807_, 19, 13, $$4, p_228693_);
                }
            }
            if (this.m_228865_(p_228693_, 37, 21, 43, 43)) {
                this.m_73441_(p_228691_, p_228693_, 37, 0, 21, 43, 0, 43, f_228804_, f_228804_, false);
                this.m_228880_(p_228691_, p_228693_, 37, 1, 22, 43, 14, 43);
                this.m_73441_(p_228691_, p_228693_, 37, 12, 22, 39, 12, 39, f_228804_, f_228804_, false);
                this.m_73441_(p_228691_, p_228693_, 37, 12, 21, 39, 12, 21, f_228805_, f_228805_, false);
                for (int $$5 = 0; $$5 < 4; ++$$5) {
                    this.m_73441_(p_228691_, p_228693_, 43 - $$5, $$5 + 9, 21, 43 - $$5, $$5 + 9, 43 - $$5, f_228805_, f_228805_, false);
                }
                for (int $$6 = 23; $$6 <= 39; $$6 += 3) {
                    this.m_73434_(p_228691_, f_228807_, 38, 13, $$6, p_228693_);
                }
            }
            if (this.m_228865_(p_228693_, 15, 37, 42, 43)) {
                this.m_73441_(p_228691_, p_228693_, 21, 0, 37, 36, 0, 43, f_228804_, f_228804_, false);
                this.m_228880_(p_228691_, p_228693_, 21, 1, 37, 36, 14, 43);
                this.m_73441_(p_228691_, p_228693_, 21, 12, 37, 36, 12, 39, f_228804_, f_228804_, false);
                for (int $$7 = 0; $$7 < 4; ++$$7) {
                    this.m_73441_(p_228691_, p_228693_, 15 + $$7, $$7 + 9, 43 - $$7, 42 - $$7, $$7 + 9, 43 - $$7, f_228805_, f_228805_, false);
                }
                for (int $$8 = 21; $$8 <= 36; $$8 += 3) {
                    this.m_73434_(p_228691_, f_228807_, $$8, 13, 38, p_228693_);
                }
            }
        }
    }

    protected static abstract class OceanMonumentPiece
    extends StructurePiece {
        protected static final BlockState f_228804_ = Blocks.f_50377_.m_49966_();
        protected static final BlockState f_228805_ = Blocks.f_50378_.m_49966_();
        protected static final BlockState f_228806_ = Blocks.f_50379_.m_49966_();
        protected static final BlockState f_228807_ = f_228805_;
        protected static final BlockState f_228808_ = Blocks.f_50386_.m_49966_();
        protected static final boolean f_228809_ = true;
        protected static final BlockState f_228810_ = Blocks.f_49990_.m_49966_();
        protected static final Set<Block> f_228811_ = ImmutableSet.builder().add((Object)Blocks.f_50126_).add((Object)Blocks.f_50354_).add((Object)Blocks.f_50568_).add((Object)f_228810_.m_60734_()).build();
        protected static final int f_228812_ = 8;
        protected static final int f_228813_ = 8;
        protected static final int f_228814_ = 4;
        protected static final int f_228815_ = 5;
        protected static final int f_228816_ = 5;
        protected static final int f_228817_ = 3;
        protected static final int f_228818_ = 25;
        protected static final int f_228819_ = 75;
        protected static final int f_228820_ = OceanMonumentPiece.m_228889_(2, 0, 0);
        protected static final int f_228821_ = OceanMonumentPiece.m_228889_(2, 2, 0);
        protected static final int f_228822_ = OceanMonumentPiece.m_228889_(0, 1, 0);
        protected static final int f_228823_ = OceanMonumentPiece.m_228889_(4, 1, 0);
        protected static final int f_228824_ = 1001;
        protected static final int f_228825_ = 1002;
        protected static final int f_228802_ = 1003;
        protected RoomDefinition f_228803_;

        protected static int m_228889_(int p_228890_, int p_228891_, int p_228892_) {
            return p_228891_ * 25 + p_228892_ * 5 + p_228890_;
        }

        public OceanMonumentPiece(StructurePieceType p_228836_, Direction p_228837_, int p_228838_, BoundingBox p_228839_) {
            super(p_228836_, p_228838_, p_228839_);
            this.m_73519_(p_228837_);
        }

        protected OceanMonumentPiece(StructurePieceType p_228828_, int p_228829_, Direction p_228830_, RoomDefinition p_228831_, int p_228832_, int p_228833_, int p_228834_) {
            super(p_228828_, p_228829_, OceanMonumentPiece.m_228874_(p_228830_, p_228831_, p_228832_, p_228833_, p_228834_));
            this.m_73519_(p_228830_);
            this.f_228803_ = p_228831_;
        }

        private static BoundingBox m_228874_(Direction p_228875_, RoomDefinition p_228876_, int p_228877_, int p_228878_, int p_228879_) {
            int $$5 = p_228876_.f_228936_;
            int $$6 = $$5 % 5;
            int $$7 = $$5 / 5 % 5;
            int $$8 = $$5 / 25;
            BoundingBox $$9 = OceanMonumentPiece.m_163541_(0, 0, 0, p_228875_, p_228877_ * 8, p_228878_ * 4, p_228879_ * 8);
            switch (p_228875_) {
                case NORTH: {
                    $$9.m_162367_($$6 * 8, $$8 * 4, -($$7 + p_228879_) * 8 + 1);
                    break;
                }
                case SOUTH: {
                    $$9.m_162367_($$6 * 8, $$8 * 4, $$7 * 8);
                    break;
                }
                case WEST: {
                    $$9.m_162367_(-($$7 + p_228879_) * 8 + 1, $$8 * 4, $$6 * 8);
                    break;
                }
                default: {
                    $$9.m_162367_($$7 * 8, $$8 * 4, $$6 * 8);
                }
            }
            return $$9;
        }

        public OceanMonumentPiece(StructurePieceType p_228841_, CompoundTag p_228842_) {
            super(p_228841_, p_228842_);
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_228872_, CompoundTag p_228873_) {
        }

        protected void m_228880_(WorldGenLevel p_228881_, BoundingBox p_228882_, int p_228883_, int p_228884_, int p_228885_, int p_228886_, int p_228887_, int p_228888_) {
            for (int $$8 = p_228884_; $$8 <= p_228887_; ++$$8) {
                for (int $$9 = p_228883_; $$9 <= p_228886_; ++$$9) {
                    for (int $$10 = p_228885_; $$10 <= p_228888_; ++$$10) {
                        BlockState $$11 = this.m_73398_(p_228881_, $$9, $$8, $$10, p_228882_);
                        if (f_228811_.contains($$11.m_60734_())) continue;
                        if (this.m_73544_($$8) >= p_228881_.m_5736_() && $$11 != f_228810_) {
                            this.m_73434_(p_228881_, Blocks.f_50016_.m_49966_(), $$9, $$8, $$10, p_228882_);
                            continue;
                        }
                        this.m_73434_(p_228881_, f_228810_, $$9, $$8, $$10, p_228882_);
                    }
                }
            }
        }

        protected void m_228859_(WorldGenLevel p_228860_, BoundingBox p_228861_, int p_228862_, int p_228863_, boolean p_228864_) {
            if (p_228864_) {
                this.m_73441_(p_228860_, p_228861_, p_228862_ + 0, 0, p_228863_ + 0, p_228862_ + 2, 0, p_228863_ + 8 - 1, f_228804_, f_228804_, false);
                this.m_73441_(p_228860_, p_228861_, p_228862_ + 5, 0, p_228863_ + 0, p_228862_ + 8 - 1, 0, p_228863_ + 8 - 1, f_228804_, f_228804_, false);
                this.m_73441_(p_228860_, p_228861_, p_228862_ + 3, 0, p_228863_ + 0, p_228862_ + 4, 0, p_228863_ + 2, f_228804_, f_228804_, false);
                this.m_73441_(p_228860_, p_228861_, p_228862_ + 3, 0, p_228863_ + 5, p_228862_ + 4, 0, p_228863_ + 8 - 1, f_228804_, f_228804_, false);
                this.m_73441_(p_228860_, p_228861_, p_228862_ + 3, 0, p_228863_ + 2, p_228862_ + 4, 0, p_228863_ + 2, f_228805_, f_228805_, false);
                this.m_73441_(p_228860_, p_228861_, p_228862_ + 3, 0, p_228863_ + 5, p_228862_ + 4, 0, p_228863_ + 5, f_228805_, f_228805_, false);
                this.m_73441_(p_228860_, p_228861_, p_228862_ + 2, 0, p_228863_ + 3, p_228862_ + 2, 0, p_228863_ + 4, f_228805_, f_228805_, false);
                this.m_73441_(p_228860_, p_228861_, p_228862_ + 5, 0, p_228863_ + 3, p_228862_ + 5, 0, p_228863_ + 4, f_228805_, f_228805_, false);
            } else {
                this.m_73441_(p_228860_, p_228861_, p_228862_ + 0, 0, p_228863_ + 0, p_228862_ + 8 - 1, 0, p_228863_ + 8 - 1, f_228804_, f_228804_, false);
            }
        }

        protected void m_228849_(WorldGenLevel p_228850_, BoundingBox p_228851_, int p_228852_, int p_228853_, int p_228854_, int p_228855_, int p_228856_, int p_228857_, BlockState p_228858_) {
            for (int $$9 = p_228853_; $$9 <= p_228856_; ++$$9) {
                for (int $$10 = p_228852_; $$10 <= p_228855_; ++$$10) {
                    for (int $$11 = p_228854_; $$11 <= p_228857_; ++$$11) {
                        if (this.m_73398_(p_228850_, $$10, $$9, $$11, p_228851_) != f_228810_) continue;
                        this.m_73434_(p_228850_, p_228858_, $$10, $$9, $$11, p_228851_);
                    }
                }
            }
        }

        protected boolean m_228865_(BoundingBox p_228866_, int p_228867_, int p_228868_, int p_228869_, int p_228870_) {
            int $$5 = this.m_73392_(p_228867_, p_228868_);
            int $$6 = this.m_73525_(p_228867_, p_228868_);
            int $$7 = this.m_73392_(p_228869_, p_228870_);
            int $$8 = this.m_73525_(p_228869_, p_228870_);
            return p_228866_.m_71019_(Math.min($$5, $$7), Math.min($$6, $$8), Math.max($$5, $$7), Math.max($$6, $$8));
        }

        protected boolean m_228843_(WorldGenLevel p_228844_, BoundingBox p_228845_, int p_228846_, int p_228847_, int p_228848_) {
            BlockPos.MutableBlockPos $$5 = this.m_163582_(p_228846_, p_228847_, p_228848_);
            if (p_228845_.m_71051_($$5)) {
                ElderGuardian $$6 = EntityType.f_20563_.m_20615_(p_228844_.m_6018_());
                $$6.m_5634_($$6.m_21233_());
                $$6.m_7678_((double)$$5.m_123341_() + 0.5, $$5.m_123342_(), (double)$$5.m_123343_() + 0.5, 0.0f, 0.0f);
                $$6.m_6518_(p_228844_, p_228844_.m_6436_($$6.m_20183_()), MobSpawnType.STRUCTURE, null, null);
                p_228844_.m_47205_($$6);
                return true;
            }
            return false;
        }
    }
}

