/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class GoToTargetLocation<E extends Mob>
extends Behavior<E> {
    private final MemoryModuleType<BlockPos> f_217231_;
    private final int f_217232_;
    private final float f_217233_;

    public GoToTargetLocation(MemoryModuleType<BlockPos> p_217235_, int p_217236_, float p_217237_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(p_217235_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED)));
        this.f_217231_ = p_217235_;
        this.f_217232_ = p_217236_;
        this.f_217233_ = p_217237_;
    }

    @Override
    protected void m_6735_(ServerLevel p_217243_, Mob p_217244_, long p_217245_) {
        BlockPos $$3 = this.m_217248_(p_217244_);
        boolean $$4 = $$3.m_123314_(p_217244_.m_20183_(), this.f_217232_);
        if (!$$4) {
            BehaviorUtils.m_22617_(p_217244_, GoToTargetLocation.m_217250_(p_217244_, $$3), this.f_217233_, this.f_217232_);
        }
    }

    private static BlockPos m_217250_(Mob p_217251_, BlockPos p_217252_) {
        RandomSource $$2 = p_217251_.f_19853_.f_46441_;
        return p_217252_.m_7918_(GoToTargetLocation.m_217246_($$2), 0, GoToTargetLocation.m_217246_($$2));
    }

    private static int m_217246_(RandomSource p_217247_) {
        return p_217247_.m_188503_(3) - 1;
    }

    private BlockPos m_217248_(Mob p_217249_) {
        return p_217249_.m_6274_().m_21952_(this.f_217231_).get();
    }
}

