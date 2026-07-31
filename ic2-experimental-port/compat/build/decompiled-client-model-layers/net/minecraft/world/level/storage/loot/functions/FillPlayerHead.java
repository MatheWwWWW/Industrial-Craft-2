/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  com.mojang.authlib.GameProfile
 */
package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.authlib.GameProfile;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class FillPlayerHead
extends LootItemConditionalFunction {
    final LootContext.EntityTarget f_80602_;

    public FillPlayerHead(LootItemCondition[] p_80604_, LootContext.EntityTarget p_80605_) {
        super(p_80604_);
        this.f_80602_ = p_80605_;
    }

    @Override
    public LootItemFunctionType m_7162_() {
        return LootItemFunctions.f_80754_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return ImmutableSet.of(this.f_80602_.m_79003_());
    }

    @Override
    public ItemStack m_7372_(ItemStack p_80608_, LootContext p_80609_) {
        Entity $$2;
        if (p_80608_.m_150930_(Items.f_42680_) && ($$2 = p_80609_.m_78953_(this.f_80602_.m_79003_())) instanceof Player) {
            GameProfile $$3 = ((Player)$$2).m_36316_();
            p_80608_.m_41784_().m_128365_("SkullOwner", NbtUtils.m_129230_(new CompoundTag(), $$3));
        }
        return p_80608_;
    }

    public static LootItemConditionalFunction.Builder<?> m_165207_(LootContext.EntityTarget p_165208_) {
        return FillPlayerHead.m_80683_(p_165211_ -> new FillPlayerHead((LootItemCondition[])p_165211_, p_165208_));
    }

    public static class Serializer
    extends LootItemConditionalFunction.Serializer<FillPlayerHead> {
        @Override
        public void m_6170_(JsonObject p_80619_, FillPlayerHead p_80620_, JsonSerializationContext p_80621_) {
            super.m_6170_(p_80619_, p_80620_, p_80621_);
            p_80619_.add("entity", p_80621_.serialize((Object)p_80620_.f_80602_));
        }

        @Override
        public FillPlayerHead m_6821_(JsonObject p_80615_, JsonDeserializationContext p_80616_, LootItemCondition[] p_80617_) {
            LootContext.EntityTarget $$3 = GsonHelper.m_13836_(p_80615_, "entity", p_80616_, LootContext.EntityTarget.class);
            return new FillPlayerHead(p_80617_, $$3);
        }

        @Override
        public /* synthetic */ LootItemConditionalFunction m_6821_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootItemConditionArray) {
            return this.m_6821_(jsonObject, jsonDeserializationContext, lootItemConditionArray);
        }
    }
}

