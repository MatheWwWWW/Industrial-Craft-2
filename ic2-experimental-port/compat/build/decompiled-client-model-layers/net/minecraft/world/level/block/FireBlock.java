/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 */
package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FireBlock
extends BaseFireBlock {
    public static final int f_153264_ = 15;
    public static final IntegerProperty f_53408_ = BlockStateProperties.f_61410_;
    public static final BooleanProperty f_53409_ = PipeBlock.f_55148_;
    public static final BooleanProperty f_53410_ = PipeBlock.f_55149_;
    public static final BooleanProperty f_53411_ = PipeBlock.f_55150_;
    public static final BooleanProperty f_53412_ = PipeBlock.f_55151_;
    public static final BooleanProperty f_53413_ = PipeBlock.f_55152_;
    private static final Map<Direction, BooleanProperty> f_53414_ = PipeBlock.f_55154_.entrySet().stream().filter(p_53467_ -> p_53467_.getKey() != Direction.DOWN).collect(Util.m_137448_());
    private static final VoxelShape f_53415_ = Block.m_49796_(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape f_53416_ = Block.m_49796_(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape f_53417_ = Block.m_49796_(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape f_53418_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape f_53419_ = Block.m_49796_(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private final Map<BlockState, VoxelShape> f_53420_;
    private static final int f_221143_ = 60;
    private static final int f_221144_ = 30;
    private static final int f_221145_ = 15;
    private static final int f_221146_ = 5;
    private static final int f_153260_ = 100;
    private static final int f_153261_ = 60;
    private static final int f_153262_ = 20;
    private static final int f_153263_ = 5;
    private final Object2IntMap<Block> f_221147_ = new Object2IntOpenHashMap();
    private final Object2IntMap<Block> f_53422_ = new Object2IntOpenHashMap();

    public FireBlock(BlockBehaviour.Properties p_53425_) {
        super(p_53425_, 1.0f);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_53408_, 0)).m_61124_(f_53409_, false)).m_61124_(f_53410_, false)).m_61124_(f_53411_, false)).m_61124_(f_53412_, false)).m_61124_(f_53413_, false));
        this.f_53420_ = ImmutableMap.copyOf(this.f_49792_.m_61056_().stream().filter(p_53497_ -> p_53497_.m_61143_(f_53408_) == 0).collect(Collectors.toMap(Function.identity(), FireBlock::m_53490_)));
    }

    private static VoxelShape m_53490_(BlockState p_53491_) {
        VoxelShape $$1 = Shapes.m_83040_();
        if (p_53491_.m_61143_(f_53413_).booleanValue()) {
            $$1 = f_53415_;
        }
        if (p_53491_.m_61143_(f_53409_).booleanValue()) {
            $$1 = Shapes.m_83110_($$1, f_53418_);
        }
        if (p_53491_.m_61143_(f_53411_).booleanValue()) {
            $$1 = Shapes.m_83110_($$1, f_53419_);
        }
        if (p_53491_.m_61143_(f_53410_).booleanValue()) {
            $$1 = Shapes.m_83110_($$1, f_53417_);
        }
        if (p_53491_.m_61143_(f_53412_).booleanValue()) {
            $$1 = Shapes.m_83110_($$1, f_53416_);
        }
        return $$1.m_83281_() ? f_49237_ : $$1;
    }

    @Override
    public BlockState m_7417_(BlockState p_53458_, Direction p_53459_, BlockState p_53460_, LevelAccessor p_53461_, BlockPos p_53462_, BlockPos p_53463_) {
        if (this.m_7898_(p_53458_, p_53461_, p_53462_)) {
            return this.m_53437_(p_53461_, p_53462_, p_53458_.m_61143_(f_53408_));
        }
        return Blocks.f_50016_.m_49966_();
    }

    @Override
    public VoxelShape m_5940_(BlockState p_53474_, BlockGetter p_53475_, BlockPos p_53476_, CollisionContext p_53477_) {
        return this.f_53420_.get(p_53474_.m_61124_(f_53408_, 0));
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_53427_) {
        return this.m_53470_(p_53427_.m_43725_(), p_53427_.m_8083_());
    }

    protected BlockState m_53470_(BlockGetter p_53471_, BlockPos p_53472_) {
        BlockPos $$2 = p_53472_.m_7495_();
        BlockState $$3 = p_53471_.m_8055_($$2);
        if (this.m_7599_($$3) || $$3.m_60783_(p_53471_, $$2, Direction.UP)) {
            return this.m_49966_();
        }
        BlockState $$4 = this.m_49966_();
        for (Direction $$5 : Direction.values()) {
            BooleanProperty $$6 = f_53414_.get($$5);
            if ($$6 == null) continue;
            $$4 = (BlockState)$$4.m_61124_($$6, this.m_7599_(p_53471_.m_8055_(p_53472_.m_121945_($$5))));
        }
        return $$4;
    }

    @Override
    public boolean m_7898_(BlockState p_53454_, LevelReader p_53455_, BlockPos p_53456_) {
        BlockPos $$3 = p_53456_.m_7495_();
        return p_53455_.m_8055_($$3).m_60783_(p_53455_, $$3, Direction.UP) || this.m_53485_(p_53455_, p_53456_);
    }

    @Override
    public void m_213897_(BlockState p_221160_, ServerLevel p_221161_, BlockPos p_221162_, RandomSource p_221163_) {
        boolean $$9;
        p_221161_.m_186460_(p_221162_, this, FireBlock.m_221148_(p_221161_.f_46441_));
        if (!p_221161_.m_46469_().m_46207_(GameRules.f_46131_)) {
            return;
        }
        if (!p_221160_.m_60710_(p_221161_, p_221162_)) {
            p_221161_.m_7471_(p_221162_, false);
        }
        BlockState $$4 = p_221161_.m_8055_(p_221162_.m_7495_());
        boolean $$5 = $$4.m_204336_(p_221161_.m_6042_().f_63836_());
        int $$6 = p_221160_.m_61143_(f_53408_);
        if (!$$5 && p_221161_.m_46471_() && this.m_53428_(p_221161_, p_221162_) && p_221163_.m_188501_() < 0.2f + (float)$$6 * 0.03f) {
            p_221161_.m_7471_(p_221162_, false);
            return;
        }
        int $$7 = Math.min(15, $$6 + p_221163_.m_188503_(3) / 2);
        if ($$6 != $$7) {
            p_221160_ = (BlockState)p_221160_.m_61124_(f_53408_, $$7);
            p_221161_.m_7731_(p_221162_, p_221160_, 4);
        }
        if (!$$5) {
            if (!this.m_53485_(p_221161_, p_221162_)) {
                BlockPos $$8 = p_221162_.m_7495_();
                if (!p_221161_.m_8055_($$8).m_60783_(p_221161_, $$8, Direction.UP) || $$6 > 3) {
                    p_221161_.m_7471_(p_221162_, false);
                }
                return;
            }
            if ($$6 == 15 && p_221163_.m_188503_(4) == 0 && !this.m_7599_(p_221161_.m_8055_(p_221162_.m_7495_()))) {
                p_221161_.m_7471_(p_221162_, false);
                return;
            }
        }
        int $$10 = ($$9 = p_221161_.m_46761_(p_221162_)) ? -50 : 0;
        this.m_221150_(p_221161_, p_221162_.m_122029_(), 300 + $$10, p_221163_, $$6);
        this.m_221150_(p_221161_, p_221162_.m_122024_(), 300 + $$10, p_221163_, $$6);
        this.m_221150_(p_221161_, p_221162_.m_7495_(), 250 + $$10, p_221163_, $$6);
        this.m_221150_(p_221161_, p_221162_.m_7494_(), 250 + $$10, p_221163_, $$6);
        this.m_221150_(p_221161_, p_221162_.m_122012_(), 300 + $$10, p_221163_, $$6);
        this.m_221150_(p_221161_, p_221162_.m_122019_(), 300 + $$10, p_221163_, $$6);
        BlockPos.MutableBlockPos $$11 = new BlockPos.MutableBlockPos();
        for (int $$12 = -1; $$12 <= 1; ++$$12) {
            for (int $$13 = -1; $$13 <= 1; ++$$13) {
                for (int $$14 = -1; $$14 <= 4; ++$$14) {
                    if ($$12 == 0 && $$14 == 0 && $$13 == 0) continue;
                    int $$15 = 100;
                    if ($$14 > 1) {
                        $$15 += ($$14 - 1) * 100;
                    }
                    $$11.m_122154_(p_221162_, $$12, $$14, $$13);
                    int $$16 = this.m_221156_(p_221161_, $$11);
                    if ($$16 <= 0) continue;
                    int $$17 = ($$16 + 40 + p_221161_.m_46791_().m_19028_() * 7) / ($$6 + 30);
                    if ($$9) {
                        $$17 /= 2;
                    }
                    if ($$17 <= 0 || p_221163_.m_188503_($$15) > $$17 || p_221161_.m_46471_() && this.m_53428_(p_221161_, $$11)) continue;
                    int $$18 = Math.min(15, $$6 + p_221163_.m_188503_(5) / 4);
                    p_221161_.m_7731_($$11, this.m_53437_(p_221161_, $$11, $$18), 3);
                }
            }
        }
    }

    protected boolean m_53428_(Level p_53429_, BlockPos p_53430_) {
        return p_53429_.m_46758_(p_53430_) || p_53429_.m_46758_(p_53430_.m_122024_()) || p_53429_.m_46758_(p_53430_.m_122029_()) || p_53429_.m_46758_(p_53430_.m_122012_()) || p_53429_.m_46758_(p_53430_.m_122019_());
    }

    private int m_221164_(BlockState p_221165_) {
        if (p_221165_.m_61138_(BlockStateProperties.f_61362_) && p_221165_.m_61143_(BlockStateProperties.f_61362_).booleanValue()) {
            return 0;
        }
        return this.f_53422_.getInt((Object)p_221165_.m_60734_());
    }

    private int m_221166_(BlockState p_221167_) {
        if (p_221167_.m_61138_(BlockStateProperties.f_61362_) && p_221167_.m_61143_(BlockStateProperties.f_61362_).booleanValue()) {
            return 0;
        }
        return this.f_221147_.getInt((Object)p_221167_.m_60734_());
    }

    private void m_221150_(Level p_221151_, BlockPos p_221152_, int p_221153_, RandomSource p_221154_, int p_221155_) {
        int $$5 = this.m_221164_(p_221151_.m_8055_(p_221152_));
        if (p_221154_.m_188503_(p_221153_) < $$5) {
            BlockState $$6 = p_221151_.m_8055_(p_221152_);
            if (p_221154_.m_188503_(p_221155_ + 10) < 5 && !p_221151_.m_46758_(p_221152_)) {
                int $$7 = Math.min(p_221155_ + p_221154_.m_188503_(5) / 4, 15);
                p_221151_.m_7731_(p_221152_, this.m_53437_(p_221151_, p_221152_, $$7), 3);
            } else {
                p_221151_.m_7471_(p_221152_, false);
            }
            Block $$8 = $$6.m_60734_();
            if ($$8 instanceof TntBlock) {
                TntBlock.m_57433_(p_221151_, p_221152_);
            }
        }
    }

    private BlockState m_53437_(LevelAccessor p_53438_, BlockPos p_53439_, int p_53440_) {
        BlockState $$3 = FireBlock.m_49245_(p_53438_, p_53439_);
        if ($$3.m_60713_(Blocks.f_50083_)) {
            return (BlockState)$$3.m_61124_(f_53408_, p_53440_);
        }
        return $$3;
    }

    private boolean m_53485_(BlockGetter p_53486_, BlockPos p_53487_) {
        for (Direction $$2 : Direction.values()) {
            if (!this.m_7599_(p_53486_.m_8055_(p_53487_.m_121945_($$2)))) continue;
            return true;
        }
        return false;
    }

    private int m_221156_(LevelReader p_221157_, BlockPos p_221158_) {
        if (!p_221157_.m_46859_(p_221158_)) {
            return 0;
        }
        int $$2 = 0;
        for (Direction $$3 : Direction.values()) {
            BlockState $$4 = p_221157_.m_8055_(p_221158_.m_121945_($$3));
            $$2 = Math.max(this.m_221166_($$4), $$2);
        }
        return $$2;
    }

    @Override
    protected boolean m_7599_(BlockState p_53489_) {
        return this.m_221166_(p_53489_) > 0;
    }

    @Override
    public void m_6807_(BlockState p_53479_, Level p_53480_, BlockPos p_53481_, BlockState p_53482_, boolean p_53483_) {
        super.m_6807_(p_53479_, p_53480_, p_53481_, p_53482_, p_53483_);
        p_53480_.m_186460_(p_53481_, this, FireBlock.m_221148_(p_53480_.f_46441_));
    }

    private static int m_221148_(RandomSource p_221149_) {
        return 30 + p_221149_.m_188503_(10);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53465_) {
        p_53465_.m_61104_(f_53408_, f_53409_, f_53410_, f_53411_, f_53412_, f_53413_);
    }

    private void m_53444_(Block p_53445_, int p_53446_, int p_53447_) {
        this.f_221147_.put((Object)p_53445_, p_53446_);
        this.f_53422_.put((Object)p_53445_, p_53447_);
    }

    public static void m_53484_() {
        FireBlock $$0 = (FireBlock)Blocks.f_50083_;
        $$0.m_53444_(Blocks.f_50705_, 5, 20);
        $$0.m_53444_(Blocks.f_50741_, 5, 20);
        $$0.m_53444_(Blocks.f_50742_, 5, 20);
        $$0.m_53444_(Blocks.f_50743_, 5, 20);
        $$0.m_53444_(Blocks.f_50744_, 5, 20);
        $$0.m_53444_(Blocks.f_50745_, 5, 20);
        $$0.m_53444_(Blocks.f_220865_, 5, 20);
        $$0.m_53444_(Blocks.f_50398_, 5, 20);
        $$0.m_53444_(Blocks.f_50399_, 5, 20);
        $$0.m_53444_(Blocks.f_50400_, 5, 20);
        $$0.m_53444_(Blocks.f_50401_, 5, 20);
        $$0.m_53444_(Blocks.f_50402_, 5, 20);
        $$0.m_53444_(Blocks.f_50403_, 5, 20);
        $$0.m_53444_(Blocks.f_220851_, 5, 20);
        $$0.m_53444_(Blocks.f_50192_, 5, 20);
        $$0.m_53444_(Blocks.f_50474_, 5, 20);
        $$0.m_53444_(Blocks.f_50475_, 5, 20);
        $$0.m_53444_(Blocks.f_50476_, 5, 20);
        $$0.m_53444_(Blocks.f_50477_, 5, 20);
        $$0.m_53444_(Blocks.f_50478_, 5, 20);
        $$0.m_53444_(Blocks.f_220850_, 5, 20);
        $$0.m_53444_(Blocks.f_50132_, 5, 20);
        $$0.m_53444_(Blocks.f_50479_, 5, 20);
        $$0.m_53444_(Blocks.f_50480_, 5, 20);
        $$0.m_53444_(Blocks.f_50481_, 5, 20);
        $$0.m_53444_(Blocks.f_50482_, 5, 20);
        $$0.m_53444_(Blocks.f_50483_, 5, 20);
        $$0.m_53444_(Blocks.f_220852_, 5, 20);
        $$0.m_53444_(Blocks.f_50086_, 5, 20);
        $$0.m_53444_(Blocks.f_50270_, 5, 20);
        $$0.m_53444_(Blocks.f_50269_, 5, 20);
        $$0.m_53444_(Blocks.f_50271_, 5, 20);
        $$0.m_53444_(Blocks.f_50372_, 5, 20);
        $$0.m_53444_(Blocks.f_50373_, 5, 20);
        $$0.m_53444_(Blocks.f_220848_, 5, 20);
        $$0.m_53444_(Blocks.f_49999_, 5, 5);
        $$0.m_53444_(Blocks.f_50000_, 5, 5);
        $$0.m_53444_(Blocks.f_50001_, 5, 5);
        $$0.m_53444_(Blocks.f_50002_, 5, 5);
        $$0.m_53444_(Blocks.f_50003_, 5, 5);
        $$0.m_53444_(Blocks.f_50004_, 5, 5);
        $$0.m_53444_(Blocks.f_220832_, 5, 5);
        $$0.m_53444_(Blocks.f_50010_, 5, 5);
        $$0.m_53444_(Blocks.f_50005_, 5, 5);
        $$0.m_53444_(Blocks.f_50006_, 5, 5);
        $$0.m_53444_(Blocks.f_50007_, 5, 5);
        $$0.m_53444_(Blocks.f_50008_, 5, 5);
        $$0.m_53444_(Blocks.f_50009_, 5, 5);
        $$0.m_53444_(Blocks.f_220835_, 5, 5);
        $$0.m_53444_(Blocks.f_50044_, 5, 5);
        $$0.m_53444_(Blocks.f_50045_, 5, 5);
        $$0.m_53444_(Blocks.f_50046_, 5, 5);
        $$0.m_53444_(Blocks.f_50047_, 5, 5);
        $$0.m_53444_(Blocks.f_50048_, 5, 5);
        $$0.m_53444_(Blocks.f_50049_, 5, 5);
        $$0.m_53444_(Blocks.f_220837_, 5, 5);
        $$0.m_53444_(Blocks.f_50011_, 5, 5);
        $$0.m_53444_(Blocks.f_50012_, 5, 5);
        $$0.m_53444_(Blocks.f_50013_, 5, 5);
        $$0.m_53444_(Blocks.f_50014_, 5, 5);
        $$0.m_53444_(Blocks.f_50015_, 5, 5);
        $$0.m_53444_(Blocks.f_50043_, 5, 5);
        $$0.m_53444_(Blocks.f_220836_, 5, 5);
        $$0.m_53444_(Blocks.f_220833_, 5, 20);
        $$0.m_53444_(Blocks.f_50050_, 30, 60);
        $$0.m_53444_(Blocks.f_50051_, 30, 60);
        $$0.m_53444_(Blocks.f_50052_, 30, 60);
        $$0.m_53444_(Blocks.f_50053_, 30, 60);
        $$0.m_53444_(Blocks.f_50054_, 30, 60);
        $$0.m_53444_(Blocks.f_50055_, 30, 60);
        $$0.m_53444_(Blocks.f_220838_, 30, 60);
        $$0.m_53444_(Blocks.f_50078_, 30, 20);
        $$0.m_53444_(Blocks.f_50077_, 15, 100);
        $$0.m_53444_(Blocks.f_50034_, 60, 100);
        $$0.m_53444_(Blocks.f_50035_, 60, 100);
        $$0.m_53444_(Blocks.f_50036_, 60, 100);
        $$0.m_53444_(Blocks.f_50355_, 60, 100);
        $$0.m_53444_(Blocks.f_50356_, 60, 100);
        $$0.m_53444_(Blocks.f_50357_, 60, 100);
        $$0.m_53444_(Blocks.f_50358_, 60, 100);
        $$0.m_53444_(Blocks.f_50359_, 60, 100);
        $$0.m_53444_(Blocks.f_50360_, 60, 100);
        $$0.m_53444_(Blocks.f_50111_, 60, 100);
        $$0.m_53444_(Blocks.f_50112_, 60, 100);
        $$0.m_53444_(Blocks.f_50113_, 60, 100);
        $$0.m_53444_(Blocks.f_50114_, 60, 100);
        $$0.m_53444_(Blocks.f_50115_, 60, 100);
        $$0.m_53444_(Blocks.f_50116_, 60, 100);
        $$0.m_53444_(Blocks.f_50117_, 60, 100);
        $$0.m_53444_(Blocks.f_50118_, 60, 100);
        $$0.m_53444_(Blocks.f_50119_, 60, 100);
        $$0.m_53444_(Blocks.f_50120_, 60, 100);
        $$0.m_53444_(Blocks.f_50121_, 60, 100);
        $$0.m_53444_(Blocks.f_50071_, 60, 100);
        $$0.m_53444_(Blocks.f_50070_, 60, 100);
        $$0.m_53444_(Blocks.f_50041_, 30, 60);
        $$0.m_53444_(Blocks.f_50042_, 30, 60);
        $$0.m_53444_(Blocks.f_50096_, 30, 60);
        $$0.m_53444_(Blocks.f_50097_, 30, 60);
        $$0.m_53444_(Blocks.f_50098_, 30, 60);
        $$0.m_53444_(Blocks.f_50099_, 30, 60);
        $$0.m_53444_(Blocks.f_50100_, 30, 60);
        $$0.m_53444_(Blocks.f_50101_, 30, 60);
        $$0.m_53444_(Blocks.f_50102_, 30, 60);
        $$0.m_53444_(Blocks.f_50103_, 30, 60);
        $$0.m_53444_(Blocks.f_50104_, 30, 60);
        $$0.m_53444_(Blocks.f_50105_, 30, 60);
        $$0.m_53444_(Blocks.f_50106_, 30, 60);
        $$0.m_53444_(Blocks.f_50107_, 30, 60);
        $$0.m_53444_(Blocks.f_50108_, 30, 60);
        $$0.m_53444_(Blocks.f_50109_, 30, 60);
        $$0.m_53444_(Blocks.f_50191_, 15, 100);
        $$0.m_53444_(Blocks.f_50353_, 5, 5);
        $$0.m_53444_(Blocks.f_50335_, 60, 20);
        $$0.m_53444_(Blocks.f_50716_, 15, 20);
        $$0.m_53444_(Blocks.f_50336_, 60, 20);
        $$0.m_53444_(Blocks.f_50337_, 60, 20);
        $$0.m_53444_(Blocks.f_50338_, 60, 20);
        $$0.m_53444_(Blocks.f_50339_, 60, 20);
        $$0.m_53444_(Blocks.f_50340_, 60, 20);
        $$0.m_53444_(Blocks.f_50341_, 60, 20);
        $$0.m_53444_(Blocks.f_50342_, 60, 20);
        $$0.m_53444_(Blocks.f_50343_, 60, 20);
        $$0.m_53444_(Blocks.f_50344_, 60, 20);
        $$0.m_53444_(Blocks.f_50345_, 60, 20);
        $$0.m_53444_(Blocks.f_50346_, 60, 20);
        $$0.m_53444_(Blocks.f_50347_, 60, 20);
        $$0.m_53444_(Blocks.f_50348_, 60, 20);
        $$0.m_53444_(Blocks.f_50349_, 60, 20);
        $$0.m_53444_(Blocks.f_50350_, 60, 20);
        $$0.m_53444_(Blocks.f_50351_, 60, 20);
        $$0.m_53444_(Blocks.f_50577_, 30, 60);
        $$0.m_53444_(Blocks.f_50571_, 60, 60);
        $$0.m_53444_(Blocks.f_50616_, 60, 60);
        $$0.m_53444_(Blocks.f_50624_, 30, 20);
        $$0.m_53444_(Blocks.f_50715_, 5, 20);
        $$0.m_53444_(Blocks.f_50685_, 60, 100);
        $$0.m_53444_(Blocks.f_50718_, 5, 20);
        $$0.m_53444_(Blocks.f_50717_, 30, 20);
        $$0.m_53444_(Blocks.f_152470_, 30, 60);
        $$0.m_53444_(Blocks.f_152471_, 30, 60);
        $$0.m_53444_(Blocks.f_152538_, 15, 60);
        $$0.m_53444_(Blocks.f_152539_, 15, 60);
        $$0.m_53444_(Blocks.f_152540_, 60, 100);
        $$0.m_53444_(Blocks.f_152541_, 30, 60);
        $$0.m_53444_(Blocks.f_152542_, 30, 60);
        $$0.m_53444_(Blocks.f_152545_, 60, 100);
        $$0.m_53444_(Blocks.f_152546_, 60, 100);
        $$0.m_53444_(Blocks.f_152547_, 60, 100);
        $$0.m_53444_(Blocks.f_152548_, 30, 60);
        $$0.m_53444_(Blocks.f_152475_, 15, 100);
    }
}

