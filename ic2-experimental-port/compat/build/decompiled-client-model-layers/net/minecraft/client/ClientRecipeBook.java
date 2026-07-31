/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashBasedTable
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.core.Registry;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.slf4j.Logger;

public class ClientRecipeBook
extends RecipeBook {
    private static final Logger f_90618_ = LogUtils.getLogger();
    private Map<RecipeBookCategories, List<RecipeCollection>> f_90619_ = ImmutableMap.of();
    private List<RecipeCollection> f_90620_ = ImmutableList.of();

    public void m_90625_(Iterable<Recipe<?>> p_90626_) {
        Map<RecipeBookCategories, List<List<Recipe<?>>>> $$1 = ClientRecipeBook.m_90642_(p_90626_);
        HashMap $$2 = Maps.newHashMap();
        ImmutableList.Builder $$3 = ImmutableList.builder();
        $$1.forEach((p_90630_, p_90631_) -> $$2.put(p_90630_, (List)p_90631_.stream().map(RecipeCollection::new).peek(arg_0 -> ((ImmutableList.Builder)$$3).add(arg_0)).collect(ImmutableList.toImmutableList())));
        RecipeBookCategories.f_92260_.forEach((p_90637_, p_90638_) -> $$2.put(p_90637_, (List)p_90638_.stream().flatMap(p_167706_ -> ((List)$$2.getOrDefault(p_167706_, ImmutableList.of())).stream()).collect(ImmutableList.toImmutableList())));
        this.f_90619_ = ImmutableMap.copyOf((Map)$$2);
        this.f_90620_ = $$3.build();
    }

    private static Map<RecipeBookCategories, List<List<Recipe<?>>>> m_90642_(Iterable<Recipe<?>> p_90643_) {
        HashMap $$1 = Maps.newHashMap();
        HashBasedTable $$2 = HashBasedTable.create();
        for (Recipe<?> $$3 : p_90643_) {
            if ($$3.m_5598_() || $$3.m_142505_()) continue;
            RecipeBookCategories $$4 = ClientRecipeBook.m_90646_($$3);
            String $$5 = $$3.m_6076_();
            if ($$5.isEmpty()) {
                $$1.computeIfAbsent($$4, p_90645_ -> Lists.newArrayList()).add(ImmutableList.of($$3));
                continue;
            }
            List $$6 = (List)$$2.get((Object)$$4, (Object)$$5);
            if ($$6 == null) {
                $$6 = Lists.newArrayList();
                $$2.put((Object)$$4, (Object)$$5, (Object)$$6);
                $$1.computeIfAbsent($$4, p_90641_ -> Lists.newArrayList()).add($$6);
            }
            $$6.add($$3);
        }
        return $$1;
    }

    private static RecipeBookCategories m_90646_(Recipe<?> p_90647_) {
        RecipeType<?> $$1 = p_90647_.m_6671_();
        if ($$1 == RecipeType.f_44107_) {
            ItemStack $$2 = p_90647_.m_8043_();
            CreativeModeTab $$3 = $$2.m_41720_().m_41471_();
            if ($$3 == CreativeModeTab.f_40749_) {
                return RecipeBookCategories.CRAFTING_BUILDING_BLOCKS;
            }
            if ($$3 == CreativeModeTab.f_40756_ || $$3 == CreativeModeTab.f_40757_) {
                return RecipeBookCategories.CRAFTING_EQUIPMENT;
            }
            if ($$3 == CreativeModeTab.f_40751_) {
                return RecipeBookCategories.CRAFTING_REDSTONE;
            }
            return RecipeBookCategories.CRAFTING_MISC;
        }
        if ($$1 == RecipeType.f_44108_) {
            if (p_90647_.m_8043_().m_41720_().m_41472_()) {
                return RecipeBookCategories.FURNACE_FOOD;
            }
            if (p_90647_.m_8043_().m_41720_() instanceof BlockItem) {
                return RecipeBookCategories.FURNACE_BLOCKS;
            }
            return RecipeBookCategories.FURNACE_MISC;
        }
        if ($$1 == RecipeType.f_44109_) {
            if (p_90647_.m_8043_().m_41720_() instanceof BlockItem) {
                return RecipeBookCategories.BLAST_FURNACE_BLOCKS;
            }
            return RecipeBookCategories.BLAST_FURNACE_MISC;
        }
        if ($$1 == RecipeType.f_44110_) {
            return RecipeBookCategories.SMOKER_FOOD;
        }
        if ($$1 == RecipeType.f_44112_) {
            return RecipeBookCategories.STONECUTTER;
        }
        if ($$1 == RecipeType.f_44111_) {
            return RecipeBookCategories.CAMPFIRE;
        }
        if ($$1 == RecipeType.f_44113_) {
            return RecipeBookCategories.SMITHING;
        }
        f_90618_.warn("Unknown recipe category: {}/{}", LogUtils.defer(() -> Registry.f_122864_.m_7981_(p_90647_.m_6671_())), LogUtils.defer(p_90647_::m_6423_));
        return RecipeBookCategories.UNKNOWN;
    }

    public List<RecipeCollection> m_90639_() {
        return this.f_90620_;
    }

    public List<RecipeCollection> m_90623_(RecipeBookCategories p_90624_) {
        return this.f_90619_.getOrDefault((Object)p_90624_, Collections.emptyList());
    }
}

