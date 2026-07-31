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

public class V808
extends NamespacedSchema {
    public V808(int p_18170_, Schema p_18171_) {
        super(p_18170_, p_18171_);
    }

    protected static void m_18174_(Schema p_18175_, Map<String, Supplier<TypeTemplate>> p_18176_, String p_18177_) {
        p_18175_.register(p_18176_, p_18177_, () -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_18175_))));
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_18179_) {
        Map $$1 = super.registerBlockEntities(p_18179_);
        V808.m_18174_(p_18179_, $$1, "minecraft:shulker_box");
        return $$1;
    }
}

