/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.level;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint;

public abstract class ChunkTracker
extends DynamicGraphMinFixedPoint {
    protected ChunkTracker(int p_140701_, int p_140702_, int p_140703_) {
        super(p_140701_, p_140702_, p_140703_);
    }

    @Override
    protected boolean m_6163_(long p_140705_) {
        return p_140705_ == ChunkPos.f_45577_;
    }

    @Override
    protected void m_7900_(long p_140707_, int p_140708_, boolean p_140709_) {
        ChunkPos $$3 = new ChunkPos(p_140707_);
        int $$4 = $$3.f_45578_;
        int $$5 = $$3.f_45579_;
        for (int $$6 = -1; $$6 <= 1; ++$$6) {
            for (int $$7 = -1; $$7 <= 1; ++$$7) {
                long $$8 = ChunkPos.m_45589_($$4 + $$6, $$5 + $$7);
                if ($$8 == p_140707_) continue;
                this.m_75593_(p_140707_, $$8, p_140708_, p_140709_);
            }
        }
    }

    @Override
    protected int m_6357_(long p_140711_, long p_140712_, int p_140713_) {
        int $$3 = p_140713_;
        ChunkPos $$4 = new ChunkPos(p_140711_);
        int $$5 = $$4.f_45578_;
        int $$6 = $$4.f_45579_;
        for (int $$7 = -1; $$7 <= 1; ++$$7) {
            for (int $$8 = -1; $$8 <= 1; ++$$8) {
                long $$9 = ChunkPos.m_45589_($$5 + $$7, $$6 + $$8);
                if ($$9 == p_140711_) {
                    $$9 = ChunkPos.f_45577_;
                }
                if ($$9 == p_140712_) continue;
                int $$10 = this.m_6359_($$9, p_140711_, this.m_6172_($$9));
                if ($$3 > $$10) {
                    $$3 = $$10;
                }
                if ($$3 != 0) continue;
                return $$3;
            }
        }
        return $$3;
    }

    @Override
    protected int m_6359_(long p_140720_, long p_140721_, int p_140722_) {
        if (p_140720_ == ChunkPos.f_45577_) {
            return this.m_7031_(p_140721_);
        }
        return p_140722_ + 1;
    }

    protected abstract int m_7031_(long var1);

    public void m_140715_(long p_140716_, int p_140717_, boolean p_140718_) {
        this.m_75576_(ChunkPos.f_45577_, p_140716_, p_140717_, p_140718_);
    }
}

