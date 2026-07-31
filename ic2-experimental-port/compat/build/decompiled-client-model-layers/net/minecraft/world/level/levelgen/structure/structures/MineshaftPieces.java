/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicOps;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftStructure;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.slf4j.Logger;

public class MineshaftPieces {
    static final Logger f_227697_ = LogUtils.getLogger();
    private static final int f_227698_ = 3;
    private static final int f_227699_ = 3;
    private static final int f_227700_ = 5;
    private static final int f_227701_ = 20;
    private static final int f_227702_ = 50;
    private static final int f_227703_ = 8;
    public static final int f_227696_ = 50;

    private static MineShaftPiece m_227715_(StructurePieceAccessor p_227716_, RandomSource p_227717_, int p_227718_, int p_227719_, int p_227720_, @Nullable Direction p_227721_, int p_227722_, MineshaftStructure.Type p_227723_) {
        int $$8 = p_227717_.m_188503_(100);
        if ($$8 >= 80) {
            BoundingBox $$9 = MineShaftCrossing.m_227854_(p_227716_, p_227717_, p_227718_, p_227719_, p_227720_, p_227721_);
            if ($$9 != null) {
                return new MineShaftCrossing(p_227722_, $$9, p_227721_, p_227723_);
            }
        } else if ($$8 >= 70) {
            BoundingBox $$10 = MineShaftStairs.m_227950_(p_227716_, p_227717_, p_227718_, p_227719_, p_227720_, p_227721_);
            if ($$10 != null) {
                return new MineShaftStairs(p_227722_, $$10, p_227721_, p_227723_);
            }
        } else {
            BoundingBox $$11 = MineShaftCorridor.m_227798_(p_227716_, p_227717_, p_227718_, p_227719_, p_227720_, p_227721_);
            if ($$11 != null) {
                return new MineShaftCorridor(p_227722_, p_227717_, $$11, p_227721_, p_227723_);
            }
        }
        return null;
    }

    static MineShaftPiece m_227706_(StructurePiece p_227707_, StructurePieceAccessor p_227708_, RandomSource p_227709_, int p_227710_, int p_227711_, int p_227712_, Direction p_227713_, int p_227714_) {
        if (p_227714_ > 8) {
            return null;
        }
        if (Math.abs(p_227710_ - p_227707_.m_73547_().m_162395_()) > 80 || Math.abs(p_227712_ - p_227707_.m_73547_().m_162398_()) > 80) {
            return null;
        }
        MineshaftStructure.Type $$8 = ((MineShaftPiece)p_227707_).f_227864_;
        MineShaftPiece $$9 = MineshaftPieces.m_227715_(p_227708_, p_227709_, p_227710_, p_227711_, p_227712_, p_227713_, p_227714_ + 1, $$8);
        if ($$9 != null) {
            p_227708_.m_142679_($$9);
            $$9.m_214092_(p_227707_, p_227708_, p_227709_);
        }
        return $$9;
    }

