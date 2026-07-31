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
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.axolotl.Axolotl;

public class ValidatePlayDead
extends Behavior<Axolotl> {
    public ValidatePlayDead() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_148195_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
    }

    @Override
    protected void m_6735_(ServerLevel p_149339_, Axolotl p_149340_, long p_149341_) {
        Brain<Axolotl> $$3 = p_149340_.m_6274_();
        int $$4 = $$3.m_21952_(MemoryModuleType.f_148195_).get();
        if ($$4 <= 0) {
            $$3.m_21936_(MemoryModuleType.f_148195_);
            $$3.m_21936_(MemoryModuleType.f_26382_);
            $$3.m_21962_();
        } else {
            $$3.m_21879_(MemoryModuleType.f_148195_, $$4 - 1);
        }
    }
}

