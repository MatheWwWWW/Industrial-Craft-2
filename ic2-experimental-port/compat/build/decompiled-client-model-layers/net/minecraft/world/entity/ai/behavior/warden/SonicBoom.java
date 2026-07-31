/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior.warden;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.phys.Vec3;

public class SonicBoom
extends Behavior<Warden> {
    private static final int f_217676_ = 15;
    private static final int f_217677_ = 20;
    private static final double f_217678_ = 0.5;
    private static final double f_217679_ = 2.5;
    public static final int f_217675_ = 40;
    private static final int f_217680_ = Mth.m_14165_(34.0);
    private static final int f_217681_ = Mth.m_14167_(60.0f);

    public SonicBoom() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_217775_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_217776_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_217777_, (Object)((Object)MemoryStatus.REGISTERED)), f_217681_);
    }

    @Override
    protected boolean m_6114_(ServerLevel p_217692_, Warden p_217693_) {
        return p_217693_.m_216992_(p_217693_.m_6274_().m_21952_(MemoryModuleType.f_26372_).get(), 15.0, 20.0);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_217695_, Warden p_217696_, long p_217697_) {
        return true;
    }

    @Override
    protected void m_6735_(ServerLevel p_217713_, Warden p_217714_, long p_217715_) {
        p_217714_.m_6274_().m_21882_(MemoryModuleType.f_26373_, true, f_217681_);
        p_217714_.m_6274_().m_21882_(MemoryModuleType.f_217777_, Unit.INSTANCE, f_217680_);
        p_217713_.m_7605_(p_217714_, (byte)62);
        p_217714_.m_5496_(SoundEvents.f_215772_, 3.0f, 1.0f);
    }

    @Override
    protected void m_6725_(ServerLevel p_217724_, Warden p_217725_, long p_217726_) {
        p_217725_.m_6274_().m_21952_(MemoryModuleType.f_26372_).ifPresent(p_217718_ -> p_217725_.m_21563_().m_24964_(p_217718_.m_20182_()));
        if (p_217725_.m_6274_().m_21874_(MemoryModuleType.f_217777_) || p_217725_.m_6274_().m_21874_(MemoryModuleType.f_217776_)) {
            return;
        }
        p_217725_.m_6274_().m_21882_(MemoryModuleType.f_217776_, Unit.INSTANCE, f_217681_ - f_217680_);
        p_217725_.m_6274_().m_21952_(MemoryModuleType.f_26372_).filter(p_217725_::m_219385_).filter(p_217707_ -> p_217725_.m_216992_((Entity)p_217707_, 15.0, 20.0)).ifPresent(p_217704_ -> {
            Vec3 $$3 = p_217725_.m_20182_().m_82520_(0.0, 1.6f, 0.0);
            Vec3 $$4 = p_217704_.m_146892_().m_82546_($$3);
            Vec3 $$5 = $$4.m_82541_();
            for (int $$6 = 1; $$6 < Mth.m_14107_($$4.m_82553_()) + 7; ++$$6) {
                Vec3 $$7 = $$3.m_82549_($$5.m_82490_($$6));
                p_217724_.m_8767_(ParticleTypes.f_235902_, $$7.f_82479_, $$7.f_82480_, $$7.f_82481_, 1, 0.0, 0.0, 0.0, 0.0);
            }
            p_217725_.m_5496_(SoundEvents.f_215771_, 3.0f, 1.0f);
            p_217704_.m_6469_(DamageSource.m_216876_(p_217725_), 10.0f);
            double $$8 = 0.5 * (1.0 - p_217704_.m_21133_(Attributes.f_22278_));
            double $$9 = 2.5 * (1.0 - p_217704_.m_21133_(Attributes.f_22278_));
            p_217704_.m_5997_($$5.m_7096_() * $$9, $$5.m_7098_() * $$8, $$5.m_7094_() * $$9);
        });
    }

    @Override
    protected void m_6732_(ServerLevel p_217732_, Warden p_217733_, long p_217734_) {
        SonicBoom.m_217698_(p_217733_, 40);
    }

    public static void m_217698_(LivingEntity p_217699_, int p_217700_) {
        p_217699_.m_6274_().m_21882_(MemoryModuleType.f_217775_, Unit.INSTANCE, p_217700_);
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Warden)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6732_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6732_(serverLevel, (Warden)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6725_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6725_(serverLevel, (Warden)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (Warden)livingEntity, l);
    }
}

