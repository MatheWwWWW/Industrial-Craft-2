/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.sensing;

import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public abstract class Sensor<E extends LivingEntity> {
    private static final RandomSource f_26792_ = RandomSource.m_216337_();
    private static final int f_148309_ = 20;
    protected static final int f_148308_ = 16;
    private static final TargetingConditions f_26793_ = TargetingConditions.m_148353_().m_26883_(16.0);
    private static final TargetingConditions f_26794_ = TargetingConditions.m_148353_().m_26883_(16.0).m_26893_();
    private static final TargetingConditions f_148310_ = TargetingConditions.m_148352_().m_26883_(16.0);
    private static final TargetingConditions f_148311_ = TargetingConditions.m_148352_().m_26883_(16.0).m_26893_();
    private static final TargetingConditions f_182375_ = TargetingConditions.m_148352_().m_26883_(16.0).m_148355_();
    private static final TargetingConditions f_182376_ = TargetingConditions.m_148352_().m_26883_(16.0).m_148355_().m_26893_();
    private final int f_26795_;
    private long f_26796_;

    public Sensor(int p_26800_) {
        this.f_26795_ = p_26800_;
        this.f_26796_ = f_26792_.m_188503_(p_26800_);
    }

    public Sensor() {
        this(20);
    }

    public final void m_26806_(ServerLevel p_26807_, E p_26808_) {
        if (--this.f_26796_ <= 0L) {
            this.f_26796_ = this.f_26795_;
            this.m_5578_(p_26807_, p_26808_);
        }
    }

    protected abstract void m_5578_(ServerLevel var1, E var2);

    public abstract Set<MemoryModuleType<?>> m_7163_();

    public static boolean m_26803_(LivingEntity p_26804_, LivingEntity p_26805_) {
        if (p_26804_.m_6274_().m_21938_(MemoryModuleType.f_26372_, p_26805_)) {
            return f_26794_.m_26885_(p_26804_, p_26805_);
        }
        return f_26793_.m_26885_(p_26804_, p_26805_);
    }

    public static boolean m_148312_(LivingEntity p_148313_, LivingEntity p_148314_) {
        if (p_148313_.m_6274_().m_21938_(MemoryModuleType.f_26372_, p_148314_)) {
            return f_148311_.m_26885_(p_148313_, p_148314_);
        }
        return f_148310_.m_26885_(p_148313_, p_148314_);
    }

    public static boolean m_182377_(LivingEntity p_182378_, LivingEntity p_182379_) {
        if (p_182378_.m_6274_().m_21938_(MemoryModuleType.f_26372_, p_182379_)) {
            return f_182376_.m_26885_(p_182378_, p_182379_);
        }
        return f_182375_.m_26885_(p_182378_, p_182379_);
    }
}

