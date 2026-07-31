/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class InteractWith<E extends LivingEntity, T extends LivingEntity>
extends Behavior<E> {
    private final int f_23238_;
    private final float f_23239_;
    private final EntityType<? extends T> f_23240_;
    private final int f_23241_;
    private final Predicate<T> f_23242_;
    private final Predicate<E> f_23243_;
    private final MemoryModuleType<T> f_23244_;

    public InteractWith(EntityType<? extends T> p_23246_, int p_23247_, Predicate<E> p_23248_, Predicate<T> p_23249_, MemoryModuleType<T> p_23250_, float p_23251_, int p_23252_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_148205_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        this.f_23240_ = p_23246_;
        this.f_23239_ = p_23251_;
        this.f_23241_ = p_23247_ * p_23247_;
        this.f_23238_ = p_23252_;
        this.f_23242_ = p_23249_;
        this.f_23243_ = p_23248_;
        this.f_23244_ = p_23250_;
    }

    public static <T extends LivingEntity> InteractWith<LivingEntity, T> m_23260_(EntityType<? extends T> p_23261_, int p_23262_, MemoryModuleType<T> p_23263_, float p_23264_, int p_23265_) {
        return new InteractWith<LivingEntity, LivingEntity>(p_23261_, p_23262_, p_23287_ -> true, p_23285_ -> true, p_23263_, p_23264_, p_23265_);
    }

    public static <T extends LivingEntity> InteractWith<LivingEntity, T> m_147566_(EntityType<? extends T> p_147567_, int p_147568_, Predicate<T> p_147569_, MemoryModuleType<T> p_147570_, float p_147571_, int p_147572_) {
        return new InteractWith<LivingEntity, T>(p_147567_, p_147568_, p_147584_ -> true, p_147569_, p_147570_, p_147571_, p_147572_);
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23254_, E p_23255_) {
        return this.f_23243_.test(p_23255_) && this.m_23266_(p_23255_);
    }

    private boolean m_23266_(E p_23267_) {
        NearestVisibleLivingEntities $$1 = ((LivingEntity)p_23267_).m_6274_().m_21952_(MemoryModuleType.f_148205_).get();
        return $$1.m_186130_(this::m_23278_);
    }

    private boolean m_23278_(LivingEntity p_23279_) {
        return this.f_23240_.equals(p_23279_.m_6095_()) && this.f_23242_.test(p_23279_);
    }

    @Override
    protected void m_6735_(ServerLevel p_23257_, E p_23258_, long p_23259_) {
        Brain<?> $$3 = ((LivingEntity)p_23258_).m_6274_();
        Optional<NearestVisibleLivingEntities> $$4 = $$3.m_21952_(MemoryModuleType.f_148205_);
        if ($$4.isEmpty()) {
            return;
        }
        NearestVisibleLivingEntities $$5 = $$4.get();
        $$5.m_186116_(p_186046_ -> this.m_186038_(p_23258_, (LivingEntity)p_186046_)).ifPresent(p_186043_ -> {
            $$3.m_21879_(this.f_23244_, p_186043_);
            $$3.m_21879_(MemoryModuleType.f_26371_, new EntityTracker((Entity)p_186043_, true));
            $$3.m_21879_(MemoryModuleType.f_26370_, new WalkTarget(new EntityTracker((Entity)p_186043_, false), this.f_23239_, this.f_23238_));
        });
    }

    private boolean m_186038_(E p_186039_, LivingEntity p_186040_) {
        return this.f_23240_.equals(p_186040_.m_6095_()) && p_186040_.m_20280_((Entity)p_186039_) <= (double)this.f_23241_ && this.f_23242_.test(p_186040_);
    }
}

