/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.slf4j.Logger;

public class SetNameFunction
extends LootItemConditionalFunction {
    private static final Logger f_81122_ = LogUtils.getLogger();
    final Component f_81123_;
    @Nullable
    final LootContext.EntityTarget f_81124_;

    SetNameFunction(LootItemCondition[] p_81127_, @Nullable Component p_81128_, @Nullable LootContext.EntityTarget p_81129_) {
        super(p_81127_);
        this.f_81123_ = p_81128_;
        this.f_81124_ = p_81129_;
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80744_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return this.f_81124_ != null ? ImmutableSet.of(this.f_81124_.m_79003_()) : ImmutableSet.of();
    }

    public static UnaryOperator<Component> m_81139_(LootContext p_81140_, @Nullable LootContext.EntityTarget p_81141_) {
        Entity $$2;
        if (p_81141_ != null && ($$2 = p_81140_.m_78953_(p_81141_.m_79003_())) != null) {
            CommandSourceStack $$3 = $$2.m_20203_().m_81325_(2);
            return p_81147_ -> {
                try {
                    return ComponentUtils.m_130731_($$3, p_81147_, $$2, 0);
                }
                catch (CommandSyntaxException $$3) {
                    f_81122_.warn("Failed to resolve text component", (Throwable)$$3);
                    return p_81147_;
                }
            };
        }
        return p_81152_ -> p_81152_;
    }

    @Override
    public ItemStack m_7372_(ItemStack p_81137_, LootContext p_81138_) {
        if (this.f_81123_ != null) {
            p_81137_.m_41714_((Component)SetNameFunction.m_81139_(p_81138_, this.f_81124_).apply(this.f_81123_));
        }
        return p_81137_;
    }

    public static LootItemConditionalFunction.Builder<?> m_165457_(Component p_165458_) {
        return SetNameFunction.m_80683_(p_165468_ -> new SetNameFunction((LootItemCondition[])p_165468_, p_165458_, null));
    }

    public static LootItemConditionalFunction.Builder<?> m_165459_(Component p_165460_, LootContext.EntityTarget p_165461_) {
        return SetNameFunction.m_80683_(p_165465_ -> new SetNameFunction((LootItemCondition[])p_165465_, p_165460_, p_165461_));
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<SetNameFunction> {
        @Override
        public void m_6170_(JsonObject p_81163_, SetNameFunction p_81164_, JsonSerializationContext p_81165_) {
            super.m_6170_(p_81163_, p_81164_, p_81165_);
            if (p_81164_.f_81123_ != null) {
                p_81163_.add("name", Component.Serializer.m_130716_(p_81164_.f_81123_));
            }
            if (p_81164_.f_81124_ != null) {
                p_81163_.add("entity", p_81165_.serialize((Object)p_81164_.f_81124_));
            }
        }

        @Override
        public SetNameFunction m_6821_(JsonObject p_81155_, JsonDeserializationContext p_81156_, LootItemCondition[] p_81157_) {
            MutableComponent $$3 = Component.Serializer.m_130691_(p_81155_.get("name"));
            LootContext.EntityTarget $$4 = GsonHelper.m_13845_(p_81155_, "entity", null, p_81156_, LootContext.EntityTarget.class);
            return new SetNameFunction(p_81157_, $$3, $$4);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}

