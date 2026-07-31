/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.phys.Vec3;

public class RemoveBlockGoal
extends MoveToBlockGoal {
    private final Block f_25836_;
    private final Mob f_25837_;
    private int f_25838_;
    private static final int f_148135_ = 20;

    public RemoveBlockGoal(Block p_25840_, PathfinderMob p_25841_, double p_25842_, int p_25843_) {
        super(p_25841_, p_25842_, 24, p_25843_);
        this.f_25836_ = p_25840_;
        this.f_25837_ = p_25841_;
    }

    @Override
    public boolean m_8036_() {
        if (!this.f_25837_.f_19853_.m_46469_().m_46207_(GameRules.f_46132_)) {
            return false;
        }
        if (this.f_25600_ > 0) {
            --this.f_25600_;
            return false;
        }
        if (this.m_25858_()) {
            this.f_25600_ = RemoveBlockGoal.m_186073_(20);
            return true;
        }
        this.f_25600_ = this.m_6099_(this.f_25598_);
        return false;
    }

    private boolean m_25858_() {
        if (this.f_25602_ != null && this.m_6465_(this.f_25598_.f_19853_, this.f_25602_)) {
            return true;
        }
        return this.m_25626_();
    }

    @Override
    public void m_8041_() {
        super.m_8041_();
        this.f_25837_.f_19789_ = 1.0f;
    }

    @Override
    public void m_8056_() {
        super.m_8056_();
        this.f_25838_ = 0;
    }

    public void m_7659_(LevelAccessor p_25847_, BlockPos p_25848_) {
    }

    public void m_5777_(Level p_25845_, BlockPos p_25846_) {
    }

    @Override
    public void m_8037_() {
        super.m_8037_();
        Level $$0 = this.f_25837_.f_19853_;
        BlockPos $$1 = this.f_25837_.m_20183_();
        BlockPos $$2 = this.m_25852_($$1, $$0);
        RandomSource $$3 = this.f_25837_.m_217043_();
        if (this.m_25625_() && $$2 != null) {
            if (this.f_25838_ > 0) {
                Vec3 $$4 = this.f_25837_.m_20184_();
                this.f_25837_.m_20334_($$4.f_82479_, 0.3, $$4.f_82481_);
                if (!$$0.f_46443_) {
                    double $$5 = 0.08;
                    ((ServerLevel)$$0).m_8767_(new ItemParticleOption(ParticleTypes.f_123752_, new ItemStack(Items.f_42521_)), (double)$$2.m_123341_() + 0.5, (double)$$2.m_123342_() + 0.7, (double)$$2.m_123343_() + 0.5, 3, ((double)$$3.m_188501_() - 0.5) * 0.08, ((double)$$3.m_188501_() - 0.5) * 0.08, ((double)$$3.m_188501_() - 0.5) * 0.08, 0.15f);
                }
            }
            if (this.f_25838_ % 2 == 0) {
                Vec3 $$6 = this.f_25837_.m_20184_();
                this.f_25837_.m_20334_($$6.f_82479_, -0.3, $$6.f_82481_);
                if (this.f_25838_ % 6 == 0) {
                    this.m_7659_($$0, this.f_25602_);
                }
            }
            if (this.f_25838_ > 60) {
                $$0.m_7471_($$2, false);
                if (!$$0.f_46443_) {
                    for (int $$7 = 0; $$7 < 20; ++$$7) {
                        double $$8 = $$3.m_188583_() * 0.02;
                        double $$9 = $$3.m_188583_() * 0.02;
                        double $$10 = $$3.m_188583_() * 0.02;
                        ((ServerLevel)$$0).m_8767_(ParticleTypes.f_123759_, (double)$$2.m_123341_() + 0.5, $$2.m_123342_(), (double)$$2.m_123343_() + 0.5, 1, $$8, $$9, $$10, 0.15f);
                    }
                    this.m_5777_($$0, $$2);
                }
            }
            ++this.f_25838_;
        }
    }

    @Nullable
    private BlockPos m_25852_(BlockPos p_25853_, BlockGetter p_25854_) {
        BlockPos[] $$2;
        if (p_25854_.m_8055_(p_25853_).m_60713_(this.f_25836_)) {
            return p_25853_;
        }
        for (BlockPos $$3 : $$2 = new BlockPos[]{p_25853_.m_7495_(), p_25853_.m_122024_(), p_25853_.m_122029_(), p_25853_.m_122012_(), p_25853_.m_122019_(), p_25853_.m_7495_().m_7495_()}) {
            if (!p_25854_.m_8055_($$3).m_60713_(this.f_25836_)) continue;
            return $$3;
        }
        return null;
    }

    @Override
    protected boolean m_6465_(LevelReader p_25850_, BlockPos p_25851_) {
        ChunkAccess $$2 = p_25850_.m_6522_(SectionPos.m_123171_(p_25851_.m_123341_()), SectionPos.m_123171_(p_25851_.m_123343_()), ChunkStatus.f_62326_, false);
        if ($$2 != null) {
            return $$2.m_8055_(p_25851_).m_60713_(this.f_25836_) && $$2.m_8055_(p_25851_.m_7494_()).m_60795_() && $$2.m_8055_(p_25851_.m_6630_(2)).m_60795_();
        }
        return false;
    }
}

