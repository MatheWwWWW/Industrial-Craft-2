/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSerializationContext
 */
package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

public class BonusLevelTableCondition
implements LootItemCondition {
    final Enchantment f_81507_;
    final float[] f_81508_;

    BonusLevelTableCondition(Enchantment p_81510_, float[] p_81511_) {
        this.f_81507_ = p_81510_;
        this.f_81508_ = p_81511_;
    }

    @Override
    public LootItemConditionType m_7940_() {
        return LootItemConditions.f_81820_;
    }

    @Override
    public Set<LootContextParam<?>> m_6231_() {
        return ImmutableSet.of(LootContextParams.f_81463_);
    }

    @Override
    public boolean test(LootContext p_81521_) {
        ItemStack $$1 = p_81521_.m_78953_(LootContextParams.f_81463_);
        int $$2 = $$1 != null ? EnchantmentHelper.m_44843_(this.f_81507_, $$1) : 0;
        float $$3 = this.f_81508_[Math.min($$2, this.f_81508_.length - 1)];
        return p_81521_.m_230907_().m_188501_() < $$3;
    }

    public static LootItemCondition.Builder m_81517_(Enchantment p_81518_, float ... p_81519_) {
        return () -> new BonusLevelTableCondition(p_81518_, p_81519_);
    }

    @Override
    public /* synthetic */ boolean test(Object object) {
        return this.test((LootContext)object);
    }

    public static class Serializer
    implements net.minecraft.world.level.storage.loot.Serializer<BonusLevelTableCondition> {
        @Override
        public void m_6170_(JsonObject p_81537_, BonusLevelTableCondition p_81538_, JsonSerializationContext p_81539_) {
            p_81537_.addProperty("enchantment", Registry.f_122825_.m_7981_(p_81538_.f_81507_).toString());
            p_81537_.add("chances", p_81539_.serialize((Object)p_81538_.f_81508_));
        }

        @Override
        public BonusLevelTableCondition m_7561_(JsonObject p_81547_, JsonDeserializationContext p_81548_) {
            ResourceLocation $$2 = new ResourceLocation(GsonHelper.m_13906_(p_81547_, "enchantment"));
            Enchantment $$3 = Registry.f_122825_.m_6612_($$2).orElseThrow(() -> new JsonParseException("Invalid enchantment id: " + $$2));
            float[] $$4 = GsonHelper.m_13836_(p_81547_, "chances", p_81548_, float[].class);
            return new BonusLevelTableCondition($$3, $$4);
        }

        @Override
        public /* synthetic */ Object m_7561_(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.m_7561_(jsonObject, jsonDeserializationContext);
        }
    }
}

