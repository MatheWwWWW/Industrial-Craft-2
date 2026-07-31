/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.npc.Villager;

public class StrollToPoiList
extends Behavior<Villager> {
    private final MemoryModuleType<List<GlobalPos>> f_24354_;
    private final MemoryModuleType<GlobalPos> f_24355_;
    private final float f_24356_;
    private final int f_24357_;
    private final int f_24358_;
    private long f_24359_;
    @Nullable
    private GlobalPos f_24360_;

    public StrollToPoiList(MemoryModuleType<List<GlobalPos>> p_24362_, float p_24363_, int p_24364_, int p_24365_, MemoryModuleType<GlobalPos> p_24366_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.REGISTERED), p_24362_, (Object)((Object)MemoryStatus.VALUE_PRESENT), p_24366_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        this.f_24354_ = p_24362_;
        this.f_24356_ = p_24363_;
        this.f_24357_ = p_24364_;
        this.f_24358_ = p_24365_;
        this.f_24355_ = p_24366_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_24375_, Villager p_24376_) {
        List<GlobalPos> $$4;
        Optional<List<GlobalPos>> $$2 = p_24376_.m_6274_().m_21952_(this.f_24354_);
        Optional<GlobalPos> $$3 = p_24376_.m_6274_().m_21952_(this.f_24355_);
        if ($$2.isPresent() && $$3.isPresent() && !($$4 = $$2.get()).isEmpty()) {
            this.f_24360_ = $$4.get(p_24375_.m_213780_().m_188503_($$4.size()));
            return this.f_24360_ != null && p_24375_.m_46472_() == this.f_24360_.m_122640_() && $$3.get().m_122646_().m_203195_(p_24376_.m_20182_(), this.f_24358_);
        }
        return false;
    }

    @Override
    protected void m_6735_(ServerLevel p_24378_, Villager p_24379_, long p_24380_) {
        if (p_24380_ > this.f_24359_ && this.f_24360_ != null) {
            p_24379_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget(this.f_24360_.m_122646_(), this.f_24356_, this.f_24357_));
            this.f_24359_ = p_24380_ + 100L;
        }
    }
}

