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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class DoNothing
extends Behavior<LivingEntity> {
    public DoNothing(int p_22840_, int p_22841_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(), p_22840_, p_22841_);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_22843_, LivingEntity p_22844_, long p_22845_) {
        return true;
    }
}

