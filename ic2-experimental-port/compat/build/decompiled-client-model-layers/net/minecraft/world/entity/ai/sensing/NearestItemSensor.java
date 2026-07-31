/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 */
package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.item.ItemEntity;

public class NearestItemSensor
extends Sensor<Mob> {
    private static final long f_148283_ = 32L;
    private static final long f_148284_ = 16L;
    public static final int f_148282_ = 32;

    @Override
    public Set<MemoryModuleType<?>> m_7163_() {
        return ImmutableSet.of(MemoryModuleType.f_26332_);
    }

    @Override
    protected void m_5578_(ServerLevel p_26697_, Mob p_26698_) {
        Brain<?> $$2 = p_26698_.m_6274_();
        List<ItemEntity> $$3 = p_26697_.m_6443_(ItemEntity.class, p_26698_.m_20191_().m_82377_(32.0, 16.0, 32.0), p_26703_ -> true);
        $$3.sort(Comparator.comparingDouble(p_26698_::m_20280_));
        Optional<ItemEntity> $$4 = $$3.stream().filter(p_26706_ -> p_26698_.m_7243_(p_26706_.m_32055_())).filter(p_26701_ -> p_26701_.m_19950_(p_26698_, 32.0)).filter(p_26698_::m_142582_).findFirst();
        $$2.m_21886_(MemoryModuleType.f_26332_, $$4);
    }
}

