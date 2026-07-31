/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class GroundPathNavigation
extends PathNavigation {
    private boolean f_26446_;

    public GroundPathNavigation(Mob p_26448_, Level p_26449_) {
        super(p_26448_, p_26449_);
    }

    @Override
    protected PathFinder m_5532_(int p_26453_) {
        this.f_26508_ = new WalkNodeEvaluator();
        this.f_26508_.m_77351_(true);
        return new PathFinder(this.f_26508_, p_26453_);
    }

    @Override
    protected boolean m_7632_() {
        return this.f_26494_.m_20096_() || this.m_26574_() || this.f_26494_.m_20159_();
    }

    @Override
    protected Vec3 m_7475_() {
        return new Vec3(this.f_26494_.m_20185_(), this.m_26493_(), this.f_26494_.m_20189_());
    }

    @Override
    public Path m_7864_(BlockPos p_26475_, int p_26476_) {
        if (this.f_26495_.m_8055_(p_26475_).m_60795_()) {
            BlockPos $$2 = p_26475_.m_7495_();
            while ($$2.m_123342_() > this.f_26495_.m_141937_() && this.f_26495_.m_8055_($$2).m_60795_()) {
                $$2 = $$2.m_7495_();
            }
            if ($$2.m_123342_() > this.f_26495_.m_141937_()) {
                return super.m_7864_($$2.m_7494_(), p_26476_);
            }
            while ($$2.m_123342_() < this.f_26495_.m_151558_() && this.f_26495_.m_8055_($$2).m_60795_()) {
                $$2 = $$2.m_7494_();
            }
            p_26475_ = $$2;
        }
        if (this.f_26495_.m_8055_(p_26475_).m_60767_().m_76333_()) {
            BlockPos $$3 = p_26475_.m_7494_();
            while ($$3.m_123342_() < this.f_26495_.m_151558_() && this.f_26495_.m_8055_($$3).m_60767_().m_76333_()) {
                $$3 = $$3.m_7494_();
            }
            return super.m_7864_($$3, p_26476_);
        }
        return super.m_7864_(p_26475_, p_26476_);
    }

    @Override
    public Path m_6570_(Entity p_26465_, int p_26466_) {
        return this.m_7864_(p_26465_.m_20183_(), p_26466_);
    }

    private int m_26493_() {
        if (!this.f_26494_.m_20069_() || !this.m_26576_()) {
            return Mth.m_14107_(this.f_26494_.m_20186_() + 0.5);
        }
        int $$0 = this.f_26494_.m_146904_();
        BlockState $$1 = this.f_26495_.m_8055_(new BlockPos(this.f_26494_.m_20185_(), (double)$$0, this.f_26494_.m_20189_()));
        int $$2 = 0;
        while ($$1.m_60713_(Blocks.f_49990_)) {
            $$1 = this.f_26495_.m_8055_(new BlockPos(this.f_26494_.m_20185_(), (double)(++$$0), this.f_26494_.m_20189_()));
            if (++$$2 <= 16) continue;
            return this.f_26494_.m_146904_();
        }
        return $$0;
    }

    @Override
    protected void m_6804_() {
        super.m_6804_();
        if (this.f_26446_) {
            if (this.f_26495_.m_45527_(new BlockPos(this.f_26494_.m_20185_(), this.f_26494_.m_20186_() + 0.5, this.f_26494_.m_20189_()))) {
                return;
            }
            for (int $$0 = 0; $$0 < this.f_26496_.m_77398_(); ++$$0) {
                Node $$1 = this.f_26496_.m_77375_($$0);
                if (!this.f_26495_.m_45527_(new BlockPos($$1.f_77271_, $$1.f_77272_, $$1.f_77273_))) continue;
                this.f_26496_.m_77388_($$0);
                return;
            }
        }
    }

    protected boolean m_7367_(BlockPathTypes p_26467_) {
        if (p_26467_ == BlockPathTypes.WATER) {
            return false;
        }
        if (p_26467_ == BlockPathTypes.LAVA) {
            return false;
        }
        return p_26467_ != BlockPathTypes.OPEN;
    }

    public void m_26477_(boolean p_26478_) {
        this.f_26508_.m_77355_(p_26478_);
    }

    public boolean m_148216_() {
        return this.f_26508_.m_77357_();
    }

    public void m_148214_(boolean p_148215_) {
        this.f_26508_.m_77351_(p_148215_);
    }

    public boolean m_26492_() {
        return this.f_26508_.m_77357_();
    }

    public void m_26490_(boolean p_26491_) {
        this.f_26446_ = p_26491_;
    }
}

