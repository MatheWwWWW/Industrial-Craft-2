/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.player.Player;

public class FollowTemptation
extends Behavior<PathfinderMob> {
    public static final int f_147482_ = 100;
    public static final double f_147483_ = 2.5;
    private final Function<LivingEntity, Float> f_147484_;

    public FollowTemptation(Function<LivingEntity, Float> p_147486_) {
        super((Map)Util.m_137537_(() -> {
            ImmutableMap.Builder $$0 = ImmutableMap.builder();
            $$0.put(MemoryModuleType.f_26371_, (Object)MemoryStatus.REGISTERED);
            $$0.put(MemoryModuleType.f_26370_, (Object)MemoryStatus.REGISTERED);
            $$0.put(MemoryModuleType.f_148197_, (Object)MemoryStatus.VALUE_ABSENT);
            $$0.put(MemoryModuleType.f_148198_, (Object)MemoryStatus.REGISTERED);
            $$0.put(MemoryModuleType.f_148196_, (Object)MemoryStatus.VALUE_PRESENT);
            $$0.put(MemoryModuleType.f_26375_, (Object)MemoryStatus.VALUE_ABSENT);
            $$0.put(MemoryModuleType.f_217768_, (Object)MemoryStatus.VALUE_ABSENT);
            return $$0.build();
        }));
        this.f_147484_ = p_147486_;
    }

    protected float m_147497_(PathfinderMob p_147498_) {
        return this.f_147484_.apply(p_147498_).floatValue();
    }

    private Optional<Player> m_147508_(PathfinderMob p_147509_) {
        return p_147509_.m_6274_().m_21952_(MemoryModuleType.f_148196_);
    }

    @Override
    protected boolean m_7773_(long p_147488_) {
        return false;
    }

    @Override
    protected boolean m_6737_(ServerLevel p_147494_, PathfinderMob p_147495_, long p_147496_) {
        return this.m_147508_(p_147495_).isPresent() && !p_147495_.m_6274_().m_21874_(MemoryModuleType.f_26375_) && !p_147495_.m_6274_().m_21874_(MemoryModuleType.f_217768_);
    }

    @Override
    protected void m_6735_(ServerLevel p_147505_, PathfinderMob p_147506_, long p_147507_) {
        p_147506_.m_6274_().m_21879_(MemoryModuleType.f_148198_, true);
    }

    @Override
    protected void m_6732_(ServerLevel p_147515_, PathfinderMob p_147516_, long p_147517_) {
        Brain<?> $$3 = p_147516_.m_6274_();
        $$3.m_21879_(MemoryModuleType.f_148197_, 100);
        $$3.m_21879_(MemoryModuleType.f_148198_, false);
        $$3.m_21936_(MemoryModuleType.f_26370_);
        $$3.m_21936_(MemoryModuleType.f_26371_);
    }

    @Override
    protected void m_6725_(ServerLevel p_147523_, PathfinderMob p_147524_, long p_147525_) {
        Player $$3 = this.m_147508_(p_147524_).get();
        Brain<?> $$4 = p_147524_.m_6274_();
        $$4.m_21879_(MemoryModuleType.f_26371_, new EntityTracker($$3, true));
        if (p_147524_.m_20280_($$3) < 6.25) {
            $$4.m_21936_(MemoryModuleType.f_26370_);
        } else {
            $$4.m_21879_(MemoryModuleType.f_26370_, new WalkTarget(new EntityTracker($$3, false), this.m_147497_(p_147524_), 2));
        }
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (PathfinderMob)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (PathfinderMob)livingEntity, l);
    }
}

