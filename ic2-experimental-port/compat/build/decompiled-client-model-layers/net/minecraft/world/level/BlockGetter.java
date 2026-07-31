/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ClipBlockStateContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface BlockGetter
extends LevelHeightAccessor {
    @Nullable
    public BlockEntity m_7702_(BlockPos var1);

    default public <T extends BlockEntity> Optional<T> m_141902_(BlockPos p_151367_, BlockEntityType<T> p_151368_) {
        BlockEntity $$2 = this.m_7702_(p_151367_);
        if ($$2 == null || $$2.m_58903_() != p_151368_) {
            return Optional.empty();
        }
        return Optional.of($$2);
    }

    public BlockState m_8055_(BlockPos var1);

    public FluidState m_6425_(BlockPos var1);

    default public int m_7146_(BlockPos p_45572_) {
        return this.m_8055_(p_45572_).m_60791_();
    }

    default public int m_7469_() {
        return 15;
    }

    default public Stream<BlockState> m_45556_(AABB p_45557_) {
        return BlockPos.m_121921_(p_45557_).map(this::m_8055_);
    }

    default public BlockHitResult m_151353_(ClipBlockStateContext p_151354_) {
        return BlockGetter.m_151361_(p_151354_.m_151405_(), p_151354_.m_151404_(), p_151354_, (p_151356_, p_151357_) -> {
            BlockState $$2 = this.m_8055_((BlockPos)p_151357_);
            Vec3 $$3 = p_151356_.m_151405_().m_82546_(p_151356_.m_151404_());
            return p_151356_.m_151406_().test($$2) ? new BlockHitResult(p_151356_.m_151404_(), Direction.m_122366_($$3.f_82479_, $$3.f_82480_, $$3.f_82481_), new BlockPos(p_151356_.m_151404_()), false) : null;
        }, p_151370_ -> {
            Vec3 $$1 = p_151370_.m_151405_().m_82546_(p_151370_.m_151404_());
            return BlockHitResult.m_82426_(p_151370_.m_151404_(), Direction.m_122366_($$1.f_82479_, $$1.f_82480_, $$1.f_82481_), new BlockPos(p_151370_.m_151404_()));
        });
    }

    default public BlockHitResult m_45547_(ClipContext p_45548_) {
        return BlockGetter.m_151361_(p_45548_.m_45702_(), p_45548_.m_45693_(), p_45548_, (p_151359_, p_151360_) -> {
            BlockState $$2 = this.m_8055_((BlockPos)p_151360_);
            FluidState $$3 = this.m_6425_((BlockPos)p_151360_);
            Vec3 $$4 = p_151359_.m_45702_();
            Vec3 $$5 = p_151359_.m_45693_();
            VoxelShape $$6 = p_151359_.m_45694_($$2, this, (BlockPos)p_151360_);
            BlockHitResult $$7 = this.m_45558_($$4, $$5, (BlockPos)p_151360_, $$6, $$2);
            VoxelShape $$8 = p_151359_.m_45698_($$3, this, (BlockPos)p_151360_);
            BlockHitResult $$9 = $$8.m_83220_($$4, $$5, (BlockPos)p_151360_);
            double $$10 = $$7 == null ? Double.MAX_VALUE : p_151359_.m_45702_().m_82557_($$7.m_82450_());
            double $$11 = $$9 == null ? Double.MAX_VALUE : p_151359_.m_45702_().m_82557_($$9.m_82450_());
            return $$10 <= $$11 ? $$7 : $$9;
        }, p_151372_ -> {
            Vec3 $$1 = p_151372_.m_45702_().m_82546_(p_151372_.m_45693_());
            return BlockHitResult.m_82426_(p_151372_.m_45693_(), Direction.m_122366_($$1.f_82479_, $$1.f_82480_, $$1.f_82481_), new BlockPos(p_151372_.m_45693_()));
        });
    }

    @Nullable
    default public BlockHitResult m_45558_(Vec3 p_45559_, Vec3 p_45560_, BlockPos p_45561_, VoxelShape p_45562_, BlockState p_45563_) {
        BlockHitResult $$6;
        BlockHitResult $$5 = p_45562_.m_83220_(p_45559_, p_45560_, p_45561_);
        if ($$5 != null && ($$6 = p_45563_.m_60820_(this, p_45561_).m_83220_(p_45559_, p_45560_, p_45561_)) != null && $$6.m_82450_().m_82546_(p_45559_).m_82556_() < $$5.m_82450_().m_82546_(p_45559_).m_82556_()) {
            return $$5.m_82432_($$6.m_82434_());
        }
        return $$5;
    }

    default public double m_45564_(VoxelShape p_45565_, Supplier<VoxelShape> p_45566_) {
        if (!p_45565_.m_83281_()) {
            return p_45565_.m_83297_(Direction.Axis.Y);
        }
        double $$2 = p_45566_.get().m_83297_(Direction.Axis.Y);
        if ($$2 >= 1.0) {
            return $$2 - 1.0;
        }
        return Double.NEGATIVE_INFINITY;
    }

    default public double m_45573_(BlockPos p_45574_) {
        return this.m_45564_(this.m_8055_(p_45574_).m_60812_(this, p_45574_), () -> {
            BlockPos $$1 = p_45574_.m_7495_();
            return this.m_8055_($$1).m_60812_(this, $$1);
        });
    }

    public static <T, C> T m_151361_(Vec3 p_151362_, Vec3 p_151363_, C p_151364_, BiFunction<C, BlockPos, T> p_151365_, Function<C, T> p_151366_) {
        int $$13;
        int $$12;
        if (p_151362_.equals(p_151363_)) {
            return p_151366_.apply(p_151364_);
        }
        double $$5 = Mth.m_14139_(-1.0E-7, p_151363_.f_82479_, p_151362_.f_82479_);
        double $$6 = Mth.m_14139_(-1.0E-7, p_151363_.f_82480_, p_151362_.f_82480_);
        double $$7 = Mth.m_14139_(-1.0E-7, p_151363_.f_82481_, p_151362_.f_82481_);
        double $$8 = Mth.m_14139_(-1.0E-7, p_151362_.f_82479_, p_151363_.f_82479_);
        double $$9 = Mth.m_14139_(-1.0E-7, p_151362_.f_82480_, p_151363_.f_82480_);
        double $$10 = Mth.m_14139_(-1.0E-7, p_151362_.f_82481_, p_151363_.f_82481_);
        int $$11 = Mth.m_14107_($$8);
        BlockPos.MutableBlockPos $$14 = new BlockPos.MutableBlockPos($$11, $$12 = Mth.m_14107_($$9), $$13 = Mth.m_14107_($$10));
        T $$15 = p_151365_.apply(p_151364_, $$14);
        if ($$15 != null) {
            return $$15;
        }
        double $$16 = $$5 - $$8;
        double $$17 = $$6 - $$9;
        double $$18 = $$7 - $$10;
        int $$19 = Mth.m_14205_($$16);
        int $$20 = Mth.m_14205_($$17);
        int $$21 = Mth.m_14205_($$18);
        double $$22 = $$19 == 0 ? Double.MAX_VALUE : (double)$$19 / $$16;
        double $$23 = $$20 == 0 ? Double.MAX_VALUE : (double)$$20 / $$17;
        double $$24 = $$21 == 0 ? Double.MAX_VALUE : (double)$$21 / $$18;
        double $$25 = $$22 * ($$19 > 0 ? 1.0 - Mth.m_14185_($$8) : Mth.m_14185_($$8));
        double $$26 = $$23 * ($$20 > 0 ? 1.0 - Mth.m_14185_($$9) : Mth.m_14185_($$9));
        double $$27 = $$24 * ($$21 > 0 ? 1.0 - Mth.m_14185_($$10) : Mth.m_14185_($$10));
        while ($$25 <= 1.0 || $$26 <= 1.0 || $$27 <= 1.0) {
            T $$28;
            if ($$25 < $$26) {
                if ($$25 < $$27) {
                    $$11 += $$19;
                    $$25 += $$22;
                } else {
                    $$13 += $$21;
                    $$27 += $$24;
                }
            } else if ($$26 < $$27) {
                $$12 += $$20;
                $$26 += $$23;
            } else {
                $$13 += $$21;
                $$27 += $$24;
            }
            if (($$28 = p_151365_.apply(p_151364_, $$14.m_122178_($$11, $$12, $$13))) == null) continue;
            return $$28;
        }
        return p_151366_.apply(p_151364_);
    }
}

