/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class WaterBoundPathNavigation
extends PathNavigation {
    private boolean f_26592_;

    public WaterBoundPathNavigation(Mob p_26594_, Level p_26595_) {
        super(p_26594_, p_26595_);
    }

    @Override
    protected PathFinder m_5532_(int p_26598_) {
        this.f_26592_ = this.f_26494_.m_6095_() == EntityType.f_20559_;
        this.f_26508_ = new SwimNodeEvaluator(this.f_26592_);
        return new PathFinder(this.f_26508_, p_26598_);
    }

    @Override
    protected boolean m_7632_() {
        return this.f_26592_ || this.m_26574_();
    }

    @Override
    protected Vec3 m_7475_() {
        return new Vec3(this.f_26494_.m_20185_(), this.f_26494_.m_20227_(0.5), this.f_26494_.m_20189_());
    }

    @Override
    protected double m_183345_(Vec3 p_186136_) {
        return p_186136_.f_82480_;
    }

    @Override
    protected boolean m_183431_(Vec3 p_186138_, Vec3 p_186139_) {
        return WaterBoundPathNavigation.m_217803_(this.f_26494_, p_186138_, p_186139_);
    }

    @Override
    public boolean m_6342_(BlockPos p_26608_) {
        return !this.f_26495_.m_8055_(p_26608_).m_60804_(this.f_26495_, p_26608_);
    }

    @Override
    public void m_7008_(boolean p_26612_) {
    }
}

