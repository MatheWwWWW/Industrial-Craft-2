/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;

public class ClimbOnTopOfPowderSnowGoal
extends Goal {
    private final Mob f_204052_;
    private final Level f_204053_;

    public ClimbOnTopOfPowderSnowGoal(Mob p_204055_, Level p_204056_) {
        this.f_204052_ = p_204055_;
        this.f_204053_ = p_204056_;
        this.m_7021_(EnumSet.of(Goal.Flag.JUMP));
    }

    @Override
    public boolean m_8036_() {
        boolean $$0;
        boolean bl = $$0 = this.f_204052_.f_146809_ || this.f_204052_.f_146808_;
        if (!$$0 || !this.f_204052_.m_6095_().m_204039_(EntityTypeTags.f_144291_)) {
            return false;
        }
        BlockPos $$1 = this.f_204052_.m_20183_().m_7494_();
        BlockState $$2 = this.f_204053_.m_8055_($$1);
        return $$2.m_60713_(Blocks.f_152499_) || $$2.m_60812_(this.f_204053_, $$1) == Shapes.m_83040_();
    }

    @Override
    public boolean m_183429_() {
        return true;
    }

    @Override
    public void m_8037_() {
        this.f_204052_.m_21569_().m_24901_();
    }
}

