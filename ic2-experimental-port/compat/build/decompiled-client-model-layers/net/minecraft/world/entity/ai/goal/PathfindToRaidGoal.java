/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 */
package net.minecraft.world.entity.ai.goal;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.raid.Raids;
import net.minecraft.world.phys.Vec3;

public class PathfindToRaidGoal<T extends Raider>
extends Goal {
    private static final int f_199887_ = 20;
    private static final float f_148132_ = 1.0f;
    private final T f_25704_;
    private int f_199888_;

    public PathfindToRaidGoal(T p_25706_) {
        this.f_25704_ = p_25706_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean m_8036_() {
        return ((Mob)this.f_25704_).m_5448_() == null && !((Entity)this.f_25704_).m_20160_() && ((Raider)this.f_25704_).m_37886_() && !((Raider)this.f_25704_).m_37885_().m_37706_() && !((ServerLevel)((Raider)this.f_25704_).f_19853_).m_8802_(((Entity)this.f_25704_).m_20183_());
    }

    @Override
    public boolean m_8045_() {
        return ((Raider)this.f_25704_).m_37886_() && !((Raider)this.f_25704_).m_37885_().m_37706_() && ((Raider)this.f_25704_).f_19853_ instanceof ServerLevel && !((ServerLevel)((Raider)this.f_25704_).f_19853_).m_8802_(((Entity)this.f_25704_).m_20183_());
    }

    @Override
    public void m_8037_() {
        if (((Raider)this.f_25704_).m_37886_()) {
            Vec3 $$1;
            Raid $$0 = ((Raider)this.f_25704_).m_37885_();
            if (((Raider)this.f_25704_).f_19797_ > this.f_199888_) {
                this.f_199888_ = ((Raider)this.f_25704_).f_19797_ + 20;
                this.m_25708_($$0);
            }
            if (!((PathfinderMob)this.f_25704_).m_21691_() && ($$1 = DefaultRandomPos.m_148412_(this.f_25704_, 15, 4, Vec3.m_82539_($$0.m_37780_()), 1.5707963705062866)) != null) {
                ((Mob)this.f_25704_).m_21573_().m_26519_($$1.f_82479_, $$1.f_82480_, $$1.f_82481_, 1.0);
            }
        }
    }

    private void m_25708_(Raid p_25709_) {
        if (p_25709_.m_37782_()) {
            HashSet $$1 = Sets.newHashSet();
            List<Raider> $$2 = ((Raider)this.f_25704_).f_19853_.m_6443_(Raider.class, ((Entity)this.f_25704_).m_20191_().m_82400_(16.0), p_25712_ -> !p_25712_.m_37886_() && Raids.m_37965_(p_25712_, p_25709_));
            $$1.addAll($$2);
            for (Raider $$3 : $$1) {
                p_25709_.m_37713_(p_25709_.m_37771_(), $$3, null, true);
            }
        }
    }
}

