/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;

public class HoglinSpecificSensor
extends Sensor<Hoglin> {
    @Override
    public Set<MemoryModuleType<?>> m_7163_() {
        return ImmutableSet.of(MemoryModuleType.f_148205_, MemoryModuleType.f_26356_, MemoryModuleType.f_26350_, MemoryModuleType.f_26348_, MemoryModuleType.f_26352_, MemoryModuleType.f_26353_, (Object[])new MemoryModuleType[0]);
    }

    @Override
    protected void m_5578_(ServerLevel p_26659_, Hoglin p_26660_) {
        Brain<Hoglin> $$2 = p_26660_.m_6274_();
        $$2.m_21886_(MemoryModuleType.f_26356_, this.m_26664_(p_26659_, p_26660_));
        Optional<Object> $$3 = Optional.empty();
        int $$4 = 0;
        ArrayList $$5 = Lists.newArrayList();
        NearestVisibleLivingEntities $$6 = $$2.m_21952_(MemoryModuleType.f_148205_).orElse(NearestVisibleLivingEntities.m_186106_());
        for (LivingEntity $$7 : $$6.m_186123_(p_186150_ -> !p_186150_.m_6162_() && (p_186150_ instanceof Piglin || p_186150_ instanceof Hoglin))) {
            if ($$7 instanceof Piglin) {
                Piglin $$8 = (Piglin)$$7;
                ++$$4;
                if ($$3.isEmpty()) {
                    $$3 = Optional.of($$8);
                }
            }
            if (!($$7 instanceof Hoglin)) continue;
            Hoglin $$9 = (Hoglin)$$7;
            $$5.add($$9);
        }
        $$2.m_21886_(MemoryModuleType.f_26350_, $$3);
        $$2.m_21879_(MemoryModuleType.f_26348_, $$5);
        $$2.m_21879_(MemoryModuleType.f_26352_, $$4);
        $$2.m_21879_(MemoryModuleType.f_26353_, $$5.size());
    }

    private Optional<BlockPos> m_26664_(ServerLevel p_26665_, Hoglin p_26666_) {
        return BlockPos.m_121930_(p_26666_.m_20183_(), 8, 4, p_186148_ -> p_26665_.m_8055_((BlockPos)p_186148_).m_204336_(BlockTags.f_13084_));
    }
}

