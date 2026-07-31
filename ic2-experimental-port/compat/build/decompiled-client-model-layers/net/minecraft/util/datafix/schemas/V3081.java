/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.templates.TypeTemplate
 */
package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.util.datafix.schemas.V100;

public class V3081
extends NamespacedSchema {
    public V3081(int p_216784_, Schema p_216785_) {
        super(p_216784_, p_216785_);
    }

    protected static void m_216788_(Schema p_216789_, Map<String, Supplier<TypeTemplate>> p_216790_, String p_216791_) {
        p_216789_.register(p_216790_, p_216791_, () -> V100.m_17330_(p_216789_));
        p_216789_.register(p_216790_, "minecraft:warden", () -> DSL.optionalFields((String)"ArmorItems", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_216789_)), (String)"HandItems", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_216789_)), (String)"listener", (TypeTemplate)DSL.optionalFields((String)"event", (TypeTemplate)DSL.optionalFields((String)"game_event", (TypeTemplate)References.f_216719_.in(p_216789_)))));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_216795_) {
        Map $$1 = super.registerEntities(p_216795_);
        V3081.m_216788_(p_216795_, $$1, "minecraft:warden");
        return $$1;
    }
}

