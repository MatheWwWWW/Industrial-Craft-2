/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.stats.Stat
 *  net.minecraft.stats.Stats
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.saveddata.SavedData
 */
package ic2.core.platform.player;

import ic2.api.crops.ICrop;
import ic2.core.IC2;
import ic2.core.block.crops.CropRegistry;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;

public class CropStatistics
extends SavedData {
    Map<UUID, PlayerInfo> allCropsFound = CollectionUtils.createLinkedMap();

    public CropStatistics() {
    }

    public CropStatistics(CompoundTag tag) {
        for (CompoundTag nbt : NBTListWrapper.wrap(tag.m_128437_("data", 10), CompoundTag.class)) {
            this.allCropsFound.put(nbt.m_128342_("player"), new PlayerInfo(nbt));
        }
    }

    public CompoundTag m_7176_(CompoundTag tag) {
        ListTag list = new ListTag();
        for (Map.Entry<UUID, PlayerInfo> entry : this.allCropsFound.entrySet()) {
            CompoundTag nbt = entry.getValue().save();
            nbt.m_128362_("player", entry.getKey());
            list.add((Object)list);
        }
        tag.m_128365_("data", (Tag)list);
        return tag;
    }

    public void onCropReceived(UUID id, ICrop crop, int growth, int gain, int resistance) {
        ServerPlayer player = IC2.PLATFORM.getServer().m_6846_().m_11259_(id);
        this.allCropsFound.computeIfAbsent(id, T -> new PlayerInfo()).onReceived((Player)player, crop, growth, gain, resistance);
    }

    public static CropStatistics getStats() {
        MinecraftServer server = IC2.PLATFORM.getServer();
        if (server == null) {
            return null;
        }
        return (CropStatistics)server.m_129783_().m_8895_().m_164861_(CropStatistics::new, CropStatistics::new, "ic2_crop_stats");
    }

    public static class PlayerInfo {
        Set<ICrop> discovered = CollectionUtils.createLinkedSet();
        int tier;
        int growth;
        int gain;
        int resistance;

        public PlayerInfo() {
        }

        public PlayerInfo(CompoundTag tag) {
            for (StringTag id : NBTListWrapper.wrap(tag.m_128437_("discovered", 8), StringTag.class)) {
                ICrop crop = CropRegistry.INSTANCE.getCrop(ResourceLocation.m_135820_((String)id.m_7916_()));
                if (crop == null) continue;
                this.discovered.add(crop);
            }
            this.tier = tag.m_128451_("tier");
            this.growth = tag.m_128451_("growth");
            this.gain = tag.m_128451_("gain");
            this.resistance = tag.m_128451_("resistance");
        }

        public CompoundTag save() {
            CompoundTag nbt = new CompoundTag();
            ListTag allFound = new ListTag();
            for (ICrop crop : this.discovered) {
                allFound.add((Object)StringTag.m_129297_((String)crop.id().toString()));
            }
            nbt.m_128365_("discovered", (Tag)allFound);
            nbt.m_128405_("tier", this.tier);
            nbt.m_128405_("growth", this.growth);
            nbt.m_128405_("gain", this.gain);
            nbt.m_128405_("resistance", this.resistance);
            return nbt;
        }

        public void onReceived(Player player, ICrop crop, int growth, int gain, int resistance) {
            this.discovered.add(crop);
            this.tier = Math.max(this.tier, crop.getProperties().getTier());
            this.growth = Math.max(this.growth, growth);
            this.gain = Math.max(this.gain, gain);
            this.resistance = Math.max(this.resistance, resistance);
            this.setValue(player, this.discovered.size(), IC2Stats.CROPS_DISCOVERED);
            this.setValue(player, this.tier, IC2Stats.HIGHEST_CROP_TIER);
            this.setValue(player, this.growth, IC2Stats.HIGHEST_CROP_GROWTH);
            this.setValue(player, this.gain, IC2Stats.HIGHEST_CROP_GAIN);
            this.setValue(player, this.resistance, IC2Stats.HIGHEST_CROP_RESISTANCE);
        }

        private void setValue(Player player, int newValue, ResourceLocation id) {
            Stat stat = Stats.f_12988_.m_12902_((Object)id);
            player.m_7166_(stat);
            player.m_36222_(id, newValue);
        }
    }
}

