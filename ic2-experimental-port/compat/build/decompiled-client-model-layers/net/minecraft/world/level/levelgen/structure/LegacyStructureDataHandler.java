/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Maps
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.StructureFeatureIndexSavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class LegacyStructureDataHandler {
    private static final Map<String, String> f_71299_ = Util.m_137469_(Maps.newHashMap(), p_71337_ -> {
        p_71337_.put("Village", "Village");
        p_71337_.put("Mineshaft", "Mineshaft");
        p_71337_.put("Mansion", "Mansion");
        p_71337_.put("Igloo", "Temple");
        p_71337_.put("Desert_Pyramid", "Temple");
        p_71337_.put("Jungle_Pyramid", "Temple");
        p_71337_.put("Swamp_Hut", "Temple");
        p_71337_.put("Stronghold", "Stronghold");
        p_71337_.put("Monument", "Monument");
        p_71337_.put("Fortress", "Fortress");
        p_71337_.put("EndCity", "EndCity");
    });
    private static final Map<String, String> f_71300_ = Util.m_137469_(Maps.newHashMap(), p_71325_ -> {
        p_71325_.put("Iglu", "Igloo");
        p_71325_.put("TeDP", "Desert_Pyramid");
        p_71325_.put("TeJP", "Jungle_Pyramid");
        p_71325_.put("TeSH", "Swamp_Hut");
    });
    private static final Set<String> f_209874_ = Set.of("pillager_outpost", "mineshaft", "mansion", "jungle_pyramid", "desert_pyramid", "igloo", "ruined_portal", "shipwreck", "swamp_hut", "stronghold", "monument", "ocean_ruin", "fortress", "endcity", "buried_treasure", "village", "nether_fossil", "bastion_remnant");
    private final boolean f_71301_;
    private final Map<String, Long2ObjectMap<CompoundTag>> f_71302_ = Maps.newHashMap();
    private final Map<String, StructureFeatureIndexSavedData> f_71303_ = Maps.newHashMap();
    private final List<String> f_71304_;
    private final List<String> f_71305_;

    public LegacyStructureDataHandler(@Nullable DimensionDataStorage p_71308_, List<String> p_71309_, List<String> p_71310_) {
        this.f_71304_ = p_71309_;
        this.f_71305_ = p_71310_;
        this.m_71320_(p_71308_);
        boolean $$3 = false;
        for (String $$4 : this.f_71305_) {
            $$3 |= this.f_71302_.get($$4) != null;
        }
        this.f_71301_ = $$3;
    }

    public void m_71318_(long p_71319_) {
        for (String $$1 : this.f_71304_) {
            StructureFeatureIndexSavedData $$2 = this.f_71303_.get($$1);
            if ($$2 == null || !$$2.m_73373_(p_71319_)) continue;
            $$2.m_73375_(p_71319_);
            $$2.m_77762_();
        }
    }

    public CompoundTag m_71326_(CompoundTag p_71327_) {
        CompoundTag $$1 = p_71327_.m_128469_("Level");
        ChunkPos $$2 = new ChunkPos($$1.m_128451_("xPos"), $$1.m_128451_("zPos"));
        if (this.m_71311_($$2.f_45578_, $$2.f_45579_)) {
            p_71327_ = this.m_71328_(p_71327_, $$2);
        }
        CompoundTag $$3 = $$1.m_128469_("Structures");
        CompoundTag $$4 = $$3.m_128469_("References");
        for (String $$5 : this.f_71305_) {
            boolean $$6 = f_209874_.contains($$5.toLowerCase(Locale.ROOT));
            if ($$4.m_128425_($$5, 12) || !$$6) continue;
            int $$7 = 8;
            LongArrayList $$8 = new LongArrayList();
            for (int $$9 = $$2.f_45578_ - 8; $$9 <= $$2.f_45578_ + 8; ++$$9) {
                for (int $$10 = $$2.f_45579_ - 8; $$10 <= $$2.f_45579_ + 8; ++$$10) {
                    if (!this.m_71314_($$9, $$10, $$5)) continue;
                    $$8.add(ChunkPos.m_45589_($$9, $$10));
                }
            }
            $$4.m_128428_($$5, (List<Long>)$$8);
        }
        $$3.m_128365_("References", $$4);
        $$1.m_128365_("Structures", $$3);
        p_71327_.m_128365_("Level", $$1);
        return p_71327_;
    }

    private boolean m_71314_(int p_71315_, int p_71316_, String p_71317_) {
        if (!this.f_71301_) {
            return false;
        }
        return this.f_71302_.get(p_71317_) != null && this.f_71303_.get(f_71299_.get(p_71317_)).m_73369_(ChunkPos.m_45589_(p_71315_, p_71316_));
    }

    private boolean m_71311_(int p_71312_, int p_71313_) {
        if (!this.f_71301_) {
            return false;
        }
        for (String $$2 : this.f_71305_) {
            if (this.f_71302_.get($$2) == null || !this.f_71303_.get(f_71299_.get($$2)).m_73373_(ChunkPos.m_45589_(p_71312_, p_71313_))) continue;
            return true;
        }
        return false;
    }

    private CompoundTag m_71328_(CompoundTag p_71329_, ChunkPos p_71330_) {
        CompoundTag $$2 = p_71329_.m_128469_("Level");
        CompoundTag $$3 = $$2.m_128469_("Structures");
        CompoundTag $$4 = $$3.m_128469_("Starts");
        for (String $$5 : this.f_71305_) {
            CompoundTag $$8;
            Long2ObjectMap<CompoundTag> $$6 = this.f_71302_.get($$5);
            if ($$6 == null) continue;
            long $$7 = p_71330_.m_45588_();
            if (!this.f_71303_.get(f_71299_.get($$5)).m_73373_($$7) || ($$8 = (CompoundTag)$$6.get($$7)) == null) continue;
            $$4.m_128365_($$5, $$8);
        }
        $$3.m_128365_("Starts", $$4);
        $$2.m_128365_("Structures", $$3);
        p_71329_.m_128365_("Level", $$2);
        return p_71329_;
    }

    private void m_71320_(@Nullable DimensionDataStorage p_71321_) {
        if (p_71321_ == null) {
            return;
        }
        for (String $$1 : this.f_71304_) {
            CompoundTag $$2 = new CompoundTag();
            try {
                $$2 = p_71321_.m_78158_($$1, 1493).m_128469_("data").m_128469_("Features");
                if ($$2.m_128456_()) {
                    continue;
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
            for (String $$3 : $$2.m_128431_()) {
                String $$7;
                String $$8;
                CompoundTag $$4 = $$2.m_128469_($$3);
                long $$5 = ChunkPos.m_45589_($$4.m_128451_("ChunkX"), $$4.m_128451_("ChunkZ"));
                ListTag $$6 = $$4.m_128437_("Children", 10);
                if (!$$6.isEmpty() && ($$8 = f_71300_.get($$7 = $$6.m_128728_(0).m_128461_("id"))) != null) {
                    $$4.m_128359_("id", $$8);
                }
                String $$9 = $$4.m_128461_("id");
                this.f_71302_.computeIfAbsent($$9, p_71335_ -> new Long2ObjectOpenHashMap()).put($$5, (Object)$$4);
            }
            String $$10 = $$1 + "_index";
            StructureFeatureIndexSavedData $$11 = p_71321_.m_164861_(StructureFeatureIndexSavedData::m_163534_, StructureFeatureIndexSavedData::new, $$10);
            if ($$11.m_73364_().isEmpty()) {
                StructureFeatureIndexSavedData $$12 = new StructureFeatureIndexSavedData();
                this.f_71303_.put($$1, $$12);
                for (String $$13 : $$2.m_128431_()) {
                    CompoundTag $$14 = $$2.m_128469_($$13);
                    $$12.m_73365_(ChunkPos.m_45589_($$14.m_128451_("ChunkX"), $$14.m_128451_("ChunkZ")));
                }
                $$12.m_77762_();
                continue;
            }
            this.f_71303_.put($$1, $$11);
        }
    }

    public static LegacyStructureDataHandler m_71331_(ResourceKey<Level> p_71332_, @Nullable DimensionDataStorage p_71333_) {
        if (p_71332_ == Level.f_46428_) {
            return new LegacyStructureDataHandler(p_71333_, (List<String>)ImmutableList.of((Object)"Monument", (Object)"Stronghold", (Object)"Village", (Object)"Mineshaft", (Object)"Temple", (Object)"Mansion"), (List<String>)ImmutableList.of((Object)"Village", (Object)"Mineshaft", (Object)"Mansion", (Object)"Igloo", (Object)"Desert_Pyramid", (Object)"Jungle_Pyramid", (Object)"Swamp_Hut", (Object)"Stronghold", (Object)"Monument"));
        }
        if (p_71332_ == Level.f_46429_) {
            ImmutableList $$2 = ImmutableList.of((Object)"Fortress");
            return new LegacyStructureDataHandler(p_71333_, (List<String>)$$2, (List<String>)$$2);
        }
        if (p_71332_ == Level.f_46430_) {
            ImmutableList $$3 = ImmutableList.of((Object)"EndCity");
            return new LegacyStructureDataHandler(p_71333_, (List<String>)$$3, (List<String>)$$3);
        }
        throw new RuntimeException(String.format(Locale.ROOT, "Unknown dimension type : %s", p_71332_));
    }
}

