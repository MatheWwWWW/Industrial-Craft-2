/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.advancements.critereon;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.Deserializers;
import net.minecraft.world.level.storage.loot.PredicateManager;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.slf4j.Logger;

public class DeserializationContext {
    private static final Logger f_25865_ = LogUtils.getLogger();
    private final ResourceLocation f_25866_;
    private final PredicateManager f_25867_;
    private final Gson f_25868_ = Deserializers.m_78798_().create();

    public DeserializationContext(ResourceLocation p_25871_, PredicateManager p_25872_) {
        this.f_25866_ = p_25871_;
        this.f_25867_ = p_25872_;
    }

    public final LootItemCondition[] m_25874_(JsonArray p_25875_, String p_25876_, LootContextParamSet p_25877_) {
        LootItemCondition[] $$3 = (LootItemCondition[])this.f_25868_.fromJson((JsonElement)p_25875_, LootItemCondition[].class);
        ValidationContext $$4 = new ValidationContext(p_25877_, this.f_25867_::m_79252_, p_25883_ -> null);
        for (LootItemCondition $$5 : $$3) {
            $$5.m_6169_($$4);
            $$4.m_79352_().forEach((p_25880_, p_25881_) -> f_25865_.warn("Found validation problem in advancement trigger {}/{}: {}", new Object[]{p_25876_, p_25880_, p_25881_}));
        }
        return $$3;
    }

    public ResourceLocation m_25873_() {
        return this.f_25866_;
    }
}

