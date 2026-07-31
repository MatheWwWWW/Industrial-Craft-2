/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.animal.axolotl;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.axolotl.Axolotl;

public class PlayDead
extends Behavior<Axolotl> {
    public PlayDead() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_148195_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26382_, (Object)((Object)MemoryStatus.VALUE_PRESENT)), 200);
    }

    @Override
    protected boolean m_6114_(ServerLevel p_149319_, Axolotl p_149320_) {
        return p_149320_.m_20072_();
    }

    @Override
    protected boolean m_6737_(ServerLevel p_149322_, Axolotl p_149323_, long p_149324_) {
        return p_149323_.m_20072_() && p_149323_.m_6274_().m_21874_(MemoryModuleType.f_148195_);
    }

    @Override
    protected void m_6735_(ServerLevel p_149330_, Axolotl p_149331_, long p_149332_) {
        Brain<Axolotl> $$3 = p_149331_.m_6274_();
        $$3.m_21936_(MemoryModuleType.f_26370_);
        $$3.m_21936_(MemoryModuleType.f_26371_);
        p_149331_.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 200, 0));
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Axolotl)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (Axolotl)livingEntity, l);
    }
}

