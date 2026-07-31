/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior.warden;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.warden.Warden;

public class TryToSniff
extends Behavior<Warden> {
    private static final IntProvider f_217735_ = UniformInt.m_146622_(100, 200);

    public TryToSniff() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_217772_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_148194_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_217783_, (Object)((Object)MemoryStatus.VALUE_ABSENT)));
    }

    @Override
    protected void m_6735_(ServerLevel p_217743_, Warden p_217744_, long p_217745_) {
        Brain<Warden> $$3 = p_217744_.m_6274_();
        $$3.m_21879_(MemoryModuleType.f_217785_, Unit.INSTANCE);
        $$3.m_21882_(MemoryModuleType.f_217772_, Unit.INSTANCE, f_217735_.m_214085_(p_217743_.m_213780_()));
        $$3.m_21936_(MemoryModuleType.f_26370_);
        p_217744_.m_20124_(Pose.SNIFFING);
    }
}

