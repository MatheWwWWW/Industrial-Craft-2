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
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootItem
extends LootPoolSingletonContainer {
    final Item f_79564_;

    LootItem(Item p_79566_, int p_79567_, int p_79568_, LootItemCondition[] p_79569_, LootItemFunction[] p_79570_) {
        super(p_79567_, p_79568_, p_79569_, p_79570_);
        this.f_79564_ = p_79566_;
    }

    @Override
    public LootPoolEntryType m_6751_() {
        return LootPoolEntries.f_79620_;
    }

    @Override
    public void m_6948_(Consumer<ItemStack> p_79590_, LootContext p_79591_) {
        p_79590_.accept(new ItemStack(this.f_79564_));
    }

    public static LootPoolSingletonContainer.Builder<?> m_79579_(ItemLike p_79580_) {
        return LootItem.m_79687_((p_79583_, p_79584_, p_79585_, p_79586_) -> new LootItem(p_79580_.m_5456_(), p_79583_, p_79584_, p_79585_, p_79586_));
    }

    public static class Serializer
    extends LootPoolSingletonContainer.Serializer<LootItem> {
        @Override
        public void m_7219_(JsonObject p_79601_, LootItem p_79602_, JsonSerializationContext p_79603_) {
            super.m_7219_(p_79601_, p_79602_, p_79603_);
            ResourceLocation $$3 = Registry.f_122827_.m_7981_(p_79602_.f_79564_);
            if ($$3 == null) {
                throw new IllegalArgumentException("Can't serialize unknown item " + p_79602_.f_79564_);
            }
            p_79601_.addProperty("name", $$3.toString());
        }

        @Override
        protected LootItem m_7267_(JsonObject p_79594_, JsonDeserializationContext p_79595_, int p_79596_, int p_79597_, LootItemCondition[] p_79598_, LootItemFunction[] p_79599_) {
            Item $$6 = GsonHelper.m_13909_(p_79594_, "name");
            return new LootItem($$6, p_79596_, p_79597_, p_79598_, p_79599_);
        }

        @Override
        protected /* synthetic */ LootPoolSingletonContainer m_7267_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int n, int n2, LootItemCondition[] lootItemConditionArray, LootItemFunction[] lootItemFunctionArray) {
            return this.m_7267_(jsonObject, jsonDeserializationContext, n, n2, lootItemConditionArray, lootItemFunctionArray);
        }
    }
}

