/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSyntaxException
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetInstrumentFunction
extends LootItemConditionalFunction {
    final TagKey<Instrument> f_231006_;

    SetInstrumentFunction(LootItemCondition[] p_231008_, TagKey<Instrument> p_231009_) {
        super(p_231008_);
        this.f_231006_ = p_231009_;
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_230994_;
    }

    @Override
    public ItemStack m_7372_(ItemStack p_231017_, LootContext p_231018_) {
        InstrumentItem.m_220110_(p_231017_, this.f_231006_, p_231018_.m_230907_());
        return p_231017_;
    }

    public static LootItemConditionalFunction.Builder<?> m_231011_(TagKey<Instrument> p_231012_) {
        return SetInstrumentFunction.m_80683_(p_231015_ -> new SetInstrumentFunction((LootItemCondition[])p_231015_, p_231012_));
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<SetInstrumentFunction> {
        @Override
        public void m_6170_(JsonObject p_231029_, SetInstrumentFunction p_231030_, JsonSerializationContext p_231031_) {
            super.m_6170_(p_231029_, p_231030_, p_231031_);
            p_231029_.addProperty("options", "#" + p_231030_.f_231006_.f_203868_());
        }

        @Override
        public SetInstrumentFunction m_6821_(JsonObject p_231021_, JsonDeserializationContext p_231022_, LootItemCondition[] p_231023_) {
            String $$3 = GsonHelper.m_13906_(p_231021_, "options");
            if (!$$3.startsWith("#")) {
                throw new JsonSyntaxException("Inline tag value not supported: " + $$3);
            }
            return new SetInstrumentFunction(p_231023_, TagKey.m_203882_(Registry.f_235737_, new ResourceLocation($$3.substring(1))));
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}

