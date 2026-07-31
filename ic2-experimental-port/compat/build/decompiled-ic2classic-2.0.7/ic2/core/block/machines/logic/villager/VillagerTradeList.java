/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.world.entity.npc.Villager
 *  net.minecraft.world.item.trading.MerchantOffer
 *  net.minecraft.world.item.trading.MerchantOffers
 */
package ic2.core.block.machines.logic.villager;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.IC2;
import ic2.core.block.machines.logic.villager.VillagerTrade;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.NBTUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

public class VillagerTradeList {
    UUID villager;
    int lastSize;
    int xp;
    List<VillagerTrade> knownTrades = CollectionUtils.createList();

    public VillagerTradeList(Villager villager) {
        this.villager = villager.m_20148_();
        this.xp = villager.m_7809_();
        MerchantOffers offers = villager.m_6616_();
        int m = offers.size();
        for (int i = 0; i < m; ++i) {
            this.knownTrades.add(new VillagerTrade(this.villager, i, (MerchantOffer)offers.get(i)));
        }
        this.lastSize = offers.size();
    }

    public VillagerTradeList(CompoundTag nbt) {
        this.villager = nbt.m_128342_("villager");
        this.lastSize = nbt.m_128451_("size");
        this.xp = nbt.m_128451_("xp");
        for (CompoundTag tag : NBTListWrapper.wrap(nbt.m_128437_("trades", 10), CompoundTag.class)) {
            this.knownTrades.add(new VillagerTrade(this.villager, tag));
        }
    }

    public VillagerTradeList(IInputBuffer buffer) {
        this.villager = buffer.readUUID();
        this.xp = buffer.readVarInt();
        int size = buffer.readVarInt();
        this.knownTrades.clear();
        for (int i = 0; i < size; ++i) {
            this.knownTrades.add(new VillagerTrade(this.villager, buffer));
        }
    }

    public boolean isDefault() {
        for (VillagerTrade trade : this.knownTrades) {
            if (!trade.getState()) continue;
            return false;
        }
        return true;
    }

    public int findIndex(VillagerTrade trade) {
        int m = this.knownTrades.size();
        for (int i = 0; i < m; ++i) {
            if (this.knownTrades.get(i) != trade) continue;
            return i;
        }
        return -1;
    }

    public List<VillagerTrade> getKnownTrades() {
        return this.knownTrades;
    }

    public UUID getOwner() {
        return this.villager;
    }

    public int getXp() {
        return this.xp;
    }

    public CompoundTag save() {
        CompoundTag nbt = new CompoundTag();
        nbt.m_128362_("villager", this.villager);
        nbt.m_128405_("size", this.lastSize);
        nbt.m_128405_("xp", this.xp);
        ListTag list = new ListTag();
        int m = this.knownTrades.size();
        for (int i = 0; i < m; ++i) {
            list.add((Object)this.knownTrades.get(i).save());
        }
        NBTUtils.put(nbt, "trades", list);
        return nbt;
    }

    public void write(IOutputBuffer buffer) {
        buffer.writeUUID(this.villager);
        buffer.writeVarInt(this.xp);
        buffer.writeVarInt(this.knownTrades.size());
        int m = this.knownTrades.size();
        for (int i = 0; i < m; ++i) {
            this.knownTrades.get(i).write(buffer);
        }
    }

    public boolean updateTrades(Villager villager) {
        return this.syncTrades(villager, villager.m_6616_());
    }

    private boolean syncTrades(Villager villager, MerchantOffers offers) {
        int newSize = offers.size();
        boolean result = false;
        if (newSize > this.lastSize) {
            for (int i = this.lastSize; i < newSize; ++i) {
                this.knownTrades.add(new VillagerTrade(this.villager, i, (MerchantOffer)offers.get(i)));
            }
            result = true;
        }
        if (this.xp != villager.m_7809_()) {
            this.xp = villager.m_7809_();
            result = true;
        }
        this.lastSize = newSize;
        Optional<List<VillagerTrade>> trades = this.rebuildTradeList(offers);
        if (trades.isPresent()) {
            this.knownTrades = trades.get();
            return true;
        }
        return result;
    }

    private Optional<List<VillagerTrade>> rebuildTradeList(MerchantOffers offers) {
        boolean changed = false;
        ObjectList trades = CollectionUtils.createList();
        int m = this.knownTrades.size();
        for (int i = 0; i < m; ++i) {
            VillagerTrade trade = this.knownTrades.get(i);
            int index = this.findTrade(offers, trade);
            if (index == -1) {
                IC2.LOGGER.info("Removed");
                changed = true;
                continue;
            }
            changed |= trade.updateIndex(index);
            changed |= trade.syncItems((MerchantOffer)offers.get(index));
            trades.add((VillagerTrade)trade);
        }
        return changed ? Optional.of(trades) : Optional.empty();
    }

    public int findTrade(MerchantOffers offer, VillagerTrade trade) {
        int index = trade.getIndex();
        if (index >= 0 && index < offer.size() && trade.matches((MerchantOffer)offer.get(index))) {
            return index;
        }
        int m = offer.size();
        for (int i = 0; i < m; ++i) {
            if (!trade.matches((MerchantOffer)offer.get(i))) continue;
            return i;
        }
        return -1;
    }
}

