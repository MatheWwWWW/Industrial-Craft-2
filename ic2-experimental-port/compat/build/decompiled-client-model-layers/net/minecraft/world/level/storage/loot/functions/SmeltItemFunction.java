/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import java.util.Optional;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.slf4j.Logger;

public class SmeltItemFunction
extends LootItemConditionalFunction {
    private static final Logger f_81260_ = LogUtils.getLogger();

    SmeltItemFunction(LootItemCondition[] p_81263_) {
        super(p_81263_);
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80740_;
    }

    @Override
    public ItemStack m_7372_(ItemStack p_81268_, LootContext p_81269_) {
        ItemStack $$3;
        if (p_81268_.m_41619_()) {
            return p_81268_;
        }
        Optional<SmeltingRecipe> $$2 = p_81269_.m_78952_().m_7465_().m_44015_(RecipeType.f_44108_, new SimpleContainer(p_81268_), p_81269_.m_78952_());
        if ($$2.isPresent() && !($$3 = $$2.get().m_8043_()).m_41619_()) {
            ItemStack $$4 = $$3.m_41777_();
            $$4.m_41764_(p_81268_.m_41613_());
            return $$4;
        }
        f_81260_.warn("Couldn't smelt {} because there is no smelting recipe", (Object)p_81268_);
        return p_81268_;
    }

    public static LootItemConditionalFunction.Builder<?> m_81271_() {
        return SmeltItemFunction.m_80683_(SmeltItemFunction::new);
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<SmeltItemFunction> {
        @Override
        public SmeltItemFunction m_6821_(JsonObject p_81274_, JsonDeserializationContext p_81275_, LootItemCondition[] p_81276_) {
            return new SmeltItemFunction(p_81276_);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}

