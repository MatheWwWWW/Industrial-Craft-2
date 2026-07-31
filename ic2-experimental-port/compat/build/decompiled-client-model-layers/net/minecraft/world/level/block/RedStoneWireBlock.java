/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.math.Vector3f;
import java.util.HashSet;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RedStoneWireBlock
extends Block {
    public static final EnumProperty<RedstoneSide> f_55496_ = BlockStateProperties.f_61383_;
    public static final EnumProperty<RedstoneSide> f_55497_ = BlockStateProperties.f_61382_;
    public static final EnumProperty<RedstoneSide> f_55498_ = BlockStateProperties.f_61384_;
    public static final EnumProperty<RedstoneSide> f_55499_ = BlockStateProperties.f_61385_;
    public static final IntegerProperty f_55500_ = BlockStateProperties.f_61426_;
    public static final Map<Direction, EnumProperty<RedstoneSide>> f_55501_ = Maps.newEnumMap((Map)ImmutableMap.of((Object)Direction.NORTH, f_55496_, (Object)Direction.EAST, f_55497_, (Object)Direction.SOUTH, f_55498_, (Object)Direction.WEST, f_55499_));
    protected static final int f_154304_ = 1;
    protected static final int f_154305_ = 3;
    protected static final int f_154306_ = 13;
    protected static final int f_154307_ = 3;
    protected static final int f_154308_ = 13;
    private static final VoxelShape f_55502_ = Block.m_49796_(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);
    private static final Map<Direction, VoxelShape> f_55503_ = Maps.newEnumMap((Map)ImmutableMap.of((Object)Direction.NORTH, (Object)Block.m_49796_(3.0, 0.0, 0.0, 13.0, 1.0, 13.0), (Object)Direction.SOUTH, (Object)Block.m_49796_(3.0, 0.0, 3.0, 13.0, 1.0, 16.0), (Object)Direction.EAST, (Object)Block.m_49796_(3.0, 0.0, 3.0, 16.0, 1.0, 13.0), (Object)Direction.WEST, (Object)Block.m_49796_(0.0, 0.0, 3.0, 13.0, 1.0, 13.0)));
    private static final Map<Direction, VoxelShape> f_55504_ = Maps.newEnumMap((Map)ImmutableMap.of((Object)Direction.NORTH, (Object)Shapes.m_83110_(f_55503_.get(Direction.NORTH), Block.m_49796_(3.0, 0.0, 0.0, 13.0, 16.0, 1.0)), (Object)Direction.SOUTH, (Object)Shapes.m_83110_(f_55503_.get(Direction.SOUTH), Block.m_49796_(3.0, 0.0, 15.0, 13.0, 16.0, 16.0)), (Object)Direction.EAST, (Object)Shapes.m_83110_(f_55503_.get(Direction.EAST), Block.m_49796_(15.0, 0.0, 3.0, 16.0, 16.0, 13.0)), (Object)Direction.WEST, (Object)Shapes.m_83110_(f_55503_.get(Direction.WEST), Block.m_49796_(0.0, 0.0, 3.0, 1.0, 16.0, 13.0))));
    private static final Map<BlockState, VoxelShape> f_55505_ = Maps.newHashMap();
    private static final Vec3[] f_55506_ = Util.m_137469_(new Vec3[16], p_154319_ -> {
        for (int $$1 = 0; $$1 <= 15; ++$$1) {
            float $$2;
            float $$3 = $$2 * 0.6f + (($$2 = (float)$$1 / 15.0f) > 0.0f ? 0.4f : 0.3f);
            float $$4 = Mth.m_14036_($$2 * $$2 * 0.7f - 0.5f, 0.0f, 1.0f);
            float $$5 = Mth.m_14036_($$2 * $$2 * 0.6f - 0.7f, 0.0f, 1.0f);
            p_154319_[$$1] = new Vec3($$3, $$4, $$5);
        }
    });
    private static final float f_154303_ = 0.2f;
    private final BlockState f_55507_;
    private boolean f_55508_ = true;

    public RedStoneWireBlock(BlockBehaviour.Properties p_55511_) {
        super(p_55511_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_55496_, RedstoneSide.NONE)).m_61124_(f_55497_, RedstoneSide.NONE)).m_61124_(f_55498_, RedstoneSide.NONE)).m_61124_(f_55499_, RedstoneSide.NONE)).m_61124_(f_55500_, 0));
        this.f_55507_ = (BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(f_55496_, RedstoneSide.SIDE)).m_61124_(f_55497_, RedstoneSide.SIDE)).m_61124_(f_55498_, RedstoneSide.SIDE)).m_61124_(f_55499_, RedstoneSide.SIDE);
        for (BlockState $$1 : this.m_49965_().m_61056_()) {
            if ($$1.m_61143_(f_55500_) != 0) continue;
            f_55505_.put($$1, this.m_55642_($$1));
        }
    }

    private VoxelShape m_55642_(BlockState p_55643_) {
        VoxelShape $$1 = f_55502_;
        for (Direction $$2 : Direction.Plane.HORIZONTAL) {
            RedstoneSide $$3 = (RedstoneSide)p_55643_.m_61143_(f_55501_.get($$2));
            if ($$3 == RedstoneSide.SIDE) {
                $$1 = Shapes.m_83110_($$1, f_55503_.get($$2));
                continue;
            }
            if ($$3 != RedstoneSide.UP) continue;
            $$1 = Shapes.m_83110_($$1, f_55504_.get($$2));
        }
        return $$1;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_55620_, BlockGetter p_55621_, BlockPos p_55622_, CollisionContext p_55623_) {
        return f_55505_.get(p_55620_.m_61124_(f_55500_, 0));
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_55513_) {
        return this.m_55514_(p_55513_.m_43725_(), this.f_55507_, p_55513_.m_8083_());
    }

    private BlockState m_55514_(BlockGetter p_55515_, BlockState p_55516_, BlockPos p_55517_) {
        boolean $$9;
        boolean $$3 = RedStoneWireBlock.m_55646_(p_55516_);
        p_55516_ = this.m_55608_(p_55515_, (BlockState)this.m_49966_().m_61124_(f_55500_, p_55516_.m_61143_(f_55500_)), p_55517_);
        if ($$3 && RedStoneWireBlock.m_55646_(p_55516_)) {
            return p_55516_;
        }
        boolean $$4 = p_55516_.m_61143_(f_55496_).m_61761_();
        boolean $$5 = p_55516_.m_61143_(f_55498_).m_61761_();
        boolean $$6 = p_55516_.m_61143_(f_55497_).m_61761_();
        boolean $$7 = p_55516_.m_61143_(f_55499_).m_61761_();
        boolean $$8 = !$$4 && !$$5;
        boolean bl = $$9 = !$$6 && !$$7;
        if (!$$7 && $$8) {
            p_55516_ = (BlockState)p_55516_.m_61124_(f_55499_, RedstoneSide.SIDE);
        }
        if (!$$6 && $$8) {
            p_55516_ = (BlockState)p_55516_.m_61124_(f_55497_, RedstoneSide.SIDE);
        }
        if (!$$4 && $$9) {
            p_55516_ = (BlockState)p_55516_.m_61124_(f_55496_, RedstoneSide.SIDE);
        }
        if (!$$5 && $$9) {
            p_55516_ = (BlockState)p_55516_.m_61124_(f_55498_, RedstoneSide.SIDE);
        }
        return p_55516_;
    }

    private BlockState m_55608_(BlockGetter p_55609_, BlockState p_55610_, BlockPos p_55611_) {
        boolean $$3 = !p_55609_.m_8055_(p_55611_.m_7494_()).m_60796_(p_55609_, p_55611_);
        for (Direction $$4 : Direction.Plane.HORIZONTAL) {
            if (((RedstoneSide)p_55610_.m_61143_(f_55501_.get($$4))).m_61761_()) continue;
            RedstoneSide $$5 = this.m_55522_(p_55609_, p_55611_, $$4, $$3);
            p_55610_ = (BlockState)p_55610_.m_61124_(f_55501_.get($$4), $$5);
        }
        return p_55610_;
    }

    @Override
    public BlockState m_7417_(BlockState p_55598_, Direction p_55599_, BlockState p_55600_, LevelAccessor p_55601_, BlockPos p_55602_, BlockPos p_55603_) {
        if (p_55599_ == Direction.DOWN) {
            return p_55598_;
        }
        if (p_55599_ == Direction.UP) {
            return this.m_55514_(p_55601_, p_55598_, p_55602_);
        }
        RedstoneSide $$6 = this.m_55518_(p_55601_, p_55602_, p_55599_);
        if ($$6.m_61761_() == ((RedstoneSide)p_55598_.m_61143_(f_55501_.get(p_55599_))).m_61761_() && !RedStoneWireBlock.m_55644_(p_55598_)) {
            return (BlockState)p_55598_.m_61124_(f_55501_.get(p_55599_), $$6);
        }
        return this.m_55514_(p_55601_, (BlockState)((BlockState)this.f_55507_.m_61124_(f_55500_, p_55598_.m_61143_(f_55500_))).m_61124_(f_55501_.get(p_55599_), $$6), p_55602_);
    }

    private static boolean m_55644_(BlockState p_55645_) {
        return p_55645_.m_61143_(f_55496_).m_61761_() && p_55645_.m_61143_(f_55498_).m_61761_() && p_55645_.m_61143_(f_55497_).m_61761_() && p_55645_.m_61143_(f_55499_).m_61761_();
    }

    private static boolean m_55646_(BlockState p_55647_) {
        return !p_55647_.m_61143_(f_55496_).m_61761_() && !p_55647_.m_61143_(f_55498_).m_61761_() && !p_55647_.m_61143_(f_55497_).m_61761_() && !p_55647_.m_61143_(f_55499_).m_61761_();
    }

    @Override
    public void m_7742_(BlockState p_55579_, LevelAccessor p_55580_, BlockPos p_55581_, int p_55582_, int p_55583_) {
        BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
        for (Direction $$6 : Direction.Plane.HORIZONTAL) {
            RedstoneSide $$7 = (RedstoneSide)p_55579_.m_61143_(f_55501_.get($$6));
            if ($$7 == RedstoneSide.NONE || p_55580_.m_8055_($$5.m_122159_(p_55581_, $$6)).m_60713_(this)) continue;
            $$5.m_122173_(Direction.DOWN);
            BlockState $$8 = p_55580_.m_8055_($$5);
            if ($$8.m_60713_(this)) {
                Vec3i $$9 = $$5.m_121945_($$6.m_122424_());
                p_55580_.m_213683_($$6.m_122424_(), p_55580_.m_8055_((BlockPos)$$9), $$5, (BlockPos)$$9, p_55582_, p_55583_);
            }
            $$5.m_122159_(p_55581_, $$6).m_122173_(Direction.UP);
            BlockState $$10 = p_55580_.m_8055_($$5);
            if (!$$10.m_60713_(this)) continue;
            Vec3i $$11 = $$5.m_121945_($$6.m_122424_());
            p_55580_.m_213683_($$6.m_122424_(), p_55580_.m_8055_((BlockPos)$$11), $$5, (BlockPos)$$11, p_55582_, p_55583_);
        }
    }

    private RedstoneSide m_55518_(BlockGetter p_55519_, BlockPos p_55520_, Direction p_55521_) {
        return this.m_55522_(p_55519_, p_55520_, p_55521_, !p_55519_.m_8055_(p_55520_.m_7494_()).m_60796_(p_55519_, p_55520_));
    }

    private RedstoneSide m_55522_(BlockGetter p_55523_, BlockPos p_55524_, Direction p_55525_, boolean p_55526_) {
        boolean $$6;
        BlockPos $$4 = p_55524_.m_121945_(p_55525_);
        BlockState $$5 = p_55523_.m_8055_($$4);
        if (p_55526_ && ($$6 = this.m_55612_(p_55523_, $$4, $$5)) && RedStoneWireBlock.m_55640_(p_55523_.m_8055_($$4.m_7494_()))) {
            if ($$5.m_60783_(p_55523_, $$4, p_55525_.m_122424_())) {
                return RedstoneSide.UP;
            }
            return RedstoneSide.SIDE;
        }
        if (RedStoneWireBlock.m_55594_($$5, p_55525_) || !$$5.m_60796_(p_55523_, $$4) && RedStoneWireBlock.m_55640_(p_55523_.m_8055_($$4.m_7495_()))) {
            return RedstoneSide.SIDE;
        }
        return RedstoneSide.NONE;
    }

    @Override
    public boolean m_7898_(BlockState p_55585_, LevelReader p_55586_, BlockPos p_55587_) {
        BlockPos $$3 = p_55587_.m_7495_();
        BlockState $$4 = p_55586_.m_8055_($$3);
        return this.m_55612_(p_55586_, $$3, $$4);
    }

    private boolean m_55612_(BlockGetter p_55613_, BlockPos p_55614_, BlockState p_55615_) {
        return p_55615_.m_60783_(p_55613_, p_55614_, Direction.UP) || p_55615_.m_60713_(Blocks.f_50332_);
    }

    private void m_55530_(Level p_55531_, BlockPos p_55532_, BlockState p_55533_) {
        int $$3 = this.m_55527_(p_55531_, p_55532_);
        if (p_55533_.m_61143_(f_55500_) != $$3) {
            if (p_55531_.m_8055_(p_55532_) == p_55533_) {
                p_55531_.m_7731_(p_55532_, (BlockState)p_55533_.m_61124_(f_55500_, $$3), 2);
            }
            HashSet $$4 = Sets.newHashSet();
            $$4.add(p_55532_);
            for (Direction $$5 : Direction.values()) {
                $$4.add(p_55532_.m_121945_($$5));
            }
            for (BlockPos $$6 : $$4) {
                p_55531_.m_46672_($$6, this);
            }
        }
    }

    private int m_55527_(Level p_55528_, BlockPos p_55529_) {
        this.f_55508_ = false;
        int $$2 = p_55528_.m_46755_(p_55529_);
        this.f_55508_ = true;
        int $$3 = 0;
        if ($$2 < 15) {
            for (Direction $$4 : Direction.Plane.HORIZONTAL) {
                BlockPos $$5 = p_55529_.m_121945_($$4);
                BlockState $$6 = p_55528_.m_8055_($$5);
                $$3 = Math.max($$3, this.m_55648_($$6));
                BlockPos $$7 = p_55529_.m_7494_();
                if ($$6.m_60796_(p_55528_, $$5) && !p_55528_.m_8055_($$7).m_60796_(p_55528_, $$7)) {
                    $$3 = Math.max($$3, this.m_55648_(p_55528_.m_8055_($$5.m_7494_())));
                    continue;
                }
                if ($$6.m_60796_(p_55528_, $$5)) continue;
                $$3 = Math.max($$3, this.m_55648_(p_55528_.m_8055_($$5.m_7495_())));
            }
        }
        return Math.max($$2, $$3 - 1);
    }

    private int m_55648_(BlockState p_55649_) {
        return p_55649_.m_60713_(this) ? p_55649_.m_61143_(f_55500_) : 0;
    }

    private void m_55616_(Level p_55617_, BlockPos p_55618_) {
        if (!p_55617_.m_8055_(p_55618_).m_60713_(this)) {
            return;
        }
        p_55617_.m_46672_(p_55618_, this);
        for (Direction $$2 : Direction.values()) {
            p_55617_.m_46672_(p_55618_.m_121945_($$2), this);
        }
    }

    @Override
    public void m_6807_(BlockState p_55630_, Level p_55631_, BlockPos p_55632_, BlockState p_55633_, boolean p_55634_) {
        if (p_55633_.m_60713_(p_55630_.m_60734_()) || p_55631_.f_46443_) {
            return;
        }
        this.m_55530_(p_55631_, p_55632_, p_55630_);
        for (Direction $$5 : Direction.Plane.VERTICAL) {
            p_55631_.m_46672_(p_55632_.m_121945_($$5), this);
        }
        this.m_55637_(p_55631_, p_55632_);
    }

    @Override
    public void m_6810_(BlockState p_55568_, Level p_55569_, BlockPos p_55570_, BlockState p_55571_, boolean p_55572_) {
        if (p_55572_ || p_55568_.m_60713_(p_55571_.m_60734_())) {
            return;
        }
        super.m_6810_(p_55568_, p_55569_, p_55570_, p_55571_, p_55572_);
        if (p_55569_.f_46443_) {
            return;
        }
        for (Direction $$5 : Direction.values()) {
            p_55569_.m_46672_(p_55570_.m_121945_($$5), this);
        }
        this.m_55530_(p_55569_, p_55570_, p_55568_);
        this.m_55637_(p_55569_, p_55570_);
    }

    private void m_55637_(Level p_55638_, BlockPos p_55639_) {
        for (Direction $$2 : Direction.Plane.HORIZONTAL) {
            this.m_55616_(p_55638_, p_55639_.m_121945_($$2));
        }
        for (Direction $$3 : Direction.Plane.HORIZONTAL) {
            BlockPos $$4 = p_55639_.m_121945_($$3);
            if (p_55638_.m_8055_($$4).m_60796_(p_55638_, $$4)) {
                this.m_55616_(p_55638_, $$4.m_7494_());
                continue;
            }
            this.m_55616_(p_55638_, $$4.m_7495_());
        }
    }

    @Override
    public void m_6861_(BlockState p_55561_, Level p_55562_, BlockPos p_55563_, Block p_55564_, BlockPos p_55565_, boolean p_55566_) {
        if (p_55562_.f_46443_) {
            return;
        }
        if (p_55561_.m_60710_(p_55562_, p_55563_)) {
            this.m_55530_(p_55562_, p_55563_, p_55561_);
        } else {
            RedStoneWireBlock.m_49950_(p_55561_, p_55562_, p_55563_);
            p_55562_.m_7471_(p_55563_, false);
        }
    }

    @Override
    public int m_6376_(BlockState p_55625_, BlockGetter p_55626_, BlockPos p_55627_, Direction p_55628_) {
        if (!this.f_55508_) {
            return 0;
        }
        return p_55625_.m_60746_(p_55626_, p_55627_, p_55628_);
    }

    @Override
    public int m_6378_(BlockState p_55549_, BlockGetter p_55550_, BlockPos p_55551_, Direction p_55552_) {
        if (!this.f_55508_ || p_55552_ == Direction.DOWN) {
            return 0;
        }
        int $$4 = p_55549_.m_61143_(f_55500_);
        if ($$4 == 0) {
            return 0;
        }
        if (p_55552_ == Direction.UP || ((RedstoneSide)this.m_55514_(p_55550_, p_55549_, p_55551_).m_61143_(f_55501_.get(p_55552_.m_122424_()))).m_61761_()) {
            return $$4;
        }
        return 0;
    }

    protected static boolean m_55640_(BlockState p_55641_) {
        return RedStoneWireBlock.m_55594_(p_55641_, null);
    }

    protected static boolean m_55594_(BlockState p_55595_, @Nullable Direction p_55596_) {
        if (p_55595_.m_60713_(Blocks.f_50088_)) {
            return true;
        }
        if (p_55595_.m_60713_(Blocks.f_50146_)) {
            Direction $$2 = p_55595_.m_61143_(RepeaterBlock.f_54117_);
            return $$2 == p_55596_ || $$2.m_122424_() == p_55596_;
        }
        if (p_55595_.m_60713_(Blocks.f_50455_)) {
            return p_55596_ == p_55595_.m_61143_(ObserverBlock.f_52588_);
        }
        return p_55595_.m_60803_() && p_55596_ != null;
    }

    @Override
    public boolean m_7899_(BlockState p_55636_) {
        return this.f_55508_;
    }

    public static int m_55606_(int p_55607_) {
        Vec3 $$1 = f_55506_[p_55607_];
        return Mth.m_14159_((float)$$1.m_7096_(), (float)$$1.m_7098_(), (float)$$1.m_7094_());
    }

    private void m_221922_(Level p_221923_, RandomSource p_221924_, BlockPos p_221925_, Vec3 p_221926_, Direction p_221927_, Direction p_221928_, float p_221929_, float p_221930_) {
        float $$8 = p_221930_ - p_221929_;
        if (p_221924_.m_188501_() >= 0.2f * $$8) {
            return;
        }
        float $$9 = 0.4375f;
        float $$10 = p_221929_ + $$8 * p_221924_.m_188501_();
        double $$11 = 0.5 + (double)(0.4375f * (float)p_221927_.m_122429_()) + (double)($$10 * (float)p_221928_.m_122429_());
        double $$12 = 0.5 + (double)(0.4375f * (float)p_221927_.m_122430_()) + (double)($$10 * (float)p_221928_.m_122430_());
        double $$13 = 0.5 + (double)(0.4375f * (float)p_221927_.m_122431_()) + (double)($$10 * (float)p_221928_.m_122431_());
        p_221923_.m_7106_(new DustParticleOptions(new Vector3f(p_221926_), 1.0f), (double)p_221925_.m_123341_() + $$11, (double)p_221925_.m_123342_() + $$12, (double)p_221925_.m_123343_() + $$13, 0.0, 0.0, 0.0);
    }

    @Override
    public void m_214162_(BlockState p_221932_, Level p_221933_, BlockPos p_221934_, RandomSource p_221935_) {
        int $$4 = p_221932_.m_61143_(f_55500_);
        if ($$4 == 0) {
            return;
        }
        block4: for (Direction $$5 : Direction.Plane.HORIZONTAL) {
            RedstoneSide $$6 = (RedstoneSide)p_221932_.m_61143_(f_55501_.get($$5));
            switch ($$6) {
                case UP: {
                    this.m_221922_(p_221933_, p_221935_, p_221934_, f_55506_[$$4], $$5, Direction.UP, -0.5f, 0.5f);
                }
                case SIDE: {
                    this.m_221922_(p_221933_, p_221935_, p_221934_, f_55506_[$$4], Direction.DOWN, $$5, 0.0f, 0.5f);
                    continue block4;
                }
            }
            this.m_221922_(p_221933_, p_221935_, p_221934_, f_55506_[$$4], Direction.DOWN, $$5, 0.0f, 0.3f);
        }
    }

    @Override
    public BlockState m_6843_(BlockState p_55592_, Rotation p_55593_) {
        switch (p_55593_) {
            case CLOCKWISE_180: {
                return (BlockState)((BlockState)((BlockState)((BlockState)p_55592_.m_61124_(f_55496_, p_55592_.m_61143_(f_55498_))).m_61124_(f_55497_, p_55592_.m_61143_(f_55499_))).m_61124_(f_55498_, p_55592_.m_61143_(f_55496_))).m_61124_(f_55499_, p_55592_.m_61143_(f_55497_));
            }
            case COUNTERCLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)p_55592_.m_61124_(f_55496_, p_55592_.m_61143_(f_55497_))).m_61124_(f_55497_, p_55592_.m_61143_(f_55498_))).m_61124_(f_55498_, p_55592_.m_61143_(f_55499_))).m_61124_(f_55499_, p_55592_.m_61143_(f_55496_));
            }
            case CLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)p_55592_.m_61124_(f_55496_, p_55592_.m_61143_(f_55499_))).m_61124_(f_55497_, p_55592_.m_61143_(f_55496_))).m_61124_(f_55498_, p_55592_.m_61143_(f_55497_))).m_61124_(f_55499_, p_55592_.m_61143_(f_55498_));
            }
        }
        return p_55592_;
    }

    @Override
    public BlockState m_6943_(BlockState p_55589_, Mirror p_55590_) {
        switch (p_55590_) {
            case LEFT_RIGHT: {
                return (BlockState)((BlockState)p_55589_.m_61124_(f_55496_, p_55589_.m_61143_(f_55498_))).m_61124_(f_55498_, p_55589_.m_61143_(f_55496_));
            }
            case FRONT_BACK: {
                return (BlockState)((BlockState)p_55589_.m_61124_(f_55497_, p_55589_.m_61143_(f_55499_))).m_61124_(f_55499_, p_55589_.m_61143_(f_55497_));
            }
        }
        return super.m_6943_(p_55589_, p_55590_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_55605_) {
        p_55605_.m_61104_(f_55496_, f_55497_, f_55498_, f_55499_, f_55500_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_55554_, Level p_55555_, BlockPos p_55556_, Player p_55557_, InteractionHand p_55558_, BlockHitResult p_55559_) {
        if (!p_55557_.m_150110_().f_35938_) {
            return InteractionResult.PASS;
        }
        if (RedStoneWireBlock.m_55644_(p_55554_) || RedStoneWireBlock.m_55646_(p_55554_)) {
            BlockState $$6 = RedStoneWireBlock.m_55644_(p_55554_) ? this.m_49966_() : this.f_55507_;
            $$6 = (BlockState)$$6.m_61124_(f_55500_, p_55554_.m_61143_(f_55500_));
            if (($$6 = this.m_55514_(p_55555_, $$6, p_55556_)) != p_55554_) {
                p_55555_.m_7731_(p_55556_, $$6, 3);
                this.m_55534_(p_55555_, p_55556_, p_55554_, $$6);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    private void m_55534_(Level p_55535_, BlockPos p_55536_, BlockState p_55537_, BlockState p_55538_) {
        for (Direction $$4 : Direction.Plane.HORIZONTAL) {
            BlockPos $$5 = p_55536_.m_121945_($$4);
            if (((RedstoneSide)p_55537_.m_61143_(f_55501_.get($$4))).m_61761_() == ((RedstoneSide)p_55538_.m_61143_(f_55501_.get($$4))).m_61761_() || !p_55535_.m_8055_($$5).m_60796_(p_55535_, $$5)) continue;
            p_55535_.m_46590_($$5, p_55538_.m_60734_(), $$4.m_122424_());
        }
    }
}

