/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2LongLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2LongMap
 *  it.unimi.dsi.fastutil.objects.Object2LongMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2LongMaps
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.personal.trade;

import ic2.api.items.ICoinItem;
import ic2.api.items.ITagItem;
import ic2.api.items.ItemRegistries;
import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.INBTSavable;
import ic2.core.inventory.filter.StackFilter;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.NBTUtils;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import java.util.UUID;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public abstract class Trade
implements INetworkDataBuffer,
INBTSavable {
    public int moneyNeeded;
    public SimpleInventory neededItems = new SimpleInventory(4);
    public int tradeCap = 0;
    public int tradeDelay = 0;
    public boolean infinite = false;
    public boolean tradeReset = false;
    public long lastTrade = 0L;
    public int tradesDone = 0;
    public int totalTradesDone = 0;
    public boolean perPlayer = false;
    public Object2IntMap<UUID> playerTradesDone = new Object2IntLinkedOpenHashMap();
    public Object2LongMap<UUID> playerLastTradesDone = new Object2LongLinkedOpenHashMap();

    public boolean canDoPerPlayer() {
        return false;
    }

    public boolean canRemove(UUID player) {
        return !this.tradeReset || !this.playerTradesDone.containsKey((Object)player) && !this.playerLastTradesDone.containsKey((Object)player);
    }

    public void reset() {
        this.playerLastTradesDone.clear();
        this.playerTradesDone.clear();
    }

    public int getStock(int original, UUID player) {
        if (this.tradeCap <= 0) {
            return original;
        }
        return this.perPlayer ? (player == null ? 0 : Math.min(this.tradeCap - this.playerTradesDone.getInt((Object)player), original)) : Math.min(this.tradeCap - this.tradesDone, original);
    }

    public boolean shouldRefresh(long currentTime, UUID player) {
        if (!this.tradeReset) {
            return false;
        }
        if (this.perPlayer && player != null) {
            long last = this.playerLastTradesDone.getLong((Object)player);
            if (last > 0L && currentTime - last >= (long)this.tradeDelay) {
                this.playerTradesDone.removeInt((Object)player);
                this.playerLastTradesDone.removeLong((Object)player);
                return true;
            }
        } else if (!this.perPlayer && this.lastTrade > 0L && currentTime - this.lastTrade >= (long)this.tradeDelay) {
            this.tradesDone = 0;
            this.lastTrade = 0L;
            return true;
        }
        return false;
    }

    public boolean canTrade(long currentTime, UUID player) {
        if (this.tradeReset) {
            this.shouldRefresh(currentTime, player);
            return this.perPlayer ? player != null && this.playerTradesDone.getInt((Object)player) < this.tradeCap : this.tradesDone < this.tradeCap;
        }
        if (this.perPlayer) {
            return !(player == null || this.tradeDelay > 0 && this.playerLastTradesDone.getLong((Object)player) + (long)this.tradeDelay > currentTime || this.tradeCap > 0 && this.playerTradesDone.getInt((Object)player) >= this.tradeCap);
        }
        return !(this.tradeDelay > 0 && this.lastTrade + (long)this.tradeDelay > currentTime || this.tradeCap > 0 && this.tradesDone >= this.tradeCap);
    }

    public void onTrade(long currentTime, UUID player) {
        if (!this.tradeReset || this.tradeReset && (this.perPlayer ? this.playerTradesDone.getInt((Object)player) == 0 : this.lastTrade == 0L)) {
            if (this.perPlayer) {
                this.playerLastTradesDone.put((Object)player, currentTime);
            } else {
                this.lastTrade = currentTime;
            }
        }
        if (this.perPlayer) {
            ((Object2IntLinkedOpenHashMap)this.playerTradesDone).addTo((Object)player, 1);
        } else {
            ++this.tradesDone;
        }
        ++this.totalTradesDone;
    }

    public void updateMoneyTrade(int value) {
        if (this.moneyNeeded == value) {
            return;
        }
        this.moneyNeeded = value;
        this.neededItems.clear();
        if (this.moneyNeeded > 0) {
            NonNullList<ItemStack> result = ItemRegistries.generateCoins(value, (NonNullList<ItemStack>)NonNullList.m_122779_(), true);
            int m = Math.min(4, result.size());
            for (int i = 0; i < m; ++i) {
                this.neededItems.setStackInSlot(i, (ItemStack)result.get(i));
            }
        }
    }

    public boolean hasItems(IItemTransporter transporter, int filters, NonNullList<ItemStack> result) {
        int foundItems = 0;
        int m = this.neededItems.getSlotCount();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = this.neededItems.getStackInSlot(i);
            if (stack.m_41619_()) continue;
            ++foundItems;
            ItemStack found = transporter.removeItem(new StackFilter(stack, filters | (stack.m_41720_() instanceof ITagItem ? 32 : 0)), null, stack.m_41613_(), false);
            if (found.m_41613_() < stack.m_41613_()) {
                return false;
            }
            result.add((Object)found);
        }
        return foundItems > 0;
    }

    public void findItems(IItemTransporter source, IItemTransporter output, int inputFilter) {
        int m = this.neededItems.getSlotCount();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = this.neededItems.getStackInSlot(i);
            if (stack.m_41619_()) continue;
            StackFilter filter = new StackFilter(stack, inputFilter | (stack.m_41720_() instanceof ITagItem ? 32 : 0));
            source.removeItem(filter, null, output.addItem(source.removeItem(filter, null, stack.m_41613_(), true), null, false), false);
        }
    }

    public int getFoundMoney(IHasInventory inventory) {
        int moneyFound = 0;
        int m = inventory.getSlotCount();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!(stack.m_41720_() instanceof ICoinItem)) continue;
            moneyFound += ((ICoinItem)stack.m_41720_()).getMoneyValue(stack) * stack.m_41613_();
        }
        return moneyFound;
    }

    public int consumeCoins(IHasInventory inventory, int needed) {
        int m = inventory.getSlotCount();
        for (int i = 0; i < m && needed > 0; ++i) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!(stack.m_41720_() instanceof ICoinItem)) continue;
            int amount = ((ICoinItem)stack.m_41720_()).getMoneyValue(stack);
            int toRemove = Math.min(Mth.m_14165_((double)((double)needed / (double)amount)), stack.m_41613_());
            needed -= toRemove * amount;
            stack.m_41774_(toRemove);
        }
        return needed;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeBoolean(this.infinite);
        buffer.writeBoolean(this.tradeReset);
        buffer.writeBoolean(this.perPlayer);
        buffer.writeVarInt(this.moneyNeeded);
        buffer.writeVarInt(this.tradeCap);
        buffer.writeVarInt(this.tradeDelay);
        buffer.writeVarInt(this.totalTradesDone);
        if (this.perPlayer && this.canDoPerPlayer()) {
            buffer.writeVarInt(this.playerLastTradesDone.size());
            for (Object2LongMap.Entry id : Object2LongMaps.fastIterable(this.playerLastTradesDone)) {
                buffer.writeUUID((UUID)id.getKey());
                buffer.writeLong(id.getLongValue());
                buffer.writeVarInt(this.playerTradesDone.getInt(id.getKey()));
            }
        } else {
            buffer.writeLong(this.lastTrade);
            buffer.writeVarInt(this.tradesDone);
        }
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.infinite = buffer.readBoolean();
        this.tradeReset = buffer.readBoolean();
        this.perPlayer = buffer.readBoolean();
        this.moneyNeeded = buffer.readVarInt();
        this.tradeCap = buffer.readVarInt();
        this.tradeDelay = buffer.readVarInt();
        this.totalTradesDone = buffer.readVarInt();
        if (this.perPlayer && this.canDoPerPlayer()) {
            int m = buffer.readVarInt();
            for (int i = 0; i < m; ++i) {
                UUID id = buffer.readUUID();
                this.playerLastTradesDone.put((Object)id, buffer.readLong());
                this.playerTradesDone.put((Object)id, buffer.readVarInt());
            }
        } else {
            this.lastTrade = buffer.readLong();
            this.tradesDone = buffer.readVarInt();
        }
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        NBTUtils.put(nbt, "wanted", this.neededItems.save(new CompoundTag()));
        NBTUtils.putInt(nbt, "money", this.moneyNeeded, 0);
        NBTUtils.putInt(nbt, "cap", this.tradeCap, 0);
        NBTUtils.putBoolean(nbt, "infinite", this.infinite, false);
        NBTUtils.putBoolean(nbt, "reset", this.tradeReset, false);
        NBTUtils.putBoolean(nbt, "perPlayer", this.perPlayer, false);
        NBTUtils.putInt(nbt, "delay", this.tradeDelay, 0);
        NBTUtils.putInt(nbt, "total", this.totalTradesDone, 0);
        if (this.perPlayer && this.canDoPerPlayer()) {
            ListTag list = new ListTag();
            for (Object2LongMap.Entry id : Object2LongMaps.fastIterable(this.playerLastTradesDone)) {
                CompoundTag data = new CompoundTag();
                data.m_128362_("id", (UUID)id.getKey());
                data.m_128356_("last", id.getLongValue());
                data.m_128405_("count", this.playerTradesDone.getInt(id.getKey()));
                list.add((Object)data);
            }
            NBTUtils.put(nbt, "players", list);
        } else {
            NBTUtils.putInt(nbt, "done", this.tradesDone, 0);
            NBTUtils.putLong(nbt, "last", this.lastTrade, 0L);
        }
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        this.neededItems.load(nbt.m_128469_("wanted"));
        this.moneyNeeded = nbt.m_128451_("money");
        this.tradeCap = nbt.m_128451_("cap");
        this.infinite = nbt.m_128471_("infinite");
        this.tradeReset = nbt.m_128471_("reset");
        this.perPlayer = nbt.m_128471_("perPlayer");
        this.tradeDelay = nbt.m_128451_("delay");
        this.totalTradesDone = nbt.m_128451_("total");
        if (this.perPlayer && this.canDoPerPlayer()) {
            for (CompoundTag data : NBTListWrapper.wrap(nbt.m_128437_("players", 10), CompoundTag.class)) {
                UUID id = data.m_128342_("id");
                this.playerLastTradesDone.put((Object)id, data.m_128454_("last"));
                this.playerTradesDone.put((Object)id, data.m_128451_("count"));
            }
        } else {
            this.tradesDone = nbt.m_128451_("done");
            this.lastTrade = nbt.m_128454_("last");
        }
    }
}

