/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMaps
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  net.minecraft.world.entity.npc.VillagerTrades$ItemListing
 *  net.minecraftforge.event.village.VillagerTradesEvent
 *  net.minecraftforge.event.village.WandererTradesEvent
 */
package ic2.core.platform.recipes.villager;

import ic2.core.platform.recipes.villager.ITradeComp;
import ic2.core.platform.recipes.villager.SimpleTrade;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Collection;
import java.util.List;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;

public class TradeBuilder {
    Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = new Int2ObjectOpenHashMap();
    int highestLevel = 1;

    public TradeBuilder addTrade(int level, ITradeComp ... comps) {
        return this.addTrade(level, new SimpleTrade(comps));
    }

    public TradeBuilder addTrade(int level, int maxUses, ITradeComp ... comps) {
        return this.addTrade(level, new SimpleTrade(maxUses, 5, 0.05f, comps));
    }

    public TradeBuilder addTrade(int level, int maxUses, float villagerCheese, ITradeComp ... comps) {
        return this.addTrade(level, new SimpleTrade(maxUses, 5, villagerCheese, comps));
    }

    public TradeBuilder addTrade(int level, int maxUses, int xp, float villagerCheese, ITradeComp ... comps) {
        return this.addTrade(level, new SimpleTrade(maxUses, xp, villagerCheese, comps));
    }

    public TradeBuilder addTrade(int level, VillagerTrades.ItemListing trade) {
        ((List)this.trades.computeIfAbsent(level, T -> CollectionUtils.createList())).add(trade);
        this.highestLevel = Math.max(level, this.highestLevel);
        return this;
    }

    public TradeBuilder addTrades(int[] level, ITradeComp ... comps) {
        return this.addTrades(level, new SimpleTrade(comps));
    }

    public TradeBuilder addTrades(int[] level, int maxUses, ITradeComp ... comps) {
        return this.addTrades(level, new SimpleTrade(maxUses, 5, 0.05f, comps));
    }

    public TradeBuilder addTrades(int[] level, int maxUses, float villagerCheese, ITradeComp ... comps) {
        return this.addTrades(level, new SimpleTrade(maxUses, 5, villagerCheese, comps));
    }

    public TradeBuilder addTrades(int[] level, int maxUses, int xp, float villagerCheese, ITradeComp ... comps) {
        return this.addTrades(level, new SimpleTrade(maxUses, xp, villagerCheese, comps));
    }

    public TradeBuilder addTrades(int[] level, VillagerTrades.ItemListing trade) {
        for (int i = 0; i < level.length; ++i) {
            ((List)this.trades.computeIfAbsent(level[i], T -> CollectionUtils.createList())).add(trade);
            this.highestLevel = Math.max(level[i], this.highestLevel);
        }
        return this;
    }

    public Int2ObjectMap<VillagerTrades.ItemListing[]> build() {
        Int2ObjectOpenHashMap trades = new Int2ObjectOpenHashMap();
        Int2ObjectMaps.fastForEach(this.trades, arg_0 -> TradeBuilder.lambda$build$2((Int2ObjectMap)trades, arg_0));
        for (int i = 1; i < this.highestLevel; ++i) {
            trades.putIfAbsent(i, (Object)new VillagerTrades.ItemListing[0]);
        }
        return trades;
    }

    public void buildVillager(VillagerTradesEvent event) {
        Int2ObjectMaps.fastForEach(this.trades, T -> ((List)event.getTrades().computeIfAbsent(T.getIntKey(), this::create)).addAll((Collection)T.getValue()));
    }

    public void buildWanderingTrade(WandererTradesEvent event, boolean rare) {
        this.trades.values().forEach((rare ? event.getRareTrades() : event.getGenericTrades())::addAll);
    }

    private List<VillagerTrades.ItemListing> create(int index) {
        return CollectionUtils.createList();
    }

    private static /* synthetic */ void lambda$build$2(Int2ObjectMap trades, Int2ObjectMap.Entry T) {
        List values = (List)T.getValue();
        trades.put(T.getIntKey(), (Object)values.toArray(new VillagerTrades.ItemListing[values.size()]));
    }
}

