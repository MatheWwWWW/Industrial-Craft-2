/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.Deserializers;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.PredicateManager;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.slf4j.Logger;

public class LootTables
extends SimpleJsonResourceReloadListener {
    private static final Logger f_79188_ = LogUtils.getLogger();
    private static final Gson f_79189_ = Deserializers.m_78800_().create();
    private Map<ResourceLocation, LootTable> f_79190_ = ImmutableMap.of();
    private final PredicateManager f_79191_;

    public LootTables(PredicateManager p_79194_) {
        super(f_79189_, "loot_tables");
        this.f_79191_ = p_79194_;
    }

    public LootTable m_79217_(ResourceLocation p_79218_) {
        return this.f_79190_.getOrDefault(p_79218_, LootTable.f_79105_);
    }

    @Override
    protected void m_5787_(Map<ResourceLocation, JsonElement> p_79214_, ResourceManager p_79215_, ProfilerFiller p_79216_) {
        ImmutableMap.Builder $$3 = ImmutableMap.builder();
        JsonElement $$4 = p_79214_.remove(BuiltInLootTables.f_78712_);
        if ($$4 != null) {
            f_79188_.warn("Datapack tried to redefine {} loot table, ignoring", (Object)BuiltInLootTables.f_78712_);
        }
        p_79214_.forEach((p_79198_, p_79199_) -> {
            try {
                LootTable $$3 = (LootTable)f_79189_.fromJson(p_79199_, LootTable.class);
                $$3.put(p_79198_, (Object)$$3);
            }
            catch (Exception $$4) {
                f_79188_.error("Couldn't parse loot table {}", p_79198_, (Object)$$4);
            }
        });
        $$3.put((Object)BuiltInLootTables.f_78712_, (Object)LootTable.f_79105_);
        ImmutableMap $$5 = $$3.build();
        ValidationContext $$6 = new ValidationContext(LootContextParamSets.f_81420_, this.f_79191_::m_79252_, arg_0 -> ((ImmutableMap)$$5).get(arg_0));
        $$5.forEach((p_79221_, p_79222_) -> LootTables.m_79202_($$6, p_79221_, p_79222_));
        $$6.m_79352_().forEach((p_79211_, p_79212_) -> f_79188_.warn("Found validation problem in {}: {}", p_79211_, p_79212_));
        this.f_79190_ = $$5;
    }

    public static void m_79202_(ValidationContext p_79203_, ResourceLocation p_79204_, LootTable p_79205_) {
        p_79205_.m_79136_(p_79203_.m_79355_(p_79205_.m_79122_()).m_79359_("{" + p_79204_ + "}", p_79204_));
    }

    public static JsonElement m_79200_(LootTable p_79201_) {
        return f_79189_.toJsonTree((Object)p_79201_);
    }

    public Set<ResourceLocation> m_79195_() {
        return this.f_79190_.keySet();
    }
}

