/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.level.Level
 */
package ic2.core.block.personal;

import ic2.core.IC2;
import ic2.core.block.personal.TileEntityTradeOMat;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;

public class TradingMarket {
    private final Level world;
    private final Set<BlockPos> traders = new HashSet<BlockPos>();
    private final List<MarketWatcher> watchers = new ArrayList<MarketWatcher>(1);

    public TradingMarket(final Level level) {
        this.world = level;
        this.watchers.add(new MarketWatcher(){

            @Override
            public void onAdd(BlockPos blockPos) {
                TradingMarket.this.traders.add(blockPos);
            }

            @Override
            public void onRemove(BlockPos blockPos) {
                TradingMarket.this.traders.remove(blockPos);
            }
        });
        if (Util.inDev()) {
            this.watchers.add(new MarketWatcher(){

                @Override
                public void onAdd(BlockPos blockPos) {
                    IC2.log.info(LogCategory.Block, "Market registration at " + Util.formatPosition(level.m_7702_(blockPos)));
                }

                @Override
                public void onRemove(BlockPos blockPos) {
                    IC2.log.info(LogCategory.Block, "Market removal at " + Util.formatPosition(level.m_7702_(blockPos)));
                }
            });
        }
    }

    public void registerTradeOMat(TileEntityTradeOMat tileEntityTradeOMat) {
        assert (tileEntityTradeOMat.m_58898_() && !tileEntityTradeOMat.m_58904_().f_46443_);
        assert (tileEntityTradeOMat.m_58904_() == this.world);
        assert (!this.traders.contains(tileEntityTradeOMat.m_58899_()));
        for (MarketWatcher marketWatcher : this.watchers) {
            marketWatcher.onAdd(tileEntityTradeOMat.m_58899_());
        }
    }

    public void unregisterTradeOMat(TileEntityTradeOMat tileEntityTradeOMat) {
        assert (tileEntityTradeOMat.m_58898_() && !tileEntityTradeOMat.m_58904_().f_46443_);
        assert (this.traders.contains(tileEntityTradeOMat.m_58899_()));
        for (MarketWatcher marketWatcher : this.watchers) {
            marketWatcher.onRemove(tileEntityTradeOMat.m_58899_());
        }
    }

    public void registerWatcher(MarketWatcher marketWatcher) {
        assert (!this.watchers.contains(marketWatcher));
        this.watchers.add(marketWatcher);
    }

    public void unregisterWatcher(MarketWatcher marketWatcher) {
        assert (this.watchers.contains(marketWatcher));
        this.watchers.remove(marketWatcher);
    }

    public Stream<BlockPos> tradersAround(BlockPos blockPos, int n) {
        long l = n * n;
        return this.traders.stream().filter(blockPos2 -> blockPos.m_123331_((Vec3i)blockPos2) <= (double)l);
    }

    public static interface MarketWatcher {
        public void onAdd(BlockPos var1);

        public void onRemove(BlockPos var1);
    }
}

