/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;

public class RailState {
    private final Level f_55414_;
    private final BlockPos f_55415_;
    private final BaseRailBlock f_55416_;
    private BlockState f_55417_;
    private final boolean f_55418_;
    private final List<BlockPos> f_55419_ = Lists.newArrayList();

    public RailState(Level p_55421_, BlockPos p_55422_, BlockState p_55423_) {
        this.f_55414_ = p_55421_;
        this.f_55415_ = p_55422_;
        this.f_55417_ = p_55423_;
        this.f_55416_ = (BaseRailBlock)p_55423_.m_60734_();
        RailShape $$3 = p_55423_.m_61143_(this.f_55416_.m_7978_());
        this.f_55418_ = this.f_55416_.m_49413_();
        this.m_55427_($$3);
    }

    public List<BlockPos> m_55424_() {
        return this.f_55419_;
    }

    private void m_55427_(RailShape p_55428_) {
        this.f_55419_.clear();
        switch (p_55428_) {
            case NORTH_SOUTH: {
                this.f_55419_.add(this.f_55415_.m_122012_());
                this.f_55419_.add(this.f_55415_.m_122019_());
                break;
            }
            case EAST_WEST: {
                this.f_55419_.add(this.f_55415_.m_122024_());
                this.f_55419_.add(this.f_55415_.m_122029_());
                break;
            }
            case ASCENDING_EAST: {
                this.f_55419_.add(this.f_55415_.m_122024_());
                this.f_55419_.add(this.f_55415_.m_122029_().m_7494_());
                break;
            }
            case ASCENDING_WEST: {
                this.f_55419_.add(this.f_55415_.m_122024_().m_7494_());
                this.f_55419_.add(this.f_55415_.m_122029_());
                break;
            }
            case ASCENDING_NORTH: {
                this.f_55419_.add(this.f_55415_.m_122012_().m_7494_());
                this.f_55419_.add(this.f_55415_.m_122019_());
                break;
            }
            case ASCENDING_SOUTH: {
                this.f_55419_.add(this.f_55415_.m_122012_());
                this.f_55419_.add(this.f_55415_.m_122019_().m_7494_());
                break;
            }
            case SOUTH_EAST: {
                this.f_55419_.add(this.f_55415_.m_122029_());
                this.f_55419_.add(this.f_55415_.m_122019_());
                break;
            }
            case SOUTH_WEST: {
                this.f_55419_.add(this.f_55415_.m_122024_());
                this.f_55419_.add(this.f_55415_.m_122019_());
                break;
            }
            case NORTH_WEST: {
                this.f_55419_.add(this.f_55415_.m_122024_());
                this.f_55419_.add(this.f_55415_.m_122012_());
                break;
            }
            case NORTH_EAST: {
                this.f_55419_.add(this.f_55415_.m_122029_());
                this.f_55419_.add(this.f_55415_.m_122012_());
            }
        }
    }

    private void m_55445_() {
        for (int $$0 = 0; $$0 < this.f_55419_.size(); ++$$0) {
            RailState $$1 = this.m_55438_(this.f_55419_.get($$0));
            if ($$1 == null || !$$1.m_55425_(this)) {
                this.f_55419_.remove($$0--);
                continue;
            }
            this.f_55419_.set($$0, $$1.f_55415_);
        }
    }

    private boolean m_55429_(BlockPos p_55430_) {
        return BaseRailBlock.m_49364_(this.f_55414_, p_55430_) || BaseRailBlock.m_49364_(this.f_55414_, p_55430_.m_7494_()) || BaseRailBlock.m_49364_(this.f_55414_, p_55430_.m_7495_());
    }

    @Nullable
    private RailState m_55438_(BlockPos p_55439_) {
        BlockPos $$1 = p_55439_;
        BlockState $$2 = this.f_55414_.m_8055_($$1);
        if (BaseRailBlock.m_49416_($$2)) {
            return new RailState(this.f_55414_, $$1, $$2);
        }
        $$1 = p_55439_.m_7494_();
        $$2 = this.f_55414_.m_8055_($$1);
        if (BaseRailBlock.m_49416_($$2)) {
            return new RailState(this.f_55414_, $$1, $$2);
        }
        $$1 = p_55439_.m_7495_();
        $$2 = this.f_55414_.m_8055_($$1);
        if (BaseRailBlock.m_49416_($$2)) {
            return new RailState(this.f_55414_, $$1, $$2);
        }
        return null;
    }

