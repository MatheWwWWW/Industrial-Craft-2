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
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetPotionFunction
extends LootItemConditionalFunction {
    final Potion f_193067_;

    SetPotionFunction(LootItemCondition[] p_193069_, Potion p_193070_) {
        super(p_193069_);
        this.f_193067_ = p_193070_;
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_193030_;
    }

    @Override
    public ItemStack m_7372_(ItemStack p_193073_, LootContext p_193074_) {
        PotionUtils.m_43549_(p_193073_, this.f_193067_);
        return p_193073_;
    }

    public static LootItemConditionalFunction.Builder<?> m_193075_(Potion p_193076_) {
        return SetPotionFunction.m_80683_(p_193079_ -> new SetPotionFunction((LootItemCondition[])p_193079_, p_193076_));
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<SetPotionFunction> {
        @Override
        public void m_6170_(JsonObject p_193090_, SetPotionFunction p_193091_, JsonSerializationContext p_193092_) {
            super.m_6170_(p_193090_, p_193091_, p_193092_);
            p_193090_.addProperty("id", Registry.f_122828_.m_7981_(p_193091_.f_193067_).toString());
        }

        @Override
        public SetPotionFunction m_6821_(JsonObject p_193082_, JsonDeserializationContext p_193083_, LootItemCondition[] p_193084_) {
            String $$3 = GsonHelper.m_13906_(p_193082_, "id");
            Potion $$4 = Registry.f_122828_.m_6612_(ResourceLocation.m_135820_($$3)).orElseThrow(() -> new JsonSyntaxException("Unknown potion '" + $$3 + "'"));
            return new SetPotionFunction(p_193084_, $$4);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}

