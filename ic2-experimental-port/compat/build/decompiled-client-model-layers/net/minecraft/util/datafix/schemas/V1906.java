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

public class V1906
extends NamespacedSchema {
    public V1906(int p_17768_, Schema p_17769_) {
        super(p_17768_, p_17769_);
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_17780_) {
        Map $$1 = super.registerBlockEntities(p_17780_);
        V1906.m_17775_(p_17780_, $$1, "minecraft:barrel");
        V1906.m_17775_(p_17780_, $$1, "minecraft:smoker");
        V1906.m_17775_(p_17780_, $$1, "minecraft:blast_furnace");
        p_17780_.register($$1, "minecraft:lectern", p_17774_ -> DSL.optionalFields((String)"Book", (TypeTemplate)References.f_16782_.in(p_17780_)));
        p_17780_.registerSimple($$1, "minecraft:bell");
        return $$1;
    }

    protected static void m_17775_(Schema p_17776_, Map<String, Supplier<TypeTemplate>> p_17777_, String p_17778_) {
        p_17776_.register(p_17777_, p_17778_, () -> DSL.optionalFields((String)"Items", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17776_))));
    }
}

