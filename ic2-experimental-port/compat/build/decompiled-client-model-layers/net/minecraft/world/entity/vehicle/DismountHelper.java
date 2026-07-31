/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.vehicle;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DismountHelper {
    public static int[][] m_38467_(Direction p_38468_) {
        Direction $$1 = p_38468_.m_122427_();
        Direction $$2 = $$1.m_122424_();
        Direction $$3 = p_38468_.m_122424_();
        return new int[][]{{$$1.m_122429_(), $$1.m_122431_()}, {$$2.m_122429_(), $$2.m_122431_()}, {$$3.m_122429_() + $$1.m_122429_(), $$3.m_122431_() + $$1.m_122431_()}, {$$3.m_122429_() + $$2.m_122429_(), $$3.m_122431_() + $$2.m_122431_()}, {p_38468_.m_122429_() + $$1.m_122429_(), p_38468_.m_122431_() + $$1.m_122431_()}, {p_38468_.m_122429_() + $$2.m_122429_(), p_38468_.m_122431_() + $$2.m_122431_()}, {$$3.m_122429_(), $$3.m_122431_()}, {p_38468_.m_122429_(), p_38468_.m_122431_()}};
    }

    public static boolean m_38439_(double p_38440_) {
        return !Double.isInfinite(p_38440_) && p_38440_ < 1.0;
    }

    public static boolean m_38456_(CollisionGetter p_38457_, LivingEntity p_38458_, AABB p_38459_) {
        Iterable<VoxelShape> $$3 = p_38457_.m_186434_(p_38458_, p_38459_);
        for (VoxelShape $$4 : $$3) {
            if ($$4.m_83281_()) continue;
            return false;
        }
        return p_38457_.m_6857_().m_61935_(p_38459_);
    }

    public static boolean m_150279_(CollisionGetter p_150280_, Vec3 p_150281_, LivingEntity p_150282_, Pose p_150283_) {
        return DismountHelper.m_38456_(p_150280_, p_150282_, p_150282_.m_21270_(p_150283_).m_82383_(p_150281_));
    }

    public static VoxelShape m_38446_(BlockGetter p_38447_, BlockPos p_38448_) {
        BlockState $$2 = p_38447_.m_8055_(p_38448_);
        if ($$2.m_204336_(BlockTags.f_13082_) || $$2.m_60734_() instanceof TrapDoorBlock && $$2.m_61143_(TrapDoorBlock.f_57514_).booleanValue()) {
            return Shapes.m_83040_();
        }
        return $$2.m_60812_(p_38447_, p_38448_);
    }

    public static double m_38463_(BlockPos p_38464_, int p_38465_, Function<BlockPos, VoxelShape> p_38466_) {
        BlockPos.MutableBlockPos $$3 = p_38464_.m_122032_();
        for (int $$4 = 0; $$4 < p_38465_; ++$$4) {
            VoxelShape $$5 = p_38466_.apply($$3);
            if (!$$5.m_83281_()) {
                return (double)(p_38464_.m_123342_() + $$4) + $$5.m_83288_(Direction.Axis.Y);
            }
            $$3.m_122173_(Direction.UP);
        }
        return Double.POSITIVE_INFINITY;
    }

    @Nullable
    public static Vec3 m_38441_(EntityType<?> p_38442_, CollisionGetter p_38443_, BlockPos p_38444_, boolean p_38445_) {
        if (p_38445_ && p_38442_.m_20630_(p_38443_.m_8055_(p_38444_))) {
            return null;
        }
        double $$4 = p_38443_.m_45564_(DismountHelper.m_38446_(p_38443_, p_38444_), () -> DismountHelper.m_38446_(p_38443_, p_38444_.m_7495_()));
        if (!DismountHelper.m_38439_($$4)) {
            return null;
        }
        if (p_38445_ && $$4 <= 0.0 && p_38442_.m_20630_(p_38443_.m_8055_(p_38444_.m_7495_()))) {
            return null;
        }
        Vec3 $$5 = Vec3.m_82514_(p_38444_, $$4);
        AABB $$6 = p_38442_.m_20680_().m_20393_($$5);
        Iterable<VoxelShape> $$7 = p_38443_.m_186434_(null, $$6);
        for (VoxelShape $$8 : $$7) {
            if ($$8.m_83281_()) continue;
            return null;
        }
        if (!p_38443_.m_6857_().m_61935_($$6)) {
            return null;
        }
        return $$5;
    }
}

