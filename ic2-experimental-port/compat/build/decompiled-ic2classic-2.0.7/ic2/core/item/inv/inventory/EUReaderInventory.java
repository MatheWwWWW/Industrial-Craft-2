/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.LongCollection
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.Nameable
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.item.inv.inventory;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.PacketStats;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.tiles.readers.IEUStorage;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.PortableInventory;
import ic2.core.item.inv.container.EUReaderContainer;
import ic2.core.networking.buffers.data.NBTBuffer;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.LongAverager;
import ic2.core.utils.collection.NBTListWrapper;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class EUReaderInventory
extends PortableInventory {
    Long2ObjectMap<SinkInfo> receivers = new Long2ObjectLinkedOpenHashMap();
    Long2ObjectMap<SourceInfo> emitters = new Long2ObjectLinkedOpenHashMap();
    public List<SinkInfo> sortedReceivers = CollectionUtils.createList();
    public List<SourceInfo> sortedEmitters = CollectionUtils.createList();

    public EUReaderInventory(Player owner, IHasHeldGui held, ItemStack stack, Slot slot) {
        super(owner, held, stack, slot);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new EUReaderContainer(this, player, this.getID(), windowID);
    }

    @Override
    public int getSlotCount() {
        return 0;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void accept(NBTBuffer buffer) {
        LongOpenHashSet receivedSinks = new LongOpenHashSet((LongCollection)this.receivers.keySet());
        LongOpenHashSet receivedSource = new LongOpenHashSet((LongCollection)this.emitters.keySet());
        boolean changedSinks = false;
        boolean changedSources = false;
        BlockPos.MutableBlockPos positions = new BlockPos.MutableBlockPos();
        for (CompoundTag nbt : NBTListWrapper.wrap(buffer.getNBT().m_128437_("data", 10), CompoundTag.class)) {
            Object info;
            Nameable nameable;
            long pos = nbt.m_128454_("pos");
            receivedSinks.remove(pos);
            receivedSource.remove(pos);
            BlockEntity tile = Minecraft.m_91087_().f_91073_.m_7702_((BlockPos)positions.m_122188_(pos));
            Object name = null;
            name = tile != null ? (tile instanceof Nameable ? ((nameable = (Nameable)tile).m_8077_() ? nameable.m_7770_() : nameable.m_7755_()) : tile.m_58900_().m_60734_().m_49954_()) : ((Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation(nbt.m_128461_("blockId")))).m_49954_();
            if (nbt.m_128471_("accepting")) {
                info = (SinkInfo)this.receivers.get(pos);
                if (info == null) {
                    info = new SinkInfo(nbt.m_128461_("id"), (Component)name, pos, nbt.m_128441_("tier") ? nbt.m_128451_("tier") : 1);
                    this.receivers.put(pos, info);
                    changedSinks = true;
                }
                ((SinkInfo)info).addPacket(nbt.m_128454_("packets"), nbt.m_128454_("power"));
                ((SinkInfo)info).updateEUData(nbt.m_128451_("stored_eu"), nbt.m_128451_("max_eu"));
                continue;
            }
            info = (SourceInfo)this.emitters.get(pos);
            if (info == null) {
                info = new SourceInfo(nbt.m_128461_("id"), (Component)name, pos, nbt.m_128441_("tier") ? nbt.m_128451_("tier") : 1);
                this.emitters.put(pos, info);
                changedSources = true;
            }
            ((SourceInfo)info).addPacket(nbt.m_128454_("packets"), nbt.m_128454_("power"), nbt.m_128441_("max") ? nbt.m_128451_("max") : -1);
            ((SourceInfo)info).updateEUData(nbt.m_128451_("stored_eu"), nbt.m_128451_("max_eu"));
        }
        if (receivedSinks.size() > 0) {
            this.receivers.keySet().removeAll((LongCollection)receivedSinks);
            changedSinks = true;
        }
        if (receivedSource.size() > 0) {
            this.emitters.keySet().removeAll((LongCollection)receivedSource);
            changedSources = true;
        }
        if (changedSinks) {
            this.sortedReceivers.clear();
            this.sortedReceivers.addAll((Collection<SinkInfo>)this.receivers.values());
        }
        if (changedSources) {
            this.sortedEmitters.clear();
            this.sortedEmitters.addAll((Collection<SourceInfo>)this.emitters.values());
        }
    }

    public static void processEnergyNet(ItemStack stack, Player player, BlockPos pos) {
        IEnergyTile tile = EnergyNet.INSTANCE.getTile(player.f_19853_, pos);
        if (tile == null) {
            return;
        }
        ListTag list = new ListTag();
        for (PacketStats stat : EnergyNet.INSTANCE.getPacketStats(tile)) {
            CompoundTag nbt = EUReaderInventory.pack(stat);
            if (nbt == null) continue;
            list.add((Object)nbt);
        }
        IC2.NETWORKING.get(true).sendItemBuffer(player, stack, "EU_INFO", new NBTBuffer("data", (Tag)list));
    }

    public static CompoundTag pack(PacketStats stat) {
        CompoundTag nbt = new CompoundTag();
        nbt.m_128379_("accepting", stat.isAccepting());
        nbt.m_128356_("power", stat.getPower());
        nbt.m_128356_("packets", stat.getPackets());
        IEnergyTile tile = stat.getTile();
        nbt.m_128359_("id", ForgeRegistries.ITEMS.getKey((Object)tile.getWorldObj().m_8055_(tile.getPosition()).m_60734_().m_5456_()).toString());
        nbt.m_128356_("pos", tile.getPosition().m_121878_());
        if (tile instanceof IEUStorage) {
            IEUStorage storage = (IEUStorage)((Object)tile);
            nbt.m_128405_("stored_eu", storage.getStoredEU());
            nbt.m_128405_("max_eu", storage.getMaxEU());
        }
        nbt.m_128359_("blockId", ForgeRegistries.ITEMS.getKey((Object)tile.getWorldObj().m_8055_(tile.getPosition()).m_60734_().m_5456_()).toString());
        if (!stat.isAccepting() && tile instanceof IEnergySource) {
            IEnergySource source = (IEnergySource)tile;
            nbt.m_128405_("max", source.getMaxEnergyOutput());
            nbt.m_128344_("tier", (byte)source.getSourceTier());
            return nbt;
        }
        if (stat.isAccepting() && tile instanceof IEnergySink) {
            nbt.m_128344_("tier", (byte)((IEnergySink)tile).getSinkTier());
            return nbt;
        }
        return null;
    }

    public static class SinkInfo {
        public final ItemStack item;
        public final BlockPos position;
        public final Component name;
        public final LongAverager packetsReceived = new LongAverager(80);
        public final LongAverager energyReceived = new LongAverager(80);
        public final int tier;
        public final int max;
        public long lastPower = -1L;
        public long lastPackets = -1L;
        public int storedEU;
        public int maxEU;

        public SinkInfo(String id, Component name, long pos, int tier) {
            this.item = new ItemStack((ItemLike)ForgeRegistries.ITEMS.getValue(new ResourceLocation(id)));
            this.name = name;
            this.position = BlockPos.m_122022_((long)pos);
            this.tier = tier;
            this.max = EnergyNet.INSTANCE.getPowerFromTier(tier);
        }

        public SinkInfo(List<SinkInfo> infos) {
            int i;
            SinkInfo info = infos.get(0);
            this.item = info.item;
            this.name = info.name;
            this.tier = info.tier;
            this.max = info.max;
            this.position = BlockPos.f_121853_;
            long[] packetArray = new long[80];
            long[] energyArray = new long[80];
            int m = infos.size();
            for (i = 0; i < m; ++i) {
                info = infos.get(i);
                info.energyReceived.extract(energyArray);
                info.packetsReceived.extract(packetArray);
                this.storedEU += info.storedEU;
                this.maxEU += info.maxEU;
            }
            for (i = 0; i < 80; ++i) {
                this.energyReceived.addEntry(energyArray[i]);
                this.packetsReceived.addEntry(packetArray[i]);
            }
            this.energyReceived.buildCache();
            this.packetsReceived.buildCache();
        }

        public void updateEUData(int stored, int max) {
            this.storedEU = stored;
            this.maxEU = max;
        }

        public void addPacket(long packets, long power) {
            if (this.lastPower == -1L) {
                this.lastPower = power;
                this.lastPackets = packets;
                return;
            }
            this.packetsReceived.addEntry(packets - this.lastPackets);
            this.energyReceived.addEntry(power - this.lastPower);
            this.lastPower = power;
            this.lastPackets = packets;
        }

        public double getEnergy() {
            return this.energyReceived.getCachedDoubleAverage();
        }

        public long getPackets() {
            return (long)Math.ceil(this.packetsReceived.getCachedDoubleAverage());
        }
    }

    public static class SourceInfo {
        public final ItemStack item;
        public final BlockPos position;
        public final Component name;
        public final LongAverager packetsReceived = new LongAverager(80);
        public final LongAverager energyReceived = new LongAverager(80);
        public int output;
        public final int tier;
        public final int max;
        public long lastPower = -1L;
        public long lastPackets = -1L;
        public int storedEU;
        public int maxEU;

        public SourceInfo(String id, Component name, long pos, int tier) {
            this.item = new ItemStack((ItemLike)ForgeRegistries.ITEMS.getValue(new ResourceLocation(id)));
            this.name = name;
            this.position = BlockPos.m_122022_((long)pos);
            this.tier = tier;
            this.max = EnergyNet.INSTANCE.getPowerFromTier(tier);
        }

        public SourceInfo(List<SourceInfo> infos) {
            int i;
            SourceInfo info = infos.get(0);
            this.item = info.item;
            this.name = info.name;
            this.tier = info.tier;
            this.max = info.max;
            this.position = BlockPos.f_121853_;
            long[] packetArray = new long[80];
            long[] energyArray = new long[80];
            int m = infos.size();
            for (i = 0; i < m; ++i) {
                info = infos.get(i);
                info.energyReceived.extract(energyArray);
                info.packetsReceived.extract(packetArray);
                this.output += info.output;
                this.storedEU += info.storedEU;
                this.maxEU += info.maxEU;
            }
            for (i = 0; i < 80; ++i) {
                this.energyReceived.addEntry(energyArray[i]);
                this.packetsReceived.addEntry(packetArray[i]);
            }
            this.energyReceived.buildCache();
            this.packetsReceived.buildCache();
        }

        public void updateEUData(int stored, int max) {
            this.storedEU = stored;
            this.maxEU = max;
        }

        public void addPacket(long packets, long power, int output) {
            this.output = output;
            if (this.lastPower == -1L) {
                this.lastPower = power;
                this.lastPackets = packets;
                return;
            }
            this.packetsReceived.addEntry(packets - this.lastPackets);
            this.energyReceived.addEntry(power - this.lastPower);
            this.lastPower = power;
            this.lastPackets = packets;
        }

        public double getEnergy() {
            return this.energyReceived.getCachedDoubleAverage();
        }

        public long getPackets() {
            return this.packetsReceived.getCachedAverage();
        }
    }
}