    public static class MineShaftCrossing
    extends MineShaftPiece {
        private final Direction f_227826_;
        private final boolean f_227827_;

        public MineShaftCrossing(CompoundTag p_227834_) {
            super(StructurePieceType.f_210126_, p_227834_);
            this.f_227827_ = p_227834_.m_128471_("tf");
            this.f_227826_ = Direction.m_122407_(p_227834_.m_128451_("D"));
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_227862_, CompoundTag p_227863_) {
            super.m_183620_(p_227862_, p_227863_);
            p_227863_.m_128379_("tf", this.f_227827_);
            p_227863_.m_128405_("D", this.f_227826_.m_122416_());
        }

        public MineShaftCrossing(int p_227829_, BoundingBox p_227830_, @Nullable Direction p_227831_, MineshaftStructure.Type p_227832_) {
            super(StructurePieceType.f_210126_, p_227829_, p_227832_, p_227830_);
            this.f_227826_ = p_227831_;
            this.f_227827_ = p_227830_.m_71057_() > 3;
        }

        @Nullable
        public static BoundingBox m_227854_(StructurePieceAccessor p_227855_, RandomSource p_227856_, int p_227857_, int p_227858_, int p_227859_, Direction p_227860_) {
            BoundingBox $$11;
            int $$7;
            if (p_227856_.m_188503_(4) == 0) {
                int $$6 = 6;
            } else {
                $$7 = 2;
            }
            switch (p_227860_) {
                default: {
                    BoundingBox $$8 = new BoundingBox(-1, 0, -4, 3, $$7, 0);
                    break;
                }
                case SOUTH: {
                    BoundingBox $$9 = new BoundingBox(-1, 0, 0, 3, $$7, 4);
                    break;
                }
                case WEST: {
                    BoundingBox $$10 = new BoundingBox(-4, 0, -1, 0, $$7, 3);
                    break;
                }
                case EAST: {
                    $$11 = new BoundingBox(0, 0, -1, 4, $$7, 3);
                }
            }
            $$11.m_162367_(p_227857_, p_227858_, p_227859_);
            if (p_227855_.m_141921_($$11) != null) {
                return null;
            }
            return $$11;
        }

        @Override
        public void m_214092_(StructurePiece p_227851_, StructurePieceAccessor p_227852_, RandomSource p_227853_) {
            int $$3 = this.m_73548_();
            switch (this.f_227826_) {
                default: {
                    MineshaftPieces.m_227706_(p_227851_, p_227852_, p_227853_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() - 1, Direction.NORTH, $$3);
                    MineshaftPieces.m_227706_(p_227851_, p_227852_, p_227853_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, Direction.WEST, $$3);
                    MineshaftPieces.m_227706_(p_227851_, p_227852_, p_227853_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, Direction.EAST, $$3);
                    break;
                }
                case SOUTH: {
                    MineshaftPieces.m_227706_(p_227851_, p_227852_, p_227853_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162401_() + 1, Direction.SOUTH, $$3);
                    MineshaftPieces.m_227706_(p_227851_, p_227852_, p_227853_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, Direction.WEST, $$3);
                    MineshaftPieces.m_227706_(p_227851_, p_227852_, p_227853_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, Direction.EAST, $$3);
                    break;
                }
                case WEST: {
                    MineshaftPieces.m_227706_(p_227851_, p_227852_, p_227853_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() - 1, Direction.NORTH, $$3);
                    MineshaftPieces.m_227706_(p_227851_, p_227852_, p_227853_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162401_() + 1, Direction.SOUTH, $$3);
                    MineshaftPieces.m_227706_(p_227851_, p_227852_, p_227853_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, Direction.WEST, $$3);
                    break;
                }
                case EAST: {
                    MineshaftPieces.m_227706_(p_227851_, p_227852_, p_227853_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() - 1, Direction.NORTH, $$3);
                    MineshaftPieces.m_227706_(p_227851_, p_227852_, p_227853_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162401_() + 1, Direction.SOUTH, $$3);
                    MineshaftPieces.m_227706_(p_227851_, p_227852_, p_227853_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, Direction.EAST, $$3);
                }
            }
            if (this.f_227827_) {
                if (p_227853_.m_188499_()) {
                    MineshaftPieces.m_227706_(p_227851_, p_227852_, p_227853_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_() + 3 + 1, this.f_73383_.m_162398_() - 1, Direction.NORTH, $$3);
                }
                if (p_227853_.m_188499_()) {
                    MineshaftPieces.m_227706_(p_227851_, p_227852_, p_227853_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() + 3 + 1, this.f_73383_.m_162398_() + 1, Direction.WEST, $$3);
                }
                if (p_227853_.m_188499_()) {
                    MineshaftPieces.m_227706_(p_227851_, p_227852_, p_227853_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() + 3 + 1, this.f_73383_.m_162398_() + 1, Direction.EAST, $$3);
                }
                if (p_227853_.m_188499_()) {
                    MineshaftPieces.m_227706_(p_227851_, p_227852_, p_227853_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_() + 3 + 1, this.f_73383_.m_162401_() + 1, Direction.SOUTH, $$3);
                }
            }
        }

        @Override
        public void m_213694_(WorldGenLevel p_227836_, StructureManager p_227837_, ChunkGenerator p_227838_, RandomSource p_227839_, BoundingBox p_227840_, ChunkPos p_227841_, BlockPos p_227842_) {
            if (this.m_227881_(p_227836_, p_227840_)) {
                return;
            }
            BlockState $$7 = this.f_227864_.m_227994_();
            if (this.f_227827_) {
                this.m_73441_(p_227836_, p_227840_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_(), this.f_73383_.m_162399_() - 1, this.f_73383_.m_162396_() + 3 - 1, this.f_73383_.m_162401_(), f_73382_, f_73382_, false);
                this.m_73441_(p_227836_, p_227840_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, this.f_73383_.m_162399_(), this.f_73383_.m_162396_() + 3 - 1, this.f_73383_.m_162401_() - 1, f_73382_, f_73382_, false);
                this.m_73441_(p_227836_, p_227840_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162400_() - 2, this.f_73383_.m_162398_(), this.f_73383_.m_162399_() - 1, this.f_73383_.m_162400_(), this.f_73383_.m_162401_(), f_73382_, f_73382_, false);
                this.m_73441_(p_227836_, p_227840_, this.f_73383_.m_162395_(), this.f_73383_.m_162400_() - 2, this.f_73383_.m_162398_() + 1, this.f_73383_.m_162399_(), this.f_73383_.m_162400_(), this.f_73383_.m_162401_() - 1, f_73382_, f_73382_, false);
                this.m_73441_(p_227836_, p_227840_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_() + 3, this.f_73383_.m_162398_() + 1, this.f_73383_.m_162399_() - 1, this.f_73383_.m_162396_() + 3, this.f_73383_.m_162401_() - 1, f_73382_, f_73382_, false);
            } else {
                this.m_73441_(p_227836_, p_227840_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_(), this.f_73383_.m_162399_() - 1, this.f_73383_.m_162400_(), this.f_73383_.m_162401_(), f_73382_, f_73382_, false);
                this.m_73441_(p_227836_, p_227840_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, this.f_73383_.m_162399_(), this.f_73383_.m_162400_(), this.f_73383_.m_162401_() - 1, f_73382_, f_73382_, false);
            }
            this.m_227843_(p_227836_, p_227840_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, this.f_73383_.m_162400_());
            this.m_227843_(p_227836_, p_227840_, this.f_73383_.m_162395_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162401_() - 1, this.f_73383_.m_162400_());
            this.m_227843_(p_227836_, p_227840_, this.f_73383_.m_162399_() - 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() + 1, this.f_73383_.m_162400_());
            this.m_227843_(p_227836_, p_227840_, this.f_73383_.m_162399_() - 1, this.f_73383_.m_162396_(), this.f_73383_.m_162401_() - 1, this.f_73383_.m_162400_());
            int $$8 = this.f_73383_.m_162396_() - 1;
            for (int $$9 = this.f_73383_.m_162395_(); $$9 <= this.f_73383_.m_162399_(); ++$$9) {
                for (int $$10 = this.f_73383_.m_162398_(); $$10 <= this.f_73383_.m_162401_(); ++$$10) {
                    this.m_227890_(p_227836_, p_227840_, $$7, $$9, $$8, $$10);
                }
            }
        }

        private void m_227843_(WorldGenLevel p_227844_, BoundingBox p_227845_, int p_227846_, int p_227847_, int p_227848_, int p_227849_) {
            if (!this.m_73398_(p_227844_, p_227846_, p_227849_ + 1, p_227848_, p_227845_).m_60795_()) {
                this.m_73441_(p_227844_, p_227845_, p_227846_, p_227847_, p_227848_, p_227846_, p_227849_, p_227848_, this.f_227864_.m_227994_(), f_73382_, false);
            }
        }
    }

