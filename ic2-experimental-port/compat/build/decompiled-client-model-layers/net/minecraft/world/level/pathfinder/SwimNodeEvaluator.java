/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.pathfinder;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.EnumMap;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.Target;

public class SwimNodeEvaluator
extends NodeEvaluator {
    private final boolean f_77455_;
    private final Long2ObjectMap<BlockPathTypes> f_192951_ = new Long2ObjectOpenHashMap();

    public SwimNodeEvaluator(boolean p_77457_) {
        this.f_77455_ = p_77457_;
    }

    @Override
    public void m_6028_(PathNavigationRegion p_192959_, Mob p_192960_) {
        super.m_6028_(p_192959_, p_192960_);
        this.f_192951_.clear();
    }

    @Override
    public void m_6802_() {
        super.m_6802_();
        this.f_192951_.clear();
    }

    @Override
    @Nullable
    public Node m_7171_() {
        return super.m_5676_(Mth.m_14107_(this.f_77313_.m_20191_().f_82288_), Mth.m_14107_(this.f_77313_.m_20191_().f_82289_ + 0.5), Mth.m_14107_(this.f_77313_.m_20191_().f_82290_));
    }

    @Override
    @Nullable
    public Target m_7568_(double p_77459_, double p_77460_, double p_77461_) {
        return this.m_230615_(super.m_5676_(Mth.m_14107_(p_77459_), Mth.m_14107_(p_77460_), Mth.m_14107_(p_77461_)));
    }

    @Override
    public int m_6065_(Node[] p_77483_, Node p_77484_) {
        int $$2 = 0;
        EnumMap $$3 = Maps.newEnumMap(Direction.class);
        for (Direction $$4 : Direction.values()) {
            Node $$5 = this.m_5676_(p_77484_.f_77271_ + $$4.m_122429_(), p_77484_.f_77272_ + $$4.m_122430_(), p_77484_.f_77273_ + $$4.m_122431_());
            $$3.put($$4, $$5);
            if (!this.m_192961_($$5)) continue;
            p_77483_[$$2++] = $$5;
        }
        for (Direction $$6 : Direction.Plane.HORIZONTAL) {
            Direction $$7 = $$6.m_122427_();
            Node $$8 = this.m_5676_(p_77484_.f_77271_ + $$6.m_122429_() + $$7.m_122429_(), p_77484_.f_77272_, p_77484_.f_77273_ + $$6.m_122431_() + $$7.m_122431_());
            if (!this.m_192963_($$8, (Node)$$3.get($$6), (Node)$$3.get($$7))) continue;
            p_77483_[$$2++] = $$8;
        }
        return $$2;
    }

    protected boolean m_192961_(@Nullable Node p_192962_) {
        return p_192962_ != null && !p_192962_.f_77279_;
    }

    protected boolean m_192963_(@Nullable Node p_192964_, @Nullable Node p_192965_, @Nullable Node p_192966_) {
        return this.m_192961_(p_192964_) && p_192965_ != null && p_192965_.f_77281_ >= 0.0f && p_192966_ != null && p_192966_.f_77281_ >= 0.0f;
    }

    @Override
    @Nullable
    protected Node m_5676_(int p_77463_, int p_77464_, int p_77465_) {
        float $$5;
        Node $$3 = null;
        BlockPathTypes $$4 = this.m_192967_(p_77463_, p_77464_, p_77465_);
        if ((this.f_77455_ && $$4 == BlockPathTypes.BREACH || $$4 == BlockPathTypes.WATER) && ($$5 = this.f_77313_.m_21439_($$4)) >= 0.0f && ($$3 = super.m_5676_(p_77463_, p_77464_, p_77465_)) != null) {
            $$3.f_77282_ = $$4;
            $$3.f_77281_ = Math.max($$3.f_77281_, $$5);
            if (this.f_77312_.m_6425_(new BlockPos(p_77463_, p_77464_, p_77465_)).m_76178_()) {
                $$3.f_77281_ += 8.0f;
            }
        }
        return $$3;
    }

    protected BlockPathTypes m_192967_(int p_192968_, int p_192969_, int p_192970_) {
        return (BlockPathTypes)((Object)this.f_192951_.computeIfAbsent(BlockPos.m_121882_(p_192968_, p_192969_, p_192970_), p_192957_ -> this.m_8086_(this.f_77312_, p_192968_, p_192969_, p_192970_)));
    }

    @Override
    public BlockPathTypes m_8086_(BlockGetter p_77467_, int p_77468_, int p_77469_, int p_77470_) {
        return this.m_7209_(p_77467_, p_77468_, p_77469_, p_77470_, this.f_77313_, this.f_77315_, this.f_77316_, this.f_77317_, this.m_77360_(), this.m_77357_());
    }

    @Override
    public BlockPathTypes m_7209_(BlockGetter p_77472_, int p_77473_, int p_77474_, int p_77475_, Mob p_77476_, int p_77477_, int p_77478_, int p_77479_, boolean p_77480_, boolean p_77481_) {
        BlockPos.MutableBlockPos $$10 = new BlockPos.MutableBlockPos();
        for (int $$11 = p_77473_; $$11 < p_77473_ + p_77477_; ++$$11) {
            for (int $$12 = p_77474_; $$12 < p_77474_ + p_77478_; ++$$12) {
                for (int $$13 = p_77475_; $$13 < p_77475_ + p_77479_; ++$$13) {
                    FluidState $$14 = p_77472_.m_6425_($$10.m_122178_($$11, $$12, $$13));
                    BlockState $$15 = p_77472_.m_8055_($$10.m_122178_($$11, $$12, $$13));
                    if ($$14.m_76178_() && $$15.m_60647_(p_77472_, (BlockPos)$$10.m_7495_(), PathComputationType.WATER) && $$15.m_60795_()) {
                        return BlockPathTypes.BREACH;
                    }
                    if ($$14.m_205070_(FluidTags.f_13131_)) continue;
                    return BlockPathTypes.BLOCKED;
                }
            }
        }
        BlockState $$16 = p_77472_.m_8055_($$10);
        if ($$16.m_60647_(p_77472_, $$10, PathComputationType.WATER)) {
            return BlockPathTypes.WATER;
        }
        return BlockPathTypes.BLOCKED;
    }
}

