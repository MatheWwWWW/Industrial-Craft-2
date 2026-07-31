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
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class StrollAroundPoi
extends Behavior<PathfinderMob> {
    private static final int f_147993_ = 180;
    private static final int f_147994_ = 8;
    private static final int f_147995_ = 6;
    private final MemoryModuleType<GlobalPos> f_24303_;
    private long f_24304_;
    private final int f_24305_;
    private final float f_24306_;

    public StrollAroundPoi(MemoryModuleType<GlobalPos> p_24308_, float p_24309_, int p_24310_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.REGISTERED), p_24308_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        this.f_24303_ = p_24308_;
        this.f_24306_ = p_24309_;
        this.f_24305_ = p_24310_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_24319_, PathfinderMob p_24320_) {
        Optional<GlobalPos> $$2 = p_24320_.m_6274_().m_21952_(this.f_24303_);
        return $$2.isPresent() && p_24319_.m_46472_() == $$2.get().m_122640_() && $$2.get().m_122646_().m_203195_(p_24320_.m_20182_(), this.f_24305_);
    }

    @Override
    protected void m_6735_(ServerLevel p_24322_, PathfinderMob p_24323_, long p_24324_) {
        if (p_24324_ > this.f_24304_) {
            Optional<Vec3> $$3 = Optional.ofNullable(LandRandomPos.m_148488_(p_24323_, 8, 6));
            p_24323_.m_6274_().m_21886_(MemoryModuleType.f_26370_, $$3.map(p_24326_ -> new WalkTarget((Vec3)p_24326_, this.f_24306_, 1)));
            this.f_24304_ = p_24324_ + 180L;
        }
    }
}

