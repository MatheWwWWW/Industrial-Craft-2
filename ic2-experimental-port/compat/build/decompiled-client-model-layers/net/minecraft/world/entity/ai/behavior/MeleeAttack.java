/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ProjectileWeaponItem;

public class MeleeAttack
extends Behavior<Mob> {
    private final int f_23510_;

    public MeleeAttack(int p_23512_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26373_, (Object)((Object)MemoryStatus.VALUE_ABSENT)));
        this.f_23510_ = p_23512_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23521_, Mob p_23522_) {
        LivingEntity $$2 = this.m_23532_(p_23522_);
        return !this.m_23527_(p_23522_) && BehaviorUtils.m_22667_(p_23522_, $$2) && p_23522_.m_217066_($$2);
    }

    private boolean m_23527_(Mob p_23528_) {
        return p_23528_.m_21093_(p_147697_ -> {
            Item $$2 = p_147697_.m_41720_();
            return $$2 instanceof ProjectileWeaponItem && p_23528_.m_5886_((ProjectileWeaponItem)$$2);
        });
    }

    @Override
    protected void m_6735_(ServerLevel p_23524_, Mob p_23525_, long p_23526_) {
        LivingEntity $$3 = this.m_23532_(p_23525_);
        BehaviorUtils.m_22595_(p_23525_, $$3);
        p_23525_.m_6674_(InteractionHand.MAIN_HAND);
        p_23525_.m_7327_($$3);
        p_23525_.m_6274_().m_21882_(MemoryModuleType.f_26373_, true, this.f_23510_);
    }

    private LivingEntity m_23532_(Mob p_23533_) {
        return p_23533_.m_6274_().m_21952_(MemoryModuleType.f_26372_).get();
    }
}

