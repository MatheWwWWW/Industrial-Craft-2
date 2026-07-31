/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.ai.goal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import org.slf4j.Logger;

public class GoalSelector {
    private static final Logger f_25342_ = LogUtils.getLogger();
    private static final WrappedGoal f_25343_ = new WrappedGoal(Integer.MAX_VALUE, new Goal(){

        @Override
        public boolean m_8036_() {
            return false;
        }
    }){

        @Override
        public boolean m_7620_() {
            return false;
        }
    };
    private final Map<Goal.Flag, WrappedGoal> f_25344_ = new EnumMap<Goal.Flag, WrappedGoal>(Goal.Flag.class);
    private final Set<WrappedGoal> f_25345_ = Sets.newLinkedHashSet();
    private final Supplier<ProfilerFiller> f_25346_;
    private final EnumSet<Goal.Flag> f_25347_ = EnumSet.noneOf(Goal.Flag.class);
    private int f_148095_;
    private int f_25348_ = 3;

    public GoalSelector(Supplier<ProfilerFiller> p_25351_) {
        this.f_25346_ = p_25351_;
    }

    public void m_25352_(int p_25353_, Goal p_25354_) {
        this.f_25345_.add(new WrappedGoal(p_25353_, p_25354_));
    }

    @VisibleForTesting
    public void m_148096_() {
        this.f_25345_.clear();
    }

    public void m_25363_(Goal p_25364_) {
        this.f_25345_.stream().filter(p_25378_ -> p_25378_.m_26015_() == p_25364_).filter(WrappedGoal::m_7620_).forEach(WrappedGoal::m_8041_);
        this.f_25345_.removeIf(p_25367_ -> p_25367_.m_26015_() == p_25364_);
    }

    private static boolean m_186075_(WrappedGoal p_186076_, EnumSet<Goal.Flag> p_186077_) {
        for (Goal.Flag $$2 : p_186076_.m_7684_()) {
            if (!p_186077_.contains((Object)$$2)) continue;
            return true;
        }
        return false;
    }

    private static boolean m_186078_(WrappedGoal p_186079_, Map<Goal.Flag, WrappedGoal> p_186080_) {
        for (Goal.Flag $$2 : p_186079_.m_7684_()) {
            if (p_186080_.getOrDefault((Object)$$2, f_25343_).m_26002_(p_186079_)) continue;
            return false;
        }
        return true;
    }

    public void m_25373_() {
        ProfilerFiller $$0 = this.f_25346_.get();
        $$0.m_6180_("goalCleanup");
        for (WrappedGoal $$1 : this.f_25345_) {
            if (!$$1.m_7620_() || !GoalSelector.m_186075_($$1, this.f_25347_) && $$1.m_8045_()) continue;
            $$1.m_8041_();
        }
        Iterator<Map.Entry<Goal.Flag, WrappedGoal>> $$2 = this.f_25344_.entrySet().iterator();
        while ($$2.hasNext()) {
            Map.Entry<Goal.Flag, WrappedGoal> $$3 = $$2.next();
            if ($$3.getValue().m_7620_()) continue;
            $$2.remove();
        }
        $$0.m_7238_();
        $$0.m_6180_("goalUpdate");
        for (WrappedGoal $$4 : this.f_25345_) {
            if ($$4.m_7620_() || GoalSelector.m_186075_($$4, this.f_25347_) || !GoalSelector.m_186078_($$4, this.f_25344_) || !$$4.m_8036_()) continue;
            for (Goal.Flag $$5 : $$4.m_7684_()) {
                WrappedGoal $$6 = this.f_25344_.getOrDefault((Object)$$5, f_25343_);
                $$6.m_8041_();
                this.f_25344_.put($$5, $$4);
            }
            $$4.m_8056_();
        }
        $$0.m_7238_();
        this.m_186081_(true);
    }

    public void m_186081_(boolean p_186082_) {
        ProfilerFiller $$1 = this.f_25346_.get();
        $$1.m_6180_("goalTick");
        for (WrappedGoal $$2 : this.f_25345_) {
            if (!$$2.m_7620_() || !p_186082_ && !$$2.m_183429_()) continue;
            $$2.m_8037_();
        }
        $$1.m_7238_();
    }

    public Set<WrappedGoal> m_148105_() {
        return this.f_25345_;
    }

    public Stream<WrappedGoal> m_25386_() {
        return this.f_25345_.stream().filter(WrappedGoal::m_7620_);
    }

    public void m_148097_(int p_148098_) {
        this.f_25348_ = p_148098_;
    }

    public void m_25355_(Goal.Flag p_25356_) {
        this.f_25347_.add(p_25356_);
    }

    public void m_25374_(Goal.Flag p_25375_) {
        this.f_25347_.remove((Object)p_25375_);
    }

    public void m_25360_(Goal.Flag p_25361_, boolean p_25362_) {
        if (p_25362_) {
            this.m_25374_(p_25361_);
        } else {
            this.m_25355_(p_25361_);
        }
    }
}