    private boolean m_55425_(RailState p_55426_) {
        return this.m_55443_(p_55426_.f_55415_);
    }

    private boolean m_55443_(BlockPos p_55444_) {
        for (int $$1 = 0; $$1 < this.f_55419_.size(); ++$$1) {
            BlockPos $$2 = this.f_55419_.get($$1);
            if ($$2.m_123341_() != p_55444_.m_123341_() || $$2.m_123343_() != p_55444_.m_123343_()) continue;
            return true;
        }
        return false;
    }

    protected int m_55435_() {
        int $$0 = 0;
        for (Direction $$1 : Direction.Plane.HORIZONTAL) {
            if (!this.m_55429_(this.f_55415_.m_121945_($$1))) continue;
            ++$$0;
        }
        return $$0;
    }

    private boolean m_55436_(RailState p_55437_) {
        return this.m_55425_(p_55437_) || this.f_55419_.size() != 2;
    }

    private void m_55441_(RailState p_55442_) {
        this.f_55419_.add(p_55442_.f_55415_);
        BlockPos $$1 = this.f_55415_.m_122012_();
        BlockPos $$2 = this.f_55415_.m_122019_();
        BlockPos $$3 = this.f_55415_.m_122024_();
        BlockPos $$4 = this.f_55415_.m_122029_();
        boolean $$5 = this.m_55443_($$1);
        boolean $$6 = this.m_55443_($$2);
        boolean $$7 = this.m_55443_($$3);
        boolean $$8 = this.m_55443_($$4);
        RailShape $$9 = null;
        if ($$5 || $$6) {
            $$9 = RailShape.NORTH_SOUTH;
        }
        if ($$7 || $$8) {
            $$9 = RailShape.EAST_WEST;
        }
        if (!this.f_55418_) {
            if ($$6 && $$8 && !$$5 && !$$7) {
                $$9 = RailShape.SOUTH_EAST;
            }
            if ($$6 && $$7 && !$$5 && !$$8) {
                $$9 = RailShape.SOUTH_WEST;
            }
            if ($$5 && $$7 && !$$6 && !$$8) {
                $$9 = RailShape.NORTH_WEST;
            }
            if ($$5 && $$8 && !$$6 && !$$7) {
                $$9 = RailShape.NORTH_EAST;
            }
        }
        if ($$9 == RailShape.NORTH_SOUTH) {
            if (BaseRailBlock.m_49364_(this.f_55414_, $$1.m_7494_())) {
                $$9 = RailShape.ASCENDING_NORTH;
            }
            if (BaseRailBlock.m_49364_(this.f_55414_, $$2.m_7494_())) {
                $$9 = RailShape.ASCENDING_SOUTH;
            }
        }
        if ($$9 == RailShape.EAST_WEST) {
            if (BaseRailBlock.m_49364_(this.f_55414_, $$4.m_7494_())) {
                $$9 = RailShape.ASCENDING_EAST;
            }
            if (BaseRailBlock.m_49364_(this.f_55414_, $$3.m_7494_())) {
                $$9 = RailShape.ASCENDING_WEST;
            }
        }
        if ($$9 == null) {
            $$9 = RailShape.NORTH_SOUTH;
        }
        this.f_55417_ = (BlockState)this.f_55417_.m_61124_(this.f_55416_.m_7978_(), $$9);
        this.f_55414_.m_7731_(this.f_55415_, this.f_55417_, 3);
    }

    private boolean m_55446_(BlockPos p_55447_) {
        RailState $$1 = this.m_55438_(p_55447_);
        if ($$1 == null) {
            return false;
        }
        $$1.m_55445_();
        return $$1.m_55436_(this);
    }

