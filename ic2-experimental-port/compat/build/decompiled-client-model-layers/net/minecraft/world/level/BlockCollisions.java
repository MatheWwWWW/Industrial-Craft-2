/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.AbstractIterator
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level;

import com.google.common.collect.AbstractIterator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockCollisions
extends AbstractIterator<VoxelShape> {
    private final AABB f_186392_;
    private final CollisionContext f_186393_;
    private final Cursor3D f_186394_;
    private final BlockPos.MutableBlockPos f_186395_;
    private final VoxelShape f_186396_;
    private final CollisionGetter f_186397_;
    private final boolean f_186398_;
    @Nullable
    private BlockGetter f_186399_;
    private long f_186400_;

    public BlockCollisions(CollisionGetter p_186402_, @Nullable Entity p_186403_, AABB p_186404_) {
        this(p_186402_, p_186403_, p_186404_, false);
    }

    public BlockCollisions(CollisionGetter p_186406_, @Nullable Entity p_186407_, AABB p_186408_, boolean p_186409_) {
        this.f_186393_ = p_186407_ == null ? CollisionContext.m_82749_() : CollisionContext.m_82750_(p_186407_);
        this.f_186395_ = new BlockPos.MutableBlockPos();
        this.f_186396_ = Shapes.m_83064_(p_186408_);
        this.f_186397_ = p_186406_;
        this.f_186392_ = p_186408_;
        this.f_186398_ = p_186409_;
        int $$4 = Mth.m_14107_(p_186408_.f_82288_ - 1.0E-7) - 1;
        int $$5 = Mth.m_14107_(p_186408_.f_82291_ + 1.0E-7) + 1;
        int $$6 = Mth.m_14107_(p_186408_.f_82289_ - 1.0E-7) - 1;
        int $$7 = Mth.m_14107_(p_186408_.f_82292_ + 1.0E-7) + 1;
        int $$8 = Mth.m_14107_(p_186408_.f_82290_ - 1.0E-7) - 1;
        int $$9 = Mth.m_14107_(p_186408_.f_82293_ + 1.0E-7) + 1;
        this.f_186394_ = new Cursor3D($$4, $$6, $$8, $$5, $$7, $$9);
    }

    @Nullable
    private BlockGetter m_186411_(int p_186412_, int p_186413_) {
        BlockGetter $$5;
        int $$2 = SectionPos.m_123171_(p_186412_);
        int $$3 = SectionPos.m_123171_(p_186413_);
        long $$4 = ChunkPos.m_45589_($$2, $$3);
        if (this.f_186399_ != null && this.f_186400_ == $$4) {
            return this.f_186399_;
        }
        this.f_186399_ = $$5 = this.f_186397_.m_7925_($$2, $$3);
        this.f_186400_ = $$4;
        return $$5;
    }

    protected VoxelShape computeNext() {
        while (this.f_186394_.m_122304_()) {
            BlockGetter $$4;
            int $$0 = this.f_186394_.m_122305_();
            int $$1 = this.f_186394_.m_122306_();
            int $$2 = this.f_186394_.m_122307_();
            int $$3 = this.f_186394_.m_122308_();
            if ($$3 == 3 || ($$4 = this.m_186411_($$0, $$2)) == null) continue;
            this.f_186395_.m_122178_($$0, $$1, $$2);
            BlockState $$5 = $$4.m_8055_(this.f_186395_);
            if (this.f_186398_ && !$$5.m_60828_($$4, this.f_186395_) || $$3 == 1 && !$$5.m_60779_() || $$3 == 2 && !$$5.m_60713_(Blocks.f_50110_)) continue;
            VoxelShape $$6 = $$5.m_60742_(this.f_186397_, this.f_186395_, this.f_186393_);
            if ($$6 == Shapes.m_83144_()) {
                if (!this.f_186392_.m_82314_($$0, $$1, $$2, (double)$$0 + 1.0, (double)$$1 + 1.0, (double)$$2 + 1.0)) continue;
                return $$6.m_83216_($$0, $$1, $$2);
            }
            VoxelShape $$7 = $$6.m_83216_($$0, $$1, $$2);
            if (!Shapes.m_83157_($$7, this.f_186396_, BooleanOp.f_82689_)) continue;
            return $$7;
        }
        return (VoxelShape)this.endOfData();
    }

    protected /* synthetic */ Object computeNext() {
        return this.computeNext();
    }
}

