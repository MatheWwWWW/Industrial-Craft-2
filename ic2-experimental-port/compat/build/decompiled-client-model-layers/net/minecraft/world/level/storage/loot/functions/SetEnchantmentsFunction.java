/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSyntaxException
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class SetEnchantmentsFunction
extends LootItemConditionalFunction {
    final Map<Enchantment, NumberProvider> f_165334_;
    final boolean f_165335_;

    SetEnchantmentsFunction(LootItemCondition[] p_165337_, Map<Enchantment, NumberProvider> p_165338_, boolean p_165339_) {
        super(p_165337_);
        this.f_165334_ = ImmutableMap.copyOf(p_165338_);
        this.f_165335_ = p_165339_;
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_165221_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return (Set)this.f_165334_.values().stream().flatMap(p_165349_ -> p_165349_.m_6231_().stream()).collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public ItemStack m_7372_(ItemStack p_165346_, LootContext p_165347_) {
        Object2IntOpenHashMap $$2 = new Object2IntOpenHashMap();
        this.f_165334_.forEach((arg_0, arg_1) -> SetEnchantmentsFunction.m_165350_((Object2IntMap)$$2, p_165347_, arg_0, arg_1));
        if (p_165346_.m_41720_() == Items.f_42517_) {
            ItemStack $$3 = new ItemStack(Items.f_42690_);
            $$2.forEach((p_165343_, p_165344_) -> EnchantedBookItem.m_41153_($$3, new EnchantmentInstance((Enchantment)p_165343_, (int)p_165344_)));
            return $$3;
        }
        Map<Enchantment, Integer> $$4 = EnchantmentHelper.m_44831_(p_165346_);
        if (this.f_165335_) {
            $$2.forEach((p_165366_, p_165367_) -> SetEnchantmentsFunction.m_165355_($$4, p_165366_, Math.max($$4.getOrDefault(p_165366_, 0) + p_165367_, 0)));
        } else {
            $$2.forEach((p_165361_, p_165362_) -> SetEnchantmentsFunction.m_165355_($$4, p_165361_, Math.max(p_165362_, 0)));
        }
        EnchantmentHelper.m_44865_($$4, p_165346_);
        return p_165346_;
    }

    private static void m_165355_(Map<Enchantment, Integer> p_165356_, Enchantment p_165357_, int p_165358_) {
        if (p_165358_ == 0) {
            p_165356_.remove(p_165357_);
        } else {
            p_165356_.put(p_165357_, p_165358_);
        }
    }

    private static /* synthetic */ void m_165350_(Object2IntMap p_165351_, LootContext p_165352_, Enchantment p_165353_, NumberProvider p_165354_) {
        p_165351_.put((Object)p_165353_, p_165354_.m_142683_(p_165352_));
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<SetEnchantmentsFunction> {
        @Override
        public void m_6170_(JsonObject p_165394_, SetEnchantmentsFunction p_165395_, JsonSerializationContext p_165396_) {
            super.m_6170_(p_165394_, p_165395_, p_165396_);
            JsonObject $$3 = new JsonObject();
            p_165395_.f_165334_.forEach((p_165387_, p_165388_) -> {
                ResourceLocation $$4 = Registry.f_122825_.m_7981_((Enchantment)p_165387_);
                if ($$4 == null) {
                    throw new IllegalArgumentException("Don't know how to serialize enchantment " + p_165387_);
                }
                $$3.add($$4.toString(), p_165396_.serialize(p_165388_));
            });
            p_165394_.add("enchantments", (JsonElement)$$3);
            p_165394_.addProperty("add", Boolean.valueOf(p_165395_.f_165335_));
        }

        @Override
        public SetEnchantmentsFunction m_6821_(JsonObject p_165381_, JsonDeserializationContext p_165382_, LootItemCondition[] p_165383_) {
            HashMap $$3 = Maps.newHashMap();
            if (p_165381_.has("enchantments")) {
                JsonObject $$4 = GsonHelper.m_13930_(p_165381_, "enchantments");
                for (Map.Entry $$5 : $$4.entrySet()) {
                    String $$6 = (String)$$5.getKey();
                    JsonElement $$7 = (JsonElement)$$5.getValue();
                    Enchantment $$8 = Registry.f_122825_.m_6612_(new ResourceLocation($$6)).orElseThrow(() -> new JsonSyntaxException("Unknown enchantment '" + $$6 + "'"));
                    NumberProvider $$9 = (NumberProvider)p_165382_.deserialize($$7, NumberProvider.class);
                    $$3.put($$8, $$9);
                }
            }
            boolean $$10 = GsonHelper.m_13855_(p_165381_, "add", false);
            return new SetEnchantmentsFunction(p_165383_, $$3, $$10);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }

    public static class Builder
    extends LootItemConditionalFunction.Builder<Builder> {
        private final Map<Enchantment, NumberProvider> f_165368_ = Maps.newHashMap();
        private final boolean f_165369_;

        public Builder() {
            this(false);
        }

        public Builder(boolean p_165372_) {
            this.f_165369_ = p_165372_;
        }

        @Override
        protected Builder m_6477_() {
            return this;
        }

        public Builder m_165374_(Enchantment p_165375_, NumberProvider p_165376_) {
            this.f_165368_.put(p_165375_, p_165376_);
            return this;
        }

        @Override
        public LootItemFunction m_7453_() {
            return new SetEnchantmentsFunction(this.m_80699_(), this.f_165368_, this.f_165369_);
        }

        @Override
        protected /* synthetic */ LootItemConditionalFunction.Builder m_6477_() {
            return this.m_6477_();
        }
    }
}

