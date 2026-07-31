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
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.InteractWithDoor;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SleepInBed
extends Behavior<LivingEntity> {
    public static final int f_147968_ = 100;
    private long f_24149_;

    public SleepInBed() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26359_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26329_, (Object)((Object)MemoryStatus.REGISTERED)));
    }

    @Override
    protected boolean m_6114_(ServerLevel p_24154_, LivingEntity p_24155_) {
        long $$5;
        if (p_24155_.m_20159_()) {
            return false;
        }
        Brain<?> $$2 = p_24155_.m_6274_();
        GlobalPos $$3 = $$2.m_21952_(MemoryModuleType.f_26359_).get();
        if (p_24154_.m_46472_() != $$3.m_122640_()) {
            return false;
        }
        Optional<Long> $$4 = $$2.m_21952_(MemoryModuleType.f_26329_);
        if ($$4.isPresent() && ($$5 = p_24154_.m_46467_() - $$4.get()) > 0L && $$5 < 100L) {
            return false;
        }
        BlockState $$6 = p_24154_.m_8055_($$3.m_122646_());
        return $$3.m_122646_().m_203195_(p_24155_.m_20182_(), 2.0) && $$6.m_204336_(BlockTags.f_13038_) && $$6.m_61143_(BedBlock.f_49441_) == false;
    }

    @Override
    protected boolean m_6737_(ServerLevel p_24161_, LivingEntity p_24162_, long p_24163_) {
        Optional<GlobalPos> $$3 = p_24162_.m_6274_().m_21952_(MemoryModuleType.f_26359_);
        if (!$$3.isPresent()) {
            return false;
        }
        BlockPos $$4 = $$3.get().m_122646_();
        return p_24162_.m_6274_().m_21954_(Activity.f_37982_) && p_24162_.m_20186_() > (double)$$4.m_123342_() + 0.4 && $$4.m_203195_(p_24162_.m_20182_(), 1.14);
    }

    @Override
    protected void m_6735_(ServerLevel p_24157_, LivingEntity p_24158_, long p_24159_) {
        if (p_24159_ > this.f_24149_) {
            InteractWithDoor.m_23298_(p_24157_, p_24158_, null, null);
            p_24158_.m_5802_(p_24158_.m_6274_().m_21952_(MemoryModuleType.f_26359_).get().m_122646_());
        }
    }

    @Override
    protected boolean m_7773_(long p_24152_) {
        return false;
    }

    @Override
    protected void m_6732_(ServerLevel p_24165_, LivingEntity p_24166_, long p_24167_) {
        if (p_24166_.m_5803_()) {
            p_24166_.m_5796_();
            this.f_24149_ = p_24167_ + 40L;
        }
    }
}

