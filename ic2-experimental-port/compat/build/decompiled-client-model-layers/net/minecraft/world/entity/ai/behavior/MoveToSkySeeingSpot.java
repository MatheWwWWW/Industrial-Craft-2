/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class MoveToSkySeeingSpot
extends Behavior<LivingEntity> {
    private final float f_23548_;

    public MoveToSkySeeingSpot(float p_23550_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT)));
        this.f_23548_ = p_23550_;
    }

    @Override
    protected void m_6735_(ServerLevel p_23555_, LivingEntity p_23556_, long p_23557_) {
        Optional<Vec3> $$3 = Optional.ofNullable(this.m_23564_(p_23555_, p_23556_));
        if ($$3.isPresent()) {
            p_23556_.m_6274_().m_21886_(MemoryModuleType.f_26370_, $$3.map(p_23563_ -> new WalkTarget((Vec3)p_23563_, this.f_23548_, 0)));
        }
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23552_, LivingEntity p_23553_) {
        return !p_23552_.m_45527_(p_23553_.m_20183_());
    }

    @Nullable
    private Vec3 m_23564_(ServerLevel p_23565_, LivingEntity p_23566_) {
        RandomSource $$2 = p_23566_.m_217043_();
        BlockPos $$3 = p_23566_.m_20183_();
        for (int $$4 = 0; $$4 < 10; ++$$4) {
            BlockPos $$5 = $$3.m_7918_($$2.m_188503_(20) - 10, $$2.m_188503_(6) - 3, $$2.m_188503_(20) - 10);
            if (!MoveToSkySeeingSpot.m_23558_(p_23565_, p_23566_, $$5)) continue;
            return Vec3.m_82539_($$5);
        }
        return null;
    }

    public static boolean m_23558_(ServerLevel p_23559_, LivingEntity p_23560_, BlockPos p_23561_) {
        return p_23559_.m_45527_(p_23561_) && (double)p_23559_.m_5452_(Heightmap.Types.MOTION_BLOCKING, p_23561_).m_123342_() <= p_23560_.m_20186_();
    }
}

