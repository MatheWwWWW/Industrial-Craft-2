/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;

public interface LevelHeightAccessor {
    public int m_141928_();

    public int m_141937_();

    default public int m_151558_() {
        return this.m_141937_() + this.m_141928_();
    }

    default public int m_151559_() {
        return this.m_151561_() - this.m_151560_();
    }

    default public int m_151560_() {
        return SectionPos.m_123171_(this.m_141937_());
    }

    default public int m_151561_() {
        return SectionPos.m_123171_(this.m_151558_() - 1) + 1;
    }

    default public boolean m_151570_(BlockPos p_151571_) {
        return this.m_151562_(p_151571_.m_123342_());
    }

    default public boolean m_151562_(int p_151563_) {
        return p_151563_ < this.m_141937_() || p_151563_ >= this.m_151558_();
    }

    default public int m_151564_(int p_151565_) {
        return this.m_151566_(SectionPos.m_123171_(p_151565_));
    }

    default public int m_151566_(int p_151567_) {
        return p_151567_ - this.m_151560_();
    }

    default public int m_151568_(int p_151569_) {
        return p_151569_ + this.m_151560_();
    }

    public static LevelHeightAccessor m_186487_(final int p_186488_, final int p_186489_) {
        return new LevelHeightAccessor(){

            @Override
            public int m_141928_() {
                return p_186489_;
            }

            @Override
            public int m_141937_() {
                return p_186488_;
            }
        };
    }
}