    public RailState m_55431_(boolean p_55432_, boolean p_55433_, RailShape p_55434_) {
        boolean $$17;
        boolean $$13;
        BlockPos $$3 = this.f_55415_.m_122012_();
        BlockPos $$4 = this.f_55415_.m_122019_();
        BlockPos $$5 = this.f_55415_.m_122024_();
        BlockPos $$6 = this.f_55415_.m_122029_();
        boolean $$7 = this.m_55446_($$3);
        boolean $$8 = this.m_55446_($$4);
        boolean $$9 = this.m_55446_($$5);
        boolean $$10 = this.m_55446_($$6);
        RailShape $$11 = null;
        boolean $$12 = $$7 || $$8;
        boolean bl = $$13 = $$9 || $$10;
        if ($$12 && !$$13) {
            $$11 = RailShape.NORTH_SOUTH;
        }
        if ($$13 && !$$12) {
            $$11 = RailShape.EAST_WEST;
        }
        boolean $$14 = $$8 && $$10;
        boolean $$15 = $$8 && $$9;
        boolean $$16 = $$7 && $$10;
        boolean bl2 = $$17 = $$7 && $$9;
        if (!this.f_55418_) {
            if ($$14 && !$$7 && !$$9) {
                $$11 = RailShape.SOUTH_EAST;
            }
            if ($$15 && !$$7 && !$$10) {
                $$11 = RailShape.SOUTH_WEST;
            }
            if ($$17 && !$$8 && !$$10) {
                $$11 = RailShape.NORTH_WEST;
            }
            if ($$16 && !$$8 && !$$9) {
                $$11 = RailShape.NORTH_EAST;
            }
        }
        if ($$11 == null) {
            if ($$12 && $$13) {
                $$11 = p_55434_;
            } else if ($$12) {
                $$11 = RailShape.NORTH_SOUTH;
            } else if ($$13) {
                $$11 = RailShape.EAST_WEST;
            }
            if (!this.f_55418_) {
                if (p_55432_) {
                    if ($$14) {
                        $$11 = RailShape.SOUTH_EAST;
                    }
                    if ($$15) {
                        $$11 = RailShape.SOUTH_WEST;
                    }
                    if ($$16) {
                        $$11 = RailShape.NORTH_EAST;
                    }
                    if ($$17) {
                        $$11 = RailShape.NORTH_WEST;
                    }
                } else {
                    if ($$17) {
                        $$11 = RailShape.NORTH_WEST;
                    }
                    if ($$16) {
                        $$11 = RailShape.NORTH_EAST;
                    }
                    if ($$15) {
                        $$11 = RailShape.SOUTH_WEST;
                    }
                    if ($$14) {
                        $$11 = RailShape.SOUTH_EAST;
                    }
                }
            }
        }
        if ($$11 == RailShape.NORTH_SOUTH) {
            if (BaseRailBlock.m_49364_(this.f_55414_, $$3.m_7494_())) {
                $$11 = RailShape.ASCENDING_NORTH;
            }
            if (BaseRailBlock.m_49364_(this.f_55414_, $$4.m_7494_())) {
                $$11 = RailShape.ASCENDING_SOUTH;
            }
        }
        if ($$11 == RailShape.EAST_WEST) {
            if (BaseRailBlock.m_49364_(this.f_55414_, $$6.m_7494_())) {
                $$11 = RailShape.ASCENDING_EAST;
            }
            if (BaseRailBlock.m_49364_(this.f_55414_, $$5.m_7494_())) {
                $$11 = RailShape.ASCENDING_WEST;
            }
        }
        if ($$11 == null) {
            $$11 = p_55434_;
        }
        this.m_55427_($$11);
        this.f_55417_ = (BlockState)this.f_55417_.m_61124_(this.f_55416_.m_7978_(), $$11);
        if (p_55433_ || this.f_55414_.m_8055_(this.f_55415_) != this.f_55417_) {
            this.f_55414_.m_7731_(this.f_55415_, this.f_55417_, 3);
            for (int $$18 = 0; $$18 < this.f_55419_.size(); ++$$18) {
                RailState $$19 = this.m_55438_(this.f_55419_.get($$18));
                if ($$19 == null) continue;
                $$19.m_55445_();
                if (!$$19.m_55436_(this)) continue;
                $$19.m_55441_(this);
            }
        }
        return this;
    }

    public BlockState m_55440_() {
        return this.f_55417_;
    }
}

