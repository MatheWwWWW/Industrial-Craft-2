/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 */
package net.minecraft.advancements.critereon;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.world.level.storage.loot.Deserializers;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SerializationContext {
    public static final SerializationContext f_64768_ = new SerializationContext();
    private final Gson f_64769_ = Deserializers.m_78798_().create();

    public final JsonElement m_64772_(LootItemCondition[] p_64773_) {
        return this.f_64769_.toJsonTree((Object)p_64773_);
    }
}

