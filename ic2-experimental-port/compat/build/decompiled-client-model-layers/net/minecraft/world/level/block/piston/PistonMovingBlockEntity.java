/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.piston;

import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.piston.PistonMath;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PistonMovingBlockEntity
extends BlockEntity {
    private static final int f_155898_ = 2;
    private static final double f_155899_ = 0.01;
    public static final double f_155897_ = 0.51;
    private BlockState f_60334_ = Blocks.f_50016_.m_49966_();
    private Direction f_60335_;
    private boolean f_60336_;
    private boolean f_60337_;
    private static final ThreadLocal<Direction> f_60338_ = ThreadLocal.withInitial(() -> null);
    private float f_60339_;
    private float f_60340_;
    private long f_60341_;
    private int f_60342_;

    public PistonMovingBlockEntity(BlockPos p_155901_, BlockState p_155902_) {
        super(BlockEntityType.f_58926_, p_155901_, p_155902_);
    }

    public PistonMovingBlockEntity(BlockPos p_155904_, BlockState p_155905_, BlockState p_155906_, Direction p_155907_, boolean p_155908_, boolean p_155909_) {
        this(p_155904_, p_155905_);
        this.f_60334_ = p_155906_;
        this.f_60335_ = p_155907_;
        this.f_60336_ = p_155908_;
        this.f_60337_ = p_155909_;
    }

    @Override
    public CompoundTag m_5995_() {
        return this.m_187482_();
    }

    public boolean m_60387_() {
        return this.f_60336_;
    }

    public Direction m_60392_() {
        return this.f_60335_;
    }

    public boolean m_60397_() {
        return this.f_60337_;
    }

    public float m_60350_(float p_60351_) {
        if (p_60351_ > 1.0f) {
            p_60351_ = 1.0f;
        }
        return Mth.m_14179_(p_60351_, this.f_60340_, this.f_60339_);
    }

    public float m_60380_(float p_60381_) {
        return (float)this.f_60335_.m_122429_() * this.m_60390_(this.m_60350_(p_60381_));
    }

    public float m_60385_(float p_60386_) {
        return (float)this.f_60335_.m_122430_() * this.m_60390_(this.m_60350_(p_60386_));
    }

    public float m_60388_(float p_60389_) {
        return (float)this.f_60335_.m_122431_() * this.m_60390_(this.m_60350_(p_60389_));
    }

    private float m_60390_(float p_60391_) {
        return this.f_60336_ ? p_60391_ - 1.0f : 1.0f - p_60391_;
    }

    private BlockState m_60403_() {
        if (!this.m_60387_() && this.m_60397_() && this.f_60334_.m_60734_() instanceof PistonBaseBlock) {
            return (BlockState)((BlockState)((BlockState)Blocks.f_50040_.m_49966_().m_61124_(PistonHeadBlock.f_60236_, this.f_60339_ > 0.25f)).m_61124_(PistonHeadBlock.f_60235_, this.f_60334_.m_60713_(Blocks.f_50032_) ? PistonType.STICKY : PistonType.DEFAULT)).m_61124_(PistonHeadBlock.f_52588_, this.f_60334_.m_61143_(PistonBaseBlock.f_52588_));
        }
        return this.f_60334_;
    }

    private static void m_155910_(Level p_155911_, BlockPos p_155912_, float p_155913_, PistonMovingBlockEntity p_155914_) {
        Direction $$4 = p_155914_.m_60399_();
        double $$5 = p_155913_ - p_155914_.f_60339_;
        VoxelShape $$6 = p_155914_.m_60403_().m_60812_(p_155911_, p_155912_);
        if ($$6.m_83281_()) {
            return;
        }
        AABB $$7 = PistonMovingBlockEntity.m_155925_(p_155912_, $$6.m_83215_(), p_155914_);
        List<Entity> $$8 = p_155911_.m_45933_(null, PistonMath.m_60328_($$7, $$4, $$5).m_82367_($$7));
        if ($$8.isEmpty()) {
            return;
        }
        List<AABB> $$9 = $$6.m_83299_();
        boolean $$10 = p_155914_.f_60334_.m_60713_(Blocks.f_50374_);
        for (Entity $$11 : $$8) {
            AABB $$19;
            AABB $$17;
            AABB $$18;
            if ($$11.m_7752_() == PushReaction.IGNORE) continue;
            if ($$10) {
                if ($$11 instanceof ServerPlayer) continue;
                Vec3 $$12 = $$11.m_20184_();
                double $$13 = $$12.f_82479_;
                double $$14 = $$12.f_82480_;
                double $$15 = $$12.f_82481_;
                switch ($$4.m_122434_()) {
                    case X: {
                        $$13 = $$4.m_122429_();
                        break;
                    }
                    case Y: {
                        $$14 = $$4.m_122430_();
                        break;
                    }
                    case Z: {
                        $$15 = $$4.m_122431_();
                    }
                }
                $$11.m_20334_($$13, $$14, $$15);
            }
            double $$16 = 0.0;
            Iterator<AABB> iterator = $$9.iterator();
            while (!(!iterator.hasNext() || ($$18 = PistonMath.m_60328_(PistonMovingBlockEntity.m_155925_(p_155912_, $$17 = iterator.next(), p_155914_), $$4, $$5)).m_82381_($$19 = $$11.m_20191_()) && ($$16 = Math.max($$16, PistonMovingBlockEntity.m_60367_($$18, $$4, $$19))) >= $$5)) {
            }
            if ($$16 <= 0.0) continue;
            $$16 = Math.min($$16, $$5) + 0.01;
            PistonMovingBlockEntity.m_60371_($$4, $$11, $$16, $$4);
            if (p_155914_.f_60336_ || !p_155914_.f_60337_) continue;
            PistonMovingBlockEntity.m_155920_(p_155912_, $$11, $$4, $$5);
        }
    }

    private static void m_60371_(Direction p_60372_, Entity p_60373_, double p_60374_, Direction p_60375_) {
        f_60338_.set(p_60372_);
        p_60373_.m_6478_(MoverType.PISTON, new Vec3(p_60374_ * (double)p_60375_.m_122429_(), p_60374_ * (double)p_60375_.m_122430_(), p_60374_ * (double)p_60375_.m_122431_()));
        f_60338_.set(null);
    }

    private static void m_155931_(Level p_155932_, BlockPos p_155933_, float p_155934_, PistonMovingBlockEntity p_155935_) {
        if (!p_155935_.m_60404_()) {
            return;
        }
        Direction $$4 = p_155935_.m_60399_();
        if (!$$4.m_122434_().m_122479_()) {
            return;
        }
        double $$5 = p_155935_.f_60334_.m_60812_(p_155932_, p_155933_).m_83297_(Direction.Axis.Y);
        AABB $$6 = PistonMovingBlockEntity.m_155925_(p_155933_, new AABB(0.0, $$5, 0.0, 1.0, 1.5000000999999998, 1.0), p_155935_);
        double $$7 = p_155934_ - p_155935_.f_60339_;
        List<Entity> $$8 = p_155932_.m_6249_(null, $$6, p_60384_ -> PistonMovingBlockEntity.m_60364_($$6, p_60384_));
        for (Entity $$9 : $$8) {
            PistonMovingBlockEntity.m_60371_($$4, $$9, $$7, $$4);
        }
    }

    private static boolean m_60364_(AABB p_60365_, Entity p_60366_) {
        return p_60366_.m_7752_() == PushReaction.NORMAL && p_60366_.m_20096_() && p_60366_.m_20185_() >= p_60365_.f_82288_ && p_60366_.m_20185_() <= p_60365_.f_82291_ && p_60366_.m_20189_() >= p_60365_.f_82290_ && p_60366_.m_20189_() <= p_60365_.f_82293_;
    }

    private boolean m_60404_() {
        return this.f_60334_.m_60713_(Blocks.f_50719_);
    }

    public Direction m_60399_() {
        return this.f_60336_ ? this.f_60335_ : this.f_60335_.m_122424_();
    }

    private static double m_60367_(AABB p_60368_, Direction p_60369_, AABB p_60370_) {
        switch (p_60369_) {
            case EAST: {
                return p_60368_.f_82291_ - p_60370_.f_82288_;
            }
            case WEST: {
                return p_60370_.f_82291_ - p_60368_.f_82288_;
            }
            default: {
                return p_60368_.f_82292_ - p_60370_.f_82289_;
            }
            case DOWN: {
                return p_60370_.f_82292_ - p_60368_.f_82289_;
            }
            case SOUTH: {
                return p_60368_.f_82293_ - p_60370_.f_82290_;
            }
            case NORTH: 
        }
        return p_60370_.f_82293_ - p_60368_.f_82290_;
    }

    private static AABB m_155925_(BlockPos p_155926_, AABB p_155927_, PistonMovingBlockEntity p_155928_) {
        double $$3 = p_155928_.m_60390_(p_155928_.f_60339_);
        return p_155927_.m_82386_((double)p_155926_.m_123341_() + $$3 * (double)p_155928_.f_60335_.m_122429_(), (double)p_155926_.m_123342_() + $$3 * (double)p_155928_.f_60335_.m_122430_(), (double)p_155926_.m_123343_() + $$3 * (double)p_155928_.f_60335_.m_122431_());
    }

    private static void m_155920_(BlockPos p_155921_, Entity p_155922_, Direction p_155923_, double p_155924_) {
        double $$8;
        Direction $$6;
        double $$7;
        AABB $$5;
        AABB $$4 = p_155922_.m_20191_();
        if ($$4.m_82381_($$5 = Shapes.m_83144_().m_83215_().m_82338_(p_155921_)) && Math.abs(($$7 = PistonMovingBlockEntity.m_60367_($$5, $$6 = p_155923_.m_122424_(), $$4) + 0.01) - ($$8 = PistonMovingBlockEntity.m_60367_($$5, $$6, $$4.m_82323_($$5)) + 0.01)) < 0.01) {
            $$7 = Math.min($$7, p_155924_) + 0.01;
            PistonMovingBlockEntity.m_60371_(p_155923_, p_155922_, $$7, $$6);
        }
    }

    public BlockState m_60400_() {
        return this.f_60334_;
    }

    public void m_60401_() {
        if (this.f_58857_ != null && (this.f_60340_ < 1.0f || this.f_58857_.f_46443_)) {
            this.f_60340_ = this.f_60339_ = 1.0f;
            this.f_58857_.m_46747_(this.f_58858_);
            this.m_7651_();
            if (this.f_58857_.m_8055_(this.f_58858_).m_60713_(Blocks.f_50110_)) {
                BlockState $$1;
                if (this.f_60337_) {
                    BlockState $$0 = Blocks.f_50016_.m_49966_();
                } else {
                    $$1 = Block.m_49931_(this.f_60334_, this.f_58857_, this.f_58858_);
                }
                this.f_58857_.m_7731_(this.f_58858_, $$1, 3);
                this.f_58857_.m_46586_(this.f_58858_, $$1.m_60734_(), this.f_58858_);
            }
        }
    }

    public static void m_155915_(Level p_155916_, BlockPos p_155917_, BlockState p_155918_, PistonMovingBlockEntity p_155919_) {
        p_155919_.f_60341_ = p_155916_.m_46467_();
        p_155919_.f_60340_ = p_155919_.f_60339_;
        if (p_155919_.f_60340_ >= 1.0f) {
            if (p_155916_.f_46443_ && p_155919_.f_60342_ < 5) {
                ++p_155919_.f_60342_;
                return;
            }
            p_155916_.m_46747_(p_155917_);
            p_155919_.m_7651_();
            if (p_155916_.m_8055_(p_155917_).m_60713_(Blocks.f_50110_)) {
                BlockState $$4 = Block.m_49931_(p_155919_.f_60334_, p_155916_, p_155917_);
                if ($$4.m_60795_()) {
                    p_155916_.m_7731_(p_155917_, p_155919_.f_60334_, 84);
                    Block.m_49902_(p_155919_.f_60334_, $$4, p_155916_, p_155917_, 3);
                } else {
                    if ($$4.m_61138_(BlockStateProperties.f_61362_) && $$4.m_61143_(BlockStateProperties.f_61362_).booleanValue()) {
                        $$4 = (BlockState)$$4.m_61124_(BlockStateProperties.f_61362_, false);
                    }
                    p_155916_.m_7731_(p_155917_, $$4, 67);
                    p_155916_.m_46586_(p_155917_, $$4.m_60734_(), p_155917_);
                }
            }
            return;
        }
        float $$5 = p_155919_.f_60339_ + 0.5f;
        PistonMovingBlockEntity.m_155910_(p_155916_, p_155917_, $$5, p_155919_);
        PistonMovingBlockEntity.m_155931_(p_155916_, p_155917_, $$5, p_155919_);
        p_155919_.f_60339_ = $$5;
        if (p_155919_.f_60339_ >= 1.0f) {
            p_155919_.f_60339_ = 1.0f;
        }
    }

    @Override
    public void m_142466_(CompoundTag p_155930_) {
        super.m_142466_(p_155930_);
        this.f_60334_ = NbtUtils.m_129241_(p_155930_.m_128469_("blockState"));
        this.f_60335_ = Direction.m_122376_(p_155930_.m_128451_("facing"));
        this.f_60340_ = this.f_60339_ = p_155930_.m_128457_("progress");
        this.f_60336_ = p_155930_.m_128471_("extending");
        this.f_60337_ = p_155930_.m_128471_("source");
    }

    @Override
    protected void m_183515_(CompoundTag p_187530_) {
        super.m_183515_(p_187530_);
        p_187530_.m_128365_("blockState", NbtUtils.m_129202_(this.f_60334_));
        p_187530_.m_128405_("facing", this.f_60335_.m_122411_());
        p_187530_.m_128350_("progress", this.f_60340_);
        p_187530_.m_128379_("extending", this.f_60336_);
        p_187530_.m_128379_("source", this.f_60337_);
    }

    public VoxelShape m_60356_(BlockGetter p_60357_, BlockPos p_60358_) {
        BlockState $$6;
        VoxelShape $$3;
        if (!this.f_60336_ && this.f_60337_ && this.f_60334_.m_60734_() instanceof PistonBaseBlock) {
            VoxelShape $$2 = ((BlockState)this.f_60334_.m_61124_(PistonBaseBlock.f_60153_, true)).m_60812_(p_60357_, p_60358_);
        } else {
            $$3 = Shapes.m_83040_();
        }
        Direction $$4 = f_60338_.get();
        if ((double)this.f_60339_ < 1.0 && $$4 == this.m_60399_()) {
            return $$3;
        }
        if (this.m_60397_()) {
            BlockState $$5 = (BlockState)((BlockState)Blocks.f_50040_.m_49966_().m_61124_(PistonHeadBlock.f_52588_, this.f_60335_)).m_61124_(PistonHeadBlock.f_60236_, this.f_60336_ != 1.0f - this.f_60339_ < 0.25f);
        } else {
            $$6 = this.f_60334_;
        }
        float $$7 = this.m_60390_(this.f_60339_);
        double $$8 = (float)this.f_60335_.m_122429_() * $$7;
        double $$9 = (float)this.f_60335_.m_122430_() * $$7;
        double $$10 = (float)this.f_60335_.m_122431_() * $$7;
        return Shapes.m_83110_($$3, $$6.m_60812_(p_60357_, p_60358_).m_83216_($$8, $$9, $$10));
    }

    public long m_60402_() {
        return this.f_60341_;
    }
}

