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
import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class DynamicLoot
extends LootPoolSingletonContainer {
    final ResourceLocation f_79463_;

    DynamicLoot(ResourceLocation p_79465_, int p_79466_, int p_79467_, LootItemCondition[] p_79468_, LootItemFunction[] p_79469_) {
        super(p_79466_, p_79467_, p_79468_, p_79469_);
        this.f_79463_ = p_79465_;
    }

    @Override
    public LootPoolEntryType m_6751_() {
        return LootPoolEntries.f_79622_;
    }

    @Override
    public void m_6948_(Consumer<ItemStack> p_79481_, LootContext p_79482_) {
        p_79482_.m_78942_(this.f_79463_, p_79481_);
    }

    public static LootPoolSingletonContainer.Builder<?> m_79483_(ResourceLocation p_79484_) {
        return DynamicLoot.m_79687_((p_79487_, p_79488_, p_79489_, p_79490_) -> new DynamicLoot(p_79484_, p_79487_, p_79488_, p_79489_, p_79490_));
    }

    public static class Serializer
    extends LootPoolSingletonContainer.Serializer<DynamicLoot> {
        @Override
        public void m_7219_(JsonObject p_79500_, DynamicLoot p_79501_, JsonSerializationContext p_79502_) {
            super.m_7219_(p_79500_, p_79501_, p_79502_);
            p_79500_.addProperty("name", p_79501_.f_79463_.toString());
        }

        @Override
        protected DynamicLoot m_7267_(JsonObject p_79493_, JsonDeserializationContext p_79494_, int p_79495_, int p_79496_, LootItemCondition[] p_79497_, LootItemFunction[] p_79498_) {
            ResourceLocation $$6 = new ResourceLocation(GsonHelper.m_13906_(p_79493_, "name"));
            return new DynamicLoot($$6, p_79495_, p_79496_, p_79497_, p_79498_);
        }

        @Override
        protected /* synthetic */ LootPoolSingletonContainer m_7267_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int n, int n2, LootItemCondition[] lootItemConditionArray, LootItemFunction[] lootItemFunctionArray) {
            return this.m_7267_(jsonObject, jsonDeserializationContext, n, n2, lootItemConditionArray, lootItemFunctionArray);
        }
    }
}

