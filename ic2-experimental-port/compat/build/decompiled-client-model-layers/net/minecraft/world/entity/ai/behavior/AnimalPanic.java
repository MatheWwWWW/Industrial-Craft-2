/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;

public class AnimalPanic
extends Behavior<PathfinderMob> {
    private static final int f_147379_ = 100;
    private static final int f_147380_ = 120;
    private static final int f_196637_ = 5;
    private static final int f_147382_ = 4;
    private final float f_147383_;

    public AnimalPanic(float p_147385_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_217768_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26381_, (Object)((Object)MemoryStatus.VALUE_PRESENT)), 100, 120);
        this.f_147383_ = p_147385_;
    }

    @Override
    protected boolean m_6737_(ServerLevel p_147391_, PathfinderMob p_147392_, long p_147393_) {
        return true;
    }

    @Override
    protected void m_6735_(ServerLevel p_147399_, PathfinderMob p_147400_, long p_147401_) {
        p_147400_.m_6274_().m_21879_(MemoryModuleType.f_217768_, true);
        p_147400_.m_6274_().m_21936_(MemoryModuleType.f_26370_);
    }

    @Override
    protected void m_6732_(ServerLevel p_217118_, PathfinderMob p_217119_, long p_217120_) {
        Brain<?> $$3 = p_217119_.m_6274_();
        $$3.m_21936_(MemoryModuleType.f_217768_);
    }

    @Override
    protected void m_6725_(ServerLevel p_147403_, PathfinderMob p_147404_, long p_147405_) {
        Vec3 $$3;
        if (p_147404_.m_21573_().m_26571_() && ($$3 = this.m_196638_(p_147404_, p_147403_)) != null) {
            p_147404_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget($$3, this.f_147383_, 0));
        }
    }

    @Nullable
    private Vec3 m_196638_(PathfinderMob p_196639_, ServerLevel p_196640_) {
        Optional<Vec3> $$2;
        if (p_196639_.m_6060_() && ($$2 = this.m_196641_(p_196640_, p_196639_).map(Vec3::m_82539_)).isPresent()) {
            return $$2.get();
        }
        return LandRandomPos.m_148488_(p_196639_, 5, 4);
    }

    private Optional<BlockPos> m_196641_(BlockGetter p_196642_, Entity p_196643_) {
        BlockPos $$2 = p_196643_.m_20183_();
        if (!p_196642_.m_8055_($$2).m_60812_(p_196642_, $$2).m_83281_()) {
            return Optional.empty();
        }
        return BlockPos.m_121930_($$2, 5, 1, p_196646_ -> p_196642_.m_6425_((BlockPos)p_196646_).m_205070_(FluidTags.f_13131_));
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (PathfinderMob)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (PathfinderMob)livingEntity, l);
    }
}

