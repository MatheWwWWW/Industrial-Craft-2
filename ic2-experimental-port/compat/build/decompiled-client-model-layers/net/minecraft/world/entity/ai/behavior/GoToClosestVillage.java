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
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.phys.Vec3;

public class GoToClosestVillage
extends Behavior<Villager> {
    private final float f_23074_;
    private final int f_23075_;

    public GoToClosestVillage(float p_23077_, int p_23078_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT)));
        this.f_23074_ = p_23077_;
        this.f_23075_ = p_23078_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23087_, Villager p_23088_) {
        return !p_23087_.m_8802_(p_23088_.m_20183_());
    }

    @Override
    protected void m_6735_(ServerLevel p_23090_, Villager p_23091_, long p_23092_) {
        PoiManager $$3 = p_23090_.m_8904_();
        int $$4 = $$3.m_27098_(SectionPos.m_123199_(p_23091_.m_20183_()));
        Vec3 $$5 = null;
        for (int $$6 = 0; $$6 < 5; ++$$6) {
            Vec3 $$7 = LandRandomPos.m_148503_(p_23091_, 15, 7, p_147554_ -> -$$3.m_27098_(SectionPos.m_123199_(p_147554_)));
            if ($$7 == null) continue;
            int $$8 = $$3.m_27098_(SectionPos.m_123199_(new BlockPos($$7)));
            if ($$8 < $$4) {
                $$5 = $$7;
                break;
            }
            if ($$8 != $$4) continue;
            $$5 = $$7;
        }
        if ($$5 != null) {
            p_23091_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget($$5, this.f_23074_, this.f_23075_));
        }
    }
}

