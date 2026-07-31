/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public abstract class WaterAnimal
extends PathfinderMob {
    protected WaterAnimal(EntityType<? extends WaterAnimal> p_30341_, Level p_30342_) {
        super((EntityType<? extends PathfinderMob>)p_30341_, p_30342_);
        this.m_21441_(BlockPathTypes.WATER, 0.0f);
    }

    @Override
    public boolean m_6040_() {
        return true;
    }

    @Override
    public MobType m_6336_() {
        return MobType.f_21644_;
    }

    @Override
    public boolean m_6914_(LevelReader p_30348_) {
        return p_30348_.m_45784_(this);
    }

    @Override
    public int m_8100_() {
        return 120;
    }

    @Override
    public int m_213860_() {
        return 1 + this.f_19853_.f_46441_.m_188503_(3);
    }

    protected void m_6229_(int p_30344_) {
        if (this.m_6084_() && !this.m_20072_()) {
            this.m_20301_(p_30344_ - 1);
            if (this.m_20146_() == -20) {
                this.m_20301_(0);
                this.m_6469_(DamageSource.f_19312_, 2.0f);
            }
        } else {
            this.m_20301_(300);
        }
    }

    @Override
    public void m_6075_() {
        int $$0 = this.m_20146_();
        super.m_6075_();
        this.m_6229_($$0);
    }

    @Override
    public boolean m_6063_() {
        return false;
    }

    @Override
    public boolean m_6573_(Player p_30346_) {
        return false;
    }

    public static boolean m_218282_(EntityType<? extends WaterAnimal> p_218283_, LevelAccessor p_218284_, MobSpawnType p_218285_, BlockPos p_218286_, RandomSource p_218287_) {
        int $$5 = p_218284_.m_5736_();
        int $$6 = $$5 - 13;
        return p_218286_.m_123342_() >= $$6 && p_218286_.m_123342_() <= $$5 && p_218284_.m_6425_(p_218286_.m_7495_()).m_205070_(FluidTags.f_13131_) && p_218284_.m_8055_(p_218286_.m_7494_()).m_60713_(Blocks.f_49990_);
    }
}

