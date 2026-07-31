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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class MoveToTargetSink
extends Behavior<Mob> {
    private static final int f_147699_ = 40;
    private int f_23567_;
    @Nullable
    private Path f_23568_;
    @Nullable
    private BlockPos f_23569_;
    private float f_23570_;

    public MoveToTargetSink() {
        this(150, 250);
    }

    public MoveToTargetSink(int p_23573_, int p_23574_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26326_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26377_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_PRESENT)), p_23573_, p_23574_);
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23583_, Mob p_23584_) {
        if (this.f_23567_ > 0) {
            --this.f_23567_;
            return false;
        }
        Brain<?> $$2 = p_23584_.m_6274_();
        WalkTarget $$3 = $$2.m_21952_(MemoryModuleType.f_26370_).get();
        boolean $$4 = this.m_23589_(p_23584_, $$3);
        if (!$$4 && this.m_23592_(p_23584_, $$3, p_23583_.m_46467_())) {
            this.f_23569_ = $$3.m_26420_().m_6675_();
            return true;
        }
        $$2.m_21936_(MemoryModuleType.f_26370_);
        if ($$4) {
            $$2.m_21936_(MemoryModuleType.f_26326_);
        }
        return false;
    }

    @Override
    protected boolean m_6737_(ServerLevel p_23586_, Mob p_23587_, long p_23588_) {
        if (this.f_23568_ == null || this.f_23569_ == null) {
            return false;
        }
        Optional<WalkTarget> $$3 = p_23587_.m_6274_().m_21952_(MemoryModuleType.f_26370_);
        PathNavigation $$4 = p_23587_.m_21573_();
        return !$$4.m_26571_() && $$3.isPresent() && !this.m_23589_(p_23587_, $$3.get());
    }

    @Override
    protected void m_6732_(ServerLevel p_23601_, Mob p_23602_, long p_23603_) {
        if (p_23602_.m_6274_().m_21874_(MemoryModuleType.f_26370_) && !this.m_23589_(p_23602_, p_23602_.m_6274_().m_21952_(MemoryModuleType.f_26370_).get()) && p_23602_.m_21573_().m_26577_()) {
            this.f_23567_ = p_23601_.m_213780_().m_188503_(40);
        }
        p_23602_.m_21573_().m_26573_();
        p_23602_.m_6274_().m_21936_(MemoryModuleType.f_26370_);
        p_23602_.m_6274_().m_21936_(MemoryModuleType.f_26377_);
        this.f_23568_ = null;
    }

    @Override
    protected void m_6735_(ServerLevel p_23609_, Mob p_23610_, long p_23611_) {
        p_23610_.m_6274_().m_21879_(MemoryModuleType.f_26377_, this.f_23568_);
        p_23610_.m_21573_().m_26536_(this.f_23568_, this.f_23570_);
    }

    @Override
    protected void m_6725_(ServerLevel p_23617_, Mob p_23618_, long p_23619_) {
        Path $$3 = p_23618_.m_21573_().m_26570_();
        Brain<?> $$4 = p_23618_.m_6274_();
        if (this.f_23568_ != $$3) {
            this.f_23568_ = $$3;
            $$4.m_21879_(MemoryModuleType.f_26377_, $$3);
        }
        if ($$3 == null || this.f_23569_ == null) {
            return;
        }
        WalkTarget $$5 = $$4.m_21952_(MemoryModuleType.f_26370_).get();
        if ($$5.m_26420_().m_6675_().m_123331_(this.f_23569_) > 4.0 && this.m_23592_(p_23618_, $$5, p_23617_.m_46467_())) {
            this.f_23569_ = $$5.m_26420_().m_6675_();
            this.m_6735_(p_23617_, p_23618_, p_23619_);
        }
    }

    private boolean m_23592_(Mob p_23593_, WalkTarget p_23594_, long p_23595_) {
        BlockPos $$3 = p_23594_.m_26420_().m_6675_();
        this.f_23568_ = p_23593_.m_21573_().m_7864_($$3, 0);
        this.f_23570_ = p_23594_.m_26421_();
        Brain<Long> $$4 = p_23593_.m_6274_();
        if (this.m_23589_(p_23593_, p_23594_)) {
            $$4.m_21936_(MemoryModuleType.f_26326_);
        } else {
            boolean $$5;
            boolean bl = $$5 = this.f_23568_ != null && this.f_23568_.m_77403_();
            if ($$5) {
                $$4.m_21936_(MemoryModuleType.f_26326_);
            } else if (!$$4.m_21874_(MemoryModuleType.f_26326_)) {
                $$4.m_21879_(MemoryModuleType.f_26326_, p_23595_);
            }
            if (this.f_23568_ != null) {
                return true;
            }
            Vec3 $$6 = DefaultRandomPos.m_148412_((PathfinderMob)p_23593_, 10, 7, Vec3.m_82539_($$3), 1.5707963705062866);
            if ($$6 != null) {
                this.f_23568_ = p_23593_.m_21573_().m_26524_($$6.f_82479_, $$6.f_82480_, $$6.f_82481_, 0);
                return this.f_23568_ != null;
            }
        }
        return false;
    }

    private boolean m_23589_(Mob p_23590_, WalkTarget p_23591_) {
        return p_23591_.m_26420_().m_6675_().m_123333_(p_23590_.m_20183_()) <= p_23591_.m_26422_();
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Mob)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6732_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6732_(serverLevel, (Mob)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (Mob)livingEntity, l);
    }
}

