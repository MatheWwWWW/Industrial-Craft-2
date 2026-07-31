/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.templates.TypeTemplate
 */
package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.util.datafix.schemas.V100;

public class V2707
extends NamespacedSchema {
    public V2707(int p_145894_, Schema p_145895_) {
        super(p_145894_, p_145895_);
    }

    protected static void m_145898_(Schema p_145899_, Map<String, Supplier<TypeTemplate>> p_145900_, String p_145901_) {
        p_145899_.register(p_145900_, p_145901_, () -> V100.m_17330_(p_145899_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_145903_) {
        Map $$1 = super.registerEntities(p_145903_);
        V2707.m_145898_(p_145903_, $$1, "minecraft:marker");
        return $$1;
    }
}

