/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public class ViewArea {
    protected final LevelRenderer f_110838_;
    protected final Level f_110839_;
    protected int f_110840_;
    protected int f_110841_;
    protected int f_110842_;
    public ChunkRenderDispatcher.RenderChunk[] f_110843_;

    public ViewArea(ChunkRenderDispatcher p_110845_, Level p_110846_, int p_110847_, LevelRenderer p_110848_) {
        this.f_110838_ = p_110848_;
        this.f_110839_ = p_110846_;
        this.m_110853_(p_110847_);
        this.m_110864_(p_110845_);
    }

    protected void m_110864_(ChunkRenderDispatcher p_110865_) {
        if (!Minecraft.m_91087_().m_18695_()) {
            throw new IllegalStateException("createChunks called from wrong thread: " + Thread.currentThread().getName());
        }
        int $$1 = this.f_110841_ * this.f_110840_ * this.f_110842_;
        this.f_110843_ = new ChunkRenderDispatcher.RenderChunk[$$1];
        for (int $$2 = 0; $$2 < this.f_110841_; ++$$2) {
            for (int $$3 = 0; $$3 < this.f_110840_; ++$$3) {
                for (int $$4 = 0; $$4 < this.f_110842_; ++$$4) {
                    int $$5 = this.m_110855_($$2, $$3, $$4);
                    ChunkRenderDispatcher chunkRenderDispatcher = p_110865_;
                    Objects.requireNonNull(chunkRenderDispatcher);
                    this.f_110843_[$$5] = new ChunkRenderDispatcher.RenderChunk(chunkRenderDispatcher, $$5, $$2 * 16, $$3 * 16, $$4 * 16);
                }
            }
        }
    }

    public void m_110849_() {
        for (ChunkRenderDispatcher.RenderChunk $$0 : this.f_110843_) {
            $$0.m_112838_();
        }
    }

    private int m_110855_(int p_110856_, int p_110857_, int p_110858_) {
        return (p_110858_ * this.f_110840_ + p_110857_) * this.f_110841_ + p_110856_;
    }

    protected void m_110853_(int p_110854_) {
        int $$1;
        this.f_110841_ = $$1 = p_110854_ * 2 + 1;
        this.f_110840_ = this.f_110839_.m_151559_();
        this.f_110842_ = $$1;
    }

    public void m_110850_(double p_110851_, double p_110852_) {
        int $$2 = Mth.m_14165_(p_110851_);
        int $$3 = Mth.m_14165_(p_110852_);
        for (int $$4 = 0; $$4 < this.f_110841_; ++$$4) {
            int $$5 = this.f_110841_ * 16;
            int $$6 = $$2 - 8 - $$5 / 2;
            int $$7 = $$6 + Math.floorMod($$4 * 16 - $$6, $$5);
            for (int $$8 = 0; $$8 < this.f_110842_; ++$$8) {
                int $$9 = this.f_110842_ * 16;
                int $$10 = $$3 - 8 - $$9 / 2;
                int $$11 = $$10 + Math.floorMod($$8 * 16 - $$10, $$9);
                for (int $$12 = 0; $$12 < this.f_110840_; ++$$12) {
                    int $$13 = this.f_110839_.m_141937_() + $$12 * 16;
                    ChunkRenderDispatcher.RenderChunk $$14 = this.f_110843_[this.m_110855_($$4, $$12, $$8)];
                    BlockPos $$15 = $$14.m_112839_();
                    if ($$7 == $$15.m_123341_() && $$13 == $$15.m_123342_() && $$11 == $$15.m_123343_()) continue;
                    $$14.m_112801_($$7, $$13, $$11);
                }
            }
        }
    }

    public void m_110859_(int p_110860_, int p_110861_, int p_110862_, boolean p_110863_) {
        int $$4 = Math.floorMod(p_110860_, this.f_110841_);
        int $$5 = Math.floorMod(p_110861_ - this.f_110839_.m_151560_(), this.f_110840_);
        int $$6 = Math.floorMod(p_110862_, this.f_110842_);
        ChunkRenderDispatcher.RenderChunk $$7 = this.f_110843_[this.m_110855_($$4, $$5, $$6)];
        $$7.m_112828_(p_110863_);
    }

    @Nullable
    protected ChunkRenderDispatcher.RenderChunk m_110866_(BlockPos p_110867_) {
        int $$1 = Mth.m_14042_(p_110867_.m_123341_(), 16);
        int $$2 = Mth.m_14042_(p_110867_.m_123342_() - this.f_110839_.m_141937_(), 16);
        int $$3 = Mth.m_14042_(p_110867_.m_123343_(), 16);
        if ($$2 < 0 || $$2 >= this.f_110840_) {
            return null;
        }
        $$1 = Mth.m_14100_($$1, this.f_110841_);
        $$3 = Mth.m_14100_($$3, this.f_110842_);
        return this.f_110843_[this.m_110855_($$1, $$2, $$3)];
    }
}

