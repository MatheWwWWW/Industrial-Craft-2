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
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class JumpOnBed
extends Behavior<Mob> {
    private static final int f_147588_ = 100;
    private static final int f_147589_ = 3;
    private static final int f_147590_ = 6;
    private static final int f_147591_ = 5;
    private final float f_23329_;
    @Nullable
    private BlockPos f_23330_;
    private int f_23331_;
    private int f_23332_;
    private int f_23333_;

    public JumpOnBed(float p_23335_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26380_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT)));
        this.f_23329_ = p_23335_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23346_, Mob p_23347_) {
        return p_23347_.m_6162_() && this.m_23368_(p_23346_, p_23347_);
    }

    @Override
    protected void m_6735_(ServerLevel p_23349_, Mob p_23350_, long p_23351_) {
        super.m_6735_(p_23349_, p_23350_, p_23351_);
        this.m_23359_(p_23350_).ifPresent(p_23355_ -> {
            this.f_23330_ = p_23355_;
            this.f_23331_ = 100;
            this.f_23332_ = 3 + p_23353_.f_46441_.m_188503_(4);
            this.f_23333_ = 0;
            this.m_23361_(p_23350_, (BlockPos)p_23355_);
        });
    }

    @Override
    protected void m_6732_(ServerLevel p_23372_, Mob p_23373_, long p_23374_) {
        super.m_6732_(p_23372_, p_23373_, p_23374_);
        this.f_23330_ = null;
        this.f_23331_ = 0;
        this.f_23332_ = 0;
        this.f_23333_ = 0;
    }

    @Override
    protected boolean m_6737_(ServerLevel p_23383_, Mob p_23384_, long p_23385_) {
        return p_23384_.m_6162_() && this.f_23330_ != null && this.m_23356_(p_23383_, this.f_23330_) && !this.m_23397_(p_23383_, p_23384_) && !this.m_23400_(p_23383_, p_23384_);
    }

    @Override
    protected boolean m_7773_(long p_23337_) {
        return false;
    }

    @Override
    protected void m_6725_(ServerLevel p_23394_, Mob p_23395_, long p_23396_) {
        if (!this.m_23379_(p_23394_, p_23395_)) {
            --this.f_23331_;
            return;
        }
        if (this.f_23333_ > 0) {
            --this.f_23333_;
            return;
        }
        if (this.m_23390_(p_23394_, p_23395_)) {
            p_23395_.m_21569_().m_24901_();
            --this.f_23332_;
            this.f_23333_ = 5;
        }
    }

    private void m_23361_(Mob p_23362_, BlockPos p_23363_) {
        p_23362_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget(p_23363_, this.f_23329_, 0));
    }

    private boolean m_23368_(ServerLevel p_23369_, Mob p_23370_) {
        return this.m_23379_(p_23369_, p_23370_) || this.m_23359_(p_23370_).isPresent();
    }

    private boolean m_23379_(ServerLevel p_23380_, Mob p_23381_) {
        BlockPos $$2 = p_23381_.m_20183_();
        BlockPos $$3 = $$2.m_7495_();
        return this.m_23356_(p_23380_, $$2) || this.m_23356_(p_23380_, $$3);
    }

    private boolean m_23390_(ServerLevel p_23391_, Mob p_23392_) {
        return this.m_23356_(p_23391_, p_23392_.m_20183_());
    }

    private boolean m_23356_(ServerLevel p_23357_, BlockPos p_23358_) {
        return p_23357_.m_8055_(p_23358_).m_204336_(BlockTags.f_13038_);
    }

    private Optional<BlockPos> m_23359_(Mob p_23360_) {
        return p_23360_.m_6274_().m_21952_(MemoryModuleType.f_26380_);
    }

    private boolean m_23397_(ServerLevel p_23398_, Mob p_23399_) {
        return !this.m_23379_(p_23398_, p_23399_) && this.f_23331_ <= 0;
    }

    private boolean m_23400_(ServerLevel p_23401_, Mob p_23402_) {
        return this.m_23379_(p_23401_, p_23402_) && this.f_23332_ <= 0;
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Mob)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6732_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6732_(serverLevel, (Mob)livingEntity, l);
    }
}

