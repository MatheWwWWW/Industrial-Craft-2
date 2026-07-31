/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.gametest.framework;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestBatch;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.gametest.framework.GameTestListener;
import net.minecraft.gametest.framework.GameTestRunner;
import net.minecraft.gametest.framework.GameTestTicker;
import net.minecraft.gametest.framework.MultipleTestTracker;
import net.minecraft.gametest.framework.StructureUtils;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;

public class GameTestBatchRunner {
    private static final Logger f_127550_ = LogUtils.getLogger();
    private final BlockPos f_127551_;
    final ServerLevel f_127552_;
    private final GameTestTicker f_127553_;
    private final int f_127554_;
    private final List<GameTestInfo> f_127555_;
    private final List<Pair<GameTestBatch, Collection<GameTestInfo>>> f_127557_;
    private final BlockPos.MutableBlockPos f_127560_;

    public GameTestBatchRunner(Collection<GameTestBatch> p_127563_, BlockPos p_127564_, Rotation p_127565_, ServerLevel p_127566_, GameTestTicker p_127567_, int p_127568_) {
        this.f_127560_ = p_127564_.m_122032_();
        this.f_127551_ = p_127564_;
        this.f_127552_ = p_127566_;
        this.f_127553_ = p_127567_;
        this.f_127554_ = p_127568_;
        this.f_127557_ = (List)p_127563_.stream().map(p_177068_ -> {
            Collection $$3 = (Collection)p_177068_.m_127549_().stream().map(p_177072_ -> new GameTestInfo((TestFunction)p_177072_, p_127565_, p_127566_)).collect(ImmutableList.toImmutableList());
            return Pair.of((Object)p_177068_, (Object)$$3);
        }).collect(ImmutableList.toImmutableList());
        this.f_127555_ = (List)this.f_127557_.stream().flatMap(p_177074_ -> ((Collection)p_177074_.getSecond()).stream()).collect(ImmutableList.toImmutableList());
    }

    public List<GameTestInfo> m_127569_() {
        return this.f_127555_;
    }

    public void m_127583_() {
        this.m_127570_(0);
    }

    void m_127570_(final int p_127571_) {
        if (p_127571_ >= this.f_127557_.size()) {
            return;
        }
        Pair<GameTestBatch, Collection<GameTestInfo>> $$1 = this.f_127557_.get(p_127571_);
        final GameTestBatch $$2 = (GameTestBatch)$$1.getFirst();
        Collection $$3 = (Collection)$$1.getSecond();
        Map<GameTestInfo, BlockPos> $$4 = this.m_177075_($$3);
        String $$5 = $$2.m_127546_();
        f_127550_.info("Running test batch '{}' ({} tests)...", (Object)$$5, (Object)$$3.size());
        $$2.m_127547_(this.f_127552_);
        final MultipleTestTracker $$6 = new MultipleTestTracker();
        $$3.forEach($$6::m_127809_);
        $$6.m_127811_(new GameTestListener(){

            private void m_177088_() {
                if ($$6.m_127821_()) {
                    $$2.m_177063_(GameTestBatchRunner.this.f_127552_);
                    GameTestBatchRunner.this.m_127570_(p_127571_ + 1);
                }
            }

            @Override
            public void m_8073_(GameTestInfo p_127590_) {
            }

            @Override
            public void m_142378_(GameTestInfo p_177090_) {
                this.m_177088_();
            }

            @Override
            public void m_8066_(GameTestInfo p_127592_) {
                this.m_177088_();
            }
        });
        $$3.forEach(p_177079_ -> {
            BlockPos $$2 = (BlockPos)$$4.get(p_177079_);
            GameTestRunner.m_127742_(p_177079_, $$2, this.f_127553_);
        });
    }

    private Map<GameTestInfo, BlockPos> m_177075_(Collection<GameTestInfo> p_177076_) {
        HashMap $$1 = Maps.newHashMap();
        int $$2 = 0;
        AABB $$3 = new AABB(this.f_127560_);
        for (GameTestInfo $$4 : p_177076_) {
            BlockPos $$5 = new BlockPos(this.f_127560_);
            StructureBlockEntity $$6 = StructureUtils.m_127883_($$4.m_127645_(), $$5, $$4.m_127646_(), 2, this.f_127552_, true);
            AABB $$7 = StructureUtils.m_127847_($$6);
            $$4.m_127617_($$6.m_58899_());
            $$1.put($$4, new BlockPos(this.f_127560_));
            $$3 = $$3.m_82367_($$7);
            this.f_127560_.m_122184_((int)$$7.m_82362_() + 5, 0, 0);
            if ($$2++ % this.f_127554_ != this.f_127554_ - 1) continue;
            this.f_127560_.m_122184_(0, 0, (int)$$3.m_82385_() + 6);
            this.f_127560_.m_142451_(this.f_127551_.m_123341_());
            $$3 = new AABB(this.f_127560_);
        }
        return $$1;
    }
}

