/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.level;

import net.minecraft.core.SectionPos;
import net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint;

public abstract class SectionTracker
extends DynamicGraphMinFixedPoint {
    protected SectionTracker(int p_8274_, int p_8275_, int p_8276_) {
        super(p_8274_, p_8275_, p_8276_);
    }

    @Override
    protected boolean m_6163_(long p_8278_) {
        return p_8278_ == Long.MAX_VALUE;
    }

    @Override
    protected void m_7900_(long p_8280_, int p_8281_, boolean p_8282_) {
        for (int $$3 = -1; $$3 <= 1; ++$$3) {
            for (int $$4 = -1; $$4 <= 1; ++$$4) {
                for (int $$5 = -1; $$5 <= 1; ++$$5) {
                    long $$6 = SectionPos.m_123186_(p_8280_, $$3, $$4, $$5);
                    if ($$6 == p_8280_) continue;
                    this.m_75593_(p_8280_, $$6, p_8281_, p_8282_);
                }
            }
        }
    }

    @Override
    protected int m_6357_(long p_8284_, long p_8285_, int p_8286_) {
        int $$3 = p_8286_;
        for (int $$4 = -1; $$4 <= 1; ++$$4) {
            for (int $$5 = -1; $$5 <= 1; ++$$5) {
                for (int $$6 = -1; $$6 <= 1; ++$$6) {
                    long $$7 = SectionPos.m_123186_(p_8284_, $$4, $$5, $$6);
                    if ($$7 == p_8284_) {
                        $$7 = Long.MAX_VALUE;
                    }
                    if ($$7 == p_8285_) continue;
                    int $$8 = this.m_6359_($$7, p_8284_, this.m_6172_($$7));
                    if ($$3 > $$8) {
                        $$3 = $$8;
                    }
                    if ($$3 != 0) continue;
                    return $$3;
                }
            }
        }
        return $$3;
    }

    @Override
    protected int m_6359_(long p_8293_, long p_8294_, int p_8295_) {
        if (p_8293_ == Long.MAX_VALUE) {
            return this.m_7409_(p_8294_);
        }
        return p_8295_ + 1;
    }

    protected abstract int m_7409_(long var1);

    public void m_8288_(long p_8289_, int p_8290_, boolean p_8291_) {
        this.m_75576_(Long.MAX_VALUE, p_8289_, p_8290_, p_8291_);
    }
}

