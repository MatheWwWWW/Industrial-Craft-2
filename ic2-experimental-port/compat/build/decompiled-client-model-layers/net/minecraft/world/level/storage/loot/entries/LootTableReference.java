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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootTableReference
extends LootPoolSingletonContainer {
    final ResourceLocation f_79754_;

    LootTableReference(ResourceLocation p_79756_, int p_79757_, int p_79758_, LootItemCondition[] p_79759_, LootItemFunction[] p_79760_) {
        super(p_79757_, p_79758_, p_79759_, p_79760_);
        this.f_79754_ = p_79756_;
    }

    @Override
    public LootPoolEntryType m_6751_() {
        return LootPoolEntries.f_79621_;
    }

    @Override
    public void m_6948_(Consumer<ItemStack> p_79774_, LootContext p_79775_) {
        LootTable $$2 = p_79775_.m_78940_(this.f_79754_);
        $$2.m_79131_(p_79775_, p_79774_);
    }

    @Override
    public void m_6165_(ValidationContext p_79770_) {
        if (p_79770_.m_79362_(this.f_79754_)) {
            p_79770_.m_79357_("Table " + this.f_79754_ + " is recursively called");
            return;
        }
        super.m_6165_(p_79770_);
        LootTable $$1 = p_79770_.m_79375_(this.f_79754_);
        if ($$1 == null) {
            p_79770_.m_79357_("Unknown loot table called " + this.f_79754_);
        } else {
            $$1.m_79136_(p_79770_.m_79359_("->{" + this.f_79754_ + "}", this.f_79754_));
        }
    }

    public static LootPoolSingletonContainer.Builder<?> m_79776_(ResourceLocation p_79777_) {
        return LootTableReference.m_79687_((p_79780_, p_79781_, p_79782_, p_79783_) -> new LootTableReference(p_79777_, p_79780_, p_79781_, p_79782_, p_79783_));
    }

    public static class Serializer
    extends LootPoolSingletonContainer.Serializer<LootTableReference> {
        @Override
        public void m_7219_(JsonObject p_79801_, LootTableReference p_79802_, JsonSerializationContext p_79803_) {
            super.m_7219_(p_79801_, p_79802_, p_79803_);
            p_79801_.addProperty("name", p_79802_.f_79754_.toString());
        }

        @Override
        protected LootTableReference m_7267_(JsonObject p_79786_, JsonDeserializationContext p_79787_, int p_79788_, int p_79789_, LootItemCondition[] p_79790_, LootItemFunction[] p_79791_) {
            ResourceLocation $$6 = new ResourceLocation(GsonHelper.m_13906_(p_79786_, "name"));
            return new LootTableReference($$6, p_79788_, p_79789_, p_79790_, p_79791_);
        }

        @Override
        protected /* synthetic */ LootPoolSingletonContainer m_7267_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int n, int n2, LootItemCondition[] lootItemConditionArray, LootItemFunction[] lootItemFunctionArray) {
            return this.m_7267_(jsonObject, jsonDeserializationContext, n, n2, lootItemConditionArray, lootItemFunctionArray);
        }
    }
}

