/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSyntaxException
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class SetStewEffectFunction
extends LootItemConditionalFunction {
    final Map<MobEffect, NumberProvider> f_81214_;

    SetStewEffectFunction(LootItemCondition[] p_81216_, Map<MobEffect, NumberProvider> p_81217_) {
        super(p_81216_);
        this.f_81214_ = ImmutableMap.copyOf(p_81217_);
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80746_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return (Set)this.f_81214_.values().stream().flatMap(p_165470_ -> p_165470_.m_6231_().stream()).collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public ItemStack m_7372_(ItemStack p_81223_, LootContext p_81224_) {
        if (!p_81223_.m_150930_(Items.f_42718_) || this.f_81214_.isEmpty()) {
            return p_81223_;
        }
        RandomSource $$2 = p_81224_.m_230907_();
        int $$3 = $$2.m_188503_(this.f_81214_.size());
        Map.Entry $$4 = (Map.Entry)Iterables.get(this.f_81214_.entrySet(), (int)$$3);
        MobEffect $$5 = (MobEffect)$$4.getKey();
        int $$6 = ((NumberProvider)$$4.getValue()).m_142683_(p_81224_);
        if (!$$5.m_8093_()) {
            $$6 *= 20;
        }
        SuspiciousStewItem.m_43258_(p_81223_, $$5, $$6);
        return p_81223_;
    }

    public static Builder m_81228_() {
        return new Builder();
    }

    public static class Builder
    extends LootItemConditionalFunction.Builder<Builder> {
        private final Map<MobEffect, NumberProvider> f_81229_ = Maps.newLinkedHashMap();

        @Override
        protected Builder m_6477_() {
            return this;
        }

        public Builder m_165472_(MobEffect p_165473_, NumberProvider p_165474_) {
            this.f_81229_.put(p_165473_, p_165474_);
            return this;
        }

        @Override
        public LootItemFunction m_7453_() {
            return new SetStewEffectFunction(this.m_80699_(), this.f_81229_);
        }

        @Override
        protected /* synthetic */ LootItemConditionalFunction.Builder m_6477_() {
            return this.m_6477_();
        }
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<SetStewEffectFunction> {
        @Override
        public void m_6170_(JsonObject p_81247_, SetStewEffectFunction p_81248_, JsonSerializationContext p_81249_) {
            super.m_6170_(p_81247_, p_81248_, p_81249_);
            if (!p_81248_.f_81214_.isEmpty()) {
                JsonArray $$3 = new JsonArray();
                for (MobEffect $$4 : p_81248_.f_81214_.keySet()) {
                    JsonObject $$5 = new JsonObject();
                    ResourceLocation $$6 = Registry.f_122823_.m_7981_($$4);
                    if ($$6 == null) {
                        throw new IllegalArgumentException("Don't know how to serialize mob effect " + $$4);
                    }
                    $$5.add("type", (JsonElement)new JsonPrimitive($$6.toString()));
                    $$5.add("duration", p_81249_.serialize((Object)p_81248_.f_81214_.get($$4)));
                    $$3.add((JsonElement)$$5);
                }
                p_81247_.add("effects", (JsonElement)$$3);
            }
        }

        @Override
        public SetStewEffectFunction m_6821_(JsonObject p_81239_, JsonDeserializationContext p_81240_, LootItemCondition[] p_81241_) {
            LinkedHashMap $$3 = Maps.newLinkedHashMap();
            if (p_81239_.has("effects")) {
                JsonArray $$4 = GsonHelper.m_13933_(p_81239_, "effects");
                for (JsonElement $$5 : $$4) {
                    String $$6 = GsonHelper.m_13906_($$5.getAsJsonObject(), "type");
                    MobEffect $$7 = Registry.f_122823_.m_6612_(new ResourceLocation($$6)).orElseThrow(() -> new JsonSyntaxException("Unknown mob effect '" + $$6 + "'"));
                    NumberProvider $$8 = GsonHelper.m_13836_($$5.getAsJsonObject(), "duration", p_81240_, NumberProvider.class);
                    $$3.put($$7, $$8);
                }
            }
            return new SetStewEffectFunction(p_81241_, $$3);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}