    public static class MineShaftStairs
    extends MineShaftPiece {
        public MineShaftStairs(int p_227932_, BoundingBox p_227933_, Direction p_227934_, MineshaftStructure.Type p_227935_) {
            super(StructurePieceType.f_210128_, p_227932_, p_227935_, p_227933_);
            this.m_73519_(p_227934_);
        }

        public MineShaftStairs(CompoundTag p_227937_) {
            super(StructurePieceType.f_210128_, p_227937_);
        }

        @Nullable
        public static BoundingBox m_227950_(StructurePieceAccessor p_227951_, RandomSource p_227952_, int p_227953_, int p_227954_, int p_227955_, Direction p_227956_) {
            BoundingBox $$9;
            switch (p_227956_) {
                default: {
                    BoundingBox $$6 = new BoundingBox(0, -5, -8, 2, 2, 0);
                    break;
                }
                case SOUTH: {
                    BoundingBox $$7 = new BoundingBox(0, -5, 0, 2, 2, 8);
                    break;
                }
                case WEST: {
                    BoundingBox $$8 = new BoundingBox(-8, -5, 0, 0, 2, 2);
                    break;
                }
                case EAST: {
                    $$9 = new BoundingBox(0, -5, 0, 8, 2, 2);
                }
            }
            $$9.m_162367_(p_227953_, p_227954_, p_227955_);
            if (p_227951_.m_141921_($$9) != null) {
                return null;
            }
            return $$9;
        }

        @Override
        public void m_214092_(StructurePiece p_227947_, StructurePieceAccessor p_227948_, RandomSource p_227949_) {
            int $$3 = this.m_73548_();
            Direction $$4 = this.m_73549_();
            if ($$4 != null) {
                switch ($$4) {
                    default: {
                        MineshaftPieces.m_227706_(p_227947_, p_227948_, p_227949_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_(), this.f_73383_.m_162398_() - 1, Direction.NORTH, $$3);
                        break;
                    }
                    case SOUTH: {
                        MineshaftPieces.m_227706_(p_227947_, p_227948_, p_227949_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_(), this.f_73383_.m_162401_() + 1, Direction.SOUTH, $$3);
                        break;
                    }
                    case WEST: {
                        MineshaftPieces.m_227706_(p_227947_, p_227948_, p_227949_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_(), Direction.WEST, $$3);
                        break;
                    }
                    case EAST: {
                        MineshaftPieces.m_227706_(p_227947_, p_227948_, p_227949_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_(), this.f_73383_.m_162398_(), Direction.EAST, $$3);
                    }
                }
            }
        }

        @Override
        public void m_213694_(WorldGenLevel p_227939_, StructureManager p_227940_, ChunkGenerator p_227941_, RandomSource p_227942_, BoundingBox p_227943_, ChunkPos p_227944_, BlockPos p_227945_) {
            if (this.m_227881_(p_227939_, p_227943_)) {
                return;
            }
            this.m_73441_(p_227939_, p_227943_, 0, 5, 0, 2, 7, 1, f_73382_, f_73382_, false);
            this.m_73441_(p_227939_, p_227943_, 0, 0, 7, 2, 2, 8, f_73382_, f_73382_, false);
            for (int $$7 = 0; $$7 < 5; ++$$7) {
                this.m_73441_(p_227939_, p_227943_, 0, 5 - $$7 - ($$7 < 4 ? 1 : 0), 2 + $$7, 2, 7 - $$7, 2 + $$7, f_73382_, f_73382_, false);
            }
        }
    }

