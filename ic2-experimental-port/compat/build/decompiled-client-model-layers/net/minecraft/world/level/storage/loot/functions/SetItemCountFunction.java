/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class SetItemCountFunction
extends LootItemConditionalFunction {
    final NumberProvider f_80997_;
    final boolean f_165407_;

    SetItemCountFunction(LootItemCondition[] p_165409_, NumberProvider p_165410_, boolean p_165411_) {
        super(p_165409_);
        this.f_80997_ = p_165410_;
        this.f_165407_ = p_165411_;
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80736_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return this.f_80997_.m_6231_();
    }

    @Override
    public ItemStack m_7372_(ItemStack p_81006_, LootContext p_81007_) {
        int $$2 = this.f_165407_ ? p_81006_.m_41613_() : 0;
        p_81006_.m_41764_(Mth.m_14045_($$2 + this.f_80997_.m_142683_(p_81007_), 0, p_81006_.m_41741_()));
        return p_81006_;
    }

    public static LootItemConditionalFunction.Builder<?> m_165412_(NumberProvider p_165413_) {
        return SetItemCountFunction.m_80683_(p_165423_ -> new SetItemCountFunction((LootItemCondition[])p_165423_, p_165413_, false));
    }

    public static LootItemConditionalFunction.Builder<?> m_165414_(NumberProvider p_165415_, boolean p_165416_) {
        return SetItemCountFunction.m_80683_(p_165420_ -> new SetItemCountFunction((LootItemCondition[])p_165420_, p_165415_, p_165416_));
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<SetItemCountFunction> {
        @Override
        public void m_6170_(JsonObject p_81026_, SetItemCountFunction p_81027_, JsonSerializationContext p_81028_) {
            super.m_6170_(p_81026_, p_81027_, p_81028_);
            p_81026_.add("count", p_81028_.serialize((Object)p_81027_.f_80997_));
            p_81026_.addProperty("add", Boolean.valueOf(p_81027_.f_165407_));
        }

        @Override
        public SetItemCountFunction m_6821_(JsonObject p_81018_, JsonDeserializationContext p_81019_, LootItemCondition[] p_81020_) {
            NumberProvider $$3 = GsonHelper.m_13836_(p_81018_, "count", p_81019_, NumberProvider.class);
            boolean $$4 = GsonHelper.m_13855_(p_81018_, "add", false);
            return new SetItemCountFunction(p_81020_, $$3, $$4);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}

