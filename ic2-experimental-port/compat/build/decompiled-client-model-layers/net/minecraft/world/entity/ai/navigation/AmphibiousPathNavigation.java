/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class AmphibiousPathNavigation
extends PathNavigation {
    public AmphibiousPathNavigation(Mob p_217788_, Level p_217789_) {
        super(p_217788_, p_217789_);
    }

    @Override
    protected PathFinder m_5532_(int p_217792_) {
        this.f_26508_ = new AmphibiousNodeEvaluator(false);
        this.f_26508_.m_77351_(true);
        return new PathFinder(this.f_26508_, p_217792_);
    }

    @Override
    protected boolean m_7632_() {
        return true;
    }

    @Override
    protected Vec3 m_7475_() {
        return new Vec3(this.f_26494_.m_20185_(), this.f_26494_.m_20227_(0.5), this.f_26494_.m_20189_());
    }

    @Override
    protected double m_183345_(Vec3 p_217794_) {
        return p_217794_.f_82480_;
    }

    @Override
    protected boolean m_183431_(Vec3 p_217796_, Vec3 p_217797_) {
        if (this.m_26574_()) {
            return AmphibiousPathNavigation.m_217803_(this.f_26494_, p_217796_, p_217797_);
        }
        return false;
    }

    @Override
    public boolean m_6342_(BlockPos p_217799_) {
        return !this.f_26495_.m_8055_(p_217799_.m_7495_()).m_60795_();
    }

    @Override
    public void m_7008_(boolean p_217801_) {
    }
}