    public static class MineShaftCorridor
    extends MineShaftPiece {
        private final boolean f_227726_;
        private final boolean f_227727_;
        private boolean f_227728_;
        private final int f_227729_;

        public MineShaftCorridor(CompoundTag p_227737_) {
            super(StructurePieceType.f_210121_, p_227737_);
            this.f_227726_ = p_227737_.m_128471_("hr");
            this.f_227727_ = p_227737_.m_128471_("sc");
            this.f_227728_ = p_227737_.m_128471_("hps");
            this.f_227729_ = p_227737_.m_128451_("Num");
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_227806_, CompoundTag p_227807_) {
            super.m_183620_(p_227806_, p_227807_);
            p_227807_.m_128379_("hr", this.f_227726_);
            p_227807_.m_128379_("sc", this.f_227727_);
            p_227807_.m_128379_("hps", this.f_227728_);
            p_227807_.m_128405_("Num", this.f_227729_);
        }

        public MineShaftCorridor(int p_227731_, RandomSource p_227732_, BoundingBox p_227733_, Direction p_227734_, MineshaftStructure.Type p_227735_) {
            super(StructurePieceType.f_210121_, p_227731_, p_227735_, p_227733_);
            this.m_73519_(p_227734_);
            this.f_227726_ = p_227732_.m_188503_(3) == 0;
            this.f_227727_ = !this.f_227726_ && p_227732_.m_188503_(23) == 0;
            this.f_227729_ = this.m_73549_().m_122434_() == Direction.Axis.Z ? p_227733_.m_71058_() / 5 : p_227733_.m_71056_() / 5;
        }

        @Nullable
        public static BoundingBox m_227798_(StructurePieceAccessor p_227799_, RandomSource p_227800_, int p_227801_, int p_227802_, int p_227803_, Direction p_227804_) {
            for (int $$6 = p_227800_.m_188503_(3) + 2; $$6 > 0; --$$6) {
                BoundingBox $$11;
                int $$7 = $$6 * 5;
                switch (p_227804_) {
                    default: {
                        BoundingBox $$8 = new BoundingBox(0, 0, -($$7 - 1), 2, 2, 0);
                        break;
                    }
                    case SOUTH: {
                        BoundingBox $$9 = new BoundingBox(0, 0, 0, 2, 2, $$7 - 1);
                        break;
                    }
                    case WEST: {
                        BoundingBox $$10 = new BoundingBox(-($$7 - 1), 0, 0, 0, 2, 2);
                        break;
                    }
                    case EAST: {
                        $$11 = new BoundingBox(0, 0, 0, $$7 - 1, 2, 2);
                    }
                }
                $$11.m_162367_(p_227801_, p_227802_, p_227803_);
                if (p_227799_.m_141921_($$11) != null) {
                    continue;
                }
                return $$11;
            }
            return null;
        }

        @Override
        public void m_214092_(StructurePiece p_227795_, StructurePieceAccessor p_227796_, RandomSource p_227797_) {
            block24: {
                int $$3 = this.m_73548_();
                int $$4 = p_227797_.m_188503_(4);
                Direction $$5 = this.m_73549_();
                if ($$5 != null) {
                    switch ($$5) {
                        default: {
                            if ($$4 <= 1) {
                                MineshaftPieces.m_227706_(p_227795_, p_227796_, p_227797_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_() - 1 + p_227797_.m_188503_(3), this.f_73383_.m_162398_() - 1, $$5, $$3);
                                break;
                            }
                            if ($$4 == 2) {
                                MineshaftPieces.m_227706_(p_227795_, p_227796_, p_227797_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() - 1 + p_227797_.m_188503_(3), this.f_73383_.m_162398_(), Direction.WEST, $$3);
                                break;
                            }
                            MineshaftPieces.m_227706_(p_227795_, p_227796_, p_227797_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() - 1 + p_227797_.m_188503_(3), this.f_73383_.m_162398_(), Direction.EAST, $$3);
                            break;
                        }
                        case SOUTH: {
                            if ($$4 <= 1) {
                                MineshaftPieces.m_227706_(p_227795_, p_227796_, p_227797_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_() - 1 + p_227797_.m_188503_(3), this.f_73383_.m_162401_() + 1, $$5, $$3);
                                break;
                            }
                            if ($$4 == 2) {
                                MineshaftPieces.m_227706_(p_227795_, p_227796_, p_227797_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() - 1 + p_227797_.m_188503_(3), this.f_73383_.m_162401_() - 3, Direction.WEST, $$3);
                                break;
                            }
                            MineshaftPieces.m_227706_(p_227795_, p_227796_, p_227797_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() - 1 + p_227797_.m_188503_(3), this.f_73383_.m_162401_() - 3, Direction.EAST, $$3);
                            break;
                        }
                        case WEST: {
                            if ($$4 <= 1) {
                                MineshaftPieces.m_227706_(p_227795_, p_227796_, p_227797_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() - 1 + p_227797_.m_188503_(3), this.f_73383_.m_162398_(), $$5, $$3);
                                break;
                            }
                            if ($$4 == 2) {
                                MineshaftPieces.m_227706_(p_227795_, p_227796_, p_227797_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_() - 1 + p_227797_.m_188503_(3), this.f_73383_.m_162398_() - 1, Direction.NORTH, $$3);
                                break;
                            }
                            MineshaftPieces.m_227706_(p_227795_, p_227796_, p_227797_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_() - 1 + p_227797_.m_188503_(3), this.f_73383_.m_162401_() + 1, Direction.SOUTH, $$3);
                            break;
                        }
                        case EAST: {
                            if ($$4 <= 1) {
                                MineshaftPieces.m_227706_(p_227795_, p_227796_, p_227797_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() - 1 + p_227797_.m_188503_(3), this.f_73383_.m_162398_(), $$5, $$3);
                                break;
                            }
                            if ($$4 == 2) {
                                MineshaftPieces.m_227706_(p_227795_, p_227796_, p_227797_, this.f_73383_.m_162399_() - 3, this.f_73383_.m_162396_() - 1 + p_227797_.m_188503_(3), this.f_73383_.m_162398_() - 1, Direction.NORTH, $$3);
                                break;
                            }
                            MineshaftPieces.m_227706_(p_227795_, p_227796_, p_227797_, this.f_73383_.m_162399_() - 3, this.f_73383_.m_162396_() - 1 + p_227797_.m_188503_(3), this.f_73383_.m_162401_() + 1, Direction.SOUTH, $$3);
                        }
                    }
                }
                if ($$3 >= 8) break block24;
                if ($$5 == Direction.NORTH || $$5 == Direction.SOUTH) {
                    int $$6 = this.f_73383_.m_162398_() + 3;
                    while ($$6 + 3 <= this.f_73383_.m_162401_()) {
                        int $$7 = p_227797_.m_188503_(5);
                        if ($$7 == 0) {
                            MineshaftPieces.m_227706_(p_227795_, p_227796_, p_227797_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_(), $$6, Direction.WEST, $$3 + 1);
                        } else if ($$7 == 1) {
                            MineshaftPieces.m_227706_(p_227795_, p_227796_, p_227797_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_(), $$6, Direction.EAST, $$3 + 1);
                        }
                        $$6 += 5;
                    }
                } else {
                    int $$8 = this.f_73383_.m_162395_() + 3;
                    while ($$8 + 3 <= this.f_73383_.m_162399_()) {
                        int $$9 = p_227797_.m_188503_(5);
                        if ($$9 == 0) {
                            MineshaftPieces.m_227706_(p_227795_, p_227796_, p_227797_, $$8, this.f_73383_.m_162396_(), this.f_73383_.m_162398_() - 1, Direction.NORTH, $$3 + 1);
                        } else if ($$9 == 1) {
                            MineshaftPieces.m_227706_(p_227795_, p_227796_, p_227797_, $$8, this.f_73383_.m_162396_(), this.f_73383_.m_162401_() + 1, Direction.SOUTH, $$3 + 1);
                        }
                        $$8 += 5;
                    }
                }
            }
        }

        @Override
        protected boolean m_213787_(WorldGenLevel p_227787_, BoundingBox p_227788_, RandomSource p_227789_, int p_227790_, int p_227791_, int p_227792_, ResourceLocation p_227793_) {
            BlockPos.MutableBlockPos $$7 = this.m_163582_(p_227790_, p_227791_, p_227792_);
            if (p_227788_.m_71051_($$7) && p_227787_.m_8055_($$7).m_60795_() && !p_227787_.m_8055_(((BlockPos)$$7).m_7495_()).m_60795_()) {
                BlockState $$8 = (BlockState)Blocks.f_50156_.m_49966_().m_61124_(RailBlock.f_55392_, p_227789_.m_188499_() ? RailShape.NORTH_SOUTH : RailShape.EAST_WEST);
                this.m_73434_(p_227787_, $$8, p_227790_, p_227791_, p_227792_, p_227788_);
                MinecartChest $$9 = new MinecartChest(p_227787_.m_6018_(), (double)$$7.m_123341_() + 0.5, (double)$$7.m_123342_() + 0.5, (double)$$7.m_123343_() + 0.5);
                $$9.m_38236_(p_227793_, p_227789_.m_188505_());
                p_227787_.m_7967_($$9);
                return true;
            }
            return false;
        }

        @Override
        public void m_213694_(WorldGenLevel p_227743_, StructureManager p_227744_, ChunkGenerator p_227745_, RandomSource p_227746_, BoundingBox p_227747_, ChunkPos p_227748_, BlockPos p_227749_) {
            if (this.m_227881_(p_227743_, p_227747_)) {
                return;
            }
            boolean $$7 = false;
            int $$8 = 2;
            boolean $$9 = false;
            int $$10 = 2;
            int $$11 = this.f_227729_ * 5 - 1;
            BlockState $$12 = this.f_227864_.m_227994_();
            this.m_73441_(p_227743_, p_227747_, 0, 0, 0, 2, 1, $$11, f_73382_, f_73382_, false);
            this.m_226788_(p_227743_, p_227747_, p_227746_, 0.8f, 0, 2, 0, 2, 2, $$11, f_73382_, f_73382_, false, false);
            if (this.f_227727_) {
                this.m_226788_(p_227743_, p_227747_, p_227746_, 0.6f, 0, 0, 0, 2, 1, $$11, Blocks.f_50033_.m_49966_(), f_73382_, false, true);
            }
            for (int $$13 = 0; $$13 < this.f_227729_; ++$$13) {
                int $$14 = 2 + $$13 * 5;
                this.m_227769_(p_227743_, p_227747_, 0, 0, $$14, 2, 2, p_227746_);
                this.m_227778_(p_227743_, p_227747_, p_227746_, 0.1f, 0, 2, $$14 - 1);
                this.m_227778_(p_227743_, p_227747_, p_227746_, 0.1f, 2, 2, $$14 - 1);
                this.m_227778_(p_227743_, p_227747_, p_227746_, 0.1f, 0, 2, $$14 + 1);
                this.m_227778_(p_227743_, p_227747_, p_227746_, 0.1f, 2, 2, $$14 + 1);
                this.m_227778_(p_227743_, p_227747_, p_227746_, 0.05f, 0, 2, $$14 - 2);
                this.m_227778_(p_227743_, p_227747_, p_227746_, 0.05f, 2, 2, $$14 - 2);
                this.m_227778_(p_227743_, p_227747_, p_227746_, 0.05f, 0, 2, $$14 + 2);
                this.m_227778_(p_227743_, p_227747_, p_227746_, 0.05f, 2, 2, $$14 + 2);
                if (p_227746_.m_188503_(100) == 0) {
                    this.m_213787_(p_227743_, p_227747_, p_227746_, 2, 0, $$14 - 1, BuiltInLootTables.f_78759_);
                }
                if (p_227746_.m_188503_(100) == 0) {
                    this.m_213787_(p_227743_, p_227747_, p_227746_, 0, 0, $$14 + 1, BuiltInLootTables.f_78759_);
                }
                if (!this.f_227727_ || this.f_227728_) continue;
                boolean $$15 = true;
                int $$16 = $$14 - 1 + p_227746_.m_188503_(3);
                BlockPos.MutableBlockPos $$17 = this.m_163582_(1, 0, $$16);
                if (!p_227747_.m_71051_($$17) || !this.m_73414_(p_227743_, 1, 0, $$16, p_227747_)) continue;
                this.f_227728_ = true;
                p_227743_.m_7731_($$17, Blocks.f_50085_.m_49966_(), 2);
                BlockEntity $$18 = p_227743_.m_7702_($$17);
                if (!($$18 instanceof SpawnerBlockEntity)) continue;
                ((SpawnerBlockEntity)$$18).m_59801_().m_45462_(EntityType.f_20554_);
            }
            for (int $$19 = 0; $$19 <= 2; ++$$19) {
                for (int $$20 = 0; $$20 <= $$11; ++$$20) {
                    this.m_227890_(p_227743_, p_227747_, $$12, $$19, -1, $$20);
                }
            }
            int $$21 = 2;
            this.m_227756_(p_227743_, p_227747_, 0, -1, 2);
            if (this.f_227729_ > 1) {
                int $$22 = $$11 - 2;
                this.m_227756_(p_227743_, p_227747_, 0, -1, $$22);
            }
            if (this.f_227726_) {
                BlockState $$23 = (BlockState)Blocks.f_50156_.m_49966_().m_61124_(RailBlock.f_55392_, RailShape.NORTH_SOUTH);
                for (int $$24 = 0; $$24 <= $$11; ++$$24) {
                    BlockState $$25 = this.m_73398_(p_227743_, 1, -1, $$24, p_227747_);
                    if ($$25.m_60795_() || !$$25.m_60804_(p_227743_, this.m_163582_(1, -1, $$24))) continue;
                    float $$26 = this.m_73414_(p_227743_, 1, 0, $$24, p_227747_) ? 0.7f : 0.9f;
                    this.m_226803_(p_227743_, p_227747_, p_227746_, $$26, 1, 0, $$24, $$23);
                }
            }
        }

        private void m_227756_(WorldGenLevel p_227757_, BoundingBox p_227758_, int p_227759_, int p_227760_, int p_227761_) {
            BlockState $$5 = this.f_227864_.m_227992_();
            BlockState $$6 = this.f_227864_.m_227994_();
            if (this.m_73398_(p_227757_, p_227759_, p_227760_, p_227761_, p_227758_).m_60713_($$6.m_60734_())) {
                this.m_227819_(p_227757_, $$5, p_227759_, p_227760_, p_227761_, p_227758_);
            }
            if (this.m_73398_(p_227757_, p_227759_ + 2, p_227760_, p_227761_, p_227758_).m_60713_($$6.m_60734_())) {
                this.m_227819_(p_227757_, $$5, p_227759_ + 2, p_227760_, p_227761_, p_227758_);
            }
        }

        @Override
        protected void m_73528_(WorldGenLevel p_227813_, BlockState p_227814_, int p_227815_, int p_227816_, int p_227817_, BoundingBox p_227818_) {
            BlockPos.MutableBlockPos $$6 = this.m_163582_(p_227815_, p_227816_, p_227817_);
            if (!p_227818_.m_71051_($$6)) {
                return;
            }
            int $$7 = $$6.m_123342_();
            while (this.m_163572_(p_227813_.m_8055_($$6)) && $$6.m_123342_() > p_227813_.m_141937_() + 1) {
                $$6.m_122173_(Direction.DOWN);
            }
            if (!this.m_227738_(p_227813_, $$6, p_227813_.m_8055_($$6))) {
                return;
            }
            while ($$6.m_123342_() < $$7) {
                $$6.m_122173_(Direction.UP);
                p_227813_.m_7731_($$6, p_227814_, 2);
            }
        }

        protected void m_227819_(WorldGenLevel p_227820_, BlockState p_227821_, int p_227822_, int p_227823_, int p_227824_, BoundingBox p_227825_) {
            BlockPos.MutableBlockPos $$6 = this.m_163582_(p_227822_, p_227823_, p_227824_);
            if (!p_227825_.m_71051_($$6)) {
                return;
            }
            int $$7 = $$6.m_123342_();
            int $$8 = 1;
            boolean $$9 = true;
            boolean $$10 = true;
            while ($$9 || $$10) {
                if ($$9) {
                    boolean $$12;
                    $$6.m_142448_($$7 - $$8);
                    BlockState $$11 = p_227820_.m_8055_($$6);
                    boolean bl = $$12 = this.m_163572_($$11) && !$$11.m_60713_(Blocks.f_49991_);
                    if (!$$12 && this.m_227738_(p_227820_, $$6, $$11)) {
                        MineShaftCorridor.m_227750_(p_227820_, p_227821_, $$6, $$7 - $$8 + 1, $$7);
                        return;
                    }
                    boolean bl2 = $$9 = $$8 <= 20 && $$12 && $$6.m_123342_() > p_227820_.m_141937_() + 1;
                }
                if ($$10) {
                    $$6.m_142448_($$7 + $$8);
                    BlockState $$13 = p_227820_.m_8055_($$6);
                    boolean $$14 = this.m_163572_($$13);
                    if (!$$14 && this.m_227808_(p_227820_, $$6, $$13)) {
                        p_227820_.m_7731_($$6.m_142448_($$7 + 1), this.f_227864_.m_227995_(), 2);
                        MineShaftCorridor.m_227750_(p_227820_, Blocks.f_50184_.m_49966_(), $$6, $$7 + 2, $$7 + $$8);
                        return;
                    }
                    $$10 = $$8 <= 50 && $$14 && $$6.m_123342_() < p_227820_.m_151558_() - 1;
                }
                ++$$8;
            }
        }

        private static void m_227750_(WorldGenLevel p_227751_, BlockState p_227752_, BlockPos.MutableBlockPos p_227753_, int p_227754_, int p_227755_) {
            for (int $$5 = p_227754_; $$5 < p_227755_; ++$$5) {
                p_227751_.m_7731_(p_227753_.m_142448_($$5), p_227752_, 2);
            }
        }

        private boolean m_227738_(LevelReader p_227739_, BlockPos p_227740_, BlockState p_227741_) {
            return p_227741_.m_60783_(p_227739_, p_227740_, Direction.UP);
        }

        private boolean m_227808_(LevelReader p_227809_, BlockPos p_227810_, BlockState p_227811_) {
            return Block.m_49863_(p_227809_, p_227810_, Direction.DOWN) && !(p_227811_.m_60734_() instanceof FallingBlock);
        }

        private void m_227769_(WorldGenLevel p_227770_, BoundingBox p_227771_, int p_227772_, int p_227773_, int p_227774_, int p_227775_, int p_227776_, RandomSource p_227777_) {
            if (!this.m_227874_(p_227770_, p_227771_, p_227772_, p_227776_, p_227775_, p_227774_)) {
                return;
            }
            BlockState $$8 = this.f_227864_.m_227994_();
            BlockState $$9 = this.f_227864_.m_227995_();
            this.m_73441_(p_227770_, p_227771_, p_227772_, p_227773_, p_227774_, p_227772_, p_227775_ - 1, p_227774_, (BlockState)$$9.m_61124_(FenceBlock.f_52312_, true), f_73382_, false);
            this.m_73441_(p_227770_, p_227771_, p_227776_, p_227773_, p_227774_, p_227776_, p_227775_ - 1, p_227774_, (BlockState)$$9.m_61124_(FenceBlock.f_52310_, true), f_73382_, false);
            if (p_227777_.m_188503_(4) == 0) {
                this.m_73441_(p_227770_, p_227771_, p_227772_, p_227775_, p_227774_, p_227772_, p_227775_, p_227774_, $$8, f_73382_, false);
                this.m_73441_(p_227770_, p_227771_, p_227776_, p_227775_, p_227774_, p_227776_, p_227775_, p_227774_, $$8, f_73382_, false);
            } else {
                this.m_73441_(p_227770_, p_227771_, p_227772_, p_227775_, p_227774_, p_227776_, p_227775_, p_227774_, $$8, f_73382_, false);
                this.m_226803_(p_227770_, p_227771_, p_227777_, 0.05f, p_227772_ + 1, p_227775_, p_227774_ - 1, (BlockState)Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.SOUTH));
                this.m_226803_(p_227770_, p_227771_, p_227777_, 0.05f, p_227772_ + 1, p_227775_, p_227774_ + 1, (BlockState)Blocks.f_50082_.m_49966_().m_61124_(WallTorchBlock.f_58119_, Direction.NORTH));
            }
        }

