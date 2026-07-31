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
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;

public class LocateHidingPlace
extends Behavior<LivingEntity> {
    private final float f_23403_;
    private final int f_23404_;
    private final int f_23405_;
    private Optional<BlockPos> f_23406_ = Optional.empty();

    public LocateHidingPlace(int p_23408_, float p_23409_, int p_23410_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26359_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26324_, (Object)((Object)MemoryStatus.REGISTERED)));
        this.f_23404_ = p_23408_;
        this.f_23403_ = p_23409_;
        this.f_23405_ = p_23410_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23412_, LivingEntity p_23413_) {
        Optional<BlockPos> $$2 = p_23412_.m_8904_().m_27186_(p_217258_ -> p_217258_.m_203565_(PoiTypes.f_218060_), p_23425_ -> true, p_23413_.m_20183_(), this.f_23405_ + 1, PoiManager.Occupancy.ANY);
        this.f_23406_ = $$2.isPresent() && $$2.get().m_203195_(p_23413_.m_20182_(), this.f_23405_) ? $$2 : Optional.empty();
        return true;
    }

    @Override
    protected void m_6735_(ServerLevel p_23415_, LivingEntity p_23416_, long p_23417_) {
        Optional<GlobalPos> $$5;
        Brain<?> $$3 = p_23416_.m_6274_();
        Optional<BlockPos> $$4 = this.f_23406_;
        if ($$4.isEmpty() && ($$4 = p_23415_.m_8904_().m_217951_(p_217256_ -> p_217256_.m_203565_(PoiTypes.f_218060_), p_23421_ -> true, PoiManager.Occupancy.ANY, p_23416_.m_20183_(), this.f_23404_, p_23416_.m_217043_())).isEmpty() && ($$5 = $$3.m_21952_(MemoryModuleType.f_26359_)).isPresent()) {
            $$4 = Optional.of($$5.get().m_122646_());
        }
        if ($$4.isPresent()) {
            $$3.m_21936_(MemoryModuleType.f_26377_);
            $$3.m_21936_(MemoryModuleType.f_26371_);
            $$3.m_21936_(MemoryModuleType.f_26375_);
            $$3.m_21936_(MemoryModuleType.f_26374_);
            $$3.m_21879_(MemoryModuleType.f_26324_, GlobalPos.m_122643_(p_23415_.m_46472_(), $$4.get()));
            if (!$$4.get().m_203195_(p_23416_.m_20182_(), this.f_23405_)) {
                $$3.m_21879_(MemoryModuleType.f_26370_, new WalkTarget($$4.get(), this.f_23403_, this.f_23405_));
            }
        }
    }
}

