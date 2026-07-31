/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.npc.Villager
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.trading.MerchantOffer
 *  net.minecraft.world.item.trading.MerchantOffers
 *  net.minecraft.world.level.entity.LevelEntityGetter
 */
package ic2.core.block.machines.logic.villager;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.block.machines.logic.villager.VillagerTrade;
import ic2.core.block.machines.logic.villager.VillagerTradeList;
import ic2.core.inventory.filter.StackFilter;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.platform.corehacks.mixins.server.VillagerMixin;
import ic2.core.platform.events.IC2EventHandler;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.NBTUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.IntConsumer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.entity.LevelEntityGetter;

public class VillagerList
implements INetworkDataBuffer {
    Map<UUID, VillagerTradeList> trades = CollectionUtils.createMap();
    Set<UUID> activeTrades = CollectionUtils.createLinkedSet();

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeVarInt(this.activeTrades.size());
        for (UUID owner : this.activeTrades) {
            this.trades.get(owner).write(buffer);
        }
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.trades.clear();
        int size = buffer.readVarInt();
        for (int i = 0; i < size; ++i) {
            VillagerTradeList list = new VillagerTradeList(buffer);
            this.trades.put(list.getOwner(), list);
        }
    }

    public CompoundTag save(CompoundTag nbt) {
        ListTag list = new ListTag();
        for (VillagerTradeList trade : this.trades.values()) {
            CompoundTag tag = new CompoundTag();
            tag.m_128365_("trade", (Tag)trade.save());
            NBTUtils.putBoolean(tag, "active", this.activeTrades.contains(trade.getOwner()), false);
            list.add((Object)tag);
        }
        NBTUtils.put(nbt, "trades", list);
        return nbt;
    }

    public void load(CompoundTag nbt) {
        for (CompoundTag tag : NBTListWrapper.wrap(nbt.m_128437_("trades", 10), CompoundTag.class)) {
            VillagerTradeList list = new VillagerTradeList(tag.m_128469_("trade"));
            this.trades.put(list.getOwner(), list);
            if (!tag.m_128471_("active")) continue;
            this.activeTrades.add(list.getOwner());
        }
    }

    public int getActiveTrades() {
        return this.activeTrades.size();
    }

    public List<UUID> getVisibleVillagerIds() {
        return new ObjectArrayList(this.trades.keySet());
    }

    public List<VillagerTradeList> getVisibleVillagerTrades() {
        return new ObjectArrayList(this.trades.values());
    }

    public VillagerTradeList getTrade(UUID owner) {
        return this.trades.get(owner);
    }

    public boolean loadData(UUID owner, int index, boolean state) {
        VillagerTradeList list = this.trades.get(owner);
        if (list == null) {
            return false;
        }
        List<VillagerTrade> trades = list.getKnownTrades();
        if (index < 0 || index >= trades.size()) {
            return false;
        }
        trades.get(index).setState(state);
        return true;
    }

    public boolean update(List<Villager> villagers, LevelEntityGetter<Entity> getter, Player trader) {
        boolean changed = false;
        ObjectLinkedOpenHashSet toRemove = new ObjectLinkedOpenHashSet(this.activeTrades);
        for (Villager villager : villagers) {
            UUID id = villager.m_20148_();
            toRemove.remove(id);
            if (villager.m_7962_() != null) continue;
            changed |= this.activeTrades.add(id);
            this.startTarding(trader, villager);
            VillagerTradeList list = this.trades.get(id);
            if (list == null) {
                list = new VillagerTradeList(villager);
                this.trades.put(id, list);
                changed = true;
                villager.m_7189_(null);
                continue;
            }
            changed |= list.updateTrades(villager);
            villager.m_7189_(null);
        }
        changed |= this.activeTrades.removeAll((Collection<?>)toRemove);
        for (UUID id : toRemove) {
            VillagerTradeList list = this.trades.get(id);
            if (list == null || !list.isDefault()) continue;
            this.trades.remove(id);
            changed = true;
        }
        return changed;
    }

    public boolean trade(LevelEntityGetter<Entity> getter, Player trader, IItemTransporter input, IItemTransporter output, IntConsumer xp) {
        boolean result = false;
        for (UUID activeTrade : this.activeTrades) {
            VillagerTradeList list;
            Villager villager;
            Entity entity = (Entity)getter.m_142694_(activeTrade);
            if (!(entity instanceof Villager) || (villager = (Villager)entity).m_7962_() != null || (list = this.trades.get(activeTrade)) == null) continue;
            this.startTarding(trader, villager);
            boolean didTrade = false;
            MerchantOffers offers = villager.m_6616_();
            for (VillagerTrade trade : list.getKnownTrades()) {
                if (!trade.getState() || trade.isOutOfStock()) continue;
                MerchantOffer offer = (MerchantOffer)offers.get(trade.getIndex());
                while (!offer.m_45380_() && this.trade(input, output, offer, true)) {
                    this.trade(input, output, offer, false);
                    IC2EventHandler.XP_ABSORBER = xp;
                    villager.m_6996_(offer);
                    IC2EventHandler.XP_ABSORBER = null;
                    didTrade = true;
                }
            }
            if (didTrade) {
                list.updateTrades(villager);
                result = true;
            }
            villager.m_7189_(null);
        }
        return result;
    }

    public boolean trade(IItemTransporter input, IItemTransporter output, MerchantOffer offer, boolean simulate) {
        ItemStack main = offer.m_45358_();
        ItemStack sub = offer.m_45364_();
        ItemStack out = offer.m_45368_();
        if (input.removeItem(StackFilter.defaultCompare(main), null, main.m_41613_(), simulate).m_41613_() < main.m_41613_()) {
            return false;
        }
        if (!sub.m_41619_() && input.removeItem(StackFilter.defaultCompare(sub), null, sub.m_41613_(), simulate).m_41613_() < sub.m_41613_()) {
            return false;
        }
        return output.addItem(out, null, simulate) >= out.m_41613_();
    }

    protected void startTarding(Player player, Villager villager) {
        if (player == null) {
            return;
        }
        ((VillagerMixin)villager).updatePrices(player);
        villager.m_7189_(player);
    }
}