        private void m_227778_(WorldGenLevel p_227779_, BoundingBox p_227780_, RandomSource p_227781_, float p_227782_, int p_227783_, int p_227784_, int p_227785_) {
            if (this.m_73414_(p_227779_, p_227783_, p_227784_, p_227785_, p_227780_) && p_227781_.m_188501_() < p_227782_ && this.m_227762_(p_227779_, p_227780_, p_227783_, p_227784_, p_227785_, 2)) {
                this.m_73434_(p_227779_, Blocks.f_50033_.m_49966_(), p_227783_, p_227784_, p_227785_, p_227780_);
            }
        }

        private boolean m_227762_(WorldGenLevel p_227763_, BoundingBox p_227764_, int p_227765_, int p_227766_, int p_227767_, int p_227768_) {
            BlockPos.MutableBlockPos $$6 = this.m_163582_(p_227765_, p_227766_, p_227767_);
            int $$7 = 0;
            for (Direction $$8 : Direction.values()) {
                $$6.m_122173_($$8);
                if (p_227764_.m_71051_($$6) && p_227763_.m_8055_($$6).m_60783_(p_227763_, $$6, $$8.m_122424_()) && ++$$7 >= p_227768_) {
                    return true;
                }
                $$6.m_122173_($$8.m_122424_());
            }
            return false;
        }
    }

    static abstract class MineShaftPiece
    extends StructurePiece {
        protected MineshaftStructure.Type f_227864_;

        public MineShaftPiece(StructurePieceType p_227867_, int p_227868_, MineshaftStructure.Type p_227869_, BoundingBox p_227870_) {
            super(p_227867_, p_227868_, p_227870_);
            this.f_227864_ = p_227869_;
        }

        public MineShaftPiece(StructurePieceType p_227872_, CompoundTag p_227873_) {
            super(p_227872_, p_227873_);
            this.f_227864_ = MineshaftStructure.Type.m_227990_(p_227873_.m_128451_("MST"));
        }

        @Override
        protected boolean m_142085_(LevelReader p_227885_, int p_227886_, int p_227887_, int p_227888_, BoundingBox p_227889_) {
            BlockState $$5 = this.m_73398_(p_227885_, p_227886_, p_227887_, p_227888_, p_227889_);
            return !$$5.m_60713_(this.f_227864_.m_227994_().m_60734_()) && !$$5.m_60713_(this.f_227864_.m_227992_().m_60734_()) && !$$5.m_60713_(this.f_227864_.m_227995_().m_60734_()) && !$$5.m_60713_(Blocks.f_50184_);
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_227898_, CompoundTag p_227899_) {
            p_227899_.m_128405_("MST", this.f_227864_.ordinal());
        }

        protected boolean m_227874_(BlockGetter p_227875_, BoundingBox p_227876_, int p_227877_, int p_227878_, int p_227879_, int p_227880_) {
            for (int $$6 = p_227877_; $$6 <= p_227878_; ++$$6) {
                if (!this.m_73398_(p_227875_, $$6, p_227879_ + 1, p_227880_, p_227876_).m_60795_()) continue;
                return false;
            }
            return true;
        }

        protected boolean m_227881_(LevelAccessor p_227882_, BoundingBox p_227883_) {
            int $$7;
            int $$6;
            int $$2 = Math.max(this.f_73383_.m_162395_() - 1, p_227883_.m_162395_());
            int $$3 = Math.max(this.f_73383_.m_162396_() - 1, p_227883_.m_162396_());
            int $$4 = Math.max(this.f_73383_.m_162398_() - 1, p_227883_.m_162398_());
            int $$5 = Math.min(this.f_73383_.m_162399_() + 1, p_227883_.m_162399_());
            BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos(($$2 + $$5) / 2, ($$3 + ($$6 = Math.min(this.f_73383_.m_162400_() + 1, p_227883_.m_162400_()))) / 2, ($$4 + ($$7 = Math.min(this.f_73383_.m_162401_() + 1, p_227883_.m_162401_()))) / 2);
            if (p_227882_.m_204166_($$8).m_203656_(BiomeTags.f_238171_)) {
                return true;
            }
            for (int $$9 = $$2; $$9 <= $$5; ++$$9) {
                for (int $$10 = $$4; $$10 <= $$7; ++$$10) {
                    if (p_227882_.m_8055_($$8.m_122178_($$9, $$3, $$10)).m_60767_().m_76332_()) {
                        return true;
                    }
                    if (!p_227882_.m_8055_($$8.m_122178_($$9, $$6, $$10)).m_60767_().m_76332_()) continue;
                    return true;
                }
            }
            for (int $$11 = $$2; $$11 <= $$5; ++$$11) {
                for (int $$12 = $$3; $$12 <= $$6; ++$$12) {
                    if (p_227882_.m_8055_($$8.m_122178_($$11, $$12, $$4)).m_60767_().m_76332_()) {
                        return true;
                    }
                    if (!p_227882_.m_8055_($$8.m_122178_($$11, $$12, $$7)).m_60767_().m_76332_()) continue;
                    return true;
                }
            }
            for (int $$13 = $$4; $$13 <= $$7; ++$$13) {
                for (int $$14 = $$3; $$14 <= $$6; ++$$14) {
                    if (p_227882_.m_8055_($$8.m_122178_($$2, $$14, $$13)).m_60767_().m_76332_()) {
                        return true;
                    }
                    if (!p_227882_.m_8055_($$8.m_122178_($$5, $$14, $$13)).m_60767_().m_76332_()) continue;
                    return true;
                }
            }
            return false;
        }

        protected void m_227890_(WorldGenLevel p_227891_, BoundingBox p_227892_, BlockState p_227893_, int p_227894_, int p_227895_, int p_227896_) {
            if (!this.m_73414_(p_227891_, p_227894_, p_227895_, p_227896_, p_227892_)) {
                return;
            }
            BlockPos.MutableBlockPos $$6 = this.m_163582_(p_227894_, p_227895_, p_227896_);
            BlockState $$7 = p_227891_.m_8055_($$6);
            if (!$$7.m_60783_(p_227891_, $$6, Direction.UP)) {
                p_227891_.m_7731_($$6, p_227893_, 2);
            }
        }
    }

    public static class MineShaftRoom
    extends MineShaftPiece {
        private final List<BoundingBox> f_227900_ = Lists.newLinkedList();

        public MineShaftRoom(int p_227902_, RandomSource p_227903_, int p_227904_, int p_227905_, MineshaftStructure.Type p_227906_) {
            super(StructurePieceType.f_210127_, p_227902_, p_227906_, new BoundingBox(p_227904_, 50, p_227905_, p_227904_ + 7 + p_227903_.m_188503_(6), 54 + p_227903_.m_188503_(6), p_227905_ + 7 + p_227903_.m_188503_(6)));
            this.f_227864_ = p_227906_;
        }

        public MineShaftRoom(CompoundTag p_227908_) {
            super(StructurePieceType.f_210127_, p_227908_);
            BoundingBox.f_162354_.listOf().parse((DynamicOps)NbtOps.f_128958_, (Object)p_227908_.m_128437_("Entrances", 11)).resultOrPartial(arg_0 -> ((Logger)f_227697_).error(arg_0)).ifPresent(this.f_227900_::addAll);
        }

        @Override
        public void m_214092_(StructurePiece p_227922_, StructurePieceAccessor p_227923_, RandomSource p_227924_) {
            int $$5;
            int $$3 = this.m_73548_();
            int $$4 = this.f_73383_.m_71057_() - 3 - 1;
            if ($$4 <= 0) {
                $$4 = 1;
            }
            for ($$5 = 0; $$5 < this.f_73383_.m_71056_() && ($$5 += p_227924_.m_188503_(this.f_73383_.m_71056_())) + 3 <= this.f_73383_.m_71056_(); $$5 += 4) {
                MineShaftPiece $$6 = MineshaftPieces.m_227706_(p_227922_, p_227923_, p_227924_, this.f_73383_.m_162395_() + $$5, this.f_73383_.m_162396_() + p_227924_.m_188503_($$4) + 1, this.f_73383_.m_162398_() - 1, Direction.NORTH, $$3);
                if ($$6 == null) continue;
                BoundingBox $$7 = $$6.m_73547_();
                this.f_227900_.add(new BoundingBox($$7.m_162395_(), $$7.m_162396_(), this.f_73383_.m_162398_(), $$7.m_162399_(), $$7.m_162400_(), this.f_73383_.m_162398_() + 1));
            }
            for ($$5 = 0; $$5 < this.f_73383_.m_71056_() && ($$5 += p_227924_.m_188503_(this.f_73383_.m_71056_())) + 3 <= this.f_73383_.m_71056_(); $$5 += 4) {
                MineShaftPiece $$8 = MineshaftPieces.m_227706_(p_227922_, p_227923_, p_227924_, this.f_73383_.m_162395_() + $$5, this.f_73383_.m_162396_() + p_227924_.m_188503_($$4) + 1, this.f_73383_.m_162401_() + 1, Direction.SOUTH, $$3);
                if ($$8 == null) continue;
                BoundingBox $$9 = $$8.m_73547_();
                this.f_227900_.add(new BoundingBox($$9.m_162395_(), $$9.m_162396_(), this.f_73383_.m_162401_() - 1, $$9.m_162399_(), $$9.m_162400_(), this.f_73383_.m_162401_()));
            }
            for ($$5 = 0; $$5 < this.f_73383_.m_71058_() && ($$5 += p_227924_.m_188503_(this.f_73383_.m_71058_())) + 3 <= this.f_73383_.m_71058_(); $$5 += 4) {
                MineShaftPiece $$10 = MineshaftPieces.m_227706_(p_227922_, p_227923_, p_227924_, this.f_73383_.m_162395_() - 1, this.f_73383_.m_162396_() + p_227924_.m_188503_($$4) + 1, this.f_73383_.m_162398_() + $$5, Direction.WEST, $$3);
                if ($$10 == null) continue;
                BoundingBox $$11 = $$10.m_73547_();
                this.f_227900_.add(new BoundingBox(this.f_73383_.m_162395_(), $$11.m_162396_(), $$11.m_162398_(), this.f_73383_.m_162395_() + 1, $$11.m_162400_(), $$11.m_162401_()));
            }
            for ($$5 = 0; $$5 < this.f_73383_.m_71058_() && ($$5 += p_227924_.m_188503_(this.f_73383_.m_71058_())) + 3 <= this.f_73383_.m_71058_(); $$5 += 4) {
                MineShaftPiece $$12 = MineshaftPieces.m_227706_(p_227922_, p_227923_, p_227924_, this.f_73383_.m_162399_() + 1, this.f_73383_.m_162396_() + p_227924_.m_188503_($$4) + 1, this.f_73383_.m_162398_() + $$5, Direction.EAST, $$3);
                if ($$12 == null) continue;
                BoundingBox $$13 = $$12.m_73547_();
                this.f_227900_.add(new BoundingBox(this.f_73383_.m_162399_() - 1, $$13.m_162396_(), $$13.m_162398_(), this.f_73383_.m_162399_(), $$13.m_162400_(), $$13.m_162401_()));
            }
        }

        @Override
        public void m_213694_(WorldGenLevel p_227914_, StructureManager p_227915_, ChunkGenerator p_227916_, RandomSource p_227917_, BoundingBox p_227918_, ChunkPos p_227919_, BlockPos p_227920_) {
            if (this.m_227881_(p_227914_, p_227918_)) {
                return;
            }
            this.m_73441_(p_227914_, p_227918_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_() + 1, this.f_73383_.m_162398_(), this.f_73383_.m_162399_(), Math.min(this.f_73383_.m_162396_() + 3, this.f_73383_.m_162400_()), this.f_73383_.m_162401_(), f_73382_, f_73382_, false);
            for (BoundingBox $$7 : this.f_227900_) {
                this.m_73441_(p_227914_, p_227918_, $$7.m_162395_(), $$7.m_162400_() - 2, $$7.m_162398_(), $$7.m_162399_(), $$7.m_162400_(), $$7.m_162401_(), f_73382_, f_73382_, false);
            }
            this.m_73453_(p_227914_, p_227918_, this.f_73383_.m_162395_(), this.f_73383_.m_162396_() + 4, this.f_73383_.m_162398_(), this.f_73383_.m_162399_(), this.f_73383_.m_162400_(), this.f_73383_.m_162401_(), f_73382_, false);
        }

        @Override
        public void m_6324_(int p_227910_, int p_227911_, int p_227912_) {
            super.m_6324_(p_227910_, p_227911_, p_227912_);
            for (BoundingBox $$3 : this.f_227900_) {
                $$3.m_162367_(p_227910_, p_227911_, p_227912_);
            }
        }

        @Override
        protected void m_183620_(StructurePieceSerializationContext p_227926_, CompoundTag p_227927_) {
            super.m_183620_(p_227926_, p_227927_);
            BoundingBox.f_162354_.listOf().encodeStart((DynamicOps)NbtOps.f_128958_, this.f_227900_).resultOrPartial(arg_0 -> ((Logger)f_227697_).error(arg_0)).ifPresent(p_227930_ -> p_227927_.m_128365_("Entrances", (Tag)p_227930_));
        }
    }
}

