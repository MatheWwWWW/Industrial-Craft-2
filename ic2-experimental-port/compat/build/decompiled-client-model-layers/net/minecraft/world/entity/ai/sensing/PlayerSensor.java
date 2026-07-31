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
import java.util.stream.Collectors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.player.Player;

public class PlayerSensor
extends Sensor<LivingEntity> {
    @Override
    public Set<MemoryModuleType<?>> m_7163_() {
        return ImmutableSet.of(MemoryModuleType.f_26367_, MemoryModuleType.f_26368_, MemoryModuleType.f_148206_);
    }

    @Override
    protected void m_5578_(ServerLevel p_26740_, LivingEntity p_26741_) {
        List $$2 = p_26740_.m_6907_().stream().filter(EntitySelector.f_20408_).filter(p_26744_ -> p_26741_.m_19950_((Entity)p_26744_, 16.0)).sorted(Comparator.comparingDouble(p_26741_::m_20280_)).collect(Collectors.toList());
        Brain<?> $$3 = p_26741_.m_6274_();
        $$3.m_21879_(MemoryModuleType.f_26367_, $$2);
        List $$4 = $$2.stream().filter(p_26747_ -> PlayerSensor.m_26803_(p_26741_, p_26747_)).collect(Collectors.toList());
        $$3.m_21879_(MemoryModuleType.f_26368_, $$4.isEmpty() ? null : (Player)$$4.get(0));
        Optional<Player> $$5 = $$4.stream().filter(p_148304_ -> PlayerSensor.m_148312_(p_26741_, p_148304_)).findFirst();
        $$3.m_21886_(MemoryModuleType.f_148206_, $$5);
    }
}

