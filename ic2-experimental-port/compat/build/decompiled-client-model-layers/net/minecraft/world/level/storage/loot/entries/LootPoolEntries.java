/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage.loot.entries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.CompositeEntryBase;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.EntryGroup;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.entries.SequentialEntry;
import net.minecraft.world.level.storage.loot.entries.TagEntry;

public class LootPoolEntries {
    public static final LootPoolEntryType f_79619_ = LootPoolEntries.m_79629_("empty", new EmptyLootItem.Serializer());
    public static final LootPoolEntryType f_79620_ = LootPoolEntries.m_79629_("item", new LootItem.Serializer());
    public static final LootPoolEntryType f_79621_ = LootPoolEntries.m_79629_("loot_table", new LootTableReference.Serializer());
    public static final LootPoolEntryType f_79622_ = LootPoolEntries.m_79629_("dynamic", new DynamicLoot.Serializer());
    public static final LootPoolEntryType f_79623_ = LootPoolEntries.m_79629_("tag", new TagEntry.Serializer());
    public static final LootPoolEntryType f_79624_ = LootPoolEntries.m_79629_("alternatives", CompositeEntryBase.m_79435_(AlternativesEntry::new));
    public static final LootPoolEntryType f_79625_ = LootPoolEntries.m_79629_("sequence", CompositeEntryBase.m_79435_(SequentialEntry::new));
    public static final LootPoolEntryType f_79626_ = LootPoolEntries.m_79629_("group", CompositeEntryBase.m_79435_(EntryGroup::new));

    private static LootPoolEntryType m_79629_(String p_79630_, Serializer<? extends LootPoolEntryContainer> p_79631_) {
        return Registry.m_122965_(Registry.f_122875_, new ResourceLocation(p_79630_), new LootPoolEntryType(p_79631_));
    }

    public static Object m_79628_() {
        return GsonAdapterFactory.m_78801_(Registry.f_122875_, "entry", "type", LootPoolEntryContainer::m_6751_).m_78822_();
    }
}

