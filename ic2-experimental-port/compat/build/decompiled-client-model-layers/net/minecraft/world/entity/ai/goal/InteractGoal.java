/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;

public class InteractGoal
extends LookAtPlayerGoal {
    public InteractGoal(Mob p_148111_, Class<? extends LivingEntity> p_148112_, float p_148113_) {
        super(p_148111_, p_148112_, p_148113_);
        this.m_7021_(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
    }

    public InteractGoal(Mob p_25415_, Class<? extends LivingEntity> p_25416_, float p_25417_, float p_25418_) {
        super(p_25415_, p_25416_, p_25417_, p_25418_);
        this.m_7021_(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
    }
}

