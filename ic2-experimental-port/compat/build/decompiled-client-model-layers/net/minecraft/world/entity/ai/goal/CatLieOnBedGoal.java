/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.LevelReader;

public class CatLieOnBedGoal
extends MoveToBlockGoal {
    private final Cat f_25133_;

    public CatLieOnBedGoal(Cat p_25135_, double p_25136_, int p_25137_) {
        super(p_25135_, p_25136_, p_25137_, 6);
        this.f_25133_ = p_25135_;
        this.f_25603_ = -2;
        this.m_7021_(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean m_8036_() {
        return this.f_25133_.m_21824_() && !this.f_25133_.m_21827_() && !this.f_25133_.m_28164_() && super.m_8036_();
    }

    @Override
    public void m_8056_() {
        super.m_8056_();
        this.f_25133_.m_21837_(false);
    }

    @Override
    protected int m_6099_(PathfinderMob p_25140_) {
        return 40;
    }

    @Override
    public void m_8041_() {
        super.m_8041_();
        this.f_25133_.m_28181_(false);
    }

    @Override
    public void m_8037_() {
        super.m_8037_();
        this.f_25133_.m_21837_(false);
        if (!this.m_25625_()) {
            this.f_25133_.m_28181_(false);
        } else if (!this.f_25133_.m_28164_()) {
            this.f_25133_.m_28181_(true);
        }
    }

    @Override
    protected boolean m_6465_(LevelReader p_25142_, BlockPos p_25143_) {
        return p_25142_.m_46859_(p_25143_.m_7494_()) && p_25142_.m_8055_(p_25143_).m_204336_(BlockTags.f_13038_);
    }
}

