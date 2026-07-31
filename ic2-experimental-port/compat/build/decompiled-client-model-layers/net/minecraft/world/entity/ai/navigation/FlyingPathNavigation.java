/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class FlyingPathNavigation
extends PathNavigation {
    public FlyingPathNavigation(Mob p_26424_, Level p_26425_) {
        super(p_26424_, p_26425_);
    }

    @Override
    protected PathFinder m_5532_(int p_26428_) {
        this.f_26508_ = new FlyNodeEvaluator();
        this.f_26508_.m_77351_(true);
        return new PathFinder(this.f_26508_, p_26428_);
    }

    @Override
    protected boolean m_7632_() {
        return this.m_26576_() && this.m_26574_() || !this.f_26494_.m_20159_();
    }

    @Override
    protected Vec3 m_7475_() {
        return this.f_26494_.m_20182_();
    }

    @Override
    public Path m_6570_(Entity p_26430_, int p_26431_) {
        return this.m_7864_(p_26430_.m_20183_(), p_26431_);
    }

    @Override
    public void m_7638_() {
        ++this.f_26498_;
        if (this.f_26506_) {
            this.m_26569_();
        }
        if (this.m_26571_()) {
            return;
        }
        if (this.m_7632_()) {
            this.m_7636_();
        } else if (this.f_26496_ != null && !this.f_26496_.m_77392_()) {
            Vec3 $$0 = this.f_26496_.m_77380_(this.f_26494_);
            if (this.f_26494_.m_146903_() == Mth.m_14107_($$0.f_82479_) && this.f_26494_.m_146904_() == Mth.m_14107_($$0.f_82480_) && this.f_26494_.m_146907_() == Mth.m_14107_($$0.f_82481_)) {
                this.f_26496_.m_77374_();
            }
        }
        DebugPackets.m_133703_(this.f_26495_, this.f_26494_, this.f_26496_, this.f_26505_);
        if (this.m_26571_()) {
            return;
        }
        Vec3 $$1 = this.f_26496_.m_77380_(this.f_26494_);
        this.f_26494_.m_21566_().m_6849_($$1.f_82479_, $$1.f_82480_, $$1.f_82481_, this.f_26497_);
    }

    public void m_26440_(boolean p_26441_) {
        this.f_26508_.m_77355_(p_26441_);
    }

    public boolean m_148212_() {
        return this.f_26508_.m_77357_();
    }

    public void m_26443_(boolean p_26444_) {
        this.f_26508_.m_77351_(p_26444_);
    }

    public boolean m_148213_() {
        return this.f_26508_.m_77357_();
    }

    @Override
    public boolean m_6342_(BlockPos p_26439_) {
        return this.f_26495_.m_8055_(p_26439_).m_60634_(this.f_26495_, p_26439_, this.f_26494_);
    }
}

