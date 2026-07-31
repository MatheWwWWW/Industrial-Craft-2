/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.pathfinder;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Target;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class AmphibiousNodeEvaluator
extends WalkNodeEvaluator {
    private final boolean f_164655_;
    private float f_164656_;
    private float f_164657_;

    public AmphibiousNodeEvaluator(boolean p_164659_) {
        this.f_164655_ = p_164659_;
    }

    @Override
    public void m_6028_(PathNavigationRegion p_164671_, Mob p_164672_) {
        super.m_6028_(p_164671_, p_164672_);
        p_164672_.m_21441_(BlockPathTypes.WATER, 0.0f);
        this.f_164656_ = p_164672_.m_21439_(BlockPathTypes.WALKABLE);
        p_164672_.m_21441_(BlockPathTypes.WALKABLE, 6.0f);
        this.f_164657_ = p_164672_.m_21439_(BlockPathTypes.WATER_BORDER);
        p_164672_.m_21441_(BlockPathTypes.WATER_BORDER, 4.0f);
    }

    @Override
    public void m_6802_() {
        this.f_77313_.m_21441_(BlockPathTypes.WALKABLE, this.f_164656_);
        this.f_77313_.m_21441_(BlockPathTypes.WATER_BORDER, this.f_164657_);
        super.m_6802_();
    }

    @Override
    @Nullable
    public Node m_7171_() {
        return this.m_230631_(new BlockPos(Mth.m_14107_(this.f_77313_.m_20191_().f_82288_), Mth.m_14107_(this.f_77313_.m_20191_().f_82289_ + 0.5), Mth.m_14107_(this.f_77313_.m_20191_().f_82290_)));
    }

    @Override
    @Nullable
    public Target m_7568_(double p_164662_, double p_164663_, double p_164664_) {
        return this.m_230615_(this.m_5676_(Mth.m_14107_(p_164662_), Mth.m_14107_(p_164663_ + 0.5), Mth.m_14107_(p_164664_)));
    }

    @Override
    public int m_6065_(Node[] p_164676_, Node p_164677_) {
        int $$6;
        int $$2 = super.m_6065_(p_164676_, p_164677_);
        BlockPathTypes $$3 = this.m_77567_(this.f_77313_, p_164677_.f_77271_, p_164677_.f_77272_ + 1, p_164677_.f_77273_);
        BlockPathTypes $$4 = this.m_77567_(this.f_77313_, p_164677_.f_77271_, p_164677_.f_77272_, p_164677_.f_77273_);
        if (this.f_77313_.m_21439_($$3) >= 0.0f && $$4 != BlockPathTypes.STICKY_HONEY) {
            int $$5 = Mth.m_14143_(Math.max(1.0f, this.f_77313_.f_19793_));
        } else {
            $$6 = 0;
        }
        double $$7 = this.m_142213_(new BlockPos(p_164677_.f_77271_, p_164677_.f_77272_, p_164677_.f_77273_));
        Node $$8 = this.m_164725_(p_164677_.f_77271_, p_164677_.f_77272_ + 1, p_164677_.f_77273_, Math.max(0, $$6 - 1), $$7, Direction.UP, $$4);
        Node $$9 = this.m_164725_(p_164677_.f_77271_, p_164677_.f_77272_ - 1, p_164677_.f_77273_, $$6, $$7, Direction.DOWN, $$4);
        if (this.m_230610_($$8, p_164677_)) {
            p_164676_[$$2++] = $$8;
        }
        if (this.m_230610_($$9, p_164677_) && $$4 != BlockPathTypes.TRAPDOOR) {
            p_164676_[$$2++] = $$9;
        }
        for (int $$10 = 0; $$10 < $$2; ++$$10) {
            Node $$11 = p_164676_[$$10];
            if ($$11.f_77282_ != BlockPathTypes.WATER || !this.f_164655_ || $$11.f_77272_ >= this.f_77313_.f_19853_.m_5736_() - 10) continue;
            $$11.f_77281_ += 1.0f;
        }
        return $$2;
    }

    private boolean m_230610_(@Nullable Node p_230611_, Node p_230612_) {
        return this.m_77626_(p_230611_, p_230612_) && p_230611_.f_77282_ == BlockPathTypes.WATER;
    }

    @Override
    protected double m_142213_(BlockPos p_164674_) {
        return this.f_77313_.m_20069_() ? (double)p_164674_.m_123342_() + 0.5 : super.m_142213_(p_164674_);
    }

    @Override
    protected boolean m_141974_() {
        return true;
    }

    @Override
    public BlockPathTypes m_8086_(BlockGetter p_164666_, int p_164667_, int p_164668_, int p_164669_) {
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        BlockPathTypes $$5 = AmphibiousNodeEvaluator.m_77643_(p_164666_, $$4.m_122178_(p_164667_, p_164668_, p_164669_));
        if ($$5 == BlockPathTypes.WATER) {
            for (Direction $$6 : Direction.values()) {
                BlockPathTypes $$7 = AmphibiousNodeEvaluator.m_77643_(p_164666_, $$4.m_122178_(p_164667_, p_164668_, p_164669_).m_122173_($$6));
                if ($$7 != BlockPathTypes.BLOCKED) continue;
                return BlockPathTypes.WATER_BORDER;
            }
            return BlockPathTypes.WATER;
        }
        return AmphibiousNodeEvaluator.m_77604_(p_164666_, $$4);
    }
}

