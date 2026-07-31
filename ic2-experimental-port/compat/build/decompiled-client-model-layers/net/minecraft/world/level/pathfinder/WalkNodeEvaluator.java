/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.pathfinder;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.Target;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WalkNodeEvaluator
extends NodeEvaluator {
    public static final double f_164724_ = 0.5;
    protected float f_77544_;
    private final Long2ObjectMap<BlockPathTypes> f_77545_ = new Long2ObjectOpenHashMap();
    private final Object2BooleanMap<AABB> f_77546_ = new Object2BooleanOpenHashMap();

    @Override
    public void m_6028_(PathNavigationRegion p_77620_, Mob p_77621_) {
        super.m_6028_(p_77620_, p_77621_);
        this.f_77544_ = p_77621_.m_21439_(BlockPathTypes.WATER);
    }

    @Override
    public void m_6802_() {
        this.f_77313_.m_21441_(BlockPathTypes.WATER, this.f_77544_);
        this.f_77545_.clear();
        this.f_77546_.clear();
        super.m_6802_();
    }

    @Override
    @Nullable
    public Node m_7171_() {
        BlockPos.MutableBlockPos $$0 = new BlockPos.MutableBlockPos();
        int $$1 = this.f_77313_.m_146904_();
        BlockState $$2 = this.f_77312_.m_8055_($$0.m_122169_(this.f_77313_.m_20185_(), $$1, this.f_77313_.m_20189_()));
        if (this.f_77313_.m_203441_($$2.m_60819_())) {
            while (this.f_77313_.m_203441_($$2.m_60819_())) {
                $$2 = this.f_77312_.m_8055_($$0.m_122169_(this.f_77313_.m_20185_(), ++$$1, this.f_77313_.m_20189_()));
            }
            --$$1;
        } else if (this.m_77361_() && this.f_77313_.m_20069_()) {
            while ($$2.m_60713_(Blocks.f_49990_) || $$2.m_60819_() == Fluids.f_76193_.m_76068_(false)) {
                $$2 = this.f_77312_.m_8055_($$0.m_122169_(this.f_77313_.m_20185_(), ++$$1, this.f_77313_.m_20189_()));
            }
            --$$1;
        } else if (this.f_77313_.m_20096_()) {
            $$1 = Mth.m_14107_(this.f_77313_.m_20186_() + 0.5);
        } else {
            BlockPos $$3 = this.f_77313_.m_20183_();
            while ((this.f_77312_.m_8055_($$3).m_60795_() || this.f_77312_.m_8055_($$3).m_60647_(this.f_77312_, $$3, PathComputationType.LAND)) && $$3.m_123342_() > this.f_77313_.f_19853_.m_141937_()) {
                $$3 = $$3.m_7495_();
            }
            $$1 = $$3.m_7494_().m_123342_();
        }
        BlockPos $$4 = this.f_77313_.m_20183_();
        BlockPathTypes $$5 = this.m_77567_(this.f_77313_, $$4.m_123341_(), $$1, $$4.m_123343_());
        if (this.f_77313_.m_21439_($$5) < 0.0f) {
            AABB $$6 = this.f_77313_.m_20191_();
            if (this.m_77646_($$0.m_122169_($$6.f_82288_, $$1, $$6.f_82290_)) || this.m_77646_($$0.m_122169_($$6.f_82288_, $$1, $$6.f_82293_)) || this.m_77646_($$0.m_122169_($$6.f_82291_, $$1, $$6.f_82290_)) || this.m_77646_($$0.m_122169_($$6.f_82291_, $$1, $$6.f_82293_))) {
                return this.m_230631_($$0);
            }
        }
        return this.m_230631_(new BlockPos($$4.m_123341_(), $$1, $$4.m_123343_()));
    }

    @Nullable
    protected Node m_230631_(BlockPos p_230632_) {
        Node $$1 = this.m_77349_(p_230632_);
        if ($$1 != null) {
            $$1.f_77282_ = this.m_77572_(this.f_77313_, $$1.m_77288_());
            $$1.f_77281_ = this.f_77313_.m_21439_($$1.f_77282_);
        }
        return $$1;
    }

    private boolean m_77646_(BlockPos p_77647_) {
        BlockPathTypes $$1 = this.m_77572_(this.f_77313_, p_77647_);
        return this.f_77313_.m_21439_($$1) >= 0.0f;
    }

    @Override
    @Nullable
    public Target m_7568_(double p_77550_, double p_77551_, double p_77552_) {
        return this.m_230615_(this.m_5676_(Mth.m_14107_(p_77550_), Mth.m_14107_(p_77551_), Mth.m_14107_(p_77552_)));
    }

    @Override
    public int m_6065_(Node[] p_77640_, Node p_77641_) {
        Node $$14;
        Node $$13;
        Node $$12;
        Node $$11;
        Node $$10;
        Node $$9;
        Node $$8;
        double $$6;
        Node $$7;
        int $$2 = 0;
        int $$3 = 0;
        BlockPathTypes $$4 = this.m_77567_(this.f_77313_, p_77641_.f_77271_, p_77641_.f_77272_ + 1, p_77641_.f_77273_);
        BlockPathTypes $$5 = this.m_77567_(this.f_77313_, p_77641_.f_77271_, p_77641_.f_77272_, p_77641_.f_77273_);
        if (this.f_77313_.m_21439_($$4) >= 0.0f && $$5 != BlockPathTypes.STICKY_HONEY) {
            $$3 = Mth.m_14143_(Math.max(1.0f, this.f_77313_.f_19793_));
        }
        if (this.m_77626_($$7 = this.m_164725_(p_77641_.f_77271_, p_77641_.f_77272_, p_77641_.f_77273_ + 1, $$3, $$6 = this.m_142213_(new BlockPos(p_77641_.f_77271_, p_77641_.f_77272_, p_77641_.f_77273_)), Direction.SOUTH, $$5), p_77641_)) {
            p_77640_[$$2++] = $$7;
        }
        if (this.m_77626_($$8 = this.m_164725_(p_77641_.f_77271_ - 1, p_77641_.f_77272_, p_77641_.f_77273_, $$3, $$6, Direction.WEST, $$5), p_77641_)) {
            p_77640_[$$2++] = $$8;
        }
        if (this.m_77626_($$9 = this.m_164725_(p_77641_.f_77271_ + 1, p_77641_.f_77272_, p_77641_.f_77273_, $$3, $$6, Direction.EAST, $$5), p_77641_)) {
            p_77640_[$$2++] = $$9;
        }
        if (this.m_77626_($$10 = this.m_164725_(p_77641_.f_77271_, p_77641_.f_77272_, p_77641_.f_77273_ - 1, $$3, $$6, Direction.NORTH, $$5), p_77641_)) {
            p_77640_[$$2++] = $$10;
        }
        if (this.m_77629_(p_77641_, $$8, $$10, $$11 = this.m_164725_(p_77641_.f_77271_ - 1, p_77641_.f_77272_, p_77641_.f_77273_ - 1, $$3, $$6, Direction.NORTH, $$5))) {
            p_77640_[$$2++] = $$11;
        }
        if (this.m_77629_(p_77641_, $$9, $$10, $$12 = this.m_164725_(p_77641_.f_77271_ + 1, p_77641_.f_77272_, p_77641_.f_77273_ - 1, $$3, $$6, Direction.NORTH, $$5))) {
            p_77640_[$$2++] = $$12;
        }
        if (this.m_77629_(p_77641_, $$8, $$7, $$13 = this.m_164725_(p_77641_.f_77271_ - 1, p_77641_.f_77272_, p_77641_.f_77273_ + 1, $$3, $$6, Direction.SOUTH, $$5))) {
            p_77640_[$$2++] = $$13;
        }
        if (this.m_77629_(p_77641_, $$9, $$7, $$14 = this.m_164725_(p_77641_.f_77271_ + 1, p_77641_.f_77272_, p_77641_.f_77273_ + 1, $$3, $$6, Direction.SOUTH, $$5))) {
            p_77640_[$$2++] = $$14;
        }
        return $$2;
    }

    protected boolean m_77626_(@Nullable Node p_77627_, Node p_77628_) {
        return p_77627_ != null && !p_77627_.f_77279_ && (p_77627_.f_77281_ >= 0.0f || p_77628_.f_77281_ < 0.0f);
    }

    protected boolean m_77629_(Node p_77630_, @Nullable Node p_77631_, @Nullable Node p_77632_, @Nullable Node p_77633_) {
        if (p_77633_ == null || p_77632_ == null || p_77631_ == null) {
            return false;
        }
        if (p_77633_.f_77279_) {
            return false;
        }
        if (p_77632_.f_77272_ > p_77630_.f_77272_ || p_77631_.f_77272_ > p_77630_.f_77272_) {
            return false;
        }
        if (p_77631_.f_77282_ == BlockPathTypes.WALKABLE_DOOR || p_77632_.f_77282_ == BlockPathTypes.WALKABLE_DOOR || p_77633_.f_77282_ == BlockPathTypes.WALKABLE_DOOR) {
            return false;
        }
        boolean $$4 = p_77632_.f_77282_ == BlockPathTypes.FENCE && p_77631_.f_77282_ == BlockPathTypes.FENCE && (double)this.f_77313_.m_20205_() < 0.5;
        return p_77633_.f_77281_ >= 0.0f && (p_77632_.f_77272_ < p_77630_.f_77272_ || p_77632_.f_77281_ >= 0.0f || $$4) && (p_77631_.f_77272_ < p_77630_.f_77272_ || p_77631_.f_77281_ >= 0.0f || $$4);
    }

    private static boolean m_230625_(BlockPathTypes p_230626_) {
        return p_230626_ == BlockPathTypes.FENCE || p_230626_ == BlockPathTypes.DOOR_WOOD_CLOSED || p_230626_ == BlockPathTypes.DOOR_IRON_CLOSED;
    }

    private boolean m_77624_(Node p_77625_) {
        AABB $$1 = this.f_77313_.m_20191_();
        Vec3 $$2 = new Vec3((double)p_77625_.f_77271_ - this.f_77313_.m_20185_() + $$1.m_82362_() / 2.0, (double)p_77625_.f_77272_ - this.f_77313_.m_20186_() + $$1.m_82376_() / 2.0, (double)p_77625_.f_77273_ - this.f_77313_.m_20189_() + $$1.m_82385_() / 2.0);
        int $$3 = Mth.m_14165_($$2.m_82553_() / $$1.m_82309_());
        $$2 = $$2.m_82490_(1.0f / (float)$$3);
        for (int $$4 = 1; $$4 <= $$3; ++$$4) {
            if (!this.m_77634_($$1 = $$1.m_82383_($$2))) continue;
            return false;
        }
        return true;
    }

    protected double m_142213_(BlockPos p_164733_) {
        return WalkNodeEvaluator.m_77611_(this.f_77312_, p_164733_);
    }

    public static double m_77611_(BlockGetter p_77612_, BlockPos p_77613_) {
        BlockPos $$2 = p_77613_.m_7495_();
        VoxelShape $$3 = p_77612_.m_8055_($$2).m_60812_(p_77612_, $$2);
        return (double)$$2.m_123342_() + ($$3.m_83281_() ? 0.0 : $$3.m_83297_(Direction.Axis.Y));
    }

    protected boolean m_141974_() {
        return false;
    }

    @Nullable
    protected Node m_164725_(int p_164726_, int p_164727_, int p_164728_, int p_164729_, double p_164730_, Direction p_164731_, BlockPathTypes p_164732_) {
        double $$14;
        double $$13;
        AABB $$15;
        Node $$7 = null;
        BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos();
        double $$9 = this.m_142213_($$8.m_122178_(p_164726_, p_164727_, p_164728_));
        if ($$9 - p_164730_ > 1.125) {
            return null;
        }
        BlockPathTypes $$10 = this.m_77567_(this.f_77313_, p_164726_, p_164727_, p_164728_);
        float $$11 = this.f_77313_.m_21439_($$10);
        double $$12 = (double)this.f_77313_.m_20205_() / 2.0;
        if ($$11 >= 0.0f) {
            $$7 = this.m_230619_(p_164726_, p_164727_, p_164728_, $$10, $$11);
        }
        if (WalkNodeEvaluator.m_230625_(p_164732_) && $$7 != null && $$7.f_77281_ >= 0.0f && !this.m_77624_($$7)) {
            $$7 = null;
        }
        if ($$10 == BlockPathTypes.WALKABLE || this.m_141974_() && $$10 == BlockPathTypes.WATER) {
            return $$7;
        }
        if (($$7 == null || $$7.f_77281_ < 0.0f) && p_164729_ > 0 && $$10 != BlockPathTypes.FENCE && $$10 != BlockPathTypes.UNPASSABLE_RAIL && $$10 != BlockPathTypes.TRAPDOOR && $$10 != BlockPathTypes.POWDER_SNOW && ($$7 = this.m_164725_(p_164726_, p_164727_ + 1, p_164728_, p_164729_ - 1, p_164730_, p_164731_, p_164732_)) != null && ($$7.f_77282_ == BlockPathTypes.OPEN || $$7.f_77282_ == BlockPathTypes.WALKABLE) && this.f_77313_.m_20205_() < 1.0f && this.m_77634_($$15 = new AABB(($$13 = (double)(p_164726_ - p_164731_.m_122429_()) + 0.5) - $$12, WalkNodeEvaluator.m_77611_(this.f_77312_, $$8.m_122169_($$13, p_164727_ + 1, $$14 = (double)(p_164728_ - p_164731_.m_122431_()) + 0.5)) + 0.001, $$14 - $$12, $$13 + $$12, (double)this.f_77313_.m_20206_() + WalkNodeEvaluator.m_77611_(this.f_77312_, $$8.m_122169_($$7.f_77271_, $$7.f_77272_, $$7.f_77273_)) - 0.002, $$14 + $$12))) {
            $$7 = null;
        }
        if (!this.m_141974_() && $$10 == BlockPathTypes.WATER && !this.m_77361_()) {
            if (this.m_77567_(this.f_77313_, p_164726_, p_164727_ - 1, p_164728_) != BlockPathTypes.WATER) {
                return $$7;
            }
            while (p_164727_ > this.f_77313_.f_19853_.m_141937_()) {
                if (($$10 = this.m_77567_(this.f_77313_, p_164726_, --p_164727_, p_164728_)) == BlockPathTypes.WATER) {
                    $$7 = this.m_230619_(p_164726_, p_164727_, p_164728_, $$10, this.f_77313_.m_21439_($$10));
                    continue;
                }
                return $$7;
            }
        }
        if ($$10 == BlockPathTypes.OPEN) {
            int $$16 = 0;
            int $$17 = p_164727_;
            while ($$10 == BlockPathTypes.OPEN) {
                if (--p_164727_ < this.f_77313_.f_19853_.m_141937_()) {
                    return this.m_230627_(p_164726_, $$17, p_164728_);
                }
                if ($$16++ >= this.f_77313_.m_6056_()) {
                    return this.m_230627_(p_164726_, p_164727_, p_164728_);
                }
                $$10 = this.m_77567_(this.f_77313_, p_164726_, p_164727_, p_164728_);
                $$11 = this.f_77313_.m_21439_($$10);
                if ($$10 != BlockPathTypes.OPEN && $$11 >= 0.0f) {
                    $$7 = this.m_230619_(p_164726_, p_164727_, p_164728_, $$10, $$11);
                    break;
                }
                if (!($$11 < 0.0f)) continue;
                return this.m_230627_(p_164726_, p_164727_, p_164728_);
            }
        }
        if (WalkNodeEvaluator.m_230625_($$10) && ($$7 = this.m_5676_(p_164726_, p_164727_, p_164728_)) != null) {
            $$7.f_77279_ = true;
            $$7.f_77282_ = $$10;
            $$7.f_77281_ = $$10.m_77124_();
        }
        return $$7;
    }

    @Nullable
    private Node m_230619_(int p_230620_, int p_230621_, int p_230622_, BlockPathTypes p_230623_, float p_230624_) {
        Node $$5 = this.m_5676_(p_230620_, p_230621_, p_230622_);
        if ($$5 != null) {
            $$5.f_77282_ = p_230623_;
            $$5.f_77281_ = Math.max($$5.f_77281_, p_230624_);
        }
        return $$5;
    }

    @Nullable
    private Node m_230627_(int p_230628_, int p_230629_, int p_230630_) {
        Node $$3 = this.m_5676_(p_230628_, p_230629_, p_230630_);
        if ($$3 != null) {
            $$3.f_77282_ = BlockPathTypes.BLOCKED;
            $$3.f_77281_ = -1.0f;
        }
        return $$3;
    }

    private boolean m_77634_(AABB p_77635_) {
        return this.f_77546_.computeIfAbsent((Object)p_77635_, p_192973_ -> !this.f_77312_.m_45756_(this.f_77313_, p_77635_));
    }

    @Override
    public BlockPathTypes m_7209_(BlockGetter p_77594_, int p_77595_, int p_77596_, int p_77597_, Mob p_77598_, int p_77599_, int p_77600_, int p_77601_, boolean p_77602_, boolean p_77603_) {
        EnumSet<BlockPathTypes> $$10 = EnumSet.noneOf(BlockPathTypes.class);
        BlockPathTypes $$11 = BlockPathTypes.BLOCKED;
        BlockPos $$12 = p_77598_.m_20183_();
        $$11 = this.m_77580_(p_77594_, p_77595_, p_77596_, p_77597_, p_77599_, p_77600_, p_77601_, p_77602_, p_77603_, $$10, $$11, $$12);
        if ($$10.contains((Object)BlockPathTypes.FENCE)) {
            return BlockPathTypes.FENCE;
        }
        if ($$10.contains((Object)BlockPathTypes.UNPASSABLE_RAIL)) {
            return BlockPathTypes.UNPASSABLE_RAIL;
        }
        BlockPathTypes $$13 = BlockPathTypes.BLOCKED;
        for (BlockPathTypes $$14 : $$10) {
            if (p_77598_.m_21439_($$14) < 0.0f) {
                return $$14;
            }
            if (!(p_77598_.m_21439_($$14) >= p_77598_.m_21439_($$13))) continue;
            $$13 = $$14;
        }
        if ($$11 == BlockPathTypes.OPEN && p_77598_.m_21439_($$13) == 0.0f && p_77599_ <= 1) {
            return BlockPathTypes.OPEN;
        }
        return $$13;
    }

    public BlockPathTypes m_77580_(BlockGetter p_77581_, int p_77582_, int p_77583_, int p_77584_, int p_77585_, int p_77586_, int p_77587_, boolean p_77588_, boolean p_77589_, EnumSet<BlockPathTypes> p_77590_, BlockPathTypes p_77591_, BlockPos p_77592_) {
        for (int $$12 = 0; $$12 < p_77585_; ++$$12) {
            for (int $$13 = 0; $$13 < p_77586_; ++$$13) {
                for (int $$14 = 0; $$14 < p_77587_; ++$$14) {
                    int $$15 = $$12 + p_77582_;
                    int $$16 = $$13 + p_77583_;
                    int $$17 = $$14 + p_77584_;
                    BlockPathTypes $$18 = this.m_8086_(p_77581_, $$15, $$16, $$17);
                    $$18 = this.m_6603_(p_77581_, p_77588_, p_77589_, p_77592_, $$18);
                    if ($$12 == 0 && $$13 == 0 && $$14 == 0) {
                        p_77591_ = $$18;
                    }
                    p_77590_.add($$18);
                }
            }
        }
        return p_77591_;
    }

    protected BlockPathTypes m_6603_(BlockGetter p_77614_, boolean p_77615_, boolean p_77616_, BlockPos p_77617_, BlockPathTypes p_77618_) {
        if (p_77618_ == BlockPathTypes.DOOR_WOOD_CLOSED && p_77615_ && p_77616_) {
            p_77618_ = BlockPathTypes.WALKABLE_DOOR;
        }
        if (p_77618_ == BlockPathTypes.DOOR_OPEN && !p_77616_) {
            p_77618_ = BlockPathTypes.BLOCKED;
        }
        if (p_77618_ == BlockPathTypes.RAIL && !(p_77614_.m_8055_(p_77617_).m_60734_() instanceof BaseRailBlock) && !(p_77614_.m_8055_(p_77617_.m_7495_()).m_60734_() instanceof BaseRailBlock)) {
            p_77618_ = BlockPathTypes.UNPASSABLE_RAIL;
        }
        if (p_77618_ == BlockPathTypes.LEAVES) {
            p_77618_ = BlockPathTypes.BLOCKED;
        }
        return p_77618_;
    }

    private BlockPathTypes m_77572_(Mob p_77573_, BlockPos p_77574_) {
        return this.m_77567_(p_77573_, p_77574_.m_123341_(), p_77574_.m_123342_(), p_77574_.m_123343_());
    }

    protected BlockPathTypes m_77567_(Mob p_77568_, int p_77569_, int p_77570_, int p_77571_) {
        return (BlockPathTypes)((Object)this.f_77545_.computeIfAbsent(BlockPos.m_121882_(p_77569_, p_77570_, p_77571_), p_77566_ -> this.m_7209_(this.f_77312_, p_77569_, p_77570_, p_77571_, p_77568_, this.f_77315_, this.f_77316_, this.f_77317_, this.m_77360_(), this.m_77357_())));
    }

    @Override
    public BlockPathTypes m_8086_(BlockGetter p_77576_, int p_77577_, int p_77578_, int p_77579_) {
        return WalkNodeEvaluator.m_77604_(p_77576_, new BlockPos.MutableBlockPos(p_77577_, p_77578_, p_77579_));
    }

    public static BlockPathTypes m_77604_(BlockGetter p_77605_, BlockPos.MutableBlockPos p_77606_) {
        int $$2 = p_77606_.m_123341_();
        int $$3 = p_77606_.m_123342_();
        int $$4 = p_77606_.m_123343_();
        BlockPathTypes $$5 = WalkNodeEvaluator.m_77643_(p_77605_, p_77606_);
        if ($$5 == BlockPathTypes.OPEN && $$3 >= p_77605_.m_141937_() + 1) {
            BlockPathTypes $$6 = WalkNodeEvaluator.m_77643_(p_77605_, p_77606_.m_122178_($$2, $$3 - 1, $$4));
            BlockPathTypes blockPathTypes = $$5 = $$6 == BlockPathTypes.WALKABLE || $$6 == BlockPathTypes.OPEN || $$6 == BlockPathTypes.WATER || $$6 == BlockPathTypes.LAVA ? BlockPathTypes.OPEN : BlockPathTypes.WALKABLE;
            if ($$6 == BlockPathTypes.DAMAGE_FIRE) {
                $$5 = BlockPathTypes.DAMAGE_FIRE;
            }
            if ($$6 == BlockPathTypes.DAMAGE_CACTUS) {
                $$5 = BlockPathTypes.DAMAGE_CACTUS;
            }
            if ($$6 == BlockPathTypes.DAMAGE_OTHER) {
                $$5 = BlockPathTypes.DAMAGE_OTHER;
            }
            if ($$6 == BlockPathTypes.STICKY_HONEY) {
                $$5 = BlockPathTypes.STICKY_HONEY;
            }
            if ($$6 == BlockPathTypes.POWDER_SNOW) {
                $$5 = BlockPathTypes.DANGER_POWDER_SNOW;
            }
        }
        if ($$5 == BlockPathTypes.WALKABLE) {
            $$5 = WalkNodeEvaluator.m_77607_(p_77605_, p_77606_.m_122178_($$2, $$3, $$4), $$5);
        }
        return $$5;
    }

    public static BlockPathTypes m_77607_(BlockGetter p_77608_, BlockPos.MutableBlockPos p_77609_, BlockPathTypes p_77610_) {
        int $$3 = p_77609_.m_123341_();
        int $$4 = p_77609_.m_123342_();
        int $$5 = p_77609_.m_123343_();
        for (int $$6 = -1; $$6 <= 1; ++$$6) {
            for (int $$7 = -1; $$7 <= 1; ++$$7) {
                for (int $$8 = -1; $$8 <= 1; ++$$8) {
                    if ($$6 == 0 && $$8 == 0) continue;
                    p_77609_.m_122178_($$3 + $$6, $$4 + $$7, $$5 + $$8);
                    BlockState $$9 = p_77608_.m_8055_(p_77609_);
                    if ($$9.m_60713_(Blocks.f_50128_)) {
                        return BlockPathTypes.DANGER_CACTUS;
                    }
                    if ($$9.m_60713_(Blocks.f_50685_)) {
                        return BlockPathTypes.DANGER_OTHER;
                    }
                    if (WalkNodeEvaluator.m_77622_($$9)) {
                        return BlockPathTypes.DANGER_FIRE;
                    }
                    if (!p_77608_.m_6425_(p_77609_).m_205070_(FluidTags.f_13131_)) continue;
                    return BlockPathTypes.WATER_BORDER;
                }
            }
        }
        return p_77610_;
    }

    protected static BlockPathTypes m_77643_(BlockGetter p_77644_, BlockPos p_77645_) {
        BlockState $$2 = p_77644_.m_8055_(p_77645_);
        Block $$3 = $$2.m_60734_();
        Material $$4 = $$2.m_60767_();
        if ($$2.m_60795_()) {
            return BlockPathTypes.OPEN;
        }
        if ($$2.m_204336_(BlockTags.f_13036_) || $$2.m_60713_(Blocks.f_50196_) || $$2.m_60713_(Blocks.f_152545_)) {
            return BlockPathTypes.TRAPDOOR;
        }
        if ($$2.m_60713_(Blocks.f_152499_)) {
            return BlockPathTypes.POWDER_SNOW;
        }
        if ($$2.m_60713_(Blocks.f_50128_)) {
            return BlockPathTypes.DAMAGE_CACTUS;
        }
        if ($$2.m_60713_(Blocks.f_50685_)) {
            return BlockPathTypes.DAMAGE_OTHER;
        }
        if ($$2.m_60713_(Blocks.f_50719_)) {
            return BlockPathTypes.STICKY_HONEY;
        }
        if ($$2.m_60713_(Blocks.f_50262_)) {
            return BlockPathTypes.COCOA;
        }
        FluidState $$5 = p_77644_.m_6425_(p_77645_);
        if ($$5.m_205070_(FluidTags.f_13132_)) {
            return BlockPathTypes.LAVA;
        }
        if (WalkNodeEvaluator.m_77622_($$2)) {
            return BlockPathTypes.DAMAGE_FIRE;
        }
        if (DoorBlock.m_52817_($$2) && !$$2.m_61143_(DoorBlock.f_52727_).booleanValue()) {
            return BlockPathTypes.DOOR_WOOD_CLOSED;
        }
        if ($$3 instanceof DoorBlock && $$4 == Material.f_76279_ && !$$2.m_61143_(DoorBlock.f_52727_).booleanValue()) {
            return BlockPathTypes.DOOR_IRON_CLOSED;
        }
        if ($$3 instanceof DoorBlock && $$2.m_61143_(DoorBlock.f_52727_).booleanValue()) {
            return BlockPathTypes.DOOR_OPEN;
        }
        if ($$3 instanceof BaseRailBlock) {
            return BlockPathTypes.RAIL;
        }
        if ($$3 instanceof LeavesBlock) {
            return BlockPathTypes.LEAVES;
        }
        if ($$2.m_204336_(BlockTags.f_13039_) || $$2.m_204336_(BlockTags.f_13032_) || $$3 instanceof FenceGateBlock && !$$2.m_61143_(FenceGateBlock.f_53341_).booleanValue()) {
            return BlockPathTypes.FENCE;
        }
        if (!$$2.m_60647_(p_77644_, p_77645_, PathComputationType.LAND)) {
            return BlockPathTypes.BLOCKED;
        }
        if ($$5.m_205070_(FluidTags.f_13131_)) {
            return BlockPathTypes.WATER;
        }
        return BlockPathTypes.OPEN;
    }

    public static boolean m_77622_(BlockState p_77623_) {
        return p_77623_.m_204336_(BlockTags.f_13076_) || p_77623_.m_60713_(Blocks.f_49991_) || p_77623_.m_60713_(Blocks.f_50450_) || CampfireBlock.m_51319_(p_77623_) || p_77623_.m_60713_(Blocks.f_152477_);
    }
}

