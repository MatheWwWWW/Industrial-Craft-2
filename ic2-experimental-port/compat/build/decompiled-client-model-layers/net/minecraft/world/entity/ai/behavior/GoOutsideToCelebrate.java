/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.behavior;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.MoveToSkySeeingSpot;
import net.minecraft.world.entity.raid.Raid;

public class GoOutsideToCelebrate
extends MoveToSkySeeingSpot {
    public GoOutsideToCelebrate(float p_23050_) {
        super(p_23050_);
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23052_, LivingEntity p_23053_) {
        Raid $$2 = p_23052_.m_8832_(p_23053_.m_20183_());
        return $$2 != null && $$2.m_37767_() && super.m_6114_(p_23052_, p_23053_);
    }
}

