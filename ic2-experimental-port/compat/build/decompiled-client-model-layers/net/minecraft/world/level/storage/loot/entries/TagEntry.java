/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 */
package net.minecraft.world.level.storage.loot.entries;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class TagEntry
extends LootPoolSingletonContainer {
    final TagKey<Item> f_79821_;
    final boolean f_79822_;

    TagEntry(TagKey<Item> p_205078_, boolean p_205079_, int p_205080_, int p_205081_, LootItemCondition[] p_205082_, LootItemFunction[] p_205083_) {
        super(p_205080_, p_205081_, p_205082_, p_205083_);
        this.f_79821_ = p_205078_;
        this.f_79822_ = p_205079_;
    }

    @Override
    public LootPoolEntryType m_6751_() {
        return LootPoolEntries.f_79623_;
    }

    @Override
    public void m_6948_(Consumer<ItemStack> p_79854_, LootContext p_79855_) {
        Registry.f_122827_.m_206058_(this.f_79821_).forEach(p_205094_ -> p_79854_.accept(new ItemStack((Holder<Item>)p_205094_)));
    }

    private boolean m_79845_(LootContext p_79846_, Consumer<LootPoolEntry> p_79847_) {
        if (this.m_79639_(p_79846_)) {
            for (final Holder<Item> $$2 : Registry.f_122827_.m_206058_(this.f_79821_)) {
                p_79847_.accept(new LootPoolSingletonContainer.EntryBase(){

                    @Override
                    public void m_6941_(Consumer<ItemStack> p_79869_, LootContext p_79870_) {
                        p_79869_.accept(new ItemStack($$2));
                    }
                });
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean m_6562_(LootContext p_79861_, Consumer<LootPoolEntry> p_79862_) {
        if (this.f_79822_) {
            return this.m_79845_(p_79861_, p_79862_);
        }
        return super.m_6562_(p_79861_, p_79862_);
    }

    public static LootPoolSingletonContainer.Builder<?> m_205084_(TagKey<Item> p_205085_) {
        return TagEntry.m_79687_((p_205099_, p_205100_, p_205101_, p_205102_) -> new TagEntry(p_205085_, false, p_205099_, p_205100_, p_205101_, p_205102_));
    }

    public static LootPoolSingletonContainer.Builder<?> m_205095_(TagKey<Item> p_205096_) {
        return TagEntry.m_79687_((p_205088_, p_205089_, p_205090_, p_205091_) -> new TagEntry(p_205096_, true, p_205088_, p_205089_, p_205090_, p_205091_));
    }

    public static class Serializer
    extends LootPoolSingletonContainer.Serializer<TagEntry> {
        @Override
        public void m_7219_(JsonObject p_79888_, TagEntry p_79889_, JsonSerializationContext p_79890_) {
            super.m_7219_(p_79888_, p_79889_, p_79890_);
            p_79888_.addProperty("name", p_79889_.f_79821_.f_203868_().toString());
            p_79888_.addProperty("expand", Boolean.valueOf(p_79889_.f_79822_));
        }

        @Override
        protected TagEntry m_7267_(JsonObject p_79873_, JsonDeserializationContext p_79874_, int p_79875_, int p_79876_, LootItemCondition[] p_79877_, LootItemFunction[] p_79878_) {
            ResourceLocation $$6 = new ResourceLocation(GsonHelper.m_13906_(p_79873_, "name"));
            TagKey<Item> $$7 = TagKey.m_203882_(Registry.f_122904_, $$6);
            boolean $$8 = GsonHelper.m_13912_(p_79873_, "expand");
            return new TagEntry($$7, $$8, p_79875_, p_79876_, p_79877_, p_79878_);
        }

        @Override
        protected /* synthetic */ LootPoolSingletonContainer m_7267_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int n, int n2, LootItemCondition[] lootItemConditionArray, LootItemFunction[] lootItemFunctionArray) {
            return this.m_7267_(jsonObject, jsonDeserializationContext, n, n2, lootItemConditionArray, lootItemFunctionArray);
        }
    }
}

