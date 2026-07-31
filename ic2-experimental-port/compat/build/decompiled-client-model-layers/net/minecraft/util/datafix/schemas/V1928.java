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

public class V1928
extends NamespacedSchema {
    public V1928(int p_17798_, Schema p_17799_) {
        super(p_17798_, p_17799_);
    }

    protected static TypeTemplate m_17800_(Schema p_17801_) {
        return DSL.optionalFields((String)"ArmorItems", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17801_)), (String)"HandItems", (TypeTemplate)DSL.list((TypeTemplate)References.f_16782_.in(p_17801_)));
    }

    protected static void m_17802_(Schema p_17803_, Map<String, Supplier<TypeTemplate>> p_17804_, String p_17805_) {
        p_17803_.register(p_17804_, p_17805_, () -> V1928.m_17800_(p_17803_));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17809_) {
        Map $$1 = super.registerEntities(p_17809_);
        $$1.remove("minecraft:illager_beast");
        V1928.m_17802_(p_17809_, $$1, "minecraft:ravager");
        return $$1;
    }
}

