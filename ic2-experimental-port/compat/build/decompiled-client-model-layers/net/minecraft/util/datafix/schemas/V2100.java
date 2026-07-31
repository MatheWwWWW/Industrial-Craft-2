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

public class V2100
extends NamespacedSchema {
    public V2100(int p_17833_, Schema p_17834_) {
        super(p_17833_, p_17834_);
    }

    protected static void m_17837_(Schema p_17838_, Map<String, Supplier<TypeTemplate>> p_17839_, String p_17840_) {
        p_17838_.register(p_17839_, p_17840_, () -> V100.m_17330_(p_17838_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17846_) {
        Map $$1 = super.registerEntities(p_17846_);
        V2100.m_17837_(p_17846_, $$1, "minecraft:bee");
        V2100.m_17837_(p_17846_, $$1, "minecraft:bee_stinger");
        return $$1;
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_17844_) {
        Map $$1 = super.registerBlockEntities(p_17844_);
        p_17844_.register($$1, "minecraft:beehive", () -> DSL.optionalFields((String)"Bees", (TypeTemplate)DSL.list((TypeTemplate)DSL.optionalFields((String)"EntityData", (TypeTemplate)References.f_16785_.in(p_17844_)))));
        return $$1;
    }
}

