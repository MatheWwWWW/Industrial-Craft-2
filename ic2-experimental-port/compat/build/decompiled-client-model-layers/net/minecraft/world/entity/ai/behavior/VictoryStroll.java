/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.behavior;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.VillageBoundRandomStroll;
import net.minecraft.world.entity.raid.Raid;

public class VictoryStroll
extends VillageBoundRandomStroll {
    public VictoryStroll(float p_24535_) {
        super(p_24535_);
    }

    @Override
    protected boolean m_6114_(ServerLevel p_24540_, PathfinderMob p_24541_) {
        Raid $$2 = p_24540_.m_8832_(p_24541_.m_20183_());
        return $$2 != null && $$2.m_37767_() && super.m_6114_(p_24540_, p_24541_);
    }
}

