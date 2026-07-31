/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level;

import com.google.common.collect.Iterables;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockCollisions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface CollisionGetter
extends BlockGetter {
    public WorldBorder m_6857_();

    @Nullable
    public BlockGetter m_7925_(int var1, int var2);

    default public boolean m_5450_(@Nullable Entity p_45750_, VoxelShape p_45751_) {
        return true;
    }

    default public boolean m_45752_(BlockState p_45753_, BlockPos p_45754_, CollisionContext p_45755_) {
        VoxelShape $$3 = p_45753_.m_60742_(this, p_45754_, p_45755_);
        return $$3.m_83281_() || this.m_5450_(null, $$3.m_83216_(p_45754_.m_123341_(), p_45754_.m_123342_(), p_45754_.m_123343_()));
    }

    default public boolean m_45784_(Entity p_45785_) {
        return this.m_5450_(p_45785_, Shapes.m_83064_(p_45785_.m_20191_()));
    }

    default public boolean m_45772_(AABB p_45773_) {
        return this.m_45756_(null, p_45773_);
    }

    default public boolean m_45786_(Entity p_45787_) {
        return this.m_45756_(p_45787_, p_45787_.m_20191_());
    }

    default public boolean m_45756_(@Nullable Entity p_45757_, AABB p_45758_) {
        for (VoxelShape $$2 : this.m_186434_(p_45757_, p_45758_)) {
            if ($$2.m_83281_()) continue;
            return false;
        }
        if (!this.m_183134_(p_45757_, p_45758_).isEmpty()) {
            return false;
        }
        if (p_45757_ != null) {
            VoxelShape $$3 = this.m_186440_(p_45757_, p_45758_);
            return $$3 == null || !Shapes.m_83157_($$3, Shapes.m_83064_(p_45758_), BooleanOp.f_82689_);
        }
        return true;
    }

    public List<VoxelShape> m_183134_(@Nullable Entity var1, AABB var2);

    default public Iterable<VoxelShape> m_186431_(@Nullable Entity p_186432_, AABB p_186433_) {
        List<VoxelShape> $$2 = this.m_183134_(p_186432_, p_186433_);
        Iterable $$3 = this.m_186434_(p_186432_, p_186433_);
        return $$2.isEmpty() ? $$3 : Iterables.concat($$2, $$3);
    }

    default public Iterable<VoxelShape> m_186434_(@Nullable Entity p_186435_, AABB p_186436_) {
        return () -> new BlockCollisions(this, p_186435_, p_186436_);
    }

    @Nullable
    private VoxelShape m_186440_(Entity p_186441_, AABB p_186442_) {
        WorldBorder $$2 = this.m_6857_();
        return $$2.m_187566_(p_186441_, p_186442_) ? $$2.m_61946_() : null;
    }

    default public boolean m_186437_(@Nullable Entity p_186438_, AABB p_186439_) {
        BlockCollisions $$2 = new BlockCollisions(this, p_186438_, p_186439_, true);
        while ($$2.hasNext()) {
            if (((VoxelShape)$$2.next()).m_83281_()) continue;
            return true;
        }
        return false;
    }

    default public Optional<Vec3> m_151418_(@Nullable Entity p_151419_, VoxelShape p_151420_, Vec3 p_151421_, double p_151422_, double p_151423_, double p_151424_) {
        if (p_151420_.m_83281_()) {
            return Optional.empty();
        }
        AABB $$6 = p_151420_.m_83215_().m_82377_(p_151422_, p_151423_, p_151424_);
        VoxelShape $$7 = StreamSupport.stream(this.m_186434_(p_151419_, $$6).spliterator(), false).filter(p_186430_ -> this.m_6857_() == null || this.m_6857_().m_61935_(p_186430_.m_83215_())).flatMap(p_186426_ -> p_186426_.m_83299_().stream()).map(p_186424_ -> p_186424_.m_82377_(p_151422_ / 2.0, p_151423_ / 2.0, p_151424_ / 2.0)).map(Shapes::m_83064_).reduce(Shapes.m_83040_(), Shapes::m_83110_);
        VoxelShape $$8 = Shapes.m_83113_(p_151420_, $$7, BooleanOp.f_82685_);
        return $$8.m_166067_(p_151421_);
    }
}

