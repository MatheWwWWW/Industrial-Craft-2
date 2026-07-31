/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class DefendVillageTargetGoal
extends TargetGoal {
    private final IronGolem f_26025_;
    @Nullable
    private LivingEntity f_26026_;
    private final TargetingConditions f_26027_ = TargetingConditions.m_148352_().m_26883_(64.0);

    public DefendVillageTargetGoal(IronGolem p_26029_) {
        super(p_26029_, false, true);
        this.f_26025_ = p_26029_;
        this.m_7021_(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean m_8036_() {
        AABB $$0 = this.f_26025_.m_20191_().m_82377_(10.0, 8.0, 10.0);
        List<Villager> $$1 = this.f_26025_.f_19853_.m_45971_(Villager.class, this.f_26027_, this.f_26025_, $$0);
        List<Player> $$2 = this.f_26025_.f_19853_.m_45955_(this.f_26027_, this.f_26025_, $$0);
        for (LivingEntity livingEntity : $$1) {
            Villager $$4 = (Villager)livingEntity;
            for (Player $$5 : $$2) {
                int $$6 = $$4.m_35532_($$5);
                if ($$6 > -100) continue;
                this.f_26026_ = $$5;
            }
        }
        if (this.f_26026_ == null) {
            return false;
        }
        return !(this.f_26026_ instanceof Player) || !this.f_26026_.m_5833_() && !((Player)this.f_26026_).m_7500_();
    }

    @Override
    public void m_8056_() {
        this.f_26025_.m_6710_(this.f_26026_);
        super.m_8056_();
    }
}

