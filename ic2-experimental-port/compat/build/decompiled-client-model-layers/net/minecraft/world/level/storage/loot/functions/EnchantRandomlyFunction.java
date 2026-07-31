/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSyntaxException
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.slf4j.Logger;

public class EnchantRandomlyFunction
extends LootItemConditionalFunction {
    private static final Logger f_80414_ = LogUtils.getLogger();
    final List<Enchantment> f_80415_;

    EnchantRandomlyFunction(LootItemCondition[] p_80418_, Collection<Enchantment> p_80419_) {
        super(p_80418_);
        this.f_80415_ = ImmutableList.copyOf(p_80419_);
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80738_;
    }

    @Override
    public ItemStack m_7372_(ItemStack p_80429_, LootContext p_80430_) {
        Enchantment $$6;
        RandomSource $$2 = p_80430_.m_230907_();
        if (this.f_80415_.isEmpty()) {
            boolean $$3 = p_80429_.m_150930_(Items.f_42517_);
            List $$4 = Registry.f_122825_.m_123024_().filter(Enchantment::m_6592_).filter(p_80436_ -> $$3 || p_80436_.m_6081_(p_80429_)).collect(Collectors.toList());
            if ($$4.isEmpty()) {
                f_80414_.warn("Couldn't find a compatible enchantment for {}", (Object)p_80429_);
                return p_80429_;
            }
            Enchantment $$5 = (Enchantment)$$4.get($$2.m_188503_($$4.size()));
        } else {
            $$6 = this.f_80415_.get($$2.m_188503_(this.f_80415_.size()));
        }
        return EnchantRandomlyFunction.m_230979_(p_80429_, $$6, $$2);
    }

    private static ItemStack m_230979_(ItemStack p_230980_, Enchantment p_230981_, RandomSource p_230982_) {
        int $$3 = Mth.m_216271_(p_230982_, p_230981_.m_44702_(), p_230981_.m_6586_());
        if (p_230980_.m_150930_(Items.f_42517_)) {
            p_230980_ = new ItemStack(Items.f_42690_);
            EnchantedBookItem.m_41153_(p_230980_, new EnchantmentInstance(p_230981_, $$3));
        } else {
            p_230980_.m_41663_(p_230981_, $$3);
        }
        return p_230980_;
    }

    public static Builder m_165191_() {
        return new Builder();
    }

    public static LootItemConditionalFunction.Builder<?> m_80440_() {
        return EnchantRandomlyFunction.m_80683_(p_80438_ -> new EnchantRandomlyFunction((LootItemCondition[])p_80438_, (Collection<Enchantment>)ImmutableList.of()));
    }

    public static class Builder
    extends LootItemConditionalFunction.Builder<Builder> {
        private final Set<Enchantment> f_80441_ = Sets.newHashSet();

        @Override
        protected Builder m_6477_() {
            return this;
        }

        public Builder m_80444_(Enchantment p_80445_) {
            this.f_80441_.add(p_80445_);
            return this;
        }

        @Override
        public LootItemFunction m_7453_() {
            return new EnchantRandomlyFunction(this.m_80699_(), this.f_80441_);
        }

        @Override
        protected /* synthetic */ LootItemConditionalFunction.Builder m_6477_() {
            return this.m_6477_();
        }
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<EnchantRandomlyFunction> {
        @Override
        public void m_6170_(JsonObject p_80454_, EnchantRandomlyFunction p_80455_, JsonSerializationContext p_80456_) {
            super.m_6170_(p_80454_, p_80455_, p_80456_);
            if (!p_80455_.f_80415_.isEmpty()) {
                JsonArray $$3 = new JsonArray();
                for (Enchantment $$4 : p_80455_.f_80415_) {
                    ResourceLocation $$5 = Registry.f_122825_.m_7981_($$4);
                    if ($$5 == null) {
                        throw new IllegalArgumentException("Don't know how to serialize enchantment " + $$4);
                    }
                    $$3.add((JsonElement)new JsonPrimitive($$5.toString()));
                }
                p_80454_.add("enchantments", (JsonElement)$$3);
            }
        }

        @Override
        public EnchantRandomlyFunction m_6821_(JsonObject p_80450_, JsonDeserializationContext p_80451_, LootItemCondition[] p_80452_) {
            ArrayList $$3 = Lists.newArrayList();
            if (p_80450_.has("enchantments")) {
                JsonArray $$4 = GsonHelper.m_13933_(p_80450_, "enchantments");
                for (JsonElement $$5 : $$4) {
                    String $$6 = GsonHelper.m_13805_($$5, "enchantment");
                    Enchantment $$7 = Registry.f_122825_.m_6612_(new ResourceLocation($$6)).orElseThrow(() -> new JsonSyntaxException("Unknown enchantment '" + $$6 + "'"));
                    $$3.add($$7);
                }
            }
            return new EnchantRandomlyFunction(p_80452_, $$3);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}

